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

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class implements the ASP version of 'Collections', which is a special
 * version of a HashTable. It implements common ASP collection routines,
 * like case insensitive keys and access by key or index.
 *
 * @author Terence Haddock
 */
public class AspCollection implements SimpleMap, Serializable
{
    /** Debugging context */
    static final Logger DBG = Logger.getLogger(AspCollection.class);

    /**
     * The items of this collection.
     */
    public ItemClass Item = new ItemClass(this);

    /** This boolean value is set to TRUE to make the contents
        of this collection read-only */
    boolean readonly = false;

    /** The map vector maps from integer index to key value */
    Vector map = null;

    /** The contents hashtable maps from key to value */
    Hashtable contents = null;

    /**
     * The put method adds an item to this collection
     * @param key Key under which to store this item
     * @param obj Object to store
     * @throws AspException if an error occurs
     */
    public synchronized void put(Object key, Object obj) throws AspException
    {
        if (readonly) throw new AspReadOnlyException("Item");
        if (map == null) initialize();
        key = Types.dereference(key);
        if (key instanceof Integer)
        {
            int iVal = ((Integer)key).intValue() - 1;
            while (map.size() <= iVal)
            {
                map.add(null);
            }
            Object realKey = map.get(iVal);
            if (realKey == null) {
                realKey = key;
                map.set(iVal, realKey);
            } else {
                if (realKey instanceof String)
                    realKey = ((String)realKey).toLowerCase();
            }
            contents.put(realKey, obj);
            return;
        }
        String strKey = Types.coerceToString(key);
        if (contents.containsKey(strKey.toLowerCase())) {
            contents.put(strKey.toLowerCase(), obj);
            return;
        }
        map.add(key);
        contents.put(strKey.toLowerCase(), obj);
    }

    /**
     * The get method obtains the value at the specified index or with
     * the specified key.
     * @param key Key/Index to obtain
     * @return the value at the specified index or key
     * @throws AspException if an error occurs
     */
    public synchronized Object get(Object key) throws AspException
    {
        if (map == null) initialize();
        key = Types.dereference(key);
        Object value;
        if (key instanceof Integer)
        {
            int iVal = ((Integer)key).intValue() - 1;

            Object realKey = map.get(iVal);
            if (realKey instanceof String)
                realKey = ((String)realKey).toLowerCase();
            value=contents.get(realKey);
        } else {
            String strKey = Types.coerceToString(key).toLowerCase();

            value=contents.get(strKey);
        }
        if (value == null) return Constants.undefinedValueNode;
        return value;
    }

    /**
     * Removes the element from this collection.
     * @param key Key to remove
     * @throws AspException on error
     */
    public synchronized void remove(Object key) throws AspException
    {
        if (readonly) throw new AspReadOnlyException("Item");
        if (map == null) initialize();
        key = Types.dereference(key);
        if (key instanceof Integer)
        {
            int iVal = ((Integer)key).intValue() - 1;

            Object realKey = map.get(iVal);
            if (realKey instanceof String)
                realKey = ((String)realKey).toLowerCase();
            
            map.remove(iVal);
            contents.remove(realKey);
        } else {
            String strValue = Types.coerceToString(key);
            
            map.remove(strValue);
            contents.remove(strValue.toLowerCase());
        }
    }

    /**
     * Detects if the key is present in this collection.
     * @param key Key to test
     * @return <b>true</b> if key is present, <b>false</b> otherwise.
     * @throws AspException on error
     */
    public synchronized boolean containsKey(Object key) throws AspException
    {
        if (map == null) initialize();
        key = Types.dereference(key);
        if (key instanceof Integer)
        {
            int iVal = ((Integer)key).intValue() - 1;
            if (iVal < map.size()) return true;
            return false;
        } else {
            String value = Types.coerceToString(key).toLowerCase();
            return contents.containsKey(value);
        }
    }

    /**
     * Obtains an enumeration of the keys in this collection.
     * @return enumeration of keys in this collection
     * @throws AspException on error
     */
    public synchronized Enumeration keys() throws AspException
    {
        if (map == null) initialize();
        return map.elements();
    }

    /**
     * Obtains the elements in this AspCollection.
     * @return enumeration of elements in this collection
     * @throws AspException on error
     */
    public synchronized Enumeration elements() throws AspException
    {
        if (map == null) initialize();
        return contents.elements();  
    }

    /**
     * Obtains the size of this collection.
     * @return size of this collection
     * @throws AspException on error
     */
    public synchronized int size() throws AspException
    {
        if (map == null) initialize();
        return map.size();
    }
    
    /**
     * This ASP-accessible function obtains the number of elements
     * stored in this collection.
     * @return number of elements in this collection.
     * @throws AspException on error
     */
    public synchronized int Count() throws AspException
    {
        if (map == null) initialize();
        return map.size();
    }

