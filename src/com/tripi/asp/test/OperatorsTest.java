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
 * The OperatorsTest class contains the unit tests for ASP operators
 * @author Terence Haddock
 */
public class OperatorsTest extends TestCase
{
    /**
     * JUnit test suite factory. Returns a test suite containing this
     * class's tests.
     * @return new test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite("ASP Operators");
        suite.addTest(new TestSuite(OperatorsTest.class));
        return suite;
    }

    /**
     * Test suite constructor.
     * @param name test name
     */
    public OperatorsTest(String name) throws AspException
    {
        super(name);
    }

    /** Addition test */
    public void test_add1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-add1"));
    }

    /** AND test */
    public void test_and1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-and1"));
    }

    /** Concat test */
    public void test_concat1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-concat1"));
    }

    /** Div test */
    public void test_div1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-div1"));
    }

    /** Equality test */
    public void test_eq1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-eq1"));
    }

    /** EQV test */
    public void test_eqv1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-eqv1"));
    }

    /** EXP test */
    public void test_exp1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-exp1"));
    }

    /** Greater-Than-Equal-To test */
    public void test_ge1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-ge1"));
    }

    /** Greater-Than test */
    public void test_gt1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-gt1"));
    }

    /** IMP test */
    public void test_imp1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-imp1"));
    }

    /** IS test */
    public void test_is1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-is1"));
    }

    /** INTDIV test */
    public void test_intdiv1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-intdiv1"));
    }

    /** Less-Than-Equal-To test */
    public void test_le1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-le1"));
    }

    /** Less-Than test */
    public void test_lt1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-lt1"));
    }

    /** MOD test */
    public void test_mod1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-mod1"));
    }

    /** Multiply test */
    public void test_multiply1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-multiply1"));
    }

    /** Inequality test */
    public void test_ne1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-ne1"));
    }

    /** Negation test */
    public void test_negation1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-negation1"));
    }

    /** NOT test */
    public void test_not1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-not1"));
    }

    /** OR test */
    public void test_or1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-or1"));
    }

    /** XOR test */
    public void test_xor1() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("operators", "test-xor1"));
    }

}
