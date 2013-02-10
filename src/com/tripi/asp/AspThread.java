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

import org.apache.log4j.Logger;

import com.tripi.asp.cache.ScriptCache;

/**
 * AspThread performs the actual processing of an ASP process.
 * @author Terence Haddock
 * @version 0.9
 */
public class AspThread implements Runnable
{
	/** Debugging category */
    private Logger DBG = Logger.getLogger(AspThread.class);

    /** Running context */
    AspContext context;

    /** Filename of ASP script */
    String filename;

    /** Exception thrown during execution */
    AspException ex = null;
	
	/** Cache of compiled scripts */    
	private ScriptCache cache;

    /**
     * Constructor.
     * @param context Global context
     * @param filename Filename of ASP code
     */
    public AspThread(AspContext context, String filename, ScriptCache cache)
    {
        this.context = context;
        this.filename = filename;
        this.cache = cache;
    }

    /**
     * Executes this thread, processing the ASP code.
     */
    public void run()
    {
        try {
            AspHandler handler = new AspHandler(context, filename, cache);
            if (DBG.isDebugEnabled()) DBG.debug("Parsing");
            Node node = handler.parse();

            if (DBG.isDebugEnabled()) DBG.debug("Preparing");
            node.prepare(context);

            if (DBG.isDebugEnabled()) DBG.debug("Executing");
            node.execute(context);
        
            if (DBG.isDebugEnabled()) DBG.debug("Done");
        } catch (AspException ex)
        {
            if (DBG.isDebugEnabled()) DBG.debug("AspException");
            if (!(ex instanceof AspExitScriptException))
                DBG.error("AspException", ex);
            this.ex = ex;
        } catch (RuntimeException ex)
        {
            if (DBG.isDebugEnabled()) DBG.debug("RuntimeException");
            DBG.error("Uncaught RuntimeException", ex);
        } catch (Exception ex)
        {
            if (DBG.isDebugEnabled()) DBG.debug("Exception");
            DBG.error("Uncaught exception", ex);
        }
    }

    /**
     * Static class which checks if a timeout has occured.
     * @param fileScope file-specific scope
     * @throws AspTimeoutException if a timeout has occured
     */
    public static void checkTimeout(AspContext context) throws AspException
    {
        IdentNode timeout = new IdentNode("!timeout");
        if (context.inScope(timeout)) {
            throw new AspTimeoutException();
        }
    }

    /**
     * Static method which marks that a timeout has occured
     * @param fileScope file-specific scope
     */
    public static void timeout(AspContext globalScope) throws AspException
    {
        IdentNode timeout = new IdentNode("!timeout");
        /* This will go into the global context */
        /* This forceScope statement is here to prevent an exception when
           option explicit is in effect */
        globalScope.forceScope(timeout);
        globalScope.setValue(timeout, "Yes");
    }

    /**
     * Obtains the exception thrown.
     * @return exception which was thrown, or null if none was thrown.
     */
    public Exception getException()
    {
        return ex;
    }
}
