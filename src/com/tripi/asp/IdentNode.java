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
 * IdentNode contains an identifier
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class IdentNode extends DefaultNode
{
    /** The string identifier of this node */
    String ident;

    /**
     * Constructor.
     * @param ident String identifier of this object
     */
    public IdentNode(String ident)
    {
        if (ident.charAt(0) == '.')
        {
            int i = 1;
            while ((i < ident.length()) && (ident.charAt(i) == ' ' ||
                ident.charAt(i) == '\t' || ident.charAt(i) == '\n' ||
                ident.charAt(i) == '_' )) i++;
            this.ident = ident.substring(i);
        } else {
            this.ident = ident;
        }
    }

    /**
     * Dumps the code representation of this node.
     * @see Node#dump()
     */
    public void dump()
    {
        System.out.print(ident);
    }

    /**
     * Executes this node. For an Ident node, if this ident contains
     * a function the function is called with no arguments. Otherwise,
     * the value of the identifier is returned.
     * @param context Context under which to execute this identifier.
     * @see Node#execute(AspContext)
     * @throws AspException if an error occurs.
     */
    public Object execute(AspContext context) throws AspException
    {
        Object val = context.getValue(this);
        if (val instanceof FunctionNode)
        {
            return ((FunctionNode)val).execute(new VarListNode(), context);
        }
        return val;
    }

    /**
     * Tests equality of this identifier to another identifier.
     * @param obj Object to compare this identifier to.
     * @return <b>true</b> if obj is an IdentNode and obj contains an
     * identifier with the same name.
     */
    public boolean equals(Object obj)
    {
        if (obj instanceof IdentNode) {
            return ident.equalsIgnoreCase(((IdentNode)obj).ident);
        } else {
            return false;
        }
    }

    /**
     * Calculates the hashtable code for this IdentNode. Every IdentNode
     * which contains the same identifier name should return the same
     * hash code.
     * @return hash code
     */
    public int hashCode()
    {
        return ident.toLowerCase().hashCode();
    }

    /**
     * Outputs the string representation of this identifier, which is
     * simple the text name of the identifier this IdentNode points to.
     * @return string representation of this identifier.
     */
    public String toString()
    {
        return ident;
    }
};

