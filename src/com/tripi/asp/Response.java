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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.Vector;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.log4j.Category;

/**
 * ASP "Response" object. This object handles communication from ASP
 * back to the client. Implementation state:
 * <ul>
 * <li><b>Cookies</b> - Implemented.
 * <li><b>Buffer</b> - Implemented
 * <li><b>CacheControl</b> - Implemented
 * <li><b>Charset</b> - Implemented
 * <li><b>ContentType</b> - Implemented
 * <li><b>Expires</b> - Implementes, <b>NOTE</b> Expires is not updated
 *                      properly when ExpiresAbsolute is used.
 * <li><b>ExpiresAbsolute</b> - Fully implemented.
 * <li><b>IsClientConnected</b> - <b>TODO</b> Not implemented.
 * <li><b>PICS</b> - <b>TODO</b> Not implemented.
 * <li><b>Status</b> - Fully implemented.
 * <li><b>AddHeader</b> - Fully supported.
 * <li><b>AppendToLog</b> - <b>TODO</b> Not implemented.
 * <li><b>BinaryWrite</b> - Fully supported.
 * <li><b>Clear</b> - Implemented.
 * <li><b>End</b> - Implemented.
 * <li><b>Flush</b> - Implemented.
 * <li><b>Redirect</b> - Implemented.
 * <li><b>Write</b> - Implemented.
 * </ul>
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class Response
{
    /** Log4j debugging object */
    Category DBG = Category.getInstance(Response.class);

    /** JSDK servlet response object */
    HttpServletResponse response;

    /**
     * Buffer for when Buffer = TRUE. When buffering is enabled,
     * stringBuf != null, and when buffering is disabled, stringBuf == null.
     */
    StringBuffer    stringBuf = new StringBuffer();

    /**
     * Have the headers been sent?
     */
    boolean            sentHeaders = false;

    /**
     * Cookie jar to hold cookies to be sent to the client.
     */
    public CookieJar Cookies = new CookieJar();

    /**
     * Cache-control setting
     */
    public String   cacheControl = "private";

    /**
     * Content type setting
     */
    public String   contentType = "text/html";

    /**
     * Charset setting
     */
    public String   charset = "";

    /**
     * Current expiry string. Prepended with an underscore to keep it
     * from being accessed within ASP.
     */
    AspDate    _expiryDate = null;

    /**
     * Current expiry time, in minutes.
     */
    int    _expiryMinutes = 0;

    /**
     * Constructor.
     * @param response JSDK response object.
     */
    public Response(HttpServletResponse response)
    {
        this.response = response;
    }

    /**
     * This unsupported ASP-accessible function obtains the HTTP response
     * object that this Response object points to.
     * @return HttpServletResponse object
     */
    public HttpServletResponse getHttpServletResponse()
    {
        return response;
    }

    /**
     * ASP-accessible function to output text to the client.
     * @param str Text to output to the client.
     * @throws IOException on output error.
     */
    public void Write(String str) throws IOException, AspException
    {
        if (stringBuf != null) {
            stringBuf.append(str);
        } else {
            if (!sentHeaders) sendHeaders();
            writeOut(str);
        }
    }

	private void writeOut(String str) throws IOException {
		Writer wr = response.getWriter();
		wr.write(str);
		wr.flush();
	}

	/**
     * ASP-accessible function to output any buffered text to the client.
     * @throws IOException on output error.
     * @throws AspException on ASP error.
     */
    public void Flush() throws IOException, AspException
    {
        if (stringBuf == null)
            throw new AspException("Flush() called when " +
                    "buffer = FALSE");
        if (!sentHeaders) sendHeaders();
        writeOut(stringBuf.toString());
        stringBuf = new StringBuffer();
    }

    /**
     * ASP-accessible function to redirect the user to a different URL.
     * @param redir URL to redirect to, should be fully-qualified
     * URL.
     * @throws IOException on output error
     * @throws AspException always throws AspExitScriptException
     */
    public void Redirect(String redir) throws IOException, AspException
    {
        if (!sentHeaders) sendHeaders();
        response.setHeader("Location", redir);
        response.sendError(302);
        stringBuf = null;
        throw new AspExitScriptException();
    }

    /**
     * This ASP-accessible function acts as the ASP field 'buffer', which
     * enables or disables buffering.
     * @param bBuffer enable, or disable buffer
     * @throws IOException on error.
     */
    public void Buffer(boolean bBuffer) throws IOException, AspException
    {
        if (bBuffer)
        {
            if (sentHeaders)
            {
                throw new AspException("Headers already sent, buffering " +
                    "cannot be turned on after the headers have been sent.");
            } else if (stringBuf == null) {
                stringBuf = new StringBuffer();
            }
        } else {
            if (stringBuf != null) {
                if (stringBuf.length() == 0) stringBuf = null;
                else {
                    throw new AspException("Buffering cannot be turned off " +
                     "after it has been turned on.");
                }
            }
        }
    }

    /**
     * This ASP-accessible function obtains the buffer status
     * @return <b>true</b> if buffering is on, <b>false</b> if not.
     */
    public boolean Buffer()
    {
        return stringBuf != null;
    }

    /**
     * The ASP-accessible Clear function clears any buffered text.
     */
    public void Clear() throws AspException
    {
        if (stringBuf == null)
            throw new AspException("Flush() called when " +
                    "buffer = FALSE");
        stringBuf = new StringBuffer();
    }

    /**
     * The ASP-accessible End function ends the current ASP script
     * immediately.
     * @throws AspException always throws AspExitScriptException.
     */
    public void End() throws AspException
    {
        throw new AspExitScriptException();
    }

    /**
     * This ASP-accessible function sets the expiry time to the specified
     * number of minutes from the current date/time.
     * @param minutes Number of minutes
     */
    public void Expires(int minutes) throws AspException
    {
        if (sentHeaders) throw new AspException("Headers Sent");
        Calendar cal = new GregorianCalendar();
        Date date = new Date(System.currentTimeMillis());
        cal.setTime(date);
        cal.add(cal.MINUTE, minutes);
        _expiryDate = new AspDate(cal.getTime());
        _expiryMinutes = minutes;
    }

    /**
     * This ASP-accessible function obtains the number of minutes the
     * expiry time is set to.
     * @return Number of minutes
     */
    public int Expires()
    {
        return _expiryMinutes;
    }

    /**
     * This ASP-accessible function sets the expiry time to the specified
     * absolute date.
     * @param date Date to set expiry to
     */
    public void ExpiresAbsolute(AspDate date) throws AspException
    {
        if (sentHeaders) throw new AspException("Headers Sent");
        _expiryDate = date;
        /* Get the current date in seconds */
        long currentTime = System.currentTimeMillis() / 1000;
        /* Get the expiry date in seconds */
        long expiryTime = date.toDate().getTime() / 1000;
        /* Obtain the different in seconds between the two times */
        long diffSec = (expiryTime - currentTime);
        /* Set the difference in minutes to the expiry setting */
        _expiryMinutes = (int)(diffSec / 60);
    }

    /**
     * This ASP-accessible function obtains the number of minutes the
     * expiry time is set to.
     * @return Number of minutes
     */
    public AspDate ExpiresAbsolute()
    {
        return _expiryDate;
    }

    /**
     * This ASP-accessible BinaryWrite function writes binary data to the
     * output stream. This function assumes it's being called with
     * an array of Chars.
     * @param an ArrayNode object
     */
    public void BinaryWrite(byte arr[]) throws AspException, IOException
    {
        /* XXX This ignores Response.Buffer */
        Flush();
        String str = new String(arr);
        writeOut(str);
    }

    /**
     * Internal function to send the headers.
     */
    synchronized void sendHeaders() throws AspException
    {
        if (sentHeaders) return;
        if (DBG.isDebugEnabled()) DBG.debug("Sending headers");
        sentHeaders = true;
        String contentType = this.contentType;
        if (this.charset != null && this.charset.length() > 0) {
            contentType = contentType + "; charset=" + this.charset;
        }
        response.setContentType(contentType);
        if (cacheControl == null) cacheControl = "private";
        response.setHeader("Cache-control", cacheControl);
        Cookies.sendCookies();
        if (_expiryDate != null)
        {
            Calendar cal = _expiryDate.toCalendar();
            if (!_expiryDate.hasTime())
            {
                /* If the time is not set, assume the expiry is at midnight
                   of the current date. */
                cal.set(cal.HOUR_OF_DAY, 24);
                cal.set(cal.MINUTE, 0);
                cal.set(cal.SECOND, 0);
                cal.set(cal.MILLISECOND, 0);
            }
            /* You can get the time in millisecond directly from cal 
               in JDK 1.4 */
            Date date = cal.getTime();
            response.setDateHeader("Expires", date.getTime());
        }
    }

    /**
     * ASP-accessible function to set the status for this request.
     * @param status New status value
     */
    public void Status(int value)
    {
        response.setStatus(value);
    }

    /**
     * ASP-accessible function to add a header to the response stream.
     * This function will not overwrite a header, it will add a new one
     * to the response stream.
     * @param name Header name
     * @param value Header value
     */
    public void AddHeader(String name, String value)
    {
        response.addHeader(name, value);
    }

    /**
     * Cookie Jar class
     */
    public class CookieJar implements SimpleMap
    {
        /**
         * AspCollection containing the cookie crumbs.
         */
        AspCollection cookieCrumbs = new AspCollection();

        /**
          * Constructor, initally creates an empty cookie jar.
         */
        public CookieJar()
        {
        }

        /**
         * Obtains a cookie from the jar. This class always returns
         * a cookie crumb.
         * @param key Key of cookie, will be converted to string and is
         * case insensitive.
         * @return CookieCrumb object representing a cookie.
         */
        public synchronized Object get(Object key) throws AspException
        {
            String strKey = Types.coerceToString(key);
            CookieCrumb crumb;

            if (cookieCrumbs.containsKey(strKey))
            {
                if (DBG.isDebugEnabled()) DBG.debug("existing(" + strKey + ")");
                crumb = (CookieCrumb)cookieCrumbs.get(strKey);
            } else {
                if (DBG.isDebugEnabled())
                {
                    DBG.debug("new(" + strKey + ")");
                    DBG.debug("This: " + this);
                }
                crumb = new CookieCrumb(strKey);
                cookieCrumbs.put(strKey, crumb);
            }
            return crumb;
        }
    
        /**
         * Stores a cookie value in the jar. 
         * @param key Key of cookie, will be converted to a string and is
         * case insensitive.
         * @param obj Value to store in the cookie, will be converted
         * to a string.
         */
        public synchronized void put(Object key, Object value)
            throws AspException
        {
            CookieCrumb crumb = (CookieCrumb)get(key);
            crumb.setValue(value);
        }

        /**
         * Enumerates the elements in this jar.
         * @return Enumeration of elements in this jar.
         */
        public Enumeration getKeys() throws AspException
        {
            return cookieCrumbs.getKeys();
        }

        /**
         * Send all of the cookies stored in the jar.
         */
        void sendCookies() throws AspException
        {
            if (DBG.isDebugEnabled())
            {
                DBG.debug("Sending cookies");
                DBG.debug("cookieCrumbs: " + cookieCrumbs);
                DBG.debug("This: " + this);
            }
            Enumeration values = cookieCrumbs.elements();
            while (values.hasMoreElements())
            {
                CookieCrumb crumb = (CookieCrumb)values.nextElement();
                if (DBG.isDebugEnabled()) DBG.debug("Sending cookie:" + crumb);
                if (crumb.name != null) {
                    StringBuffer cookieBuf = new StringBuffer();
                    cookieBuf.append(crumb.name);
                    if (crumb.fullValue != null)
                    {
                        cookieBuf.append('=');
                        cookieBuf.append(crumb.fullValue);
                    }
                    if (crumb.expires != null)
                    {
                        DateFormat df = new SimpleDateFormat(
                            "EEE, dd-MMM-yyyy hh:mm:ss z");
                        final TimeZone gmtTime = TimeZone.getTimeZone("GMT");
                        df.setTimeZone(gmtTime);
                        String text = df.format(crumb.expires.toDate());
                        cookieBuf.append("; expires=");
                        cookieBuf.append(text);
                    }
                    cookieBuf.append("; path=");
                    cookieBuf.append(crumb.path);
                    if (crumb.secure)
                    {
                        cookieBuf.append("; secure");
                    }
                    response.addHeader("Set-Cookie", cookieBuf.toString());
                }
            }
        }

        /**
         * CookieCrumb class to hold cookie information.
         */
        public class CookieCrumb implements SimpleReference, SimpleMap
        {
            /**
             * Cookie name
             */
            String name = null;

            /**
             * Cookie base value
             */
            String value = null;

            /**
             * Values of the keys
             */
            AspCollection keys = new AspCollection();

            /**
             * Expires value.
             */
            AspDate expires = null;

            /**
             * Cookie path
             */
            String path = "/";

            /**
             * Secure cookie?
             */
            boolean secure = false;

            /**
             * Cookie full value
             */
            String fullValue = null;

            /**
             * Constructor, setting a value.
             * @param name cookie name
             */
            public CookieCrumb(String name)
            {
                this.name = name;
            }

            /**
             * Sets the date this cookie expires.
             * @param Expiry expiration date.
             * @throws AspException
             */
            public void Expires(AspDate date) throws AspException
            {
                this.expires = date;
                updateCookie();
            }

            /**
             * Sets the cookie path
             * @param Path cookie path
             * @throws AspException
             */
            public void Path(String path) throws AspException
            {
                this.path = path;
                updateCookie();
            }

            /**
             * Set the secure flag
             * @param secure The secure flag
             * @throws AspException
             */
            public void Secure(boolean secure) throws AspException
            {
                this.secure = secure;
                updateCookie();
            }

            /**
             * Reference write function. Overwrites the current cookie value.
             * @param value new value
             * @throws AspException on error
             * @see SimpleReference#setValue
             */
            public void setValue(Object value) throws AspException
            {
                if (sentHeaders) {
                    throw new AspException("Headers Sent");
                }
                String str = Types.coerceToString(value);
                this.value = str;
                updateCookie();
            }

            /**
             * Reference read function.
             * @return value current value
             * @throws AspException on error
             * @see SimpleReference#getValue
             */
            public Object getValue() throws AspException
            {
                throw new AspException("Not implemented");
            }

            /**
             * Does this set of cookies have keys?
             */
            public boolean HasKeys() throws AspException
            {
                return keys.size() > 0;
            }

            /**
             * Gets the value in a SimpleMap context.
             * @param index Index of value to get
             * @return value of cookie key
             */
            public Object get(Object index) throws AspException
            {
                throw new AspNotImplementedException();
            }
            
            /**
             * Sets the value in a SimpleMap context.
             * @param index Index of value to get
             * @param value New value
             */
            public void put(Object index, Object value) throws AspException
            {
                String indStr = Types.coerceToString(index);
                String valStr = Types.coerceToString(value);
                keys.put(indStr, valStr);
                updateCookie();
            }

            /**
             * Enumerates all of the keys in this cookie.
             * @return enumeration of the keys in this cookie.
             */
            public Enumeration getKeys() throws AspException
            {
                return keys.elements();
            }

            /**
             * Update the value of the cookie.
             */
            private void updateCookie() throws AspException
            {
                boolean first = true;
                StringBuffer buf = new StringBuffer();
                if (value != null) {
                    buf.append(Server.URLEncode(value));
                    first = false;
                }
                for (Enumeration e = keys.keys(); e.hasMoreElements();)
                {
                    String key = (String)e.nextElement();
                    String val = (String)keys.get(key);

                    if (!first)
                    {
                        buf.insert(0, '&');
                    } else {
                        first = false;
                    }
                    buf.insert(0, Server.URLEncode(val));
                    buf.insert(0, '=');
                    buf.insert(0, Server.URLEncode(key));
                }
                fullValue = buf.toString();
            }
        }
    }
}
