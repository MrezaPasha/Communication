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
 An <see cref="IArchiveStorage"/> implementation suitable for in memory streams.
*/
public class MemoryArchiveStorage extends BaseArchiveStorage
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initializes a new instance of the <see cref="MemoryArchiveStorage"/> class.
	*/
	public MemoryArchiveStorage()
	{
		super(FileUpdateMode.Direct);
	}

	/** 
	 Initializes a new instance of the <see cref="MemoryArchiveStorage"/> class.
	 
	 @param updateMode The <see cref="FileUpdateMode"/> to use
	 This constructor is for testing as memory streams dont really require safe mode.
	*/
	public MemoryArchiveStorage(FileUpdateMode updateMode)
	{
		super(updateMode);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	/** 
	 Get the stream returned by <see cref="ConvertTemporaryToFinal"/> if this was in fact called.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
	public final MemoryStream getFinalStream()
	{
		return finalStream_;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IArchiveStorage Members

	/** 
	 Gets the temporary output <see cref="Stream"/>
	 
	 @return Returns the temporary output stream.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	@Override
	public Stream GetTemporaryOutput()
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
		temporaryStream_ = new MemoryStream();
		return temporaryStream_;
	}

	/** 
	 Converts the temporary <see cref="Stream"/> to its final form.
	 
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

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
		finalStream_ = new MemoryStream(temporaryStream_.ToArray());
		return finalStream_;
	}

	/** 
	 Make a temporary copy of the original stream.
	 
	 @param stream The <see cref="Stream"/> to copy.
	 @return Returns a temporary output <see cref="Stream"/> that is a copy of the input.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	@Override
	public Stream MakeTemporaryCopy(Stream stream)
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
		temporaryStream_ = new MemoryStream();
		stream.Position = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: StreamUtils.Copy(stream, temporaryStream_, new byte[4096]);
		StreamUtils.Copy(stream, temporaryStream_, new byte[4096]);
		return temporaryStream_;
	}

	/** 
	 Return a stream suitable for performing direct updates on the original source.
	 
	 @param stream The original source stream
	 @return Returns a stream suitable for direct updating.
	 If the <paramref name="stream"/> passed is not null this is used;
	 otherwise a new <see cref="MemoryStream"/> is returned.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	@Override
	public Stream OpenForDirectUpdate(Stream stream)
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		Stream result;
		if ((stream == null) || !stream.CanWrite)
		{

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
			result = new MemoryStream();

			if (stream != null)
			{
				stream.Position = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: StreamUtils.Copy(stream, result, new byte[4096]);
				StreamUtils.Copy(stream, result, new byte[4096]);

				stream.Close();
			}
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
		///#region Instance Fields
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
	private MemoryStream temporaryStream_;
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
	private MemoryStream finalStream_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion
