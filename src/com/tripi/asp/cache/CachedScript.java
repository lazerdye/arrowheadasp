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
package com.tripi.asp.cache;

import java.io.Serializable;

import com.tripi.asp.AspContext;
import com.tripi.asp.AspFileFactory;
import com.tripi.asp.Node;

/**
 * This object contains information about a cached script.
 */
public class CachedScript implements Serializable {
	/** File factory for this script */
	public AspFileFactory fileFactory;

	/** Node representation of script */
	public Node node;

	/** Last time file was checked */
	public long checkedTime;
	
	public CachedScript(AspContext context) {
		fileFactory = new AspFileFactory(context);
	}
}
