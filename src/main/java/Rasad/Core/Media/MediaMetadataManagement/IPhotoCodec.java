package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

/** 
	This interface inherits <see cref="ICodec" /> to provide
	information about a photo.
 
 
	<p>When dealing with a <see cref="ICodec" />, if <see
	cref="ICodec.MediaTypes" /> contains <see cref="MediaTypes.Photo"
	/>, it is safe to assume that the object also inherits <see
	cref="IPhotoCodec" /> and can be recast without issue.</p>
 
*/
public interface IPhotoCodec extends ICodec
{
	/** 
		Gets the width of the photo represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the width of the
		photo represented by the current instance.
	 </value>
	*/
	int getPhotoWidth();

	/** 
		Gets the height of the photo represented by the current
		instance.
	 
	 <value>
		A <see cref="int" /> value containing the height of the
		photo represented by the current instance.
	 </value>
	*/
	int getPhotoHeight();

	/** 
		Gets the (format specific) quality indicator of the photo
		represented by the current instance.
	 
	 <value>
		A <see cref="int" /> value indicating the quality. A value
		0 means that there was no quality indicator for the format
		or the file.
	 </value>
	*/
	int getPhotoQuality();
}