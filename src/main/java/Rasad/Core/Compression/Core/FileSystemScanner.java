package main.java.Rasad.Core.Compression.Core;


import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

/** 
 FileSystemScanner provides facilities scanning of files and directories.
*/
public class FileSystemScanner
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a new instance of <see cref="FileSystemScanner"></see>
	 
	 @param filter The <see cref="PathFilter">file filter</see> to apply when scanning.
	*/
	public FileSystemScanner(String filter)
	{
		fileFilter_ = new PathFilter(filter);
	}

	/** 
	 Initialise a new instance of <see cref="FileSystemScanner"></see>
	 
	 @param fileFilter The <see cref="PathFilter">file filter</see> to apply.
	 @param directoryFilter The <see cref="PathFilter"> directory filter</see> to apply.
	*/
	public FileSystemScanner(String fileFilter, String directoryFilter)
	{
		fileFilter_ = new PathFilter(fileFilter);
		directoryFilter_ = new PathFilter(directoryFilter);
	}

	/** 
	 Initialise a new instance of <see cref="FileSystemScanner"></see>
	 
	 @param fileFilter The file <see cref="IScanFilter">filter</see> to apply.
	*/
	public FileSystemScanner(IScanFilter fileFilter)
	{
		fileFilter_ = fileFilter;
	}

	/** 
	 Initialise a new instance of <see cref="FileSystemScanner"></see>
	 
	 @param fileFilter The file <see cref="IScanFilter">filter</see>  to apply.
	 @param directoryFilter The directory <see cref="IScanFilter">filter</see>  to apply.
	*/
	public FileSystemScanner(IScanFilter fileFilter, IScanFilter directoryFilter)
	{
		fileFilter_ = fileFilter;
		directoryFilter_ = directoryFilter;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Delegates
	/** 
	 Delegate to invoke when a directory is processed.
	*/
	public ProcessDirectoryHandler ProcessDirectory;

	/** 
	 Delegate to invoke when a file is processed.
	*/
	public ProcessFileHandler ProcessFile;

	/** 
	 Delegate to invoke when processing for a file has finished.
	*/
	public CompletedFileHandler CompletedFile;

	/** 
	 Delegate to invoke when a directory failure is detected.
	*/
	public DirectoryFailureHandler DirectoryFailure;

	/** 
	 Delegate to invoke when a file failure is detected.
	*/
	public FileFailureHandler FileFailure;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Raise the DirectoryFailure event.
	 
	 @param directory The directory name.
	 @param e The exception detected.
	*/
	private boolean OnDirectoryFailure(String directory, RuntimeException e)
	{
		DirectoryFailureHandler handler = (Object sender, ScanFailureEventArgs e) -> DirectoryFailure.invoke(sender, e);
		boolean result = (handler != null);
		if (result)
		{
			ScanFailureEventArgs args = new ScanFailureEventArgs(directory, e);
			handler.invoke(this, args);
			alive_ = args.getContinueRunning();
		}
		return result;
	}

	/** 
	 Raise the FileFailure event.
	 
	 @param file The file name.
	 @param e The exception detected.
	*/
	private boolean OnFileFailure(String file, RuntimeException e)
	{
		FileFailureHandler handler = (Object sender, ScanFailureEventArgs e) -> FileFailure.invoke(sender, e);

		boolean result = (handler != null);

		if (result)
		{
			ScanFailureEventArgs args = new ScanFailureEventArgs(file, e);
			FileFailure.invoke(this, args);
			alive_ = args.getContinueRunning();
		}
		return result;
	}

	/** 
	 Raise the ProcessFile event.
	 
	 @param file The file name.
	*/
	private void OnProcessFile(String file)
	{
		ProcessFileHandler handler = (Object sender, ScanEventArgs e) -> ProcessFile.invoke(sender, e);

		if (handler != null)
		{
			ScanEventArgs args = new ScanEventArgs(file);
			handler.invoke(this, args);
			alive_ = args.getContinueRunning();
		}
	}

	/** 
	 Raise the complete file event
	 
	 @param file The file name
	*/
	private void OnCompleteFile(String file)
	{
		CompletedFileHandler handler = (Object sender, ScanEventArgs e) -> CompletedFile.invoke(sender, e);

		if (handler != null)
		{
			ScanEventArgs args = new ScanEventArgs(file);
			handler.invoke(this, args);
			alive_ = args.getContinueRunning();
		}
	}

	/** 
	 Raise the ProcessDirectory event.
	 
	 @param directory The directory name.
	 @param hasMatchingFiles Flag indicating if the directory has matching files.
	*/
	private void OnProcessDirectory(String directory, boolean hasMatchingFiles)
	{
		ProcessDirectoryHandler handler = (Object sender, DirectoryEventArgs e) -> ProcessDirectory.invoke(sender, e);

		if (handler != null)
		{
			DirectoryEventArgs args = new DirectoryEventArgs(directory, hasMatchingFiles);
			handler.invoke(this, args);
			alive_ = args.getContinueRunning();
		}
	}

	/** 
	 Scan a directory.
	 
	 @param directory The base directory to scan.
	 @param recurse True to recurse subdirectories, false to scan a single directory.
	*/
	public final void Scan(String directory, boolean recurse)
	{
		alive_ = true;
		ScanDir(directory, recurse);
	}

	private void ScanDir(String directory, boolean recurse)
	{

		try
		{
			String[] names = (new File(directory)).list(File::isFile);
			boolean hasMatch = false;
			for (int fileIndex = 0; fileIndex < names.length; ++fileIndex)
			{
				if (!fileFilter_.IsMatch(names[fileIndex]))
				{
					names[fileIndex] = null;
				}
				else
				{
					hasMatch = true;
				}
			}

			OnProcessDirectory(directory, hasMatch);

			if (alive_ && hasMatch)
			{
				for (String fileName : names)
				{
					try
					{
						if (fileName != null)
						{
							OnProcessFile(fileName);
							if (!alive_)
							{
								break;
							}
						}
					}
					catch (RuntimeException e)
					{
						if (!OnFileFailure(fileName, e))
						{
							throw e;
						}
					}
				}
			}
		}
		catch (RuntimeException e)
		{
			if (!OnDirectoryFailure(directory, e))
			{
				throw e;
			}
		}

		if (alive_ && recurse)
		{
			try
			{
				String[] names = (new File(directory)).list(File::isDirectory);
				for (String fulldir : names)
				{
					if ((directoryFilter_ == null) || (directoryFilter_.IsMatch(fulldir)))
					{
						ScanDir(fulldir, true);
						if (!alive_)
						{
							break;
						}
					}
				}
			}
			catch (RuntimeException e)
			{
				if (!OnDirectoryFailure(directory, e))
				{
					throw e;
				}
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	/** 
	 The file filter currently in use.
	*/
	private IScanFilter fileFilter_;
	/** 
	 The directory filter currently in use.
	*/
	private IScanFilter directoryFilter_;
	/** 
	 Flag indicating if scanning should continue running.
	*/
	private boolean alive_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}