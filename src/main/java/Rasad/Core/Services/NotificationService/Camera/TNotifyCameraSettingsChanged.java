package main.java.Rasad.Core.Services.NotificationService.Camera;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(CameraChangeType))][DataContract][ProtoContract] public class TNotifyCameraSettingsChanged : TMessageBase
public class TNotifyCameraSettingsChanged extends TMessageBase
{
	private TNotifyCameraSettingsChanged()
	{
	}
	public TNotifyCameraSettingsChanged(int userID, short cameraID, String cameraTitle, CameraChangeType changeType, String ipAddress, String userInfo, boolean isSystemMessage)
	{
		super(cameraID);
		setUserID(userID);
		setCameraTitle(cameraTitle);
		setCameraID(cameraID);
		setCameraChangeType(changeType);
		setIpAddress(ipAddress);
		this.setUserInfo(userInfo);
		setIsSystemMessage(isSystemMessage);
		this.setFederationRouted(false);
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public CameraChangeType CameraChangeType {get;private set;}
	//TODO MRREPLACE
	//private CameraChangeType CameraChangeType = getCameraChangeType().values()[0];
	private CameraChangeType CameraChangeType = getCameraChangeType();
	public final CameraChangeType getCameraChangeType()
	{
		return CameraChangeType;
	}
	private void setCameraChangeType(CameraChangeType value)
	{
		CameraChangeType = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public int UserID {get;set;}
	private int UserID;
	public final int getUserID()
	{
		return UserID;
	}
	public final void setUserID(int value)
	{
		UserID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public string IpAddress {get;set;}
	private String IpAddress;
	public final String getIpAddress()
	{
		return IpAddress;
	}
	public final void setIpAddress(String value)
	{
		IpAddress = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(5)] public string CameraTitle {get;set;}
	private String CameraTitle;
	public final String getCameraTitle()
	{
		return CameraTitle;
	}
	public final void setCameraTitle(String value)
	{
		CameraTitle = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(6)] public String UserInfo {get;set;}
	private String UserInfo;
	public final String getUserInfo()
	{
		return UserInfo;
	}
	public final void setUserInfo(String value)
	{
		UserInfo = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(7)] public bool IsSystemMessage {get;set;}
	private boolean IsSystemMessage;
	public final boolean getIsSystemMessage()
	{
		return IsSystemMessage;
	}
	public final void setIsSystemMessage(boolean value)
	{
		IsSystemMessage = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(8)] public bool FederationRouted {get;set;}
	private boolean FederationRouted;
	public final boolean getFederationRouted()
	{
		return FederationRouted;
	}
	public final void setFederationRouted(boolean value)
	{
		FederationRouted = value;
	}

	public final void ResetUserIDAndCameraID(int newUserID, short newCameraID)
	{
		this.setUserID(newUserID);
		this.set_CameraID(newCameraID);
		this.setCameraID(newCameraID);
		this.setFederationRouted(true);
	}

	@Override
	public String toString()
	{
		return "Changer USER ID : " + getUserID() + " , Camera : " + getCameraID() + " , Changes : " + getCameraChangeType().toString();
	}
}