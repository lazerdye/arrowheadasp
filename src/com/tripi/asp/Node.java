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

import java.io.Serializable;

/**
 * This is the base interface for all internal ASP objects.
 * Implemented statements:
 * <ul>
 * <li><b>Call</b> - Fully implemented.
 * <li><b>Const</b> - Implemented, <b>TODO</b> not for Public/Private.
 * <li><b>Dim</b> - Fully implemented.
 * <li><b>Do ... Loop</b> - Fully implemented.
 * <li><b>Erase</b> - <b>TODO</b> not implemented.
 * <li><b>Exit ... </b> - Fully implemented.
 * <li><b>For ... Next</b> - Fully implemented.
 * <li><b>For Each ... Next</b> - Fully implemented.
 * <li><b>Function</b> -
 * <li><b>If ... Then .. Else</b> -
 * <li><b>On Error</b> -
 * <li><b>Option Explicit</b> -
 * <li><b>Private</b> -
 * <li><b>Public</b> -
 * <li><b>Randomize</b> -
 * <li><b>ReDim</b> -
 * <li><b>Rem</b> -
 * <li><b>Select Case</b> -
 * <li><b>Set</b> -
 * <li><b>Sub</b> -
 * <li><b>While ... Wend</b> -
 * </ul>
 *
 * @author Terence Haddock
 * @version 0.9
 */
public interface Node extends Serializable
{
    /**
     * Dumps the node representation in ASP textual format.
     * This function is primarily used for debugging, but it is recommended
     * that the node dump itself in a format which could be re-parsed as
     * valid code. Whitespace need not be preserved.
     *
     * @throws AspException if an error occurs.
     */
    public void dump() throws AspException;

    /**
     * Prepares the code for execution. This includes defining global
     * functions and subroutines, setting global variables, and
     * any other preprocessing required. This procedure should be called
     * before execute(AspContext). As part of the preparation, it should call
     * prepare() on any child nodes it may use during execution.
     *
     * @param context The global context
     * @throws AspException on error
     * @see #execute(AspContext)
     */
    public void prepare(AspContext context) throws AspException;

    /**
     * Executes this node in the defined context. This function should return
     * a valid Node or base type as a return value. It can return one of the
     * Exit... objects to signal the Asp server to exit the block, loop, etc.
     * Except in special cases, prepare(AspContext) should
     * be called before execute(AspContext)
     *
     * @param context The current context
     * @return result of executing this node.
     * @throws AspException on error
     * @see #prepare(AspContext)
     */
    public Object execute(AspContext context) throws AspException;
}
