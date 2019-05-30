package main.java.Rasad.Core.Compression.Core;


import java.io.*;

/** 
 Event arguments for directories.
*/
public class DirectoryEventArgs extends Rasad.Core.Compression.Core.ScanEventArgs
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialize an instance of <see cref="DirectoryEventArgs"></see>.
	 
	 @param name The name for this directory.
	 @param hasMatchingFiles Flag value indicating if any matching files are contained in this directory.
	*/
	public DirectoryEventArgs(String name, boolean hasMatchingFiles)
	{
		super(name);
		hasMatchingFiles_ = hasMatchingFiles;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Get a value indicating if the directory contains any matching files or not.
	*/
	public final boolean getHasMatchingFiles()
	{
		return hasMatchingFiles_;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private boolean hasMatchingFiles_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}