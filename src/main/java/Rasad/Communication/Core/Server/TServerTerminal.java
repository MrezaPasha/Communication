package main.java.Rasad.Communication.Core.Server;

import Rasad.Core.Services.*;
import Rasad.Core.*;
import main.java.Rasad.Communication.Core.*;
import main.java.Rasad.Communication.Core.Server.*;
import main.java.Rasad.Core.Common.TCommonUtilities;
import main.java.Rasad._core.TimeSpan;
import main.java.Rasad.tangible.EventHandler;
import tangible.EventArgs;
import xyz.baddeveloper.lwsl.client.SocketClient;
import xyz.baddeveloper.lwsl.server.SocketServer;

import java.net.Socket;
import java.util.*;

//using Rasad.Core.Services.NotificationService;
//using Rasad.Core.Services.NotificationService.Public;


public class TServerTerminal<TMSG, TIdentity extends TMSG>
{
	public TServerTerminal()
	{
		List<TConnectedClient<TMSG, TIdentity>>  ConnectedClients = new ArrayList<>();
		setClients(ConnectedClients);
		timerCleanupCachedMessages = new Timer(String.valueOf(TimeSpan.FromMinutes(1).TotalMilliseconds()));
		//timerCleanupCachedMessages.AutoReset = false;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		//timerCleanupCachedMessages.Elapsed += timerCleanupCachedMessages_Elapsed;
		((Timer) timerCleanupCachedMessages).schedule(new TimerTask() {
			@Override
			public void run() {
				timerCleanupCachedMessages_Elapsed(this);

			}
		},0);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private SocketServer _Socket;
	private TerminalState _State = TerminalState.values()[0];
	private Timer timerCleanupCachedMessages;

	private HashMap<UUID, TEntityCacheInformation<TMSG, TIdentity>> cachedMessages = new HashMap<UUID, TEntityCacheInformation<TMSG, TIdentity>>();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	private List<TConnectedClient<TMSG, TIdentity>> Clients;
	public final List<TConnectedClient<TMSG, TIdentity>> getClients()
	{
		return Clients;
	}
	private void setClients(List<TConnectedClient<TMSG, TIdentity>> value)
	{
		Clients = value;
	}

	public final TerminalState getState()
	{
		return _State;
	}
	public final void setState(TerminalState value)
	{
		if (_State != value)
		{
			_State = value;
			OnConnectionStateChanged();
		}
	}

	private String EndPoint;
	public final String getEndPoint()
	{
		return EndPoint;
	}
	private void setEndPoint(String value)
	{
		EndPoint = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods

	private void CleanupNow()
	{
		int shortInterval = 1000;
		/*if (timerCleanupCachedMessages.Interval != shortInterval)
		{
			timerCleanupCachedMessages.Interval = shortInterval;
			timerCleanupCachedMessages.Stop();
			timerCleanupCachedMessages.Start();
		}*/
	}

	private void timerCleanupCachedMessages_Elapsed(Object sender)
	{
		try
		{
			//ArrayList<TEntityCacheInformation<TMSG, TIdentity>> items;
			ArrayList<TEntityCacheInformation<TMSG, TIdentity>> items;
			boolean anyMessagesCleanuped = false;
			synchronized (cachedMessages)
			{
				//new ArrayList<Element>(Arrays.asList(array));
				//items = cachedMessages.values().toArray();
				 items = new ArrayList(Arrays.asList(cachedMessages.values()));

			}
			for (TEntityCacheInformation<TMSG, TIdentity> item : items)
			{
				try
				{
					synchronized (item.getSyncObject())
					{
						anyMessagesCleanuped = anyMessagesCleanuped || item.CleanUpOldMessages();
					}
				}
				catch (RuntimeException exp)
				{
					//TODO MRCOMMENT : must uncomment
					//TLogManager.Error("Error caches messages cleanup", exp);
				}
			}
			synchronized (cachedMessages)
			{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
				//TODO MRCOMMENT : must uncomment
				//ArrayList<Object> expiredItems = cachedMessages.Where(s -> s.Value.IsExpired).Select(s -> s.Value).ToList();
				ArrayList<Object> expiredItems = null;
				if (!expiredItems.isEmpty())
				{
					class AnonymousType
					{
						public int Count;
						public String Items;

						public AnonymousType(int _Count, String _Items)
						{
							Count = _Count;
							Items = _Items;
						}
					}
					//TODO MRCOMMENT : must uncomment both lines
					//TLogManager.Info("Expired disconnected clients", AnonymousType(expiredItems.size(), tangible.StringHelper.join(", ", expiredItems.Select(s -> s.EntityKey.toString()).ToArray())));
					//expiredItems.forEach(s -> cachedMessages.remove(s.EntityKey));
					anyMessagesCleanuped = true;
				}
			}
			if (anyMessagesCleanuped)
			{
				TCommonUtilities.MinimizeProcessMemorySafe(true);
			}
		}
		finally
		{
			//timerCleanupCachedMessages.Interval = TimeSpan.FromMinutes(1).TotalMilliseconds();
			timerCleanupCachedMessages.schedule(null,0, (long) TimeSpan.FromMinutes(1).TotalMilliseconds());
			timerCleanupCachedMessages.purge();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public virtual bool Start()
	public boolean Start()
	{
		//TODO MRCOMMENT : must uncomment
		setEndPoint("comment"/*new IPEndPoint(IPAddress.Any, TCommunicationConfig<TMSG, TIdentity>.CommunicationPortNumber)*/);
		return StartInternal();
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] protected virtual bool StartInternal()
	protected boolean StartInternal()
	{
		if (getState() == TerminalState.Connecting || getState() == TerminalState.Connected)
		{
			return false;
		}
		setState(TerminalState.Connecting);
		try
		{
			synchronized (cachedMessages)
			{
				cachedMessages.clear();
			}

			//_Socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
			//bind to local IP Address...
			//if ip address is allready being used write to log
			//_Socket.Bind(getEndPoint());

			//start listening...
			_Socket.start();

			//timerCleanupCachedMessages.Start();

			setState(TerminalState.Connected);
			return true;
		}
		catch (RuntimeException ex)
		{
			//TODO MRCOMMENT : must uncomment
			//TLogManager.Error(ex.toString(), String.format("Can't connect to port %1$s!", getEndPoint().Port));
			StopInternal();
			return false;
		}
		finally
		{
			StartAcceptNewConnections();
		}
	}

	protected void StartAcceptNewConnections()
	{
		if (getState() == TerminalState.Connected)
		{
			// create the call back for any client connections...
			//_Socket.BeginAccept((System.IAsyncResult ar) -> OnClientConnection(ar), _Socket);
			_Socket.addConnectEvent(this::OnClientConnection);
		}
	}

	public void Stop()
	{
		StopInternal();
	}

	protected void StopInternal()
	{
		if (getState() == TerminalState.Stopping || getState() == TerminalState.Stopped)
		{
			return;
		}
		setState(TerminalState.Stopping);

		timerCleanupCachedMessages.purge();
		if (_Socket != null)
		{
			try
			{
				TConnectedClient<TMSG, TIdentity>[] clients = null;
				synchronized (getClients())
				{
					clients = (TConnectedClient<TMSG, TIdentity>[]) getClients().toArray();
				}

				for (TConnectedClient<TMSG, TIdentity> connectedClient : clients)
				{
					TryRemoveClient(connectedClient);
				}
			}
			finally
			{
				if (_Socket.isRunning())
				{
					//_Socket.Disconnect(false);
				}
				_Socket.stop();
				_Socket = null;
			}
		}
		setState(TerminalState.Stopped);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] private void OnClientConnection(IAsyncResult asyn)
	private void OnClientConnection(Socket socket)
	{
		if (getState() == TerminalState.Stopping || getState() == TerminalState.Stopped || getState() == TerminalState.Connecting)
		{
			return;
		}

		try
		{
			SocketClient clientSocket = new SocketClient(getEndPoint(),socket.getPort());
			TConnectedClient<TMSG, TIdentity> connectedClient = new TConnectedClient<TMSG, TIdentity>(clientSocket);
			if (connectedClient.StartListen((TConnectedClient<TMSG, TIdentity> arg1, TMessageContainer<TMSG, TIdentity> arg2) -> OnMessageRecived(arg1, arg2), (TConnectedClient<TMSG, TIdentity> arg1, RuntimeException arg2) -> OnClientError(arg1, arg2), (TConnectedClient<TMSG, TIdentity> arg1, ArrayList<TMessageWithTime<TMSG, TIdentity>> arg2) -> OnCollectUnsentMessages(arg1, arg2)))
			{
				synchronized (getClients())
				{
					//getClients().Add(connectedClient);
					getClients().add(connectedClient);
				}
				OnClientConnected(connectedClient);
			}
			else
			{
				connectedClient.close();
			}
		}
		catch (Exception odex)
		{
			//TODO MRCOMMENT : must uncomment
			//TLogManager.Error(odex.toString(), "OnClientConnection: Socket has been closed");
		}

		finally
		{
			StartAcceptNewConnections();
		}
	}

	private void OnClientError(TConnectedClient<TMSG, TIdentity> client, RuntimeException error)
	{
		if (getState() == TerminalState.Connected)
		{
			TryRemoveClient(client);
		}
	}


	private void TryRemoveClient(TConnectedClient<TMSG, TIdentity> client)
	{
		synchronized (getClients())
		{
			//if (getClients().Contains(client))
			if (getClients().contains(client))
			{
				getClients().remove(client);
			}
		}
		synchronized (cachedMessages)
		{
			if (client.getIsSetRemotePartyIdentity())
			{
				TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
				var entityKey = communicationConfig.GetEntityKeyFromIdentityMessageHandler.invoke(client.getRemotePartyIdentity());
				if (cachedMessages.containsKey(entityKey))
				{
					cachedMessages.get(entityKey).SetConnectionState(false);
				}
			}
		}
		try
		{
			client.close();
		}
		catch (java.lang.Exception e)
		{
		}
		finally
		{
			OnClientDisconnected(client);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void BroadcastMessage(TMessageWithTime<TMSG, TIdentity> message, params TConnectedClient<TMSG, TIdentity>[] except)
	public final void BroadcastMessage(TMessageWithTime<TMSG, TIdentity> message, TConnectedClient<TMSG, TIdentity>... except)
	{
		if (getState() == TerminalState.Connected)
		{
			int messageTime = new Long(System.currentTimeMillis()).intValue();

			TConnectedClient<TMSG, TIdentity>[] Receivers = null;
			synchronized (getClients())
			{
				// only send messages if identity is set
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
				//TODO MRCOMMENT : must uncomment
				//Receivers = getClients().Where(t -> !except.Contains(t) && t.Connected && t.IsSetRemotePartyIdentity).ToArray();
			}

			if (Receivers != null)
			{

				for (TConnectedClient<TMSG, TIdentity> connectedClient : Receivers)
				{
					try
					{
						if (connectedClient.getIsSetRemotePartyIdentity())
						{
							connectedClient.Send(message);
						}
					}
					catch (RuntimeException exp)
					{
						//TODO COMMENT : must uncomment
						//TLogManager.Error(exp);
					}
				}
			}
			TEntityCacheInformation<TMSG, TIdentity>[] cachedReceivers;
			synchronized (cachedMessages)
			{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
				//TODO COMMENT : must uncomment
				//cachedReceivers = cachedMessages.Where(s -> !Receivers.Any(r -> TCommunicationConfig<TMSG, TIdentity>.GetEntityKeyFromIdentityMessageHandler(r.RemotePartyIdentity) == s.Key) && !except.Any(p -> p.IsSetRemotePartyIdentity && TCommunicationConfig<TMSG, TIdentity>.GetEntityKeyFromIdentityMessageHandler(p.RemotePartyIdentity) == s.Key)).Select(s -> s.Value).ToArray();
				cachedReceivers = null;
			}
			if (cachedReceivers != null)
			{
				for (TEntityCacheInformation<TMSG, TIdentity> cachedItem : cachedReceivers)
				{
					synchronized (cachedItem.getSyncObject())
					{
						cachedItem.getMessages().add(message);
					}
				}
				CleanupNow();
			}
		}
	}

	//public void BroadcastMessage(TMessageBase[] message, params TConnectedClient[] except)
	//{
	//    BroadcastMessage(new TMessageContainer(message), except);
	//}

	private TEntityCacheInformation<TMSG, TIdentity> EnsureClientCacheEntry(TConnectedClient<TMSG, TIdentity> client)
	{
		TEntityCacheInformation<TMSG, TIdentity> cacheInfo;
		synchronized (cachedMessages)
		{
			TCommunicationConfig <TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
			UUID entityKey = communicationConfig.GetEntityKeyFromIdentityMessageHandler.invoke(client.getRemotePartyIdentity());
			if (cachedMessages.containsKey(entityKey))
			{
				cacheInfo = cachedMessages.get(entityKey);
			}
			else
			{
				cacheInfo = new TEntityCacheInformation<TMSG, TIdentity>(entityKey);
				cachedMessages.put(cacheInfo.getEntityKey(), cacheInfo);
			}
		}
		return cacheInfo;
	}

	private void OnCollectUnsentMessages(TConnectedClient<TMSG, TIdentity> client, ArrayList<TMessageWithTime<TMSG, TIdentity>> messages)
	{
		try
		{
			if (client.getIsSetRemotePartyIdentity())
			{
				if (!messages.isEmpty())
				{
					TEntityCacheInformation<TMSG, TIdentity> cacheInfo = EnsureClientCacheEntry(client);
					synchronized (cacheInfo.getSyncObject())
					{
						cacheInfo.getMessages().addAll(messages);
					}
				}
			}
		}
		catch (RuntimeException exp)
		{
			//TODO COMMENT : must uncomment

			//TLogManager.Error("CollectUnsentMessage error", exp);
		}
	}

	protected void OnMessageRecived(TConnectedClient<TMSG, TIdentity> sender, TMessageContainer<TMSG, TIdentity> message)
	{
		TMSG[] messageDetails;
		try
		{
			TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
			messageDetails = message.GetMessage(sender.getIsSetRemotePartyIdentity() && !communicationConfig.DebuggingCommunicationService);
		}
		catch (java.lang.Exception e)
		{
			//TODO COMMENT : must uncomment

			//TLogManager.Error("MM* Invalid message received - unable to deserialize");
			return;
		}
		if (sender.SetRemotePartyIdentity(message))
		{
			CommunicationEntityLifetime lifeTime;
			boolean requestUnsentMessages;
			tangible.OutObject<CommunicationEntityLifetime> tempOut_lifeTime = new tangible.OutObject<CommunicationEntityLifetime>();
			tangible.OutObject<Boolean> tempOut_requestUnsentMessages = new tangible.OutObject<Boolean>();
			TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
			communicationConfig.ParseRemotePartyIdentityMessageHandler.invoke(sender.getRemotePartyIdentity(), tempOut_lifeTime, tempOut_requestUnsentMessages);
		requestUnsentMessages = tempOut_requestUnsentMessages.argValue;
		lifeTime = tempOut_lifeTime.argValue;
			if (lifeTime == CommunicationEntityLifetime.Permanent)
			{
				// if not sent messages requested, add for send
				TEntityCacheInformation<TMSG, TIdentity> cacheInfo = EnsureClientCacheEntry(sender);
				if (requestUnsentMessages)
				{
					cacheInfo.SetConnectionState(true);
					synchronized (cacheInfo.getSyncObject())
					{
						for (TMessageWithTime<TMSG, TIdentity> item : cacheInfo.getMessages())
						{
							sender.Send(item);
						}
						cacheInfo.getMessages().clear();
					}
				}
				else
				{
					cacheInfo.getMessages().clear();
				}
				// if not exists in cache list, add to cache list
			}
			else
			{
				synchronized (cachedMessages)
				{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
					var entityKey = communicationConfig.GetEntityKeyFromIdentityMessageHandler.invoke(sender.getRemotePartyIdentity());
					if (cachedMessages.containsKey(entityKey))
					{
						cachedMessages.remove(entityKey);
					}
				}
			}
		}
		TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();

		if (communicationConfig.DebuggingCommunicationService)
		{
			if (messageDetails != null && messageDetails.length > 0)
			{
				Object messages;
				class AnonymousType
				{
					public String Message;

					public AnonymousType(String _Message)
					{
						Message = _Message;
					}
				}
				//TODO COMMENT : must uncomment
				//messages = messageDetails.Select(m -> AnonymousType(m.toString())).ToArray();
				class AnonymousType2
				{
					public Object Messages;

					public AnonymousType2(Object _Messages)
					{
						Messages = _Messages;
					}
				}
				//TODO COMMENT : must uncomment

				//TLogManager.Debug("Com Msg Received", AnonymousType2(messages));
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = MessageRecived;
		if (del != null)
		{
			try
			{
				List<MessageRecievedEventHandler<TMSG, TIdentity>> listeners = this.MessageRecived.listeners();
				for (MessageRecievedEventHandler<TMSG, TIdentity> listener:listeners) {
					listener.invoke(sender,message);
				}
			}
			catch (java.lang.Exception e2)
			{
			}
		}

		BroadcastMessage(new TMessageWithTime<TMSG, TIdentity>(new Long(System.currentTimeMillis()).intValue(), message, false), sender);
	}

	protected void OnClientConnected(TConnectedClient<TMSG, TIdentity> client)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = ClientConnect;
		if (del != null)
		{
			try
			{
				List<TClientConnectEventHandler<TMSG, TIdentity>> listeners = this.ClientConnect.listeners();
				for (TClientConnectEventHandler<TMSG, TIdentity> listener:listeners) {
					listener.invoke(client.getClientSoket());
				}
			}
			catch (java.lang.Exception e)
			{
			}
		}
		//TODO COMMENT : must uncomment


		//TLogManager.Info("Client ", client.toString(), " Connected.");
	}

	protected void OnClientDisconnected(TConnectedClient<TMSG, TIdentity> client)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = ClientDisconnect;
		if (del != null)
		{
			try
			{
				List<TClientDisconnectEventHandler<TMSG, TIdentity>> listeners = this.ClientDisconnect.listeners();
				for (TClientDisconnectEventHandler<TMSG, TIdentity> listener:listeners) {
					listener.invoke(client.getClientSoket());
				}
			}
			catch (java.lang.Exception e)
			{
			}
		}

		//

		//TODO COMMENT : must uncomment


		//TLogManager.Info("Client " + client.toString() + " Disconnected (" + client.getSystemEntity().toString() + ") !!!");
	}

	protected void OnConnectionStateChanged()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = ConnectionStateChanged;
		if (del != null)
		{
			try
			{
				List<EventHandler<EventArgs>> listeners = this.ConnectionStateChanged.listeners();
				for (EventHandler<EventArgs> listener:listeners) {
					listener.invoke(this,EventArgs.Empty);
				}
			}

			catch (java.lang.Exception e)
			{
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<MessageRecievedEventHandler<TMSG, TIdentity>> MessageRecived = new tangible.Event<MessageRecievedEventHandler<TMSG, TIdentity>>();
	public tangible.Event<TClientConnectEventHandler<TMSG, TIdentity>> ClientConnect = new tangible.Event<TClientConnectEventHandler<TMSG, TIdentity>>();
	public tangible.Event<TClientDisconnectEventHandler<TMSG, TIdentity>> ClientDisconnect = new tangible.Event<TClientDisconnectEventHandler<TMSG, TIdentity>>();
	public tangible.Event<EventHandler<EventArgs>> ConnectionStateChanged = new tangible.Event<EventHandler<EventArgs>>();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


}