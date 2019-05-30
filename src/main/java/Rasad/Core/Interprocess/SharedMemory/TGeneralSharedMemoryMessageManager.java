package main.java.Rasad.Core.Interprocess.SharedMemory;

import Rasad.Core.*;
import Rasad.Core.Interprocess.SharedMemory.TGeneralOneWaySharedMemoryManager;
import main.java.Rasad.Core.Interprocess.SharedMemory.SharedMemoryMode;
import tangible.EventArgs;

import java.io.*;

public class TGeneralSharedMemoryMessageManager<T> implements Closeable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields
	private boolean disposed = false;
	private SharedMemoryMode mode = SharedMemoryMode.values()[0];
	private Rasad.Core.Interprocess.SharedMemory.TGeneralOneWaySharedMemoryManager<T> sender = null;
	private Rasad.Core.Interprocess.SharedMemory.TGeneralOneWaySharedMemoryManager<T> receiver = null;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor/Dtor
	public TGeneralSharedMemoryMessageManager(String sharedMemoryId, SharedMemoryMode mode, boolean limitSimultaneousReads, boolean limitSimultaneousWrites)
	{
		this.setSharedMemoryID(sharedMemoryId);
		this.mode = mode;
		String senderID = "";
		String receiverID = "";
		switch (mode)
		{
			case Server:
			{
					senderID = sharedMemoryId + "_SENDER";
					receiverID = sharedMemoryId + "_RECEIVER";
			}
				break;
			case Client:
			{
					senderID = sharedMemoryId + "_RECEIVER";
					receiverID = sharedMemoryId + "_SENDER";
			}
				break;
		}
		sender = new Rasad.Core.Interprocess.SharedMemory.TGeneralOneWaySharedMemoryManager<T>(senderID, SharedMemoryChannel.Sender, limitSimultaneousReads, limitSimultaneousWrites);
		receiver = new TGeneralOneWaySharedMemoryManager<T>(receiverID, SharedMemoryChannel.Receiver, limitSimultaneousReads, limitSimultaneousWrites);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		//receiver.SharedMemoryNewMessage += receiver_SharedMemoryNewMessage();
		receiver.SharedMemoryNewMessage.addListener("receiver_SharedMemoryNewMessage",receiver_SharedMemoryNewMessage(this, EventArgs.Empty));

//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		receiver.CustomSerialization += DoCustomSerialization;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		sender.CustomSerialization += DoCustomSerialization;
	}

	protected void finalize() throws Throwable
	{
		Dispose(false);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<SharedMemoryNewMessageEventHandler<T>> SharedMemoryNewMessage = new tangible.Event<SharedMemoryNewMessageEventHandler<T>>();
	public tangible.Event<SharedMemoryCustomSerializationEventHandler<T>> CustomSerialization = new tangible.Event<SharedMemoryCustomSerializationEventHandler<T>>();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties
	public final boolean getIsDisposed()
	{
		return this.disposed;
	}

	private String SharedMemoryID;
	public final String getSharedMemoryID()
	{
		return SharedMemoryID;
	}
	private void setSharedMemoryID(String value)
	{
		SharedMemoryID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private void receiver_SharedMemoryNewMessage(Object sender, SharedMemoryNewMessageEventArgs<T> e)
	{
		OnSharedMemoryNewMessage(e.getMessage());
	}
	private void DoCustomSerialization(Object sender, SharedMemoryCustomSerializationEventArgs<T> e)
	{
		OnCustomSerialization(e);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods

	public final void Start()
	{
		Start(2 * 1024);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public void Start(int maxDataSize = 2 * 1024)
	public final void Start(int maxDataSize)
	{
		sender.Start(maxDataSize);
		receiver.Start(maxDataSize);
	}

	public final void Stop()
	{
		sender.Stop();
		receiver.Stop();
	}

	public final void SendMessage(T message)
	{
		sender.SendMessager(message);
	}

	public final boolean WaitSenderQueueEmpty(int timeoutMilliseconds)
	{
		return sender.WaitForMessageQueueEmpty(timeoutMilliseconds);
	}

	public final boolean TryWriteToOutputBufferImmediate(T o)
	{
		return sender.TryWriteToOutputBufferImmediate(o);
	}

	public final T TryReadFromInputBuffer()
	{
		return receiver.TryReadFromInputBuffer();
	}
	public final T TryReadFromOutputBuffer()
	{
		return sender.TryReadFromOutputBuffer();
	}
	public final void ResetBuffers()
	{
		receiver.ResetBuffer();
		sender.ResetBuffer();
	}

	public final void close() throws IOException
	{
		Dispose(true);
		GC.SuppressFinalize(this);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Protected Members
	protected void OnSharedMemoryNewMessage(T message)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = SharedMemoryNewMessage;
		if (del != null)
		{
			del(this, new SharedMemoryNewMessageEventArgs<T>(message));
		}
	}
	protected void OnCustomSerialization(SharedMemoryCustomSerializationEventArgs<T> e)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = this.CustomSerialization;
		if (del != null)
		{
			del(this, e);
		}
	}

	protected void Dispose(boolean disposing)
	{
		disposed = true;

		if (disposing)
		{
			// free managed resources
		}
		Stop();
		// free native resources if there are any.
		if (sender != null)
		{
			sender.close();
		}
		if (receiver != null)
		{
			receiver.close();
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}