package Rasad.VideoSurveillance.Core.UploadDownload.Map;

import Rasad.Core.*;
import Rasad.VideoSurveillance.Core.RVSSystem.*;
import Rasad.VideoSurveillance.Core.UploadDownload.Common.*;
import Rasad.VideoSurveillance.Core.UploadDownload.Map.Common.*;
import Rasad.VideoSurveillance.Core.*;
import java.io.*;

public class TMapUploadServer
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Variables
	private boolean disposed = false;
	private TcpListener tcpListener = null;
	private Object lockObj = new Object();

	private boolean stopFlagInternal = false;
	private Thread threadListener = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private void Log(String s)
	{
	}

	private void EnsureStopped()
	{
		if (!getStopped())
		{
			throw new RuntimeException("Server must be stopped to do this operation.");
		}
	}

	private void ProcessRequestMessage(TcpClient client, TMapFileUploadRequestMessage requestMessage)
	{
		try
		{
			switch (requestMessage.getCommand())
			{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region UploadMapFile
				case UploadMapFile:
				{
						String localFilePath = Path.Combine(getMapsRootPth(), String.valueOf(requestMessage.getMapAccessID()), requestMessage.getFilenameRelativePath());
						String dir = (new File(localFilePath)).getParent();
						(new File(dir)).mkdirs();

						// check for disk free space before save
						boolean freeSpaceNotAvailable = false;
						try
						{
							DriveInfo logDriveInfo = new DriveInfo((new File(dir)).Root.getPath());
							long minDiskFreeSpaceBytes = RVSConstants.RVSRootDriveMinDiskFreeSpaceMB * 1024 * 1024;
							long availableFreeSpace = logDriveInfo.AvailableFreeSpace;
							long requiredFreeSpace = minDiskFreeSpaceBytes + requestMessage.getFileSize();
							freeSpaceNotAvailable = availableFreeSpace <= requiredFreeSpace;
							if (freeSpaceNotAvailable)
							{
								class AnonymousType
								{
									public long AvailableFreeSpace;
									public long RequiredFreeSpace;

									public AnonymousType(long _AvailableFreeSpace, long _RequiredFreeSpace)
									{
										AvailableFreeSpace = _AvailableFreeSpace;
										RequiredFreeSpace = _RequiredFreeSpace;
									}
								}
								TLogManager.Warn("Free space not available for map upload", AnonymousType(availableFreeSpace, requiredFreeSpace));
							}
						}
						catch (RuntimeException exp)
						{
							TLogManager.Error("Error checking disk free space", exp);
						}

						if (!freeSpaceNotAvailable)
						{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
							FileStream fs = new FileStream(localFilePath, FileMode.OpenOrCreate);
							boolean completeleyReceived = false;
							try
							{
								NetworkStream ns = client.GetStream();

								completeleyReceived = TUploadDownloadUtilities.ReadNetworkStreamAndSave(ns, 1 * 1024 * 1024 * 1024, true, fs);
							}
							finally
							{
								fs.Dispose();
								if (!completeleyReceived)
								{
									class AnonymousType2
									{
										public java.util.UUID MapID;
										public long AccessID;

										public AnonymousType2(java.util.UUID _MapID, long _AccessID)
										{
											MapID = _MapID;
											AccessID = _AccessID;
										}
									}
									TLogManager.Warn("Map file did not completely received", AnonymousType2(requestMessage.getMapID(), requestMessage.getMapAccessID()));
									(new File(localFilePath)).delete();
								}
							}
						}
				}
					break;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region DownloadMapFile
				case DownloadMapFile:
				{
						TLogManager.Info("Download map file command - not implemented yet");
				}
					break;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#region Other
				default:
				{
						TLogManager.Error("Invalid command received in map uploader: " + requestMessage.getCommand().toString());
				}
					break;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
					///#endregion
			}
		}
		finally
		{
			client.Close();
		}

		// sample code:
		//switch ((UpdateRequestCommand)requestMessage.RequestCommand)
		//{
		//    #region DownloadFile
		//    case UpdateRequestCommand.DownloadFile:
		//        {
		//            UpdateSetDetail requestedFile = updateComponent.UpdateConfig.UpdateSets
		//                .SelectMany(s => s.Details).ToList()
		//                .FirstOrDefault(s => s.FilePath == requestMessage.FilePath);
		//            if (requestedFile == null)
		//            {
		//                Log("A non-existing update file requested: " + requestMessage.FilePath);
		//            }
		//            else
		//            {
		//                NetworkStream ns = client.GetStream();
		//                byte[] buffer = new byte[1024];
		//                byte[] sizeBytes = UpdateUtilities.GetBytes((int)requestedFile.Size);
		//                ns.Write(sizeBytes, 0, sizeBytes.Length);
		//                using (var fsream = File.OpenRead(requestedFile.FullFilePath))
		//                {
		//                    while (true)
		//                    {
		//                        int bytesRead = fsream.Read(buffer, 0, buffer.Length);
		//                        if (bytesRead > 0)
		//                        {
		//                            ns.Write(buffer, 0, bytesRead);
		//                        }
		//                        else
		//                            break;
		//                    }
		//                    ns.Flush();
		//                    ns.Close();
		//                    client.Close();
		//                }
		//            }
		//        }
		//        break;
		//    #endregion
		//}
	}

	private void DoUploadServerListener()
	{
		try
		{
			if (tcpListener == null)
			{
				throw new RuntimeException("No listener");
			}
			while (!stopFlagInternal)
			{
				if (tcpListener.Pending())
				{
					TcpClient client = null;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] streamData = null;
					byte[] streamData = null;
					NetworkStream clientStream = null;
					try
					{
						client = tcpListener.AcceptTcpClient();
						clientStream = client.GetStream();
						streamData = TUploadDownloadUtilities.ReadNetworkStream(clientStream, 5 * 1024 * 1024, true); // max 5 MB
					}
					catch (java.lang.Exception e)
					{
					}

					if (streamData != null && streamData.length > 0)
					{
						TMapFileUploadRequestMessage requestMessage = null;
						try
						{
							requestMessage = TSerializationHelper.<TMapFileUploadRequestMessage>JsonDeserializeUnicode(streamData);
						}
						catch (java.lang.Exception e2)
						{
						}
						if (requestMessage != null)
						{
							// process this request
							Thread threadMessageProcessor = new Thread()
							{
							void run()
							{
									try
									{
										ProcessRequestMessage(client, requestMessage);
									}
									catch (RuntimeException exp)
									{
										Log("Error processing request: " + exp.toString());
									}
							}
							};
							threadMessageProcessor.start();
						}
					}
				}

				Thread.sleep(5);
			}
		}
		finally
		{
			threadListener = null;
			try
			{
				tcpListener.Stop();
			}
			catch (java.lang.Exception e3)
			{
			}
			tcpListener = null;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor/Dtor
	public TMapUploadServer(int port)
	{
		this.setPort(port);
		setMapsRootPth(TRVSSystemHelper.getMapsStorageFolder());
		try
		{
			if (!(new File(getMapsRootPth())).isDirectory())
			{
				(new File(getMapsRootPth())).mkdirs();
			}
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error preparing maps directory", exp);
		}
	}

	protected void finalize() throws Throwable
	{
		Dispose(false);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties
	private String MapsRootPth;
	public final String getMapsRootPth()
	{
		return MapsRootPth;
	}
	public final void setMapsRootPth(String value)
	{
		MapsRootPth = value;
	}

	private int Port;
	public final int getPort()
	{
		return Port;
	}
	private void setPort(int value)
	{
		Port = value;
	}

	public final boolean getStopped()
	{
		return tcpListener == null;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods
	public final void Dispose()
	{
		Dispose(true);
		GC.SuppressFinalize(this);
	}

	public final void Start()
	{
		synchronized (lockObj)
		{
			if (tcpListener != null)
			{
				return;
			}

			tcpListener = new TcpListener(IPAddress.Any, getPort());
			tcpListener.ExclusiveAddressUse = true;
			tcpListener.Start();

			stopFlagInternal = false;

			threadListener = new Thread()
			{
			void run()
			{
					DoUploadServerListener();
			}
			};
			threadListener.IsBackground = true;
			threadListener.Priority = ThreadPriority.Normal;
			threadListener.start();
		}
	}

	public final void Stop()
	{
		stopFlagInternal = true;
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
			Stop();
			disposed = true;
			// free managed resources
		}
		// free native resources if there are any.
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}