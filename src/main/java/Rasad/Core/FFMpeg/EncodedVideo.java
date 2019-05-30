package Rasad.Core.FFMpeg;

import Rasad.Core.*;

public class EncodedVideo
{
	private String EncodedVideoPath;
	public final String getEncodedVideoPath()
	{
		return EncodedVideoPath;
	}
	public final void setEncodedVideoPath(String value)
	{
		EncodedVideoPath = value;
	}
	private String ThumbnailPath;
	public final String getThumbnailPath()
	{
		return ThumbnailPath;
	}
	public final void setThumbnailPath(String value)
	{
		ThumbnailPath = value;
	}
	private String EncodingLog;
	public final String getEncodingLog()
	{
		return EncodingLog;
	}
	public final void setEncodingLog(String value)
	{
		EncodingLog = value;
	}
	private boolean Success;
	public final boolean getSuccess()
	{
		return Success;
	}
	public final void setSuccess(boolean value)
	{
		Success = value;
	}
}