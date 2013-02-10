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

import com.tripi.asp.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import java.util.Enumeration;
import java.util.Hashtable;

import java.io.*;

import org.apache.log4j.Category;

/**
 * This class implements the ADODB.RecordSet object.
 * RecordSet Implementation status:
 * <ul>
 * <li>AbsolutePage - Implemented <b>TODO</b> - locking/cursor type
 * <li>AbsolutePosition - Implemented <b>TODO</b> - locking/cursor type
 * <li>ActiveCommand - Fully implemented
 * <li>ActiveConnection - Implemented
 * <li>BOF - Implemented
 * <li>EOF - Implemented
 * <li>Bookmark - <b>TODO</b> Not implemented
 * <li>CacheSize - <b>TODO</b> Not implemented
 * <li>CursorLocation - <b>TODO</b> Not implemented
 * <li>CursorType - <b>TODO</b> Not implemented
 * <li>Filter - <b>TODO</b> Not implemented
 * <li>Index - <b>TODO</b> Not implemented
 * <li>LockType - <b>TODO</b> Not implemented
 * <li>MarshalOptions - <b>TODO</b> Not implemented
 * <li>MaxRecords - <b>TODO</b> Not implemented
 * <li>PageCount - Implemented <b>TODO</b> - locking / cursor type
 * <li>PageSize - <b>TODO</b> Not implemented
 * <li>RecordCount - Implemented <b>TODO</b> - locking / cursor type
 * <li>Sort - <B>TODO</b> Not implemented
 * <li>Source - Implemented
 * <li>State - Implemented
 * <li>Status - Implemented
 * <li>StayInSync - <b>TODO</b> Not implemented
 * <li>AddNew - Implemented
 * <li>Cancel - Not implemented, not usable in web-based ADODB
 * <li>CancelBatch - <b>TODO</b> Not implemented
 * <li>CancelUpdate - Implemented
 * <li>Clone - <b>TODO</b> Not implemented
 * <li>Close - Implemented
 * <li>CompareBookmarks - <B>TODO</b> Not implemented
 * <li>Delete - <B>TODO</b> Not implemented
 * <li>Find - <B>TODO</b> Not implemented
 * <li>GetRows - <B>TODO</b> Not implemented
 * <li>GetString - <b>TODO</b> Not implemented
 * <li>Move - Implemented
 * <li>MoveFirst - Implemented
 * <li>MoveLast - Implemented
 * <li>MoveNext - Implemented
 * <li>MovePrevious - Implemented
 * <li>NextRecordSet - <B>TODO</b> Not implemented
 * <li>Open - Implemented
 * <li>Requery - Implemented
 * <li>Resync - <B>TODO</b> Not implemented
 * <li>Save - <B>TODO</b> Not implemented
 * <li>Seek - <B>TODO</b> Not implemented
 * <li>Supports - <B>TODO</b> Not implemented
 * <li>Update - Implemented, <B>TODO</b> Array-updating not implemented
 * <li>UpdateBatch - <B>TODO</b> Not implemented
 * </ul>
 * ADODB.Field implementation status:
 * <ul>
 * <li>ActualSize - <b>TODO</b> Not implemented
 * <li>Attributes - <b>TODO</b> Not implemented
 * <li>DefinedSize - Implemented
 * <li>Name - Implemented
 * <li>NumericScale - Implemented
 * <li>OriginalValue - <B>TODO</b> Not implemented
 * <li>Precision - Implemented
 * <li>Status - <b>TODO</b> Not implemented
 * <li>Type - <b>TODO</b> Not implemented
 * <li>UnderlyingValue - <b>TODO</b> Not implemented
 * <li>Value - Implemented
 * <li>AppendChunk - <b>TODO</b> Not implemented
 * <li>GetChunk - <b>TODO</b> Not implemented
 * <li>Append - <b>TODO</b> Not implemented
 * </ul>
 * @author Terence Haddock
 */
public class RecordSet implements SimpleMap
{
    /** Debugging category */
    static Category DBG = Category.getInstance(RecordSet.class.getName());

    /** Active record set, null if no active RS */
    java.sql.ResultSet rs;

    /** Active statement, null if no active statement */
    java.sql.Statement stmt;

    /** Meta data for the query */
    ResultSetMetaData rsmd = null;

    /** Hashtable mapping column names to index. */
    Hashtable columnNames;

    /** This variable is marked true when there are no results in the query */
    boolean    hasNoElements = false;

    /** This variable is marked true when the query is currently on the insert row */
    boolean     isOnInsertRow = false;

