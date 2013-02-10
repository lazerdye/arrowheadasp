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

import java.util.Date;

import jregex.Pattern;
import jregex.Replacer;

import org.apache.log4j.Logger;

/**
 * The Types class contains utilities for conversion between data types.
 * 
 * @author Terence Haddock
 * @version 0.9
 */
public class Types
{
    /** Debugging instance */
    static private final Logger DBG = Logger.getLogger(Types.class);

    /**
     * Coerces a data type into an Integer type.
     * <ul>
     * <li><i>null</i> = 0
     * <li><i>undefined</i> = 0
     * <li><i>Integer</i> = self
     * <li><i>Double</i> = Cast to integer
     * <li><i>String</i> = Parsed to integer, errors are passed through
     * <li>Everything else causes a AspCastException
     * </ul>
     *
     * @param obj Object to cast
     * @return Integer representation of the cast.
     * @throws AspException if an error occurs.
     */
    static public Integer coerceToInteger(Object obj) throws AspException
    {
        if (obj instanceof SimpleReference)
        {
            return coerceToInteger(((SimpleReference)obj).getValue());
        } else
        if (obj == null || obj instanceof NullNode ||
            obj instanceof UndefinedValueNode)
        {
            return new Integer(0);
        } else if (obj instanceof Integer)
        {
            return (Integer)obj;
        } else if (obj instanceof Double)
        {
            return new Integer((int)((Double)obj).doubleValue());
        } else if (obj instanceof Long)
        {
            return new Integer((int)((Long)obj).doubleValue());
        } else if (obj instanceof Short)
        {
            return new Integer((int)((Short)obj).doubleValue());
        } else if (obj instanceof Boolean)
        {
            boolean boolValue = ((Boolean)obj).booleanValue();
            if (boolValue) return new Integer(-1);
            return new Integer(0);
        } else if (obj instanceof String)
        {
            String strValue = ((String)obj).trim();
            if (strValue.equals("")) return new Integer(0);
            try {
                return new Integer(strValue);
            } catch (NumberFormatException ex) {
                throw new AspCastException(ex.toString());
            }
        } else {
            throw new AspCastException(obj.getClass().toString());
        }
    }
    
	/**
	 * Coerces a data type into a Long type.
	 * <ul>
	 * <li><i>null</i> = 0
	 * <li><i>undefined</i> = 0
	 * <li><i>Integer</i> = self
	 * <li><i>Long</i> = self
	 * <li><i>Double</i> = Cast to long
	 * <li><i>String</i> = Parsed to long, errors are passed through
	 * <li>Everything else causes a AspCastException
	 * </ul>
	 *
	 * @param obj Object to cast
	 * @return Integer representation of the cast.
	 * @throws AspException if an error occurs.
	 */
	static public Long coerceToLong(Object obj) throws AspException
	{
		if (obj instanceof SimpleReference)
		{
			return coerceToLong(((SimpleReference)obj).getValue());
		} else
		if (obj == null || obj instanceof NullNode ||
			obj instanceof UndefinedValueNode)
		{
			return new Long(0);
		} else if (obj instanceof Long)
		{
			return (Long)obj;
		} else if (obj instanceof Double)
		{
			return new Long((long)((Double)obj).longValue());
		} else if (obj instanceof Integer)
		{
			return new Long((long)((Integer)obj).longValue());
		} else if (obj instanceof Short)
		{
			return new Long((long)((Short)obj).longValue());
		} else if (obj instanceof Boolean)
		{
			boolean boolValue = ((Boolean)obj).booleanValue();
			if (boolValue) return new Long(-1);
			return new Long(0);
		} else if (obj instanceof String)
		{
			String strValue = ((String)obj).trim();
			if (strValue.equals("")) return new Long(0);
			try {
				return new Long(strValue);
			} catch (NumberFormatException ex) {
				throw new AspCastException(ex.toString());
			}
		} else {
			throw new AspCastException(obj.getClass().toString());
		}
	}

