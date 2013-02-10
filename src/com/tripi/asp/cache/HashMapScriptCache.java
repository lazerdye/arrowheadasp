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

import java.util.Map;

import org.apache.log4j.Logger;

import com.tripi.asp.AspContext;

/**
 * This class implements a "static" script cache, all scripts are stored in
 * an internal hashtable.
 */
public class HashMapScriptCache implements ScriptCache {
	private final Logger LOG = Logger.getLogger(HashMapScriptCache.class);
	
	private Map scriptCache = new java.util.HashMap();

	/**
	 * @see com.tripi.asp.cache.ScriptCache#get(java.lang.String, com.tripi.asp.AspContext)
	 */
	public synchronized CachedScript get(String filename, AspContext context) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Get: " + filename);
		}
		CachedScript script = (CachedScript)scriptCache.get(filename);
		if (script == null)
		{
			script = new CachedScript(context);
			scriptCache.put(filename, script);
		}
		return script;
	}
}
