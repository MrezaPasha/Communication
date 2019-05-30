package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;

//
// FileTypes.cs: Provides a mechanism for registering file classes and mime-
// types, to be used when constructing a class via Rasad.Core.Media.MediaMetadataManagement.File.Create.
//
// Author:
//   Aaron Bockover (abockover@novell.com)
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
	This static class provides a mechanism for registering file
	classes and mime-types, to be used when constructing a class via
	<see cref="File.Create(string)" />.
 
 
	<p>The default types built into the taglib-sharp.dll assembly
	are registered automatically when the class is initialized. To
	register your own custom types, use <see cref="Register"
	/>.</p>
 
 {@link SupportedMimeType }
*/
public final class FileTypes
{
	/** 
		Contains a mapping between mime-types and the <see
		cref="File" /> subclasses that support them.
	*/
	private static HashMap<String, java.lang.Class> file_types;

	/** 
		Contains a static array of file types contained in the
		Rasad.Core.Media.MediaMetadataManagement# assembly.
	 
	 
		A static Type array is used instead of getting types by
		reflecting the executing assembly as Assembly.GetTypes is
		very inefficient and leaks every type instance under
		Mono. Not reflecting taglib-sharp.dll saves about 120KB
		of heap.
	 
	*/
	private static java.lang.Class [] static_file_types = new java.lang.Class [] {Rasad.Core.Media.MediaMetadataManagement.Aac.File.class, Rasad.Core.Media.MediaMetadataManagement.Aiff.File.class, Rasad.Core.Media.MediaMetadataManagement.Ape.File.class, Rasad.Core.Media.MediaMetadataManagement.Asf.File.class, Rasad.Core.Media.MediaMetadataManagement.Audible.File.class, Rasad.Core.Media.MediaMetadataManagement.Flac.File.class, Rasad.Core.Media.MediaMetadataManagement.Matroska.File.class, Rasad.Core.Media.MediaMetadataManagement.Gif.File.class, Rasad.Core.Media.MediaMetadataManagement.Image.NoMetadata.File.class, Rasad.Core.Media.MediaMetadataManagement.Jpeg.File.class, Rasad.Core.Media.MediaMetadataManagement.Mpeg4.File.class, Rasad.Core.Media.MediaMetadataManagement.Mpeg.AudioFile.class, Rasad.Core.Media.MediaMetadataManagement.Mpeg.File.class, Rasad.Core.Media.MediaMetadataManagement.MusePack.File.class, Rasad.Core.Media.MediaMetadataManagement.Ogg.File.class, Rasad.Core.Media.MediaMetadataManagement.Png.File.class, Rasad.Core.Media.MediaMetadataManagement.Riff.File.class, Rasad.Core.Media.MediaMetadataManagement.Tiff.Arw.File.class, Rasad.Core.Media.MediaMetadataManagement.Tiff.Cr2.File.class, Rasad.Core.Media.MediaMetadataManagement.Tiff.Dng.File.class, Rasad.Core.Media.MediaMetadataManagement.Tiff.File.class, Rasad.Core.Media.MediaMetadataManagement.Tiff.Nef.File.class, Rasad.Core.Media.MediaMetadataManagement.Tiff.Pef.File.class, Rasad.Core.Media.MediaMetadataManagement.Tiff.Rw2.File.class, Rasad.Core.Media.MediaMetadataManagement.WavPack.File.class};

	/** 
		Constructs and initializes the <see cref="FileTypes" />
		class by registering the default types.
	*/
	static
	{
		Init();
	}

	/** 
		Initializes the class by registering the default types.
	*/
	public static void Init()
	{
		if (file_types != null)
		{
			return;
		}

		file_types = new HashMap<String, java.lang.Class>();

		for (java.lang.Class type : static_file_types)
		{
			Register(type);
		}
	}

	/** 
		Registers a <see cref="File" /> subclass to be used when
		creating files via <see cref="File.Create(string)" />.
	 
	 @param type
		A <see cref="Type" /> object for the class to register.
	 
	 
		In order to register mime-types, the class represented by
		<paramref name="type" /> should use the <see
		cref="SupportedMimeType" /> custom attribute.
	 
	*/
	public static void Register(java.lang.Class type)
	{
		Attribute [] attrs = Attribute.GetCustomAttributes(type, SupportedMimeType.class, false);

		if (attrs == null || attrs.length == 0)
		{
			return;
		}

		for (SupportedMimeType attr : attrs)
		{
			file_types.put(attr.getMimeType(), type);
		}
	}

	/** 
		Gets a dictionary containing all the supported mime-types
		and file classes used by <see cref="File.Create(string)"
		/>.
	 
	 <value>
		A <see cref="T:System.Collections.Generic.IDictionary`2" /> object containing the
		supported mime-types.
	 </value>
	*/
	public static Map<String, java.lang.Class> getAvailableTypes()
	{
		return file_types;
	}
}