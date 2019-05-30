package Rasad.Core.Media.MediaMetadataManagement.Matroska;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

//
// MatroskaIDs.cs:
//
// Author:
//   Julien Moutte <julien@fluendo.com>
//
// Copyright (C) 2011 FLUENDO S.A.
//
// This library is free software; you can redistribute it and/or modify
// it  under the terms of the GNU Lesser General Public License version
// 2.1 as published by the Free Software Foundation.
//
// This library is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
// USA
//


/** 
 Public enumeration listing Matroska specific EBML Identifiers.
*/
public enum MatroskaID
{
	/** 
	 Indicates a Matroska Segment EBML element.
	*/
	MatroskaSegment(0x18538067),

	/** 
	 Indicates a Matroska Segment Info EBML element.
	*/
	MatroskaSegmentInfo(0x1549A966),

	/** 
	 Indicates a Matroska Tracks EBML Element.
	*/
	MatroskaTracks(0x1654AE6B),

	/** 
	 Indicates a Matroska Cues EBML element.
	*/
	MatroskaCues(0x1C53BB6B),

	/** 
	 Indicates a Matroska Tags EBML element.
	*/
	MatroskaTags(0x1254C367),

	/** 
	 Indicates a Matroska Seek Head EBML element.
	*/
	MatroskaSeekHead(0x114D9B74),

	/** 
	 Indicates a Matroska Cluster EBML element.
	*/
	MatroskaCluster(0x1F43B675),

	/** 
	 Indicates a Matroska Attachments EBML element.
	*/
	MatroskaAttachments(0x1941A469),

	/** 
	 Indicates a Matroska Chapters EBML element.
	*/
	MatroskaChapters(0x1043A770),

	/* IDs in the SegmentInfo master */

	/** 
	 Indicate a Matroska Code Scale EBML element.
	*/
	MatroskaTimeCodeScale(0x2AD7B1),

	/** 
	 Indicates a Matroska Duration EBML element.
	*/
	MatroskaDuration(0x4489),

	/** 
	 Indicates a Matroska Writing App EBML element.
	*/
	MatroskaWrittingApp(0x5741),

	/** 
	 Indicates a Matroska Muxing App EBML element.
	*/
	MatroskaMuxingApp(0x4D80),

	/** 
	 Indicate a Matroska Date UTC EBML element.
	*/
	MatroskaDateUTC(0x4461),

	/** 
	 Indicate a Matroska Segment UID EBML element.
	*/
	MatroskaSegmentUID(0x73A4),

	/** 
	 Indicate a Matroska Segment File Name EBML element.
	*/
	MatroskaSegmentFileName(0x7384),

	/** 
	 Indicate a Matroska Prev UID EBML element.
	*/
	MatroskaPrevUID(0x3CB923),

	/** 
	 Indicate a Matroska Prev File Name EBML element.
	*/
	MatroskaPrevFileName(0x3C83AB),

	/** 
	 Indicate a Matroska Nex UID EBML element.
	*/
	MatroskaNexUID(0x3EB923),

	/** 
	 Indicate a Matroska Nex File Name EBML element.
	*/
	MatroskaNexFileName(0x3E83BB),

	/** 
	 Indicate a Matroska Title EBML element.
	*/
	MatroskaTitle(0x7BA9),

	/** 
	 Indicate a Matroska Segment Family EBML element.
	*/
	MatroskaSegmentFamily(0x4444),

	/** 
	 Indicate a Matroska Chapter Translate EBML element.
	*/
	MatroskaChapterTranslate(0x6924),

	/* ID in the Tracks master */

	/** 
	 Indicate a Matroska Track Entry EBML element.
	*/
	MatroskaTrackEntry(0xAE),

	/* IDs in the TrackEntry master */

	/** 
	 Indicate a Matroska Track Number EBML element.
	*/
	MatroskaTrackNumber(0xD7),

	/** 
	 Indicate a Matroska Track UID EBML element.
	*/
	MatroskaTrackUID(0x73C5),

