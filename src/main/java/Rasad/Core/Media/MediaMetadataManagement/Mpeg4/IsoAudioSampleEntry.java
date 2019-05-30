package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// IsoAudioSampleEntry.cs: Provides an implementation of a ISO/IEC 14496-12
// AudioSampleEntry and support for reading MPEG-4 video properties.
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
	<see cref="IAudioCodec" /> to provide an implementation of a
	ISO/IEC 14496-12 AudioSampleEntry and support for reading MPEG-4
	video properties.
*/
public class IsoAudioSampleEntry extends IsoSampleEntry implements IAudioCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the channel count.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort channel_count;
	private short channel_count;

	/** 
		Contains the sample size.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort sample_size;
	private short sample_size;

	/** 
		Contains the sample rate.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint sample_rate;
	private int sample_rate;

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
	public IsoAudioSampleEntry(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), file, handler);
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.Seek(super.getDataPosition() + 8);
		channel_count = file.ReadBlock(2).ToUShort();
		sample_size = file.ReadBlock(2).ToUShort();
		file.Seek(super.getDataPosition() + 16);
		sample_rate = file.ReadBlock(4).ToUInt();
		children = LoadChildren(file);
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
		return super.getDataPosition() + 20;
	}

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



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IAudioCodec Properties

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
		return MediaTypes.Audio;
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
		return String.format(CultureInfo.InvariantCulture, "MPEG-4 Audio (%1$s)", getBoxType());
	}

	/** 
		Gets the bitrate of the audio represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing a bitrate of the
		audio represented by the current instance.
	 </value>
	*/
	public final int getAudioBitrate()
	{
		Rasad.Core.Media.MediaMetadataManagement.Mpeg4.Box tempVar = GetChildRecursively("esds");
		AppleElementaryStreamDescriptor esds = tempVar instanceof AppleElementaryStreamDescriptor ? (AppleElementaryStreamDescriptor)tempVar : null;

			// If we don't have an stream descriptor, we
			// don't know what's what.
		if (esds == null)
		{
			return 0;
		}

			// Return from the elementary stream descriptor.
		return (int) esds.getAverageBitrate();
	}

	/** 
		Gets the sample rate of the audio represented by the
		current instance.
	 
	 <value>
		A <see cref="int" /> value containing the sample rate of
		the audio represented by the current instance.
	 </value>
	*/
	public final int getAudioSampleRate()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
		return (int)(sample_rate >>> 16);
	}

	/** 
		Gets the number of channels in the audio represented by
		the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the number of
		channels in the audio represented by the current
		instance.
	 </value>
	*/
	public final int getAudioChannels()
	{
		return channel_count;
	}

	/** 
		Gets the sample size of the audio represented by the
		current instance.
	 
	 <value>
		A <see cref="int" /> value containing the sample size of
		the audio represented by the current instance.
	 </value>
	*/
	public final int getAudioSampleSize()
	{
		return sample_size;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}