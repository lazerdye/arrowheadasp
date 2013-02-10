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

import java.sql.SQLException;
import java.util.Vector;
import java.util.Enumeration;
import jregex.*;

import com.tripi.asp.*;

import org.apache.log4j.Category;

/**
 * This class represents the ADODB.Connection object. This object handles
 * connections to the database.
 * Implementation status:
 * <ul>
 * <li>Attributes - Implemented.
 * <li>CommandTimeout - Implemented, but ignored.
 * <li>ConnectionString - Implemented.
 * <li>ConnectionTimeout - Implemented, but ignored.
 * <li>CursorLocation - Implemented, but ignored.
 * <li>DefaultDatabase - Implemented, but ignored, does not make sense in JDBC
 * <li>Errors - Implemented
 * <li>IsolationLevel - <b>TODO</b> Not implemented
 * <li>Mode - <b>TODO</b> Not implemented
 * <li>Properties - <b>TODO</b> Not implemented.
 * <li>Provider - <b>TODO</b> Not implemented
 * <li>State - Implemented
 * <li>Version - <b>TODO</b> Not implemented
 * <li>BeginTrans - Implemented
 * <li>CommitTrans - Implemented
 * <li>RollbackTrans - Implemented
 * <li>Close - Implemented
 * <li>Execute - Implemented
 * <li>Open - Implemented
 * <li>OpenSchema - <b>TODO</b> Not implemented
 * </ul>
 * @author Terence Haddock
 */
public class Connection
{
    /** Debugging category. */
    Category DBG = Category.getInstance(Connection.class);

    /** Current java.sql.Connection object. */
    java.sql.Connection cx = null;

    /** Are we in a transaction? */
    protected boolean inTransaction = false;
    
    /**
     * Constructor, no arguments.
     */
    public Connection()
    {
    }

    /**
     * What to do on page end.
     */
    public void OnPageEnd(Object ignoreme)
    {
        if (DBG.isDebugEnabled()) DBG.debug("OnPageEnd called");
        try {
            /* Close the connection */
            Close();
        } catch (AspException ex)
        {
            DBG.error("Error on ADODB.Connection.OnPageEnd", ex);
        }
    }

    /** List of errors thrown by this class. */
    public ErrorsClass Errors = new ErrorsClass();

    /** Attributes of this connection */
    public AspCollection Attributes = new AspCollection();

    /** Timeout for commands */
    public int CommandTimeout;

    /** Current connection string */
    public String ConnectionString = null;

    /** Timeout for connections */
    public int ConnectionTimeout;

    /** Cursor location, server or client. */
    public int CursorLocation = 2;

    /** Default database. */
    public int DefaultDatabase;

    /** Isolation level. */
    public int IsolationLevel;

    /** Mode for modifying data. */
    public int Mode;

    /** Properties list. */
    public AspCollection Properties = new AspCollection();

    /** Provider information */
    public String Provider;

    /**
     * State, open or closed.
     * @return state of connection
     */
    public int State()
    {
        if (cx == null)
            return 0;
        else
            return 1;
    }

    /**
     * Obtains the ASP Version.
     * @return ASP Version, currently 2.5
     */
    public String Version()
    {
        return "2.5";
    }

    /**
     * This sub-class is used to return multiple arguments from the
     * parseConnectionInfo(String) function.
     */
    static class ConnectionInfo
    {
        /** The JDBC driver */
        String driver = null;
        /** The JDBC URL */
        String url = null;
        /** The username */
        String username = null;
        /** The password */
        String password = null;
    }

