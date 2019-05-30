package main.java.Rasad.Communication.Core;


import xyz.baddeveloper.lwsl.client.SocketClient;
import xyz.baddeveloper.lwsl.server.SocketServer;

@FunctionalInterface
public interface TMessageRecivedEventHandler<TMSG>
{
	void invoke(SocketClient socket, TMSG message);
}