package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.ICameraInformation;

import java.io.*;

public interface IMotionDetector extends Closeable
{
//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	event EventHandler MotionDetected;
	ICameraInformation getCamera();
}