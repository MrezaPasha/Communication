package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;

public class TWebApiFileTransferClient
{
	public TWebApiFileTransferClient(String serverIp, int serverPort)
	{
		setServerBaseAddress(new Uri(String.format("http://%1$s:%2$s/StreamFiles/", serverIp, serverPort)));
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	private Uri ServerBaseAddress;
	public final Uri getServerBaseAddress()
	{
		return ServerBaseAddress;
	}
	private void setServerBaseAddress(Uri value)
	{
		ServerBaseAddress = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods
	/** 
	 Upload file
	 
	 @return Awaitable task object
	*/
	public final TUploadAction UploadFile(String filePath)
	{
		return new TUploadAction(this, filePath);
	}

	public final TDownloadAction DownloadFile(String serverfileName, String clientFileName)
	{
		return new TDownloadAction(this, serverfileName, clientFileName);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}