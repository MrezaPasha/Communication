package main.java.Rasad.Communication.Core;

@FunctionalInterface
public interface CheckIfIsIdentitySetDelegate<TMSG, TIdentity> {
	boolean invoke(TMSG data, TIdentity sentRemotePartyIdentity, tangible.OutObject<Boolean> isDataIdentityResponse);
}