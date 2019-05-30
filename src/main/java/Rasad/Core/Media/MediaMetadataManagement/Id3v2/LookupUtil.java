package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// MusicCdIdentifierFrame.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
// 
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
// 
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//



/** 
	This class extends <see cref="Frame" />, implementing support for
	ID3v2 Music CD Identifier (MCDI) Frames.
 
 
	Music CD Identifier Frames should contain the table of
	contents data as stored on the physical CD. It is primarily used
	for track information lookup by through web sources like CDDB.
 
 <example>
	<p>Reading the music CD identifier from a tag.</p>
	<code lang="C#">
 using Rasad.Core.Media.MediaMetadataManagement;
 using Rasad.Core.Media.MediaMetadataManagement.Id3v2;
 
 public static class LookupUtil
 {
 	public static ByteVector GetCdIdentifier (string filename)
 	{
 		File file = File.Create (filename, ReadStyle.None);
 		Id3v2.Tag tag = file.GetTag (TagTypes.Id3v2, false) as Id3v2.Tag;
 		if (tag == null)
 			return new ByteVector ();
 		
 		MusicCdIdentifierFrame frame = MusicCdIdentifierFrame.Get (tag, false);
 		if (frame == null)
 			return new ByteVector ();

 		return frame.Data;
 	}
 }