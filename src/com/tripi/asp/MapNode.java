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
 * MapNode is an interface to a multi-dimensional array.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public interface MapNode extends Node
{
    /**
     * Obtains the value of this map node at the specified index.
     * To handle multi-dimensional arrays, the index is given as a list
     * of parameters.
     * @param varlist multi-dimension index of value to obtain from this map.
     * @param context Context under which this map is evaluated.
     * @return value of the map at the specified index(es)
     * @throws AspException if an error occurs
     */
    public Object getIndex(VarListNode varlist, AspContext context)
        throws AspException;

    /**
     * Obtains the upper bound of this map.
     * @param dimension Dimension of which to obtain upper bound of
     * @return upper bound of this map
     * @throws AspException if an error occurs
     */
    public int getUBOUND(int dimension) throws AspException;

    /**
     * Obtains the lower bound of this map.
     * @param dimension Dimension of which to obtain lower bound of
     * @return lower bound of this map
     * @throws AspException if an error occurs
     */
    public int getLBOUND(int dimension) throws AspException;
}

