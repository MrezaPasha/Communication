package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

@FunctionalInterface
public interface VideoSourceRawPacketInfoReceivedHandler
{
	void invoke(IVideoSource sender, VideoSourceRawPacketInfoReceivedEventArgs e);
}