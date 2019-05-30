package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// FrameFactory.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   id3v2framefactory.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2002,2003 Scott Wheeler (Original Implementation)
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
	This static class performs the necessary operations to determine
	and create the correct child class of <see cref="Frame" /> for a
	given raw ID3v2 frame.
 
 
	By default, <see cref="FrameFactory" /> will only load frames
	contained in the library. To add additional frames to the
	process, register a frame creator with <see
	cref="AddFrameCreator" />.
 
*/
public final class FrameFactory
{
	/** 
		Creates a frame from a specified block of data, or
		returns <see langword="null" /> if unsupported.
	 
	 @param data
		A <see cref="ByteVector" /> object containing a raw ID3v2
		frame.
	 
	 @param offset
		A <see cref="int" /> value specifying the offset in
		<paramref name="data"/> at which the frame data begins.
	 
	 @param header
		A <see cref="FrameHeader" /> object for the frame
		contained in <paramref name="data" />.
	 
	 @param version
		A <see cref="byte" /> specifying the version of ID3v2 the
		raw frame data is stored in.
	 
	 @return 
		 A <see cref="Frame" /> object if the method was able to
		 match and create one. Otherwise <see langword="null" />.
	 
	 
		<p>Frame creators are used to provide access or
		support for items that are left out of Rasad.Core.Media.MediaMetadataManagement#.</p>
	 
	 <example>
		<code lang="C#">
	 public Frame Creator (Rasad.Core.Media.MediaMetadataManagement.ByteVector data, Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameHeader header)
	 {
	 	if (header.FrameId == "RVRB")
	 		return new ReverbFrame (data, header);
	 	else
	 		return null;
	 }
	 ...
	 Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFactor.AddFrameCreator (ReverbFrame.Creator);
	   </code>
	 </example>
	 {@link AddFrameCreator }
	*/
	@FunctionalInterface
	public interface FrameCreator
	{
		Frame invoke(ByteVector data, int offset, FrameHeader header, byte version);
	}

	/** 
		Contains registered frame creators.
	*/
	private static ArrayList<FrameCreator> frame_creators = new ArrayList<FrameCreator> ();

