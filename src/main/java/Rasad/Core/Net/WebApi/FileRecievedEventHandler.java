package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;
import java.nio.file.*;

@FunctionalInterface
public interface FileRecievedEventHandler
{
	void invoke(TWebApiFileTransferServer sender, String filePath);
}