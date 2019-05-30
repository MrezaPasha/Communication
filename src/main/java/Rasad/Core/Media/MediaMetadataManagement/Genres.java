package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

//
// Genres.cs: Provides convenience functions for converting between String
// genres and their respective audio and video indices as used by several
// formats.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   id3v1genres.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2002 Scott Wheeler (Original Implementation)
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
	This static class provides convenience functions for converting
	between <see cref="string" /> genres and their respective audio
	and video indices as used by several formats.
*/
public final class Genres
{
	/** 
		Contains a list of ID3v1 audio generes.
	*/
	private static final String [] audio = {"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "Alternative Rock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychedelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk/Rock", "National Folk", "Swing", "Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A Cappella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", "Negerpunk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "Jpop", "Synthpop"};

	/** 
		Contains a list of DivX audio generes.
	*/
	private static final String [] video = new String [] {"Action", "Action/Adventure", "Adult", "Adventure", "Catastrophe", "Child's", "Claymation", "Comedy", "Concert", "Documentary", "Drama", "Eastern", "Entertaining", "Erotic", "Extremal Sport", "Fantasy", "Fashion", "Historical", "Horror", "Horror/Mystic", "Humor", "Indian", "Informercial", "Melodrama", "Military & War", "Music Video", "Musical", "Mystery", "Nature", "Political Satire", "Popular Science", "Psychological Thriller", "Religion", "Science Fiction", "Scifi Action", "Slapstick", "Splatter", "Sports", "Thriller", "Western"};

	/** 
		Gets a list of standard audio generes.
	 
	 <value>
		A <see cref="string[]" /> containing standard audio
		genres.
	 </value>
	 
		The genres are stored in the same order and with the same
		values as in the ID3v1 format.
	 
	*/
	public static String[] getAudio()
	{
		return (String []) audio.clone();
	}

	/** 
		Gets a list of standard video generes.
	 
	 <value>
		A <see cref="string[]" /> containing standard video
		genres.
	 </value>
	 
		The genres are stored in the same order and with the same
		values as in the DivX format.
	 
	*/
	public static String[] getVideo()
	{
		return (String []) video.clone();
	}

	/** 
		Gets the genre index for a specified audio genre.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		genre to look up.
	 
	 @return 
		A <see cref="byte" /> value containing the index of the
		genre in the audio array or 255 if it could not be found.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte AudioToIndex(string name)
	public static byte AudioToIndex(String name)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: for (byte i = 0; i < audio.Length; i ++)
		for (byte i = 0; i < audio.length; i++)
		{
			if (audio[i].equals(name))
			{
				return i;
			}
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return 255;
		return (byte)255;
	}

	/** 
		Gets the genre index for a specified video genre.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		genre to look up.
	 
	 @return 
		A <see cref="byte" /> value containing the index of the
		genre in the video array or 255 if it could not be found.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte VideoToIndex(string name)
	public static byte VideoToIndex(String name)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: for (byte i = 0; i < video.Length; i ++)
		for (byte i = 0; i < video.length; i++)
		{
			if (video[i].equals(name))
			{
				return i;
			}
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return 255;
		return (byte)255;
	}

	/** 
		Gets the audio genre from its index in the array.
	 
	 @param index
		A <see cref="byte" /> value containing the index to
		aquire the genre from.
	 
	 @return 
		A <see cref="string" /> object containing the audio genre
		found at the index, or <see langword="null" /> if it does
		not exist.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string IndexToAudio(byte index)
	public static String IndexToAudio(byte index)
	{
		return (index < audio.length) ? audio [index] : null;
	}

	/** 
		Gets the video genre from its index in the array.
	 
	 @param index
		A <see cref="byte" /> value containing the index to
		aquire the genre from.
	 
	 @return 
		A <see cref="string" /> object containing the video genre
		found at the index, or <see langword="null" /> if it does
		not exist.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string IndexToVideo(byte index)
	public static String IndexToVideo(byte index)
	{
		return (index < video.length) ? video [index] : null;
	}

	/** 
		Gets the audio genre from its index in the array.
	 
	 @param text
		A <see cref="string" /> object, either in the format
		<c>"(123)"</c> or <c>"123"</c>.
	 
	 @return 
		A <see cref="string" /> object containing the audio genre
		found at the index, or <see langword="null" /> if it does
		not exist.
	 
	*/
	public static String IndexToAudio(String text)
	{
		return IndexToAudio(StringToByte(text));
	}

	/** 
		Gets the video genre from its index in the array.
	 
	 @param text
		A <see cref="string" /> object, either in the format
		<c>"(123)"</c> or <c>"123"</c>.
	 
	 @return 
		A <see cref="string" /> object containing the video genre
		found at the index, or <see langword="null" /> if it does
		not exist.
	 
	*/
	public static String IndexToVideo(String text)
	{
		return IndexToVideo(StringToByte(text));
	}

	/** 
		Converts a string, either in the format <c>"(123)"</c> or
		<c>"123"</c> into a byte or equal numeric value.
	 
	 @param text
		A <see cref="string" /> object, either in the format
		<c>"(123)"</c> or <c>"123"</c>, to be converted.
	 
	 @return 
		A <see cref="byte" /> value containing the numeric value
		of <paramref name="text" /> or 255 if no numeric value
		could be extracted.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static byte StringToByte(string text)
	private static byte StringToByte(String text)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte value;
		byte value;
		int last_pos;
		tangible.OutObject<Byte> tempOut_value = new tangible.OutObject<Byte>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (text != null && text.Length > 2 && text [0] == '(' && (last_pos = text.IndexOf(')')) != -1 && byte.TryParse(text.Substring(1, last_pos - 1), out value))
		if (text != null && text.length() > 2 && text.charAt (0) == '(' && (last_pos = text.indexOf(')')) != -1 && tangible.TryParseHelper.tryParseByte(text.substring(1, last_pos), tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
	else
	{
		value = tempOut_value.argValue;
	}

		tangible.OutObject<Byte> tempOut_value2 = new tangible.OutObject<Byte>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (text != null && byte.TryParse(text, out value))
		if (text != null && tangible.TryParseHelper.tryParseByte(text, tempOut_value2))
		{
		value = tempOut_value2.argValue;
			return value;
		}
	else
	{
		value = tempOut_value2.argValue;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return 255;
		return (byte)255;
	}
}