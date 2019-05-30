package main.java.Rasad.Communication.Core;

import main.java.Rasad.Core.Services.NotificationService.Public.TRequestServerRuntimeInfomation;
import main.java.Rasad.Core.Services.NotificationService.Public.TResponseServerRuntimeInfomation;
import main.java.Rasad.Core.Services.NotificationService.Structs.TMessageBaseStructHolder;
import main.java.Rasad.Core.Services.NotificationService.Structs.TRequestCameraCurrentRecordingStatusStruct;
import main.java.Rasad.Core.Services.NotificationService.Structs.TResponseCameraCurrentRecordingStatusStruct;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;
import main.java.Rasad.Core.Services.NotificationService.TMessageRequest;
import main.java.Rasad.Core.Services.NotificationService.TMessageResponse;
import org.springframework.util.StopWatch;

import java.util.*;
import java.io.*;



public class TRequestResponseManager
{
	public static class TTwoWayMessageItem implements Closeable
	{

		public TTwoWayMessageItem(TMessageBase requestMessage, int timeoutMilliseconds)
		{
			this._timeoutMilliseconds = timeoutMilliseconds;
			_RequestMessage = requestMessage;
			setResponseMessage(null);
			if (requestMessage instanceof TMessageRequest)
			{
				_MessageRequestKey = (requestMessage instanceof TMessageRequest ? (TMessageRequest)requestMessage : null).getRequestKey();
			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Fields
		private int _timeoutMilliseconds;
		private TMessageBase _RequestMessage = null;
		private UUID _MessageRequestKey = null;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

		private boolean TimedOut;
		public final boolean getTimedOut()
		{
			return TimedOut;
		}
		private void setTimedOut(boolean value)
		{
			TimedOut = value;
		}

		public final boolean getReady()
		{
			return getResponseMessage() != null;
		}

		private TMessageBase ResponseMessage;
		public final TMessageBase getResponseMessage()
		{
			return ResponseMessage;
		}
		private void setResponseMessage(TMessageBase value)
		{
			ResponseMessage = value;
		}

		public final boolean IsResponse(TMessageBase response)
		{
			if (response instanceof TMessageResponse)
			{
				if (response instanceof TMessageResponse)
                {
                    response = null;
                }
				TMessageResponse MR = response instanceof TMessageResponse ? (TMessageResponse) response : null;
				if (_MessageRequestKey != null && MR.getRequestKey().equals(_MessageRequestKey))
				{
					this.setResponseMessage(response);
					return true;
				}
				return false;
			}
			else if (_RequestMessage instanceof TMessageBaseStructHolder && (_RequestMessage instanceof TMessageBaseStructHolder ? (TMessageBaseStructHolder)_RequestMessage : null).get_MessageTypeID() == this.getResponseMessage().get_MessageTypeID() && (response instanceof TMessageBaseStructHolder && (response instanceof TMessageBaseStructHolder ? (TMessageBaseStructHolder)response : null).get_MessageTypeID() == this.getResponseMessage().get_MessageTypeID()))
			{
				TResponseCameraCurrentRecordingStatusStruct responseStruct = (TResponseCameraCurrentRecordingStatusStruct)(response instanceof TMessageBaseStructHolder ? (TMessageBaseStructHolder)response : null).getStruct();
				TRequestCameraCurrentRecordingStatusStruct requestStruct = (TRequestCameraCurrentRecordingStatusStruct) (_RequestMessage instanceof TMessageBaseStructHolder ? (TMessageBaseStructHolder)_RequestMessage : null).getStruct();
				if (responseStruct.getCameraID() == requestStruct.getCameraID())
				{
					setResponseMessage(response);
					return true;
				}
			}
			else if (_RequestMessage instanceof TRequestServerRuntimeInfomation && response instanceof TResponseServerRuntimeInfomation)
			{
				if ((response instanceof TResponseServerRuntimeInfomation ? (TResponseServerRuntimeInfomation)response : null).getServerID() == (_RequestMessage instanceof TRequestServerRuntimeInfomation ? (TRequestServerRuntimeInfomation)_RequestMessage : null).getServerID())
				{
					setResponseMessage(response);
					return true;
				}
			}

			return false;
		}

		public final void Wait() throws Exception
		{
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
			//Stopwatch sw = Stopwatch.StartNew();
			while (!this.getReady() && stopWatch.getTotalTimeMillis() <= _timeoutMilliseconds)
			{
				Thread.sleep(1);
			}
            stopWatch.stop();
			setTimedOut(!this.getReady());
		}

		public final void close() throws IOException
		{
			_RequestMessage = null;
		}
	}

	private ArrayList<TTwoWayMessageItem> _MessagesWaitingForResponse = new ArrayList<TTwoWayMessageItem>();



	public final void Process(TMessageBase data, tangible.RefObject<Boolean> handeled)
	{
		synchronized (_MessagesWaitingForResponse)
		{
			//TODO MRCOMMENT : uncomment
			if (/*_MessagesWaitingForResponse.Any()*/ !_MessagesWaitingForResponse.isEmpty())
			{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
				//TODO MRCOMMENT : no Entity Framework here
				/*Object[] itemsDone = _MessagesWaitingForResponse.AsParallel().Where(r -> r.IsResponse(data)).ToArray();
				itemsDone.ForEach(s -> _MessagesWaitingForResponse.remove(s));
				handeled.argValue = itemsDone.Any();*/
			}
		}

	}



	//TODO MRSDDED : insert throws exception
	public final TMessageBase NotifyAndWaitForResponse(TMessageBase data) throws Exception
	{
		return NotifyAndWaitForResponse(data, 1000);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: internal TMessageBase NotifyAndWaitForResponse(TMessageBase data, int timeOutMilliseconds = 1000)

	//TODO : MRADDED : add throws exception
	public final TMessageBase NotifyAndWaitForResponse(TMessageBase data, int timeOutMilliseconds) throws Exception
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}
		if (timeOutMilliseconds <= 0)
		{
			throw new IndexOutOfBoundsException("timeOutMilliseconds");
		}


		//if (!TNotificationService.IsConnected)
		//    return null;

		TRequestResponseManager.TTwoWayMessageItem msg = RegisterForResponse(data, timeOutMilliseconds);
		//TODO MRREPLACE : convert static call
		new TNotificationService().Notify(data);
		msg.Wait();
		if (msg.getReady())
		{
			return msg.getResponseMessage();
		}
		else
		{
			//TODO MRCOMMENT : logger
			//TLogManager.Warn("Time out waiting for response to message '" + data.toString() + "'");
			return null;
		}

	}


	private TTwoWayMessageItem RegisterForResponse(TMessageBase data)
	{
		return RegisterForResponse(data, 1000);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: private TTwoWayMessageItem RegisterForResponse(TMessageBase data, int timeOutMilliseconds = 1000)
	private TTwoWayMessageItem RegisterForResponse(TMessageBase data, int timeOutMilliseconds)
	{
		synchronized (_MessagesWaitingForResponse)
		{
			tangible.ListHelper.removeAll(_MessagesWaitingForResponse, s -> s.TimedOut);

			TTwoWayMessageItem msg = new TTwoWayMessageItem(data, timeOutMilliseconds);
			_MessagesWaitingForResponse.add(msg);
			return msg;
		}
	}
}