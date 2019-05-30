package Rasad.Core.Media.MediaMetadataManagement.Mpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// AudioFile.cs: Provides tagging and properties support for MPEG-1, MPEG-2, and
// MPEG-2.5 audio files.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   mpegfile.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2002, 2003 by Scott Wheeler (Original Implementation)
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
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.NonContainer.File" /> to
	provide tagging and properties support for MPEG-1, MPEG-2, and
	MPEG-2.5 audio files.
 
 
	A <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag" /> and <see
	cref="Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag" /> will be added automatically to any
	file that doesn't contain one. This change does not effect the
	file until it is saved and can be reversed using the following
	method:
	<code>file.RemoveTags (file.TagTypes &amp; ~file.TagTypesOnDisk);</code>
 
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/mp3", "mp3")][SupportedMimeType("audio/x-mp3")][SupportedMimeType("application/x-id3")][SupportedMimeType("audio/mpeg")][SupportedMimeType("audio/x-mpeg")][SupportedMimeType("audio/x-mpeg-3")][SupportedMimeType("audio/mpeg3")][SupportedMimeType("audio/mp3")][SupportedMimeType("taglib/m2a", "m2a")][SupportedMimeType("taglib/mp2", "mp2")][SupportedMimeType("taglib/mp1", "mp1")][SupportedMimeType("audio/x-mp2")][SupportedMimeType("audio/x-mp1")] public class AudioFile : Rasad.Core.Media.MediaMetadataManagement.NonContainer.File
public class AudioFile extends Rasad.Core.Media.MediaMetadataManagement.NonContainer.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the first audio header.
	*/
	private AudioHeader first_header = new AudioHeader();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="AudioFile" /> for a specified path in the local
		file system and specified read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public AudioFile(String path, ReadStyle propertiesStyle)
	{
		super(path, propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="AudioFile" /> for a specified path in the local
		file system with an average read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public AudioFile(String path)
	{
		super(path);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="AudioFile" /> for a specified file abstraction and
		specified read style.
	 
	 @param abstraction
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File.IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public AudioFile(File.IFileAbstraction abstraction, ReadStyle propertiesStyle)
	{
		super(abstraction, propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="AudioFile" /> for a specified file abstraction with
		an average read style.
	 
	 @param abstraction
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File.IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public AudioFile(File.IFileAbstraction abstraction)
	{
		super(abstraction);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Gets a tag of a specified type from the current instance,
		optionally creating a new tag if possible.
	 
	 @param type
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value indicating the
		type of tag to read.
	 
	 @param create
		A <see cref="bool" /> value specifying whether or not to
		try and create the tag if one is not found.
	 
	 @return 
		A <see cref="Tag" /> object containing the tag that was
		found in or added to the current instance. If no
		matching tag was found and none was created, <see
		langword="null" /> is returned.
	 
	 
		If a <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag" /> is added to the
		current instance, it will be placed at the start of the
		file. On the other hand, <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v1.Tag" />
		<see cref="Rasad.Core.Media.MediaMetadataManagement.Ape.Tag" /> will be added to the end of
		the file. All other tag types will be ignored.
	 
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag GetTag(TagTypes type, boolean create)
	{
		Rasad.Core.Media.MediaMetadataManagement.Tag tempVar = getTag();
		Tag t = (tempVar instanceof Rasad.Core.Media.MediaMetadataManagement.NonContainer.Tag ? (Rasad.Core.Media.MediaMetadataManagement.NonContainer.Tag)tempVar : null).GetTag(type);

		if (t != null || !create)
		{
			return t;
		}

		switch (type)
		{
		case TagTypes.Id3v1:
			return getEndTag().AddTag(type, getTag());

		case TagTypes.Id3v2:
			return getStartTag().AddTag(type, getTag());

		case TagTypes.Ape:
			return getEndTag().AddTag(type, getTag());

		default:
			return null;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Reads format specific information at the start of the
		file.
	 
	 @param start
		A <see cref="long" /> value containing the seek position
		at which the tags end and the media data begins.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 
		This method only searches for an audio header in the
		first 16384 bytes of code to avoid searching forever in
		corrupt files.
	 
	*/
	@Override
	protected void ReadStart(long start, ReadStyle propertiesStyle)
	{
		// Only check the first 16 bytes so we're not stuck
		// reading a bad file forever.
		tangible.OutObject<Rasad.Core.Media.MediaMetadataManagement.Mpeg.AudioHeader> tempOut_first_header = new tangible.OutObject<Rasad.Core.Media.MediaMetadataManagement.Mpeg.AudioHeader>();
		if (propertiesStyle != ReadStyle.None && !AudioHeader.Find(tempOut_first_header, this, start, 0x4000))
		{
		first_header = tempOut_first_header.argValue;
			throw new CorruptFileException("MPEG audio header not found.");
		}
	else
	{
		first_header = tempOut_first_header.argValue;
	}
	}

	/** 
		Reads format specific information at the end of the
		file.
	 
	 @param end
		A <see cref="long" /> value containing the seek position
		at which the media data ends and the tags begin.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	@Override
	protected void ReadEnd(long end, ReadStyle propertiesStyle)
	{
		// Make sure we have ID3v1 and ID3v2 tags.
		GetTag(TagTypes.Id3v1, true);
		GetTag(TagTypes.Id3v2, true);
	}

	/** 
		Reads the audio properties from the file represented by
		the current instance.
	 
	 @param start
		A <see cref="long" /> value containing the seek position
		at which the tags end and the media data begins.
	 
	 @param end
		A <see cref="long" /> value containing the seek position
		at which the media data ends and the tags begin.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @return 
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Properties" /> object describing the
		media properties of the file represented by the current
		instance.
	 
	*/
	@Override
	protected Properties ReadProperties(long start, long end, ReadStyle propertiesStyle)
	{
		first_header.SetStreamLength(end - start);
		return new Properties(TimeSpan.Zero, first_header.clone());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}