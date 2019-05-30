package Rasad.Core.Media.MediaMetadataManagement.Ogg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// PageHeader.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   oggpage.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2005-2007 Brian Nickel
// Copyright (C) 2003 Scott Wheeler (Original Implementation)
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
	This class provides a representation of an Ogg page.
*/
public class Page
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Properties

	/** 
		Contains the page header.
	*/
	private PageHeader header = new PageHeader();

	/** 
		Contains the packets.
	*/
	private ByteVectorCollection packets;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and intializes a new instance of <see
		cref="Page" /> with a specified header and no packets.
	 
	 @param header
		A <see cref="PageHeader"/> object to use as the header of
		the new instance.
	 
	*/
	protected Page(PageHeader header)
	{
		this.header = header.clone();
		packets = new ByteVectorCollection();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Page" /> by reading a raw Ogg page from a specified
		position in a specified file.
	 
	 @param file
		A <see cref="File" /> object containing the file from
		which the contents of the new instance are to be read.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		read.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="position" /> is less than zero or greater
		than the size of the file.
	 
	 @exception CorruptFileException
		The Ogg identifier could not be found at the correct
		location.
	 
	*/
	public Page(File file, long position)
	{
		this(new PageHeader(file, position));
		file.Seek(position + header.getSize());

		for (int packet_size : header.getPacketSizes())
		{
			packets.add(file.ReadBlock(packet_size));
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Page" /> with a specified header and packets.
	 
	 @param packets
		A <see cref="ByteVectorCollection" /> object containing
		packets to use for the new instance.
	 
	 @param header
		A <see cref="PageHeader"/> object to use as the header of
		the new instance.
	 
	 @exception ArgumentNullException
		<paramref name="packets" /> is <see langword="null" />.
	 
	*/
	public Page(ByteVectorCollection packets, PageHeader header)
	{
		this(header.clone());
		if (packets == null)
		{
			throw new NullPointerException("packets");
		}

		this.packets = new ByteVectorCollection(packets);

		ArrayList<Integer> packet_sizes = new ArrayList<Integer> ();

		// Build a page from the list of packets.
		for (ByteVector v : packets)
		{
			packet_sizes.add(v.size());
		}

		header.setPacketSizes(tangible.IntegerLists.toArray(packet_sizes));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance as a raw Ogg page.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	public final ByteVector Render()
	{
		ByteVector data = header.Render();

		for (ByteVector v : packets)
		{
			data.add(v);
		}

		// Compute and set the checksum for the Ogg page. The
		// checksum is taken over the entire page with the 4
		// bytes reserved for the checksum zeroed and then
		// inserted in bytes 22-25 of the page header.

		ByteVector checksum = ByteVector.FromUInt(data.getChecksum(), false);

		for (int i = 0; i < 4; i++)
		{
			data.set(i + 22, checksum.get(i));
		}

		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Properties

	/** 
		Gets the header of the current instance.
	 
	 <value>
		A <see cref="PageHeader" /> object that applies to the
		current instance.
	 </value>
	*/
	public final PageHeader getHeader()
	{
		return header;
	}

	/** 
		Gets the packets contained in the current instance.
	 
	 <value>
		A <see cref="ByteVector[]" /> containing the packets
		contained in the current instance.
	 </value>
	*/
	public final ByteVector[] getPackets()
	{
		return packets.toArray(new Object[0]);
	}

	/** 
		Gets the total size of the current instance as it
		appeared on disk.
	 
	 <value>
		A <see cref="uint" /> value containing the size of the
		page, including the header, as it appeared on disk.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getSize()
	public final int getSize()
	{
		return header.getSize() + header.getDataSize();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Methods

	/** 
		Overwrites all page headers in a file starting at a
		specified position, shifting the page sequence numbers
		a set amount.
	 
	 @param file
		A <see cref="File" /> object containing the file to
		update.
	 
	 @param position
		A <see cref="long" /> value specify at what position to
		start updating.
	 
	 @param shiftTable
		A <see cref="T:System.Collections.Generic.IDictionary`2"
		/> object where the key is the serial number of the
		stream to update and the value is the amount to offset
		the page sequence numbers in the stream.
	 
	 @exception ArgumentNullException
		<paramref name="file" /> or <paramref name="shiftTable"
		/> is <see langword="null" />.
	 
	 
		When the number of pages in a stream changes, all
		subsequent pages in the stream need to have their page
		sequence number update in order to remain valid.
		Additionally, when the page sequence number changes, the
		page needs to have its checksum recomputed. This makes
		for a costly recalculation if large comment data is
		added.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void OverwriteSequenceNumbers(File file, long position, IDictionary<uint, int> shiftTable)
	public static void OverwriteSequenceNumbers(File file, long position, Map<Integer, Integer> shiftTable)
	{
		if (file == null)
		{
			throw new NullPointerException("file");
		}

		if (shiftTable == null)
		{
			throw new NullPointerException("shiftTable");
		}

		// Check to see if there are no changes to be made.
		boolean done = true;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (KeyValuePair<uint, int> pair in shiftTable)
		for (Map.Entry<Integer, Integer> pair : shiftTable.entrySet())
		{
			if (pair.getValue() != 0)
			{
				done = false;
				break;
			}
		}

		// If the file is fine, quit.
		if (done)
		{
			return;
		}

		while (position < file.getLength() - 27)
		{
			PageHeader header = new PageHeader(file, position);
			int size = (int)(header.getSize() + header.getDataSize());

			if (shiftTable.containsKey(header.getStreamSerialNumber()) && !shiftTable.get(header.getStreamSerialNumber()).equals(0))
			{
				file.Seek(position);
				ByteVector page_data = file.ReadBlock(size);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ByteVector new_data = ByteVector.FromUInt((uint)(header.PageSequenceNumber + shiftTable [header.StreamSerialNumber]), false);
				ByteVector new_data = ByteVector.FromUInt((int)(header.getPageSequenceNumber() + shiftTable.get(header.getStreamSerialNumber())), false);

				for (int i = 18; i < 22; i++)
				{
					page_data.set(i, new_data.get(i - 18));
				}
				for (int i = 22; i < 26; i++)
				{
					page_data.set(i, 0);
				}

				new_data.add(ByteVector.FromUInt(page_data.getChecksum(), false));
				file.Seek(position + 18);
				file.WriteBlock(new_data);
			}
			position += size;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}