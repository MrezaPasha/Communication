package main.java.Rasad.Core;

/** 
 Base class for windows services.
*/
public abstract class TWindowsServiceBase
{
	public TWindowsServiceBase()
	{
		TLogManager.SetInformation(getSystemIdentity());
	}

	public abstract Object getSystemIdentity();

	/** 
	 
	 
	 @param isSimulation is start called from user interface for testing
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void StartService(bool isSimulation = false)

	public final void StartService()
	{
		StartService(false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void StartService(bool isSimulation = false)
	public final void StartService(boolean isSimulation)
	{
		if (!isSimulation)
		{
			TViewModelBase.setDesignMode(false);
			TTaskHelper.Prepare();
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			AppDomain.CurrentDomain.UnhandledException += CurrentDomain_UnhandledException;
		}
		try
		{
			StartServiceInternal();
		}
		finally
		{
			setIsServiceWorking(true);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void StopService()
	public final void StopService()
	{
		if (!getIsServiceWorking())
		{
			return;
		}
		TViewModelBase.setDesignMode(true);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		AppDomain.CurrentDomain.UnhandledException -= CurrentDomain_UnhandledException;
		try
		{
			StopServiceInternal();
		}
		finally
		{
			setIsServiceWorking(false);
		}
	}

	private void CurrentDomain_UnhandledException(Object sender, UnhandledExceptionEventArgs e)
	{
		OnUnhandledException(e);
	}

	protected void OnUnhandledException(UnhandledExceptionEventArgs e)
	{
		TLogManager.Error("Unhandled Exception !!!", e.ExceptionObject.ToStringSafe());
	}

	protected abstract void StartServiceInternal();
	protected abstract void StopServiceInternal();

	private boolean IsServiceWorking;
	public final boolean getIsServiceWorking()
	{
		return IsServiceWorking;
	}
	private void setIsServiceWorking(boolean value)
	{
		IsServiceWorking = value;
	}

	@Override
	public String toString()
	{
		return getSystemIdentity() + " IsWorking : " + getIsServiceWorking();
	}

}