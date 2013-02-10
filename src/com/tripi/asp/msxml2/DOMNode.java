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
package com.tripi.asp.msxml2;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;

/**
 * This class implements msxml2.DOMNode. 
 *
 * Contributed by Jim Horner &lt;jhorner@arinbe.com&gt;
 */
public class DOMNode {

	/** Debugging category. */
	Logger DBG = Logger.getLogger(DOMNode.class);

	// contains the list of attributes for this node. [read-only; see Attributes()]
	protected DOMNamedNodeMap _Attributes;

	// returns the base name for the name qualified with the namespace [read-only]
	// private String baseName;

	// contains a node list containing the children 
	// (for nodes that can have children) [read-only; see ChildNodes()]	
	protected DOMNodeList _ChildNodes;

	// specifies the datatpe for this node. [read/write]
	// public String dataType;

	// private String Definition;

	// return the Uniform Resource Identifier (URI) for the 
	// namespace [read-only; see NamespaceURI()]
	protected String _NamespaceURI;

	// contains the qualified name of the element, attribute, or entity reference
	// or a fixed string for other node types [read-only; see NodeName()]
	protected String _NodeName;

	// specifies teh XML Document Object Model node type, which determines
	// valid values and whether the node can have child nodes. 
	// [read-only; see NodeType() and NodeTypeString()] 	
	protected DOMNodeType _NodeType;

	// contains the text associated with the node [read/write; see NodeValue()]
	protected Object _NodeValue;

	// returns the root of the document that contains this node.
	// [read-only; see OwnerDocument()]
	protected DOMDocument _OwnerDocument;

	// contains the parent node (for nodes that can have parents)
	// [read-only; see ParentNode()]
	protected DOMNode _ParentNode;

	// returns the namespace prefix [read-only; see Prefix()]
	protected String _Prefix;

	public DOMNode() {
		this._ChildNodes = new DOMNodeList();
		this._Attributes = null;
		this._Prefix = "";
	}

	public DOMNode AppendChild(DOMNode node) {

		node._ParentNode = this;
		node._OwnerDocument = this._OwnerDocument;
		this._ChildNodes.AppendNode(node);

		return node;
	}

	public String Prefix() {
		return this._Prefix;
	}

	/**
	 * ASP-Accessible function
	 *   @return the attributes for this node
	 */
	public DOMNamedNodeMap Attributes() {
		
		// these types should always return null
		String type = this._NodeType.toString();
		if (type.equals("attribute")
			|| type.equals("cdatasection")
			|| type.equals("comment")
			|| type.equals("document")
			|| type.equals("documentfragement")
			|| type.equals("entityreference")
			|| type.equals("text")) {

			return null;
		}

		return this._Attributes;
	}

	/**
	 * ASP-Accessible function
	 *   @return the first node of the node list
	 */
	public DOMNode FirstChild() {
		DOMNode result = null;
		if (this._ChildNodes != null) {
			result = this._ChildNodes.Item(0);
		}
		return result;
	}

	/**
	 * ASP-Accessible function
	 *   @return the last node of the node list
	 */
	public DOMNode LastChild() {
		DOMNode result = null;
		if (this._ChildNodes != null) {
			result = this._ChildNodes.Item(0);
		}
		return result;
	}

	/**
	 * ASP-Accessible function
	 *   @return the node type
	 */
	public int NodeType() {
		return this._NodeType.toInteger();
	}

	/**
	 * ASP-Accessible function
	 *   @return the node type string value
	 */
	public String NodeTypeString() {
		return this._NodeType.toString();
	}

	/**
	 * ASP-Accessible function
	 *   @return the URI of the namespace
	 */
	public String NamespaceURI() {
		return this._NamespaceURI;
	}

	// contains the text associated with the node [read/write]
	public Object NodeValue() {

		// check for valid types
		/* These types contain null and throw an error when setting
		 * NODE_DOCUMENT, NODE_DOCUMENT_TYPE, NODE_DOCUMENT_FRAGMENT, 
		 * NODE_ELEMENT, NODE_ENTITY, NODE_ENTITY_REFERENCE, NODE_NOTATION
		 */
		String type = this._NodeType.toString();
		if (type.equals("document")
			|| type.equals("documenttype")
			|| type.equals("documentfragement")
			|| type.equals("element")
			|| type.equals("entity")
			|| type.equals("entityreference")
			|| type.equals("notation")) {

			return null;
		}

		return this._NodeValue;
	}

	public void NodeValue(Object value) throws Exception {

		// check for valid types
		if (this._NodeType == null) {
			throw new Exception("Must set NodeType before setting NodeValue.");
		}
		
		/* These types contain null and throw an error when setting
		 * NODE_DOCUMENT, NODE_DOCUMENT_TYPE, NODE_DOCUMENT_FRAGMENT, 
		 * NODE_ELEMENT, NODE_ENTITY, NODE_ENTITY_REFERENCE, NODE_NOTATION
		 */
			String type = this._NodeType.toString();
			if (type.equals("document")
				|| type.equals("documenttype")
				|| type.equals("documentfragement")
				|| type.equals("element")
				|| type.equals("entity")
				|| type.equals("entityreference")
				|| type.equals("notation")) {
				// bland, general, "plagarized" error from IIS error
				// don't won't anyone spoiled with too much information
				throw new Exception(
					"This operation can not be performed "
						+ "with a Node of type "
						+ type.toUpperCase()
						+ ".");
			}

		this._NodeValue = value;
	}

	// contains the text content of the node and its subtrees. [read/write]
	public String Text() {

		String result = "";

		String type = this._NodeType.toString();
		if (type.equals("text") || type.equals("attribute")) {
			result = this.NodeValue().toString();
		}
		else {
			Collection children = _ChildNodes.Items();
			Iterator it = children.iterator();
			while (it.hasNext()) {

				// check for a text child
				DOMNode child = (DOMNode) it.next();
				result = result + child.Text();
			}
		}

		return result;
	}

	public void Text(String text) throws Exception {

		String result = "";

		if (this._NodeType == null) {
			throw new Exception("Must set NodeType before setting Text.");
		}

		String type = this._NodeType.toString();
		if (type.equals("text") || type.equals("attribute")) {
			this.NodeValue(text);
		}
		else {
			DOMNode child = new DOMNode();
			child._NodeType = new DOMNodeType("text");
			child.Text(text);
			this.AppendChild(child);
		}

	}
	
	public String Xml() {

			XMLOutputter xmlDbg = new XMLOutputter();
			xmlDbg.setIndent("   ");
			xmlDbg.setNewlines(true);
			Document xmldoc = new Document(DOMUtils.convertToJdom(this));
			String result = xmlDbg.outputString(xmldoc);

		return result;
	}
}
