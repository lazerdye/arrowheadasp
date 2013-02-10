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
 * This class implements for FOR ident = expr to expr vbscript statement.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class ForNode implements Node
{
    /** For loop identifier */
    IdentNode ident;

    /** From expression */
    Node aExpr;

    /** To expression */
    Node bExpr;

    /** Step expression */
    Node step;

    /** Block to execute for each statement */
    BlockNode block;

    /** Context under which the for and next expression evaluated */
    DebugContext dbgCtx;

    /**
     * Constructor for the for expression. Use this constructor
     * when step = 1.
     *
     * @param ident For loop expression
     * @param aExpr FROM expression
     * @param bExpr TO expression
     * @param block Block to execute
     * @param dbgCtx Debugging context
     */
    public ForNode(IdentNode ident, Node aExpr, Node bExpr, BlockNode block,
            DebugContext dbgCtx)
    {
        this.ident = ident;
        this.aExpr = aExpr;
        this.bExpr = bExpr;
        this.block = block;
        this.step = null;
        this.dbgCtx = dbgCtx;
    }

    /**
     * Constructor for the for expression, with a specified step expression.
     *
     * @param ident Ident for FOR expression.
     * @param aExpr FROM expression
     * @param bExpr TO expression
     * @param block Block to execute
     * @param step Step
     * @param dbgCtx Context for the FROM..TO expression.
     */
    public ForNode(IdentNode ident, Node aExpr, Node bExpr, BlockNode block,
            Node step, DebugContext dbgCtx)
    {
        this.ident = ident;
        this.aExpr = aExpr;
        this.bExpr = bExpr;
        this.block = block;
        this.step = step;
        this.dbgCtx = dbgCtx;
    }

    /**
     * Get the ident we are looping on.
     * @param ident Identifier we are looping on.
     */
    public IdentNode getIdent()
    {
        return ident;
    }

    /**
     * Get the initial expression.
     * @return initial expression.
     */
    public Node getInitialExpression()
    {
        return aExpr;
    }

    /**
     * Get the final expression.
     * @return final expression.
     */
    public Node getFinalExpression()
    {
        return bExpr;
    }

    /**
     * Get the increment step.
     * @return increment step, null for default (1)
     */
    public Node getIncrementStep()
    {
        return step;
    }

    /**
     * Get the block.
     * @return block
     */
    public Node getBlock()
    {
        return block;
    }

    /**
     * Prepare this expression for evaluation.
     *
     * @param context AspContext to evaluate this expression.
     * @see Node#prepare(AspContext)
     * @throws AspException if an error occurs.
     */
    public void prepare(AspContext context) throws AspException
    {
        this.block.prepare(context);
    }

    /**
     * Dumps the visual expression for this expression.
     *
     * @see Node#dump
     * @throws AspException if an error occurs.
     */
    public void dump() throws AspException
    {
        System.out.print("FOR ");
        ident.dump();
        System.out.print(" = ");
        aExpr.dump();
        System.out.print(" TO ");
        bExpr.dump();
        if (step != null) {
            System.out.print(" STEP ");
            step.dump();
        }
        System.out.println();
        block.dump();
        System.out.println("NEXT");
    }

    /**
     * Executes this node.
     *
     * @param context AspContext of this expression.
     * @return value of this expression, null for the FOR loop
     * @see Node#execute(AspContext)
     * @throws AspException if an exception occurs
     */
    public Object execute(AspContext context) throws AspException
    {
        try {
            int startIndex = Types.coerceToInteger(aExpr.execute(context)).intValue();
            int endIndex = Types.coerceToInteger(bExpr.execute(context)).intValue();
            int stepInt;
            if (step != null) {
                stepInt = Types.coerceToInteger(step.execute(context)).intValue();
            } else {
                stepInt = 1;
            }
            int currentIndex = startIndex;
            do {
                context.setValue(ident, new Integer(currentIndex));
                if (stepInt >= 0) {
                    if (currentIndex > endIndex) {
                        break;
                    }
                } else {
                    if (currentIndex < endIndex) {
                        break;
                    }
                }
                try {
                    block.execute(context);
                } catch (AspExitForException ex) {
                    return null;
                }
                currentIndex = Types.coerceToInteger(
                    context.getValue(ident)).intValue();
                currentIndex = currentIndex + stepInt;
            } while (true);
        } catch (AspException ex) {
            if (!ex.hasContext())
                ex.setContext(dbgCtx);
            throw ex;
        }
        return null;
    }
};

