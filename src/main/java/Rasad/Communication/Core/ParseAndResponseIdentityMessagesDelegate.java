package main.java.Rasad.Communication.Core;


@FunctionalInterface
public interface ParseAndResponseIdentityMessagesDelegate<TMSG, TIdentity>
{
	void invoke(TIdentity identityMessage, tangible.OutObject<Object> parsedIdentity, tangible.OutObject<TMSG> responseRemotePartyIdentity);
}