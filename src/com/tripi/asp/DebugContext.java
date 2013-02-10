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
 * Debugging context, filename and line number.
 * 
 * @author Terence Haddock
 * @version 0.9
 */
public class DebugContext implements Serializable
{
    /** Display filename */
    String displayFilename;

    /** Line number */
    int lineno;

    /** Starting column number */
    int columnno;

    /**
     * Constructor.
     * @param displayFilename File name
     * @param lineno Line number
     */
    public DebugContext(String displayFilename, int lineno)
    {
        this.displayFilename = displayFilename;
        this.lineno = lineno;
        this.columnno = -1;
    }

    /**
     * Constructor.
     * @param displayFilename File name
     * @param lineno Line number
     * @param column Column number
     */
    public DebugContext(String displayFilename, int lineno, int columnno)
    {
        this.displayFilename = displayFilename;
        this.lineno = lineno;
        this.columnno = columnno;
    }

    /**
     * Constructor, without a defined filename/lineno.
     */
    public DebugContext()
    {
        this.displayFilename = null;
        this.lineno = 0;
        this.columnno = 0;
    }

    /**
     * Set the location filename.
     * @param filename Filename to set for the debugging context
     */
    public void setFilename(String filename)
    {
        this.displayFilename = filename;
    }

    /**
     * Get the location filename.
     * @param filename Filename for debugging location.
     */
    public String getFilename()
    {
        return displayFilename;
    }

    /**
     * Set the location line number.
     * @param lineno Line number to set for the debugging context
     */
    public void setLineNo(int lineno)
    {
        this.lineno = lineno;
    }

    /**
     * Get the location line number.
     * @return location line number.
     */
    public int getLineNo()
    {
        return lineno;
    }

    /**
     * Set the location column number.
     * @param columnno Column number to set for the debugging context
     */
    public void setColumnNo(int lineno)
    {
        this.columnno = columnno;
    }

    /**
     * Get the column number.
     * @return location column number.
     */
    public int getColumnNo()
    {
        return columnno;
    }

    /**
     * Obtain the string representation of this debugging context.
     * @return string representation of this debugging context.
     */
    public String toString()
    {
        if (columnno != -1)
            return "file: " + displayFilename + " line: " + lineno + " column: " + columnno;
        return "file: " + displayFilename + " line: " + lineno;
    }
};

