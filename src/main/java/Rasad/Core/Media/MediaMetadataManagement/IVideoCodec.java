package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

/** 
	This interface inherits <see cref="ICodec" /> to provide
	information about a video codec.
 
 
	<p>When dealing with a <see cref="ICodec" />, if <see
	cref="ICodec.MediaTypes" /> contains <see cref="MediaTypes.Video"
	/>, it is safe to assume that the object also inherits <see
	cref="IVideoCodec" /> and can be recast without issue.</p>
 
*/
public interface IVideoCodec extends ICodec
{
	/** 
		Gets the width of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the width of the
		video represented by the current instance.
	 </value>
	*/
	int getVideoWidth();

	/** 
		Gets the height of the video represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the height of the
		video represented by the current instance.
	 </value>
	*/
	int getVideoHeight();
}