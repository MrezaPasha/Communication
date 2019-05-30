package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

	</code>
	<code lang="C++">
 #using &lt;System.dll>
 #using &lt;taglib-sharp.dll>

 using System;
 using Rasad.Core.Media.MediaMetadataManagement;
 using Rasad.Core.Media.MediaMetadataManagement::Id3v2;
 
 public ref class LookupUtil abstract sealed
 {
 public:
 	static ByteVector^ GetCdIdentifier (String^ filename)
 	{
 		File^ file = File::Create (filename, ReadStyle::None);
 		Id3v2::Tag^ tag = dynamic_cast&lt;Id3v2::Tag^> (file.GetTag (TagTypes::Id3v2, false));
 		if (tag == null)
 			return gcnew ByteVector;
 		
 		MusicCdIdentifierFrame^ frame = MusicCdIdentifierFrame::Get (tag, false);
 		if (frame == null)
 			return gcnew ByteVector;

 		return frame->Data;
 	}
 }