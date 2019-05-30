package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// HeaderObject.cs: Provides a representation of an ASF Header object which can
// be read from and written to disk.
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
	representation of an ASF Header object which can be read from and
	written to disk.
*/
public class HeaderObject extends Object
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the reserved header data.
	*/
	private ByteVector reserved;

	/** 
		Contains the child objects.
	*/
	private ArrayList<Object> children;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="HeaderObject" /> by reading the contents from a
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
	 
	 @exception CorruptFileException
		The object read from disk does not have the correct GUID
		or smaller than the minimum size.
	 
	*/
	public HeaderObject(Asf.File file, long position)
	{
		super(file, position);
		if (!UUID.equals(Asf.Guid.AsfHeaderObject))
		{
			throw new CorruptFileException("Object GUID incorrect.");
		}

		if (getOriginalSize() < 26)
		{
			throw new CorruptFileException("Object size too small.");
		}

		children = new ArrayList<Object> ();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint child_count = file.ReadDWord();
		int child_count = file.ReadDWord();

		reserved = file.ReadBlock(2);

		children.addAll(file.ReadObjects(child_count, file.Tell));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the header extension object contained in the
		current instance.
	 
	 <value>
		A <see cref="HeaderExtensionObject" /> object containing
		the header extension object.
	 </value>
	*/
	public final HeaderExtensionObject getExtension()
	{
		for (Object child : children)
		{
			if (child instanceof HeaderExtensionObject)
			{
				return child instanceof HeaderExtensionObject ? (HeaderExtensionObject)child : null;
			}
		}
		return null;
	}

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

	/** 
		Gets the media properties contained within the current
		instance.
	 
	 <value>
		A <see cref="Properties" /> object containing the media
		properties of the current instance.
	 </value>
	*/
	public final Properties getProperties()
	{
		TimeSpan duration = TimeSpan.Zero;
		ArrayList<ICodec> codecs = new ArrayList<ICodec> ();

		for (Object obj : getChildren())
		{
			FilePropertiesObject fpobj = obj instanceof FilePropertiesObject ? (FilePropertiesObject)obj : null;

			if (fpobj != null)
			{
				duration = fpobj.getPlayDuration() - new TimeSpan((long) fpobj.getPreroll());
				continue;
			}

			StreamPropertiesObject spobj = obj instanceof StreamPropertiesObject ? (StreamPropertiesObject)obj : null;

			if (spobj != null)
			{
				codecs.add(spobj.getCodec());
				continue;
			}
		}

		return new Properties(duration, codecs);
	}

	/** 
		Gets whether or not the current instance contains either
		type of content descriptiors.
	 
	 <value>
		<see langword="true" /> if the current instance contains
		a <see cref="ContentDescriptionObject" /> or a <see
		cref="ExtendedContentDescriptionObject" />. Otherwise
		<see langword="false" />.
	 </value>
	*/
	public final boolean getHasContentDescriptors()
	{
		for (Asf.Object child : children)
		{
			if (child.getGuid().equals(Asf.Guid.AsfContentDescriptionObject) || child.getGuid().equals(Asf.Guid.AsfExtendedContentDescriptionObject))
			{
				return true;
			}
		}

		return false;
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
//ORIGINAL LINE: uint child_count = 0;
		int child_count = 0;

		for (Object child : children)
		{
			if (!child.getGuid().equals(Asf.Guid.AsfPaddingObject))
			{
				output.add(child.Render());
				child_count++;
			}
		}

		long size_diff = (long) output.size() + 30 - (long) getOriginalSize();

		if (size_diff != 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: PaddingObject obj = new PaddingObject((uint)(size_diff > 0 ? 4096 : - size_diff));
			PaddingObject obj = new PaddingObject((int)(size_diff > 0 ? 4096 : - size_diff));

			output.add(obj.Render());
			child_count++;
		}

		output.add(0, reserved);
		output.add(0, RenderDWord(child_count));
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
			if (children.get(i).getGuid().equals(obj.getGuid()))
			{
				children.set(i, obj);
				return;
			}
		}

		children.add(obj);
	}

	/** 
		Removes the content description objects from the current
		instance.
	*/
	public final void RemoveContentDescriptors()
	{
		for (int i = children.size() - 1; i >= 0; i--)
		{
			if (children.get(i).getGuid().equals(Asf.Guid.AsfContentDescriptionObject) || children.get(i).getGuid().equals(Asf.Guid.AsfExtendedContentDescriptionObject))
			{
				children.remove(i);
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}