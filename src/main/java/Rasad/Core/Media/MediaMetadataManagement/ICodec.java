package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

/** 
	This interface provides basic information, common to all media
	codecs.
*/
public interface ICodec
{
	/** 
		Gets the duration of the media represented by the current
		instance.
	 
	 <value>
		A <see cref="TimeSpan" /> containing the duration of the
		media represented by the current instance.
	 </value>
	*/
	TimeSpan getDuration();

	/** 
		Gets the types of media represented by the current
		instance.
	 
	 <value>
		A bitwise combined <see cref="MediaTypes" /> containing
		the types of media represented by the current instance.
	 </value>
	*/
	MediaTypes getMediaTypes();

	/** 
		Gets a text description of the media represented by the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the media represented by the current instance.
	 </value>
	*/
	String getDescription();
}