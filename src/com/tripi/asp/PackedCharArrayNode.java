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

import org.apache.log4j.Category;

/**
 * Implements a special version of an ArrayNode which will contain a
 * read-only array of characters.
 *
 * @author Terence Haddock
 */
public class PackedCharArrayNode extends DefaultNode implements MapNode
{
    /** Debugging object */
    private static final transient Category DBG =
        Category.getInstance(PackedCharArrayNode.class);

    /** Contents of this array */
    char values[];

    /**
     * Constructor.
     * @param values Initial values of this array.
     */
    public PackedCharArrayNode(char values[])
    {
        this.values = values;
    }

    /**
     * MapNode function to obtain the value of this array at an index.
     * @param varlist List of indexes, expecting a single element in the array
     * @param context AspContext of this execution.
     */
    public Object getIndex(VarListNode varlist, AspContext context)
        throws AspException
    {
        Vector vec = (Vector)varlist.execute(context);
        if (vec.size() != 1) {
            throw new AspException("Invalid number of parameters to array index");
        }
        Integer value = Types.coerceToInteger(vec.get(0));
        return new Character(values[value.intValue()]);
    }

    /**
     * Obtains the number of elements in this array
     * @param dimension the dimension, for this object this value should be 1
     * @return the number of elements in this array
     */
    public int getUBOUND(int dimension) throws AspException
    {
        if (dimension != 1) {
            throw new AspSubscriptOutOfRangeException("UBOUND");
        }
        return values.length - 1;
    }

    /**
     * Obtains the lower bound of this array, always zero
     * @param dimension the dimension, for this object this value should be 1
     * @return the lower bound of this array
     */
    public int getLBOUND(int dimension) throws AspException
    {
        if (dimension != 1) {
            throw new AspSubscriptOutOfRangeException("UBOUND");
        }
        return 0;
    }

    /**
     * Internal function to obtain the actual character array.
     * @return character array containing values in this array
     */
    public char[] internGetValues()
    {
        return values;
    }
}

