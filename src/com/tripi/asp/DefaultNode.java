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
 * This class contains the default implementation of the Node
 * interface. The default implementation is to throw the
 * AspNotImplemented exception for every method.
 * 
 * @author Terence Haddock
 * @version 0.9
 */
public class DefaultNode implements Node
{
    /**
     * Dumps the node representation in a textual format. Default
     * implementation is to throw an AspNotImplemented exception
     *
     * @throws AspNotImplementedException if not overriden
     * @see Node#dump()
     */
    public void dump() throws AspException
    {
        throw new AspNotImplementedException("dump");
    }

    /**
     * Prepares this node for execution. Default implementation is to
     * do nothing.
     *
     * @param context Global context
     * @throws AspException if an error occurs
     * @see Node#prepare(AspContext)
     */
    public void prepare(AspContext context) throws AspException
    {
        /* Default is to do nothing. */
    }

    /**
     * Executes this node, returning the result.
     * Default implementation is to return itself.
     *
     * @param context Current context
     * @return Result of this node's execution.
     * @throws AspNotException if any error occurs
     * @see Node#execute(AspContext)
     */
    public Object execute(AspContext context) throws AspException
    {
        return this;
    }
}
