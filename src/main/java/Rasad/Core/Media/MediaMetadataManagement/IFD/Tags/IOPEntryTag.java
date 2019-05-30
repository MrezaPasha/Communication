package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// IOPEntryTag.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009-2010 Ruben Vermeersch
// Copyright (C) 2009 Mike Gemuende
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
	Entry tags occuring in the Interoperability IFD
	The complete overview can be obtained at:
	http: //www.awaresystems.be/imaging/tiff.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum IOPEntryTag : ushort
public enum IOPEntryTag 
{
	/** 
		 Indicates the identification of the Interoperability rule. (Hex: 0x0001)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/interoperability/interoperabilityindex.html
	*/
	InteroperabilityIndex((short)1),

	/** 
		 Interoperability version. (Hex: 0x0002)
	*/
	InteroperabilityVersion((short)2),

	/** 
		 File format of image file. (Hex: 0x1000)
	*/
	RelatedImageFileFormat((short)4096),

	/** 
		 Image Width. (Hex: 0x1001)
	*/
	RelatedImageWidth((short)4097),

	/** 
		 Image Height. (Hex: 0x1002)
	*/
	RelatedImageLength((short)4098);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, IOPEntryTag> mappings;
	private static java.util.HashMap<Short, IOPEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (IOPEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, IOPEntryTag>();
				}
			}
		}
		return mappings;
	}

	private IOPEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static IOPEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}