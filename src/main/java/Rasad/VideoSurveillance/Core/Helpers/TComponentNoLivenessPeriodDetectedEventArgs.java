package Rasad.VideoSurveillance.Core.Helpers;

import Rasad.VideoSurveillance.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

public static class TComponentNoLivenessPeriodDetectedEventArgs extends tangible.EventArgs
{
	public TComponentNoLivenessPeriodDetectedEventArgs(LocalDateTime startTime, LocalDateTime endTime)
	{
		super();
		this.StartTime = startTime;
		this.EndTime = endTime;
	}

	private LocalDateTime StartTime = LocalDateTime.MIN;
	public final LocalDateTime getStartTime()
	{
		return StartTime;
	}
	public final void setStartTime(LocalDateTime value)
	{
		StartTime = value;
	}
	private LocalDateTime EndTime = LocalDateTime.MIN;
	public final LocalDateTime getEndTime()
	{
		return EndTime;
	}
	public final void setEndTime(LocalDateTime value)
	{
		EndTime = value;
	}
}
}
