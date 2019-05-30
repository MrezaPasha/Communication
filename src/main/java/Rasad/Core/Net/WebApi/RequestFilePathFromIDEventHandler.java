package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;
import java.nio.file.*;

@FunctionalInterface
public interface RequestFilePathFromIDEventHandler
{
	String invoke(TWebApiFileTransferServer sender, int fileID);
}