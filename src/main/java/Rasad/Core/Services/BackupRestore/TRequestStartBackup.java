package Rasad.Core.Services.BackupRestore;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(Guid))][DataContract][ProtoContract] public class TRequestStartBackup : TMessageBase
public class TRequestStartBackup extends TMessageBase
{
	private TRequestStartBackup()
	{
	}
	public TRequestStartBackup(UUID actionKey, String creatorFullName, String description)
	{
		this.setActionKey(actionKey);
		setCreatorFullName(creatorFullName);
		setDescription(description);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public Guid ActionKey {get;set;}
	private UUID ActionKey;
	public final UUID getActionKey()
	{
		return ActionKey;
	}
	public final void setActionKey(UUID value)
	{
		ActionKey = value;
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string CreatorFullName {get;private set;}
	private String CreatorFullName;
	public final String getCreatorFullName()
	{
		return CreatorFullName;
	}
	private void setCreatorFullName(String value)
	{
		CreatorFullName = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public string Description {get;private set;}
	private String Description;
	public final String getDescription()
	{
		return Description;
	}
	private void setDescription(String value)
	{
		Description = value;
	}
}