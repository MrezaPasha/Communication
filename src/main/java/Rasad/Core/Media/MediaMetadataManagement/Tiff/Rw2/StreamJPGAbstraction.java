package Rasad.Core.Media.MediaMetadataManagement.Tiff.Rw2;

import Rasad.Core.Media.MediaMetadataManagement.IFD.*;
import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.Tiff.*;

public class StreamJPGAbstraction implements File.IFileAbstraction
{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream stream;

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public StreamJPGAbstraction(Stream stream)
	{
		this.stream = stream;
	}

	public final String getName()
	{
		return "JpgFromRaw.jpg";
	}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final void CloseStream(System.IO.Stream stream)
	{
		stream.Close();
	}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final System.IO.Stream getReadStream()
	{
		return stream;
	}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public final System.IO.Stream getWriteStream()
	{
		return stream;
	}
}