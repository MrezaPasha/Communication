package main.java.Rasad.Core.Services.NotificationService.Streamer;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract][KnownType(typeof(CameraStreamerAddressChangeType))] public class TNotifyCameraStreamerAddressChange : TMessageBase
public class TNotifyCameraStreamerAddressChange extends TMessageBase
{
	private TNotifyCameraStreamerAddressChange()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyCameraStreamerAddressChange(byte serverID, short cameraID)
	public TNotifyCameraStreamerAddressChange(byte serverID, short cameraID)
	{
		this(serverID, cameraID, 0);
		setChangeType(CameraStreamerAddressChangeType.Remove);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyCameraStreamerAddressChange(byte serverID, short cameraID, int port)
	public TNotifyCameraStreamerAddressChange(byte serverID, short cameraID, int port)
	{
		super(serverID, cameraID);
		setCameraID(cameraID);
		setServerID(serverID);
		this.setPort(port);
		setProfileToken(null);
		setChangeType(CameraStreamerAddressChangeType.AddOrUpdate);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public short CameraID {get;private set;}
	private short CameraID;
	public final short getCameraID()
	{
		return CameraID;
	}
	private void setCameraID(short value)
	{
		CameraID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public byte ServerID {get;private set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte ServerID;
	private byte ServerID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getServerID()
	public final byte getServerID()
	{
		return ServerID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setServerID(byte value)
	private void setServerID(byte value)
	{
		ServerID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public int Port {get;private set;}
	private int Port;
	public final int getPort()
	{
		return Port;
	}
	private void setPort(int value)
	{
		Port = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public String ProfileToken {get;private set;}
	private String ProfileToken;
	public final String getProfileToken()
	{
		return ProfileToken;
	}
	private void setProfileToken(String value)
	{
		ProfileToken = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public CameraStreamerAddressChangeType ChangeType {get;private set;}
	private CameraStreamerAddressChangeType ChangeType = CameraStreamerAddressChangeType.values()[0];
	public final CameraStreamerAddressChangeType getChangeType()
	{
		return ChangeType;
	}
	private void setChangeType(CameraStreamerAddressChangeType value)
	{
		ChangeType = value;
	}

	@Override
	public String toString()
	{
		return "Streamer Change: " + getServerID() + " " + getCameraID() + " " + getPort() + " " + getChangeType().toString();
	}
}