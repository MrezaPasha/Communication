package main.java.Rasad.Communication.Core.Server;

import Rasad.Core.Services.*;
import main.java.Rasad.Communication.Core.TCommunicationConfig;
import main.java.Rasad.Communication.Core.TMessageContainer;
import main.java.Rasad.Communication.Core.TSocketListener;
import org.apache.tools.ant.types.Environment;
import xyz.baddeveloper.lwsl.client.SocketClient;

import javax.print.attribute.standard.RequestingUserName;
import java.net.Socket;
import java.util.*;
import java.io.*;

//using Rasad.Core.Services.NotificationService;
//using Rasad.Core.Services.NotificationService.Public;


public class TConnectedClient<TMSG, TIdentity extends TMSG> implements Closeable
{
	public TConnectedClient(SocketClient clientSocket)
	{
		//TODO MRREPLACE
		setSystemEntity(TCommunicationConfig.DefaultSystemEntity);
		setRemotePartyIdentity(null);
		setClientSoket(clientSocket);
		try
		{
			_Title = clientSocket.getAddress();
		}
		catch (java.lang.Exception e)
		{
			_Title = "N/A";
		}
		_MessageStack = new TMessageStack(this, _Title, this);
		_listener = new TSocketListener<TMSG, TIdentity>();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private String _Title;
	private TSocketListener<TMSG, TIdentity> _listener;

	private boolean _IsDisposed = false;
	private tangible.Action2Param<TConnectedClient<TMSG, TIdentity>, TMessageContainer<TMSG, TIdentity>> _MessageRecievedCallback;
	private tangible.Action2Param<TConnectedClient<TMSG, TIdentity>, RuntimeException> _ClientErrorCallback;
	private tangible.Action2Param<TConnectedClient<TMSG, TIdentity>, ArrayList<TMessageWithTime<TMSG, TIdentity>>> _CollectClientUnsentMessagesBeforeDisposeCallback;

	private boolean isSetRemotePartyIdentity = false;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	public final boolean getIsSetRemotePartyIdentity()
	{
		return isSetRemotePartyIdentity;
	}

	public final boolean getConnected()
	{
		return _listener != null ;
	}
	private SocketClient ClientSoket;
	public final SocketClient getClientSoket()
	{
		return ClientSoket;
	}
	private void setClientSoket(SocketClient value)
	{
		ClientSoket = value;
	}
	private Object SystemEntity;
	public final Object getSystemEntity()
	{
		return SystemEntity;
	}
	public final void setSystemEntity(Object value)
	{
		SystemEntity = value;
	}
	private TIdentity RemotePartyIdentity;
	public final TIdentity getRemotePartyIdentity()
	{
		return RemotePartyIdentity;
	}
	private void setRemotePartyIdentity(TIdentity value)
	{
		RemotePartyIdentity = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods


	private void OnError(RuntimeException obj)
	{
		tangible.Action2Param<TConnectedClient<TMSG, TIdentity>, RuntimeException> del = (TConnectedClient<TMSG, TIdentity> arg1, RuntimeException arg2) -> _ClientErrorCallback.invoke(arg1, arg2);
		if (del != null)
		{
			del.invoke(this, obj);
		}
	}

	private void OnMessageRecived(TMessageContainer<TMSG, TIdentity> data)
	{
		tangible.Action2Param<TConnectedClient<TMSG, TIdentity>, TMessageContainer<TMSG, TIdentity>> del = (TConnectedClient<TMSG, TIdentity> arg1, TMessageContainer<TMSG, TIdentity> arg2) -> _MessageRecievedCallback.invoke(arg1, arg2);
		if (del != null)
		{
			del.invoke(this, data);
		}
	}
	private void OnCollectClientUnsentMessagesBeforeDisposeCallback(ArrayList<TMessageWithTime<TMSG, TIdentity>> messages)
	{
		tangible.Action2Param<TConnectedClient<TMSG, TIdentity>, ArrayList<TMessageWithTime<TMSG, TIdentity>>> del = (TConnectedClient<TMSG, TIdentity> arg1, ArrayList<TMessageWithTime<TMSG, TIdentity>> arg2) -> _CollectClientUnsentMessagesBeforeDisposeCallback.invoke(arg1, arg2);
		if (del != null)
		{
			del.invoke(this, messages);
		}
	}

	public final boolean StartListen(tangible.Action2Param<TConnectedClient<TMSG, TIdentity>, TMessageContainer<TMSG, TIdentity>> messageRecieved, tangible.Action2Param<TConnectedClient<TMSG, TIdentity>, RuntimeException> clientError, tangible.Action2Param<TConnectedClient<TMSG, TIdentity>, ArrayList<TMessageWithTime<TMSG, TIdentity>>> collectUnsentMessage)
	{
		_MessageRecievedCallback = (TConnectedClient<TMSG, TIdentity> arg1, TMessageContainer<TMSG, TIdentity> arg2) -> messageRecieved.invoke(arg1, arg2);
		_ClientErrorCallback = (TConnectedClient<TMSG, TIdentity> arg1, RuntimeException arg2) -> clientError.invoke(arg1, arg2);
		_CollectClientUnsentMessagesBeforeDisposeCallback = (TConnectedClient<TMSG, TIdentity> arg1, ArrayList<TMessageWithTime<TMSG, TIdentity>> arg2) -> collectUnsentMessage.invoke(arg1, arg2);
		try
		{
			return _listener.StartReciving(getClientSoket(), (TMessageContainer<TMSG, TIdentity> obj) -> OnMessageRecived(obj), (RuntimeException obj) -> OnError(obj));
		}
		catch (Exception odex)
		{
			//TODO MRCOMMENT : logger
			//TLogManager.Error(odex.toString(), "TConnectedClient.StartListen() : Socket has been closed");
		}
		//TODO MRCOMMENT : must uncomment
		/*catch (IllegalArgumentException ae)
		{
			//TODO MRCOMMENT : logger
			//TLogManager.Error(ae.toString(), "TConnectedClient.StartListen() : ArgumentException occurd in EndAccept !");
		}
		catch (RuntimeException sex)
		{
			//TODO MRCOMMENT : logger
			//TLogManager.Error(sex.toString(), "TConnectedClient.StartListen() : Socket failed");
		}*/
		return false;
	}

	//[MethodImpl(MethodImplOptions.Synchronized)]
	//private bool Send(TMessageBase[] message)
	//{
	//    return _listener.Send(message);
	//}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void Send(TMessageWithTime<TMSG, TIdentity> message)
	public final void Send(TMessageWithTime<TMSG, TIdentity> message)
	{
		_MessageStack.Send(message);

	}

	/** 
	 
	 
	 @param message
	 @return true if identity is set at first time
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public bool SetRemotePartyIdentity(TMessageContainer<TMSG, TIdentity> message)

	public final boolean SetRemotePartyIdentity(TMessageContainer<TMSG, TIdentity> message)
	{
		if (isSetRemotePartyIdentity)
		{
			return false;
		}
		TMSG[] messages = message.GetMessage();
		for (TMSG item : messages)
		{
			//if (item instanceof TIdentity)
			//TODO MRCOMMENT : must uncomment
			//if (item instanceof TIdentity)
			//if (item instanceof TIdentity)
			if (true)
			{
				TIdentity notify = (TIdentity)item;
				Object parsedIdentity;
				TMSG responseRemotePartyIdentity;
				tangible.OutObject<Object> tempOut_parsedIdentity = new tangible.OutObject<Object>();
				tangible.OutObject<TMSG> tempOut_responseRemotePartyIdentity = new tangible.OutObject<TMSG>();
				TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
				communicationConfig.ParseAndResponseIdentityMessages(notify, tempOut_parsedIdentity, tempOut_responseRemotePartyIdentity);


				responseRemotePartyIdentity = tempOut_responseRemotePartyIdentity.argValue;
			parsedIdentity = tempOut_parsedIdentity.argValue;

				this.setSystemEntity(parsedIdentity);
				this.setRemotePartyIdentity(notify);
				isSetRemotePartyIdentity = true;
//C# TO JAVA CONVERTER WARNING: Java does not allow direct instantiation of arrays of generic type parameters:
//ORIGINAL LINE: this.Send(new TMessageWithTime<TMSG, TIdentity>(Environment.TickCount, new TMessageContainer<TMSG, TIdentity>(new TMSG[] { responseRemotePartyIdentity }), true));
				//TODO COMMENT : must uncomment
				this.Send(new TMessageWithTime<TMSG, TIdentity>(/*Environment.TickCount*/3, new TMessageContainer<TMSG, TIdentity>((TMSG[])new Object[] {responseRemotePartyIdentity}), true));

