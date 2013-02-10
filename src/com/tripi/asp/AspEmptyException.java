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
 * Special exception class which returns blank for all values.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class AspEmptyException extends AspException
{

    /**
     * Constructor
     */
    public AspEmptyException()
    {
        super("Empty");
    }

    /**
     * AspCode
     * @return AspCode of this error
     */
    public int AspCode()
    {
        return 0;
    }

    /**
     * AspDescription
     * @return Detailed description of this error
     */
    public String AspDescription()
    {
        return "";
    }

    /**
     * Description
     * @return Short Description of this error
     */
    public String Description()
    {
        return "";
    }

    /**
     * Category
     * @return Category of this error 
     */
    public int Category()
    {
        return 0;
    }

    /**
     * Column
     * @return Column where this error occured
     */
    public int Column()
    {
        return 0;
    }

    /**
     * File
     * @return File where this error occured
     */
    public String File()
    {
        return "";
    }

    /**
     * Line
     * @return Line number where this error occured
     */
    public int Line()
    {
        return 0;
    }

    /**
     * Number
     * @return COM number of this error
     */
    public int Number()
    {
        return 0;
    }

    /**
     * Source
     * @return Source where this error occured
     */
    public String Source()
    {
        return "";
    }
}
