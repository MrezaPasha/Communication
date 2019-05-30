package Rasad.Core.Media.MediaMetadataManagement.IFD.Entries;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// MakernoteIFDEntry.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009 Ruben Vermeersch
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
	An enum to represent the manufactor of the makernote
	The information of the makernote types is from:
	http: //exiv2.org/makernote.html
*/
public enum MakernoteType
{

	/** 
		The manufactor could not be determined
	*/
	Unknown,

	/** 
		Canon makernote.
		Standard IFD without a special prefix.
	*/
	Canon,

	/** 
		Panasonic makernote.
		"Panasonic\0\0\0" prefix and IFD starting at offset 12.
		The next-IFD pointer is missing
	*/
	Panasonic,

	/** 
		Leica makernote.
		"LEICA\0\0\0" prefix and IFD starting at offset 10.
	*/
	Leica,

	/** 
		Pentax makernote.
		"AOC\0" + 2 unknown bytes as prefix. The IFD starts at
		offset 6.
	*/
	Pentax,

	/** 
		Nikon makernote (type 1).
		Standard IFD without a special prefix.
	*/
	Nikon1,

	/** 
		Nikon makernote (type 2).
		"Nikon\0" + 2 unknown bytes prefix. The IFD starts at
		offset 8.
	*/
	Nikon2,

	/** 
		Nikon makernote (type 3).
		"Nikon\0" + 4 bytes with verison code + Tiff header.
		The IFD starts usually at offset 18. The offsets of the IFD
		are relative to start of the Tiff header (byte 10)
	*/
	Nikon3,

	/** 
		Olympus makernote (type 1).
		"OLYMP\0" + 2 unknown bytes as prefix. The IFD starts at
		offset 8.
	*/
	Olympus1,

	/** 
		Olympus makernote (type 2)
		"OLYMPUS\0II" + 2 unknown bytes as prefix. The IFD starts at
		offset 12. The offsets of the IFD are relative to the
		beginning of the makernote.
	*/
	Olympus2,

	/** 
		Sony makernote (type 1).
		"SONY DSC \0\0\0" as prefix. The IFD starts at offset 12. A
		next-IFD pointer is missing.
	*/
	Sony;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static MakernoteType forValue(int value)
	{
		return values()[value];
	}
}