				//this.SystemEntity = notify.SystemEntity;
				//this.RemotePartyIdentity = notify;
				//isSetRemotePartyIdentity = true;
				//this.Send(new TMessageWithTime(Environment.TickCount, new TMessageContainer<TMSG, TIdentity>(new TMSG[]{ new TResponseRemotePartyIdentity(
				//    EntityKeyAcceptStatus.Accepted, notify.SystemEntity, notify.EntityKey)}), true));
				class AnonymousType
				{
					public String SystemEntity;
					public TMSG IdentityMessage;

					public AnonymousType(String _SystemEntity, TMSG _IdentityMessage)
					{
						SystemEntity = _SystemEntity;
						IdentityMessage = _IdentityMessage;
					}
				}
				//TODO MRCOMMENT : logger
				//TLogManager.Info("MM* Identity Set", AnonymousType(getSystemEntity().toString(), notify));
				//break;
				return true;
			}
		}
		return false;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void Dispose()
	public final void close() throws IOException
	{
		if (_IsDisposed)
		{
			return;
		}
		_IsDisposed = true;
		try
		{
			_MessageStack.close();
			if (_listener != null)
			{
				_listener.close();
			}
			else if (getClientSoket() != null)
			{
				//getClientSoket().
				getClientSoket().shutdown();
			}
		}
		catch (java.lang.Exception e)
		{
		}
	}

	@Override
	public String toString()
	{
		return _Title;
	}
	private TMessageStack _MessageStack;

	private  class TMessageStack implements Closeable
	{
		private static final int MaxSimultaneousMessages = 5;
		private String _Title;
		private  TConnectedClient<TMSG, TIdentity> parent;

		public TMessageStack(TConnectedClient<TMSG, TIdentity> client, String title, TConnectedClient<TMSG, TIdentity> parent)
		{
			setClient(client);
			_Title = title;
			this.parent = parent;
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						DoSenderThread();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});

			senderThread.setDaemon(true);
			senderThread.setPriority(4);
			senderThread.start();

			//_Timer = new Timer(100);
			//_Timer.AutoReset = false;
			//_Timer.Elapsed += _Timer_Elapsed;
		}

		private void DoSenderThread() throws InterruptedException {
			while (!_IsDisposed)
			{
				TMessageWithTime[] SendingItems = null;

				synchronized (_NotSended)
				{
					SendingItems[0] = _NotSended.get(MaxSimultaneousMessages);
				}

				if (SendingItems != null && SendingItems.length > 0)
				{
					TMessageContainer SendingItem = null;
					// contact sending items
					if (SendingItems.length >= 1)
					{
						ArrayList<TMSG> messages = new ArrayList<TMSG>();
						for (TMessageWithTime item : SendingItems)
						{
							TMSG[] itemMessages = (TMSG[]) item.getMessage().GetMessage(true);
							if (itemMessages != null) // deserialization successful?!
							{
								for (TMSG itemMsg : itemMessages)
								{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
										///#region Filter based on identity
									TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
									if (communicationConfig.FilterMessageOnServerHandler.invoke(itemMsg, this.parent.getRemotePartyIdentity(), this.parent.getSystemEntity()))
									{
										continue;
									}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
										///#endregion
									messages.add(itemMsg);
								}
							}
						}
						if (!messages.isEmpty())
						{
							SendingItem = new TMessageContainer<TMSG, TIdentity>();
						}
					}

					boolean sended = false;
					try
					{
						if (SendingItem != null)
						{
							TSocketListener SL = getSocketListener();
							if (SL != null)
							{
								sended = SL.Send(SendingItem);
							}
						}
						else
						{
							sended = true;
						}
					}
					finally
					{
						int notSendedCount;
						synchronized (_NotSended)
						{
							if (sended)
							{
								for (TMessageWithTime item : SendingItems)
								{
									_NotSended.remove(item);
								}
							}
							notSendedCount = _NotSended.size();
						}
						if (notSendedCount > 0)
						{
							//TODO MRCOMMENT : logger

							//TLogManager.Debug("MM*: _NotSended - " + this.parent.getSystemEntity().toString() + ": " + String.valueOf(notSendedCount) + "[" + _Title + "]");
						}
						SendingItem = null;
						//if (!_IsDisposed)
						//{
						//    _Timer.Start();
						//}
					}
				}
				else
				{
					Thread.sleep(1);
				}
			}
		}

		private void _Timer_Elapsed(Object sender, tangible.EventArgs e)
		{
			//lock (_NotSended)
			//{
			//    if (SendingItem != null) return;
			//    SendingItem = _NotSended.FirstOrDefault();
			//    if (SendingItem == null) return;
			//}

			//bool sended = false;
			//try
			//{
			//    TSocketListener SL = SocketListener;
			//    if (SL != null)
			//    {
			//        sended = SL.Send(SendingItem);
			//    }
			//}
			//finally
			//{
			//    lock (_NotSended)
			//    {
			//        if (sended) _NotSended.Remove(SendingItem);
			//        TLogManager.LogToHDD("MM*: _NotSended: " + _NotSended.Count.ToString() + "[" + (this.Client.ClientSoket.RemoteEndPoint as IPEndPoint).ToString() + "]");
			//        SendingItem = null;
			//        if (!_IsDisposed)
			//        {
			//            _Timer.Start();
			//        }
			//    }
			//}
		}

		private boolean _IsDisposed;
		//Timer _Timer;
		private Thread senderThread = null;

		private TConnectedClient Client;
		public final TConnectedClient getClient()
		{
			return Client;
		}
		private void setClient(TConnectedClient value)
		{
			Client = value;
		}
		public final TSocketListener getSocketListener()
		{
			return getClient()._listener;
		}

		public final void Send(TMessageWithTime message)
		{
			synchronized (_NotSended)
			{
				if (!_IsDisposed)
				{
					_NotSended.add(message);
					//_Timer.Start();
				}
				else
				{
					if (!message.getIsServerGeneratedMssage())
					{
						parent.OnCollectClientUnsentMessagesBeforeDisposeCallback(new ArrayList<TMessageWithTime<TMSG, TIdentity>>((Collection<? extends TMessageWithTime<TMSG, TIdentity>>) message));
					}
				}
			}
		}


		//private TMessageContainer SendingItem;
		private ArrayList<TMessageWithTime> _NotSended = new ArrayList<TMessageWithTime>();
		//private object SenderLock = new object();

		public final void close() throws IOException
		{
			synchronized (_NotSended)
			{
				if (_IsDisposed)
				{
					return;
				}
				_IsDisposed = true;
			}

			//_Timer.Dispose();
			synchronized (_NotSended)
			{
				tangible.ListHelper.removeAll(_NotSended, m -> m.getIsServerGeneratedMssage());
				//TODO MRCOMMENT : must uncomment
				//parent.OnCollectClientUnsentMessagesBeforeDisposeCallback(this._NotSended);
				//
				_NotSended.clear();
			}
		}
	}
}