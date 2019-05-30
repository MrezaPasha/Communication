package Rasad.VideoSurveillance.Core.UploadDownload.Map.Common;

import Rasad.VideoSurveillance.Core.*;
import Rasad.VideoSurveillance.Core.UploadDownload.Map.*;

public enum MapUploadCommand
{
	UploadMapFile,
	DownloadMapFile;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static MapUploadCommand forValue(int value)
	{
		return values()[value];
	}
}