package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// IsoVisualSampleEntry.cs: Provides an implementation of a ISO/IEC 14496-12
// VisualSampleEntry and support for reading MPEG-4 video properties.
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
	This class extends <see cref="IsoSampleEntry" /> and implements
	<see cref="IVideoCodec" /> to provide an implementation of a
	ISO/IEC 14496-12 VisualSampleEntry and support for reading MPEG-4
	video properties.
*/
public class IsoVisualSampleEntry extends IsoSampleEntry implements IVideoCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the width of the visual.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort width;
	private short width;

	/** 
		Contains the height of the visual.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort height;
	private short height;

	/*
	/// <summary>
	///    Contains the children of the box.
	/// </summary>
	private BoxList children;
	*/

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="IsoVisualSampleEntry" /> with a provided header and
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
	public IsoVisualSampleEntry(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), file, handler);
		file.Seek(super.getDataPosition() + 16);
		width = file.ReadBlock(2).ToUShort();
		height = file.ReadBlock(2).ToUShort();

		/*
		TODO: What are the children anyway?
		children = LoadChildren (file);
		*/
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
		return super.getDataPosition() + 62;
	}

	/*
	/// <summary>
	///    Gets the children of the current instance.
	/// </summary>
	/// <value>
	///    A <see cref="IEnumerable{T}" /> object enumerating the
	///    children of the current instance.
	/// </value>
	public override BoxList Children {
		get {return children;}
	}
	*/

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IVideoCodec Properties

	/** 
		Gets the duration of the media represented by the current
		instance.
	 
	 <value>
		Always <see cref="TimeSpan.Zero" />.
	 </value>
	*/
	public final TimeSpan getDuration()
	{
		return TimeSpan.Zero;
	}

	/** 
		Gets the types of media represented by the current
		instance.
	 
	 <value>
		Always <see cref="MediaTypes.Video" />.
	 </value>
	*/
	public final MediaTypes getMediaTypes()
	{
		return MediaTypes.Video;
	}

	/** 
		Gets a text description of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the media represented by the current instance.
	 </value>
	*/
	public final String getDescription()
	{
		return String.format(CultureInfo.InvariantCulture, "MPEG-4 Video (%1$s)", getBoxType());
	}

	/** 
		Gets the width of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> containing the width of the video
		represented by the current instance.
	 </value>
	*/
	public final int getVideoWidth()
	{
		return width;
	}

	/** 
		Gets the height of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> containing the height of the video
		represented by the current instance.
	 </value>
	*/
	public final int getVideoHeight()
	{
		return height;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}