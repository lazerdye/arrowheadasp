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
package com.tripi.asp.parse;

import com.tripi.asp.*;

import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;
import jregex.*;
import org.apache.log4j.Category;

/**
 * This class is a nested token manager, and handles VBScript tokens 
 * embedded within ASP files.
 */
public class NestedTokenManager implements TokenManager
{
    /** Debugging class */
    private static final transient Category DBG = Category.getInstance(NestedTokenManager.class);

    /** This inner class contains information about a file being loaded */
    static class FileInfo
    {
        /** Abosolute filename for the file. */
        String filename; 

        /** Active token manager for the file. */
        AspParseTokenManager tokManager;
    }

    /** Stack of current parsers */
    Stack fileInfoStack = new Stack();

    /** Are we in VBScript or looking as ASP tokens */
    boolean inScript;

    /** The token factory we use to read VBScript tokens */
    TokenManager subTokenManager;

    /** File Factory used to obtain input streams from fiels */
    FileFactory fileFactory;

    /** Next token */
    Token nextToken = null;

    /**
     * Constructor, with an initial filename and file factory.
     * @param filename Initial filename
     * @param fileFactory factory used to obtain files
     * @throws IOException on error
     */
    public NestedTokenManager(String filename, FileFactory fileFactory)
        throws AspException
    {
        this.fileFactory = fileFactory;
        this.inScript = false;
        FileInfo fileInfo = getFileInfo(filename);
        fileInfoStack.push(fileInfo);
    }

    /**
     * Get the FileInfo structure for the file.
     * @param base Base filename
     * @param filename Relative filename to open
     * @return FileInfo class containing filename and parser.
     * @throws IOException on input/output error
     */
    FileInfo getFileInfo(String filename) throws AspException
    {
        FileInfo fileInfo = new FileInfo();
        fileInfo.filename = filename;

		Reader r = fileFactory.getResource(fileInfo.filename);
        SimpleCharStream stream = new SimpleCharStream(r);
        fileInfo.tokManager = new AspParseTokenManager(stream);
        return fileInfo;
    }

    /**
     * Internal function to find the contents of an SGML token.
     * @param str String to find contents of
     * @return contents of SGML token
     */
    private String getTokenContents(String str)
    {
        final Pattern p = new Pattern("^<[^>]*>(.*)<[^>]*>$",
            REFlags.MULTILINE | REFlags.IGNORE_SPACES | REFlags.DOTALL);
        Matcher m = p.matcher(str);
        if (!m.matches()) throw new AspRuntimeException("Internal error");
        return m.group(1);
    }

    /**
     * Obtains a token manager for the data contained within the token.
     * The token should be an ASPScript token, the <%, %> characters will
     * be stripped.
     * @param tok ASP Token to parse code from.
     * @return TokenManager for parsing the code.
     */
    TokenManager getTokenManager(Token tok)
    {
        String subStr;
        if (tok.kind == AspParseConstants.ASPSCRIPT ||
            tok.kind == AspParseConstants.OUTPUTSCRIPT)
        {
            subStr = tok.image.substring(2, tok.image.length() - 2);
        } else if (tok.kind == AspParseConstants.SERVERSCRIPT)
        {
            subStr = getTokenContents(tok.image);
        } else {
            DebugContext ctx = new DebugContext();
            tok.fillDebugContext(ctx);
            throw new AspRuntimeException("Internal parse error: " +
                "Unexptected token type " + tok.kind, ctx);
        }
        StringReader sr = new StringReader(subStr);
        SimpleCharStream stream = new SimpleCharStream(sr, tok.beginLine,
            tok.beginColumn + 2);
        return new VBScriptTokenManagerInterface(stream);
    }

    /**
     * Obtain the filename from an include specification.
     * @param include include specification which to obtain file from.
     * @return filename for the include specification.
     */
    String getFilenameFromInclude(String baseFilename, String include)
        throws AspException
    {
        final Pattern p = new Pattern("<!--[ \t]*#include[ \t]*(file|virtual)" +
            "[ \t]*=[ \t]*\"([^\"]*)\"[ \t]*-->", REFlags.IGNORE_CASE);
        Matcher m = p.matcher(include);
        if (!m.matches())
        {
            throw new AspRuntimeException("Internal error");
        }
        String virtual = m.group(1);
        String relativeFilename = m.group(2);
        String filename = fileFactory.resolveFile(baseFilename,
                    relativeFilename, virtual.equalsIgnoreCase("virtual"));
        return filename;
    }

