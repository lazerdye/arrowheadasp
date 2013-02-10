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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.tripi.asp.util.ParseQueryString;
import com.tripi.asp.util.Tools;

/**
 * Request is a class which holds request-specific data.
 * It is an adaptor class which allows access to the Java Servlet
 * javax.servlet.http.HttpServletRequest class.
 * Implementation state:
 * <ul>
 * <li><b>Cookies</b> - Implemented. <b>Note</b> does not support cookies
 * like "VALUE=First&amp;VALUE=Second", does support cookies like
 * "VALUE1=First&amp;VALUE2=Second".
 * <li><b>Form</b> - Fully implemented.
 * <li><b>QueryString</b> - Fully implemented.
 * <li><b>ServerVariables</b> - Fully implemented.
 * <li><b>TotalBytes</b> - Fully implemented.
 * <li><b>BinaryRead</b> - Fully implemented.
 * </ul>
 * 
 * @author Terence Haddock
 * @version 0.9
 */
public class Request implements SimpleMap
{
    /** Debugging category */
    static private Logger DBG = Logger.getLogger(Request.class);

    /** Java servlet request object */
    HttpServletRequest request;

    /** QueryString sub-object */
    public QueryStringObj QueryString;

    /** ServerVariables sub-object */
    public ServerVariablesObj ServerVariables;

    /** Form sub-object */
    public FormObj Form;

    /** Cookies sub-object */
    public CookiesObj Cookies;

    /**
     * Constructor.
     * @param request Java servlet request object.
     * @see HttpServletRequest
     */
    public Request(HttpServletRequest request) throws AspException
    {
        this.request = request;
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (java.io.UnsupportedEncodingException ex)
        {
            throw new AspNestedException(ex);
        }
        QueryString = new QueryStringObj(request.getQueryString());
        ServerVariables = new ServerVariablesObj(request);
        Form = new FormObj(request);
        Cookies = new CookiesObj(request);
    }

    /**
     * This unsupported ASP-accessible function obtains the 
     * HttpServletRequest object that this Request object points to.
     * @return HttpServletRequest object
     */
    public HttpServletRequest getHttpServletRequest()
    {
        return request;
    }

    /**
     * SimpleMap interface, obtains the value of an object stored in one
     * of the sub objects, in the following order: QueryString, Form, Cookies,
     * ServerVariables.
     * @param obj Key of object to obtain. Will be coerced to string and
     * is case insensitive.
     * @return value stored at the given position, or UndefinedValueNode
     * if no value is found
     * @throws AspException on error.
     * @see SimpleMap#get(Object)
     */
    public Object get(Object obj) throws AspException
    {
        Object queryString = QueryString.get(obj);
        if (!(queryString instanceof UndefinedValueNode)) {
            return queryString;
        }
        Object formValue = Form.get(obj);
        if (!(formValue instanceof UndefinedValueNode)) {
            return formValue;
        }
        Object serverVariable = ServerVariables.get(obj);
        if (!(serverVariable instanceof UndefinedValueNode)) {
            return serverVariable;
        }
        return Constants.undefinedValueNode;
    }

    /**
     * Stores a value in this object. This method will always throw
     * a AspReadOnlyException error.
     * @param key Key to store this object under.
     * @param value Value to store with the given key.
     * @throws AspException always, throws AspReadOnlyException.
     * @see SimpleMap#put(Object,Object)
     */
    public void put(Object key, Object value) throws AspException
    {
        throw new AspReadOnlyException("Request");
    }

    /**
     * Obtains the list of keys in this object. Not currently implemented.
     * @return enumeration of keys in this object.
     * @throws AspException always, throws AspNotImplementedException    
     * @see SimpleMap#getKeys
     */
    public Enumeration getKeys() throws AspException
    {
        throw new AspNotImplementedException("getKeys");
    }

    /**
     * Obtain the content length of the request.
     * @return content length of the request.
     */
    public int TotalBytes()
    {
        int totalBytes = request.getContentLength();
        if (totalBytes == -1) return 0;
        return totalBytes;
    }

