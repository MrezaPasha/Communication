package Rasad.Core.Media.MediaMetadataManagement.Tiff.Rw2;

import Rasad.Core.Media.MediaMetadataManagement.IFD.Tags.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Tiff.*;

//
// IFDTag.cs: Handles Panasonics weird metadata structure.
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//
// Copyright (C) 2010 Ruben Vermeersch
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
	Handles the weird structure of Panasonic metadata.
*/
public class IFDTag extends Rasad.Core.Media.MediaMetadataManagement.IFD.IFDTag
{
	private File file;

	public IFDTag(File file)
	{
		super();
		this.file = file;
	}

	/** 
		Gets the ISO speed the image, the current instance belongs
		to, was taken with.
	 
	 <value>
		A <see cref="System.Nullable"/> with the ISO speed as defined in ISO 12232.
	 </value>
	 
		<p>Panasonic stores these in a somewhat unstandard location.</p>
	 
	*/
		// TODO: The value in JPGFromRAW should probably be used as well.
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override Nullable<uint> getISOSpeedRatings()
	@Override
	public Integer getISOSpeedRatings()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return Structure.GetLongValue(0, (ushort) PanasonicMakerNoteEntryTag.ISO);
		return Structure.GetLongValue(0, (short) PanasonicMakerNoteEntryTag.ISO.getValue());
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setISOSpeedRatings(Nullable<uint> value)
	@Override
	public void setISOSpeedRatings(Integer value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Structure.SetLongValue(0, (ushort) PanasonicMakerNoteEntryTag.ISO, value.HasValue ? (uint) value : 0);
		Structure.SetLongValue(0, (short) PanasonicMakerNoteEntryTag.ISO.getValue(), value.HasValue ? (int) value : 0);
	}

	/** 
		Gets the focal length the image, the current instance belongs
		to, was taken with, assuming a 35mm film camera.
	 
	 <value>
		A <see cref="System.Nullable"/> with the focal length in 35mm equivalent in millimeters.
	 </value>
	 
		<p>Panasonic stores these in a somewhat unstandard location.</p>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override Nullable<uint> getFocalLengthIn35mmFilm()
	@Override
	public Integer getFocalLengthIn35mmFilm()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var jpg = file.JpgFromRaw;
		if (jpg == null)
		{
			return super.getFocalLengthIn35mmFilm();
		}
		Object tempVar = jpg.GetTag(TagTypes.TiffIFD, true);
		Image.ImageTag tag = tempVar instanceof Image.ImageTag ? (Image.ImageTag)tempVar : null;
		if (tag == null)
		{
			return super.getFocalLengthIn35mmFilm();
		}
		Nullable<Integer> tempVar2 = tag.getFocalLengthIn35mmFilm();
		return (tempVar2 != null) ? tempVar2 : super.getFocalLengthIn35mmFilm();
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void setFocalLengthIn35mmFilm(Nullable<uint> value)
	@Override
	public void setFocalLengthIn35mmFilm(Integer value)
	{
		Object tempVar = file.JpgFromRaw.GetTag(TagTypes.TiffIFD, true);
		(tempVar instanceof Image.ImageTag ? (Image.ImageTag)tempVar : null).setFocalLengthIn35mmFilm(value);
	}
}