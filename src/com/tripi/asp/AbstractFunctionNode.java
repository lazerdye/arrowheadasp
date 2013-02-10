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

/**
 * AbstractFunctionNode contains basic code used by most Function
 * nodes including converting a VarListNode node to a Vector of
 * de-referenced values.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public abstract class AbstractFunctionNode extends DefaultNode
    implements FunctionNode
{
    /**
     * Overloaded class to handle executing functions, will convert
     * the variable list passed in to a Vector of elements.
     * @param varList parameter list
     * @param context Current context.
     * @return result of the function call
     * @throws AspException if any error occurs.
     * @see FunctionNode#execute(VarListNode,AspContext)
     * @see AbstractFunctionNode#execute(Vector,AspContext)
     */
    public Object execute(VarListNode varList, AspContext context)
        throws AspException
    {
        Vector values = (Vector)varList.execute(context);
        for (int i = 0; i < values.size(); i++)
        {
            Object obj = values.get(i);
            while (obj instanceof SimpleReference) {
                obj = ((SimpleReference)obj).getValue();
            }
            values.set(i, obj);
        }
        return execute(values, context);
    }

    /**
     * Abstract class to handle executing a function.
     * @param values De-referenced values
     * @param context Current context.
     * @return result of the function call
     * @throws AspException if any error occurs.
     * @see FunctionNode#execute(VarListNode,AspContext)
     * @see AbstractFunctionNode#execute(Vector,AspContext)
     */
    abstract public Object execute(Vector values, AspContext context)
        throws AspException;
}
