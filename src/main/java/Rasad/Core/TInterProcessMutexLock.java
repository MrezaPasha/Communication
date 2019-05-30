package main.java.Rasad.Core;

import java.io.*;

/** 
 Provides a wrapped way to create critical sections across multiple processes using named mutexes
*/
public class TInterProcessMutexLock implements Closeable
{
	private String _mutexName;

	private Mutex _currentMutex;

	private boolean _created;
	public final boolean getCreated()
	{
		return _created;
	}
	public final void setCreated(boolean value)
	{
		_created = value;
	}

	public TInterProcessMutexLock(String mutexName)
	{
		try
		{
			_mutexName = mutexName;

			try
			{
				_currentMutex = Mutex.OpenExisting(_mutexName);
			}
			catch (WaitHandleCannotBeOpenedException e)
			{
				// grant everyone access to the mutex
				MutexSecurity security = new MutexSecurity();
				SecurityIdentifier everyoneIdentity = new SecurityIdentifier(WellKnownSidType.WorldSid, null);
				MutexAccessRule rule = new MutexAccessRule(everyoneIdentity, MutexRights.FullControl, AccessControlType.Allow);
				security.AddAccessRule(rule);

				// make sure to not initially own it, because if you do it also acquires the lock
				// we want to explicitly attempt to acquire the lock ourselves so we know how many times
				// this object acquired and released the lock
				tangible.OutObject<Boolean> tempOut__created = new tangible.OutObject<Boolean>();
				_currentMutex = new Mutex(false, mutexName, tempOut__created, security);
			_created = tempOut__created.argValue;
			}

			AquireMutex();
		}
		catch (RuntimeException ex)
		{
			String exceptionString = String.format("Exception in InterProcessMutexLock, mutex name %1$s", mutexName);
			Log.Error(this, exceptionString, ex);
			throw ExceptionUtil.Rethrow(ex, exceptionString);
		}
	}

	private void AquireMutex()
	{
		try
		{
			_currentMutex.WaitOne();
		}
		catch (AbandonedMutexException ex)
		{
			try
			{
				Log.Error(this, "An abandoned mutex was encountered, attempting to release", ex);
				_currentMutex.ReleaseMutex();

				Log.Debug(this, "Abandonded mutex was released and now aquiring");

				_currentMutex.WaitOne();
			}
			catch (RuntimeException abandondedMutexEx)
			{
				throw ExceptionUtil.Rethrow(abandondedMutexEx, "tried to re-acquire abandoned mutex but failed");
			}
		}
		catch (RuntimeException ex)
		{
			String exceptionString = "An unexpected error occurred acquiring mutex " + _mutexName;
			throw ExceptionUtil.Rethrow(ex, exceptionString);
		}
	}


	protected final void Dispose(boolean disposing)
	{
		if (disposing)
		{
			if (_currentMutex != null)
			{
				_currentMutex.ReleaseMutex();
				_currentMutex.Dispose();
			}
		}
	}

	public final void close() throws IOException
	{
		Dispose(true);
	}
}