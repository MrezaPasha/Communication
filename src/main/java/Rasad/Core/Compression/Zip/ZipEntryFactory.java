package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Core.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

// ZipEntryFactory.cs
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
 Basic implementation of <see cref="IEntryFactory"></see>
*/
public class ZipEntryFactory implements IEntryFactory
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Enumerations
	/** 
	 Defines the possible values to be used for the <see cref="ZipEntry.DateTime"/>.
	*/
	public enum TimeSetting
	{
		/** 
		 Use the recorded LastWriteTime value for the file.
		*/
		LastWriteTime(0),
		/** 
		 Use the recorded LastWriteTimeUtc value for the file
		*/
		LastWriteTimeUtc(1),
		/** 
		 Use the recorded CreateTime value for the file.
		*/
		CreateTime(2),
		/** 
		 Use the recorded CreateTimeUtc value for the file.
		*/
		CreateTimeUtc(3),
		/** 
		 Use the recorded LastAccessTime value for the file.
		*/
		LastAccessTime(4),
		/** 
		 Use the recorded LastAccessTimeUtc value for the file.
		*/
		LastAccessTimeUtc(5),
		/** 
		 Use a fixed value.
		 
		 The actual <see cref="DateTime"/> value used can be
		 specified via the <see cref="ZipEntryFactory(DateTime)"/> constructor or 
		 using the <see cref="ZipEntryFactory(TimeSetting)"/> with the setting set
		 to <see cref="TimeSetting.Fixed"/> which will use the <see cref="DateTime"/> when this class was constructed.
		 The <see cref="FixedDateTime"/> property can also be used to set this value.
		*/
		Fixed(6);

		public static final int SIZE = java.lang.Integer.SIZE;

		private int intValue;
		private static java.util.HashMap<Integer, TimeSetting> mappings;
		private static java.util.HashMap<Integer, TimeSetting> getMappings()
		{
			if (mappings == null)
			{
				synchronized (TimeSetting.class)
				{
					if (mappings == null)
					{
						mappings = new java.util.HashMap<Integer, TimeSetting>();
					}
				}
			}
			return mappings;
		}

		private TimeSetting(int value)
		{
			intValue = value;
			getMappings().put(value, this);
		}

		public int getValue()
		{
			return intValue;
		}

		public static TimeSetting forValue(int value)
		{
			return getMappings().get(value);
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a new instance of the <see cref="ZipEntryFactory"/> class.
	 
	 A default <see cref="INameTransform"/>, and the LastWriteTime for files is used.
	*/
	public ZipEntryFactory()
	{
		nameTransform_ = new ZipNameTransform();
	}

	/** 
	 Initialise a new instance of <see cref="ZipEntryFactory"/> using the specified <see cref="TimeSetting"/>
	 
	 @param timeSetting The <see cref="TimeSetting">time setting</see> to use when creating <see cref="ZipEntry">Zip entries</see>.
	*/
	public ZipEntryFactory(TimeSetting timeSetting)
	{
		timeSetting_ = timeSetting;
		nameTransform_ = new ZipNameTransform();
	}

	/** 
	 Initialise a new instance of <see cref="ZipEntryFactory"/> using the specified <see cref="DateTime"/>
	 
	 @param time The time to set all <see cref="ZipEntry.DateTime"/> values to.
	*/
	public ZipEntryFactory(LocalDateTime time)
	{
		timeSetting_ = TimeSetting.Fixed;
		setFixedDateTime(time);
		nameTransform_ = new ZipNameTransform();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	/** 
	 Get / set the <see cref="INameTransform"/> to be used when creating new <see cref="ZipEntry"/> values.
	 
	 
	 Setting this property to null will cause a default <see cref="ZipNameTransform">name transform</see> to be used.
	 
	*/
	public final INameTransform getNameTransform()
	{
		return nameTransform_;
	}
	public final void setNameTransform(INameTransform value)
	{
		if (value == null)
		{
			nameTransform_ = new ZipNameTransform();
		}
		else
		{
			nameTransform_ = value;
		}
	}

	/** 
	 Get / set the <see cref="TimeSetting"/> in use.
	*/
	public final TimeSetting getSetting()
	{
		return timeSetting_;
	}
	public final void setSetting(TimeSetting value)
	{
		timeSetting_ = value;
	}

	/** 
	 Get / set the <see cref="DateTime"/> value to use when <see cref="Setting"/> is set to <see cref="TimeSetting.Fixed"/>
	*/
	public final LocalDateTime getFixedDateTime()
	{
		return fixedDateTime_;
	}
	public final void setFixedDateTime(LocalDateTime value)
	{
		if (value.getYear() < 1970)
		{
			throw new IllegalArgumentException("Value is too old to be valid", "value");
		}
		fixedDateTime_ = value;
	}

	/** 
	 A bitmask defining the attributes to be retrieved from the actual file.
	 
	 The default is to get all possible attributes from the actual file.
	*/
	public final int getGetAttributes()
	{
		return getAttributes_;
	}
	public final void setGetAttributes(int value)
	{
		getAttributes_ = value;
	}

	/** 
	 A bitmask defining which attributes are to be set on.
	 
	 By default no attributes are set on.
	*/
	public final int getSetAttributes()
	{
		return setAttributes_;
	}
	public final void setSetAttributes(int value)
	{
		setAttributes_ = value;
	}

	/** 
	 Get set a value indicating wether unidoce text should be set on.
	*/
	public final boolean getIsUnicodeText()
	{
		return isUnicodeText_;
	}
	public final void setIsUnicodeText(boolean value)
	{
		isUnicodeText_ = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IEntryFactory Members

	/** 
	 Make a new <see cref="ZipEntry"/> for a file.
	 
	 @param fileName The name of the file to create a new entry for.
	 @return Returns a new <see cref="ZipEntry"/> based on the <paramref name="fileName"/>.
	*/
	public final ZipEntry MakeFileEntry(String fileName)
	{
		return MakeFileEntry(fileName, null, true);
	}

	/** 
	 Make a new <see cref="ZipEntry"/> for a file.
	 
	 @param fileName The name of the file to create a new entry for.
	 @param useFileSystem If true entry detail is retrieved from the file system if the file exists.
	 @return Returns a new <see cref="ZipEntry"/> based on the <paramref name="fileName"/>.
	*/
	public final ZipEntry MakeFileEntry(String fileName, boolean useFileSystem)
	{
		return MakeFileEntry(fileName, null, useFileSystem);
	}

	/** 
	 Make a new <see cref="ZipEntry"/> from a name.
	 
	 @param fileName The name of the file to create a new entry for.
	 @param entryName An alternative name to be used for the new entry. Null if not applicable.
	 @param useFileSystem If true entry detail is retrieved from the file system if the file exists.
	 @return Returns a new <see cref="ZipEntry"/> based on the <paramref name="fileName"/>.
	*/
	public final ZipEntry MakeFileEntry(String fileName, String entryName, boolean useFileSystem)
	{
		ZipEntry result = new ZipEntry(nameTransform_.TransformFile(entryName != null && entryName.length() > 0 ? entryName : fileName));
		result.setIsUnicodeText(isUnicodeText_);

		int externalAttributes = 0;
		boolean useAttributes = (setAttributes_ != 0);

		File fi = null;
		if (useFileSystem)
		{
			fi = new File(fileName);
		}

		if ((fi != null) && fi.exists())
		{
			switch (timeSetting_)
			{
				case CreateTime:
					result.setDateTime(fi.CreationTime);
					break;

				case CreateTimeUtc:
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0 || NETCF_2_0
					result.setDateTime(fi.CreationTime.ToUniversalTime());
//#else
					result.setDateTime(fi.CreationTimeUtc);
//#endif
					break;

				case LastAccessTime:
					result.setDateTime(fi.LastAccessTime);
					break;

				case LastAccessTimeUtc:
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0 || NETCF_2_0
					result.setDateTime(fi.LastAccessTime.ToUniversalTime());
//#else
					result.setDateTime(fi.LastAccessTimeUtc);
//#endif
					break;

				case LastWriteTime:
					result.setDateTime(fi.LastWriteTime);
					break;

				case LastWriteTimeUtc:
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0 || NETCF_2_0
					result.setDateTime(fi.LastWriteTime.ToUniversalTime());
//#else
					result.setDateTime(fi.LastWriteTimeUtc);
//#endif
					break;

				case Fixed:
					result.setDateTime(fixedDateTime_);
					break;

				default:
					throw new ZipException("Unhandled time setting in MakeFileEntry");
			}

			result.setSize(fi.length());

			useAttributes = true;
			externalAttributes = (fi.Attributes.getValue() & getAttributes_);
		}
		else
		{
			if (timeSetting_ == TimeSetting.Fixed)
			{
				result.setDateTime(fixedDateTime_);
			}
		}

		if (useAttributes)
		{
			externalAttributes |= setAttributes_;
			result.setExternalFileAttributes(externalAttributes);
		}

		return result;
	}

	/** 
	 Make a new <see cref="ZipEntry"></see> for a directory.
	 
	 @param directoryName The raw untransformed name for the new directory
	 @return Returns a new <see cref="ZipEntry"></see> representing a directory.
	*/
	public final ZipEntry MakeDirectoryEntry(String directoryName)
	{
		return MakeDirectoryEntry(directoryName, true);
	}

	/** 
	 Make a new <see cref="ZipEntry"></see> for a directory.
	 
	 @param directoryName The raw untransformed name for the new directory
	 @param useFileSystem If true entry detail is retrieved from the file system if the file exists.
	 @return Returns a new <see cref="ZipEntry"></see> representing a directory.
	*/
	public final ZipEntry MakeDirectoryEntry(String directoryName, boolean useFileSystem)
	{

		ZipEntry result = new ZipEntry(nameTransform_.TransformDirectory(directoryName));
		result.setIsUnicodeText(isUnicodeText_);
		result.setSize(0);

		int externalAttributes = 0;

		File di = null;

		if (useFileSystem)
		{
			di = new File(directoryName);
		}


		if ((di != null) && di.exists())
		{
			switch (timeSetting_)
			{
				case CreateTime:
					result.setDateTime(di.CreationTime);
					break;

				case CreateTimeUtc:
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0 || NETCF_2_0
					result.setDateTime(di.CreationTime.ToUniversalTime());
//#else
					result.setDateTime(di.CreationTimeUtc);
//#endif
					break;

				case LastAccessTime:
					result.setDateTime(di.LastAccessTime);
					break;

				case LastAccessTimeUtc:
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0 || NETCF_2_0
					result.setDateTime(di.LastAccessTime.ToUniversalTime());
//#else
					result.setDateTime(di.LastAccessTimeUtc);
//#endif
					break;

				case LastWriteTime:
					result.setDateTime(di.LastWriteTime);
					break;

				case LastWriteTimeUtc:
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0 || NETCF_2_0
					result.setDateTime(di.LastWriteTime.ToUniversalTime());
//#else
					result.setDateTime(di.LastWriteTimeUtc);
//#endif
					break;

				case Fixed:
					result.setDateTime(fixedDateTime_);
					break;

				default:
					throw new ZipException("Unhandled time setting in MakeDirectoryEntry");
			}

			externalAttributes = (di.Attributes.getValue() & getAttributes_);
		}
		else
		{
			if (timeSetting_ == TimeSetting.Fixed)
			{
				result.setDateTime(fixedDateTime_);
			}
		}

		// Always set directory attribute on.
		externalAttributes |= (setAttributes_ | 16);
		result.setExternalFileAttributes(externalAttributes);

		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private INameTransform nameTransform_;
	private LocalDateTime fixedDateTime_ = LocalDateTime.now();
	private TimeSetting timeSetting_ = TimeSetting.values()[0];
	private boolean isUnicodeText_;

	private int getAttributes_ = -1;
	private int setAttributes_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}