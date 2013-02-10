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
 * The OnErrorNode handles the ON ERROR GOTO x and ON ERROR RESUME NEXT
 * code which enables error handling in subroutines.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class OnErrorNode extends DefaultNode
{
    /**
     * Debugging class
     */ 
    private static final transient Category DBG = Category.getInstance(OnErrorNode.class);

    /**
     * Line number to jump to
     */
    NumberNode lineno;

    /**
     * Resume next?
     */
    boolean resumenext;

    /**
     * Constructor for resume next.
     */
    public OnErrorNode()
    {
        resumenext = true;
    }

    /**
     * Constructor to jump to line number.
     * @param lineno Line number to jump to
     */
    public OnErrorNode(NumberNode lineno)
    {
        this.lineno = lineno;
        resumenext = false;
    }

    /**
     * Executes this node, setting the error handler appropriately.
     * 
     * @param context AspContext under which to execute this node.
     * @return null
     * @see Node#execute(AspContext)
     */
    public Object execute(AspContext context) throws AspException
    {
        final IdentNode ident = new IdentNode("_err");
        if (DBG.isDebugEnabled()) DBG.debug("Executing On Error");
        context.forceScope(ident);
        if (resumenext) {
            if (DBG.isDebugEnabled()) DBG.debug("Setting value to null");
            context.setValue(ident, null);
        } else {
            Object val = lineno.execute(context);
            Integer iVal = Types.coerceToInteger(val);
            if (DBG.isDebugEnabled()) DBG.debug("Setting value to " + iVal);
            context.setValue(ident, iVal);
        }
        return null;
    }
}
