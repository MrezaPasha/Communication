package main.java.Rasad.Communication.Core;


//TODO MRREPLACE: delegate to interface
//public interface TMSG[] CustomDeserializeMessagesDelegate<TMSG>(byte[] data, bool dontDeserialize = false);
//public delegate TMSG[] CustomDeserializeMessagesDelegate<TMSG>(byte[] data, bool dontDeserialize = false) where TMSG : class;
//TODO MRCOMMENT : must uncomment
@FunctionalInterface
public interface CustomSerializeMessagesDelegate<TMSG> {
    byte[] invoke(TMSG[] dataItems);
}



