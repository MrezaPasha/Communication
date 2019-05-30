package Rasad.Core.Media;

import NAudio.Dsp.*;
import NAudio.Wave.*;
import Rasad.Core.*;
import java.io.*;

/** 
 plays *.mp3;*.wav;*.aiff
*/
public class TSoundPlayer extends TViewModelBase implements Closeable
{
	public TSoundPlayer()
	{
		_PlaybackDevice = new WaveOut();
		_PlaybackDevice.DesiredLatency = 200;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		_PlaybackDevice.PlaybackStateChanged += PlaybackDevice_PlaybackStateChanged;
	}



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private IWavePlayer _PlaybackDevice;
	private WaveStream _FileStream;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	//private float _Volume;
	//public float Volume
	//{
	//    get
	//    {
	//        return _Volume;
	//    }
	//    set
	//    {
	//        _Volume = value;
	//        IWavePlayer pd = playbackDevice;
	//        if (pd != null)
	//        {
	//            pd.Volume = value;
	//        }
	//    }
	//}
	public final TimeSpan getCurrentTime()
	{
		if (_FileStream == null)
		{
			return TimeSpan.Zero;
		}
		return _FileStream.CurrentTime;
	}
	public final WaveFormat getWaveFormat()
	{
		if (_FileStream == null)
		{
			return null;
		}
		return _FileStream.WaveFormat;
	}
	public final TimeSpan getTotalTime()
	{
		if (_FileStream == null)
		{
			return TimeSpan.Zero;
		}
		return _FileStream.TotalTime;
	}
	public final PlaybackState getState()
	{
		IWavePlayer pd = _PlaybackDevice;
		if (pd != null)
		{
			return pd.PlaybackState;
		}
		else
		{
			return PlaybackState.Stopped;
		}
	}

	//public string FileName
	//{
	//    get
	//    {
	//        return GetValue<string>("FileName");
	//    }
	//    set
	//    {
	//        if (SetValue(value, "FileName"))
	//        {
	//            Stop();
	//            CloseFile();
	//            if (!FileName.IsNullOrEmpty())
	//            {
	//                OpenFile(FileName);
	//            }
	//        }
	//    }
	//}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods
	private void PlaybackDevice_PlaybackStateChanged(Object sender, tangible.EventArgs e)
	{
		OnPlaybackStateChanged();
	}

	private void OnPlaybackStateChanged()
	{
		if (getState() == PlaybackState.Stopped)
		{
			_FileStream.Seek(0, System.IO.SeekOrigin.Begin);
		}

		OnPropertyChanged(() -> getState());
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = PlaybackStateChanged;
		if (del != null)
		{
			del(this, tangible.EventArgs.Empty);
		}
	}
	private void CloseFile()
	{
		if (_FileStream != null)
		{
			_FileStream.Dispose();
			_FileStream = null;
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void OpenStream(byte[] bytes, string fileExtention)
	public final void OpenStream(byte[] bytes, String fileExtention)
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
		OpenStream(new MemoryStream(bytes), fileExtention);
	}
	public final void OpenStream(InputStream stream, String fileExtention)
	{
		Stop();
		CloseFile();
		try
		{
			AudioFileReader inputStream = new AudioFileReader(fileExtention, stream);
			_FileStream = inputStream;

			SampleAggregator aggregator = new SampleAggregator(inputStream);
			aggregator.setNotificationCount(inputStream.WaveFormat.SampleRate / 100);
			aggregator.setPerformFFT(true);
			aggregator.FftCalculated.addListener((s, a) -> OnFftCalculated(a));
			aggregator.MaximumCalculated.addListener((s, a) -> OnMaximumCalculated(a));
			_PlaybackDevice.Init(aggregator);
		}
		catch (RuntimeException e)
		{
			CloseFile();
			throw new RuntimeException("Problem opening file", e);
		}
	}
	public final void OpenFile(String fileName)
	{
		Stop();
		CloseFile();
		try
		{
			AudioFileReader inputStream = new AudioFileReader(fileName);
			_FileStream = inputStream;

			SampleAggregator aggregator = new SampleAggregator(inputStream);
			aggregator.setNotificationCount(inputStream.WaveFormat.SampleRate / 100);
			aggregator.setPerformFFT(true);
			aggregator.FftCalculated.addListener((s, a) -> OnFftCalculated(a));
			aggregator.MaximumCalculated.addListener((s, a) -> OnMaximumCalculated(a));
			_PlaybackDevice.Init(aggregator);
		}
		catch (RuntimeException e)
		{
			CloseFile();
			throw new RuntimeException("Problem opening file", e);
		}
	}

	public final void Play()
	{
		if (_PlaybackDevice != null && _FileStream != null && _PlaybackDevice.PlaybackState != PlaybackState.Playing)
		{
			_PlaybackDevice.Play();
			OnPropertyChanged(() -> getState());
		}
	}

	protected void OnFftCalculated(FftEventArgs e)
	{
		tangible.EventHandler<FftEventArgs> handler = (Object sender, FftEventArgs e) -> FftCalculated.invoke(sender, e);
		if (handler != null)
		{
			handler.invoke(this, e);
		}
	}
	protected void OnMaximumCalculated(MaxSampleEventArgs e)
	{
		tangible.EventHandler<MaxSampleEventArgs> handler = (Object sender, MaxSampleEventArgs e) -> MaximumCalculated.invoke(sender, e);
		if (handler != null)
		{
			handler.invoke(this, e);
		}
	}

	public final void Pause()
	{
		if (_PlaybackDevice != null)
		{
			_PlaybackDevice.Pause();
			OnPropertyChanged(() -> getState());
		}
	}

	public final void Stop()
	{
		if (_FileStream != null)
		{
			_FileStream.Position = 0;
		}

		if (_PlaybackDevice != null)
		{
			_PlaybackDevice.Stop();
			OnPropertyChanged(() -> getState());
		}
	}

	public final void close() throws IOException
	{
		Stop();
		CloseFile();
		if (_PlaybackDevice != null)
		{
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			_PlaybackDevice.PlaybackStateChanged -= PlaybackDevice_PlaybackStateChanged;
			_PlaybackDevice.Dispose();
			_PlaybackDevice = null;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
	public tangible.Event<tangible.EventHandler<FftEventArgs>> FftCalculated = new tangible.Event<tangible.EventHandler<FftEventArgs>>();
	public tangible.Event<tangible.EventHandler<MaxSampleEventArgs>> MaximumCalculated = new tangible.Event<tangible.EventHandler<MaxSampleEventArgs>>();
	public tangible.Event<tangible.EventHandler<tangible.EventArgs>> PlaybackStateChanged = new tangible.Event<tangible.EventHandler<tangible.EventArgs>>();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}