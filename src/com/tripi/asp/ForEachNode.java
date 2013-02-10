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

import java.util.Enumeration;
import java.lang.reflect.Array;

import org.apache.log4j.Category;

/**
 * The ForEachNode class handles the FOR EACH .. IN .. statement in
 * VBScript.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class ForEachNode implements Node
{
    /** The debugging class */
    private static final transient Category DBG = Category.getInstance(ForEachNode.class);

    /** The identifier of the loop variable */
    IdentNode ident;

    /** The expression we are looping through */
    Node expr;

    /** The block to execute for each statement */
    BlockNode block;

    /**
     * Constructor.
     *
     * @param ident The identifier to use as the loop variable.
     * @param expr The expression to loop through.
     * @param block The block of code to execute.
     */
    public ForEachNode(IdentNode ident, Node expr, BlockNode block)
    {
        this.ident = ident;
        this.expr = expr;
        this.block = block;
    }

    /**
     * Get the ident we should use for the loop.
     * @return ident for the loop.
     */
    public IdentNode getIdent()
    {
        return ident;
    }

    /**
     * Get the expression we should use for the loop.
     * @return expression for the loop
     */
    public Node getExpression()
    {
        return expr;
    }

    /**
     * Get the block to use in the loop.
     * @return block in the loop
     */
    public Node getBlock()
    {
        return block;
    }

    /**
     * Dumps the visual representation of this script.
     *
     * @throws AspException is an error occurs.
     * @see Node#dump()
     */
    public void dump() throws AspException
    {
        System.out.print("FOR EACH ");
        ident.dump();
        System.out.print(" IN ");
        expr.dump();
        System.out.println();
        block.dump();
        System.out.println("NEXT");
    }

    /**
     * Prepares this statement for execution. Calls prepare on the block
     * of statements contained within the for loop.
     * @param context The context under which we are operating.
     * @throws AspException if an error occurs.
     * @see Node#prepare(AspContext)
     */
    public void prepare(AspContext context) throws AspException
    {
        block.prepare(context);
    }

    /**
     * Executes this node.
     * @param context The context under which we are operating.
     * @return Return value of the expression
     * @throws AspException if an error occurs.
     * @see Node#execute(AspContext)
     */
    public Object execute(AspContext context) throws AspException
    {
        /* Obtain the value of the expression we are using for the loop */
        Object obj = expr.execute(context);
        if (DBG.isDebugEnabled()) {
            DBG.debug("ForEach called on object: " + obj);
        }
        /* Dereference */
        while (obj instanceof SimpleReference &&
            !(obj instanceof ArrayNode) &&
            !(obj instanceof JavaObjectNode) &&
            !(obj instanceof SimpleMap))
        {
            if (DBG.isDebugEnabled()) DBG.debug("Dereferencing: " +
                obj);
            obj = ((SimpleReference)obj).getValue();
        }
        if (DBG.isDebugEnabled()) DBG.debug("Final object: " + obj);
        /* Test if this is an instance of ArrayNode */
        if (obj instanceof ArrayNode)
        {
            executeArrayNode((ArrayNode)obj, context);
            return null;
        }
        /* Should be an instance of SimpleMap */
        if (obj instanceof SimpleMap)
        {
            executeSimpleMap((SimpleMap)obj, context);
            return null;
        }
        /* Test if this is an instance of JavaObjectNode
           this test is last because the previous tests may override this one */
        if (obj instanceof JavaObjectNode)
        {
            executeJavaObjectNode((JavaObjectNode)obj, context);
            return null;
        }
        throw new AspException("FOR EACH called on non-array object: " + obj);
    }

    /**
     * Internal function which handles the enumeration of JavaObjectNode
     * objects. XXX This is probably a bad idea, and should be moved somewhere
     * else (like a new class: JavaArrayNode).
     * @param context AspContext
     * @throws AspException on error
     */
    private void executeJavaObjectNode(JavaObjectNode javaObj, AspContext context)
        throws AspException
    {
        /* Find the sub-object contained in this Java object */
        Object array = javaObj.getSubObject();
        /* This object should be a Java array */
        if (!array.getClass().isArray()) 
            throw new AspException("FOR EACH called on non-array object: " + javaObj);
        /* Loop through all of the values */
        for (int i = 0; i < Array.getLength(array); i++)
        {
            Object value = Types.coerceToNode(Array.get(array, i));

            /* Set the ident to the current value */
            context.setValue(ident, value);

            /* Execute the block, handling Exit For exceptions */
            try {
                block.execute(context);
            } catch (AspExitForException ex) {
                return;
            }
        }
    }

    /**
     * Internal function which handles the enumeration of ArrayNode
     * object.
     * @param context AspContext
     * @throws AspException on error
     */
    private void executeArrayNode(ArrayNode array, AspContext context)
        throws AspException
    {
        /* Obtain the upper bound of the array */
        int upperBound = array.getUBOUND(1);
        /* Loop through each value */
        for (int i = 0; i <= upperBound; i++)
        {
            /* Create a parameter list of a single element to pass to array */
            VarListNode vn = new VarListNode();
            vn.append(new Integer(i));
            /* Obtain the value at the specified index */
            Object value = array.getIndex(vn, context);

            /* Sets the ident to the current value */
            context.setValue(ident, value);

            try {
                block.execute(context);
            } catch (AspExitForException ex) {
                return;
            }
        }
    }
    
    /**
     * Internal function which handles the enumeration of SimpleMap
     * object.
     * @param context AspContext
     * @throws AspException on error
     */
    private void executeSimpleMap(SimpleMap map, AspContext context)
        throws AspException
    {
        /* Obtain the keys of the expression */
        Enumeration keys = map.getKeys();
        /* Loop through the elements, executing the expression for each value */
        while (keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            /* Sets the ident to the current value */
            context.setValue(ident, key);

            try {
                block.execute(context);
            } catch (AspExitForException ex) {
                return;
            }
        }
    }
};