	/** 
	 Indicate a Matroska Track Type EBML element.
	*/
	MatroskaTrackType(0x83),

	/** 
	 Indicate a Matroska Track Audio EBML element.
	*/
	MatroskaTrackAudio(0xE1),

	/** 
	 Indicate a Matroska Track Video EBML element.
	*/
	MatroskaTrackVideo(0xE0),

	/** 
	 Indicate a Matroska Track Encoding EBML element.
	*/
	MatroskaContentEncodings(0x6D80),

	/** 
	 Indicate a Matroska Codec ID EBML element.
	*/
	MatroskaCodecID(0x86),

	/** 
	 Indicate a Matroska Codec Private EBML element.
	*/
	MatroskaCodecPrivate(0x63A2),

	/** 
	 Indicate a Matroska Codec Name EBML element.
	*/
	MatroskaCodecName(0x258688),

	/** 
	 Indicate a Matroska Track Name EBML element.
	*/
	MatroskaTrackName(0x536E),

	/** 
	 Indicate a Matroska Track Language EBML element.
	*/
	MatroskaTrackLanguage(0x22B59C),

	/** 
	 Indicate a Matroska Track Enabled EBML element.
	*/
	MatroskaTrackFlagEnabled(0xB9),

	/** 
	 Indicate a Matroska Track Flag Default EBML element.
	*/
	MatroskaTrackFlagDefault(0x88),

	/** 
	 Indicate a Matroska Track Flag Forced EBML element.
	*/
	MatroskaTrackFlagForced(0x55AA),

	/** 
	 Indicate a Matroska Track Flag Lacing EBML element.
	*/
	MatroskaTrackFlagLacing(0x9C),

	/** 
	 Indicate a Matroska Track Min Cache EBML element.
	*/
	MatroskaTrackMinCache(0x6DE7),

	/** 
	 Indicate a Matroska Track Max Cache EBML element.
	*/
	MatroskaTrackMaxCache(0x6DF8),

	/** 
	 Indicate a Matroska Track Default Duration EBML element.
	*/
	MatroskaTrackDefaultDuration(0x23E383),

	/** 
	 Indicate a Matroska Track Time Code Scale EBML element.
	*/
	MatroskaTrackTimeCodeScale(0x23314F),

	/** 
	 Indicate a Matroska Track Max Block Addition EBML element.
	*/
	MatroskaMaxBlockAdditionID(0x55EE),

	/** 
	 Indicate a Matroska Track Attachment Link EBML element.
	*/
	MatroskaTrackAttachmentLink(0x7446),

	/** 
	 Indicate a Matroska Track Overlay EBML element.
	*/
	MatroskaTrackOverlay(0x6FAB),

	/** 
	 Indicate a Matroska Track Translate EBML element.
	*/
	MatroskaTrackTranslate(0x6624),

	/** 
	 Indicate a Matroska Track Offset element.
	*/
	MatroskaTrackOffset(0x537F),

	/** 
	 Indicate a Matroska Codec Settings EBML element.
	*/
	MatroskaCodecSettings(0x3A9697),

	/** 
	 Indicate a Matroska Codec Info URL EBML element.
	*/
	MatroskaCodecInfoUrl(0x3B4040),

	/** 
	 Indicate a Matroska Codec Download URL EBML element.
	*/
	MatroskaCodecDownloadUrl(0x26B240),

	/** 
	 Indicate a Matroska Codec Decode All EBML element.
	*/
	MatroskaCodecDecodeAll(0xAA),

	/* IDs in the TrackVideo master */
	/* NOTE: This one is here only for backward compatibility.
	* Use _TRACKDEFAULDURATION */

	/** 
	 Indicate a Matroska Video Frame Rate EBML element.
	*/
	MatroskaVideoFrameRate(0x2383E3),

	/** 
	 Indicate a Matroska Video Display Width EBML element.
	*/
	MatroskaVideoDisplayWidth(0x54B0),

	/** 
	 Indicate a Matroska Video Display Height EBML element.
	*/
	MatroskaVideoDisplayHeight(0x54BA),

