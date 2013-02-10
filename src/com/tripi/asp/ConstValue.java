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
 * ConstValue handles constant values, preventing changing the value.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class ConstValue implements SimpleReference
{
    /** Value this constant contains */
    Object value;

    /**
     * Constructor.
     * @param value Value this constant contains.
     */
    public ConstValue(Object value)
    {
        this.value = value;
    }

    /**
     * Sets the value of this variable, throws an error for these 
     * constant values.
     * @param value New value for this node.
     * @throws AspReadOnlyException when called.
     * @see SimpleReference#setValue
     */
    public void setValue(Object value) throws AspException
    {
        throw new AspReadOnlyException("Constant expression");
    }

    /**
     * Obtains the value of this variable
     * @return Value of this expression.
     * @see SimpleReference#getValue
     */
    public Object getValue() throws AspException
    {
        return value;    
    }
};

