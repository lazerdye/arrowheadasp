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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import jregex.Pattern;

import org.apache.log4j.Category;

import com.tripi.asp.AspCollection;
import com.tripi.asp.AspException;
import com.tripi.asp.AspNestedException;
import com.tripi.asp.AspReadOnlyException;
import com.tripi.asp.Constants;
import com.tripi.asp.SimpleMap;
import com.tripi.asp.SimpleReference;
import com.tripi.asp.Types;
import com.tripi.asp.UndefinedValueNode;

public class Tools
{
    /** Debugging category */
    private static Category DBG = Category.getInstance(Tools.class);

    /**
     * This function parses a query string. The keys and values
     * are left un-decoded so post-processing can occur.
     * @param queryString query string to parse
     * @return map of key -> list of values
     */
    public static Map parseQueryString(String queryString)
    {
        if (DBG.isDebugEnabled()) 
        {
            DBG.debug("Parse: " + queryString);
            try {
                String testString = new String(
                    queryString.getBytes("ISO8859-1"), "UTF-8");
                DBG.debug("Test: " + testString);
            } catch (Exception ex) {
                DBG.error(ex);
            }
        }
        HashMap map = new HashMap();
        if (queryString == null) return map;

        final Pattern tokP = new Pattern("&");
        String[] arr=tokP.tokenizer(queryString).split();
        for (int i =0; i < arr.length; i++)
        {
            String paramValue = arr[i];
            if (DBG.isDebugEnabled()) {
                DBG.debug("ParamValue: " + paramValue);
            }
            int eqIndex = paramValue.indexOf('=');
            String param, value;
            if (eqIndex == -1) {
                param = paramValue;
                value = "";
            } else {
                param = paramValue.substring(0, eqIndex);
                value = paramValue.substring(eqIndex + 1);
            }

            if (DBG.isDebugEnabled()) {
                DBG.debug("Param: " + param);
                DBG.debug("Value: " + value);
            }
            if (map.containsKey(param)) {
                List oldValue = (List)map.get(param);
                oldValue.add(value);
            } else {
                List theList = new ArrayList();
                theList.add(value);
                map.put(param, theList);
            }
        }
        return map;
    }

    private static String fixValue(String value, boolean bDecode)
        throws AspException
    {
        String retValue;
        if (bDecode)
        {
            retValue = urlDecode(value);
        } else {
            retValue = value;
        }
        if (DBG.isDebugEnabled()) DBG.debug("Decoding value: " + retValue);
        try {
            retValue = new String(retValue.getBytes("ISO8859-1"), "UTF-8");
        } catch (Exception ex)
        {
            throw new AspNestedException(ex);
        }
        if (DBG.isDebugEnabled()) DBG.debug("Value: " + retValue);
        return retValue;
    }

    private static void testFixValue(String value)
    {
        try {
            String testString = new String(
                value.getBytes("ISO8859-1"), "UTF-8");
            DBG.debug("Value: " + value);
            DBG.debug("Value(UTF-8): " + testString);
        } catch (Exception ex) {
            DBG.error(ex);
        }
    }

    public static void convertToMultiValue(AspCollection contents,
        Map paramValues, boolean bDecodeValues) throws AspException
    {
        Iterator e = paramValues.keySet().iterator();
        while (e.hasNext())
        {
            String key = (String)e.next();
            Object valuesObj = paramValues.get(key);
            String keyDec = fixValue(key, bDecodeValues);

            Object contentsValue = contents.get(keyDec);
            if (contentsValue instanceof UndefinedValueNode)
            {
                MultiValue multiValue = new MultiValue();
                contents.put(keyDec, multiValue);
                contentsValue = multiValue;
            }
            if (valuesObj instanceof List)
            {
                List values = (List)valuesObj;
                for (int i = 0; i < values.size(); i++)
                {
                    String value = (String)values.get(i);
                    String valDec = fixValue(value, bDecodeValues);

                    ((MultiValue)contentsValue).add(valDec);
                }
            } else if (valuesObj instanceof String[])
            {
                String values[] = (String[])valuesObj;
                for (int i = 0; i < values.length; i++)
                {
                    String valDec = fixValue(values[i], bDecodeValues);
                
                    ((MultiValue)contentsValue).add(valDec);
                }
            } else {
                String valDec = fixValue(valuesObj.toString(), bDecodeValues);
        
                ((MultiValue)contentsValue).add(valDec);
            }
        }
    }

