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

import java.util.List;
import org.apache.log4j.Category;

/**
 * This node implements the defining of an array, DIM ident(length)
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class DefineIdentArrayNode extends DefaultNode
{
    /** Debugging class */
    private static final transient Category DBG =
        Category.getInstance(DefineIdentArrayNode.class);

    /** Name of this identifier */
    IdentNode ident;

    /** List of dimension sizes */
    VarListNode dimensions;

    /**
     * Constructor.
     *
     * @param ident Name of this array
     * @param dimensions Array of dimensions
     */
    public DefineIdentArrayNode(IdentNode ident, VarListNode dimensions)
    {
        this.ident = ident;
        this.dimensions = dimensions;
    }

    /**
     * Get the ident this define array is defining.
     *
     * @return identifier
     */
    public IdentNode getIdent()
    {
        return ident;
    }

    /**
     * Get the list of dimensions this define array is defining
     *
     * @return dimension list
     */
    public VarListNode getDimensionList()
    {
        return dimensions;
    }

    /**
     * Dumps this node.
     * @throws AspException if an error occurs
     * @see Node#dump()
     */
    public void dump() throws AspException
    {
        System.out.print("DIM ");
        ident.dump();
        System.out.print("(");
        dimensions.dump();
        System.out.println(")");
    }

    /**
     * Executes this node, defining the ident.
     *
     * @param context AspContext under which to evaluate this expression.
     * @throws AspException if an error occurs.
     * @return always null
     * @see Node#execute(AspContext)
     */
    public Object execute(AspContext context) throws AspException
    {
        /* Check if this variable is already in scope */
        if (context.inDirectScope(ident)) {
            throw new AspRedefineIdentException(ident.toString());
        }

        context.forceScope(ident);

        if (dimensions.size() > 0) {
            List dimList = (List)dimensions.execute(context);
            ArrayNode an = createArray(dimList, 0);
            context.setValue(ident, an);
        }
        return null;
    }

    /**
     * This function handles the actual creation of an array.
     * Internal function.
     *
     * @param vl List of dimensions.
     * @param index Index of this array's creation, used for recursive
     *  calls to create multi-dimensional arrays.
     * @return Array created.
     */
    private ArrayNode createArray(List vl, int index) {
        Integer size = (Integer)vl.get(index);

        if (DBG.isDebugEnabled())
            DBG.debug("createArray(vl,"+index+") = " + size);

        ArrayNode array = new ArrayNode(size.intValue()+1);
        if (index < vl.size() - 1) {
            for (int i = 0; i < size.intValue()+1; i++) {
                ArrayNode an = createArray(vl, index+1);
                array._setValue(i, an);
            }
        }
        return array;
    }
};

