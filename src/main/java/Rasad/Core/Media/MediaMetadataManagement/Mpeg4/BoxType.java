package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// BoxTypes.cs: Contains common box names.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2006-2007 Brian Nickel
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
	<see cref="BoxType" /> provides references to different box types
	used by the library.
 
 
	<p>This class is used to severely reduce the number of times
	these types are created in <see cref="AppleTag" />, greatly
	improving the speed at which warm files are read.</p>
	<p>The reason it is marked as internal is because I'm not sure
	I like the way the fields are named, and it is really
	unneccessary for external uses. While the library may use
	<c>DataBoxes (BoxType.Gen, BoxType.Gnre);</c>, an external user
	could use <c>tag.DataBoxes ("gen", "gnre");</c> with the same
	result.</p>
 
*/
public final class BoxType
{
	public static final ReadOnlyByteVector Aart = "aART";
	public static final ReadOnlyByteVector Alb = AppleTag.FixId("alb");
	public static final ReadOnlyByteVector Art = AppleTag.FixId("ART");
	public static final ReadOnlyByteVector Cmt = AppleTag.FixId("cmt");
	public static final ReadOnlyByteVector Cond = "cond";
	public static final ReadOnlyByteVector Covr = "covr";
	public static final ReadOnlyByteVector Co64 = "co64";
	public static final ReadOnlyByteVector Cpil = "cpil";
	public static final ReadOnlyByteVector Cprt = "cprt";
	public static final ReadOnlyByteVector Data = "data";
	public static final ReadOnlyByteVector Day = AppleTag.FixId("day");
	public static final ReadOnlyByteVector Disk = "disk";
	public static final ReadOnlyByteVector Esds = "esds";
	public static final ReadOnlyByteVector Ilst = "ilst";
	public static final ReadOnlyByteVector Free = "free";
	public static final ReadOnlyByteVector Gen = AppleTag.FixId("gen");
	public static final ReadOnlyByteVector Gnre = "gnre";
	public static final ReadOnlyByteVector Grp = AppleTag.FixId("grp");
	public static final ReadOnlyByteVector Hdlr = "hdlr";
	public static final ReadOnlyByteVector Lyr = AppleTag.FixId("lyr");
	public static final ReadOnlyByteVector Mdat = "mdat";
	public static final ReadOnlyByteVector Mdia = "mdia";
	public static final ReadOnlyByteVector Meta = "meta";
	public static final ReadOnlyByteVector Mean = "mean";
	public static final ReadOnlyByteVector Minf = "minf";
	public static final ReadOnlyByteVector Moov = "moov";
	public static final ReadOnlyByteVector Mvhd = "mvhd";
	public static final ReadOnlyByteVector Nam = AppleTag.FixId("nam");
	public static final ReadOnlyByteVector Name = "name";
	public static final ReadOnlyByteVector Skip = "skip";
	public static final ReadOnlyByteVector Soaa = "soaa"; // Album Artist Sort
	public static final ReadOnlyByteVector Soar = "soar"; // Performer Sort
	public static final ReadOnlyByteVector Soco = "soco"; // Composer Sort
	public static final ReadOnlyByteVector Sonm = "sonm"; // Track Title Sort
	public static final ReadOnlyByteVector Soal = "soal"; // Album Title Sort
	public static final ReadOnlyByteVector Stbl = "stbl";
	public static final ReadOnlyByteVector Stco = "stco";
	public static final ReadOnlyByteVector Stsd = "stsd";
	public static final ReadOnlyByteVector Text = "text";
	public static final ReadOnlyByteVector Tmpo = "tmpo";
	public static final ReadOnlyByteVector Trak = "trak";
	public static final ReadOnlyByteVector Trkn = "trkn";
	public static final ReadOnlyByteVector Udta = "udta";
	public static final ReadOnlyByteVector Url = AppleTag.FixId("url");
	public static final ReadOnlyByteVector Uuid = "uuid";
	public static final ReadOnlyByteVector Wrt = AppleTag.FixId("wrt");
	public static final ReadOnlyByteVector DASH = "----";

	// Handler types.
	public static final ReadOnlyByteVector Soun = "soun";
	public static final ReadOnlyByteVector Vide = "vide";

	// Another handler type, found in wild in audio file ripped using iTunes
	public static final ReadOnlyByteVector Alis = "alis";
}