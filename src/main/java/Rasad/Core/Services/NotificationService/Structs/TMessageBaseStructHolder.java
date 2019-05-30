package main.java.Rasad.Core.Services.NotificationService.Structs;


import main.java.Rasad.Core.Services.NotificationService.MessageTypeID;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

public class TMessageBaseStructHolder extends TMessageBase
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TMessageBaseStructHolder(MessageTypeID messageTypeID, byte serverID, short cameraID, object o, byte[] structSerialized)
	public TMessageBaseStructHolder(MessageTypeID messageTypeID, byte serverID, short cameraID, Object o, byte[] structSerialized)
	{
		//if (o.getClass().IsValueType)
		if (o.getClass().getTypeName() != null)
		{
			this.set_MessageTypeID(messageTypeID);
			this.set_ServerID(serverID);
			this.set_CameraID(cameraID);
			this.setStruct(o);
			this.setStructSerialized(structSerialized);
		}
		else
		{
			throw new RuntimeException("Invalid class used");
		}
	}

	private Object Struct;
	public final Object getStruct()
	{
		return Struct;
	}
	private void setStruct(Object value)
	{
		Struct = value;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] StructSerialized;
	private byte[] StructSerialized;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getStructSerialized()
	public final byte[] getStructSerialized()
	{
		return StructSerialized;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setStructSerialized(byte[] value)
	private void setStructSerialized(byte[] value)
	{
		StructSerialized = value;
	}

	@Override
	public String toString()
	{
		String retVal = String.format("_MessageTypeID : %1$s, ServerID : %2$s, CameraID : %3$s", get_MessageTypeID().toString(), get_ServerID(), get_CameraID());
		return retVal + " - " + getStruct().toString();
	}
}