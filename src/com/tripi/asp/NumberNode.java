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
 * NumberNode class represents a number parsed from the ASP source file.
 * Usually contains either the Integer class or the Double class.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class NumberNode extends DefaultNode
{
    /** Number this node contains */
    Object number;

    /**
     * Constructor.
     * @param number Number this node should contain.
     */
    public NumberNode(Object number)
    {
        this.number = number;
    }

    /**
     * Get the number this node contains.
     * @return number
     */
    public Object getNumber()
    {
        return number;
    }

    /**
     * Dumps this node's string representation.
     * @see DefaultNode#dump
     */
    public void dump()
    {
        System.out.print(number);
    }

    /**
     * Executes this node within the specified context. This class evaluates
     * to the number this class contains.
     * @param context AspContext under which to execute this node.
     * @return Number this node contains.
     * @see Node#execute(AspContext)
     */
    public Object execute(AspContext context)
    {
        return number;
    }

    /**
     * Create a number node from a decimal string.
     * @param str String to create number node from.
     */
    public static NumberNode fromDoubleToken(String str)
    {
        /* See if it should be an integer or float */
        if (str.indexOf(".") == -1)
        {
            /* Should be an integer */
            long longValue = Long.parseLong(str);
            if (longValue < Integer.MAX_VALUE)
            {
                return new NumberNode(new Integer((int)longValue));
            } else {
                return new NumberNode(new Long(longValue));
            }
        } else {
            /* Should be a floating point */
            return new NumberNode(new Double(str));
        }
    }

    /**
     * Create a number node from a hex string.
     * @param str Hex string to create number node from.
     */
    public static NumberNode fromHexToken(String str)
    {
        if (!str.startsWith("&H"))
        {
            throw new AspRuntimeException("Invalid hex string: " + str);
        }
        long longValue = Long.parseLong(str.substring(2), 16);
        if (longValue < Integer.MAX_VALUE)
            return new NumberNode(new Integer((int)longValue));
        else
            return new NumberNode(new Long(longValue));
    }
};

