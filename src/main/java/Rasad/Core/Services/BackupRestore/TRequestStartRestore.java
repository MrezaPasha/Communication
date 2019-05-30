package main.java.Rasad.Core.Services.BackupRestore;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(Guid))][DataContract][ProtoContract] public class TRequestStartRestore : TMessageBase
public class TRequestStartRestore extends TMessageBase
{
	private TRequestStartRestore()
	{
	}
	public TRequestStartRestore(UUID actionKey, String backupFilePath)
	{
		this.setActionKey(actionKey);
		this.setFilePath(backupFilePath);
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public string FilePath {get;private set;}
	private String FilePath;
	public final String getFilePath()
	{
		return FilePath;
	}
	private void setFilePath(String value)
	{
		FilePath = value;
	}
}