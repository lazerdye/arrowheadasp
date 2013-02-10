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

import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;

import com.tripi.asp.util.ParseQueryString;

/**
 * This class test the ParseQueryString class
 * 
 * @author Terence Haddock
 */
public class ParseQueryStringTest extends TestCase
{
    /**
     * Constructor.
     * @param name Test name
     */
    public ParseQueryStringTest(String name)
    {
        super(name);
        BasicConfigurator.configure();
    }

    public void testEmptyString()
    {
        Map map = ParseQueryString.parse("");
        Assert.assertEquals(0, map.size());    
    }
    
    public void testEscape1()
    {
        Map map = ParseQueryString.parse("a=the+value");
        Assert.assertEquals(1, map.size());  
        String aValue[] = (String[])map.get("a");
        Assert.assertEquals(1, aValue.length);
        Assert.assertEquals("the value", aValue[0]);  
    }
    
    public void testEscape2()
    {
        Map map = ParseQueryString.parse("a=the%26value");
        Assert.assertEquals(1, map.size());  
        String aValue[] = (String[])map.get("a");
        Assert.assertEquals(1, aValue.length);
        Assert.assertEquals("the&value", aValue[0]);  
    }
    
    public void testEscape3()
    {
        Map map = ParseQueryString.parse("a=the%2d%2Dvalue");
        Assert.assertEquals(1, map.size());  
        String aValue[] = (String[])map.get("a");
        Assert.assertEquals(1, aValue.length);
        Assert.assertEquals("the--value", aValue[0]);  
    }
    
    public void testSimpleStringNoValue1()
    {
        Map map = ParseQueryString.parse("a");
        Assert.assertEquals(1, map.size());  
        String aValue[] = (String[])map.get("a");
        Assert.assertEquals(1, aValue.length);
        Assert.assertEquals("", aValue[0]);  
    }

    public void testSimpleStringValue1()
    {
        Map map = ParseQueryString.parse("a=1");
        Assert.assertEquals(1, map.size());    
        String aValue[] = (String[])map.get("a");
        Assert.assertEquals(1, aValue.length);
        Assert.assertEquals("1", aValue[0]);  
    }

    public void testSimpleStringValue2()
    {
        Map map = ParseQueryString.parse("a=value=5=4");
        Assert.assertEquals(1, map.size());    
        String aValue[] = (String[])map.get("a");
        Assert.assertEquals(1, aValue.length);
        Assert.assertEquals("value=5=4", aValue[0]);  
    }

    public void testMultipleValues1()
    {
        Map map = ParseQueryString.parse("a=1&b=2");
        Assert.assertEquals(2, map.size());   
        String aValue[] = (String[])map.get("a");
        Assert.assertEquals(1, aValue.length);
        Assert.assertEquals("1", aValue[0]);  
        String bValue[] = (String[])map.get("b");
        Assert.assertEquals(1, bValue.length);
        Assert.assertEquals("2", bValue[0]);   
    }

    public void testMultipleValues2()
    {
        Map map = ParseQueryString.parse("a=1&b=2&c=3");
        Assert.assertEquals(3, map.size());    
        String aValue[] = (String[])map.get("a");
        Assert.assertEquals(1, aValue.length);
        Assert.assertEquals("1", aValue[0]);  
        String bValue[] = (String[])map.get("b");
        Assert.assertEquals(1, bValue.length);
        Assert.assertEquals("2", bValue[0]);   
        String cValue[] = (String[])map.get("c");
        Assert.assertEquals(1, cValue.length);
        Assert.assertEquals("3", cValue[0]);   
    }

    public void testMultipleValues3()
    {
        Map map = ParseQueryString.parse("a=1&b=first&b=second&c=3&b=third");
        Assert.assertEquals(3, map.size());
        Object bObject = map.get("b");
        Assert.assertSame(String[].class, bObject.getClass());
        String[] bArray = (String[])bObject;
        Assert.assertEquals(3, bArray.length);
        Assert.assertEquals("first", bArray[0]);
        Assert.assertEquals("second", bArray[1]);
        Assert.assertEquals("third", bArray[2]);
    }
}

