package main.java.Rasad.Core;

import Rasad.Core.Services.NotificationService.Public.*;
import Rasad.VideoSurveillance.Core.*;
import java.util.*;

public enum VersionTarget
{
	Rasad,
	Sepah,
	Hafezan,
	SepidBam;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static VersionTarget forValue(int value)
	{
		return values()[value];
	}
}