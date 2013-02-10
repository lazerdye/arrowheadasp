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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import org.apache.log4j.Category;

/**
 * This class implements a class to hold the current state of ASP 
 * execution.
 *
 * @author Terence Haddock
 */
public class AspContext
{
    /** Debugging information */
    private static final Category DBG = Category.getInstance(AspContext.class);

    /** Universal scope */
    Hashtable   universalScope;

    /** Global scope */
    Hashtable   globalScope;

    /** Subroutine scope */
    Stack       subroutineScope;

    /** Vector of objects to call OnPageEnd to */
    Vector      onPageEnd;
    
    /** Are we option explicit? */
    boolean     optionExplicit = false;

    /**
     * Constructor.
     * @param universalScope Universal scope
     */
    public AspContext(Hashtable universalScope)
    {
        this.universalScope = universalScope;
        this.globalScope = new Hashtable();
        this.subroutineScope = new Stack();
        onPageEnd = new Vector();
    }

    /**
     * This function clones this context.
     * @return a cloned snapshop of this context.
     */
    protected Object clone()
    {
        AspContext ctx = new AspContext(universalScope);
        ctx.globalScope = (Hashtable)globalScope.clone();
        ctx.subroutineScope = (Stack)subroutineScope.clone();
        ctx.onPageEnd = onPageEnd;
        ctx.optionExplicit = optionExplicit;
        return ctx;
    }

    /**
     * This function determines if a variable is directly in scope.
     * This is slightly different from inScope, as the variable is not
     * tested against the global scope if we are in a subroutine.
     *
     * @param ident identifier to check
     * @return <b>true</b> if the variable is in scope, <b>false</b> otherwise.
     */
    public boolean inDirectScope(IdentNode ident)
    {
        if (DBG.isDebugEnabled())
            DBG.debug("inDirectScope(" + ident + ")");
        if (!subroutineScope.empty())
        {
            Hashtable subScope = (Hashtable)subroutineScope.peek();
            if (subScope.containsKey(ident))
                return true;
        } else {
            if (globalScope.containsKey(ident))
                return true;
        }
        if (universalScope.containsKey(ident))
            return true;
        return false;
    }

    /**
     * This function determines if a variable is in scope.
     *
     * @param ident identifier to check
     * @return <b>true</b> if the variable is in scope, <b>false</b>
     * otherwise.
     */
    public boolean inScope(IdentNode ident)
    {
        if (DBG.isDebugEnabled())
            DBG.debug("inScope(" + ident + ")");
        if (!subroutineScope.empty())
        {
            Hashtable subScope = (Hashtable)subroutineScope.peek();
            if (subScope.containsKey(ident))
                return true;
        }
        if (globalScope.containsKey(ident))
            return true;
        if (universalScope.containsKey(ident))
            return true;
        return false;
    }

    /**
     * This function forces an identifier's scope to the current context.
     *
     * @param ident Identifier to force
     */
    public void forceScope(IdentNode ident)
    {
        if (!subroutineScope.empty())
        {
            Hashtable subScope = (Hashtable)subroutineScope.peek();
            subScope.put(ident, Constants.undefinedValueNode);
            return;
        }
        globalScope.put(ident, Constants.undefinedValueNode);
    }

    /**
     * This function obtains the value of a variable
     *
     * @param ident Identifier to obtain
     * @return Value of the variable.
     * @throws AspException on error
     */
    public Object getValue(IdentNode ident) throws AspException
    {
        if (DBG.isDebugEnabled())
            DBG.debug("getValue(" + ident + ")");
        if (!subroutineScope.empty())
        {
            Hashtable subScope = (Hashtable)subroutineScope.peek();
            if (subScope.containsKey(ident))
                return subScope.get(ident);
        }
        if (globalScope.containsKey(ident))
            return globalScope.get(ident);
        if (universalScope.containsKey(ident))
            return universalScope.get(ident);
        if (optionExplicit)
            throw new AspException("Undefined variable: " + ident);
        return Constants.undefinedValueNode;
    }

