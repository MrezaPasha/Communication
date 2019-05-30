package Rasad.Core.Net;

import Rasad.Core.*;
import Rasad.Core.*;
import java.io.*;

public class TIpMonitor implements Closeable
{

	public TIpMonitor(String nameOrAddress, double interval, int diconnectTryCount)
	{
		this(nameOrAddress, interval, diconnectTryCount, 3000);
	}

	public TIpMonitor(String nameOrAddress, double interval)
	{
		this(nameOrAddress, interval, 3, 3000);
	}

	public TIpMonitor(String nameOrAddress)
	{
		this(nameOrAddress, 10000.0, 3, 3000);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TIpMonitor(string nameOrAddress, double interval = 10000.0, int diconnectTryCount = 3, int waitBetweenFailedRetries = 3000)
	public TIpMonitor(String nameOrAddress, double interval, int diconnectTryCount, int waitBetweenFailedRetries)
	{
		this._diconnectTryCount = Math.max(1, diconnectTryCount);
		this._waitBetweenFailedRetries = waitBetweenFailedRetries;
		this.setNameOrAddress(nameOrAddress);
		this._Timer = new System.Timers.Timer(interval);
		this._Timer.AutoReset = false;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this._Timer.Elapsed += new ElapsedEventHandler(this._Timer_Elapsed);
	}
	private System.Timers.Timer _Timer;
	private boolean _Pingable;
	private int _diconnectTryCount;
	private boolean firstPing = true;
	private int _waitBetweenFailedRetries = 3000;
	private boolean stopped = true;


	private String NameOrAddress;
	public final String getNameOrAddress()
	{
		return NameOrAddress;
	}
	private void setNameOrAddress(String value)
	{
		NameOrAddress = value;
	}

	public final boolean getPingable()
	{
		return this._Pingable;
	}
	public final void setPingable(boolean value)
	{
		if (firstPing || this._Pingable != value)
		{
			if (!stopped)
			{
				firstPing = false;
				this._Pingable = value;
				OnStateChanged();
			}
		}
	}

	protected void OnStateChanged()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = this.StateChanged;
		if (del != null)
		{
			del(this, tangible.EventArgs.Empty);
		}
	}

	public tangible.Event<tangible.EventHandler<tangible.EventArgs>> StateChanged = new tangible.Event<tangible.EventHandler<tangible.EventArgs>>();

	public final void Start()
	{
		stopped = false;
		this._Timer.Start();
	}

	public final void Stop()
	{
		stopped = true;
		if (_Timer != null)
		{
			this._Timer.Stop();
		}
	}

	private void _Timer_Elapsed(Object sender, ElapsedEventArgs e)
	{
		try
		{
			int tryCount = 0;
			boolean isPingable = false;
			while (true)
			{
				isPingable = this.PingHost();

				//int aa = new Random(DateTime.Now.Millisecond).Next(5);
				//isPingable = aa == 1;

				if (isPingable)
				{
					break;
				}
				else
				{
					tryCount++;
					if (tryCount < _diconnectTryCount)
					{
						Thread.sleep(_waitBetweenFailedRetries);
					}
					else
					{
						break;
					}
				}
				if (stopped)
				{
					break;
				}
			}
			this.setPingable(isPingable);
		}
		finally
		{
			if (!stopped)
			{
				this._Timer.Start();
			}
		}
	}

	protected boolean PingHost()
	{
		try (Ping ping = new Ping())
		{
			try
			{
				// get only ip part
				IPAddress pingIPAddress = TNetworkHelper.GetIPAddressPart(this.getNameOrAddress());

				if (pingIPAddress != null)
				{
					return ping.Send(pingIPAddress).Status == IPStatus.Success;
				}
				else
				{
					return ping.Send(this.getNameOrAddress()).Status == IPStatus.Success;
				}
			}
			catch (PingException ex)
			{
				TLogManager.Error("Error ping of '" + this.getNameOrAddress() + "'", ex);
				return false;
			}
		}
	}

	public final void close() throws IOException
	{
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this._Timer.Elapsed -= new ElapsedEventHandler(this._Timer_Elapsed);
		this._Timer.Stop();
		this._Timer.Dispose();
	}

	@Override
	public String toString()
	{
		return String.format("IP %1$s => Is Pingable : %2$s", getNameOrAddress(), getPingable());
	}
}