    /**
     * Coerces an object to a string.
     * <ul>
     * <li><i>null</i> = ""
     * <li><i>undefined</i> = ""
     * <li><i>String</i> = self
     * <li><i>Integer</i> = cast to string
     * <li><i>Boolean</i> = cast to "true"/"false"
     * </ul>
     *
     * @param obj Object to cast to string.
     * @return Object casted to string
     * @throws AspException is a casting error occurs.
     */
    static public String coerceToString(Object obj) throws AspException
    {
        if (obj instanceof SimpleReference)
        {
            return coerceToString(((SimpleReference)obj).getValue());
        } else
        if (obj == null || obj instanceof NullNode ||
            obj instanceof UndefinedValueNode)
        {
            return "";
        } else if (obj instanceof String)
        {
            return (String)obj;
        } else if (obj instanceof Integer)
        {
            return obj.toString();
        } else if (obj instanceof Boolean)
        {
            Boolean b = (Boolean)obj;
            if (b.booleanValue()) return "True";
                return "False";
        } else if (obj instanceof Double)
        {
            /* If a double value contains an integer number, do not print
               decimal places. */
            Double doub = (Double)obj;
            if (DBG.isDebugEnabled())
            {
                DBG.debug("int value: " + doub.intValue());
                DBG.debug("doub value: " + doub.doubleValue());
            }
            if (doub.intValue() == doub.doubleValue())
                return "" + doub.intValue();
            return doub.toString();
        } else if (obj instanceof AspDate)
        {
            AspDate date = (AspDate)obj;
            return date.toString();
        } else if (obj instanceof JavaObjectNode)
        {
            return coerceToString(((JavaObjectNode)obj).
                    getSubObject());
        } else {
            //throw new AspCastException(obj.getClass().toString());
            return obj.toString();
        }
    }

    /**
     * Converts a data object to a boolean type.
     * <ul>
     * <li><i>null</i> = false
     * <li><i>undefined</i> = false
     * <li><i>Boolean</i> = self
     * </ul>
     *
     * @param obj Object to cast.
     * @return boolean value of object.
     * @throws AspException if exception occurs.
     */
    static Boolean coerceToBoolean(Object obj) throws AspException
    {
        if (DBG.isDebugEnabled()) {
            DBG.debug("coerceToBoolean: " + obj);
        }
        if (obj instanceof SimpleReference)
        {
            return coerceToBoolean(((SimpleReference)obj).getValue());
        } else
        if (obj == null || obj instanceof NullNode ||
                obj instanceof UndefinedValueNode) {
            return new Boolean(false);
        } else if (obj instanceof Boolean)
        {
            return (Boolean)obj;
        } else if (obj instanceof String)
        {
            String str = (String)obj;
            if (str.equalsIgnoreCase("true"))
                return new Boolean(true);
            else if (str.equalsIgnoreCase("false"))
                return new Boolean(false);
            else 
                return new Boolean(coerceToInteger(obj).intValue() != 0);
        } else {
            return new Boolean(coerceToInteger(obj).intValue() != 0);
        }
    }

