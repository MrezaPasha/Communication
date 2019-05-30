package main.java.Rasad.Core.Services.NotificationService.Public;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

/** 
 When in client system settings is saved and changed, this notification arises.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TNotifyGlobalSettingsChanged : TMessageBase
public class TNotifyGlobalSettingsChanged extends TMessageBase
{
	public TNotifyGlobalSettingsChanged()
	{
		this(null);
	}

	public TNotifyGlobalSettingsChanged(String settingKey)
	{
		this.setSettingKey(settingKey);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public String SettingKey {get;set;}
	private String SettingKey;
	public final String getSettingKey()
	{
		return SettingKey;
	}
	public final void setSettingKey(String value)
	{
		SettingKey = value;
	}

	@Override
	public String toString()
	{
		//TODO MRREPLACE
		//return "Notify Global Settings Changed - SettingKey: " + (getSettingKey().IsNullOrEmpty() ? "{N/A}" : getSettingKey());
		return "Notify Global Settings Changed - SettingKey: " + (getSettingKey().isEmpty() ? "{N/A}" : getSettingKey());
	}
}