package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;
import java.io.*;

/** 
	This class represents and performs operations on variable length
	list of <see cref="byte" /> elements.
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public class ByteVector : IList<byte>, IComparable<ByteVector>
public class ByteVector implements List<Byte>, java.lang.Comparable<ByteVector>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Static Fields

	/** 
		Contains values to use in CRC calculation.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static uint [] crc_table = new uint[256] { 0x00000000, 0x04c11db7, 0x09823b6e, 0x0d4326d9, 0x130476dc, 0x17c56b6b, 0x1a864db2, 0x1e475005, 0x2608edb8, 0x22c9f00f, 0x2f8ad6d6, 0x2b4bcb61, 0x350c9b64, 0x31cd86d3, 0x3c8ea00a, 0x384fbdbd, 0x4c11db70, 0x48d0c6c7, 0x4593e01e, 0x4152fda9, 0x5f15adac, 0x5bd4b01b, 0x569796c2, 0x52568b75, 0x6a1936c8, 0x6ed82b7f, 0x639b0da6, 0x675a1011, 0x791d4014, 0x7ddc5da3, 0x709f7b7a, 0x745e66cd, 0x9823b6e0, 0x9ce2ab57, 0x91a18d8e, 0x95609039, 0x8b27c03c, 0x8fe6dd8b, 0x82a5fb52, 0x8664e6e5, 0xbe2b5b58, 0xbaea46ef, 0xb7a96036, 0xb3687d81, 0xad2f2d84, 0xa9ee3033, 0xa4ad16ea, 0xa06c0b5d, 0xd4326d90, 0xd0f37027, 0xddb056fe, 0xd9714b49, 0xc7361b4c, 0xc3f706fb, 0xceb42022, 0xca753d95, 0xf23a8028, 0xf6fb9d9f, 0xfbb8bb46, 0xff79a6f1, 0xe13ef6f4, 0xe5ffeb43, 0xe8bccd9a, 0xec7dd02d, 0x34867077, 0x30476dc0, 0x3d044b19, 0x39c556ae, 0x278206ab, 0x23431b1c, 0x2e003dc5, 0x2ac12072, 0x128e9dcf, 0x164f8078, 0x1b0ca6a1, 0x1fcdbb16, 0x018aeb13, 0x054bf6a4, 0x0808d07d, 0x0cc9cdca, 0x7897ab07, 0x7c56b6b0, 0x71159069, 0x75d48dde, 0x6b93dddb, 0x6f52c06c, 0x6211e6b5, 0x66d0fb02, 0x5e9f46bf, 0x5a5e5b08, 0x571d7dd1, 0x53dc6066, 0x4d9b3063, 0x495a2dd4, 0x44190b0d, 0x40d816ba, 0xaca5c697, 0xa864db20, 0xa527fdf9, 0xa1e6e04e, 0xbfa1b04b, 0xbb60adfc, 0xb6238b25, 0xb2e29692, 0x8aad2b2f, 0x8e6c3698, 0x832f1041, 0x87ee0df6, 0x99a95df3, 0x9d684044, 0x902b669d, 0x94ea7b2a, 0xe0b41de7, 0xe4750050, 0xe9362689, 0xedf73b3e, 0xf3b06b3b, 0xf771768c, 0xfa325055, 0xfef34de2, 0xc6bcf05f, 0xc27dede8, 0xcf3ecb31, 0xcbffd686, 0xd5b88683, 0xd1799b34, 0xdc3abded, 0xd8fba05a, 0x690ce0ee, 0x6dcdfd59, 0x608edb80, 0x644fc637, 0x7a089632, 0x7ec98b85, 0x738aad5c, 0x774bb0eb, 0x4f040d56, 0x4bc510e1, 0x46863638, 0x42472b8f, 0x5c007b8a, 0x58c1663d, 0x558240e4, 0x51435d53, 0x251d3b9e, 0x21dc2629, 0x2c9f00f0, 0x285e1d47, 0x36194d42, 0x32d850f5, 0x3f9b762c, 0x3b5a6b9b, 0x0315d626, 0x07d4cb91, 0x0a97ed48, 0x0e56f0ff, 0x1011a0fa, 0x14d0bd4d, 0x19939b94, 0x1d528623, 0xf12f560e, 0xf5ee4bb9, 0xf8ad6d60, 0xfc6c70d7, 0xe22b20d2, 0xe6ea3d65, 0xeba91bbc, 0xef68060b, 0xd727bbb6, 0xd3e6a601, 0xdea580d8, 0xda649d6f, 0xc423cd6a, 0xc0e2d0dd, 0xcda1f604, 0xc960ebb3, 0xbd3e8d7e, 0xb9ff90c9, 0xb4bcb610, 0xb07daba7, 0xae3afba2, 0xaafbe615, 0xa7b8c0cc, 0xa379dd7b, 0x9b3660c6, 0x9ff77d71, 0x92b45ba8, 0x9675461f, 0x8832161a, 0x8cf30bad, 0x81b02d74, 0x857130c3, 0x5d8a9099, 0x594b8d2e, 0x5408abf7, 0x50c9b640, 0x4e8ee645, 0x4a4ffbf2, 0x470cdd2b, 0x43cdc09c, 0x7b827d21, 0x7f436096, 0x7200464f, 0x76c15bf8, 0x68860bfd, 0x6c47164a, 0x61043093, 0x65c52d24, 0x119b4be9, 0x155a565e, 0x18197087, 0x1cd86d30, 0x029f3d35, 0x065e2082, 0x0b1d065b, 0x0fdc1bec, 0x3793a651, 0x3352bbe6, 0x3e119d3f, 0x3ad08088, 0x2497d08d, 0x2056cd3a, 0x2d15ebe3, 0x29d4f654, 0xc5a92679, 0xc1683bce, 0xcc2b1d17, 0xc8ea00a0, 0xd6ad50a5, 0xd26c4d12, 0xdf2f6bcb, 0xdbee767c, 0xe3a1cbc1, 0xe760d676, 0xea23f0af, 0xeee2ed18, 0xf0a5bd1d, 0xf464a0aa, 0xf9278673, 0xfde69bc4, 0x89b8fd09, 0x8d79e0be, 0x803ac667, 0x84fbdbd0, 0x9abc8bd5, 0x9e7d9662, 0x933eb0bb, 0x97ffad0c, 0xafb010b1, 0xab710d06, 0xa6322bdf, 0xa2f33668, 0xbcb4666d, 0xb8757bda, 0xb5365d03, 0xb1f740b4 };
	private static int [] crc_table = new int[] {0x00000000, 0x04c11db7, 0x09823b6e, 0x0d4326d9, 0x130476dc, 0x17c56b6b, 0x1a864db2, 0x1e475005, 0x2608edb8, 0x22c9f00f, 0x2f8ad6d6, 0x2b4bcb61, 0x350c9b64, 0x31cd86d3, 0x3c8ea00a, 0x384fbdbd, 0x4c11db70, 0x48d0c6c7, 0x4593e01e, 0x4152fda9, 0x5f15adac, 0x5bd4b01b, 0x569796c2, 0x52568b75, 0x6a1936c8, 0x6ed82b7f, 0x639b0da6, 0x675a1011, 0x791d4014, 0x7ddc5da3, 0x709f7b7a, 0x745e66cd, (int)0x9823b6e0, (int)0x9ce2ab57, (int)0x91a18d8e, (int)0x95609039, (int)0x8b27c03c, (int)0x8fe6dd8b, (int)0x82a5fb52, (int)0x8664e6e5, (int)0xbe2b5b58, (int)0xbaea46ef, (int)0xb7a96036, (int)0xb3687d81, (int)0xad2f2d84, (int)0xa9ee3033, (int)0xa4ad16ea, (int)0xa06c0b5d, (int)0xd4326d90, (int)0xd0f37027, (int)0xddb056fe, (int)0xd9714b49, (int)0xc7361b4c, (int)0xc3f706fb, (int)0xceb42022, (int)0xca753d95, (int)0xf23a8028, (int)0xf6fb9d9f, (int)0xfbb8bb46, (int)0xff79a6f1, (int)0xe13ef6f4, (int)0xe5ffeb43, (int)0xe8bccd9a, (int)0xec7dd02d, 0x34867077, 0x30476dc0, 0x3d044b19, 0x39c556ae, 0x278206ab, 0x23431b1c, 0x2e003dc5, 0x2ac12072, 0x128e9dcf, 0x164f8078, 0x1b0ca6a1, 0x1fcdbb16, 0x018aeb13, 0x054bf6a4, 0x0808d07d, 0x0cc9cdca, 0x7897ab07, 0x7c56b6b0, 0x71159069, 0x75d48dde, 0x6b93dddb, 0x6f52c06c, 0x6211e6b5, 0x66d0fb02, 0x5e9f46bf, 0x5a5e5b08, 0x571d7dd1, 0x53dc6066, 0x4d9b3063, 0x495a2dd4, 0x44190b0d, 0x40d816ba, (int)0xaca5c697, (int)0xa864db20, (int)0xa527fdf9, (int)0xa1e6e04e, (int)0xbfa1b04b, (int)0xbb60adfc, (int)0xb6238b25, (int)0xb2e29692, (int)0x8aad2b2f, (int)0x8e6c3698, (int)0x832f1041, (int)0x87ee0df6, (int)0x99a95df3, (int)0x9d684044, (int)0x902b669d, (int)0x94ea7b2a, (int)0xe0b41de7, (int)0xe4750050, (int)0xe9362689, (int)0xedf73b3e, (int)0xf3b06b3b, (int)0xf771768c, (int)0xfa325055, (int)0xfef34de2, (int)0xc6bcf05f, (int)0xc27dede8, (int)0xcf3ecb31, (int)0xcbffd686, (int)0xd5b88683, (int)0xd1799b34, (int)0xdc3abded, (int)0xd8fba05a, 0x690ce0ee, 0x6dcdfd59, 0x608edb80, 0x644fc637, 0x7a089632, 0x7ec98b85, 0x738aad5c, 0x774bb0eb, 0x4f040d56, 0x4bc510e1, 0x46863638, 0x42472b8f, 0x5c007b8a, 0x58c1663d, 0x558240e4, 0x51435d53, 0x251d3b9e, 0x21dc2629, 0x2c9f00f0, 0x285e1d47, 0x36194d42, 0x32d850f5, 0x3f9b762c, 0x3b5a6b9b, 0x0315d626, 0x07d4cb91, 0x0a97ed48, 0x0e56f0ff, 0x1011a0fa, 0x14d0bd4d, 0x19939b94, 0x1d528623, (int)0xf12f560e, (int)0xf5ee4bb9, (int)0xf8ad6d60, (int)0xfc6c70d7, (int)0xe22b20d2, (int)0xe6ea3d65, (int)0xeba91bbc, (int)0xef68060b, (int)0xd727bbb6, (int)0xd3e6a601, (int)0xdea580d8, (int)0xda649d6f, (int)0xc423cd6a, (int)0xc0e2d0dd, (int)0xcda1f604, (int)0xc960ebb3, (int)0xbd3e8d7e, (int)0xb9ff90c9, (int)0xb4bcb610, (int)0xb07daba7, (int)0xae3afba2, (int)0xaafbe615, (int)0xa7b8c0cc, (int)0xa379dd7b, (int)0x9b3660c6, (int)0x9ff77d71, (int)0x92b45ba8, (int)0x9675461f, (int)0x8832161a, (int)0x8cf30bad, (int)0x81b02d74, (int)0x857130c3, 0x5d8a9099, 0x594b8d2e, 0x5408abf7, 0x50c9b640, 0x4e8ee645, 0x4a4ffbf2, 0x470cdd2b, 0x43cdc09c, 0x7b827d21, 0x7f436096, 0x7200464f, 0x76c15bf8, 0x68860bfd, 0x6c47164a, 0x61043093, 0x65c52d24, 0x119b4be9, 0x155a565e, 0x18197087, 0x1cd86d30, 0x029f3d35, 0x065e2082, 0x0b1d065b, 0x0fdc1bec, 0x3793a651, 0x3352bbe6, 0x3e119d3f, 0x3ad08088, 0x2497d08d, 0x2056cd3a, 0x2d15ebe3, 0x29d4f654, (int)0xc5a92679, (int)0xc1683bce, (int)0xcc2b1d17, (int)0xc8ea00a0, (int)0xd6ad50a5, (int)0xd26c4d12, (int)0xdf2f6bcb, (int)0xdbee767c, (int)0xe3a1cbc1, (int)0xe760d676, (int)0xea23f0af, (int)0xeee2ed18, (int)0xf0a5bd1d, (int)0xf464a0aa, (int)0xf9278673, (int)0xfde69bc4, (int)0x89b8fd09, (int)0x8d79e0be, (int)0x803ac667, (int)0x84fbdbd0, (int)0x9abc8bd5, (int)0x9e7d9662, (int)0x933eb0bb, (int)0x97ffad0c, (int)0xafb010b1, (int)0xab710d06, (int)0xa6322bdf, (int)0xa2f33668, (int)0xbcb4666d, (int)0xb8757bda, (int)0xb5365d03, (int)0xb1f740b4};

	/** 
		Specifies whether or not to use a broken Latin-1
		behavior.
	*/
	private static boolean use_broken_latin1 = false;

	/** 
		Contains a one byte text delimiter.
	*/
	private static final ReadOnlyByteVector td1 = new ReadOnlyByteVector((int)1);

	/** 
		Contains a two byte text delimiter.
	*/
	private static final ReadOnlyByteVector td2 = new ReadOnlyByteVector((int)2);

	/** 
		Contains the last generic UTF-16 encoding read.
	 
	 
		When reading a collection of UTF-16 strings, sometimes
		only the first one will contain the BOM. In that case,
		this field will inform the file what encoding to use for
		the second string.
	 
	*/
	private static System.Text.Encoding last_utf16_encoding = System.Text.Encoding.Unicode;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** 
		Contains the internal byte list.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private List<byte> data = new List<byte>();
	private ArrayList<Byte> data = new ArrayList<Byte>();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="ByteVector" /> with a length of zero.
	*/
	public ByteVector()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ByteVector" /> by copying the values from another
		instance.
	 
	 @param vector
		A <see cref="ByteVector" /> object containing the bytes
		to be stored in the new instance.
	 
	*/
	public ByteVector(ByteVector vector)
	{
		if (vector != null)
		{
			this.data.addAll(vector);
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ByteVector" /> by copying the values from a
		specified <see cref="byte[]" />.
	 
	 @param data
		A <see cref="byte[]" /> containing the bytes to be stored
		in the new instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ByteVector(params byte [] data)
	public ByteVector(Byte... data)
	{
		if (data != null)
		{
			tangible.ByteLists.addPrimitiveArrayToList(data, this.data);
		}
	}


	/** 
		Constructs and initializes a new instance of <see
		cref="ByteVector" /> by copying a specified number of
		values from a specified <see cref="byte[]" />.
	 
	 @param data
		A <see cref="byte[]" /> containing the bytes to be stored
		in the new instance.
	 
	 @param length
		A <see cref="int" /> value specifying the number of bytes
		to be copied to the new instance.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="length" /> is less than zero or greater
		than the length of the data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ByteVector(byte [] data, int length)
	public ByteVector(byte[] data, int length)
	{
		if (length > data.length)
		{
			throw new IndexOutOfBoundsException("length", "Length exceeds size of data.");
		}

		if (length < 0)
		{
			throw new IndexOutOfBoundsException("length", "Length is less than zero.");
		}

		if (length == data.length)
		{
			tangible.ByteLists.addPrimitiveArrayToList(data, this.data);
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] array = new byte[length];
			byte [] array = new byte[length];
			System.arraycopy(data, 0, array, 0, length);
			tangible.ByteLists.addPrimitiveArrayToList(array, this.data);
		}
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ByteVector" /> of specified size containing bytes
		with a zeroed value.
	 
	 @param size
		A <see cref="int" /> value specifying the number of bytes
		to be stored in the new instance.
	 
	 
		Each element of the new instance will have a value of
		<c>0x00</c>. <see cref="ByteVector(int,byte)" /> to fill
		a new instance with a specified value.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="size" /> is less than zero.
	 
	*/
	public ByteVector(int size)
	{
		this(size, 0);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="ByteVector" /> of specified size containing bytes
		of a specified value.
	 
	 @param size
		A <see cref="int" /> value specifying the number of bytes
		to be stored in the new instance.
	 
	 @param value
		A <see cref="byte" /> value specifying the value to be
		stored in the new instance.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="size" /> is less than zero.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ByteVector(int size, byte value)
	public ByteVector(int size, byte value)
	{
		if (size < 0)
		{
			throw new IndexOutOfBoundsException("size", "Size is less than zero.");
		}

		if (size == 0)
		{
			return;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] data = new byte [size];
		byte [] data = new byte [size];

		for (int i = 0; i < size; i++)
		{
			data[i] = value;
		}

		tangible.ByteLists.addPrimitiveArrayToList(data, this.data);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the data stored in the current instance.
	 
	 <value>
		A <see cref="byte[]" /> containing the data stored in the
		current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte [] getData()
	public final byte[] getData()
	{
		return tangible.ByteLists.toArray(this.data);
	}

	/** 
		Gets whether or not the current instance is empty.
	 
	 <value>
		A <see cref="bool" /> value indicating whether or not the
		current instance is empty.
	 </value>
	*/
	public final boolean getIsEmpty()
	{
		return this.data.isEmpty();
	}

	/** 
		Gets the CRC-32 checksum of the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the CRC-32 checksum
		of the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getChecksum()
	public final int getChecksum()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint sum = 0;
		int sum = 0;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach(byte b in this.data)
		for (byte b : this.data)
		{
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
			sum = (sum << 8) ^ crc_table [((sum >>> 24) & 0xFF) ^ b];
		}

		return sum;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Static Properties

	/** 
		Gets and sets whether or not to use a broken behavior for
		Latin-1 strings, common to ID3v1 and ID3v2 tags.
	 
	 <value>
		<see langword="true" /> if the broken behavior is to be
		used. Otherwise, <see langword="false" />.
	 </value>
	 
		<p>Many media players and taggers incorrectly treat
		Latin-1 fields as "default encoding" fields. As such, a
		tag may end up with Windows-1250 encoded text. While this
		problem would be apparent when moving a file from one
		computer to another, it would not be apparent on the
		original machine. By setting this property to <see
		langword="true" />, your program will behave like Windows
		Media Player and others, who read tags with this broken
		behavior.</p>
		<p>Please note that Rasad.Core.Media.MediaMetadataManagement# stores tag data in Unicode
		formats at every possible instance to avoid these
		problems in tags it has written.</p>
	 
	*/
	public static boolean getUseBrokenLatin1Behavior()
	{
		return use_broken_latin1;
	}
	public static void setUseBrokenLatin1Behavior(boolean value)
	{
		use_broken_latin1 = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Creates a new instance of <see cref="ByteVector" />
		containing a specified range of elements from the current
		instance.
	 
	 @param startIndex
		A <see cref="int" /> value specifying the index at which
		to start copying elements from the current instance.
	 
	 @param length
		A <see cref="int" /> value specifying the number of
		elements to copy from the current instance.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="startIndex" /> is less than zero or
		greater than or equal to <see cref="Count" />. OR
		<paramref name="length" /> is less than zero or greater
		than the amount of available data.
	 
	*/
	public final ByteVector Mid(int startIndex, int length)
	{
		if (startIndex < 0 || startIndex > getCount())
		{
			throw new IndexOutOfBoundsException("startIndex");
		}

		if (length < 0 || startIndex + length > getCount())
		{
			throw new IndexOutOfBoundsException("length");
		}

		if (length == 0)
		{
			return new ByteVector();
		}

		if (startIndex + length > this.data.size())
		{
			length = this.data.size() - startIndex;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] data = new byte [length];
		byte [] data = new byte [length];

		this.data.CopyTo(startIndex, data, 0, length);

		return data;
	}

	/** 
		Creates a new instance of <see cref="ByteVector" />
		containing elements from the current instance starting
		from a specified point.
	 
	 @param index
		A <see cref="int" /> value specifying the index at which
		to start copying elements from the current instance.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="ndex" /> is less than zero or greater
		than or equal to <see cref="Count" />.
	 
	*/
	public final ByteVector Mid(int index)
	{
		return Mid(index, getCount() - index);
	}

	/** 
		Finds the first byte-aligned occurance of a pattern in
		the current instance, starting at a specified position.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to search for in the current instance.
	 
	 @param offset
		A <see cref="int"/> value specifying the index in the
		current instance at which to start searching.
	 
	 @param byteAlign
		A <see cref="int"/> value specifying the byte alignment
		of the pattern to search for, relative to <paramref
		name="offset" />.
	 
	 @return 
		A <see cref="int"/> value containing the index at which
		<paramref name="pattern" /> was found in the current
		instance, or -1 if the pattern was not found. The
		difference between the position and <paramref
		name="offset" /> will be divisible by <paramref
		name="byteAlign" />.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="offset" /> is less than zero or
		<paramref name="byteAlign" /> is less than 1.
	 
	*/
	public final int Find(ByteVector pattern, int offset, int byteAlign)
	{
		if (pattern == null)
		{
			throw new NullPointerException("pattern");
		}

		if (offset < 0)
		{
			throw new IndexOutOfBoundsException("offset", "offset must be at least 0.");
		}

		if (byteAlign < 1)
		{
			throw new IndexOutOfBoundsException("byteAlign", "byteAlign must be at least 1.");
		}

		if (pattern.size() > getCount() - offset)
		{
			return -1;
		}

		// Let's go ahead and special case a pattern of size one
		// since that's common and easy to make fast.

		if (pattern.size() == 1)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte p = pattern [0];
			byte p = pattern.get(0);
			for (int i = offset; i < this.data.size(); i += byteAlign)
			{
				if (this.data.get(i).equals(p))
				{
					return i;
				}
			}
			return -1;
		}

		int [] last_occurrence = new int [256];
		for (int i = 0; i < 256; ++i)
		{
			last_occurrence [i] = pattern.size();
		}

		for (int i = 0; i < pattern.size() - 1; ++i)
		{
			last_occurrence [pattern.get(i)] = pattern.size() - i - 1;
		}

		for (int i = pattern.size() - 1 + offset; i < this.data.size(); i += last_occurrence [this.data.get(i)])
		{
			int iBuffer = i;
			int iPattern = pattern.size() - 1;

			while (iPattern >= 0 && this.data.get(iBuffer).equals(pattern.get(iPattern)))
			{
				--iBuffer;
				--iPattern;
			}

			if (-1 == iPattern && (iBuffer + 1 - offset) % byteAlign == 0)
			{
				return iBuffer + 1;
			}
		}

		return -1;
	}

	/** 
		Finds the first occurance of a pattern in the current
		instance, starting at a specified position.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to search for in the current instance.
	 
	 @param offset
		A <see cref="int"/> value specifying the index in the
		current instance at which to start searching.
	 
	 @return 
		A <see cref="int"/> value containing the index at which
		<paramref name="pattern" /> was found in the current
		instance, or -1 if the pattern was not found.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="offset" /> is less than zero.
	 
	*/
	public final int Find(ByteVector pattern, int offset)
	{
		return Find(pattern, offset, 1);
	}

	/** 
		Finds the first occurance of a pattern in the current
		instance.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to search for in the current instance.
	 
	 @return 
		A <see cref="int"/> value containing the index at which
		<paramref name="pattern" /> was found in the current
		instance, or -1 if the pattern was not found.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final int Find(ByteVector pattern)
	{
		return Find(pattern, 0, 1);
	}

	/** 
		Finds the last byte-aligned occurance of a pattern in
		the current instance, starting before a specified
		position.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to search for in the current instance.
	 
	 @param offset
		A <see cref="int"/> value specifying the index in the
		current instance at which to start searching.
	 
	 @param byteAlign
		A <see cref="int"/> value specifying the byte alignment
		of the pattern to search for, relative to <paramref
		name="offset" />.
	 
	 @return 
		A <see cref="int"/> value containing the index at which
		<paramref name="pattern" /> was found in the current
		instance, or -1 if the pattern was not found. The
		difference between the position and <paramref
		name="offset" /> will be divisible by <paramref
		name="byteAlign" />.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="offset" /> is less than zero.
	 
	*/
	public final int RFind(ByteVector pattern, int offset, int byteAlign)
	{
		if (pattern == null)
		{
			throw new NullPointerException("pattern");
		}

		if (offset < 0)
		{
			throw new IndexOutOfBoundsException("offset");
		}

		if (pattern.size() == 0 || pattern.size() > getCount() - offset)
		{
			return -1;
		}

		// Let's go ahead and special case a pattern of size one
		// since that's common and easy to make fast.

		if (pattern.size() == 1)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte p = pattern [0];
			byte p = pattern.get(0);
			for (int i = getCount() - offset - 1; i >= 0; i -= byteAlign)
			{
				if (this.data.get(i).equals(p))
				{
					return i;
				}
			}
			return -1;
		}

		int [] first_occurrence = new int [256];

		for (int i = 0; i < 256; ++i)
		{
			first_occurrence [i] = pattern.size();
		}

		for (int i = pattern.size() - 1; i > 0; --i)
		{
			first_occurrence [pattern.get(i)] = i;
		}

		for (int i = getCount() - offset - pattern.size(); i >= 0; i -= first_occurrence [this.data.get(i)])
		{
			if ((offset - i) % byteAlign == 0 && ContainsAt(pattern, i))
			{
				return i;
			}
		}

		return -1;
	}

	/** 
		Finds the last occurance of a pattern in the current
		instance, starting before a specified position.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to search for in the current instance.
	 
	 @param offset
		A <see cref="int"/> value specifying the index in the
		current instance at which to start searching.
	 
	 @return 
		A <see cref="int"/> value containing the index at which
		<paramref name="pattern" /> was found in the current
		instance, or -1 if the pattern was not found.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="offset" /> is less than zero.
	 
	*/
	public final int RFind(ByteVector pattern, int offset)
	{
		return RFind(pattern, offset, 1);
	}

	/** 
		Finds the last occurance of a pattern in the current
		instance.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to search for in the current instance.
	 
	 @return 
		A <see cref="int"/> value containing the index at which
		<paramref name="pattern" /> was found in the current
		instance, or -1 if the pattern was not found.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final int RFind(ByteVector pattern)
	{
		return RFind(pattern, 0, 1);
	}

	/** 
		Checks whether or not a pattern appears at a specified
		position in the current instance.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to check for in the current instance.
	 
	 @param offset
		A <see cref="int"/> value specifying the offset in the
		current instance at which to check for the pattern.
	 
	 @param patternOffset
		A <see cref="int"/> value specifying the position in
		<paramref name="pattern" /> at which to start checking.
	 
	 @param patternLength
		A <see cref="int"/> value specifying the number of bytes
		in <paramref name="pattern" /> to compare.
	 
	 @return 
		<see langword="true"/> if the pattern was found at the
		specified position. Otherwise, <see langword="false"/>.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final boolean ContainsAt(ByteVector pattern, int offset, int patternOffset, int patternLength)
	{
		if (pattern == null)
		{
			throw new NullPointerException("pattern");
		}

		if (pattern.size() < patternLength)
		{
			patternLength = pattern.size();
		}

		// do some sanity checking -- all of these things are 
		// needed for the search to be valid
		if (patternLength > this.data.size() || offset >= this.data.size() || patternOffset >= pattern.size() || patternLength <= 0 || offset < 0)
		{
			return false;
		}

		// loop through looking for a mismatch
		for (int i = 0; i < patternLength - patternOffset; i++)
		{
			if (!this.data.get(i + offset).equals(pattern.get(i + patternOffset)))
			{
				return false;
			}
		}

		return true;
	}

	/** 
		Checks whether or not a pattern appears at a specified
		position in the current instance.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to check for in the current instance.
	 
	 @param offset
		A <see cref="int"/> value specifying the offset in the
		current instance at which to check for the pattern.
	 
	 @param patternOffset
		A <see cref="int"/> value specifying the position in
		<paramref name="pattern" /> at which to start checking.
	 
	 @return 
		<see langword="true"/> if the pattern was found at the
		specified position. Otherwise, <see langword="false"/>.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final boolean ContainsAt(ByteVector pattern, int offset, int patternOffset)
	{
		return ContainsAt(pattern, offset, patternOffset, Integer.MAX_VALUE);
	}

	/** 
		Checks whether or not a pattern appears at a specified
		position in the current instance.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to check for in the current instance.
	 
	 @param offset
		A <see cref="int"/> value specifying the offset in the
		current instance at which to check for the pattern.
	 
	 @return 
		<see langword="true"/> if the pattern was found at the
		specified position. Otherwise, <see langword="false"/>.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final boolean ContainsAt(ByteVector pattern, int offset)
	{
		return ContainsAt(pattern, offset, 0);
	}

	/** 
		Checks whether or not a pattern appears at the beginning
		of the current instance.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to check for in the current instance.
	 
	 @return 
		<see langword="true"/> if the pattern was found at the
		beginning of the current instance. Otherwise, <see
		langword="false"/>.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final boolean StartsWith(ByteVector pattern)
	{
		return ContainsAt(pattern, 0);
	}

	/** 
		Checks whether or not a pattern appears at the end of the
		current instance.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to check for in the current instance.
	 
	 @return 
		<see langword="true"/> if the pattern was found at the
		end of the current instance. Otherwise, <see
		langword="false"/>.
	 
	 @exception ArgumentNullException
		<paramref name="pattern" /> is <see langword="null" />.
	 
	*/
	public final boolean EndsWith(ByteVector pattern)
	{
		if (pattern == null)
		{
			throw new NullPointerException("pattern");
		}

		return ContainsAt(pattern, this.data.size() - pattern.size());
	}

	/** 
		Checks whether or not the current instance ends with part
		of a pattern.
	 
	 @param pattern
		A <see cref="ByteVector"/> object containing the pattern
		to search for in the current instance.
	 
	 @return 
		A <see cref="int" /> value containing the index at which
		a partial match was located, or -1 if no match was found.
	 
	 
		<p>This function is useful for checking for patterns
		across multiple buffers.</p>
	 
	*/
	public final int EndsWithPartialMatch(ByteVector pattern)
	{
		if (pattern == null)
		{
			throw new NullPointerException("pattern");
		}

		if (pattern.size() > this.data.size())
		{
			return -1;
		}

		int start_index = this.data.size() - pattern.size();

		// try to match the last n-1 bytes from the vector
		// (where n is the pattern size) -- continue trying to
		// match n-2, n-3...1 bytes

		for (int i = 1; i < pattern.size(); i++)
		{
			if (ContainsAt(pattern, start_index + i, 0, pattern.size() - i))
			{
				return start_index + i;
			}
		}

		return -1;
	}

	/** 
		Adds the contents of another <see cref="ByteVector" />
		object to the end of the current instance.
	 
	 @param data
		A <see cref="ByteVector"/> object containing data to add
		to the end of the current instance.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
	public final void Add(ByteVector data)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		if (data != null)
		{
			this.data.addAll(data);
		}
	}

	/** 
		Adds the contents of an array to the end of the current
		instance.
	 
	 @param data
		A <see cref="byte[]"/> containing data to add to the end
		of the current instance.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void Add(byte [] data)
	public final void Add(byte[] data)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		if (data != null)
		{
			tangible.ByteLists.addPrimitiveArrayToList(data, this.data);
		}
	}

	/** 
		Inserts the contents of another <see cref="ByteVector" />
		object into the current instance.
	 
	 @param index
		A <see cref="int"/> value specifying the index at which
		to insert the data.
	 
	 @param data
		A <see cref="ByteVector"/> object containing data to
		insert into the current instance.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
	public final void add(int index, ByteVector data)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		if (data != null)
		{
			this.data.addAll(index, data);
		}
	}

	/** 
		Inserts the contents of an array to insert into the
		current instance.
	 
	 @param index
		A <see cref="int"/> value specifying the index at which
		to insert the data.
	 
	 @param data
		A <see cref="byte[]"/> containing data to insert into the
		current instance.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void Insert(int index, byte [] data)
	public final void add(int index, byte[] data)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		if (data != null)
		{
			tangible.ByteLists.addPrimitiveArrayToList(index, data, this.data);
		}
	}

	/** 
		Resizes the current instance.
	 
	 @param size
		A <see cref="int"/> value specifying the new size of the
		current instance.
	 
	 @param padding
		A <see cref="byte"/> object containing the padding byte
		to use if the current instance is growing.
	 
	 @return 
		The current instance.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ByteVector Resize(int size, byte padding)
	public final ByteVector Resize(int size, byte padding)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		if (this.data.size() > size)
		{
			this.data.subList(size, this.data.size() - size).clear();
		}

		while (this.data.size() < size)
		{
			this.data.add(padding);
		}

		return this;
	}

	/** 
		Resizes the current instance.
	 
	 @param size
		A <see cref="int"/> value specifying the new size of the
		current instance.
	 
	 @return 
		The current instance.
	 
	 
		If the current instance grows, the added bytes are filled
		with '0'. Use <see cref="Resize(int,byte)" /> to specify
		the padding byte.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	 {@link Resize(int,byte) }
	*/
	public final ByteVector Resize(int size)
	{
		return Resize(size, (byte)0);
	}

	/** 
		Removes a range of data from the current instance.
	 
	 @param index
		A <see cref="int" /> value specifying the index at which
		to start removing data.
	 
	 @param count
		A <see cref="int"/> value specifying the number of bytes
		to remove.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
	public final void RemoveRange(int index, int count)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		this.data.subList(index, count).clear();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Conversions

	/** 
		Converts an first four bytes of the current instance to
		a <see cref="int" /> value.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte
		appears first (big endian format), or <see
		langword="false" /> if the least significant byte appears
		first (little endian format).
	 
	 @return 
		A <see cref="int"/> value containing the value read from
		the current instance.
	 
	*/
	public final int ToInt(boolean mostSignificantByteFirst)
	{
		int ret = 0;
		int last = getCount() > 4 ? 3 : getCount() - 1;

		for (int i = 0; i <= last; i++)
		{
			int offset = mostSignificantByteFirst ? last - i : i;
			ret |= (int) this.get(i) << (offset * 8);
		}

		return ret;
	}

	/** 
		Converts an first four bytes of the current instance to
		a <see cref="int" /> value using big-endian format.
	 
	 @return 
		A <see cref="int"/> value containing the value read from
		the current instance.
	 
	*/
	public final int ToInt()
	{
		return ToInt(true);
	}

	/** 
		Converts an first four bytes of the current instance to
		a <see cref="uint" /> value.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte
		appears first (big endian format), or <see
		langword="false" /> if the least significant byte appears
		first (little endian format).
	 
	 @return 
		A <see cref="uint"/> value containing the value read from
		the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint ToUInt(bool mostSignificantByteFirst)
	public final int ToUInt(boolean mostSignificantByteFirst)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint sum = 0;
		int sum = 0;
		int last = getCount() > 4 ? 3 : getCount() - 1;

		for (int i = 0; i <= last; i++)
		{
			int offset = mostSignificantByteFirst ? last - i : i;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: sum |= (uint) this[i] << (offset * 8);
			sum |= (int) this.get(i) << (offset * 8);
		}

		return sum;
	}

	/** 
		Converts an first four bytes of the current instance to
		a <see cref="uint" /> value using big-endian format.
	 
	 @return 
		A <see cref="uint"/> value containing the value read from
		the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint ToUInt()
	public final int ToUInt()
	{
		return ToUInt(true);
	}

	/** 
		Converts an first two bytes of the current instance to a
		<see cref="ushort" /> value.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte
		appears first (big endian format), or <see
		langword="false" /> if the least significant byte appears
		first (little endian format).
	 
	 @return 
		A <see cref="ushort"/> value containing the value read
		from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort ToUShort(bool mostSignificantByteFirst)
	public final short ToUShort(boolean mostSignificantByteFirst)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort sum = 0;
		short sum = 0;
		int last = getCount() > 2 ? 1 : getCount() - 1;
		for (int i = 0; i <= last; i++)
		{
			int offset = mostSignificantByteFirst ? last - i : i;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: sum |= (ushort)(this[i] << (offset * 8));
			sum |= (short)(this.get(i) << (offset * 8));
		}

		return sum;
	}

	/** 
		Converts an first two bytes of the current instance to
		a <see cref="ushort" /> value using big-endian format.
	 
	 @return 
		A <see cref="ushort"/> value containing the value read
		from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort ToUShort()
	public final short ToUShort()
	{
		return ToUShort(true);
	}

	/** 
		Converts an first eight bytes of the current instance to
		a <see cref="ulong" /> value.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte
		appears first (big endian format), or <see
		langword="false" /> if the least significant byte appears
		first (little endian format).
	 
	 @return 
		A <see cref="ulong"/> value containing the value read
		from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong ToULong(bool mostSignificantByteFirst)
	public final long ToULong(boolean mostSignificantByteFirst)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong sum = 0;
		long sum = 0;
		int last = getCount() > 8 ? 7 : getCount() - 1;
		for (int i = 0; i <= last; i++)
		{
			int offset = mostSignificantByteFirst ? last - i : i;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: sum |= (ulong) this[i] << (offset * 8);
			sum |= (long) this.get(i) << (offset * 8);
		}
		return sum;
	}

	/** 
		Converts an first eight bytes of the current instance to
		a <see cref="ulong" /> value using big-endian format.
	 
	 @return 
		A <see cref="ulong"/> value containing the value read
		from the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong ToULong()
	public final long ToULong()
	{
		return ToULong(true);
	}

	/** 
		Converts an first four bytes of the current instance to
		a <see cref="float" /> value.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte
		appears first (big endian format), or <see
		langword="false" /> if the least significant byte appears
		first (little endian format).
	 
	 @return 
		A <see cref="float"/> value containing the value read
		from the current instance.
	 
	*/
	public final float ToFloat(boolean mostSignificantByteFirst)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] bytes = (byte []) Data.Clone();
		byte [] bytes = (byte []) getData().clone();

		if (mostSignificantByteFirst)
		{
			Array.Reverse(bytes);
		}

		return BitConverter.ToSingle(bytes, 0);
	}

	/** 
		Converts an first four bytes of the current instance to
		a <see cref="float" /> value using big-endian format.
	 
	 @return 
		A <see cref="float"/> value containing the value read
		from the current instance.
	 
	*/
	public final float ToFloat()
	{
		return ToFloat(true);
	}

	/** 
		Converts an first eight bytes of the current instance to
		a <see cref="double" /> value.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte
		appears first (big endian format), or <see
		langword="false" /> if the least significant byte appears
		first (little endian format).
	 
	 @return 
		A <see cref="double"/> value containing the value read
		from the current instance.
	 
	*/
	public final double ToDouble(boolean mostSignificantByteFirst)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] bytes = (byte []) Data.Clone();
		byte [] bytes = (byte []) getData().clone();

		if (mostSignificantByteFirst)
		{
			Array.Reverse(bytes);
		}

		return BitConverter.ToDouble(bytes, 0);
	}

	/** 
		Converts an first eight bytes of the current instance to
		a <see cref="double" /> value using big-endian format.
	 
	 @return 
		A <see cref="double"/> value containing the value read
		from the current instance.
	 
	*/
	public final double ToDouble()
	{
		return ToDouble(true);
	}

	/** 
		Converts a portion of the current instance to a <see
		cref="string"/> object using a specified encoding.
	 
	 @param type
		A <see cref="StringType"/> value indicating the encoding
		to use when converting to a <see cref="string"/> object.
	 
	 @param offset
		A <see cref="int"/> value specify the index in the
		current instance at which to start converting.
	 
	 @param count
		A <see cref="int"/> value specify the number of bytes to
		convert.
	 
	 @return 
		A <see cref="string"/> object containing the converted
		text.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="offset" /> is less than zero or greater
		than the total number of bytes, or <paramref name="count"
		/> is less than zero or greater than the number of bytes
		after <paramref name="offset" />.
	 
	*/
	public final String toString(StringType type, int offset, int count)
	{
		if (offset < 0 || offset > getCount())
		{
			throw new IndexOutOfBoundsException("offset");
		}

		if (count < 0 || count + offset > getCount())
		{
			throw new IndexOutOfBoundsException("count");
		}

		ByteVector bom = type == StringType.UTF16 && this.data.size() - offset > 1 ? Mid(offset, 2) : null;

		String s = StringTypeToEncoding(type, bom).GetString(getData(), offset, count);

		// UTF16 BOM
		if (s.length() != 0 && (s.charAt(0) == 0xfffe || s.charAt(0) == 0xfeff))
		{
			return s.substring(1);
		}

		return s;
	}

	/** 
		Converts all data after a specified index in the current
		instance to a <see cref="string"/> object using a
		specified encoding.
	 
	 @param type
		A <see cref="StringType"/> value indicating the encoding
		to use when converting to a <see cref="string"/> object.
	 
	 @param offset
		A <see cref="int"/> value specify the index in the
		current instance at which to start converting.
	 
	 @return 
		A <see cref="string"/> object containing the converted
		text.
	 
	 @exception ArgumentOutOfRangeException
		<paramref name="offset" /> is less than zero or greater
		than the total number of bytes.
	 
	*/
	@Deprecated
	public final String toString(StringType type, int offset)
	{
		return toString(type, offset, getCount() - offset);
	}

	/** 
		Converts the current instance into a <see cref="string"/>
		object using a specified encoding.
	 
	 @return 
		A <see cref="string"/> object containing the converted
		text.
	 
	*/
	public final String toString(StringType type)
	{
		return toString(type, 0, getCount());
	}

	/** 
		Converts the current instance into a <see cref="string"/>
		object using a UTF-8 encoding.
	 
	 @return 
		A <see cref="string"/> object containing the converted
		text.
	 
	*/
	@Override
	public String toString()
	{
		return toString(StringType.UTF8);
	}

	/** 
		Converts the current instance into a <see cref="string[]"
		/> starting at a specified offset and using a specified
		encoding, assuming the values are nil separated.
	 
	 @param type
		A <see cref="StringType"/> value indicating the encoding
		to use when converting to a <see cref="string"/> object.
	 
	 @param offset
		A <see cref="int"/> value specify the index in the
		current instance at which to start converting.
	 
	 @return 
		A <see cref="string[]" /> containing the converted text.
	 
	*/
	public final String[] ToStrings(StringType type, int offset)
	{
		return ToStrings(type, offset, Integer.MAX_VALUE);
	}

	/** 
		Converts the current instance into a <see cref="string[]"
		/> starting at a specified offset and using a specified
		encoding, assuming the values are nil separated and
		limiting it to a specified number of items.
	 
	 @param type
		A <see cref="StringType"/> value indicating the encoding
		to use when converting to a <see cref="string"/> object.
	 
	 @param offset
		A <see cref="int"/> value specify the index in the
		current instance at which to start converting.
	 
	 @param count
		A <see cref="int"/> value specifying a limit to the
		number of strings to create. Once the limit has been
		reached, the last string will be filled by the remainder
		of the data.
	 
	 @return 
		A <see cref="string[]" /> containing the converted text.
	 
	*/
	public final String[] ToStrings(StringType type, int offset, int count)
	{
		int chunk = 0;
		int position = offset;

		ArrayList<String> list = new ArrayList<String> ();
		ByteVector separator = TextDelimiter(type);
		int align = separator.size();

		while (chunk < count && position < getCount())
		{
			int start = position;

			if (chunk + 1 == count)
			{
				position = offset + count;
			}
			else
			{
				position = Find(separator, start, align);

				if (position < 0)
				{
					position = getCount();
				}
			}

			int length = position - start;

			if (length == 0)
			{
				list.add("");
			}
			else
			{
				String s = toString(type, start, length);
				if (s.length() != 0 && (s.charAt(0) == 0xfffe || s.charAt(0) == 0xfeff))
				{ // UTF16 BOM
					s = s.substring(1);
				}

				list.add(s);
			}

			position += align;
		}

		return list.toArray(new String[0]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Operators

	/** 
		Determines whether two specified <see cref="ByteVector"
		/> objects are equal.
	 
	 @param first
		A <see cref="ByteVector"/> to compare.
	 
	 @param second
		A <see cref="ByteVector"/> to compare.
	 
	 @return 
		<p><see langword="true" /> if <paramref name="first"
		/> and <paramref name="second" /> contain the same
		data; otherwise, <see langword="false" />.</p>
	 
	*/
	public static boolean OpEquality(ByteVector first, ByteVector second)
	{
		boolean fnull = (Object) first == null;
		boolean snull = (Object) second == null;
		if (fnull && snull)
		{
			return true;
		}
		else if (fnull || snull)
		{
			return false;
		}

		return first.equals(second);
	}

	/** 
		Determines whether two specified <see cref="ByteVector"
		/> objects differ.
	 
	 @param first
		A <see cref="ByteVector"/> to compare.
	 
	 @param second
		A <see cref="ByteVector"/> to compare.
	 
	 @return 
		<p><see langword="true" /> if <paramref name="first"
		/> and <paramref name="second" /> contain different
		data; otherwise, <see langword="false" />.</p>
	 
	*/
	public static boolean OpInequality(ByteVector first, ByteVector second)
	{
		return !Rasad.Core.Media.MediaMetadataManagement.ByteVector.OpEquality(first, second);
	}

	/** 
		Determines whether or not one <see cref="ByteVector" />
		is less than another.
	 
	 @param first
		A <see cref="ByteVector"/> to compare.
	 
	 @param second
		A <see cref="ByteVector"/> to compare.
	 
	 @return 
		<p><see langword="true" /> if <paramref name="first"
		/> is less than <paramref name="second" />; otherwise,
		<see langword="false" />.</p>
	 
	 @exception ArgumentNullException
		<paramref name="first" /> or <paramref name="second" />
		is <see langword="null" />.
	 
	*/
	public static boolean OpLessThan(ByteVector first, ByteVector second)
	{
		if (first == null)
		{
			throw new NullPointerException("first");
		}

		if (second == null)
		{
			throw new NullPointerException("second");
		}

		return first.CompareTo(second) < 0;
	}

	/** 
		Determines whether or not one <see cref="ByteVector" />
		is less than or equal to another.
	 
	 @param first
		A <see cref="ByteVector"/> to compare.
	 
	 @param second
		A <see cref="ByteVector"/> to compare.
	 
	 @return 
		<p><see langword="true" /> if <paramref name="first"
		/> is less than or equal to <paramref name="second" />;
		otherwise, <see langword="false" />.</p>
	 
	 @exception ArgumentNullException
		<paramref name="first" /> or <paramref name="second" />
		is <see langword="null" />.
	 
	*/
	public static boolean OpLessThanOrEqual(ByteVector first, ByteVector second)
	{
		if (first == null)
		{
			throw new NullPointerException("first");
		}

		if (second == null)
		{
			throw new NullPointerException("second");
		}

		return first.CompareTo(second) <= 0;
	}

	/** 
		Determines whether or not one <see cref="ByteVector" />
		is greater than another.
	 
	 @param first
		A <see cref="ByteVector"/> to compare.
	 
	 @param second
		A <see cref="ByteVector"/> to compare.
	 
	 @return 
		<p><see langword="true" /> if <paramref name="first"
		/> is greater than <paramref name="second" />; otherwise,
		<see langword="false" />.</p>
	 
	 @exception ArgumentNullException
		<paramref name="first" /> or <paramref name="second" />
		is <see langword="null" />.
	 
	*/
	public static boolean OpGreaterThan(ByteVector first, ByteVector second)
	{
		if (first == null)
		{
			throw new NullPointerException("first");
		}

		if (second == null)
		{
			throw new NullPointerException("second");
		}

		return first.CompareTo(second) > 0;
	}

	/** 
		Determines whether or not one <see cref="ByteVector" />
		is greater than or equal to another.
	 
	 @param first
		A <see cref="ByteVector"/> to compare.
	 
	 @param second
		A <see cref="ByteVector"/> to compare.
	 
	 @return 
		<p><see langword="true" /> if <paramref name="first"
		/> is greater than or equal to <paramref name="second"
		/>; otherwise, <see langword="false" />.</p>
	 
	 @exception ArgumentNullException
		<paramref name="first" /> or <paramref name="second" />
		is <see langword="null" />.
	 
	*/
	public static boolean OpGreaterThanOrEqual(ByteVector first, ByteVector second)
	{
		if (first == null)
		{
			throw new NullPointerException("first");
		}

		if (second == null)
		{
			throw new NullPointerException("second");
		}

		return first.CompareTo(second) >= 0;
	}

	/** 
		Creates a new <see cref="ByteVector"/> object by adding
		two objects together.
	 
	 @param first
		A <see cref="ByteVector"/> to combine.
	 
	 @param second
		A <see cref="ByteVector"/> to combine.
	 
	 @return 
		A new instance of <see cref="ByteVector" /> with the
		contents of <paramref name="first" /> followed by the
		contents of <paramref name="second" />.
	 
	*/
	public static ByteVector OpAddition(ByteVector first, ByteVector second)
	{
		ByteVector sum = new ByteVector(first);
		sum.add(second);
		return sum;
	}

	/** 
		Converts a <see cref="byte" /> to a new <see
		cref="ByteVector" /> object.
	 
	 @param value
		A <see cref="byte" /> to convert.
	 
	 @return 
		A new instance of <see cref="ByteVector" /> containing
		<paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static implicit operator ByteVector(byte value)
	public static implicit operator ByteVector(byte value)
	{
		return new ByteVector(value);
	}

	/** 
		Converts a <see cref="byte[]" /> to a new <see
		cref="ByteVector" /> object.
	 
	 @param value
		A <see cref="byte[]" /> to convert.
	 
	 @return 
		A new instance of <see cref="ByteVector" /> containing
		the contents of <paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static implicit operator ByteVector(byte [] value)
	public static implicit operator ByteVector(byte[] value)
	{
		return new ByteVector(value);
	}

	/** 
		Converts a <see cref="string" /> to a new <see
		cref="ByteVector" /> object.
	 
	 @param value
		A <see cref="string" /> to convert.
	 
	 @return 
		A new instance of <see cref="ByteVector" /> containing
		the UTF-8 encoded contents of <paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator ByteVector(String value)
	{
		return ByteVector.FromString(value, StringType.UTF8);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Static Conversions

	/** 
		Converts a value into a data representation.
	 
	 @param value
		A <see cref="int"/> value to convert into bytes.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte is
		to appear first (big endian format), or <see
		langword="false" /> if the least significant byte is to
		appear first (little endian format).
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="value" />.
	 
	*/
	public static ByteVector FromInt(int value, boolean mostSignificantByteFirst)
	{
		ByteVector vector = new ByteVector();
		for (int i = 0; i < 4; i++)
		{
			int offset = mostSignificantByteFirst ? 3 - i : i;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: vector.Add((byte)(value >> (offset * 8) & 0xFF));
			vector.add((byte)(value >> (offset * 8) & 0xFF));
		}

		return vector;
	}

	/** 
		Converts an value into a big-endian data representation.
	 
	 @param value
		A <see cref="int"/> value to convert into bytes.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="value" />.
	 
	*/
	public static ByteVector FromInt(int value)
	{
		return FromInt(value, true);
	}

	/** 
		Converts an unsigned value into a data representation.
	 
	 @param value
		A <see cref="uint"/> value to convert into bytes.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte is
		to appear first (big endian format), or <see
		langword="false" /> if the least significant byte is to
		appear first (little endian format).
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector FromUInt(uint value, bool mostSignificantByteFirst)
	public static ByteVector FromUInt(int value, boolean mostSignificantByteFirst)
	{
		ByteVector vector = new ByteVector();
		for (int i = 0; i < 4; i++)
		{
			int offset = mostSignificantByteFirst ? 3 - i : i;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: vector.Add((byte)(value >> (offset * 8) & 0xFF));
			vector.add((byte)(value >>> (offset * 8) & 0xFF));
		}

		return vector;
	}

	/** 
		Converts an unsigned value into a big-endian data
		representation.
	 
	 @param value
		A <see cref="uint"/> value to convert into bytes.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector FromUInt(uint value)
	public static ByteVector FromUInt(int value)
	{
		return FromUInt(value, true);
	}

	/** 
		Converts an unsigned value into a data representation.
	 
	 @param value
		A <see cref="ushort"/> value to convert into bytes.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte is
		to appear first (big endian format), or <see
		langword="false" /> if the least significant byte is to
		appear first (little endian format).
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector FromUShort(ushort value, bool mostSignificantByteFirst)
	public static ByteVector FromUShort(short value, boolean mostSignificantByteFirst)
	{
		ByteVector vector = new ByteVector();
		for (int i = 0; i < 2; i++)
		{
			int offset = mostSignificantByteFirst ? 1 - i : i;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: vector.Add((byte)(value >> (offset * 8) & 0xFF));
			vector.add((byte)(value >>> (offset * 8) & 0xFF));
		}

		return vector;
	}

	/** 
		Converts an unsigned value into a big-endian data
		representation.
	 
	 @param value
		A <see cref="ushort"/> value to convert into bytes.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector FromUShort(ushort value)
	public static ByteVector FromUShort(short value)
	{
		return FromUShort(value, true);
	}

	/** 
		Converts an unsigned value into a data representation.
	 
	 @param value
		A <see cref="ulong"/> value to convert into bytes.
	 
	 @param mostSignificantByteFirst
		<see langword="true" /> if the most significant byte is
		to appear first (big endian format), or <see
		langword="false" /> if the least significant byte is to
		appear first (little endian format).
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector FromULong(ulong value, bool mostSignificantByteFirst)
	public static ByteVector FromULong(long value, boolean mostSignificantByteFirst)
	{
		ByteVector vector = new ByteVector();
		for (int i = 0; i < 8; i++)
		{
			int offset = mostSignificantByteFirst ? 7 - i : i;
//C# TO JAVA CONVERTER WARNING: The right shift operator was replaced by Java's logical right shift operator since the left operand was originally of an unsigned type, but you should confirm this replacement:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: vector.Add((byte)(value >> (offset * 8) & 0xFF));
			vector.add((byte)(value >>> (offset * 8) & 0xFF));
		}
		return vector;
	}

	/** 
		Converts an unsigned value into a big-endian data
		representation.
	 
	 @param value
		A <see cref="ulong"/> value to convert into bytes.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="value" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static ByteVector FromULong(ulong value)
	public static ByteVector FromULong(long value)
	{
		return FromULong(value, true);
	}

	/** 
		Converts an string into a encoded data representation.
	 
	 @param text
		A <see cref="string"/> object containing the text to
		convert.
	 
	 @param type
		A <see cref="StringType"/> value specifying the encoding
		to use when converting the text.
	 
	 @param length
		A <see cref="int"/> value specifying the number of
		characters in <paramref name="text" /> to encoded.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="text" />.
	 
	*/
	public static ByteVector FromString(String text, StringType type, int length)
	{
		ByteVector data = new ByteVector();

		if (type == StringType.UTF16)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: data.Add(new byte [] {0xff, 0xfe});
			data.add(new byte [] {(byte)0xff, (byte)0xfe});
		}

		if (text == null || text.length() == 0)
		{
			return data;
		}

		if (text.length() > length)
		{
			text = text.substring(0, length);
		}

		data.add(StringTypeToEncoding(type, data).GetBytes(text));

		return data;
	}

	/** 
		Converts an string into a encoded data representation.
	 
	 @param text
		A <see cref="string"/> object containing the text to
		convert.
	 
	 @param type
		A <see cref="StringType"/> value specifying the encoding
		to use when converting the text.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="text" />.
	 
	*/
	public static ByteVector FromString(String text, StringType type)
	{
		return FromString(text, type, Integer.MAX_VALUE);
	}

	/** 
		Converts an string into a encoded data representation.
	 
	 @param text
		A <see cref="string"/> object containing the text to
		convert.
	 
	 @param length
		A <see cref="int"/> value specifying the number of
		characters in <paramref name="text" /> to encoded.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="text" />.
	 
	*/
	public static ByteVector FromString(String text, int length)
	{
		return FromString(text, StringType.UTF8, length);
	}

	/** 
		Converts an string into a encoded data representation.
	 
	 @param text
		A <see cref="string"/> object containing the text to
		convert.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the encoded
		representation of <paramref name="text" />.
	 
	*/
	@Deprecated
	public static ByteVector FromString(String text)
	{
		return FromString(text, StringType.UTF8);
	}

	/** 
		Creates a new instance of <see cref="ByteVector" /> by
		reading in the contents of a specified file.
	 
	 @param path
		A <see cref="string"/> object containing the path of the
		file to read.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the contents
		of the specified file.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public static ByteVector FromPath(String path)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] tmp_out;
		byte [] tmp_out;
		tangible.OutObject<byte[]> tempOut_tmp_out = new tangible.OutObject<byte[]>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return FromPath(path, out tmp_out, false);
		ByteVector tempVar = FromPath(path, tempOut_tmp_out, false);
	tmp_out = tempOut_tmp_out.argValue;
	return tempVar;
	}

	/** 
		Creates a new instance of <see cref="ByteVector" /> by
		reading in the contents of a specified file.
	 
	 @param path
		A <see cref="string"/> object containing the path of the
		file to read.
	 
	 @param firstChunk
		A <see cref="byte[]"/> reference to be filled with the
		first data chunk from the read file.
	 
	 @param copyFirstChunk
		A <see cref="bool"/> value specifying whether or not to
		copy the first chunk of the file into <paramref
		name="firstChunk" />.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the contents
		of the specified file.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internal static ByteVector FromPath(string path, out byte [] firstChunk, bool copyFirstChunk)
	public static ByteVector FromPath(String path, tangible.OutObject<byte[]> firstChunk, boolean copyFirstChunk)
	{
		if (path == null)
		{
			throw new NullPointerException("path");
		}

		return FromFile(new File.LocalFileAbstraction(path), firstChunk, copyFirstChunk);
	}

	/** 
		Creates a new instance of <see cref="ByteVector" /> by
		reading in the contents of a specified file abstraction.
	 
	 @param abstraction
		A <see cref="File.IFileAbstraction"/> object containing
		abstraction of the file to read.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the contents
		of the specified file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public static ByteVector FromFile(File.IFileAbstraction abstraction)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] tmp_out;
		byte [] tmp_out;
		tangible.OutObject<byte[]> tempOut_tmp_out = new tangible.OutObject<byte[]>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return FromFile(abstraction, out tmp_out, false);
		ByteVector tempVar = FromFile(abstraction, tempOut_tmp_out, false);
	tmp_out = tempOut_tmp_out.argValue;
	return tempVar;
	}

	/** 
		Creates a new instance of <see cref="ByteVector" /> by
		reading in the contents of a specified file abstraction.
	 
	 @param abstraction
		A <see cref="File.IFileAbstraction"/> object containing
		abstraction of the file to read.
	 
	 @param firstChunk
		A <see cref="byte[]"/> reference to be filled with the
		first data chunk from the read file.
	 
	 @param copyFirstChunk
		A <see cref="bool"/> value specifying whether or not to
		copy the first chunk of the file into <paramref
		name="firstChunk" />.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the contents
		of the specified file.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internal static ByteVector FromFile(File.IFileAbstraction abstraction, out byte [] firstChunk, bool copyFirstChunk)
	public static ByteVector FromFile(File.IFileAbstraction abstraction, tangible.OutObject<byte[]> firstChunk, boolean copyFirstChunk)
	{
		if (abstraction == null)
		{
			throw new NullPointerException("abstraction");
		}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
		System.IO.Stream stream = abstraction.getReadStream();
		ByteVector output = FromStream(stream, firstChunk, copyFirstChunk);
		abstraction.CloseStream(stream);
		return output;
	}

	/** 
		Creates a new instance of <see cref="ByteVector" /> by
		reading in the contents of a specified stream.
	 
	 @param stream
		A <see cref="System.IO.Stream"/> object containing
		the stream to read.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the contents
		of the specified stream.
	 
	 @exception ArgumentNullException
		<paramref name="stream" /> is <see langword="null" />.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public static ByteVector FromStream(System.IO.Stream stream)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] tmp_out;
		byte [] tmp_out;
		tangible.OutObject<byte[]> tempOut_tmp_out = new tangible.OutObject<byte[]>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return FromStream(stream, out tmp_out, false);
		ByteVector tempVar = FromStream(stream, tempOut_tmp_out, false);
	tmp_out = tempOut_tmp_out.argValue;
	return tempVar;
	}

	/** 
		Creates a new instance of <see cref="ByteVector" /> by
		reading in the contents of a specified stream.
	 
	 @param stream
		A <see cref="System.IO.Stream"/> object containing
		the stream to read.
	 
	 @param firstChunk
		A <see cref="byte[]"/> reference to be filled with the
		first data chunk from the read stream.
	 
	 @param copyFirstChunk
		A <see cref="bool"/> value specifying whether or not to
		copy the first chunk of the stream into <paramref
		name="firstChunk" />.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the contents
		of the specified stream.
	 
	 @exception ArgumentNullException
		<paramref name="stream" /> is <see langword="null" />.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internal static ByteVector FromStream(System.IO.Stream stream, out byte [] firstChunk, bool copyFirstChunk)
	public static ByteVector FromStream(InputStream stream, tangible.OutObject<byte[]> firstChunk, boolean copyFirstChunk)
	{
		ByteVector vector = new ByteVector();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte [] bytes = new byte[4096];
		byte [] bytes = new byte[4096];
		int read_size = bytes.length;
		int bytes_read = 0;
		boolean set_first_chunk = false;

		firstChunk.argValue = null;

		while (true)
		{
			Array.Clear(bytes, 0, bytes.length);
			int n = stream.read(bytes, 0, read_size);
			vector.add(bytes);
			bytes_read += n;

			if (!set_first_chunk)
			{
				if (copyFirstChunk)
				{
					if (firstChunk.argValue == null || firstChunk.argValue.length != read_size)
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: firstChunk = new byte [read_size];
						firstChunk.argValue = new byte [read_size];
					}

					System.arraycopy(bytes, 0, firstChunk.argValue, 0, n);
				}
				set_first_chunk = true;
			}

			if ((bytes_read == stream.Length && stream.Length > 0) || (n < read_size && stream.Length <= 0))
			{
				break;
			}
		}

		if (stream.Length > 0 && vector.size() != stream.Length)
		{
			vector.Resize((int)stream.Length);
		}

		return vector;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Utilities

	/** 
		Gets the text delimiter for nil separated string lists of
		a specified encoding.
	 
	 @param type
		A <see cref="StringType"/> value specifying the encoding
		to use.
	 
	 @return 
		A <see cref="ByteVector"/> object containing the text
		delimiter.
	 
	*/
	public static ByteVector TextDelimiter(StringType type)
	{
		return type == StringType.UTF16 || type == StringType.UTF16BE || type == StringType.UTF16LE ? td2 : td1;
	}

	/** 
		Gets the <see cref="Encoding" /> to use for a specified
		encoding.
	 
	 @param type
		A <see cref="StringType"/> value specifying encoding to
		use.
	 
	 @param bom
		A <see cref="ByteVector"/> object containing the first
		two bytes of the data to convert if <paramref
		name="type" /> equals <see cref="StringType.UTF16" />.
	 
	 @return 
		A <see cref="Encoding" /> object capable of encoding
		and decoding text with the specified type.
	 
	 
		<paramref name="bom" /> is used to determine whether the
		encoding is big or little endian. If it does not contain
		BOM data, the previously used endian format is used.
	 
	*/
	private static Encoding StringTypeToEncoding(StringType type, ByteVector bom)
	{
		switch (type)
		{
		case UTF16:
			// If we have a BOM, return the appropriate
			// encoding. Otherwise, assume we're reading
			// from a string that was already identified. In
			// that case, the encoding will be stored as
			// last_utf16_encoding.

			if (bom == null)
			{
				return last_utf16_encoding;
			}

			if (bom.get(0) == 0xFF && bom.get(1) == 0xFE)
			{
				return last_utf16_encoding = Encoding.Unicode;
			}

			if (bom.get(1) == 0xFF && bom.get(0) == 0xFE)
			{
				return last_utf16_encoding = Encoding.BigEndianUnicode;
			}

			return last_utf16_encoding;

		case UTF16BE:
			return Encoding.BigEndianUnicode;

		case UTF8:
			return Encoding.UTF8;

		case UTF16LE:
			return Encoding.Unicode;
		}

		if (use_broken_latin1)
		{
			return Encoding.Default;
		}

		try
		{
			return Encoding.GetEncoding("latin1");
		}
		catch (IllegalArgumentException e)
		{
			return Encoding.UTF8;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region System.Object

	/** 
		Determines whether another object is equal to the current
		instance.
	 
	 @param other
		A <see cref="object"/> to compare to the current
		instance.
	 
	 @return 
		<see langword="true" /> if <paramref name="other"/> is not
		<see langword="null" />, is of type <see
		cref="ByteVector" />, and is equal to the current
		instance; otherwise <see langword="false" />.
	 
	*/
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof ByteVector))
		{
			return false;
		}

		return Equals((ByteVector) other);
	}

	/** 
		Determines whether another <see cref="ByteVector"/>
		object is equal to the current instance.
	 
	 @param other
		A <see cref="ByteVector"/> object to compare to the
		current instance.
	 
	 @return 
		<see langword="true" /> if <paramref name="other"/> is not
		<see langword="null" /> and equal to the current instance;
		otherwise <see langword="false" />.
	 
	*/
	public final boolean equals(ByteVector other)
	{
		return CompareTo(other) == 0;
	}

	/** 
		Gets the hash value for the current instance.
	 
	 @return 
		A <see cref="int" /> value equal to the CRC checksum of
		the current instance.
	 
	*/
	@Override
	public int hashCode()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
			return (int) getChecksum();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IComparable<T>

	/** 
		Compares the current instance to another to determine if
		their order.
	 
	 @param other
		A <see cref="ByteVector" /> object to compare to the
		current instance.
	 
	 @return 
		A <see cref="int" /> which is less than zero if the
		current instance is less than <paramref name="other" />,
		zero if it is equal to <paramref name="other" />, and
		greater than zero if the current instance is greater than
		<paramref name="other" />.
	 
	*/
	public final int compareTo(ByteVector other)
	{
		if ((Object) other == null)
		{
			throw new NullPointerException("other");
		}

		int diff = getCount() - other.size();

		for (int i = 0; diff == 0 && i < getCount(); i++)
		{
			diff = this.get(i) - other.get(i);
		}

		return diff;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IEnumerable<T>

	/** 
		Gets an enumerator for enumerating through the the bytes
		in the current instance.
	 
	 @return 
		A <see cref="T:System.Collections.IEnumerator`1" /> for
		enumerating through the contents of the current instance.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public IEnumerator<byte> GetEnumerator()
	public final Iterator<Byte> iterator()
	{
		return this.data.iterator();
	}

	public final Iterator GetEnumerator()
	{
		return this.data.iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region ICollection<T>

	/** 
		Clears the current instance.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
	public final void clear()
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		this.data.clear();
	}

	/** 
		Adds a single byte to the end of the current instance.
	 
	 @param item
		A <see cref="byte" /> to add to the current instance.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void Add(byte item)
	public final void Add(byte item)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		this.data.add(item);
	}

	/** 
		Removes the first occurance of a <see cref="byte" /> from
		the current instance.
	 
	 @param item
		A <see cref="byte"/> to remove from the current instance.
	 
	 @return 
		<see langword="true" /> if the value was removed;
		otherwise the value did not appear in the current
		instance and <see langword="false" /> is returned.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
	public final boolean remove(Object objectValue)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte item = (byte)objectValue;
		byte item = (byte)objectValue;
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		return this.data.remove(item);
	}

	/** 
		Copies the current instance to a <see cref="byte[]"/>
		starting at a specified index.
	 
	 @param array
		A <see cref="byte[]" /> to copy to.
	 
	 @param arrayIndex
		A <see cref="int" /> value indicating the index in
		<paramref name="array" /> at which to start copying.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void CopyTo(byte [] array, int arrayIndex)
	public final void CopyTo(byte[] array, int arrayIndex)
	{
		this.data.CopyTo(array, arrayIndex);
	}

	/** 
		Gets whether or not the current instance contains a
		specified byte.
	 
	 @param item
		A <see cref="byte" /> value to look for in the current
		instance.
	 
	 @return 
		<see langword="true" /> if the value could be found;
		otherwise <see langword="false" />.
	 
	*/
	public final boolean contains(Object objectValue)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte item = (byte)objectValue;
		byte item = (byte)objectValue;
		return this.data.contains(item);
	}

	/** 
		Gets the number of elements in the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the number of bytes
		in the current instance.
	 </value>
	*/
	public final int size()
	{
		return this.data.size();
	}

	/** 
		Gets whether or not the current instance is synchronized.
	 
	 <value>
		Always <see langword="false" />.
	 </value>
	*/
	public final boolean getIsSynchronized()
	{
		return false;
	}

	/** 
		Gets the object that can be used to synchronize the
		current instance.
	 
	 <value>
		A <see cref="object" /> that can be used to synchronize
		the current instance.
	 </value>
	*/
	public final Object getSyncRoot()
	{
		return this;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region IList<T>

	/** 
		Removes the byte at the specified index.
	 
	 @param index
		A <see cref="int" /> value specifying the position at
		which to remove a byte.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
	public final void remove(int index)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		this.data.remove(index);
	}

	/** 
		Inserts a single byte into the current instance at a
	*/
	//     specified index.
	/** 
	 @param index
		A <see cref="int" /> value specifying the position at
		which to insert the value.
	 
	 @param item
		A <see cref="byte"/> value to insert into the current
		instance.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void Insert(int index, byte item)
	public final void add(int index, byte item)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		this.data.add(index, item);
	}

	/** 
		Gets the index of the first occurance of a value.
	 
	 @param item
		A <see cref="byte" /> to find in the current instance.
	 
	 @return 
		A <see cref="int" /> value containing the first index
		at which the value was found, or -1 if it was not found.
	 
	*/
	public final int indexOf(Object objectValue)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte item = (byte)objectValue;
		byte item = (byte)objectValue;
		return this.data.indexOf(item);
	}

	/** 
		Gets whether or not the current instance is read-only.
	 
	 <value>
		<see langword="true" /> if the current instance is
		read-only; otherwise <see langword="false" />.
	 </value>
	*/
	public boolean getIsReadOnly()
	{
		return false;
	}

	/** 
		Gets whether or not the current instance has a fixed
		size.
	 
	 <value>
		<see langword="true" /> if the current instance has a
		fixed size; otherwise <see langword="false" />.
	 </value>
	*/
	public boolean getIsFixedSize()
	{
		return false;
	}

	/** 
		Gets and sets the value as a specified index.
	 
	 @exception NotSupportedException
		The current instance is read-only.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getItem(int index)
	public final byte get(int index)
	{
		return this.data.get(index);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setItem(int index, byte value)
	public final void set(int index, byte value)
	{
		if (getIsReadOnly())
		{
			throw new UnsupportedOperationException("Cannot edit readonly objects.");
		}

		data.set(index, value);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}