package Rasad.Core.Interprocess.SharedMemory;

import Rasad.Core.*;
import Rasad.Core.Common.*;
import Rasad.Core.*;
import main.java.Rasad.Core.Interprocess.SharedMemory.SharedMemoryChannel;

import java.util.*;
import java.io.*;

public class TGeneralOneWaySharedMemoryManager<T> implements Closeable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constants
	private static final int DataSizeFieldLength = 4;
	private static final int MaxSenderQueueSize = 5000;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields
	private boolean disposed = false;
	private String sharedMemoryId;
	//private int size;
	private SharedMemoryChannel channel = SharedMemoryChannel.values()[0];
	private MemoryMappedFile mappedFile = null;
	private int dataSize;
	private Object lockObj = new Object();

	private EventWaitHandle incomingMessageEventWait = null;
	private EventWaitHandle incomingMessageProcessedEventWait = null;

	private EventWaitHandle outgoingMessageEventWait = null;
	private EventWaitHandle outgoingMessageProcessedEventWait = null;

	private Thread threadMessageReceiver = null;
	private Thread threadMessageSender = null;

	private boolean stopped = true;
	private LinkedList<T> messageQueue = new LinkedList<T>();

	private EventWaitHandle processorEventWaiterForRead = null; // new EventWaitHandle(true, EventResetMode.AutoReset, "SHARED_MEM_READ_DATA");
	private EventWaitHandle processorEventWaiterForWrite = null; // new EventWaitHandle(true, EventResetMode.AutoReset, "SHARED_MEM_WRITE_DATA");

	private boolean _limitSimultaneousReads, _limitSimultaneousWrites;

	private EventWaitHandleSecurity eventWaitHandleSecurity;
	private MemoryMappedFileSecurity memoryMappedFileSecurity;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor/Dtor
	public TGeneralOneWaySharedMemoryManager(String sharedMemoryId, SharedMemoryChannel channel, boolean limitSimultaneousReads, boolean limitSimultaneousWrites)
	{
		// this sid is for 'everyone' user in english version of windows. to get it regardless of windows language,
		//   use WellKnownSidType.WorldSid.
		System.Security.Principal.SecurityIdentifier sid = new System.Security.Principal.SecurityIdentifier(System.Security.Principal.WellKnownSidType.WorldSid, null);

		eventWaitHandleSecurity = new EventWaitHandleSecurity();
		eventWaitHandleSecurity.AddAccessRule(new EventWaitHandleAccessRule(sid, EventWaitHandleRights.FullControl, AccessControlType.Allow));

		memoryMappedFileSecurity = new MemoryMappedFileSecurity();
		memoryMappedFileSecurity.AddAccessRule(new AccessRule<MemoryMappedFileRights>(sid, MemoryMappedFileRights.FullControl, AccessControlType.Allow));

		this._limitSimultaneousReads = limitSimultaneousReads;
		this._limitSimultaneousWrites = limitSimultaneousWrites;

		if (_limitSimultaneousReads)
		{
			try
			{
				boolean createdNew;
				tangible.OutObject<Boolean> tempOut_createdNew = new tangible.OutObject<Boolean>();
				processorEventWaiterForRead = new EventWaitHandle(true, EventResetMode.AutoReset, "SHARED_MEM_READ_DATA", tempOut_createdNew, eventWaitHandleSecurity);
			createdNew = tempOut_createdNew.argValue;
			}
			catch (RuntimeException exp)
			{
				// may not read here if wait handle security is specified as 'everyone' full control
				TLogManager.Warn("Error occurred creating event wait handle read (" + exp.getMessage() + ") - try again with different permissions");
				try
				{
					processorEventWaiterForRead = EventWaitHandle.OpenExisting("SHARED_MEM_READ_DATA", System.Security.AccessControl.EventWaitHandleRights.Synchronize.getValue() | System.Security.AccessControl.EventWaitHandleRights.Modify.getValue());
					processorEventWaiterForRead.Set();
				}
				catch (RuntimeException exp2)
				{
					TLogManager.Error("Shared mem read sync not available", exp2);
				}
			}
		}
		if (_limitSimultaneousWrites)
		{
			try
			{
				boolean createdNew;
				tangible.OutObject<Boolean> tempOut_createdNew2 = new tangible.OutObject<Boolean>();
				processorEventWaiterForWrite = new EventWaitHandle(true, EventResetMode.AutoReset, "SHARED_MEM_WRITE_DATA", tempOut_createdNew2, eventWaitHandleSecurity);
			createdNew = tempOut_createdNew2.argValue;
			}
			catch (RuntimeException exp)
			{
				// may not read here if wait handle security is specified as 'everyone' full control
				TLogManager.Warn("Error occurred creating event wait handle write (" + exp.getMessage() + ") - try again with different permissions");
				try
				{
					processorEventWaiterForWrite = EventWaitHandle.OpenExisting("SHARED_MEM_WRITE_DATA", System.Security.AccessControl.EventWaitHandleRights.Synchronize.getValue() | System.Security.AccessControl.EventWaitHandleRights.Modify.getValue());
					processorEventWaiterForWrite.Set();
				}
				catch (RuntimeException exp2)
				{
					TLogManager.Error("Shared mem write sync not available", exp2);
				}
			}
		}

		this.sharedMemoryId = sharedMemoryId;
		this.channel = channel;
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
		///#region Private Methods
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private bool CheckCustomSerialize(T o, out byte[] serializedData)
	private boolean CheckCustomSerialize(T o, tangible.OutObject<byte[]> serializedData)
	{
		serializedData.argValue = null;
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = CustomSerialization;
		if (del != null)
		{
			SharedMemoryCustomSerializationEventArgs<T> args = new SharedMemoryCustomSerializationEventArgs<T>(o);
			del(this, args);
			if (args.getHandled())
			{
				serializedData.argValue = args.getSerializedData();
			}
			return args.getHandled();
		}
		else
		{
			return false;
		}
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private bool CheckCustomDeserialize(byte[] data, out T o)
	private boolean CheckCustomDeserialize(byte[] data, tangible.OutObject<T> o)
	{
		o.argValue = null;
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = CustomSerialization;
		if (del != null)
		{
			SharedMemoryCustomSerializationEventArgs<T> args = new SharedMemoryCustomSerializationEventArgs<T>(data);
			del(this, args);
			if (args.getHandled())
			{
				o.argValue = args.getDeserializedObject();
			}
			return args.getHandled();
		}
		else
		{
			return false;
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] Serialize(T o)
	private byte[] Serialize(T o)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] retVal;
		byte[] retVal;
		tangible.OutObject<byte[]> tempOut_retVal = new tangible.OutObject<byte[]>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (CheckCustomSerialize(o, out retVal))
		if (CheckCustomSerialize(o, tempOut_retVal))
		{
		retVal = tempOut_retVal.argValue;
			// nothing to do
		}
		else
		{
		retVal = tempOut_retVal.argValue;
			retVal = TSerializationHelper.BinarySerialize((Object)o);
		}
		return retVal;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private T Deserialize(byte[] data)
	private T Deserialize(byte[] data)
	{
		T retVal;
		tangible.OutObject<T> tempOut_retVal = new tangible.OutObject<T>();
		if (CheckCustomDeserialize(data, tempOut_retVal))
		{
		retVal = tempOut_retVal.argValue;
			// nothing to do
		}
		else
		{
		retVal = tempOut_retVal.argValue;
			retVal = (T)TSerializationHelper.BinaryDeserialize(data);
		}
		return retVal;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private T Read(out byte[] rawData)
	private T Read(tangible.OutObject<byte[]> rawData)
	{
		if (processorEventWaiterForRead != null)
		{
			processorEventWaiterForRead.WaitOne(2000);
		}
		try
		{
			if (mappedFile != null)
			{
				try (var accessor = mappedFile.CreateViewStream(1, dataSize, MemoryMappedFileAccess.Read))
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] lengthPrefixBytes = new byte[DataSizeFieldLength];
					byte[] lengthPrefixBytes = new byte[DataSizeFieldLength];
					int bytesRead = accessor.Read(lengthPrefixBytes, 0, lengthPrefixBytes.length);
					if (bytesRead != lengthPrefixBytes.length)
					{
						throw new RuntimeException("Unable to read data length field from shared memory");
					}
					int mainDataSize = BitConverter.ToInt32(lengthPrefixBytes, 0);

					if (mainDataSize <= 0)
					{
						rawData.argValue = null;
						return null;
					}
					else
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] dataBytes = new byte[mainDataSize];
						byte[] dataBytes = new byte[mainDataSize];
						bytesRead = accessor.Read(dataBytes, 0, dataBytes.length);
						if (bytesRead != dataBytes.length)
						{
							throw new RuntimeException("Shared memory unable to read requested data size");
						}
						T retVal = Deserialize(dataBytes);
						rawData.argValue = dataBytes;
						return retVal;
					}
				}
			}
			else
			{
				rawData.argValue = null;
				return null;
			}
		}
		finally
		{
			if (processorEventWaiterForRead != null)
			{
				processorEventWaiterForRead.Set();
			}
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void Write(byte[] dataBytes)
	private void Write(byte[] dataBytes)
	{
		if (processorEventWaiterForWrite != null)
		{
			processorEventWaiterForWrite.WaitOne(2000);
		}
		try
		{
			if (mappedFile != null)
			{
				try (var accessor = mappedFile.CreateViewStream(1, dataSize, MemoryMappedFileAccess.Write))
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] LengthPrefixBytes = BitConverter.GetBytes((Int32)dataBytes.Length);
					byte[] LengthPrefixBytes = BitConverter.GetBytes((int)dataBytes.length);
					Object[] finalData = LengthPrefixBytes.Concat(dataBytes).ToArray();
					accessor.Write(finalData, 0, finalData.length);
				}
			}
		}
		finally
		{
			if (processorEventWaiterForWrite != null)
			{
				processorEventWaiterForWrite.Set();
			}
		}
	}

	private void Write(T v)
	{
		this.Write(Serialize(v));
	}

	private void DoMessageReceiver()
	{
		//incomingMessageProcessedEventWait.Set();

		while (!stopped)
		{
			// wait for incoming message
			incomingMessageEventWait.WaitOne();
			if (stopped)
			{
				break;
			}
			incomingMessageEventWait.Reset();

			// read incoming message
			T msg = null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] msgSerialized = null;
			byte[] msgSerialized = null;
			boolean error = false;
			try
			{
				tangible.OutObject<byte[]> tempOut_msgSerialized = new tangible.OutObject<byte[]>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: msg = Read(out msgSerialized);
				msg = Read(tempOut_msgSerialized);
			msgSerialized = tempOut_msgSerialized.argValue;
			}
			catch (RuntimeException exp)
			{
				error = true;
				TLogManager.Error("Error reading and processing data in shared memory", exp);
			}

			// signal to sender
			incomingMessageProcessedEventWait.Set();

			// raise event
			// when stopping counter part from another process, the wait flag is set. So check if msg is not null.
			if (!error)
			{
				if (msg == null)
				{
					TLogManager.Info("Null message parsed in MessageManager Message Receiver");
				}
				else
				{
					T msgTemp = msg;
					//TLogManager.LogToHDD("* Message Received");
					tangible.Action0Param action = () ->
					{
								try
								{
									OnSharedMemoryNewMessage(msgTemp);
								}
								catch (RuntimeException exp)
								{
									TLogManager.Error("Error in shared memory message event", exp);
								}
					};
					action.invoke();
				}
			}
		}
	}

	private void DoMessageSender()
	{
		while (!stopped)
		{
			// check for any message in message queue
			T message = null;
			boolean messageAvailable = false;
			synchronized (messageQueue)
			{
				if (!messageQueue.isEmpty())
				{
					message = messageQueue.poll();
					messageAvailable = true;
				}
			}
			if (messageAvailable)
			{
				// write to shared memory
				boolean error = false;
				try
				{
					Write(message);
				}
				catch (RuntimeException exp)
				{
					error = true;
					TLogManager.Error("Error writing to shared memory", exp);
				}

				if (!error)
				{
					// signal sender
					outgoingMessageEventWait.Set();
				}

				// wait until message is processed
				outgoingMessageProcessedEventWait.WaitOne();
				outgoingMessageProcessedEventWait.Reset();
			}
			else
			{
				Thread.sleep(1);
			}
		}
	}

	private void EnsureNotStopped()
	{
		if (stopped)
		{
			throw new RuntimeException("Shared memory manager not started");
		}
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
		synchronized (lockObj)
		{
			if (!stopped)
			{
				throw new RuntimeException("Already started");
			}

			messageQueue.clear();
			if (maxDataSize <= 0)
			{
				throw new IndexOutOfBoundsException("maxDataSize must be positive");
			}
			dataSize = maxDataSize;

			dataSize += DataSizeFieldLength;

			stopped = false;
			try
			{
				if (channel == SharedMemoryChannel.Receiver)
				{
					// this is message receiver
					mappedFile = MemoryMappedFile.CreateOrOpen(sharedMemoryId, dataSize, MemoryMappedFileAccess.ReadWrite, MemoryMappedFileOptions.None, memoryMappedFileSecurity, System.IO.HandleInheritability.None);

					boolean createdNew;
					tangible.OutObject<Boolean> tempOut_createdNew = new tangible.OutObject<Boolean>();
					incomingMessageEventWait = new EventWaitHandle(false, EventResetMode.ManualReset, sharedMemoryId + "_EVENT_IN", tempOut_createdNew, eventWaitHandleSecurity);
				createdNew = tempOut_createdNew.argValue;
					tangible.OutObject<Boolean> tempOut_createdNew2 = new tangible.OutObject<Boolean>();
					incomingMessageProcessedEventWait = new EventWaitHandle(false, EventResetMode.ManualReset, sharedMemoryId + "_EVENT_DONE", tempOut_createdNew2, eventWaitHandleSecurity);
				createdNew = tempOut_createdNew2.argValue;

					threadMessageReceiver = new Thread(() ->
					{
								try
								{
									DoMessageReceiver();
								}
								finally
								{
									threadMessageReceiver = null;
								}
					}, 100);
					threadMessageReceiver.Name = "DoMessageReceiver() - " + sharedMemoryId;
					threadMessageReceiver.IsBackground = true;
					threadMessageReceiver.start();
				}
				else if (channel == SharedMemoryChannel.Sender)
				{
					// this is message sender
					mappedFile = MemoryMappedFile.CreateOrOpen(sharedMemoryId, dataSize, MemoryMappedFileAccess.ReadWrite, MemoryMappedFileOptions.None, memoryMappedFileSecurity, System.IO.HandleInheritability.None);

					boolean createdNew;
					tangible.OutObject<Boolean> tempOut_createdNew3 = new tangible.OutObject<Boolean>();
					outgoingMessageEventWait = new EventWaitHandle(false, EventResetMode.ManualReset, sharedMemoryId + "_EVENT_IN", tempOut_createdNew3, eventWaitHandleSecurity);
				createdNew = tempOut_createdNew3.argValue;
					tangible.OutObject<Boolean> tempOut_createdNew4 = new tangible.OutObject<Boolean>();
					outgoingMessageProcessedEventWait = new EventWaitHandle(false, EventResetMode.ManualReset, sharedMemoryId + "_EVENT_DONE", tempOut_createdNew4, eventWaitHandleSecurity);
				createdNew = tempOut_createdNew4.argValue;

					threadMessageSender = new Thread(() ->
					{
							try
							{
								DoMessageSender();
							}
							finally
							{
								threadMessageSender = null;
							}
					}, 100);
					threadMessageSender.Name = "DoMessageSender() - " + sharedMemoryId;
					threadMessageSender.IsBackground = true;
					threadMessageSender.start();
				}
				else
				{
					throw new RuntimeException("Unknown shared memory mode specified.");
				}
			}
			catch (java.lang.Exception e)
			{
				stopped = true;
				throw e;
			}
		}
	}
	public final void Stop()
	{
		synchronized (lockObj)
		{
			if (stopped)
			{
				return;
			}
			stopped = true;

			if (mappedFile != null)
			{
				mappedFile.Dispose();
				mappedFile = null;
			}

			if (threadMessageReceiver != null)
			{
				try
				{
					incomingMessageEventWait.Set();
					Thread th = threadMessageReceiver;
					if (th != null && Thread.currentThread().ManagedThreadId != th.ManagedThreadId)
					{
						while (threadMessageReceiver != null)
						{
							Thread.sleep(5);
						}
					}
				}
				catch (RuntimeException exp)
				{
					TLogManager.Error("Error stopping message receiver thread", exp);
				}
			}
			if (threadMessageSender != null)
			{
				try
				{
					outgoingMessageProcessedEventWait.Set();
					Thread th = threadMessageSender;
					if (th != null && Thread.currentThread().ManagedThreadId != th.ManagedThreadId)
					{
						while (threadMessageSender != null)
						{
							Thread.sleep(5);
						}
					}
				}
				catch (RuntimeException exp)
				{
					TLogManager.Error("Error stopping message sender thread", exp);
				}
			}

			if (incomingMessageEventWait != null)
			{
				incomingMessageEventWait.Dispose();
				incomingMessageEventWait = null;
			}
			if (incomingMessageProcessedEventWait != null)
			{
				incomingMessageProcessedEventWait.Dispose();
				incomingMessageProcessedEventWait = null;
			}
			if (outgoingMessageEventWait != null)
			{
				outgoingMessageEventWait.Dispose();
				outgoingMessageEventWait = null;
			}
			if (outgoingMessageProcessedEventWait != null)
			{
				outgoingMessageProcessedEventWait.Dispose();
				outgoingMessageProcessedEventWait = null;
			}
		}
	}

	public final void SendMessager(T message)
	{
		EnsureNotStopped();
		if (channel != SharedMemoryChannel.Sender)
		{
			throw new RuntimeException("Unable to send message in this mode - " + channel.toString());
		}
		synchronized (messageQueue)
		{
			int dropCount = 0;
			while (messageQueue.size() >= MaxSenderQueueSize)
			{
				messageQueue.poll();
				dropCount++;
			}
			if (dropCount > 0)
			{
				TLogManager.Warn("Shared mem send queue full - dropped " + String.valueOf(dropCount));
			}
			messageQueue.offer(message);
		}
	}
	public final boolean WaitForMessageQueueEmpty(int timeoutMilliseconds)
	{
		boolean retVal = false;
		int waitStartTick = Environment.TickCount;
		while (true)
		{
			synchronized (messageQueue)
			{
				if (messageQueue.isEmpty())
				{
					retVal = true;
					break;
				}
			}
			Thread.sleep(5);
			if (TCommonUtilities.ElapsedTickCount(waitStartTick) >= timeoutMilliseconds)
			{
				break;
			}
		}
		return retVal;
	}

	public final boolean TryWriteToOutputBufferImmediate(T o)
	{
		try
		{
			Write(o);
			return true;
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error writing to shared memory", exp);
			return false;
		}
	}
	public final T TryReadFromInputBuffer()
	{
		EnsureNotStopped();
		if (channel != SharedMemoryChannel.Receiver)
		{
			throw new RuntimeException("Cannot read in this mode - " + channel.toString());
		}
		try
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] messageRawData;
			byte[] messageRawData;
			tangible.OutObject<byte[]> tempOut_messageRawData = new tangible.OutObject<byte[]>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return Read(out messageRawData);
			T tempVar = Read(tempOut_messageRawData);
		messageRawData = tempOut_messageRawData.argValue;
		return tempVar;
		}
		catch (RuntimeException exp)
		{
			return null;
		}
	}
	public final T TryReadFromOutputBuffer()
	{
		EnsureNotStopped();
		if (channel != SharedMemoryChannel.Sender)
		{
			throw new RuntimeException("Cannot read in this mode - " + channel.toString());
		}
		try
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] messageRawData;
			byte[] messageRawData;
			tangible.OutObject<byte[]> tempOut_messageRawData = new tangible.OutObject<byte[]>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return Read(out messageRawData);
			T tempVar = Read(tempOut_messageRawData);
		messageRawData = tempOut_messageRawData.argValue;
		return tempVar;
		}
		catch (RuntimeException exp)
		{
			return null;
		}
	}
	public final void ResetBuffer()
	{
		EnsureNotStopped();

		if (mappedFile != null)
		{
			try (var accessor = mappedFile.CreateViewStream(1, dataSize, MemoryMappedFileAccess.Write))
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] resetData = new byte[dataSize];
				byte[] resetData = new byte[dataSize];
				Array.Clear(resetData, 0, resetData.length);
				accessor.Write(resetData, 0, resetData.length);
			}
		}
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
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if(DEBUG)
		System.out.println("* New Message: " + message.toString());
//#endif
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = SharedMemoryNewMessage;
		if (del != null)
		{
			del(this, new SharedMemoryNewMessageEventArgs<T>(message));
		}
	}

	protected void Dispose(boolean disposing)
	{
		disposed = true;

		if (disposing)
		{
			// free managed resources
			Stop();

			if (processorEventWaiterForRead != null)
			{
				processorEventWaiterForRead.Dispose();
			}
			if (processorEventWaiterForWrite != null)
			{
				processorEventWaiterForWrite.Dispose();
			}
		}
		// free native resources if there are any.
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}