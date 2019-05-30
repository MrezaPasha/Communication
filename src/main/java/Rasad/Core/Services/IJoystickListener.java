package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.time.*;

public interface IJoystickListener
{
	ICameraInformation getCameraInformation();
	boolean getIsSelected();
}