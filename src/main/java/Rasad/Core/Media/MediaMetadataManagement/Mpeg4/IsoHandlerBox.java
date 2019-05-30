package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// IsoHandlerBox.cs: Provides an implementation of a ISO/IEC 14496-12
// HandlerBox.
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
	implementation of a ISO/IEC 14496-12 FullBox.
*/
public class IsoHandlerBox extends FullBox
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the handler type.
	*/
	private ByteVector handler_type;

	/** 
		Contains the handler name.
	*/
	private String name;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="IsoHandlerBox" /> with a provided header and
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
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	*/
	public IsoHandlerBox(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), file, handler);
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.Seek(getDataPosition() + 4);
		ByteVector box_data = file.ReadBlock(getDataSize() - 4);
		handler_type = box_data.Mid(0, 4);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: int end = box_data.Find((byte) 0, 16);
		int end = box_data.Find((byte) 0, 16);
		if (end < 16)
		{
			end = box_data.size();
		}
		name = box_data.toString(StringType.UTF8, 16, end - 16);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="IsoHandlerBox" /> with a specified type and name.
	 
	 @param handlerType
		A <see cref="ByteVector" /> object specifying a 4 byte
		handler type.
	 
	 @param name
		A <see cref="string" /> object specifying the handler
		name.
	 
	 @exception ArgumentNullException
		<paramref name="handlerType" /> is <see langword="null"
		/>.
	 
	 @exception ArgumentException
		<paramref name="handlerType" /> is less than 4 bytes
		long.
	 
	*/
	public IsoHandlerBox(ByteVector handlerType, String name)
	{
		super("hdlr", 0, 0);
		if (handlerType == null)
		{
			throw new NullPointerException("handlerType");
		}

		if (handlerType.size() < 4)
		{
			throw new IllegalArgumentException("The handler type must be four bytes long.", "handlerType");
		}

		this.handler_type = handlerType.Mid(0, 4);
		this.name = name;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the data contained in the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the
		rendered version of the data contained in the current
		instance.
	 </value>
	*/
	@Override
	public ByteVector getData()
	{
		ByteVector output = new ByteVector(4);
		output.add(handler_type);
		output.add(new ByteVector(12));
		output.add(ByteVector.FromString(name, StringType.UTF8));
		output.add(new ByteVector(2));
		return output;
	}

	/** 
		Gets the handler type of the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the
		handler type of the current instance.
	 </value>
	*/
	public final ByteVector getHandlerType()
	{
		return handler_type;
	}

	/** 
		Gets the name of the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the name of the
		current instance.
	 </value>
	*/
	public final String getName()
	{
		return name;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}