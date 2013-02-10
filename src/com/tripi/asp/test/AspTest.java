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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tripi.asp.AspContext;
import com.tripi.asp.AspException;
import com.tripi.asp.IdentNode;
import com.tripi.asp.JavaObjectNode;
import com.tripi.asp.Response;

/**
 * AspTest executes an ASP script directly like a command script.
 *
 * Usage: java com.tripi.asp.AspTest &lt;filename&gt;<br>
 * Where <i>filename</i> is the name of the file.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class AspTest
{
    /** Debugging category */
    static final Logger DBG = Logger.getLogger(AspTest.class);

    /** Log4j file to use */
    static String  param_log4j = "log4j.configure";

    /**
     * Displays the help information file.
     */
    private static void displayHelp()
    {
        System.err.println("Usage: java com.tripi.asp.AspTest [options] <input_file>");
        System.err.println("Options available (options can be abbreviated):");
        System.err.println("  -log4j <file>      log4j configuration file to use");
        System.err.println("  -help              Show this help");
    }

    /**
     * Parameter parser.
     * @param args Runtime arguments
     */
    private static void parseParameters(String args[])
    {
        int i;
        for (i = 0; i < args.length; i++)
        {
            if (args[i].startsWith("-"))
            {
                if (args[i].startsWith("-l")) {
                    i++;
                    if (i >= args.length) {
                        System.err.println("Expected argument for -log4j");
                        displayHelp();
                        System.exit(1);
                    }
                    param_log4j = args[i];
                } else if (args[i].startsWith("-h")) {
                    displayHelp();
                    System.exit(1);
                } else {
                    System.err.println("Unexpected argument: " + args[i]);
                    displayHelp();
                    System.exit(1);
                }
            } else {
                System.err.println("Unexpected argument: " + args[i]);
                displayHelp();
                System.exit(1);
            }
        }
    }

    /**
     * Entry point.
     * 
     * @param args Arguments, see above
     */
    public static void main(String args[])
    {
        parseParameters(args);

        PropertyConfigurator.configure(param_log4j);

        junit.textui.TestRunner.run (suite());
    }

    /**
     * Test suite
     */
    public static Test suite()
    {
        param_log4j = System.getProperty("log4j.configure", "log4j.configure");
        PropertyConfigurator.configure(param_log4j);

        TestSuite suite = new TestSuite("All Test");
        suite.addTest(new TestSuite(AspCollectionTest.class));
        suite.addTest(new TestSuite(ParseQueryStringTest.class));
        suite.addTest(AspParseTest.suite());
        suite.addTest(FunctionsTest.suite());
        suite.addTest(ObjectsTest.suite());
        suite.addTest(StatementsTest.suite());
        suite.addTest(OperatorsTest.suite());
        suite.addTest(MiscTest.suite());
        suite.addTest(ADODBTest.suite());
        return suite;
    }

    /**
     * This function returns the ASP Response object, for testing.
     * @param context AspContext to search
     * @return Internal HTTP Response object
     */
    public static Response getResponse(AspContext context) throws AspException
    {
        JavaObjectNode objNode = (JavaObjectNode)getValue(context, "response");
        return (Response)objNode.getSubObject();
    }

    /**
     * This function is a utility function to obtain the value of a 
     * variable in a context.
     * @param context AspContext to find variable in
     * @param name Name of variable
     */
    public static Object getValue(AspContext context, String name) throws AspException
    {
        return context.getValue(new IdentNode(name));
    }

    /**
     * Obtains the connection for the given relative URL.
     * @param location relative location for the URL
     * @return new URLConnection
     */
    public static URLConnection getConnection(String location)
        throws MalformedURLException, IOException
    {
        String testServer = System.getProperty("asptest.server", "localhost:8080");
        URL url = new URL("http://" + testServer + "/asptest/" + location);
        return url.openConnection();
    }

    /**
     * Saves the text of an output test case.
     * @param text Text to save
     * @param file filename to save to
     */
    public static void save(String content, String filename) throws IOException
    {
    	Writer output = new FileWriter(filename);
    	output.write(content);
    	output.close();
        /*OutputStream output = new FileOutputStream(filename);
        output.write(content.getBytes("UTF-8"));
        output.close();*/
    }

    /**
     * Compares the text to the give template.
     * @param text Text to compare
     * @param filename Filename of template to compare to
     */
    public static boolean compare(String content, String filename)
        throws IOException
    {
        InputStream is = new FileInputStream(filename);
        String temp = getContent(is);
        is.close();

        TestComparator cmp = new TestComparator(temp);
        return cmp.matches(content);
    }

    /**
     * Performs a simple output test.
     * @param directory Sub-directory where tests reside (relative to test
     * directory)
     * @param name Test name
     */
    public static boolean doSimpleTest(String directory, String name)
        throws MalformedURLException, IOException
    {
        InputStream is;
        try {
            URLConnection con = getConnection(directory + "/" + name + ".asp");
            is = con.getInputStream();
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        String content = getContent(is);
        is.close();
        save(content, directory + "/out/" + name + ".out");
        return compare(content, directory + "/expect/" + name + ".out");
    }

    /**
     * Performs a simple output test, which will be a error.
     * @param directory Sub-directory where tests reside (relative to test
     * directory)
     * @param name Test name
     * @param code Error code expected
     */
    public static boolean doSimpleFailureTest(String directory, String name)
        throws MalformedURLException, IOException
    {
        return !doSimpleTest(directory, name);
    }

    /**
     * Gets the full content of the data read in from an input stream.
     * @param is Input Stream to read.
     * @return String as full content of input stream
     */
    public static String getContent(InputStream is) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        String line;
        StringBuffer ret = new StringBuffer();
        while ((line = br.readLine()) != null)
        {
            ret.append(line);
            ret.append("\n");
        }
        return ret.toString();
    }

    /**
     * Gets the full content of this URL connection.
     * @param con connection
     * @return String as full content of URL connection.
     */
    public static String getContent(URLConnection con) throws IOException
    {
        InputStream is = con.getInputStream();
        String content = getContent(is);
        is.close();
        return content;
    }

    /**
     * Writes the full content to the URL connection.
     * @param con connection
     * @param str String to write
     */
    public static void writeContent(URLConnection con, String str)
        throws IOException
    {
        OutputStream os = con.getOutputStream();
        os.write(str.getBytes());
        os.close();
    }

    /**
     * Gets a cookie by name.
     * @param con Connection
     * @param name Cookie name
     * @return the entire Set-Cookie header corresponding to the cookie
     */
    public static String getCookie(URLConnection con, String name)
        throws IOException
    {
        int x = 1;
        while (con.getHeaderFieldKey(x) != null)
        {
            if (con.getHeaderFieldKey(x).equals("Set-Cookie")) {
                String value = con.getHeaderField(x);
                if (value.startsWith(name + "=")) {
                    return value;
                }
            }
            x++;
        }
        return null;
    }
}

