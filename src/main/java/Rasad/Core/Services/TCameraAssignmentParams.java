package main.java.Rasad.Core.Services;

import Rasad.Core.Services.*;
import Rasad.Core.*;

public class TCameraAssignmentParams
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
	private boolean Assigned;
	public final boolean getAssigned()
	{
		return Assigned;
	}
	public final void setAssigned(boolean value)
	{
		Assigned = value;
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