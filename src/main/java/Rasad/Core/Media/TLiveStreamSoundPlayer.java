package Rasad.Core.Media;

import NAudio.Codecs.*;
import NAudio.Wave.*;
import Rasad.Core.Services.*;
import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

public class TLiveStreamSoundPlayer implements Closeable
{
	private boolean disposed = false;
	private ODMAudioFormat odmAudioFormat = null;
	//private DirectSoundOut waveOut = null;
	private WaveOutEvent waveOut = null;
	private CircularStream circularStream = null;
	private Object lockObj = new Object();
	private int playDelayMilliseconds;
	private LocalDateTime firstChunkTime = null;
	private boolean playActive = false;


	public TLiveStreamSoundPlayer(ODMAudioFormat odmAudioFormat)
	{
		this(odmAudioFormat, 0);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TLiveStreamSoundPlayer(ODMAudioFormat odmAudioFormat, int playDelayMilliseconds = 0)
	public TLiveStreamSoundPlayer(ODMAudioFormat odmAudioFormat, int playDelayMilliseconds)
	{
		if (odmAudioFormat == null)
		{
			throw new NullPointerException("odmAudioFormat");
		}
		this.odmAudioFormat = odmAudioFormat;
		if (playDelayMilliseconds < 0)
		{
			playDelayMilliseconds = 0;
		}
		this.playDelayMilliseconds = playDelayMilliseconds;
		this.playActive = playDelayMilliseconds == 0;
	}

	protected void finalize() throws Throwable
	{
		Dispose(false);
	}

	private void InitNAudio()
	{
		if (waveOut == null)
		{
			waveOut = new WaveOutEvent();
			int sampleRate = odmAudioFormat.getSampleRate();
			int channels = odmAudioFormat.getChannels();
			int bitsPerSample = odmAudioFormat.getBitsPerSample();
			int blockAlign = 2;
			int averageBytesPerSecond = sampleRate * blockAlign;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#region Create WaveFormat
			WaveFormat waveFormat;
			//waveFormat = WaveFormat.CreateCustomFormat(WaveFormatEncoding.Pcm, sampleRate, channels, averageBytesPerSecond, blockAlign, 16);
			switch (odmAudioFormat.getSampleFormat())
			{
				case Double:
				case DoublePlanar:
					waveFormat = WaveFormat.CreateCustomFormat(WaveFormatEncoding.IeeeFloat, sampleRate, channels, averageBytesPerSecond, blockAlign, bitsPerSample);
					break;
				case Float:
				case FloatPlanar:
					waveFormat = WaveFormat.CreateIeeeFloatWaveFormat(sampleRate, channels);
					break;
				case None:
					throw new UnsupportedOperationException("no format");
				case Signed16:
				case Signed16Planar:
				case Signed32:
				case Signed32Planar:
				case Unsigned8:
				case Unsigned8Planar:
					//blockAlign = (short)(channels * (bitsPerSample / 8));
					//averageBytesPerSecond = sampleRate * blockAlign;
					//waveFormat = new WaveFormat(sampleRate, bitsPerSample, channels);
					// *NOTE: don't use bitrate - it is automatically calculated to a correct value inside NAudio WaveFormat class
					waveFormat = new WaveFormat(sampleRate, channels);
					//waveFormat = new WaveFormat(sampleRate, bitsPerSample, channels);
					//waveFormat = WaveFormat.CreateCustomFormat(WaveFormatEncoding.Pcm, sampleRate, channels, averageBytesPerSecond, blockAlign, bitsPerSample);
					break;
				default:
					waveFormat = WaveFormat.CreateIeeeFloatWaveFormat(sampleRate, channels);
					break;
			}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
				///#endregion

			//circularStream = new CircularStream(averageBytesPerSecond * 16);
			//circularStream = new CircularStream(10 * 1024 * 1024);  // 10 MB
			circularStream = new CircularStream(4 * 1024 * 1024); // 4 MB
			RawSourceWaveStream rawSource = new RawSourceWaveStream(circularStream, waveFormat);
			waveOut.Init(rawSource);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void AddChunk(byte[] data)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] public void AddChunk(byte[] data)
	public final void AddChunk(byte[] data)
	{
		//if (waveOut == null)
		//    return;

		try
		{
			if (firstChunkTime == null)
			{
				firstChunkTime = LocalDateTime.now();
			}

			InitNAudio();

			if (data != null && data.length > 0)
			{
				switch (odmAudioFormat.getAudioCodec())
				{
					case Pcm:
					{
							circularStream.Write(data, 0, data.length);
					}
						break;
					case ULaw:
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (var dataByte in data)
							for (byte dataByte : data)
							{
								short value = MuLawDecoder.MuLawToLinearSample(dataByte);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: circularStream.WriteByte((byte)(value & 0x00FF));
								circularStream.WriteByte((byte)(value & 0x00FF));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: circularStream.WriteByte((byte)(value >> 8));
								circularStream.WriteByte((byte)(value >> 8));
							}
					}
						break;
					case ALaw:
					{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (var dataByte in data)
							for (byte dataByte : data)
							{
								short value = ALawDecoder.ALawToLinearSample(dataByte);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: circularStream.WriteByte((byte)(value & 0x00FF));
								circularStream.WriteByte((byte)(value & 0x00FF));
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: circularStream.WriteByte((byte)(value >> 8));
								circularStream.WriteByte((byte)(value >> 8));
							}
					}
						break;
					case AAC:
					{
							//circularStream.Write(data, 0, data.Length);
					}
						break;
				}
			}

			if (!playActive)
			{
				if (this.playDelayMilliseconds > 0)
				{
					TimeSpan ts = LocalDateTime.now() - firstChunkTime.getValue();
					if (ts.TotalMilliseconds < 0 || ts.TotalMilliseconds >= playDelayMilliseconds)
					{
						playActive = true;
					}
				}
				else
				{
					playActive = true;
				}
			}

			{
			//if (waveOut.PlaybackState != PlaybackState.Playing && !played)
				//played = true;
				if (playActive)
				{
					waveOut.Play();
				}
			}
		}
		catch (java.lang.Exception e) // (Exception exp)
		{
			//System.Windows.MessageBox.Show(exp.ToString());
		}
	}

	public final void close() throws IOException
	{
		Dispose(true);
		GC.SuppressFinalize(this);
	}

	protected void Dispose(boolean disposing)
	{
		if (disposed)
		{
			return;
		}

		if (disposing)
		{
			disposed = true;
			// free managed resources
		}
		// free native resources if there are any.
		if (waveOut != null)
		{
			waveOut.Stop();
			waveOut.Dispose();
			waveOut = null;
			circularStream.Dispose();
			circularStream = null;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Internal Class
//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
	public static class CircularBuffer<T> implements Collection<T>, java.lang.Iterable<T>, Collection, java.lang.Iterable
	{
		private int capacity;
		private int size;
		private int head;
		private int tail;
		private T[] buffer;

		private transient Object syncRoot;

		public CircularBuffer(int capacity)
		{
			this(capacity, false);
		}

		public CircularBuffer(int capacity, boolean allowOverflow)
		{
			if (capacity < 0)
			{
				throw new IllegalArgumentException("capacity must be greater than or equal to zero.", "capacity");
			}

			this.capacity = capacity;
			size = 0;
			head = 0;
			tail = 0;
//C# TO JAVA CONVERTER WARNING: Java does not allow direct instantiation of arrays of generic type parameters:
//ORIGINAL LINE: buffer = new T[capacity];
			buffer = (T[])new Object[capacity];
			setAllowOverflow(allowOverflow);
		}

		private boolean AllowOverflow;
		public final boolean getAllowOverflow()
		{
			return AllowOverflow;
		}
		public final void setAllowOverflow(boolean value)
		{
			AllowOverflow = value;
		}

		public final int getCapacity()
		{
			return capacity;
		}
		public final void setCapacity(int value)
		{
			if (value == capacity)
			{
				return;
			}

			if (value < size)
			{
				throw new IndexOutOfBoundsException("value", "value must be greater than or equal to the buffer size.");
			}

//C# TO JAVA CONVERTER WARNING: Java does not allow direct instantiation of arrays of generic type parameters:
//ORIGINAL LINE: var dst = new T[value];
			T[] dst = (T[])new Object[value];
			if (size > 0)
			{
				CopyTo(dst);
			}
			buffer = dst;

			capacity = value;
		}

		public final int getSize()
		{
			return size;
		}

		public final boolean contains(Object objectValue)
		{
			T item = (T)objectValue;
			int bufferIndex = head;
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var comparer = EqualityComparer<T>.Default;
			for (int i = 0; i < size; i++, bufferIndex++)
			{
				if (bufferIndex == capacity)
				{
					bufferIndex = 0;
				}

				if (item == null && buffer[bufferIndex] == null)
				{
					return true;
				}
				else if ((buffer[bufferIndex] != null) && buffer[bufferIndex].equals(item))
				{
					return true;
				}
			}

			return false;
		}

		public final void clear()
		{
			size = 0;
			head = 0;
			tail = 0;
		}

		public final int Put(T[] src)
		{
			return Put(src, 0, src.length);
		}

		public final int Put(T[] src, int offset, int count)
		{
			int realCount = getAllowOverflow() ? count : Math.min(count, capacity - size);
			int srcIndex = offset;
			for (int i = 0; i < realCount; i++, tail++, srcIndex++)
			{
				if (tail == capacity)
				{
					tail = 0;
				}
				buffer[tail] = src[srcIndex];
			}
			size = Math.min(size + realCount, capacity);
			return realCount;
		}

		public final void Put(T item)
		{
			if (!getAllowOverflow() && size == capacity)
			{
				throw new InternalBufferOverflowException("Buffer is full.");
			}

			if (tail == buffer.length) // !!!!! Why? MM
			{
				tail = 0;
			}

			buffer[tail] = item;
			if (tail++ == capacity)
			{
				tail = 0;
			}
			size++;
		}

		public final void Skip(int count)
		{
			head += count;
			if (head >= capacity)
			{
				head -= capacity;
			}
		}

		public final T[] Get(int count)
		{
//C# TO JAVA CONVERTER WARNING: Java does not allow direct instantiation of arrays of generic type parameters:
//ORIGINAL LINE: var dst = new T[count];
			T[] dst = (T[])new Object[count];
			Get(dst);
			return dst;
		}

		public final int Get(T[] dst)
		{
			return Get(dst, 0, dst.length);
		}

		public final int Get(T[] dst, int offset, int count)
		{
			int realCount = Math.min(count, size);
			int dstIndex = offset;
			for (int i = 0; i < realCount; i++, head++, dstIndex++)
			{
				if (head == capacity)
				{
					head = 0;
				}
				dst[dstIndex] = buffer[head];
			}
			size -= realCount;
			return realCount;
		}

		public final T Get()
		{
			if (size == 0)
			{
				throw new IllegalStateException("Buffer is empty.");
			}

			T item = buffer[head];
			if (head++ == capacity)
			{
				head = 0;
			}
			size--;
			return item;
		}

		public final void CopyTo(T[] array)
		{
			CopyTo(array, 0);
		}

		public final void CopyTo(T[] array, int arrayIndex)
		{
			CopyTo(0, array, arrayIndex, size);
		}

		public final void CopyTo(int index, T[] array, int arrayIndex, int count)
		{
			if (count > size)
			{
				throw new IndexOutOfBoundsException("count", "count cannot be greater than the buffer size.");
			}

			int bufferIndex = head;
			for (int i = 0; i < count; i++, bufferIndex++, arrayIndex++)
			{
				if (bufferIndex == capacity)
				{
					bufferIndex = 0;
				}
				array[arrayIndex] = buffer[bufferIndex];
			}
		}

		public final Iterator<T> iterator()
		{
			int bufferIndex = head;
			for (int i = 0; i < size; i++, bufferIndex++)
			{
				if (bufferIndex == capacity)
				{
					bufferIndex = 0;
				}

//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
				yield return buffer[bufferIndex];
			}
		}

		public final T[] GetBuffer()
		{
			return buffer;
		}

		public final T[] ToArray()
		{
//C# TO JAVA CONVERTER WARNING: Java does not allow direct instantiation of arrays of generic type parameters:
//ORIGINAL LINE: var dst = new T[size];
			T[] dst = (T[])new Object[size];
			CopyTo(dst);
			return dst;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region ICollection<T> Members

		public final int size()
		{
			return getSize();
		}

		public final boolean getIsReadOnly()
		{
			return false;
		}

		public final void Add(T item)
		{
			Put(item);
		}

		public final boolean remove(Object objectValue)
		{
			T item = (T)objectValue;
			if (size == 0)
			{
				return false;
			}

			Get();
			return true;
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region IEnumerable<T> Members

		public final Iterator<T> GetEnumerator()
		{
			if (this instanceof IEnumerable)
				return IEnumerable_GetEnumerator();
			else if (this instanceof IEnumerable)
				return IEnumerable_GetEnumerator();
			else
				throw new UnsupportedOperationException("No interface found.");
		}

		private Iterator<T> IEnumerable_GetEnumerator()
		{
			return GetEnumerator();
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region ICollection Members

		public final int size()
		{
			return getSize();
		}

		public final boolean getIsSynchronized()
		{
			return false;
		}

		public final Object getSyncRoot()
		{
			if (syncRoot == null)
			{
				tangible.RefObject<Object> tempRef_syncRoot = new tangible.RefObject<Object>(syncRoot);
				Interlocked.CompareExchange(tempRef_syncRoot, new Object(), null);
			syncRoot = tempRef_syncRoot.argValue;
			}
			return syncRoot;
		}

		public final void CopyTo(Array array, int arrayIndex)
		{
			CopyTo((T[])array, arrayIndex);
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region IEnumerable Members

		private Iterator IEnumerable_GetEnumerator()
		{
			return (Iterator)GetEnumerator();
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}
	public static class CircularStream extends Stream
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
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}