    /**
     * Read the data from the form.
     * @param length number of bytes to read
     * @return array of data from the form.
     */
    public byte[] BinaryRead(int length) throws IOException
    {
        StringBuffer buf = new StringBuffer();
        byte bytes[] = new byte[length];
        InputStream is = request.getInputStream();

        int nread = 0;

        
        if (DBG.isDebugEnabled()) DBG.debug("Length: " + length);
        while (nread < length) {
            int thistime = is.read(bytes, nread, length - nread);
            if (DBG.isDebugEnabled()) {
                DBG.debug("nread: " + nread);
                DBG.debug("thistime: " + thistime);
            }
            if (thistime <= 0) break;
            nread += thistime;
        }
        
        if (DBG.isDebugEnabled()) DBG.debug("Total read: " + nread);
        while (nread < length) {
            byte nbytes[] = new byte[nread];
            System.arraycopy(bytes, 0, nbytes, 0, nread);
            bytes = nbytes;
        }
        return bytes;
    }

    /**
     * The ServerVariablesObj implemnets the Request.ServerVariables
     * object, containing a list of server variables.
     */
    static public class ServerVariablesObj implements SimpleMap
    {
        /** Reference to the collection object */
        public AspCollection Contents = new ServerVariablesContents();

        /** HTTP Servlet Request object */
        HttpServletRequest req;

        /**
         * Constructor.
         * @param req HTTP Servlet Request object
         */
        ServerVariablesObj(HttpServletRequest req)
        {
            this.req = req;
        }

        /**
         * This class handles the Contents varable, which is not defined
         * until it is first accessed.
         */
        class ServerVariablesContents extends AspCollection
        {
            /**
             * Initialize the values for this collection
             */
            protected void initialize() throws AspException
            {
                try {
                    super.initialize();
                    /* Fill the hashtable with the predefined server variable
                       names. */
                    String REQUEST_METHOD = req.getMethod();
                    if (REQUEST_METHOD != null) 
                        this.put("REQUEST_METHOD", REQUEST_METHOD);
                    String URL = req.getRequestURI();
                    if (URL != null)
                        this.put("URL", URL);
                    String SERVER_PROTOCOL = req.getProtocol();
                    if (SERVER_PROTOCOL != null)
                        this.put("SERVER_PROTOCOL", SERVER_PROTOCOL);
                    String SCRIPT_NAME = req.getServletPath();
                    if (SCRIPT_NAME != null)
                        this.put("SCRIPT_NAME", SCRIPT_NAME);
                    String PATH_INFO = req.getPathInfo();
                    if (PATH_INFO != null)
                        this.put("PATH_INFO", PATH_INFO);
                    String PATH_TRANSLATED = req.getPathTranslated();
                    if (PATH_TRANSLATED != null)
                        this.put("PATH_TRANSLATED", PATH_TRANSLATED);
                    String QUERY_STRING = req.getQueryString();
                    if (QUERY_STRING != null)
                        this.put("QUERY_STRING", QUERY_STRING);
                    int ContentLength = req.getContentLength();
                    if (ContentLength == -1) ContentLength = 0;
                    Integer CONTENT_LENGTH = new Integer(ContentLength);
                    this.put("CONTENT_LENGTH", CONTENT_LENGTH);
                    String CONTENT_TYPE = req.getContentType();
                    if (CONTENT_TYPE != null)
                        this.put("CONTENT_TYPE", CONTENT_TYPE);
                    String SERVER_NAME = req.getServerName();
                    if (SERVER_NAME != null)
                        this.put("SERVER_NAME", SERVER_NAME);
                    this.put("SERVER_PORT",
                        new Integer(req.getServerPort()));
                    String REMOTE_USER = req.getRemoteUser();
                    if (REMOTE_USER != null)
                        this.put("REMOTE_USER", REMOTE_USER);
                    String REMOTE_ADDR = req.getRemoteAddr();
                    if (REMOTE_ADDR != null)
                        this.put("REMOTE_ADDR", REMOTE_ADDR);
                    String REMOTE_HOST = req.getRemoteHost();
                    if (REMOTE_HOST != null)
                        this.put("REMOTE_HOST", REMOTE_HOST);
                    String AUTH_TYPE = req.getAuthType();
                    if (AUTH_TYPE != null)
                        this.put("AUTH_TYPE", AUTH_TYPE);
                    /* Fill the hashtable with all of the included server headers,
                       mangling the name (prepending with HTTP_ and converting
                       -'s to _'s. */
                    Enumeration enNames = req.getHeaderNames();
                    while (enNames.hasMoreElements()) {
                        String name = (String)enNames.nextElement();
                        String value = req.getHeader(name);
                        String newName = "HTTP_" + name.toUpperCase();
                        newName = newName.replace('-', '_');
                        this.put(newName, value);
                    }
                } finally {
                    setReadOnly();
                }
            }
        }

