package main.java.Rasad.Communication.Core;

@FunctionalInterface
public interface NotificationMessageEventHandler<TMSG>
{
	void invoke(TMSG data);
}