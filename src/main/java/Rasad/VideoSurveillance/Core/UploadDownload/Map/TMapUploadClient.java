package Rasad.VideoSurveillance.Core.UploadDownload.Map;

import Rasad.Core.*;
import Rasad.VideoSurveillance.Core.UploadDownload.Common.*;
import Rasad.VideoSurveillance.Core.UploadDownload.Map.Common.*;
import Rasad.VideoSurveillance.Core.*;
import java.util.*;
import java.io.*;

public class TMapUploadClient implements Closeable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Variables
	private boolean disposed = false;

	private String remoteIp;
	private int remotePort;
	private IPEndPoint remoteEndPoint;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private void Log(String s)
	{
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor/Dtor
	public TMapUploadClient(String remoteIp, int remotePort)
	{
		this.remoteIp = remoteIp;
		this.remotePort = remotePort;
		remoteEndPoint = new IPEndPoint(IPAddress.Parse(remoteIp), remotePort);
	}

	protected void finalize() throws Throwable
	{
		Dispose(false);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties
	public final String getRemoteIP()
	{
		return this.remoteIp;
	}

	public final int getRemotePort()
	{
		return this.remotePort;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods
	public final void close() throws IOException
	{
		Dispose(true);
		GC.SuppressFinalize(this);
	}

	//public UpdateConfig GetFileList()
	//{
	//    TcpClient tcpClient = new TcpClient();
	//    tcpClient.ReceiveTimeout = 10000;
	//    tcpClient.SendTimeout = 10000;
	//    tcpClient.Connect(remoteEndPoint);

	//    UpdateRequestMessage request = new UpdateRequestMessage();
	//    request.ClientIdentifier = this.Identifier;
	//    request.RequestCommand = (int)UpdateRequestCommand.GetUpdateConfig;

	//    var ns = tcpClient.GetStream();
	//    byte[] requestData = UpdateUtilities.JsonSerialize(request);
	//    UpdateUtilities.WriteNetworkStream(ns, requestData, true);

	//    // read response
	//    byte[] data = UpdateUtilities.ReadNetworkStream(ns, 5 * 1024 * 1024, true);
	//    if (data.Length > 0)
	//    {
	//        UpdateResponseMessage response = UpdateUtilities.JsonDeserialize<UpdateResponseMessage>(data);
	//        if (response == null)
	//            throw new Exception("Invalid response received");
	//        else
	//            return response.UpdateConfig;
	//    }
	//    else
	//        throw new Exception("No response received");
	//}

	//public byte[] DownloadFile(UpdateSetDetail fileItem)
	//{
	//    TcpClient tcpClient = new TcpClient();
	//    tcpClient.ReceiveTimeout = 10000;
	//    tcpClient.SendTimeout = 10000;
	//    tcpClient.Connect(remoteEndPoint);

	//    UpdateRequestMessage request = new UpdateRequestMessage();
	//    request.ClientIdentifier = this.Identifier;
	//    request.RequestCommand = (int)UpdateRequestCommand.DownloadFile;
	//    request.FilePath = fileItem.FilePath;

	//    var ns = tcpClient.GetStream();
	//    byte[] requestData = UpdateUtilities.JsonSerialize(request);
	//    UpdateUtilities.WriteNetworkStream(ns, requestData, true);

	//    // read response
	//    byte[] data = UpdateUtilities.ReadNetworkStream(ns, 0, true);
	//    if (data.Length == 0)
	//        throw new Exception("No data received");
	//    if (data.Length != fileItem.Size)
	//        throw new Exception("Received data size not match");
	//    if (!UpdateUtilities.ByteArraysEqual(UpdateUtilities.ComputeFileMD5Hash(data), Convert.FromBase64String(fileItem.HashBase64)))
	//        throw new Exception("Received data hash not match");
	//    return data;
	//}


	public final void UploadFile(TMapUploadInfo uploadInfo)
	{
		UploadFile(uploadInfo, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public void UploadFile(TMapUploadInfo uploadInfo, Action<int> progressPercent = null)
	public final void UploadFile(TMapUploadInfo uploadInfo, tangible.Action1Param<Integer> progressPercent)
	{
		if (uploadInfo == null)
		{
			throw new NullPointerException("uploadInfo");
		}

		TcpClient tcpClient = new TcpClient();
		tcpClient.ReceiveTimeout = 10000;
		tcpClient.SendTimeout = 10000;
		tcpClient.Connect(remoteEndPoint);
		TMapFileUploadRequestMessage requestMessage = new TMapFileUploadRequestMessage();
		requestMessage.setCommand(MapUploadCommand.UploadMapFile);
		requestMessage.setFilenameFullPath(uploadInfo.getFilenameFullPath());
		requestMessage.setFilenameRelativePath(uploadInfo.getFilenameRelativePath());
		requestMessage.setFileSize((new File(uploadInfo.getFilenameFullPath())).length());
		requestMessage.setMapAccessID(uploadInfo.getMapAccessID());
		requestMessage.setMapID(uploadInfo.getMapID());
		requestMessage.setX(uploadInfo.getX());
		requestMessage.setY(uploadInfo.getY());
		requestMessage.setZ(uploadInfo.getZ());

		System.Net.Sockets.NetworkStream ns = tcpClient.GetStream();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] requestData = TSerializationHelper.JsonSerializeUnicode(requestMessage);
		byte[] requestData = TSerializationHelper.JsonSerializeUnicode(requestMessage);
		TUploadDownloadUtilities.WriteNetworkStream(ns, requestData, true);

		// now go for file data upload
		try (FileStream sourceStream = new FileStream(uploadInfo.getFilenameFullPath(), FileMode.Open))
		{
			TUploadDownloadUtilities.WriteNetworkStreamFromSource(ns, sourceStream, true, progressPercent);
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Methods
	protected void Dispose(boolean disposing)
	{
		if (disposed)
		{
			return;
		}

		if (disposing)
		{
			disposed = true;
			// free managed resources
		}
		// free native resources if there are any.
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}