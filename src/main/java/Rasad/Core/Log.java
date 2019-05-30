package main.java.Rasad.Core;

import java.io.*;

public final class Log
{
	public static void Error(Object source, String format, RuntimeException ex)
	{
		System.out.println(String.format("%1$s: %2$s - %3$s", source, format, ex));
	}

	public static void Debug(Object source, String format)
	{
		Error(source, format, null);
	}
}