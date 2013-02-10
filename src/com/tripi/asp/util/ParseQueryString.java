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
package com.tripi.asp.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jregex.Matcher;
import jregex.Pattern;
import jregex.RETokenizer;

import org.apache.log4j.Category;

/**
 * This class implements parsing for query string variables.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class ParseQueryString
{
    /** Debugging category */
    private static final Category DBG = Category.getInstance(ParseQueryString.class);
    
    /** Pattern used to tokenize the list */
    protected static final Pattern tokenizerPattern = new Pattern("&");
    
    /** Pattern used to find arguments/variables */
    protected static final Pattern variablesPattern = new Pattern("^([^=]*)(=(.*))?$");
    
    /**
     * Function to perform a parse on a query string. This function returns a map
     * containing String keys which are the variable names, and Set values
     * containing the values for the string.
     * @param queryString string to parse
     * @return map containing the results.
     */
    public static Map parse(String queryString)
    {
        return parse(new RETokenizer(tokenizerPattern, queryString));
    }
    
    /**
     * Function to perform a parse on a query string. This function returns a map
     * containing String keys which are the variable names, and Set values
     * containing the values for the string.
     * @param reader reader
     * @param length length of input
     * @return map containing the results
     * @throws IOException on I/O error.
     */
    public static Map parse(java.io.Reader reader, int length) throws IOException
    {
        return parse(new RETokenizer(tokenizerPattern, reader, length));
    }
    
    /**
     * Internal function to perform the parsing.
     * @param tok tokenizer
     * @return parse results.
     */
    private static Map parse(RETokenizer tok)
    {
        Map tmpMap = new java.util.HashMap();
        
        while (tok.hasMore())
        {
            String value = tok.nextToken();
            if (DBG.isDebugEnabled()) DBG.debug("Token: " + value);
            Matcher m = variablesPattern.matcher(value);
            if (m.matches())
            {
                String arg = Tools.urlDecode(m.group(1));
                String val = m.group(3);
                if (val == null) val = "";
                val = Tools.urlDecode(val);
                if (DBG.isDebugEnabled()) DBG.debug("Arg: " + arg + "/ val: " + val);
                List list = (List)tmpMap.get(arg);
                if (list == null)
                {
                    list = new java.util.ArrayList();
                    tmpMap.put(arg, list);
                }
                list.add(val);
            }
        }
        Map retMap = new java.util.HashMap();
        for (Iterator i = tmpMap.keySet().iterator(); i.hasNext();)
        {
            String key = (String)i.next();
            List list = (List)tmpMap.get(key);
            String strArray[] = new String[list.size()];
            int x = 0;
            for (Iterator j = list.iterator(); j.hasNext();)
            {
                strArray[x++] = (String)j.next();
            }
            retMap.put(key, strArray);
        }
        return retMap;
    }
}

