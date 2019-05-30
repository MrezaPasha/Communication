package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;

public class TUploadAction implements INotifyPropertyChanged, Closeable
{
	public TUploadAction(TWebApiFileTransferClient client, String filePath)
	{
		setClientFilePath(filePath);
		File file = new File(filePath);
		if (!file.exists())
		{
			throw new FileNotFoundException("File not found !!!", filePath);
		}

		String uploadRequestURI = "UploadFile";

		MultipartFormDataContent formDataContent = new MultipartFormDataContent();

		// Validate the file and add to MultipartFormDataContent object

		StreamContent FSC = new StreamContent(new TCustomStream(this, filePath));
		FSC.Headers.ContentType = new MediaTypeHeaderValue("multipart/form-data");
		FSC.Headers.ContentDisposition = new ContentDispositionHeaderValue("form-data");
		FSC.Headers.ContentDisposition.FileName = (new File(filePath)).getName();
		formDataContent.Add(FSC);

		ValidateUpload(formDataContent);

		HttpRequestMessage request = new HttpRequestMessage(HttpMethod.Post, uploadRequestURI);

		request.Content = formDataContent;

		_httpClient = new HttpClient();

		_httpClient.BaseAddress = client.getServerBaseAddress();
		_httpClient.SendAsync(request).ContinueWith(StepOne);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private HttpClient _httpClient;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte _UploadProgressPercent;
	private byte _UploadProgressPercent;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties

	private String ClientFilePath;
	public final String getClientFilePath()
	{
		return ClientFilePath;
	}
	private void setClientFilePath(String value)
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
	private RuntimeException Exception;
	public final RuntimeException getException()
	{
		return Exception;
	}
	private void setException(RuntimeException value)
	{
		Exception = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getProgressPercent()
	public final byte getProgressPercent()
	{
		return _UploadProgressPercent;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setProgressPercent(byte value)
	public final void setProgressPercent(byte value)
	{
		if (_UploadProgressPercent != value)
		{
			_UploadProgressPercent = value;
			if (ProgressChanged != null)
			{
				for (ProgressChangedEventHandler listener : ProgressChanged.listeners())
				{
					listener.invoke(this, new ProgressChangedEventArgs(_UploadProgressPercent, null));
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
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods
	protected void StepOne(Task<HttpResponseMessage> task)
	{
		if (task.Exception != null)
		{
			this.setException(task.Exception);
		}
		else if (!task.Result.IsSuccessStatusCode)
		{
			this.setException(new Exception(task.Result.ReasonPhrase));
		}
		OnCompleted();
	}
	protected void OnCompleted()
	{
		if (_httpClient != null)
		{
			_httpClient.Dispose();
			_httpClient = null;
		}
		setIsCompleted(true);
		if (Completed != null)
		{
			for (EventHandler<tangible.EventArgs> listener : Completed.listeners())
			{
				listener.invoke(this, EventArgs.Empty);
			}
		}
	}

	/** 
	 Validate upload value
	 
	 @param multipartFormDataContent MultipartFormDataContent value
	*/
	private void ValidateUpload(MultipartFormDataContent multipartFormDataContent)
	{
		double gigaSize = 0.0;

		if (multipartFormDataContent != null && multipartFormDataContent.Count() > 0)
		{
			long totalUploadFileSize = 0;
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			for (var item : multipartFormDataContent)
			{
				totalUploadFileSize += (item.Headers.ContentLength != null) ? item.Headers.ContentLength : item.Headers.ContentLength.Value;
			}

			gigaSize = Math.round((totalUploadFileSize / (1024.0 * 1024.0 * 1024.0)) * Math.pow(10, 5)) / Math.pow(10, 5);

			if (totalUploadFileSize > Integer.MAX_VALUE) // Max uploadable size is 2GB
			{
				throw new IllegalStateException(String.format("Upload data content size is (%1$s GB) which is beyond maximum allowed size(2.0 GB) !", gigaSize));
			}
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

	public static class TCustomStream extends Stream
	{
		public TCustomStream(TUploadAction uploadAction, String filePath)
		{
			_FileStream = new FileInputStream(filePath);
			_Action = uploadAction;
			;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Fields
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
		private FileStream _FileStream;
		private TUploadAction _Action;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

		private long BytesReaded;
		public final long getBytesReaded()
		{
			return BytesReaded;
		}
		private void setBytesReaded(long value)
		{
			BytesReaded = value;
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] array, int offset, int count)
		@Override
		public int Read(byte[] array, int offset, int count)
		{
			int result = _FileStream.Read(array, offset, count);
			setBytesReaded(getBytesReaded() + result);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _Action.ProgressPercent = (byte)((BytesReaded * 100) / Length);
			_Action.setProgressPercent((byte)((getBytesReaded() * 100) / getLength()));
			return result;
		}


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
			return false;
		}

		@Override
		public void Flush()
		{
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

		@Override
		public long Seek(long offset, SeekOrigin origin)
		{

			return _FileStream.Seek(offset, origin);
		}

		@Override
		public void SetLength(long value)
		{

		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
		@Override
		public void Write(byte[] buffer, int offset, int count)
		{

		}
		@Override
		public void Close()
		{
			_FileStream.Close();
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

	}


	public final void close() throws IOException
	{
		if (_httpClient != null)
		{
			_httpClient.Dispose();
			_httpClient = null;
		}
	}
}