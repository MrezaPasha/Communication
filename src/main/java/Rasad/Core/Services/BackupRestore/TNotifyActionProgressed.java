package Rasad.Core.Services.BackupRestore;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(Guid))][DataContract][ProtoContract] public class TNotifyActionProgressed : TMessageBase
public class TNotifyActionProgressed extends TMessageBase
{
	private TNotifyActionProgressed()
	{
	} // for protobuf

	public TNotifyActionProgressed(java.util.UUID actionKey, int progress)
	{
		this(actionKey, progress, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TNotifyActionProgressed(Guid actionKey, int progress, string exception = null)
	public TNotifyActionProgressed(UUID actionKey, int progress, String exception)
	{
		this.setActionKey(actionKey);
		this.setProgress(progress);
		this.setException(exception);
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
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public int Progress {get;set;}
	private int Progress;
	public final int getProgress()
	{
		return Progress;
	}
	public final void setProgress(int value)
	{
		Progress = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public string Exception {get;set;}
	private String Exception;
	public final String getException()
	{
		return Exception;
	}
	public final void setException(String value)
	{
		Exception = value;
	}
	public final boolean getIsCompleted()
	{
		return getProgress() >= 100;
	}
}