package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Encryption.*;
import Rasad.Core.Compression.Core.*;
import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.Compression.Zip.Compression.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;

/** 
 An <see cref="IArchiveStorage"/> implementation suitable for hard disks.
*/
public class DiskArchiveStorage extends BaseArchiveStorage
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initializes a new instance of the <see cref="DiskArchiveStorage"/> class.
	 
	 @param file The file.
	 @param updateMode The update mode.
	*/
	public DiskArchiveStorage(ZipFile file, FileUpdateMode updateMode)
	{
		super(updateMode);
		if (file.getName() == null)
		{
			throw new ZipException("Cant handle non file archives");
		}

		fileName_ = file.getName();
	}

	/** 
	 Initializes a new instance of the <see cref="DiskArchiveStorage"/> class.
	 
	 @param file The file.
	*/
	public DiskArchiveStorage(ZipFile file)
	{
		this(file, FileUpdateMode.Safe);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IArchiveStorage Members

	/** 
	 Gets a temporary output <see cref="Stream"/> for performing updates on.
	 
	 @return Returns the temporary output stream.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	@Override
	public Stream GetTemporaryOutput()
	{
		if (temporaryName_ != null)
		{
			temporaryName_ = GetTempFileName(temporaryName_, true);
			temporaryStream_ = File.Open(temporaryName_, FileMode.OpenOrCreate, FileAccess.Write, FileShare.None);
		}
		else
		{
			// Determine where to place files based on internal strategy.
			// Currently this is always done in system temp directory.
			temporaryName_ = Path.GetTempFileName();
			temporaryStream_ = File.Open(temporaryName_, FileMode.OpenOrCreate, FileAccess.Write, FileShare.None);
		}

		return temporaryStream_;
	}

	/** 
	 Converts a temporary <see cref="Stream"/> to its final form.
	 
	 @return Returns a <see cref="Stream"/> that can be used to read
	 the final storage for the archive.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	@Override
	public Stream ConvertTemporaryToFinal()
	{
		if (temporaryStream_ == null)
		{
			throw new ZipException("No temporary stream has been created");
		}

		InputStream result = null;

		String moveTempName = GetTempFileName(fileName_, false);
		boolean newFileCreated = false;

		try
		{
			temporaryStream_.Close();
			Files.move(Paths.get(fileName_), Paths.get(moveTempName));
			Files.move(Paths.get(temporaryName_), Paths.get(fileName_));
			newFileCreated = true;
			(new File(moveTempName)).delete();

			result = File.Open(fileName_, FileMode.Open, FileAccess.Read, FileShare.Read);
		}
		catch (RuntimeException e)
		{
			result = null;

			// Try to roll back changes...
			if (!newFileCreated)
			{
				Files.move(Paths.get(moveTempName), Paths.get(fileName_));
				(new File(temporaryName_)).delete();
			}

			throw e;
		}

		return result;
	}

	/** 
	 Make a temporary copy of a stream.
	 
	 @param stream The <see cref="Stream"/> to copy.
	 @return Returns a temporary output <see cref="Stream"/> that is a copy of the input.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	@Override
	public Stream MakeTemporaryCopy(Stream stream)
	{
		stream.Close();

		temporaryName_ = GetTempFileName(fileName_, true);
		Files.copy(Paths.get(fileName_), Paths.get(temporaryName_), StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
		temporaryStream_ = new FileStream(temporaryName_, FileMode.Open, FileAccess.ReadWrite);
		return temporaryStream_;
	}

	/** 
	 Return a stream suitable for performing direct updates on the original source.
	 
	 @param stream The current stream.
	 @return Returns a stream suitable for direct updating.
	 If the <paramref name="current"/> stream is not null this is used as is.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	@Override
	public Stream OpenForDirectUpdate(Stream stream)
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		Stream result;
		if ((stream == null) || !stream.CanWrite)
		{
			if (stream != null)
			{
				stream.Close();
			}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
			result = new FileStream(fileName_, FileMode.Open, FileAccess.ReadWrite);
		}
		else
		{
			result = stream;
		}

		return result;
	}

	/** 
	 Disposes this instance.
	*/
	@Override
	public void Dispose()
	{
		if (temporaryStream_ != null)
		{
			temporaryStream_.Close();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Internal routines
	private static String GetTempFileName(String original, boolean makeTempFile)
	{
		String result = null;

		if (original == null)
		{
			result = Path.GetTempFileName();
		}
		else
		{
			int counter = 0;
			int suffixSeed = LocalDateTime.now().getSecond();

			while (result == null)
			{
				counter += 1;
				String newName = String.format("%1$s.%2$s%3$s.tmp", original, suffixSeed, counter);
				if (!(new File(newName)).isFile())
				{
					if (makeTempFile)
					{
						try
						{
							// Try and create the file.
							try (FileStream stream = File.Create(newName))
							{
							}
							result = newName;
						}
						catch (java.lang.Exception e)
						{
							suffixSeed = LocalDateTime.now().getSecond();
						}
					}
					else
					{
						result = newName;
					}
				}
			}
		}
		return result;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream temporaryStream_;
	private String fileName_;
	private String temporaryName_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}