        /**
         * Obtain the value of a server variable.
         * @param obj Key to obtain, will be converted to string and is
         * case insensitive.
         * @return value of server variable with the specified key.
         * @throws AspException on error
         * @see SimpleMap#get(Object)
         */
        public Object get(Object obj) throws AspException
        {
            return Contents.get(obj);
        }

        /**
         * Stores a value with a specified key. ServerVariables are read-only    
         * so this method always throws an AspReadOnlyException error.
         * @param key Key to store value under.
         * @param value Value to store.
         * @throws AspException always, AspReadOnlyException
         * @see SimpleMap#put
         */
        public void put(Object key, Object value) throws AspException
        {
            throw new AspReadOnlyException("Request.ServerVariables");
        }

        /**
         * Obtains the keys of objects stored in this object.
         * @return Enumeration of keys stored in this object.
         * @see SimpleMap#getKeys
         */
        public Enumeration getKeys() throws AspException
        {
            return Contents.keys();
        }
    }

    /**
     * QueryStringObj implements the Request.QueryString object.
     */
    static public class QueryStringObj implements SimpleMap, SimpleReference
    {
        /** Collection reference to query string values */
        public AspCollection Contents = new QueryStringContents();

        /** The entire query string */
        String wholeString;

        /**    
         * Constructor.
         * @param queryString the entire query string.
         */
        public QueryStringObj(String queryString) {
            this.wholeString = queryString;
        }

        /**
         * SimpleReference read method which obtains the full value of this string.
         * @return full query string.
         * @throws AspException on error.
         * @see SimpleReference#getValue
         */
        public Object getValue() throws AspException
        {
            return wholeString;
        }
        
        /**
         * SimpleReference write method which always throws an
         * AspReadOnlyException because Request.QueryString objects
         * are read-only.
         * @param obj New value for Request.QueryString
         * @throws AspException always throws AspReadOnlyException
         * @see SimpleReference#setValue
         */
        public void setValue(Object obj) throws AspException
        {
            throw new AspReadOnlyException("Request.QueryString");
        }

        /**
         * SimpleMap read method which obtains the value of a specific
         * parameter in the query string.
         * @param obj Name of object to obtain, will be converted to
         * string and is case insensitive.
         * @return value of query string parameter given by obj key, this
         * value will actually be an instance of the QueryStringValue class in
         * order to implement methods and arrays of query string values.
         * @throws AspException on error.
         * @see SimpleReference#get
         */
        public Object get(Object obj) throws AspException
        {
            return Contents.get(obj);
        }

        /**
         * SimpleMap write method which stores a value with the specified
         * key. This method always throws AspReadOnlyException since
         * Request.QueryString is read-only.
         * @param key Key of object to store
         * @param value object to store.
         * @throws AspException always throws AspReadOnlyException
         * @see SimpleMap#put
         */
        public void put(Object key, Object value) throws AspException
        {
            throw new AspReadOnlyException("Request.QueryString");
        }

        /**
         * Obtains the names of parameters stored in this object.
         * @return Enumeration of keys stored in this object.
         * @see SimpleMap#getKeys
         */
        public Enumeration getKeys() throws AspException
        {
            return Contents.getKeys();
        }

        /**
         * ASP-Accessible function to obtain the number of items in this
         * query string.
         * @return number of items in this query string
         */
        public int Count() throws AspException
        {
            return Contents.Count();
        }

        /**
         * This class handles the Contents varable, which is not defined
         * until it is first accessed.
         */
        class QueryStringContents extends AspCollection
        {
            /**
             * Initialized the values for the query string contents
             */
            protected void initialize() throws AspException
            {
                try {
                    super.initialize();
                    /* Parse out all of the param/value pairs */
                    Map paramValues = Tools.parseQueryString(wholeString);
                    Tools.convertToMultiValue(this, paramValues, true);
                } finally {
                    setReadOnly();
                }
            }
        }
    }

