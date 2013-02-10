/*
 * Created on Mar 15, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package com.tripi.asp.msxml2;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;

/**
 * @author jhorner
 */
public class DOMUtils {

	public static Element convertToJdom(DOMNode mselement) {

		Element result = null;
		if (mselement._NodeType.toString().equals("element")) {

			// remove any prefixes
			String name = mselement._NodeName.replaceAll(".*:", "");
			result = new Element(name);
			result.setNamespace(
				Namespace.getNamespace(
					mselement._Prefix,
					mselement._NamespaceURI));

			// set the attributes
			if (mselement.Attributes() != null) {
				List attrs = convertToJdomAttributes(mselement);
				if (attrs != null) {
					result.setAttributes(attrs);
				}
			}

			// add the children
			Collection children = mselement._ChildNodes.Items();
			Iterator it = children.iterator();
			while (it.hasNext()) {

				// check for a text child
				DOMNode child = (DOMNode) it.next();
				if (child._NodeType.toString().equals("text")) {
					result.setText(child.NodeValue().toString());
				}
				else {
					Element domchild = convertToJdom(child);
					if (domchild != null) {
						result.addContent(convertToJdom(child));
					}
				}
			}
		}

		return result;
	}

	public static List convertToJdomAttributes(DOMNode mselement) {
		List result = new Vector();

		Collection attributes = mselement._Attributes.Items();
		Iterator it = attributes.iterator();
		while (it.hasNext()) {
			DOMNode attribute = (DOMNode) it.next();

			String value = "";
			Object myobj = attribute.NodeValue();
			if (myobj != null) {
				value = myobj.toString();
			}
			Attribute attr = new Attribute(attribute._NodeName, value);
			result.add(attr);
		}

		return result;
	}

	public static DOMElement convertToMs(Element element) throws Exception {

		DOMElement result = new DOMElement();

		// create the initial element
		result._NodeName = element.getQualifiedName();
		result._Prefix = element.getNamespacePrefix();
		result._NamespaceURI = element.getNamespaceURI();
		result._NodeType = new DOMNodeType("element");

		// create the text child
		String nodetext = element.getText();
		if (nodetext != null) {
			DOMNode textnode = new DOMNode();
			textnode._NodeType = new DOMNodeType("text");
			textnode.NodeValue(element.getText());
			result.AppendChild(textnode);
		}

		// add the attributes
		result._Attributes = convertToMsAttributes(element);

		// add the children
		List children = element.getChildren();
		Iterator it = children.iterator();
		while (it.hasNext()) {
			Element child = (Element) it.next();

			// recurse through the children
			result._ChildNodes.AppendNode(convertToMs(child));
		}

		return result;
	}

	public static DOMNamedNodeMap convertToMsAttributes(Element element)
		throws Exception {

		DOMNamedNodeMap attributes = new DOMNamedNodeMap();
		List list = element.getAttributes();
		Iterator it = list.iterator();
		while (it.hasNext()) {

			Attribute attr = (Attribute) it.next();
			DOMNode node = new DOMNode();
			node._NodeName = attr.getQualifiedName();
			node._NodeType = new DOMNodeType("attribute");
			node.NodeValue(attr.getValue());

			// add this attribute
			attributes.SetNamedItem(node);
		}

		return attributes;
	}

	public static String transform(
		String pathprefix,
		String basefile,
		Document xmlDoc,
		Document xslDoc)
		throws Exception {

		try {

			// Get the the generated Transformer (processed stylesheet Source)
			TransformerFactory tfactory = TransformerFactory.newInstance();
			DOMURIResolver resolver = new DOMURIResolver();
			resolver.setPathprefix(pathprefix);
			resolver.setBasefilename(basefile);
			tfactory.setURIResolver(resolver);

			XMLOutputter xslOut = new XMLOutputter();
			StringReader xslReader =
				new StringReader(xslOut.outputString(xslDoc));
			Transformer transformer =
				tfactory.newTransformer(new StreamSource(xslReader));
			// Create an XML Source by outputting the JDOM Document to a Reader
			XMLOutputter xmlOut = new XMLOutputter();
			StringReader xmlReader =
				new StringReader(xmlOut.outputString(xmlDoc));
			StreamSource source = new StreamSource(xmlReader);

			// create a stream to be used as a buffer for the Result object
			ByteArrayOutputStream htmlbuf = new ByteArrayOutputStream();

			// Use the Transformer to transform the XML Source and send the
			// output to a Result object.
			transformer.transform(source, new StreamResult(htmlbuf));

			// convert the byte stream to a string and close the stream
			String html = htmlbuf.toString();
			htmlbuf.close();
			return html;
		}
		catch (Exception e) {

			throw new Exception(
				"XMLTransformation::transform - " + e.getMessage());
		}

	}

}
