package Rasad.Core.Compression.LZW;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

private ﻿ using System;



/** 
 This filter stream is used to decompress a LZW format stream.
 Specifically, a stream that uses the LZC compression method.
 This file format is usually associated with the .Z file extension.

 See http: //en.wikipedia.org/wiki/Compress
 See http: //wiki.wxwidgets.org/Development:_Z_File_Format
 
 The file header consists of 3 (or optionally 4) bytes. The first two bytes
 contain the magic marker "0x1f 0x9d", followed by a byte of flags.

 Based on Java code by Ronald Tschalar, which in turn was based on the unlzw.c
 code in the gzip package.
 
 <example> This sample shows how to unzip a compressed file
 <code>
 using System;
 using System.IO;
 
 using Rasad.Core.Compression.Core;
 using Rasad.Core.Compression.LZW;
 
 class MainClass
 {
 	public static void Main(string[] args)
 	{
			using (Stream inStream = new LzwInputStream(File.OpenRead(args[0])))
			using (FileStream outStream = File.Create(Path.GetFileNameWithoutExtension(args[0]))) {
				byte[] buffer = new byte[4096];
				StreamUtils.Copy(inStream, outStream, buffer);
						 // OR
						 inStream.Read(buffer, 0, buffer.Length);
						 // now do something with the buffer
 		}
 	}
 }	