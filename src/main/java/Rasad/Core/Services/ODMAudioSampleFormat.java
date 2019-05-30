package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public enum ODMAudioSampleFormat
{
	/*
AV_SAMPLE_FMT_NONE = -1,
AV_SAMPLE_FMT_U8,          ///< unsigned 8 bits
AV_SAMPLE_FMT_S16,         ///< signed 16 bits
AV_SAMPLE_FMT_S32,         ///< signed 32 bits
AV_SAMPLE_FMT_FLT,         ///< float
AV_SAMPLE_FMT_DBL,         ///< double

AV_SAMPLE_FMT_U8P,         ///< unsigned 8 bits, planar
AV_SAMPLE_FMT_S16P,        ///< signed 16 bits, planar
AV_SAMPLE_FMT_S32P,        ///< signed 32 bits, planar
AV_SAMPLE_FMT_FLTP,        ///< float, planar
AV_SAMPLE_FMT_DBLP,        ///< double, planar
	*/
	None(-1),
	Unsigned8(0),
	Signed16(1),
	Signed32(2),
	Float(3),
	Double(4),

	Unsigned8Planar(5),
	Signed16Planar(6),
	Signed32Planar(7),
	FloatPlanar(8),
	DoublePlanar(9);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ODMAudioSampleFormat> mappings;
	private static java.util.HashMap<Integer, ODMAudioSampleFormat> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ODMAudioSampleFormat.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ODMAudioSampleFormat>();
				}
			}
		}
		return mappings;
	}

	private ODMAudioSampleFormat(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ODMAudioSampleFormat forValue(int value)
	{
		return getMappings().get(value);
	}
}