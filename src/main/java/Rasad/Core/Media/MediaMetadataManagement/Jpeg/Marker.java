package Rasad.Core.Media.MediaMetadataManagement.Jpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// Marker.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Stephane Delcroix (stephane@delcroix.org)
//
// Copyright (C) 2009 Ruben Vermeersch
// Copyright (c) 2009 Stephane Delcroix
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
	This enum defines the different markers used in JPEG segments.

	See CCITT Rec. T.81 (1992 E), Table B.1 (p.32)
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum Marker : byte
public enum Marker 
{
	/** 
		Start Of Frame marker, non-differential, Huffman coding, Baseline DCT
	*/
	SOF0((byte)0xc0),

	/** 
		Start Of Frame marker, non-differential, Huffman coding, Extended Sequential DCT
	*/
	SOF1(((byte)0xc0) + 1),

	/** 
		Start Of Frame marker, non-differential, Huffman coding, Progressive DCT
	*/
	SOF2(((byte)0xc0) + 2),

	/** 
		Start Of Frame marker, non-differential, Huffman coding, Lossless (sequential)
	*/
	SOF3(((byte)0xc0) + 3),

	/** 
		Start Of Frame marker, differential, Huffman coding, Differential Sequential DCT
	*/
	SOF5((byte)0xc5),

	/** 
		Start Of Frame marker, differential, Huffman coding, Differential Progressive DCT
	*/
	SOF6(((byte)0xc5) + 1),
	/** 
		Start Of Frame marker, differential, Huffman coding, Differential Lossless (sequential)
	*/
	SOF7(((byte)0xc5) + 2),

	/** 
		Reserved for JPG extensions
	*/
	JPG(((byte)0xc5) + 3),

	/** 
		Start Of Frame marker, non-differential, arithmetic coding, Extended Sequential DCT
	*/
	SOF9(((byte)0xc5) + 4),

	/** 
		Start Of Frame marker, non-differential, arithmetic coding, Progressive DCT
	*/
	SOF10(((byte)0xc5) + 5),

	/** 
		Start Of Frame marker, non-differential, arithmetic coding, Lossless (sequential)
	*/
	SOF11(((byte)0xc5) + 6),

	/** 
		Start Of Frame marker, differential, arithmetic coding, Differential Sequential DCT
	*/
	SOF13((byte)0xcd),

	/** 
		Start Of Frame marker, differential, arithmetic coding, Differential Progressive DCT
	*/
	SOF14(((byte)0xcd) + 1),

	/** 
		Start Of Frame marker, differential, arithmetic coding, Differential Lossless (sequential)
	*/
	SOF15(((byte)0xcd) + 2),

	/** 
		Define Huffman table(s)
	*/
	DHT((byte)0xc4),

	/** 
		Define arithmetic coding conditioning(s)
	*/
	DAC((byte)0xcc),

	//Restart interval termination with modulo 8 count "m"
	/** 
		Restart
	*/
	RST0((byte)0xd0),

	/** 
		Restart
	*/
	RST1(((byte)0xd0) + 1),

	/** 
		Restart
	*/
	RST2(((byte)0xd0) + 2),

	/** 
		Restart
	*/
	RST3(((byte)0xd0) + 3),

	/** 
		Restart
	*/
	RST4(((byte)0xd0) + 4),

	/** 
		Restart
	*/
	RST5(((byte)0xd0) + 5),

	/** 
		Restart
	*/
	RST6(((byte)0xd0) + 6),

	/** 
		Restart
	*/
	RST7(((byte)0xd0) + 7),

	/** 
		Start of Image
	*/
	SOI((byte)0xd8),

	/** 
		End of Image
	*/
	EOI(((byte)0xd8) + 1),

	/** 
		Start of scan
	*/
	SOS(((byte)0xd8) + 2),

	/** 
		Define quantization table (s)
	*/
	DQT(((byte)0xd8) + 3),

	/** 
		Define number of lines
	*/
	DNL(((byte)0xd8) + 4),

