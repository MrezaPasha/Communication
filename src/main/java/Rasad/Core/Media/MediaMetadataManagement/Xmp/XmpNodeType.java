package Rasad.Core.Media.MediaMetadataManagement.Xmp;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// XmpNodeType.cs:
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
	Denotes the type of a node.
*/
public enum XmpNodeType
{
	/** 
		Unstructured (simple) value node.
	*/
	Simple,

	/** 
		Structured value node.
	*/
	Struct,

	/** 
		Ordered array.
	*/
	Seq,

	/** 
		Language alternative.
	*/
	Alt,

	/** 
		Unordered structured value.
	*/
	Bag;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static XmpNodeType forValue(int value)
	{
		return values()[value];
	}
}