    /**
     * The FormObj class implements the interface between ASP Request.Form
     * object and the Servlet API. The form values are 'lazy' evaluated
     * to keep from accessing the Servlet's input stream until required,
     * in case the BinaryRead function will be called.
     */
    static public class FormObj implements SimpleMap
    {
        /** AspCollection to store form data */
        public AspCollection Contents = new FormContents();

        /** Java Servlet request object */
        HttpServletRequest request;

        /**
         * Constructor.
         * @param request Java Servlet request object.
         */
        FormObj(HttpServletRequest request)
        {
            this.request = request;
        }

        /**
         * Obtains the list of keys in this object.
         * @return Enumeration of keys in this object.
         * @throws AspException on error.
         */
        public Enumeration getKeys() throws AspException
        {
            return Contents.getKeys();
        }

        /**
         * Obtains a form value with the specified key.
         * @param obj Key of form parameter, will be converted to string
         * and is case insensitive.
         * @return instance of FormValue class corresponding to the
         * parameter name given.
         * @throws AspException on error.
         * @see SimpleMap#get
         */
        public Object get(Object obj) throws AspException
        {
            return Contents.get(obj);
        }

        /**
         * Implements the SimpleMap.put interface function. Will always
         * throw a AspReadOnlyException becaues Request.Form is read-only.
         * @param key Key of object to store.
         * @param value Object to store
         * @throws AspException on error
         * @see SimpleMap#put
         */
        public void put(Object key, Object value) throws AspException
        {
            throw new AspReadOnlyException("Request.Form");
        }

        /**
         * ASP-Accessible function to obtain the number of items in this form.
         * @return number of items in this form
         */
        public int Count() throws AspException
        {
            return Contents.Count();
        }

        /**
         * This class implements processing specific to a form
         * data object.
         */
        class FormContents extends AspCollection
        {
            /**
             * Empty mult-value
             */
            public Tools.MultiValue emptyValue = null;

            /**
             * This internal function initialized the form contents.
             */
            protected void initialize() throws AspException
            {
                super.initialize();

                try {
                    /* Check if this is a POST */
                    if (!request.getMethod().equalsIgnoreCase("POST"))
                    {
                        return;
                    }
                    /* Check if this is an understood content type */
                    if (!request.getContentType().
                            equalsIgnoreCase("application/x-www-form-urlencoded")) {
                        return;
                    }
                    int len = request.getContentLength();
                    Reader in;
                    Map htValues;
                    try {
                        in = request.getReader();
                        htValues = ParseQueryString.parse(in, len);
                    } catch (IOException ex) {
                        throw new AspException("Input error: " + ex);
                    }
                    Tools.convertToMultiValue(this, htValues, false);
    
                    emptyValue = new Tools.MultiValue();
                } finally {
                    setReadOnly();
                }
            }

            /**
             * Overriding the get function in order to handle empty items.
             * @param key Key/Index to obtain
             * @return value for the key/index
             * @throws AspException in an error occurs
             */
            public synchronized Object get(Object key) throws AspException
            {
                Object value = super.get(key);
                if (emptyValue != null && value instanceof UndefinedValueNode)
                {
                    return emptyValue;
                }
                return value;
            }
        }
    }

    /**
     * The CookiesObj class implementes the interface between the ASP 
     * Request.Cookies object and the Servlet API.
     */
    static public class CookiesObj implements SimpleMap
    {
        /** List of value of cookies */
        Hashtable cookieValues = null;

        /** An "empty" value used for non-existant cookies */
        CookieValue emptyCookie = new CookieValue();

        /** List of cookies in this request */
        Cookie cookies[];

        /**
         * Constructor.
         * @param request Java Servlet request object.
         */
        CookiesObj(HttpServletRequest request)
        {
            this.cookies = request.getCookies();
            /* Parse through the cookies and get their values */
            cookieValues = new Hashtable();
            if (cookies != null)
            for (int i = 0; i < cookies.length; i++)
            {
                Cookie cookie = cookies[i];
                CookieValue cookieValue = new CookieValue(cookie);
                if (DBG.isDebugEnabled()) {
                    DBG.debug("Adding cookie: " + cookie);
                }
                cookieValues.put(new IdentNode(cookie.getName()), cookieValue);
            }
        }

        /**
         * Obtains the list of keys in this object.
         * @return Enumeration of keys in this object.
         * @throws AspException on error.
         */
        public Enumeration getKeys() throws AspException
        {
            return cookieValues.keys();
        }

