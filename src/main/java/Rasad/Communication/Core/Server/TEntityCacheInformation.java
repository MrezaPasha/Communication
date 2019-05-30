package main.java.Rasad.Communication.Core.Server;

import Rasad.Core.*;
import main.java.Rasad.Communication.Core.TCommunicationConfig;
import main.java.Rasad.Core.Common.TCommonUtilities;
import main.java.Rasad._core.TimeSpan;

import java.util.*;

public class TEntityCacheInformation<TMSG, TIdentity extends TMSG>
{
	private boolean isConnected = false;
	//private int disconnectTime = Environment.TickCount;
	Long totalElapsedTime = System.currentTimeMillis();
	private int disconnectTime = totalElapsedTime.intValue();
	private Object internalLockObj = new Object();

	public TEntityCacheInformation(UUID entityKey)
	{
		this.setEntityKey(entityKey);
		setMessages(new ArrayList<TMessageWithTime<TMSG, TIdentity>>());
		setSyncObject(new Object());
	}

	private UUID EntityKey;
	public final UUID getEntityKey()
	{
		return EntityKey;
	}
	private void setEntityKey(UUID value)
	{
		EntityKey = value;
	}
	private ArrayList<TMessageWithTime<TMSG, TIdentity>> Messages;
	public final ArrayList<TMessageWithTime<TMSG, TIdentity>> getMessages()
	{
		return Messages;
	}
	private void setMessages(ArrayList<TMessageWithTime<TMSG, TIdentity>> value)
	{
		Messages = value;
	}
	private Object SyncObject;
	public final Object getSyncObject()
	{
		return SyncObject;
	}
	private void setSyncObject(Object value)
	{
		SyncObject = value;
	}

	public final void SetConnectionState(boolean connected)
	{
		synchronized (internalLockObj)
		{
			if (connected != isConnected)
			{
				//disconnectTime = Environment.TickCount;
				disconnectTime = new Long(totalElapsedTime.intValue()).intValue();
			}
			this.isConnected = connected;
		}
	}
	public final boolean CleanUpOldMessages()
	{
		if (!getMessages().isEmpty())
		{
			int beforCleanupCount = getMessages().size();
			int maxCacheCount = 500000;
			if (beforCleanupCount > maxCacheCount)
			{
				getMessages().subList(0, getMessages().size() - maxCacheCount).clear();
			}
			else
			{
				tangible.ListHelper.removeAll(getMessages(), m -> TCommonUtilities.ElapsedTickCount(m.getMessageTime()) >= TCommunicationConfig.CommunicationServerMessageCacheTimeMilliseconds);
			}
			int afterCleanupCount = getMessages().size();
			if (beforCleanupCount != afterCleanupCount)
			{
				class AnonymousType
				{
					public int Count;
					public java.util.UUID EntityKey;

					public AnonymousType(int _Count, java.util.UUID _EntityKey)
					{
						Count = _Count;
						EntityKey = _EntityKey;
					}
				}
				//TODO MRCOMMENT : logger

				//TLogManager.Debug("Some messages clean-uped", AnonymousType(beforCleanupCount - afterCleanupCount, getEntityKey()));
				return true;
			}
		}
		return false;
	}

	public final boolean getIsExpired()
	{
		if (isConnected)
		{
			return false;
		}
		else
		{
			return TCommonUtilities.ElapsedTickCount(disconnectTime) > TimeSpan.FromHours(30).Milliseconds();
		}
	}
}