    /** This flag is true if we opened the connection through RS.Open */
    boolean     ownConnection = false;

    /** ASP - Accessible fields array */
    public FieldsMap Fields = new FieldsMap();

    /** ASP - Accessible EOF marker */
    public boolean EOF;

    /** ASP - Accessible BOF marker */
    public boolean BOF = true;

    /** Where is the cursor, server or client? */
    public int CursorLocation = 0;

    /** Type of cursor */
    public int CursorType = 0;

    /** Size for paging */
    public int PageSize = 10;

    /** Size for caching */
    public int CacheSize = 10;

    /** Internal variable used to re-query the SQL */
    protected String _ActiveSQL = null;

    /** Internal variable to store the active connection */
    protected Connection _ActiveConnection = null;

    /** Internal variable to store the active command */
    protected Command _ActiveCommand = null;

    /** Constructor with no parameters. */
    public RecordSet()
    {
        this.rs = null;
    }

    /**
     * ASP-Accessible function
     * @return the active connection
     */
    public String ActiveConnection()
    {
        return _ActiveConnection.ConnectionString;
    }

    /**
     * ASP-Accessible function
     * @return the active command
     */
    public Object ActiveCommand()
    {
        if (_ActiveCommand == null)
            return "";
        return _ActiveCommand;
    }

    /**
     * Internal function to set the active connection.
     * @param con New connection
     */
    protected void setConnection(Connection con)
    {
        this._ActiveConnection = con;
    }

    /**
     * Internal function to set the active command.
     * @param com New command
     */
    protected void setCommand(Command com)
    {
        this._ActiveCommand = com;
    }

    /**
     * Internal function to set the active statement.
     * @param stmt Active statement
     */
    protected void setStatement(java.sql.Statement stmt)
    {
        this.stmt = stmt;
    }

