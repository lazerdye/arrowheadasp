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

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * This class handles an exception by setting the status on the response object.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class SetStatusExceptionHandler implements AspExceptionHandler
{
    /** Debugging class */
    static Logger DBG = Logger.getLogger(SetStatusExceptionHandler.class);

    /** Exception code */
    int status = 500;

    /**
     * This function configures any properties
     */
    public void configureExceptionHandler(Properties prop)
    {
        String statusStr = prop.getProperty("Status");
        if (statusStr != null) status = Integer.parseInt(statusStr);
    }

    /**
     * This function is called when an exception occurs
     * @param ex Exception which occured
     */
    public boolean onExceptionOccured(AspContext ctx, AspException ex)
        throws AspException
    {
        if (DBG.isDebugEnabled()) DBG.debug("Setting the response status to "
            + status);
        Response rsp = ctx.getAspResponse();
        rsp.Status(status);
        return true;
    }
}

