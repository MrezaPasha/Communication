package main.java.Rasad.Communication.Core;

@FunctionalInterface
public interface FilterMessageOnServerDelegate<TMSG, TIdentity>
{
	boolean invoke(TMSG itemMsg, TIdentity remotePartyIdentity, Object currentSystemEntity);
}