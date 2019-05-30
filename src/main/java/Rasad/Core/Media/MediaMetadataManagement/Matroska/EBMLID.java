package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// EBMLIDs.cs:
//
// Author:
//   Julien Moutte <julien@fluendo.com>
//
// Copyright (C) 2011 FLUENDO S.A.
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
 Public enumeration listing the possible EBML element identifiers.
*/
public enum EBMLID
{
	/** 
	 Indicates an EBML Header element.
	*/
	EBMLHeader(0x1A45DFA3),

	/** 
	 Indicates an EBML Version element.
	*/
	EBMLVersion(0x4286),

	/** 
	 Indicates an EBML Read Version element.
	*/
	EBMLReadVersion(0x42F7),

	/** 
	 Indicates an EBML Max ID Length element.
	*/
	EBMLMaxIDLength(0x42F2),

	/** 
	 Indicates an EBML Max Size Length element.
	*/
	EBMLMaxSizeLength(0x42F3),

	/** 
	 Indicates an EBML Doc Type element.
	*/
	EBMLDocType(0x4282),

	/** 
	 Indicates an EBML Doc Type Version element.
	*/
	EBMLDocTypeVersion(0x4287),

	/** 
	 Indicates an EBML Doc Type Read Version element.
	*/
	EBMLDocTypeReadVersion(0x4285),

	/** 
	 Indicates an EBML Void element.
	*/
	EBMLVoid(0xEC);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, EBMLID> mappings;
	private static java.util.HashMap<Integer, EBMLID> getMappings()
	{
		if (mappings == null)
		{
			synchronized (EBMLID.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, EBMLID>();
				}
			}
		}
		return mappings;
	}

	private EBMLID(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static EBMLID forValue(int value)
	{
		return getMappings().get(value);
	}
}