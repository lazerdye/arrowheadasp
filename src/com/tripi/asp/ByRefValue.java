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
 * This class contains a reference to an object which is being passed
 * by reference. Useful in ASP to Java calls where the object is passed
 * by reference instead of by value.
 *
 * @author Terence Haddock
 */
public class ByRefValue
{
    /** The sub-object this class contains. */
    Object subObject;

    /**
     * Constructor.
     */
    public ByRefValue()
    {
    };

    /**
     * Constructor, with initial object.
     * @param subObject sub-object this class contains.
     */
    public ByRefValue(Object subObject)
    {
        this.subObject = subObject;
    }

    /**
     * Obtains this object's value.
     */
    public Object getValue()
    {
        return subObject;
    }

    /**
     * Set this object's value.
     */
    public void setValue(Object subObject)
    {
        this.subObject = subObject;
    }
}

