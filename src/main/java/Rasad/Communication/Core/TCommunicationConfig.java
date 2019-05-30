package main.java.Rasad.Communication.Core;

import main.java.Rasad.Core.Services.NotificationService.Public.SystemEntity;

public final class TCommunicationConfig<TMSG, TIdentity extends TMSG> {
    static {
        setDebuggingCommunicationService(false);
    }

    public static int CommunicationPingIntervalMilliseconds = 5000;

    public static GeneratePingMessageDelegate/*<TMSG>*/ GeneratePingMessageHandler = null;
    public static CustomDeserializeMessagesDelegate/*<TMSG>*/ CustomDeserializeMessagesHandler = null;
    public static CustomSerializeMessagesDelegate/*<TMSG>*/ CustomSerializeMessagesHandler = null;
    public static ParseAndResponseIdentityMessagesDelegate/*<TMSG, TIdentity>*/ ParseAndResponseIdentityMessagesHandler = null;
    //public static PrepareEntityIdentityDelegate</*TMSG, TIdentity>*/ PrepareEntityIdentityDelegate = null;
    public static PrepareEntityIdentityDelegate/*<TMSG, TIdentity>*/ PrepareEntityIdentityHandler = null;

    public static GetEntityKeyFromIdentityMessageDelegate/*<TIdentity>*/ GetEntityKeyFromIdentityMessageHandler = null;
    public static CheckIfIsIdentitySetDelegate/*<TMSG, TIdentity>*/ CheckIfIsIdentitySetHandler = null;
    public static ParseRemotePartyIdentityMessageDelegate/*<TIdentity>*/ ParseRemotePartyIdentityMessageHandler = null;
    public static ModifyRemotePartyIdentityMessageDelegate/*<TIdentity>*/ ModifyRemotePartyIdentityMessageHandler = null;
    public static FilterMessageOnServerDelegate/*<TMSG, TIdentity>*/ FilterMessageOnServerHandler = null;


    //public static GetCommunicationServiceSeverAddressDelegate GetCommunicationServiceSeverAddressHandler = null;

    public static <TMSG> TMSG GetNewPingMessage() {
        if (GeneratePingMessageHandler.getClass() != null) {
            return (TMSG) GeneratePingMessageHandler.invoke();
        } else {
            throw new RuntimeException("Ping message handler not set");
        }
    }


    //TODO MRCHNAGE : static field remove
    public TMSG[] CustomDeserializeMessages(byte[] data) {
        return CustomDeserializeMessages(data, false);
    }

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: internal static TMSG[] CustomDeserializeMessages(byte[] data, bool dontDeserialize = false)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
    //TODO MRCHNAGE : static field remove

    public TMSG[] CustomDeserializeMessages(byte[] data, boolean dontDeserialize) {
        if (CustomDeserializeMessagesHandler != null) {
            //TODO MRCOMMENT : must uncomment
            //return CustomDeserializeMessagesHandler(data, dontDeserialize);
            return null;
        } else {
            throw new RuntimeException("Custom deserialization handler not set.");
        }
    }

    //C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: internal static byte[] CustomDeserializeMessages(TMSG[] dataItems)
    public static <TMSG> byte[] CustomDeserializeMessages(TMSG[] dataItems) {
        if (CustomSerializeMessagesHandler != null) {
            return CustomSerializeMessagesHandler.invoke(dataItems);
        } else {
            throw new RuntimeException("Custom serialization handler not set.");
        }
    }

    public static <TIdentity, TMSG> void ParseAndResponseIdentityMessages(TIdentity identityMessage, tangible.OutObject<Object> parsedIdentity, tangible.OutObject<TMSG> responseRemotePartyIdentity) {
        if (ParseAndResponseIdentityMessagesHandler != null) {
            ParseAndResponseIdentityMessagesHandler.invoke(identityMessage, parsedIdentity, responseRemotePartyIdentity);
        } else {
            throw new RuntimeException("ParseAndResponseIdentityMessages handler not set!");
        }
    }

    public static <TIdentity> TIdentity PrepareEntityIdentity(SystemEntity systemEntity, boolean requestUnsentMessages, Integer entityUserID) {
        if (PrepareEntityIdentityHandler != null) {
            //TODO MREDIT : test runtime
            return (TIdentity) PrepareEntityIdentityHandler.invoke(systemEntity, requestUnsentMessages, entityUserID);
        } else {
            throw new RuntimeException("PrepareEntityIdentityHandler not set!");
        }
    }

    public static Object DefaultSystemEntity = null;

    public static int CommunicationServerMessageCacheTimeMilliseconds = 30 * 60 * 1000;
    public static boolean DebuggingCommunicationService;

    public static boolean getDebuggingCommunicationService() {
        return DebuggingCommunicationService;
    }

    public static void setDebuggingCommunicationService(boolean value) {
        DebuggingCommunicationService = value;
    }

    public static int CommunicationPortNumber = 8221;
}
