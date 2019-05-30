package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Guid.cs: Provides common GUID values used by ASF Objects.
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
	This static class contains common <see cref="System.Guid" />
	values used by ASF Objects.
*/
public final class UUID
{
	/** 
		Indicates that an object is a <see
		cref="ContentDescriptionObject" />.
	*/
	public static final UUID AsfContentDescriptionObject = UUID.fromString("75B22633-668E-11CF-A6D9-00AA0062CE6C");

	/** 
		Indicates that an object is a <see
		cref="ExtendedContentDescriptionObject" />.
	*/
	public static final UUID AsfExtendedContentDescriptionObject = UUID.fromString("D2D0A440-E307-11D2-97F0-00A0C95EA850");

	/** 
		Indicates that an object is a <see
		cref="FilePropertiesObject" />.
	*/
	public static final UUID AsfFilePropertiesObject = UUID.fromString("8CABDCA1-A947-11CF-8EE4-00C00C205365");

	/** 
		Indicates that an object is a <see
		cref="HeaderExtensionObject" />.
	*/
	public static final UUID AsfHeaderExtensionObject = UUID.fromString("5FBF03B5-A92E-11CF-8EE3-00C00C205365");

	/** 
		Indicates that an object is a <see
		cref="HeaderObject" />.
	*/
	public static final UUID AsfHeaderObject = UUID.fromString("75B22630-668E-11CF-A6D9-00AA0062CE6C");

	/** 
		Indicates that an object is a <see
		cref="MetadataLibraryObject" />.
	*/
	public static final UUID AsfMetadataLibraryObject = UUID.fromString("44231C94-9498-49D1-A141-1D134E457054");

	/** 
		Indicates that an object is a <see
		cref="PaddingObject" />.
	*/
	public static final UUID AsfPaddingObject = UUID.fromString("1806D474-CADF-4509-A4BA-9AABCB96AAE8");

	/** 
		Indicates that an object is a <see
		cref="StreamPropertiesObject" />.
	*/
	public static final UUID AsfStreamPropertiesObject = UUID.fromString("B7DC0791-A9B7-11CF-8EE6-00C00C205365");


	/** 
		Indicates that a <see cref="StreamPropertiesObject" />
		contains information about an audio stream.
	*/
	public static final UUID AsfAudioMedia = UUID.fromString("F8699E40-5B4D-11CF-A8FD-00805F5C442B");

	/** 
		Indicates that a <see cref="StreamPropertiesObject" />
		contains information about an video stream.
	*/
	public static final UUID AsfVideoMedia = UUID.fromString("BC19EFC0-5B4D-11CF-A8FD-00805F5C442B");

	/** 
		Indicates a placeholder portion of a file is correctly
		encoded.
	*/
	public static final UUID AsfReserved1 = UUID.fromString("ABD3D211-A9BA-11cf-8EE6-00C00C205365");
}