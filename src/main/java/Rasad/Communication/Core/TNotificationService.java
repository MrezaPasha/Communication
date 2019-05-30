package main.java.Rasad.Communication.Core;


import jdk.jfr.Timespan;
import main.java.Rasad.Core.Services.NotificationService.Public.SystemEntity;
import main.java.Rasad.Core.TServiceContext;
import main.java.Rasad._core.TimeSpan;
import main.java.Rasad.tangible.EventHandler;
import net.tascalate.async.core.AsyncMethodExecutor;
import org.jetbrains.annotations.Nullable;
import tangible.Event;
import tangible.EventArgs;

import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static main.java.Rasad.Communication.Core.TCommunicationConfig.PrepareEntityIdentity;

public class TNotificationService<TMSG, TIdentity extends TMSG>
{
	//public static TNotificationService<TMSG, TIdentity> Instance { get; private set; }

	//static TNotificationService()
	//{
	//    if (Instance == null)
	//    {
	//        Instance = new TNotificationService<TMSG, TIdentity>();
	//    }
	//}

	public TNotificationService()
	{
		setServerMode(true);
		setAddToServiceContext(true);
		_DelaySender = new TDelayMessageSender<TMSG, TIdentity>(this);
		//timerIdentitySet = new System.Timers.Timer(TimeSpan.FromSeconds(10).TotalMilliseconds);
		TimeSpan timespan ;
		//timespan.
		timerIdentitySet = new Timer(String.valueOf(TimeSpan.FromSeconds(10).Milliseconds()));
		///timerIdentitySet.AutoReset = true;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		//timerIdentitySet.Elapsed += timerIdentitySet_Elapsed();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				timerIdentitySet_Elapsed(this, EventArgs.Empty);

			}
		};
		timerIdentitySet.schedule(task,0,10);

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private boolean _IsStartedExternally;
	private TIdentity _RemotePartyIdentity = null;
	protected TNotificationServiceSocket<TMSG, TIdentity> _Connector;
	//private static TRequestResponseManager _RequestResponseManager = new TRequestResponseManager();
	private TDelayMessageSender<TMSG, TIdentity> _DelaySender;
	private LocalDateTime _ConnectionStartDateTime = LocalDateTime.now();
	//private static String _CommunicationServiceSeverAddress;

	private boolean _IsIdentitySet = false;
	//TODO MRCOMMENT
	//private System.Timers.Timer timerIdentitySet;
	private Timer timerIdentitySet;
	protected boolean requestUnsentMessagesOnIdentify = false;
	protected Integer entityUserID = null;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	private boolean ServerMode;
	public final boolean getServerMode()
	{
		return ServerMode;
	}
	protected final void setServerMode(boolean value)
	{
		ServerMode = value;
	}
	private boolean AddToServiceContext;
	public final boolean getAddToServiceContext()
	{
		return AddToServiceContext;
	}
	protected final void setAddToServiceContext(boolean value)
	{
		AddToServiceContext = value;
	}

	public final TimeSpan getConnectedTimeLength()
	{
		if (!getIsConnected())
		{
			return TimeSpan.Zero;
		}
		//MRCOMMENT
		return TimeSpan.FromDays(ChronoUnit.SECONDS.between(_ConnectionStartDateTime,LocalDateTime.now()));
		//return LocalDateTime.now() - _ConnectionStartDateTime;
	}

	public final long getRecievedMessageCount()
	{
		if (_Connector == null)
		{
			return 0;
		}
		return _Connector.getRecievedMessageCount();
	}
	public final long getRecievedDataSize()
	{
		if (_Connector == null)
		{
			return 0;
		}
		return _Connector.getRecievedDataSize();
	}
	public final long getSendMessageCount()
	{
		if (_Connector == null)
		{
			return 0;
		}
		return _Connector.getSendMessageCount();
	}
	public final long getSendDataSize()
	{
		if (_Connector == null)
		{
			return 0;
		}
		return _Connector.getSendDataSize();
	}

	public final boolean getIsConnected()
	{
		return _Connector != null && _Connector.getState() == TerminalState.Connected;
	}

	public final boolean getIsIdentitySet()
	{
		return _IsIdentitySet;
	}
	public final void setIsIdentitySet(boolean value)
	{
		synchronized (timerIdentitySet)
		{
			_IsIdentitySet = value;
			//timerIdentitySet.Enabled = !_IsIdentitySet;
		}
			// After first successful connection to communication, if connection is lost, request for unsent messages
			//  after connection. This is valid until exit from process.
		if (value)
		{
			requestUnsentMessagesOnIdentify = true;
		}
	}

	public final boolean getIsStarted()
	{
		return _IsStartedExternally;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods
	private void timerIdentitySet_Elapsed(Object sender, EventArgs e)
	{
		NotifyIdentity();
	}

	//public void Start(Object systemEntity, bool serverMode)
	//{
	//    // this auto generates and stores entity key
	//    Start(TCommunicationConfig<TMSG, TIdentity>.PrepareEntityIdentity(systemEntity, requestUnsentMessagesOnIdentify), serverMode);
	//}
	protected final void Start(TIdentity remotePartyIdentity, boolean serverMode, Integer entityUserID) throws Exception {
		_IsStartedExternally = true;
		setServerMode(serverMode);

		if (getIsConnected())
		{
			Stop();
		}
		_Connector = new TNotificationServiceSocket<TMSG, TIdentity>();

		if (getAddToServiceContext())
		{
			TServiceContext.getInstance().<INotificationService<TMSG>>AddService(_Connector);
		}
		try
		{
			setIsIdentitySet(false);
			//Dns.GetHostEntry(remotePartyIdentity)
			//Current.Start("192.168.1.202", PortNumber);
			TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
			_RemotePartyIdentity = remotePartyIdentity;
			_Connector.Start(OnGetCommunicationServiceSeverAddress(), communicationConfig.CommunicationPortNumber);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			//MRCOMMENT
			_Connector.ConnectedStateChanged.addListener(this::Current_ConnectedStateChanged);
			//_Connector.ConnectedStateChanged += Current_ConnectedStateChanged;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			//_Connector.NotificationRecieved += Current_NotificationRecieved();
			_Connector.NotificationRecieved.addListener("Current_NotificationRecieved",this::Current_NotificationRecieved);
			//_Connector.MessageSended += _Connector_MessageSended;
			// send statup identity
			NotifyIdentity();
		}
		catch (RuntimeException exc)
		{
			//TODO MRCOMMENT : logger
			//TLogManager.Error("NotificationService Start Error", exc);
			throw new RuntimeException("عدم موفقیت در برقراری ارتباط با سرویس ارتباطات !", exc);
		}
	}

	//static void _Connector_MessageSended(object sender, EventArgs e)
	//{
	//    OnConnectionInfoChanged();
	//}

	public final void Stop() throws InterruptedException, IOException {
		//TLogManager.Info("TNotificationService STOP");

		_IsStartedExternally = false;
		if (_Connector != null) // && _Connector.State == TerminalState.Connected) --> remove connected condition to allow stop a non-connected client
		{
			if (getAddToServiceContext())
			{
				TServiceContext.getInstance().RemoveService(INotificationService.class);
			}
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			//_Connector.NotificationRecieved -= Current_NotificationRecieved;
			_Connector.NotificationRecieved.removeListener("Current_NotificationRecieved");
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			//TODO : MRCOMMENT
			_Connector.ConnectedStateChanged.removeListener("Current_ConnectedStateChanged");
			//_Connector.ConnectedStateChanged -= Current_ConnectedStateChanged;
			//_Connector.MessageSended -= _Connector_MessageSended;
			while (_DelaySender.getHasMessage() && _Connector.getState() == TerminalState.Connected)
			{
				Thread.sleep(10);
			}
			_Connector.Stop();
		}
	}


	public void Restart(SystemEntity systemEntity, Integer entityUserID)
	{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Restart(systemEntity, entityUserID, 20);

			}
		});
	}


	//TODO Comment : must uncomment
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent in Java to the 'async' keyword:
//ORIGINAL LINE: public async Task Restart(Object systemEntity, Nullable<int> entityUserID, int waitSeconds = 20)
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	//public final Task Restart(Object systemEntity, Integer entityUserID, int waitSeconds)
	public CompletionStage<Void> Restart(SystemEntity systemEntity, Integer entityUserID, int waitSeconds)
	{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				requestUnsentMessagesOnIdentify = false;
				TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
				Integer x=1;
				///
				//AsyncMethodExecutor.await(Restart(communicationConfig.PrepareEntityIdentity(systemEntity, requestUnsentMessagesOnIdentify, entityUserID, waitSeconds));
				AsyncMethodExecutor.await(Restart(systemEntity,entityUserID,waitSeconds));


			}
		});
		// on restart, don't request for identity because server may have changed
		requestUnsentMessagesOnIdentify = false;
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to 'await' in Java:
		return null;
	}

	//TODO Comment : must uncomment
	public void Restart(TIdentity remotePartyIdentity, Integer entityUserID)
	{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Restart(remotePartyIdentity, entityUserID, 20);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent in Java to the 'async' keyword:
//ORIGINAL LINE: public async Task Restart(TIdentity remotePartyIdentity, Nullable<int> entityUserID, int waitSeconds = 20)
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	public void Restart(TIdentity remotePartyIdentity, Integer entityUserID, int waitSeconds) throws Exception {
		Stop();
		if (waitSeconds > 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to 'await' in Java:
			//await Task.Delay(waitSeconds * 1000);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(waitSeconds*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
		}
		Start(remotePartyIdentity, getServerMode(), entityUserID);
	}

	private void NotifyIdentity()
	{
		TMSG msg = _RemotePartyIdentity;
		if (msg != null)
		{
			TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<TMSG, TIdentity>();
			//TCommunicationConfig<TMSG, TIdentity>.ModifyRemotePartyIdentityMessageHandler.invoke();msg, requestUnsentMessagesOnIdentify, entityUserID);
			communicationConfig.ModifyRemotePartyIdentityMessageHandler.invoke(msg,requestUnsentMessagesOnIdentify,entityUserID);
			//msg.RequestUnsentMessages = requestUnsentMessagesOnIdentify;
			_DelaySender.SendIdentityMessage(msg);
		}
	}

	private void Current_ConnectedStateChanged(Object sender, tangible.EventArgs e)
	{
		//OnConnectionInfoChanged();
		_ConnectionStartDateTime = LocalDateTime.now();
		TNotificationServiceSocket<TMSG, TIdentity> con = _Connector;
		if (con != null)
		{
			if (con.getState() == TerminalState.Connected)
			{
				NotifyIdentity();
			}
			else
			{
				setIsIdentitySet(false);
			}
			//TODO MRCOMMENT
			//Debug.WriteLine(con.getState());
		}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = ConnectedStateChanged;
		/*if (del != null)
		{
			del(sender, e);
		}*/

		//MRCOMMENT : must uncomment

		/*List<tangible.Event<EventHandler<tangible.EventArgs>>> listeners = new ConnectedStateChanged.listeners();
		for (tangible.Event<EventHandler<tangible.EventArgs>> listener:listeners) {
			listener.invoke();
		}*/
		////////

	}

	private void Current_NotificationRecieved(TMSG data)
	{
		boolean Handled = false;
		//OnConnectionInfoChanged();
		if (!getIsIdentitySet())
		{
			boolean isDataIdentityResponse;
			tangible.OutObject<Boolean> tempOut_isDataIdentityResponse = new tangible.OutObject<Boolean>();
			//TODO MREDITED
			TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
			boolean isIdentitySetValue = communicationConfig.CheckIfIsIdentitySetHandler.invoke(data, _RemotePartyIdentity, tempOut_isDataIdentityResponse);
		isDataIdentityResponse = tempOut_isDataIdentityResponse.argValue;
			if (isDataIdentityResponse)
			{
				setIsIdentitySet(isIdentitySetValue);
				//TLogManager.Info("ResponseRemotePartyIdentity : ", notif.ToString());
				Handled = true;
			}
			//if (data is TResponseRemotePartyIdentity)
			//{
			//    TResponseRemotePartyIdentity notif = (TResponseRemotePartyIdentity)data;
			//    IsIdentitySet =
			//        notif.Status == EntityKeyAcceptStatus.Accepted &&
			//        _RemotePartyIdentity != null &&
			//        notif.ReceivedEntityKey == _RemotePartyIdentity.EntityKey;
			//    TLogManager.Info("ResponseRemotePartyIdentity : ", notif.ToString());
			//    Handled = true;
			//}
		}

		//_RequestResponseManager.Process(data, ref Handled);   ##############################################################
		if (!Handled)
		{
			RaiseOnNotificationReceived(data);
		}
	}
	//private void OnConnectionInfoChanged()
	//{
	//    var del = ConnectionInfoChanged;
	//    if (del != null)
	//    {
	//        del(null, EventArgs.Empty);
	//    }
	//}



	public final void Notify(TMSG data, boolean fromLocalCommunicationClients)
	{
		Notify(data, fromLocalCommunicationClients, true);
	}

	public final void Notify(TMSG data)
	{
		Notify(data, false, true);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public void Notify(TMSG data, bool fromLocalCommunicationClients = false, bool allowBroadcastToLocalCommunicationClients = true)
	public final void Notify(TMSG data, boolean fromLocalCommunicationClients, boolean allowBroadcastToLocalCommunicationClients)
	{
		//if (TLogManager.LogLevel.HasFlag(RasadLogLevel.Debug))
		//{
		//    if (data is Rasad.Core.Services.NotificationService.Camera.TNotifyCameraRecordingStateChanged)
		//    {
		//        TLogManager.Debug("RecordingStateChanged Notify",
		//            new
		//            {
		//                Message = data.ToString()
		//            });
		//    }
		//}
		_DelaySender.SendMessage(data);
	}

	public TMSG NotifyAndWaitForResponse(TMSG data, int timeOutMilliseconds)
	{
	    return NotifyAndWaitForResponse(data, timeOutMilliseconds);
	}

	 /*public async Task<R> NotifyAndWaitForResponse<T, R>(T data, int timeOutMilliseconds = 1000)
	    where T : TMessageBase
	    where R : TMessageBase
	{
	   return await Task.Run<R>(() => (R)NotifyAndWaitForResponse(data, timeOutMilliseconds));
	}*/


	public final void RaiseOnNotificationReceived(TMSG data)
	{
		try
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var del = NotificationRecieved;
			/*if (del != null)
			{
				del(data);
			}*/
			///var del = this.GetCommunicationServiceSeverAddress;
			List<NotificationMessageEventHandler<TMSG>> listeners = this.NotificationRecieved.listeners();
			for (NotificationMessageEventHandler listener:listeners) {
				listener.invoke(this);
			}
		}
		catch (RuntimeException exp)
		{
			//TODO MRCOMMENT : logger
			//TLogManager.Error("Error processing notification message, Message = " + data.toString(), exp);
		}
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	protected String OnGetCommunicationServiceSeverAddress()
	{
		GetCommunicationServiceSeverAddressEventArgs eventArgs = new GetCommunicationServiceSeverAddressEventArgs();

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = this.GetCommunicationServiceSeverAddress;
		List<GetCommunicationServiceSeverAddressEventHandler> listeners = this.GetCommunicationServiceSeverAddress.listeners();
		for (GetCommunicationServiceSeverAddressEventHandler listener:listeners) {
			listener.invoke(this,eventArgs.getCommunicationServiceSeverAddress());
		}
		return eventArgs.getCommunicationServiceSeverAddress();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<NotificationMessageEventHandler<TMSG>> NotificationRecieved = new Event<NotificationMessageEventHandler<TMSG>>();
	public Event<EventHandler<tangible.EventArgs>> ConnectedStateChanged = new Event<EventHandler<tangible.EventArgs>>();
	//public event EventHandler ConnectionInfoChanged;

	public Event<GetCommunicationServiceSeverAddressEventHandler> GetCommunicationServiceSeverAddress = new Event<GetCommunicationServiceSeverAddressEventHandler>();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}