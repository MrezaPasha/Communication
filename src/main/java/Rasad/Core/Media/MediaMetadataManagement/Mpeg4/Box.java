package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Box.cs: Provides a generic implementation of a ISO/IEC 14496-12 box.
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
	This abstract class provides a generic implementation of a
	ISO/IEC 14496-12 box.
*/
public class Box
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the box header.
	*/
	private BoxHeader header = new BoxHeader();

	/** 
		Contains the box's handler, if applicable.
	*/
	private IsoHandlerBox handler;

	/** 
		Contains the position of the box data.
	*/
	private long data_position;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Box" /> with a specified header and handler.
	 
	 @param header
		A <see cref="BoxHeader" /> object describing the new
		instance.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new instance, or <see
		langword="null" /> if no handler applies.
	 
	*/
	protected Box(BoxHeader header, IsoHandlerBox handler)
	{
		this.header = header.clone();
		this.data_position = header.getPosition() + header.getHeaderSize();
		this.handler = handler;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Box" /> with a specified header.
	 
	 @param header
		A <see cref="BoxHeader" /> object describing the new
		instance.
	 
	*/
	protected Box(BoxHeader header)
	{
		this(header.clone(), null);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Box" /> with a specified box type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the box
		type to use for the new instance.
	 
	*/
	protected Box(ByteVector type)
	{
		this(new BoxHeader(type));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the MPEG-4 box type of the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the four
		byte box type of the current instance.
	 </value>
	*/
	public ByteVector getBoxType()
	{
		return header.getBoxType();
	}

	/** 
		Gets the total size of the current instance as it last
		appeared on disk.
	 
	 <value>
		A <see cref="int" /> value containing the total size of
		the current instance as it last appeared on disk.
	 </value>
	*/
	public int getSize()
	{
		return (int)header.getTotalBoxSize();
	}

	/** 
		Gets and sets the data contained in the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the data
		contained in the current instance.
	 </value>
	*/
	public ByteVector getData()
	{
		return null;
	}
	public void setData(ByteVector value)
	{
	}

	/** 
		Gets the children of the current instance.
	 
	 <value>
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating the
		children of the current instance.
	 </value>
	*/
	public java.lang.Iterable<Box> getChildren()
	{
		return null;
	}

	/** 
		Gets the handler box that applies to the current
		instance.
	 
	 <value>
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the current instance, or <see
		langword="null" /> if no handler applies.
	 </value>
	*/
	public final IsoHandlerBox getHandler()
	{
		return handler;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Renders the current instance, including its children, to
		a new <see cref="ByteVector" /> object.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	public final ByteVector Render()
	{
		return Render(new ByteVector());
	}

	/** 
		Gets a child box from the current instance by finding
		a matching box type.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the box
		type to match.
	 
	 @return 
		A <see cref="Box" /> object containing the matched box,
		or <see langword="null" /> if no matching box was found.
	 
	*/
	public final Box GetChild(ByteVector type)
	{
		if (getChildren() == null)
		{
			return null;
		}

		for (Box box : getChildren())
		{
			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(box.getBoxType(), type))
			{
				return box;
			}
		}

		return null;
	}

	/*
	/// <summary>
	///    Gets a child box from the current instance by finding
	///    a matching object type.
	/// </summary>
	/// <param name="type">
	///    A <see cref="System.Type" /> object containing the object
	///    type to match.
	/// </param>
	/// <returns>
	///    A <see cref="Box" /> object containing the matched box,
	///    or <see langword="null" /> if no matching box was found.
	/// </returns>
	public Box GetChild (System.Type type)
	{
		if (Children == null)
			return null;
		
		foreach (Box box in Children)
			if (box.GetType () == type)
				return box;
		
		return null;
	}
	*/

	/** 
		Gets a child box from the current instance by finding
		a matching box type, searching recursively.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the box
		type to match.
	 
	 @return 
		A <see cref="Box" /> object containing the matched box,
		or <see langword="null" /> if no matching box was found.
	 
	*/
	public final Box GetChildRecursively(ByteVector type)
	{
		if (getChildren() == null)
		{
			return null;
		}

		for (Box box : getChildren())
		{
			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(box.getBoxType(), type))
			{
				return box;
			}
		}

		for (Box box : getChildren())
		{
			Box child_box = box.GetChildRecursively(type);
			if (child_box != null)
			{
				return child_box;
			}
		}

		return null;
	}

	/*
	/// <summary>
	///    Gets a child box from the current instance by finding
	///    a matching object type, searching recursively.
	/// </summary>
	/// <param name="type">
	///    A <see cref="System.Type" /> object containing the object
	///    type to match.
	/// </param>
	/// <returns>
	///    A <see cref="Box" /> object containing the matched box,
	///    or <see langword="null" /> if no matching box was found.
	/// </returns>
	public Box GetChildRecursively (System.Type type)
	{
		if (Children == null)
			return null;
		
		foreach (Box box in Children)
			if (box.GetType () == type)
				return box;
		
		foreach (Box box in Children) {
			Box child_box = box.GetChildRecursively (type);
			if (child_box != null)
				return child_box;
		}
		
		return null;
	}
	*/

	/** 
		Removes all children with a specified box type from the
		current instance.
	 
	 @param type
		A <see cref="ByteVector" /> object containing the box
		type to remove.
	 
	*/
	public final void RemoveChild(ByteVector type)
	{
		java.lang.Iterable<Box> tempVar = getChildren();
		Collection<Box> children = tempVar instanceof Collection<Box> ? (Collection<Box>)tempVar : null;

		if (children == null)
		{
			return;
		}

		for (Box b : new ArrayList<Box> (children))
		{
			if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(b.getBoxType(), type))
			{
				children.remove(b);
			}
		}
	}

	/*
	/// <summary>
	///    Removes all children with a specified box type from the
	///    current instance.
	/// </summary>
	/// <param name="type">
	///    A <see cref="ByteVector" /> object containing the box
	///    type to remove.
	/// </param>
	public void RemoveChild (System.Type type)
	{
		ICollection<Box> children = Children as ICollection<Box>;
		
		if (children == null)
			return;
		
		foreach (Box b in new List<Box> (children))
			if (b.GetType () == type)
				children.Remove (b);
	}
	*/

	/** 
		Removes a specified box from the current instance.
	 
	 @param box
		A <see cref="Box" /> object to remove from the current
		instance.
	 
	*/
	public final void RemoveChild(Box box)
	{
		java.lang.Iterable<Box> tempVar = getChildren();
		Collection<Box> children = tempVar instanceof Collection<Box> ? (Collection<Box>)tempVar : null;

		if (children != null)
		{
			children.remove(box);
		}
	}

	/** 
		Adds a specified box to the current instance.
	 
	 @param box
		A <see cref="Box" /> object to add to the current
		instance.
	 
	*/
	public final void AddChild(Box box)
	{
		java.lang.Iterable<Box> tempVar = getChildren();
		Collection<Box> children = tempVar instanceof Collection<Box> ? (Collection<Box>)tempVar : null;

		if (children != null)
		{
			children.add(box);
		}
	}

	/** 
		Removes all children from the current instance.
	*/
	public final void ClearChildren()
	{
		java.lang.Iterable<Box> tempVar = getChildren();
		Collection<Box> children = tempVar instanceof Collection<Box> ? (Collection<Box>)tempVar : null;

		if (children != null)
		{
			children.clear();
		}
	}

	/** 
		Gets whether or not the current instance has children.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		current instance has any children.
	 </value>
	*/
	public final boolean getHasChildren()
	{
		java.lang.Iterable<Box> tempVar = getChildren();
		Collection<Box> children = tempVar instanceof Collection<Box> ? (Collection<Box>)tempVar : null;

		return children != null && !children.isEmpty();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Properties

	/** 
		Gets the size of the data contained in the current
		instance, minux the size of any box specific headers.
	 
	 <value>
		A <see cref="long" /> value containing the size of
		the data contained in the current instance.
	 </value>
	*/
	protected final int getDataSize()
	{
		return (int)(header.getDataSize() + data_position - getDataPosition());
	}

	/** 
		Gets the position of the data contained in the current
		instance, after any box specific headers.
	 
	 <value>
		A <see cref="long" /> value containing the position of
		the data contained in the current instance.
	 </value>
	*/
	protected long getDataPosition()
	{
		return data_position;
	}

	/** 
		Gets the header of the current instance.
	 
	 <value>
		A <see cref="BoxHeader" /> object containing the header
		of the current instance.
	 </value>
	*/
	protected final BoxHeader getHeader()
	{
		return header;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Loads the children of the current instance from a
		specified file using the internal data position and size.
	 
	 @param file
		The <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> from which the current
		instance was read and from which to read the children.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating the
		boxes read from the file.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	*/
	protected final java.lang.Iterable<Box> LoadChildren(Rasad.Core.Media.MediaMetadataManagement.File file)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		ArrayList<Box> children = new ArrayList<Box> ();

		long position = getDataPosition();
		long end = position + getDataSize();

		header.setBox(this);
		while (position < end)
		{
			Box child = BoxFactory.CreateBox(file, position, header.clone(), handler, children.size());
			children.add(child);
			position += child.getSize();
		}
		header.setBox(null);

		return children;
	}

	/** 
		Loads the data of the current instance from a specified
		file using the internal data position and size.
	 
	 @param file
		The <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> from which the current
		instance was read and from which to read the data.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the data
		read from the file.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	*/
	protected final ByteVector LoadData(Rasad.Core.Media.MediaMetadataManagement.File file)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		file.Seek(getDataPosition());
		return file.ReadBlock(getDataSize());
	}

	/** 
		Renders the current instance, including its children, to
		a new <see cref="ByteVector" /> object, preceeding the
		contents with a specified block of data.
	 
	 @param topData
		A <see cref="ByteVector" /> object containing box
		specific header data to preceed the content.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	protected ByteVector Render(ByteVector topData)
	{
		boolean free_found = false;
		ByteVector output = new ByteVector();

		if (getChildren() != null)
		{
			for (Box box : getChildren())
			{
				if (box.getClass() == IsoFreeSpaceBox.class)
				{
					free_found = true;
				}
				else
				{
					output.add(box.Render());
				}
			}
		}
		else if (getData() != null)
		{
			output.add(getData());
		}

		// If there was a free, don't take it away, and let meta
		// be a special case.
		if (free_found || Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(getBoxType(), Mpeg4.BoxType.Meta))
		{
			long size_difference = getDataSize() - output.size();

			// If we have room for free space, add it so we
			// don't have to resize the file.
			if (header.getDataSize() != 0 && size_difference >= 8)
			{
				output.add((new IsoFreeSpaceBox(size_difference)).Render());
			}

			// If we're getting bigger, get a lot bigger so
			// we might not have to again.
			else
			{
				output.add((new IsoFreeSpaceBox(2048)).Render());
			}
		}

		// Adjust the header's data size to match the content.
		header.setDataSize(topData.size() + output.size());

		// Render the full box.
		output.add(0, topData);
		output.add(0, header.Render());

		return output;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/*
	#region Internal Methods
	
	/// <summary>
	///    Dumps the child tree of the current instance to the
	///    console.
	/// </summary>
	/// <param name="start">
	///    A <see cref="string" /> object to preface each line with.
	/// </param>
	internal void DumpTree (string start)
	{
		if (BoxType == BoxType.Data)
		Console.WriteLine ("{0}{1} {2}", start,
			BoxType.ToString (),
			(this as AppleDataBox).Text);
		else
			Console.WriteLine ("{0}{1}", start,
			BoxType.ToString ());
		
		if (Children != null)
			foreach (Box child in Children)
				child.DumpTree (start + "   ");
	}
	
	#endregion
	*/
}