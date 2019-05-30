package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

/** 
	This interface provides generic information about a picture,
	including its contents, as used by various formats.
*/
public interface IPicture
{
	/** 
		Gets and sets the mime-type of the picture data
		stored in the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the mime-type
		of the picture data stored in the current instance.
	 </value>
	*/
	String getMimeType();
	void setMimeType(String value);

	/** 
		Gets and sets the type of content visible in the picture
		stored in the current instance.
	 
	 <value>
		A <see cref="PictureType" /> containing the type of
		content visible in the picture stored in the current
		instance.
	 </value>
	*/
	PictureType getType();
	void setType(PictureType value);

	/** 
		Gets and sets a description of the picture stored in the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the picture stored in the current instance.
	 </value>
	*/
	String getDescription();
	void setDescription(String value);

	/** 
		Gets and sets the picture data stored in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the picture
		data stored in the current instance.
	 </value>
	*/
	ByteVector getData();
	void setData(ByteVector value);
}