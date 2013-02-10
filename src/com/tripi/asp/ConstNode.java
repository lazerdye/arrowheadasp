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
 * ConstNode handles the "CONST ident = expr" expression in ASP.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class ConstNode extends DefaultNode
{
    /** Identifier of constant expression */
    IdentNode ident;

    /** Expression the constant should be defined as. */
    Node expr;

    /** Is this constant expression private? */
    boolean priv;

    /**
     * Constructor.
     * @param ident identifier for constant value.
     * @param expr Expression constant identifier should have value defined.
     * @param priv Is this constant expression private?
     */
    public ConstNode(IdentNode ident, Node expr, boolean priv)
    {
        this.ident = ident;
        this.expr = expr;
        this.priv = priv;
    }

    /**
     * Dumps the string representation of this node.
     * @throws AspException on error
     * @see DefaultNode#dump
     */
    public void dump() throws AspException
    {
        if (priv) System.out.print("PRIVATE ");
        System.out.print("CONST ");
        ident.dump();
        System.out.print(" = ");
        expr.dump();
        System.out.println();
    }

    /**
     * Executes this node. Causes the definition of the constant, 
     * wrapped in a ConstValue class to allow read-only access.
     * @param context The current context
     * @return result of executing this node, for this node the result is NULL
     * @throws AspException on error
     * @see Node#execute
     */
    public Object execute(AspContext context) throws AspException
    {
        if (context.inScope(ident))
            throw new AspException("Variable/Const redefined");
        Object res = expr.execute(context);
        context.forceScope(ident);
        context.setValue(ident, new ConstValue(res));
        return null;
    }
};

