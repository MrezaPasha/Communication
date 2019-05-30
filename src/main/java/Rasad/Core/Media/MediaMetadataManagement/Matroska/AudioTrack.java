package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// AudioTrack.cs:
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
 Describes a Matroska Audio track.
*/
public class AudioTrack extends Track implements IAudioCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private fields

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning disable 414 // Assigned, never used
	private double rate;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint channels;
	private int channels;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint depth;
	private int depth;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning restore 414

	private ArrayList<EBMLElement> unknown_elems = new ArrayList<EBMLElement> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
	  Construct a <see cref="AudioTrack" /> reading information from 
	  provided file data.
	 Parsing will be done reading from _file at position references by 
	 parent element's data section.
	 
	 @param _file <see cref="File" /> instance to read from.
	 @param element Parent <see cref="EBMLElement" />.
	*/
	public AudioTrack(File _file, EBMLElement element)
	{
		super(_file, element);
		MatroskaID matroska_id;

		// Here we handle the unknown elements we know, and store the rest
		for (EBMLElement elem : super.getUnknownElements())
		{

			matroska_id = MatroskaID.forValue(elem.getID());

			if (matroska_id == MatroskaID.MatroskaTrackAudio)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
				long i = 0;

				while (i < elem.getDataSize())
				{
					EBMLElement child = new EBMLElement(_file, elem.getDataOffset() + i);

					matroska_id = MatroskaID.forValue(child.getID());

					switch (matroska_id)
					{
						case MatroskaAudioChannels:
							channels = child.ReadUInt();
							break;
						case MatroskaAudioBitDepth:
							depth = child.ReadUInt();
							break;
						case MatroskaAudioSamplingFreq:
							rate = child.ReadDouble();
							break;
						default:
							unknown_elems.add(child);
							break;
					}

					i += child.getSize();
				}
			}
			else
			{
				unknown_elems.add(elem);
			}
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
	 This type of track only has audio media type.
	*/
	@Override
	public MediaTypes getMediaTypes()
	{
		return MediaTypes.Audio;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IAudioCodec

	/** 
	 Audio track bitrate.
	*/
	public final int getAudioBitrate()
	{
		return 0;
	}

	/** 
	 Audio track sampling rate.
	*/
	public final int getAudioSampleRate()
	{
		return (int) rate;
	}

	/** 
	 Number of audio channels in this track.
	*/
	public final int getAudioChannels()
	{
		return (int) channels;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}