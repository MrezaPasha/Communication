package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.Media.MediaMetadataManagement.Mpeg4.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;

//
//  UrlBox.cs
//
//  Author:
//       Alan McGovern <alan.mcgovern@gmail.com>
//
//  Copyright (c) 2012 Alan McGovern
//
//  This library is free software; you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  This library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//  Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA


public class UrlBox extends Box
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the box's data.
	*/
	private ByteVector data;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="UnknownBox" /> with a provided header and handler
		by reading the contents from a specified file.
	 
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
	public UrlBox(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), handler);
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		this.data = LoadData(file);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets and sets the box data contained in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the box
		data contained in the current instance.
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
		data = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}