    /**
     * This function parses the given URL into parts. It supports the
     * follow formats:
     * <ul>
     * <li>jdbc:<URL> - JDBC URLs
     * <li>driver=<driver>;URL=<url>;UID=<user>;PWD=<password>
     *   <ul>
     *   <li>driver - JDBC driver class, such as org.postgresql.Driver
     *   <li>URL - JDBC URL, such as jdbc:postgres://database
     *   <li>UID - Username (optional)
     *   <li>PWD - Password (optional)
     *   </ul>
     * </ul>
     * @param url URL to parse
     * @return Connection info
     */
    private ConnectionInfo parseConnectionInfo(String url)
    {
        ConnectionInfo cinfo = new ConnectionInfo();
        if (url.startsWith("jdbc:"))
        {
            cinfo.url = url;
            return cinfo;
        }
        final Pattern dpat = new Pattern("(^|;)(driver)=([^;]+)(;|$)",
                REFlags.IGNORE_CASE);
        Matcher dmat = dpat.matcher(url);
        if (dmat.find()) cinfo.driver = dmat.group(3);
        final Pattern rpat = new Pattern("(^|;)(url)=([^;]+)(;|$)",
                REFlags.IGNORE_CASE);
        Matcher rmat = rpat.matcher(url);
        if (rmat.find()) cinfo.url = rmat.group(3);
        final Pattern upat = new Pattern("(^|;)(user|uid|user id)=([^;]+)(;|$)",
                REFlags.IGNORE_CASE);
        Matcher umat = upat.matcher(url);
        if (umat.find()) cinfo.username = umat.group(3);
        final Pattern ppat = new Pattern("(^|;)(password|pwd)=([^;]+)(;|$)",
                REFlags.IGNORE_CASE);
        Matcher pmat = ppat.matcher(url);
        if (pmat.find()) cinfo.password = pmat.group(3);

        if (DBG.isDebugEnabled())
        {
            DBG.debug("Driver: " + cinfo.driver);
            DBG.debug("URL: " + cinfo.url);
            DBG.debug("User: " + cinfo.username);
            DBG.debug("Password: " + cinfo.password);
        }
        return cinfo;
    }

    /**
     * Open a URL with username and password.
     * @param url URL to open   
     * @param username Username to use
     * @param password Password to use
     * @throws AspException on error    
     */
    public void Open(String url, String username, String password)
        throws AspException
    {
        this.ConnectionString = url;
        ConnectionInfo cinfo = parseConnectionInfo(url);
        try {
            if (cinfo.driver != null)
            try {
                Class.forName(cinfo.driver).newInstance();
            } catch (Exception ex) {
                throw new AspNestedException(ex);
            }
            if (username != null) cinfo.username = username;
            if (password != null) cinfo.password = password;
            /* Close any open connections */
            if (cx != null) Close();
            if (DBG.isDebugEnabled())
            {
                DBG.debug("cinfo.url = " + cinfo.url);
                DBG.debug("cinfo.username = " + cinfo.username);
                DBG.debug("cinfo.password = " + cinfo.password);
            }
            if (cinfo.username == null && cinfo.password == null)
            {
                cx = java.sql.DriverManager.getConnection(cinfo.url);
            } else {
                cx = java.sql.DriverManager.getConnection(cinfo.url,
                        cinfo.username, cinfo.password);
            }
        } catch (SQLException ex) {
            throw processException(ex);
        }
    }

    /**
     * Open a database connection, giving only an URL.
     * @param url URL of database to open
     * @throws AspException on error
     */
    public void Open(String url) throws AspException
    {
        Open(url, null, null);
    }

    /**
     * Open a database using the ConnectionString parameter as the URL.
     * @throws AspException on error
     */
    public void Open() throws AspException
    {
        Open(ConnectionString, null, null);
    }

    /**
     * Begin a transaction. Returns the transaction level for nested
     * transactions (not implemented).
     * @return transaction level (always 1 for this implementation)
     * @throws AspException on error, throws exception if called while
     *  already in a transaction.
     */
    public int BeginTrans() throws AspException
    {
        try {
            if (inTransaction)
            {
                throw new AspNotImplementedException("Nested transactions not supported");
            }
            cx.setAutoCommit(false);
            inTransaction = true;
        } catch (SQLException ex) {
            throw processException(ex);
        }
        return 1;
    }

    /**
     * Commits the current, active transaction, throws an exception if called
     * outside of a transaction.
     * @throws AspException on error
     */
    public void CommitTrans() throws AspException
    {
        try {
            if (!inTransaction)
            {
                throw new AspNotImplementedException("Commit called outside of transaction.");
            }
            cx.commit();
        } catch (SQLException ex) {
            throw processException(ex);
        } finally {
            try {
                cx.setAutoCommit(true);
            } catch (SQLException ex)
            {
                throw processException(ex);
            }
            inTransaction = false;
        }
    }

