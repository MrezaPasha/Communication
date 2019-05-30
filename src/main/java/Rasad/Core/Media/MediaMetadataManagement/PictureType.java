package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

//
// Picture.cs: Provides IPicture and Picture.
//
// Author:
//   Aaron Bockover (abockover@novell.com)
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   attachedpictureframe.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2006 Novell, Inc.
// Copyright (C) 2007 Brian Nickel
// Copyright (C) 2004 Scott Wheeler (Original Implementation)
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
	Specifies the type of content appearing in the picture.
*/
public enum PictureType
{
	/** 
		The picture is of a type other than those specified.
	*/
	Other(0x00),

	/** 
		The picture is a 32x32 PNG image that should be used when
		displaying the file in a browser.
	*/
	FileIcon(0x01),

	/** 
		The picture is of an icon different from <see
		cref="FileIcon" />.
	*/
	OtherFileIcon(0x02),

	/** 
		The picture is of the front cover of the album.
	*/
	FrontCover(0x03),

	/** 
		The picture is of the back cover of the album.
	*/
	BackCover(0x04),

	/** 
		The picture is of a leaflet page including with the
		album.
	*/
	LeafletPage(0x05),

	/** 
		The picture is of the album or disc itself.
	*/
	Media(0x06),
	// Image from the album itself

	/** 
		The picture is of the lead artist or soloist.
	*/
	LeadArtist(0x07),

	/** 
		The picture is of the artist or performer.
	*/
	Artist(0x08),

	/** 
		The picture is of the conductor.
	*/
	Conductor(0x09),

	/** 
		The picture is of the band or orchestra.
	*/
	Band(0x0A),

	/** 
		The picture is of the composer.
	*/
	Composer(0x0B),

	/** 
		The picture is of the lyricist or text writer.
	*/
	Lyricist(0x0C),

	/** 
		The picture is of the recording location or studio.
	*/
	RecordingLocation(0x0D),

	/** 
		The picture is one taken during the track's recording.
	*/
	DuringRecording(0x0E),

	/** 
		The picture is one taken during the track's performance.
	*/
	DuringPerformance(0x0F),

	/** 
		The picture is a capture from a movie screen.
	*/
	MovieScreenCapture(0x10),

	/** 
		The picture is of a large, colored fish.
	*/
	ColoredFish(0x11),

	/** 
		The picture is an illustration related to the track.
	*/
	Illustration(0x12),

	/** 
		The picture contains the logo of the band or performer.
	*/
	BandLogo(0x13),

	/** 
		The picture is the logo of the publisher or record
		company.
	*/
	PublisherLogo(0x14);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, PictureType> mappings;
	private static java.util.HashMap<Integer, PictureType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (PictureType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, PictureType>();
				}
			}
		}
		return mappings;
	}

	private PictureType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static PictureType forValue(int value)
	{
		return getMappings().get(value);
	}
}