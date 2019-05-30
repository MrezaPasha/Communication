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
	///#region Update Definitions
/** 
 The possible ways of <see cref="ZipFile.CommitUpdate()">applying updates</see> to an archive.
*/
public enum FileUpdateMode
{
	/** 
	 Perform all updates on temporary files ensuring that the original file is saved.
	*/
	Safe,
	/** 
	 Update the archive directly, which is faster but less safe.
	*/
	Direct;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static FileUpdateMode forValue(int value)
	{
		return values()[value];
	}
}