    /**
     * Set the value of an identifier.
     *
     * @param ident Identifier name
     * @param value New value of object
     * @throws AspException on error
     */
    public void setValue(IdentNode ident, Object value) throws AspException
    {
        if (DBG.isDebugEnabled())
            DBG.debug("setValue(" + ident + ")=" + value);
        if (value == null) value = Constants.nullNode;
        /**
         * If the variable is in subroutine scope: store it in sub.
         * Otherwise, if the variable is in global scope, store it in global.
         * Otherwise, if subroutine scope exists, store it there.
         * Otherwise, store it in global scope.
         */
        if (!subroutineScope.empty()) {
            Hashtable subScope = (Hashtable)subroutineScope.peek();
            if (subScope.containsKey(ident) || !globalScope.containsKey(ident))
            {
                subScope.put(ident, value);
                return;
            }
        }
        globalScope.put(ident, value);
        return;
    }

    /**
     * Sets the option explicit flag.
     * @throws AspException on error
     */
    void setOptionExplicit() throws AspException
    {
        if (optionExplicit) 
            throw new AspException("Option Explicit can be called only once");
        optionExplicit = true;
    }

    /**
     * Pushes a new subroutine scope onto the scope stack.
     * @param scope new subroutine scope
     * @throws AspException on error
     */
    void pushSubroutineScope(Hashtable scope) throws AspException
    {
        subroutineScope.push(scope);
    }

    /**
     * Pops a subroutine scope off of the scope stack.
     * @return old subroutine scope, or null if there is no scopes left
     * @throws AspException on error
     */
    Hashtable popSubroutineScope() throws AspException
    {
        return (Hashtable)subroutineScope.pop();
    }

    /**
     * Utility function to obtain the Server object.
     * XXX Should this be here, or in a separate class?
     * @return Server object
     * @throws AspException on error
     */
    public Server getAspServer() throws AspException
    {
        final IdentNode serverIdent = new IdentNode("Server");
        JavaObjectNode jon = (JavaObjectNode)getValue(serverIdent);
        return (Server)jon.getSubObject();
    }

    /**
     * Utility function to obtain the Session object.
     * @return Session object
     * @throws AspException on error
     */
    public Session getAspSession() throws AspException
    {
        final IdentNode sessionIdent = new IdentNode("Session");
        JavaObjectNode jon = (JavaObjectNode)getValue(sessionIdent);
        return (Session)jon.getSubObject();
    }

    /**
     * Utility function to obtain the Application object.
     * @return Application object
     * @throws AspException on error
     */
    public Application getAspApplication() throws AspException
    {
        final IdentNode appIdent = new IdentNode("Application");
        JavaObjectNode jon = (JavaObjectNode)getValue(appIdent);
        return (Application)jon.getSubObject();
    }

    /**
     * Utility function to obtain the Request object.
     * @return Request object
     * @throws AspException on error
     */
    public Request getAspRequest() throws AspException
    {
        final IdentNode rspIdent = new IdentNode("Request");
        JavaObjectNode jon = (JavaObjectNode)getValue(rspIdent);
        return (Request)jon.getSubObject();
    }

    /**
     * Utility function to obtain the Response object.
     * @return Response object
     * @throws AspException on error
     */
    public Response getAspResponse() throws AspException
    {
        final IdentNode rspIdent = new IdentNode("Response");
        JavaObjectNode jon = (JavaObjectNode)getValue(rspIdent);
        return (Response)jon.getSubObject();
    }

    /**
     * Adds a class to call the OnPageEnd when the page ends.
     * @param obj Object to call the OnPageEnd method.
     */
    public void addOnPageEnd(Object obj)
    {
        if (DBG.isDebugEnabled()) DBG.debug("Adding OnPageEnd for " + obj);
        onPageEnd.add(obj);
    }

