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

import org.apache.log4j.Category;

/**
 * Implements the internal VBScript arrays.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class ArrayNode extends DefaultNode implements MapNode
{
    /** Debugging object */
    private static final transient Category DBG = Category.getInstance(ArrayNode.class);

    /** Contents of this array */
    Vector values;

    /**
     * Array constructor.
     * @param count Number of elements in the array 
     */
    public ArrayNode(int count)
    {
        values = new Vector(count);
        for (int i = 0; i < count; i++)
        {
            values.add(Constants.undefinedValueNode);
        }
    }

    /**
     * Internal function to convert this ASP Array to Java List.
     * TODO: Does not handle multidimensional arrays.
     * @return Java array version of this ASP array.
     */
    public Object[] toJavaArray()
    {
        Object array[] = new Object[values.size()];
        for (int i = 0; i < values.size(); i++)
        {
            Object theValue = values.get(i);
            while (theValue instanceof JavaObjectNode)
                theValue = ((JavaObjectNode)theValue).getSubObject();
            array[i] = theValue;
        }
        return array;
    }

    /**
     * Obtains an element of this array.
     *
     * @param varlist index of item to obtain from array.
     * @param context AspContext in which to evaluate this expression.
     * @return object stored in the array
     * @throws AspException if an error occurs.
     * @see MapNode#getIndex(VarListNode,AspContext)
     */
    public Object getIndex(VarListNode varlist, AspContext context)
        throws AspException
    {
        Vector vec = (Vector)varlist.execute(context);
        if (vec.size() == 0) {
            throw new AspException("Invalid number of parameters to array index");
        }
        return internGetIndex(vec, 0);
    }
    
    /**
     * Internal routine to obtain a element of an array.
     * Handles multi-dimentional arrays.
     *
     * @param vec vector of indexes
     * @param index current index we are working on
     * @return object stored in the array
     * @throws AspException if an error occurs.
     * @see ArrayNode#getIndex(VarListNode,AspContext)
     * @see MapNode#getIndex(VarListNode,AspContext)
     */
    protected Object internGetIndex(Vector vec, int index)
        throws AspException
    {
        Object objVal = vec.get(index);
        final Integer num = Types.coerceToInteger(objVal);
        if (num.intValue() < 0 || num.intValue() >= values.size())
        {
            throw new AspException("Array out of bounds: " + num.intValue());
        }
        if (DBG.isDebugEnabled())
            DBG.debug("internGetIndex: " + vec.size() + "/" + index);
        if (index == (vec.size() - 1)) {
            /**
             * SimpleReference is an internal class used to get and
             * set specific values.
             */
            return new SimpleReference() {
                /**
                 * Obtain value.
                 * @return value
                 */
                public Object getValue() {
                    return values.get(num.intValue());
                };
                /**
                 * Set the value
                 * @param inObj value to set
                 */
                public void setValue(Object inObj) {
                    values.set(num.intValue(),inObj);
                }
            };
        } else {
            if (DBG.isDebugEnabled())
                DBG.debug("Getting index " + num.intValue());
            Object realValue = values.get(num.intValue());
            if (realValue instanceof ArrayNode) {
                return ((ArrayNode)realValue).internGetIndex(vec, index + 1);
            } else {
                throw new AspException("Too many dimensions: " + index);
            }
        }
    }

    /**
     * Internal function to erase the contents of the array.
     */
    synchronized void internErase()
    {
        int i;
        for (i = 0; i < values.size(); i++)
        {
            Object obj = values.get(i);
            if (obj instanceof ArrayNode)
                ((ArrayNode)obj).internErase();
            else 
                values.set(i, Constants.undefinedValueNode);
        }
    }

    /**
     * Internal function to resize the array.
     * @param vecValues vector of values
     * @param index Index we are at in the vecValues array
     * @throws AspException on error
     */
    synchronized void internResizeArray(Vector vecValues, int index)
        throws AspException
    {
        boolean isMulti;
        int newSize = ((Integer)vecValues.get(index)).intValue();
        isMulti = (values.size()>0 && (values.get(0) instanceof ArrayNode));
        if (DBG.isDebugEnabled()) {
            DBG.debug("Original size: " + values.size());
            DBG.debug("New size: " + newSize);
            DBG.debug("Ismulti? " + isMulti);
        }
        if ((newSize + 1) != values.size())
        {
            if (index != vecValues.size() - 1)
                throw new AspInvalidArgumentsException("Cannot change the number of dimensions in a multi-dimensional array");
            if (isMulti)
                throw new AspInvalidArgumentsException("Cannot resize any but the last dimension in a multi-dimensional array");
            if (vecValues.size() > (index + 1))
                throw new AspInvalidArgumentsException("Cannot change the number of dimensions of an array");
            while (values.size() > (newSize + 1))
            {
                values.removeElementAt(values.size() - 1);
            }
            while (values.size() < (newSize + 1))
            {
                values.add(Constants.undefinedValueNode);
            }
        } else {
            if (!isMulti && vecValues.size() != index-1)
            {
                throw new AspInvalidArgumentsException("Cannot change the number of dimensions in a multi-dimensional array");
            }
            for (int i = 0; i < values.size(); i++)
            {
                Object obj = values.get(i);
                if (obj instanceof ArrayNode)
                    ((ArrayNode)obj).internResizeArray(vecValues,index+1);
            }
        }
    }

    /**
     * Internal function to get the value of a specified element in the array.
     * @param index Index to get
     * @return value at that index
     */
    public Object _getValue(int index)
    {
        return values.get(index);
    }

    /**
     * Internal function to set the value of a specific element of the array.
     * @param index Index to set
     * @param value new value
     */
    public void _setValue(int index, Object value)
    {
        values.set(index, value);
    }

    /**
     * Obtains the upper boundary of the array.
     * @param dimension Dimension to obtain UBOUND of
     * @return upper bound of the array, the number of elements in the array.
     * @throws AspException on error
     */
    public int getUBOUND(int dimension) throws AspException
    {
        if (dimension > 1) {
            Object ref = values.get(0);
            if (ref instanceof ArrayNode) {
                return ((ArrayNode)ref).getUBOUND(dimension - 1);
            } else {
                throw new AspSubscriptOutOfRangeException("UBOUND");
            }
        } else {
            return values.size()-1;
        }
    }
    
    /**
     * Obtains the lower boundary of the array.
     * @param dimension Dimension to obtain LBOUND of
     * @return lower bound of the array, always 1
     * @throws AspException on error
     */
    public int getLBOUND(int dimension) throws AspException
    {
        if (dimension > 1) {
            Object ref = values.get(0);
            if (ref instanceof ArrayNode) {
                return ((ArrayNode)ref).getLBOUND(dimension - 1);
            } else {
                throw new AspSubscriptOutOfRangeException("UBOUND");
            }
        } else {
            return 0;
        }
    }
};

