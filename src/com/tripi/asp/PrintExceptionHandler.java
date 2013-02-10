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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * This class handles an exception by printing out the stack trace
 * to the response handler.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class PrintExceptionHandler implements AspExceptionHandler
{
    /** Debugging class */
    static Logger DBG = Logger.getLogger(PrintExceptionHandler.class);

    /**
     * This function configures any properties
     */
    public void configureExceptionHandler(Properties prop)
    {
        /* Nothing to configure */
    }

    /**
     * This function is called when an exception occurs
     * @param ex Exception which occured
     */
    public boolean onExceptionOccured(AspContext ctx, AspException ex)
        throws AspException
    {
        try {
            Response rsp = ctx.getAspResponse();
            StringWriter string = new StringWriter();
            PrintWriter write = new PrintWriter(string);
            write.write("</table></table><pre>");
            ex.printStackTrace(write);
            write.write("</pre>");
            write.close();
            rsp.Write(string.toString());
        } catch (IOException ioex) {
            DBG.error("Error while displaying exception", ioex);
        }
        return true;
    }
}

