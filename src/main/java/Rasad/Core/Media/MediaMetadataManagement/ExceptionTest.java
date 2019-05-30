package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.io.*;

//
// CorruptFileException.cs:
//
// Author:
//   Aaron Bockover (abockover@novell.com)
//
// Original Source:
//   Entagged#
//
// Copyright (C) 2006 Novell, Inc.
// 
// This library is free software; you can redistribute it and/or modify
// it  under the terms of the GNU Lesser General Public License version
// 2.1 as published by the Free Software Foundation.
//
// This library is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
// USA
//



/** 
	This class extends <see cref="Exception" /> and is used to
	indicate that a file or tag is corrupt.
 
 
	This exception will be thrown if invalid data interferes with the
	reading of the file or tag. One common example is in the (legal)
	downloading of media files with BitTorrent, in which case large
	portions of the file will contain zeroed bytes.
 
 <example>
	<p>Catching an exception when creating a <see
	cref="File" />.</p>
	<code lang="C#">
 using System;
 using Rasad.Core.Media.MediaMetadataManagement;

 public class ExceptionTest
 {
 	public static void Main ()
 	{
 		try {
 			File file = File.Create ("partial.mp3"); // Partial download.
 		} catch (CorruptFileException e) {
 			Console.WriteLine ("That file is corrupt: {0}", e.ToString ());
 		}
	}