package Rasad.Core.Compression.Tar;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.nio.file.*;

/** 
 The TarArchive class implements the concept of a
 'Tape Archive'. A tar archive is a series of entries, each of
 which represents a file system object. Each entry in
 the archive consists of a header block followed by 0 or more data blocks.
 Directory entries consist only of the header block, and are followed by entries
 for the directory's contents. File entries consist of a
 header followed by the number of blocks needed to
 contain the file's contents. All entries are written on
 block boundaries. Blocks are 512 bytes long.
 
 TarArchives are instantiated in either read or write mode,
 based upon whether they are instantiated with an InputStream
 or an OutputStream. Once instantiated TarArchives read/write
 mode can not be changed.
 
 There is currently no support for random access to tar archives.
 However, it seems that subclassing TarArchive, and using the
 TarBuffer.CurrentRecord and TarBuffer.CurrentBlock
 properties, this would be rather trivial.
*/
public class TarArchive implements Closeable
{
	/** 
	 Client hook allowing detailed information to be reported during processing
	*/
	public tangible.Event<ProgressMessageHandler> ProgressMessageEvent = new tangible.Event<ProgressMessageHandler>();

	/** 
	 Raises the ProgressMessage event
	 
	 @param entry The <see cref="TarEntry">TarEntry</see> for this event
	 @param message message for this event.  Null is no message
	*/
	protected void OnProgressMessageEvent(TarEntry entry, String message)
	{
		ProgressMessageHandler handler = (TarArchive archive, TarEntry entry, String message) -> ProgressMessageEvent.invoke(archive, entry, message);
		if (handler != null)
		{
			handler.invoke(this, entry, message);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Constructor for a default <see cref="TarArchive"/>.
	*/
	protected TarArchive()
	{
	}

	/** 
	 Initalise a TarArchive for input.
	 
	 @param stream The <see cref="TarInputStream"/> to use for input.
	*/
	protected TarArchive(TarInputStream stream)
	{
		if (stream == null)
		{
			throw new NullPointerException("stream");
		}

		tarIn = stream;
	}

	/** 
	 Initialise a TarArchive for output.
	 
	 @param stream The <see cref="TarOutputStream"/> to use for output. 
	*/
	protected TarArchive(TarOutputStream stream)
	{
		if (stream == null)
		{
			throw new NullPointerException("stream");
		}

		tarOut = stream;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Static factory methods
	/** 
	 The InputStream based constructors create a TarArchive for the
	 purposes of extracting or listing a tar archive. Thus, use
	 these constructors when you wish to extract files from or list
	 the contents of an existing tar archive.
	 
	 @param inputStream The stream to retrieve archive data from.
	 @return Returns a new <see cref="TarArchive"/> suitable for reading from.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public static TarArchive CreateInputTarArchive(Stream inputStream)
	{
		if (inputStream == null)
		{
			throw new NullPointerException("inputStream");
		}

		TarInputStream tarStream = inputStream instanceof TarInputStream ? (TarInputStream)inputStream : null;

		TarArchive result;
		if (tarStream != null)
		{
			result = new TarArchive(tarStream);
		}
		else
		{
			result = CreateInputTarArchive(inputStream, TarBuffer.DefaultBlockFactor);
		}
		return result;
	}

	/** 
	 Create TarArchive for reading setting block factor
	 
	 @param inputStream A stream containing the tar archive contents
	 @param blockFactor The blocking factor to apply
	 @return Returns a <see cref="TarArchive"/> suitable for reading.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public static TarArchive CreateInputTarArchive(Stream inputStream, int blockFactor)
	{
		if (inputStream == null)
		{
			throw new NullPointerException("inputStream");
		}

		if (inputStream instanceof TarInputStream)
		{
			throw new IllegalArgumentException("TarInputStream not valid");
		}

		return new TarArchive(new TarInputStream(inputStream, blockFactor));
	}

	/** 
	 Create a TarArchive for writing to, using the default blocking factor
	 
	 @param outputStream The <see cref="Stream"/> to write to
	 @return Returns a <see cref="TarArchive"/> suitable for writing.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public static TarArchive CreateOutputTarArchive(Stream outputStream)
	{
		if (outputStream == null)
		{
			throw new NullPointerException("outputStream");
		}

		TarOutputStream tarStream = outputStream instanceof TarOutputStream ? (TarOutputStream)outputStream : null;

		TarArchive result;
		if (tarStream != null)
		{
			result = new TarArchive(tarStream);
		}
		else
		{
			result = CreateOutputTarArchive(outputStream, TarBuffer.DefaultBlockFactor);
		}
		return result;
	}

	/** 
	 Create a <see cref="TarArchive">tar archive</see> for writing.
	 
	 @param outputStream The stream to write to
	 @param blockFactor The blocking factor to use for buffering.
	 @return Returns a <see cref="TarArchive"/> suitable for writing.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public static TarArchive CreateOutputTarArchive(Stream outputStream, int blockFactor)
	{
		if (outputStream == null)
		{
			throw new NullPointerException("outputStream");
		}

		if (outputStream instanceof TarOutputStream)
		{
			throw new IllegalArgumentException("TarOutputStream is not valid");
		}

		return new TarArchive(new TarOutputStream(outputStream, blockFactor));
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Set the flag that determines whether existing files are
	 kept, or overwritten during extraction.
	 
	 @param keepExistingFiles
	 If true, do not overwrite existing files.
	 
	*/
	public final void SetKeepOldFiles(boolean keepExistingFiles)
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		keepOldFiles = keepExistingFiles;
	}

	/** 
	 Get/set the ascii file translation flag. If ascii file translation
	 is true, then the file is checked to see if it a binary file or not. 
	 If the flag is true and the test indicates it is ascii text 
	 file, it will be translated. The translation converts the local
	 operating system's concept of line ends into the UNIX line end,
	 '\n', which is the defacto standard for a TAR archive. This makes
	 text files compatible with UNIX.
	*/
	public final boolean getAsciiTranslate()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		return asciiTranslate;
	}

	public final void setAsciiTranslate(boolean value)
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		asciiTranslate = value;
	}


