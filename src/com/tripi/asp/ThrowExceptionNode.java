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
 * The ThrowExceptionNode handles nodes which automatically throw exceptions.
 * This includes Exit nodes.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class ThrowExceptionNode extends DefaultNode
{
    /** Exception to throw when this block is executed. */
    AspException exception;

    /**
     * Constructor, gives exception to throw.
     * @param exception Exception to throw when this block is executed.
     */
    public ThrowExceptionNode(AspException exception)
    {
        this.exception = exception;
    }

    /**
     * Dumps this VBScript element.
     * @see Node#dump()
     */
    public void dump() throws AspException
    {
        if (exception instanceof AspExitForException)
        {
            System.out.println("EXIT FOR");
        } else if (exception instanceof AspExitFunctionException)
        {
            System.out.println("EXIT FUNCTION");
        } else if (exception instanceof AspExitSubException)
        {
            System.out.println("EXIT SUB");
        } else if (exception instanceof AspExitDoException)
        {
            System.out.println("EXIT DO");
        } else super.dump();
    }

    /**
     * Executes this node, throwing the exception we want to throw.
     *
     * @param context AspContext under which to execute this node.
     * @return this function never returns
     * @see Node#execute(AspContext)
     */
    public Object execute(AspContext context) throws AspException
    {
        throw exception;
    }
};

