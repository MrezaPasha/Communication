package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

@FunctionalInterface
public interface VideoSourceYUVFrameReceivedHandler
{
	void invoke(IVideoSource sender, VideoSourceYUVFrameReceivedEventArgs e);
}