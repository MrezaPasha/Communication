package Rasad.VideoSurveillance.Core.Services.MobileNotificationService.Public;

import ProtoBuf.*;
import Rasad.VideoSurveillance.Core.*;
import Rasad.VideoSurveillance.Core.Services.MobileNotificationService.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Serializable][ProtoContract] public class TMobileRequestConnectionPing : TMobileMessageBase
public class TMobileRequestConnectionPing extends TMobileMessageBase implements Serializable
{
	//  ** Always include a parameter-less consutructor for this class, even if be private
	public TMobileRequestConnectionPing()
	{
		this.setDummy((byte)89);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(1)][DataMember] public byte Dummy {get;set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte Dummy;
	private byte Dummy;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getDummy()
	public final byte getDummy()
	{
		return Dummy;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setDummy(byte value)
	public final void setDummy(byte value)
	{
		Dummy = value;
	}

	@Override
	public String toString()
	{
		return "Mobile Ping Request";
	}
}