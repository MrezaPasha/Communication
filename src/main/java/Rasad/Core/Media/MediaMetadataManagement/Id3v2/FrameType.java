package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// FrameTypes.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
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
	   <see cref="FrameType" /> provides references to different frame
	   types used by the library.
	
	
	   <p>This class is used to severely reduce the number of times
	   these types are created in <see cref="Rasad.Core.Media.MediaMetadataManagement.Id3v2.Tag" />,
	   greatly improving the speed at which warm files are read. It is,
	   however, not necessary for external users to use this class. While
	   the library may use <c>GetTextAsString (FrameType.TIT2);</c> an
	   external user could use <c>tag.GetTextAsString ("TIT2");</c> with
	   the same result.</p>
	
   */
public final class FrameType
{
	public static final ReadOnlyByteVector APIC = "APIC";
	public static final ReadOnlyByteVector COMM = "COMM";
	public static final ReadOnlyByteVector EQUA = "EQUA";
	public static final ReadOnlyByteVector GEOB = "GEOB";
	public static final ReadOnlyByteVector MCDI = "MCDI";
	public static final ReadOnlyByteVector PCNT = "PCNT";
	public static final ReadOnlyByteVector POPM = "POPM";
	public static final ReadOnlyByteVector PRIV = "PRIV";
	public static final ReadOnlyByteVector RVA2 = "RVA2";
	public static final ReadOnlyByteVector RVAD = "RVAD";
	public static final ReadOnlyByteVector SYLT = "SYLT";
	public static final ReadOnlyByteVector TALB = "TALB";
	public static final ReadOnlyByteVector TBPM = "TBPM";
	public static final ReadOnlyByteVector TCOM = "TCOM";
	public static final ReadOnlyByteVector TCON = "TCON";
	public static final ReadOnlyByteVector TCOP = "TCOP";
	public static final ReadOnlyByteVector TCMP = "TCMP";
	public static final ReadOnlyByteVector TDRC = "TDRC";
	public static final ReadOnlyByteVector TDAT = "TDAT";
	public static final ReadOnlyByteVector TEXT = "TEXT";
	public static final ReadOnlyByteVector TIT1 = "TIT1";
	public static final ReadOnlyByteVector TIT2 = "TIT2";
	public static final ReadOnlyByteVector TIME = "TIME";
	public static final ReadOnlyByteVector TOLY = "TOLY";
	public static final ReadOnlyByteVector TOPE = "TOPE";
	public static final ReadOnlyByteVector TPE1 = "TPE1";
	public static final ReadOnlyByteVector TPE2 = "TPE2";
	public static final ReadOnlyByteVector TPE3 = "TPE3";
	public static final ReadOnlyByteVector TPE4 = "TPE4";
	public static final ReadOnlyByteVector TPOS = "TPOS";
	public static final ReadOnlyByteVector TRCK = "TRCK";
	public static final ReadOnlyByteVector TRDA = "TRDA";
	public static final ReadOnlyByteVector TSIZ = "TSIZ";
	public static final ReadOnlyByteVector TSOA = "TSOA"; // Album Title Sort Frame
	public static final ReadOnlyByteVector TSO2 = "TSO2"; // Album Artist Sort Frame
	public static final ReadOnlyByteVector TSOC = "TSOC"; // Composer Sort Frame
	public static final ReadOnlyByteVector TSOP = "TSOP"; // Performer Sort Frame
	public static final ReadOnlyByteVector TSOT = "TSOT"; // Track Title Sort Frame
	public static final ReadOnlyByteVector TXXX = "TXXX";
	public static final ReadOnlyByteVector TYER = "TYER";
	public static final ReadOnlyByteVector UFID = "UFID";
	public static final ReadOnlyByteVector USER = "USER";
	public static final ReadOnlyByteVector USLT = "USLT";
}