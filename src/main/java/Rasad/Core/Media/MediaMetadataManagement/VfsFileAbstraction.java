package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;
import java.io.*;

	
	public class VfsFileAbstraction : Rasad.Core.Media.MediaMetadataManagement.File.IFileAbstraction
	{
		private string name;
	
		public VfsFileAbstraction (string file)
		{
			name = file;
		}
	
		public string Name {
			get { return name; }
		}
	
		public System.IO.Stream ReadStream {
			get { return new VfsStream(Name, System.IO.FileMode.Open); }
		}
	
		public System.IO.Stream WriteStream {
			get { return new VfsStream(Name, System.IO.FileMode.Open); }
		}
	
		public void CloseStream (System.IO.Stream stream)
		{
			stream.Close ();
		}
	}</code>