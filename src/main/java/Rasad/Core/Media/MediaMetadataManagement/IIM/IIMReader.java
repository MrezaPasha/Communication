package Rasad.Core.Media.MediaMetadataManagement.IIM;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
//  IIMReader.cs
//
//  Author:
//       Eberhard Beilharz <eb1@sil.org>
//
//  Copyright (c) 2012 Eberhard Beilharz
//
//  This library is free software; you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as
//  published by the Free Software Foundation; either version 2.1 of the
//  License, or (at your option) any later version.
//
//  This library is distributed in the hope that it will be useful, but
//  WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
//  Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA


/** 
 Processes all IPTC-IIM segments
*/
public class IIMReader
{
	/** 
	 The magic bytes that start a new IPTC-IIM segment
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static readonly byte[] IPTC_IIM_SEGMENT = new byte[] { 0x1C, 0x02};
	private static final byte[] IPTC_IIM_SEGMENT = new byte[] {0x1C, 0x02};

	private IIMTag Tag;
	private IIMTag getTag()
	{
		return Tag;
	}
	private void setTag(IIMTag value)
	{
		Tag = value;
	}
	private ByteVector Data;
	private ByteVector getData()
	{
		return Data;
	}
	private void setData(ByteVector value)
	{
		Data = value;
	}

	public IIMReader(ByteVector data)
	{
		setData(data);
		setTag(new IIMTag());
	}

	public final IIMTag Process()
	{
		// now process the IIM segments which all start with 0x1C 0x02 followed by the type
		// of the IIM segment
		int findOffset = 0;
		int count = 0;
		for (int i = getData().Find(IPTC_IIM_SEGMENT, findOffset); i >= findOffset; i = getData().Find(IPTC_IIM_SEGMENT, findOffset))
		{
			count++;
			// skip over segment marker
			i += IPTC_IIM_SEGMENT.length;

			int len = getData().Mid(i + 1).ToUShort();

			// ENHANCE: enhance encoding used for string conversion. Unfortunately this is
			// not detectable from IIM data.
			switch (getData().get(i))
			{
				case 5: // Object Name
					getTag().setTitle(getData().toString(StringType.Latin1, i + 3, len));
					break;
				case 25: // Keywords
					getTag().AddKeyword(getData().toString(StringType.Latin1, i + 3, len));
					break;
				case 80: // By-line
					getTag().setCreator(getData().toString(StringType.Latin1, i + 3, len));
					break;
				case 116: // Copyright notice
					getTag().setCopyright(getData().toString(StringType.Latin1, i + 3, len));
					break;
				case 120: // Caption/Abstract
					getTag().setComment(getData().toString(StringType.Latin1, i + 3, len));
					break;
			}
			findOffset = i + 3 + len;
		}
		if (count == 0)
		{
			return null;
		}
		return getTag();
	}
}