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
import junit.framework.*;
import java.io.IOException;

/**
 * The MiscTest class contains the unit tests for ASP operators
 * @author Terence Haddock
 */
public class MiscTest extends TestCase
{
    /**
     * JUnit test suite factory. Returns a test suite containing this
     * class's tests.
     * @return new test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite("Miscellaneous test");
        suite.addTest(new TestSuite(MiscTest.class));
        return suite;
    }

    /**
     * Test suite constructor.
     * @param name test name
     */
    public MiscTest(String name) throws AspException
    {
        super(name);
    }

    /** 8Bit test 1 */
    public void test_8bit1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("misc", "test-8bit1"));
    }

    /** Comments test 1 */
    public void test_comments1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("misc", "test-comments1"));
    }

    /** Comments test 2 */
    public void test_comments2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("misc", "test-comments2"));
    }

    /** Continuation test */
    public void test_continuation1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("misc", "test-continuation1"));
    }
}
