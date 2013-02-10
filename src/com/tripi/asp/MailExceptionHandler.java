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

import com.tripi.asp.CDONTS.NewMail;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * This class handles an exception by printing out the stack trace
 * to the response handler.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class MailExceptionHandler implements AspExceptionHandler
{
    /** Debugging class */
    static Logger DBG = Logger.getLogger(MailExceptionHandler.class);

    /** Who should this message be from? */
    String msgFrom;

    /** Who should this message be to? */
    String msgTo;

    /** What should be the subject? */
    String msgSubject;

    /**
     * This function configures any properties
     */
    public void configureExceptionHandler(Properties prop) throws AspException
    {
        DBG.debug("configureExceptionHandler");
        msgFrom = prop.getProperty("From", "arrowhead");
        msgTo = prop.getProperty("To");
        if (msgTo == null) throw new AspException("Message To not defined");
        msgSubject = prop.getProperty("Subject", "An error has occured in the ArrowHead application");
    }

    /**
     * This function is called when an exception occurs
     * @param ex Exception which occured
     */
    public boolean onExceptionOccured(AspContext ctx, AspException ex)
        throws AspException
    {
        try {
            Session session = ctx.getAspSession();
            Server server = ctx.getAspServer();
            Request request = ctx.getAspRequest();

            String IPAddress = (String)request.ServerVariables.get("REMOTE_ADDR");
            Object forwardedFor = request.ServerVariables.get("HTTP_X_FORWARDED_FOR");
            String sessionID = "No session";
            session.getSessionIfExists();
            if (session.httpSession != null)
            {
                sessionID = session.httpSession.getId();
            }
            NewMail mail;
            try {
                mail = (NewMail)server.CreateObject("CDONTS.NewMail");
            } catch (ClassNotFoundException cex) {
                throw new AspException("CDONTS.NewMail is not installed");
            } catch (Exception cex) {
                throw new AspException("Error creating CDONTS.NewMail");
            }
            mail.From = msgFrom;
            mail.To = msgTo;
            mail.Subject = msgSubject;

            StringWriter msgBody = new StringWriter();
            msgBody.write("An error has occured in the application.\n");
            msgBody.write("SessionID: ");msgBody.write(sessionID);msgBody.write("\n");
            if (forwardedFor instanceof String)
            {
                msgBody.write("Forward For: ");msgBody.write((String)forwardedFor);msgBody.write("\n");
            }
            msgBody.write("IP Address: ");msgBody.write(IPAddress);msgBody.write("\n");
            msgBody.write("The exception was:\n");
            msgBody.flush();
            PrintWriter pr = new PrintWriter(msgBody);
            ex.printStackTrace(pr);
            pr.flush();
            msgBody.flush();
            
            mail.Body = msgBody.toString();
            mail.Send();
        } catch (AspException aspEx) {
            DBG.error("Error during MailExceptionHandler", aspEx);
        }
        return true;
    }
}

