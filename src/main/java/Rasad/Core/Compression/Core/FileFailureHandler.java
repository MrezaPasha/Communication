package main.java.Rasad.Core.Compression.Core;

import java.io.*;

/** 
 Delegate invoked when a file failure is detected.
 
 @param sender The source of the event
 @param e The event arguments.
*/
@FunctionalInterface
public interface FileFailureHandler
{
	void invoke(Object sender, Rasad.Core.Compression.Core.ScanFailureEventArgs e);
}