    /**
     * Casts an object to a double value.
     * <ul>
     * <li><i>null</i> - 0.0
     * <li><i>undefined</i> - 0.0
     * <li><i>Double</i> - self
     * <li><i>Integer</i> - Casted to double
     * <li><i>String</i> - Parsed to double
     * </ul>
     *
     * @param obj Object to cast to double.
     * @return obj as a double value.
     * @throws AspException if an error occurs.
     */
    static Double coerceToDouble(Object obj) throws AspException
    {
        if (obj instanceof SimpleReference)
        {
            return coerceToDouble(((SimpleReference)obj).getValue());
        } else
        if (obj == null || obj instanceof NullNode)
        {
            throw new AspCastException("NULL");
        } else if (obj instanceof UndefinedValueNode)
        {
            return new Double(0);
        } else
        if (obj instanceof Double)
        {
            return (Double)obj;
        } else if (obj instanceof Integer)
        {
            return new Double((double)((Integer)obj).intValue());
        } else if (obj instanceof Long)
        {
            return new Double((double)((Long)obj).intValue());
        } else if (obj instanceof Short)
        {
            return new Double((double)((Short)obj).intValue());
        } else if (obj instanceof String)
        {
            try {
                String str = (String)obj;
                if (str.indexOf(",") != -1)
                {
                    /* Have to ignore commas */
                    final Pattern p = new Pattern(",");
                    /* XXX It would be nice to make Replacer final, but there is no
                      mention of the thread safely of replacer */
                    Replacer r = p.replacer("");   
                    str = r.replace((String)obj);
                }
                return new Double(str);
            } catch (NumberFormatException ex) {
                throw new AspCastException(ex.toString());
            }
        } else if (obj instanceof Boolean) {
            Boolean b = (Boolean)obj;
            if (b.booleanValue()) return new Double(-1);
                return new Double(0);
        } else {
            if (DBG.isDebugEnabled()) {
                DBG.debug("Cast Exception for object: " + obj);
            }
            throw new AspCastException(obj.getClass().toString());
        }
    }

    /**
     * Coerces a data value to a Date value.
     * <ul>
     * <li><i>Date</i> - Self
     * </ul>
     *
     * @param obj Object to convert to date.
     * @return obj casted to a date value
     * @throws AspException if the type cannot be casted.
     */
    static AspDate coerceToDate(Object obj) throws AspException
    {
        if (obj instanceof SimpleReference)
        {
            return coerceToDate(((SimpleReference)obj).getValue());
        } else if (obj instanceof AspDate)
        {
            return (AspDate)obj;
        } else if (obj instanceof Date)
        {
            return new AspDate((Date)obj);
        } else {
            String objStr = coerceToString(obj).trim();
            return new AspDate(objStr);
        }
    }

    /**
     * Coerces an object to a "node".
     * All of the known base types (Integer, Boolean, Double, String)
     * are left as-is, but other Java objects are wrapped into    
     * a JavaObjectNode-type class to handle ASP-&gt;Java calls.
     * <ul>
     * <li><i>null</i> - null
     * <li><i>Node</i> - self
     * <li><i>Double</i> - self
     * <li><i>Integer</i> - self
     * <li><i>Boolean</i> - self
     * <li><i>Byte</i> - self
     * <li><i>String</i> - self
     * <li><i>SimpleMap + SimpleReference</i> - Wrapped in a JavaReferenceMapNode
     * <li><i>SimpleMap</i> - Wrapped in a JavaMapNode
     * <li><i>SimpleReference</i> - Wrapped in a JavaReferenceNode
     * <li>Other - Wrapped in a JavaObjectNode
     * </ul>
     *
     * @param obj Object to map
     * @return Node object, or base type.
     * @throws AspException if a casting error occurs
     */
    static Object coerceToNode(Object obj) throws AspException
    {
        if (DBG.isDebugEnabled())
            DBG.debug("coerceToNode: " + obj);
        if (obj == null) {
            if (DBG.isDebugEnabled())
                DBG.debug("=null");
            return Constants.undefinedValueNode;
        } else if (obj instanceof Node) {
            if (DBG.isDebugEnabled())
                DBG.debug("=Node");
            return obj;
        } else if (obj instanceof Double) {
            if (DBG.isDebugEnabled())
                DBG.debug("=Double");
            return obj;
        } else if (obj instanceof Integer) {
            if (DBG.isDebugEnabled())
                DBG.debug("=Integer");
            return obj;
        } else if (obj instanceof Long) {
            if (DBG.isDebugEnabled())
                DBG.debug("=Long");
            return obj;
        } else if (obj instanceof Boolean) {
            if (DBG.isDebugEnabled())
                DBG.debug("=Boolean");
            return obj;
        } else if (obj instanceof Short) {
            if (DBG.isDebugEnabled())
                DBG.debug("=Short");
            return obj;
        } else if (obj instanceof Byte) {
            return new Integer(((Byte)obj).byteValue());
        } else if (obj instanceof String) {
            if (DBG.isDebugEnabled())
                DBG.debug("=String");
            return obj;
        // XXX Problem with objects like java.sql.Timestamp
        //} else if (obj instanceof java.util.Date) {
        //      return new AspDate((java.util.Date)obj);
        } else if (obj instanceof SimpleMap) {
            if (obj instanceof SimpleReference) {
                if (DBG.isDebugEnabled())
                    DBG.debug("=ObjectReferenceMap(" +
                        obj.toString() + ")");
                return new JavaReferenceMapNode(obj);
            }
            if (DBG.isDebugEnabled())
                DBG.debug("=ObjectMap(" + obj.toString() + ")");
            return new JavaMapNode((SimpleMap)obj);
        } else if (obj instanceof SimpleReference) {
            if (DBG.isDebugEnabled())
                DBG.debug("=ObjectReference(" + obj.toString() + ")");
            return new JavaReferenceNode((SimpleReference)obj);
        } else {
            if (DBG.isDebugEnabled())
                DBG.debug("=Object(" + obj.toString() + ")");
            /* Test for character array */
            if (obj.getClass().isArray())
            {
                if (obj.getClass().getComponentType() == char.class) {
                    return new PackedCharArrayNode((char[])obj);
                }
                else if (obj.getClass().getComponentType() == byte.class) {
                    return new PackedByteArrayNode((byte[])obj);
                }
            }
            return new JavaObjectNode(obj);
        }
    }

