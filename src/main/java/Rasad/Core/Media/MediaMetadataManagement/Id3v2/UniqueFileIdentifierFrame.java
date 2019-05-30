package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// UniqueFileIdentifierFrame.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   uniquefileidentifierframe.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2004 Scott Wheeler (Original Implementation)
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
	This class extends <see cref="Frame" />, implementing support for
	ID3v2 Unique File Identifier (UFID) Frames.
*/
public class UniqueFileIdentifierFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the owner string.
	*/
	private String owner = null;

	/** 
		Contains the identifier data.
	*/
	private ByteVector identifier = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="UniqueFileIdentifierFrame" /> with a specified
		owner and identifier data.
	 
	 @param owner
		A <see cref="string" /> containing the owner of the new
		frame.
	 
	 @param identifier
		A <see cref="ByteVector" /> object containing the
		identifier for the new frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see
		cref="Get(Tag,string,bool)" /> for more integrated frame
		creation.
	 
	 @exception ArgumentNullException
		<paramref name="owner" /> is <see langword="null" />.
	 
	*/
	public UniqueFileIdentifierFrame(String owner, ByteVector identifier)
	{
		super(FrameType.UFID, 4);
		if (owner == null)
		{
			throw new NullPointerException("owner");
		}

		this.owner = owner;
		this.identifier = identifier;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UniqueFileIdentifierFrame" /> with a specified
		owner.
	 
	 @param owner
		A <see cref="string" /> containing the owner of the new
		frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see
		cref="Get(Tag,string,bool)" /> for more integrated frame
		creation.
	 
	 @exception ArgumentNullException
		<paramref name="owner" /> is <see langword="null" />.
	 
	*/
	public UniqueFileIdentifierFrame(String owner)
	{
		this(owner, null);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UniqueFileIdentifierFrame" /> by reading its raw
		data in a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public UniqueFileIdentifierFrame(ByteVector data, byte version)
	public UniqueFileIdentifierFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="UniqueFileIdentifierFrame" /> by reading its raw
		data in a specified ID3v2 version.
	 
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
//ORIGINAL LINE: protected internal UniqueFileIdentifierFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected UniqueFileIdentifierFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(header.clone());
		SetData(data, offset, version, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets and sets the owner of the current instance.
	 
	 <value>
		A <see cref="string" /> containing the owner of the
		current instance.
	 </value>
	 
		There should only be one frame with a matching owner per
		tag.
	 
	*/
	public final String getOwner()
	{
		return owner;
	}

	/** 
		Gets and sets the identifier data stored in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containiner the unique
		file identifier frame.
	 </value>
	*/
	public final ByteVector getIdentifier()
	{
		return identifier;
	}
	public final void setIdentifier(ByteVector value)
	{
		identifier = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Gets a specified unique file identifer frame from the
		specified tag, optionally creating it if it does not
		exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param owner
		A <see cref="string" /> specifying the owner to match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="UserTextInformationFrame" /> object
		containing the matching frame, or <see langword="null" />
		if a match wasn't found and <paramref name="create" /> is
		<see langword="false" />.
	 
	*/
	public static UniqueFileIdentifierFrame Get(Tag tag, String owner, boolean create)
	{
		UniqueFileIdentifierFrame ufid;

		for (Frame frame : tag.GetFrames(FrameType.UFID))
		{
			ufid = frame instanceof UniqueFileIdentifierFrame ? (UniqueFileIdentifierFrame)frame : null;

			if (ufid == null)
			{
				continue;
			}

			if (ufid.getOwner().equals(owner))
			{
				return ufid;
			}
		}

		if (!create)
		{
			return null;
		}

		ufid = new UniqueFileIdentifierFrame(owner, null);
		tag.AddFrame(ufid);
		return ufid;
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
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVectorCollection fields = ByteVectorCollection.Split(data, (byte) 0);
		ByteVectorCollection fields = ByteVectorCollection.split(java.util.regex.Pattern.quote(String.valueOf(data), (byte) 0));

		if (fields.size() != 2)
		{
			return;
		}

		owner = fields.get(0).toString(StringType.Latin1);
		identifier = fields.get(1);
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
		ByteVector data = new ByteVector();

		data.add(ByteVector.FromString(owner, StringType.Latin1));
		data.add(ByteVector.TextDelimiter(StringType.Latin1));
		data.add(identifier);

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region ICloneable

	/** 
		Creates a deep copy of the current instance.
	 
	 @return 
		A new <see cref="Frame" /> object identical to the
		current instance.
	 
	*/
	@Override
	public Frame Clone()
	{
		UniqueFileIdentifierFrame frame = new UniqueFileIdentifierFrame(owner);
		if (identifier != null)
		{
			frame.identifier = new ByteVector(identifier);
		}
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}