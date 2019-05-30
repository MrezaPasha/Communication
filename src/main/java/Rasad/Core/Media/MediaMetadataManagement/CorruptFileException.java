package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.io.*;

 }
	</code>
	<code lang="C++">
 #using &lt;System.dll>
 #using &lt;taglib-sharp.dll>
 
 using System;
 using Rasad.Core.Media.MediaMetadataManagement;

 void main ()
 {
 	try {
 		File file = File::Create ("partial.mp3"); // Partial download.
 	} catch (CorruptFileException^ e) {
 		Console::WriteLine ("That file is corrupt: {0}", e);
 	}
 }
	</code>
	<code lang="VB">
 Imports System
 Imports Rasad.Core.Media.MediaMetadataManagement

 Public Class ExceptionTest
 	Public Shared Sub Main ()
 		Try
 			file As File = File.Create ("partial.mp3") ' Partial download.
 		Catch e As CorruptFileException
 			Console.WriteLine ("That file is corrupt: {0}", e.ToString ());
 		End Try
	End Sub
 End Class
	</code>
	<code lang="Boo">
 import System
 import Rasad.Core.Media.MediaMetadataManagement

 try:
 	file As File = File.Create ("partial.mp3") # Partial download.
 catch e as CorruptFileException:
 	Console.WriteLine ("That file is corrupt: {0}", e.ToString ());
	</code>
 </example>
*/
public class CorruptFileException extends RuntimeException implements Serializable
{
	/** 
		Constructs and initializes a new instance of <see
		cref="CorruptFileException" /> with a specified
		message.
	 
	 @param message
		A <see cref="string" /> containing a message explaining
		the reason for the exception.
	 
	*/
	public CorruptFileException(String message)
	{
		super(message);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="CorruptFileException" /> with the default
		values.
	*/
	public CorruptFileException()
	{
		super();
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="CorruptFileException" /> with a specified
		message containing a specified exception.
	 
	 @param message
		A <see cref="string" /> containing a message explaining
		the reason for the exception.
	 
	 @param innerException
		A <see cref="Exception" /> object to be contained in the
		new exception. For example, previously caught exception.
	 
	*/
	public CorruptFileException(String message, RuntimeException innerException)
	{
		super(message, innerException);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="CorruptFileException" /> from a specified
		serialization info and streaming context.
	 
	 @param info
		A <see cref="SerializationInfo" /> object containing the
		serialized data to be used for the new instance.
	 
	 @param context
		A <see cref="StreamingContext" /> object containing the
		streaming context information for the new instance.
	 
	 
		This constructor is implemented because <see
		cref="CorruptFileException" /> implements the <see
		cref="ISerializable" /> interface.
	 
	*/
	protected CorruptFileException(SerializationInfo info, StreamingContext context)
	{
		super(info, context);
	}
}