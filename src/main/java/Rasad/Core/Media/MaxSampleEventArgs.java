package Rasad.Core.Media;

import NAudio.Dsp.*;
import NAudio.Wave.*;
import Rasad.Core.*;
import java.io.*;

public class MaxSampleEventArgs extends tangible.EventArgs
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerStepThrough] public MaxSampleEventArgs(float minValue, float maxValue)
	public MaxSampleEventArgs(float minValue, float maxValue)
	{
		this.setMaxSample(maxValue);
		this.setMinSample(minValue);
	}
	private float MaxSample;
	public final float getMaxSample()
	{
		return MaxSample;
	}
	private void setMaxSample(float value)
	{
		MaxSample = value;
	}
	private float MinSample;
	public final float getMinSample()
	{
		return MinSample;
	}
	private void setMinSample(float value)
	{
		MinSample = value;
	}
}