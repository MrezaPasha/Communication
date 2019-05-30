package main.java.Rasad.Communication.Core.Interprocess.SharedMemory;

import main.java.Rasad.Communication.Core.INotificationAgent;
import main.java.Rasad.Communication.Core.TMessageContainer;
import main.java.Rasad.Core.Interprocess.SharedMemory.SharedMemoryCustomSerializationEventArgs;
import main.java.Rasad.Core.Interprocess.SharedMemory.SharedMemoryMode;
import main.java.Rasad.Core.Interprocess.SharedMemory.SharedMemorySerializationAction;
import main.java.Rasad.Core.Interprocess.SharedMemory.TGeneralSharedMemoryMessageManager;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.ArrayList;
import java.util.List;

//using Rasad.Core.Services.NotificationService;


public class TVideoSurveillanceSharedMemoryMessageManager<T> extends TGeneralSharedMemoryMessageManager<T> implements INotificationAgent<T>
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor/Dtor
	public TVideoSurveillanceSharedMemoryMessageManager(String sharedMemoryId, SharedMemoryMode mode)
	{
		//TODO MRCMMENT : must uncomment
		//super(sharedMemoryId, mode);
		super(sharedMemoryId,mode,true,true);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		//this.CustomSerialization += TVideoSurveillanceSharedMemoryMessageManager_CustomSerialization;
		this.CustomSerialization.addListener(" TVideoSurveillanceSharedMemoryMessageManager_CustomSerialization", this::TVideoSurveillanceSharedMemoryMessageManager_CustomSerialization);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private void TVideoSurveillanceSharedMemoryMessageManager_CustomSerialization(Object sender, SharedMemoryCustomSerializationEventArgs<T> e)
	{
		switch (e.getAction())
		{
			case Serialize:
				if (e.getObjectToSerialize() instanceof TMessageBase)
				{
					e.setSerializedData((new TMessageContainer(new TMessageBase[] {(TMessageBase)(Object)e.getObjectToSerialize()})).ToByte());
					e.setHandled(true);
				}
				break;
			case Deserialize:
				//TODO MRCOMMENT : must uncomment
				//if (T.class == TMessageBase.class)
				if (true)
				{
					e.setHandled(true);
					TMessageContainer container = new TMessageContainer();
					List<Byte> bytes = new ArrayList<>();
					for (byte b: e.getDataToDeserialize()) {
						bytes.add(b);

					}
					//if (e.getDeserializedObject() != null && container.Recieve(e.getDataToDeserialize().ToList()))
					if (e.getDeserializedObject() != null && container.Recieve(new ArrayList<Byte>(bytes)))
					{
						Object[] message = container.GetMessage();
						if (message.length != 1)
						{
							throw new RuntimeException("Inconsistency");
						}
						//e.DeserializedObject = (T)(Object)message[0];
						e.setDeserializedObject((T) message[0]);
					}
					else
					{
						e.setDeserializedObject(null);
					}
				}
				break;
			default:
				throw new RuntimeException("Unknown serialization action");
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public final void Notify(T data)
	{
		this.SendMessage(data);
	}
}