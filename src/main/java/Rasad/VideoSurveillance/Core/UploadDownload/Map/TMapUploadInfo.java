package Rasad.VideoSurveillance.Core.UploadDownload.Map;

import Rasad.Core.*;
import Rasad.VideoSurveillance.Core.UploadDownload.Common.*;
import Rasad.VideoSurveillance.Core.UploadDownload.Map.Common.*;
import Rasad.VideoSurveillance.Core.*;
import java.util.*;
import java.io.*;

public class TMapUploadInfo
{
	private UUID MapID;
	public final UUID getMapID()
	{
		return MapID;
	}
	public final void setMapID(UUID value)
	{
		MapID = value;
	}
	private long MapAccessID;
	public final long getMapAccessID()
	{
		return MapAccessID;
	}
	public final void setMapAccessID(long value)
	{
		MapAccessID = value;
	}

	private String FilenameFullPath;
	public final String getFilenameFullPath()
	{
		return FilenameFullPath;
	}
	public final void setFilenameFullPath(String value)
	{
		FilenameFullPath = value;
	}
	private String FilenameRelativePath;
	public final String getFilenameRelativePath()
	{
		return FilenameRelativePath;
	}
	public final void setFilenameRelativePath(String value)
	{
		FilenameRelativePath = value;
	}
	private int Z;
	public final int getZ()
	{
		return Z;
	}
	public final void setZ(int value)
	{
		Z = value;
	}
	private int X;
	public final int getX()
	{
		return X;
	}
	public final void setX(int value)
	{
		X = value;
	}
	private int Y;
	public final int getY()
	{
		return Y;
	}
	public final void setY(int value)
	{
		Y = value;
	}
	private long FileSize;
	public final long getFileSize()
	{
		return FileSize;
	}
	public final void setFileSize(long value)
	{
		FileSize = value;
	}
}