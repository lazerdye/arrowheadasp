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
 * This class is a container which stores a single object and the
 * DebugContext in which this object was created.
 *
 * @author Terence Haddock
 * @version 0.9
 * @see DebugContext
 */
public class ContextObject
{
    /**
     * The object this container holds.
     */
    public Object object;

    /**
     * The line number this object was created.
     */
    public int lineno;

    /**
     * Constructor.
     *
     * @param object The object to hold.
     * @param context The line number of this object.
     */
    public ContextObject(Object object, int lineno)
    {
        this.object = object;
        this.lineno = lineno;
    }

    /**
     * Output this context container as a string. For debugging.
     */
    public String toString()
    {
        return "[" + object + "] at line " + lineno;
    }
}

