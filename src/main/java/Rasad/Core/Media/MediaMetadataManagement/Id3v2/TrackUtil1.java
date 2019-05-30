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

 public ref class TrackUtil abstract sealed
 {
 public:
 	static int GetPlayCount (String^ filename)
 	{
 		File^ file = File.Create (filename, ReadStyle.None);
 		Id3v2::Tag^ tag = dynamic_cast&lt;Id3v2::Tag^> (file.GetTag (TagTypes::Id3v2, false));
 		if (tag == null)
 			return 0;
 		
 		PlayCountFrame^ frame = PlayCountFrame::Get (tag, false);
 		if (frame == null)
 			return 0;

 		return frame->PlayCount;
 	}
 	
 	static void IncrementPlayCount (String^ filename)
 	{
 		File^ file = File::Create (filename, ReadStyle::None);
 		Id3v2.Tag^ tag = dynamic_cast&lt;Id3v2::Tag^> (file.GetTag (TagTypes::Id3v2, true));
 		if (tag == null)
 			return;
 		
 		PlayCountFrame::Get (tag, true)->PlayCount ++;
 		file->Save ();
 	}
 }