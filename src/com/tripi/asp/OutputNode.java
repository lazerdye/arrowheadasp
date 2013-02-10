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

import java.io.IOException;

/**
 * OutputNode handles the ASP notation &lt;%= value %&gt; to directly output 
 * an expression's value.
 * @author Terence Haddock
 * @version 0.9
 */
public class OutputNode extends DefaultNode
{
    /** Expression to output */
    Node expr;

    /** Debugging context, location of output node in asp code. */
    DebugContext dbgCtx;

    /**
     * Constructor.
     * @param expr Expression to output.
     * @param dbgCtx Context of expression in ASP file.
     */
    public OutputNode(Object expr, DebugContext dbgCtx)
    {
        this.expr = (Node)expr;
    }

    /**
     * Dumps the representation of this node.
     * @throws AspException in case of error
     * @see Node#dump
     */
    public void dump() throws AspException
    {
        System.out.print("<%=");
        expr.dump();
        System.out.print("%>");
    }

    /**
     * Executes this output node.
     * @param context AspContext under which to execute this node.
     * @return return value, for this node always null
     * @throws AspException in case of error
     * @see Node#execute
     */
    public Object execute(AspContext context) throws AspException
    {
        final IdentNode respIdent = new IdentNode("response");
        try {
            Object obj = expr.execute(context);
            String str = Types.coerceToString(obj);
            JavaObjectNode respObj = (JavaObjectNode)context.getValue(respIdent);
            Response resp = (Response)respObj.getSubObject();
            resp.Write(str);
        } catch (IOException ex) {
            throw new AspNestedException(ex, dbgCtx);
        } catch (AspException ex) {
            if (!ex.hasContext()) {
                ex.setContext(dbgCtx);
            }
            throw ex;
        }
        return null;
    }
};

