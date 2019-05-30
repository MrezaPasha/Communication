package Rasad.Core.Media.MediaMetadataManagement.IFD;

import Rasad.Core.Media.MediaMetadataManagement.Image.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Entries.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.Tags.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// IFDTag.cs: Basic Tag-class to handle an IFD (Image File Directory) with
// its image-tags.
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
	Contains the metadata for one IFD (Image File Directory).
*/
public class IFDTag extends ImageTag
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		A reference to the Exif IFD (which can be found by following the
		pointer in IFD0, ExifIFD tag). This variable should not be used
		directly, use the <see cref="ExifIFD"/> property instead.
	*/
	private IFDStructure exif_ifd = null;

	/** 
		A reference to the GPS IFD (which can be found by following the
		pointer in IFD0, GPSIFD tag). This variable should not be used
		directly, use the <see cref="GPSIFD"/> property instead.
	*/
	private IFDStructure gps_ifd = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** <value>
		The IFD structure referenced by the current instance
	 </value>
	*/
	private IFDStructure Structure;
	public final IFDStructure getStructure()
	{
		return Structure;
	}
	private void setStructure(IFDStructure value)
	{
		Structure = value;
	}

	/** 
		The Exif IFD. Will create one if the file doesn't alread have it.
	 
	 
		<p>Note how this also creates an empty IFD for exif, even if
		you don't set a value. That's okay, empty nested IFDs get ignored
		when rendering.</p>
	 
	*/
	public final IFDStructure getExifIFD()
	{
		if (exif_ifd == null)
		{
			Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry tempVar = getStructure().GetEntry(0, IFDEntryTag.ExifIFD);
			SubIFDEntry entry = tempVar instanceof SubIFDEntry ? (SubIFDEntry)tempVar : null;
			if (entry == null)
			{
				exif_ifd = new IFDStructure();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: entry = new SubIFDEntry((ushort) IFDEntryTag.ExifIFD, (ushort) IFDEntryType.Long, 1, exif_ifd);
				entry = new SubIFDEntry((short) IFDEntryTag.ExifIFD.getValue(), (short) IFDEntryType.Long.getValue(), 1, exif_ifd);
				getStructure().SetEntry(0, entry);
			}

			exif_ifd = entry.getStructure();
		}

		return exif_ifd;
	}

	/** 
		The GPS IFD. Will create one if the file doesn't alread have it.
	 
	 
		<p>Note how this also creates an empty IFD for GPS, even if
		you don't set a value. That's okay, empty nested IFDs get ignored
		when rendering.</p>
	 
	*/
	public final IFDStructure getGPSIFD()
	{
		if (gps_ifd == null)
		{
			Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry tempVar = getStructure().GetEntry(0, IFDEntryTag.GPSIFD);
			SubIFDEntry entry = tempVar instanceof SubIFDEntry ? (SubIFDEntry)tempVar : null;
			if (entry == null)
			{
				gps_ifd = new IFDStructure();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: entry = new SubIFDEntry((ushort) IFDEntryTag.GPSIFD, (ushort) IFDEntryType.Long, 1, gps_ifd);
				entry = new SubIFDEntry((short) IFDEntryTag.GPSIFD.getValue(), (short) IFDEntryType.Long.getValue(), 1, gps_ifd);
				getStructure().SetEntry(0, entry);
			}

			gps_ifd = entry.getStructure();
		}

		return gps_ifd;
	}

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		Always <see cref="TagTypes.TiffIFD" />.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		return TagTypes.TiffIFD;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructor. Creates an empty IFD tag. Can be populated manually, or via
		<see cref="IFDReader"/>.
	*/
	public IFDTag()
	{
		setStructure(new IFDStructure());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Clears the values stored in the current instance.
	*/
	@Override
	public void Clear()
	{
		throw new UnsupportedOperationException();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Metadata fields

	/** 
		Gets or sets the comment for the image described
		by the current instance.
	 
	 <value>
		A <see cref="string" /> containing the comment of the
		current instace.
	 </value>
	*/
	@Override
	public String getComment()
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry tempVar = getExifIFD().GetEntry(0, (short) ExifEntryTag.UserComment.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: {
		UserCommentIFDEntry comment_entry = tempVar instanceof UserCommentIFDEntry ? (UserCommentIFDEntry)tempVar : null;

		if (comment_entry == null)
		{
			Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry tempVar2 = getStructure().GetEntry(0, IFDEntryTag.ImageDescription);
			StringIFDEntry description = tempVar2 instanceof StringIFDEntry ? (StringIFDEntry)tempVar2 : null;
			return description == null ? null : description.getValue();
		}

		return comment_entry.getValue();
	}
	@Override
	public void setComment(String value)
	{
		if (value == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.RemoveTag(0, (ushort) ExifEntryTag.UserComment);
			getExifIFD().RemoveTag(0, (short) ExifEntryTag.UserComment.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.RemoveTag(0, (ushort) IFDEntryTag.ImageDescription);
			getStructure().RemoveTag(0, (short) IFDEntryTag.ImageDescription.getValue());
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.SetEntry(0, new UserCommentIFDEntry((ushort) ExifEntryTag.UserComment, value));
		getExifIFD().SetEntry(0, new UserCommentIFDEntry((short) ExifEntryTag.UserComment.getValue(), value));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.SetEntry(0, new StringIFDEntry((ushort) IFDEntryTag.ImageDescription, value));
		getStructure().SetEntry(0, new StringIFDEntry((short) IFDEntryTag.ImageDescription.getValue(), value));
	}

	/** 
		Gets and sets the copyright information for the media
		represented by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the copyright
		information for the media represented by the current
		instance or <see langword="null" /> if no value present.
	 </value>
	*/
	@Override
	public String getCopyright()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return Structure.GetStringValue(0, (ushort) IFDEntryTag.Copyright);
		return getStructure().GetStringValue(0, (short) IFDEntryTag.Copyright.getValue());
	}
	@Override
	public void setCopyright(String value)
	{
		if (value == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.RemoveTag(0, (ushort) IFDEntryTag.Copyright);
			getStructure().RemoveTag(0, (short) IFDEntryTag.Copyright.getValue());
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.SetEntry(0, new StringIFDEntry((ushort) IFDEntryTag.Copyright, value));
		getStructure().SetEntry(0, new StringIFDEntry((short) IFDEntryTag.Copyright.getValue(), value));
	}

	/** 
		Gets or sets the creator of the image.
	 
	 <value>
		A <see cref="string" /> with the name of the creator.
	 </value>
	*/
	@Override
	public String getCreator()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return Structure.GetStringValue(0, (ushort) IFDEntryTag.Artist);
		return getStructure().GetStringValue(0, (short) IFDEntryTag.Artist.getValue());
	}
	@Override
	public void setCreator(String value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.SetStringValue(0, (ushort) IFDEntryTag.Artist, value);
		getStructure().SetStringValue(0, (short) IFDEntryTag.Artist.getValue(), value);
	}

	/** 
		Gets or sets the software the image, the current instance
		belongs to, was created with.
	 
	 <value>
		A <see cref="string" /> containing the name of the
		software the current instace was created with.
	 </value>
	*/
	@Override
	public String getSoftware()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return Structure.GetStringValue(0, (ushort) IFDEntryTag.Software);
		return getStructure().GetStringValue(0, (short) IFDEntryTag.Software.getValue());
	}
	@Override
	public void setSoftware(String value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.SetStringValue(0, (ushort) IFDEntryTag.Software, value);
		getStructure().SetStringValue(0, (short) IFDEntryTag.Software.getValue(), value);
	}

	/** 
		Gets or sets the time when the image, the current instance
		belongs to, was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the time the image was taken.
	 </value>
	*/
	@Override
	public DateTime getDateTime()
	{
		return getDateTimeOriginal();
	}
	@Override
	public void setDateTime(DateTime value)
	{
		setDateTimeOriginal(value);
	}

	/** 
		The time of capturing.
	 
	 <value>
		A <see cref="System.Nullable"/> with the time of capturing.
	 </value>
	*/
	public final DateTime getDateTimeOriginal()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ExifIFD.GetDateTimeValue(0, (ushort) ExifEntryTag.DateTimeOriginal);
		return getExifIFD().GetDateTimeValue(0, (short) ExifEntryTag.DateTimeOriginal.getValue());
	}
	public final void setDateTimeOriginal(DateTime value)
	{
		if (value == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.RemoveTag(0, (ushort) ExifEntryTag.DateTimeOriginal);
			getExifIFD().RemoveTag(0, (short) ExifEntryTag.DateTimeOriginal.getValue());
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.SetDateTimeValue(0, (ushort) ExifEntryTag.DateTimeOriginal, value.Value);
		getExifIFD().SetDateTimeValue(0, (short) ExifEntryTag.DateTimeOriginal.getValue(), value.getValue());
	}

	/** 
		The time of digitization.
	 
	 <value>
		A <see cref="System.Nullable"/> with the time of digitization.
	 </value>
	*/
	public final DateTime getDateTimeDigitized()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ExifIFD.GetDateTimeValue(0, (ushort) ExifEntryTag.DateTimeDigitized);
		return getExifIFD().GetDateTimeValue(0, (short) ExifEntryTag.DateTimeDigitized.getValue());
	}
	public final void setDateTimeDigitized(DateTime value)
	{
		if (value == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.RemoveTag(0, (ushort) ExifEntryTag.DateTimeDigitized);
			getExifIFD().RemoveTag(0, (short) ExifEntryTag.DateTimeDigitized.getValue());
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.SetDateTimeValue(0, (ushort) ExifEntryTag.DateTimeDigitized, value.Value);
		getExifIFD().SetDateTimeValue(0, (short) ExifEntryTag.DateTimeDigitized.getValue(), value.getValue());
	}

	/** 
		Gets or sets the latitude of the GPS coordinate the current
		image was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the latitude ranging from -90.0
		to +90.0 degrees.
	 </value>
	*/
	@Override
	public Double getLatitude()
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDStructure gps_ifd = getGPSIFD();
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry tempVar = gps_ifd.GetEntry(0, (short) GPSEntryTag.GPSLatitude.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var gps_ifd = GPSIFD;
		RationalArrayIFDEntry degree_entry = tempVar instanceof RationalArrayIFDEntry ? (RationalArrayIFDEntry)tempVar : null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var degree_ref = gps_ifd.GetStringValue(0, (ushort) GPSEntryTag.GPSLatitudeRef);
		String degree_ref = gps_ifd.GetStringValue(0, (short) GPSEntryTag.GPSLatitudeRef.getValue());

		if (degree_entry == null || degree_ref == null)
		{
			return null;
		}

		Rational [] values = degree_entry.getValues().clone();
		if (values.length != 3)
		{
			return null;
		}

		double deg = values[0] + values[1] / 60.0d + values[2] / 3600.0d;

		if (degree_ref.equals("S"))
		{
			deg *= -1.0d;
		}

		return Math.max(Math.min(deg, 90.0d), -90.0d);
	}
	@Override
	public void setLatitude(Double value)
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDStructure gps_ifd = getGPSIFD();

		if (value == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.RemoveTag(0, (ushort) GPSEntryTag.GPSLatitudeRef);
			gps_ifd.RemoveTag(0, (short) GPSEntryTag.GPSLatitudeRef.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.RemoveTag(0, (ushort) GPSEntryTag.GPSLatitude);
			gps_ifd.RemoveTag(0, (short) GPSEntryTag.GPSLatitude.getValue());
			return;
		}

		double angle = value.doubleValue();

		if (angle < -90.0d || angle > 90.0d)
		{
			throw new IllegalArgumentException("value");
		}

		InitGpsDirectory();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.SetStringValue(0, (ushort) GPSEntryTag.GPSLatitudeRef, angle < 0 ? "S" : "N");
		gps_ifd.SetStringValue(0, (short) GPSEntryTag.GPSLatitudeRef.getValue(), angle < 0 ? "S" : "N");

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var entry = new RationalArrayIFDEntry((ushort) GPSEntryTag.GPSLatitude, DegreeToRationals(Math.Abs(angle)));
		RationalArrayIFDEntry entry = new RationalArrayIFDEntry((short) GPSEntryTag.GPSLatitude.getValue(), DegreeToRationals(Math.abs(angle)));
		gps_ifd.SetEntry(0, entry);
	}

	/** 
		Gets or sets the longitude of the GPS coordinate the current
		image was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the longitude ranging from -180.0
		to +180.0 degrees.
	 </value>
	*/
	@Override
	public Double getLongitude()
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDStructure gps_ifd = getGPSIFD();
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDEntry tempVar = gps_ifd.GetEntry(0, (short) GPSEntryTag.GPSLongitude.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var gps_ifd = GPSIFD;
		RationalArrayIFDEntry degree_entry = tempVar instanceof RationalArrayIFDEntry ? (RationalArrayIFDEntry)tempVar : null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var degree_ref = gps_ifd.GetStringValue(0, (ushort) GPSEntryTag.GPSLongitudeRef);
		String degree_ref = gps_ifd.GetStringValue(0, (short) GPSEntryTag.GPSLongitudeRef.getValue());

		if (degree_entry == null || degree_ref == null)
		{
			return null;
		}

		Rational [] values = degree_entry.getValues().clone();
		if (values.length != 3)
		{
			return null;
		}

		double deg = values[0] + values[1] / 60.0d + values[2] / 3600.0d;

		if (degree_ref.equals("W"))
		{
			deg *= -1.0d;
		}

		return Math.max(Math.min(deg, 180.0d), -180.0d);
	}
	@Override
	public void setLongitude(Double value)
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDStructure gps_ifd = getGPSIFD();

		if (value == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.RemoveTag(0, (ushort) GPSEntryTag.GPSLongitudeRef);
			gps_ifd.RemoveTag(0, (short) GPSEntryTag.GPSLongitudeRef.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.RemoveTag(0, (ushort) GPSEntryTag.GPSLongitude);
			gps_ifd.RemoveTag(0, (short) GPSEntryTag.GPSLongitude.getValue());
			return;
		}

		double angle = value.doubleValue();

		if (angle < -180.0d || angle > 180.0d)
		{
			throw new IllegalArgumentException("value");
		}

		InitGpsDirectory();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.SetStringValue(0, (ushort) GPSEntryTag.GPSLongitudeRef, angle < 0 ? "W" : "E");
		gps_ifd.SetStringValue(0, (short) GPSEntryTag.GPSLongitudeRef.getValue(), angle < 0 ? "W" : "E");

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var entry = new RationalArrayIFDEntry((ushort) GPSEntryTag.GPSLongitude, DegreeToRationals(Math.Abs(angle)));
		RationalArrayIFDEntry entry = new RationalArrayIFDEntry((short) GPSEntryTag.GPSLongitude.getValue(), DegreeToRationals(Math.abs(angle)));
		gps_ifd.SetEntry(0, entry);
	}

	/** 
		Gets or sets the altitude of the GPS coordinate the current
		image was taken. The unit is meter.
	 
	 <value>
		A <see cref="System.Nullable"/> with the altitude. A positive value
		is above sea level, a negative one below sea level. The unit is meter.
	 </value>
	*/
	@Override
	public Double getAltitude()
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDStructure gps_ifd = getGPSIFD();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var altitude = gps_ifd.GetRationalValue(0, (ushort) GPSEntryTag.GPSAltitude);
		Double altitude = gps_ifd.GetRationalValue(0, (short) GPSEntryTag.GPSAltitude.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var ref_entry = gps_ifd.GetByteValue(0, (ushort) GPSEntryTag.GPSAltitudeRef);
		Byte ref_entry = gps_ifd.GetByteValue(0, (short) GPSEntryTag.GPSAltitudeRef.getValue());

		if (altitude == null || ref_entry == null)
		{
			return null;
		}

		if (ref_entry.byteValue() == 1)
		{
//C# TO JAVA CONVERTER TODO TASK: Arithmetic operations involving nullable type instances are not converted to null-value logic:
			altitude *= -1.0d;
		}

		return altitude;
	}
	@Override
	public void setAltitude(Double value)
	{
		Rasad.Core.Media.MediaMetadataManagement.IFD.IFDStructure gps_ifd = getGPSIFD();

		if (value == null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.RemoveTag(0, (ushort) GPSEntryTag.GPSAltitudeRef);
			gps_ifd.RemoveTag(0, (short) GPSEntryTag.GPSAltitudeRef.getValue());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.RemoveTag(0, (ushort) GPSEntryTag.GPSAltitude);
			gps_ifd.RemoveTag(0, (short) GPSEntryTag.GPSAltitude.getValue());
			return;
		}

		double altitude = value.doubleValue();

		InitGpsDirectory();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.SetByteValue(0, (ushort) GPSEntryTag.GPSAltitudeRef, (byte)(altitude < 0 ? 1 : 0));
		gps_ifd.SetByteValue(0, (short) GPSEntryTag.GPSAltitudeRef.getValue(), (byte)(altitude < 0 ? 1 : 0));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: gps_ifd.SetRationalValue(0, (ushort) GPSEntryTag.GPSAltitude, Math.Abs(altitude));
		gps_ifd.SetRationalValue(0, (short) GPSEntryTag.GPSAltitude.getValue(), Math.abs(altitude));
	}

	/** 
		Gets the exposure time the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the exposure time in seconds.
	 </value>
	*/
	@Override
	public Double getExposureTime()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ExifIFD.GetRationalValue(0, (ushort) ExifEntryTag.ExposureTime);
		return getExifIFD().GetRationalValue(0, (short) ExifEntryTag.ExposureTime.getValue());
	}
	@Override
	public void setExposureTime(Double value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.SetRationalValue(0, (ushort) ExifEntryTag.ExposureTime, value.HasValue ? (double) value : 0);
		getExifIFD().SetRationalValue(0, (short) ExifEntryTag.ExposureTime.getValue(), value != null ? (double) value : 0);
	}

	/** 
		Gets the FNumber the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the FNumber.
	 </value>
	*/
	@Override
	public Double getFNumber()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ExifIFD.GetRationalValue(0, (ushort) ExifEntryTag.FNumber);
		return getExifIFD().GetRationalValue(0, (short) ExifEntryTag.FNumber.getValue());
	}
	@Override
	public void setFNumber(Double value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.SetRationalValue(0, (ushort) ExifEntryTag.FNumber, value.HasValue ? (double) value : 0);
		getExifIFD().SetRationalValue(0, (short) ExifEntryTag.FNumber.getValue(), value != null ? (double) value : 0);
	}

	/** 
		Gets the ISO speed the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the ISO speed as defined in ISO 12232.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override Nullable<uint> getISOSpeedRatings()
	@Override
	public Integer getISOSpeedRatings()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ExifIFD.GetLongValue(0, (ushort) ExifEntryTag.ISOSpeedRatings);
		return getExifIFD().GetLongValue(0, (short) ExifEntryTag.ISOSpeedRatings.getValue());
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setISOSpeedRatings(Nullable<uint> value)
	@Override
	public void setISOSpeedRatings(Integer value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.SetLongValue(0, (ushort) ExifEntryTag.ISOSpeedRatings, value.HasValue ? (uint) value : 0);
		getExifIFD().SetLongValue(0, (short) ExifEntryTag.ISOSpeedRatings.getValue(), value != null ? (int) value : 0);
	}

	/** 
		Gets the focal length the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the focal length in millimeters.
	 </value>
	*/
	@Override
	public Double getFocalLength()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ExifIFD.GetRationalValue(0, (ushort) ExifEntryTag.FocalLength);
		return getExifIFD().GetRationalValue(0, (short) ExifEntryTag.FocalLength.getValue());
	}
	@Override
	public void setFocalLength(Double value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.SetRationalValue(0, (ushort) ExifEntryTag.FocalLength, value.HasValue ? (double) value : 0);
		getExifIFD().SetRationalValue(0, (short) ExifEntryTag.FocalLength.getValue(), value != null ? (double) value : 0);
	}

	/** 
		Gets the focal length the image, the current instance belongs
		to, was taken with, assuming a 35mm film camera.
	 
	 <value>
		A <see cref="System.Nullable"/> with the focal length in 35mm equivalent in millimeters.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override Nullable<uint> getFocalLengthIn35mmFilm()
	@Override
	public Integer getFocalLengthIn35mmFilm()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ExifIFD.GetLongValue(0, (ushort) ExifEntryTag.FocalLengthIn35mmFilm);
		return getExifIFD().GetLongValue(0, (short) ExifEntryTag.FocalLengthIn35mmFilm.getValue());
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setFocalLengthIn35mmFilm(Nullable<uint> value)
	@Override
	public void setFocalLengthIn35mmFilm(Integer value)
	{
		if (value != null)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.SetLongValue(0, (ushort) ExifEntryTag.FocalLengthIn35mmFilm, (uint) value);
			getExifIFD().SetLongValue(0, (short) ExifEntryTag.FocalLengthIn35mmFilm.getValue(), (int) value);
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ExifIFD.RemoveTag(0, (ushort) ExifEntryTag.FocalLengthIn35mmFilm);
			getExifIFD().RemoveTag(0, (short) ExifEntryTag.FocalLengthIn35mmFilm.getValue());
		}
	}

	/** 
		Gets or sets the orientation of the image described
		by the current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Image.ImageOrientation" /> containing the orientation of the
		image
	 </value>
	*/
	@Override
	public ImageOrientation getOrientation()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var orientation = Structure.GetLongValue(0, (ushort) IFDEntryTag.Orientation);
		Integer orientation = getStructure().GetLongValue(0, (short) IFDEntryTag.Orientation.getValue());

		if (orientation != null)
		{
			return ImageOrientation.forValue(orientation);
		}

		return ImageOrientation.None;
	}
	@Override
	public void setOrientation(ImageOrientation value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((uint) value < 1U || (uint) value > 8U)
		if ((int) value.getValue() < 1 || (int) value.getValue() > 8)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.RemoveTag(0, (ushort) IFDEntryTag.Orientation);
			getStructure().RemoveTag(0, (short) IFDEntryTag.Orientation.getValue());
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.SetLongValue(0, (ushort) IFDEntryTag.Orientation, (uint) value);
		getStructure().SetLongValue(0, (short) IFDEntryTag.Orientation.getValue(), (int) value.getValue());
	}

	/** 
		Gets the manufacture of the recording equipment the image, the
		current instance belongs to, was taken with.
	 
	 <value>
		A <see cref="string" /> with the manufacture name.
	 </value>
	*/
	@Override
	public String getMake()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return Structure.GetStringValue(0, (ushort) IFDEntryTag.Make);
		return getStructure().GetStringValue(0, (short) IFDEntryTag.Make.getValue());
	}
	@Override
	public void setMake(String value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.SetStringValue(0, (ushort) IFDEntryTag.Make, value);
		getStructure().SetStringValue(0, (short) IFDEntryTag.Make.getValue(), value);
	}

	/** 
		Gets the model name of the recording equipment the image, the
		current instance belongs to, was taken with.
	 
	 <value>
		A <see cref="string" /> with the model name.
	 </value>
	*/
	@Override
	public String getModel()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return Structure.GetStringValue(0, (ushort) IFDEntryTag.Model);
		return getStructure().GetStringValue(0, (short) IFDEntryTag.Model.getValue());
	}
	@Override
	public void setModel(String value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.SetStringValue(0, (ushort) IFDEntryTag.Model, value);
		getStructure().SetStringValue(0, (short) IFDEntryTag.Model.getValue(), value);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	/** 
		Initilazies the GPS IFD with some basic entries.
	*/
	private void InitGpsDirectory()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: GPSIFD.SetStringValue(0, (ushort) GPSEntryTag.GPSVersionID, "2 0 0 0");
		getGPSIFD().SetStringValue(0, (short) GPSEntryTag.GPSVersionID.getValue(), "2 0 0 0");
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: GPSIFD.SetStringValue(0, (ushort) GPSEntryTag.GPSMapDatum, "WGS-84");
		getGPSIFD().SetStringValue(0, (short) GPSEntryTag.GPSMapDatum.getValue(), "WGS-84");
	}

	/** 
		Converts a given (positive) angle value to three rationals like they
		are used to store an angle for GPS data.
	 
	 @param angle
		A <see cref="System.Double"/> between 0.0d and 180.0d with the angle
		in degrees
	 
	 @return 
		A <see cref="Rational"/> representing the same angle by degree, minutes
		and seconds of the angle.
	 
	*/
	private Rational[] DegreeToRationals(double angle)
	{
		if (angle < 0.0 || angle > 180.0)
		{
			throw new IllegalArgumentException("angle");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint deg = (uint) Math.Floor(angle);
		int deg = (int) Math.floor(angle);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint min = (uint)((angle - Math.Floor(angle)) * 60.0);
		int min = (int)((angle - Math.floor(angle)) * 60.0);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint sec = (uint)((angle - Math.Floor(angle) - (min / 60.0)) * 360000000.0);
		int sec = (int)((angle - Math.floor(angle) - (min / 60.0)) * 360000000.0);

		Rational[] rationals = new Rational []
		{
			new Rational(deg, 1),
			new Rational(min, 1),
			new Rational(sec, 100000)
		};

		return rationals;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}