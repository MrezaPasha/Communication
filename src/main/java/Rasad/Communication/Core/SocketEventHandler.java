package main.java.Rasad.Communication.Core;

import java.net.ServerSocket;

@FunctionalInterface
public interface SocketEventHandler
{
	void invoke(ServerSocket socket);
}