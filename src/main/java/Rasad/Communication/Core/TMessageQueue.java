package main.java.Rasad.Communication.Core;

import main.java.Rasad._core.TimeSpan;

import javax.management.NotificationListener;
import javax.management.timer.Timer;
import java.util.*;
import java.io.*;

public class TMessageQueue implements Closeable
{

	//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMessageQueue(Action<byte[]> sendCallback, TimeSpan sendInterval)
	public TMessageQueue(tangible.Action1Param<byte[]> sendCallback, TimeSpan sendInterval)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _SendCallback = (byte[] obj) -> sendCallback.invoke(obj);
		_SendCallback = (byte[] obj) -> sendCallback.invoke(obj);
		//_Timer = new TTimer(sendInterval);
		_Timer = new Timer();
		//TODO MREDIT : check run time
		_Timer.addNotificationListener(this.Recheck(),null,_Timer);
		//_Timer.setElapsed(() -> Recheck());
	}
	//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private List<byte[]> _Messages = new List<byte[]>();
	private ArrayList<byte[]> _Messages = new ArrayList<byte[]>();
	//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private Action<byte[]> _SendCallback;
	private tangible.Action1Param<byte[]> _SendCallback;
	private Timer _Timer;

	//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void Enqueue(byte[] message)
	public final void Enqueue(byte[] message)
	{
		synchronized (_Messages)
		{
			_Messages.add(message);
			if (!_Timer.isActive())
			{
				_Timer.start();
			}
		}
	}

	private NotificationListener Recheck()
	{
		synchronized (_Messages)
		{
			//TODO MRCOMMENT : check it run time
			if (/*_Messages.NotAny()*/_Messages.isEmpty())
			{
				_Timer.stop();
			}
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] sendingMessage = _Messages[0];
		byte[] sendingMessage = _Messages.get(0);
		_Messages.remove(0);
		_SendCallback.invoke(sendingMessage);
		return null;
	}

	public final void close() throws IOException
	{
		if (_Timer != null)
		{
			_Timer.stop();
			_Timer = null;
			_Messages.clear();
		}
	}
}