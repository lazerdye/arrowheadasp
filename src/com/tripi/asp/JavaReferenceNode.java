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

import java.util.Vector;
import java.util.Map;
import java.lang.reflect.*;
import org.apache.log4j.Category;

/**
 * JavaReferenceNode is a sub-class of the JavaObjectNode class
 * which handles Java objects which implement the SimpleReference interface.
 * @author Terence Haddock
 * @version 0.9
 */
public class JavaReferenceNode extends JavaObjectNode implements SimpleReference
{
    /** Debugging category */
    private static final transient Category DBG = Category.getInstance(JavaReferenceNode.class);

    /** SimpleReference cast of the object contained in this JavaObjectNode */
    SimpleReference obj;

    /** Constructor.
     * @param obj SimpleReference object
     */
    public JavaReferenceNode(SimpleReference obj)
    {
        super(obj);
        this.obj = obj;
    }

    /**
     * Sets the value of this object.
     * @param value New value of this object
     * @throws AspException if an error occurs
     * @see SimpleReference#setValue
     */
    public void setValue(Object value) throws AspException
    {
        obj.setValue(value);
    }

    /**
     * Obtains the value of this object.
     * @return value of this object
     * @throws AspException if an error occurs
     * @see SimpleReference#getValue
     */
    public Object getValue() throws AspException
    {
        return obj.getValue();
    }
}
