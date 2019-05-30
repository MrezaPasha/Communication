package Rasad.Core.Media;

import NAudio.Dsp.*;
import NAudio.Wave.*;
import Rasad.Core.*;
import java.io.*;

public class SampleAggregator implements ISampleProvider
{
	// volume
	public tangible.Event<tangible.EventHandler<MaxSampleEventArgs>> MaximumCalculated = new tangible.Event<tangible.EventHandler<MaxSampleEventArgs>>();
	private float maxValue;
	private float minValue;
	private int NotificationCount;
	public final int getNotificationCount()
	{
		return NotificationCount;
	}
	public final void setNotificationCount(int value)
	{
		NotificationCount = value;
	}
	private int count;

	// FFT
	public tangible.Event<tangible.EventHandler<FftEventArgs>> FftCalculated = new tangible.Event<tangible.EventHandler<FftEventArgs>>();
	private boolean PerformFFT;
	public final boolean getPerformFFT()
	{
		return PerformFFT;
	}
	public final void setPerformFFT(boolean value)
	{
		PerformFFT = value;
	}
	private Complex[] fftBuffer;
	private FftEventArgs fftArgs;
	private int fftPos;
	private int fftLength;
	private int m;
	private ISampleProvider source;

	private int channels;


	public SampleAggregator(ISampleProvider source)
	{
		this(source, 1024);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public SampleAggregator(ISampleProvider source, int fftLength = 1024)
	public SampleAggregator(ISampleProvider source, int fftLength)
	{
		channels = source.WaveFormat.Channels;
		if (!IsPowerOfTwo(fftLength))
		{
			throw new IllegalArgumentException("FFT Length must be a power of two");
		}
		this.m = (int)Math.log(fftLength, 2.0);
		this.fftLength = fftLength;
		this.fftBuffer = new Complex[fftLength];
		this.fftArgs = new FftEventArgs(fftBuffer);
		this.source = source;
	}

	private boolean IsPowerOfTwo(int x)
	{
		return (x & (x - 1)) == 0;
	}


	public final void Reset()
	{
		count = 0;
		maxValue = minValue = 0;
	}

	private void Add(float value)
	{
		if (getPerformFFT() && FftCalculated != null)
		{
			fftBuffer[fftPos].X = (float)(value * FastFourierTransform.HammingWindow(fftPos, fftLength));
			fftBuffer[fftPos].Y = 0;
			fftPos++;
			if (fftPos >= fftBuffer.length)
			{
				fftPos = 0;
				// 1024 = 2^10
				FastFourierTransform.FFT(true, m, fftBuffer);
				for (EventHandler<FftEventArgs> listener : FftCalculated.listeners())
				{
					listener.invoke(this, fftArgs);
				}
			}
		}

		maxValue = Math.max(maxValue, value);
		minValue = Math.min(minValue, value);
		count++;
		if (count >= getNotificationCount() && getNotificationCount() > 0)
		{
			if (MaximumCalculated != null)
			{
				for (EventHandler<MaxSampleEventArgs> listener : MaximumCalculated.listeners())
				{
					listener.invoke(this, new MaxSampleEventArgs(minValue, maxValue));
				}
			}
			Reset();
		}
	}

	public final WaveFormat getWaveFormat()
	{
		return source.WaveFormat;
	}

	public final int Read(float[] buffer, int offset, int count)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var samplesRead = source.Read(buffer, offset, count);

		for (int n = 0; n < samplesRead; n += channels)
		{
			Add(buffer[n + offset]);
		}
		return samplesRead;
	}
}