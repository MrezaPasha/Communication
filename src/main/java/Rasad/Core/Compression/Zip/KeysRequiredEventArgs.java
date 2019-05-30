package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Encryption.*;
import Rasad.Core.Compression.Core.*;
import Rasad.Core.Compression.Checksums.*;
import Rasad.Core.Compression.Zip.Compression.Streams.*;
import Rasad.Core.Compression.Zip.Compression.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;

// ZipFile.cs
//
// Copyright (C) 2001 Mike Krueger
// Copyright (C) 2004 John Reilly
//
// This file was translated from java, it was part of the GNU Classpath
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
//	2009-12-22	Z-1649	Added AES support
//	2010-03-02	Z-1650	Fixed updating ODT archives in memory. Exposed exceptions in updating.
//	2010-05-25	Z-1663	Fixed exception when testing local header compressed size of -1
//	2012-11-29	Z-1684	Fixed ZipFile.Add(string fileName, string entryName) losing the file TimeStamp


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0
//#endif



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Keys Required Event Args
/** 
 Arguments used with KeysRequiredEvent
*/
public class KeysRequiredEventArgs extends tangible.EventArgs
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a new instance of <see cref="KeysRequiredEventArgs"></see>
	 
	 @param name The name of the file for which keys are required.
	*/
	public KeysRequiredEventArgs(String name)
	{
		fileName = name;
	}

	/** 
	 Initialise a new instance of <see cref="KeysRequiredEventArgs"></see>
	 
	 @param name The name of the file for which keys are required.
	 @param keyValue The current key value.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public KeysRequiredEventArgs(string name, byte[] keyValue)
	public KeysRequiredEventArgs(String name, byte[] keyValue)
	{
		fileName = name;
		key = keyValue;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	/** 
	 Gets the name of the file for which keys are required.
	*/
	public final String getFileName()
	{
		return fileName;
	}

	/** 
	 Gets or sets the key value
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getKey()
	public final byte[] getKey()
	{
		return key;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setKey(byte[] value)
	public final void setKey(byte[] value)
	{
		key = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private String fileName;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] key;
	private byte[] key;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}