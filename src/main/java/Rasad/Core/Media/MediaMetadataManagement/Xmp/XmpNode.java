package Rasad.Core.Media.MediaMetadataManagement.Xmp;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// XmpNode.cs:
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
	An <see cref="XmpNode"/> represents a node in the XMP document.
	This is any valid XMP element.
*/
public class XmpNode
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** <value>
		The children of the current node
	 </value>
	*/
	private ArrayList<XmpNode> children;

	/** <value>
		The qualifiers of the current node
	 </value>
	*/
	private HashMap<String, HashMap<String, XmpNode>> qualifiers;

	/** <value>
		The name of the current node
	 </value>
	*/
	private String name;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Properties

	/** <value>
		The namespace the current instance belongs to
	 </value>
	*/
	private String Namespace;
	public final String getNamespace()
	{
		return Namespace;
	}
	private void setNamespace(String value)
	{
		Namespace = value;
	}

	/** <value>
		The name of the current node instance
	 </value>
	*/
	public final String getName()
	{
		return name;
	}
	public final void setName(String value)
	{
		if (name != null)
		{
			throw new RuntimeException("Cannot change named node");
		}

		if (value == null)
		{
			throw new IllegalArgumentException("value");
		}

		name = value;
	}

	/** <value>
		The text value of the current node
	 </value>
	*/
	private String Value;
	public final String getValue()
	{
		return Value;
	}
	public final void setValue(String value)
	{
		Value = value;
	}

	/** <value>
		The type of the current node
	 </value>
	*/
	private XmpNodeType Type = XmpNodeType.values()[0];
	public final XmpNodeType getType()
	{
		return Type;
	}
	public final void setType(XmpNodeType value)
	{
		Type = value;
	}


	/** <value>
		The number of qualifiers of the current instance
	 </value>
	*/
	public final int getQualifierCount()
	{
		if (qualifiers == null)
		{
			return 0;
		}
		int count = 0;
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		for (var collection : qualifiers.values())
		{
			count += collection == null ? 0 : collection.Count;
		}
		return count;
	}

	/** <value>
		The children of the current instance.
	 </value>
	*/
		// TODO: do not return a list, because it can be modified elsewhere
	public final ArrayList<XmpNode> getChildren()
	{
		return (children != null) ? children : new ArrayList<XmpNode> ();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructor.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the new instance.
	 
	 @param name
		A <see cref="System.String"/> with the name of the new instance.
	 
	*/
	public XmpNode(String ns, String name)
	{
		// Namespaces in XMP need to end with / or #. Broken files are known
		// to be floating around (we have one with MicrosoftPhoto in our tree).
		// Correcting below.
		if (!ns.equals("") && !XmpTag.XML_NS.equals(ns) && !ns.endsWith("/") && !ns.endsWith("#"))
		{
			ns = String.format("%1$s/", ns);
		}

		setNamespace(ns);
		setName(name);
		setType(XmpNodeType.Simple);
		setValue("");
	}

	/** 
		Constructor.
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the new instance.
	 
	 @param name
		A <see cref="System.String"/> with the name of the new instance.
	 
	 @param value
		A <see cref="System.String"/> with the txt value of the new instance.
	 
	*/
	public XmpNode(String ns, String name, String value)
	{
		this(ns, name);
		setValue(value);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Adds a node as child of the current node
	 
	 @param node
		A <see cref="XmpNode"/> to be add as child
	 
	*/
	public final void AddChild(XmpNode node)
	{
		if (node == null || node == this)
		{
			throw new IllegalArgumentException("node");
		}

		if (children == null)
		{
			children = new ArrayList<XmpNode> ();
		}

		children.add(node);
	}

	/** 
		Removes the given node as child of the current instance
	 
	 @param node
		A <see cref="XmpNode"/> to remove as child
	 
	*/
	public final void RemoveChild(XmpNode node)
	{
		if (children == null)
		{
			return;
		}

		children.remove(node);
	}

	/** 
		Get a named child from the current node
	 
	 @param ns
		The namespace of the child node.
	 
	 @param name
		The name of the child node.
	 
	 @return 
		A <see cref="XmpNode"/> with the given name and namespace.
	 
	*/
	public final XmpNode GetChild(String ns, String name)
	{
		for (XmpNode node : children)
		{
			if (node.getNamespace().equals(ns) && node.getName().equals(name))
			{
				return node;
			}
		}
		return null;
	}

	/** 
		Adds a node as qualifier of the current instance
	 
	 @param node
		A <see cref="XmpNode"/> to add as qualifier
	 
	*/
	public final void AddQualifier(XmpNode node)
	{
		if (node == null || node == this)
		{
			throw new IllegalArgumentException("node");
		}

		if (qualifiers == null)
		{
			qualifiers = new HashMap<String, HashMap<String, XmpNode>> ();
		}

		if (!qualifiers.containsKey(node.getNamespace()))
		{
			qualifiers.put(node.getNamespace(), new HashMap<String, XmpNode> ());
		}

		qualifiers.get(node.getNamespace()).put(node.getName(), node);
	}

	/** 
		Returns the qualifier associated with the given namespace <paramref name="ns"/>
		and name <paramref name="name"/>
	 
	 @param ns
		A <see cref="System.String"/> with the namespace of the qualifier
	 
	 @param name
		A <see cref="System.String"/> with the name of the qualifier
	 
	 @return 
		A <see cref="XmpNode"/> with the qualifier
	 
	*/
	public final XmpNode GetQualifier(String ns, String name)
	{
		if (qualifiers == null)
		{
			return null;
		}
		if (!qualifiers.containsKey(ns))
		{
			return null;
		}
		if (!qualifiers.get(ns).containsKey(name))
		{
			return null;
		}
		return qualifiers.get(ns).get(name);
	}

	/** 
		Print a debug output of the node.
	*/
	public final void Dump()
	{
		Dump("");
	}

	/** 
		Calls the Visitor for this node and every child node.
	 
	 @param visitor
		A <see cref="XmpNodeVisitor"/> to access the node and the children.
	 
	*/
	public final void Accept(XmpNodeVisitor visitor)
	{
		visitor.Visit(this);

		// TODO: what is with the qualifiers ?
		// either add them to be also visited, or add a comment
		if (children != null)
		{
			for (XmpNode child : children)
			{
				child.Accept(visitor);
			}
		}
	}

	/** 
		Renders the current instance as child of the given node to the
		given <see cref="XmlNode"/>
	 
	 @param parent
		A <see cref="XmlNode"/> to render the current instance as child of.
	 
	*/
	public final void RenderInto(XmlNode parent)
	{
		if (getIsRootNode())
		{
			AddAllChildrenTo(parent);

		}
		else if (getIsReallySimpleType() && parent.Attributes.GetNamedItem(XmpTag.PARSE_TYPE_URI, XmpTag.RDF_NS) == null)
		{
			// Simple values can be added as attributes of the parent node. Not allowed when the parent has an rdf:parseType.
			XmlAttribute attr = XmpTag.CreateAttribute(parent.OwnerDocument, getName(), getNamespace());
			attr.Value = getValue();
			parent.Attributes.Append(attr);

		}
		else if (getType() == XmpNodeType.Simple || getType() == XmpNodeType.Struct)
		{
			XmlNode node = XmpTag.CreateNode(parent.OwnerDocument, getName(), getNamespace());
			node.InnerText = getValue();

			if (getType() == XmpNodeType.Struct)
			{
				// Structured types are always handled as a parseType=Resource node. This way, IsReallySimpleType will
				// not match for child nodes, which makes sure they are added as extra nodes to this node. Does the
				// trick well, unit tests that prove this are in XmpSpecTest.
				XmlAttribute attr = XmpTag.CreateAttribute(parent.OwnerDocument, XmpTag.PARSE_TYPE_URI, XmpTag.RDF_NS);
				attr.Value = "Resource";
				node.Attributes.Append(attr);
			}

			AddAllQualifiersTo(node);
			AddAllChildrenTo(node);
			parent.AppendChild(node);

		}
		else if (getType() == XmpNodeType.Bag)
		{
			XmlNode node = XmpTag.CreateNode(parent.OwnerDocument, getName(), getNamespace());
			// TODO: Add all qualifiers.
			if (getQualifierCount() > 0)
			{
				throw new UnsupportedOperationException();
			}
			XmlNode bag = XmpTag.CreateNode(parent.OwnerDocument, XmpTag.BAG_URI, XmpTag.RDF_NS);
			for (XmpNode child : getChildren())
			{
				child.RenderInto(bag);
			}
			node.AppendChild(bag);
			parent.AppendChild(node);

		}
		else if (getType() == XmpNodeType.Alt)
		{
			XmlNode node = XmpTag.CreateNode(parent.OwnerDocument, getName(), getNamespace());
			// TODO: Add all qualifiers.
			if (getQualifierCount() > 0)
			{
				throw new UnsupportedOperationException();
			}
			XmlNode bag = XmpTag.CreateNode(parent.OwnerDocument, XmpTag.ALT_URI, XmpTag.RDF_NS);
			for (XmpNode child : getChildren())
			{
				child.RenderInto(bag);
			}
			node.AppendChild(bag);
			parent.AppendChild(node);

		}
		else if (getType() == XmpNodeType.Seq)
		{
			XmlNode node = XmpTag.CreateNode(parent.OwnerDocument, getName(), getNamespace());
			// TODO: Add all qualifiers.
			if (getQualifierCount() > 0)
			{
				throw new UnsupportedOperationException();
			}
			XmlNode bag = XmpTag.CreateNode(parent.OwnerDocument, XmpTag.SEQ_URI, XmpTag.RDF_NS);
			for (XmpNode child : getChildren())
			{
				child.RenderInto(bag);
			}
			node.AppendChild(bag);
			parent.AppendChild(node);

		}
		else
		{
			// Probably some combination of things we don't fully cover yet.
			Dump();
			throw new UnsupportedOperationException();
		}
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Internal Methods

	public final void Dump(String prefix)
	{
		System.out.printf("%1$s%2$s%3$s (%5$s) = \"%4$s\"" + "\r\n", prefix, getNamespace(), getName(), getValue(), getType());
		if (qualifiers != null)
		{
			System.out.printf("%1$sQualifiers:" + "\r\n", prefix);

			for (String ns : qualifiers.keySet())
			{
				for (String name : qualifiers.get(ns).keySet())
				{
					qualifiers.get(ns).get(name).Dump(prefix + "  ->  ");
				}
			}
		}
		if (children != null)
		{
			System.out.printf("%1$sChildren:" + "\r\n", prefix);

			for (XmpNode child : children)
			{
				child.Dump(prefix + "  ->  ");
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	/** 
		Is this a node that we can transform into an attribute of the
		parent node? Yes if it has no qualifiers or children, nor is
		it part of a list.
	*/
	private boolean getIsReallySimpleType()
	{
		return getType() == XmpNodeType.Simple && (children == null || children.isEmpty()) && getQualifierCount() == 0 && (!getName().equals(XmpTag.LI_URI) || !getNamespace().equals(XmpTag.RDF_NS));
	}

	/** 
		Is this the root node of the tree?
	*/
	private boolean getIsRootNode()
	{
		return getName().equals("") && getNamespace().equals("");
	}

	private void AddAllQualifiersTo(XmlNode xml)
	{
		if (qualifiers == null)
		{
			return;
		}
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		for (var collection : qualifiers.values())
		{
			for (XmpNode node : collection.Values)
			{
				XmlAttribute attr = XmpTag.CreateAttribute(xml.OwnerDocument, node.getName(), node.getNamespace());
				attr.Value = node.getValue();
				xml.Attributes.Append(attr);
			}
		}
	}

	private void AddAllChildrenTo(XmlNode parent)
	{
		if (children == null)
		{
			return;
		}
		for (XmpNode child : children)
		{
			child.RenderInto(parent);
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion


}