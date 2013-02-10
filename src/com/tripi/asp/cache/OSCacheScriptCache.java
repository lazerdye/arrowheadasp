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

import org.apache.log4j.Logger;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.tripi.asp.AspContext;

import java.io.Serializable;


/**
 * This class implements a script cache with an OSCache backend.
 */
public class OSCacheScriptCache implements ScriptCache, Serializable {
	private final transient Logger LOG = Logger.getLogger(OSCacheScriptCache.class);
	
	private Cache cache;

    public OSCacheScriptCache()
    {
		GeneralCacheAdministrator admin = new GeneralCacheAdministrator();
		cache = admin.getCache();
		assert(cache != null);
    }
	
	/**
	 * Get the cached script specified by the given filename/context.
	 * @see ScriptCache#get(String, AspContext)
	 */
	public synchronized CachedScript get(String filename, AspContext context) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Get: " + filename);
		}
		CachedScript script;
		try {
			script = (CachedScript)cache.getFromCache(filename);
            script.fileFactory.setContext(context);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Old cached.");
                LOG.debug("Node: " + script.node);
            }
		} catch(NeedsRefreshException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Needs refreshed: " + ex.toString());
            }
			script = new CachedScript(context);
			cache.putInCache(filename, script);
		};
		return script;
	}

    public synchronized void refresh(String filename, CachedScript script)
    {
        cache.putInCache(filename, script);
    }

}
