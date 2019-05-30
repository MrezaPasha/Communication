package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;

/** 
 Event arguments during processing of a single file or directory.
*/
public class ProgressEventArgs extends tangible.EventArgs
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a new instance of <see cref="ScanEventArgs"/>
	 
	 @param name The file or directory name if known.
	 @param processed The number of bytes processed so far
	 @param target The total number of bytes to process, 0 if not known
	*/
	public ProgressEventArgs(String name, long processed, long target)
	{
		name_ = name;
		processed_ = processed;
		target_ = target;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 The name for this event if known.
	*/
	public final String getName()
	{
		return name_;
	}

	/** 
	 Get set a value indicating wether scanning should continue or not.
	*/
	public final boolean getContinueRunning()
	{
		return continueRunning_;
	}
	public final void setContinueRunning(boolean value)
	{
		continueRunning_ = value;
	}

	/** 
	 Get a percentage representing how much of the <see cref="Target"></see> has been processed
	 
	 <value>0.0 to 100.0 percent; 0 if target is not known.</value>
	*/
	public final float getPercentComplete()
	{
		float result;
		if (target_ <= 0)
		{
			result = 0;
		}
		else
		{
			result = ((float)processed_ / (float)target_) * 100.0f;
		}
		return result;
	}

	/** 
	 The number of bytes processed so far
	*/
	public final long getProcessed()
	{
		return processed_;
	}

	/** 
	 The number of bytes to process.
	 
	 Target may be 0 or negative if the value isnt known.
	*/
	public final long getTarget()
	{
		return target_;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private String name_;
	private long processed_;
	private long target_;
	private boolean continueRunning_ = true;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}