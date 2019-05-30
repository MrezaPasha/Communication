package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// IsoFreeSpaceBox.cs: Provides an implementation of a ISO/IEC 14496-12
// FreeSpaceBox.
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
	implementation of a ISO/IEC 14496-12 FreeSpaceBox.
*/
public class IsoFreeSpaceBox extends Box
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the size of the padding.
	*/
	private long padding;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="IsoMetaBox" /> with a provided header and
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
	public IsoFreeSpaceBox(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), handler);
		padding = getDataSize();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="IsoFreeSpaceBox" /> to occupy a specified number of
		bytes.
	 
	 @param padding
		A <see cref="long" /> value specifying the number of
		bytes the new instance should occupy when rendered.
	 
	*/
	public IsoFreeSpaceBox(long padding)
	{
		super("free");
		setPaddingSize(padding);
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
		return new ByteVector((int) padding);
	}
	@Override
	public void setData(ByteVector value)
	{
		padding = (value != null) ? value.size() : 0;
	}

	/** 
		Gets and sets the size the current instance will occupy
		when rendered.
	 
	 <value>
		A <see cref="long" /> value containing the size the
		current instance will occupy when rendered.
	 </value>
	*/
	public final long getPaddingSize()
	{
		return padding + 8;
	}
	public final void setPaddingSize(long value)
	{
		padding = value - 8;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}