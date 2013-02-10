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
 * Base class for runtime exceptions thrown from internal ASP classes.
 * 
 * @author Terence Haddock
 * @version 0.9
 */
public class AspRuntimeException extends RuntimeException
{
    /** Current debugging context */
    DebugContext ctx = null;

    /** Short description of this exception */
    String desc;

    /** 
     * Constructor with no initial debugging context.
     * @param desc Short description.
     */
    public AspRuntimeException(String desc)
    {
        super(desc);
        this.desc = desc;
    }

    /**
     * Constructor with the short description of the string, and
     * the debugging context of the exception.
     * @param desc Short description.
     * @param ctx Debugging context.
     */
    public AspRuntimeException(String desc, DebugContext ctx)
    {
        super(desc);
        this.desc = desc;
        this.ctx = ctx;
    }

    /**
     * Checks if this exception has its context.
     * @return <b>true</b> if this exception has its context, 
     *    <b>false</b> otherwise.
     */
    public boolean hasContext()
    {
        return ctx != null;
    }

    /**
     * Sets this exception's context.
     * @param ctx Debugging context
     */
    public void setContext(DebugContext ctx)
    {
        this.ctx = ctx;
    }

    /**
     * Gets the exception's context.
     * @return Debugging context
     */
    public DebugContext getContext()
    {
        return ctx;
    }

    /**
     * Obtains the short description of this exception.
     * @return short description of this exception, including
     *    debugging context.
     */
    public String toString()
    {
        if (ctx != null)
        {
            return "File " + ctx.displayFilename + " line " + ctx.lineno +
                ": " + desc;
        } else {
            return "File unknown line unknown: " + desc;
        }
    }
}
