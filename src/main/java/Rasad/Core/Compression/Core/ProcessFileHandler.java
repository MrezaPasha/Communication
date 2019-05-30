package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;

/** 
 Delegate invoked before starting to process a file.
 
 @param sender The source of the event
 @param e The event arguments.
*/
@FunctionalInterface
public interface ProcessFileHandler
{
	void invoke(Object sender, ScanEventArgs e);
}