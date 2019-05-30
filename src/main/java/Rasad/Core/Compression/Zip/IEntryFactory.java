package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Core.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;

// IEntryFactory.cs
//
// Copyright 2006 John Reilly
//
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
//	2012-11-29	Z-1684	Added MakeFileEntry(string fileName, string entryName, bool useFileSystem)



/** 
 Defines factory methods for creating new <see cref="ZipEntry"></see> values.
*/
public interface IEntryFactory
{
	/** 
	 Create a <see cref="ZipEntry"/> for a file given its name
	 
	 @param fileName The name of the file to create an entry for.
	 @return Returns a <see cref="ZipEntry">file entry</see> based on the <paramref name="fileName"/> passed.
	*/
	ZipEntry MakeFileEntry(String fileName);

	/** 
	 Create a <see cref="ZipEntry"/> for a file given its name
	 
	 @param fileName The name of the file to create an entry for.
	 @param useFileSystem If true get details from the file system if the file exists.
	 @return Returns a <see cref="ZipEntry">file entry</see> based on the <paramref name="fileName"/> passed.
	*/
	ZipEntry MakeFileEntry(String fileName, boolean useFileSystem);

	/** 
	 Create a <see cref="ZipEntry"/> for a file given its actual name and optional override name
	 
	 @param fileName The name of the file to create an entry for.
	 @param entryName An alternative name to be used for the new entry. Null if not applicable.
	 @param useFileSystem If true get details from the file system if the file exists.
	 @return Returns a <see cref="ZipEntry">file entry</see> based on the <paramref name="fileName"/> passed.
	*/
	ZipEntry MakeFileEntry(String fileName, String entryName, boolean useFileSystem);

	/** 
	 Create a <see cref="ZipEntry"/> for a directory given its name
	 
	 @param directoryName The name of the directory to create an entry for.
	 @return Returns a <see cref="ZipEntry">directory entry</see> based on the <paramref name="directoryName"/> passed.
	*/
	ZipEntry MakeDirectoryEntry(String directoryName);

	/** 
	 Create a <see cref="ZipEntry"/> for a directory given its name
	 
	 @param directoryName The name of the directory to create an entry for.
	 @param useFileSystem If true get details from the file system for this directory if it exists.
	 @return Returns a <see cref="ZipEntry">directory entry</see> based on the <paramref name="directoryName"/> passed.
	*/
	ZipEntry MakeDirectoryEntry(String directoryName, boolean useFileSystem);

	/** 
	 Get/set the <see cref="INameTransform"></see> applicable.
	*/
	INameTransform getNameTransform();
	void setNameTransform(INameTransform value);
}