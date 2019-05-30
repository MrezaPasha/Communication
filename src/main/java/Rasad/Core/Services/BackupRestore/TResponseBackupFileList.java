package Rasad.Core.Services.BackupRestore;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(TBackupItem))][DataContract][ProtoContract] public class TResponseBackupFileList : TMessageBase
public class TResponseBackupFileList extends TMessageBase
{
	private TResponseBackupFileList()
	{
	}
	public TResponseBackupFileList(java.lang.Iterable<TBackupItem> items)
	{
		setItems(items.ToArray());
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public TBackupItem[] Items {get;private set;}
	private TBackupItem[] Items;
	public final TBackupItem[] getItems()
	{
		return Items;
	}
	private void setItems(TBackupItem[] value)
	{
		Items = value;
	}
}