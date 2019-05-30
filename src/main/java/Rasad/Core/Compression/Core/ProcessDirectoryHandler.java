package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Delegates
/** 
 Delegate invoked before starting to process a directory.
*/
@FunctionalInterface
public interface ProcessDirectoryHandler
{
	void invoke(Object sender, DirectoryEventArgs e);
}