	/** 
		Creates a <see cref="Frame" /> object by reading it from
		raw ID3v2 frame data.
	 
	 @param data
		A <see cref="ByteVector" /> object containing a raw ID3v2
		frame.
	 
	 @param offset
		A <see cref="int" /> value reference specifying at what
		index in <paramref name="data" /> at which the frame
		begins. After reading, it contains the offset of the next
		frame to be read.
	 
	 @param version
		A <see cref="byte" /> value specifying the ID3v2 version
		the frame in <paramref name="data"/> is encoded in.
	 
	 @param alreadyUnsynched
		A <see cref="bool" /> value specifying whether the entire
		tag has already been unsynchronized.
	 
	 @return 
		A <see cref="Frame" /> object read from the data, or <see
		langword="null" /> if none is found.
	 
	 @exception System.NotImplementedException
		The frame contained in the raw data could not be
		converted to ID3v2 or uses encryption or compression.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static Frame CreateFrame(ByteVector data, ref int offset, byte version, bool alreadyUnsynched)
	public static Frame CreateFrame(ByteVector data, tangible.RefObject<Integer> offset, byte version, boolean alreadyUnsynched)
	{
		int position = offset.argValue;

		FrameHeader header = new FrameHeader(data.Mid(position, (int) FrameHeader.Size(version)), version);

		offset.argValue += (int)(header.getFrameSize() + FrameHeader.Size(version));

		if (header.getFrameId() == null)
		{
			throw new UnsupportedOperationException();
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (byte b in header.FrameId)
		for (byte b : header.getFrameId())
		{
			char c = (char) b;
				if ((c < 'A' || c > 'Z') && (c < '0' || c > '9'))
				{
					return null;
				}
		}

		if (alreadyUnsynched)
		{
			// Mark the frame as not Unsynchronozed because the entire
			// tag has already been Unsynchronized
			header.setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(header.getFlags().getValue() & ~FrameFlags.Unsynchronisation.getValue()));
		}

		// Windows Media Player may create zero byte frames.
		// Just send them off as unknown and delete them.
		if (header.getFrameSize() == 0)
		{
			header.setFlags(Rasad.Core.Media.MediaMetadataManagement.Id3v2.FrameFlags.forValue(header.getFlags().getValue() | FrameFlags.TagAlterPreservation.getValue()));
			return new UnknownFrame(data, position, header.clone(), version);
		}

		// TODO: Support Compression.
		if ((header.getFlags().getValue() & FrameFlags.Compression.getValue()) != 0)
		{
			throw new UnsupportedOperationException();
		}

		// TODO: Support Encryption.
		if ((header.getFlags().getValue() & FrameFlags.Encryption.getValue()) != 0)
		{
			throw new UnsupportedOperationException();
		}

		for (FrameCreator creator : frame_creators)
		{
			Frame frame = creator.invoke(data, position, header.clone(), version);

			if (frame != null)
			{
				return frame;
			}
		}

		// This is where things get necissarily nasty.  Here we
		// determine which Frame subclass (or if none is found
		// simply an Frame) based on the frame ID. Since there
		// are a lot of possibilities, that means a lot of if
		// blocks.

		// Text Identification (frames 4.2)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.TXXX))
		{
			return new UserTextInformationFrame(data, position, header.clone(), version);
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (header.FrameId [0] == (byte) 'T')
		if (header.getFrameId().get(0) == (byte) 'T')
		{
			return new TextInformationFrame(data, position, header.clone(), version);
		}

		// Unique File Identifier (frames 4.1)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.UFID))
		{
			return new UniqueFileIdentifierFrame(data, position, header.clone(), version);
		}

		// Music CD Identifier (frames 4.5)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.MCDI))
		{
			return new MusicCdIdentifierFrame(data, position, header.clone(), version);
		}

		// Unsynchronized Lyrics (frames 4.8)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.USLT))
		{
			return new UnsynchronisedLyricsFrame(data, position, header.clone(), version);
		}

		// Synchronized Lyrics (frames 4.9)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.SYLT))
		{
			return new SynchronisedLyricsFrame(data, position, header.clone(), version);
		}

		// Comments (frames 4.10)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.COMM))
		{
			return new CommentsFrame(data, position, header.clone(), version);
		}

		// Relative Volume Adjustment (frames 4.11)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.RVA2))
		{
			return new RelativeVolumeFrame(data, position, header.clone(), version);
		}

		// Attached Picture (frames 4.14)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.APIC))
		{
			return new AttachedPictureFrame(data, position, header.clone(), version);
		}

		// General Encapsulated Object (frames 4.15)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.GEOB))
		{
			return new GeneralEncapsulatedObjectFrame(data, position, header.clone(), version);
		}

		// Play Count (frames 4.16)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.PCNT))
		{
			return new PlayCountFrame(data, position, header.clone(), version);
		}

		// Play Count (frames 4.17)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.POPM))
		{
			return new PopularimeterFrame(data, position, header.clone(), version);
		}

		// Terms of Use (frames 4.22)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.USER))
		{
			return new TermsOfUseFrame(data, position, header.clone(), version);
		}

		// Private (frames 4.27)
		if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(header.getFrameId(), FrameType.PRIV))
		{
			return new PrivateFrame(data, position, header.clone(), version);
		}

		return new UnknownFrame(data, position, header.clone(), version);
	}

	/** 
		Adds a curstom frame creator to try before using standard
		frame creation methods.
	 
	 @param creator
		A <see cref="FrameCreator" /> delegate to be used by the
		frame factory.
	 
	 
		Frame creators are used before standard methods so custom
		checking can be used and new formats can be added. They
		are executed in the reverse order in which they are
		added.
	 
	 @exception System.ArgumentNullException
		<paramref name="creator" /> is <see langword="null" />.
	 
	*/
	public static void AddFrameCreator(FrameCreator creator)
	{
		if (creator == null)
		{
			throw new NullPointerException("creator");
		}

		frame_creators.add(0, creator);
	}
}