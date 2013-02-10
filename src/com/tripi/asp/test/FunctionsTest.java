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
 * The FunctionsTest class contains the unit tests for the global functions.
 * @author Terence Haddock
 */
public class FunctionsTest extends TestCase
{
    /**
     * JUnit test suite factory. Returns a test suite containing this
     * class's tests.
     * @return new test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite("ASP Functions");
        suite.addTest(new TestSuite(FunctionsTest.class));
        return suite;
    }

    /**
     * Test suite constructor.
     * @param name test name
     */
    public FunctionsTest(String name) throws AspException
    {
        super(name);
    }

    /** Absolute function test */
    public void test01_abs() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test01-abs"));
    }

    public void test02_array() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test02-array"));
    }

    public void test03_asc() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test03-asc"));
    }

    public void test04_atn() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test04-atn"));
    }

    public void test05_1_cbool() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test05.1-cbool"));
    }

    public void test05_2_cbool() throws IOException
    {
        /* The server should output an error. */
        Assert.assertTrue(AspTest.doSimpleFailureTest("functions", "test05.2-cbool"));
    }

    public void test06_1_cbyte() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test06.1-cbyte"));
    }

    public void test06_2_cbyte() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("functions", "test06.2-cbyte"));
    }

    public void test06_3_cbyte() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("functions", "test06.3-cbyte"));
    }

    public void test07_cint() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test07-cint"));
    }

    public void test08_clng() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test08-clng"));
    }

    public void test09_cos() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test09-cos"));
    }

    public void test10_createobject() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test10-createobject"));
    }

    public void test11_csng() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test11-csng"));
    }

    public void test12_1_cstr() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test12.1-cstr"));
    }

    public void test12_2_cstr() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleFailureTest("functions", "test12.2-cstr"));
    }

    public void test13_date() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test13-date"));
    }

    public void test14_dateadd() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test14-dateadd"));
    }

    public void test15_datediff() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test15-datediff"));
    }

    public void test16_datepart() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test16-datepart"));
    }

    public void test17_dateserial() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test17-dateserial"));
    }

    public void test18_datevalue() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test18-datevalue"));
    }

    public void test19_day() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test19-day"));
    }

    public void test20_exp() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test20-exp"));
    }

    public void test21_fix() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test21-fix"));
    }

    public void test22_formatdatetime() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test22-formatdatetime"));
    }

    public void test23_hex() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test23-hex"));
    }

    public void test24_hour() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test24-hour"));
    }

    public void test25_instr() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test25-instr"));
    }

    public void test26_instrrev() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test26-instrrev"));
    }

    public void test27_int() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test27-int"));
    }

    public void test28_isarray() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test28-isarray"));
    }

    public void test29_isdate() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test29-isdate"));
    }

    public void test30_isempty() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test30-isempty"));
    }

    public void test31_isnull() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test31-isnull"));
    }

    public void test32_isnumeric() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test32-isnumeric"));
    }

    public void test33_isobject() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test33-isobject"));
    }

    public void test34_join() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test34-join"));
    }

    public void test35_lbound() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test35-lbound"));
    }

    public void test36_lcase() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test36-lcase"));
    }

    public void test37_left() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test37-left"));
    }

    public void test38_len() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test38-len"));
    }

    public void test39_log() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test39-log"));
    }

    public void test40_ltrim() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test40-ltrim"));
    }

    public void test41_mid() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test41-mid"));
    }

    public void test42_minute() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test42-minute"));
    }

    public void test43_month() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test43-month"));
    }

    public void test44_monthname() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test44-monthname"));
    }

    public void test45_now() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test45-now"));
    }

    public void test46_oct() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test46-oct"));
    }

    public void test47_replace() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test47-replace"));
    }

    public void test48_rgb() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test48-rgb"));
    }

    public void test49_right() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test49-right"));
    }

    public void test50_rnd() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test50-rnd"));
    }

    public void test51_round() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test51-round"));
    }

    public void test52_rtrim() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test52-rtrim"));
    }

    public void test53_scriptengine() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test53-scriptengine"));
    }

    public void test54_second() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test54-second"));
    }

    public void test55_sgn() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test55-sgn"));
    }

    public void test56_sin() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test56-sin"));
    }

    public void test57_space() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test57-space"));
    }

    public void test58_split() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test58-split"));
    }

    public void test59_sqr() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test59-sqr"));
    }

    public void test60_strcomp() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test60-strcomp"));
    }

    public void test61_strreverse() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test61-strreverse"));
    }

    public void test62_string() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test62-string"));
    }

    public void test63_tan() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test63-tan"));
    }

    public void test64_time() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test64-time"));
    }

    public void test65_timeserial() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test65-timeserial"));
    }

    public void test66_trim() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test66-trim"));
    }

    public void test67_ubound() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test67-ubound"));
    }

    public void test68_ucase() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test68-ucase"));
    }

    public void test69_weekday() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test69-weekday"));
    }

    public void test70_weekdayname() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test70-weekdayname"));
    }

    public void test71_year() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test71-year"));
    }

    public void test72_cdate() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test72-cdate"));
    }
    public void test73_formatcurrency() throws IOException
    {
        Assert.assertTrue(AspTest.doSimpleTest("functions", "test73-formatcurrency"));
    }
}
