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

import org.apache.log4j.Logger;

/**
 * Application is a class which holds application-specific data.
 * This class corresponds to the global Application object
 * available to VBScript applications. Implementation state:
 * <ul>
 * <li><b>Lock</b> - Fully implemented.
 * <li><b>Unlock</b> - Fully implemented.
 * <li><b>Get/Set</b> - Fully implemented.
 * </ul>
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class Application implements SimpleMap
{
    /**
     * Debugging output class.
     */
    Logger DBG = Logger.getLogger(Application.class);

    /**
     * The thread which currently has the lock, null means no thread
     * has the lock.
     */
    private Thread lock = null;

    /**
     * The contents of the variables stored in this application.
     */
    public AspCollection Contents = new AspCollection();

    /**
     * Obtains the value of data contained within the application
     * object.
     * @param obj Value to obtain.
     * @return value of key obtained
     * @throws AspException if an error occurs, most likely 
     *  could not coerce value to string.
     * @see SimpleMap#get(Object)
     */
    public Object get(Object obj)
        throws AspException
    {
        return Contents.get(obj);
    }

    /**
     * Stores an objects into the application object.
     * @param key Key to store.
     * @param value Value to store.
     * @throws AspException if an error occurs, most likely 
     *  could not coerce value to string.
     * @see SimpleMap#put(Object,Object)
     */
    public void put(Object key, Object value)
        throws AspException
    {
        Contents.put(key, value);
    }

    /**
     * Obtains the list of keys stored in this object. Used for 
     * foreach statements.
     * @return list of keys stores in this application object.
     * @throws AspException if an error occurs
     * @see SimpleMap#getKeys()
     */
    public java.util.Enumeration getKeys()
        throws AspException
    {
        return Contents.getKeys();
    }

    /**
     * ASP function Lock, locks the application object.
     * @throws AspException on error
     */
    public synchronized void Lock() throws AspException
    {
        try {
            while (lock != null)
            {
                wait();
            }
            lock = Thread.currentThread();
        } catch (InterruptedException ex)
        {
            DBG.error(ex);
            throw new AspNestedException(ex);
        }
    }

    /**
     * ASP function Unlock, unlocks the application object.
     * @throws AspException on error
     */
    public synchronized void Unlock() throws AspException
    {
        if (lock == null || lock != Thread.currentThread())
        {
            throw new AspException("Application.Unlock() called on an " +
                "unlocked application object");
        }
        lock = null;
        notify();
    }

    /**
     * Internal function used to unlock the current application object
     * only if the specified thread has a lock on it.
     * @param thread Unlock if this thread has the lock
     */
    synchronized void unlockIfThreadHasLock(Thread thread)
    {
        if (lock == thread) {
            lock = null;
            notify();
        }
    }
}

