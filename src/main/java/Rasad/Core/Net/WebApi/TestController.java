package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;
import java.nio.file.*;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Controllers
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region TestController

public class TestController extends ApiController
{
	public final TWebApiFileTransferServer getServer()
	{
		return (TWebApiFileTransferServer)this.Configuration.Properties["Server"];
	}

	//[ActionName("Test")]
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [HttpGet] public string Test()
	public final String Test()
	{
		return "Test Completed by mehdi nazari !!!";
	}
}