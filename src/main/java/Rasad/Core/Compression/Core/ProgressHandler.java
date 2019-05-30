package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;

/** 
 Delegate invoked during processing of a file or directory
 
 @param sender The source of the event
 @param e The event arguments.
*/
@FunctionalInterface
public interface ProgressHandler
{
	void invoke(Object sender, ProgressEventArgs e);
}