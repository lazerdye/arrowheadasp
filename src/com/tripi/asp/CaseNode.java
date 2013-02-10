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
 * Implements the case part of the select..case statement.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class CaseNode extends DefaultNode
{
    /** List of tests for this case */
    VarListNode tests;

    /** Code to execute if one of the tests evaluate to true */
    BlockNode exec;

    /**
     * Constructor to create a new case node.
     * @param tests Tests to evaluate, <b>null</b> for the ELSE statement.
     * @param exec Block to execute.
     */
    public CaseNode(VarListNode tests, BlockNode exec)
    {
        this.tests = tests;
        this.exec = exec;
    }

    /**
     * Dumps this case node.
     * @see Node#dump()
     * @throws AspException if an error occurs
     */
    public void dump() throws AspException
    {
        System.out.print("CASE ");
        if (tests == null)
        {
            System.out.print("ELSE");
        } else {
            tests.dump();
        }
        System.out.println();
        exec.dump();
    }

    /**
     * Checks if the test matches the case node.
     * @return <b>true</b> if the case node matches, <b>false</b> otherwise.
     * @throws AspException if an error occurs
     */
    public boolean matches(Object value, AspContext context) throws AspException
    {
        if (tests == null) {
            return true;
        }
        String strValue = Types.coerceToString(value);
        Vector vecValues = (Vector)tests.execute(context);
        for (int i = 0; i < vecValues.size(); i++)
        {
            String testValue = Types.coerceToString(vecValues.get(i));
            if (testValue.equals(strValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prepares this node. Calls prepare on children nodes.
     * 
     * @param context Global context
     * @see Node#prepare(AspContext)
     * @throws AspException if an error occurs.
     */
    public void prepare(AspContext context) throws AspException
    {
        exec.prepare(context);
    }

    /**
     * Executes this case node.
     * @param context AspContext under which to execute this case node.
     * @return the result of this block node.
     * @see Node#execute(AspContext)
     * @throws AspException if an error occurs
     */
    public Object execute(AspContext context) throws AspException
    {
        return exec.execute(context);
    }
};

