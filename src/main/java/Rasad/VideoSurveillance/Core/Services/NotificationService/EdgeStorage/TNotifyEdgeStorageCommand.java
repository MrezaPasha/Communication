package Rasad.VideoSurveillance.Core.Services.NotificationService.EdgeStorage;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.VideoSurveillance.Core.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(EdgeStorageCommandType))][DataContract][ProtoContract] public class TNotifyEdgeStorageCommand : TMessageBase
public class TNotifyEdgeStorageCommand extends TMessageBase
{
	private TNotifyEdgeStorageCommand()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyEdgeStorageCommand(EdgeStorageCommandType command, Nullable<byte> serverID)
	public TNotifyEdgeStorageCommand(EdgeStorageCommandType command, Byte serverID)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: base(serverID == null ? (byte)0 : serverID.Value);
		super(serverID == null ? (byte)0 : serverID.byteValue());
		this.setCommand(command);
		this.setServerID(serverID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public EdgeStorageCommandType Command {get;private set;}
	private EdgeStorageCommandType Command = EdgeStorageCommandType.values()[0];
	public final EdgeStorageCommandType getCommand()
	{
		return Command;
	}
	private void setCommand(EdgeStorageCommandType value)
	{
		Command = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public Nullable<byte> ServerID {get;private set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private Nullable<byte> ServerID;
	private Byte ServerID = null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Nullable<byte> getServerID()
	public final Byte getServerID()
	{
		return ServerID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setServerID(Nullable<byte> value)
	private void setServerID(Byte value)
	{
		ServerID = value;
	}

	@Override
	public String toString()
	{
		return "NotifyEdgeStorageCommand: Command=" + getCommand().toString() +
			", ServerID=" + getServerID().ToStringSafe();
	}
}