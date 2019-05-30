package Rasad.Core.Media.MediaMetadataManagement.IFD.Entries;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

/** 
	Contains a Makernote IFD.
 
 
	Makernote IFDs are mostly of the same form. They start with and
	Manufactor specific prefix indicating the type and contain then
	a IFD structure.
	It must be distinguished, where the offsets in the IFD belongs to.
	For some makernotes the offset refers to the beginning of the
	surrounding metadata IFD structure, for others they refer to the
	start of the makernote.
	In addition the endianess of the makernote can be different to the
	endianess of the surrounding metadata.
	This class takes care about all those things.
 
*/
public class MakernoteIFDEntry implements IFDEntry
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Private Fields

	/** <value>
		Stores the prefix of the makernote
	 </value>
	*/
	private ByteVector prefix;

	/** <value>
		Stores the offset of the IFD contained in makernote
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint ifd_offset;
	private int ifd_offset;

	/** <value>
		Indicates, if the offsets are relative to the current makernote
		or absolut to the base_offset of the surrounding IFD.
	 </value>
	*/
	private boolean absolute_offset;

	/** <value>
		Stores, if the makernote is encoded in big- or little endian.
		If the field is <see langword="null"/>, the endianess of the
		surrounding IFD is used.
	 </value>
	*/
	private Boolean is_bigendian;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

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
		The type of the makernote the current instance represents
	 </value>
	*/
	private MakernoteType MakernoteType = getMakernoteType().values()[0];
	public final MakernoteType getMakernoteType()
	{
		return MakernoteType;
	}
	private void setMakernoteType(MakernoteType value)
	{
		MakernoteType = value;
	}

	/** <value>
		The pure <see cref="IFDStructure"/> which is stored by the
		makernote.
	 </value>
	*/
	private IFDStructure Structure;
	public final IFDStructure getStructure()
	{
		return Structure;
	}
	private void setStructure(IFDStructure value)
	{
		Structure = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region Constructors

	/** 
		Construcor.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag ID of the entry this instance
		represents
	 
	 @param structure
		A <see cref="IFDStructure"/> with the IFD structure, which is stored by this
		instance
	 
	 @param makernote_type
		A <see cref="MakernoteType"/> with the type of the makernote.
	 
	 @param prefix
		A <see cref="ByteVector"/> containing the prefix, which should be rendered
		before the real IFD.
	 
	 @param ifd_offset
		A <see cref="System.UInt32"/> with the offset in addition to the relative
		offsets in the IFD
	 
	 @param absolute_offset
		A <see cref="System.Boolean"/> indicating if the offsets of the IFD are relative
		to the <paramref name="ifd_offset"/>, or absolut to the base offset of the
		surrounding IFD.
	 
	 @param is_bigendian
		A <see cref="System.Nullable"/> indicating if the current IFD is encoded in
		big- or little endian. It it is <see langword="null"/>, the endianess of the
		surrounding IFD is used.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public MakernoteIFDEntry(ushort tag, IFDStructure structure, MakernoteType makernote_type, ByteVector prefix, uint ifd_offset, bool absolute_offset, Nullable<bool> is_bigendian)
	public MakernoteIFDEntry(short tag, IFDStructure structure, MakernoteType makernote_type, ByteVector prefix, int ifd_offset, boolean absolute_offset, Boolean is_bigendian)
	{
		setTag(tag);
		setStructure(structure);
		setMakernoteType(makernote_type);
		this.prefix = prefix;
		this.ifd_offset = ifd_offset;
		this.absolute_offset = absolute_offset;
		this.is_bigendian = is_bigendian;
	}

	/** 
		Constructor. Creates a makernote instance just containing an IFD and
		without any special prefix or offset behavior.
	 
	 @param tag
		A <see cref="System.UInt16"/> with the tag ID of the entry this instance
		represents
	 
	 @param structure
		A <see cref="IFDStructure"/> with the IFD structure, which is stored by this
		instance
	 
	 @param makernote_type
		A <see cref="MakernoteType"/> with the type of the makernote.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public MakernoteIFDEntry(ushort tag, IFDStructure structure, MakernoteType makernote_type)
	public MakernoteIFDEntry(short tag, IFDStructure structure, MakernoteType makernote_type)
	{
		this(tag, structure, makernote_type, null, 0, true, null);
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
//ORIGINAL LINE: public ByteVector Render(bool is_bigendian, uint offset, out ushort type, out uint count)
	public final ByteVector Render(boolean is_bigendian, int offset, tangible.OutObject<Short> type, tangible.OutObject<Integer> count)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: type = (ushort) IFDEntryType.Undefined;
		type.argValue = (short) IFDEntryType.Undefined.getValue();

		IFDRenderer renderer = new IFDRenderer((this.is_bigendian != null) ? this.is_bigendian : is_bigendian, getStructure(), absolute_offset ? offset + ifd_offset : ifd_offset);

		ByteVector data = renderer.Render();
		data.add(0, prefix);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: count = (uint) data.Count;
		count.argValue = (int) data.size();
		return data;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion

}