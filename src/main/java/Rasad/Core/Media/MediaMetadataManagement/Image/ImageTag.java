package Rasad.Core.Media.MediaMetadataManagement.Image;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// ImageTag.cs: This abstract class extends the Tag class by basic Image
// properties.
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
	A class to abstract the image tags. It extends the <see cref="Tag"/>
	class and adds some image specific propties.
*/
public abstract class ImageTag extends Tag
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets or sets the keywords for the image described
		by the current instance.
	 
	 <value>
		A <see cref="string[]" /> containing the keywords of the
		current instace.
	 </value>
	*/
	public String[] getKeywords()
	{
		return new String [] {};
	}
	public void setKeywords(String[] value)
	{
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
//ORIGINAL LINE: public virtual Nullable<uint> getRating()
	public Integer getRating()
	{
		return null;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual void setRating(Nullable<uint> value)
	public void setRating(Integer value)
	{
	}

	/** 
		Gets or sets the time when the image, the current instance
		belongs to, was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the time the image was taken.
	 </value>
	*/
	public DateTime getDateTime()
	{
		return null;
	}
	public void setDateTime(DateTime value)
	{
	}

	/** 
		Gets or sets the orientation of the image described
		by the current instance.
	 
	 <value>
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.Image.ImageOrientation" /> containing the orientation of the
		image
	 </value>
	*/
	public ImageOrientation getOrientation()
	{
		return ImageOrientation.None;
	}
	public void setOrientation(ImageOrientation value)
	{
	}

	/** 
		Gets or sets the software the image, the current instance
		belongs to, was created with.
	 
	 <value>
		A <see cref="string" /> containing the name of the
		software the current instace was created with.
	 </value>
	*/
	public String getSoftware()
	{
		return null;
	}
	public void setSoftware(String value)
	{
	}

	/** 
		Gets or sets the latitude of the GPS coordinate the current
		image was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the latitude ranging from -90.0
		to +90.0 degrees.
	 </value>
	*/
	public Double getLatitude()
	{
		return null;
	}
	public void setLatitude(Double value)
	{
	}

	/** 
		Gets or sets the longitude of the GPS coordinate the current
		image was taken.
	 
	 <value>
		A <see cref="System.Nullable"/> with the longitude ranging from -180.0
		to +180.0 degrees.
	 </value>
	*/
	public Double getLongitude()
	{
		return null;
	}
	public void setLongitude(Double value)
	{
	}

	/** 
		Gets or sets the altitude of the GPS coordinate the current
		image was taken. The unit is meter.
	 
	 <value>
		A <see cref="System.Nullable"/> with the altitude. A positive value
		is above sea level, a negative one below sea level. The unit is meter.
	 </value>
	*/
	public Double getAltitude()
	{
		return null;
	}
	public void setAltitude(Double value)
	{
	}

	/** 
		Gets the exposure time the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the exposure time in seconds.
	 </value>
	*/
	public Double getExposureTime()
	{
		return null;
	}
	public void setExposureTime(Double value)
	{
	}

	/** 
		Gets the FNumber the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the FNumber.
	 </value>
	*/
	public Double getFNumber()
	{
		return null;
	}
	public void setFNumber(Double value)
	{
	}

	/** 
		Gets the ISO speed the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the ISO speed as defined in ISO 12232.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual Nullable<uint> getISOSpeedRatings()
	public Integer getISOSpeedRatings()
	{
		return null;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual void setISOSpeedRatings(Nullable<uint> value)
	public void setISOSpeedRatings(Integer value)
	{
	}

	/** 
		Gets the focal length the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the focal length in millimeters.
	 </value>
	*/
	public Double getFocalLength()
	{
		return null;
	}
	public void setFocalLength(Double value)
	{
	}

	/** 
		Gets the focal length the image, the current instance belongs
		to, was taken with, assuming a 35mm film camera.
	 
	 <value>
		A <see cref="System.Nullable"/> with the focal length in 35mm equivalent in millimeters.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual Nullable<uint> getFocalLengthIn35mmFilm()
	public Integer getFocalLengthIn35mmFilm()
	{
		return null;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public virtual void setFocalLengthIn35mmFilm(Nullable<uint> value)
	public void setFocalLengthIn35mmFilm(Integer value)
	{
	}

	/** 
		Gets the manufacture of the recording equipment the image, the
		current instance belongs to, was taken with.
	 
	 <value>
		A <see cref="string" /> with the manufacture name.
	 </value>
	*/
	public String getMake()
	{
		return null;
	}
	public void setMake(String value)
	{
	}

	/** 
		Gets the model name of the recording equipment the image, the
		current instance belongs to, was taken with.
	 
	 <value>
		A <see cref="string" /> with the model name.
	 </value>
	*/
	public String getModel()
	{
		return null;
	}
	public void setModel(String value)
	{
	}

	/** 
		Gets or sets the creator of the image.
	 
	 <value>
		A <see cref="string" /> with the name of the creator.
	 </value>
	*/
	public String getCreator()
	{
		return null;
	}
	public void setCreator(String value)
	{
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}