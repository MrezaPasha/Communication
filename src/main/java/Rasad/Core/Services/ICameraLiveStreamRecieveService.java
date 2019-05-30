package main.java.Rasad.Core.Services;

import Rasad.Core.Services.*;
import Rasad.Core.*;
import java.util.*;
import java.io.*;

public interface ICameraLiveStreamRecieveService extends Closeable
{
	void UnRegister(UUID playerHostKey);

	void Register(Rasad.Core.Services.IPlayerHostSite playerHost);

	boolean IsStarted(Rasad.Core.Services.IPlayerHostSite playerHost);
}