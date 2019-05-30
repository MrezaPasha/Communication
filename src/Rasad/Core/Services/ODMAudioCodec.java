package Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public enum ODMAudioCodec
{
	Unknown,
	Pcm,
	ULaw,
	ALaw,
	AAC;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ODMAudioCodec forValue(int value)
	{
		return values()[value];
	}
}