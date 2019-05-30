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
 Default implementation of <see cref="IDynamicDataSource"/> for files stored on disk.
*/
public class DynamicDiskDataSource implements IDynamicDataSource
{
	/** 
	 Initialise a default instance of <see cref="DynamicDiskDataSource"/>.
	*/
	public DynamicDiskDataSource()
	{
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IDataSource Members
	/** 
	 Get a <see cref="Stream"/> providing data for an entry.
	 
	 @param entry The entry to provide data for.
	 @param name The file name for data if known.
	 @return Returns a stream providing data; or null if not available
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final Stream GetSource(ZipEntry entry, String name)
	{
		InputStream result = null;

		if (name != null)
		{
			result = File.Open(name, FileMode.Open, FileAccess.Read, FileShare.Read);
		}

		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}