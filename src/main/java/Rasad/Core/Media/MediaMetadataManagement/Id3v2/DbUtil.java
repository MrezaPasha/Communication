package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// PrivateFrame.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2005-2007 Brian Nickel
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
	This class extends <see cref="Frame" />, implementing support for
	ID3v2 Private (PRIV) Frames.
 
 
	<p>A <see cref="PrivateFrame" /> should be used for storing
	values specific to the application that cannot or should not be
	stored in another frame type.</p>
 
 <example>
	<p>Serializing a database entry and storing it in a private
	field.</p>
	<code lang="C#">
 using System;
 using System.IO;
 using System.Runtime.Serialization;
 using System.Text;
 using System.Xml.Serialization;
 using Rasad.Core.Media.MediaMetadataManagement.Id3v2;

 public static class DbUtil
 {
 	public static void StoreDatabaseEntry (Tag tag, ISerializable dbEntry)
 	{
 		StringWriter data = new StringWriter (new StringBuilder ());
 		XmlSerializer serializer = new XmlSerializer (dbEntry.GetType ());
 		serializer.Serialize (data, dbEntry);
 		PrivateFrame frame = PrivateFrame.Get (tag, "org.MyProgram.DatabaseEntry", true);
 		frame.PrivateData = Encoding.UTF8.GetBytes (data.ToString ());
 	}
 	
 	public static object GetDatabaseEntry (Tag tag, Type type)
 	{
 		PrivateFrame frame = PrivateFrame.Get (tag, "org.MyProgram.DatabaseEntry", false);
 		if (frame == null)
 			return null;
 	
 		XmlSerializer serializer = new XmlSerializer (type);
 		return serializer.Deserialize (new MemoryStream (frame.PrivateData));
 	}
 }