        /**
         * Obtains a cookie value with the specified key.
         * @param obj Key of form parameter, will be converted to string
         * and is case insensitive.
         * @return instance of CookieValue class corresponding to the
         * parameter name given.
         * @throws AspException on error.
         * @see SimpleMap#get
         */
        public Object get(Object obj) throws AspException
        {
            IdentNode strValue = new IdentNode(Types.coerceToString(obj));
            if (!cookieValues.containsKey(strValue)) {
                return emptyCookie;
            }
            return cookieValues.get(strValue);
        }

        /**
         * Implements the SimpleMap.put interface function. Will always
         * throw a AspReadOnlyException becaues Request.Form is read-only.
         * @param key Key of object to store.
         * @param value Object to store
         * @throws AspException on error
         * @see SimpleMap#put
         */
        public void put(Object key, Object value) throws AspException
        {
            throw new AspReadOnlyException("Request.Cookies");
        }

        /**
         * CookieValue class contains methods and functions related
         * to the value of a cookie.
         */
        static class CookieValue implements SimpleReference, SimpleMap
        {            
            /** Hashtable of values in this cookie */
            Hashtable cookieValues = new Hashtable();

            /** String containing the entire list of values */
            String valuesAsString;

            /**
             * Constructor. FormValue is initally created with no values.
             */
            CookieValue()
            {
                valuesAsString = null;
            }

            /**
             * Constructor from a cookie object.
             * @param cookie Cookie object
             */
            CookieValue(Cookie cookie)
            {
                valuesAsString = cookie.getValue();
                /* XXX Not sure if this works */
                if (DBG.isDebugEnabled()) DBG.debug("Cookie values: " +
                        valuesAsString);
                if (valuesAsString.indexOf('=') != -1) 
                {
                    Map hashValues = ParseQueryString.parse(valuesAsString);
                    /* Lowercase the value keys */
                    for (Iterator i = hashValues.keySet().iterator(); i.hasNext();)
                    {
                        String key = (String)i.next();
                        String value[] = (String[])hashValues.get(key);
                        if (DBG.isDebugEnabled()) {
                            DBG.debug("Key: " + key);
                            DBG.debug("Value: " + value[0]);
                        }
                        cookieValues.put(new IdentNode(key), value[0]);
                    }
                }
            }

            /**
             * Obtains a specific index in this array of values.
             * @param value index to obtain, will be converted to an string.
             * @return string value of form at the specified index.
             * @throws AspException on error
             * @see SimpleMap#get
             */
            public Object get(Object value) throws AspException
            {
                IdentNode iValue = new IdentNode(Types.coerceToString(value));
                if (!cookieValues.containsKey(iValue)) {
                    return Constants.undefinedValueNode;
                }
                Object res = cookieValues.get(iValue);
                if (DBG.isDebugEnabled())
                    DBG.debug("Get key: " + res);
                return res;
            }

            /**
             * Stores a value. This method always returned an
             * AspReadOnlyException because Request.Form values
             * are read-only.
             * @param key Key to store
             * @param value Value to store.
             * @throws AspException always throws AspReadOnlyException
             */
            public void put(Object key, Object value) throws AspException
            {
                throw new AspReadOnlyException("Request.Cookies");
            }

            /**
             * Obtains the list of values in this Request.Form(x) array.
             * @return enumeration of the list of values in this array.
             * @throws AspException on error
             */
            public Enumeration getKeys() throws AspException
            {
                return cookieValues.keys();
            }

            /**
             * Obtains the entire value array
             * @return the entire value array
             * @throws AspException on error
             */
            public Object getValue() throws AspException
            {
                if (valuesAsString == null)
                    return Constants.undefinedValueNode;
                return valuesAsString;
            }

            /**
             * Sets the value of this value array. Always throws 
             * AspReadOnlyException because this array is read-only.
             * @param obj Value to set.
             * @throws AspException on error.
             */
            public void setValue(Object obj) throws AspException
            {
                throw new AspException("Modification of read-only variable");
            }

            /**
             * Converts this value array to a string. For debugging only.
             * @return string representation of this value array.
             */
            public String toString()
            {
                return "{CookieValue(" + cookieValues + ")}";
            }
        }
    }
}
