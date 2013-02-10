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
 * Implements the definition of an ident.
 * 
 * @author Terence Haddock
 * @version 0.9
 */
public class DefineIdentNode extends DefaultNode
{
    /** Name of ident to define */
    IdentNode ident;

    /**
     * Constructor.
     *
     * @param ident Name of the identifier to define
     */
    public DefineIdentNode(IdentNode ident)
    {
        this.ident = ident;
    }

    /**
     * Get the ident this node is defining.
     * @param ident ident this node is defining
     */
    public IdentNode getIdent()
    {
        return ident;
    }

    /**
     * Dumps this expression.
     * 
     * @see Node#dump()
     * @throws AspException if an error occurs.
     */
    public void dump() throws AspException
    {
        System.out.print("DIM ");
        ident.dump();
        System.out.println();
    }

    /**
     * Prepares this expression, this involves setting the scope of
     * the dimension statement.
     *
     * @param scope AspContext under which to prepare this expression.
     * @see Node#prepare(AspContext)
     * @throws AspException if an error occurs
     */
    public void prepare(AspContext scope) throws AspException
    {
        /* Check if this variable is already in scope */
        if (scope.inDirectScope(ident)) {
            throw new AspRedefineIdentException(ident.toString());
        }
        scope.forceScope(ident);
    }
    

    /**
     * Executes this expression.
     * 
     * @param scope AspContext under which to evaluate this expression.
     * @return always null
     * @see Node#execute(AspContext)
     * @throws AspException if an error occurs.
     */
    public Object execute(AspContext scope) throws AspException
    {
        return null;
    }
};

