package Rasad.Core.FFMpeg;

import Rasad.Core.*;
import java.io.*;

public class VideoFile
{
	private String _Path;
	public final String getPath()
	{
		return _Path;
	}
	public final void setPath(String value)
	{
		_Path = value;
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
	private double BitRate;
	public final double getBitRate()
	{
		return BitRate;
	}
	public final void setBitRate(double value)
	{
		BitRate = value;
	}
	private double VideoBitRate;
	public final double getVideoBitRate()
	{
		return VideoBitRate;
	}
	public final void setVideoBitRate(double value)
	{
		VideoBitRate = value;
	}
	private double AudioBitRate;
	public final double getAudioBitRate()
	{
		return AudioBitRate;
	}
	public final void setAudioBitRate(double value)
	{
		AudioBitRate = value;
	}
	private String RawAudioFormat;
	public final String getRawAudioFormat()
	{
		return RawAudioFormat;
	}
	public final void setRawAudioFormat(String value)
	{
		RawAudioFormat = value;
	}
	private String AudioFormat;
	public final String getAudioFormat()
	{
		return AudioFormat;
	}
	public final void setAudioFormat(String value)
	{
		AudioFormat = value;
	}
	private FFMpegAudioCodec AudioCodec = FFMpegAudioCodec.values()[0];
	public final FFMpegAudioCodec getAudioCodec()
	{
		return AudioCodec;
	}
	public final void setAudioCodec(FFMpegAudioCodec value)
	{
		AudioCodec = value;
	}
	private String RawVideoFormat;
	public final String getRawVideoFormat()
	{
		return RawVideoFormat;
	}
	public final void setRawVideoFormat(String value)
	{
		RawVideoFormat = value;
	}
	private String VideoFormat;
	public final String getVideoFormat()
	{
		return VideoFormat;
	}
	public final void setVideoFormat(String value)
	{
		VideoFormat = value;
	}
	private int Height;
	public final int getHeight()
	{
		return Height;
	}
	public final void setHeight(int value)
	{
		Height = value;
	}
	private int Width;
	public final int getWidth()
	{
		return Width;
	}
	public final void setWidth(int value)
	{
		Width = value;
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
	private String RawInfo;
	public final String getRawInfo()
	{
		return RawInfo;
	}
	public final void setRawInfo(String value)
	{
		RawInfo = value;
	}
	private boolean infoGathered;
	public final boolean getinfoGathered()
	{
		return infoGathered;
	}
	public final void setinfoGathered(boolean value)
	{
		infoGathered = value;
	}

	public VideoFile(String path)
	{
		_Path = path;
		Initialize();
	}
	private void Initialize()
	{
		this.setinfoGathered(false);
		if (tangible.StringHelper.isNullOrEmpty(_Path))
		{
			throw new RuntimeException("Video file Path not set or empty.");
		}
		if (!System.TStringHelper.IsURL(_Path))
		{
			if (!(new File(_Path)).isFile())
			{
				throw new RuntimeException("The video file " + _Path + " does not exist.");
			}
		}
	}
}