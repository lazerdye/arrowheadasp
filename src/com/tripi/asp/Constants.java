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
 * The Constants class contains static contains used in the
 * com.tripi.asp package.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class Constants
{
    /**
     * Static, constant NullNode.
     */
    public final static NullNode nullNode = new NullNode();

    /**
     * Static, constant UndefinedValueNode.
     */
    public final static UndefinedValueNode undefinedValueNode =
        new UndefinedValueNode();

    /**
     * Static, constant NothingNode.
     */
    public final static NothingNode nothingNode =
        new NothingNode();

    /**
     * Static, constant empty exception.
     */
    public final static AspEmptyException emptyException =
        new AspEmptyException();
}