	/** 
		Define restart interval
	*/
	DRI(((byte)0xd8) + 5),

	/** 
		Define hierarchical progression
	*/
	DHP(((byte)0xd8) + 6),

	/** 
		Define reference component
	*/
	EXP(((byte)0xd8) + 7),

	/** 
		Reserved for application segment
	*/
	APP0((byte)0xe0),

	/** 
		Reserved for application segment
	*/
	APP1(((byte)0xe0) + 1),

	/** 
		Reserved for application segment
	*/
	APP2(((byte)0xe0) + 2),

	/** 
		Reserved for application segment
	*/
	APP3(((byte)0xe0) + 3),

	/** 
		Reserved for application segment
	*/
	APP4(((byte)0xe0) + 4),

	/** 
		Reserved for application segment
	*/
	APP5(((byte)0xe0) + 5),

	/** 
		Reserved for application segment
	*/
	APP6(((byte)0xe0) + 6),

	/** 
		Reserved for application segment
	*/
	APP7(((byte)0xe0) + 7),

	/** 
		Reserved for application segment
	*/
	APP8(((byte)0xe0) + 8),

	/** 
		Reserved for application segment
	*/
	APP9(((byte)0xe0) + 9),

	/** 
		Reserved for application segment
	*/
	APP10(((byte)0xe0) + 10),

	/** 
		Reserved for application segment
	*/
	APP11(((byte)0xe0) + 11),

	/** 
		Reserved for application segment
	*/
	APP12(((byte)0xe0) + 12),

	/** 
		Reserved for application segment
	*/
	APP13(((byte)0xe0) + 13),

	/** 
		Reserved for application segment
	*/
	APP14(((byte)0xe0) + 14),

	/** 
		Reserved for application segment
	*/
	APP15(((byte)0xe0) + 15),

	/** 
		Reserved for JPEG extension
	*/
	JPG0((byte)0xf0),

	/** 
		Reserved for JPEG extension
	*/
	JPG1(((byte)0xf0) + 1),

	/** 
		Reserved for JPEG extension
	*/
	JPG2(((byte)0xf0) + 2),

	/** 
		Reserved for JPEG extension
	*/
	JPG3(((byte)0xf0) + 3),

	/** 
		Reserved for JPEG extension
	*/
	JPG4(((byte)0xf0) + 4),

	/** 
		Reserved for JPEG extension
	*/
	JPG5(((byte)0xf0) + 5),

	/** 
		Reserved for JPEG extension
	*/
	JPG6(((byte)0xf0) + 6),

	/** 
		Reserved for JPEG extension
	*/
	JPG7(((byte)0xf0) + 7),

	/** 
		Reserved for JPEG extension
	*/
	JPG8(((byte)0xf0) + 8),

	/** 
		Reserved for JPEG extension
	*/
	JPG9(((byte)0xf0) + 9),

	/** 
		Reserved for JPEG extension
	*/
	JPG10(((byte)0xf0) + 10),

	/** 
		Reserved for JPEG extension
	*/
	JPG11(((byte)0xf0) + 11),

	/** 
		Reserved for JPEG extension
	*/
	JPG12(((byte)0xf0) + 12),

	/** 
		Reserved for JPEG extension
	*/
	JPG13(((byte)0xf0) + 13),

	/** 
	   Comment
	*/
	COM((byte)0xfe);

	public static final int SIZE = java.lang.Byte.SIZE;

	private byte byteValue;
	private static java.util.HashMap<Byte, Marker> mappings;
	private static java.util.HashMap<Byte, Marker> getMappings()
	{
		if (mappings == null)
		{
			synchronized (Marker.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Byte, Marker>();
				}
			}
		}
		return mappings;
	}

	private Marker(byte value)
	{
		byteValue = value;
		getMappings().put(value, this);
	}

	public byte getValue()
	{
		return byteValue;
	}

	public static Marker forValue(byte value)
	{
		return getMappings().get(value);
	}
}