package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// IsoChunkOffsetBox.cs: Provides an implementation of a ISO/IEC 14496-12
// ChunkOffsetBox.
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
	This class extends <see cref="FullBox" /> to provide an
	implementation of a ISO/IEC 14496-12 ChunkOffsetBox.
 
 
	<see cref="IsoChunkOffsetBox" /> and <see
	cref="IsoChunkLargeOffsetBox" /> contain offsets of media data
	within the file. As such, if the file changes by even one byte,
	these values are devalidatated and the box will have to be
	overwritten to maintain playability.
 
*/
public class IsoChunkOffsetBox extends FullBox
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the chunk offsets.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint [] offsets;
	private int [] offsets;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="IsoChunkOffsetBox" /> with a provided header and
		handler by reading the contents from a specified file.
	 
	 @param header
		A <see cref="BoxHeader" /> object containing the header
		to use for the new instance.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to read the contents
		of the box from.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new instance.
	 
	*/
	public IsoChunkOffsetBox(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), file, handler);
		ByteVector box_data = file.ReadBlock(getDataSize());

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: offsets = new uint [(int) box_data.Mid(0, 4).ToUInt()];
		offsets = new int [(int) box_data.Mid(0, 4).ToUInt()];

		for (int i = 0; i < offsets.length; i++)
		{
			offsets [i] = box_data.Mid(4 + i * 4, 4).ToUInt();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets and sets the data contained in the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the data
		contained in the current instance.
	 </value>
	*/
	@Override
	public ByteVector getData()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector output = ByteVector.FromUInt((uint) offsets.Length);
		ByteVector output = ByteVector.FromUInt((int) offsets.length);
		for (int i = 0; i < offsets.length; i++)
		{
			output.add(ByteVector.FromUInt(offsets [i]));
		}

		return output;
	}

	/** 
		Gets the offset table contained in the current instance.
	 
	 <value>
		A <see cref="uint[]" /> containing the offset table
		contained in the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint [] getOffsets()
	public final int[] getOffsets()
	{
		return offsets;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Overwrites the existing box in the file after updating
		the table for a size change.
	 
	 @param file
		A <see cref="File" /> object containing the file to which
		the current instance belongs and wo which modifications
		must be applied.
	 
	 @param sizeDifference
		A <see cref="long" /> value containing the size
		change that occurred in the file.
	 
	 @param after
		A <see cref="long" /> value containing the position in
		the file after which offsets will be invalidated. If an
		offset is before this point, it won't be updated.
	 
	 @exception ArgumentNullException
		@param file / is <see langword="null" />.
	 
	*/
	public final void Overwrite(File file, long sizeDifference, long after)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.Insert(Render(sizeDifference, after), getHeader().getPosition(), getSize());
	}

	/** 
		Renders the current instance after updating the table for
		a size change.
	 
	 @param sizeDifference
		A <see cref="long" /> value containing the size
		change that occurred in the file.
	 
	 @param after
		A <see cref="long" /> value containing the position in
		the file after which offsets will be invalidated. If an
		offset is before this point, it won't be updated.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the file.
	 
	*/
	public final ByteVector Render(long sizeDifference, long after)
	{
		for (int i = 0; i < offsets.length; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (offsets [i] >= (uint) after)
			if (offsets [i] >= (int) after)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: offsets [i] = (uint)(offsets [i] + sizeDifference);
				offsets [i] = (int)(offsets [i] + sizeDifference);
			}
		}

		return Render();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}