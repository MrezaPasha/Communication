package Rasad.Core.Media.MediaMetadataManagement.Mpeg;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;

/** 
	Indicates the MPEG audio channel mode of a file or stream.
*/
public enum ChannelMode
{
	/** 
		Stereo
	*/
	Stereo(0),

	/** 
		Joint Stereo
	*/
	JointStereo(1),

	/** 
		Dual Channel Mono
	*/
	DualChannel(2),

	/** 
		Single Channel Mono
	*/
	SingleChannel(3);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, ChannelMode> mappings;
	private static java.util.HashMap<Integer, ChannelMode> getMappings()
	{
		if (mappings == null)
		{
			synchronized (ChannelMode.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, ChannelMode>();
				}
			}
		}
		return mappings;
	}

	private ChannelMode(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static ChannelMode forValue(int value)
	{
		return getMappings().get(value);
	}
}