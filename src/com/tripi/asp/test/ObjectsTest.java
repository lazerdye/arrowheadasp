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

import com.tripi.asp.AspException;
import com.tripi.asp.Constants;
import com.tripi.asp.IdentNode;

import javax.servlet.http.Cookie;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.IOException;

import junit.framework.*;

/**
 * The ObjectsTest class contains the unit tests for the ASP build-in objects.
 * @author Terence Haddock
 */
public class ObjectsTest 
{
    /**
     * JUnit test suite
     * @return test suite for the built-in objects
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite("ASP Built In Objects");
        suite.addTest(new TestSuite(ApplicationTest.class));
        suite.addTest(new TestSuite(RequestTest.class));
        suite.addTest(new TestSuite(ResponseTest.class));
        return suite;
    }

    /**
     * This class contains the application object tests
     */
    public static class ApplicationTest extends TestCase
    {
        public ApplicationTest(String name) throws AspException
        {
            super(name);
        }

        /**
         * Tests Application.Contents.Count
         */
        public void testApplicationCount1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-application-count1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-application-count1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-application-count1.out"));
        }

        /**
         * Tests ForEach on Application.Contents
         */
        public void testApplicationForEach1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-application-foreach1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-application-foreach1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-application-foreach1.out"));
        }

        /**
         * Tests Application.Items
         */
        public void testApplicationItems1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-application-items1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-application-items1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-application-items1.out"));
        }

        /**
         * Tests Application.Items
         */
        public void testApplicationItems2() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-application-items2.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-application-items2.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-application-items2.out"));
        }

        /**
         * Tests Application.Items
         */
        public void testApplicationItems3() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-application-items3.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-application-items3.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-application-items3.out"));
        }

        /**
         * Tests Application.Remove
         */
        public void testApplicationRemove1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-application-remove1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-application-remove1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-application-remove1.out"));
        }

        /**
         * Tests Application.Remove
         */
        public void testApplicationRemove2() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-application-remove2.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-application-remove2.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-application-remove2.out"));
        }
    }

    /**
     * This class contains the request object tests
     */
    public static class RequestTest extends TestCase
    {
        public RequestTest(String name) throws AspException
        {
            super(name);
        }

        /** Test simple cookies */
        public void testCookie1() throws AspException, MalformedURLException,
            IOException
        {
            URLConnection con =
                AspTest.getConnection("objects/test-request-cookie1.asp");
            con.setRequestProperty("Cookie","HELLO=there");
            InputStream is = con.getInputStream();
            String content = AspTest.getContent(is);
            is.close();
            AspTest.save(content, "objects/out/test-request-cookie1.out");
            Assert.assertTrue(AspTest.compare(content,
                "objects/expect/test-request-cookie1.out"));
        }

        /** Test more complex cookies */
        public void testCookie2() throws AspException, MalformedURLException,
                IOException
        {
            URLConnection con =
                AspTest.getConnection("objects/test-request-cookie2.asp");
            con.setRequestProperty("Cookie","HELLO=TEST=test&THERE=two");
            InputStream is = con.getInputStream();
            String content = AspTest.getContent(is);
            is.close();
            AspTest.save(content, "objects/out/test-request-cookie2.out");
            Assert.assertTrue(AspTest.compare(content,
                "objects/expect/test-request-cookie2.out"));
        }

        /** Test more complex cookies */
        public void testCookie3() throws AspException, MalformedURLException,
                IOException
        {
            URLConnection con =
                AspTest.getConnection("objects/test-request-cookie3.asp");
            con.setRequestProperty("Cookie","HELLO=First=First+One&Second=The%2Dsecond%2done&Third=The%26third%26one");
            InputStream is = con.getInputStream();
            String content = AspTest.getContent(is);
            is.close();
            AspTest.save(content, "objects/out/test-request-cookie3.out");
            Assert.assertTrue(AspTest.compare(content,
                "objects/expect/test-request-cookie3.out"));
        }

        /** Test form values */
        public void testForm1() throws AspException, MalformedURLException,
                IOException
        {
            HttpURLConnection con = (HttpURLConnection)
                AspTest.getConnection("objects/test-request-form1.asp");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            AspTest.writeContent(con, "valuea=one&valueb=two");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-request-form1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-request-form1.out"));
        }

        /** Test form values, with escape sequences */
        public void testForm2() throws AspException, MalformedURLException,
                IOException
        {
            HttpURLConnection con = (HttpURLConnection)
                AspTest.getConnection("objects/test-request-form2.asp");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            AspTest.writeContent(con, "valuea=one+value&valueb=two%3dvalues");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-request-form2.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-request-form2.out"));
        }

        /** Test form values, multiple values for same parameter */
        public void testForm3() throws AspException, MalformedURLException,
                IOException
        {
            HttpURLConnection con = (HttpURLConnection)
                AspTest.getConnection("objects/test-request-form3.asp");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            AspTest.writeContent(con, "valuea=first+value&valuea=second+value");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-request-form3.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-request-form3.out"));
        }

        /** Test query string values */
        public void testQueryString1() throws AspException, MalformedURLException,
                IOException
        {
            URLConnection con = 
                AspTest.getConnection("objects/test-request-querystring1.asp" +
                        "?valuea=one&valueb=two");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-request-querystring1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-request-querystring1.out"));
        }

        /** Test embedded escapes in query string values */
        public void testQueryString2() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
                AspTest.getConnection("objects/test-request-querystring2.asp" +
                        "?valuea=first+value&valueb=second%3Dvalue");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-request-querystring2.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-request-querystring2.out"));
        }

        /** Test multiple parameters with the same names. */
        public void testQueryString3() throws AspException, MalformedURLException,
                IOException
        {
            URLConnection con = 
                AspTest.getConnection("objects/test-request-querystring3.asp" +
                        "?valuea=first+value&valuea=second%3Dvalue");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-request-querystring3.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-request-querystring3.out"));
        }

        /** Test server variables, GET request */
        public void testServerVariables1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-request-servervariables1.asp?daquery");
            con.setRequestProperty("X-Fake-Header","Fake data");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-request-servervariables1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-request-servervariables1.out"));
        }

        /** Test server variables, POST request */
        public void testServerVariables2() throws AspException,
                MalformedURLException, IOException
        {
            HttpURLConnection con = (HttpURLConnection)
              AspTest.getConnection("objects/test-request-servervariables2.asp");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("X-Fake-Header","More fake data");
            AspTest.writeContent(con, "some=data&some=more+data&even=more+data");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-request-servervariables2.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-request-servervariables2.out"));
        }

        /** Test binary read statement */
        public void testBinaryRead1() throws AspException,
                MalformedURLException, IOException
        {
            HttpURLConnection con = (HttpURLConnection)
              AspTest.getConnection("objects/test-request-binaryread1.asp");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            AspTest.writeContent(con, "some=data&some=more+data&even=more+data");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-request-binaryread1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-request-binaryread1.out"));
        }
    }

    /**
     * This class contains the response object tests
     */
    public static class ResponseTest extends TestCase
    {
        public ResponseTest(String name) throws AspException
        {
            super(name);
        }

        /** 
         * Simple cookies test
         */
        public void testCookies1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cookies1.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("Hello=There; path=/",
                    AspTest.getCookie(con, "Hello"));
        }

        /** 
         * Multiple cookies test
         */
        public void testCookies2() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cookies2.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("First=One; path=/",
                    AspTest.getCookie(con, "First"));
            Assert.assertEquals("Second=Two; path=/",
                    AspTest.getCookie(con, "Second"));
        }

        /** 
         * Single cookie, multiple values, test
         */
        public void testCookies3() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cookies3.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("Hello=Second=Two&First=One; path=/",
                    AspTest.getCookie(con, "Hello"));
        }

        /** 
         * Single cookie, embedded weird characters.
         */
        public void testCookies4() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cookies4.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("First=Embedded+spaces+and+%26+funky+%3D+characters; path=/",
                    AspTest.getCookie(con, "First"));
        }

        /** 
         * Single cookie, setting the path
         */
        public void testCookies5() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cookies5.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("First=Value; path=/asptest",
                    AspTest.getCookie(con, "First"));
        }

        /** 
         * Single cookie, setting the expires date
         */
        public void testCookies6() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cookies6.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("First=Value; expires=Sat, 25-Jun-2005 04:00:00 GMT; path=/",
                    AspTest.getCookie(con, "First"));
        }

        /** 
         * Single cookie, setting security on
         */
        public void testCookies7() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cookies7.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("First=Value; path=/; secure",
                    AspTest.getCookie(con, "First"));
        }

        /** 
         * Test HasKeys, no keys
         */
        public void testCookies8() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cookies8.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-cookies8.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-cookies8.out"));
        }

        /** 
         * Test HasKeys, has keys
         */
        public void testCookies9() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cookies9.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-cookies9.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-cookies9.out"));
        }

        /** 
         * Buffering on
         */
        public void testBuffer1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-buffer1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-buffer1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-buffer1.out"));
        }

        /** 
         * Buffering off
         */
        public void testBuffer2() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-buffer2.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-buffer2.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-buffer2.out"));
        }

        /** 
         * Buffering default
         */
        public void testBuffer3() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-buffer3.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-buffer3.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-buffer3.out"));
        }

        /** 
         * Cache control, placebo
         */
        public void testCacheControl1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cachecontrol1.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("private", con.getHeaderField("Cache-Control"));
            AspTest.save(content, "objects/out/test-response-cachecontrol1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-cachecontrol1.out"));
        }

        /** 
         * Cache control, explicitly set to public.
         */
        public void testCacheControl2() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cachecontrol2.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("Public", con.getHeaderField("Cache-Control"));
            AspTest.save(content, "objects/out/test-response-cachecontrol2.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-cachecontrol2.out"));
        }

        /** 
         * Cache control, explicitly set to private.
         */
        public void testCacheControl3() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-cachecontrol3.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("Private", con.getHeaderField("Cache-Control"));
            AspTest.save(content, "objects/out/test-response-cachecontrol3.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-cachecontrol3.out"));
        }

        /**
         * charset, setting
         */
        public void testCharset1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-charset1.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("text/html;charset=ISO-8859-7",
                con.getHeaderField("Content-Type"));
            AspTest.save(content, "objects/out/test-response-charset1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-charset1.out"));
        }

        /**
         * Content-Type, setting
         */
        public void testContentType1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-contenttype1.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("text/plain",
                con.getHeaderField("Content-Type"));
            AspTest.save(content, "objects/out/test-response-contenttype1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-contenttype1.out"));
        }
        
        /**
         * Expires, ten minutes
         */
        public void testExpires1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-expires1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-expires1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-expires1.out"));
        }

        /**
         * Expires, zero minutes
         */
        public void testExpires2() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-expires2.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-expires2.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-expires2.out"));
        }

        /**
         * Expires absolute, future date
         */
        public void testExpiresAbsolute1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-expiresabsolute1.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("Thu, 26 Jan 2012 05:00:00 GMT",
                con.getHeaderField("Expires"));
            AspTest.save(content, "objects/out/test-response-expiresabsolute1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-expiresabsolute1.out"));
        }

        /**
         * Expires absolute, past date
         */
        public void testExpiresAbsolute2() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-expiresabsolute2.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("Sat, 26 Jan 1980 05:00:00 GMT",
                con.getHeaderField("Expires"));
            AspTest.save(content, "objects/out/test-response-expiresabsolute2.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-expiresabsolute2.out"));
        }

        /**
         * Add a header to the output.
         */
        public void testAddHeader1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-addheader1.asp");
            String content = AspTest.getContent(con);
            Assert.assertEquals("This is a test",
                con.getHeaderField("X-Test"));
            AspTest.save(content, "objects/out/test-response-addheader1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-addheader1.out"));
        }

        /** Test binary write statement */
        public void testBinaryWrite1() throws AspException,
                MalformedURLException, IOException
        {
            HttpURLConnection con = (HttpURLConnection)
              AspTest.getConnection("objects/test-response-binarywrite1.asp");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            AspTest.writeContent(con, "some=data&some=more+data&even=more+data");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-binarywrite1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-binarywrite1.out"));
        }


        /**
         * Clear the output buffer
         */
        public void testClear1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-clear1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-clear1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-clear1.out"));
        }

        /**
         * end processing
         */
        public void testEnd1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-end1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-end1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-end1.out"));
        }


        /**
         * Flush the output buffer, this doesn't test it very well.
         */
        public void testFlush1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-flush1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-flush1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-flush1.out"));
        }

        /**
         * Redirects the browser.
         */
        public void testRedirect1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-redirect1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-redirect1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-redirect1.out"));
        }
   
        /**
         * Writes output to the client.
         */
        public void testWrite1() throws AspException,
                MalformedURLException, IOException
        {
            URLConnection con = 
              AspTest.getConnection("objects/test-response-write1.asp");
            String content = AspTest.getContent(con);
            AspTest.save(content, "objects/out/test-response-write1.out");
            Assert.assertTrue(AspTest.compare(content,
                    "objects/expect/test-response-write1.out"));
        }

    }
}

