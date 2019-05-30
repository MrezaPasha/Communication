package Rasad.Core.Media.MediaMetadataManagement.IFD;

import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Tags.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;
import java.time.*;

//
// IFDStructure.cs: A structure resembling the logical structure of a TIFF IFD
// file. This is the same structure as used by Exif.
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//   Paul Lange (palango@gmx.de)
//
// Copyright (C) 2009 Ruben Vermeersch
// Copyright (C) 2009 Mike Gemuende
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
	This class resembles the structure of a TIFF file. It can either be a
	top-level IFD, or a nested IFD (in the case of Exif).
*/
public class IFDStructure
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	private static final String DATETIME_FORMAT = "yyyy:MM:dd HH:mm:ss";

	/** 
		Contains the IFD directories in this tag.
	*/
	public final ArrayList<IFDDirectory> directories = new ArrayList<IFDDirectory> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets the IFD directories contained in the current instance.
	 
	 <value>
		An array of <see cref="IFDDirectory"/> instances.
	 </value>
	*/
	public final IFDDirectory[] getDirectories()
	{
		return directories.toArray(new IFDDirectory[0]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Checks, if a value for the given tag is contained in the IFD.
	 
	 @param directory
		A <see cref="System.Int32"/> value with the directory index that
		contains the tag.
	 
	 @param tag
		A <see cref="System.UInt16"/> value with the tag.
	 
	 @return 
		A <see cref="System.Boolean"/>, which is true, if the tag is already
		contained in the IFD, otherwise false.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public bool ContainsTag(int directory, ushort tag)
	public final boolean ContainsTag(int directory, short tag)
	{
		if (directory >= directories.size())
		{
			return false;
		}
		return directories.get(directory).containsKey(tag);
	}

	/** 
		Removes a given tag from the IFD.
	 
	 @param directory
		A <see cref="System.Int32"/> value with the directory index that
		contains the tag to remove.
	 
	 @param tag
		A <see cref="System.UInt16"/> value with the tag to remove.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void RemoveTag(int directory, ushort tag)
	public final void RemoveTag(int directory, short tag)
	{
		if (ContainsTag(directory, tag))
		{
			directories.get(directory).remove(tag);
		}
	}

	/** 
		Removes a given tag from the IFD.
	 
	 @param directory
		A <see cref="System.Int32"/> value with the directory index that
		contains the tag to remove.
	 
	 @param entry_tag
		A <see cref="IFDEntryTag"/> value with the tag to remove.
	 
	*/
	public final void RemoveTag(int directory, IFDEntryTag entry_tag)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: RemoveTag(directory, (ushort) entry_tag);
		RemoveTag(directory, (short) entry_tag.getValue());
	}

	/** 
		Adds an <see cref="IFDEntry"/> to the IFD, if it is not already
		contained in, it fails otherwise.
	 
	 @param directory
		A <see cref="System.Int32"/> value with the directory index that
		should contain the tag that will be added.
	 
	 @param entry
		A <see cref="IFDEntry"/> to add to the IFD.
	 
	*/
	public final void AddEntry(int directory, IFDEntry entry)
	{
		while (directory >= directories.size())
		{
			directories.add(new IFDDirectory());
		}

		directories.get(directory).put(entry.getTag(), entry);
	}

	/** 
		Adds an <see cref="IFDEntry"/> to the IFD. If it is already contained
		in the IFD, it is overwritten.
	 
	 @param directory
		A <see cref="System.Int32"/> value with the directory index that
		contains the tag that will be set.
	 
	 @param entry
		A <see cref="IFDEntry"/> to add to the IFD.
	 
	*/
	public final void SetEntry(int directory, IFDEntry entry)
	{
		if (ContainsTag(directory, entry.getTag()))
		{
			RemoveTag(directory, entry.getTag());
		}

		AddEntry(directory, entry);
	}

	/** 
	   Returns the <see cref="IFDEntry"/> belonging to the given tag.
	 
	 @param directory
		A <see cref="System.Int32"/> with the directory that contains
		the wanted tag.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag to get.
	 
	 @return 
		A <see cref="IFDEntry"/> belonging to the given tag, or
		null, if no such tag is contained in the IFD.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public IFDEntry GetEntry(int directory, ushort tag)
	public final IFDEntry GetEntry(int directory, short tag)
	{
		if (!ContainsTag(directory, tag))
		{
			return null;
		}

		return directories.get(directory).get(tag);
	}

	/** 
	   Returns the <see cref="IFDEntry"/> belonging to the given tag.
	 
	 @param directory
		A <see cref="System.Int32"/> with the directory that contains
		the wanted tag.
	 
	 @param entry_tag
		A <see cref="IFDEntryTag"/> with the tag to get.
	 
	 @return 
		A <see cref="IFDEntry"/> belonging to the given tag, or
		null, if no such tag is contained in the IFD.
	 
	*/
	public final IFDEntry GetEntry(int directory, IFDEntryTag entry_tag)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return GetEntry(directory, (ushort) entry_tag);
		return GetEntry(directory, (short) entry_tag.getValue());
	}

	/** 
		Returns the <see cref="System.String"/> stored in the
		entry defined by <paramref name="entry_tag"/>.
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to search for the entry.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @return 
		A <see cref="System.String"/> with the value stored in the entry
		or <see langword="null" /> if no such entry is contained or it
		does not contain a <see cref="System.String"/> value.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public string GetStringValue(int directory, ushort entry_tag)
	public final String GetStringValue(int directory, short entry_tag)
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry entry = GetEntry(directory, entry_tag);

		if (entry != null && entry instanceof StringIFDEntry)
		{
			return (entry instanceof StringIFDEntry ? (StringIFDEntry)entry : null).getValue();
		}

		return null;
	}

	/** 
		Returns a <see cref="System.Nullable"/> containing the
		<see cref="System.Byte"/> stored in the entry defined
		by <paramref name="entry_tag"/>.
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to search for the entry.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @return 
		A <see cref="System.Nullable"/> containing the
		<see cref="System.Byte"/> stored in the entry, or
		<see langword="null" /> if no such entry is contained or it
		does not contain a <see cref="System.Byte"/> value.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nullable<byte> GetByteValue(int directory, ushort entry_tag)
	public final Byte GetByteValue(int directory, short entry_tag)
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry entry = GetEntry(directory, entry_tag);

		if (entry != null && entry instanceof ByteIFDEntry)
		{
			return (entry instanceof ByteIFDEntry ? (ByteIFDEntry)entry : null).getValue();
		}

		return null;
	}

	/** 
		Returns a <see cref="System.Nullable"/> containing the
		<see cref="System.UInt32"/> stored in the entry defined
		by <paramref name="entry_tag"/>.
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to search for the entry.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @return 
		A <see cref="System.Nullable"/> containing the
		<see cref="System.UInt32"/> stored in the entry, or
		<see langword="null" /> if no such entry is contained or it
		does not contain a <see cref="System.UInt32"/> value.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nullable<uint> GetLongValue(int directory, ushort entry_tag)
	public final Integer GetLongValue(int directory, short entry_tag)
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry entry = GetEntry(directory, entry_tag);

		if (entry instanceof LongIFDEntry)
		{
			return (entry instanceof LongIFDEntry ? (LongIFDEntry)entry : null).getValue();
		}

		if (entry instanceof ShortIFDEntry)
		{
			return (entry instanceof ShortIFDEntry ? (ShortIFDEntry)entry : null).getValue();
		}

		return null;
	}

	/** 
		Returns a <see cref="System.Nullable"/> containing the
		<see cref="System.Double"/> stored in the entry defined
		by <paramref name="entry_tag"/>. The entry can be of type
		<see cref="Entries.RationalIFDEntry"/> or
		<see cref="Entries.SRationalIFDEntry"/>
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to search for the entry.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @return 
		A <see cref="System.Nullable"/> containing the
		<see cref="System.Double"/> stored in the entry, or
		<see langword="null" /> if no such entry is contained.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nullable<double> GetRationalValue(int directory, ushort entry_tag)
	public final Double GetRationalValue(int directory, short entry_tag)
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry entry = GetEntry(directory, entry_tag);

		if (entry instanceof RationalIFDEntry)
		{
			return (entry instanceof RationalIFDEntry ? (RationalIFDEntry)entry : null).getValue();
		}

		if (entry instanceof SRationalIFDEntry)
		{
			return (entry instanceof SRationalIFDEntry ? (SRationalIFDEntry)entry : null).getValue();
		}

		return null;
	}

	/** 
		Returns a <see cref="System.Nullable"/> containing the
		<see cref="System.DateTime"/> stored in the entry defined
		by <paramref name="entry_tag"/>. The entry must be of type
		<see cref="Entries.StringIFDEntry"/> and contain an datestring
		according to the Exif specification.
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to search for the entry.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @return 
		A <see cref="System.Nullable"/> containing the
		<see cref="System.DateTime"/> stored in the entry, or
		<see langword="null" /> if no such entry is contained or it
		does not contain a valid value.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nullable<DateTime> GetDateTimeValue(int directory, ushort entry_tag)
	public final LocalDateTime GetDateTimeValue(int directory, short entry_tag)
	{
		String date_string = GetStringValue(directory, entry_tag);

		try
		{
			LocalDateTime date_time = LocalDateTime.ParseExact(date_string, DATETIME_FORMAT, System.Globalization.CultureInfo.InvariantCulture);

			return date_time;
		}
		catch (java.lang.Exception e)
		{
		}

		return null;
	}

	/** 
		Adds a <see cref="Entries.StringIFDEntry"/> to the directory with tag
		given by <paramref name="entry_tag"/> and value given by <paramref name="value"/>
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to add the entry to.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @param value
		A <see cref="System.String"/> with the value to add. If it is <see langword="null" />
		an possibly already contained entry is removed for given tag.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetStringValue(int directory, ushort entry_tag, string value)
	public final void SetStringValue(int directory, short entry_tag, String value)
	{
		if (value == null)
		{
			RemoveTag(directory, entry_tag);
			return;
		}

		SetEntry(directory, new StringIFDEntry(entry_tag, value));
	}

	/** 
		Adds a <see cref="Entries.ByteIFDEntry"/> to the directory with tag
		given by <paramref name="entry_tag"/> and value given by <paramref name="value"/>
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to add the entry to.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @param value
		A <see cref="System.Byte"/> with the value to add.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetByteValue(int directory, ushort entry_tag, byte value)
	public final void SetByteValue(int directory, short entry_tag, byte value)
	{
		SetEntry(directory, new ByteIFDEntry(entry_tag, value));
	}

	/** 
		Adds a <see cref="Entries.LongIFDEntry"/> to the directory with tag
		given by <paramref name="entry_tag"/> and value given by <paramref name="value"/>
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to add the entry to.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @param value
		A <see cref="System.UInt32"/> with the value to add.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetLongValue(int directory, ushort entry_tag, uint value)
	public final void SetLongValue(int directory, short entry_tag, int value)
	{
		SetEntry(directory, new LongIFDEntry(entry_tag, value));
	}

	/** 
		Adds a <see cref="Entries.RationalIFDEntry"/> to the directory with tag
		given by <paramref name="entry_tag"/> and value given by <paramref name="value"/>
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to add the entry to.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @param value
		A <see cref="System.Double"/> with the value to add. It must be possible to
		represent the value by a <see cref="Entries.Rational"/>.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetRationalValue(int directory, ushort entry_tag, double value)
	public final void SetRationalValue(int directory, short entry_tag, double value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (value < 0.0d || value > (double)UInt32.MaxValue)
		if (value < 0.0d || value > (double)Integer.MAX_VALUE)
		{
			throw new IllegalArgumentException("value");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint scale = (value >= 1.0d) ? 1 : UInt32.MaxValue;
		int scale = (value >= 1.0d) ? 1 : Integer.MAX_VALUE;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Rational rational = new Rational((uint)(scale * value), scale);
		Rational rational = new Rational((int)(scale * value), scale);

		SetEntry(directory, new RationalIFDEntry(entry_tag, rational.clone()));
	}

	/** 
		Adds a <see cref="Entries.StringIFDEntry"/> to the directory with tag
		given by <paramref name="entry_tag"/> and value given by <paramref name="value"/>.
		The value is stored as a date string according to the Exif specification.
	 
	 @param directory
		A <see cref="System.Int32"/> with the number of the directory
		to add the entry to.
	 
	 @param entry_tag
		A <see cref="System.UInt16"/> with the tag of the entry
	 
	 @param value
		A <see cref="DateTime"/> with the value to add.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetDateTimeValue(int directory, ushort entry_tag, DateTime value)
	public final void SetDateTimeValue(int directory, short entry_tag, LocalDateTime value)
	{
		String date_string = value.toString(DATETIME_FORMAT);

		SetStringValue(directory, entry_tag, date_string);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}