package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

	</code>
	<code lang="VB">
 Imports Rasad.Core.Media.MediaMetadataManagement
 Imports Rasad.Core.Media.MediaMetadataManagement.Id3v2

 Public Shared Class TrackUtil
 	Public Shared Sub GetPlayCount (filename As String) As Integer
 		Dim file As File = File.Create (filename, ReadStyle.None)
 		Dim tag As Id3v2.Tag = file.GetTag (TagTypes.Id3v2, False)
 		If tag Is Nothing Then Return 0
 		
 		Dim frame As PlayCountFrame = PlayCountFrame.Get (tag, False)
		If frame Is Nothing Then Return 0

 		Return frame.PlayCount
 	End Sub

	Public Shared Sub IncrementPlayCount (filename As String)
 		Dim file As File = File.Create (filename, ReadStyle.None)
 		Dim tag As Id3v2.Tag = file.GetTag (TagTypes.Id3v2, True)
 		If tag Is Nothing Then Exit Sub
 		
 		PlayCountFrame.Get (tag, True).PlayCount += 1
 		file.Save ()
 	End Sub
 End Class
	</code>
	<code lang="Boo">
 import Rasad.Core.Media.MediaMetadataManagement
 import Rasad.Core.Media.MediaMetadataManagement.Id3v2
 
 public static class TrackUtil:
 	static def GetPlayCount (filename as string) as int:
 		file As File = File.Create (filename, ReadStyle.None)
 		tag as Id3v2.Tag = file.GetTag (TagTypes.Id3v2, false)
		if tag == null:
 			return 0
 		
 		frame as PlayCountFrame = PlayCountFrame.Get (tag, false)
 		if frame == null:
			return 0

 		return frame.PlayCount

	static def IncrementPlayCount (filename as string):
 		file as File = File.Create (filename, ReadStyle.None)
 		tag as Id3v2.Tag = file.GetTag (TagTypes.Id3v2, True)
		if tag == null:
 			return
 		
 		PlayCountFrame.Get (tag, true).PlayCount ++
 		file.Save ()
	</code>
 </example>
*/
public class PlayCountFrame extends Frame
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Properties

	/** 
		Contains the total number of times the file has been
		played.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private ulong play_count = 0;
	private long play_count = 0;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="PlayCountFrame" /> with a count of zero.
	 
	 
		When a frame is created, it is not automatically added to
		the tag. Consider using <see cref="Get" /> for more
		integrated frame creation.
	 
	*/
	public PlayCountFrame()
	{
		super(FrameType.PCNT, 4);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="PlayCountFrame" /> by reading its raw data in a
		specified ID3v2 version.
	 
	 @param data
		A <see cref="ByteVector" /> object starting with the raw
		representation of the new frame.
	 
	 @param version
		A <see cref="byte" /> indicating the ID3v2 version the
		raw frame is encoded in.
	 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public PlayCountFrame(ByteVector data, byte version)
	public PlayCountFrame(ByteVector data, byte version)
	{
		super(data, version);
		SetData(data, 0, version, true);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="PlayCountFrame" /> by reading its raw data in a
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
//ORIGINAL LINE: protected internal PlayCountFrame(ByteVector data, int offset, FrameHeader header, byte version)
	protected PlayCountFrame(ByteVector data, int offset, FrameHeader header, byte version)
	{
		super(header.clone());
		SetData(data, offset, version, false);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets and sets the play count of the current instance.
	 
	 <value>
		A <see cref="ulong" /> containing the play count of the
		current instance.
	 </value>
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong getPlayCount()
	public final long getPlayCount()
	{
		return play_count;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setPlayCount(ulong value)
	public final void setPlayCount(long value)
	{
		play_count = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Gets a play count frame from a specified tag, optionally
		creating it if it does not exist.
	 
	 @param tag
		A <see cref="Tag" /> object to search in.
	 
	 @param create
		A <see cref="bool" /> specifying whether or not to create
		and add a new frame to the tag if a match is not found.
	 
	 @return 
		A <see cref="PlayCountFrame" /> object containing the
		matching frame, or <see langword="null" /> if a match
		wasn't found and <paramref name="create" /> is <see
		langword="false" />.
	 
	*/
	public static PlayCountFrame Get(Tag tag, boolean create)
	{
		PlayCountFrame pcnt;
		for (Frame frame : tag)
		{
			pcnt = frame instanceof PlayCountFrame ? (PlayCountFrame)frame : null;

			if (pcnt != null)
			{
				return pcnt;
			}
		}

		if (!create)
		{
			return null;
		}

		pcnt = new PlayCountFrame();
		tag.AddFrame(pcnt);
		return pcnt;
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
		play_count = data.ToULong();
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
		ByteVector data = ByteVector.FromULong(play_count);
		while (data.size() > 4 && data.get(0) == 0)
		{
			data.remove(0);
		}

		return data;
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
		PlayCountFrame frame = new PlayCountFrame();
		frame.play_count = play_count;
		return frame;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#endregion
}