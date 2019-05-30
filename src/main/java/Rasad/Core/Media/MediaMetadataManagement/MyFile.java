package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;

//
// SupportedMimeType.cs:
//
// Author:
//   Aaron Bockover (abockover@novell.com)
//
// Original Source:
//   Entagged#
//
// Copyright (C) 2006 Novell, Inc.
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
	This class provides an attribute for listing supported mime-types
	for classes that extend <see cref="File" />.
 
 
	When classes that extend <see cref="File" /> are registered with
	<see cref="FileTypes.Register" />, its <see
	cref="SupportedMimeType" /> attributes are read.
 
 <example>
	<code lang="C#">using Rasad.Core.Media.MediaMetadataManagement;

[SupportedMimeType("taglib/wv", "wv")]
[SupportedMimeType("audio/x-wavpack")]
public class MyFile : File {
	...
}</code>