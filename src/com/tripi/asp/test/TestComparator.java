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

import org.apache.log4j.Logger;
import junit.framework.*;
import jregex.*;

/**
 * TestComparator is a class which can compare a string to a template,
 * including embedded regular expressions.
 * 
 * @author Terence Haddock
 */
public class TestComparator
{
    /** Debugging category */
    static final Logger DBG = Logger.getLogger(TestComparator.class);

    /** Template */
    String  templ;

    /**
     * Constructor with template.
     * @param templ Template to use
     */
    public TestComparator(String templ)
    {
        /** XXX Only temporary */
        org.apache.log4j.BasicConfigurator.configure();
        this.templ = templ;
    }

    /** 
     * Tests if the current string matches the template.
     * @param str String to test
     */
    public boolean matches(String str)
    {
        int strIndex = 0;
        int tmpIndex = 0;

        final Pattern p = new Pattern("[\\^][^\\^\\$]*\\$");
        Matcher m = p.matcher(templ);
        MatchIterator mi = m.findAll();
        MatchResult mr = null;
        boolean done = !mi.hasMore();
        if (mi.hasMore()) mr = mi.nextMatch();
        while (!done)
        {
            String regex = mr.toString();
            if (DBG.isDebugEnabled()) {
                DBG.debug("Prefix: " + mr.prefix());
                DBG.debug("Regex: " + regex);
                DBG.debug("End: " + mr.end());
            }
            /* Compare everything up to the regex pattern */
            int diff = mr.start() - tmpIndex;
            if (DBG.isDebugEnabled())
                DBG.debug("Diff: " + diff);
            if (!simpleMatch(str.substring(strIndex, strIndex + diff),
                    templ.substring(tmpIndex, tmpIndex + diff))) {
                if (DBG.isDebugEnabled())
                    DBG.debug("Failed simple match");
                return false;
            }
            strIndex += diff;
            tmpIndex = mr.end();
            if (DBG.isDebugEnabled()) {
                DBG.debug("After prefix match");
                DBG.debug("strIndex: " + strIndex);
                DBG.debug("tmpIndex: " + tmpIndex);
            }

            /* Figure out what to match */
            int nextMatch = templ.length();
            boolean last;
            if (mi.hasMore()) {
                mr = mi.nextMatch();
                nextMatch = mr.start();
                last = false;
            } else {
                done = true;
                last = true;
            }
            String toFind = templ.substring(tmpIndex, nextMatch);
            if (DBG.isDebugEnabled()) {
                DBG.debug("String to find: [" + toFind + "]");
            }
            int nextstrIndex;
            if (last)
            {
                nextstrIndex = str.substring(strIndex).lastIndexOf(toFind);
                if (nextstrIndex == -1) {
                    if (DBG.isDebugEnabled())
                        DBG.debug("Failed to find next string.");
                    return false;
                }
                nextstrIndex += strIndex;
            } else {
                nextstrIndex = str.substring(strIndex).indexOf(toFind);
                if (nextstrIndex == -1) {
                    if (DBG.isDebugEnabled())
                        DBG.debug("Failed to find next string.");
                    return false;
                }
                nextstrIndex += strIndex;
            }
            String subString = str.substring(strIndex, nextstrIndex);
            /* Here we compare the string to the regex prepared */
            if (DBG.isDebugEnabled()) {
                DBG.debug("String to match: [" + subString + "]");
            }
            Pattern sp = new Pattern(regex);
            Matcher sm = sp.matcher(subString);
            if (!sm.matches()) return false;
            strIndex = nextstrIndex;
        }
        if (!simpleMatch(str.substring(strIndex), templ.substring(tmpIndex)))
            return false;
        return true;
    }

    /**
     * Internal function which performs a 'simple' match, matching two
     * strings handling the ^^ escape sequence.
     * @param str String to compare
     * @param templ Template to user
     */
    public boolean simpleMatch(String str, String templ)
    {
        if (DBG.isDebugEnabled()) {
            DBG.debug("Str: [" + str + "]");
            DBG.debug("tmp: [" + templ + "]");
        }
        final Pattern p = new Pattern("\\^\\^");
        Replacer r = p.replacer("\\^");
        String replaced = r.replace(templ);
        return replaced.equals(str);
    }

