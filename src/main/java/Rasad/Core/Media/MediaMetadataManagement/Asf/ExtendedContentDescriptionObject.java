package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// ExtendedContentDescriptionObject.cs: Provides a representation of an ASF
// Extended Content Description object which can be read from and written to
// disk.
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
	representation of an ASF Extended Content Description object
	which can be read from and written to disk.
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class ExtendedContentDescriptionObject extends Object implements java.lang.Iterable<ContentDescriptor>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the content descriptors.
	*/
	private ArrayList<ContentDescriptor> descriptors = new ArrayList<ContentDescriptor> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="ExtendedContentDescriptionObject" /> by reading the
		contents from a specified position in a specified file.
	 
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
		or smaller than the minimum size.
	 
	*/
	public ExtendedContentDescriptionObject(Asf.File file, long position)
	{
		super(file, position);
		if (!UUID.equals(Asf.Guid.AsfExtendedContentDescriptionObject))
		{
			throw new CorruptFileException("Object GUID incorrect.");
		}

		if (getOriginalSize() < 26)
		{
			throw new CorruptFileException("Object size too small.");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort count = file.ReadWord();
		short count = file.ReadWord();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: for (ushort i = 0; i < count; i ++)
		for (short i = 0; i < count; i++)
		{
			AddDescriptor(new ContentDescriptor(file));
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ExtendedContentDescriptionObject" /> with no
		contents.
	*/
	public ExtendedContentDescriptionObject()
	{
		super(Asf.Guid.AsfExtendedContentDescriptionObject);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if the current instance doesn't
		contain any <see cref="ContentDescriptor" /> objects.
		Otherwise <see langword="false" />.
	 </value>
	*/
	public final boolean getIsEmpty()
	{
		return descriptors.isEmpty();
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
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort count = 0;
		short count = 0;

		for (ContentDescriptor desc : descriptors)
		{
			count++;
			output.add(desc.Render());
		}

		return Render(Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpAddition(RenderWord(count), output));
	}

	/** 
		Removes all descriptors with a given name from the
		current instance.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		descriptors to be removed.
	 
	*/
	public final void RemoveDescriptors(String name)
	{
		for (int i = descriptors.size() - 1; i >= 0; i--)
		{
			if (descriptors.get(i).getName().equals(name))
			{
				descriptors.remove(i);
			}
		}
	}

	/** 
		Gets all descriptors with any of a collection of names
		from the current instance.
	 
	 @param names
		A <see cref="string[]" /> containing the names of the
		descriptors to be retrieved.
	 
	 @exception ArgumentNullException
		<paramref name="names" /> is <see langword="null" />.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating
		through the <see cref="ContentDescriptor" /> objects
		retrieved from the current instance.
	 
	*/
	public final java.lang.Iterable<ContentDescriptor> GetDescriptors(String... names)
	{
		if (names == null)
		{
			throw new NullPointerException("names");
		}

		for (String name : names)
		{
			for (ContentDescriptor desc : descriptors)
			{
				if (desc.getName().equals(name))
				{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
					yield return desc;
				}
			}
		}
	}

	/** 
		Adds a descriptor to the current instance.
	 
	 @param descriptor
		A <see cref="ContentDescriptor" /> object to add to the
		current instance.
	 
	 @exception ArgumentNullException
		<paramref name="descriptor" /> is <see langword="null"
		/>.
	 
	*/
	public final void AddDescriptor(ContentDescriptor descriptor)
	{
		if (descriptor == null)
		{
			throw new NullPointerException("descriptor");
		}

		descriptors.add(descriptor);
	}

	/** 
		Sets the a collection of desciptors for a given name,
		removing the existing matching records.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		descriptors to be added.
	 
	 @param descriptors
		A <see cref="ContentDescriptor[]" /> containing
		descriptors to add to the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="name" /> is <see langword="null" />.
	 
	 
		All added entries in <paramref name="descriptors" />
		should match <paramref name="name" /> but it is not
		verified by the method. The descriptors will be added
		with their own names and not the one provided in this
		method, which are used for removing existing values and
		determining where to position the new objects.
	 
	*/
	public final void SetDescriptors(String name, ContentDescriptor... descriptors)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		int position = this.descriptors.size();
		for (int i = this.descriptors.size() - 1; i >= 0; i--)
		{
			if (this.descriptors.get(i).getName().equals(name))
			{
				this.descriptors.remove(i);
				position = i;
			}
		}
		this.descriptors.addAll(position, Arrays.asList(descriptors));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IEnumerable

	/** 
		Gets an enumerator for enumerating through the content
		descriptors.
	 
	 @return 
		A <see cref="T:System.Collections.IEnumerator`1" /> for
		enumerating through the content descriptors.
	 
	*/
	public final Iterator<ContentDescriptor> iterator()
	{
		return descriptors.iterator();
	}

	public final Iterator GetEnumerator()
	{
		return descriptors.iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}