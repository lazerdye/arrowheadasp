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

public class SelectNode implements Node
{
    Node expr;
    VarListNode statements;

    public SelectNode(Node expr, VarListNode statements)
    {
        this.expr = expr;
        this.statements = statements;
    }

    public void dump() throws AspException
    {
        System.out.print("SELECT CASE ");
        expr.dump();
        System.out.println();
        for (int i = 0; i < statements.size(); i++)
        {
            CaseNode obj = (CaseNode)statements.get(i);
            obj.dump();
        }
        System.out.println("END SELECT");
    }

    public void prepare(AspContext context) throws AspException
    {
        for (int i = 0; i < statements.size(); i++)
        {
            CaseNode obj = (CaseNode)statements.get(i);
            obj.prepare(context);
        }
        /* expr needs not be prepared */
    }

    public Object execute(AspContext context) throws AspException
    {
        Object value = expr.execute(context);
        for (int i = 0; i < statements.size(); i++)
        {
            CaseNode cas = (CaseNode)statements.get(i);
            if (cas.matches(value, context)) {
                return cas.execute(context);
            }
        }
        return null;
    }
};

