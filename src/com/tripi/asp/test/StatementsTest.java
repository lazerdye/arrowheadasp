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

import java.io.IOException;
import junit.framework.*;

/**
 * The StatementsTest class contains the unit tests for VBScript statements.
 * @author Terence Haddock
 */
public class StatementsTest extends TestCase
{
    /**
     * JUnit test suite factory. Returns a test suite containing this
     * class's tests.
     * @return new test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite("ASP Statements");
        suite.addTest(new TestSuite(StatementsTest.class));
        return suite;
    }

    /**
     * Test suite constructor.
     * @param name test name
     */
    public StatementsTest(String name)
    {
        super(name);
    }

    /** ByRef test 1 */
    public void testByRef1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-byref1"));
    }

    /** ByRef test 2 */
    public void testByRef2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-byref2"));
    }

    /** Call function test 1 */
    public void testCall1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-call1"));
    }

    /** Call function test 2 */
    public void testCall2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-call2"));
    }

    /** Call function test 3 */
    public void testCall3() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-call3"));
    }

    /** Call function test 4 */
    public void testCall4() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-call4"));
    }

    /** Dim function test 1 */
    public void testDim1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-dim1"));
    }

    /** Dim function test 2 */
    public void testDim2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-dim2"));
    }

    /** Dim function test 3 */
    public void testDim3() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-dim3"));
    }

    /** Do statement test 1 */
    public void testDo1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-do1"));
    }

    /** Do statement erase 1 */
    public void testErase1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-erase1"));
    }

    /** Do statement exit 1 */
    public void testExit1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-exit1"));
    }

    /** Do statement exit 2 */
    public void testExit2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-exit2"));
    }

    /** Do statement exit 3 */
    public void testExit3() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-exit3"));
    }

    /** Do statement exit 4 */
    public void testExit4() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-exit4"));
    }

    /** Do statement exit 5 */
    public void testExit5() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-exit5"));
    }

    /** Do statement for 1 */
    public void testFor1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-for1"));
    }

    /** Do statement foreach 1 */
    public void testForEach1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-foreach1"));
    }

    /** Do statement function 1 */
    public void testFunction1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-function1"));
    }

    /** Do statement if 1 */
    public void testIf1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-if1"));
    }

    /** Do statement option explicit 1 */
    public void testOptionExplicit1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-optionexplicit1"));
    }

    /** Do statement option explicit 2 */
    public void testOptionExplicit2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-optionexplicit2"));
    }

    /** Do statement option explicit 3 */
    public void testOptionExplicit3() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-optionexplicit3"));
    }

    /** Do statement redim 1 */
    public void testReDim1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-redim1"));
    }

    /** Do statement redim 2 */
    public void testReDim2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-redim2"));
    }

    /** Do statement redim 3 */
    public void testReDim3() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-redim3"));
    }

    /** Do statement redim 4 */
    public void testReDim4() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-redim4"));
    }

    /** Do statement rem 1 */
    public void testRem1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-rem1"));
    }

    /** Do statement select 1 */
    public void testSelect1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-select1"));
    }

    /** Do statement select 2 */
    public void testSelect2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-select2"));
    }

    /** Do statement set 1 */
    /* XXX Removed because I can't figure out what is proper and what is not
    public void testSet1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-set1"));
    }
    */

    /** Do statement set 2 */
    /* XXX Removed because I can't figure out what is proper and what is not
    public void testSet2() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-set2"));
    }
    */

    /** Do statement set 3 */
    /* XXX Removed because I can't figure out what is proper and what is not
    public void testSet3() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("statements", "test-set3"));
    }
    */

    /** Do statement set 4 */
    public void testSet4() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-set4"));
    }

    /** Do statement sub 1 */
    public void testSub1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-sub1"));
    }

    /** Do statement while 1 */
    public void testWhile1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("statements", "test-while1"));
    }
}

