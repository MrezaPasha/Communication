package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

public enum ViewModelState
{
	UnChanged,
	Added,
	Modified,
	Deleted;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ViewModelState forValue(int value)
	{
		return values()[value];
	}
}