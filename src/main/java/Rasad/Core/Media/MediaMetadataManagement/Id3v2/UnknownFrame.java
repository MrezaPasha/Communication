package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// UnknownFrame.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   unknownframe.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2002 Scott Wheeler (Original Implementation)
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
	This class extends <see cref="Frame" /> to provide a fallback
	type when no other frame class works for a given frame.
*/
public class UnknownFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Properties

	/** 
		Contains the field data.
	*/
	private ByteVector field_data = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="UnknownFrame" /> with a specified type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing an ID3v2.4
		frame identifier.
	 
	 @param data
		A <see cref="ByteVector"/> object containing the contents
		of the frame.
	 
	*/
	public UnknownFrame(ByteVector type, ByteVector data)
	{
		super(type, 4);
		field_data = data;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UnknownFrame" /> with a specified type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing an ID3v2.4
		frame identifier.
	 
	*/
	public UnknownFrame(ByteVector type)
	{
		this(type, null);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UnknownFrame" /> by reading its raw data in a
		specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public UnknownFrame(ByteVector data, byte version)
	public UnknownFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UnknownFrame" /> by reading its raw data in a
		specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		representation of the new frame.
	 
	 @param offset
		A <see cref="int" /> indicating at what offset in
		<paramref name="data" /> the frame actually begins.
	 
	 @param header
		A <see cref="FrameHeader" /> containing the header of the
		frame found at <paramref name="offset" /> in the data.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected internal UnknownFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected UnknownFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(header.clone());
		SetData(data, offset, version, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets and sets the field data in the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> containing the field data.
	 </value>
	*/
	public final ByteVector getData()
	{
		return field_data;
	}
	public final void setData(ByteVector value)
	{
		field_data = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Gets a string representation of the current instance.
	 
	 @return 
		A <see cref="string" /> object describing the current
		instance.
	 
	*/
	@Override
	public String toString()
	{
		return super.toString();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Methods

	/** 
		Populates the values in the current instance by parsing
		its field data in a specified version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the
		extracted field data.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		field data is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override void ParseFields(ByteVector data, byte version)
	@Override
	protected void ParseFields(ByteVector data, byte version)
	{
		field_data = data;
	}

	/** 
		Renders the values in the current instance into field
		data for a specified version.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		field data is to be encoded in.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered field data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override ByteVector RenderFields(byte version)
	@Override
	protected ByteVector RenderFields(byte version)
	{
		return (field_data != null) ? field_data : new ByteVector();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}