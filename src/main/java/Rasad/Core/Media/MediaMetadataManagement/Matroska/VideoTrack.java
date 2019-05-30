package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
 Describes a Matroska Video Track.
*/
public class VideoTrack extends Track implements IVideoCodec
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private fields

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning disable 414 // Assigned, never used
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint width;
	private int width;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint height;
	private int height;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint disp_width;
	private int disp_width;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint disp_height;
	private int disp_height;
	private double framerate;
	private boolean interlaced;
	private VideoAspectRatioType ratio_type = VideoAspectRatioType.values()[0];
	private ByteVector fourcc;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
///#pragma warning restore 414

	private ArrayList<EBMLElement> unknown_elems = new ArrayList<EBMLElement> ();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
	 Constructs a <see cref="VideoTrack" /> parsing from provided
	 file data.
	 Parsing will be done reading from _file at position references by 
	 parent element's data section.
	 
	 @param _file <see cref="File" /> instance to read from.
	 @param element Parent <see cref="EBMLElement" />.
	*/
	public VideoTrack(File _file, EBMLElement element)
	{
		super(_file, element);
		MatroskaID matroska_id;

		// Here we handle the unknown elements we know, and store the rest
		for (EBMLElement elem : super.getUnknownElements())
		{
			matroska_id = MatroskaID.forValue(elem.getID());

			if (matroska_id == MatroskaID.MatroskaTrackVideo)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ulong i = 0;
				long i = 0;

				while (i < elem.getDataSize())
				{
					EBMLElement child = new EBMLElement(_file, elem.getDataOffset() + i);

					matroska_id = MatroskaID.forValue(child.getID());

					switch (matroska_id)
					{
						case MatroskaVideoDisplayWidth:
							disp_width = child.ReadUInt();
							break;
						case MatroskaVideoDisplayHeight:
							disp_height = child.ReadUInt();
							break;
						case MatroskaVideoPixelWidth:
							width = child.ReadUInt();
							break;
						case MatroskaVideoPixelHeight:
							height = child.ReadUInt();
							break;
						case MatroskaVideoFrameRate:
							framerate = child.ReadDouble();
							break;
						case MatroskaVideoFlagInterlaced:
							interlaced = child.ReadBool();
							break;
						case MatroskaVideoAspectRatioType:
							ratio_type = VideoAspectRatioType.forValue(child.ReadUInt());
							break;
						case MatroskaVideoColourSpace:
							fourcc = child.ReadBytes();
							break;
						default:
							unknown_elems.add(child);
							break;
					}

					i += child.getSize();
				}
			}
			else if (matroska_id == MatroskaID.MatroskaTrackDefaultDuration)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint tmp = elem.ReadUInt();
				int tmp = elem.ReadUInt();
				framerate = 1000000000.0 / (double) tmp;
			}
			else
			{
				unknown_elems.add(elem);
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public fields

	/** 
	 List of unknown elements encountered while parsing.
	*/
	public final ArrayList<EBMLElement> getUnknownElements()
	{
		return unknown_elems;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public methods

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICodec

	/** 
	 This type of track only has video media type.
	*/
	@Override
	public MediaTypes getMediaTypes()
	{
		return MediaTypes.Video;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IVideoCodec

	/** 
	 Describes video track width in pixels.
	*/
	public final int getVideoWidth()
	{
		return (int) width;
	}

	/** 
	 Describes video track height in pixels.
	*/
	public final int getVideoHeight()
	{
		return (int) height;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}