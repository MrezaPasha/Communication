package main.java.Rasad.Core.Services;

import Rasad.Core.*;

public class TPluginCameraItem
{
	private short CameraID;
	public final short getCameraID()
	{
		return CameraID;
	}
	public final void setCameraID(short value)
	{
		CameraID = value;
	}
	private boolean Active;
	public final boolean getActive()
	{
		return Active;
	}
	public final void setActive(boolean value)
	{
		Active = value;
	}
}