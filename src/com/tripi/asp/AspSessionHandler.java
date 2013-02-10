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

import org.apache.log4j.Category;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionBindingEvent;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import java.io.IOException;
import java.io.Serializable;

/**
 * This class handles synchronization between ASP Sessions and Servlet
 * sessions for ASP to JSP interaction.
 *
 * @author Terence Haddock
 */
public class AspSessionHandler implements HttpSessionListener
{
    /**
     * Debugging class
     */
    private Category DBG = Category.getInstance(AspSessionHandler.class);


    /**
     * Constructor.
     */
    public AspSessionHandler() {};

    /**
     * Global Session to AspCollection mapping.
     */
    private static HashMap sessionToAspCollection = new HashMap();

    /**
     * Session initialized.
     * Note: I am assuming that this function is called in a thread-safe
     * manner within a single session.
     * @param e Event describing the initiazing of the session.
     */
    public void sessionCreated(HttpSessionEvent e)
    {
        HttpSession session = e.getSession();
        if (DBG.isDebugEnabled())
        {
            DBG.debug("sessionCreated: " + e);
            DBG.debug("Session: " + session.getId());
        }
        
        /* Create a hashtable to store the mappings to handle case changes */
        HashMap caseMapping = new HashMap();

        /* Store the case mappings into the session to handle passivation/activation */
        session.setAttribute("ArrowHeadASP_CaseMappings", caseMapping);

        /* Create the ArrowHead_Contents variable which will contain
           an ASP-accessible mirror of the session contents */
        AspCollection contents = new AspSynchronizedCollection(session, caseMapping);

        /* Store this mapping to the hash map */
        synchronized(sessionToAspCollection) {
            sessionToAspCollection.put(session.getId(), contents);
        }
            

        if (DBG.isDebugEnabled()) DBG.debug("Before SESSION_ONSTART");

        try {
            GlobalScope.callSessionOnStart(session, contents);
        } catch (Exception ex) {
            DBG.error("Error calling SESSION_ONSTART", ex);
        }

        if (DBG.isDebugEnabled()) DBG.debug("After SESSION_ONSTART");
    }

    /**
     * Session destroyed.
     * @param e Event describing the destruction of the session.
     */
    public void sessionDestroyed(HttpSessionEvent e)
    {
        HttpSession session = e.getSession();
        if (DBG.isDebugEnabled())
        {
            DBG.debug("sessionDestroyed: " + e);
            DBG.debug("Session: " + session.getId());
        }

        if (DBG.isDebugEnabled()) DBG.debug("Before SESSION_ONEND");

        AspSynchronizedCollection contents;
        synchronized(sessionToAspCollection)
        {
            contents = (AspSynchronizedCollection)sessionToAspCollection.get(session.getId());
        }
        if (contents != null) {
            contents.disconnectFromSession();

            try {
                GlobalScope.callSessionOnEnd(session, contents);
            } catch (Exception ex) {
                DBG.error("Error calling SESSION_ONEND", ex);
            }
        }

        if (DBG.isDebugEnabled()) DBG.debug("After SESSION_ONEND");
        synchronized(sessionToAspCollection)
        {
            sessionToAspCollection.remove(session.getId());
        }
    }

    /**
     * This function obtains the case mappings for the specified session.
     */
    static Map getCaseMappings(HttpSession session)
    {
        Map caseMapping;
        synchronized(sessionToAspCollection) {
            caseMapping = (Map)sessionToAspCollection.get("ArrowHeadASP_CaseMappings");
            if (caseMapping == null) {
                caseMapping = new HashMap();
                sessionToAspCollection.put("ArrowHeadASP_CaseMappings", caseMapping);
            }
        }
        return caseMapping;
    }

    /**
     * This function obtains the ASP Contents for the specified session.
     */
    static AspCollection getContents(HttpSession session)
    {
        AspCollection collection;
        synchronized(sessionToAspCollection) {
            collection = (AspCollection)sessionToAspCollection.get(session.getId());
            if (collection == null)
            {
                Map caseMapping = getCaseMappings(session);
                collection = new AspSynchronizedCollection(session, caseMapping);
                sessionToAspCollection.put(session.getId(), collection);
            }
        }
        return collection;
    }

