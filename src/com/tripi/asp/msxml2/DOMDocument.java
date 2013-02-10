/**
 * ArrowHead ASP Server 
 * This is a source file for the ArrowHead ASP Server - an 100% Java
 * VBScript interpreter and ASP server.
 *
 * For more information, see http://www.tripi.com/arrowhead
 *
 * ArrowHead Copyright (C) 2003  Terence Haddock
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
package com.tripi.asp.msxml2;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.tripi.asp.AspContext;
import com.tripi.asp.AspException;
import com.tripi.asp.Server;

/**
 * This class implements msxml2.DOMDocument. 
 *
 * Contributed by Jim Horner &lt;jhorner@arinbe.com&gt;
 */
public class DOMDocument extends DOMNode {

	/** Debugging category. */
	Logger DBG = Logger.getLogger(DOMDocument.class);

	// indicates whether asynchronous download is permitted [read/write]
	// this value is ignored
	public boolean Async = false;

	// Contains the root element of the document [read/write]
	public DOMElement DocumentElement;

	// returns an IXMLDOMParseError object that contains information
	// about the last parsing error. [read-only; see ParseError()]
	private DOMParseError _ParseError;

	// these objects will be used for filename manipulation during transformation
	private Server server;
	private String filename;

    /** Internal properties */
    protected Properties properties;

	public DOMDocument() {
		DocumentElement = null;
		this._ParseError = new DOMParseError();
	}

	public String Xml() {

		String result = "[Error :: No root element created.]";
		if (this.DocumentElement != null) {
			XMLOutputter xmlDbg = new XMLOutputter();
			xmlDbg.setIndent("   ");
			xmlDbg.setNewlines(true);
			Document xmldoc =
				new Document(DOMUtils.convertToJdom(this.DocumentElement));
			result = xmlDbg.outputString(xmldoc);
		}

		return result;
	}

    /**
     * This function handles when the object has been loaded in order
     * to store away the Server object.
     * @param obj Object
     */
	public void OnPageStart(Object obj) throws AspException {
		AspContext context = (AspContext) obj;
		this.server = context.getAspServer();
	}

    /**
     * Set a property.
     * @param prop property name
     * @param val property value
     */
    public void setProperty(String name, String value)
    {
        properties.put(name, value);
    }

	public DOMNode AppendChild(DOMElement element) throws Exception {

		DocumentElement = element;
		if (DocumentElement != null) {
			DocumentElement._OwnerDocument = this;
		}

		return DocumentElement;
	}

	public DOMElement CreateElement(String elementname) {

		DOMElement result = new DOMElement();
		result._NodeName = elementname;
		result._NodeType = new DOMNodeType("element");

		return result;
	}

	public DOMNode CreateNode(String nodeType, String name, String namespace) {
		DOMNode result = new DOMNode();
		String prefix = "";
		if (namespace != null && !namespace.equals("")) {
			prefix = namespace + ":";
		}
		result._NodeName = prefix + name;
		result._NodeType = new DOMNodeType(nodeType);

		return result;
	}

	public DOMNode CreateNode(int nodeType, String name, String namespace) {
		DOMNode result = new DOMNode();
		String prefix = "";
		if (namespace != null && !namespace.equals("")) {
			prefix = namespace + ":";
		}
		result._NodeName = prefix + name;
		result._NodeType = new DOMNodeType(nodeType);

		return result;
	}

	public boolean Load(String filename) throws Exception {

		boolean result = false;
		boolean found = false;

		this.setFilename("");

		// locate the file
		File xmlfile = null;
		try {
			xmlfile = new File(filename);
			if (xmlfile.exists()) {
				found = true;
			}
		}
		catch (Exception e) {
			// just assume not found
		}

		if (found) {
			SAXBuilder sax = new SAXBuilder();
			Document doc = sax.build(xmlfile);
			if (doc.hasRootElement()) {
				Element root = doc.getRootElement();
				this.DocumentElement = DOMUtils.convertToMs(root);
				result = true;
				this.setFilename(filename);
			}
		}
		else {
			String msg = "Unable to find file [" + filename + "].";
			this._ParseError.setReason(msg);
		}

		return result;
	}

	public String TransformNode(DOMDocument msxsldoc) throws Exception {

		// need to convert myself to a Document
		Document xmldoc =
			new Document(DOMUtils.convertToJdom(this.DocumentElement));

		// need to convert xsl to a Document
		Document xsldoc =
			new Document(DOMUtils.convertToJdom(msxsldoc.DocumentElement));

		return DOMUtils.transform(
			server.MapPath("."),
			msxsldoc.getFilename(),
			xmldoc,
			xsldoc);
	}

	public String TransformNode(DOMElement xslelement) throws Exception {

		// need to convert myself to a Document
		Document xmldoc =
			new Document(DOMUtils.convertToJdom(this.DocumentElement));

		// need to convert xsl to a Document
		Document xsldoc = new Document(DOMUtils.convertToJdom(xslelement));

		return DOMUtils.transform(
			server.MapPath("."),
			this.getFilename(),
			xmldoc,
			xsldoc);
	}

	public DOMParseError ParseError() {
		return this._ParseError;
	}

	/**
	 * @return String
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the filename.
	 * @param filename The filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

}
