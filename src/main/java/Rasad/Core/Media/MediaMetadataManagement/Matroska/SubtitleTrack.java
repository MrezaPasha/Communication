package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// SubtitleTrack.cs:
//
// Author:
//   Julien Moutte <julien@fluendo.com>
//
// Copyright (C) 2011 FLUENDO S.A.
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
 Describes a Matroska Subtitle Track.
*/
public class SubtitleTrack extends Track
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private fields

	private ArrayList<EBMLElement> unknown_elems = new ArrayList<EBMLElement> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
	 Constructs a <see cref="SubtitleTrack" /> parsing from provided
	 file data.
	 Parsing will be done reading from _file at position references by 
	 parent element's data section.
	 
	 @param _file <see cref="File" /> instance to read from.
	 @param element Parent <see cref="EBMLElement" />.
	*/
	public SubtitleTrack(File _file, EBMLElement element)
	{
		super(_file, element);
		// Here we handle the unknown elements we know, and store the rest
		for (EBMLElement elem : super.getUnknownElements())
		{
			MatroskaID matroska_id = MatroskaID.forValue(elem.getID());

			switch (matroska_id)
			{
				default:
					unknown_elems.add(elem);
					break;
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public fields

	/** 
	 List of unknown elements encountered while parsing.
	*/
	public final ArrayList<EBMLElement> getUnknownElements()
	{
		return unknown_elems;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public methods

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICodec

	/** 
	 This type of track only has text media type.
	*/
	@Override
	public MediaTypes getMediaTypes()
	{
		return MediaTypes.Text;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}