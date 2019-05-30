package main.java.Rasad.Communication.Core.Client;

import Rasad.Core.Services.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import main.java.Rasad.Communication.Core.TMessageContainer;
import main.java.Rasad.Communication.Core.TMessageRecivedEventHandler;
import main.java.Rasad.Communication.Core.TSocketListener;
import main.java.Rasad.Communication.Core.TerminalState;
import main.java.Rasad.socket.SocketConnection;
import main.java.Rasad.socket.SocketListener;
import main.java.Rasad.tangible.EventHandler;
import tangible.Event;
import tangible.EventArgs;
import xyz.baddeveloper.lwsl.client.SocketClient;
import xyz.baddeveloper.lwsl.client.exceptions.ConnectException;

import java.net.ServerSocket;
import java.util.List;


public class TClientTerminal<TMSG, TIdentity extends TMSG> implements SocketListener
{

	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Fields
	public SocketClient _socClient ;
	//private Socket _socClient;
	public TSocketListener<TMSG, TIdentity> _listener;
	public TerminalState _State = TerminalState.Stopped;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion


	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Properties
	public final long getRecievedMessageCount()
	{
		if (_listener == null)
		{
			return 0;
		}
		return _listener.getRecievedMessageCount();
	}
	public final long getRecievedDataSize()
	{
		if (_listener == null)
		{
			return 0;
		}
		return _listener.getRecievedDataSize();
	}
	public final long getSendMessageCount()
	{
		if (_listener == null)
		{
			return 0;
		}
		return _listener.getSendMessageCount();
	}
	public final long getSendDataSize()
	{
		if (_listener == null)
		{
			return 0;
		}
		return _listener.getSendDataSize();
	}
	public final TerminalState getState()
	{
		return _State;
	}
	private void setState(TerminalState value)
	{
		if (_State != value)
		{
			_State = value;
			if (_State == TerminalState.Connected)
			{

				//TLogManager.Info("Connected Successfully.");
				//TODO Logger
			}
			OnConnectionStateChanged();
		}
	}
	private String ServerMachineName;
	public final String getServerMachineName()
	{
		return ServerMachineName;
	}
	public final void setServerMachineName(String value)
	{
		ServerMachineName = value;
	}
	private int ServerPort;
	public final int getServerPort()
	{
		return ServerPort;
	}
	public final void setServerPort(int value)
	{
		ServerPort = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Methods
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public virtual bool Start(string serverMachineName, int serverPort)
	public boolean Start(String serverMachineName, int serverPort) throws Exception {
		setServerMachineName(serverMachineName);
		setServerPort(serverPort);
		return StartInternal();
	}

	//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] protected virtual bool StartInternal()
	protected boolean StartInternal() throws Exception{
		if (getState() == TerminalState.Connected || getState() == TerminalState.Connecting)
		{
			return false;
		}
		setState(TerminalState.Connecting);
		try
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Start Client Socket
			//TODO Logger
			//TLogManager.Info(String.format("Connecting to %1$s:%2$s...", getServerMachineName(), String.valueOf(getServerPort())));
			//_socClient = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
			try
			{

				//TODO MRREPLACE
				_socClient = new SocketClient("127.0.0.1",getServerPort());
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							_socClient.connect();
						} catch (ConnectException e) {
							e.printStackTrace();
						}

					}
				});
				thread.start();

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			//_socClient.Connect(getServerMachineName(), getServerPort());
			//TODO logger
			//TLogManager.Info("KeepAlive: " + _socClient.GetSocketOption(SocketOptionLevel.Socket, SocketOptionName.KeepAlive).toString());
			//_socClient.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.KeepAlive, true);
			//System.Net.EndPoint tempVar = _socClient.LocalEndPoint;
			//System.Net.EndPoint tempVar2 = _socClient.LocalEndPoint;
			//TODO logger
			//TLogManager.Info("Connected via LocalEndPoint: " + String.format("%1$s:%2$s", (tempVar instanceof IPEndPoint ? (IPEndPoint)tempVar : null).Address.toString(), (tempVar2 instanceof IPEndPoint ? (IPEndPoint)tempVar2 : null).Port));
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Start Listener
			_listener = new TSocketListener<TMSG, TIdentity>();
			if (_listener.StartReciving(_socClient, (TMessageContainer<TMSG, TIdentity> obj) -> OnMessageRecvied(obj), (RuntimeException obj) -> {
				try {
					OnError(obj);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}))
			{
				setState(TerminalState.Connected);
				return true;
			}
			else
			{
				setState(TerminalState.Stopped);
				return false;
			}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
		}
		catch (RuntimeException exc)
		{
			//TODO logger
			//TLogManager.Error("Can not start client terminal ! ", exc);
			setState(TerminalState.Stopped);
			StopInternal();
			return false;
		}

	}

	private void OnMessageRecvied(TMessageContainer<TMSG, TIdentity> message)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = MessageRecived;
		if (del != null)
		{
			TMSG[] msg = message.GetMessage();
			if (msg != null)
			{
				for (TMSG item : msg)
				{

					//
					List<TMessageRecivedEventHandler<TMSG>> listeners = this.MessageRecived.listeners();
					for (TMessageRecivedEventHandler<TMSG> listener:listeners) {
						listener.invoke(_socClient,item);
					}



					//
					//del(_socClient, item);
				}
			}
		}
	}

	private void OnError(RuntimeException obj) throws IOException {
		//TODO logger
		//TLogManager.Error("ErrorCallback in ClientTerminal", obj);
		StopInternal();
	}

	private void OnDisconnected() throws IOException {
		StopInternal();
	}

	//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public virtual void Stop()
	public void Stop() throws IOException {
		StopInternal();
	}

	//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] protected virtual void StopInternal()
	protected void StopInternal() throws IOException {

		//TODO logger
		//TLogManager.Debug("TClientTerminal StopInternal");
		if (getState() == TerminalState.Stopped || getState() == TerminalState.Stopping)
		{
			return;
		}
		setState(TerminalState.Stopping);
		try
		{
			if (_listener != null)
			{
				_listener.close();
				_listener = null;
			}
		}
		finally
		{
			setState(TerminalState.Stopped);
		}
	}

	//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public bool SendMessage(TMSG[] data)
	public final boolean SendMessage(TMSG[] data)
	{
		TSocketListener<TMSG, TIdentity> Li = _listener;
		if (_socClient == null || Li == null || getState() != TerminalState.Connected)
		{
			return false;
		}
		boolean result = Li.Send(data);
		OnMessageSended();
		return result;
	}
	public void OnMessageSended()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = MessageSended;
		if (del != null)
		{

			// here
			List<EventHandler<EventArgs>> listeners = this.MessageSended.listeners();
			//List<Event<EventHandler<tangible.EventArgs>> listeners = this.MessageSended.listeners();
			for (EventHandler<EventArgs> listener:listeners) {
				listener.invoke(this,EventArgs.Empty);
			}


			//
			//del(this, tangible.EventArgs.Empty);
		}
	}
	protected void OnConnectionStateChanged()
	{

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = ConnectionStateChanged;
		if (del != null)
		{

			//
			List<EventHandler<EventArgs>> listeners = this.ConnectionStateChanged.listeners();
			//List<Event<EventHandler<tangible.EventArgs>> listeners = this.MessageSended.listeners();
			for (EventHandler<EventArgs> listener:listeners) {
				listener.invoke(this,EventArgs.Empty);
			}


			//
			//del(this, tangible.EventArgs.Empty);
		}
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion

	//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#region Events
	//public tangible.Event<TMessageRecivedEventHandler<TMSG>> MessageRecived = new tangible.Event<TMessageRecivedEventHandler<TMSG>>();
	//TODO MRREPLACE
	/*public tangible.Event<TMessageRecivedEventHandler<TMSG>> MessageRecived = new Event<TMessageRecivedEventHandler<TMSG>>();
	public Event<Rasad.auxilary.EventHandler<EventArgs>> ConnectionStateChanged = new Event<Rasad.auxilary.EventHandler<EventArgs>>();
	public Event<Rasad.auxilary.EventHandler<EventArgs>> MessageSended = new Event<EventHandler<EventArgs>>();*/


	public tangible.Event<TMessageRecivedEventHandler<TMSG>> MessageRecived = new tangible.Event<TMessageRecivedEventHandler<TMSG>>();
	public tangible.Event<EventHandler<tangible.EventArgs>> ConnectionStateChanged = new tangible.Event<EventHandler<tangible.EventArgs>>();
	public tangible.Event<EventHandler<tangible.EventArgs>> MessageSended = new tangible.Event<EventHandler<tangible.EventArgs>>();

	@Override
	public void processSocketEvent(byte[] data, SocketConnection connection) {

	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	///#endregion
}