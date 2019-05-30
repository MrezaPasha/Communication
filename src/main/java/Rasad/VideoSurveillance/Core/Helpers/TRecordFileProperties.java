package Rasad.VideoSurveillance.Core.Helpers;

import Rasad.Core.Globalization.*;
import Rasad.VideoSurveillance.Core.*;
import java.time.*;

public class TRecordFileProperties
{
	private boolean HasRasadFlag;
	public final boolean getHasRasadFlag()
	{
		return HasRasadFlag;
	}
	public final void setHasRasadFlag(boolean value)
	{
		HasRasadFlag = value;
	}
	private long RecordFileID;
	public final long getRecordFileID()
	{
		return RecordFileID;
	}
	public final void setRecordFileID(long value)
	{
		RecordFileID = value;
	}
	private short CameraID;
	public final short getCameraID()
	{
		return CameraID;
	}
	public final void setCameraID(short value)
	{
		CameraID = value;
	}
	private String CameraName;
	public final String getCameraName()
	{
		return CameraName;
	}
	public final void setCameraName(String value)
	{
		CameraName = value;
	}
	private String MACAddress;
	public final String getMACAddress()
	{
		return MACAddress;
	}
	public final void setMACAddress(String value)
	{
		MACAddress = value;
	}
	private String IPAddress;
	public final String getIPAddress()
	{
		return IPAddress;
	}
	public final void setIPAddress(String value)
	{
		IPAddress = value;
	}
	private LocalDateTime StartDateTime = LocalDateTime.MIN;
	public final LocalDateTime getStartDateTime()
	{
		return StartDateTime;
	}
	public final void setStartDateTime(LocalDateTime value)
	{
		StartDateTime = value;
	}
	private LocalDateTime EndDateTime = LocalDateTime.MIN;
	public final LocalDateTime getEndDateTime()
	{
		return EndDateTime;
	}
	public final void setEndDateTime(LocalDateTime value)
	{
		EndDateTime = value;
	}
	private TimeSpan Duration = new TimeSpan();
	public final TimeSpan getDuration()
	{
		return Duration;
	}
	public final void setDuration(TimeSpan value)
	{
		Duration = value;
	}
	private double FrameRate;
	public final double getFrameRate()
	{
		return FrameRate;
	}
	public final void setFrameRate(double value)
	{
		FrameRate = value;
	}
	private long TotalFrames;
	public final long getTotalFrames()
	{
		return TotalFrames;
	}
	public final void setTotalFrames(long value)
	{
		TotalFrames = value;
	}
}