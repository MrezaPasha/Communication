package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// TextInformationFrame.cs: Provides support ID3v2 Text Information Frames
// (Section 4.2), covering "T000" to "TZZZ", excluding "TXXX".
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   textidentificationframe.cpp from Rasad.Core.Media.MediaMetadataManagement
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
	This class extends <see cref="Frame" /> to provide support ID3v2
	Text Information Frames (Section 4.2), covering "<c>T000</c>" to
	"<c>TZZZ</c>", excluding "<c>TXXX</c>".
 
 
	<p>Text Information Frames contain the most commonly used
	values in tagging, including the artist, the track name, and just
	about any value that can be expressed as text.</p>
	<p>The following table contains types and descriptions as
	found in the ID3 2.4.0 native frames specification. (Copyright
	(C) Martin Nilsson 2000.)</p>
	
	<list type="table">
	   <listheader>
		  <term>ID</term>
		  <description>Description</description>
	   </listheader>
	   <item>
		  <term>TIT1</term>
		  <description>The 'Content group description' frame is used
		  if the sound belongs to a larger category of sounds/music.
		  For example, classical music is often sorted in different
		  musical sections (e.g. "Piano Concerto", "Weather -
		  Hurricane").</description>
	   </item>
	   <item>
		  <term>TIT2</term>
		  <description>The 'Title/Songname/Content description' frame
		  is the actual name of the piece (e.g. "Adagio", "Hurricane
		  Donna").</description>
	   </item>
	   <item>
		  <term>TIT3</term>
		  <description>The 'Subtitle/Description refinement' frame is
		  used for information directly related to the contents title
		  (e.g. "Op. 16" or "Performed live at
		  Wembley").</description>
	   </item>
	   <item>
		  <term>TALB</term>
		  <description>The 'Album/Movie/Show title' frame is intended
		  for the title of the recording (or source of sound) from
		  which the audio in the file is taken.</description>
	   </item>
	   <item>
		  <term>TOAL</term>
		  <description>The 'Original album/movie/show title' frame is
		  intended for the title of the original recording (or source
		  of sound), if for example the music in the file should be a
		  cover of a previously released song.</description>
	   </item>
	   <item>
		  <term>TRCK</term>
		  <description>The 'Track number/Position in set' frame is a
		  numeric string containing the order number of the
		  audio-file on its original recording. This MAY be extended
		  with a "/" character and a numeric string containing the
		  total number of tracks/elements on the original recording.
		  E.g. "4/9".</description>
	   </item>
	   <item>
		  <term>TPOS</term>
		  <description>The 'Part of a set' frame is a numeric string
		  that describes which part of a set the audio came from.
		  This frame is used if the source described in the "TALB"
		  frame is divided into several mediums, e.g. a double CD.
		  The value MAY be extended with a "/" character and a
		  numeric string containing the total number of parts in the
		  set. E.g. "1/2".</description>
	   </item>
	   <item>
		  <term>TSST</term>
		  <description>The 'Set subtitle' frame is intended for the
		  subtitle of the part of a set this track belongs
		  to.</description>
	   </item>
	   <item>
		  <term>TSRC</term>
		  <description>The 'ISRC' frame should contain the
		  International Standard Recording Code [ISRC] (12
		  characters).</description>
	   </item>
	   <item>
		  <term>TPE1</term>
		  <description>The
		  'Lead artist/Lead performer/Soloist/Performing group' is
		  used for the main artist.</description>
	   </item>
	   <item>
		  <term>TPE2</term>
		  <description>The 'Band/Orchestra/Accompaniment' frame is
		  used for additional information about the performers in the
		  recording.</description>
	   </item>
	   <item>
		  <term>TPE3</term>
		  <description>The 'Conductor' frame is used for the name of
		  the conductor.</description>
	   </item>
	   <item>
		  <term>TPE4</term>
		  <description>The 'Interpreted, remixed, or otherwise
		  modified by' frame contains more information about the
		  people behind a remix and similar interpretations of
		  another existing piece.</description>
	   </item>
	   <item>
		  <term>TOPE</term>
		  <description>The 'Original artist/performer' frame is
		  intended for the performer of the original recording, if
		  for example the music in the file should be a cover of a
		  previously released song.</description>
	   </item>
	   <item>
		  <term>TEXT</term>
		  <description>The 'Lyricist/Text writer' frame is intended
		  for the writer of the text or lyrics in the
		  recording.</description>
	   </item>
	   <item>
		  <term>TOLY</term>
		  <description>The 'Original lyricist/text writer' frame is
		  intended for the text writer of the original recording, if
		  for example the music in the file should be a cover of a
		  previously released song.</description>
	   </item>
	   <item>
		  <term>TCOM</term>
		  <description>The 'Composer' frame is intended for the name
		  of the composer.</description>
	   </item>
	   <item>
		  <term>TMCL</term>
		  <description>The 'Musician credits list' is intended as a
		  mapping between instruments and the musician that played
		  it. Every odd field is an instrument and every even is an
		  artist or a comma delimited list of artists.</description>
	   </item>
	   <item>
		  <term>TIPL</term>
		  <description>The 'Involved people list' is very similar to
		  the musician credits list, but maps between functions, like
		  producer, and names.</description>
	   </item>
	   <item>
		  <term>TENC</term>
		  <description>The 'Encoded by' frame contains the name of
		  the person or organisation that encoded the audio file.
		  This field may contain a copyright message, if the audio
		  file also is copyrighted by the encoder.</description>
	   </item>
	   <item>
		  <term>TBPM</term>
		  <description>The 'BPM' frame contains the number of beats
		  per minute in the main part of the audio. The BPM is an
		  integer and represented as a numerical
		  string.</description>
	   </item>
	   <item>
		  <term>TLEN</term>
		  <description>The 'Length' frame contains the length of the
		  audio file in milliseconds, represented as a numeric
		  string.</description>
	   </item>
	   <item>
		  <term>TKEY</term>
		  <description>The 'Initial key' frame contains the musical
		  key in which the sound starts. It is represented as a
		  string with a maximum length of three characters. The
		  ground keys are represented with "A","B","C","D","E", "F"
		  and "G" and halfkeys represented with "b" and "#". Minor is
		  represented as "m", e.g. "Dbm". Off key is represented with
		  an "o" only.</description>
	   </item>
	   <item>
		  <term>TLAN</term>
		  <description>The 'Language' frame should contain the
		  languages of the text or lyrics spoken or sung in the
		  audio. The language is represented with three characters
		  according to ISO-639-2. If more than one language is used
		  in the text their language codes should follow according to
		  the amount of their usage.</description>
	   </item>
	   <item>
		  <term>TCON</term>
		  <description>The 'Content type', which ID3v1 was stored as
		  a one byte numeric value only, is now a string. You may use
		  one or several of the ID3v1 types as numerical strings, or,
		  since the category list would be impossible to maintain
		  with accurate and up to date categories, define your
		  own.</description>
	   </item>
	   <item>
		  <term>TFLT</term>
		  <description>The 'File type' frame indicates which type of
		  audio this tag defines. (See the specification for more
		  details.)</description>
	   </item>
	   <item>
		  <term>TMED</term>
		  <description>The 'Media type' frame describes from which
		  media the sound originated. (See the specification for more
		  details.)</description>
	   </item>
	   <item>
		  <term>TMOO</term>
		  <description>The 'Mood' frame is intended to reflect the
		  mood of the audio with a few keywords, e.g. "Romantic" or
		  "Sad".</description>
	   </item>
	   <item>
		  <term>TCOP</term>
		  <description>The 'Copyright message' frame, in which the
		  string must begin with a year and a space character (making
		  five characters), is intended for the copyright holder of
		  the original sound, not the audio file itself. The absence
		  of this frame means only that the copyright information is
		  unavailable or has been removed, and must not be
		  interpreted to mean that the audio is public domain. Every
		  time this field is displayed the field must be preceded
		  with "Copyright " (C) " ", where (C) is one character
		  showing a C in a circle.</description>
	   </item>
	   <item>
		  <term>TPRO</term>
		  <description>The 'Produced notice' frame, in which the
		  string must begin with a year and a space character (making
		  five characters), is intended for the production copyright
		  holder of the original sound, not the audio file itself.
		  The absence of this frame means only that the production
		  copyright information is unavailable or has been removed,
		  and must not be interpreted to mean that the audio is
		  public domain. Every time this field is displayed the field
		  must be preceded with "Produced " (P) " ", where (P) is one
		  character showing a P in a circle.</description>
	   </item>
	   <item>
		  <term>TPUB</term>
		  <description>The 'Publisher' frame simply contains the name
		  of the label or publisher.</description>
	   </item>
	   <item>
		  <term>TOWN</term>
		  <description>The 'File owner/licensee' frame contains the
		  name of the owner or licensee of the file and it's
		  contents.</description>
	   </item>
	   <item>
		  <term>TRSN</term>
		  <description>The 'Internet radio station name' frame
		  contains the name of the internet radio station from which
		  the audio is streamed.</description>
	   </item>
	   <item>
		  <term>TRSO</term>
		  <description>The 'Internet radio station owner' frame
		  contains the name of the owner of the internet radio
		  station from which the audio is streamed.</description>
	   </item>
	   <item>
		  <term>TOFN</term>
		  <description>The 'Original filename' frame contains the
		  preferred filename for the file, since some media doesn't
		  allow the desired length of the filename. The filename is
		  case sensitive and includes its suffix.</description>
	   </item>
	   <item>
		  <term>TDLY</term>
		  <description>The 'Playlist delay' defines the numbers of
		  milliseconds of silence that should be inserted before this
		  audio. The value zero indicates that this is a part of a
		  multifile audio track that should be played
		  continuously.</description>
	   </item>
	   <item>
		  <term>TDEN</term>
		  <description>The 'Encoding time' frame contains a timestamp
		  describing when the audio was encoded. Timestamp format is
		  described in the ID3v2 structure document.</description>
	   </item>
	   <item>
		  <term>TDOR</term>
		  <description>The 'Original release time' frame contains a
		  timestamp describing when the original recording of the
		  audio was released. Timestamp format is described in the
		  ID3v2 structure document.</description>
	   </item>
	   <item>
		  <term>TDRC</term>
		  <description>The 'Recording time' frame contains a
		  timestamp describing when the audio was recorded. Timestamp
		  format is described in the ID3v2 structure
		  document.</description>
	   </item>
	   <item>
		  <term>TDRL</term>
		  <description>The 'Release time' frame contains a timestamp
		  describing when the audio was first released. Timestamp
		  format is described in the ID3v2 structure
		  document.</description>
	   </item>
	   <item>
		  <term>TDTG</term>
		  <description>The 'Tagging time' frame contains a timestamp
		  describing then the audio was tagged. Timestamp format is
		  described in the ID3v2 structure document.</description>
	   </item>
	   <item>
		  <term>TSSE</term>
		  <description>The 'Software/Hardware and settings used for
		  encoding' frame includes the used audio encoder and its
		  settings when the file was encoded. Hardware refers to
		  hardware encoders, not the computer on which a program was
		  run.</description>
	   </item>
	   <item>
		  <term>TSOA</term>
		  <description>The 'Album sort order' frame defines a string
		  which should be used instead of the album name (TALB) for
		  sorting purposes. E.g. an album named "A Soundtrack" might
		  preferably be sorted as "Soundtrack".</description>
	   </item>
	   <item>
		  <term>TSOP</term>
		  <description>The 'Performer sort order' frame defines a
		  string which should be used instead of the performer (TPE2)
		  for sorting purposes.</description>
	   </item>
	   <item>
		  <term>TSOT</term>
		  <description>The 'Title sort order' frame defines a string
		  which should be used instead of the title (TIT2) for
		  sorting purposes.</description>
	   </item>
	</list>
 
*/
public class TextInformationFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the encoding to use for the text.
	*/
	private StringType encoding = Id3v2.Tag.getDefaultEncoding();

	/** 
		Contains the text fields.
	*/
	private String [] text_fields = new String [0];

	/** 
		Contains the raw data from the frame, or <see
		langword="null" /> if it has been processed.
	 
	 
		Rather than processing the data when the frame is loaded,
		it is parsed on demand, reducing the ammount of
		unnecessary conversion.
	 
	*/
	private ByteVector raw_data = null;

	/** 
		Contains the ID3v2 version of <see cref="raw_data" />.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte raw_version = 0;
	private byte raw_version = 0;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="TextInformationFrame" /> with a specified
		identifier and text encoding.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing an ID3v2.4
		frame identifier.
	 
	 @param encoding
		A <see cref="StringType" /> value specifying the encoding
		to use for the new instance.
	 
	*/
	public TextInformationFrame(ByteVector ident, StringType encoding)
	{
		super(ident, 4);
		this.encoding = encoding;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="TextInformationFrame" /> with a specified
		identifer.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing an ID3v2.4
		frame identifier.
	 
	*/
	public TextInformationFrame(ByteVector ident)
	{
		this(ident, Id3v2.Tag.getDefaultEncoding());
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="TextInformationFrame" /> by reading its raw
		contents in a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the
		frame to read.
	 
	 @param version
		A <see cref="byte" /> value containing the ID3v2 version
		in which <paramref name="data" /> is encoded.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TextInformationFrame(ByteVector data, byte version)
	public TextInformationFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="TextInformationFrame" /> by reading its raw
		contents from a specifed position in a <see
		cref="ByteVector" /> object in a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the frame
		to read.
	 
	 @param offset
		A <see cref="int" /> value specifying the offset in
		<paramref name="data" /> at which the frame begins.
	 
	 @param header
		A <see cref="FrameHeader" /> value containing the header
		that would be read in the frame.
	 
	 @param version
		A <see cref="byte" /> value containing the ID3v2 version
		in which <paramref name="data" /> is encoded.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected internal TextInformationFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected TextInformationFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(header.clone());
		SetData(data, offset, version, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the text contained in the current instance.
	 
	 <value>
		A <see cref="StringCollection" /> object containing the
		text contained in the current instance.
	 </value>
	 
		Modifying the contents of the returned value will not
		modify the contents of the current instance.
	 
	*/
	@Deprecated
	public final StringCollection getFieldList()
	{
		ParseRawData();
		return new StringCollection(getText());
	}

	/** 
		Gets and sets the text contained in the current
		instance.
	 
	 <value>
		A <see cref="string[]" /> containing the text contained
		in the current instance.
	 </value>
	 
		<p>Modifying the contents of the returned value will
		not modify the contents of the current instance. The
		value must be reassigned for the value to change.</p>
	 
	 <example>
		<p>Modifying the values text values of a frame.</p>
		<code>TextInformationFrame frame = TextInformationFrame.Get (myTag, "TPE1", true);
	*/
	////* Upper casing all the text: */
	/**string[] text = frame.Text;
	for (int i = 0; i &lt; text.Length; i++)
		text [i] = text [i].ToUpper ();
	frame.Text = text;
	
	*/
	////* Replacing the value completely: */
	/**frame.Text = new string [] {"DJ Jazzy Jeff"};</code>
	 </example>
	*/
	public String[] getText()
	{
		ParseRawData();
		return (String[]) text_fields.clone();
	}
	public void setText(String[] value)
	{
		raw_data = null;
		text_fields = value != null ? (String[]) value.clone() : new String [0];
	}

	/** 
		Gets and sets the text encoding to use when rendering
		the current instance.
	 
	 <value>
		A <see cref="StringType" /> value specifying the encoding
		to use when rendering the current instance.
	 </value>
	 
		This value will be overwritten if <see
		cref="Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag.ForceDefaultEncoding" /> is <see
		langword="true" />.
	 
	*/
	public final StringType getTextEncoding()
	{
		ParseRawData();
		return encoding;
	}
	public final void setTextEncoding(StringType value)
	{
		encoding = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Sets the text contained in the current instance.
	 
	 @param fields
		A <see cref="StringCollection" /> object containing text
		to store in the current instance.
	 
	*/
	@Deprecated
	public final void SetText(StringCollection fields)
	{
		raw_data = null;
		setText(fields != null ? fields.toArray(new Object[0]) : null);
	}

	/** 
		Sets the text contained in the current instance.
	 
	 @param text
		A <see cref="string[]" /> containing text to store in the
		current instance.
	 
	*/
	@Deprecated
	public final void SetText(String... text)
	{
		raw_data = null;
		setText(text);
	}

	/** 
		Gets a string representation of the current instance.
	 
	 @return 
		A <see cref="string" /> containing the joined text.
	 
	*/
	@Override
	public String toString()
	{
		ParseRawData();
		return tangible.StringHelper.join("; ", getText());
	}

	/** 
		Renders the current instance, encoded in a specified
		ID3v2 version.
	 
	 @param version
		A <see cref="byte" /> value specifying the version of
		ID3v2 to use when encoding the current instance.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override ByteVector Render(byte version)
	@Override
	public ByteVector Render(byte version)
	{
		if (version != 3 || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpInequality(getFrameId(), FrameType.TDRC))
		{
			return super.Render(version);
		}

		String text = toString();
		if (text.length() < 10 || text.charAt (4) != '-' || text.charAt (7) != '-')
		{
			return super.Render(version);
		}

		ByteVector output = new ByteVector();
		TextInformationFrame f;

		f = new TextInformationFrame(FrameType.TYER, encoding);
		f.setText(new String [] {text.substring(0, 4)});
		output.add(f.Render(version));

		f = new TextInformationFrame(FrameType.TDAT, encoding);
		f.setText(new String [] {text.substring(5, 7) + text.substring(8, 10)});
		output.add(f.Render(version));

		if (text.length() < 16 || text.charAt (10) != 'T' || text.charAt (13) != ':')
		{
			return output;
		}

		f = new TextInformationFrame(FrameType.TIME, encoding);
		f.setText(new String [] {text.substring(11, 13) + text.substring(14, 16)});
		output.add(f.Render(version));

		return output;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Gets a <see cref="TextInformationFrame" /> object of a
		specified type from a specified tag, optionally creating
		and adding one with a specified encoding if none is
		found.
	 
	 @param tag
		A <see cref="Tag" /> object to search for the specified
		tag in.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the frame
		identifer to search for.
	 
	 @param encoding
		A <see cref="StringType" /> value specifying the encoding
		to use if a new frame is created.
	 
	 @param create
		A <see cref="bool" /> value specifying whether or not to
		create a new frame if an existing frame was not found.
	 
	 @return 
		A <see cref="TextInformationFrame" /> object containing
		the frame found in or added to <paramref name="tag" /> or
		<see langword="null" /> if no value was found <paramref
		name="create" /> is <see langword="false" />.
	 
	 
		To create a frame without having to specify the encoding,
		use <see cref="Get(Tag,ByteVector,bool)" />.
	 
	 @exception ArgumentNullException
		<paramref name="tag" /> or <paramref name="type" /> is
		<see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="type" /> is not exactly four bytes long.
	 
	*/
	public static TextInformationFrame Get(Tag tag, ByteVector ident, StringType encoding, boolean create)
	{
		if (tag == null)
		{
			throw new NullPointerException("tag");
		}

		if (ident == null)
		{
			throw new NullPointerException("ident");
		}

		if (ident.size() != 4)
		{
			throw new IllegalArgumentException("Identifier must be four bytes long.", "ident");
		}

		for (TextInformationFrame frame : tag.<TextInformationFrame>GetFrames (ident))
		{
			return frame;
		}

		if (!create)
		{
			return null;
		}

		TextInformationFrame new_frame = new TextInformationFrame(ident, encoding);
		tag.AddFrame(new_frame);
		return new_frame;
	}

	/** 
		Gets a <see cref="TextInformationFrame" /> object of a
		specified type from a specified tag, optionally creating
		and adding one if none is found.
	 
	 @param tag
		A <see cref="Tag" /> object to search for the specified
		tag in.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the frame
		identifer to search for.
	 
	 @param create
		A <see cref="bool" /> value specifying whether or not to
		create a new frame if an existing frame was not found.
	 
	 @return 
		A <see cref="TextInformationFrame" /> object containing
		the frame found in or added to <paramref name="tag" /> or
		<see langword="null" /> if no value was found <paramref
		name="create" /> is <see langword="false" />.
	 
	 @exception ArgumentNullException
		<paramref name="tag" /> or <paramref name="ident" /> is
		<see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="ident" /> is not exactly four bytes long.
	 
	*/
	public static TextInformationFrame Get(Tag tag, ByteVector ident, boolean create)
	{
		return Get(tag, ident, Tag.getDefaultEncoding(), create);
	}

	/** 
		Gets a <see cref="TextInformationFrame" /> object of a
		specified type from a specified tag.
	 
	 @param tag
		A <see cref="Tag" /> object to search for the specified
		tag in.
	 
	 @param ident
		A <see cref="ByteVector" /> object containing the frame
		identifer to search for.
	 
	 @return 
		A <see cref="TextInformationFrame" /> object containing
		the frame found in <paramref name="tag" /> or <see
		langword="null" /> if no value was found.
	 
	 @exception ArgumentNullException
		<paramref name="tag" /> or <paramref name="type" /> is
		<see langword="null" />.
	 
	 @exception ArgumentException
		<paramref name="type" /> is not exactly four bytes long.
	 
	*/
	@Deprecated
	public static TextInformationFrame Get(Tag tag, ByteVector ident)
	{
		return Get(tag, ident, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Populates the values in the current instance by parsing
		its field data in a specified version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the
		extracted field data.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		field data is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override void ParseFields(ByteVector data, byte version)
	@Override
	protected void ParseFields(ByteVector data, byte version)
	{
		raw_data = data;
		raw_version = version;
	}

	/** 
		Performs the actual parsing of the raw data.
	 
	 
		Because of the high parsing cost and relatively low usage
		of the class, <see cref="ParseFields" /> only stores the
		field data so it can be parsed on demand. Whenever a
		property or method is called which requires the data,
		this method is called, and only on the first call does it
		actually parse the data.
	 
	*/
	protected final void ParseRawData()
	{
		if (raw_data == null)
		{
			return;
		}

		ByteVector data = raw_data;
		raw_data = null;

		// read the string data type (the first byte of the
		// field data)
		encoding = StringType.forValue(data.get(0));
		ArrayList<String> field_list = new ArrayList<String> ();

		ByteVector delim = ByteVector.TextDelimiter(encoding);

		if (raw_version > 3 || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TXXX))
		{
			field_list.addAll(Arrays.asList(data.ToStrings(encoding, 1)));
		}
		else if (data.size() > 1 && !data.Mid(1, delim.size()).equals(delim))
		{
			String value = data.toString(encoding, 1, data.size() - 1);

			// Truncate values containing NULL bytes
			int null_index = value.indexOf('\u0000');
			if (null_index >= 0)
			{
				value = value.substring(0, null_index);
			}

			if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TCOM) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TEXT) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TOLY) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TOPE) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TPE1) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TPE2) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TPE3) || Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TPE4))
			{
				field_list.addAll(value.split("[/]", -1));
			}
			else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TCON))
			{
				while (value.length() > 1 && value.charAt (0) == '(')
				{
					int closing = value.indexOf(')');
					if (closing < 0)
					{
						break;
					}

					String number = value.substring(1, closing);

					field_list.add(number);

					value = tangible.StringHelper.trimStart(value.substring(closing + 1), '/', ' ');

					String text = Genres.IndexToAudio(number);
					if (text != null && value.startsWith(text))
					{
						value = tangible.StringHelper.trimStart(value.substring(text.length()), '/', ' ');
					}
				}

				if (value.length() > 0)
				{
					field_list.addAll(value.split("[/]", -1));
				}
			}
			else
			{
				field_list.add(value);
			}
		}

		// Bad tags may have one or more nul characters at the
		// end of a string, resulting in empty strings at the
		// end of the FieldList. Strip them off.
		while (!field_list.isEmpty() && tangible.StringHelper.isNullOrEmpty(field_list.get(field_list.size() - 1)))
		{
			field_list.remove(field_list.size() - 1);
		}

		text_fields = field_list.toArray(new String[0]);
	}

	/** 
		Renders the values in the current instance into field
		data for a specified version.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		field data is to be encoded in.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered field data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override ByteVector RenderFields(byte version)
	@Override
	protected ByteVector RenderFields(byte version)
	{
		if (raw_data != null && raw_version == version)
		{
			return raw_data;
		}

		StringType encoding = CorrectEncoding(getTextEncoding(), version);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector v = new ByteVector((byte) encoding);
		ByteVector v = new ByteVector((byte) encoding.getValue());
		String [] text = text_fields;

		boolean txxx = Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TXXX);

		if (version > 3 || txxx)
		{

			if (txxx)
			{
				if (text.length == 0)
				{
					text = new String [] {null, null};
				}
				else if (text.length == 1)
				{
					text = new String [] {text [0], null};
				}
			}

			for (int i = 0; i < text.length; i++)
			{
				// Since the field list is null
				// delimited, if this is not the first
				// element in the list, append the
				// appropriate delimiter for this
				// encoding.

				if (i != 0)
				{
					v.add(ByteVector.TextDelimiter(encoding));
				}

				if (text [i] != null)
				{
					v.add(ByteVector.FromString(text [i], encoding));
				}
			}
		}
		else if (Rasad.Core.Media.MediaMetadataManagement.ReadOnlyByteVector.OpEquality(getFrameId(), FrameType.TCON))
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte id;
			byte id;
			boolean prev_value_indexed = true;
			StringBuilder data = new StringBuilder();
			for (String s : text)
			{
				if (!prev_value_indexed)
				{
					data.append("/").append(s);
					continue;
				}

				tangible.OutObject<Byte> tempOut_id = new tangible.OutObject<Byte>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (prev_value_indexed = byte.TryParse(s, out id))
				if (prev_value_indexed = tangible.TryParseHelper.tryParseByte(s, tempOut_id))
				{
				id = tempOut_id.argValue;
					data.AppendFormat(CultureInfo.InvariantCulture, "({0})", id);
				}
				else
				{
				id = tempOut_id.argValue;
					data.append(s);
				}
			}

			v.add(ByteVector.FromString(data.toString(), encoding));
		}
		else
		{
			v.add(ByteVector.FromString(tangible.StringHelper.join("/", text), encoding));
		}

		return v;
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region ICloneable

	/** 
		Creates a deep copy of the current instance.
	 
	 @return 
		A new <see cref="Frame" /> object identical to the
		current instance.
	 
	*/
	@Override
	public Frame Clone()
	{
		TextInformationFrame frame = (this instanceof UserTextInformationFrame) ? new UserTextInformationFrame(null, encoding) : new TextInformationFrame(getFrameId(), encoding);
		frame.text_fields = (String[]) text_fields.clone();
		if (raw_data != null)
		{
			frame.raw_data = new ByteVector(raw_data);
		}
		frame.raw_version = raw_version;
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}