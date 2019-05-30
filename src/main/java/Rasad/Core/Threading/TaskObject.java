package main.java.Rasad.Core.Threading;

import Rasad.Core.*;

public class TaskObject<T>
{
	private tangible.Action1Param<T> Action;
	public final tangible.Action1Param<T> getAction()
	{
		return Action;
	}
	public final void setAction(tangible.Action1Param<T> value)
	{
		Action = (T obj) -> value.invoke(obj);
	}

	private tangible.Action1Param<T> Failed;
	public final tangible.Action1Param<T> getFailed()
	{
		return Failed;
	}
	public final void setFailed(tangible.Action1Param<T> value)
	{
		Failed = (T obj) -> value.invoke(obj);
	}

	private T State;
	public final T getState()
	{
		return State;
	}
	public final void setState(T value)
	{
		State = value;
	}

	private CancellationToken CancellationToken = new CancellationToken();
	public final CancellationToken getCancellationToken()
	{
		return CancellationToken;
	}
	public final void setCancellationToken(CancellationToken value)
	{
		CancellationToken = value;
	}

	private int TimeoutInMs;
	public final int getTimeoutInMs()
	{
		return TimeoutInMs;
	}
	public final void setTimeoutInMs(int value)
	{
		TimeoutInMs = value;
	}
}