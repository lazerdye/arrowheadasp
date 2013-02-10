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
import java.util.Vector;


import org.apache.log4j.Category;

/**
 * This class implements msxml2.DOMNodeList. 
 *
 * Contributed by Jim Horner &lt;jhorner@arinbe.com&gt;
 */
public class DOMNodeList {

	/** Debugging category. */
	Category DBG = Category.getInstance(DOMNodeList.class);
	
	private Vector children;

	public DOMNodeList () {
		children = new Vector();
	}
	
	/**
	 * ASP-Accessible function
	 *   @param index integer specifying the node index
	 * 	 @return the DOMNode at the index
	 */
	public DOMNode Item(int index) {
		
		DOMNode result = null;
		result = (DOMNode) children.get(index);

		return result;
	}

	/**
	 * ASP-Accessible function
	 *   @param node DOMNode to append to list
	 */
	public void AppendNode (DOMNode node) {
		children.add(node);
	}
	
	/**
	 * ASP-Accessible function
	 *   @return the size of the node list
	 */
	public int Length() {
		return children.size();
	}

	// cheat method
	public Collection Items() {
		return this.children;
	}

}
