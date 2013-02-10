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

import org.apache.log4j.Logger;

/**
 * This class contains a list of variables.
 * 
 * @author Terence Haddock
 */
public class VarListNode extends DefaultNode
{
    /** Debugging context */
    private static final transient Logger DBG = Logger.getLogger(VarListNode.class);

    /** List of variables */
    Vector    vars;

    /**
     * Constructor, empty list
     */
    public VarListNode()
    {
        vars = new Vector();
    }

    /**
     * Adds an element to the list
     * @param obj Element to add
     */
    public void append(Object obj)
    {
        vars.add(obj);
    }

    /**
     * Adds all of the elements in another varlistnode to this varlistnode.
     * @param v Sub-var list node to add
     */
    public void appendAll(VarListNode v)
    {
        for (int i = 0; i < v.size(); i++)
        {
            append(v.get(i));
        }
    }

    /**
     * Inserts the element to the beginning of the list.
     * @param obj Element to insert
     */
    public void prepend(Object obj)
    {
        vars.insertElementAt(obj, 0);
    }

    /**
     * Returns the size of this list 
     * @return size of this list
     */
    public int size()
    {
        return vars.size();
    }

    /**
     * Obtains an element at the specified index
     * @param index Index of element to obtain
     */
    public Object get(int index)
    {
        return vars.get(index);
    }

    /**
     * Dumps the representation of this varlist
     * @see DefaultNode#dump
     */
    public void dump() throws AspException
    {
        int i;
        for (i = 0; i < vars.size(); i++)
        {
            if (i != 0) System.out.print(" , ");
            Object obj = vars.get(i);
            if (obj instanceof Node)
            {
                ((Node)obj).dump();
            } else if (obj == null) {
                System.out.print("NULL");
            } else {
                System.out.print(obj.toString());
            }
        }
    }

    /**
     * Executes all of the elements in the variable list and returns
     * a vector of the return values of each node.
     * @param context Current context
     * @see DefaultNode#execute(AspContext)
     */
    public Object execute(AspContext context) throws AspException
    {
        Vector vec = new Vector();
        int i;
        for (i = 0; i < vars.size(); i++)
        {
            Object nObj = vars.get(i);
            if (nObj instanceof Node)
            {
                Node node = (Node)nObj;
                Object obj = node.execute(context);
                obj = Types.dereference(obj);
                vec.add(obj);
            } else {
                vec.add(nObj);
            }
        }
        return vec;
    }
};

