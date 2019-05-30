package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

/** 
	This interface inherits <see cref="ICodec" /> to provide
	information about an audio codec.
 
 
	<p>When dealing with a <see cref="ICodec" />, if <see
	cref="ICodec.MediaTypes" /> contains <see cref="MediaTypes.Audio"
	/>, it is safe to assume that the object also inherits <see
	cref="IAudioCodec" /> and can be recast without issue.</p>
 
*/
public interface IAudioCodec extends ICodec
{
	/** 
		Gets the bitrate of the audio represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing a bitrate of the
		audio represented by the current instance.
	 </value>
	*/
	int getAudioBitrate();

	/** 
		Gets the sample rate of the audio represented by the
		current instance.
	 
	 <value>
		A <see cref="int" /> value containing the sample rate of
		the audio represented by the current instance.
	 </value>
	*/
	int getAudioSampleRate();

	/** 
		Gets the number of channels in the audio represented by
		the current instance.
	 
	 <value>
		A <see cref="int" /> value containing the number of
		channels in the audio represented by the current
		instance.
	 </value>
	*/
	int getAudioChannels();
}