package main.java.Rasad.Communication.Core.Server;

import Rasad.Core.Services.*;
import Rasad.Core.*;
import main.java.Rasad.Communication.Core.TMessageContainer;

import java.util.*;

@FunctionalInterface
public interface MessageRecievedEventHandler<TMSG, TIdentity>
{
	void invoke(TConnectedClient sender, TMessageContainer message);
}