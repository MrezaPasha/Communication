package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;

public class TDownloadAction implements INotifyPropertyChanged
{
	public TDownloadAction(TWebApiFileTransferClient client, String serverfileName, String clientFileName)
	{
		setServerFilePath(serverfileName);
		setClientFilePath(clientFileName);
		String actionURL = "DownloadFile?fileName=" + HttpUtility.HtmlEncode(serverfileName);
		httpClient = new HttpClient();

		httpClient.BaseAddress = client.getServerBaseAddress();
		HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Get, actionURL);

		httpClient.SendAsync(request, HttpCompletionOption.ResponseHeadersRead).ContinueWith(OnResponseRecieved);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private HttpClient httpClient;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte _DownloadProgressPercent;
	private byte _DownloadProgressPercent;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	private String ServerFilePath;
	public final String getServerFilePath()
	{
		return ServerFilePath;
	}
	public final void setServerFilePath(String value)
	{
		ServerFilePath = value;
	}
	private String ClientFilePath;
	public final String getClientFilePath()
	{
		return ClientFilePath;
	}
	public final void setClientFilePath(String value)
	{
		ClientFilePath = value;
	}
	private boolean IsCompleted;
	public final boolean getIsCompleted()
	{
		return IsCompleted;
	}
	private void setIsCompleted(boolean value)
	{
		IsCompleted = value;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getProgressPercent()
	public final byte getProgressPercent()
	{
		return _DownloadProgressPercent;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setProgressPercent(byte value)
	public final void setProgressPercent(byte value)
	{
		if (_DownloadProgressPercent != value)
		{
			_DownloadProgressPercent = value;
			if (ProgressChanged != null)
			{
				for (ProgressChangedEventHandler listener : ProgressChanged.listeners())
				{
					listener.invoke(this, new ProgressChangedEventArgs(_DownloadProgressPercent, null));
				}
			}
			if (PropertyChanged != null)
			{
				for (PropertyChangedEventHandler listener : PropertyChanged.listeners())
				{
					listener.invoke(this, new PropertyChangedEventArgs("ProgressPercent"));
				}
			}
		}
	}
	private RuntimeException Exception;
	public final RuntimeException getException()
	{
		return Exception;
	}
	private void setException(RuntimeException value)
	{
		Exception = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods
	protected void OnResponseRecieved(Task<HttpResponseMessage> task)
	{
		if (task.Result.IsSuccessStatusCode)
		{
			System.TTaskHelper.ContinueWith(SaveContentToFileAsync(task.Result.Content, getClientFilePath()), _ -> OnComplete());
		}
		else
		{
			this.setException((task.Exception != null) ? task.Exception : new Exception(task.Result.ReasonPhrase));
			OnComplete();
		}
	}

	protected void OnComplete()
	{
		if (httpClient != null)
		{
			httpClient.Dispose();
			httpClient = null;
		}
		setIsCompleted(true);
		if (Completed != null)
		{
			for (EventHandler<tangible.EventArgs> listener : Completed.listeners())
			{
				listener.invoke(this, new EventArgs());
			}
		}
	}

	/** 
	 Read content of file
	 
	 @param content Content value
	 @param fileFullPath FileFullPath value
	 @return Awaitable task value
	*/
	protected Task SaveContentToFileAsync(HttpContent content, String fileFullPath)
	{
		TCustomStream fileStream = null;
		boolean result = false;
		try
		{
			File F = new File(fileFullPath);
			if (!F.getParentFile().exists())
			{
				F.getParentFile().Create();
			}
			fileStream = new TCustomStream(this, fileFullPath, content.Headers.ContentDisposition.Size.Value);
			return content.CopyToAsync(fileStream).ContinueWith((copyTask) ->
			{
					fileStream.Close();
					result = !result;
			});

		}
		catch (DirectoryNotFoundException directoryNotFoundException)
		{
			throw directoryNotFoundException;
		}
		catch (IllegalStateException invalidOperationException)
		{
			throw invalidOperationException;
		}
		catch (Exception exception)
		{
			throw new Exception("Unable to read content ", exception);
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<ProgressChangedEventHandler> ProgressChanged = new tangible.Event<ProgressChangedEventHandler>();
	public tangible.Event<PropertyChangedEventHandler> PropertyChanged = new tangible.Event<PropertyChangedEventHandler>();
	public tangible.Event<tangible.EventHandler<tangible.EventArgs>> Completed = new tangible.Event<tangible.EventHandler<tangible.EventArgs>>();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private static class TCustomStream extends Stream
	{
		public TCustomStream(TDownloadAction action, String fileName, long length)
		{
			_Action = action;
			_Length = length;
			_FileStream = new FileOutputStream(fileName);
		}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Fields
		private long _Length;
		private TDownloadAction _Action;
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
		private FileStream _FileStream;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion


		@Override
		public boolean getCanRead()
		{
			return _FileStream.CanRead;
		}

		@Override
		public boolean getCanSeek()
		{
			return _FileStream.CanSeek;
		}

		@Override
		public boolean getCanWrite()
		{
			return _FileStream.CanWrite;
		}

		@Override
		public void Flush()
		{
			_FileStream.Flush();
		}

		@Override
		public long getLength()
		{
			return _FileStream.Length;
		}

		@Override
		public long getPosition()
		{
			return _FileStream.Position;
		}
		@Override
		public void setPosition(long value)
		{
			_FileStream.Position = value;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
		@Override
		public int Read(byte[] buffer, int offset, int count)
		{
			return _FileStream.Read(buffer, offset, count);
		}

		@Override
		public long Seek(long offset, SeekOrigin origin)
		{
			return _FileStream.Seek(offset, origin);
		}

		@Override
		public void SetLength(long value)
		{
			_FileStream.SetLength(value);

		}
		private long _ByteWrited;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
		@Override
		public void Write(byte[] buffer, int offset, int count)
		{
			_FileStream.Write(buffer, offset, count);
			_ByteWrited += count;
			if (_Action != null)
			{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _Action.ProgressPercent = (byte)((_ByteWrited * 100) / _Length);
				_Action.setProgressPercent((byte)((_ByteWrited * 100) / _Length));
			}
		}
		@Override
		protected void Dispose(boolean disposing)
		{
			if (_FileStream != null)
			{
				_FileStream.Dispose();
				_FileStream = null;
			}
			super.Dispose(disposing);
		}
		@Override
		public void Close()
		{
			_FileStream.Close();
			super.Close();
		}

	}

}