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
package com.tripi.asp.test;

import java.io.Reader;

import com.tripi.asp.parse.AspParseTokenManager;
import com.tripi.asp.parse.NestedTokenManager;
import com.tripi.asp.parse.SimpleCharStream;
import com.tripi.asp.parse.Token;

/**
 * This class tests the tokenizer.
 */
public class AspLexerTest
{
    /**
     * Main procedure.
     */
    public static void main(String args[])
    {
        try {
            String filename = args[0];

    		Reader r = new SimpleFileFactory().getResource(filename);
            SimpleCharStream stream = new SimpleCharStream(r);
            AspParseTokenManager tokManager = new AspParseTokenManager(stream);
            Token token;
            do
            {
                token = tokManager.getNextToken();
            	System.out.println("Tok: [" + token.kind + "] [" + token.image + "]");
            } while (token.kind != 0);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getClass());
            ex.printStackTrace();
        }
    }
}