    /** This class helps synchronize Asp Session data with Servlet Session
     * data.
     */
    public static class AspSynchronizedCollection extends AspCollection
        implements SimpleMap
    {
        /**
         * Debugging class
         */
        private static Category DBG = Category.getInstance(AspSynchronizedCollection.class);

        /* The session this collection is attached to */
        HttpSession session;

        /* This variable is used to override the session synchronization */
        boolean connected = true;

        /* This hashtable is used to keep track of variable case */
        Map caseMapping = null;

        /**
         * Constructor.
         * @param session Session we are synchronized with.
         */
        AspSynchronizedCollection(HttpSession session, Map caseMapping)
        {
            this.session = session;
            this.caseMapping = caseMapping;
            /* Load all of the stored session data */
            Set keySet = caseMapping.keySet();
            for (Iterator i = keySet.iterator(); i.hasNext();)
            {
                String keyLower = (String)i.next();
                String key = (String)caseMapping.get(keyLower);
                Object value = session.getAttribute(key);
                try {
                    if (value != null) {
                        internalPut(key, Types.coerceToNode(value));
                    }
                } catch (AspException ex) {
                    DBG.error("Error during session synchronization", ex);
                }
            }
        }

        /**
         * Obtains the value of data contained within the application
         * object.
         * @param obj Value to obtain.
         * @return value of key obtained
         * @throws AspException if an error occurs, most likely 
         *  could not coerce value to string.
         * @see SimpleMap#get(Object)
         */
        public synchronized Object get(Object key)
            throws AspException
        {
            /* If we are not connected, get it from the collection */
            if (!connected) {
                return super.get(key);
            }
            /* If we are connected, first try the mapping */
            String strKey = Types.coerceToString(key);
            String strAspKey = (String)caseMapping.get(strKey.toLowerCase());
            /* If we know the key, try the session */
            if (strAspKey != null)
            {
                Object sessionValue = session.getAttribute(strAspKey);
                if (sessionValue != null)
                {
                    /* Found the value from the session */
                    return Types.coerceToNode(sessionValue);
                } else {
                    return super.get(key);
                }
            } 
            /* We do not know about this key, let's look for it
               in the session. First come, first serve. */
            for (Enumeration e = session.getAttributeNames();e.hasMoreElements();)
            {
                String name = (String)e.nextElement();
                if (name.equalsIgnoreCase(strKey)) {
                    /* Found it */
                    caseMapping.put(name.toLowerCase(), name);
                    return Types.coerceToNode(session.getAttribute(name));
                }
            }
            /* This value is totally unknown. */
            return Constants.undefinedValueNode;
        }

        /**
         * Internal function to store a value without touching the HttpSession
         * objects.
         * @param key Key under which to store this item
         * @param obj Object to store
         * @throws AspException if an error occurs
         */
        public synchronized void internalPut(Object key, Object obj)
            throws AspException
        {
            String strKey = Types.coerceToString(key);
            caseMapping.put(strKey.toLowerCase(), strKey);
            super.put(strKey, obj);
        }

        /**
         * The put method adds an item to this collection
         * @param key Key under which to store this item
         * @param obj Object to store
         * @throws AspException if an error occurs
         */
        public synchronized void put(Object key, Object obj) throws AspException
        {
            /* Store the value internally */
            internalPut(key, obj);
            if (connected)
            {
                /* Place the object into the session */
                String str = Types.coerceToString(key);
                /* De-reference wrapped Java objects */
                Object value = obj;
                while (value instanceof JavaObjectNode)
                    value = ((JavaObjectNode)value).getSubObject();
                /* Only store the value if its serializable */
                /* Map and List objects are not stored, because they can
                   contain non-serializable items */
                if (value instanceof Serializable &&
                    !(value instanceof ArrayNode) &&
                    !(value instanceof java.util.Map) &&
                    !(value instanceof java.util.List))
                {
                    if (DBG.isDebugEnabled())
                        DBG.debug("Storing value " + str + " to session");
                    /* Store the value into the session */
                    session.setAttribute(str, value);
                } else {
                    if (DBG.isDebugEnabled())
                        DBG.debug("Removing value " + str + " from session");
                    session.removeAttribute(str);
                }
            }
        }

        /**
         * Internal function to remove the element without touching the
         * HTTPSession.
         * @param key Key to remove
         * @throws AspException on error
         */
        public synchronized void internalRemove(Object key) throws AspException
        {
            String strKey = Types.coerceToString(key);
            caseMapping.remove(strKey.toLowerCase());
            super.remove(strKey);
        }
    
        /**
         * Removes the element from this collection.
         * @param key Key to remove
         * @throws AspException on error
         */
        public synchronized void remove(Object key) throws AspException
        {
            internalRemove(key);
            if (connected)
            {
                /* Get the key value */
                String str = Types.coerceToString(key);
                /* Remove all of the matching values from the session. */
                for (Enumeration e = session.getAttributeNames();e.hasMoreElements();)
                {
                    String name = (String)e.nextElement();
                    if (name.equalsIgnoreCase(str)) {
                        session.removeAttribute(name);
                    }
                }
            }
        }
    
        /**
         * Removes all of the elements from this collection.
         * @throws AspException on error
         */
        public synchronized void removeAll() throws AspException
        {
            super.removeAll();
            if (connected)
            {
                /* Remove all values from the session. */
                for (Enumeration e = session.getAttributeNames();e.hasMoreElements();)
                {
                    String name = (String)e.nextElement();
                    session.removeAttribute(name);
                }
            }
        }

        /**
         * Disconnect from the session. Used when sessions are closed so
         * that setting/removing values to the session does not cause an
         * invalidated session error.
         */
        public void disconnectFromSession()
        {
            connected = false;
        }
    }
}
