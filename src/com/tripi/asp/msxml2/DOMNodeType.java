/*
 * Created on Mar 14, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package com.tripi.asp.msxml2;

/**
 * @author jhorner
 * 
 * 
NODE_ELEMENT (1)
The node represents an element (its nodeTypeString property is "element"). 
An Element node can have the following child node types: Element, Text, 
Comment, ProcessingInstruction, CDATASection, and EntityReference. 
The Element node can be the child of the Document, DocumentFragment, 
EntityReference, and Element nodes.

NODE_ATTRIBUTE (2)
The node represents an attribute of an element (its nodeTypeString 
property is "attribute"). An Attribute node can have the following child 
node types: Text and EntityReference. The Attribute node does not appear as 
the child node of any other node type; it is not considered a 
child node of an Element.

NODE_TEXT (3)
The node represents the text content of a tag (its nodeTypeString property 
is "text"). A Text node cannot have any child nodes. The Text node can appear 
as the child node of the Attribute, DocumentFragment, Element, and EntityReference nodes.

NODE_CDATA_SECTION (4)
The node represents a CDATA section in the XML source (its nodeTypeString 
property is "cdatasection"). CDATA sections are used to escape blocks of text 
that would otherwise be recognized as markup. A CDATASection node cannot have 
any child nodes. The CDATASection node can appear as the child of the 
DocumentFragment, EntityReference, and Element nodes.

NODE_ENTITY_REFERENCE (5)
The node represents a reference to an entity in the XML document (its 
nodeTypeString property is "entityreference"). This applies to all entities, 
including character entity references. An EntityReference node can have the 
following child node types: Element, ProcessingInstruction, Comment, Text, 
CDATASection, and EntityReference. The EntityReference node can appear as the 
child of the Attribute, DocumentFragment, Element, and EntityReference nodes.

NODE_ENTITY (6)
The node represents an expanded entity (its nodeTypeString property is 
"entity"). An Entity node can have child nodes that represent the expanded 
entity (for example, Text and EntityReference nodes). The Entity node can 
appear as the child of the DocumentType node.

NODE_PROCESSING_INSTRUCTION (7)
The node represents a processing instruction from the XML document (its 
nodeTypeString property is "processinginstruction"). A ProcessingInstruction 
node cannot have any child nodes. The ProcessingInstruction node can appear 
as the child of the Document, DocumentFragment, Element, and EntityReference nodes.

NODE_COMMENT (8)
The node represents a comment in the XML document (its nodeTypeString 
property is "comment"). A Comment node cannot have any child nodes. The 
Comment node can appear as the child of the Document, DocumentFragment, 
Element, and EntityReference nodes.

NODE_DOCUMENT (9)
The node represents a document object, that as the root of the document 
tree, provides access to the entire XML document (its nodeTypeString 
property is "document"). It is created using the progID "Microsoft.XMLDOM" 
or through a data island using <XML> or <SCRIPT LANGUAGE=XML>. 
A Document node can have the following child node types: 
Element (maximum of one), ProcessingInstruction, Comment, and DocumentType. 
The Document node cannot appear as the child of any node types.

NODE_DOCUMENT_TYPE (10)
The node represents the document type declaration, indicated by the 
<!DOCTYPE> tag (its nodeTypeString property is "documenttype"). 
A DocumentType node can have the following child node types: 
Notation and Entity. The DocumentType node can appear as the 
child of the Document node.

NODE_DOCUMENT_FRAGMENT (11)
The node represents a document fragment (its nodeTypeString property 
is "documentfragment"). The DocumentFragment node associates a node 
or subtree with a document without actually being contained within 
the document. A DocumentFragment node can have the following 
child node types: Element, ProcessingInstruction, Comment, 
Text, CDATASection, and EntityReference. The DocumentFragment node 
cannot appear as the child of any node types.

NODE_NOTATION (12)
The node represents a notation in the document type declaration 
(its nodeTypeString property is "notation"). A Notation node cannot 
have any child nodes. The Notation node can appear as the 
child of the DocumentType node.
 *
 * 
 **/
public class DOMNodeType {

	protected int type = 0;
	
	public DOMNodeType(String nodeTypeString) {
		this.type = convertToInteger(nodeTypeString);
	}
	
	public DOMNodeType(int nodeTypeValue) {
		this.type = convertToInteger(toString(nodeTypeValue));
	}

	public int toInteger() {
		return this.type;
	}	

	public String toString() {
		return toString(this.type);
	}

	private String toString(int mytype) {
		String result = "unknown";
		
		switch (mytype) {
			case 1:
				result = "element";
				break;
			case 2:
				result = "attribute";
				break;
			case 3:
				result = "text";
				break;
			case 4:
				result = "cdatasection";
				break;
			case 5:
				result = "entityreference";
				break;
			case 6:
				result = "entity";
				break;
			case 7:
				result = "processinginstruction";
				break;
			case 8:
				result = "comment";
				break;
			case 9:
				result = "document";
				break;
			case 10:
				result = "documenttype";
				break;
			case 11:
				result = "documentfragement";
				break;
			case 12:
				result = "notation";
				break;
		}
		
		return result;
	}
	
	private int convertToInteger(String str) {
		int result = -1;
		
		if ( str.equalsIgnoreCase("element") ){
			result = 1;
		}
		else if ( str.equalsIgnoreCase("attribute") ) {
			result = 2;
		}
		else if ( str.equalsIgnoreCase("text") ) {
			result = 3;
		}
		else if ( str.equalsIgnoreCase("cdatasection") ) {
			result = 4;
		}
		else if ( str.equalsIgnoreCase("entityreference") ) {
			result = 5;
		}
		else if ( str.equalsIgnoreCase("entity") ) {
			result = 6;
		}
		else if ( str.equalsIgnoreCase("processinginstruction") ) {
			result = 7;
		}
		else if ( str.equalsIgnoreCase("comment") ) {
			result = 8;
		}
		else if ( str.equalsIgnoreCase("document") ) {
			result = 9;
		}
		else if ( str.equalsIgnoreCase("documenttype") ) {
			result = 10;
		}
		else if ( str.equalsIgnoreCase("documentfragment") ) {
			result = 11;
		}
		else if ( str.equalsIgnoreCase("notation") ) {
			result = 12;
		}
		
		return result;	
	}	

}
