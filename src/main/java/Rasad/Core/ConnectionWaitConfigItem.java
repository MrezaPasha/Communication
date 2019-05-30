package main.java.Rasad.Core;

import Rasad.Core.Services.NotificationService.Public.*;
import Rasad.VideoSurveillance.Core.*;
import java.util.*;

public class ConnectionWaitConfigItem
{
	public ConnectionWaitConfigItem(int retryCount, int maxWaitMilliseconds)
	{
		this.setRetryCount(retryCount);
		this.setMaxWaitMilliseconds(maxWaitMilliseconds);
	}

	private int RetryCount;
	public final int getRetryCount()
	{
		return RetryCount;
	}
	public final void setRetryCount(int value)
	{
		RetryCount = value;
	}
	private int MaxWaitMilliseconds;
	public final int getMaxWaitMilliseconds()
	{
		return MaxWaitMilliseconds;
	}
	public final void setMaxWaitMilliseconds(int value)
	{
		MaxWaitMilliseconds = value;
	}
}