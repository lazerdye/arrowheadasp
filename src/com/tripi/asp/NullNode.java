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
 * NullNode represents the NULL value in Asp code.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class NullNode extends DefaultNode
{
    /**
     * Constructor
     */
    public NullNode()
    {
    }

    /**
     * Dumps the string representation of this node.
     * @see DefaultNode#dump
     */
    public void dump()
    {
        System.out.print("NULL");
    }

    /**
     * This function always returns true if it is being compared to a null
     * value, or if the other value is a NullNode object.
     * @param obj object to compare to
     * @return <b>true</b> if the objects are equals, <b>false</b> otherwise.
     */
    final public boolean equals(Object obj)
    {
        if (obj == null || obj instanceof NullNode)
            return true;
        return false;
    };
     
    /**
     * NullNodes all have the hashcode of zero.
     * @return always zero
     */
    public int hashCode()
    {
        return 0;
    };
};