    /**
     * Test suite
     * @return tests for this class
     */
    public static Test suite()
    {
        return new TestSuite(TestComparatorTests.class);
    }

    /**
     * Test class which contains tests for the TestComparator class
     */
    public static class TestComparatorTests extends TestCase
    {
        /**
         * Constructor.
         * @param name Test name
         */
        public TestComparatorTests(String name)
        {
            super(name);
        }

        /**
         * Basic test case, matches.
         */
        public void testBasicMatch()
        {
            String templ = "Hello this is a test.";
            String input = "Hello this is a test.";
            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        public void testBasicMatchWithNewlines()
        {
            String templ = "Hello this is a test.\nMulti line test\nCha cha cha\n";
            String input = "Hello this is a test.\nMulti line test\nCha cha cha\n";
            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        public void testBasicMatchWithCarets()
        {
            String templ = "Hello this is a test. ^^ Gotta ^^ match these ^^ chars.";
            String input = "Hello this is a test. ^ Gotta ^ match these ^ chars.";
            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        /**
         * Basic test case, does not match.
         */
        public void testBasicNoMatch1()
        {
            String templ = "Hello this is a test.";
            String input = "Hello this is not a test.";
            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(!cmp.matches(input));
        }

        public void testBasicNoMatch2()
        {
            String templ = "Hello this is a test. Too much in the template.";
            String input = "Hello this is a test.";
            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(!cmp.matches(input));
        }

        public void testBasicNoMatch3()
        {
            String templ = "Hello this is a test.";
            String input = "Hello this is a test. Too little in the template.";
            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(!cmp.matches(input));
        }
    
        public void testBasicNoMatch4()
        {
            String templ = "Template has a prefix. Hello this is a test.";
            String input = "Hello this is a test.";
            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(!cmp.matches(input));
        }
        

        public void testBasicNoMatch5()
        {
            String templ = "Hello this is a test.";
            String input = "Input has a prefix. Hello this is a test.";
            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(!cmp.matches(input));
        }

        public void testSimpleRegex1()
        {
            String templ = "This is a ^[a-zA-Z]*$.";
            String input = "This is a test.";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        public void testSimpleRegex2()
        {
            String templ = "This is a ^[a-zA-Z]*$.";
            String input = "This is a frog.";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        public void testSimpleRegex3()
        {
            String templ = "This is a ^[a-zA-Z]*$.";
            String input = "This is a cha cha cha.";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(!cmp.matches(input));
        }

        public void testSimpleRegex4()
        {
            String templ = "This is a ^[a-zA-Z]*$.";
            String input = "This is a 1stplace.";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(!cmp.matches(input));
        }

        public void testSimpleRegex5()
        {
            String templ = "This is a ^[a-zA-Z]*$.";
            String input = "This is a .";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        public void testSimpleRegex6()
        {
            String templ = "^[a-zA-Z]*$ is a test.";
            String input = "This is a test.";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        public void testSimpleRegex7()
        {
            String templ = "This is a ^[a-zA-Z]*$";
            String input = "This is a test";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        public void testMultipleRegex1()
        {
            String templ = "^[a-zA-Z]*$ blah ^[0-9]*$ cha cha ^quack|crack$";
            String input = "qjkej blah 90839829 cha cha quack";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        public void testMultipleRegex2()
        {
            String templ = "^[a-zA-Z]*$ blah ^[0-9]*$ cha cha ^quack|crack$";
            String input = "jdjlk blah  cha cha crack";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(cmp.matches(input));
        }

        public void testMultipleRegex3()
        {
            String templ = "^[a-zA-Z]*$ blah ^[0-9]*$ cha cha ^quack|crack$";
            String input = "jdjlk blah da90d cha cha crack";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(!cmp.matches(input));
        }

        public void testMultipleRegex4()
        {
            String templ = "^[a-zA-Z]*$ blah ^[0-9]*$ cha cha ^quack|crack$";
            String input = "kjdjkdjkl blh 39089389 cha cha quack";

            TestComparator cmp = new TestComparator(templ);
            Assert.assertTrue(!cmp.matches(input));
        }
    }
}

