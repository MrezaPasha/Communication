package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.Compression.Encryption.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;

// ZipInputStream.cs
//
// Copyright (C) 2001 Mike Krueger
// Copyright (C) 2004 John Reilly
//
// This file was translated from java, it was part of the GNU Classpath
// Copyright (C) 2001 Free Software Foundation, Inc.
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// Linking this library statically or dynamically with other modules is
// making a combined work based on this library.  Thus, the terms and
// conditions of the GNU General Public License cover the whole
// combination.
// 
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent
// modules, and to copy and distribute the resulting executable under
// terms of your choice, provided that you also meet, for each linked
// independent module, the terms and conditions of the license of that
// module.  An independent module is a module which is not derived from
// or based on this library.  If you modify this library, you may extend
// this exception to your version of the library, but you are not
// obligated to do so.  If you do not wish to do so, delete this
// exception statement from your version.

// HISTORY
//	2010-05-25	Z-1663	Fixed exception when testing local header compressed size of -1



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
//#endif


/** 
 This is an InflaterInputStream that reads the files baseInputStream an zip archive
 one after another.  It has a special method to get the zip entry of
 the next file.  The zip entry contains information about the file name
 size, compressed size, Crc, etc.
 It includes support for Stored and Deflated entries.
 <br/>
 <br/>Author of the original java version : Jochen Hoenicke
 
 
 <example> This sample shows how to read a zip file
 <code lang="C#">
 using System;
 using System.Text;
 using System.IO;
 
 using Rasad.Core.Compression.Zip;
 
 class MainClass
 {
 	public static void Main(string[] args)
 	{
 		using ( ZipInputStream s = new ZipInputStream(File.OpenRead(args[0]))) {

 			ZipEntry theEntry;
 			const int size = 2048;
 			byte[] data = new byte[2048];
 			
 			while ((theEntry = s.GetNextEntry()) != null) {
				 if ( entry.IsFile ) {
 					Console.Write("Show contents (y/n) ?");
 					if (Console.ReadLine() == "y") {
 						while (true) {
 							size = s.Read(data, 0, data.Length);
 							if (size > 0) {
 								Console.Write(new ASCIIEncoding().GetString(data, 0, size));
 							} else {
 								break;
 							}
 						}
 					}
 				}
 			}
 		}
 	}