package main.java.Rasad.Communication.Core.Server;


import main.java.Rasad.Communication.Core.TerminalState;

public class TServerTerminalSafe<TMSG, TIdentity extends TMSG> extends TServerTerminal<TMSG, TIdentity>
{
	private boolean _ShouldWork;
	@Override
	public boolean Start()
	{
		_ShouldWork = true;
		return super.Start();
	}
	@Override
	public void Stop()
	{
		_ShouldWork = false;
		super.Stop();
	}

	@Override
	protected void OnConnectionStateChanged()
	{
		super.OnConnectionStateChanged();
		if (!_IsTryConnect)
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

		}
	}

	private boolean _IsTryConnect;

	private void TryStartAgain() throws InterruptedException {
		TryStartAgain(5000);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: private void TryStartAgain(int wait = 5000)
	private void TryStartAgain(int wait) throws InterruptedException {
		if (_IsTryConnect)
		{
			return;
		}
		_IsTryConnect = true;
		while (_ShouldWork && getState() != TerminalState.Connected)
		{
			Thread.sleep(wait);
			try
			{
				StartInternal();
			}
			catch (java.lang.Exception e)
			{
			}
		}
		_IsTryConnect = false;
	}

}