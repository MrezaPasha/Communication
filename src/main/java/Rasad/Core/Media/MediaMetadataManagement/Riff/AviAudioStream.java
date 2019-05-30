package Rasad.Core.Media.MediaMetadataManagement.Riff;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	This class extends <see cref="AviStream" /> to provide support
	for reading audio stream data.
*/
public class AviAudioStream extends AviStream
{
	/** 
		Constructs and intializes a new instance of <see
		cref="AviAudioStream" /> with a specified stream header.
	 
	 @param header
	   A <see cref="AviStreamHeader"/> object containing the
	   stream's header.
	 
	*/
	public AviAudioStream(AviStreamHeader header)
	{
		super(header.clone());
	}

	/** 
		Parses a stream list item.
	 
	 @param id
		A <see cref="ByteVector" /> object containing the item's
		ID.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the item's
		data.
	 
	 @param start
		A <see cref="uint" /> value specifying the index in
		<paramref name="data" /> at which the item data begins.
	 
	 @param length
		A <see cref="uint" /> value specifying the length of the
		item.
	 
	*/
	@Override
	public void ParseItem(ByteVector id, ByteVector data, int start, int length)
	{
		if (Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(id, "strf"))
		{
			setCodec(new WaveFormatEx(data, start));
		}
	}
}