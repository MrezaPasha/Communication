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
 Delegate invoked during <see cref="ZipFile.TestArchive(bool, TestStrategy, ZipTestResultHandler)">testing</see> if supplied indicating current progress and status.
 
 If the message is non-null an error has occured.  If the message is null
 the operation as found in <see cref="TestStatus">status</see> has started.
*/
@FunctionalInterface
public interface ZipTestResultHandler
{
	void invoke(TestStatus status, String message);
}