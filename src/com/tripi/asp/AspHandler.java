/**
 * ArrowHead ASP Server 
 * This is a source file for the ArrowHead ASP Server - an 100% Java
 * VBScript interpreter and ASP server.
 *
 * For more information, see http://www.tripi.com/arrowhead
 *
 * Copyright (C) 2002  Terence Haddock
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package com.tripi.asp;

import org.apache.log4j.Category;

import com.tripi.asp.cache.CachedScript;
import com.tripi.asp.cache.HashMapScriptCache;
import com.tripi.asp.cache.ScriptCache;
import com.tripi.asp.parse.NestedTokenManager;
import com.tripi.asp.parse.ParseException;
import com.tripi.asp.parse.TokenMgrError;
import com.tripi.asp.parse.VBScript;

/**
 * This class handles the parsing of ASP code.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class AspHandler
{
    static private final transient Category DBG = Category.getInstance(AspHandler.class);

    /**
     * Cache of pre-parsed files.
     */
    private ScriptCache cachedScripts = null;

    /**
     * Current ASP Context.
     */
    private AspContext context;

    /**
     * Display path to ASP file
     */
    private String filename;

    /**
     * Constructor.
     * @param file Full path to ASP file to parse.
     * @param filename Display path to ASP file to parse
     */
    public AspHandler(AspContext context, String filename, ScriptCache cache) throws AspNestedException
    {
        this.context = context;
        this.filename = filename;
        this.cachedScripts = cache;
    }

    /**
     * Parses the current file and returns the base node of the file.
     *
     * @return Node representation of parsed file.
     */
    public Node parse() throws AspException
    {
        CachedScript cachedScript = cachedScripts.get(filename, context);
        synchronized(cachedScript)
        {
            if (cachedScript.node != null)
            {
                long time = System.currentTimeMillis();
                if ((time - cachedScript.checkedTime) > 10000)
                {
                    cachedScript.checkedTime = time;
                    if (!cachedScript.fileFactory.isModified())
                        return cachedScript.node;
                    else
                        if (DBG.isDebugEnabled()) DBG.debug("File was modified, re-parsing");
                } else {
                    return cachedScript.node;
                }
            } else {
                if (DBG.isDebugEnabled()) DBG.debug("No node in cache");
            }
            try {
                if (DBG.isDebugEnabled())
                    DBG.debug("Opening token manager for " + filename);

                if (DBG.isDebugEnabled()) DBG.debug("Clearing cache");
                cachedScript.fileFactory.clearLoadedFilesCache();

                if (DBG.isDebugEnabled()) DBG.debug("Resolving file");
                String absoluteFilename = cachedScript.fileFactory.
                    resolveFile(null, filename, true);
                if (DBG.isDebugEnabled()) DBG.debug("Creating TokenManager");
                NestedTokenManager tokManager = new NestedTokenManager(
                    absoluteFilename, cachedScript.fileFactory);
                if (DBG.isDebugEnabled()) DBG.debug("Creating Parser");
                VBScript script = new VBScript(tokManager);
                if (DBG.isDebugEnabled()) DBG.debug("Parsing file");
                Node value = script.WholeFile();

                if (DBG.isDebugEnabled()) DBG.debug("Updating values");
                cachedScript.node = value;
                cachedScript.checkedTime = System.currentTimeMillis();

                if (DBG.isDebugEnabled()) DBG.debug("Done.");
                return value;
            } catch (AspException ex) {
                throw ex;
            } catch (AspRuntimeSubException ex) {
                throw ex.getException();
            } catch (AspRuntimeException ex) {
                throw new AspNestedException(ex);
            } catch (TokenMgrError ex) {
                DebugContext ctx = new DebugContext(ex.getFilename(),
                    ex.getLineNo());
                throw new AspNestedException(ex, ctx);
            } catch (ParseException ex) {
                DebugContext ctx = new DebugContext();
                ex.currentToken.fillDebugContext(ctx);
                throw new AspNestedException(ex, ctx);
            }
        }
    }
}
