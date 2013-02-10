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

import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * SubDefinitionCode contains the parsed representation of a subroutine
 * definition, also includes function definition syntax.
 *
 * @author Terence Haddock
 */
public class SubDefinitionNode implements FunctionNode
{
    /** Debugging category */
    private static final transient Logger DBG = Logger.getLogger(SubDefinitionNode.class);

    /** Name of the subroutine/function */
    IdentNode        ident;

    /** Parameter list to the function */
    VarListNode        identlist;

    /** Code within the function */
    BlockNode        block;

    /** <b>true</b> if this is a function, <b>false</b> otherwise */
    boolean            isFunction;

    /**
     * Constructor.
     * @param ident Name of subroutine
     * @param identlist Name of parameters to subroutine
     * @param block Code within the subroutine
     * @param isFunction is this a function?
     */
    public SubDefinitionNode(IdentNode ident, VarListNode identlist,
                BlockNode block, boolean isFunction)
    {
        this.ident = ident;
        this.identlist = identlist;
        this.block = block;
        this.isFunction = isFunction;
    }

    /**
     * Get the identifier of this subroutine.
     * @return identifier of this subroutine.
     */
    public IdentNode getIdent()
    {
        return ident;
    }

    /**
     * Get the list of parameters for this subroutine.
     * @return list of parameters for this subroutine.
     */
    public VarListNode getIdentList()
    {
        return identlist;
    }

    /**
     * Get the block of this subroutine.
     * @return block of this subroutine.
     */
    public BlockNode getBlock()
    {
        return block;
    }

    /**
     * Is this a function?
     * @return <b>true</b> if this is a function, <b>false</b> otherwise.
     */
    public boolean isFunction()
    {
        return isFunction;
    }

    /**
     * Dumps the string representation of this subroutine definition.
     * @see DefaultNode#dump
     */
    public void dump() throws AspException
    {
        if (isFunction) {
            System.out.print("FUNCTION ");
        } else {
            System.out.print("SUB ");
        }
        ident.dump();
        System.out.print(" (");
        identlist.dump();
        System.out.println(")");
        block.dump();
        if (isFunction) {
            System.out.println("END FUNCTION");
        } else {
            System.out.println("END SUB");
        }
    }

    /**
     * Prepares this subroutine, it effectively defines this subroutine
     * within the global scope.
     * @param context AspContext for this function call
     */
    public void prepare(AspContext scope) throws AspException
    {
        /* Here is the purpose of prepare, to set the scope of a
         * subroutine definition before code is executed which
         * may reference it. */
        scope.setValue(ident, this);
        /* We do not prepare the block itself for execution, as this would
           bring the block's define statements into global scope, a big oops */
    }

    /**
     * Executes this subroutine, the subroutine definition itself has no
     * effect when executed.
     * @param context AspContext under which to execute this code.
     * @return always null
     */
    public Object execute(AspContext scope) throws AspException
    {
        return null;
    }

    /**
     * This function executes the subroutine/function itself.
     * @param param List of parameters
     * @param context Context under which to execute this code.
     * @return return value of function, null if this is a subroutine
     * @see FunctionNode#execute(VarListNode, AspContext)
     */
    public Object execute(VarListNode param, AspContext scope) throws AspException
    {
        /* Check parameter count */
        if (param.size() != identlist.size()) {
            throw new AspException("Function called with invalid arguments");
        }
        /* Get the values of the parameters */
        Vector value = (Vector)param.execute(scope);
        /* Create a new scope */
        Hashtable newScope = new Hashtable();
        /* If this is a function node, we need to prepare the scope for
           the function name. */
        if (isFunction)
        {
            newScope.put(ident, "");
        }
        /* Set the variables */
        for (int i = 0; i < value.size(); i++)
        {
            Object iVal = Types.dereference(value.get(i));
            Object iParam = identlist.get(i);
            /* If this is a pass by reference, get the actual ident */
            if (iParam instanceof ByRefNode) {
                iParam = ((ByRefNode)iParam).getIdent();
            }
            IdentNode ident = (IdentNode)iParam;
            if (iVal == null) iVal = Constants.nullNode;
            newScope.put(ident, iVal);
        }
        /* Execute the block within the new scope */
        scope.pushSubroutineScope(newScope);
        try {
            /* Prepare the scope of the variables */
            block.prepare(scope);
            block.execute(scope);
        } catch (AspExitFunctionException ex) {
            if (!isFunction) throw ex;
        } catch (AspExitSubException ex) {
            if (isFunction) throw ex;
        } finally {
            scope.popSubroutineScope();
        }
        /* Check for any pass by reference values */
        for (int i = 0; i < value.size(); i++)
        {
            Object iVal = param.get(i);
            /* The param needs to be an IdentNode */
            if (iVal instanceof IdentNode) {
                Object iParam = identlist.get(i);
                /* The parameter has to be a pass by reference */
                if (iParam instanceof ByRefNode) {
                    iParam = ((ByRefNode)iParam).getIdent();
                    /* Get the value */
                    Object newVal = Types.dereference(newScope.get((IdentNode)iParam));
                    /* Set the value in the return scope */
                    scope.setValue((IdentNode)iVal, newVal);
                }
            }
        }
        /* If this is a function, we need to set the return value */
        if (isFunction) {
            return newScope.get(ident);
        } else {
            return null;
        }
    }
};

