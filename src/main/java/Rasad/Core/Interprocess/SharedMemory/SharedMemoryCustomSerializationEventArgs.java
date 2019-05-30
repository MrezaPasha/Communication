package main.java.Rasad.Core.Interprocess.SharedMemory;

import Rasad.Core.*;
import main.java.Rasad.Core.Interprocess.SharedMemory.SharedMemorySerializationAction;

public class SharedMemoryCustomSerializationEventArgs<T> extends tangible.EventArgs
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private SharedMemoryCustomSerializationEventArgs(SharedMemorySerializationAction action, byte[] dataToDeserialize, T objectToSerialize)
	private SharedMemoryCustomSerializationEventArgs(SharedMemorySerializationAction action, byte[] dataToDeserialize, T objectToSerialize)
	{
		super();
		this.setAction(action);
		this.setDataToDeserialize(dataToDeserialize);
		this.setObjectToSerialize(objectToSerialize);

		this.setHandled(false);
		this.setSerializedData(null);
		this.setDeserializedObject(null);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public SharedMemoryCustomSerializationEventArgs(byte[] data)
	public SharedMemoryCustomSerializationEventArgs(byte[] data)
	{
		this(SharedMemorySerializationAction.Deserialize, data, null);
	}
	public SharedMemoryCustomSerializationEventArgs(T o)
	{
		this(SharedMemorySerializationAction.Serialize, null, o);
	}

	private SharedMemorySerializationAction Action = SharedMemorySerializationAction.values()[0];
	public final SharedMemorySerializationAction getAction()
	{
		return Action;
	}
	private void setAction(SharedMemorySerializationAction value)
	{
		Action = value;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] DataToDeserialize;
	private byte[] DataToDeserialize;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getDataToDeserialize()
	public final byte[] getDataToDeserialize()
	{
		return DataToDeserialize;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setDataToDeserialize(byte[] value)
	private void setDataToDeserialize(byte[] value)
	{
		DataToDeserialize = value;
	}
	private T ObjectToSerialize;
	public final T getObjectToSerialize()
	{
		return ObjectToSerialize;
	}
	private void setObjectToSerialize(T value)
	{
		ObjectToSerialize = value;
	}
	private boolean Handled;
	public final boolean getHandled()
	{
		return Handled;
	}
	public final void setHandled(boolean value)
	{
		Handled = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private Byte[] SerializedData;
	private byte[] SerializedData;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public Byte[] getSerializedData()
	public final byte[] getSerializedData()
	{
		return SerializedData;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setSerializedData(Byte[] value)
	public final void setSerializedData(byte[] value)
	{
		SerializedData = value;
	}
	private T DeserializedObject;
	public final T getDeserializedObject()
	{
		return DeserializedObject;
	}
	public final void setDeserializedObject(T value)
	{
		DeserializedObject = value;
	}
}