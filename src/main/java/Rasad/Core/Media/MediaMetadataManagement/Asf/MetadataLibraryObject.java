package Rasad.Core.Media.MediaMetadataManagement.Asf;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// MetadataLibraryObject.cs: Provides a representation of an ASF Metadata
// Library object which can be read from and written to disk.
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
	representation of an ASF Metadata Library object which can be
	read from and written to disk.
*/
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class MetadataLibraryObject extends Object implements java.lang.Iterable<DescriptionRecord>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the description records.
	*/
	private ArrayList<DescriptionRecord> records = new ArrayList<DescriptionRecord> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="MetadataLibraryObject" /> by reading the contents
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
		or smaller than the minimum size.
	 
	*/
	public MetadataLibraryObject(Asf.File file, long position)
	{
		super(file, position);
		if (!UUID.equals(Asf.Guid.AsfMetadataLibraryObject))
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
			DescriptionRecord rec = new DescriptionRecord(file);
			AddRecord(rec);
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="MetadataLibraryObject" /> with no contents.
	*/
	public MetadataLibraryObject()
	{
		super(Asf.Guid.AsfMetadataLibraryObject);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		<see langword="true" /> if the current instance doesn't
		contain any <see cref="DescriptionRecord" /> objects.
		Otherwise <see langword="false" />.
	 </value>
	*/
	public final boolean getIsEmpty()
	{
		return records.isEmpty();
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

		for (DescriptionRecord rec : records)
		{
			count++;
			output.add(rec.Render());
		}

		return Render(Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpAddition(RenderWord(count), output));
	}

	/** 
		Removes all records with a given language, stream, and
		name from the current instance.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the records to be removed.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the records to be removed.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		records to be removed.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void RemoveRecords(ushort languageListIndex, ushort streamNumber, string name)
	public final void RemoveRecords(short languageListIndex, short streamNumber, String name)
	{
		for (int i = records.size() - 1; i >= 0; i--)
		{
			DescriptionRecord rec = records.get(i);
			if (rec.getLanguageListIndex() == languageListIndex && rec.getStreamNumber() == streamNumber && rec.getName().equals(name))
			{
				records.remove(i);
			}
		}
	}

	/** 
		Gets all records with a given language, stream, and any
		of a collection of names from the current instance.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the records to be retrieved.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the records to be retrieved.
	 
	 @param names
		A <see cref="string[]" /> containing the names of the
		records to be retrieved.
	 
	 @return 
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object enumerating
		through the <see cref="DescriptionRecord" /> objects
		retrieved from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public IEnumerable<DescriptionRecord> GetRecords(ushort languageListIndex, ushort streamNumber, params string [] names)
	public final java.lang.Iterable<DescriptionRecord> GetRecords(short languageListIndex, short streamNumber, String... names)
	{
		for (DescriptionRecord rec : records)
		{
			if (rec.getLanguageListIndex() != languageListIndex || rec.getStreamNumber() != streamNumber)
			{
				continue;
			}

			for (String name : names)
			{
				if (rec.getName().equals(name))
				{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
					yield return rec;
				}
			}
		}
	}

	/** 
		Adds a record to the current instance.
	 
	 @param record
		A <see cref="DescriptionRecord" /> object to add to the
		current instance.
	 
	*/
	public final void AddRecord(DescriptionRecord record)
	{
		records.add(record);
	}

	/** 
		Sets the a collection of records for a given language,
		stream, and name, removing the existing matching records.
	 
	 @param languageListIndex
		A <see cref="ushort" /> value containing the language
		list index of the records to be added.
	 
	 @param streamNumber
		A <see cref="ushort" /> value containing the stream
		number of the records to be added.
	 
	 @param name
		A <see cref="string" /> object containing the name of the
		records to be added.
	 
	 @param records
		A <see cref="DescriptionRecord[]" /> containing records
		to add to the new instance.
	 
	 
		All added entries in <paramref name="records" /> should
		match <paramref name="languageListIndex" />, <paramref
		name="streamNumber" /> and <paramref name="name" /> but
		it is not verified by the method. The records will be
		added with their own values and not those provided in
		this method, which are used for removing existing values
		and determining where to position the new object.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetRecords(ushort languageListIndex, ushort streamNumber, string name, params DescriptionRecord [] records)
	public final void SetRecords(short languageListIndex, short streamNumber, String name, DescriptionRecord... records)
	{
		int position = this.records.size();
		for (int i = this.records.size() - 1; i >= 0; i--)
		{
			DescriptionRecord rec = this.records.get(i);
			if (rec.getLanguageListIndex() == languageListIndex && rec.getStreamNumber() == streamNumber && rec.getName().equals(name))
			{
				this.records.remove(i);
				position = i;
			}
		}
		this.records.addAll(position, Arrays.asList(records));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IEnumerable

	/** 
		Gets an enumerator for enumerating through the
		description records.
	 
	 @return 
		A <see cref="T:System.Collections.IEnumerator`1" /> for
		enumerating through the description records.
	 
	*/
	public final Iterator<DescriptionRecord> iterator()
	{
		return records.iterator();
	}

	public final Iterator GetEnumerator()
	{
		return records.iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}