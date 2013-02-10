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
package com.tripi.asp.msxml2;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * This class implements msxml2.DOMNamedNodeMap. 
 *
 * Contributed by Jim Horner &lt;jhorner@arinbe.com&gt;
 */
public class DOMNamedNodeMap {

	/** Debugging category. */
	Logger DBG = Logger.getLogger(DOMNamedNodeMap.class);
	
	private HashMap map;
	
	public DOMNamedNodeMap () {
		map = new HashMap();
	}

	public DOMNode getNamedItem(String name) {
		return (DOMNode) map.get(name);
	}
	
	public DOMNode Item(int index) {
		// might need to use something other than a map
		return null;
	}
	
	public DOMNode SetNamedItem(DOMNode attribute) {
		
		map.put(attribute._NodeName, attribute);

		return attribute;
	}	

	public int Length() {
		return this.map.size();
	}
		
	// cheat method
	public Collection Items(){
		return map.values();
	}
	
}
