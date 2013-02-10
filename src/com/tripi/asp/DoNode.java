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
 * Implements the looping constructs, DO .. UNTIL, WHILE ... DO,
 * WHILE .. WEND, etc...
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class DoNode implements Node
{
    /** Expression to test */
    Node        expr;

    /** Block to execute */
    BlockNode    code;

    /** Check expression after loop? */
    boolean        checkAfter;

    /** DO .. UNTIL, or DO .. WHILE? */
    boolean        doUntil;

    /**
     * Constructor.
     *
     * @param expr Expression to test.
     * @param code Code to execute in loop.
     * @param checkAfter check after loop?
     * @param doUntil DO .. UNTIL or DO .. WHILE?
     */
    public DoNode(Node expr, BlockNode code, boolean checkAfter,
                boolean doUntil)
    {
        this.expr = expr;
        this.code = code;
        this.checkAfter = checkAfter;
        this.doUntil = doUntil;
    }

    /**
     * Dumps this node.
     * @throws AspException if an error occurs
     * @see Node#dump() 
     */
    public void dump() throws AspException
    {
        if (!checkAfter) {
            System.out.print("DO ");
            if (doUntil) {
                System.out.print("UNTIL");
            } else {
                System.out.print("WHILE");
            }
            expr.dump();
            System.out.println();
        }
        code.dump();
        System.out.print("LOOP");
        if (checkAfter) {
            if (doUntil) {
                System.out.print("UNTIL ");
            } else {
                System.out.print("WHILE ");
            }
            expr.dump();
            System.out.println();
        }
        
    }

    /**
     * Prepares this node for executtion.
     * @param context Global context
     * @throws AspException if an error occurs
     * @see Node#prepare(AspContext)
     */
    public void prepare(AspContext context) throws AspException
    {
        code.prepare(context);
    }

    /**
     * Executes this node
     * @param context Current context
     * @return result of this node's execution
     * @throws AspException if an error occurs
     * @see Node#prepare(AspContext)
     */
    public Object execute(AspContext context) throws AspException
    {
        if (!checkAfter) {
            // Check when we start.
            Object obj = expr.execute(context);
            Boolean val = Types.coerceToBoolean(obj);
            if (doUntil && val.booleanValue()) return null;
            if (!doUntil && !val.booleanValue()) return null;
        }
        do {
            // Execute the sub-expression
            try {
                code.execute(context);
            } catch (AspExitDoException ex) {
                // Early exit
                return null;
            }
            // Check the values now
            Object obj = expr.execute(context);
            Boolean val = Types.coerceToBoolean(obj);
            if (doUntil && val.booleanValue()) return null;
            if (!doUntil && !val.booleanValue()) return null;
        } while (true);
    }
};