    /**
     * Calls the OnPageEnd methods of the object.
     */
    public synchronized void callOnPageEnd()
    {
        if (DBG.isDebugEnabled()) DBG.debug("Calling all OnPageEnd");
        for (Enumeration eItems = onPageEnd.elements(); eItems.hasMoreElements();)
        {
            Object obj = eItems.nextElement();
            callOnPageEnd(obj);
        }
        onPageEnd.clear();
    }

    /**
     * Calls the OnPageEnd method of a particular object.
     * @param obj Object to call
     */
    void callOnPageEnd(Object obj)
    {
        if (DBG.isDebugEnabled()) DBG.debug("OnPageEnd for " + obj);
        Class paramTypes[] = new Class[1];
        paramTypes[0] = Object.class;
        Object paramArguments[] = new Object[1];
        paramArguments[0] = this;

        Class cls = obj.getClass();
        try {
            Method met = cls.getMethod("OnPageEnd", paramTypes);
            met.invoke(obj, paramArguments);
        } catch (NoSuchMethodException ex)
        {
        } catch (IllegalAccessException ex)
        {
        } catch (InvocationTargetException ex)
        {
        }
    }

    /**
     * Calls the OnPageStart method of a particular object.
     * @param obj Object to call
     */
    void callOnPageStart(Object obj)
    {
        if (DBG.isDebugEnabled()) DBG.debug("OnPageStart for " + obj);
        Class paramTypes[] = new Class[1];
        paramTypes[0] = Object.class;
        Object paramArguments[] = new Object[1];
        paramArguments[0] = this;

        Class cls = obj.getClass();
        try {
            Method met = cls.getMethod("OnPageStart", paramTypes);
            met.invoke(obj, paramArguments);
        } catch (NoSuchMethodException ex)
        {
        } catch (IllegalAccessException ex)
        {
        } catch (InvocationTargetException ex)
        {
        }
    }

    /**
     * This utility function checks if ResumeNext is in effect.
     *
     * @param context Current Context
     * @return true if resume next is in effect, false otherwise
     * @throws AspException on error
     */
    boolean resumeNext() throws AspException
    {
        final IdentNode ident = new IdentNode("_err");
        if (DBG.isDebugEnabled()) DBG.debug("Resume next test");
        if (inScope(ident)) {
            Object obj = getValue(ident);
            if (DBG.isDebugEnabled()) DBG.debug("Resume next: " + obj);
            if (obj == null || obj instanceof NullNode) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function handles error processing.
     * 
     * @param ex Exception thrown
     * @param ctx Current debugging context
     * @throws AspException on error
     */
    public void processException(Exception ex, DebugContext ctx)
        throws AspException
    {
        if (ex instanceof AspExitException) throw (AspExitException)ex;

        final IdentNode Err = new IdentNode("Err");
        AspException aspEx = new AspNestedException(ex, ctx);
        setValue(Err, new JavaObjectNode(aspEx));
        if (!resumeNext())
        {
            throw aspEx;
        }
    }

    /**
     * This function obtains the URL associated with the given filename.
     *
     * @param filename Full pathname to file
     * @return URL associated with the given filename
     * @throws AspException on error
     */
    public URL getURL(String filename) throws AspException
    {
        /* Get the server object */
        final IdentNode serverIdent = new IdentNode("server");
        JavaObjectNode serverNode = (JavaObjectNode)getValue(serverIdent);
        Server server = (Server)serverNode.getSubObject();

        try {
            if (DBG.isDebugEnabled()) DBG.debug("Getting URL for: " + filename);
            /* Get the URL for the file */
            URL url = server.servletContext.getResource(filename);

            if (DBG.isDebugEnabled()) DBG.debug("URL for: " + filename + "=" + url);

            if (url == null) url = new URL("file://" + filename);

            return url;
        } catch (MalformedURLException ex) {
            throw new AspNestedException(ex);
        }
    }
}

