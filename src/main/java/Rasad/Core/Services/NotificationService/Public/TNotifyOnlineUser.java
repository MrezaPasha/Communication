package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyOnlineUser : TMessageBase
public class TNotifyOnlineUser extends TMessageBase
{
	private TNotifyOnlineUser()
	{
	}
	public TNotifyOnlineUser(int userID, String ipAddress)
	{
		this.setUserID(userID);
		this.setIpAddress(ipAddress);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int UserID {get;private set;}
	private int UserID;
	public final int getUserID()
	{
		return UserID;
	}
	private void setUserID(int value)
	{
		UserID = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string IpAddress {get;set;}
	private String IpAddress;
	public final String getIpAddress()
	{
		return IpAddress;
	}
	public final void setIpAddress(String value)
	{
		IpAddress = value;
	}
}