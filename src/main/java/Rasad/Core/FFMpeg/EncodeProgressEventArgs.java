package Rasad.Core.FFMpeg;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class EncodeProgressEventArgs extends tangible.EventArgs
{
	private String RawOutputLine;
	public final String getRawOutputLine()
	{
		return RawOutputLine;
	}
	public final void setRawOutputLine(String value)
	{
		RawOutputLine = value;
	}
	private short FPS;
	public final short getFPS()
	{
		return FPS;
	}
	public final void setFPS(short value)
	{
		FPS = value;
	}
	private short Percentage;
	public final short getPercentage()
	{
		return Percentage;
	}
	public final void setPercentage(short value)
	{
		Percentage = value;
	}
	private long CurrentFrame;
	public final long getCurrentFrame()
	{
		return CurrentFrame;
	}
	public final void setCurrentFrame(long value)
	{
		CurrentFrame = value;
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