package Rasad.Core.Media.MediaMetadataManagement.Xmp;

import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// XmpTag.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//
// Copyright (C) 2009 Ruben Vermeersch
//
// This library is free software; you can redistribute it and/or modify
// it  under the terms of the GNU Lesser General Public License version
// 2.1 as published by the Free Software Foundation.
//
// This library is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
// USA
//





/** 
	Holds XMP (Extensible Metadata Platform) metadata.
*/
public class XmpTag extends ImageTag
{
	static
	{
		Initialize();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Parsing speedup
	private HashMap<String, HashMap<String, XmpNode>> nodes;

	/** 
		Adobe namespace
	*/
	public static final String ADOBE_X_NS = "adobe:ns:meta/";

	/** 
		Camera Raw Settings namespace
	*/
	public static final String CRS_NS = "http://ns.adobe.com/camera-raw-settings/1.0/";

	/** 
		Dublin Core namespace
	*/
	public static final String DC_NS = "http://purl.org/dc/elements/1.1/";

	/** 
		Exif namespace
	*/
	public static final String EXIF_NS = "http://ns.adobe.com/exif/1.0/";

	/** 
		Exif aux namespace
	*/
	public static final String EXIF_AUX_NS = "http://ns.adobe.com/exif/1.0/aux/";

	/** 
		JOB namespace
	*/
	public static final String JOB_NS = "http://ns.adobe.com/xap/1.0/sType/Job#";

	/** 
		Microsoft Photo namespace
	*/
	public static final String MS_PHOTO_NS = "http://ns.microsoft.com/photo/1.0/";

	/** 
		Photoshop namespace
	*/
	public static final String PHOTOSHOP_NS = "http://ns.adobe.com/photoshop/1.0/";

	/** 
		Prism namespace
	*/
	public static final String PRISM_NS = "http://prismstandard.org/namespaces/basic/2.1/";

	/** 
		RDF namespace
	*/
	public static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

	/** 
		STDIM namespace
	*/
	public static final String STDIM_NS = "http://ns.adobe.com/xap/1.0/sType/Dimensions#";

	/** 
		TIFF namespace
	*/
	public static final String TIFF_NS = "http://ns.adobe.com/tiff/1.0/";

	/** 
		XAP (XMP's previous name) namespace
	*/
	public static final String XAP_NS = "http://ns.adobe.com/xap/1.0/";

	/** 
		XAP bj namespace
	*/
	public static final String XAP_BJ_NS = "http://ns.adobe.com/xap/1.0/bj/";

	/** 
		XAP mm namespace
	*/
	public static final String XAP_MM_NS = "http://ns.adobe.com/xap/1.0/mm/";

	/** 
		XAP rights namespace
	*/
	public static final String XAP_RIGHTS_NS = "http://ns.adobe.com/xap/1.0/rights/";

	/** 
		XML namespace
	*/
	public static final String XML_NS = "http://www.w3.org/XML/1998/namespace";

	/** 
		XMLNS namespace
	*/
	public static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/";

	/** 
		XMP TPg (XMP Paged-Text) namespace
	*/
	public static final String XMPTG_NS = "http://ns.adobe.com/xap/1.0/t/pg/";

	public static final String ABOUT_URI = "about";
	public static final String ABOUT_EACH_URI = "aboutEach";
	public static final String ABOUT_EACH_PREFIX_URI = "aboutEachPrefix";
	public static final String ALT_URI = "Alt";
	public static final String BAG_URI = "Bag";
	public static final String BAG_ID_URI = "bagID";
	public static final String DATA_TYPE_URI = "datatype";
	public static final String DESCRIPTION_URI = "Description";
	public static final String ID_URI = "ID";
	public static final String LANG_URI = "lang";
	public static final String LI_URI = "li";
	public static final String NODE_ID_URI = "nodeID";
	public static final String PARSE_TYPE_URI = "parseType";
	public static final String RDF_URI = "RDF";
	public static final String RESOURCE_URI = "resource";
	public static final String SEQ_URI = "Seq";
	public static final String VALUE_URI = "value";

	// This allows for fast string comparison using operator==
	private static final NameTable NameTable = new NameTable();
	private static boolean initialized = false;

	private static void Initialize()
	{
		if (initialized)
		{
			return;
		}

		synchronized (NameTable)
		{
			if (initialized)
			{
				return;
			}
			PrepareNamespaces();
			initialized = true;
		}
	}

	private static void PrepareNamespaces()
	{
		// Namespaces
		AddNamespacePrefix("", ""); // Needed for the about attribute, which can be unqualified.
		AddNamespacePrefix("x", ADOBE_X_NS);
		AddNamespacePrefix("crs", CRS_NS);
		AddNamespacePrefix("dc", DC_NS);
		AddNamespacePrefix("exif", EXIF_NS);
		AddNamespacePrefix("aux", EXIF_AUX_NS);
		AddNamespacePrefix("stJob", JOB_NS);
		AddNamespacePrefix("MicrosoftPhoto", MS_PHOTO_NS);
		AddNamespacePrefix("photoshop", PHOTOSHOP_NS);
		AddNamespacePrefix("prism", PRISM_NS);
		AddNamespacePrefix("rdf", RDF_NS);
		AddNamespacePrefix("stDim", STDIM_NS);
		AddNamespacePrefix("tiff", TIFF_NS);
		AddNamespacePrefix("xmp", XAP_NS);
		AddNamespacePrefix("xapBJ", XAP_BJ_NS);
		AddNamespacePrefix("xapMM", XAP_MM_NS);
		AddNamespacePrefix("xapRights", XAP_RIGHTS_NS);
		AddNamespacePrefix("xml", XML_NS);
		AddNamespacePrefix("xmlns", XMLNS_NS);
		AddNamespacePrefix("xmpTPg", XMPTG_NS);

		// Attribute names
		NameTable.Add(ABOUT_URI);
		NameTable.Add(ABOUT_EACH_URI);
		NameTable.Add(ABOUT_EACH_PREFIX_URI);
		NameTable.Add(ALT_URI);
		NameTable.Add(BAG_URI);
		NameTable.Add(BAG_ID_URI);
		NameTable.Add(DATA_TYPE_URI);
		NameTable.Add(DESCRIPTION_URI);
		NameTable.Add(ID_URI);
		NameTable.Add(LANG_URI);
		NameTable.Add(LI_URI);
		NameTable.Add(NODE_ID_URI);
		NameTable.Add(PARSE_TYPE_URI);
		NameTable.Add(RDF_URI);
		NameTable.Add(RESOURCE_URI);
		NameTable.Add(SEQ_URI);
		NameTable.Add(VALUE_URI);
	}

	/** 
		Mapping between full namespaces and their short prefix. Needs to be public for the unit test generator.
	*/
	public static HashMap<String, String> NamespacePrefixes = new HashMap<String, String>();

	private static int anon_ns_count = 0;

	private static void AddNamespacePrefix(String prefix, String ns)
	{
		NameTable.Add(ns);
		NamespacePrefixes.put(ns, prefix);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Construct a new empty <see cref="XmpTag"/>.
	*/
	public XmpTag()
	{
		setNodeTree(new XmpNode("", ""));
		nodes = new HashMap<String, HashMap<String, XmpNode>> ();
	}

	/** 
		Construct a new <see cref="XmpTag"/>, using the data parsed from the given string.
	 
	 @param data
		A <see cref="System.String"/> containing an XMP packet. This should be a valid
		XMP block.
	 
	 @param file
		The file that's currently being parsed, used for reporting corruptions.
	 
	*/
	public XmpTag(String data, Rasad.Core.Media.MediaMetadataManagement.File file)
	{
		// For some cameras, we have XMP data ending with the null value.
		// This is fine with Mono, but with Microsoft .NET it will throw
		// an XmlException. See also XmpNullValuesTest.cs.
		if (data.charAt(data.length() - 1) == '\0')
		{
			data = data.substring(0, data.length() - 1);
		}

		XmlDocument doc = new XmlDocument(NameTable);
		doc.LoadXml(data);

		XmlNamespaceManager nsmgr = new XmlNamespaceManager(doc.NameTable);
		nsmgr.AddNamespace("x", ADOBE_X_NS);
		nsmgr.AddNamespace("rdf", RDF_NS);

		XmlNode node = doc.SelectSingleNode("/x:xmpmeta/rdf:RDF", nsmgr);
		// Old versions of XMP were called XAP, fall back to this case (tested in sample_xap.jpg)
		node = (node != null) ? node : doc.SelectSingleNode("/x:xapmeta/rdf:RDF", nsmgr);
		if (node == null)
		{
			throw new CorruptFileException();
		}

		setNodeTree(ParseRDF(node, file));
		AcceptVisitors();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	// 7.2.9 RDF
	//		start-element ( URI == rdf:RDF, attributes == set() )
	//		nodeElementList
	//		end-element()
	private XmpNode ParseRDF(XmlNode rdf_node, Rasad.Core.Media.MediaMetadataManagement.File file)
	{
		XmpNode top = new XmpNode("", "");
		for (XmlNode node : rdf_node.ChildNodes)
		{
			if (node instanceof XmlWhitespace)
			{
				continue;
			}

			if (Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(node, RDF_NS, DESCRIPTION_URI))
			{
				Object tempVar = node.Attributes.GetNamedItem(RDF_NS, ABOUT_URI);
				XmlAttribute attr = tempVar instanceof XmlAttribute ? (XmlAttribute)tempVar : null;
				if (attr != null)
				{
					if (!top.getName().equals("") && !top.getName().equals(attr.InnerText))
					{
						throw new CorruptFileException("Multiple inconsistent rdf:about values!");
					}
					top.setName(attr.InnerText);
				}
				continue;
			}

			file.MarkAsCorrupt("Cannot have anything other than rdf:Description at the top level");
			return top;
		}
		ParseNodeElementList(top, rdf_node);
		return top;
	}

	// 7.2.10 nodeElementList
	//		ws* ( nodeElement ws* )*
	private void ParseNodeElementList(XmpNode parent, XmlNode xml_parent)
	{
		for (XmlNode node : xml_parent.ChildNodes)
		{
			if (node instanceof XmlWhitespace)
			{
				continue;
			}
			ParseNodeElement(parent, node);
		}
	}

	// 7.2.11 nodeElement
	//		start-element ( URI == nodeElementURIs,
	//						attributes == set ( ( idAttr | nodeIdAttr | aboutAttr )?, propertyAttr* ) )
	//		propertyEltList
	//		end-element()
	//
	// 7.2.13 propertyEltList
	//		ws* ( propertyElt ws* )*
	private void ParseNodeElement(XmpNode parent, XmlNode node)
	{
		if (!Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsNodeElement(node))
		{
			throw new CorruptFileException("Unexpected node found, invalid RDF?");
		}

		if (Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(node, RDF_NS, SEQ_URI))
		{
			parent.setType(XmpNodeType.Seq);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(node, RDF_NS, ALT_URI))
		{
			parent.setType(XmpNodeType.Alt);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(node, RDF_NS, BAG_URI))
		{
			parent.setType(XmpNodeType.Bag);
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(node, RDF_NS, DESCRIPTION_URI))
		{
			parent.setType(XmpNodeType.Struct);
		}
		else
		{
			throw new RuntimeException("Unknown nodeelement found! Perhaps an unimplemented collection?");
		}

		for (XmlAttribute attr : node.Attributes)
		{
			if (attr.In(XMLNS_NS))
			{
				continue;
			}
			if (attr.Is(RDF_NS, ID_URI) || attr.Is(RDF_NS, NODE_ID_URI) || attr.Is(RDF_NS, ABOUT_URI))
			{
				continue;
			}
			if (attr.Is(XML_NS, LANG_URI))
			{
				throw new CorruptFileException("xml:lang is not allowed here!");
			}
			parent.AddChild(new XmpNode(attr.NamespaceURI, attr.LocalName, attr.InnerText));
		}

		for (XmlNode child : node.ChildNodes)
		{
			if (child instanceof XmlWhitespace || child instanceof XmlComment)
			{
				continue;
			}
			ParsePropertyElement(parent, child);
		}
	}

	// 7.2.14 propertyElt
	//		resourcePropertyElt | literalPropertyElt | parseTypeLiteralPropertyElt |
	//		parseTypeResourcePropertyElt | parseTypeCollectionPropertyElt |
	//		parseTypeOtherPropertyElt | emptyPropertyElt
	private void ParsePropertyElement(XmpNode parent, XmlNode node)
	{
		int count = 0;
		boolean has_other = false;
		for (XmlAttribute attr : node.Attributes)
		{
			if (!attr.In(XMLNS_NS))
			{
				count++;
			}

			if (!attr.Is(XML_NS, LANG_URI) && !attr.Is(RDF_NS, ID_URI) && !attr.In(XMLNS_NS))
			{
				has_other = true;
			}
		}

		if (count > 3)
		{
			ParseEmptyPropertyElement(parent, node);
		}
		else
		{
			if (!has_other)
			{
				if (!node.HasChildNodes)
				{
					ParseEmptyPropertyElement(parent, node);
				}
				else
				{
					boolean only_text = true;
					for (XmlNode child : node.ChildNodes)
					{
						if (!(child instanceof XmlText))
						{
							only_text = false;
						}
					}

					if (only_text)
					{
						ParseLiteralPropertyElement(parent, node);
					}
					else
					{
						ParseResourcePropertyElement(parent, node);
					}
				}
			}
			else
			{
				for (XmlAttribute attr : node.Attributes)
				{
					if (attr.Is(XML_NS, LANG_URI) || attr.Is(RDF_NS, ID_URI) || attr.In(XMLNS_NS))
					{
						continue;
					}

					if (attr.Is(RDF_NS, DATA_TYPE_URI))
					{
						ParseLiteralPropertyElement(parent, node);
					}
					else if (!attr.Is(RDF_NS, PARSE_TYPE_URI))
					{
						ParseEmptyPropertyElement(parent, node);
					}
					else if (attr.InnerText.equals("Resource"))
					{
						ParseTypeResourcePropertyElement(parent, node);
					}
					else
					{
						// Neither Literal, Collection or anything else is allowed
						throw new CorruptFileException(String.format("This is not allowed in XMP! Bad XMP: %1$s", node.OuterXml));
					}
				}
			}
		}
	}

	// 7.2.15 resourcePropertyElt
	//		start-element ( URI == propertyElementURIs, attributes == set ( idAttr? ) )
	//		ws* nodeElement ws*
	//		end-element()
	private void ParseResourcePropertyElement(XmpNode parent, XmlNode node)
	{
		if (!Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsPropertyElement(node))
		{
			throw new CorruptFileException("Invalid property");
		}

		XmpNode new_node = new XmpNode(node.NamespaceURI, node.LocalName);
		for (XmlAttribute attr : node.Attributes)
		{
			if (attr.Is(XML_NS, LANG_URI))
			{
				new_node.AddQualifier(new XmpNode(XML_NS, LANG_URI, attr.InnerText));
			}
			else if (attr.Is(RDF_NS, ID_URI) || attr.In(XMLNS_NS))
			{
				continue;
			}

			throw new CorruptFileException(String.format("Invalid attribute: %1$s", attr.OuterXml));
		}

		boolean has_xml_children = false;
		for (XmlNode child : node.ChildNodes)
		{
			if (child instanceof XmlWhitespace)
			{
				continue;
			}
			if (child instanceof XmlText)
			{
				throw new CorruptFileException("Can't have text here!");
			}
			has_xml_children = true;

			ParseNodeElement(new_node, child);
		}

		if (!has_xml_children)
		{
			throw new CorruptFileException("Missing children for resource property element");
		}

		parent.AddChild(new_node);
	}

	// 7.2.16 literalPropertyElt
	//		start-element ( URI == propertyElementURIs, attributes == set ( idAttr?, datatypeAttr?) )
	//		text()
	//		end-element()
	private void ParseLiteralPropertyElement(XmpNode parent, XmlNode node)
	{
		if (!Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsPropertyElement(node))
		{
			throw new CorruptFileException("Invalid property");
		}
		parent.AddChild(CreateTextPropertyWithQualifiers(node, node.InnerText));
	}

	// 7.2.18 parseTypeResourcePropertyElt
	//		start-element ( URI == propertyElementURIs, attributes == set ( idAttr?, parseResource ) )
	//		propertyEltList
	//		end-element()
	private void ParseTypeResourcePropertyElement(XmpNode parent, XmlNode node)
	{
		if (!Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsPropertyElement(node))
		{
			throw new CorruptFileException("Invalid property");
		}

		XmpNode new_node = new XmpNode(node.NamespaceURI, node.LocalName);
		new_node.setType(XmpNodeType.Struct);

		for (XmlNode attr : node.Attributes)
		{
			if (Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(attr, XML_NS, LANG_URI))
			{
				new_node.AddQualifier(new XmpNode(XML_NS, LANG_URI, attr.InnerText));
			}
		}

		for (XmlNode child : node.ChildNodes)
		{
			if (child instanceof XmlWhitespace || child instanceof XmlComment)
			{
				continue;
			}
			ParsePropertyElement(new_node, child);
		}

		parent.AddChild(new_node);
	}

	// 7.2.21 emptyPropertyElt
	//		start-element ( URI == propertyElementURIs,
	//						attributes == set ( idAttr?, ( resourceAttr | nodeIdAttr )?, propertyAttr* ) )
	//		end-element()
	private void ParseEmptyPropertyElement(XmpNode parent, XmlNode node)
	{
		if (!Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsPropertyElement(node))
		{
			throw new CorruptFileException("Invalid property");
		}
		if (node.HasChildNodes)
		{
			throw new CorruptFileException(String.format("Can't have content in this node! Node: %1$s", node.OuterXml));
		}

		Object tempVar = node.Attributes.GetNamedItem(VALUE_URI, RDF_NS);
		XmlAttribute rdf_value = tempVar instanceof XmlAttribute ? (XmlAttribute)tempVar : null;
		Object tempVar2 = node.Attributes.GetNamedItem(RESOURCE_URI, RDF_NS);
		XmlAttribute rdf_resource = tempVar2 instanceof XmlAttribute ? (XmlAttribute)tempVar2 : null;

		// Options 1 and 2
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var simple_prop_val = (rdf_value != null) ? rdf_value : (rdf_resource != null) ? rdf_resource : null;
		if (simple_prop_val != null)
		{
			String value = simple_prop_val.InnerText;
			parent.AddChild(CreateTextPropertyWithQualifiers(node, value));
			return;
		}

		// Options 3 & 4
		XmpNode new_node = new XmpNode(node.NamespaceURI, node.LocalName);
		for (XmlAttribute a : node.Attributes)
		{
			if (a.Is(RDF_NS, ID_URI) || a.Is(RDF_NS, NODE_ID_URI))
			{
				continue;
			}
			else if (a.In(XMLNS_NS))
			{
				continue;
			}
			else if (a.Is(XML_NS, LANG_URI))
			{
				new_node.AddQualifier(new XmpNode(XML_NS, LANG_URI, a.InnerText));
			}

			new_node.AddChild(new XmpNode(a.NamespaceURI, a.LocalName, a.InnerText));
		}
		parent.AddChild(new_node);
	}

	private XmpNode CreateTextPropertyWithQualifiers(XmlNode node, String value)
	{
		XmpNode t = new XmpNode(node.NamespaceURI, node.LocalName, value);
		for (XmlAttribute attr : node.Attributes)
		{
			if (attr.In(XMLNS_NS))
			{
				continue;
			}
			if (attr.Is(RDF_NS, VALUE_URI) || attr.Is(RDF_NS, RESOURCE_URI))
			{
				continue; // These aren't qualifiers
			}
			t.AddQualifier(new XmpNode(attr.NamespaceURI, attr.LocalName, attr.InnerText));
		}
		return t;
	}

	private XmpNode NewNode(String ns, String name)
	{
		HashMap <String, XmpNode> ns_nodes = null;

		if (!nodes.containsKey(ns))
		{
			ns_nodes = new HashMap<String, XmpNode> ();
			nodes.put(ns, ns_nodes);

		}
		else
		{
			ns_nodes = nodes.get(ns);
		}

		if (ns_nodes.containsKey(name))
		{
			for (XmpNode child_node : getNodeTree().getChildren())
			{
				if (child_node.getNamespace().equals(ns) && child_node.getName().equals(name))
				{
					getNodeTree().RemoveChild(child_node);
					break;
				}
			}

			ns_nodes.remove(name);
		}

		XmpNode node = new XmpNode(ns, name);
		ns_nodes.put(name, node);

		getNodeTree().AddChild(node);

		return node;
	}

	private XmpNode NewNode(String ns, String name, XmpNodeType type)
	{
		XmpNode node = NewNode(ns, name);
		node.setType(type);

		return node;
	}

	private void RemoveNode(String ns, String name)
	{
		if (!nodes.containsKey(ns))
		{
			return;
		}

		for (XmpNode node : getNodeTree().getChildren())
		{
			if (node.getNamespace().equals(ns) && node.getName().equals(name))
			{
				getNodeTree().RemoveChild(node);
				break;
			}
		}

		nodes.get(ns).remove(name);
	}

	/** 
	 Accept visitors to touch up the node tree.
	*/
	private void AcceptVisitors()
	{
		getNodeTree().Accept(new NodeIndexVisitor(this));
		//NodeTree.Dump ();
		//Console.WriteLine (node.OuterXml);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.XMP" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.XMP;
	}

	/** 
		Get the tree of <see cref="XmpNode" /> nodes. These contain the values
		parsed from the XMP file.
	*/
	private XmpNode NodeTree;
	public final XmpNode getNodeTree()
	{
		return NodeTree;
	}
	private void setNodeTree(XmpNode value)
	{
		NodeTree = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		   Replace the current tag with the given one.
		
	 @param tag
		The tag from which the data should be copied.
	 
	*/
	public final void ReplaceFrom(XmpTag tag)
	{
		setNodeTree(tag.getNodeTree());
		nodes = new HashMap<String, HashMap<String, XmpNode>> ();
		AcceptVisitors();
	}

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		throw new UnsupportedOperationException();
	}

	/** 
		Finds the node associated with the namespace <paramref name="ns"/> and the name
		<paramref name="name"/>.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @return 
		A <see cref="XmpNode"/> with the found node, or <see langword="null"/>
		if no node was found.
	 
	*/
	public final XmpNode FindNode(String ns, String name)
	{
		if (!nodes.containsKey(ns))
		{
			return null;
		}
		if (!nodes.get(ns).containsKey(name))
		{
			return null;
		}
		return nodes.get(ns).get(name);

	}

	/** 
		Returns the text of the node associated with the namespace
		@param ns/ and the name <paramref name="name"/>.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @return 
		A <see cref="System.String"/> with the text of the node, or
		<see langword="null"/> if no such node exists, or if it is not
		a text node.
	 
	*/
	public final String GetTextNode(String ns, String name)
	{
		Rasad.Core.Media.MediaMetadataManagement.Xmp.XmpNode node = FindNode(ns, name);

		if (node == null || node.getType() != XmpNodeType.Simple)
		{
			return null;
		}

		return node.getValue();
	}

	/** 
		Creates a new text node associated with the namespace
		<paramref name="ns"/> and the name <paramref name="name"/>.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @param value
		A <see cref="System.String"/> with the value for the new node.
		If <see langword="null"/> is given, a possibly existing node will
		be deleted.
	 
	*/
	public final void SetTextNode(String ns, String name, String value)
	{
		if (value == null)
		{
			RemoveNode(ns, name);
			return;
		}

		Rasad.Core.Media.MediaMetadataManagement.Xmp.XmpNode node = NewNode(ns, name);
		node.setValue(value);
	}

	/** 
		Searches for a node holding language alternatives. The return value
		is the value of the default language stored by the node. The node is
		identified by the namespace <paramref name="ns"/> and the name
		<paramref name="name"/>. If the default language is not set, an arbitrary
		one is chosen.
		It is also tried to return the value a simple text node, if no
		associated alt-node exists.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @return 
		A <see cref="System.String"/> with the value stored as default language
		for the referenced node.
	 
	*/
	public final String GetLangAltNode(String ns, String name)
	{
		Rasad.Core.Media.MediaMetadataManagement.Xmp.XmpNode node = FindNode(ns, name);

		if (node == null)
		{
			return null;
		}

		if (node.getType() == XmpNodeType.Simple)
		{
			return node.getValue();
		}

		if (node.getType() != XmpNodeType.Alt)
		{
			return null;
		}

		ArrayList<XmpNode> children = node.getChildren();
		for (XmpNode child_node : children)
		{
			Rasad.Core.Media.MediaMetadataManagement.Xmp.XmpNode qualifier = child_node.GetQualifier(XML_NS, "lang");
			if (qualifier != null && qualifier.getValue().equals("x-default"))
			{
				return child_node.getValue();
			}
		}

		if (!children.isEmpty() && children.get(0).getType() == XmpNodeType.Simple)
		{
			return children.get(0).getValue();
		}

		return null;
	}

	/** 
		Stores a the given <paramref name="value"/> as the default language
		value for the alt-node associated with the namespace
		<paramref name="ns"/> and the name <paramref name="name"/>.
		All other alternatives set, are deleted by this method.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @param value
		A <see cref="System.String"/> with the value for the default language
		to set. If <see langword="null"/> is given, a possibly existing node
		will be deleted.
	 
	*/
	public final void SetLangAltNode(String ns, String name, String value)
	{
		if (value == null)
		{
			RemoveNode(ns, name);
			return;
		}

		Rasad.Core.Media.MediaMetadataManagement.Xmp.XmpNode node = NewNode(ns, name, XmpNodeType.Alt);

		XmpNode child_node = new XmpNode(RDF_NS, LI_URI, value);
		child_node.AddQualifier(new XmpNode(XML_NS, "lang", "x-default"));

		node.AddChild(child_node);
	}

	/** 
		The method returns an array of <see cref="System.String"/> values
		which are the stored text of the child nodes of the node associated
		with the namespace <paramref name="ns"/> and the name <paramref name="name"/>.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @return 
		A <see cref="System.String[]"/> with the text stored in the child nodes.
	 
	*/
	public final String[] GetCollectionNode(String ns, String name)
	{
		Rasad.Core.Media.MediaMetadataManagement.Xmp.XmpNode node = FindNode(ns, name);

		if (node == null)
		{
			return null;
		}

		ArrayList<String> items = new ArrayList<String> ();

		for (XmpNode child : node.getChildren())
		{

			String item = child.getValue();
			if (item != null)
			{
				items.add(item);
			}
		}

		return items.toArray(new String[0]);
	}

	/** 
		Sets a <see cref="System.String[]"/> as texts to the children of the
		node associated with the namespace <paramref name="ns"/> and the name
		<paramref name="name"/>.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @param values
		A <see cref="System.String[]"/> with the values to set for the children.
	 
	 @param type
		A <see cref="XmpNodeType"/> with the type of the parent node.
	 
	*/
	public final void SetCollectionNode(String ns, String name, String[] values, XmpNodeType type)
	{
		if (type == XmpNodeType.Simple || type == XmpNodeType.Alt)
		{
			throw new IllegalArgumentException("type");
		}

		if (values == null)
		{
			RemoveNode(ns, name);
			return;
		}

		Rasad.Core.Media.MediaMetadataManagement.Xmp.XmpNode node = NewNode(ns, name, type);
		for (String value : values)
		{
			node.AddChild(new XmpNode(RDF_NS, LI_URI, value));
		}
	}

	/** 
		Returns the rational value of the node associated with the namespace
		@param ns/ and the name <paramref name="name"/>.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @return 
		A double? with the read value, or
		<see langword="null"/> if no such node exists, or if it is in wrong
		format.
	 
	 
		Rational nodes only used in EXIF schema.
	 
	*/
	public final Double GetRationalNode(String ns, String name)
	{
		String text = GetTextNode(ns, name);

		if (text == null)
		{
			return null;
		}

		// format is expected to be e.g. "1/200" ...
		String [] values = text.split("[/]", -1);

		if (values.length != 2)
		{

			// but we also try to parse a double value directly.
			double result;
			tangible.OutObject<Double> tempOut_result = new tangible.OutObject<Double>();
			if (tangible.TryParseHelper.tryParseDouble(text, tempOut_result))
			{
			result = tempOut_result.argValue;
				return result;
			}
		else
		{
			result = tempOut_result.argValue;
		}

			return null;
		}

		double nom, den;
		tangible.OutObject<Double> tempOut_nom = new tangible.OutObject<Double>();
		tangible.OutObject<Double> tempOut_den = new tangible.OutObject<Double>();
		if (tangible.TryParseHelper.tryParseDouble(values[0], tempOut_nom) && tangible.TryParseHelper.tryParseDouble(values[1], tempOut_den))
		{
		den = tempOut_den.argValue;
		nom = tempOut_nom.argValue;
			if (den != 0.0)
			{
				return ((double) nom) / ((double) den);
			}
		}
	else
	{
		den = tempOut_den.argValue;
		nom = tempOut_nom.argValue;
	}

		return null;
	}

	/** 
		Creates a new rational node with the namespace
		@param ns/ and the name <paramref name="name"/>.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @param value
		A <see cref="System.Double"/> with the value of the node.
	 
	*/
	public final void SetRationalNode(String ns, String name, double value)
	{

		String fraction = DecimalToFraction(value, (long) Math.pow(10, 10));
		SetTextNode(ns, name, fraction);
	}

	// Based on http://www.ics.uci.edu/~eppstein/numth/frap.c
	private String DecimalToFraction(double value, long max_denominator)
	{
		long[][] m = new long [2][2];
		m[0][0] = m[1][1] = 1;
		m[1][0] = m[0][1] = 0;

		double x = value;
		long ai;

		while (m[1][0] * (ai = (long)x) + m[1][1] <= max_denominator)
		{
			long t = m[0][0] * ai + m[0][1];
			m[0][1] = m[0][0];
			m[0][0] = t;
			t = m[1][0] * ai + m[1][1];
			m[1][1] = m[1][0];
			m[1][0] = t;
			if (x == (double)ai)
			{
				break; // AF: division by zero
			}
			x = 1 / (x - (double) ai);
			if (x > (double) 0x7FFFFFFF)
			{
				break; // AF: representation failure
			}

		}

		return String.format("%1$s/%2$s", m[0][0], m[1][0]);
	}


	/** 
		Returns the unsigned integer value of the node associated with the
		namespace @param ns/ and the name <paramref name="name"/>.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the node.
	 
	 @param name
		A <see cref="System.String"/> with the name of the node.
	 
	 @return 
		A uint? with the read value, or
		<see langword="null"/> if no such node exists, or if it is in wrong
		format.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nullable<uint> GetUIntNode(string ns, string name)
	public final Integer GetUIntNode(String ns, String name)
	{
		String text = GetTextNode(ns, name);

		if (text == null)
		{
			return null;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint result;
		int result;

		tangible.OutObject<Integer> tempOut_result = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (UInt32.TryParse(text, out result))
		if (tangible.TryParseHelper.tryParseInt(text, tempOut_result))
		{
		result = tempOut_result.argValue;
			return result;
		}
	else
	{
		result = tempOut_result.argValue;
	}

		return null;
	}

	/** 
		Renders the current instance to an XMP <see cref="System.String"/>.
	 
	 @return 
		A <see cref="System.String"/> with the XMP structure.
	 
	*/
	public final String Render()
	{
		XmlDocument doc = new XmlDocument(NameTable);
		XmlNode meta = CreateNode(doc, "xmpmeta", ADOBE_X_NS);
		XmlNode rdf = CreateNode(doc, "RDF", RDF_NS);
		XmlNode description = CreateNode(doc, "Description", RDF_NS);
		getNodeTree().RenderInto(description);
		doc.AppendChild(meta);
		meta.AppendChild(rdf);
		rdf.AppendChild(description);
		return doc.OuterXml;
	}

	/** 
		Make sure there's a suitable prefix mapped for the given namespace URI.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace that will be rendered.
	 
	*/
	private static void EnsureNamespacePrefix(String ns)
	{
		if (!NamespacePrefixes.containsKey(ns))
		{
			NamespacePrefixes.put(ns, String.format("ns%1$s", ++anon_ns_count));
			System.out.printf("TAGLIB# DEBUG: Added %1$s prefix for %2$s namespace (XMP)" + "\r\n", NamespacePrefixes.get(ns), ns);
		}
	}

	public static XmlNode CreateNode(XmlDocument doc, String name, String ns)
	{
		EnsureNamespacePrefix(ns);
		return doc.CreateElement(NamespacePrefixes.get(ns), name, ns);
	}

	public static XmlAttribute CreateAttribute(XmlDocument doc, String name, String ns)
	{
		EnsureNamespacePrefix(ns);
		return doc.CreateAttribute(NamespacePrefixes.get(ns), name, ns);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

	private static class NodeIndexVisitor implements XmpNodeVisitor
	{
		private XmpTag tag;

		public NodeIndexVisitor(XmpTag tag)
		{
			this.tag = tag;
		}

		public final void Visit(XmpNode node)
		{
			// TODO: This should be a proper check to see if it is a nodeElement
			if (node.getNamespace().equals(XmpTag.RDF_NS) && node.getName().equals(XmpTag.LI_URI))
			{
				return;
			}

			AddNode(node);
		}

		private void AddNode(XmpNode node)
		{
			if (tag.nodes == null)
			{
				tag.nodes = new HashMap<String, HashMap<String, XmpNode>> ();
			}
			if (!tag.nodes.containsKey(node.getNamespace()))
			{
				tag.nodes.put(node.getNamespace(), new HashMap<String, XmpNode> ());
			}

			tag.nodes.get(node.getNamespace()).put(node.getName(), node);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Metadata fields

	/** 
		Gets or sets the comment for the image described
		by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the comment of the
		current instace.
	 </value>
	*/
	@Override
	public String getComment()
	{
		String comment = GetLangAltNode(DC_NS, "description");

		if (comment != null)
		{
			return comment;
		}

		comment = GetLangAltNode(EXIF_NS, "UserComment");
		return comment;
	}
	@Override
	public void setComment(String value)
	{
		SetLangAltNode(DC_NS, "description", value);
		SetLangAltNode(EXIF_NS, "UserComment", value);
	}

	/** 
		Gets or sets the keywords for the image described
		by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the keywords of the
		current instace.
	 </value>
	*/
	@Override
	public String[] getKeywords()
	{
		String[] tempVar = GetCollectionNode(DC_NS, "subject");
		return (tempVar != null) ? tempVar : new String [] {};
	}
	@Override
	public void setKeywords(String[] value)
	{
		SetCollectionNode(DC_NS, "subject", value, XmpNodeType.Bag);
	}

	/** 
		Gets or sets the rating for the image described
		by the current instance.
	 
	 <value>
		A <see cref="System.Nullable"/> containing the rating of the
		current instace.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override Nullable<uint> getRating()
	@Override
	public Integer getRating()
	{
		return GetUIntNode(XAP_NS, "Rating");
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setRating(Nullable<uint> value)
	@Override
	public void setRating(Integer value)
	{
		SetTextNode(XAP_NS, "Rating", value != null ? value.toString() : null);
	}

	/** 
		Gets or sets the time when the image, the current instance
		belongs to, was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the time the image was taken.
	 </value>
	*/
	@Override
	public DateTime getDateTime()
	{
			// TODO: use correct parsing
		try
		{
			return getDateTime().Parse(GetTextNode(XAP_NS, "CreateDate"));
		}
		catch (java.lang.Exception e)
		{
		}

		return null;
	}
	@Override
	public void setDateTime(DateTime value)
	{
			// TODO: write correct format
		SetTextNode(XAP_NS, "CreateDate", value != null ? value.toString() : null);
	}

	/** 
		Gets or sets the orientation of the image described
		by the current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Image.ImageOrientation" /> containing the orientation of the
		image
	 </value>
	*/
	@Override
	public ImageOrientation getOrientation()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var orientation = GetUIntNode(TIFF_NS, "Orientation");
		Integer orientation = GetUIntNode(TIFF_NS, "Orientation");

		if (orientation != null)
		{
			return ImageOrientation.forValue(orientation);
		}

		return ImageOrientation.None;
	}
	@Override
	public void setOrientation(ImageOrientation value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((uint) value < 1U || (uint) value > 8U)
		if ((int) value.getValue() < 1 || (int) value.getValue() > 8)
		{
			RemoveNode(TIFF_NS, "Orientation");
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: SetTextNode(TIFF_NS, "Orientation", String.Format("{0}", (ushort) value));
		SetTextNode(TIFF_NS, "Orientation", String.format("%1$s", (short) value.getValue()));
	}

	/** 
		Gets or sets the software the image, the current instance
		belongs to, was created with.
	 
	 <value>
		A <see cref="string" /> containing the name of the
		software the current instace was created with.
	 </value>
	*/
	@Override
	public String getSoftware()
	{
		return GetTextNode(XAP_NS, "CreatorTool");
	}
	@Override
	public void setSoftware(String value)
	{
		SetTextNode(XAP_NS, "CreatorTool", value);
	}

	/** 
		Gets or sets the latitude of the GPS coordinate the current
		image was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the latitude ranging from -90.0
		to +90.0 degrees.
	 </value>
	*/
	@Override
	public Double getLatitude()
	{
		return null;
	}
	@Override
	public void setLatitude(Double value)
	{
	}

	/** 
		Gets or sets the longitude of the GPS coordinate the current
		image was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the longitude ranging from -180.0
		to +180.0 degrees.
	 </value>
	*/
	@Override
	public Double getLongitude()
	{
		return null;
	}
	@Override
	public void setLongitude(Double value)
	{
	}

	/** 
		Gets or sets the altitude of the GPS coordinate the current
		image was taken. The unit is meter.
	 
	 <value>
		A <see cref="System.Nullable"/> with the altitude. A positive value
		is above sea level, a negative one below sea level. The unit is meter.
	 </value>
	*/
	@Override
	public Double getAltitude()
	{
		return null;
	}
	@Override
	public void setAltitude(Double value)
	{
	}

	/** 
		Gets the exposure time the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the exposure time in seconds.
	 </value>
	*/
	@Override
	public Double getExposureTime()
	{
		return GetRationalNode(EXIF_NS, "ExposureTime");
	}
	@Override
	public void setExposureTime(Double value)
	{
		SetRationalNode(EXIF_NS, "ExposureTime", value != null ? (double) value : 0);
	}

	/** 
		Gets the FNumber the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the FNumber.
	 </value>
	 
		Bibble wrongly tends to put this into tiff:FNumber so we
		use that as a fallback and correct it if needed.
	 
	*/
	@Override
	public Double getFNumber()
	{
		Nullable<Double> tempVar = GetRationalNode(EXIF_NS, "FNumber");
		return (tempVar != null) ? tempVar : GetRationalNode(TIFF_NS, "FNumber");
	}
	@Override
	public void setFNumber(Double value)
	{
		SetTextNode(TIFF_NS, "FNumber", null); // Remove wrong value
		SetRationalNode(EXIF_NS, "FNumber", value != null ? (double) value : 0);
	}

	/** 
		Gets the ISO speed the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the ISO speed as defined in ISO 12232.
	 </value>
	 
		Bibble writes ISOSpeedRating instead of ISOSpeedRatings.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override Nullable<uint> getISOSpeedRatings()
	@Override
	public Integer getISOSpeedRatings()
	{
		String[] values = GetCollectionNode(EXIF_NS, "ISOSpeedRatings");

		if (values != null && values.length > 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint result;
			int result;
			tangible.OutObject<Integer> tempOut_result = new tangible.OutObject<Integer>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (UInt32.TryParse(values[0], out result))
			if (tangible.TryParseHelper.tryParseInt(values[0], tempOut_result))
			{
			result = tempOut_result.argValue;
				return result;
			}
		else
		{
			result = tempOut_result.argValue;
		}
		}

			// Bibble fallback.
		return GetUIntNode(EXIF_NS, "ISOSpeedRating");
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setISOSpeedRatings(Nullable<uint> value)
	@Override
	public void setISOSpeedRatings(Integer value)
	{
		SetCollectionNode(EXIF_NS, "ISOSpeedRating", null, XmpNodeType.Seq);
		SetCollectionNode(EXIF_NS, "ISOSpeedRatings", new String [] {value.toString()}, XmpNodeType.Seq);
	}

	/** 
		Gets the focal length the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the focal length in millimeters.
	 </value>
	*/
	@Override
	public Double getFocalLength()
	{
		return GetRationalNode(EXIF_NS, "FocalLength");
	}
	@Override
	public void setFocalLength(Double value)
	{
		SetRationalNode(EXIF_NS, "FocalLength", value != null ? (double) value : 0);
	}

	/** 
		Gets the focal length the image, the current instance belongs
		to, was taken with, assuming a 35mm film camera.
	 
	 <value>
		A <see cref="System.Nullable"/> with the focal length in 35mm equivalent in millimeters.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override Nullable<uint> getFocalLengthIn35mmFilm()
	@Override
	public Integer getFocalLengthIn35mmFilm()
	{
		return GetUIntNode(EXIF_NS, "FocalLengthIn35mmFilm");
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setFocalLengthIn35mmFilm(Nullable<uint> value)
	@Override
	public void setFocalLengthIn35mmFilm(Integer value)
	{
		SetTextNode(EXIF_NS, "FocalLengthIn35mmFilm", value != null ? String.valueOf(value.intValue()) : "");
	}

	/** 
		Gets the manufacture of the recording equipment the image, the
		current instance belongs to, was taken with.
	 
	 <value>
		A <see cref="string" /> with the manufacture name.
	 </value>
	*/
	@Override
	public String getMake()
	{
		return GetTextNode(TIFF_NS, "Make");
	}
	@Override
	public void setMake(String value)
	{
		SetTextNode(TIFF_NS, "Make", value);
	}

	/** 
		Gets the model name of the recording equipment the image, the
		current instance belongs to, was taken with.
	 
	 <value>
		A <see cref="string" /> with the model name.
	 </value>
	*/
	@Override
	public String getModel()
	{
		return GetTextNode(TIFF_NS, "Model");
	}
	@Override
	public void setModel(String value)
	{
		SetTextNode(TIFF_NS, "Model", value);
	}

	/** 
		Gets or sets the creator of the image.
	 
	 <value>
		A <see cref="string" /> with the name of the creator.
	 </value>
	*/
	@Override
	public String getCreator()
	{
		String [] values = GetCollectionNode(DC_NS, "creator");
		if (values != null && values.length > 0)
		{
			return values [0];
		}

		return null;
	}
	@Override
	public void setCreator(String value)
	{
		if (value == null)
		{
			RemoveNode(DC_NS, "creator");
		}

		SetCollectionNode(DC_NS, "creator", new String [] {value}, XmpNodeType.Seq);
	}

	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	*/
	@Override
	public String getTitle()
	{
		return GetLangAltNode(DC_NS, "title");
	}
	@Override
	public void setTitle(String value)
	{
		SetLangAltNode(DC_NS, "title", value);
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	*/
	@Override
	public String getCopyright()
	{
		return GetLangAltNode(DC_NS, "rights");
	}
	@Override
	public void setCopyright(String value)
	{
		SetLangAltNode(DC_NS, "rights", value);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}