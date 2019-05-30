package main.java.Rasad.Communication.Core;

@FunctionalInterface
public interface GeneratePingMessageDelegate<TMSG>
{
	TMSG invoke();
}