package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;

/** 
 Arguments passed when scan failures are detected.
*/
public class ScanFailureEventArgs extends tangible.EventArgs
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a new instance of <see cref="ScanFailureEventArgs"></see>
	 
	 @param name The name to apply.
	 @param e The exception to use.
	*/
	public ScanFailureEventArgs(String name, RuntimeException e)
	{
		name_ = name;
		exception_ = e;
		continueRunning_ = true;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 The applicable name.
	*/
	public final String getName()
	{
		return name_;
	}

	/** 
	 The applicable exception.
	*/
	public final RuntimeException getException()
	{
		return exception_;
	}

	/** 
	 Get / set a value indicating wether scanning should continue.
	*/
	public final boolean getContinueRunning()
	{
		return continueRunning_;
	}
	public final void setContinueRunning(boolean value)
	{
		continueRunning_ = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private String name_;
	private RuntimeException exception_;
	private boolean continueRunning_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}