package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Track.cs:
//
// Author:
//   Julien Moutte <julien@fluendo.com>
//
// Copyright (C) 2011 FLUENDO S.A.
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
 Describes a Matroska Track.
*/
public class Track implements ICodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private fields

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning disable 414 // Assigned, never used
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint track_number;
	private int track_number;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint track_uid;
	private int track_uid;
	private String track_codec_id;
	private String track_codec_name;
	private String track_name;
	private String track_language;
	private boolean track_enabled;
	private boolean track_default;
	private ByteVector codec_data;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning restore 414

	private ArrayList<EBMLElement> unknown_elems = new ArrayList<EBMLElement> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
	 Constructs a <see cref="Track" /> parsing from provided 
	 file data.
	 Parsing will be done reading from _file at position references by 
	 parent element's data section.
	 
	 @param _file <see cref="File" /> instance to read from.
	 @param element Parent <see cref="EBMLElement" />.
	*/
	public Track(File _file, EBMLElement element)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
		long i = 0;

		while (i < element.getDataSize())
		{
			EBMLElement child = new EBMLElement(_file, element.getDataOffset() + i);

			MatroskaID matroska_id = MatroskaID.forValue(child.getID());

			switch (matroska_id)
			{
				case MatroskaTrackNumber:
					track_number = child.ReadUInt();
					break;
				case MatroskaTrackUID:
					track_uid = child.ReadUInt();
					break;
				case MatroskaCodecID:
					track_codec_id = child.ReadString();
					break;
				case MatroskaCodecName:
					track_codec_name = child.ReadString();
					break;
				case MatroskaTrackName:
					track_name = child.ReadString();
					break;
				case MatroskaTrackLanguage:
					track_language = child.ReadString();
					break;
				case MatroskaTrackFlagEnabled:
					track_enabled = child.ReadBool();
					break;
				case MatroskaTrackFlagDefault:
					track_default = child.ReadBool();
					break;
				case MatroskaCodecPrivate:
					codec_data = child.ReadBytes();
					break;
				default:
					unknown_elems.add(child);
					break;
			}

			i += child.getSize();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public fields

	/** 
	 List of unknown elements encountered while parsing.
	*/
	public final ArrayList<EBMLElement> getUnknownElements()
	{
		return unknown_elems;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public methods

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICodec

	/** 
	 Describes track duration.
	*/
	public TimeSpan getDuration()
	{
		return TimeSpan.Zero;
	}

	/** 
	 Describes track media types.
	*/
	public MediaTypes getMediaTypes()
	{
		return MediaTypes.None;
	}

	/** 
	 Track description.
	*/
	public String getDescription()
	{
		return String.format("%1$s %2$s", track_codec_name, track_language);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}