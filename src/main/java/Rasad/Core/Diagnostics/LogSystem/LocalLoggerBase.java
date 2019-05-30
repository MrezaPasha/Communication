package Rasad.Core.Diagnostics.LogSystem;

import Rasad.Core.*;
import Rasad.Core.Diagnostics.*;

public abstract class LocalLoggerBase extends LoggerBase implements ILogger
{
	public LocalLoggerBase()
	{
		super();
		setPrefix("");
	}

	private String Prefix;
	protected final String getPrefix()
	{
		return Prefix;
	}
	private void setPrefix(String value)
	{
		Prefix = value;
	}

	private int systemEntity;
	private String systemEntityString;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private Nullable<byte> serverID = null;
	private Byte serverID = null;
	private Short cameraID = null;

	private void UpdatePrefix()
	{
		setPrefix(systemEntityString + (cameraID == null ? "" : " " + String.valueOf(cameraID.shortValue())));
	}

	public final int getSystemEntity()
	{
		return systemEntity;
	}
	public final void setSystemEntity(int value)
	{
		systemEntity = value;
		UpdatePrefix();
	}

	public final String getSystemEntityString()
	{
		return systemEntityString;
	}
	public final void setSystemEntityString(String value)
	{
		systemEntityString = value;
		UpdatePrefix();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nullable<byte> getServerID()
	public final Byte getServerID()
	{
		return serverID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setServerID(Nullable<byte> value)
	public final void setServerID(Byte value)
	{
		serverID = value;
		UpdatePrefix();
	}

	public final Short getCameraID()
	{
		return cameraID;
	}
	public final void setCameraID(Short value)
	{
		cameraID = value;
		UpdatePrefix();
	}
}