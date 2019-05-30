package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class VideoSourceRawPacketInfoReceivedEventArgs extends tangible.EventArgs
{
	private int AVCodecId;
	public final int getAVCodecId()
	{
		return AVCodecId;
	}
	public final void setAVCodecId(int value)
	{
		AVCodecId = value;
	}
	private String SProps;
	public final String getSProps()
	{
		return SProps;
	}
	public final void setSProps(String value)
	{
		SProps = value;
	}
}