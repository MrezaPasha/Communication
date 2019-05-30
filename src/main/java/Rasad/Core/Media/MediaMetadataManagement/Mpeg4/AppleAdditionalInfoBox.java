package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// AppleAdditionalInfoBox.cs: Provides an implementation of an Apple
// AdditionalInfoBox.
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
	implementation of an Apple AdditionalInfoBox.
*/
public class AppleAdditionalInfoBox extends Box
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the box data.
	*/
	private ByteVector data;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="AppleAdditionalInfoBox" /> with a provided header
		and handler by reading the contents from a specified
		file.
	 
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
	public AppleAdditionalInfoBox(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), handler);
		// We do not care what is in this custom data section
		// see: https://developer.apple.com/library/mac/#documentation/QuickTime/QTFF/QTFFChap2/qtff2.html
		setData(LoadData(file));
	}

	/** 
	 Constructs and initializes a new instance of <see
		cref="AppleAdditionalInfoBox" /> using specified header, version and flags
	 
	 @param header
	 @param version
	 @param flags
	*/
	public AppleAdditionalInfoBox(ByteVector header)
	{
		super(header);
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
		return data;
	}
	@Override
	public void setData(ByteVector value)
	{
		data = value != null ? value : new ByteVector();
	}

	/** 
		Gets and sets the text contained in the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the text
		contained in the current instance.
	 </value>
	*/
	public final String getText()
	{
		return tangible.StringHelper.trimStart(getData().toString(StringType.Latin1), '\0');
	}
	public final void setText(String value)
	{
		setData(ByteVector.FromString(value, StringType.Latin1));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}