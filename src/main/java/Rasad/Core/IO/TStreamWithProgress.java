package Rasad.Core.IO;

import Rasad.Core.*;
import java.io.*;

public class TStreamWithProgress extends Stream implements Serializable
{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	public TStreamWithProgress(Stream stream)
	{
		this._Stream = stream;
		OnProgressChanged();
	}

	private void OnProgressChanged()
	{
		if (ProgressChanged != null)
		{
			for (EventHandler<ProgressChangedEventArgs> listener : ProgressChanged.listeners())
			{
				listener.invoke(this, new ProgressChangedEventArgs(ProgressedPercent, null));
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
	private Stream _Stream;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<tangible.EventHandler<ProgressChangedEventArgs>> ProgressChanged = new tangible.Event<tangible.EventHandler<ProgressChangedEventArgs>>();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion


	public final int getProgressedPercent()
	{
		if (getLength() > 0)
		{
			return (int)((getPosition() * 100) / getLength());
		}
		return 100;
	}

	@Override
	public boolean getCanRead()
	{
		return _Stream.CanRead;
	}

	@Override
	public boolean getCanSeek()
	{
		return _Stream.CanSeek;
	}

	@Override
	public long getLength()
	{
		return _Stream.Length;
	}

	@Override
	public long getPosition()
	{
		return _Stream.Position;
	}
	@Override
	public void setPosition(long value)
	{
		_Stream.Position = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
	@Override
	public int Read(byte[] buffer, int offset, int count)
	{
		int result = _Stream.Read(buffer, offset, count);
		OnProgressChanged();
		return result;
	}

	@Override
	public long Seek(long offset, SeekOrigin origin)
	{
		return _Stream.Seek(offset, origin);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Writing Support
	@Override
	public boolean getCanWrite()
	{
		return false;
	}

	@Override
	public void Flush()
	{
		throw new RuntimeException("The method or operation is not implemented.");
	}
	@Override
	public void SetLength(long value)
	{
		throw new RuntimeException("The method or operation is not implemented.");
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
	{
		throw new RuntimeException("The method or operation is not implemented.");
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}
//public class ProgressChangedEventArgs : EventArgs
//{
//    public long BytesRead { get; private set; }
//    public long Length { get; private set; }

//    public ProgressChangedEventArgs(long BytesRead, long Length)
//    {
//        this.BytesRead = BytesRead;
//        this.Length = Length;
//    }
//    public override string ToString()
//    {
//        return string.Format("Total : {0} , Readed : {1}", Length.ToFileSize(), BytesRead.ToFileSize());
//    }
//}
