package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;
import java.io.*;

/** 
	This abstract class provides a basic framework for reading from
	and writing to a file, as well as accessing basic tagging and
	media properties.
 
 
	<p>This class is agnostic to all specific media types. Its
	child classes, on the other hand, support the the intricacies of
	different media and tagging formats. For example, <see
	cref="Mpeg4.File" /> supports the MPEG-4 specificication and
	Apple's tagging format.</p>
	<p>Each file type can be created using its format specific
	constructors, ie. <see cref="Mpeg4.File(string)" />, but the
	preferred method is to use <see
	cref="File.Create(string,string,ReadStyle)" /> or one of its
	variants, as it automatically detects the appropriate class from
	the file extension or provided mime-type.</p>
 
*/
public abstract class File implements Closeable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Enums

	/** 
	   Specifies the type of file access operations currently
	   permitted on an instance of <see cref="File" />.
	*/
	public enum AccessMode
	{
		/** 
			Read operations can be performed.
		*/
		Read,

		/** 
			Read and write operations can be performed.
		*/
		Write,

		/** 
			The file is closed for both read and write
			operations.
		*/
		Closed;

		public static final int SIZE = java.lang.Integer.SIZE;

		public int getValue()
		{
			return this.ordinal();
		}

		public static AccessMode forValue(int value)
		{
			return values()[value];
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Delegates

	/** 
		This delegate is used for intervening in <see
		cref="File.Create(string)" /> by resolving the file type
		before any standard resolution operations.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object representing the
		file to be read.
	 
	 @param mimetype
		A <see cref="string" /> object containing the mime-type
		of the file.
	 
	 @param style
		A <see cref="ReadStyle" /> value specifying how to read
		media properties from the file.
	 
	 @return 
		A new instance of <see cref="File" /> or <see
		langword="null" /> if the resolver could not match it.
	 
	 
		<p>A <see cref="FileTypeResolver" /> is one way of
		altering the behavior of <see cref="File.Create(string)" />
		.</p>
		<p>When <see cref="File.Create(string)" /> is called, the
		registered resolvers are invoked in the reverse order in
		which they were registered. The resolver may then perform
		any operations necessary, including other type-finding
		methods.</p>
		<p>If the resolver returns a new <see cref="File" />,
		it will instantly be returned, by <see
		cref="File.Create(string)" />. If it returns <see 
		langword="null" />, <see cref="File.Create(string)" /> will
		continue to process. If the resolver throws an exception
		it will be uncaught.</p>
		<p>To register a resolver, use <see
		cref="AddFileTypeResolver" />.</p>
	 
	*/
	@FunctionalInterface
	public interface FileTypeResolver
	{
		File invoke(IFileAbstraction abstraction, String mimetype, ReadStyle style);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Properties

	/** 
		Contains the current stream used in reading/writing.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private System.IO.Stream file_stream;

	/** 
		Contains the internal file abstraction.
	*/
	private IFileAbstraction file_abstraction;

	/** 
		Contains the mime-type of the file as provided by <see
		cref="Create(string)" />.
	*/
	private String mime_type;

	/** 
		Contains the types of tags in the file on disk.
	*/
	private TagTypes tags_on_disk = TagTypes.None;

	/** 
		Contains buffer size to use when reading.
	*/
	private static int buffer_size = 1024;

	/** 
		Contains the file type resolvers to use in <see
		cref="Create(string)" />.
	*/
	private static ArrayList<FileTypeResolver> file_type_resolvers = new ArrayList<FileTypeResolver> ();

	/** 
		Contains position at which the invariant data portion of
		the file begins.
	*/
	private long invariant_start_position = -1;

	/** 
		Contains position at which the invariant data portion of
		the file ends.
	*/
	private long invariant_end_position = -1;

	/** 
		The reasons (if any) why this file is marked as corrupt.
	*/
	private ArrayList<String> corruption_reasons = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Properties

	/** 
		The buffer size to use when reading large blocks of data
		in the <see cref="File" /> class.
	 
	 <value>
		A <see cref="uint" /> containing the buffer size to use
		when reading large blocks of data.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static uint getBufferSize()
	public static int getBufferSize()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint) buffer_size;
		return (int) buffer_size;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified path in the local file
		system.
	 
	 @param path
		A <see cref="string" /> object containing the path of the
		file to use in the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	protected File(String path)
	{
		if (path == null)
		{
			throw new NullPointerException("path");
		}

		file_abstraction = new LocalFileAbstraction(path);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="File" /> for a specified file abstraction.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading from and writing to the file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	protected File(IFileAbstraction abstraction)
	{
		if (abstraction == null)
		{
			throw new NullPointerException("abstraction");
		}

		file_abstraction = abstraction;
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
	 
		<p>This property provides generic and general access
		to the most common tagging features of a file. To access
		or add a specific type of tag in the file, use <see
		cref="GetTag(Rasad.Core.Media.MediaMetadataManagement.TagTypes,bool)" />.</p>
	 
	*/
	public abstract Tag getTag();

	/** 
		Gets the media properties of the file represented by the
		current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Properties" /> object containing the
		media properties of the file represented by the current
		instance.
	 </value>
	*/
	public abstract Properties getProperties();

	/** 
		Gets the tag types contained in the physical file
		represented by the current instance.
	 
	 <value>
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value
		containing the tag types stored in the physical file as
		it was read or last saved.
	 </value>
	*/
	public final TagTypes getTagTypesOnDisk()
	{
		return tags_on_disk;
	}
	protected final void setTagTypesOnDisk(TagTypes value)
	{
		tags_on_disk = value;
	}

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value
		containing the tag types stored in the current instance.
	 </value>
	*/
	public final TagTypes getTagTypes()
	{
		return getTag() != null ? getTag().getTagTypes() : TagTypes.None;
	}

	/** 
		Gets the name of the file as stored in its file
		abstraction.
	 
	 <value>
		A <see cref="string" /> object containing the name of the
		file as stored in the <see cref="IFileAbstraction" />
		object used to create it or the path if created with a
		local path.
	 </value>
	*/
	public final String getName()
	{
		return file_abstraction.getName();
	}

	/** 
		Gets the mime-type of the file as determined by <see
		cref="Create(IFileAbstraction,string,ReadStyle)" /> if
		that method was used to create the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the mime-type
		used to create the file or <see langword="null" /> if <see
		cref="Create(IFileAbstraction,string,ReadStyle)" /> was
		not used to create the current instance.
	 </value>
	*/
	public final String getMimeType()
	{
		return mime_type;
	}
	public final void setMimeType(String value)
	{
		mime_type = value;
	}

	/** 
		Gets the seek position in the internal stream used by the
		current instance.
	 
	 <value>
		A <see cref="long" /> value representing the seek
		position, or 0 if the file is not open for reading.
	 </value>
	*/
	public final long getTell()
	{
		return (getMode() == AccessMode.Closed) ? 0 : file_stream.Position;
	}

	/** 
		Gets the length of the file represented by the current
		instance.
	 
	 <value>
		A <see cref="long" /> value representing the size of the
		file, or 0 if the file is not open for reading.
	 </value>
	*/
	public final long getLength()
	{
		return (getMode() == AccessMode.Closed) ? 0 : file_stream.Length;
	}

	/** 
		Gets the position at which the invariant portion of the
		current instance begins.
	 
	 <value>
		A <see cref="long" /> value representing the seek
		position at which the file's invariant (media) data
		section begins. If the value could not be determined,
		<c>-1</c> is returned.
	 </value>
	*/
	public final long getInvariantStartPosition()
	{
		return invariant_start_position;
	}
	protected final void setInvariantStartPosition(long value)
	{
		invariant_start_position = value;
	}

	/** 
		Gets the position at which the invariant portion of the
		current instance ends.
	 
	 <value>
		A <see cref="long" /> value representing the seek
		position at which the file's invariant (media) data
		section ends. If the value could not be determined,
		<c>-1</c> is returned.
	 </value>
	*/
	public final long getInvariantEndPosition()
	{
		return invariant_end_position;
	}
	protected final void setInvariantEndPosition(long value)
	{
		invariant_end_position = value;
	}

	/** 
		Gets and sets the file access mode in use by the current
		instance.
	 
	 <value>
		A <see cref="AccessMode" /> value describing the features
		of stream currently in use by the current instance.
	 </value>
	 
		Changing the value will cause the stream currently in use
		to be closed, except when a change is made from <see
		cref="AccessMode.Write" /> to <see cref="AccessMode.Read"
		/> which has no effect.
	 
	*/
	public final AccessMode getMode()
	{
		return (file_stream == null) ? AccessMode.Closed : (file_stream.CanWrite) ? AccessMode.Write : AccessMode.Read;
	}
	public final void setMode(AccessMode value)
	{
		if (getMode() == value || (getMode() == AccessMode.Write && value == AccessMode.Read))
		{
			return;
		}

		if (file_stream != null)
		{
			file_abstraction.CloseStream(file_stream);
		}

		file_stream = null;

		if (value == AccessMode.Read)
		{
			file_stream = file_abstraction.getReadStream();
		}
		else if (value == AccessMode.Write)
		{
			file_stream = file_abstraction.getWriteStream();
		}

		setMode(value);
	}

	/** 
		Indicates if tags can be written back to the current file or not
	 
	 <value>
		A <see cref="bool" /> which is true if tags can be written to the
		current file, otherwise false.
	 </value>
	*/
	public boolean getWriteable()
	{
		return !getPossiblyCorrupt();
	}

	/** 
	   Indicates whether or not this file may be corrupt.
	 
	 <value>
	 <c>true</c> if possibly corrupt; otherwise, <c>false</c>.
	 </value>
	 
		Files with unknown corruptions should not be written.
	 
	*/
	public final boolean getPossiblyCorrupt()
	{
		return corruption_reasons != null;
	}

	/** 
	   The reasons for which this file is marked as corrupt.
	*/
	public final java.lang.Iterable<String> getCorruptionReasons()
	{
		return corruption_reasons;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		   Mark the file as corrupt.
	 
	 @param reason
		The reason why this file is considered to be corrupt.
	 
	*/
	public final void MarkAsCorrupt(String reason)
	{
		if (corruption_reasons == null)
		{
			corruption_reasons = new ArrayList<String> ();
		}
		corruption_reasons.add(reason);
	}

	/** 
		Dispose the current file. Equivalent to setting the
		mode to closed
	*/
	public final void close() throws IOException
	{
		setMode(AccessMode.Closed);
	}

	/** 
		Saves the changes made in the current instance to the
		file it represents.
	*/
	public abstract void Save();

	/** 
		Removes a set of tag types from the current instance.
	 
	 @param types
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value
		containing tag types to be removed from the file.
	 
	 
		In order to remove all tags from a file, pass <see
		cref="TagTypes.AllTags" /> as <paramref name="types" />.
	 
	*/
	public abstract void RemoveTags(TagTypes types);

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
	 
	 
		<p>Passing <see langword="true" /> to <paramref
		name="create" /> does not guarantee the tag will be
		created. For example, trying to create an ID3v2 tag on an
		OGG Vorbis file will always fail.</p>
		<p>It is safe to assume that if <see langword="null"
		/> is not returned, the returned tag can be cast to the
		appropriate type.</p>
	 
	 <example>
		<p>The following example sets the mood of a file to
		several tag types.</p>
		<code lang="C#">string [] SetMoods (Rasad.Core.Media.MediaMetadataManagement.File file, params string[] moods)
	{
	   Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag id3 = file.GetTag (Rasad.Core.Media.MediaMetadataManagement.TagTypes.Id3v2, true);
	   if (id3 != null)
		  id3.SetTextFrame ("TMOO", moods);
	   
	   Rasad.Core.Media.MediaMetadataManagement.Asf.Tag asf = file.GetTag (Rasad.Core.Media.MediaMetadataManagement.TagTypes.Asf, true);
	   if (asf != null)
		  asf.SetDescriptorStrings (moods, "WM/Mood", "Mood");
	   
	   Rasad.Core.Media.MediaMetadataManagement.Ape.Tag ape = file.GetTag (Rasad.Core.Media.MediaMetadataManagement.TagTypes.Ape);
	   if (ape != null)
		  ape.SetValue ("MOOD", moods);
		  
	   // Whatever tag types you want...
	}</code>
	 </example>
	*/
	public abstract Tag GetTag(TagTypes type, boolean create);

	/** 
		Gets a tag of a specified type from the current instance.
	 
	 @param type
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" /> value indicating the
		type of tag to read.
	 
	 @return 
		A <see cref="Tag" /> object containing the tag that was
		found in the current instance. If no matching tag
		was found, <see langword="null" /> is returned.
	 
	 
		<p>This class merely accesses the tag if it exists.
		<see cref="GetTag(TagTypes,bool)" /> provides the option
		of adding the tag to the current instance if it does not
		exist.</p>
		<p>It is safe to assume that if <see langword="null"
		/> is not returned, the returned tag can be cast to the
		appropriate type.</p>
	 
	 <example>
		<p>The following example reads the mood of a file from
		several tag types.</p>
		<code lang="C#">static string [] GetMoods (Rasad.Core.Media.MediaMetadataManagement.File file)
	{
	   Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag id3 = file.GetTag (Rasad.Core.Media.MediaMetadataManagement.TagTypes.Id3v2);
	   if (id3 != null) {
		  TextIdentificationFrame f = TextIdentificationFrame.Get (this, "TMOO");
		  if (f != null)
			 return f.FieldList.ToArray ();
	   }
	   
	   Rasad.Core.Media.MediaMetadataManagement.Asf.Tag asf = file.GetTag (Rasad.Core.Media.MediaMetadataManagement.TagTypes.Asf);
	   if (asf != null) {
		  string [] value = asf.GetDescriptorStrings ("WM/Mood", "Mood");
		  if (value.Length &gt; 0)
			 return value;
	   }
	   
	   Rasad.Core.Media.MediaMetadataManagement.Ape.Tag ape = file.GetTag (Rasad.Core.Media.MediaMetadataManagement.TagTypes.Ape);
	   if (ape != null) {
		  Item item = ape.GetItem ("MOOD");
		  if (item != null)
			 return item.ToStringArray ();
	   }
		  
	   // Whatever tag types you want...
	   
	   return new string [] {};
	}</code>
	 </example>
	*/
	public final Tag GetTag(TagTypes type)
	{
		return GetTag(type, false);
	}

	/** 
		Reads a specified number of bytes at the current seek
		position from the current instance.
	 
	 @param length
		A <see cref="int" /> value specifying the number of bytes
		to read.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the data
		read from the current instance.
	 
	 
		<p>This method reads the block of data at the current
		seek position. To change the seek position, use <see
		cref="Seek(long,System.IO.SeekOrigin)" />.</p>
	 
	 @exception ArgumentException
		<paramref name="length" /> is less than zero.
	 
	*/
	public final ByteVector ReadBlock(int length)
	{
		if (length < 0)
		{
			throw new IllegalArgumentException("Length must be non-negative", "length");
		}

		if (length == 0)
		{
			return new ByteVector();
		}

		setMode(AccessMode.Read);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] buffer = new byte [length];
		byte [] buffer = new byte [length];

		int count = 0, read = 0, needed = length;

		do
		{
			count = file_stream.Read(buffer, read, needed);

			read += count;
			needed -= count;
		} while (needed > 0 && count != 0);

		return new ByteVector(buffer, (byte)read);
	}

	/** 
		Writes a block of data to the file represented by the
		current instance at the current seek position.
	 
	 @param data
		A <see cref="ByteVector" /> object containing data to be
		written to the current instance.
	 
	 
		This will overwrite any existing data at the seek
		position and append new data to the file if writing past
		the current end.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
	public final void WriteBlock(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		setMode(AccessMode.Write);

		file_stream.Write(data.getData(), 0, data.size());
	}

	/** 
		Searches forwards through a file for a specified
		pattern, starting at a specified offset.
	 
	 @param pattern
		A <see cref="ByteVector" /> object containing a pattern
		to search for in the current instance.
	 
	 @param startPosition
		A <see cref="int" /> value specifying at what
		seek position to start searching.
	 
	 @param before
		A <see cref="ByteVector" /> object specifying a pattern
		that the searched for pattern must appear before. If this
		pattern is found first, -1 is returned.
	 
	 @return 
		A <see cref="long" /> value containing the index at which
		the value was found. If not found, -1 is returned.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final long Find(ByteVector pattern, long startPosition, ByteVector before)
	{
		if (pattern == null)
		{
			throw new NullPointerException("pattern");
		}

		setMode(AccessMode.Read);

		if (pattern.size() > buffer_size)
		{
			return -1;
		}

		// The position in the file that the current buffer
		// starts at.

		long buffer_offset = startPosition;
		long original_position = file_stream.Position;

		try
		{
			// Start the search at the offset.
			file_stream.Position = startPosition;
			for (Rasad.Core.Media.MediaMetadataManagement.ByteVector buffer = ReadBlock(buffer_size); buffer.size() > 0; buffer = ReadBlock(buffer_size))
			{
				int location = tangible.ListHelper.find(buffer, pattern);
				if (before != null)
				{
					int beforeLocation = tangible.ListHelper.find(buffer, before);
					if (beforeLocation < location)
					{
						return -1;
					}
				}

				if (location >= 0)
				{
					return buffer_offset + location;
				}

				// Ensure that we always rewind the stream a little so we never have a partial
				// match where our data exists between the end of read A and the start of read B.
				buffer_offset += buffer_size - pattern.size();
				if (before != null && before.size() > pattern.size())
				{
					buffer_offset -= before.size() - pattern.size();
				}
				file_stream.Position = buffer_offset;
			}

			return -1;
		}
		finally
		{
			file_stream.Position = original_position;
		}
	}

	/** 
		Searches forwards through a file for a specified
		pattern, starting at a specified offset.
	 
	 @param pattern
		A <see cref="ByteVector" /> object containing a pattern
		to search for in the current instance.
	 
	 @param startPosition
		A <see cref="int" /> value specifying at what
		seek position to start searching.
	 
	 @return 
		A <see cref="long" /> value containing the index at which
		the value was found. If not found, -1 is returned.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final long Find(ByteVector pattern, long startPosition)
	{
		return Find(pattern, startPosition, null);
	}

	/** 
		Searches forwards through a file for a specified
		pattern, starting at the beginning of the file.
	 
	 @param pattern
		A <see cref="ByteVector" /> object containing a pattern
		to search for in the current instance.
	 
	 @return 
		A <see cref="long" /> value containing the index at which
		the value was found. If not found, -1 is returned.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final long Find(ByteVector pattern)
	{
		return Find(pattern, 0);
	}

	/** 
		Searches backwards through a file for a specified
		pattern, starting at a specified offset.
	 
	 @param pattern
		A <see cref="ByteVector" /> object containing a pattern
		to search for in the current instance.
	 
	 @param startPosition
		A <see cref="int" /> value specifying at what
		seek position to start searching.
	 
	 @param after
		A <see cref="ByteVector" /> object specifying a pattern
		that the searched for pattern must appear after. If this
		pattern is found first, -1 is returned.
	 
	 @return 
		A <see cref="long" /> value containing the index at which
		the value was found. If not found, -1 is returned.
	 
	 
		Searching for <paramref name="after" /> is not yet
		implemented.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	private long RFind(ByteVector pattern, long startPosition, ByteVector after)
	{
		if (pattern == null)
		{
			throw new NullPointerException("pattern");
		}

		setMode(AccessMode.Read);

		if (pattern.size() > buffer_size)
		{
			return -1;
		}

		// The position in the file that the current buffer
		// starts at.

		ByteVector buffer;

		// These variables are used to keep track of a partial
		// match that happens at the end of a buffer.

		/*
		int previous_partial_match = -1;
		int after_previous_partial_match = -1;
		*/

		// Save the location of the current read pointer.  We
		// will restore the position using Seek() before all 
		// returns.

		long original_position = file_stream.Position;

		// Start the search at the offset.

		long buffer_offset = getLength() - startPosition;
		int read_size = buffer_size;

		read_size = (int) Math.min(buffer_offset, buffer_size);
		buffer_offset -= read_size;
		file_stream.Position = buffer_offset;

		// See the notes in find() for an explanation of this
		// algorithm.

		for (buffer = ReadBlock(read_size); buffer.size() > 0; buffer = ReadBlock(read_size))
		{

			// TODO: (1) previous partial match

			// (2) pattern contained in current buffer

			long location = buffer.RFind(pattern);
			if (location >= 0)
			{
				file_stream.Position = original_position;
				return buffer_offset + location;
			}

			if (after != null && buffer.RFind(after) >= 0)
			{
				file_stream.Position = original_position;
				return -1;
			}

			read_size = (int) Math.min(buffer_offset, buffer_size);
			buffer_offset -= read_size;
			if (read_size + pattern.size() > buffer_size)
			{
				buffer_offset += pattern.size();
			}

			file_stream.Position = buffer_offset;
		}

		// Since we hit the end of the file, reset the status
		// before continuing.

		file_stream.Position = original_position;
		return -1;
	}

	/** 
		Searches backwards through a file for a specified
		pattern, starting at a specified offset.
	 
	 @param pattern
		A <see cref="ByteVector" /> object containing a pattern
		to search for in the current instance.
	 
	 @param startPosition
		A <see cref="int" /> value specifying at what
		seek position to start searching.
	 
	 @return 
		A <see cref="long" /> value containing the index at which
		the value was found. If not found, -1 is returned.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final long RFind(ByteVector pattern, long startPosition)
	{
		return RFind(pattern, startPosition, null);
	}

	/** 
		Searches backwards through a file for a specified
		pattern, starting at the end of the file.
	 
	 @param pattern
		A <see cref="ByteVector" /> object containing a pattern
		to search for in the current instance.
	 
	 @return 
		A <see cref="long" /> value containing the index at which
		the value was found. If not found, -1 is returned.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final long RFind(ByteVector pattern)
	{
		return RFind(pattern, 0);
	}

	/** 
		Inserts a specifed block of data into the file repesented
		by the current instance at a specified location,
		replacing a specified number of bytes.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the data to
		insert into the file.
	 
	 @param start
		A <see cref="long" /> value specifying at which point to
		insert the data.
	 
	 @param replace
		A <see cref="long" /> value specifying the number of
		bytes to replace. Typically this is the original size of
		the data block so that a new block will replace the old
		one.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
	public final void Insert(ByteVector data, long start, long replace)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		setMode(AccessMode.Write);

		if (data.size() == replace)
		{
			file_stream.Position = start;
			WriteBlock(data);
			return;
		}
		else if (data.size() < replace)
		{
			file_stream.Position = start;
			WriteBlock(data);
			RemoveBlock(start + data.size(), replace - data.size());
			return;
		}

		// Woohoo!  Faster (about 20%) than id3lib at last. I
		// had to get hardcore and avoid Rasad.Core.Media.MediaMetadataManagement's high level API
		// for rendering just copying parts of the file that
		// don't contain tag data.
		//
		// Now I'll explain the steps in this ugliness:

		// First, make sure that we're working with a buffer
		// that is longer than the *differnce* in the tag sizes.
		// We want to avoid overwriting parts that aren't yet in
		// memory, so this is necessary.

		int buffer_length = buffer_size;

		while (data.size() - replace > buffer_length)
		{
			buffer_length += (int) getBufferSize();
		}

		// Set where to start the reading and writing.

		long read_position = start + replace;
		long write_position = start;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] buffer;
		byte [] buffer;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] about_to_overwrite;
		byte [] about_to_overwrite;

		// This is basically a special case of the loop below.  
		// Here we're just doing the same steps as below, but 
		// since we aren't using the same buffer size -- instead
		// we're using the tag size -- this has to be handled as
		// a special case.  We're also using File::writeBlock()
		// just for the tag. That's a bit slower than using char
		// *'s so, we're only doing it here.

		file_stream.Position = read_position;
		about_to_overwrite = ReadBlock(buffer_length).getData();
		read_position += buffer_length;

		file_stream.Position = write_position;
		WriteBlock(data);
		write_position += data.size();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer = new byte [about_to_overwrite.Length];
		buffer = new byte [about_to_overwrite.length];
		System.arraycopy(about_to_overwrite, 0, buffer, 0, about_to_overwrite.length);

		// Ok, here's the main loop.  We want to loop until the
		// read fails, which means that we hit the end of the 
		// file.

		while (buffer_length != 0)
		{
			// Seek to the current read position and read
			// the data that we're about to overwrite. 
			// Appropriately increment the readPosition.

			file_stream.Position = read_position;
			int bytes_read = file_stream.Read(about_to_overwrite, 0, buffer_length < about_to_overwrite.length ? buffer_length : about_to_overwrite.length);
			read_position += buffer_length;

			// Seek to the write position and write our
			// buffer. Increment the writePosition.

			file_stream.Position = write_position;
			file_stream.Write(buffer, 0, buffer_length < buffer.length ? buffer_length : buffer.length);
			write_position += buffer_length;

			// Make the current buffer the data that we read
			// in the beginning.

			System.arraycopy(about_to_overwrite, 0, buffer, 0, bytes_read);

			// Again, we need this for the last write.  We
			// don't want to write garbage at the end of our
			// file, so we need to set the buffer size to
			// the amount that we actually read.

			buffer_length = bytes_read;
		}
	}

	/** 
		Inserts a specifed block of data into the file repesented
		by the current instance at a specified location.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the data to
		insert into the file.
	 
	 @param start
		A <see cref="long" /> value specifying at which point to
		insert the data.
	 
	 
		This method inserts a new block of data into the file. To
		replace an existing block, ie. replacing an existing
		tag with a new one of different size, use <see
		cref="Insert(ByteVector,long,long)" />.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
	public final void Insert(ByteVector data, long start)
	{
		Insert(data, start, 0);
	}

	/** 
		Removes a specified block of data from the file
		represented by the current instance.
	 
	 @param start
		A <see cref="long" /> value specifying at which point to
		remove data.
	 
	 @param length
		A <see cref="long" /> value specifying the number of
		bytes to remove.
	 
	*/
	public final void RemoveBlock(long start, long length)
	{
		if (length <= 0)
		{
			return;
		}

		setMode(AccessMode.Write);

		int buffer_length = buffer_size;

		long read_position = start + length;
		long write_position = start;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector buffer = (byte) 1;
		ByteVector buffer = (byte) 1;

		while (buffer.size() != 0)
		{
			file_stream.Position = read_position;
			buffer = ReadBlock(buffer_length);
			read_position += buffer.size();

			file_stream.Position = write_position;
			WriteBlock(buffer);
			write_position += buffer.size();
		}

		Truncate(write_position);
	}

	/** 
		Seeks the read/write pointer to a specified offset in the
		current instance, relative to a specified origin.
	 
	 @param offset
		A <see cref="long" /> value indicating the byte offset to
		seek to.
	 
	 @param origin
		A <see cref="System.IO.SeekOrigin" /> value specifying an
		origin to seek from.
	 
	*/
	public final void Seek(long offset, System.IO.SeekOrigin origin)
	{
		if (getMode() != AccessMode.Closed)
		{
			file_stream.Seek(offset, origin);
		}
	}

	/** 
		Seeks the read/write pointer to a specified offset in the
		current instance, relative to the beginning of the file.
	 
	 @param offset
		A <see cref="long" /> value indicating the byte offset to
		seek to.
	 
	*/
	public final void Seek(long offset)
	{
		Seek(offset, System.IO.SeekOrigin.Begin);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Creates a new instance of a <see cref="File" /> subclass
		for a specified path, guessing the mime-type from the
		file's extension and using the average read style.
	 
	 @param path
		A <see cref="string" /> object specifying the file to
		read from and write to.
	 
	 @return 
		A new instance of <see cref="File" /> as read from the
		specified path.
	 
	 @exception CorruptFileException
		The file could not be read due to corruption.
	 
	 @exception UnsupportedFormatException
		The file could not be read because the mime-type could
		not be resolved or the library does not support an
		internal feature of the file crucial to its reading.
	 
	*/
	public static File Create(String path)
	{
		return Create(path, null, ReadStyle.Average);
	}

	/** 
		Creates a new instance of a <see cref="File" /> subclass
		for a specified file abstraction, guessing the mime-type
		from the file's extension and using the average read
		style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading to and writing from the current instance.
	 
	 @return 
		A new instance of <see cref="File" /> as read from the
		specified abstraction.
	 
	 @exception CorruptFileException
		The file could not be read due to corruption.
	 
	 @exception UnsupportedFormatException
		The file could not be read because the mime-type could
		not be resolved or the library does not support an
		internal feature of the file crucial to its reading.
	 
	*/
	public static File Create(IFileAbstraction abstraction)
	{
		return Create(abstraction, null, ReadStyle.Average);
	}

	/** 
		Creates a new instance of a <see cref="File" /> subclass
		for a specified path and read style, guessing the
		mime-type from the file's extension.
	 
	 @param path
		A <see cref="string" /> object specifying the file to
		read from and write to.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying the level of
		detail to use when reading the media information from the
		new instance.
	 
	 @return 
		A new instance of <see cref="File" /> as read from the
		specified path.
	 
	 @exception CorruptFileException
		The file could not be read due to corruption.
	 
	 @exception UnsupportedFormatException
		The file could not be read because the mime-type could
		not be resolved or the library does not support an
		internal feature of the file crucial to its reading.
	 
	*/
	public static File Create(String path, ReadStyle propertiesStyle)
	{
		return Create(path, null, propertiesStyle);
	}

	/** 
		Creates a new instance of a <see cref="File" /> subclass
		for a specified file abstraction and read style, guessing
		the mime-type from the file's extension.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading to and writing from the current instance.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying the level of
		detail to use when reading the media information from the
		new instance.
	 
	 @return 
		A new instance of <see cref="File" /> as read from the
		specified abstraction.
	 
	 @exception CorruptFileException
		The file could not be read due to corruption.
	 
	 @exception UnsupportedFormatException
		The file could not be read because the mime-type could
		not be resolved or the library does not support an
		internal feature of the file crucial to its reading.
	 
	*/
	public static File Create(IFileAbstraction abstraction, ReadStyle propertiesStyle)
	{
		return Create(abstraction, null, propertiesStyle);
	}

	/** 
		Creates a new instance of a <see cref="File" /> subclass
		for a specified path, mime-type, and read style.
	 
	 @param path
		A <see cref="string" /> object specifying the file to
		read from and write to.
	 
	 @param mimetype
		A <see cref="string" /> object containing the mime-type
		to use when selecting the appropriate class to use, or
		<see langword="null" /> if the extension in <paramref
		name="abstraction" /> is to be used.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying the level of
		detail to use when reading the media information from the
		new instance.
	 
	 @return 
		A new instance of <see cref="File" /> as read from the
		specified path.
	 
	 @exception CorruptFileException
		The file could not be read due to corruption.
	 
	 @exception UnsupportedFormatException
		The file could not be read because the mime-type could
		not be resolved or the library does not support an
		internal feature of the file crucial to its reading.
	 
	*/
	public static File Create(String path, String mimetype, ReadStyle propertiesStyle)
	{
		return Create(new LocalFileAbstraction(path), mimetype, propertiesStyle);
	}

	/** 
		Creates a new instance of a <see cref="File" /> subclass
		for a specified file abstraction, mime-type, and read
		style.
	 
	 @param abstraction
		A <see cref="IFileAbstraction" /> object to use when
		reading to and writing from the current instance.
	 
	 @param mimetype
		A <see cref="string" /> object containing the mime-type
		to use when selecting the appropriate class to use, or
		<see langword="null" /> if the extension in <paramref
		name="abstraction" /> is to be used.
	 
	 @param propertiesStyle
		A <see cref="ReadStyle" /> value specifying the level of
		detail to use when reading the media information from the
		new instance.
	 
	 @return 
		A new instance of <see cref="File" /> as read from the
		specified abstraction.
	 
	 @exception CorruptFileException
		The file could not be read due to corruption.
	 
	 @exception UnsupportedFormatException
		The file could not be read because the mime-type could
		not be resolved or the library does not support an
		internal feature of the file crucial to its reading.
	 
	*/
	public static File Create(IFileAbstraction abstraction, String mimetype, ReadStyle propertiesStyle)
	{
		if (mimetype == null)
		{
			String ext = "";

			int index = abstraction.getName().lastIndexOf(".") + 1;

			if (index >= 1 && index < abstraction.getName().length())
			{
				ext = abstraction.getName().substring(index, abstraction.getName().length());
			}

			mimetype = "taglib/" + ext.toLowerCase(Locale.ROOT);
		}

		for (FileTypeResolver resolver : file_type_resolvers)
		{
			File file = resolver.invoke(abstraction, mimetype, propertiesStyle);

			if (file != null)
			{
				return file;
			}
		}

		if (!FileTypes.getAvailableTypes().containsKey(mimetype))
		{
			throw new UnsupportedFormatException(String.format(CultureInfo.InvariantCulture, "%1$s (%2$s)", abstraction.getName(), mimetype));
		}

		java.lang.Class file_type = FileTypes.getAvailableTypes().get(mimetype);

		try
		{
			File file = (File) System.Activator.CreateInstance(file_type, new Object [] {abstraction, propertiesStyle});

			file.setMimeType(mimetype);
			return file;
		}
		catch (System.Reflection.TargetInvocationException e)
		{
			PrepareExceptionForRethrow(e.getCause());
			throw e.getCause();
		}
	}

	/** 
		Adds a <see cref="FileTypeResolver" /> to the <see
		cref="File" /> class. The one added last gets run first.
	 
	 @param resolver
		A <see cref="FileTypeResolver" /> delegate to add to the
		file type recognition stack.
	 
	 
		A <see cref="FileTypeResolver" /> adds support for 
		recognizing a file type outside of the standard mime-type
		methods.
	 
	*/
	public static void AddFileTypeResolver(FileTypeResolver resolver)
	{
		if (resolver != null)
		{
			file_type_resolvers.add(0, resolver);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Resized the current instance to a specified number of
		bytes.
	 
	 @param length
		A <see cref="long" /> value specifying the number of
		bytes to resize the file to.
	 
	*/
	protected final void Truncate(long length)
	{
		AccessMode old_mode = getMode();
		setMode(AccessMode.Write);
		file_stream.SetLength(length);
		setMode(old_mode);
	}

	/** 
	 Causes the original strack trace of the exception to be preserved when it is rethrown
	 
	 @param ex
	*/
	private static void PrepareExceptionForRethrow(RuntimeException ex)
	{
		StreamingContext ctx = new StreamingContext(StreamingContextStates.CrossAppDomain);
		ObjectManager mgr = new ObjectManager(null, ctx);
		SerializationInfo si = new SerializationInfo(ex.getClass(), new FormatterConverter());

		ex.GetObjectData(si, ctx);
		mgr.RegisterObject(ex, 1, si); // prepare for SetObjectData
		mgr.DoFixups(); // ObjectManager calls SetObjectData
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Classes

	/** 
		This class implements <see cref="IFileAbstraction" />
		to provide support for accessing the local/standard file
		system.
	 
	 
		This class is used as the standard file abstraction
		throughout the library.
	 
	*/
	public static class LocalFileAbstraction implements IFileAbstraction
	{
		/** 
			Contains the name used to open the file.
		*/
		private String name;

		/** 
			Constructs and initializes a new instance of
			<see cref="LocalFileAbstraction" /> for a
			specified path in the local file system.
		 
		 @param path
			A <see cref="string" /> object containing the
			path of the file to use in the new instance.
		 
		 @exception ArgumentNullException
			<paramref name="path" /> is <see langword="null"
			/>.
		 
		*/
		public LocalFileAbstraction(String path)
		{
			if (path == null)
			{
				throw new NullPointerException("path");
			}

			name = path;
		}

		/** 
			Gets the path of the file represented by the
			current instance.
		 
		 <value>
			A <see cref="string" /> object containing the
			path of the file represented by the current
			instance.
		 </value>
		*/
		public final String getName()
		{
			return name;
		}

		/** 
			Gets a new readable, seekable stream from the
			file represented by the current instance.
		 
		 <value>
			A new <see cref="System.IO.Stream" /> to be used
			when reading the file represented by the current
			instance.
		 </value>
		*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		public final System.IO.Stream getReadStream()
		{
			return System.IO.File.Open(getName(), System.IO.FileMode.Open, System.IO.FileAccess.Read, System.IO.FileShare.Read);
		}

		/** 
			Gets a new writable, seekable stream from the
			file represented by the current instance.
		 
		 <value>
			A new <see cref="System.IO.Stream" /> to be used
			when writing to the file represented by the
			current instance.
		 </value>
		*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		public final System.IO.Stream getWriteStream()
		{
			return System.IO.File.Open(getName(), System.IO.FileMode.Open, System.IO.FileAccess.ReadWrite);
		}

		/** 
			Closes a stream created by the current instance.
		 
		 @param stream
			A <see cref="System.IO.Stream" /> object
			created by the current instance.
		 
		*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		public final void CloseStream(System.IO.Stream stream)
		{
			if (stream == null)
			{
				throw new NullPointerException("stream");
			}

			stream.Close();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Interfaces

	/** 
		This interface provides abstracted access to a file. It
	*/
	//     premits access to non-standard file systems and data
	/**    retrieval methods.
	 
	 
		<p>To use a custom abstraction, use <see
		cref="Create(IFileAbstraction)" /> instead of <see
		cref="Create(string)" /> when creating files.</p>
	 
	 <example>
		<p>The following example uses Gnome VFS to open a file
		and read its title.</p>
	 <code lang="C#">using Rasad.Core.Media.MediaMetadataManagement;
	using Gnome.Vfs;
	
	public class ReadTitle
	{
	   public static void Main (string [] args)
	   {
		  if (args.Length != 1)
			 return;
	
		  Gnome.Vfs.Vfs.Initialize ();
		  
		  try {
			  Rasad.Core.Media.MediaMetadataManagement.File file = Rasad.Core.Media.MediaMetadataManagement.File.Create (
				 new VfsFileAbstraction (args [0]));
			  System.Console.WriteLine (file.Tag.Title);
		  } finally {
			 Vfs.Shutdown()
		  }
	   }
	}