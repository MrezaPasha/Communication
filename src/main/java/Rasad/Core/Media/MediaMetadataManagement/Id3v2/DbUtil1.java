package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

	</code>
	<code lang="C++">
 #using &lt;System.dll>
 #using &lt;System.Xml.dll>
 #using &lt;taglib-sharp.dll>
 
 using System;
 using System::IO;
 using System::Runtime::Serialization;
 using System::Text;
 using System::Xml::Serialization;
 using Rasad.Core.Media.MediaMetadataManagement::Id3v2;
 
 public ref class DbUtil abstract sealed
 {
 public:
 	static void StoreDatabaseEntry (Tag^ tag, ISerializable^ dbEntry)
 	{
 		StringWriter^ data = gcnew StringWriter (gcnew StringBuilder);
 		XmlSerializer serializer = gcnew XmlSerializer (dbEntry->GetType ());
 		serializer->Serialize (data, dbEntry);
 		PrivateFrame frame = PrivateFrame::Get (tag, L"org.MyProgram.DatabaseEntry", true);
 		frame.PrivateData = Encoding::UTF8->GetBytes (data->ToString ());
 	}
 	
 	static Object^ GetDatabaseEntry (Tag^ tag, Type^ type)
 	{
 		PrivateFrame^ frame = PrivateFrame::Get (tag, L"org.MyProgram.DatabaseEntry", false);
 		if (frame == null)
 			return null;
 	
 		XmlSerializer serializer = gcnew XmlSerializer (type);
 		return serializer->Deserialize (gcnew MemoryStream (frame->PrivateData));
 	}
 }