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
 An abstract <see cref="IArchiveStorage"/> suitable for extension by inheritance.
*/
public abstract class BaseArchiveStorage implements IArchiveStorage
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initializes a new instance of the <see cref="BaseArchiveStorage"/> class.
	 
	 @param updateMode The update mode.
	*/
	protected BaseArchiveStorage(FileUpdateMode updateMode)
	{
		updateMode_ = updateMode;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IArchiveStorage Members

	/** 
	 Gets a temporary output <see cref="Stream"/>
	 
	 @return Returns the temporary output stream.
	 @see ConvertTemporaryToFinal
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public abstract Stream GetTemporaryOutput();

	/** 
	 Converts the temporary <see cref="Stream"/> to its final form.
	 
	 @return Returns a <see cref="Stream"/> that can be used to read
	 the final storage for the archive.
	 {@link GetTemporaryOutput}
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public abstract Stream ConvertTemporaryToFinal();

	/** 
	 Make a temporary copy of a <see cref="Stream"/>.
	 
	 @param stream The <see cref="Stream"/> to make a copy of.
	 @return Returns a temporary output <see cref="Stream"/> that is a copy of the input.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public abstract Stream MakeTemporaryCopy(Stream stream);

	/** 
	 Return a stream suitable for performing direct updates on the original source.
	 
	 @param stream The <see cref="Stream"/> to open for direct update.
	 @return Returns a stream suitable for direct updating.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public abstract Stream OpenForDirectUpdate(Stream stream);

	/** 
	 Disposes this instance.
	*/
	public abstract void Dispose();

	/** 
	 Gets the update mode applicable.
	 
	 <value>The update mode.</value>
	*/
	public final FileUpdateMode getUpdateMode()
	{
		return updateMode_;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private FileUpdateMode updateMode_ = FileUpdateMode.values()[0];
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}