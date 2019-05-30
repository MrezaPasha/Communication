package Rasad.Core.FFMpeg;

import Rasad.Core.*;

public enum FFMpegAudioCodec
{
	/** 
	 means no audio is available
	*/
	None,

	AAC,
	PCM_ALAW,
	PCM_ULAW,
	Other;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static FFMpegAudioCodec forValue(int value)
	{
		return values()[value];
	}
}