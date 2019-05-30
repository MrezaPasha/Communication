package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.io.*;

//
// UnsupportedFormatException.cs:
//
// Author:
//   Aaron Bockover (abockover@novell.com)
//
// Original Source:
//   Entagged#
//
// Copyright (C) 2005-2006 Novell, Inc.
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
	indicate that a file or tag is stored in an unsupported format
	and cannot be read or written by the current implementation.
 
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
 			File file = File.Create ("myfile.flv"); // Not supported, YET!
 		} catch (UnsupportedFormatException e) {
 			Console.WriteLine ("That file format is not supported: {0}", e.ToString ());
 		}
	}