package main.java.Rasad.Core.Services;

import Rasad.Core.*;

import java.util.*;

@FunctionalInterface
public interface CameraEventHandler
{
	void invoke(ICameraService sender, ICameraInformation camera);
}