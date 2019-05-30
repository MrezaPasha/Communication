package main.java.Rasad.Communication.Core;

import main.java.Rasad._core.TimeSpan;
import main.java.Rasad.tangible.EventHandler;
import org.apache.tools.ant.Task;
import tangible.Event;
import xyz.baddeveloper.lwsl.client.SocketClient;
import xyz.baddeveloper.lwsl.server.SocketServer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static Rasad.Core.Diagnostics.log.Close;

public class TSocket extends SocketServer
{


	//TODO MRREPLACE :;
	Timer timer = new Timer();
	//private TTimer timer;
	private static final int INTERVAL = 1000;

	public TSocket(/*AddressFamily addressFamily, SocketType socketType, ProtocolType protocolType*/)
	{
		//super(addressFamily, socketType, protocolType);
		//TODO MRREPLACE :
		//timer = new Timer(String.valueOf(TimeSpan.FromMilliseconds(INTERVAL)));
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("do nothing");
			}
		},0,TimeSpan.FromMilliseconds(INTERVAL).Ticks());
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		//timer.Elapsed += TimerTick;
	}

	public TSocket(int socketInformation)
	{
		super(socketInformation);
		timer = new Timer(String.valueOf(TimeSpan.FromMilliseconds(INTERVAL)));
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		//timer.Elapsed += TimerTick;
		timer.schedule(null,0,1);
	}

	public final void Stop()
	{
		Close();
	}

	public final boolean getEventsEnabled()
	{
		if (timer == null)
		{
			return false;
		}
		return true;
	}
	public final void setEventsEnabled(boolean value)
	{
		if (timer != null)
		{
			if (value)
			{
				//timer.schedule();
				timer.schedule(null,0,1);
			}
			else
			{
				timer.purge();
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] private void TimerTick()
	private void TimerTick()
	{
		if (CheckIsConnected())
		{
			setConnected(true);
		}
		else
		{
			stop();
		}
	}
	protected boolean CheckIsConnected()
	{
		//TODO MRCOMMENT : must uncomment
		/*boolean part1 = Poll(1000, SelectMode.SelectRead);
		boolean part2 = (Available == 0);
		boolean result = !(part1 && part2);
		return result;*/
		return true;
	}

	// Hiding base connected property
	private boolean Connected;
//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: public new bool getConnected()
	public final boolean getConnected()
	{
		return Connected;
	}
//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: private new void setConnected(bool value)
	private void setConnected(boolean value)
	{
		Connected = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] protected override void Dispose(bool disposing)
	protected void Dispose(boolean disposing)
	{
		if (timer != null)
		{
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			//TODO MRCOMMENT
			/*timer.Elapsed -= TimerTick;
			timer.Dispose();*/
			timer.purge();
			timer = null;
			Close();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var del = SocketClosed;
			/*if (getConnected() && del != null)
			{
				setConnected(false);
				del(this);
			}*/

			List<SocketEventHandler> listeners = this.SocketClosed.listeners();
			for (SocketEventHandler listener:listeners) {
				listener.invoke(getServerSocket());
			}

		}
		super.stop();
	}

	public tangible.Event<SocketEventHandler> SocketClosed = new Event<SocketEventHandler>();

}