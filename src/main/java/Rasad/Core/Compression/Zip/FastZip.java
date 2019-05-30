package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Core.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;
import java.io.*;

/** 
 FastZip provides facilities for creating and extracting zip files.
*/
public class FastZip
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Enumerations
	/** 
	 Defines the desired handling when overwriting files during extraction.
	*/
	public enum Overwrite
	{
		/** 
		 Prompt the user to confirm overwriting
		*/
		Prompt,
		/** 
		 Never overwrite files.
		*/
		Never,
		/** 
		 Always overwrite files.
		*/
		Always;

		public static final int SIZE = java.lang.Integer.SIZE;

		public int getValue()
		{
			return this.ordinal();
		}

		public static Overwrite forValue(int value)
		{
			return values()[value];
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a default instance of <see cref="FastZip"/>.
	*/
	public FastZip()
	{
	}

	/** 
	 Initialise a new instance of <see cref="FastZip"/>
	 
	 @param events The <see cref="FastZipEvents">events</see> to use during operations.
	*/
	public FastZip(FastZipEvents events)
	{
		events_ = events;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	/** 
	 Get/set a value indicating wether empty directories should be created.
	*/
	public final boolean getCreateEmptyDirectories()
	{
		return createEmptyDirectories_;
	}
	public final void setCreateEmptyDirectories(boolean value)
	{
		createEmptyDirectories_ = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
	/** 
	 Get / set the password value.
	*/
	public final String getPassword()
	{
		return password_;
	}
	public final void setPassword(String value)
	{
		password_ = value;
	}
//#endif

	/** 
	 Get or set the <see cref="INameTransform"></see> active when creating Zip files.
	 
	 @see EntryFactory
	*/
	public final INameTransform getNameTransform()
	{
		return entryFactory_.getNameTransform();
	}
	public final void setNameTransform(INameTransform value)
	{
		entryFactory_.setNameTransform(value);
	}

	/** 
	 Get or set the <see cref="IEntryFactory"></see> active when creating Zip files.
	*/
	public final IEntryFactory getEntryFactory()
	{
		return entryFactory_;
	}
	public final void setEntryFactory(IEntryFactory value)
	{
		if (value == null)
		{
			entryFactory_ = new ZipEntryFactory();
		}
		else
		{
			entryFactory_ = value;
		}
	}

	/** 
	 Gets or sets the setting for <see cref="UseZip64">Zip64 handling when writing.</see>
	 
	 
	 The default value is dynamic which is not backwards compatible with old
	 programs and can cause problems with XP's built in compression which cant
	 read Zip64 archives. However it does avoid the situation were a large file
	 is added and cannot be completed correctly.
	 NOTE: Setting the size for entries before they are added is the best solution!
	 By default the EntryFactory used by FastZip will set fhe file size.
	 
	*/
	public final UseZip64 getUseZip64()
	{
		return useZip64_;
	}
	public final void setUseZip64(UseZip64 value)
	{
		useZip64_ = value;
	}

	/** 
	 Get/set a value indicating wether file dates and times should 
	 be restored when extracting files from an archive.
	 
	 The default value is false.
	*/
	public final boolean getRestoreDateTimeOnExtract()
	{
		return restoreDateTimeOnExtract_;
	}
	public final void setRestoreDateTimeOnExtract(boolean value)
	{
		restoreDateTimeOnExtract_ = value;
	}

	/** 
	 Get/set a value indicating wether file attributes should
	 be restored during extract operations
	*/
	public final boolean getRestoreAttributesOnExtract()
	{
		return restoreAttributesOnExtract_;
	}
	public final void setRestoreAttributesOnExtract(boolean value)
	{
		restoreAttributesOnExtract_ = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Delegates
	/** 
	 Delegate called when confirming overwriting of files.
	*/
	@FunctionalInterface
	public interface ConfirmOverwriteDelegate
	{
		boolean invoke(String fileName);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreateZip
	/** 
	 Create a zip file.
	 
	 @param zipFileName The name of the zip file to create.
	 @param sourceDirectory The directory to source files from.
	 @param recurse True to recurse directories, false for no recursion.
	 @param fileFilter The <see cref="PathFilter">file filter</see> to apply.
	 @param directoryFilter The <see cref="PathFilter">directory filter</see> to apply.
	*/
	public final void CreateZip(String zipFileName, String sourceDirectory, boolean recurse, String fileFilter, String directoryFilter)
	{
		CreateZip(File.Create(zipFileName), sourceDirectory, recurse, fileFilter, directoryFilter);
	}

	/** 
	 Create a zip file/archive.
	 
	 @param zipFileName The name of the zip file to create.
	 @param sourceDirectory The directory to obtain files and directories from.
	 @param recurse True to recurse directories, false for no recursion.
	 @param fileFilter The file filter to apply.
	*/
	public final void CreateZip(String zipFileName, String sourceDirectory, boolean recurse, String fileFilter)
	{
		CreateZip(File.Create(zipFileName), sourceDirectory, recurse, fileFilter, null);
	}

	/** 
	 Create a zip archive sending output to the <paramref name="outputStream"/> passed.
	 
	 @param outputStream The stream to write archive data to.
	 @param sourceDirectory The directory to source files from.
	 @param recurse True to recurse directories, false for no recursion.
	 @param fileFilter The <see cref="PathFilter">file filter</see> to apply.
	 @param directoryFilter The <see cref="PathFilter">directory filter</see> to apply.
	 The <paramref name="outputStream"/> is closed after creation.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final void CreateZip(Stream outputStream, String sourceDirectory, boolean recurse, String fileFilter, String directoryFilter)
	{
		setNameTransform(new ZipNameTransform(sourceDirectory));
		sourceDirectory_ = sourceDirectory;

		try (outputStream_ = new ZipOutputStream(outputStream))
		{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
			if (password_ != null)
			{
				outputStream_.Password = password_;
			}
//#endif

			outputStream_.setUseZip64(getUseZip64());
			FileSystemScanner scanner = new FileSystemScanner(fileFilter, directoryFilter);
			scanner.ProcessFile += (Object sender, ScanEventArgs e) -> ProcessFile(sender, e);
			if (this.getCreateEmptyDirectories())
			{
				scanner.ProcessDirectory += (Object sender, DirectoryEventArgs e) -> ProcessDirectory(sender, e);
			}

			if (events_ != null)
			{
				if (events_.FileFailure != null)
				{
					scanner.FileFailure += events_.FileFailure;
				}

				if (events_.DirectoryFailure != null)
				{
					scanner.DirectoryFailure += events_.DirectoryFailure;
				}
			}

			scanner.Scan(sourceDirectory, recurse);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ExtractZip
	/** 
	 Extract the contents of a zip file.
	 
	 @param zipFileName The zip file to extract from.
	 @param targetDirectory The directory to save extracted information in.
	 @param fileFilter A filter to apply to files.
	*/
	public final void ExtractZip(String zipFileName, String targetDirectory, String fileFilter)
	{
		ExtractZip(zipFileName, targetDirectory, Overwrite.Always, null, fileFilter, null, restoreDateTimeOnExtract_);
	}

	/** 
	 Extract the contents of a zip file.
	 
	 @param zipFileName The zip file to extract from.
	 @param targetDirectory The directory to save extracted information in.
	 @param overwrite The style of <see cref="Overwrite">overwriting</see> to apply.
	 @param confirmDelegate A delegate to invoke when confirming overwriting.
	 @param fileFilter A filter to apply to files.
	 @param directoryFilter A filter to apply to directories.
	 @param restoreDateTime Flag indicating whether to restore the date and time for extracted files.
	*/
	public final void ExtractZip(String zipFileName, String targetDirectory, Overwrite overwrite, ConfirmOverwriteDelegate confirmDelegate, String fileFilter, String directoryFilter, boolean restoreDateTime)
	{
		InputStream inputStream = File.Open(zipFileName, FileMode.Open, FileAccess.Read, FileShare.Read);
		ExtractZip(inputStream, targetDirectory, overwrite, confirmDelegate, fileFilter, directoryFilter, restoreDateTime, true);
	}

	/** 
	 Extract the contents of a zip file held in a stream.
	 
	 @param inputStream The seekable input stream containing the zip to extract from.
	 @param targetDirectory The directory to save extracted information in.
	 @param overwrite The style of <see cref="Overwrite">overwriting</see> to apply.
	 @param confirmDelegate A delegate to invoke when confirming overwriting.
	 @param fileFilter A filter to apply to files.
	 @param directoryFilter A filter to apply to directories.
	 @param restoreDateTime Flag indicating whether to restore the date and time for extracted files.
	 @param isStreamOwner Flag indicating whether the inputStream will be closed by this method.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final void ExtractZip(Stream inputStream, String targetDirectory, Overwrite overwrite, ConfirmOverwriteDelegate confirmDelegate, String fileFilter, String directoryFilter, boolean restoreDateTime, boolean isStreamOwner)
	{
		if ((overwrite == Overwrite.Prompt) && (confirmDelegate == null))
		{
			throw new NullPointerException("confirmDelegate");
		}

		continueRunning_ = true;
		overwrite_ = overwrite;
		confirmDelegate_ = (String fileName) -> confirmDelegate.invoke(fileName);
		extractNameTransform_ = new WindowsNameTransform(targetDirectory);

		fileFilter_ = new NameFilter(fileFilter);
		directoryFilter_ = new NameFilter(directoryFilter);
		restoreDateTimeOnExtract_ = restoreDateTime;

		try (zipFile_ = new ZipFile(inputStream))
		{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
			if (password_ != null)
			{
				zipFile_.setPassword(password_);
			}
//#endif
			zipFile_.setIsStreamOwner(isStreamOwner);
			Iterator enumerator = zipFile_.iterator();
			while (continueRunning_ && enumerator.hasNext())
			{
				ZipEntry entry = (ZipEntry)enumerator.next();
				if (entry.getIsFile())
				{
					// TODO Path.GetDirectory can fail here on invalid characters.
					if (directoryFilter_.IsMatch((new File(entry.getName())).getParent()) && fileFilter_.IsMatch(entry.getName()))
					{
						ExtractEntry(entry);
					}
				}
				else if (entry.getIsDirectory())
				{
					if (directoryFilter_.IsMatch(entry.getName()) && getCreateEmptyDirectories())
					{
						ExtractEntry(entry);
					}
				}
				else
				{
					// Do nothing for volume labels etc...
				}
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Internal Processing
	private void ProcessDirectory(Object sender, DirectoryEventArgs e)
	{
		if (!e.getHasMatchingFiles() && getCreateEmptyDirectories())
		{
			if (events_ != null)
			{
				events_.OnProcessDirectory(e.getName(), e.getHasMatchingFiles());
			}

			if (e.getContinueRunning())
			{
				if (!e.getName().equals(sourceDirectory_))
				{
					ZipEntry entry = entryFactory_.MakeDirectoryEntry(e.getName());
					outputStream_.PutNextEntry(entry);
				}
			}
		}
	}

	private void ProcessFile(Object sender, ScanEventArgs e)
	{
		if ((events_ != null) && (events_.ProcessFile != null))
		{
			events_.ProcessFile.invoke(sender, e);
		}

		if (e.getContinueRunning())
		{
			try
			{
				// The open below is equivalent to OpenRead which gaurantees that if opened the 
				// file will not be changed by subsequent openers, but precludes opening in some cases
				// were it could succeed. ie the open may fail as its already open for writing and the share mode should reflect that.
				try (FileStream stream = File.Open(e.getName(), FileMode.Open, FileAccess.Read, FileShare.Read))
				{
					ZipEntry entry = entryFactory_.MakeFileEntry(e.getName());
					outputStream_.PutNextEntry(entry);
					AddFileContents(e.getName(), stream);
				}
			}
			catch (RuntimeException ex)
			{
				if (events_ != null)
				{
					continueRunning_ = events_.OnFileFailure(e.getName(), ex);
				}
				else
				{
					continueRunning_ = false;
					throw ex;
				}
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private void AddFileContents(String name, Stream stream)
	{
		if (stream == null)
		{
			throw new NullPointerException("stream");
		}

		if (buffer_ == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer_ = new byte[4096];
			buffer_ = new byte[4096];
		}

		if ((events_ != null) && (events_.Progress != null))
		{
			StreamUtils.Copy(stream, outputStream_, buffer_, events_.Progress, events_.getProgressInterval(), this, name);
		}
		else
		{
			StreamUtils.Copy(stream, outputStream_, buffer_);
		}

		if (events_ != null)
		{
			continueRunning_ = events_.OnCompletedFile(name);
		}
	}

	private void ExtractFileEntry(ZipEntry entry, String targetName)
	{
		boolean proceed = true;
		if (overwrite_ != Overwrite.Always)
		{
			if ((new File(targetName)).isFile())
			{
				if ((overwrite_ == Overwrite.Prompt) && (confirmDelegate_ != null))
				{
					proceed = confirmDelegate_.invoke(targetName);
				}
				else
				{
					proceed = false;
				}
			}
		}

		if (proceed)
		{
			if (events_ != null)
			{
				continueRunning_ = events_.OnProcessFile(entry.getName());
			}

			if (continueRunning_)
			{
				try
				{
					try (FileStream outputStream = File.Create(targetName))
					{
						if (buffer_ == null)
						{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer_ = new byte[4096];
							buffer_ = new byte[4096];
						}
						if ((events_ != null) && (events_.Progress != null))
						{
							StreamUtils.Copy(zipFile_.GetInputStream(entry), outputStream, buffer_, events_.Progress, events_.getProgressInterval(), this, entry.getName(), entry.getSize());
						}
						else
						{
							StreamUtils.Copy(zipFile_.GetInputStream(entry), outputStream, buffer_);
						}

						if (events_ != null)
						{
							continueRunning_ = events_.OnCompletedFile(entry.getName());
						}
					}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0 && !NETCF_2_0
					if (restoreDateTimeOnExtract_)
					{
						File.SetLastWriteTime(targetName, entry.getDateTime());
					}

					if (getRestoreAttributesOnExtract() && entry.getIsDOSEntry() && (entry.getExternalFileAttributes() != -1))
					{
						FileAttributes fileAttributes = FileAttributes.forValue(entry.getExternalFileAttributes());
						// TODO: FastZip - Setting of other file attributes on extraction is a little trickier.
						fileAttributes = FileAttributes.forValue(fileAttributes.getValue() & System.IO.FileAttributes.forValue(FileAttributes.Archive.getValue() | FileAttributes.Normal.getValue() | FileAttributes.ReadOnly.getValue() | FileAttributes.Hidden.getValue()).getValue());
						File.SetAttributes(targetName, fileAttributes);
					}
//#endif
				}
				catch (RuntimeException ex)
				{
					if (events_ != null)
					{
						continueRunning_ = events_.OnFileFailure(targetName, ex);
					}
					else
					{
						continueRunning_ = false;
						throw ex;
					}
				}
			}
		}
	}

	private void ExtractEntry(ZipEntry entry)
	{
		boolean doExtraction = entry.IsCompressionMethodSupported();
		String targetName = entry.getName();

		if (doExtraction)
		{
			if (entry.getIsFile())
			{
				targetName = extractNameTransform_.TransformFile(targetName);
			}
			else if (entry.getIsDirectory())
			{
				targetName = extractNameTransform_.TransformDirectory(targetName);
			}

			doExtraction = !((targetName == null) || (targetName.length() == 0));
		}

		// TODO: Fire delegate/throw exception were compression method not supported, or name is invalid?

		String dirName = null;

		if (doExtraction)
		{
				if (entry.getIsDirectory())
				{
					dirName = targetName;
				}
				else
				{
					dirName = (new File((new File(targetName)).getAbsolutePath())).getParent();
				}
		}

		if (doExtraction && !(new File(dirName)).isDirectory())
		{
			if (!entry.getIsDirectory() || getCreateEmptyDirectories())
			{
				try
				{
					(new File(dirName)).mkdirs();
				}
				catch (RuntimeException ex)
				{
					doExtraction = false;
					if (events_ != null)
					{
						if (entry.getIsDirectory())
						{
							continueRunning_ = events_.OnDirectoryFailure(targetName, ex);
						}
						else
						{
							continueRunning_ = events_.OnFileFailure(targetName, ex);
						}
					}
					else
					{
						continueRunning_ = false;
						throw ex;
					}
				}
			}
		}

		if (doExtraction && entry.getIsFile())
		{
			ExtractFileEntry(entry, targetName);
		}
	}

	private static int MakeExternalAttributes(File info)
	{
		return info.Attributes.getValue();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NET_1_0 || NET_1_1 || NETCF_1_0
	private static boolean NameIsValid(String name)
	{
		return (name != null) && (name.length() > 0) && (tangible.StringHelper.indexOfAny(name, Path.InvalidPathChars) < 0);
	}
//#else
	private static boolean NameIsValid(String name)
	{
		return (name != null) && (name.length() > 0) && (tangible.StringHelper.indexOfAny(name, Path.GetInvalidPathChars()) < 0);
	}
//#endif
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private boolean continueRunning_;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer_;
	private byte[] buffer_;
	private ZipOutputStream outputStream_;
	private ZipFile zipFile_;
	private String sourceDirectory_;
	private NameFilter fileFilter_;
	private NameFilter directoryFilter_;
	private Overwrite overwrite_ = Overwrite.values()[0];
	private ConfirmOverwriteDelegate confirmDelegate_;

	private boolean restoreDateTimeOnExtract_;
	private boolean restoreAttributesOnExtract_;
	private boolean createEmptyDirectories_;
	private FastZipEvents events_;
	private IEntryFactory entryFactory_ = new ZipEntryFactory();
	private INameTransform extractNameTransform_;
	private UseZip64 useZip64_ = UseZip64.Dynamic;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
	private String password_;
//#endif

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}