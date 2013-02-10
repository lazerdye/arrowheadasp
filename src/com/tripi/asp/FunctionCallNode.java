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
 * This node handles function calls within a vbscript node.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class FunctionCallNode implements Node
{
    /** Expression of this function */
    Node        ident;
    /** Parameters to this function */
    VarListNode    expr;

    /**
     * Constructor.
     *
     * @param ident Identifier of this function
     * @param expr Parameters to this function
     */
    public FunctionCallNode(Object ident, Object expr)
    {
        this.ident = (Node)ident;
        this.expr = (VarListNode)expr;
    }

    /**
     * Dumps the reprensentation of this node.
     * @throws AspException if an error occurs.
     * @see Node#dump
     */
    public void dump() throws AspException
    {
        ident.dump();
        System.out.print("(");
        expr.dump();
        System.out.print(")");
    }

    /**
     * Obtains the expression of this function call node.
     * @return expression
     */
    public VarListNode getExpression()
    {
        return expr;
    }

    /**
     * Prepares this function for evaluation.
     *
     * @param context AspContext under which to evaluate this expression.
     * @throws AspException if an error occurs.
     */
    public void prepare(AspContext context) throws AspException
    {
        /* Nothing to do, the arguments to function calls
          * should not need prepared.
         */
    }

    /**
     * Executes this expression.
     *
     * @param context AspContext under which to evaluate this expression.
     * @return the value of this function call.
     * @throws AspException if an error occurs.
     */
    public Object execute(AspContext context) throws AspException
    {
        SubDefinitionNode sub = (SubDefinitionNode)ident.execute(context);
        return sub.execute(expr, context);
    }
};

