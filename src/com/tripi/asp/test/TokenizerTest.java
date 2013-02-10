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

import com.tripi.asp.*;
import com.tripi.asp.parse.*;

/**
 * This class tests the tokenizer.
 */
public class TokenizerTest
{
    /**
     * Main procedure.
     */
    public static void main(String args[])
    {
        try {
            String filename = args[0];
            NestedTokenManager tokManager = new NestedTokenManager(filename,
                new SimpleFileFactory());
            VBScript script = new VBScript(tokManager);
            Node node = script.WholeFile();
            node.dump();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getClass());
            ex.printStackTrace();
        }
    }
}
