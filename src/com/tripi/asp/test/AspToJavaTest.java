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
package com.tripi.asp.test;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 * The AspToJavaTest class is used to test calling Java functions from ASP
 * and having the session data maintained properly.
 *
 * @author Terence Haddock
 */
public class AspToJavaTest
{
    /**
     * Constructor
     */
    public AspToJavaTest() {};

    /**
     * Test creating a session in Java.
     */
    public Object testCreateSession(HttpServletRequest request)
    {
        /* Obtain a new session */
        HttpSession session = request.getSession(true);

        /* Return the "SessionOnStart" variable */
        return session.getAttribute("SessionOnStart");
    }

    /**
     * Test obtaining a session variable set from ASP
     */
    public Object testGetSessionVariable(HttpServletRequest request,
        String variableName)
    {
        /* Obtain the session */
        HttpSession session = request.getSession(false);

        /* Return the requested variable. */
        return session.getAttribute(variableName);
    }

    /**
     * Test setting a variable from Java.
     */
    public void testSetSessionVariable(HttpServletRequest request, 
        String variableName, Object variableValue)
    {
        /* Obtain the session */
        HttpSession session = request.getSession(false);

        /* Set the requested variable. */
        session.setAttribute(variableName, variableValue);
    }
}