    /**
     * Coerce a type to a SimpleMap object.
     * @param obj Object to coerce.
     * @return SimpleMap object.
     */
    //public static SimpleMap coerceToMap(Object obj) throws AspException
    //{
    //    while (obj instanceof SimpleReference && 
    //        !(obj instanceof SimpleMap))
    //    {
    //        if (DBG.isDebugEnabled()) DBG.debug("Dereferencing: " 
    //                + obj);
    //        obj = ((SimpleReference)obj).getValue();
    //    }
    //    if (obj instanceof SimpleMap) return (SimpleMap)obj;
    //    throw new AspCastException("Map");
    //}

    /**
     * Obtains an integer value from a hex string line &amp;FF.
     * @param str String to convert
     * @return Integer value of string
     */
    static public Integer integerFromHex(String str)
    {
        return new Integer(Integer.parseInt(str.substring(2),16));
    }

    /**
     * Determines if this object is a number.
     * @param obj Object to test
     * @return <b>true</b> if this object is a number, <b>false</b> otherwise.
     */
    static public boolean isNumber(Object obj)
        throws AspException
    {
        Object deref = dereference(obj);
        if (deref instanceof Integer) return true;
        if (deref instanceof Double) return true;
        if (deref instanceof Long) return true;
        return false;
    }

    /**
     * Derefernces the given object. If the object is a SimpleReference
     * object, it will dereference it, and keep dereferencing it until
     * a non-SimpleReference object is found.
     * @param obj Object to dereference.    
     * @return Non-SimpleReference object.
     */
    public static Object dereference(Object obj) throws AspException
    {
        while (obj instanceof SimpleReference) {
            if (DBG.isDebugEnabled())
                DBG.debug("Dereferencing: " + obj);
            obj = ((SimpleReference)obj).getValue();
        }
        return obj;
    }

    /**
     * This function tests an object to see if it is a date object.
     * @param testObject object to test
     * @return <b>true</b> if this is a date object, <b>false</b> otherwise.
     */
    public static boolean isDate(Object testObject)
    {
        return testObject instanceof AspDate;
    }
}
