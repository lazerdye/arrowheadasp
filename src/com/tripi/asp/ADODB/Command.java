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
package com.tripi.asp.ADODB;

import com.tripi.asp.AspException;
import com.tripi.asp.ByRefValue;
import java.sql.SQLException;

/**
 * This class represents the ADODB.Command object used to prepare statements
 * for execution.
 * @author Terence Haddock
 */
public class Command
{
    /** Text of the command to execute */
    public String CommandText = null;

    /** Type of the command */
    public int CommandType = 0;

    /** Internal Active connection */
    public Connection _ActiveConnection = null;

    /**
     * Execute a statements based off this command object.
     * @throws SQLException on SQL error
     */
    public RecordSet Execute() throws SQLException, AspException
    {
        return Execute(null);
    }

    /**
     * Execute a statement, returning the number of elements affected.
     * @param recordsAffected returns the number of rows affected.
     * @throws SQLException on SQL error
     */
    public RecordSet Execute(ByRefValue recordsAffected) throws SQLException,
        AspException
    {
        if (_ActiveConnection.State() == 0)
        {
            _ActiveConnection.Open();
        }
        RecordSet RS = new RecordSet();
        int ra = RS.Open(this);
        if ((recordsAffected != null) && (ra != -1))
        {
            Integer res = new Integer(ra);
            recordsAffected.setValue(res);
        }
        return RS;
    }

    /**
     * Obtain the active connection.
     * @return Active connection
     */
    public Connection ActiveConnection()
    {
        return _ActiveConnection;
    }

    /**
     * Set the active connection to a connection object.
     * @param ActiveConnection Active connection object.
     */
    public void ActiveConnection(Connection con)
    {
        _ActiveConnection = con;
    }

    /**
     * Sets the active connection to a string object.
     * @param DSN Active Connection DSN
     */
    public void ActiveConnection(String DSN)
    {
        _ActiveConnection = new Connection();
        _ActiveConnection.ConnectionString = DSN;
    }
}

