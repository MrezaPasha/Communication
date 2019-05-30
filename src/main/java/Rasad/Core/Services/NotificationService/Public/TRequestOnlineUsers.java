package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TRequestOnlineUsers : TMessageBase
public class TRequestOnlineUsers extends TMessageBase
{
	private TRequestOnlineUsers()
	{

	}
	public TRequestOnlineUsers(int currentUserID)
	{
		setCurrentUserID(currentUserID);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public int CurrentUserID {get;private set;}
	private int CurrentUserID;
	public final int getCurrentUserID()
	{
		return CurrentUserID;
	}
	private void setCurrentUserID(int value)
	{
		CurrentUserID = value;
	}
}