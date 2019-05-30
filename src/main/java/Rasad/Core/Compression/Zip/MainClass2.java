package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;

// ZipOutputStream.cs
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
//	22-12-2009	Z-1649	Added AES support
//	22-02-2010	Z-1648	Zero byte entries would create invalid zip files
//	27-07-2012	Z-1724	Compressed size was incorrect in local header when CRC and Size are known




/** 
 This is a DeflaterOutputStream that writes the files into a zip
 archive one after another.  It has a special method to start a new
 zip entry.  The zip entries contains information about the file name
 size, compressed size, CRC, etc.
 
 It includes support for Stored and Deflated entries.
 This class is not thread safe.
 <br/>
 <br/>Author of the original java version : Jochen Hoenicke
 
 <example> This sample shows how to create a zip file
 <code>
 using System;
 using System.IO;
 
 using Rasad.Core.Compression.Core;
 using Rasad.Core.Compression.Zip;
 
 class MainClass
 {
 	public static void Main(string[] args)
 	{
 		string[] filenames = Directory.GetFiles(args[0]);
 		byte[] buffer = new byte[4096];
 		
 		using ( ZipOutputStream s = new ZipOutputStream(File.Create(args[1])) ) {
 		
 			s.SetLevel(9); // 0 - store only to 9 - means best compression
 		
 			foreach (string file in filenames) {
 				ZipEntry entry = new ZipEntry(file);
 				s.PutNextEntry(entry);

 				using (FileStream fs = File.OpenRead(file)) {
						StreamUtils.Copy(fs, s, buffer);
 				}
 			}
 		}
 	}
 }	