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

import org.apache.log4j.Category;

/**
 * SetValueNode implements setting of variables, the following construct:
 * <pre>
 * Set A = "Value"
 * </pre>
 * And:
 * <pre>
 * A = "Value"
 * </pre>
 *
 * @author Terence Haddock
 */
public class SetValueNode implements Node
{
    /** Debugging category */
    private static final transient Category    DBG = Category.getInstance(SetValueNode.class);

    /** The identifier we wish to set */
    Node    ident;

    /** The identifier's new expression when executed */
    Node    expr;

    /** Was the "SET" keyword used? */
    boolean setUsed;

    /**
     * Constructor.
     * @param ident Identifier we wish to set, can be an IdentNode,
     * or a compound expression which evaluates to a SimpleReference or
     * FunctionNode.
     * @param expr Target expression for the identifier.
     * @param setUsed was the SET keyword used?
     */
    public SetValueNode(Node ident, Node expr, boolean setUsed)
    {
        this.ident = ident;
        this.expr = expr;
        this.setUsed = setUsed;
    }

    /**
     * Gets the identifier which is being set.
     * @return identifier being set
     */
    public Node getIdent()
    {
        return ident;
    }

    /**
     * Gets the expression.
     * @return expression
     */
    public Node getExpression()
    {
        return expr;
    }

    /**
     * Dumps the debugging information about this node.
     * @throws AspException on error    
     */
    public void dump() throws AspException
    {
        System.out.print("{S}");
        if (setUsed) System.out.print("SET ");
        ident.dump();
        System.out.print("=");
        expr.dump();
    }

    /**
     * Prepare this node for execution. This does nothing for a SetValueNode.
     * @param context AspContext for this node.
     * @throws AspException on error
     */
    public void prepare(AspContext context) throws AspException
    {
        /* Nothing to do, the arguments to SetValueNodes should not
         * need prepared.
         */
    }

    /**
     * Executes this node, setting the value of the node.
     * @param context Context for the node.
     * @return Always returns null.
     * @throws AspException on error
     */
    public Object execute(AspContext context) throws AspException
    {
        /* Executes the expression, obtaning the dereferenced value */
        Object value = Types.dereference(expr.execute(context));
        if (DBG.isDebugEnabled()) {
            DBG.debug("Execute " + ident + " = " + value);
        }
        /* Check if the expression is valid for this expression, depending on
           if the SET keyword was used */
        if (setUsed && !(value instanceof ObjectNode))
        {
            DBG.debug("SET was used where not allowed");
            /* XXX throw new AspInvalidArgumentsException("SET"); */
        } else if (!setUsed && (value instanceof ObjectNode))
        {
            DBG.debug("SET was not used where required");
            /* XXX throw new AspInvalidArgumentsException("SET"); */
        }
        /* Test for IdentNode */
        if (ident instanceof IdentNode) {
            Object identVal = context.getValue((IdentNode)ident);
            if (identVal instanceof ConstValue)
                throw new AspReadOnlyException("Constant value");
            context.setValue((IdentNode)ident, value);
        } else {
            /* If it's not an IdentNode, we execute the node and see what
             * the result is. */
            Object identVal = ident.execute(context);
            if (identVal instanceof SimpleReference) {
                ((SimpleReference)identVal).setValue(value);
            } else if (identVal instanceof FunctionNode) {
                VarListNode vl = new VarListNode();
                vl.append(value);
                ((FunctionNode)identVal).execute(vl, context);
            } else {
                throw new AspException("Unknown object to set: " +
                            identVal.getClass().getName());
            }
        }
        return null;
    }
};

