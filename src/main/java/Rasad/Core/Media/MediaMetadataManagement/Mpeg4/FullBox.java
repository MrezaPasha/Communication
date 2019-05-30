package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// FullBox.cs: Provides an implementation of a ISO/IEC 14496-12 FullBox.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2006-2007 Brian Nickel
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
	This class extends <see cref="Box" /> to provide an
	implementation of a ISO/IEC 14496-12 FullBox.
*/
public abstract class FullBox extends Box
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the box version.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte version;
	private byte version;

	/** 
		Contains the box flags.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint flags;
	private int flags;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="FullBox" /> with a provided header and handler by
		reading the contents from a specified file.
	 
	 @param header
		A <see cref="BoxHeader" /> object containing the header
		to use for the new instance.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to read the contents
		of the box from.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	*/
	protected FullBox(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), handler);
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.Seek(super.getDataPosition());
		ByteVector header_data = file.ReadBlock(4);
		version = header_data.get(0);
		flags = header_data.Mid(1, 3).ToUInt();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="FullBox" /> with a provided header, version, and
		flags.
	 
	 @param header
		A <see cref="BoxHeader" /> object containing the header
		to use for the new instance.
	 
	 @param version
		A <see cref="byte" /> value containing the version of the
		new instance.
	 
	 @param flags
		A <see cref="byte" /> value containing the flags for the
		new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected FullBox(BoxHeader header, byte version, uint flags)
	protected FullBox(BoxHeader header, byte version, int flags)
	{
		super(header.clone());
		this.version = version;
		this.flags = flags;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="FullBox" /> with a provided header, version, and
		flags.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the four
		byte box type.
	 
	 @param version
		A <see cref="byte" /> value containing the version of the
		new instance.
	 
	 @param flags
		A <see cref="byte" /> value containing the flags for the
		new instance.
	 
	 @exception ArgumentNullException
		<paramref name="type" /> is <see langword="null" /> of
		equal to "<c>uuid</c>".
	 
	 @exception ArgumentException
		<paramref name="type" /> isn't exactly 4 bytes long.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected FullBox(ByteVector type, byte version, uint flags)
	protected FullBox(ByteVector type, byte version, int flags)
	{
		this(new BoxHeader(type), version, flags);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the position of the data contained in the current
		instance, after any box specific headers.
	 
	 <value>
		A <see cref="long" /> value containing the position of
		the data contained in the current instance.
	 </value>
	*/
	@Override
	protected long getDataPosition()
	{
		return super.getDataPosition() + 4;
	}

	/** 
		Gets and sets the version number of the current instance.
	 
	 <value>
		A <see cref="byte" /> value containing the version
		number of the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getVersion()
	public final int getVersion()
	{
		return version;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setVersion(uint value)
	public final void setVersion(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: version = (byte) value;
		version = (byte) value;
	}

	/** 
		Gets and sets the flags that apply to the current
		instance.
	 
	 <value>
		A <see cref="uint" /> value containing the flags that
		apply to the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getFlags()
	public final int getFlags()
	{
		return flags;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setFlags(uint value)
	public final void setFlags(int value)
	{
		flags = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Renders the current instance, including its children, to
		a new <see cref="ByteVector" /> object, preceeding the
		contents with a specified block of data.
	 
	 @param topData
		A <see cref="ByteVector" /> object containing box
		specific header data to preceed the content.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	@Override
	protected ByteVector Render(ByteVector topData)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector output = new ByteVector((byte) version);
		ByteVector output = new ByteVector((byte) version);
		output.add(ByteVector.FromUInt(flags).Mid(1, 3));
		output.add(topData);

		return super.Render(output);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}