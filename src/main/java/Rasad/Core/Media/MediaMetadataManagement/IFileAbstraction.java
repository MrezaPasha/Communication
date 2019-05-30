package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;
import java.io.*;

		<code lang="Boo">import Rasad.Core.Media.MediaMetadataManagement from "taglib-sharp.dll"
	import Gnome.Vfs from "gnome-vfs-sharp"
	
	class VfsFileAbstraction (Rasad.Core.Media.MediaMetadataManagement.File.IFileAbstraction):
			
			_name as string
			
			def constructor(file as string):
					_name = file
			
			Name:
					get:
							return _name
					
			ReadStream:
					get:
							return VfsStream(_name, FileMode.Open)
					
			WriteStream:
					get:
							return VfsStream(_name, FileMode.Open)
			
	if len(argv) == 1:
			Vfs.Initialize()
	
			try:
					file as Rasad.Core.Media.MediaMetadataManagement.File = Rasad.Core.Media.MediaMetadataManagement.File.Create (VfsFileAbstraction (argv[0]))
					print file.Tag.Title
			ensure:
					Vfs.Shutdown()</code>
	 </example>
	*/
	public interface IFileAbstraction
	{
		/** 
			Gets the name or identifier used by the
			implementation.
		 
		 <value>
			A <see cref="string" /> object containing the 
			name or identifier used by the implementation.
		 </value>
		 
			This value would typically represent a path or
			URL to be used when identifying the file in the
			file system, but it could be any value
			as appropriate for the implementation.
		 
		*/
		String getName();

		/** 
			Gets a readable, seekable stream for the file
			referenced by the current instance.
		 
		 <value>
			A <see cref="System.IO.Stream" /> object to be
			used when reading a file.
		 </value>
		 
			This property is typically used when creating
			constructing an instance of <see cref="File" />.
			Upon completion of the constructor, <see
			cref="CloseStream" /> will be called to close
			the stream. If the stream is to be reused after
			this point, <see cref="CloseStream" /> should be
			implemented in a way to keep it open.
		 
		*/
		System.IO.Stream getReadStream();

		/** 
			Gets a writable, seekable stream for the file
			referenced by the current instance.
		 
		 <value>
			A <see cref="System.IO.Stream" /> object to be
			used when writing to a file.
		 </value>
		 
			This property is typically used when saving a
			file with <see cref="Save" />. Upon completion of
			the method, <see cref="CloseStream" /> will be
			called to close the stream. If the stream is to
			be reused after this point, <see
			cref="CloseStream" /> should be implemented in a
			way to keep it open.
		 
		*/
		System.IO.Stream getWriteStream();

		/** 
			Closes a stream originating from the current
			instance.
		 
		 @param stream
			A <see cref="System.IO.Stream" /> object
			originating from the current instance.
		 
		 
			If the stream is to be used outside of the scope,
			of Rasad.Core.Media.MediaMetadataManagement#, this method should perform no action.
			For example, a stream that was created outside of
			the current instance, or a stream that will
			subsequently be used to play the file.
		 
		*/
		void CloseStream(System.IO.Stream stream);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}
