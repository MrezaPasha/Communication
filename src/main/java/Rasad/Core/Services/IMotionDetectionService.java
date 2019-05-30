package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.ICameraInformation;

import java.io.*;

public interface IMotionDetectionService
{
	IMotionDetector CreateDetector(ICameraInformation camera, String mutexID);
}