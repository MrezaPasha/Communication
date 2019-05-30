package main.java.Rasad.Communication.Core;

@FunctionalInterface
public interface GetEntityKeyFromIdentityMessageDelegate<TIdentity>
{
	java.util.UUID invoke(TIdentity msg);
}