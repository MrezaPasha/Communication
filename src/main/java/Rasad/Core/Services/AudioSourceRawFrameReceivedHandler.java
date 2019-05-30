package main.java.Rasad.Core.Services;


import Rasad.Core.*;
import java.util.*;
import java.io.*;

@FunctionalInterface
public interface AudioSourceRawFrameReceivedHandler
{
	void invoke(IVideoSource sender, byte[] audioData, ODMAudioFormat audioFrameFormat);
}