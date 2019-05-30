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
	///#region ZipFile Class
/** 
 This class represents a Zip archive.  You can ask for the contained
 entries, or get an input stream for a file entry.  The entry is
 automatically decompressed.
 
 You can also update the archive adding or deleting entries.
 
 This class is thread safe for input:  You can open input streams for arbitrary
 entries in different threads.
 <br/>
 <br/>Author of the original java version : Jochen Hoenicke
 
 <example>
 <code>
 using System;
 using System.Text;
 using System.Collections;
 using System.IO;
 
 using Rasad.Core.Compression.Zip;
 
 class MainClass
 {
 	static public void Main(string[] args)
 	{
 		using (ZipFile zFile = new ZipFile(args[0])) {
 			Console.WriteLine("Listing of : " + zFile.Name);
 			Console.WriteLine("");
 			Console.WriteLine("Raw Size    Size      Date     Time     Name");
 			Console.WriteLine("--------  --------  --------  ------  ---------");
 			foreach (ZipEntry e in zFile) {
 				if ( e.IsFile ) {
 					DateTime d = e.DateTime;
 					Console.WriteLine("{0, -10}{1, -10}{2}  {3}   {4}", e.Size, e.CompressedSize,
 						d.ToString("dd-MM-yy"), d.ToString("HH:mm"),
 						e.Name);
 				}
 			}
 		}
 	}
 }