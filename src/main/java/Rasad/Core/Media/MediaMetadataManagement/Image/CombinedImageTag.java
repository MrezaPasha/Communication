package Rasad.Core.Media.MediaMetadataManagement.Image;

import Rasad.Core.Media.MediaMetadataManagement.IFD.*;
import Rasad.Core.Media.MediaMetadataManagement.Xmp.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// CombinedImageTag.cs: The class provides an abstraction to combine
// ImageTags.
//
// Author:
//   Mike Gemuende (mike@gemuende.de)
//   Paul Lange (palango@gmx.de)
//
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
	Combines some <see cref="ImageTag"/> instance to behave as one.
*/
public class CombinedImageTag extends ImageTag
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Direct access to the Exif (IFD) tag (if any)
	*/
	private IFDTag Exif;
	public final IFDTag getExif()
	{
		return Exif;
	}
	private void setExif(IFDTag value)
	{
		Exif = value;
	}

	/** 
		Direct access to the Xmp tag (if any)
	*/
	private XmpTag Xmp;
	public final XmpTag getXmp()
	{
		return Xmp;
	}
	private void setXmp(XmpTag value)
	{
		Xmp = value;
	}

	/** 
		Other image tags available in this tag.
	*/
	private ArrayList<ImageTag> OtherTags;
	public final ArrayList<ImageTag> getOtherTags()
	{
		return OtherTags;
	}
	private void setOtherTags(ArrayList<ImageTag> value)
	{
		OtherTags = value;
	}

	/** 
		Stores the types of the tags, which are allowed for
		the current instance.
	*/
	private TagTypes AllowedTypes = getTagTypes().values()[0];
	public final TagTypes getAllowedTypes()
	{
		return AllowedTypes;
	}
	private void setAllowedTypes(TagTypes value)
	{
		AllowedTypes = value;
	}

	/** 
		Returns all image tags in this tag, with XMP
		and Exif first.
	*/
	public final ArrayList<ImageTag> getAllTags()
	{
		if (all_tags == null)
		{
			all_tags = new ArrayList<ImageTag> ();
			if (getXmp() != null)
			{
				all_tags.add(getXmp());
			}
			if (getExif() != null)
			{
				all_tags.add(getExif());
			}
			all_tags.addAll(getOtherTags());
		}

		return all_tags;
	}

	private ArrayList<ImageTag> all_tags = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="CombinedImageTag" /> with a restriction on the
		allowed tag types contained in this combined tag.
	 
	 @param allowed_types
		A <see cref="TagTypes" /> value, which restricts the
		types of metadata that can be contained in this
		combined tag.
	 
	*/
	public CombinedImageTag(TagTypes allowed_types)
	{
		setAllowedTypes(allowed_types);
		setOtherTags(new ArrayList<ImageTag> ());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Protected Methods

	public final void AddTag(ImageTag tag)
	{
		if ((tag.getTagTypes().getValue() & getAllowedTypes().getValue()) != tag.getTagTypes().getValue())
		{
			throw new RuntimeException(String.format("Attempted to add %1$s to an image, but the only allowed types are %2$s", tag.getTagTypes(), getAllowedTypes()));
		}

		if (tag instanceof IFDTag)
		{
			setExif(tag instanceof IFDTag ? (IFDTag)tag : null);
		}
		else if (tag instanceof XmpTag)
		{
			// we treat a IPTC-IIM tag as a XMP tag. However, we prefer the real XMP tag.
			// See comments in Jpeg/File.cs for what we should do to deal with this properly.
			if (getXmp() != null && (tag instanceof IIM.IIMTag || getXmp() instanceof IIM.IIMTag))
			{
				IIM.IIMTag iimTag = tag instanceof IIM.IIMTag ? (IIM.IIMTag)tag : null;
				if (iimTag == null)
				{
					Rasad.Core.Media.MediaMetadataManagement.Xmp.XmpTag tempVar = getXmp();
					iimTag = tempVar instanceof IIM.IIMTag ? (IIM.IIMTag)tempVar : null;
					setXmp(tag instanceof XmpTag ? (XmpTag)tag : null);
				}

				if (tangible.StringHelper.isNullOrEmpty(getXmp().getTitle()))
				{
					getXmp().setTitle(iimTag.getTitle());
				}
				if (tangible.StringHelper.isNullOrEmpty(getXmp().getCreator()))
				{
					getXmp().setCreator(iimTag.getCreator());
				}
				if (tangible.StringHelper.isNullOrEmpty(getXmp().getCopyright()))
				{
					getXmp().setCopyright(iimTag.getCopyright());
				}
				if (tangible.StringHelper.isNullOrEmpty(getXmp().getComment()))
				{
					getXmp().setComment(iimTag.getComment());
				}
				if (getXmp().getKeywords() == null)
				{
					getXmp().setKeywords(iimTag.getKeywords());
				}
			}
			else
			{
				setXmp(tag instanceof XmpTag ? (XmpTag)tag : null);
			}
		}
		else
		{
			getOtherTags().add(tag);
		}

		all_tags = null;
	}

	public final void RemoveTag(ImageTag tag)
	{
		if (tag instanceof IFDTag)
		{
			setExif(null);
		}
		else if (tag instanceof XmpTag)
		{
			setXmp(null);
		}
		else
		{
			getOtherTags().remove(tag);
		}

		all_tags = null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods (Tag)

	/** 
		Gets the tag types contained in the current instance.
	 
	 <value>
		A bitwise combined <see cref="Rasad.Core.Media.MediaMetadataManagement.TagTypes" />
		containing the tag types contained in the current
		instance.
	 </value>
	*/
	@Override
	public TagTypes getTagTypes()
	{
		TagTypes types = TagTypes.None;

		for (ImageTag tag : getAllTags())
		{
			types = Rasad.Core.Media.MediaMetadataManagement.TagTypes.forValue(types.getValue() | tag.getTagTypes().getValue());
		}

		return types;
	}

	/** 
		Clears all of the child tags.
	*/
	@Override
	public void Clear()
	{
		for (ImageTag tag : getAllTags())
		{
			tag.Clear();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties (ImageTag)

	/** 
		Gets or sets the keywords for the image described
		by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the keywords of the
		current instace.
	 </value>
	*/
	@Override
	public String[] getKeywords()
	{
		for (ImageTag tag : getAllTags())
		{
			String[] value = tag.getKeywords();
			if (value != null && value.length > 0)
			{
				return value;
			}
		}

		return new String[] {};
	}
	@Override
	public void setKeywords(String[] value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setKeywords(value);
		}
	}

	/** 
		Gets or sets the rating for the image described
		by the current instance.
	 
	 <value>
		A <see cref="System.Nullable"/> containing the rating of the
		current instace.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override Nullable<uint> getRating()
	@Override
	public Integer getRating()
	{
		for (ImageTag tag : getAllTags())
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Nullable<uint> value = tag.Rating;
			Integer value = tag.getRating();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setRating(Nullable<uint> value)
	@Override
	public void setRating(Integer value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setRating(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			DateTime value = tag.getDateTime();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setDateTime(DateTime value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setDateTime(value);
		}
	}

	/** 
		Gets or sets the orientation of the image described
		by the current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Image.ImageOrientation" /> containing the orienatation of the
		image
	 </value>
	*/
	@Override
	public ImageOrientation getOrientation()
	{
		for (ImageTag tag : getAllTags())
		{
			ImageOrientation value = tag.getOrientation();

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if ((uint) value >= 1U && (uint) value <= 8U)
			if ((int) value.getValue() >= 1 && (int) value.getValue() <= 8)
			{
				return value;
			}
		}

		return ImageOrientation.None;
	}
	@Override
	public void setOrientation(ImageOrientation value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setOrientation(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			String value = tag.getSoftware();

			if (!tangible.StringHelper.isNullOrEmpty(value))
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setSoftware(String value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setSoftware(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			Double value = tag.getLatitude();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setLatitude(Double value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setLatitude(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			Double value = tag.getLongitude();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setLongitude(Double value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setLongitude(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			Double value = tag.getAltitude();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setAltitude(Double value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setAltitude(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			Double value = tag.getExposureTime();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setExposureTime(Double value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setExposureTime(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			Double value = tag.getFNumber();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setFNumber(Double value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setFNumber(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Nullable<uint> value = tag.ISOSpeedRatings;
			Integer value = tag.getISOSpeedRatings();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setISOSpeedRatings(Nullable<uint> value)
	@Override
	public void setISOSpeedRatings(Integer value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setISOSpeedRatings(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			Double value = tag.getFocalLength();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setFocalLength(Double value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setFocalLength(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Nullable<uint> value = tag.FocalLengthIn35mmFilm;
			Integer value = tag.getFocalLengthIn35mmFilm();

			if (value != null)
			{
				return value;
			}
		}

		return null;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setFocalLengthIn35mmFilm(Nullable<uint> value)
	@Override
	public void setFocalLengthIn35mmFilm(Integer value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setFocalLengthIn35mmFilm(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			String value = tag.getMake();

			if (!tangible.StringHelper.isNullOrEmpty(value))
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setMake(String value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setMake(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			String value = tag.getModel();

			if (!tangible.StringHelper.isNullOrEmpty(value))
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setModel(String value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setModel(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			String value = tag.getCreator();

			if (!tangible.StringHelper.isNullOrEmpty(value))
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setCreator(String value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setCreator(value);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties (Tag)


	/** 
		Gets and sets the title for the media described by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing the title for
		the media described by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	*/
	@Override
	public String getTitle()
	{
		for (ImageTag tag : getAllTags())
		{
			String value = tag.getTitle();

			if (!tangible.StringHelper.isNullOrEmpty(value))
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setTitle(String value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setTitle(value);
		}
	}

	/** 
		Gets and sets a user comment on the media represented by
		the current instance.
	 
	 <value>
		A <see cref="string" /> object containing user comments
		on the media represented by the current instance or <see
		langword="null" /> if no value is present.
	 </value>
	*/
	@Override
	public String getComment()
	{
		for (ImageTag tag : getAllTags())
		{
			String value = tag.getComment();

			if (!tangible.StringHelper.isNullOrEmpty(value))
			{
				return value;
			}
		}

		return "";
	}
	@Override
	public void setComment(String value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setComment(value);
		}
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
		for (ImageTag tag : getAllTags())
		{
			String value = tag.getCopyright();

			if (!tangible.StringHelper.isNullOrEmpty(value))
			{
				return value;
			}
		}

		return null;
	}
	@Override
	public void setCopyright(String value)
	{
		for (ImageTag tag : getAllTags())
		{
			tag.setCopyright(value);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}