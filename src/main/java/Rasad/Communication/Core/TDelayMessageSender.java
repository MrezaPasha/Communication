package main.java.Rasad.Communication.Core;

import main.java.Rasad._core.AutoResetEvent;
import org.dizitart.jbus.JBus;

import java.util.*;
import java.io.*;

//[System.Diagnostics.DebuggerStepThrough]
public class TDelayMessageSender<TMSG, TIdentity extends TMSG> implements Closeable
{

	public TDelayMessageSender(TNotificationService<TMSG, TIdentity> parentNotificationService)
	{
		this.parent = parentNotificationService;
		_EventSender = new AutoResetEvent(false);
		_MainThread = new Thread()
		{
		public void run()
		{
			try {
				DoSenderThread();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		};
		//TODO MRREPLACE
		_MainThread.setDaemon(true);
		//_MainThread.setPriority(ThreadPriority.AboveNormal);
		//TODO MRREPLACE
		_MainThread.setPriority(3);
		_MainThread.start();
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		parent.ConnectedStateChanged.addListener(this::TNotificationService_ConnectedStateChanged);
	}

	private void TNotificationService_ConnectedStateChanged(Object sender, tangible.EventArgs e)
	{
		if (parent.getIsConnected())
		{
			_EventSender.set();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private static final int MaxSimultaneousMessages = 10;
	private Thread _MainThread = null;
	private AutoResetEvent _EventSender;
	private ArrayList<TMSG> _NotSendedMessages = new ArrayList<TMSG>();
	private TMSG identityMessage = null;
	private TNotificationService<TMSG, TIdentity> parent = null;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	private boolean IsDisposed;
	public final boolean getIsDisposed()
	{
		return IsDisposed;
	}
	private void setIsDisposed(boolean value)
	{
		IsDisposed = value;
	}
	public final boolean getHasMessage()
	{
		synchronized (_NotSendedMessages)
		{
			//TODO MRREPLACE;
			return !_NotSendedMessages.isEmpty();
		}
	}
	public final void SendIdentityMessage(TMSG identityMessage)
	{
		this.identityMessage = identityMessage;
		_EventSender.set();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private void DoSenderThread() throws InterruptedException {
		while (!getIsDisposed())
		{
			try
			{
				while (!getIsDisposed() && parent.getIsConnected())
				{
					TMSG[] _SendingMessages = null;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
						///#region Take Some Messages For Sending
					if (parent.getIsIdentitySet())
					{
						synchronized (_NotSendedMessages)
						{
							//TODO MREDIT
							_SendingMessages = (TMSG[]) _NotSendedMessages.get(MaxSimultaneousMessages);
						}
					}
					else
					{
						TMSG msg = this.identityMessage;
						this.identityMessage = null;
						if (msg != null)
						{
//C# TO JAVA CONVERTER WARNING: Java does not allow direct instantiation of arrays of generic type parameters:
//ORIGINAL LINE: SendInternal(new TMSG[] { msg });
							SendInternal((TMSG[])new Object[] {msg});
						}
					}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
						///#endregion

					//TODO MRREPLACE

					if (_SendingMessages != null && _SendingMessages.length !=0)
					{
						if (SendInternal(_SendingMessages))
						{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
								///#region Release Sended Messages
							//TODO MRREPLACE
							//_SendingMessages.ForEach(m -> _NotSendedMessages.remove(m));

							synchronized (_NotSendedMessages)
							{
								for (TMSG tmsg:_NotSendedMessages) {
									_NotSendedMessages.remove(tmsg);

								}
							}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
								///#endregion
						}
						//lock (_NotSendedMessages)
						//{
						//    //Trace.WriteLine("_NotSendedMessages: " + _NotSendedMessages.Count.ToString());
						//}
					}
					else
					{
						break;
					}
				}

				if (this.identityMessage == null || !parent.getIsConnected())
				{
					_EventSender.waitOne();
				}
			}
			catch (RuntimeException exp)
			{
				//TODO Logger
				//TLogManager.Error("Error in DoSenderThread", exp);
			}
		}
		//TODO MREDIT
		//_EventSender.Dispose();
		_EventSender.reset();
		_MainThread = null;
	}

	public final void SendMessage(TMSG message)
	{
		synchronized (_NotSendedMessages)
		{
			_NotSendedMessages.add(message);
			_EventSender.set();
		}
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] private bool SendInternal(TMSG[] messages)
	private boolean SendInternal(TMSG[] messages)
	{
		try
		{
			return !getIsDisposed() && parent._Connector != null && parent.getIsConnected() && parent._Connector.SendMessage(messages);
		}
		catch (java.lang.Exception e)
		{
			return false;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void Dispose()
	public final void close() throws IOException
	{
		if (getIsDisposed())
		{
			return;
		}
		synchronized (_NotSendedMessages)
		{
			if (getIsDisposed())
			{
				return;
			}
			setIsDisposed(true);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			//TDelayMessageSender delayMessageSender = new TDelayMessageSender();
			//JBus jBus = new JBus();
			//jBus.register(delayMessageSender);
			//TODO MRCOMMENT
			//parent.ConnectedStateChanged. -= TNotificationService_ConnectedStateChanged;
			parent.ConnectedStateChanged.removeListener("TNotificationService_ConnectedStateChanged");
			_EventSender.set();
		}
	}


}