package main.java.Rasad.Communication.Core.Client;

import Rasad.Core.Services.*;
import Rasad.*;
import main.java.Rasad.Communication.Core.Client.TClientTerminal;
import main.java.Rasad.Communication.Core.TerminalState;

import java.io.IOException;

public class TClientTerminalSafe<TMSG, TIdentity extends TMSG> extends TClientTerminal<TMSG, TIdentity>
{
	private boolean ShouldWork = false;
	@Override
	public boolean Start(String serverMachineName, int serverPort) throws Exception {
		ShouldWork = true;
		if (super.Start(serverMachineName, serverPort))
		{
			return true;
		}
		else
		{
			TryStartAgainAsync();
			return false;
		}
	}
	@Override
	public void Stop() throws IOException {
		ShouldWork = false;
		super.Stop();
	}

	@Override
	protected void OnConnectionStateChanged()
	{
		//TLogManager.Info("Connection State Changed To  : " + getState().toString());
		if (getState() == TerminalState.Stopped && ShouldWork)
		{
			TryStartAgainAsync();
		}
		super.OnConnectionStateChanged();
	}


	private void TryStartAgainAsync()
	{
		TryStartAgainAsync(5000);
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: private void TryStartAgainAsync(int wait = 5000)
	private void TryStartAgainAsync(int wait)
	{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TryStartAgain();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}


			}
		});
		/*Task.Factory.StartNew(() ->
		{
			TryStartAgain();
		});*/
	}
	private boolean _IsInTryMode = false;


	private void TryStartAgain() throws InterruptedException {
		TryStartAgain(5000);
	}

	//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: private void TryStartAgain(int wait = 5000)
	private void TryStartAgain(int wait) throws InterruptedException {
		if (_IsInTryMode)
		{
			return;
		}
		_IsInTryMode = true;
		try
		{
			while (ShouldWork && getState() != TerminalState.Connected)
			{
				Thread.sleep(wait);
				try
				{
					if (ShouldWork) // check this to now connect again after stopped
					{
						//TLogManager.Info("Try to Start after 5 seconds ...");
						StartInternal();
					}
				}
				catch (RuntimeException exp)
				{
					//TLogManager.Error("Try for connect failed after 5 second !!!", exp);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//Thread.Sleep(wait);
			}
		}
		finally
		{
			_IsInTryMode = false;
		}
	}


}