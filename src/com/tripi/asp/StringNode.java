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

/**
 * This class represents a string in the parsed ASP code.
 *
 * @author Terence Haddock
 */
public class StringNode extends DefaultNode
{
    /** String this string node contains */
    String string;

    /**
     * Constructor.
     * @param string String value
     */
    public StringNode(Object string)
    {
        this.string = (String)string;
    }

    /**
     * Dumps this string value.
     * @see DefaultNode#dump
     */
    public void dump()
    {
        System.out.print(string);
    }

    /**
     * Get the string value.
     * @return string value
     */
    public String getString()
    {
        return string;
    }

    /**
     * Executes this string, returns the string value stripped of the
     * surrounding quotes and any escape sequences.
     * @param context ASP Context
     * @return Strng value
     * @see DefaultNode#execute(AspContext)
     */
    public Object execute(AspContext context)
    {
        return string;
        /*String substr = string.substring(1, string.length() - 1);
        int index = substr.indexOf("\"\"");
        while (index >= 0) {
            substr = substr.substring(0, index) + substr.substring(index+1);
            index = substr.indexOf("\"\"", index+1);
        }
        return substr;*/
    }

    /**
     * Construct a string node from a string token.
     * @param str String token
     */
    public static StringNode fromStringToken(String str)
    {
        String substr = str.substring(1, str.length() - 1);
        int index = substr.indexOf("\"\"");
        while (index >= 0) {
            substr = substr.substring(0, index) + substr.substring(index+1);
            index = substr.indexOf("\"\"", index+1);
        }
        return new StringNode(substr);
    }
};