    /**
     * Internal function to set the active result set 
     * @param rs active result set
     */
    protected void setResultSet(java.sql.ResultSet rs) throws AspException
    {
        this.rs = rs;
        BOF = true;
        try {
            if (rs == null)
            {
                EOF = true;
            } else {
                rsmd = rs.getMetaData();
                getColumnNames();
                EOF = !rs.next();
                if (!EOF) BOF = false;
                if (DBG.isDebugEnabled())
                    DBG.debug("EOF: " + EOF);
            }
            hasNoElements = EOF;
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * Internal function which obtains the column names from the result set.
     */
    private void getColumnNames() throws AspException
    {
        try {
            int i;
            columnNames = new Hashtable();
            if (DBG.isDebugEnabled()) DBG.debug("Number of columns: " +
                rsmd.getColumnCount());
            for (i = 1; i <= rsmd.getColumnCount(); i++)
            {
                String name = rsmd.getColumnName(i);
                if (DBG.isDebugEnabled())
                    DBG.debug("Column name: " + name);
                columnNames.put(name.toLowerCase(), new Integer(i));
            }
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to move to the next row.
     * @throws AspException on SQL error
     */
    public void MoveNext() throws AspException
    {
        try {
            EOF = !rs.next();
            if (!EOF) BOF = false;
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to move to the first row.
     * @throws AspException on SQL error
     */
    public void MoveFirst() throws AspException
    {
        try {
            rs.first();
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to move back one row.
     * @throws AspException on SQL error
     */
    public void MovePrevious() throws AspException
    {
        try {
            rs.previous();
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to move to the last row.
     * @throws AspException on SQL error
     */
    public void MoveLast() throws AspException
    {
        try {
            rs.last();
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to move by the specified number of rows.
     * @param numRows number of rows to move by, can be negative to move
     * backwards.
     * @throws AspException on SQL error
     */
    public void Move(int numRows) throws AspException
    {
        try {
            boolean result = rs.relative(numRows);
            if (!result)
            {
                if (numRows>0) EOF = true;
                    else BOF = true;
            }
            if (!EOF) BOF = false;
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * Open a connection, based on a string DSN.
     * @param sql SQL statement to use
     * @param DSN DSN to use to connect
     * @throws AspException on SQL error
     * @throws AspException on ASP error
     */
    public boolean Open(String sql, String DSN)
        throws AspException
    {
        return Open(sql, DSN, 0, 0);
    }

    /**
     * Open a connection, based on a string DSN, with a specified cursor type.
     * @param sql SQL statement to use
     * @param DSN DSN to use to connect
     * @param type Cursor type
     * @throws AspException on ASP error
     */
    public boolean Open(String sql, String DSN, int type)
        throws AspException
    {
        return Open(sql, DSN, type, 0);
    }

    /**
     * Open a connection, based on a string DSN, with a specified cursor type.
     * and a specified lock type.
     * @param sql SQL statement to use
     * @param DSN DSN to use to connect
     * @param type Cursor type
     * @param lock Lock type
     * @throws AspException on ASP error
     */
    public boolean Open(String sql, String DSN, int type, int lock)
        throws AspException
    {
        ownConnection = true;
        Connection con = new Connection();
        con.Open(DSN);
        return Open(sql, con, type, lock);
    }

    /**
     * Open a result set, based on the specified connection object.
     * @param sql SQL statement to use
     * @param con Connection object
     * @throws AspException on SQL error
     */
    public boolean Open(String sql, Connection con)
        throws AspException
    {
        return Open(sql, con, 0, 0);
    }

    /**
     * Open a result set, based on the specified connection object,
     * with a specified lock type.
     * @param sql SQL statement to use
     * @param con Connection object
     * @param type cursor type
     * @throws AspException on SQL error
     */
    public boolean Open(String sql, Connection con, int type)
        throws AspException
    {
        return Open(sql, con, type, 0);
    }

    /**
     * Open a result set, based on the specified connection object,
     * with a specified cursor type, and specified lock semantics.
     * @param sql SQL statement to use
     * @param con Connection object
     * @param type cursor type
     * @param lock lock type
     * @throws AspException on SQL error
     */
    public boolean Open(String sql, Connection con, int type, int lock)
        throws AspException
    {
        return Open(sql, con, type, lock, 0);
    }

    /**
     * Open a result set, based on the specified connection object,
     * with a specified cursor type, and specified lock semantics.
     * @param sql SQL statement to use
     * @param con Connection object
     * @param type cursor type
     * @param lock lock type
     * @param flag some sort of flag
     * @throws AspException on SQL error
     */
    public boolean Open(String sql, Connection con, int type, int lock, int flag)
        throws AspException
    {
        try {
            java.sql.Statement stmt = con.cx.createStatement(
                java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                java.sql.ResultSet.CONCUR_READ_ONLY);
            _ActiveSQL = sql;
            boolean result = stmt.execute(sql + ";");
            setConnection(con);
            setStatement(stmt);
            if (result)
            {
                setResultSet(stmt.getResultSet());
            } else {
                setResultSet(null);
            }
            return true;
        } catch (SQLException ex) {
            throw con.processException(ex);
        }
    }

    /**
     * Open a result set, based on the specified command object.
     * @param sql SQL statement to use
     * @param command Command object
     * @throws AspException on SQL error
     */
    public int Open(Command command) throws AspException
    {
        return Open(command, null, 0, 0);
    }

    /**
     * Open a result set, based on the specified command object.
     * <B>TODO</b> Figure out these parameters.
     * @param sql SQL statement to use
     * @param command Command object
     * @param a Object A
     * @param b Cursor type
     * @param c Lock type
     * @throws AspException on SQL error
     */
    public int Open(Command command, Object a, int b, int c)
        throws AspException
    {
        return Open(command, a, b, c, 0);
    }

    /**
     * Open a result set, based on the specified command object.
     * <B>TODO</b> Figure out these parameters.
     * @param sql SQL statement to use
     * @param command Command object
     * @param a Object A
     * @param b Cursor type
     * @param c Lock type
     * @param d More flags
     * @throws AspException on SQL error
     */
    public int Open(Command command, Object a, int b, int c, int d)
        throws AspException
    {
        try {
            if (DBG.isDebugEnabled()) {
                DBG.debug("Command.CommandText: " +
                    command.CommandText);
                DBG.debug("A = " + a);
                DBG.debug("B = " + b);
                DBG.debug("C = " + c);
                DBG.debug("D = " + d);
            }
            Connection con = command.ActiveConnection();
            setConnection(con);
            String sql = command.CommandText;
            _ActiveSQL = sql;
            java.sql.Statement stmt = con.cx.createStatement(
                java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                java.sql.ResultSet.CONCUR_READ_ONLY);
            boolean result = stmt.execute(sql + ";");
            setCommand(command);
            setStatement(stmt);
            if (result)
            {
                setResultSet(stmt.getResultSet());
            } else {
                setResultSet(null);
            }
            return stmt.getUpdateCount();
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to requery the database.
     * @throws AspException on SQL error
     */
    public void Requery() throws AspException
    {
        Open(_ActiveSQL, _ActiveConnection, 0, 0);
    }

    /**
     * ASP-Accessible function to add a new row.
     * @throws AspException on SQL error
     */
    public void AddNew() throws AspException
    {
        try {
            rs.moveToInsertRow();
            isOnInsertRow = true;
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to add a new row with predefined values.
     * @param fields array of field names
     * @param values arrow of column values
     * @throws AspException on SQL error
     */
    public void AddNew(Object fields[], Object values[]) throws AspException
    {
        if (fields.length != values.length)
        {
            throw new AspException("Parameters to AddNew() should be the " +
                "same length");
        }
        AddNew();
        for (int i = 0; i < fields.length; i++)
        {
            if (DBG.isDebugEnabled())
            {
                DBG.debug("Field[" + i + "] = " + fields[i]);
                DBG.debug("Value[" + i + "] = " + values[i]);
            }
            Fields.put(fields[i], values[i]);
        }
        Update();
    }

    /**
     * ASP-Accessible function to return the cursor position.
     * @return cursor position
     * @throws AspException on SQL error
     */
    public int AbsolutePosition() throws AspException
    {
        try {
            int pos = rs.getRow();
            if (pos == 0) return -1;
            return pos;
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to set the cursor position.
     * @param pos new cursor position
     * @throws AspException on SQL error
     */
    public void AbsolutePosition(int pos) throws AspException
    {
        try {
            rs.absolute(pos);
            if (hasNoElements)
            {
                EOF = true;
            } else {
                EOF = rs.isAfterLast();
            }
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to return cursor page.
     * @return cursor page
     * @throws AspException on SQL error
     */
    public int AbsolutePage() throws AspException
    {
        int row = AbsolutePosition();
        if (row <= 0) return -1;
        return ((row - 1) / PageSize) + 1;
    }

    /**
     * ASP-Accessible function to obtain the number of pages in the query
     * @return number of pages in the query 
     * @throws AspException on SQL error
     */
    public int PageCount() throws AspException
    {
        int count = RecordCount();
        return ((count - 1) / PageSize) + 1;
    }

    /**
     * ASP-Accessible function to obtain the number of records in the query.
     * @return number of records
     * @throws AspException on SQL error
     */
    public int RecordCount() throws AspException
    {
        try {
            int pos, last;
    
            if (hasNoElements) {
                return 0;
            }
    
            pos = rs.getRow();
            rs.last();
            last = rs.getRow();
            if (pos != 0) {
                /* If pos = 0, there is no rows in the result set,
                   so there is no reason to change the position. */
                DBG.debug("Setting position to: " + pos);
                rs.absolute(pos);
            }
            return last;
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to close the record set.
     * @throws AspException on SQL error
     */
    public void Close() throws AspException
    {
        try {
            rs.close();
            stmt.close();
            if (ownConnection)
            {
                _ActiveConnection.Close();
            }
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to obtain a set of rows in an array.
     * @return array of data
     */
    public ArrayNode GetRows() throws AspException
    {
        return GetRows(-1);
    }

    /**
     * ASP-Accessible function to obtain a set of rows in an array.
     * @param rows Number of rows to obtain
     * @return array of data
     */
    public ArrayNode GetRows(int rows) throws AspException
    {
        if (rows == -1)
            rows = (RecordCount() - AbsolutePosition()) + 1;
        int fieldsCount = columnNames.size();
        ArrayNode retArray = new ArrayNode(fieldsCount);
        ArrayNode fields[] = new ArrayNode[fieldsCount];
        for (int i = 0; i < fieldsCount; i++)
        {
            ArrayNode subArray = new ArrayNode(rows);
            retArray._setValue(i, subArray);
            fields[i] = subArray;
        }
        int row = 0;
        while ((!EOF)&&(row < rows))
        {
            for (Enumeration e = columnNames.keys();e.hasMoreElements();)
            {
                Object key = e.nextElement();
                Integer fieldIndex = (Integer)columnNames.get(key);
                Object value = Fields.get(key);
                value = Types.dereference(value);
                fields[fieldIndex.intValue() - 1]._setValue(row, value);
            }
            MoveNext();
            row++;
        }
        return retArray;
    }

    /**
     * ASP-Accessible function to cancel any pending updates.
     * @throws AspException on SQL error
     */
    public void CancelUpdate() throws AspException
    {
        try {
            rs.cancelRowUpdates();
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * ASP-Accessible function to update all pending changes.
     * @throws AspException on SQL error
     */
    public void Update() throws AspException
    {
        try {
            if (isOnInsertRow)
            {
                rs.insertRow();
                rs.moveToCurrentRow();
                isOnInsertRow = false;
            } else {
                rs.updateRow();
            }
        } catch (SQLException ex) {
            throw _ActiveConnection.processException(ex);
        }
    }

    /**
     * SimpleMap function to obtain the value of the record at the specified
     * index.
     * @param key key to obtain
     * @return value of the specified column
     * @throws AspException on SQL error
     */
    public Object get(Object key) throws AspException
    {
        return Fields.get(key);
    }

    /**
     * SimpleMap function to set the value of the record at the specified
     * index.
     * @param key key to use
     * @param value Value to set
     */
    public void put(Object key, Object value) throws AspException
    {
        Fields.put(key, value);
    }

    /**
     * SimpleMap function to obtain the keys in the record, effectivly the
     * column names.
     */
    public Enumeration getKeys() throws AspException
    {
        return Fields.getKeys();
    }

    /**
     * Internal function called on page end.
     */
    public void OnPageEnd(Object ignoreme)
    {
        try {
            Close();
        } catch (AspException ex)
        {
            DBG.error("Error on ADODB.ResultSet.OnPageEnd", ex);
        }
    }

    /**
     * This class implements the ADODB.Fields records.
     */
    public class Field implements SimpleReference
    {
        /**
         * This class's column index.
         */
        int columnIndex;

        /**
         * Internal constructor, column index based
         */
        protected Field(int columnIndex)
        {
            this.columnIndex = columnIndex;
        }

        /**
         * Getter, obtains the value.
         * @return value of this object
         * @throws AspException on error
         */
        public Object getValue() throws AspException
        {
            return Value();
        }

        /**
         * Setting, set the value.
         * @param newValue new value of this object
         * @throws AspException on error
         */
        public void setValue(Object newValue) throws AspException
        {
            Value(newValue);
        }

        /**
         * ASP-Accessible function to obtain the 'actual size' of a column.
         * This function always return 4, as ActualSize makes no sense
         * in Java/JDBC.
         * @return size of column
         */
        public int ActualSize() throws SQLException, AspException
        {
            String typeName = rsmd.getColumnTypeName(columnIndex);
            if (typeName.equalsIgnoreCase("varchar") || 
                typeName.equalsIgnoreCase("text")) {
                String value = (String)Value();
                return value.length();
            }

            return 4;
        }

        /**
         * ASP-Accessible function to obtain the column's display size.
         * @return display size of a column.
         * @throws SQLException on error
         */
        public int DefinedSize() throws SQLException
        {
            return rsmd.getColumnDisplaySize(columnIndex);
        }

        /**
         * ASP-Accessible function to obtain the column's name.
         * @return name of a column.
         * @throws SQLException on error
         */
        public String Name() throws SQLException
        {
            return rsmd.getColumnName(columnIndex);
        }

        /**
         * ASP-Accessible function to obtain the column's numeric scale.
         * @return numeric scale of a column.
         * @throws SQLException on error
         */
        public int NumericScale() throws SQLException
        {
            return rsmd.getScale(columnIndex);
        }

        /**
         * ASP-Accessible function to obtain the column's precision.
         * @return precision of a column.
         * @throws SQLException on error
         */
        public int Precision() throws SQLException
        {
            return rsmd.getPrecision(columnIndex);
        }

        /**
         * ASP-Accessible function to obtain the value of the column.
         * @return value of column
         * @throws SQLException on SQL error
         */
        public Object Value() throws AspException
        {
            try {
                if (DBG.isDebugEnabled()) {
                    DBG.debug("Index of column: " + columnIndex);
                    DBG.debug("Type index: " + rsmd.getColumnType(columnIndex));
                    DBG.debug("Type name: " + rsmd.getColumnTypeName(columnIndex));
                }

                String typeName = rsmd.getColumnTypeName(columnIndex);
                if (typeName.equalsIgnoreCase("uniqueidentifier")) {
                    InputStream is = rs.getBinaryStream(columnIndex);
                    if (is == null) {
                        return Constants.nullNode;
                    }
                    byte[] uniqueID = new byte[16];
                    int nread;
                    try {
                        nread = is.read(uniqueID);
                    } catch (java.io.IOException ex) {
                        throw new RuntimeException(ex.toString());
                    }
                    if (nread == -1) {
                        return Constants.nullNode;
                    } else if (nread != 16) {
                        throw new RuntimeException("Invalid unique identifier (length = " + nread + ")");
                    }
                    return new UniqueIdentifierObj(uniqueID);
                } else if (typeName.equalsIgnoreCase("timestamp")) {
                    java.util.Date timestamp = rs.getTimestamp(columnIndex);
                    if (timestamp == null) return Constants.nullNode;
                    return new AspDate(timestamp);
                } else if (typeName.equalsIgnoreCase("date")) {
                    java.util.Date date = rs.getDate(columnIndex);
                    if (date == null) return Constants.nullNode;
                    return new AspDate(date, false, true);
                } else {
                    Object ret = rs.getObject(columnIndex);
                    if (ret == null) return Constants.nullNode;
                    return ret;
                }
            } catch (SQLException ex)
            {
                throw new AspNestedException(ex);
            }
        }

        /**
         * ASP-Accessible function to set this column's value.
         * @param value Column's new value
         * @throws SQLException on SQL error
         */
        public void Value(Object value) throws AspException
        {
            try {
                if (DBG.isDebugEnabled()) {
                    DBG.debug("Index of column: " + columnIndex);
                    DBG.debug("Type index: " + rsmd.getColumnType(columnIndex));
                    DBG.debug("Type name: " + rsmd.getColumnTypeName(columnIndex));
                }
    
                String typeName = rsmd.getColumnTypeName(columnIndex);
                if (typeName.equalsIgnoreCase("uniqueidentifier")) {
                    String uidValue = value.toString();
                    rs.updateString(columnIndex, uidValue);
                } else {
                    if (value == null)
                    {
                        rs.updateNull(columnIndex);
                    } else {
                        rs.updateObject(columnIndex, value);
                    }
                }
            } catch (SQLException ex) {
                throw new AspNestedException(ex);
            }
        }
    }

    /**
     * This class represents a mapping from a field name/index to a 
     * ADODB.Fields record
     */
    public class FieldsMap implements SimpleMap
    {
        public Object get(Object key) throws AspException
        {
            while (key instanceof SimpleReference)
            {
                key = ((SimpleReference)key).getValue();
            }
            int keyIndex;
            if (key instanceof Integer)
            {
                keyIndex = ((Integer)key).intValue() + 1;
            } else {
                Integer iKey = (Integer)columnNames.get(key.toString().toLowerCase());
                if (iKey == null) 
                    throw new RuntimeException("Unknown column name: " + key.toString());
                keyIndex = iKey.intValue();
            }
            return new Field(keyIndex);
        }
    
        public void put(Object key, Object value) throws AspException
        {
            while (key instanceof SimpleReference)
            {
                key = ((SimpleReference)key).getValue();
            }
            int keyIndex;
            if (key instanceof Integer)
            {
                keyIndex = ((Integer)key).intValue() + 1;
            } else {
                Integer iKey = (Integer)columnNames.get(key.toString().toLowerCase());
                if (iKey == null) 
                    throw new RuntimeException("Unknown column name: " + key.toString());
                keyIndex = iKey.intValue();
            }
            Field field = new Field(keyIndex);
            field.setValue(value);
        }
    
        public Enumeration getKeys() throws AspException
        {
            throw new RuntimeException("Not implemented");
        }

        /**
         * ASP-Accessible function to obtain the number of fields.
         */
        public int Count()
        {
            return columnNames.size();
        }
    }

    public static class UniqueIdentifierObj 
        implements SimpleReference
    {
            byte    uniqueID[];

        public UniqueIdentifierObj(byte bytes[]) {
            this.uniqueID = bytes;
        }

        public Object getValue() {
            return toString();
        }

        public void setValue(Object obj) throws AspException
        {
            throw new AspException("Modification read-only value");
        }

        public String toString()
        {
            StringBuffer buf = new StringBuffer();
            buf.append('{');
            for (int i = 0; i < uniqueID.length; i++)
            {
                int j;
                if (i < 4)
                {
                      j = 3 - i; 
                } else if (i < 6) {
                      j = (5 - i) + 4;
                } else if (i < 8) {
                      j = (7 - i) + 6;
                } else {
                      j = i;
                }
                int ch = uniqueID[j]; 
                if (ch < 0) {
                      ch+=256;
                }
                if (ch<16) {
                      buf.append("0");
                }
                buf.append(Integer.toHexString(ch).toUpperCase());
                if (i == 3 || i == 5 || i == 7 || i == 9) {
                      buf.append('-');
                               }
            }
                    buf.append('}');
                    return buf.toString();
        }
    }
}

