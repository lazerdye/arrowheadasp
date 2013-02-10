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
public class ADODBTest
{
    /**
     * JUnit test suite factory. Returns a test suite containing this
     * class's tests.
     * @return new test suite
     */
    public static Test suite()
    {
        TestSuite suite = new TestSuite("ADODB test");
        suite.addTest(new TestSuite(ConnectionTest.class));
        suite.addTest(new TestSuite(CommandTest.class));
        suite.addTest(new TestSuite(RecordSetTest.class));
        suite.addTest(new TestSuite(FieldTest.class));
        return suite;
    }

    /**
     * This class contains the connection object tests
     */
    public static class ConnectionTest extends TestCase
    {
        /**
         * Test suite constructor.
         * @param name test name
         */
        public ConnectionTest(String name) throws AspException
        {
            super(name);
        }

        /** Cursor Location test 1 */
        public void test_cursorlocation1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-cursorlocation1"));
        }

        /** Errors test 1 */
        public void test_errors1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-errors1"));
        }

        /** Execute test 1 */
        public void test_execute1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-execute1"));
        }

        /** Execute test 2 */
        public void test_execute2() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-execute2"));
        }

        /** Fields test 1 */
        /* XXX Not yet implemented
        public void test_fields1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-fields1"));
        }
        */

        /** Open test 1 */
        public void test_open1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-open1"));
        }

        /** Open test 2 */
        public void test_open2() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-open2"));
        }

        /** Open test 3 */
        public void test_open3() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-open3"));
        }

        /** OpenSchema test 1 */
        /* XXX Not yet implemented
        public void test_openschema1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-openschema1"));
        }
        */

        /** Properties test 1 */
        /* XXX Not yet implemented
        public void test_properties1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-properties1"));
        }
        */

        /** State test 1 */
        public void test_state1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-state1"));
        }

        /** Transactions test 1 */
        public void test_transactions1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-transactions1"));
        }

        /** Transactions test 2 */
        public void test_transactions2() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-connection-transactions2"));
        }
    }

    /**
     * This class contains the ADODB.Command object tests
     */
    public static class CommandTest extends TestCase
    {
        /**
         * Test suite constructor.
         * @param name test name
         */
        public CommandTest(String name) throws AspException
        {
            super(name);
        }

        /** Active Connection test 1 */
        public void test_activeconnection1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-command-activeconnection1"));
        }

        /** Active Connection test 2 */
        public void test_activeconnection2() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-command-activeconnection2"));
        }

        /** Execute test 1 */
        public void test_execute1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-command-execute1"));
        }
    }

    /**
     * This class contains the recordset object tests
     */
    public static class RecordSetTest extends TestCase
    {
        /**
         * Test suite constructor.
         * @param name test name
         */
        public RecordSetTest(String name) throws AspException
        {
            super(name);
        }

        /** Execute activerecordset1 */
        public void test_activerecordset1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-activecommand1"));
        }

        /** Execute activeconnection1 */
        public void test_activeconnection1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-activeconnection1"));
        }

        /** Execute addnew1 */
        public void test_addnew1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-addnew1"));
        }

        /** Execute addnew2 */
        public void test_addnew2() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-addnew2"));
        }

        /** Execute bof1 */
        public void test_bof1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-bof1"));
        }

        /** Execute bof2 */
        public void test_bof2() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-bof2"));
        }

        /** Execute cancelupdate1 */
        public void test_cancelupdate1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-cancelupdate1"));
        }

        /** Execute move1 */
        public void test_move1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-move1"));
        }

        /** Execute move2 */
        public void test_move2() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-move2"));
        }

        /** Execute paging1 */
        public void test_paging1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-paging1"));
        }

        /** Execute position1 */
        public void test_position1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-position1"));
        }

        /** Execute requery1 */
        public void test_requery1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-requery1"));
        }

        /** Execute update1 */
        public void test_update1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-recordset-update1"));
        }
    }

    /**
     * This class contains the field object tests
     */
    public static class FieldTest extends TestCase
    {
        /**
         * Test suite constructor.
         * @param name test name
         */
        public FieldTest(String name) throws AspException
        {
            super(name);
        }

        /** Execute actualsize1 */
        public void test_actualsize1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-field-actualsize1"));
        }

        /** Execute attributes1 */
        /* XXX TODO
        public void test_attributes1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-field-attributes1"));
        }
        */

        /** Test Fields.Count */
        public void test_fieldscount1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-field-count1"));
        }

        /** Execute definedsize1 */
        public void test_definedsize1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-field-definedsize1"));
        }

        /** Execute name1 */
        public void test_name1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-field-name1"));
        }

        /** Execute numericscale1 */
        public void test_numericscale1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-field-numericscale1"));
        }

        /** Execute originalvalue1 */
        /* XXX TODO
        public void test_originalvalue1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-field-originalvalue1"));
        }
        */

        /** Execute precision1 */
        public void test_precision1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-field-precision1"));
        }

        /** Execute type1 */
        /* XXX TODO 
        public void test_type1() throws IOException
        {
            Assert.assertTrue(AspTest.doSimpleTest("ADODB",
                    "test-field-type1"));
        }
        */
    }
}
