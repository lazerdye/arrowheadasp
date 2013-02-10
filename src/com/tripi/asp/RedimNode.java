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

import java.util.Vector;

/**
 * RedimNode handles the REDIM statement in vbscript.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class RedimNode extends DefaultNode 
{
    /** Debugging class */
    private static final transient Logger DBG = Logger.getLogger(RedimNode.class);

    /** Identifier of variable to redim */
    IdentNode ident;

    /** List of new dimensions */
    VarListNode dimensions;

    /** Preserve the existing entries? */
    boolean preserve;

    /**
     * Constructor.
     * @param ident Identifier of variable to redim.
     * @param dimensions list of sizes of dimensions
     * @param preserve Preserve the existing entries?
     */
    public RedimNode(IdentNode ident, VarListNode dimensions, boolean preserve)
    {
        this.ident = ident;
        this.dimensions = dimensions;
        this.preserve = preserve;
    }

    /**
     * Get the ident
     * @param ident Identifier
     */
    public IdentNode getIdent()
    {
        return ident;
    }

    /**
     * Get the dimension list.
     * @return dimension list
     */
    public VarListNode getDimensionList()
    {
        return dimensions;
    }

    /**
     * Should this redimension preserve the values?
     * @return <b>true</b> or <b>false</b>
     */
    public boolean isPreserve()
    {
        return preserve;
    }

    /**
     * Dumps the source representation of this node.
     * @throws AspException on error
     * @see Node#dump
     */
    public void dump() throws AspException
    {
        System.out.print("REDIM ");
        ident.dump();
        System.out.print("(");
        dimensions.dump();
        System.out.println(")");
    }

    /**
     * Executes this node.
     * @param context AspContext under which this node is to be execute.
     * @return return value, ReDimNode-s have no return value.
     * @throws AspException on error
     * @see Node#execute
     */
    public Object execute(AspContext context) throws AspException
    {
        if (DBG.isDebugEnabled()) {
            DBG.debug("Redim-ing: " + ident + " to " + dimensions.size() + " dims");
            DBG.debug("Preseve: " + preserve);
        }
        Vector vecValues = (Vector)dimensions.execute(context);

        if (preserve)
        {
            Object obj = context.getValue(ident);
            if (DBG.isDebugEnabled())
                DBG.debug("Sub-element: " + obj.getClass());
            if (obj instanceof ArrayNode)
            {
                ArrayNode an = (ArrayNode)obj;
                an.internResizeArray(vecValues, 0);
                return null;
            }
        }

        if (dimensions.size() > 0) {
            ArrayNode an = createArray(vecValues, 0);
            context.setValue(ident, an);
        }
        return null;
    }

    /**
     * Internal function which creates an array from a list of dimensions.
     * @param vl List of dimensions
     * @param index Current position, initially should be 0
     * @return new ArrayNode
     */
    static ArrayNode createArray(Vector vl, int index) throws AspException {
        Integer size = Types.coerceToInteger(vl.get(index));
        ArrayNode array = new ArrayNode(size.intValue()+1);
        if (index < vl.size() - 1) {
            for (int i = 0; i <= size.intValue(); i++) {
                ArrayNode an = createArray(vl, index+1);
                array._setValue(i, an);
            }
        }
        return array;
    }
};

