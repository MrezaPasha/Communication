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
	///#region Archive Storage
/** 
 Defines facilities for data storage when updating Zip Archives.
*/
public interface IArchiveStorage
{
	/** 
	 Get the <see cref="FileUpdateMode"/> to apply during updates.
	*/
	FileUpdateMode getUpdateMode();

	/** 
	 Get an empty <see cref="Stream"/> that can be used for temporary output.
	 
	 @return Returns a temporary output <see cref="Stream"/>
	 @see ConvertTemporaryToFinal
	*/
	Stream GetTemporaryOutput();

	/** 
	 Convert a temporary output stream to a final stream.
	 
	 @return The resulting final <see cref="Stream"/>
	 {@link GetTemporaryOutput}
	*/
	Stream ConvertTemporaryToFinal();

	/** 
	 Make a temporary copy of the original stream.
	 
	 @param stream The <see cref="Stream"/> to copy.
	 @return Returns a temporary output <see cref="Stream"/> that is a copy of the input.
	*/
	Stream MakeTemporaryCopy(Stream stream);

	/** 
	 Return a stream suitable for performing direct updates on the original source.
	 
	 @param stream The current stream.
	 @return Returns a stream suitable for direct updating.
	 This may be the current stream passed.
	*/
	Stream OpenForDirectUpdate(Stream stream);

	/** 
	 Dispose of this instance.
	*/
	void Dispose();
}