package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

	</code>
	<code lang="VB">
 Imports System
 Imports System.IO
 Imports System.Runtime.Serialization
 Imports System.Text
 Imports System.Xml.Serialization
 Imports Rasad.Core.Media.MediaMetadataManagement.Id3v2

 Public Shared Class DbUtil
 	Public Shared Sub StoreDatabaseEntry (tag As Tag, dbEntry As ISerializable)
 		Dim data As New StringWriter (New StringBuilder ())
 		Dim serializer As New XmlSerializer (dbEntry.GetType ())
 		serializer.Serialize (data, dbEntry)
 		Dim frame As PrivateFrame = PrivateFrame.Get (tag, "org.MyProgram.DatabaseEntry", True)
 		frame.PrivateData = Encoding.UTF8.GetBytes (data.ToString ())
 	End Sub
 	
 	Public Shared Sub GetDatabaseEntry (tag As Tag, type As Type)
 		Dim frame As PrivateFrame = PrivateFrame.Get (tag, "org.MyProgram.DatabaseEntry", False)
 		If frame Is Nothing Then Return Nothing
 	
 		Dim serializer As XmlSerializer = New XmlSerializer (type)
 		Return serializer.Deserialize (New MemoryStream (frame.PrivateData))
 	End Sub
 End Class
	</code>
	<code lang="Boo">
 import System
 import System.IO
 import System.Runtime.Serialization
 import System.Text
 import System.Xml.Serialization
 import Rasad.Core.Media.MediaMetadataManagement.Id3v2
 
 public static class DbUtil:
 	static def StoreDatabaseEntry (tag as Tag, dbEntry as ISerializable):
 		data as StringWriter = StringWriter (StringBuilder ())
 		serializer as XmlSerializer = XmlSerializer (dbEntry.GetType ())
 		serializer.Serialize (data, dbEntry)
 		frame as PrivateFrame = PrivateFrame.Get (tag, "org.MyProgram.DatabaseEntry", true)
 		frame.PrivateData = Encoding.UTF8.GetBytes (data.ToString ())
	
 	static def GetDatabaseEntry (tag As Tag, type As Type):
 		frame as PrivateFrame = PrivateFrame.Get (tag, "org.MyProgram.DatabaseEntry", false)
 		if frame == null:
			return null
 		
 		serializer as XmlSerializer = XmlSerializer (type)
 		return serializer.Deserialize (MemoryStream (frame.PrivateData))
	</code>
 </example>
*/
public class PrivateFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Properties

	/** 
		Contains the owner of the current instance.
	*/
	private String owner = null;

	/** 
		Contains private data stored in the current instance.
	*/
	private ByteVector data = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="PrivateFrame" /> for a specified owner and data.
	 
	 @param owner
		A <see cref="string" /> containing the owner of the new
		frame.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the data
		for the new frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public PrivateFrame(String owner, ByteVector data)
	{
		super(FrameType.PRIV, 4);
		this.owner = owner;
		this.data = data;
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="PrivateFrame" /> without data for a specified
		owner.
	 
	 @param owner
		A <see cref="string" /> containing the owner of the new
		frame.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public PrivateFrame(String owner)
	{
		this(owner, null);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="PrivateFrame" /> by reading its raw data in a
		specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public PrivateFrame(ByteVector data, byte version)
	public PrivateFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="PrivateFrame" /> by reading its raw data in a
		specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the raw
		representation of the new frame.
	 
	 @param offset
		A <see cref="int" /> indicating at what offset in
		<paramref name="data" /> the frame actually begins.
	 
	 @param header
		A <see cref="FrameHeader" /> containing the header of the
		frame found at <paramref name="offset" /> in the data.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected internal PrivateFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected PrivateFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(header.clone());
		SetData(data, offset, version, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets the owner of the current instance.
	 
	 <value>
		A <see cref="string" /> containing the owner of the
		current instance.
	 </value>
	 
		There should only be one frame with a given owner per
		tag.
	 
	*/
	public final String getOwner()
	{
		return owner;
	}

	/** 
		Gets and sets the private data stored in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> containing the private data
		stored in the current instance.
	 </value>
	*/
	public final ByteVector getPrivateData()
	{
		return data;
	}
	public final void setPrivateData(ByteVector value)
	{
		data = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Gets a specified private frame from the specified tag,
		optionally creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param owner
		A <see cref="string" /> specifying the owner to match.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="PrivateFrame" /> object containing the
		matching frame, or <see langword="null" /> if a match
		wasn't found and <paramref name="create" /> is <see
		langword="false" />.
	 
	*/
	public static PrivateFrame Get(Tag tag, String owner, boolean create)
	{
		PrivateFrame priv;

		for (Frame frame : tag.GetFrames(FrameType.PRIV))
		{
			priv = frame instanceof PrivateFrame ? (PrivateFrame)frame : null;
			if (priv != null && priv.getOwner().equals(owner))
			{
				return priv;
			}
		}

		if (!create)
		{
			return null;
		}

		priv = new PrivateFrame(owner);
		tag.AddFrame(priv);
		return priv;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods

	/** 
		Populates the values in the current instance by parsing
		its field data in a specified version.
	 
	 @param data
		A <see cref="ByteVector" /> object containing the
		extracted field data.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		field data is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override void ParseFields(ByteVector data, byte version)
	@Override
	protected void ParseFields(ByteVector data, byte version)
	{
		if (data.size() < 1)
		{
			throw new CorruptFileException("A private frame must contain at least 1 byte.");
		}

		ByteVectorCollection l = ByteVectorCollection.split(data, ByteVector.TextDelimiter(StringType.Latin1), 1, 2);

		if (l.size() == 2)
		{
			this.owner = l.get(0).toString(StringType.Latin1);
			this.data = l.get(1);
		}
	}

	/** 
		Renders the values in the current instance into field
		data for a specified version.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		field data is to be encoded in.
	 
	 @return 
		A <see cref="ByteVector" /> object containing the
		rendered field data.
	 
	 @exception NotImplementedException
		<paramref name="version" /> is less than 3. ID3v2.2 does
		not support this frame.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override ByteVector RenderFields(byte version)
	@Override
	protected ByteVector RenderFields(byte version)
	{
		if (version < 3)
		{
			throw new UnsupportedOperationException();
		}

		ByteVector v = new ByteVector();

		v.add(ByteVector.FromString(owner, StringType.Latin1));
		v.add(ByteVector.TextDelimiter(StringType.Latin1));
		v.add(data);

		return v;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#region ICloneable

	/** 
		Creates a deep copy of the current instance.
	 
	 @return 
		A new <see cref="Frame" /> object identical to the
		current instance.
	 
	*/
	@Override
	public Frame Clone()
	{
		PrivateFrame frame = new PrivateFrame(owner);
		if (data != null)
		{
			frame.data = new ByteVector(data);
		}
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}