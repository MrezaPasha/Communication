package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

/** 
	This interface provides information specific
	to lossless audio codecs.
*/
public interface ILosslessAudioCodec
{
	/** 
		Gets the number of bits per sample in the audio
		represented by the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the number of bits
		per sample in the audio represented by the current
		instance.
	 </value>
	*/
	int getBitsPerSample();
}