package Rasad.Core.Diagnostics;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public interface ILogUtils
{
	TraceListenerCollection getlisteners();
	void WriteEvent(String evtMsg, String evtSrc, TraceEventType evtType);
	void Flush();
	void Close();
}