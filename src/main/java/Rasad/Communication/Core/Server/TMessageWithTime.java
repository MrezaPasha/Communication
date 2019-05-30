package main.java.Rasad.Communication.Core.Server;


import main.java.Rasad.Communication.Core.TMessageContainer;

public class TMessageWithTime<TMSG, TIdentity extends TMSG>
{
	public TMessageWithTime(int messageTime, TMessageContainer<TMSG, TIdentity> message, boolean isServerGeneratedMessage)
	{
		this.setMessageTime(messageTime);
		this.setMessage(message);
		this.setIsServerGeneratedMssage(isServerGeneratedMessage);
	}

	private int MessageTime;
	public final int getMessageTime()
	{
		return MessageTime;
	}
	private void setMessageTime(int value)
	{
		MessageTime = value;
	}
	private TMessageContainer<TMSG, TIdentity> Message;
	public final TMessageContainer<TMSG, TIdentity> getMessage()
	{
		return Message;
	}
	private void setMessage(TMessageContainer<TMSG, TIdentity> value)
	{
		Message = value;
	}
	private boolean IsServerGeneratedMssage;
	public final boolean getIsServerGeneratedMssage()
	{
		return IsServerGeneratedMssage;
	}
	private void setIsServerGeneratedMssage(boolean value)
	{
		IsServerGeneratedMssage = value;
	}
}