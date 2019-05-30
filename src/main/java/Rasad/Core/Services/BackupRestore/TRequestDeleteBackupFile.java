package Rasad.Core.Services.BackupRestore;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(TBackupItem))][DataContract][ProtoContract] public class TRequestDeleteBackupFile : TMessageBase
public class TRequestDeleteBackupFile extends TMessageBase
{
	private TRequestDeleteBackupFile()
	{
	}
	public TRequestDeleteBackupFile(String backupFilePath)
	{
		this.setFilePath(backupFilePath);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public string FilePath {get;private set;}
	private String FilePath;
	public final String getFilePath()
	{
		return FilePath;
	}
	private void setFilePath(String value)
	{
		FilePath = value;
	}
	@Override
	public String toString()
	{
		return getFilePath();
	}
}