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
 The operation in progress reported by a <see cref="ZipTestResultHandler"/> during testing.
 
 @see ZipFile.TestArchive(bool) TestArchive
*/
public enum TestOperation
{
	/** 
	 Setting up testing.
	*/
	Initialising,

	/** 
	 Testing an individual entries header
	*/
	EntryHeader,

	/** 
	 Testing an individual entries data
	*/
	EntryData,

	/** 
	 Testing an individual entry has completed.
	*/
	EntryComplete,

	/** 
	 Running miscellaneous tests
	*/
	MiscellaneousTests,

	/** 
	 Testing is complete
	*/
	Complete;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static TestOperation forValue(int value)
	{
		return values()[value];
	}
}