package main.java.Rasad.Core.Compression.Core;


import java.io.*;

/** 
 Delegate invoked when a file has been completely processed.
 
 @param sender The source of the event
 @param e The event arguments.
*/
@FunctionalInterface
public interface CompletedFileHandler
{
	void invoke(Object sender, Rasad.Core.Compression.Core.ScanEventArgs e);
}