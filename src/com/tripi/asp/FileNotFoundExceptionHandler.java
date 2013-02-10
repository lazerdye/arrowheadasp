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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Category;

/**
 * This exception only handles a "FileNotFound" exception by sending
 * a file not found error report.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class FileNotFoundExceptionHandler implements AspExceptionHandler
{
    /** Debugging class */
    static Category DBG = Category.getInstance(FileNotFoundExceptionHandler.class);

    /**
     * This function configures any properties
     */
    public void configureExceptionHandler(Properties prop)
    {
    }

    /**
     * This function is called when an exception occurs
     * @param ctx ASP context
     * @param ex Exception which occured
     * @return <b>true</b> on non-file not found exceptions, <b>false</b> on
     *  File not found exceptions.
     */
    public boolean onExceptionOccured(AspContext ctx, AspException ex)
        throws AspException
    {
        if (ex instanceof AspNestedException)
        {
            AspNestedException nex = (AspNestedException)ex;
            /* This is a little bit of a hack, but oh well. */
            if ((nex.Line() == 0) &&
                (nex.nestedEx instanceof FileNotFoundException))
            {
                /* Grab the filename */
                final IdentNode filenameIdent = new IdentNode("!filename");
                String filename = (String)ctx.getValue(filenameIdent);
                /* Grab the response object */
                Response rsp = ctx.getAspResponse();
                HttpServletResponse servRest = rsp.getHttpServletResponse();
                try {
                    servRest.sendError(404, filename);
                } catch (IOException ioex) {
                    throw new AspNestedException(ioex);
                }
                return false;
            }
        }
        return true;
    }
}