	/** 
	 Set the ascii file translation flag.
	 
	 @param  translateAsciiFiles
	 If true, translate ascii text files.
	 
	*/
	@Deprecated
	public final void SetAsciiTranslation(boolean translateAsciiFiles)
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		asciiTranslate = translateAsciiFiles;
	}

	/** 
	 PathPrefix is added to entry names as they are written if the value is not null.
	 A slash character is appended after PathPrefix 
	*/
	public final String getPathPrefix()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		return pathPrefix;
	}

	public final void setPathPrefix(String value)
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		pathPrefix = value;
	}


	/** 
	 RootPath is removed from entry names if it is found at the
	 beginning of the name.
	*/
	public final String getRootPath()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		return rootPath;
	}

	public final void setRootPath(String value)
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}
			// Convert to forward slashes for matching. Trim trailing / for correct final path
		rootPath = tangible.StringHelper.trimEnd(value.replace('\\', '/'), '/');
	}

	/** 
	 Set user and group information that will be used to fill in the
	 tar archive's entry headers. This information is based on that available 
	 for the linux operating system, which is not always available on other
	 operating systems.  TarArchive allows the programmer to specify values
	 to be used in their place.
	 <see cref="ApplyUserInfoOverrides"/> is set to true by this call.
	 
	 @param userId
	 The user id to use in the headers.
	 
	 @param userName
	 The user name to use in the headers.
	 
	 @param groupId
	 The group id to use in the headers.
	 
	 @param groupName
	 The group name to use in the headers.
	 
	*/
	public final void SetUserInfo(int userId, String userName, int groupId, String groupName)
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		this.userId = userId;
		this.userName = userName;
		this.groupId = groupId;
		this.groupName = groupName;
		applyUserInfoOverrides = true;
	}

	/** 
	 Get or set a value indicating if overrides defined by <see cref="SetUserInfo">SetUserInfo</see> should be applied.
	 
	 If overrides are not applied then the values as set in each header will be used.
	*/
	public final boolean getApplyUserInfoOverrides()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		return applyUserInfoOverrides;
	}

	public final void setApplyUserInfoOverrides(boolean value)
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		applyUserInfoOverrides = value;
	}

	/** 
	 Get the archive user id.
	 See <see cref="ApplyUserInfoOverrides">ApplyUserInfoOverrides</see> for detail
	 on how to allow setting values on a per entry basis.
	 
	 @return 
	 The current user id.
	 
	*/
	public final int getUserId()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		return userId;
	}

	/** 
	 Get the archive user name.
	 See <see cref="ApplyUserInfoOverrides">ApplyUserInfoOverrides</see> for detail
	 on how to allow setting values on a per entry basis.
	 
	 @return 
	 The current user name.
	 
	*/
	public final String getUserName()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		return userName;
	}

	/** 
	 Get the archive group id.
	 See <see cref="ApplyUserInfoOverrides">ApplyUserInfoOverrides</see> for detail
	 on how to allow setting values on a per entry basis.
	 
	 @return 
	 The current group id.
	 
	*/
	public final int getGroupId()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		return groupId;
	}

	/** 
	 Get the archive group name.
	 See <see cref="ApplyUserInfoOverrides">ApplyUserInfoOverrides</see> for detail
	 on how to allow setting values on a per entry basis.
	 
	 @return 
	 The current group name.
	 
	*/
	public final String getGroupName()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		return groupName;
	}

	/** 
	 Get the archive's record size. Tar archives are composed of
	 a series of RECORDS each containing a number of BLOCKS.
	 This allowed tar archives to match the IO characteristics of
	 the physical device being used. Archives are expected
	 to be properly "blocked".
	 
	 @return 
	 The record size this archive is using.
	 
	*/
	public final int getRecordSize()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		if (tarIn != null)
		{
			return tarIn.getRecordSize();
		}
		else if (tarOut != null)
		{
			return tarOut.getRecordSize();
		}
		return TarBuffer.DefaultRecordSize;
	}

	/** 
	 Sets the IsStreamOwner property on the underlying stream.
	 Set this to false to prevent the Close of the TarArchive from closing the stream.
	*/
	public final void setIsStreamOwner(boolean value)
	{
		if (tarIn != null)
		{
			tarIn.setIsStreamOwner(value);
		}
		else
		{
			tarOut.setIsStreamOwner(value);
		}
	}

	/** 
	 Close the archive.
	*/
	@Deprecated
	public final void CloseArchive()
	{
		Close();
	}

	/** 
	 Perform the "list" command for the archive contents.
	 
	 NOTE That this method uses the <see cref="ProgressMessageEvent"> progress event</see> to actually list
	 the contents. If the progress display event is not set, nothing will be listed!
	*/
	public final void ListContents()
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		while (true)
		{
			TarEntry entry = tarIn.GetNextEntry();

			if (entry == null)
			{
				break;
			}
			OnProgressMessageEvent(entry, null);
		}
	}

	/** 
	 Perform the "extract" command and extract the contents of the archive.
	 
	 @param destinationDirectory
	 The destination directory into which to extract.
	 
	*/
	public final void ExtractContents(String destinationDirectory)
	{
		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		while (true)
		{
			TarEntry entry = tarIn.GetNextEntry();

			if (entry == null)
			{
				break;
			}

			ExtractEntry(destinationDirectory, entry);
		}
	}

	/** 
	 Extract an entry from the archive. This method assumes that the
	 tarIn stream has been properly set with a call to GetNextEntry().
	 
	 @param destDir
	 The destination directory into which to extract.
	 
	 @param entry
	 The TarEntry returned by tarIn.GetNextEntry().
	 
	*/
	private void ExtractEntry(String destDir, TarEntry entry)
	{
		OnProgressMessageEvent(entry, null);

		String name = entry.getName();

		if (Paths.get(name).getParent() != null)
		{
			// NOTE:
			// for UNC names...  \\machine\share\zoom\beet.txt gives \zoom\beet.txt
			name = name.substring(Path.GetPathRoot(name).length());
		}

		name = name.replace('/', File.separatorChar);

		String destFile = Paths.get(destDir).resolve(name).toString();

		if (entry.getIsDirectory())
		{
			EnsureDirectoryExists(destFile);
		}
		else
		{
			String parentDirectory = (new File(destFile)).getParent();
			EnsureDirectoryExists(parentDirectory);

			boolean process = true;
			File fileInfo = new File(destFile);
			if (fileInfo.exists())
			{
				if (keepOldFiles)
				{
					OnProgressMessageEvent(entry, "Destination file already exists");
					process = false;
				}
				else if ((fileInfo.Attributes.getValue() & FileAttributes.ReadOnly.getValue()) != 0)
				{
					OnProgressMessageEvent(entry, "Destination file already exists, and is read-only");
					process = false;
				}
			}

			if (process)
			{
				boolean asciiTrans = false;

				OutputStream outputStream = File.Create(destFile);
				if (this.asciiTranslate)
				{
					asciiTrans = !IsBinary(destFile);
				}

				OutputStreamWriter outw = null;
				if (asciiTrans)
				{
					outw = new OutputStreamWriter(outputStream);
				}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] rdbuf = new byte[32 * 1024];
				byte[] rdbuf = new byte[32 * 1024];

				while (true)
				{
					int numRead = tarIn.Read(rdbuf, 0, rdbuf.length);

					if (numRead <= 0)
					{
						break;
					}

					if (asciiTrans)
					{
						for (int off = 0, b = 0; b < numRead; ++b)
						{
							if (rdbuf[b] == 10)
							{
								String s = Encoding.ASCII.GetString(rdbuf, off, (b - off));
								outw.write(s + System.lineSeparator());
								off = b + 1;
							}
						}
					}
					else
					{
						outputStream.write(rdbuf, 0, numRead);
					}
				}

				if (asciiTrans)
				{
					outw.close();
				}
				else
				{
					outputStream.close();
				}
			}
		}
	}

	/** 
	 Write an entry to the archive. This method will call the putNextEntry
	 and then write the contents of the entry, and finally call closeEntry()
	 for entries that are files. For directories, it will call putNextEntry(),
	 and then, if the recurse flag is true, process each entry that is a
	 child of the directory.
	 
	 @param sourceEntry
	 The TarEntry representing the entry to write to the archive.
	 
	 @param recurse
	 If true, process the children of directory entries.
	 
	*/
	public final void WriteEntry(TarEntry sourceEntry, boolean recurse)
	{
		if (sourceEntry == null)
		{
			throw new NullPointerException("sourceEntry");
		}

		if (isDisposed)
		{
			throw new ObjectDisposedException("TarArchive");
		}

		try
		{
			if (recurse)
			{
				TarHeader.SetValueDefaults(sourceEntry.getUserId(), sourceEntry.getUserName(), sourceEntry.getGroupId(), sourceEntry.getGroupName());
			}
			WriteEntryCore(sourceEntry, recurse);
		}
		finally
		{
			if (recurse)
			{
				TarHeader.RestoreSetValues();
			}
		}
	}

	/** 
	 Write an entry to the archive. This method will call the putNextEntry
	 and then write the contents of the entry, and finally call closeEntry()
	 for entries that are files. For directories, it will call putNextEntry(),
	 and then, if the recurse flag is true, process each entry that is a
	 child of the directory.
	 
	 @param sourceEntry
	 The TarEntry representing the entry to write to the archive.
	 
	 @param recurse
	 If true, process the children of directory entries.
	 
	*/
	private void WriteEntryCore(TarEntry sourceEntry, boolean recurse)
	{
		String tempFileName = null;
		String entryFilename = sourceEntry.getFile();

		TarEntry entry = (TarEntry)sourceEntry.Clone();

		if (applyUserInfoOverrides)
		{
			entry.setGroupId(groupId);
			entry.setGroupName(groupName);
			entry.setUserId(userId);
			entry.setUserName(userName);
		}

		OnProgressMessageEvent(entry, null);

		if (asciiTranslate && !entry.getIsDirectory())
		{

			if (!IsBinary(entryFilename))
			{
				tempFileName = Path.GetTempFileName();

				try (InputStreamReader inStream = File.OpenText(entryFilename))
				{
					try (Stream outStream = File.Create(tempFileName))
					{

						while (true)
						{
							String line = inStream.ReadLine();
							if (line == null)
							{
								break;
							}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] data = Encoding.ASCII.GetBytes(line);
							byte[] data = line.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
							outStream.Write(data, 0, data.length);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: outStream.WriteByte((byte)'\n');
							outStream.WriteByte((byte)'\n');
						}

						outStream.Flush();
					}
				}

				entry.setSize((new File(tempFileName)).length());
				entryFilename = tempFileName;
			}
		}

		String newName = null;

		if (rootPath != null)
		{
			if (entry.getName().startsWith(rootPath, StringComparison.OrdinalIgnoreCase))
			{
				newName = entry.getName().substring(rootPath.length() + 1);
			}
		}

		if (pathPrefix != null)
		{
			newName = (newName == null) ? pathPrefix + "/" + entry.getName() : pathPrefix + "/" + newName;
		}

		if (newName != null)
		{
			entry.setName(newName);
		}

		tarOut.PutNextEntry(entry);

		if (entry.getIsDirectory())
		{
			if (recurse)
			{
				TarEntry[] list = entry.GetDirectoryEntries();
				for (int i = 0; i < list.length; ++i)
				{
					WriteEntryCore(list[i], recurse);
				}
			}
		}
		else
		{
			try (Stream inputStream = File.OpenRead(entryFilename))
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] localBuffer = new byte[32 * 1024];
				byte[] localBuffer = new byte[32 * 1024];
				while (true)
				{
					int numRead = inputStream.Read(localBuffer, 0, localBuffer.length);

					if (numRead <= 0)
					{
						break;
					}

					tarOut.Write(localBuffer, 0, numRead);
				}
			}

			if ((tempFileName != null) && (tempFileName.length() > 0))
			{
				(new File(tempFileName)).delete();
			}

			tarOut.CloseEntry();
		}
	}

	/** 
	 Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
	*/
	public final void close() throws IOException
	{
		Dispose(true);
		GC.SuppressFinalize(this);
	}

	/** 
	 Releases the unmanaged resources used by the FileStream and optionally releases the managed resources.
	 
	 @param disposing true to release both managed and unmanaged resources;
	 false to release only unmanaged resources.
	*/
	protected void Dispose(boolean disposing)
	{
		if (!isDisposed)
		{
			isDisposed = true;
			if (disposing)
			{
				if (tarOut != null)
				{
					tarOut.Flush();
					tarOut.Close();
				}

				if (tarIn != null)
				{
					tarIn.Close();
				}
			}
		}
	}

	/** 
	 Closes the archive and releases any associated resources.
	*/
	public void Close()
	{
		Dispose(true);
	}

	/** 
	 Ensures that resources are freed and other cleanup operations are performed
	 when the garbage collector reclaims the <see cref="TarArchive"/>.
	*/
	protected void finalize() throws Throwable
	{
		Dispose(false);
	}

	private static void EnsureDirectoryExists(String directoryName)
	{
		if (!(new File(directoryName)).isDirectory())
		{
			try
			{
				(new File(directoryName)).mkdirs();
			}
			catch (RuntimeException e)
			{
				throw new TarException("Exception creating directory '" + directoryName + "', " + e.getMessage(), e);
			}
		}
	}

	// TODO: TarArchive - Is there a better way to test for a text file?
	// It no longer reads entire files into memory but is still a weak test!
	// This assumes that byte values 0-7, 14-31 or 255 are binary
	// and that all non text files contain one of these values
	private static boolean IsBinary(String filename)
	{
		try (FileStream fs = File.OpenRead(filename))
		{
			int sampleSize = Math.min(4096, (int)fs.Length);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] content = new byte[sampleSize];
			byte[] content = new byte[sampleSize];

			int bytesRead = fs.Read(content, 0, sampleSize);

			for (int i = 0; i < bytesRead; ++i)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte b = content[i];
				byte b = content[i];
				if ((b < 8) || ((b > 13) && (b < 32)) || (b == 255))
				{
					return true;
				}
			}
		}
		return false;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private boolean keepOldFiles;
	private boolean asciiTranslate;

	private int userId;
	private String userName = "";
	private int groupId;
	private String groupName = "";

	private String rootPath;
	private String pathPrefix;

	private boolean applyUserInfoOverrides;

	private TarInputStream tarIn;
	private TarOutputStream tarOut;
	private boolean isDisposed;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
/* The original Java file had this header:
	** Authored by Timothy Gerard Endres
	** <mailto:time@gjt.org>  <http: //www.trustice.com>
	**
	** This work has been placed into the public domain.
	** You may use this work in any way and for any purpose you wish.
	**
	** THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
	** NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
	** OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
	** CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
	** REDISTRIBUTION OF THIS SOFTWARE.
	**
	*/

