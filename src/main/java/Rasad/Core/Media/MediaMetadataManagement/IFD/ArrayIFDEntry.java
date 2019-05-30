package Rasad.Core.Media.MediaMetadataManagement.IFD;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	This class abstracts common stuff for array IFD entries
*/
public abstract class ArrayIFDEntry<T> implements IFDEntry
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Properties

	/** <value>
		The ID of the tag, the current instance belongs to
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ushort Tag;
	private short Tag;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort getTag()
	public final short getTag()
	{
		return Tag;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setTag(ushort value)
	private void setTag(short value)
	{
		Tag = value;
	}

	/** <value>
		The values stored by the current instance.
	 </value>
	*/
	private T [] Values;
	public final T[] getValues()
	{
		return Values;
	}
	protected final void setValues(T[] value)
	{
		Values = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Constructor.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag ID of the entry this instance
		represents
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ArrayIFDEntry(ushort tag)
	public ArrayIFDEntry(short tag)
	{
		setTag(tag);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Public Methods

	/** 
		Renders the current instance to a <see cref="ByteVector"/>
	 
	 @param is_bigendian
		A <see cref="System.Boolean"/> indicating the endianess for rendering.
	 
	 @param offset
		A <see cref="System.UInt32"/> with the offset, the data is stored.
	 
	 @param type
		A <see cref="System.UInt16"/> the ID of the type, which is rendered
	 
	 @param count
		A <see cref="System.UInt32"/> with the count of the values which are
		rendered.
	 
	 @return 
		A <see cref="ByteVector"/> with the rendered data.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public abstract ByteVector Render(bool is_bigendian, uint offset, out ushort type, out uint count);
	public abstract ByteVector Render(boolean is_bigendian, int offset, tangible.OutObject<Short> type, tangible.OutObject<Integer> count);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}