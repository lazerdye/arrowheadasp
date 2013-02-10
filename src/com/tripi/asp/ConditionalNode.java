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

import org.apache.log4j.Category;

/**
 * Implements a conditional node. IF .. THEN .. ELSE .. END IF
 * 
 * @author Terence Haddock
 * @version 0.9
 */
public class ConditionalNode implements Node
{
    /** Debugging category */
    private static final transient Category        DBG = Category.getInstance(ConditionalNode.class);

    /** Condition to test */
    Node            condition;

    /** Code to evaluate if the condition is true */
    Node            trueNode;

    /** Code to evaluate if the condition is false, <b>null</b> if no code. */
    Node            falseNode;

    /** Context of this conditional node */
    DebugContext        context;

    /** 
     * Conditional node constructor.
     *
     * @param condition Condition to test.
     * @param trueNode node to execute if condition is true
     * @param falseNode node to execute if condition is false
     * @param context Context of this conditional node
     */
    public ConditionalNode(Object condition, Object trueNode, Object falseNode, DebugContext context)
    {
        this.condition = (Node)condition;
        this.trueNode = (Node)trueNode;
        this.falseNode = (Node)falseNode;
        this.context = context;
    }

    /**
     * Gets the condition this node is based on.
     * @return condition
     */
    public Node getCondition()
    {
        return condition;
    }

    /**
     * Gets the true node, null for none
     * @return true node
     */
    public Node getTrueNode()
    {
        return trueNode;
    }

    /**
     * Gets the false node, null for none
     * @return false node
     */
    public Node getFalseNode()
    {
        return falseNode;
    }

    /**
     * Dumps the output of this conditional node.
     * @throws AspException if any error occurs.
     * @see Node#dump()
     */
    public void dump() throws AspException
    {
        System.out.print("if ");
        condition.dump();
        System.out.println(" then");
        trueNode.dump();
        if (falseNode != null) {
            System.out.print("else\n");
            falseNode.dump();
        }
        System.out.println("end if");
    }

    /**
     * Prepares the nodes for execution.
     *
     * @param context Global context
     * @throws AspException if any error occurs.
     * @see Node#prepare(AspContext)
     */
    public void prepare(AspContext context) throws AspException
    {
        /* Prepare the true node */
        trueNode.prepare(context);
        /* Prepare the false node, if non-null */
        if (falseNode != null) falseNode.prepare(context);
    }

    /**
     * Executes this conditional node.
     * @param context AspContext to evaluate this node under.
     * @return Result of this conditional node.
     * @throws AspException if any error occurs.
     * @see Node#execute(AspContext)
     */
    public Object execute(AspContext ctx) throws AspException
    {
        try {
            Object obj = condition.execute(ctx);
            if (DBG.isDebugEnabled()) {
                DBG.debug("Condition: " + condition);
                DBG.debug("Result: " + obj);
                DBG.debug("Result Class: " + obj.getClass());
                DBG.debug("Result context: " + context);
            }
            Boolean i = Types.coerceToBoolean(obj);
            if (i.booleanValue()) {
                return trueNode.execute(ctx);
            } else if (falseNode != null) {
                return falseNode.execute(ctx);
            }
        } catch (AspException ex) {
            if (!ex.hasContext()) ex.setContext(context);
            throw ex;
        } catch (Exception ex) {
            DBG.error("Error in execute: ", ex);
            throw new AspNestedException(ex, context);
        }
        return null;
    }
};

