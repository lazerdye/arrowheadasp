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

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Category;


/**
 * Base class for handling ASP requests.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class AspServlet extends HttpServlet implements HttpSessionListener
{
    /** Debugging */
    private Category DBG = Category.getInstance(AspServlet.class);

    /** Servlet configuration */
    ServletConfig config;

    /** List of handlers which should be called on exception */
    private Vector exceptionHandlers = new Vector();

    /**
     * Initializes the ASP servlet.
     *
     * @param config Servlet configuration
     * @throws ServletException if an error occurs
     * @see javax.servlet.Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);

        try {
            ServletContext sctx = getServletContext();
            this.config = config;

            /* Set up logging */
            String log4jConfigure = config.getInitParameter(
                "log4j.configure");
            if (log4jConfigure != null) {
                PropertyConfigurator.configure(log4jConfigure);
            }
            if (DBG.isDebugEnabled()) {
                DBG.debug("INIT: " + config);
                DBG.debug("this: " + this);
            }

            /* Prepare the exception handlers */
            prepareExceptionHandlers();

            /* Grab the global scope */
            GlobalScope globalScope = GlobalScope.getGlobalScope(sctx);

            /* This should be a new scope, initialize it */
            globalScope.initializeScope(config);

            /* Now call application_onstart */
            GlobalScope.callApplicationOnStart(config);
        } catch (AspException ex) {
            DBG.error("Exception in global scope", ex);
        }
    }

    /**
     * Obtains the properties for the exception handler
     * @param handlerName name of the handler
     * @param prop destination properties
     */
    private void fillExceptionHandlerProperties(String handlerName, Properties prop)
    {
        String lookingFor = "exception.parameter." + handlerName + ".";
        for (Enumeration e = config.getInitParameterNames();
                e.hasMoreElements() ;)
        {
            String name = (String)e.nextElement();
            if (name.startsWith(lookingFor)) {
                String paramName = name.substring(lookingFor.length());
                prop.setProperty(paramName, config.getInitParameter(name));
            }
        }
    }

    /**
     * Prepares the exception handlers.
     */
    private void prepareExceptionHandlers()
    {
        boolean anyExceptionHandlers = false;
        for (Enumeration e = config.getInitParameterNames();
                e.hasMoreElements() ;)
        {
            String name = (String)e.nextElement();
            if (name.startsWith("exception.handler.")) {
                if (DBG.isDebugEnabled()) DBG.debug("Configuring " + name);
                String handlerName = name.substring(18);
                Properties prop = new Properties();
                fillExceptionHandlerProperties(handlerName, prop);
                String value = config.getInitParameter(name);
                try {
                    AspExceptionHandler aeh = (AspExceptionHandler)
                        Class.forName(value).newInstance();
                    aeh.configureExceptionHandler(prop);
                    exceptionHandlers.add(aeh);
                    anyExceptionHandlers = true;
                } catch (ClassNotFoundException ex) {
                    DBG.error("Class not found", ex);
                } catch (InstantiationException ex) {
                    DBG.error("Error initializing class", ex);
                } catch (IllegalAccessException ex) {
                    DBG.error("Error initializing class", ex);
                } catch (AspException ex) {
                    DBG.error("Error initializing class", ex);
                }
            }
        }
        if (!anyExceptionHandlers) {
            exceptionHandlers.add(new FileNotFoundExceptionHandler());
            exceptionHandlers.add(new SetStatusExceptionHandler());
            exceptionHandlers.add(new PrintExceptionHandler());
        }
    }

    /**
     * Processes an ASP file request.
     * @param request Incoming request
     * @param response Outgoing response
     * @see javax.servlet.Servlet#service
     */
    public void service(HttpServletRequest request,
                    HttpServletResponse response)
    {
        String uri = request.getServletPath();

        if (DBG.isDebugEnabled()) {
            DBG.debug("ServletPath: " + request.getServletPath());
            DBG.debug("RequestURI: " + request.getRequestURI());
            DBG.debug("PathInfo: " + request.getPathInfo());
            DBG.debug("PathTranslated: " + request.getPathTranslated());
        }

        response.setContentType("text/html");

        try {
            final IdentNode respIdent = new IdentNode("response");
            final IdentNode reqIdent = new IdentNode("request");
            final IdentNode sessionIdent = new IdentNode("session");
            final IdentNode serverIdent = new IdentNode("server");
            final IdentNode filenameIdent = new IdentNode("!filename");
            final IdentNode globalIdent = new IdentNode("!global");

            ServletContext ctx = getServletContext();
            GlobalScope globalScope = GlobalScope.getGlobalScope(ctx);
            AspContext context = globalScope.createContext();

            Response respObj = new Response(response);
            JavaObjectNode javaObj = new JavaObjectNode(respObj);
            context.setValue(respIdent, javaObj);

            Request reqObj = new Request(request);
            JavaObjectNode reqJavaObj = new JavaObjectNode(reqObj);
            context.setValue(reqIdent, reqJavaObj);

            Session sesObj = new Session(request, context);
            JavaObjectNode sessionObj = new JavaObjectNode(sesObj);
            context.setValue(sessionIdent, sessionObj);

            Server servObj = new Server(config, context);
            JavaObjectNode serverObj = new JavaObjectNode(servObj);
            context.setValue(serverIdent, serverObj);

            context.forceScope(globalIdent);

            context.setValue(filenameIdent, uri);

            Server server = context.getAspServer();

            AspThread aspRunner = new AspThread(context, uri, globalScope.getScriptCache());
            Thread aspThread = new Thread(aspRunner);

            aspThread.start();

            aspThread.join((long)server.ScriptTimeout * 1000);

            if (aspThread.isAlive())
            {
                aspRunner.timeout(context);

                aspThread.join();
            }

            context.callOnPageEnd();

            Application application = context.getAspApplication();
            application.unlockIfThreadHasLock(aspThread);
        
            if (DBG.isDebugEnabled()) DBG.debug("Sub-exception: " + aspRunner.ex);
            if (aspRunner.ex instanceof AspExitScriptException)
            {
                if (respObj.stringBuf != null) {
                    respObj.Flush();
                }
                return;
            }

            if (aspRunner.ex != null) {
                Enumeration e = exceptionHandlers.elements();
                while (e.hasMoreElements())
                {
                    AspExceptionHandler aeh =
                        (AspExceptionHandler)e.nextElement();
                    try {
                        if (!aeh.onExceptionOccured(context, aspRunner.ex))
                            break;
                    } catch(Exception ex) {
                        DBG.error("Error while calling exception handler", ex);
                    }
                }
            }
            if (respObj.stringBuf != null) {
                respObj.Flush();
            }
        } catch (Exception ex) {
            throw new AspNestedRuntimeException(ex);
        }
    };

    /** Contains a refernece to a session handler. */
    private AspSessionHandler sessionHandler = new AspSessionHandler();
    
    /**
     * Session initialized.
     * @param e Event describing the initiazing of the session.
     */
    public void sessionCreated(HttpSessionEvent e)
    {
        sessionHandler.sessionCreated(e);
    }

    /**
     * Session destroyed.
     * @param e Event describing the destruction of the session.
     */
    public void sessionDestroyed(HttpSessionEvent e)
    {
        sessionHandler.sessionDestroyed(e);
    }
}

