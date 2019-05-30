package main.java.Rasad.Communication.Core;


import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ModifyRemotePartyIdentityMessageDelegate<TIdentity>
{

	//TODO MRREPLACE :

	// 	void invoke(TIdentity msg, boolean requestUnsentMessagesNewValue, Nullable<Integer> entityUserID);
	void invoke(TIdentity msg, boolean requestUnsentMessagesNewValue, Integer entityUserID);
}