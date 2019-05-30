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
 Default implementation of a <see cref="IStaticDataSource"/> for use with files stored on disk.
*/
public class StaticDiskDataSource implements IStaticDataSource
{
	/** 
	 Initialise a new instnace of <see cref="StaticDiskDataSource"/>
	 
	 @param fileName The name of the file to obtain data from.
	*/
	public StaticDiskDataSource(String fileName)
	{
		fileName_ = fileName;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IDataSource Members

	/** 
	 Get a <see cref="Stream"/> providing data.
	 
	 @return Returns a <see cref="Stream"/> provising data.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final Stream GetSource()
	{
		return File.Open(fileName_, FileMode.Open, FileAccess.Read, FileShare.Read);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private String fileName_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}