	/** 
	 Indicate a Matroska Video Display Unit EBML element.
	*/
	MatroskaVideoDisplayUnit(0x54B2),

	/** 
	 Indicate a Matroska Video Pixel Width EBML element.
	*/
	MatroskaVideoPixelWidth(0xB0),

	/** 
	 Indicate a Matroska Video Pixel Height EBML element.
	*/
	MatroskaVideoPixelHeight(0xBA),

	/** 
	 Indicate a Matroska Video Pixel Crop Bottom EBML element.
	*/
	MatroskaVideoPixelCropBottom(0x54AA),

	/** 
	 Indicate a Matroska Video Pixel Crop Top EBML element.
	*/
	MatroskaVideoPixelCropTop(0x54BB),

	/** 
	 Indicate a Matroska Video Pixel Crop Left EBML element.
	*/
	MatroskaVideoPixelCropLeft(0x54CC),

	/** 
	 Indicate a Matroska Video Pixel Crop Right EBML element.
	*/
	MatroskaVideoPixelCropRight(0x54DD),

	/** 
	 Indicate a Matroska Video Flag Interlaced EBML element.
	*/
	MatroskaVideoFlagInterlaced(0x9A),

	/** 
	 Indicate a Matroska Video Stereo Mode EBML element.
	*/
	MatroskaVideoStereoMode(0x53B8),

	/** 
	 Indicate a Matroska Video Aspect Ratio Type EBML element.
	*/
	MatroskaVideoAspectRatioType(0x54B3),

	/** 
	 Indicate a Matroska Video Colour Space EBML element.
	*/
	MatroskaVideoColourSpace(0x2EB524),

	/** 
	 Indicate a Matroska Video Gamma Value EBML element.
	*/
	MatroskaVideoGammaValue(0x2FB523),

	/* IDs in the TrackAudio master */

	/** 
	 Indicate a Matroska Audio Sampling Freq EBML element.
	*/
	MatroskaAudioSamplingFreq(0xB5),

	/** 
	 Indicate a Matroska Audio Bit Depth EBML element.
	*/
	MatroskaAudioBitDepth(0x6264),

	/** 
	 Indicate a Matroska Audio Channels EBML element.
	*/
	MatroskaAudioChannels(0x9F),

	/** 
	 Indicate a Matroska Audio Channels Position EBML element.
	*/
	MatroskaAudioChannelsPositions(0x7D7B),

	/** 
	 Indicate a Matroska Audio Output Sampling Freq EBML element.
	*/
	MatroskaAudioOutputSamplingFreq(0x78B5),

	/* IDs in the Tags master */

	/** 
	 Indicate a Matroska Tag EBML element.
	*/
	MatroskaTag(0x7373),

	/* in the Tag master */

	/** 
	 Indicate a Matroska Simple Tag EBML element.
	*/
	MatroskaSimpleTag(0x67C8),

	/** 
	 Indicate a Matroska Targets EBML element.
	*/
	MatroskaTargets(0x63C0),

	/* in the SimpleTag master */

	/** 
	 Indicate a Matroska Tag Name EBML element.
	*/
	MatroskaTagName(0x45A3),

	/** 
	 Indicate a Matroska Tag String EBML element.
	*/
	MatroskaTagString(0x4487),

	/** 
	 Indicate a Matroska Tag Language EBML element.
	*/
	MatroskaTagLanguage(0x447A),

	/** 
	 Indicate a Matroska Tag Default EBML element.
	*/
	MatroskaTagDefault(0x4484),

	/** 
	 Indicate a Matroska Tag Binary EBML element.
	*/
	MatroskaTagBinary(0x4485);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, MatroskaID> mappings;
	private static java.util.HashMap<Integer, MatroskaID> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MatroskaID.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, MatroskaID>();
				}
			}
		}
		return mappings;
	}

	private MatroskaID(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static MatroskaID forValue(int value)
	{
		return getMappings().get(value);
	}
}