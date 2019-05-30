package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
	This class extends <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> to provide tagging
	and properties support for Matroska files.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [SupportedMimeType("taglib/mkv", "mkv")][SupportedMimeType("taglib/mka", "mka")][SupportedMimeType("taglib/mks", "mks")][SupportedMimeType("video/webm")][SupportedMimeType("video/x-matroska")] public class File : Rasad.Core.Media.MediaMetadataManagement.File
public class File extends Rasad.Core.Media.MediaMetadataManagement.File
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
	   Contains the tags for the file.
	*/
	private Matroska.Tag tag = new Matroska.Tag();

	/** 
		Contains the media properties.
	*/
	private Properties properties;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private UInt64 duration_unscaled;
	private long duration_unscaled;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint time_scale;
	private int time_scale;

	private TimeSpan duration = new TimeSpan();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning disable 414 // Assigned, never used
	private String title;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning restore 414

	private ArrayList<Track> tracks = new ArrayList<Track> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system and specified read style.
	 
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
	public File(String path, ReadStyle propertiesStyle)
	{
		this(new File.LocalFileAbstraction(path), propertiesStyle);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system with an average read style.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public File(String path)
	{
		this(path, ReadStyle.Average);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction and
		specified read style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public File(File.IFileAbstraction abstraction, ReadStyle propertiesStyle)
	{
		super(abstraction);
		Mode = AccessMode.Read;
		try
		{
			Read(propertiesStyle);
			TagTypesOnDisk = TagTypes;
		}
		finally
		{
			Mode = AccessMode.Closed;
		}

		ArrayList<ICodec> codecs = new ArrayList<ICodec> ();

		for (Track track : tracks)
		{
			codecs.add(track);
		}

		properties = new Properties(duration, codecs);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction with an
		average read style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public File(File.IFileAbstraction abstraction)
	{
		this(abstraction, ReadStyle.Average);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Saves the changes made in the current instance to the
		file it represents.
	*/
	@Override
	public void Save()
	{
		Mode = AccessMode.Write;
		try
		{

		}
		finally
		{
			Mode = AccessMode.Closed;
		}
	}

	/** 
		Removes a set of tag types from the current instance.
	 
	 @param types
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value
		containing tag types to be removed from the file.
	 
	 
		In order to remove all tags from a file, pass <see
		cref="TagTypes.AllTags" /> as <paramref name="types" />.
	 
	*/
	@Override
	public void RemoveTags(Rasad.Core.Media.MediaMetadataManagement.TagTypes types)
	{

	}

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
	 
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag GetTag(Rasad.Core.Media.MediaMetadataManagement.TagTypes type, boolean create)
	{

		return null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets a abstract representation of all tags stored in the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Tag" /> object representing all tags
		stored in the current instance.
	 </value>
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Tag getTag()
	{
		return tag;
	}

	/** 
		Gets the media properties of the file represented by the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Properties" /> object containing the
		media properties of the file represented by the current
		instance.
	 </value>
	*/
	@Override
	public Rasad.Core.Media.MediaMetadataManagement.Properties getProperties()
	{
		return properties;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Reads the file with a specified read style.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying at what level
		of accuracy to read the media properties, or <see
		cref="ReadStyle.None" /> to ignore the properties.
	 
	*/
	private void Read(ReadStyle propertiesStyle)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong offset = 0;
		long offset = 0;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: while (offset < (ulong) Length)
		while (offset < (long) Length)
		{
			EBMLElement element = new EBMLElement(this, offset);

			EBMLID ebml_id = EBMLID.forValue(element.getID());
			MatroskaID matroska_id = MatroskaID.forValue(element.getID());

			switch (ebml_id)
			{
				case EBMLHeader:
					ReadHeader(element);
					break;
				default:
					break;
			}
			switch (matroska_id)
			{
				case MatroskaSegment:
					ReadSegment(element);
					break;
				default:
					break;
			}

			offset += element.getSize();
		}
	}

	private void ReadHeader(EBMLElement element)
	{
		String doctype = null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
		long i = 0;

		while (i < element.getDataSize())
		{
			EBMLElement child = new EBMLElement(this, element.getDataOffset() + i);

			EBMLID ebml_id = EBMLID.forValue(child.getID());

			switch (ebml_id)
			{
				case EBMLDocType:
					doctype = child.ReadString();
					break;
				default:
					break;
			}

			i += child.getSize();
		}

		// Check DocType
		if (tangible.StringHelper.isNullOrEmpty(doctype) || (!doctype.equals("matroska") && !doctype.equals("webm")))
		{
			throw new UnsupportedFormatException("DocType is not matroska or webm");
		}
	}

	private void ReadSegment(EBMLElement element)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
		long i = 0;

		while (i < element.getDataSize())
		{
			EBMLElement child = new EBMLElement(this, element.getDataOffset() + i);

			MatroskaID matroska_id = MatroskaID.forValue(child.getID());

			switch (matroska_id)
			{
				case MatroskaTracks:
					ReadTracks(child);
					break;
				case MatroskaSegmentInfo:
					ReadSegmentInfo(child);
					break;
				case MatroskaTags:
					ReadTags(child);
					break;
				case MatroskaCluster:
					// Get out of here when we reach the clusters for now.
					return;
				default:
					break;
			}

			i += child.getSize();
		}
	}

	private void ReadTags(EBMLElement element)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
		long i = 0;

		while (i < element.getDataSize())
		{
			EBMLElement child = new EBMLElement(this, element.getDataOffset() + i);

			MatroskaID matroska_id = MatroskaID.forValue(child.getID());

			switch (matroska_id)
			{
				case MatroskaTag:
					ReadTag(child);
					break;
				default:
					break;
			}

			i += child.getSize();
		}
	}

	private void ReadTag(EBMLElement element)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
		long i = 0;

		while (i < element.getDataSize())
		{
			EBMLElement child = new EBMLElement(this, element.getDataOffset() + i);

			MatroskaID matroska_id = MatroskaID.forValue(child.getID());

			switch (matroska_id)
			{
				case MatroskaSimpleTag:
					ReadSimpleTag(child);
					break;
				default:
					break;
			}

			i += child.getSize();
		}
	}

	private void ReadSimpleTag(EBMLElement element)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
		long i = 0;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning disable 219 // Assigned, never read
		String tag_name = null, tag_language = null, tag_string = null;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning restore 219

		while (i < element.getDataSize())
		{
			EBMLElement child = new EBMLElement(this, element.getDataOffset() + i);

			MatroskaID matroska_id = MatroskaID.forValue(child.getID());

			switch (matroska_id)
			{
				case MatroskaTagName:
					tag_name = child.ReadString();
					break;
				case MatroskaTagLanguage:
					tag_language = child.ReadString();
					break;
				case MatroskaTagString:
					tag_string = child.ReadString();
					break;
				default:
					break;
			}

			i += child.getSize();
		}

		if (tag_name.equals("AUTHOR"))
		{
			tag.setPerformers(new String [] {tag_string});
		}
		else if (tag_name.equals("TITLE"))
		{
			tag.setTitle(tag_string);
		}
		else if (tag_name.equals("ALBUM"))
		{
			tag.setAlbum(tag_string);
		}
		else if (tag_name.equals("COMMENTS"))
		{
			tag.setComment(tag_string);
		}
	}

	private void ReadSegmentInfo(EBMLElement element)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
		long i = 0;

		while (i < element.getDataSize())
		{
			EBMLElement child = new EBMLElement(this, element.getDataOffset() + i);

			MatroskaID matroska_id = MatroskaID.forValue(child.getID());

			switch (matroska_id)
			{
				case MatroskaDuration:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: duration_unscaled = (UInt64) child.ReadDouble();
					duration_unscaled = (long) child.ReadDouble();
					if (time_scale > 0)
					{
						duration = TimeSpan.FromSeconds(duration_unscaled * time_scale / 1000000000);
					}
					break;
				case MatroskaTimeCodeScale:
					time_scale = child.ReadUInt();
					if (duration_unscaled > 0)
					{
						duration = TimeSpan.FromSeconds(duration_unscaled * time_scale / 1000000000);
					}
					break;
				case MatroskaTitle:
					title = child.ReadString();
					break;
				default:
					break;
			}

			i += child.getSize();
		}
	}

	private void ReadTracks(EBMLElement element)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
		long i = 0;

		while (i < element.getDataSize())
		{
			EBMLElement child = new EBMLElement(this, element.getDataOffset() + i);

			MatroskaID matroska_id = MatroskaID.forValue(child.getID());

			switch (matroska_id)
			{
				case MatroskaTrackEntry:
					ReadTrackEntry(child);
					break;
				default:
					break;
			}

			i += child.getSize();
		}
	}

	private void ReadTrackEntry(EBMLElement element)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
		long i = 0;

		while (i < element.getDataSize())
		{
			EBMLElement child = new EBMLElement(this, element.getDataOffset() + i);

			MatroskaID matroska_id = MatroskaID.forValue(child.getID());

			switch (matroska_id)
			{
				case MatroskaTrackType:
				{
						TrackType track_type = TrackType.forValue(child.ReadUInt());

						switch (track_type)
						{
							case Video:
							{
									VideoTrack track = new VideoTrack(this, element);

									tracks.add(track);
									break;
							}
							case Audio:
							{
									AudioTrack track = new AudioTrack(this, element);

									tracks.add(track);
									break;
							}
							case Subtitle:
							{
									SubtitleTrack track = new SubtitleTrack(this, element);

									tracks.add(track);
									break;
							}
							default:
								break;
						}
						break;
				}
				default:
					break;
			}

			i += child.getSize();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Properties

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}