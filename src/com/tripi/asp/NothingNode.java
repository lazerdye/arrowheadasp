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
 * This class contains a reference to a 'nothing' object, an object which
 * has no fields, no methods, no nothing.
 *
 * @author Terence Haddock
 */
public class NothingNode extends DefaultNode implements ObjectNode
{

    /**
     * Package-level constructor.
     */
    NothingNode() {};

    /**
     * Object method, objtains a field.
     * @param ident Identifier of the field to obtain.
     * @return Object which represents the field.
     * @throws AspException if an error occurs
     * @see ObjectNode#getField(IdentNode)
     */
    public Object getField(IdentNode ident) throws AspException
    {
        throw new AspException("Unknown field: " + ident);
    }

}
