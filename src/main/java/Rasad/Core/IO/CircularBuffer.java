package Rasad.Core.IO;

import Rasad.Core.*;
import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: The interface type was changed to the closest equivalent Java type, but the methods implemented will need adjustment:
public class CircularBuffer<T> implements Collection<T>, java.lang.Iterable<T>, Collection, java.lang.Iterable
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