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

import org.apache.log4j.Logger;

/**
 * This node handles the evaluating of a field expression evaluation.
 * Such as Object.Value.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class GetFieldNode extends DefaultNode
{
    /** Debugging category */
    private static final transient Logger DBG =
        Logger.getLogger(GetFieldNode.class);

    /** Identifier of the field expression */
    IdentNode ident;

    /** Expression which to find the field of */
    Node expr;

    /**
     * Constructor.
     *
     * @param expr Expression of the field.
     * @param ident Identifier of the field to evaluate.
     */
    public GetFieldNode(Node expr, IdentNode ident)
    {
        this.ident = ident;
        this.expr = (Node)expr;
    }

    /**
     * Get the ident.
     * TODO: This should be getIdentiifer()
     * @return field this object is getting.
     */
    public IdentNode getIdent()
    {
        return ident;
    }

    /**
     * Expression this object is getting the field of.
     * @return expression this object is getting the field index of.
     */
    public Node getExpression()
    {
        return expr;
    }

    /**
     * Dump this expression.
     * @see Node#dump
     * @throws AspException if an error occurs.
     */
    public void dump() throws AspException
    {
        System.out.print("{F}");
        expr.dump();
        System.out.print(".");
        ident.dump();
    }

    /**
     * Executes this field expression.
     *
     * @param context AspContext under which to evaluate the expression.
     * @return return value of this expression
     * @throws AspException If an error occurs
     * @see Node#execute(AspContext)
     */
    public Object execute(AspContext context) throws AspException
    {
        if (DBG.isDebugEnabled()) 
            DBG.debug("execute");
        Object value = expr.execute(context);
        while (value instanceof SimpleReference &&
                !(value instanceof ObjectNode))
        {
            if (DBG.isDebugEnabled()) 
                DBG.debug("De-referencing " + value);
            value = ((SimpleReference)value).getValue();
        }
        if (value instanceof ObjectNode)
        {
            ObjectNode obj = (ObjectNode)value;
            if (DBG.isDebugEnabled()) 
                DBG.debug("Get field of " + ident + " from " + obj);
            return Types.coerceToNode(obj.getField(ident));
        } else {
            throw new AspException("Invalid class for field get: " + value.getClass().getName());
        }
    }
};

