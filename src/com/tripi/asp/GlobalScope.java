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
package com.tripi.asp;

import org.apache.log4j.Logger;

import com.tripi.asp.cache.ScriptCache;

import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


/**
 * The GlobalScope class contains a reference to variables and functions
 * global to the entire ASP application.
 *
 * @author Terence Haddock
 */
public class GlobalScope
{
    /** Debugging category */
    static final private Logger DBG =
        Logger.getLogger(GlobalScope.class);

    /** The scope universal to all ASP scripts. */
    Hashtable universalScope = null;

    /** AspContext object for this global scope */
    AspContext globalContext = null;

    /** Server object for this global scope */
    Server server = null;

    /** Application object for this global scope */
    Application application = null;
    
    /** ScriptCache to use to cache scripts */
    ScriptCache cache = null;

    /** Constructor */
    private GlobalScope() {};

    /** 
     * Obtains the global scope for the specified Servlet context.
     * @param ctx ServletContext this global scope should be based on.
     * @return new or existing global scope relative to servlet context.
     */
    public static GlobalScope getGlobalScope(ServletContext ctx)
        throws AspException
    {
        GlobalScope scope;
        synchronized(ctx)
        {
            scope = (GlobalScope)ctx.getAttribute("ArrowHeadASP_GlobalScope");
            if (scope == null) {
                scope = new GlobalScope();
                ctx.setAttribute("ArrowHeadASP_GlobalScope", scope);
            }
        }
        return scope;
    }

    /**
     * Initialized this scope.
     */
    protected void initializeScope(ServletConfig config) throws AspException
    {
    	getScriptCache(config); 
        ServletContext ctx = config.getServletContext();
        final IdentNode applicationIdent = new IdentNode("application");
        final IdentNode serverIdent = new IdentNode("server");

        universalScope = new Hashtable();
        VBScriptGlobals.initScope(universalScope);
        globalContext = new AspContext(universalScope);

        /* Set the application-level Server object */
        server = new Server(config, null);
        JavaObjectNode serverObj = new JavaObjectNode(server);
        universalScope.put(serverIdent, serverObj);

        /* Set the application-level Application object */
        application = new Application();
        JavaObjectNode applicationObj = new JavaObjectNode(application);
        universalScope.put(applicationIdent, applicationObj);

        /* Load the global.asa file */
        String global = config.getInitParameter("global.asa");
        if (global==null) global = java.io.File.separatorChar + "global.asa";
        AspFileFactory fileFactory = new AspFileFactory(globalContext);
        global = fileFactory.resolveFile(null, global, false);

        if (DBG.isDebugEnabled()) {
            DBG.debug("Reading global.asa file: " + global);
        }

        //java.io.File testExists = new java.io.File(global);
        //if (testExists.exists())
        //{
            AspHandler handler = new AspHandler(globalContext, global, cache);
            if (DBG.isDebugEnabled()) {
                DBG.debug("Parsing global.asa...");
            }
            Node node = handler.parse();
            if (DBG.isDebugEnabled()) {
                DBG.debug("Preparing global.asa...");
            }
            node.prepare(globalContext);
        //} else {
        //    if (DBG.isDebugEnabled()) DBG.debug("global.asa file does not exist");
        //}
    }

    /**
	 * @param config
	 */
	private void getScriptCache(ServletConfig config) {
        ServletContext ctx = config.getServletContext();
        synchronized(ctx)
        {
            cache = (ScriptCache)ctx.getAttribute("ArrowHeadASP_ScriptCache");
            if (cache == null)
            {
		        String clsName = config.getInitParameter("com.tripi.asp.cache.ScriptCache");
		        if (clsName == null) clsName = "com.tripi.asp.cache.HashMapScriptCache";
		        try {
			        Class cls = Class.forName(clsName);
			        cache = (ScriptCache)cls.newInstance();
		        } catch (Exception ex) {
			        throw new Error("Could not create class: " + clsName);
		        }
                ctx.setAttribute("ArrowHeadASP_ScriptCache", cache);
            }
        }
	}

	/**
     * Obtains a new context relative to the global context.
     * @return New context.
     */
    public AspContext createContext()
    {
        return new AspContext(universalScope);
    }

    /**
     * Get the global context.
     * @return AspContext object this global scope defines.
     */
    public AspContext getGlobalContext()
    {
        return globalContext;
    }