    /**
     * Removes all of the elements from this collection.
     * @throws AspException on error
     */
    public synchronized void removeAll() throws AspException
    {
        if (readonly) throw new AspReadOnlyException("Item");
        if (map == null) initialize();
        contents = new Hashtable();
        map = new Vector();
    }

    /**
     * Obtains the list of keys stored in this object. Used for 
     * foreach statements.
     * @return list of keys stores in this application object.
     * @throws AspException if an error occurs
     * @see SimpleMap#getKeys()
     */
    public synchronized java.util.Enumeration getKeys()
        throws AspException
    {
        if (map == null) initialize();
        return map.elements();
    }

    /**
     * Use this method to set this collection read-only, cannot
     * be undone.
     */
    public void setReadOnly()
    {
        readonly = true;
    }

    /**
     * Write this object to the output.
     * First outputs the number of elements in this object, then outputs
     * each object first key then value. If a value is not Serializable,
     * the Constants.undefinedValueNode object is outputted.
     * @param out OutputStream to write object to.
     * @throws IOException on input/output error
     */
    private void writeObject(java.io.ObjectOutputStream out)
        throws IOException
    {
        int size;
        if (map == null) size = 0;
            else size = map.size();
        out.writeInt(size);
        for (int x = 0; x < size; x++)
        {
            Object key = map.get(x);
            if (DBG.isDebugEnabled())
                DBG.debug("Key " + x + " = " + key);
            if (key instanceof String)
                key = ((String)key).toLowerCase();
            Object value = contents.get(key);
            out.writeObject(key);
            if (value instanceof Serializable) {
                if (DBG.isDebugEnabled())
                    DBG.debug("Obj(S) " + x + " = " + value);
                out.writeObject(value);
            } else {
                if (DBG.isDebugEnabled())
                    DBG.debug("Obj " + x + " = " + value);
                out.writeObject(Constants.undefinedValueNode);
            }
        }
    }

    /**
     * Reads an AspCollection back in from an ObjectInputStream.
     * @param in InputStream to read object from
     * @throws IOException on input/output error
     * @throws ClassNotFoundException when a class cannot be found
     */
    private void readObject(java.io.ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        try {
            removeAll();
        } catch (Exception ex) {
        }
        int size = in.readInt();
        for (int x = 0; x < size; x++)
        {
            String strKey = (String)in.readObject();
            if (DBG.isDebugEnabled())
                DBG.debug("Key " + x + " = " + strKey);
            Object objValue = in.readObject();
            if (DBG.isDebugEnabled())
                DBG.debug("Obj " + x + " = " + objValue);
            try {
                put(strKey, objValue);
            } catch (Exception ex)
            {
            }
        }
    }

    /**
     * This protected function initializes this object.
     * @throws AspException on error
     */
    protected void initialize() throws AspException
    {
        map = new Vector();
        contents = new Hashtable();
    }

    /**
     * This class contains an interface back to the contents class
     */
    public class ItemClass implements SimpleMap
    {
        /** The AspCollection class this class is part of */
        AspCollection collection;

        /**
         * Constructor of this sub-class.
         * @param collection class this item class is contained in
         */
        public ItemClass(AspCollection collection)
        {
            this.collection = collection;
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
        public Object get(Object obj)
            throws AspException
        {
            return collection.get(obj);
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
            collection.put(key, value);
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
            return collection.getKeys();
        }
    
        /**
         * This ASP-accessible function removes all of the elements
         * in the application object.
         * @throws AspException on error
         */
        public void RemoveAll() throws AspException
        {
            collection.removeAll();
        }
    
        /**
         * This ASP-accessible function removes the specified element
         * from this application object.
         * @param key Key to remove
         * @throws AspException on error
         */
        public void Remove(Object key) throws AspException
        {
            collection.remove(key);
        }
    
        /**
         * This ASP-accessible function obtains the number of elements
         * stored in this application object.
         * @return number of elements in this array
         * @throws AspException on error
         */
        public int Count() throws AspException
        {
            return collection.size();
        }
    
        /**
         * This method returns <b>true</b> if the specified key exists in
         * this collection, <b>false</b> otherwise.
         * @param key Key to test
         * @return true or false
         * @throws AspException on error
         */
        public boolean containsKey(Object key) throws AspException
        {
            return collection.containsKey(key);
        }
    
        /**
         * Obtains an enumeration of the keys in this collection.
         * @return enumeration of keys in this collection
         * @throws AspException on error
         */
        public synchronized Enumeration keys() throws AspException
        {
            return collection.keys();
        }
    
        /**
         * Obtains the elements in this AspCollection.
         * @return enumeration of elements in this collection
         * @throws AspException on error
         */
        public synchronized Enumeration elements() throws AspException
        {
            return collection.elements();  
        }
    
        /**
         * Obtains the size of this collection.
         * @return size of this collection
         * @throws AspException on error
         */
        public synchronized int size() throws AspException
        {
            return collection.size();
        }
    }
}
