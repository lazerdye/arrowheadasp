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

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 * Session class, implements the ASP "Session" object.
 * <p>Status:
 * <ul>
 * <li><b>CodePage</b> - Not implemented
 * <li><b>LCID</b> - Not implemented
 * <li><b>SessionID</b> - Not implemented
 * <li><b>Timeout</b> - Not implemented
 * <li><b>Contents</b> - Implemented <b>TODO</b> Have to implement ASP syntax
 * <li><b>Contents.Remove</b> - Not implemented
 * <li><b>Contents.RemoveAll</b> - Not implemented
 * <li><b>StaticObjects</b> - Not implemented
 * <li><b>Abandon</b> - Implmented
 * </ul>
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class Session implements SimpleMap
{
    /** Debugging category */
    private final static Logger DBG = Logger.getLogger(Session.class);

    /** HTTP Session */
    HttpSession httpSession;

    /** HTTP Request object */
    HttpServletRequest request;

    /** The actual collection contents */
    AspCollection collection = null;

    /** Local context, Session_OnStart and Session_OnEnd are executed under
        this context */
    AspContext localContext;

    /** Reference to the collection object */
    public SimpleReference Contents = new ContentsReference();

    /** Session timeout value */
    public int timeout = 60;

    /** Session LCID value */
    public int LCID = 0;

    /**
     * Constructor.
     * @param request HTTP Request object
     * @param localContext Local context object, used in call to Session_On*
     */
    public Session(HttpServletRequest request, AspContext localContext)
    {
        this.request = request;
        this.localContext = localContext;
    }

    /**
     * Special, protected constructor for use with Session_OnStart and
     * Session_OnEnd.
     * @param session pre-existing session
     */
    protected Session(HttpSession session, AspCollection aspCollection)
    {
        this.httpSession = session;
        this.collection = aspCollection;
        this.request = null;
        this.localContext = null;
    }

    /**
     * Obtains a value in the session object, implementing the SimpleMap.get
     * function. Key will be converted to string and is case insensitive.
     * @param key Key of object to obtain, will be converted to string.
     * @return value of object with the given key.
     * @throws AspException on error
     * @see SimpleMap#get
     */
    public Object get(Object key) throws AspException
    {
        if (collection == null) getSession();
        return collection.get(key);
    }

    /**
     * Stores a value in the session. Key will be converted to string
     * and is case insensitive.
     * @param key Key of object to store, will be converted to string.
     * @param value Value to store with the given key.
     * @throws AspException on error
     * @see SimpleMap#put
     */
    public void put(Object key, Object value) throws AspException
    {
        if (collection == null) getSession();
        collection.put(key, value);
    }

    /**
     * Obtains the keys of the map interface.
     * @return enumeration of all keys stores in this session.
     * @throws AspException if an error occurs
     * @see SimpleMap#getKeys
     */
    public Enumeration getKeys() throws AspException
    {
        if (collection == null) getSession();
        return collection.getKeys();
    }

    /**
     * ASP function, abandons the current session.
     */
    public void abandon() throws AspException
    {
        if (httpSession == null) getSession();
        httpSession.invalidate();
    }

    /**
     * ASP function, obtains the session identifier
     * @return string representation of the session identifier.
     */
    public String SessionID() throws AspException
    {
        if (httpSession == null) getSession();
        return httpSession.getId();
    }

    /**
     * Internal function to obtain the session if it already exists.
     */
    protected void getSessionIfExists() throws AspException
    {
        httpSession = request.getSession(false);
        if (httpSession != null) getSession();
    }

    /**
     * Internal function which obtains the current, active session.
     */
    private void getSession() throws AspException
    {
        httpSession = request.getSession(true);
        if (DBG.isDebugEnabled())
        {
            if (httpSession.isNew()) DBG.debug("New session");
        } 
        collection = AspSessionHandler.getContents(httpSession);
        if (collection == null) throw new AspException("Internal Error, did you add the listener tags to your web.xml file?");
    }

    /**
     * This class handles the Contents variable, which is not defined
     * until it is first accessed.
     */
    class ContentsReference implements SimpleReference
    {
        /**
         * Obtain the value this reference represents
         */
        public Object getValue() throws AspException
        {
            if (collection == null) getSession();
            return collection;
        }

        /**
         * Sets the value this reference represents.
         */
        public void setValue(Object obj) throws AspException
        {
            throw new AspReadOnlyException("Contents variable cannot be set");
        }
    }
}
