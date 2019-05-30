package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

/** 
	 Represents a group of Disposables that are disposed together.
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public sealed class CompositeDisposable : ICollection<IDisposable>, IEnumerable<IDisposable>, IEnumerable, IDisposable
public final class CompositeDisposable implements Collection<Closeable>, java.lang.Iterable<Closeable>, java.lang.Iterable, Closeable
{
	private ArrayList<Closeable> disposables;
	private boolean disposed;

	/** 
		 Initializes a new instance of the <see cref="T:System.Reactive.Disposables.CompositeDisposable" /> class from a
		 group of disposables.
	*/
	public CompositeDisposable()
	{
		disposables = new ArrayList<Closeable>(16);
	}

	/** 
		 Initializes a new instance of the <see cref="T:System.Reactive.Disposables.CompositeDisposable" /> class from a
		 group of disposables.
	 
	 @param disposables Disposables that will be disposed together.
	*/
	public CompositeDisposable(java.lang.Iterable<Closeable> disposables)
	{
		if (disposables == null)
		{
			throw new NullPointerException("disposables");
		}
		this.disposables = new ArrayList<Closeable>(disposables);
	}

	/** 
		 Initializes a new instance of the <see cref="T:System.Reactive.Disposables.CompositeDisposable" /> class from a
		 group of disposables.
	 
	 @param disposables Disposables that will be disposed together.
	*/
	public CompositeDisposable(Closeable... disposables)
	{
		if (disposables == null)
		{
			throw new NullPointerException("disposables");
		}
		this.disposables = new ArrayList<Closeable>(Arrays.asList(disposables));
	}

	/** 
		 Initializes a new instance of the <see cref="T:System.Reactive.Disposables.CompositeDisposable" /> class with the
		 specified number of disposables.
	 
	 @param capacity The number of disposables that the new CompositeDisposable can initially store.
	*/
	public CompositeDisposable(int capacity)
	{
		if (capacity < 0)
		{
			throw new IndexOutOfBoundsException("capacity");
		}
		disposables = new ArrayList<Closeable>(capacity);
	}

	/** 
		 Gets a value that indicates whether the object is disposed.
	*/
	public boolean getIsDisposed()
	{
		return disposed;
	}

	/** 
		 Adds a disposable to the CompositeDisposable or disposes the disposable if the CompositeDisposable is disposed.
	 
	 @param item Disposable to add.
	*/
	public void Add(Closeable item)
	{
		if (item == null)
		{
			throw new NullPointerException("item");
		}
		boolean disposed = false;
		synchronized (disposables)
		{
			disposed = this.disposed;
			if (!this.disposed)
			{
				disposables.add(item);
			}
		}
		if (disposed)
		{
			item.Dispose();
		}
	}

	/** 
		 Removes and disposes all disposables from the GroupDisposable, but does not dispose the CompositeDisposable.
	*/
	public void clear()
	{
		Closeable[] disposableArray = null;
		synchronized (disposables)
		{
			disposableArray = disposables.toArray(new Closeable[0]);
			disposables.clear();
		}
		for (Closeable disposable : disposableArray)
		{
			disposable.Dispose();
		}
	}

	/** 
		 Determines whether the CompositeDisposable contains a specific disposable.
	 
	 @param item Disposable to search for.
	 @return true if the disposable was found; otherwise, false.
	*/
	public boolean contains(Object objectValue)
	{
		Closeable item = (Closeable)objectValue;
		synchronized (disposables)
		{
			return disposables.contains(item);
		}
	}

	/** 
		 Copies the disposables contained in the CompositeDisposable to an array, starting at a particular array index.
	 
	 @param array Array to copy the contained disposables to.
	 @param arrayIndex Target index at which to copy the first disposable of the group.
	*/
	public void CopyTo(Closeable[] array, int arrayIndex)
	{
		if (array == null)
		{
			throw new NullPointerException("array");
		}
		if ((arrayIndex < 0) || (arrayIndex >= array.length))
		{
			throw new IndexOutOfBoundsException("arrayIndex");
		}
		synchronized (disposables)
		{
			System.arraycopy(disposables.toArray(new Closeable[0]), 0, array, arrayIndex, array.length - arrayIndex);
		}
	}

	/** 
		 Returns an enumerator that iterates through the CompositeDisposable.
	 
	 @return An enumerator to iterate over the disposables.
	*/
	public Iterator<Closeable> iterator()
	{
		synchronized (disposables)
		{
			return ((java.lang.Iterable<Closeable>) disposables.toArray(new Closeable[0])).iterator();
		}
	}

	/** 
		 Removes and disposes the first occurrence of a disposable from the CompositeDisposable.
	 
	 @param item Disposable to remove.
	*/
	public boolean remove(Object objectValue)
	{
		Closeable item = (Closeable)objectValue;
		if (item == null)
		{
			throw new NullPointerException("item");
		}
		boolean flag = false;
		synchronized (disposables)
		{
			if (!disposed)
			{
				flag = disposables.remove(item);
			}
		}
		if (flag)
		{
			item.Dispose();
		}
		return flag;
	}

	/** 
		 Returns an enumerator that iterates through the CompositeDisposable.
	 
	 @return An enumerator to iterate over the disposables.
	*/
	public Iterator iterator()
	{
		return GetEnumerator();
	}

	/** 
		 Gets the number of disposables contained in the CompositeDisposable.
	*/
	public int size()
	{
		return disposables.size();
	}

	/** 
		 Always returns false.
	*/
	public boolean getIsReadOnly()
	{
		return false;
	}

	/** 
		 Disposes all disposables in the group and removes them from the group.
	*/
	public void close() throws IOException
	{
		Closeable[] disposableArray = null;
		synchronized (disposables)
		{
			if (!disposed)
			{
				disposed = true;
				disposableArray = disposables.toArray(new Closeable[0]);
				disposables.clear();
			}
		}
		if (disposableArray != null)
		{
			for (Closeable disposable : disposableArray)
			{
				disposable.Dispose();
			}
		}
	}
}