    /**
     * Clone the token, keeping debugging information and image.
     * @param tok Token to close
     */
    public Token cloneToken(Token tok)
    {
        Token retTok = new Token();
        retTok.image = tok.image;
        retTok.kind = 0;
        retTok.beginLine = tok.beginLine;
        retTok.beginColumn = tok.beginColumn;
        retTok.endLine = tok.endLine;
        retTok.endColumn = tok.endColumn;
        retTok.filename = tok.filename;
        return retTok;
    }

    /**
     * Obtain the next token from the stream.
     * @return next token from the stream.
     */
    public Token getNextToken()
    {
        if (DBG.isDebugEnabled()) DBG.debug("getNextToken");
        if (nextToken != null) {
            Token retTok = nextToken;
            nextToken = null;   
            if (DBG.isDebugEnabled()) DBG.debug("nextToken: " + retTok.kind);
            return retTok;
        }
        if (DBG.isDebugEnabled()) DBG.debug("Peek");
        FileInfo fileInfo = (FileInfo)fileInfoStack.peek();
        if (DBG.isDebugEnabled()) DBG.debug("inScript: " + inScript);
        if (inScript)
        try {
            if (DBG.isDebugEnabled()) DBG.debug("subTokenManager: " + subTokenManager);
            Token tok = subTokenManager.getNextToken();
            if (DBG.isDebugEnabled()) DBG.debug("filename: " + fileInfo.filename);
            tok.filename = fileInfo.filename;
            if (DBG.isDebugEnabled()) DBG.debug("Language token: " + tok.kind);
            if (tok.kind != 0)
                return tok;
            inScript = false;
        } catch (TokenMgrError e) {
            DBG.error(e);
            e.setFilename(fileInfo.filename);
            throw e;
        }
        Token tok = null;
        try {
            AspParseTokenManager tm = fileInfo.tokManager;
            if (DBG.isDebugEnabled()) DBG.debug("Asp getNextToken");
            tok = tm.getNextToken();
            if (DBG.isDebugEnabled()) DBG.debug("ASP Token: " + tok.kind);
            tok.filename = fileInfo.filename;
            if (tok.kind == 0) {
                fileInfoStack.pop();
                if (fileInfoStack.empty())
                {
                    return tok;
                }
                return getNextToken();
            }
            switch(tok.kind)
            {
                case AspParseConstants.ASPSCRIPT:
                    subTokenManager = getTokenManager(tok);
                    inScript = true;
                    return getNextToken();
                case AspParseConstants.SERVERSCRIPT:
                    subTokenManager = getTokenManager(tok);
                    inScript = true;
                    return getNextToken();
                case AspParseConstants.OUTPUTSCRIPT:
                    subTokenManager = getTokenManager(tok);
                    inScript = true;
                    nextToken = cloneToken(tok);
                    nextToken.kind = VBScriptConstants.OUTPUT;

                    Token retTok = cloneToken(tok);
                    retTok.kind = VBScriptConstants.NL;
                    retTok.image = "\n";
                    return retTok;
                case AspParseConstants.INCLUDE:
                    String filename = getFilenameFromInclude(
                    fileInfo.filename, tok.image);
                    FileInfo subFileInfo = getFileInfo(filename);
                    fileInfoStack.push(subFileInfo);
                    return getNextToken();
                case AspParseConstants.HTML:
                    if (!containsOnlyWS(tok.image))
                    {
                        Token thisTok = cloneToken(tok);

                        nextToken = cloneToken(tok);
                        nextToken.kind = VBScriptConstants.HTML;
                    }

                    Token subTok = cloneToken(tok);
                    subTok.kind = VBScriptConstants.NL;
                    subTok.image = "\n";
                    return subTok;
                case AspParseConstants.LANGUAGEDEF:
                    /* Skipped */
                    return getNextToken();
                default:
                    DebugContext ctx = new DebugContext();
                    tok.fillDebugContext(ctx);
                    throw new AspRuntimeException("Unknown token: " + tok.kind,
                        ctx);
            }
        } catch (TokenMgrError e)
        {
            e.setFilename(fileInfo.filename);
            throw e;
        } catch (AspException ex)
        {
            DBG.debug("AspException thrown");
            if (!ex.hasContext() && tok != null) {
                DebugContext ctx = new DebugContext();
                tok.fillDebugContext(ctx);
                ex.setContext(ctx);
            }
            throw new AspRuntimeSubException(ex);
        }
    }

    /**
     * This function tests if the string contains only whitespace.
     * @param str String to test for white space
     * @return <b>true</b> if the string contains only whitespace, <b>false</b>
     *      otherwise.
     */
    public boolean containsOnlyWS(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (!Character.isWhitespace(str.charAt(i)))
                return false;
        }
        return true;
    }
}

