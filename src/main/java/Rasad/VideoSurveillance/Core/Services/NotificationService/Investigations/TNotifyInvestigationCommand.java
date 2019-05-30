package Rasad.VideoSurveillance.Core.Services.NotificationService.Investigations;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.VideoSurveillance.Core.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(InvestigationCommandType))][DataContract][ProtoContract] public class TNotifyInvestigationCommand : TMessageBase
public class TNotifyInvestigationCommand extends TMessageBase
{
	private TNotifyInvestigationCommand()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TNotifyInvestigationCommand(InvestigationCommandType command, int investigationID, String investigationTitle, byte serverID)
	public TNotifyInvestigationCommand(InvestigationCommandType command, int investigationID, String investigationTitle, byte serverID)
	{
		super(serverID);
		this.setCommand(command);
		this.setInvestigationID(investigationID);
		this.setInvestigationTitle(investigationTitle);
		this.setServerID(serverID);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public InvestigationCommandType Command {get;private set;}
	private InvestigationCommandType Command = InvestigationCommandType.values()[0];
	public final InvestigationCommandType getCommand()
	{
		return Command;
	}
	private void setCommand(InvestigationCommandType value)
	{
		Command = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public int InvestigationID {get;private set;}
	private int InvestigationID;
	public final int getInvestigationID()
	{
		return InvestigationID;
	}
	private void setInvestigationID(int value)
	{
		InvestigationID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(3)] public String InvestigationTitle {get;private set;}
	private String InvestigationTitle;
	public final String getInvestigationTitle()
	{
		return InvestigationTitle;
	}
	private void setInvestigationTitle(String value)
	{
		InvestigationTitle = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(4)] public byte ServerID {get;private set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte ServerID;
	private byte ServerID;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getServerID()
	public final byte getServerID()
	{
		return ServerID;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setServerID(byte value)
	private void setServerID(byte value)
	{
		ServerID = value;
	}

	@Override
	public String toString()
	{
		return "NotifyInvestigationCommand: Command=" + getCommand().toString() +
			", InvestigationID=" + String.valueOf(getInvestigationID()) +
			", InvestigationTitle=" + getInvestigationTitle() +
			", ServerID=" + String.valueOf(getServerID());
	}
}