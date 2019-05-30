package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Core.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;
import java.io.*;

// FastZip.cs
//
// Copyright 2005 John Reilly
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// Linking this library statically or dynamically with other modules is
// making a combined work based on this library.  Thus, the terms and
// conditions of the GNU General Public License cover the whole
// combination.
// 
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent
// modules, and to copy and distribute the resulting executable under
// terms of your choice, provided that you also meet, for each linked
// independent module, the terms and conditions of the license of that
// module.  An independent module is a module which is not derived from
// or based on this library.  If you modify this library, you may extend
// this exception to your version of the library, but you are not
// obligated to do so.  If you do not wish to do so, delete this
// exception statement from your version.



/** 
 FastZipEvents supports all events applicable to <see cref="FastZip">FastZip</see> operations.
*/
public class FastZipEvents
{
	/** 
	 Delegate to invoke when processing directories.
	*/
	public ProcessDirectoryHandler ProcessDirectory;

	/** 
	 Delegate to invoke when processing files.
	*/
	public ProcessFileHandler ProcessFile;

	/** 
	 Delegate to invoke during processing of files.
	*/
	public ProgressHandler Progress;

	/** 
	 Delegate to invoke when processing for a file has been completed.
	*/
	public CompletedFileHandler CompletedFile;

	/** 
	 Delegate to invoke when processing directory failures.
	*/
	public DirectoryFailureHandler DirectoryFailure;

	/** 
	 Delegate to invoke when processing file failures.
	*/
	public FileFailureHandler FileFailure;

	/** 
	 Raise the <see cref="DirectoryFailure">directory failure</see> event.
	 
	 @param directory The directory causing the failure.
	 @param e The exception for this event.
	 @return A boolean indicating if execution should continue or not.
	*/
	public final boolean OnDirectoryFailure(String directory, RuntimeException e)
	{
		boolean result = false;
		DirectoryFailureHandler handler = (Object sender, ScanFailureEventArgs e) -> DirectoryFailure.invoke(sender, e);

		if (handler != null)
		{
			ScanFailureEventArgs args = new ScanFailureEventArgs(directory, e);
			handler.invoke(this, args);
			result = args.getContinueRunning();
		}
		return result;
	}

	/** 
	 Fires the <see cref="FileFailure"> file failure handler delegate</see>.
	 
	 @param file The file causing the failure.
	 @param e The exception for this failure.
	 @return A boolean indicating if execution should continue or not.
	*/
	public final boolean OnFileFailure(String file, RuntimeException e)
	{
		FileFailureHandler handler = (Object sender, ScanFailureEventArgs e) -> FileFailure.invoke(sender, e);
		boolean result = (handler != null);

		if (result)
		{
			ScanFailureEventArgs args = new ScanFailureEventArgs(file, e);
			handler.invoke(this, args);
			result = args.getContinueRunning();
		}
		return result;
	}

	/** 
	 Fires the <see cref="ProcessFile">ProcessFile delegate</see>.
	 
	 @param file The file being processed.
	 @return A boolean indicating if execution should continue or not.
	*/
	public final boolean OnProcessFile(String file)
	{
		boolean result = true;
		ProcessFileHandler handler = (Object sender, ScanEventArgs e) -> ProcessFile.invoke(sender, e);

		if (handler != null)
		{
			ScanEventArgs args = new ScanEventArgs(file);
			handler.invoke(this, args);
			result = args.getContinueRunning();
		}
		return result;
	}

	/** 
	 Fires the <see cref="CompletedFile"/> delegate
	 
	 @param file The file whose processing has been completed.
	 @return A boolean indicating if execution should continue or not.
	*/
	public final boolean OnCompletedFile(String file)
	{
		boolean result = true;
		CompletedFileHandler handler = (Object sender, ScanEventArgs e) -> CompletedFile.invoke(sender, e);
		if (handler != null)
		{
			ScanEventArgs args = new ScanEventArgs(file);
			handler.invoke(this, args);
			result = args.getContinueRunning();
		}
		return result;
	}

	/** 
	 Fires the <see cref="ProcessDirectory">process directory</see> delegate.
	 
	 @param directory The directory being processed.
	 @param hasMatchingFiles Flag indicating if the directory has matching files as determined by the current filter.
	 @return A <see cref="bool"/> of true if the operation should continue; false otherwise.
	*/
	public final boolean OnProcessDirectory(String directory, boolean hasMatchingFiles)
	{
		boolean result = true;
		ProcessDirectoryHandler handler = (Object sender, DirectoryEventArgs e) -> ProcessDirectory.invoke(sender, e);
		if (handler != null)
		{
			DirectoryEventArgs args = new DirectoryEventArgs(directory, hasMatchingFiles);
			handler.invoke(this, args);
			result = args.getContinueRunning();
		}
		return result;
	}

	/** 
	 The minimum timespan between <see cref="Progress"/> events.
	 
	 <value>The minimum period of time between <see cref="Progress"/> events.</value>
	 {@link Progress}
	 The default interval is three seconds.
	*/
	public final TimeSpan getProgressInterval()
	{
		return progressInterval_;
	}
	public final void setProgressInterval(TimeSpan value)
	{
		progressInterval_ = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private TimeSpan progressInterval_ = TimeSpan.FromSeconds(3);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}