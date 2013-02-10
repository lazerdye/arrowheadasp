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

import junit.framework.*;
import java.util.Enumeration;
import com.tripi.asp.*;

/**
 * This class tests the AspCollection object.
 *
 * @author Terence Haddock
 */
public class AspCollectionTest extends TestCase
{
    /**
     * Constructor.
     * @param name Test name
     */
    public AspCollectionTest(String name)
    {
        super(name);
    }

    /** Caches versions of integers */
    public static final Integer zero = new Integer(0);
    public static final Integer one = new Integer(1);
    public static final Integer two = new Integer(2);
    public static final Integer three = new Integer(3);

    /** Test put by index */
    public void testPutByIndex() throws AspException
    {
        AspCollection col = new AspCollection();
        col.put(three, "What's up?");
        col.put(one, "Hello there");
        col.put(two, "How are you?");
        Assert.assertEquals("Hello there", col.get(one));
        Assert.assertEquals("How are you?", col.get(two));
        Assert.assertEquals("What's up?", col.get(three));
    }

    /** Test put by key */
    public void testPutByKey() throws AspException
    {
        AspCollection col = new AspCollection();
        col.put("Hello", "What's up?");
        Assert.assertEquals("What's up?", col.get("Hello"));
        Assert.assertEquals("What's up?", col.get("HELLO"));
        Assert.assertEquals("What's up?", col.get("hello"));
        Assert.assertEquals("What's up?", col.get("hElLo"));
    }

    /** Test put by key/index mix */
    public void testPutByKeyIndex() throws AspException
    {
        AspCollection col = new AspCollection();
        col.put("Hello", "First Row");
        col.put("There", "Second Row");
        col.put("George", "Third Row");
        col.put(two, "Second Row Revisited");
        Assert.assertEquals("First Row", col.get(one));
        Assert.assertEquals("Second Row Revisited", col.get(two));
        Assert.assertEquals("Third Row", col.get(three));
        Assert.assertEquals("First Row", col.get("Hello"));
        Assert.assertEquals("Second Row Revisited", col.get("There"));
        Assert.assertEquals("Third Row", col.get("George"));
    }

    /** Test getting non-existant element */
    public void testGetNonExistant() throws AspException
    {
        AspCollection col = new AspCollection();
        Assert.assertEquals(Constants.undefinedValueNode, col.get("chacha"));
    }

    /** Test ContainsKey */
    public void testContainsKey() throws AspException
    {
        AspCollection col = new AspCollection();
        col.put("Hello", "First Row");
        col.put("There", "Second Row");
        col.put("George", "Third Row");
        Assert.assertTrue(col.containsKey("Hello"));
        Assert.assertTrue(col.containsKey("HELLO"));
        Assert.assertTrue(col.containsKey("hElLo"));
        Assert.assertTrue(col.containsKey("There"));
        Assert.assertTrue(col.containsKey("George"));
        Assert.assertTrue(!col.containsKey("NotGeorge"));
    }

    /** Test keys enumeration */
    public void testKeysEnumeration() throws AspException
    {
        AspCollection col = new AspCollection();
        col.put("One", "First");
        col.put("Two", "Second");
        col.put("Three", "Third");
        Enumeration enKeys = col.keys();
        Assert.assertEquals("One", (String)enKeys.nextElement());
        Assert.assertEquals("Two", (String)enKeys.nextElement());
        Assert.assertEquals("Three", (String)enKeys.nextElement());
    }

    /** Test elements enumeration */
    public void testElementsEnumeration() throws AspException
    {
        AspCollection col = new AspCollection();
        col.put("One", "First");
        col.put("Two", "Second");
        col.put("Three", "Third");
        Enumeration enKeys = col.elements();
        boolean hasFirst = false, hasSecond = false, hasThird = false;
        while (enKeys.hasMoreElements())
        {
            String key = (String)enKeys.nextElement();
            if (key.equals("First")) {
                Assert.assertTrue(!hasFirst);
                hasFirst = true;
            } else if (key.equals("Second")) {
                Assert.assertTrue(!hasSecond);
                hasSecond = true;
            } else if (key.equals("Third")) {
                Assert.assertTrue(!hasThird);
                hasThird = true;
            }
        }
        Assert.assertTrue(hasFirst);
        Assert.assertTrue(hasSecond);
        Assert.assertTrue(hasThird);
    }
}

