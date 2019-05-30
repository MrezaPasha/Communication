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
 Status returned returned by <see cref="ZipTestResultHandler"/> during testing.
 
 @see ZipFile.TestArchive(bool) TestArchive
*/
public class TestStatus
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a new instance of <see cref="TestStatus"/>
	 
	 @param file The <see cref="ZipFile"/> this status applies to.
	*/
	public TestStatus(ZipFile file)
	{
		file_ = file;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties

	/** 
	 Get the current <see cref="TestOperation"/> in progress.
	*/
	public final TestOperation getOperation()
	{
		return operation_;
	}

	/** 
	 Get the <see cref="ZipFile"/> this status is applicable to.
	*/
	public final ZipFile getFile()
	{
		return file_;
	}

	/** 
	 Get the current/last entry tested.
	*/
	public final ZipEntry getEntry()
	{
		return entry_;
	}

	/** 
	 Get the number of errors detected so far.
	*/
	public final int getErrorCount()
	{
		return errorCount_;
	}

	/** 
	 Get the number of bytes tested so far for the current entry.
	*/
	public final long getBytesTested()
	{
		return bytesTested_;
	}

	/** 
	 Get a value indicating wether the last entry test was valid.
	*/
	public final boolean getEntryValid()
	{
		return entryValid_;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Internal API
	public final void AddError()
	{
		errorCount_++;
		entryValid_ = false;
	}

	public final void SetOperation(TestOperation operation)
	{
		operation_ = operation;
	}

	public final void SetEntry(ZipEntry entry)
	{
		entry_ = entry;
		entryValid_ = true;
		bytesTested_ = 0;
	}

	public final void SetBytesTested(long value)
	{
		bytesTested_ = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private ZipFile file_;
	private ZipEntry entry_;
	private boolean entryValid_;
	private int errorCount_;
	private long bytesTested_;
	private TestOperation operation_ = TestOperation.values()[0];
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}