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

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region DataSources
/** 
 Provides a static way to obtain a source of data for an entry.
*/
public interface IStaticDataSource
{
	/** 
	 Get a source of data by creating a new stream.
	 
	 @return Returns a <see cref="Stream"/> to use for compression input.
	 Ideally a new stream is created and opened to achieve this, to avoid locking problems.
	*/
	Stream GetSource();
}