package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;

//
// Properties.cs: This class implements IAudioCodec and IVideoCodec
// and combines codecs to create generic media properties for a file.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   audioproperties.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2006,2007 Brian Nickel
// Copyright (C) 2003 Scott Wheeler (Original Implementation)
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
	This class implements <see cref="IAudioCodec" />, <see
	cref="IVideoCodec" /> and <see cref="IPhotoCodec" />
	and combines codecs to create generic media properties
	for a file.
*/
public class Properties implements IAudioCodec, IVideoCodec, IPhotoCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the codecs.
	*/
	private ICodec[] codecs = new ICodec [0];

	/** 
		Contains the duration.
	*/
	private TimeSpan duration = TimeSpan.Zero;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Properties" /> with no codecs or duration.
	 
	 
		<p>This constructor is used when media properties are
		not read.</p>
	 
	*/
	public Properties()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Properties" /> with a specified duration and array
		of codecs.
	 
	 @param duration
		A <see cref="TimeSpan" /> containing the duration of the
		media, or <see cref="TimeSpan.Zero" /> if the duration is
		to be read from the codecs.
	 
	 @param codecs
		A <see cref="ICodec[]" /> containing the codecs to be
		used in the new instance.
	 
	*/
	public Properties(TimeSpan duration, ICodec... codecs)
	{
		this.duration = duration;
		if (codecs != null)
		{
			this.codecs = codecs;
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Properties" /> with a specified duration and
		enumaration of codecs.
	 
	 @param duration
		A <see cref="TimeSpan" /> containing the duration of the
		media, or <see cref="TimeSpan.Zero" /> if the duration is
		to be read from the codecs.
	 
	 @param codecs
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object containing the
		codec to be used in the new instance.
	 
	*/
	public Properties(TimeSpan duration, java.lang.Iterable<ICodec> codecs)
	{
		this.duration = duration;
		if (codecs != null)
		{
			this.codecs = (new ArrayList<ICodec> (codecs)).toArray(new ICodec[0]);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the codecs contained in the current instance.
	 
	 <value>
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object containing the
		<see cref="ICodec" /> objects contained in the current
		instance.
	 </value>
	*/
	public final java.lang.Iterable<ICodec> getCodecs()
	{
		return codecs;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICodec

	/** 
		Gets the duration of the media represented by the current
		instance.
	 
	 <value>
		A <see cref="TimeSpan" /> containing the duration of the
		media represented by the current instance.
	 </value>
	 
		If the duration was set in the constructor, that value is
		returned. Otherwise, the longest codec duration is used.
	 
	*/
	public final TimeSpan getDuration()
	{
		TimeSpan duration = this.duration;

		if (System.TimeSpan.OpInequality(duration, TimeSpan.Zero))
		{
			return duration;
		}

		for (ICodec codec : codecs)
		{
			if (codec != null && codec.getDuration() > duration)
			{
				duration = codec.getDuration();
			}
		}

		return duration;
	}

	/** 
		Gets the types of media represented by the current
		instance.
	 
	 <value>
		A bitwise combined <see cref="MediaTypes" /> containing
		the types of media represented by the current instance.
	 </value>
	*/
	public final MediaTypes getMediaTypes()
	{
		MediaTypes types = MediaTypes.None;

		for (ICodec codec : codecs)
		{
			if (codec != null)
			{
				types = Rasad.Core.Media.MediaMetadataManagement.MediaTypes.forValue(types.getValue() | codec.getMediaTypes().getValue());
			}
		}

		return types;
	}

	/** 
		Gets a string description of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the media represented by the current instance.
	 </value>
	 
		The value contains the descriptions of the codecs joined
		by colons.
	 
	*/
	public final String getDescription()
	{
		StringBuilder builder = new StringBuilder();
		for (ICodec codec : codecs)
		{
			if (codec == null)
			{
				continue;
			}

			if (builder.length() != 0)
			{
				builder.append("; ");
			}

			builder.append(codec.getDescription());
		}
		return builder.toString();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IAudioCodec

	/** 
		Gets the bitrate of the audio represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> containing the bitrate of the audio
		represented by the current instance.
	 </value>
	 
		This value is equal to the first non-zero audio bitrate.
	 
	*/
	public final int getAudioBitrate()
	{
		for (ICodec codec : codecs)
		{
			if (codec == null || (codec.getMediaTypes().getValue() & MediaTypes.Audio.getValue()) == 0)
			{
				continue;
			}

			IAudioCodec audio = codec instanceof IAudioCodec ? (IAudioCodec)codec : null;

			if (audio != null && audio.getAudioBitrate() != 0)
			{
				return audio.getAudioBitrate();
			}
		}

		return 0;
	}

	/** 
		Gets the sample rate of the audio represented by the
		current instance.
	 
	 <value>
		A <see cref="int" /> containing the sample rate of the
		audio represented by the current instance.
	 </value>
	 
		This value is equal to the first non-zero audio sample
		rate.
	 
	*/
	public final int getAudioSampleRate()
	{
		for (ICodec codec : codecs)
		{
			if (codec == null || (codec.getMediaTypes().getValue() & MediaTypes.Audio.getValue()) == 0)
			{
				continue;
			}

			IAudioCodec audio = codec instanceof IAudioCodec ? (IAudioCodec)codec : null;

			if (audio != null && audio.getAudioSampleRate() != 0)
			{
				return audio.getAudioSampleRate();
			}
		}

		return 0;
	}

	/** 
		Gets the number of bits per sample in the audio
		represented by the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the number of bits
		per sample in the audio represented by the current
		instance.
	 </value>
	 
		This value is equal to the first non-zero quantization.
	 
	*/
	public final int getBitsPerSample()
	{
		for (ICodec codec : codecs)
		{
			if (codec == null || (codec.getMediaTypes().getValue() & MediaTypes.Audio.getValue()) == 0)
			{
				continue;
			}

			ILosslessAudioCodec lossless = codec instanceof ILosslessAudioCodec ? (ILosslessAudioCodec)codec : null;

			if (lossless != null && lossless.getBitsPerSample() != 0)
			{
				return lossless.getBitsPerSample();
			}
		}

		return 0;
	}

	/** 
		Gets the number of channels in the audio represented by
		the current instance.
	 
	 <value>
		A <see cref="int" /> object containing the number of
		channels in the audio represented by the current
		instance.
	 </value>
	 
		This value is equal to the first non-zero audio channel
		count.
	 
	*/
	public final int getAudioChannels()
	{
		for (ICodec codec : codecs)
		{
			if (codec == null || (codec.getMediaTypes().getValue() & MediaTypes.Audio.getValue()) == 0)
			{
				continue;
			}

			IAudioCodec audio = codec instanceof IAudioCodec ? (IAudioCodec)codec : null;

			if (audio != null && audio.getAudioChannels() != 0)
			{
				return audio.getAudioChannels();
			}
		}

		return 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IVideoCodec

	/** 
		Gets the width of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> containing the width of the video
		represented by the current instance.
	 </value>
	 
		This value is equal to the first non-zero video width.
	 
	*/
	public final int getVideoWidth()
	{
		for (ICodec codec : codecs)
		{
			if (codec == null || (codec.getMediaTypes().getValue() & MediaTypes.Video.getValue()) == 0)
			{
				continue;
			}

			IVideoCodec video = codec instanceof IVideoCodec ? (IVideoCodec)codec : null;

			if (video != null && video.getVideoWidth() != 0)
			{
				return video.getVideoWidth();
			}
		}

		return 0;
	}

	/** 
		Gets the height of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> containing the height of the video
		represented by the current instance.
	 </value>
	 
		This value is equal to the first non-zero video height.
	 
	*/
	public final int getVideoHeight()
	{
		for (ICodec codec : codecs)
		{
			if (codec == null || (codec.getMediaTypes().getValue() & MediaTypes.Video.getValue()) == 0)
			{
				continue;
			}

			IVideoCodec video = codec instanceof IVideoCodec ? (IVideoCodec)codec : null;

			if (video != null && video.getVideoHeight() != 0)
			{
				return video.getVideoHeight();
			}
		}

		return 0;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IPhotoCodec

	/** 
		Gets the width of the photo represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the width of the
		photo represented by the current instance.
	 </value>
	*/
	public final int getPhotoWidth()
	{
		for (ICodec codec : codecs)
		{
			if (codec == null || (codec.getMediaTypes().getValue() & MediaTypes.Photo.getValue()) == 0)
			{
				continue;
			}

			IPhotoCodec photo = codec instanceof IPhotoCodec ? (IPhotoCodec)codec : null;

			if (photo != null && photo.getPhotoWidth() != 0)
			{
				return photo.getPhotoWidth();
			}
		}

		return 0;
	}

	/** 
		Gets the height of the photo represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the height of the
		photo represented by the current instance.
	 </value>
	*/
	public final int getPhotoHeight()
	{
		for (ICodec codec : codecs)
		{
			if (codec == null || (codec.getMediaTypes().getValue() & MediaTypes.Photo.getValue()) == 0)
			{
				continue;
			}

			IPhotoCodec photo = codec instanceof IPhotoCodec ? (IPhotoCodec)codec : null;

			if (photo != null && photo.getPhotoHeight() != 0)
			{
				return photo.getPhotoHeight();
			}
		}

		return 0;
	}

	/** 
		Gets the (format specific) quality indicator of the photo
		represented by the current instance.
	 
	 <value>
		A <see cref="int" /> value indicating the quality. A value
		0 means that there was no quality indicator for the format
		or the file.
	 </value>
	*/
	public final int getPhotoQuality()
	{
		for (ICodec codec : codecs)
		{
			if (codec == null || (codec.getMediaTypes().getValue() & MediaTypes.Photo.getValue()) == 0)
			{
				continue;
			}

			IPhotoCodec photo = codec instanceof IPhotoCodec ? (IPhotoCodec)codec : null;

			if (photo != null && photo.getPhotoQuality() != 0)
			{
				return photo.getPhotoQuality();
			}
		}

		return 0;
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}