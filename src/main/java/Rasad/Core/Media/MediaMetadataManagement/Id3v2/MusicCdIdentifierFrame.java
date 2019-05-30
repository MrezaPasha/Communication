package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

	</code>
	<code lang="VB">
 Imports Rasad.Core.Media.MediaMetadataManagement
 Imports Rasad.Core.Media.MediaMetadataManagement.Id3v2
 
 Public Shared Class LookupUtil
 	Public Shared Sub GetCdIdentifier (filename As String) As Rasad.Core.Media.MediaMetadataManagement.ByteVector
 		Dim file As File = File.Create (filename, ReadStyle.None)
 		Dim tag As Id3v2.Tag = file.GetTag (TagTypes.Id3v2, False)
 		If tag Is Nothing Return New ByteVector ()
 		
 		Dim frame As MusicCdIdentifierFrame = MusicCdIdentifierFrame.Get (tag, False)
 		If frame Is Nothing Return New ByteVector ()

 		Return frame.Data
 	End Sub
 End Class
	</code>
	<code lang="Boo">
 import Rasad.Core.Media.MediaMetadataManagement
 import Rasad.Core.Media.MediaMetadataManagement.Id3v2
 
 public static class LookupUtil:
 	static def GetCdIdentifier (filename as string) as Rasad.Core.Media.MediaMetadataManagement.ByteVector:
 		file as File = File.Create (filename, ReadStyle.None)
 		tag as Id3v2.Tag = file.GetTag (TagTypes.Id3v2, false)
 		if tag == null:
 			return ByteVector ()
 		
 		frame as MusicCdIdentifierFrame = MusicCdIdentifierFrame.Get (tag, false)
 		if frame == null:
 			return ByteVector ()

 		return frame.Data
	</code>
 </example>
*/
public class MusicCdIdentifierFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Properties

	/** 
		Contains the identifer data for the current instance.
	*/
	private ByteVector field_data = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="MusicCdIdentifierFrame" /> with empty
		identifier data.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public MusicCdIdentifierFrame()
	{
		super(FrameType.MCDI, 4);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="MusicCdIdentifierFrame" /> by reading its raw data
		in a specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public MusicCdIdentifierFrame(ByteVector data, byte version)
	public MusicCdIdentifierFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="MusicCdIdentifierFrame" /> by reading its raw data
		in a specified ID3v2 version.
	 
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
//ORIGINAL LINE: protected internal MusicCdIdentifierFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected MusicCdIdentifierFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(header.clone());
		SetData(data, offset, version, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets and sets the identifier data stored in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> containing the identifier
		data stored in the current instance.
	 </value>
	*/
	public final ByteVector getData()
	{
		return field_data;
	}
	public final void setData(ByteVector value)
	{
		field_data = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Gets a music CD identifier frame from a specified tag,
		optionally creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="MusicCdIdentifierFrame" /> object containing
		the matching frame, or <see langword="null" /> if a match
		wasn't found and <paramref name="create" /> is <see
		langword="false" />.
	 
	*/
	public static MusicCdIdentifierFrame Get(Tag tag, boolean create)
	{
		MusicCdIdentifierFrame mcdi;
		for (Frame frame : tag)
		{
			mcdi = frame instanceof MusicCdIdentifierFrame ? (MusicCdIdentifierFrame)frame : null;

			if (mcdi != null)
			{
				return mcdi;
			}
		}

		if (!create)
		{
			return null;
		}

		mcdi = new MusicCdIdentifierFrame();
		tag.AddFrame(mcdi);
		return mcdi;
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
		field_data = data;
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
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: protected override ByteVector RenderFields(byte version)
	@Override
	protected ByteVector RenderFields(byte version)
	{
		return field_data != null ? field_data : new ByteVector();
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
		MusicCdIdentifierFrame frame = new MusicCdIdentifierFrame();
		if (field_data != null)
		{
			frame.field_data = new ByteVector(field_data);
		}
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}