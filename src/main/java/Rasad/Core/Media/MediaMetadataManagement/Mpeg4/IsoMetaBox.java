package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// IsoMetaBox.cs: Provides an implementation of a ISO/IEC 14496-12 MetaBox.
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
	implementation of a ISO/IEC 14496-12 MetaBox.
*/
public class IsoMetaBox extends FullBox
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the children of the box.
	*/
	private java.lang.Iterable<Box> children;

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
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	*/
	public IsoMetaBox(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), file, handler);
		children = LoadChildren(file);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="IsoMetaBox" /> with a specified handler.
	 
	 @param handlerType
		A <see cref="ByteVector" /> object specifying a 4 byte
		handler type.
	 
	 @param handlerName
		A <see cref="string" /> object specifying the handler
		name.
	 
	 @exception ArgumentNullException
		<paramref name="handlerType" /> is <see langword="null"
		/>.
	 
	 @exception ArgumentException
		<paramref name="handlerType" /> is less than 4 bytes
		long.
	 
	*/
	public IsoMetaBox(ByteVector handlerType, String handlerName)
	{
		super("meta", 0, 0);
		if (handlerType == null)
		{
			throw new NullPointerException("handlerType");
		}

		if (handlerType.size() < 4)
		{
			throw new IllegalArgumentException("The handler type must be four bytes long.", "handlerType");
		}

		children = new ArrayList<Box> ();
		AddChild(new IsoHandlerBox(handlerType, handlerName));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the children of the current instance.
	 
	 <value>
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating the
		children of the current instance.
	 </value>
	*/
	@Override
	public java.lang.Iterable<Box> getChildren()
	{
		return children;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}