    /**
     * Utility function to call the APPLICATION_ONSTART VBScript subroutine.
     */
    public static void callApplicationOnStart(ServletConfig config)
        throws AspException
    {
        ServletContext ctx = config.getServletContext();
        final IdentNode application_onstartIdent =
            new IdentNode("Application_OnStart");

        GlobalScope globalScope = getGlobalScope(ctx);
        if (DBG.isDebugEnabled()) DBG.debug("Global Scope: " + globalScope);

        AspContext tmpContext = (AspContext)globalScope.getGlobalContext().clone();
        if (DBG.isDebugEnabled()) DBG.debug("Global Context: " + tmpContext);

        Object obj = tmpContext.getValue(application_onstartIdent);
        if (!(obj instanceof UndefinedValueNode)) {
            if (DBG.isDebugEnabled())
                DBG.debug("Calling APPLICATION_ONSTART (" + obj + ")");
            ((FunctionNode)obj).execute(new VarListNode(), tmpContext);
        } else {
            if (DBG.isDebugEnabled())
                DBG.debug("APPLICATION_ONSTART does not exist.");
        }
    }

    /**
     * Utility function to call the SESSION_ONSTART VBScript subroutine.
     * @param session Session to use when calling SESSION_ONSTART
     */
    public static void callSessionOnStart(HttpSession session, AspCollection contents)
        throws AspException
    {
        if (DBG.isDebugEnabled())
        {
            DBG.debug("Session: " + session.getId());
            DBG.debug("Collection: " + contents);
        }
        final IdentNode sessionIdent = new IdentNode("Session");
        final IdentNode session_onstartIdent = new IdentNode("Session_OnStart");

        ServletContext ctx = session.getServletContext();
        if (DBG.isDebugEnabled()) DBG.debug("Servlet Context: " + ctx);

        GlobalScope globalScope = getGlobalScope(ctx);
        if (DBG.isDebugEnabled()) DBG.debug("Global Scope: " + globalScope);

        AspContext tmpContext = (AspContext)globalScope.getGlobalContext().clone();
        if (DBG.isDebugEnabled()) DBG.debug("Global Context: " + tmpContext);

        Session tmpSession = new Session(session, contents);
        JavaObjectNode sessionObj = new JavaObjectNode(tmpSession);
        tmpContext.setValue(sessionIdent, sessionObj);

        Object obj = tmpContext.getValue(session_onstartIdent);
        if (!(obj instanceof UndefinedValueNode)) {
            if (DBG.isDebugEnabled())
                DBG.debug("Calling SESSION_ONSTART (" + obj + ")");
            ((FunctionNode)obj).execute(new VarListNode(), tmpContext);
        } else {
            if (DBG.isDebugEnabled())
                DBG.debug("SESSION_ONSTART does not exist.");
        }
    }

    /**
     * Utility function to call the SESSION_ONEND VBScript subroutine.
     * @param session Session to use when calling SESSION_ONEND
     */
    public static void callSessionOnEnd(HttpSession session, AspCollection contents)
        throws AspException
    {
        if (DBG.isDebugEnabled())
        {
            DBG.debug("Session: " + session.getId());
            DBG.debug("Collection: " + contents);
        }
        final IdentNode sessionIdent = new IdentNode("Session");
        final IdentNode session_onendIdent = new IdentNode("Session_OnEnd");

        ServletContext ctx = session.getServletContext();
        if (DBG.isDebugEnabled()) DBG.debug("Servlet Context: " + ctx);

        GlobalScope globalScope = getGlobalScope(ctx);
        if (DBG.isDebugEnabled()) DBG.debug("Global Scope: " + globalScope);

        AspContext tmpContext = (AspContext)globalScope.getGlobalContext().clone();
        if (DBG.isDebugEnabled()) DBG.debug("Global Context: " + tmpContext);

        Session tmpSession = new Session(session, contents);
        JavaObjectNode sessionObj = new JavaObjectNode(tmpSession);
        tmpContext.setValue(sessionIdent, sessionObj);

        Object obj = tmpContext.getValue(session_onendIdent);
        if (!(obj instanceof UndefinedValueNode)) {
            if (DBG.isDebugEnabled())
                DBG.debug("Calling SESSION_ONEND (" + obj + ")");
            ((FunctionNode)obj).execute(new VarListNode(), tmpContext);
        } else {
            if (DBG.isDebugEnabled())
                DBG.debug("SESSION_ONEND does not exist.");
        }
    }
    
	public ScriptCache getScriptCache() {
		return cache;
	}
}
