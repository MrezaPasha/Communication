package main.java.Rasad.Communication.Core.Server;

import Rasad.Core.Services.*;
import Rasad.Core.*;
import xyz.baddeveloper.lwsl.client.SocketClient;

import java.util.*;

@FunctionalInterface
public interface TClientDisconnectEventHandler<TMSG, TIdentity>
{
	void invoke(SocketClient socket);
}