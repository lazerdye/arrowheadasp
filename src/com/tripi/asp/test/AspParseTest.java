/**
 * ArrowHead ASP Server 
 * This is a source file for the ArrowHead ASP Server - an 100% Java
 * VBScript interpreter and ASP server.
 *
 * For more information, see http://www.tripi.com/arrowhead
 *
 * Copyright (C) 2004  Terence Haddock
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
import junit.framework.*;
import java.io.IOException;

/**
 * The MiscTest class contains the unit tests for ASP operators
 * @author Terence Haddock
 */
public class AspParseTest extends TestCase
{
    /**
     * JUnit test suite factory. Returns a test suite containing this
     * class's tests.
     * @return new test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite("ASP parsing test");
        suite.addTest(new TestSuite(AspParseTest.class));
        return suite;
    }

    /**
     * Test suite constructor.
     * @param name test name
     */
    public AspParseTest(String name) throws AspException
    {
        super(name);
    }

    /** html test 1 */
    public void test_html1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-html1"));
    }
    
    /** include test 1 */
    public void test_include1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-include1"));
    }
    
    /** include test 2 */
    public void test_include2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-include2"));
    }
    
    /** include test 3 */
    /*public void test_include3() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-include3"));
    }*/
    
    /** script script 1 */
    public void test_script1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-script1"));
    }
    
    /** Simple ASP 1 */
    public void test_simpleasp1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-simpleasp1"));
    }
    
    /** Simple ASP 2 */
    public void test_simpleasp2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-simpleasp2"));
    }
    
    /** Simple ASP 3 */
    public void test_simpleasp3() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-simpleasp3"));
    }
    
    /** Simple ASP 4 */
    public void test_simpleasp4() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-simpleasp4"));
    }
    
    /** Simple ASP 5 */
    public void test_simpleasp5() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("aspparse", "test-simpleasp5"));
    }
}
