package Rasad.Core.Net.WebApi;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.io.*;
import java.nio.file.*;

public class PartialReadFileStream extends Stream
{
	private long _start;
	private long _end;
	private long _position;
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
	private FileStream _fileStream;
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.FileStream is input or output:
	public PartialReadFileStream(FileStream fileStream, long start, long end)
	{
		_start = start;
		_position = start;
		_end = end;
		_fileStream = fileStream;

		if (start > 0)
		{
			_fileStream.Seek(start, SeekOrigin.Begin);
		}
	}



	@Override
	public void Flush()
	{
		_fileStream.Flush();
	}

	@Override
	public long Seek(long offset, SeekOrigin origin)
	{
		if (origin == SeekOrigin.Begin)
		{
			_position = _start + offset;
			return _fileStream.Seek(_start + offset, origin);
		}
		else if (origin == SeekOrigin.Current)
		{
			_position += offset;
			return _fileStream.Seek(_position + offset, origin);
		}
		else
		{
			throw new UnsupportedOperationException("Seeking from SeekOrigin.End is not implemented");
		}
	}

	@Override
	public void SetLength(long value)
	{
		throw new UnsupportedOperationException();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
	@Override
	public int Read(byte[] buffer, int offset, int count)
	{
		int byteCountToRead = count;
		if (_position + count > _end)
		{
			byteCountToRead = (int)(_end - _position) + 1;
		}
		int result = _fileStream.Read(buffer, offset, byteCountToRead);
		_position += byteCountToRead;
		return result;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override IAsyncResult BeginRead(byte[] buffer, int offset, int count, AsyncCallback callback, object state)
	@Override
	public IAsyncResult BeginRead(byte[] buffer, int offset, int count, AsyncCallback callback, Object state)
	{
		int byteCountToRead = count;
		if (_position + count > _end)
		{
			byteCountToRead = (int)(_end - _position);
		}
		System.IAsyncResult result = _fileStream.BeginRead(buffer, offset, count, (s) ->
		{
												   _position += byteCountToRead;
												   callback.invoke(s);
		}, state);
		return result;
	}

	@Override
	public int EndRead(IAsyncResult asyncResult)
	{
		return _fileStream.EndRead(asyncResult);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int ReadByte()
	{
		int result = _fileStream.ReadByte();
		_position++;
		return result;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void WriteByte(byte value)
	@Override
	public void WriteByte(byte value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getCanRead()
	{
		return true;
	}

	@Override
	public boolean getCanSeek()
	{
		return true;
	}

	@Override
	public boolean getCanWrite()
	{
		return false;
	}

	@Override
	public long getLength()
	{
		return _end - _start;
	}

	@Override
	public long getPosition()
	{
		return _position;
	}
	@Override
	public void setPosition(long value)
	{
		_position = value;
		_fileStream.Seek(_position, SeekOrigin.Begin);
	}
	@Override
	protected void Dispose(boolean disposing)
	{
		if (disposing)
		{
			_fileStream.Dispose();
		}
		super.Dispose(disposing);
	}
}