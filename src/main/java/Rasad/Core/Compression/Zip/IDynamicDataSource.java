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
 Represents a source of data that can dynamically provide
 multiple <see cref="Stream">data sources</see> based on the parameters passed.
*/
public interface IDynamicDataSource
{
	/** 
	 Get a data source.
	 
	 @param entry The <see cref="ZipEntry"/> to get a source for.
	 @param name The name for data if known.
	 @return Returns a <see cref="Stream"/> to use for compression input.
	 Ideally a new stream is created and opened to achieve this, to avoid locking problems.
	*/
	Stream GetSource(ZipEntry entry, String name);
}