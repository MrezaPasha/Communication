package Rasad.Core.Media.MediaMetadataManagement.Ape;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion



/** 
	This structure provides a representation of an APEv2 tag footer
	which can be read from and written to disk.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct Footer : IEquatable<Footer>
public final class Footer implements IEquatable<Footer>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Properties

	/** 
		Contains the APE tag version.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint version;
	private int version;

	/** 
		Contains the footer flags.
	*/
	private FooterFlags flags = FooterFlags.values()[0];

	/** 
		Contains the number of items in the tag.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint item_count;
	private int item_count;

	/** 
		Contains the tag size including the footer but excluding
		the header.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint tag_size;
	private int tag_size;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Fields

	/** 
		Specifies the size of an APEv2 footer.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint Size = 32;
	public static final int Size = 32;

	/** 
		Specifies the identifier used find an APEv2 footer in a
		file.
	 
	 <value>
		"<c>APETAGEX</c>"
	 </value>
	*/
	public static final ReadOnlyByteVector FileIdentifier = "APETAGEX";

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Footer" /> by reading it from raw footer data.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		data to build the new instance from.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	 @exception CorruptFileException
		<paramref name="data" /> is smaller than <see
		cref="Size" /> or does not begin with <see
		cref="FileIdentifier" />.
	 
	*/
	public Footer()
	{
	}

	public Footer(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		if (data.size() < Size)
		{
			throw new CorruptFileException("Provided data is smaller than object size.");
		}

		if (!data.StartsWith(FileIdentifier))
		{
			throw new CorruptFileException("Provided data does not start with File Identifier");
		}

		version = data.Mid(8, 4).ToUInt(false);
		tag_size = data.Mid(12, 4).ToUInt(false);
		item_count = data.Mid(16, 4).ToUInt(false);
		flags = FooterFlags.forValue(data.Mid(20, 4).ToUInt(false));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the version of APE tag described by the current
		instance.
	 
	 <value>
		A <see cref="uint" /> value containing the version of the
		APE tag described by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getVersion()
	public int getVersion()
	{
		return version == 0 ? 2000 : version;
	}

	/** 
		Gets and sets the flags that apply to the current
		instance.
	 
	 <value>
		A bitwise combined <see cref="FooterFlags" /> value
		containing the flags that apply to the current instance.
	 </value>
	*/
	public FooterFlags getFlags()
	{
		return flags;
	}
	public void setFlags(FooterFlags value)
	{
		flags = value;
	}

	/** 
		Gets and sets the number of items in the tag represented
		by the current instance.
	 
	 <value>
		A <see cref="uint" /> value containing the number of
		items in the tag represented by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getItemCount()
	public int getItemCount()
	{
		return item_count;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setItemCount(uint value)
	public void setItemCount(int value)
	{
		item_count = value;
	}

	/** 
		Gets the size of the tag represented by the current
		instance, including the footer but excluding the header
		if applicable.
	 
	 <value>
		A <see cref="uint" /> value containing the size of the
		tag represented by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getTagSize()
	public int getTagSize()
	{
		return tag_size;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setTagSize(uint value)
	public void setTagSize(int value)
	{
		tag_size = value;
	}

	/** 
		Gets the complete size of the tag represented by the
		current instance, including the header and footer.
	 
	 <value>
		A <see cref="uint" /> value containing the size of the
		tag represented by the current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint getCompleteTagSize()
	public int getCompleteTagSize()
	{
		return getTagSize() + ((getFlags().getValue() & FooterFlags.HeaderPresent.getValue()) != 0 ? Size : 0);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	/** 
		Renders the current instance as an APE tag footer.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	public ByteVector RenderFooter()
	{
		return Render(false);
	}

	/** 
		Renders the current instance as an APE tag header.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance or an empty
		<see cref="ByteVector" /> object if <see cref="Flags" />
		does not include <see cref="FooterFlags.HeaderPresent"
		/>.
	 
	*/
	public ByteVector RenderHeader()
	{
		return (getFlags().getValue() & FooterFlags.HeaderPresent.getValue()) != 0 ? Render(true) : new ByteVector();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Renders the current instance as either an APE tag header
		or footer.
	 
	 @param isHeader
		A <see cref="bool" /> value indicating whether or not the
		current instance is to be rendered as a header.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered version of the current instance.
	 
	*/
	private ByteVector Render(boolean isHeader)
	{
		ByteVector v = new ByteVector();

		// add the file identifier -- "APETAGEX"
		v.add(FileIdentifier);

		// add the version number -- we always render a 2.000
		// tag regardless of what the tag originally was.
		v.add(ByteVector.FromUInt(2000, false));

		// add the tag size
		v.add(ByteVector.FromUInt(tag_size, false));

		// add the item count
		v.add(ByteVector.FromUInt(item_count, false));

		// render and add the flags
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint flags = 0;
		int flags = 0;

		if ((getFlags().getValue() & FooterFlags.HeaderPresent.getValue()) != 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: flags |= (uint) FooterFlags.HeaderPresent;
			flags |= (int) FooterFlags.HeaderPresent.getValue();
		}

		// footer is always present
		if (isHeader)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: flags |= (uint) FooterFlags.IsHeader;
			flags |= (int) FooterFlags.IsHeader.getValue();
		}
		else
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: flags &= (uint) ~FooterFlags.IsHeader;
			flags &= (int)~FooterFlags.IsHeader;
		}

		v.add(ByteVector.FromUInt(flags, false));

		// add the reserved 64bit
		v.add(ByteVector.FromULong(0));

		return v;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IEquatable

	/** 
		Generates a hash code for the current instance.
	 
	 @return 
		A <see cref="int" /> value containing the hash code for
		the current instance.
	 
	*/
	@Override
	public int hashCode()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to an 'unchecked' block in Java:
		unchecked
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (int)((uint) flags ^ tag_size ^ item_count ^ version);
			return (int)((int) flags.getValue() ^ tag_size ^ item_count ^ version);
		}
	}

	/** 
		Checks whether or not the current instance is equal to
		another object.
	 
	 @param other
		A <see cref="object" /> to compare to the current
		instance.
	 
	 @return 
		A <see cref="bool" /> value indicating whether or not the
		current instance is equal to <paramref name="other" />.
	 
	 {@link M:System.IEquatable`1.Equals }
	*/
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Footer))
		{
			return false;
		}

		return Equals((Footer) other);
	}

	/** 
		Checks whether or not the current instance is equal to
		another instance of <see cref="Footer" />.
	 
	 @param other
		A <see cref="Footer" /> object to compare to the current
		instance.
	 
	 @return 
		A <see cref="bool" /> value indicating whether or not the
		current instance is equal to <paramref name="other" />.
	 
	 {@link M:System.IEquatable`1.Equals }
	*/
	public boolean equals(Footer other)
	{
		return flags == other.flags && tag_size == other.tag_size && item_count == other.item_count && version == other.version;
	}

	/** 
		Gets whether or not two instances of <see cref="Footer"
		/> are equal to eachother.
	 
	 @param first
		The first <see cref="Footer" /> object to compare.
	 
	 @param second
		The second <see cref="Footer" /> object to compare.
	 
	 @return 
		<see langword="true" /> if <paramref name="first" /> is
		equal to <paramref name="second" />. Otherwise, <see
		langword="false" />.
	 
	*/
	public static boolean OpEquality(Footer first, Footer second)
	{
		return first.equals(second.clone());
	}

	/** 
		Gets whether or not two instances of <see cref="Footer"
		/> are unequal to eachother.
	 
	 @param first
		The first <see cref="Footer" /> object to compare.
	 
	 @param second
		The second <see cref="Footer" /> object to compare.
	 
	 @return 
		<see langword="true" /> if <paramref name="first" /> is
		unequal to <paramref name="second" />. Otherwise, <see
		langword="false" />.
	 
	*/
	public static boolean OpInequality(Footer first, Footer second)
	{
		return !first.equals(second.clone());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public Footer clone()
	{
		Footer varCopy = new Footer();

		varCopy.version = this.version;
		varCopy.flags = this.flags;
		varCopy.item_count = this.item_count;
		varCopy.tag_size = this.tag_size;

		return varCopy;
	}
}