package main.java.Rasad.Core.Compression.Core;


import java.io.*;

/** 
 Delegate invoked when a directory failure is detected.
 
 @param sender The source of the event
 @param e The event arguments.
*/
@FunctionalInterface
public interface DirectoryFailureHandler
{
	void invoke(Object sender, Rasad.Core.Compression.Core.ScanFailureEventArgs e);
}