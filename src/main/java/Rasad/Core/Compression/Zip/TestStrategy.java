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
	///#region Test Definitions
/** 
 The strategy to apply to testing.
*/
public enum TestStrategy
{
	/** 
	 Find the first error only.
	*/
	FindFirstError,
	/** 
	 Find all possible errors.
	*/
	FindAllErrors;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static TestStrategy forValue(int value)
	{
		return values()[value];
	}
}