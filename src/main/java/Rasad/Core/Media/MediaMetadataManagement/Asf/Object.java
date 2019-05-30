package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Object.cs: Provides a basic representation of an ASF object which can be read
// from and written to disk.
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
	This abstract class provides a basic representation of an ASF
	object which can be read from and written to disk.
*/
public abstract class Object
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the GUID of the object.
	*/
	private UUID id;

	/** 
		Contains the size of the object on disk.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong size;
	private long size;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Object" /> by reading the contents from a
		specified position in a specified file.
	 
	 @param file
		A <see cref="Asf.File" /> object containing the file from
		which the contents of the new instance are to be read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read the object.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	*/
	protected Object(Asf.File file, long position)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (position < 0 || position > file.Length - 24)
		{
			throw new IndexOutOfBoundsException("position");
		}

		file.Seek(position);
		id = file.ReadGuid();
		size = file.ReadQWord();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Object" /> with a specified GUID.
	 
	 @param guid
		A <see cref="System.Guid" /> value containing the GUID to
		use for the new instance.
	 
	*/
	protected Object(UUID guid)
	{
		id = guid;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the GUID for the current instance.
	 
	 <value>
		A <see cref="System.Guid" /> object containing the GUID
		of the current instance.
	 </value>
	*/
	public final UUID getGuid()
	{
		return id;
	}

	/** 
		Gets the original size of the current instance.
	 
	 <value>
		A <see cref="ulong" /> value containing the size of the
		current instance as it originally appeared on disk.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getOriginalSize()
	public final long getOriginalSize()
	{
		return size;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Renders the current instance as a raw ASF object.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	 {@link Render(ByteVector) }
	*/
	public abstract ByteVector Render();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Renders a Unicode (wide) string.
	 
	 @param value
		A <see cref="string" /> object containing the text to
		render.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered value.
	 
	*/
	public static ByteVector RenderUnicode(String value)
	{
		ByteVector v = ByteVector.FromString(value, StringType.UTF16LE);
		v.add(RenderWord((short)0));
		return v;
	}

	/** 
		Renders a 4-byte DWORD.
	 
	 @param value
		A <see cref="uint" /> value containing the DWORD to
		render.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered value.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector RenderDWord(uint value)
	public static ByteVector RenderDWord(int value)
	{
		return ByteVector.FromUInt(value, false);
	}

	/** 
		Renders a 8-byte QWORD.
	 
	 @param value
		A <see cref="ulong" /> value containing the QWORD to
		render.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered value.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector RenderQWord(ulong value)
	public static ByteVector RenderQWord(long value)
	{
		return ByteVector.FromULong(value, false);
	}

	/** 
		Renders a 2-byte WORD.
	 
	 @param value
		A <see cref="ushort" /> value containing the WORD to
		render.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered value.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector RenderWord(ushort value)
	public static ByteVector RenderWord(short value)
	{
		return ByteVector.FromUShort(value, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Renders the current instance as a raw ASF object
		containing specified data.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the data to
		contained in the rendered version of the current
		instance.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	 
		Child classes implementing <see cref="Render()" /> should
		render their contents and then send the data through this
		method to produce the final output.
	 
	*/
	protected final ByteVector Render(ByteVector data)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong length = (ulong)((data != null ? data.Count : 0) + 24);
		long length = (long)((data != null ? data.size() : 0) + 24);
		ByteVector v = id.ToByteArray();
		v.add(RenderQWord(length));
		v.add(data);
		return v;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}