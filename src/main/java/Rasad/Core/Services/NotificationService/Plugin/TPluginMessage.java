package main.java.Rasad.Core.Services.NotificationService.Plugin;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TPluginMessage : TMessageBase
public class TPluginMessage extends TMessageBase
{
	private TPluginMessage()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TPluginMessage(Guid messageKey, byte[] data)
	public TPluginMessage(UUID messageKey, byte[] data)
	{
		this.setMessageKey(messageKey);
		this.setData(data);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TPluginMessage(string messageKey, byte[] data)
	public TPluginMessage(String messageKey, byte[] data)
	{
		this(UUID.fromString(messageKey), data);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public Guid MessageKey {get;set;}
	private UUID MessageKey;
	public final UUID getMessageKey()
	{
		return MessageKey;
	}
	public final void setMessageKey(UUID value)
	{
		MessageKey = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(2)] public byte[] Data {get;set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] Data;
	private byte[] Data;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getData()
	public final byte[] getData()
	{
		return Data;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setData(byte[] value)
	public final void setData(byte[] value)
	{
		Data = value;
	}

	@Override
	public String toString()
	{
		//return "MessageKey : " + getMessageKey().toString() + " Length : " + ((getData() == null) ? "0" : getData().length.toString());
		return "MessageKey : " + getMessageKey().toString() + " Length : " + ((getData() == null) ? "0" : Integer.toString(getData().length));
	}


}