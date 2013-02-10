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
 * The ByRefNode holds an ident which is a variable passed by reference to
 * a function or procedure.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class ByRefNode extends DefaultNode
{
    /** The identifier this byrefnode corresponds to. */
    IdentNode ident;

    /**
     * Constructor.
     * @param ident Identifier this ByRefNode corresponds to
     */
    public ByRefNode(IdentNode ident)
    {
        this.ident = ident;
    }

    /**
     * Dumps the visual contents of this ByRefNode
     */
    public void dump()
    {
        ident.dump();
    }

    /**
     * Get the identifier this ByRefNode points to.
     * @return IdentNode this ByRefNode points to
     */
    public IdentNode getIdent()
    {
        return ident;
    }
}

