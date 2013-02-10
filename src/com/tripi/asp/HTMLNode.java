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

import java.io.IOException;

/**
 * HTMLNode handles a normal HTML %&gt; ... &lt;% "output" node.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class HTMLNode implements Node
{
    /** Text string of the HTML */
    String html;

    /**
     * Constructor.
     *
     * @param inHtml HTML string of this node.
     */
    public HTMLNode(String inHtml)
    {
        html = inHtml;
    }

    /**
     * Dumps this node.
     * @see Node#dump()
     */
    public void dump()
    {
        System.out.print("%>" + html + "<%");
    }

    /**
     * Prepares this node for execution.
     * @param context AspContext this node will be executed under.
     * @see Node#prepare(AspContext)
     */
    public void prepare(AspContext context)
    {
        /* Nothing to do */
    }

    /**
     * Executes this node within the specified context. This node simply
     * calls the Response.Write routine within the current context
     * to output this node.
     * @param context AspContext to execute this node under.
     * @see Node#execute(AspContext)
     * @throws AspException if an error occurs
     */
    public Object execute(AspContext context) throws AspException
    {    
        final IdentNode respIdent = new IdentNode("response");
        JavaObjectNode respObj = (JavaObjectNode)context.getValue(respIdent);
        Response resp = (Response)respObj.getSubObject();
        try {
            resp.Write(html);
        } catch (IOException ex) {
            throw new AspNestedException(ex);
        }

        return null;
    }
};

