package Rasad.Core.Media.MediaMetadataManagement.Ogg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

//
// Paginator.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Original Source:
//   oggpage.cpp from Rasad.Core.Media.MediaMetadataManagement
//
// Copyright (C) 2006-2007 Brian Nickel
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
	This class accepts a sequence of pages for a single Ogg stream,
	accepts changes, and produces a new sequence of pages to write to
	disk.
*/
public class Paginator
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the packets to paginate.
	*/
	private ByteVectorCollection packets = new ByteVectorCollection();

	/** 
		Contains the first page header.
	*/
	private PageHeader first_page_header = null;

	/** 
		Contains the codec to use.
	*/
	private Codec codec;

	/** 
		contains the number of pages read.
	*/
	private int pages_read = 0;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Paginator" /> for a given <see cref="Codec" />
		object.
	 
	 @param codec
		A <see cref="Codec"/> object to use when processing
		packets.
	 
	*/
	public Paginator(Codec codec)
	{
		this.codec = codec;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Adds the next page to the current instance.
	 
	 @param page
		The next <see cref="Page" /> object found in the stream.
	 
	*/
	public final void AddPage(Page page)
	{
		pages_read++;

		if (first_page_header == null)
		{
			first_page_header = page.getHeader().clone();
		}

		if (page.getPackets().length == 0)
		{
			return;
		}

		ByteVector[] page_packets = page.getPackets();

		for (int i = 0; i < page_packets.length; i++)
		{
			if ((page.getHeader().getFlags().getValue() & PageFlags.FirstPacketContinued.getValue()) != 0 && i == 0 && packets.size() > 0)
			{
				packets.get(packets.size() - 1).Add(page_packets [0]);
			}
			else
			{
				packets.add(page_packets [i]);
			}
		}
	}

	/** 
		Stores a Xiph comment in the codec-specific comment
		packet.
	 
	 @param comment
		A <see cref="XiphComment" /> object to store in the
		comment packet.
	 
	*/
	public final void SetComment(XiphComment comment)
	{
		codec.SetCommentPacket(packets, comment);
	}

	/** 
		Repaginates the pages passed into the current instance to
		handle changes made to the Xiph comment.
	 
	 @return 
		A <see cref="Page[]" /> containing the new page
		collection.
	 
	*/
	@Deprecated
	public final Page[] Paginate()
	{
		int dummy;
		tangible.OutObject<Integer> tempOut_dummy = new tangible.OutObject<Integer>();
		Page[] tempVar = Paginate(tempOut_dummy);
	dummy = tempOut_dummy.argValue;
	return tempVar;
	}

	/** 
		Repaginates the pages passed into the current instance to
		handle changes made to the Xiph comment.
	 
	 @param change
		A <see cref="int" /> value reference containing the
		the difference between the number of pages returned and
		the number of pages that were added to the class.
	 
	 @return 
		A <see cref="Page[]" /> containing the new page
		collection.
	 
	*/
	public final Page[] Paginate(tangible.OutObject<Integer> change)
	{
		// Ogg Pagination: Welcome to sucksville!
		// If you don't understand this, you're not alone.
		// It is confusing as Hell.

		// TODO: Document this method, in the mean time, there
		// is always http://xiph.org/ogg/doc/framing.html

		if (pages_read == 0)
		{
			change.argValue = 0;
			return new Page [0];
		}

		int count = pages_read;
		ByteVectorCollection packets = new ByteVectorCollection(this.packets);
		PageHeader first_header = (PageHeader) first_page_header;
		ArrayList<Page> pages = new ArrayList<Page> ();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint index = 0;
		int index = 0;
		boolean bos = first_header.getPageSequenceNumber() == 0;

		if (bos)
		{
			pages.add(new Page(new ByteVectorCollection(packets.get(0)), first_header.clone()));
			index++;
			packets.remove(0);
			count--;
		}

		int lacing_per_page = 0xfc;
		if (count > 0)
		{
			int total_lacing_bytes = 0;

			for (int i = 0; i < packets.size(); i++)
			{
				total_lacing_bytes += GetLacingValueLength(packets, i);
			}

			lacing_per_page = Math.min(total_lacing_bytes / count + 1, lacing_per_page);
		}

		int lacing_bytes_used = 0;
		ByteVectorCollection page_packets = new ByteVectorCollection();
		boolean first_packet_continued = false;

		while (packets.size() > 0)
		{
			int packet_bytes = GetLacingValueLength(packets, 0);
			int remaining = lacing_per_page - lacing_bytes_used;
			boolean whole_packet = packet_bytes <= remaining;
			if (whole_packet)
			{
				page_packets.add(packets.get(0));
				lacing_bytes_used += packet_bytes;
				packets.remove(0);
			}
			else
			{
				page_packets.add(packets.get(0).Mid(0, remaining * 0xff));
				packets.set(0, packets.get(0).Mid(remaining * 0xff));
				lacing_bytes_used += remaining;
			}

			if (lacing_bytes_used == lacing_per_page)
			{
				pages.add(new Page(page_packets, new PageHeader(first_header.clone(), index, first_packet_continued ? PageFlags.FirstPacketContinued : PageFlags.None)));
				page_packets = new ByteVectorCollection();
				lacing_bytes_used = 0;
				index++;
				count--;
				first_packet_continued = !whole_packet;
			}
		}

		if (page_packets.size() > 0)
		{
			pages.add(new Page(page_packets, new PageHeader(first_header.getStreamSerialNumber(), index, first_packet_continued ? PageFlags.FirstPacketContinued : PageFlags.None)));
			index++;
			count--;
		}
		change.argValue = -count;
		return pages.toArray(new Page[0]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Methods

	/** 
		Gets the number of lacing value bytes that would be
		required for a given packet.
	 
	 @param packets
		A <see cref="ByteVectorCollection" /> object containing
		the packet.
	 
	 @param index
		A <see cref="int" /> value containing the index of the
		packet to compute.
	 
	 @return 
		A <see cref="int" /> value containing the number of bytes
		needed to store the length.
	 
	*/
	private static int GetLacingValueLength(ByteVectorCollection packets, int index)
	{
		int size = packets.get(index).getCount();
		return size / 0xff + ((index + 1 < packets.size() || size % 0xff > 0) ? 1 : 0);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}