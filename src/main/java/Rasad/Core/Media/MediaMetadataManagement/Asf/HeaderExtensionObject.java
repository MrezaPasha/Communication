package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// HeaderExtensionObject.cs: Provides a representation of an ASF Header
// Extension object which can be read from and written to disk.
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
	This class extends <see cref="Object" /> to provide a
	representation of an ASF Header Extension object which can be
	read from and written to disk.
*/
public class HeaderExtensionObject extends Object
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the child objects.
	*/
	private ArrayList<Object> children = new ArrayList<Object> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="HeaderExtensionObject" /> by reading the contents
		from a specified position in a specified file.
	 
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
	 
	 @exception CorruptFileException
		The object read from disk does not have the correct GUID
		or contents.
	 
	*/
	public HeaderExtensionObject(Asf.File file, long position)
	{
		super(file, position);
		if (!UUID.equals(Asf.Guid.AsfHeaderExtensionObject))
		{
			throw new CorruptFileException("Object GUID incorrect.");
		}

		if (!file.ReadGuid().equals(Asf.Guid.AsfReserved1))
		{
			throw new CorruptFileException("Reserved1 GUID expected.");
		}

		if (file.ReadWord() != 6)
		{
			throw new CorruptFileException("Invalid reserved WORD. Expected '6'.");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint size_remaining = file.ReadDWord();
		int size_remaining = file.ReadDWord();
		position += 0x170 / 8;

		while (size_remaining > 0)
		{
			Object obj = file.ReadObject(position);
			position += (Long) obj.getOriginalSize();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: size_remaining -= (uint) obj.OriginalSize;
			size_remaining -= (int) obj.getOriginalSize();
			children.add(obj);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the child objects contained in the current instance.
	 
	 <value>
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating
		through the children of the current instance.
	 </value>
	*/
	public final java.lang.Iterable<Object> getChildren()
	{
		return children;
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
	 
	*/
	@Override
	public ByteVector Render()
	{
		ByteVector output = new ByteVector();

		for (Object child : children)
		{
			output.add(child.Render());
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: output.Insert(0, RenderDWord((uint) output.Count));
		output.add(0, RenderDWord((int) output.size()));
		output.add(0, RenderWord((short)6));
		output.add(0, Asf.Guid.AsfReserved1.ToByteArray());

		return Render(output);
	}

	/** 
		Adds a child object to the current instance.
	 
	 @param obj
		A <see cref="Object" /> object to add to the current
		instance.
	 
	*/
	public final void AddObject(Object obj)
	{
		children.add(obj);
	}

	/** 
		Adds a child unique child object to the current instance,
		replacing and existing child if present.
	 
	 @param obj
		A <see cref="Object" /> object to add to the current
		instance.
	 
	*/
	public final void AddUniqueObject(Object obj)
	{
		for (int i = 0; i < children.size(); i++)
		{
			if (((Object) children.get(i)).getGuid().equals(obj.getGuid()))
			{
				children.set(i, obj);
				return;
			}
		}

		children.add(obj);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}