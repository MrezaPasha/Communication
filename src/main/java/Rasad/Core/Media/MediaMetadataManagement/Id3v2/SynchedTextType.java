package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
	Specifies the type of text contained in a <see
	cref="SynchronisedLyricsFrame" />.
*/
public enum SynchedTextType
{
	/** 
		The text is some other type of text.
	*/
	Other(0x00),

	/** 
		The text contains lyrical data.
	*/
	Lyrics(0x01),

	/** 
		The text contains a transcription.
	*/
	TextTranscription(0x02),

	/** 
		The text lists the movements in the piece.
	*/
	Movement(0x03),

	/** 
		The text describes events that occur.
	*/
	Events(0x04),

	/** 
		The text contains chord changes that occur in the music.
	*/
	Chord(0x05),

	/** 
		The text contains trivia or "pop up" information about
		the media.
	*/
	Trivia(0x06),

	/** 
		The text contains URL's for relevant webpages.
	*/
	WebpageUrls(0x07),

	/** 
		 The text contains URL's for relevant images.
	*/
	ImageUrls(0x08);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, SynchedTextType> mappings;
	private static java.util.HashMap<Integer, SynchedTextType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (SynchedTextType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, SynchedTextType>();
				}
			}
		}
		return mappings;
	}

	private SynchedTextType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static SynchedTextType forValue(int value)
	{
		return getMappings().get(value);
	}
}