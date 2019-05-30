package main.java.Rasad.Communication.Core;


import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface PrepareEntityIdentityDelegate<TMSG, TIdentity>
{

	//TODO MRREPLACE :

	TIdentity invoke(Object systemEntity, boolean requestUnsentMessages, Integer entityUserID);
	//TIdentity invoke(Object systemEntity, boolean requestUnsentMessages, Nullable<Inte> entityUserID);
}