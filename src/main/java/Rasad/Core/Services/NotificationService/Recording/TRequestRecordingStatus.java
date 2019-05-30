package main.java.Rasad.Core.Services.NotificationService.Recording;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TRequestRecordingStatus : TMessageBase
public class TRequestRecordingStatus extends TMessageBase
{
	private TRequestRecordingStatus()
	{
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestRecordingStatus(byte serverID, short cameraID)
	public TRequestRecordingStatus(byte serverID, short cameraID)
	{
		super(serverID, cameraID);
		setServerID(serverID);
		setCameraID(cameraID);
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
}