    /**
     * Rolls back the current, active transaction, throws an exception if called
     * outside of a transaction.
     * @throws AspException on error
     */
    public void RollbackTrans() throws AspException
    {
        try {
            if (!inTransaction)
            {
                throw new AspNotImplementedException("Commit called outside of transaction.");
            }
            cx.rollback();
        } catch (SQLException ex) {
            throw processException(ex);
        } finally {
            try {
                cx.setAutoCommit(false);
            } catch (SQLException ex) {
                throw processException(ex);
            }
            
            inTransaction = false;
        }
    }

    /**
     * Close the current, open database connection.
     * @throws AspException on SQL exception
     */
    public void Close() throws AspException
    {
        if (cx == null) return;
        try {
            cx.close();
        } catch (SQLException ex) {
            throw processException(ex);
        } finally {
            cx = null;
        }
    }

    /**
     * Execute the given SQL statement.
     * @param sql SQL expression to execute
     * @return Record of results
     * @throws AspException on SQL exception
     */
    public RecordSet Execute(String sql) throws AspException
    {
        return Execute(sql, null);
    }

    /**
     * Executes the given SQL statement, returning a result value.
     * @param sql SQL expression to execute
     * @param recordsEffected by-reference variable which will contain the
     * number of records modified.
     * @return Record of results
     * @throws AspException on SQL exception
     */
    public RecordSet Execute(String sql, ByRefValue recordsAffected)
        throws AspException
    {
        return Execute(sql, recordsAffected, 0);
    }

    /**
     * Executes the given SQL statement, returning a result value.
     * @param sql SQL expression to execute
     * @param recordsEffected by-reference variable which will contain the
     * number of records modified.
     * @param flags ADODB flags
     * @return Record of results
     * @throws AspException on SQL exception
     */
    public RecordSet Execute(String sql, ByRefValue recordsAffected, int flags)
        throws AspException
    {
        RecordSet rc = new RecordSet();
        Command cmd = new Command();
        cmd.CommandText = sql;
        cmd.ActiveConnection(this);

        int res = rc.Open(cmd, null, 0, 0);
        if ((recordsAffected != null) && (res != -1))
        {
            Integer ra = new Integer(res);
            recordsAffected.setValue(ra);
        }

        return rc;
    }

    /**
     * Process this exception, add it to the error list and re-throw it
     * as an AspException. Package scope on purpose.
     * @param ex Exception to process
     */
    AspException processException(Throwable ex)
    {
        return processException(ex, 0x80004005);
    }

    /**
     * Process this exception, add it to the error list and re-throw it
     * as an AspException. Package scope on purpose.
     * @param ex Exception to process
     * @param errorCode error code
     */
    AspException processException(Throwable ex, int errorCode)
    {
        AspException aspEx;
        if (ex instanceof AspException)
        {
            aspEx = (AspException)ex;
        } else {
            aspEx = new AspNestedException(ex, errorCode);
        }
        Errors.AddError(aspEx);
        return aspEx;
    }

    /**
     * This inner class contains the list of SQL errors.
     */
    static public class ErrorsClass implements SimpleMap
    {
        /** Debugging context */
        static Category DBG = Category.getInstance(ErrorsClass.class);

        /** Internal list of SQL errors. */
        protected Vector _errorlist = new Vector();

        /**
         * Returns number of errors in list.
         * @return number of SQL errors
         */
        public int Count()
        {
            return _errorlist.size();
        }

        /**
         * Clears the SQL errors
         */
        public void Clear()
        {
            _errorlist = new Vector();
        }
    
        /**
         * Internal function to add an error to the list
         * @param ex Exception to add to list
         */
        protected void AddError(AspException ex)
        {
            if (DBG.isDebugEnabled())
                DBG.debug("AddError: " + ex);
            _errorlist.add(ex);
        }

        /**
         * Obtain the error at the specified index.
         * @param obj Object to be coerced into integer
         * @return Error at the specified index.
         * @throws AspException on error
         */
        public Object get(Object obj) throws AspException
        {
            int i = Types.coerceToInteger(obj).intValue();

            return _errorlist.get(i);
        }

        /**
         * The Errors list is read-only, so this function will throw
         * an error.
         * @param obj Index
         * @param value Value
         * @throws AspException always throws read only exception
         */
        public void put(Object obj, Object value) throws AspException
        {
            throw new AspReadOnlyException("Errors");
        }

        /**
         * Function to obtain keys in this collection. This function should
         * not be called.
         * @return enumeration of keys
         */
        public Enumeration getKeys()
        {
            return null;
        }
    }
}

