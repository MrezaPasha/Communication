package Rasad.VideoSurveillance.Core.Services.NotificationService.NetworkStorage;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.VideoSurveillance.Core.*;

public enum NetworkStorageActionType
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ویرایش")][EnumMember] EditNetworkStorage,
	EditNetworkStorage,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("حـذف")][EnumMember] DeleteNetworkStorage,
	DeleteNetworkStorage,

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("افزودن")][EnumMember] AddNetworkStorage
	AddNetworkStorage;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static NetworkStorageActionType forValue(int value)
	{
		return values()[value];
	}
}