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
package com.tripi.asp.CDONTS;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import com.tripi.asp.AspException;
import org.apache.log4j.Logger;

/**
 * This class implements the CDONTS.NewMail ASP Object.
 *
 * @author Terence Haddock
 * @version 0.9
 */
public class NewMail
{
    /** Debugging context */
    static Logger DBG = Logger.getLogger(NewMail.class);

    /** Which server to use for sending the mail */
    public String Host = null;

    /** Who this mail should be from */
    public String From = null;

    /** Who this mail should be to */
    public String To = null;

    /** The subject of the mail */
    public String Subject = null;

    /** The body of the mail */
    public String Body = null;

    /**
     * This ASP-Accessible function sends the mail.
     */
    public void Send() throws AspException
    {
        try {
            Properties props = new Properties();
            if (Host != null) {
                props.put("mail.smtp.host", Host);
            }
            Session session = Session.getDefaultInstance(props, null);
            Message msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress(From));
            } catch (AddressException ex) {
                throw new AspException("Invalid From address: " + From);
            }
            try {
                msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To, false));
            } catch (AddressException ex) {
                throw new AspException("Invalid To address: " + To);
            }
            msg.setSubject(Subject);
            msg.setText(Body);
            msg.setSentDate(new java.util.Date());
            Transport.send(msg);
            DBG.debug("Sent mail to " + To);
        } catch (MessagingException ex) {
            throw new AspException("Exception while sending message: " + ex);
        }
    }
}
