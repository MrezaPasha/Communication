package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;
import java.nio.file.*;

public class TWebApiFileTransferServer implements Closeable
{
	public TWebApiFileTransferServer()
	{

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private HttpSelfHostServer _Server = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	private String ServerRootFolder;
	public final String getServerRootFolder()
	{
		return ServerRootFolder;
	}
	private void setServerRootFolder(String value)
	{
		ServerRootFolder = value;
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


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods

	public final void Start(int port)
	{
		Start(port, "C:\\");
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public void Start(int port, string serverRootFolder = "C:\\")
	public final void Start(int port, String serverRootFolder)
	{

		Stop();
		setServerRootFolder(serverRootFolder);

		this.setPort(port);
		// Create configuration
		HttpSelfHostConfiguration config = new HttpSelfHostConfiguration("http://localhost:" + getPort() + "/");

		// Set the max message size to 1M instead of the default size of 64k and also
		// set the transfer mode to 'streamed' so that don't allocate a 1M buffer but 
		// rather just have a small read buffer.

		config.MaxReceivedMessageSize = Integer.MAX_VALUE;

		config.TransferMode = TransferMode.Streamed;
		//config.Services.Add(typeof(TWebApiFileTransferServer), this);

		// Add a route
		config.Properties.AddOrUpdate("Server", this, (a, b) ->
		{
				return this;
		});
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter could not resolve the named parameters in the following line:
//ORIGINAL LINE: config.Routes.MapHttpRoute(name: "A1", routeTemplate: "StreamFiles/{action}/{id}", defaults: new { controller = "StreamFiles", id = RouteParameter.Optional });
		config.Routes.MapHttpRoute(name: "A1", routeTemplate: "StreamFiles/{action}/{id}", defaults: new {controller = "StreamFiles", id = RouteParameter.Optional});
		//config.Routes.MapHttpRoute(name: "A2", routeTemplate: "Playback/{recordFileID}", defaults: new { controller = "Playback" });
		//config.Routes.MapHttpRoute(name: "A3", routeTemplate: "Playback/{action}", defaults: new { controller = "Playback" });
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter could not resolve the named parameters in the following line:
//ORIGINAL LINE: config.Routes.MapHttpRoute(name: "A4", routeTemplate: "Test/{action}", defaults: new { controller = "Test", id = RouteParameter.Optional });
		config.Routes.MapHttpRoute(name: "A4", routeTemplate: "Test/{action}", defaults: new {controller = "Test", id = RouteParameter.Optional});
		//config.Routes.MapHttpRoute(name: "ControllersApi", routeTemplate: "{controller}/{action}/{id}", defaults: new { controller = "Test", action = "Test", id = RouteParameter.Optional });

		_Server = new HttpSelfHostServer(config);

		_Server.OpenAsync().Wait();
	}

	public final void Stop()
	{
		if (_Server != null)
		{
			_Server.CloseAsync().Wait();
			_Server = null;
		}
	}



	public void OnFileRecieved(MultipartFileData item)
	{
		File TargetFile = new File(Paths.get(getServerRootFolder()).resolve(item.Headers.ContentDisposition.FileName).toString());

		if (TargetFile.exists())
		{
			TargetFile.delete();
		}

		File F = new File(item.LocalFileName);
		F.renameTo(TargetFile.getPath());


		if (FileRecieved != null)
		{
			for (FileRecievedEventHandler listener : FileRecieved.listeners())
			{
				listener.invoke(this, TargetFile.FullName);
			}
		}
	}
	public final String ConvertFileIDToPath(int recordFileID)
	{
		if (RequestFilePathFromID != null)
		{
			for (RequestFilePathFromIDEventHandler listener : RequestFilePathFromID.listeners())
			{
				listener.invoke(this, recordFileID);
			}
		}
		return null;
	}
	public final void close() throws IOException
	{
		Stop();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<FileRecievedEventHandler> FileRecieved = new tangible.Event<FileRecievedEventHandler>();
	public tangible.Event<RequestFilePathFromIDEventHandler> RequestFilePathFromID = new tangible.Event<RequestFilePathFromIDEventHandler>();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion




}