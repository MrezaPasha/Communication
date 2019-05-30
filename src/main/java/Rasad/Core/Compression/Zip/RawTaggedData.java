package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

/** 
 A raw binary tagged value
*/
public class RawTaggedData implements ITaggedData
{
	/** 
	 Initialise a new instance.
	 
	 @param tag The tag ID.
	*/
	public RawTaggedData(short tag)
	{
		_tag = tag;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ITaggedData Members

	/** 
	 Get the ID for this tagged data value.
	*/
	public final short getTagID()
	{
		return _tag;
	}
	public final void setTagID(short value)
	{
		_tag = value;
	}

	/** 
	 Set the data from the raw values provided.
	 
	 @param data The raw data to extract values from.
	 @param offset The index to start extracting values from.
	 @param count The number of bytes available.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void SetData(byte[] data, int offset, int count)
	public final void SetData(byte[] data, int offset, int count)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _data = new byte[count];
		_data = new byte[count];
		System.arraycopy(data, offset, _data, 0, count);
	}

	/** 
	 Get the binary data representing this instance.
	 
	 @return The raw binary data representing this instance.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] GetData()
	public final byte[] GetData()
	{
		return _data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get /set the binary data representing this instance.
	 
	 @return The raw binary data representing this instance.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getData()
	public final byte[] getData()
	{
		return _data;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setData(byte[] value)
	public final void setData(byte[] value)
	{
		_data = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 The tag ID for this instance.
	*/
	private short _tag;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] _data;
	private byte[] _data;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}