    public static boolean isHex(char ch)
    {
        if ((ch >= '0') && (ch <= '9')) return true;
        if ((ch >= 'a') && (ch <= 'f')) return true;
        if ((ch >= 'A') && (ch <= 'F')) return true;
        return false;
    }

    public static String urlDecode(String paramsEncoded)
    {
        StringBuffer str = new StringBuffer();
        int i = 0;

        while (i < paramsEncoded.length())
        {
            if (paramsEncoded.charAt(i)=='%') {
                if (paramsEncoded.length()<(i+3)) {
                    str.append(paramsEncoded.charAt(i));
                } else {
                    String hexStr = paramsEncoded.substring(i+1, i+3);
                    if (isHex(hexStr.charAt(0)) && isHex(hexStr.charAt(1)))
                    {
                        char chVal = (char)Integer.parseInt(hexStr, 16);
                        str.append(chVal);
                        i+=2;
                    } else {
                        str.append(paramsEncoded.charAt(i));
                    }
                }
            } else if (paramsEncoded.charAt(i)=='+') {
                str.append(' ');
            } else {
                str.append(paramsEncoded.charAt(i));
            }
            i++;
        }
        return str.toString();
    }


    /**
     * MultiValue is a class which can store multiple values for
     * a single object.
     */
    public static class MultiValue implements SimpleReference, SimpleMap
    {
        /** List of values */
        Vector values;

        /** String representation of the whole list */
        String  wholeValue;

        /** count of items in the array of values, publically
            accessible to the ASP code. */
        public int count;

        /**
         * Constructor, initially created with no values.
         */
        public MultiValue()
        {
            values = new Vector();
            wholeValue = "";
            count = 0;
        }

        /**
         * Adds a value to this multiple value list.
         * @param value New value to add.
         */
        void add(String value)
        {
            values.add(value);
            if (!wholeValue.equals("")) wholeValue += ", ";
            wholeValue+=value;
            count++;
        }

        /**
         * Obtains the list of keys for this multi value.
         * This function is used in the For Each ... statement.
         * @return Enumeration of values in this multi value.
         * @see SimpleMap#getKeys
         */
        public Enumeration getKeys()
        {
            return values.elements();
        }

        /**
         * SimpleMap read function to obtain a multi value at
         * the specified index. <b>TODO</b> 1-based or 0-based?
         * @param value key of multi value to obtain, will
         * be converted to integer.
         * @return value at the specified index.
         * @throws AspException on error, mosting array index out of bounds.
         * @see SimpleMap#get
         */
        public Object get(Object value) throws AspException
        {
            int iVal = Types.coerceToInteger(value).intValue();
            if (iVal < 1 || iVal > count) {
                throw new AspException("Array index out of bound: " + iVal);
            }
            return values.get(iVal - 1);
        }

        /**
         * SimpleMap write function to store a value at a specified
         * index. This function always throws AspReadOnlyException
         * since Request.QueryString(i) is read-only.
         * @param key Key of object to store.
          * @param value Value of object to store.
         * @throws AspException always throws AspReadOnlyException
         * @see SimpleMap#put
         */
        public void put(Object key, Object value) throws AspException
        {
            throw new AspReadOnlyException("Request.QueryString");
        }

        /**
         * SimpleReference method to obtain the first value of this
         * multi value list.
         * @return first value of this multi value list.
         * @throws AspException on error
         * @see SimpleReference#getValue
         */
        public Object getValue() throws AspException
        {
            if (values.size()==0)
                return Constants.undefinedValueNode;
            return wholeValue;
        }

        /**
         * SimpleReference method to set the value of this multi value 
         * list. Always throws AspReadOnlyException 
         * @param obj New value
         * @throws AspException always throws AspReadOnlyException
         */
        public void setValue(Object obj) throws AspException
        {
            throw new AspReadOnlyException("Request.QueryString");
        }

        /**
         * Converts this multi value class to string, for debugging.
         * @return string value of this query string value class
         */
        public String toString()
        {
            return "{MultiValue(" + values + ")}";
        }
    }
}

