package Rasad.Core.Media.MediaMetadataManagement.Mpeg4;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// AppleDataBox.cs: Provides an implementation of an Apple DataBox.
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
	This class extends <see cref="FullBox" /> to provide an
	implementation of an Apple DataBox.
*/
public class AppleDataBox extends FullBox
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Enums

	/** 
		Specifies the type of data contained in a box.
	*/
	public enum FlagType
	{
		/** 
			The box contains UTF-8 text.
		*/
		ContainsText(0x01),

		/** 
			The box contains binary data.
		*/
		ContainsData(0x00),

		/** 
			The box contains data for a tempo box.
		*/
		ForTempo(0x15),

		/** 
			The box contains a raw JPEG image.
		*/
		ContainsJpegData(0x0D),

		/** 
			The box contains a raw PNG image.
		*/
		ContainsPngData(0x0E);

		public static final int SIZE = java.lang.Integer.SIZE;

		private int intValue;
		private static java.util.HashMap<Integer, FlagType> mappings;
		private static java.util.HashMap<Integer, FlagType> getMappings()
		{
			if (mappings == null)
			{
				synchronized (FlagType.class)
				{
					if (mappings == null)
					{
						mappings = new java.util.HashMap<Integer, FlagType>();
					}
				}
			}
			return mappings;
		}

		private FlagType(int value)
		{
			intValue = value;
			getMappings().put(value, this);
		}

		public int getValue()
		{
			return intValue;
		}

		public static FlagType forValue(int value)
		{
			return getMappings().get(value);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the box data.
	*/
	private ByteVector data;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="AppleDataBox" /> with a provided header and handler
		by reading the contents from a specified file.
	 
	 @param header
		A <see cref="BoxHeader" /> object containing the header
		to use for the new instance.
	 
	 @param file
		A <see cref="Rasad.Core.Media.MediaMetadataManagement.File" /> object to read the contents
		of the box from.
	 
	 @param handler
		A <see cref="IsoHandlerBox" /> object containing the
		handler that applies to the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	*/
	public AppleDataBox(BoxHeader header, Rasad.Core.Media.MediaMetadataManagement.File file, IsoHandlerBox handler)
	{
		super(header.clone(), file, handler);
		setData(LoadData(file));
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="AppleDataBox" /> with specified data and flags.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the data to
		store in the new instance.
	 
	 @param flags
		A <see cref="uint" /> value containing flags to use for
		the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public AppleDataBox(ByteVector data, uint flags)
	public AppleDataBox(ByteVector data, int flags)
	{
		super("data", 0, flags);
		setData(data);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the position of the data contained in the current
		instance, after any box specific headers.
	 
	 <value>
		A <see cref="long" /> value containing the position of
		the data contained in the current instance.
	 </value>
	*/
	@Override
	protected long getDataPosition()
	{
		return super.getDataPosition() + 4;
	}

	/** 
		Gets and sets the data contained in the current instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the data
		contained in the current instance.
	 </value>
	*/
	@Override
	public ByteVector getData()
	{
		return data;
	}
	@Override
	public void setData(ByteVector value)
	{
		data = value != null ? value : new ByteVector();
	}

	/** 
		Gets and sets the text contained in the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the text
		contained in the current instance, or <see
		langword="null" /> if the box is not flagged as
		containing text.
	 </value>
	*/
	public final String getText()
	{
		return ((getFlags() &  FlagType.ContainsText.getValue()) != 0) ? getData().toString(StringType.UTF8) : null;
	}
	public final void setText(String value)
	{
		setFlags(FlagType.ContainsText.getValue());
		setData(ByteVector.FromString(value, StringType.UTF8));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

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
	@Override
	protected ByteVector Render(ByteVector topData)
	{
		ByteVector output = new ByteVector(4);
		output.add(topData);
		return super.Render(output);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}