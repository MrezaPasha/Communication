package Rasad.Core.IO;

import NAudio.Wave.*;
import Rasad.Core.*;

public class CircularStream extends Stream
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private CircularBuffer<byte> buffer;
	private CircularBuffer<Byte> buffer;

	public CircularStream(int bufferCapacity)
	{
		super();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: buffer = new CircularBuffer<byte>(bufferCapacity);
		buffer = new CircularBuffer<Byte>(bufferCapacity);
	}

	@Override
	public long getPosition()
	{
		throw new UnsupportedOperationException();
	}
	@Override
	public void setPosition(long value)
	{
		throw new UnsupportedOperationException();
	}

	public final int getCapacity()
	{
		return buffer.getCapacity();
	}
	public final void setCapacity(int value)
	{
		buffer.setCapacity(value);
	}

	@Override
	public long getLength()
	{
		return buffer.getSize();
	}

	@Override
	public boolean getCanSeek()
	{
		return true;
	}

	@Override
	public boolean getCanRead()
	{
		return true;
	}

	@Override
	public boolean getCanWrite()
	{
		return true;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] GetBuffer()
	public final byte[] GetBuffer()
	{
		return buffer.GetBuffer();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] ToArray()
	public final byte[] ToArray()
	{
		return buffer.ToArray();
	}

	@Override
	public void Flush()
	{
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void Write(byte[] buffer, int offset, int count)
	@Override
	public void Write(byte[] buffer, int offset, int count)
	{
		this.buffer.Put(buffer, offset, count);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override void WriteByte(byte value)
	@Override
	public void WriteByte(byte value)
	{
		this.buffer.Put(value);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public override int Read(byte[] buffer, int offset, int count)
	@Override
	public int Read(byte[] buffer, int offset, int count)
	{
		return this.buffer.Get(buffer, offset, count);
	}

	@Override
	public int ReadByte()
	{
		return this.buffer.Get();
	}

	@Override
	public long Seek(long offset, SeekOrigin origin)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void SetLength(long value)
	{
		throw new UnsupportedOperationException();
	}

	public final void Clear()
	{
		this.buffer.Clear();
	}
}