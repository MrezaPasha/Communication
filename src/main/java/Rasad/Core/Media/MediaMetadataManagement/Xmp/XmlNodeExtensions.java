package Rasad.Core.Media.MediaMetadataManagement.Xmp;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// XmlNodeExtensions.cs:
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



public final class XmlNodeExtensions
{
	public static boolean In(XmlNode node, String ns)
	{
		return ns.equals(node.NamespaceURI);
	}

	public static boolean Is(XmlNode node, String ns, String name)
	{
		return Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.In(node, ns) && name.equals(node.LocalName);
	}

	// 7.2.2 coreSyntaxTerms
	//		rdf:RDF | rdf:ID | rdf:about | rdf:parseType | rdf:resource | rdf:nodeID | rdf:datatype
	public static boolean IsCoreSyntax(XmlNode node)
	{
		return Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.In(node, XmpTag.RDF_NS) && (XmpTag.RDF_URI.equals(node.LocalName) || XmpTag.ID_URI.equals(node.LocalName) || XmpTag.ABOUT_URI.equals(node.LocalName) || XmpTag.PARSE_TYPE_URI.equals(node.LocalName) || XmpTag.RESOURCE_URI.equals(node.LocalName) || XmpTag.NODE_ID_URI.equals(node.LocalName) || XmpTag.DATA_TYPE_URI.equals(node.LocalName));
	}

	// 7.2.4 oldTerms
	//		rdf:aboutEach | rdf:aboutEachPrefix | rdf:bagID
	public static boolean IsOld(XmlNode node)
	{
		return Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.In(node, XmpTag.RDF_NS) && (XmpTag.ABOUT_EACH_URI.equals(node.LocalName) || XmpTag.ABOUT_EACH_PREFIX_URI.equals(node.LocalName) || XmpTag.BAG_ID_URI.equals(node.LocalName));
	}

	// 7.2.5 nodeElementURIs
	//		anyURI - ( coreSyntaxTerms | rdf:li | oldTerms )
	public static boolean IsNodeElement(XmlNode node)
	{
		return !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsCoreSyntax(node) && !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(node, XmpTag.RDF_NS, XmpTag.LI_URI) && !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsOld(node);
	}

	// 7.2.6 propertyElementURIs
	//		anyURI - ( coreSyntaxTerms | rdf:Description | oldTerms )
	public static boolean IsPropertyElement(XmlNode node)
	{
		return !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsCoreSyntax(node) && !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(node, XmpTag.RDF_NS, XmpTag.DESCRIPTION_URI) && !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsOld(node);
	}

	// 7.2.7 propertyAttributeURIs
	//		anyURI - ( coreSyntaxTerms | rdf:Description | rdf:li | oldTerms )
	public static boolean IsPropertyAttribute(XmlNode node)
	{
		return node instanceof XmlAttribute && !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsCoreSyntax(node) && !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(node, XmpTag.RDF_NS, XmpTag.DESCRIPTION_URI) && !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.Is(node, XmpTag.RDF_NS, XmpTag.LI_URI) && !Rasad.Core.Media.MediaMetadataManagement.Xmp.XmlNodeExtensions.IsOld(node);
	}
}