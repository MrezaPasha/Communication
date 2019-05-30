package main.java.Rasad.Core.Services.NotificationService.DesktopCollabration;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TResponseDesktopCollabration : TMessageBase
public class TResponseDesktopCollabration extends TMessageBase
{
	private TResponseDesktopCollabration()
	{

	}
	public TResponseDesktopCollabration(boolean confirmed, int requestUser, int responseUser, String[] ipAddresses)
	{
		this.setConfirmed(confirmed);
		this.setRequestUser(requestUser);
		this.setResponseUser(responseUser);
		this.setIPAddresses(ipAddresses);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public bool Confirmed {get;private set;}
	private boolean Confirmed;
	public final boolean getConfirmed()
	{
		return Confirmed;
	}
	private void setConfirmed(boolean value)
	{
		Confirmed = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public int RequestUser {get;private set;}
	private int RequestUser;
	public final int getRequestUser()
	{
		return RequestUser;
	}
	private void setRequestUser(int value)
	{
		RequestUser = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public int ResponseUser {get;private set;}
	private int ResponseUser;
	public final int getResponseUser()
	{
		return ResponseUser;
	}
	private void setResponseUser(int value)
	{
		ResponseUser = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public string[] IPAddresses {get;set;}
	private String[] IPAddresses;
	public final String[] getIPAddresses()
	{
		return IPAddresses;
	}
	public final void setIPAddresses(String[] value)
	{
		IPAddresses = value;
	}
}