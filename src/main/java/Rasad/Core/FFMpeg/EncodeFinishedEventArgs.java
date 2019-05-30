package Rasad.Core.FFMpeg;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class EncodeFinishedEventArgs extends tangible.EventArgs
{
	private EncodedVideo EncodedVideoInfo;
	public final EncodedVideo getEncodedVideoInfo()
	{
		return EncodedVideoInfo;
	}
	public final void setEncodedVideoInfo(EncodedVideo value)
	{
		EncodedVideoInfo = value;
	}
}