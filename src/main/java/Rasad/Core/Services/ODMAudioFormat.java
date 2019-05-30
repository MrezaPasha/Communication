package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import Rasad.Core.Services.ODMAudioCodec;

import java.util.*;
import java.io.*;

public class ODMAudioFormat
{
	public ODMAudioFormat(ODMAudioCodec audioCodec, int sampleRate, int channels, ODMAudioSampleFormat sampleFormat, int numSamples, int bitsPerSample)
	{
		setAudioCodec(audioCodec);
		setSampleRate(sampleRate);
		setChannels(channels);
		this.setSampleFormat(sampleFormat);
		this.setNumSamples(numSamples);
		this.setBitsPerSample(bitsPerSample);
	}

	private ODMAudioCodec AudioCodec = ODMAudioCodec.values()[0];
	public final ODMAudioCodec getAudioCodec()
	{
		return AudioCodec;
	}
	private void setAudioCodec(ODMAudioCodec value)
	{
		AudioCodec = value;
	}
	private int SampleRate;
	public final int getSampleRate()
	{
		return SampleRate;
	}
	private void setSampleRate(int value)
	{
		SampleRate = value;
	}
	private int Channels;
	public final int getChannels()
	{
		return Channels;
	}
	private void setChannels(int value)
	{
		Channels = value;
	}
	private ODMAudioSampleFormat SampleFormat = ODMAudioSampleFormat.values()[0];
	public final ODMAudioSampleFormat getSampleFormat()
	{
		return SampleFormat;
	}
	private void setSampleFormat(ODMAudioSampleFormat value)
	{
		SampleFormat = value;
	}
	private int NumSamples;
	public final int getNumSamples()
	{
		return NumSamples;
	}
	private void setNumSamples(int value)
	{
		NumSamples = value;
	}
	private int BitsPerSample;
	public final int getBitsPerSample()
	{
		return BitsPerSample;
	}
	private void setBitsPerSample(int value)
	{
		BitsPerSample = value;
	}

	@Override
	public String toString()
	{
		return String.format("%1$s (%2$s, %3$s)", getAudioCodec().toString(), getSampleRate(), getChannels());
	}
}