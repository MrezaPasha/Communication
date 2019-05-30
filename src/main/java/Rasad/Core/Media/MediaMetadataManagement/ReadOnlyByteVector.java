package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

//
// ReadOnlyByteVector.cs: This class extends ByteVector" to provide an
// immutable version.
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2007 Brian Nickel
// 
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
// 
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//


/** 
	This class extends <see cref="ByteVector" /> to provide an
	immutable version.
*/
public final class ReadOnlyByteVector extends ByteVector
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="ReadOnlyByteVector" /> with no contents.
	*/
	public ReadOnlyByteVector()
	{
		super();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ReadOnlyByteVector" /> of a specified length filled
		with bytes of a specified value.
	 
	 @param size
		A <see cref="int" /> specifying the number of bytes to
		add to the new instance.
	 
	 @param value
		A <see cref="byte" /> specifying the value to use for the
		bytes added to the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ReadOnlyByteVector(int size, byte value)
	public ReadOnlyByteVector(int size, byte value)
	{
		super(size, value);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ReadOnlyByteVector" /> of a specified length filled
		with bytes with a value of zero.
	 
	 @param size
		A <see cref="int" /> specifying the number of bytes to
		add to the new instance.
	 
	 
		<p>To specify the value to fill the new instance with,
		use <see cref="ReadOnlyByteVector(int,byte)" />.</p>
	 
	*/
	public ReadOnlyByteVector(int size)
	{
		this(size, 0);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ReadOnlyByteVector" /> by copying the contents from
		another instance.
	 
	 @param vector
		A <see cref="ByteVector" /> object to copy the values
		from.
	 
	*/
	public ReadOnlyByteVector(ByteVector vector)
	{
		super(vector);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ReadOnlyByteVector" /> by copying a specified
		number of bytes from an array.
	 
	 @param data
		A <see cref="byte[]" /> to copy values from.
	 
	 @param length
		A <see cref="int" /> specifying the number of bytes to
		copy.
	 
	 
		<p>If copying the entire contents of an array, use
		<see cref="ReadOnlyByteVector(byte[])" />.</p>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ReadOnlyByteVector(byte [] data, int length)
	public ReadOnlyByteVector(byte[] data, int length)
	{
		super(data, (byte)length);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ReadOnlyByteVector" /> by copying the contents of a
		specified array.
	 
	 @param data
		A <see cref="byte[]" /> to copy values from.
	 
	 
		<p>To copy only part of the array, use <see
		cref="ReadOnlyByteVector(byte[],int)" />.</p>
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ReadOnlyByteVector(params byte [] data)
	public ReadOnlyByteVector(Byte... data)
	{
		super(data);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Operators

	/** 
		Implicitly converts a <see cref="byte" /> to a new
		<see cref="ReadOnlyByteVector" />.
	 
	 @param value
		A <see cref="byte" /> object to convert.
	 
	 @return 
		A <see cref="ReadOnlyByteVector" /> equivalent to
		<paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static implicit operator ReadOnlyByteVector(byte value)
	public static implicit operator ReadOnlyByteVector(byte value)
	{
		return new ReadOnlyByteVector(value);
	}

	/** 
		Implicitly converts a <see cref="byte[]" /> to a new
		<see cref="ReadOnlyByteVector" />.
	 
	 @param value
		A <see cref="byte[]" /> object to convert.
	 
	 @return 
		A <see cref="ReadOnlyByteVector" /> equivalent to
		<paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static implicit operator ReadOnlyByteVector(byte [] value)
	public static implicit operator ReadOnlyByteVector(byte[] value)
	{
		return new ReadOnlyByteVector(value);
	}

	/** 
		Implicitly converts a <see cref="string" /> object to a
		new <see cref="ReadOnlyByteVector" /> using the UTF-8
		encoding.
	 
	 @param value
		A <see cref="string" /> object to convert.
	 
	 @return 
		A <see cref="ReadOnlyByteVector" /> equivalent to
		<paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator ReadOnlyByteVector(String value)
	{
		return new ReadOnlyByteVector(ByteVector.FromString(value, StringType.UTF8));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IList<T>

	/** 
		Gets whether or not the current instance is read-only.
	 
	 <value>
		Always <see langword="true" />.
	 </value>
	*/
	@Override
	public boolean getIsReadOnly()
	{
		return true;
	}

	/** 
		Gets whether or not the current instance is fixed size.
	 
	 <value>
		Always <see langword="true" />.
	 </value>
	*/
	@Override
	public boolean getIsFixedSize()
	{
		return true;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}