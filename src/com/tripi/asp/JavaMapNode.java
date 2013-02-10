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

import java.util.Enumeration;
import org.apache.log4j.Logger;

/**
 * JavaMapNode handles Java objects which implement the SimpleMap 
 * interface.
 * @author Terence Haddock
 * @version 0.9
 */
public class JavaMapNode extends JavaObjectNode implements SimpleMap
{
    /** Debugging category */
    private static final transient Logger DBG = Logger.getLogger(JavaMapNode.class);

    /** The object this JavaObjectNode points to, dereferenced as a 
     * SimpleMap object */
    SimpleMap mapObj;

    /**
     * Constructor.
     * @param obj Sub-object this node is referencing
     */
    public JavaMapNode(SimpleMap obj)
    {
        super(obj);
        this.mapObj = obj;
    }

    /**
     * Overrides the get() operation for an object.
     * @param key Key key of object to objtain
     * @return Value of the key reference
     * @see SimpleMap#get(Object)
     * @throws AspException when an exception occurs
     */
    public Object get(Object key) throws AspException
    {
        return Types.coerceToNode(mapObj.get(key));
    }

    /**
     * Overrides the put() operation for an object.
     * @param key Key of the object to store.
     * @param value Value of the object to store
     * @see SimpleMap#put(Object, Object)
     * @throws AspException if an exception occurs
     */
    public void put(Object key, Object value) throws AspException
    {
        mapObj.put(key, value);
    }

    /**
     * Overrides the getKeys() operation for an object.
     * @return enumeration of the keys contained within this object.
     * @throws AspException if an error occurs.
     */
    public Enumeration getKeys() throws AspException
    {
        return mapObj.getKeys();
    }
}
