package main.java.Rasad.Communication.Core;

import com.sun.jdi.event.Event;
import main.java.Rasad.Communication.Core.Client.TClientTerminal;
import main.java.Rasad.Communication.Core.Client.TClientTerminalSafe;
import main.java.Rasad.tangible.EventHandler;
import tangible.EventArgs;
import xyz.baddeveloper.lwsl.client.SocketClient;
import xyz.baddeveloper.lwsl.server.SocketServer;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.List;

//using Rasad.Core.Services.NotificationService;
//using Rasad.Core.Services.NotificationService.Public;
//using Rasad.Core.Services.NotificationService.Structs;

public class TNotificationServiceSocket<TMSG, TIdentity extends TMSG> implements INotificationService<TMSG>
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private TClientTerminalSafe<TMSG, TIdentity> _Terminal;

	public TNotificationServiceSocket() {
		ConnectedStateChanged = new tangible.Event<EventHandler<tangible.EventArgs>>();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Static Methods


	//TODO MRREPLACE :
	public static java.lang.Iterable<InetAddress> DoGetHostAddresses(String hostname)
	{
		//TODO : MRCOMMENT : orm framework
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		/*return Dns.GetHostAddresses(hostname).Where(t -> t.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork);*/
		return null;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	public final long getRecievedMessageCount()
	{
		if (_Terminal == null)
		{
			return 0;
		}
		return _Terminal.getRecievedMessageCount();
	}
	public final long getRecievedDataSize()
	{
		if (_Terminal == null)
		{
			return 0;
		}
		return _Terminal.getRecievedDataSize();
	}
	public final long getSendMessageCount()
	{
		if (_Terminal == null)
		{
			return 0;
		}
		return _Terminal.getSendMessageCount();
	}
	public final long getSendDataSize()
	{
		if (_Terminal == null)
		{
			return 0;
		}
		return _Terminal.getSendDataSize();
	}

	public final TerminalState getState()
	{
		if (_Terminal == null)
		{
			return TerminalState.Stopped;
		}
		return _Terminal.getState();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void Start(string serverMachineName, int serverPort)
	public final void Start(String serverMachineName, int serverPort) throws Exception {
		Stop();
		_Terminal = new TClientTerminalSafe<TMSG, TIdentity>();
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		_Terminal.ConnectionStateChanged.addListener("_Terminal_ConnectionStateChanged",(x,y)->_Terminal_ConnectionStateChanged(this,EventArgs.Empty));
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		//_Terminal.MessageRecived += _Terminal_MessageRecived(_Terminal,);
		//TODO MRCOMMENT : must uncomment
		//_Terminal.MessageRecived.addListener("_Terminal_MessageRecived",this::_Terminal_MessageRecived);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		//_Terminal.OnMessageSended() += _Terminal_MessageSended();
		_Terminal.MessageSended.addListener("_Terminal_MessageSended",this::_Terminal_MessageSended);
		_Terminal.Start(serverMachineName, serverPort);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void Stop()
	public final void Stop() throws IOException {
		if (_Terminal != null)
		{
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			_Terminal.ConnectionStateChanged.addListener("_Terminal_ConnectionStateChanged",this::_Terminal_ConnectionStateChanged);

			//
			//
			_Terminal.ConnectionStateChanged.addListener("_Terminal_ConnectionStateChanged",this::_Terminal_ConnectionStateChanged);
			_Terminal.ConnectionStateChanged.addListener("_Terminal_ConnectionStateChanged",this::_Terminal_ConnectionStateChanged);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			//_Terminal.MessageRecived.addListener(_Terminal_MessageRecived()); -= _Terminal_MessageRecived();
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			_Terminal.MessageSended.addListener("_Terminal_MessageSended",(x,y) -> _Terminal_MessageRecived(null, (TMSG) EventArgs.Empty));
			//_Terminal.MessageSended.addListener(_Terminal_MessageRecived(this._Terminal._socClient,null));

			_Terminal.Stop();
			_Terminal = null;
		}
	}
	public final boolean SendMessage(TMSG... dataItems)
	{
		TClientTerminalSafe<TMSG, TIdentity> terminal = _Terminal;
		if (terminal != null)
		{
			try
			{
				try
				{
					return terminal.SendMessage(dataItems);
				}
				catch (RuntimeException exc1)
				{
					//TODO MRCOMMENT : must uncomment
/*
					throw new RuntimeException("Can not send messages : " + tangible.StringHelper.join("\r\n", dataItems.Select(t -> t.getClass().getSimpleName())), exc1);
*/
				}
			}
			catch (RuntimeException exc)
			{
				//TODO MRCOMMENt : logger

				//TLogManager.Error("Send Error from Client", exc);
				return false;
			}
		}
		else
		{
			//TODO MRCOMMENt : logger

			//TLogManager.Warn("Notification service Not Exist ! Can not Notify : " + tangible.StringHelper.join(" | ", dataItems.Select(d -> d.toString()).ToArray()));
			return false;
		}
		return false;

	}

	//TODO MRREPLACED
	private void _Terminal_MessageRecived(SocketServer socket, TMSG message)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = NotificationRecieved;
		if (del != null)
		{
			try
			{
				 List<NotificationMessageEventHandler<TMSG>> listeners = this.NotificationRecieved.listeners();
				for (NotificationMessageEventHandler<TMSG> listener:listeners) {
					listener.invoke(message);

				}



		}
			catch (RuntimeException exc)
			{
				//TODO MRCOMMENt : logger
				//TLogManager.Error("Client Socket Error on Splitting !", exc);
			}
		}
	}

	private void _Terminal_ConnectionStateChanged(Object sender, tangible.EventArgs e)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = ConnectedStateChanged;

		///
		List<EventHandler<tangible.EventArgs>> listeners = this.ConnectedStateChanged.listeners();
		for (EventHandler<tangible.EventArgs> listener:listeners) {
			listener.invoke(sender,e);
		}




		//

		if (del != null)
		{
			for (EventHandler<tangible.EventArgs> listener:listeners) {
				listener.invoke(sender,EventArgs.Empty);
			}
		}
	}

	private void _Terminal_MessageSended(Object sender, tangible.EventArgs e)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = MessageSended;
		if (del != null)
		{

			///
			List<EventHandler<tangible.EventArgs>> listeners = this.MessageSended.listeners();
			for (EventHandler<tangible.EventArgs> listener:listeners) {
				listener.invoke(sender,e);
			}



			///
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<NotificationMessageEventHandler<TMSG>> NotificationRecieved = new tangible.Event<NotificationMessageEventHandler<TMSG>>();
	public tangible.Event<EventHandler<tangible.EventArgs>> ConnectedStateChanged = new tangible.Event<EventHandler<EventArgs>>();
	public tangible.Event<EventHandler<tangible.EventArgs>> MessageSended = new tangible.Event<EventHandler<EventArgs>>();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


}