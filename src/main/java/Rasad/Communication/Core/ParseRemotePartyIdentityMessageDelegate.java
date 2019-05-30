package main.java.Rasad.Communication.Core;


@FunctionalInterface
public interface ParseRemotePartyIdentityMessageDelegate<TIdentity>
{
	void invoke(TIdentity msg, tangible.OutObject<CommunicationEntityLifetime> lifeTime, tangible.OutObject<Boolean> RequestUnsentMessages);
}