package Rasad.Core.FFMpeg;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Encoder
{
	public Encoder()
	{
		this(Paths.get((new File(Assembly.GetExecutingAssembly().Location)).getParent()).resolve("ffmpeg.exe").toString());
	}
	public Encoder(String ffmpegPath)
	{
		this.setFFmpegPath(ffmpegPath);
		setKillOnTooManyErrors(false);
	}

	private EncodedVideo tempEncodedVideo = null;
	private VideoFile tempVideoFile = null;

	private int iProgressErrorCount = 0;
	private static final int PROGRESS_ERROR_LIMIT = 100;

	public tangible.Event<EncodeProgressEventHandler> OnEncodeProgress = new tangible.Event<EncodeProgressEventHandler>();
	public tangible.Event<EncodeFinishedEventHandler> OnEncodeFinished = new tangible.Event<EncodeFinishedEventHandler>();

	private boolean KillOnTooManyErrors;
	public final boolean getKillOnTooManyErrors()
	{
		return KillOnTooManyErrors;
	}
	public final void setKillOnTooManyErrors(boolean value)
	{
		KillOnTooManyErrors = value;
	}

	public final boolean ConcatVideos(File[] sourceFiles, File outputFile)
	{
		if (outputFile.exists())
		{
			outputFile.delete();
		}
		File FileListText = new File(Paths.get(outputFile.getParentFile().getPath()).resolve(UUID.NewGuid().toString() + ".Txt").toString());
		if (FileListText.exists())
		{
			FileListText.delete();
		}
		try
		{
			Files.writeString(FileListText.getPath(), tangible.StringHelper.join("\r\n", sourceFiles.Select(t -> "file '" + t.FullName + "'")));
			String command = String.format("-f concat -i \"%1$s\" -vcodec copy -acodec copy \"%2$s\"", FileListText.getPath(), outputFile.getPath());
			RunProcess(command);
			return true;
		}
		catch (java.lang.Exception e)
		{
			return false;
		}
		finally
		{
			if ((new File(FileListText.getPath())).isFile())
			{
				FileListText.delete();
			}
		}
	}
	public final boolean ConcatVideosAsync(String[] sourceFiles, File outputFile, int threadCount)
	{
		if (outputFile.exists())
		{
			outputFile.delete();
		}
		File FileListText = new File(Paths.get(outputFile.getParentFile().getPath()).resolve(UUID.NewGuid().toString() + ".Txt").toString());
		if (FileListText.exists())
		{
			FileListText.delete();
		}
		try
		{
			//Create new encoded video
			tempEncodedVideo = new EncodedVideo();
			tempEncodedVideo.setEncodedVideoPath(outputFile.getPath());
			if (sourceFiles.length > 0)
			{
				tempVideoFile = new VideoFile(sourceFiles.First());
				GetVideoInfo(tempVideoFile);
				Files.writeString(FileListText.getPath(), tangible.StringHelper.join("\r\n", sourceFiles.Select(t -> "file '" + t + "'")));

				String command = String.format("-f concat -i \"%1$s\"  -c copy \"%2$s\" -threads %3$s", FileListText.getPath(), outputFile.getPath(), threadCount);
				RunProcessAsync(command);
				this.OnEncodeFinished.addListener((s, e) ->
				{
							if ((new File(FileListText.getPath())).isFile())
							{
								FileListText.delete();
							}
				});
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (java.lang.Exception e)
		{
			if (OnEncodeFinished != null)
			{
				EncodeFinishedEventArgs tempVar = new EncodeFinishedEventArgs();
				tempVar.setEncodedVideoInfo(null);
				for (EncodeFinishedEventHandler listener : OnEncodeFinished.listeners())
				{
					listener.invoke(this, tempVar);
				}
			}
			if (FileListText.exists())
			{
				FileListText.delete();
			}
			return false;
		}

	}
	public final boolean Split(File source, TimeSpan start, TimeSpan duration, File outputFile)
	{
		String command = String.format("-i \"%1$s\" -ss %02d:%02d:%02d -t %02d:%02d:%02d -async 1 \"%8$s\"", source.getPath(), start.Hours, start.Minutes, start.Seconds, duration.Hours, duration.Minutes, duration.Seconds, outputFile.getPath());
		try
		{
			RunProcess(command, (int)TimeSpan.FromMinutes(1).TotalMilliseconds);
			return true;
		}
		catch (java.lang.Exception e)
		{

			return false;
		}
	}
	public final boolean SplitAsync(File source, TimeSpan start, TimeSpan duration, File outputFile)
	{
		String command = String.format("-i \"%1$s\" -ss %02d:%02d:%02d -t %02d:%02d:%02d -c copy -async 1 \"%8$s\"", source.getPath(), start.Hours, start.Minutes, start.Seconds, duration.Hours, duration.Minutes, duration.Seconds, outputFile.getPath());
		try
		{
			//Create new encoded video
			tempEncodedVideo = new EncodedVideo();
			tempEncodedVideo.setEncodedVideoPath(outputFile.getPath());

			tempVideoFile = new VideoFile(source.getPath());
			GetVideoInfo(tempVideoFile);
			RunProcessAsync(command);
			return true;
		}
		catch (java.lang.Exception e)
		{
			if (OnEncodeFinished != null)
			{
				EncodeFinishedEventArgs tempVar = new EncodeFinishedEventArgs();
				tempVar.setEncodedVideoInfo(null);
				for (EncodeFinishedEventHandler listener : OnEncodeFinished.listeners())
				{
					listener.invoke(this, tempVar);
				}
			}
			return false;
		}
	}
	public final boolean ExtractVideo(File[] sourceFiles, TimeSpan start, TimeSpan duration, File outputFile)
	{
		File ConcatedFile = new File(Paths.get(outputFile.getParentFile().getPath()).resolve(UUID.NewGuid().toString() + Path.GetExtension(outputFile.getPath())).toString());

		try
		{
			if (ConcatVideos(sourceFiles, ConcatedFile))
			{
				if (Split(ConcatedFile, start, duration, outputFile))
				{
					return true;
				}
			}
		}
		finally
		{
			if ((new File(ConcatedFile.getPath())).isFile())
			{
				(new File(ConcatedFile.getPath())).delete();
			}
		}
		return false;
	}


	protected void DoEncodeProgress(EncodeProgressEventArgs e)
	{
		if (OnEncodeProgress != null)
		{
			for (EncodeProgressEventHandler listener : OnEncodeProgress.listeners())
			{
				listener.invoke(this, e);
			}
		}
	}

	protected void DoEncodeFinished(EncodeFinishedEventArgs e)
	{
		if (OnEncodeFinished != null)
		{
			for (EncodeFinishedEventHandler listener : OnEncodeFinished.listeners())
			{
				listener.invoke(this, e);
			}
		}
	}

	public final EncodedVideo EncodeVideo(VideoFile input, String encodingCommand, String outputFile, boolean getVideoThumbnail)
	{
		EncodedVideo encoded = new EncodedVideo();

		setParams(String.format("-i \"%1$s\" %2$s \"%3$s\"", input.getPath(), encodingCommand, outputFile));
		String output = RunProcess(getParams());
		encoded.setEncodingLog(output);
		encoded.setEncodedVideoPath(outputFile);

		if ((new File(outputFile)).isFile())
		{
			encoded.setSuccess(true);

			//get thumbnail?
			if (getVideoThumbnail)
			{
				String saveThumbnailTo = outputFile + "_thumb.jpg";

				if (GetVideoThumbnail(input, saveThumbnailTo))
				{
					encoded.setThumbnailPath(saveThumbnailTo);
				}
			}
		}
		else
		{
			encoded.setSuccess(false);
		}

		return encoded;
	}

	/** 
	 Async for WinForms where secure threading is necessary, pass the control that will be used as Invoker
	 
	 @param input
	 @param encodingCommand
	 @param outputFile
	 @param caller
	*/
	public final void EncodeVideoAsync(VideoFile input, String encodingCommand, String outputFile, int threadCount)
	{
		//Gather info
		if (!input.getinfoGathered())
		{
			GetVideoInfo(input);
		}

		//Create new encoded video
		tempEncodedVideo = new EncodedVideo();
		tempEncodedVideo.setEncodedVideoPath(outputFile);

		////Set caller
		//tempCaller = caller;

		//Set input
		tempVideoFile = input;

		//Create parameters
		//if (threadCount.Equals(1))
		//    Params = string.Format("-i \"{0}\" {1} \"{2}\"", input.Path, encodingCommand, outputFile);
		//else
			setParams(String.format("-i \"%1$s\" -threads %2$s %3$s \"%4$s\"", input.getPath(), String.valueOf(threadCount), encodingCommand, outputFile));

		//Execute ffmpeg async
		RunProcessAsync(getParams());
	}

	/** 
	 Async for WinForms where secure threading is necessary, pass the control that will be used as Invoker
	 This method uses output filename to detect resulting type, same resolution as source and the same bitrate as source
	 
	 @param input
	 @param encodingCommand
	 @param outputFile
	 @param caller The WinForm that makes the call
	*/
	public final void EncodeVideoAsyncAutoCommand(VideoFile input, String outputFile, int treadCount)
	{
		//Gather info
		if (!input.getinfoGathered())
		{
			GetVideoInfo(input);
		}

		//If input video bitrate 0, then the correct video bitrate has not been detected, generate a value
		if (input.getVideoBitRate() == 0)
		{
			//Use video height, guestimations, tweak these at ur own will
			int h = input.getHeight();

			if (h < 180)
			{
				input.setVideoBitRate(400);
			}
			else if (h < 260)
			{
				input.setVideoBitRate(1000);
			}
			else if (h < 400)
			{
				input.setVideoBitRate(2000);
			}
			else if (h < 800)
			{
				input.setVideoBitRate(5000);
			}
			else
			{
				input.setVideoBitRate(8000);
			}
		}

		//If input audio bitrate is 0, then the correct audio bitrate has not been detected, set it to 128k
		if (input.getAudioBitRate() == 0)
		{
			input.setAudioBitRate(128);
		}

		//Build encoding command
		String encodingCommand = String.format("-threads %1$s -y -b %2$s -ab %3$s", String.valueOf(treadCount), String.valueOf(input.getVideoBitRate()) + "k", String.valueOf(input.getAudioBitRate()) + "k");

		//Create new encoded video
		tempEncodedVideo = new EncodedVideo();
		tempEncodedVideo.setEncodedVideoPath(outputFile);

		////Set caller
		//tempCaller = caller;

		//Set input
		tempVideoFile = input;

		//Create parameters
		setParams(String.format("-i \"%1$s\" %2$s \"%3$s\"", input.getPath(), encodingCommand, outputFile));

		//Execute ffmpeg async
		RunProcessAsync(getParams());
	}

	private void RunProcessAsync(String Parameters)
	{
		//Create process info
		ProcessStartInfo oInfo = new ProcessStartInfo(this.getFFmpegPath(), Parameters);

		//Set process properties
		oInfo.UseShellExecute = false;
		oInfo.CreateNoWindow = true;
		oInfo.RedirectStandardOutput = false;
		oInfo.RedirectStandardError = true;

		//Construct the process
		_Process = new Process();

		//Set start info
		_Process.StartInfo = oInfo;

		//Hook up events
		_Process.EnableRaisingEvents = true;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		_Process.ErrorDataReceived += new DataReceivedEventHandler(proc_ErrorDataReceived);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		_Process.Exited += new EventHandler(proc_Exited);

		//Start the process
		_Process.Start();

		_Process.ProcessorAffinity = new IntPtr(1);

		//Start async output reading
		_Process.BeginErrorReadLine();
	}

	public final void AbortCurrentProcess()
	{
		if (_Process != null)
		{
			_Process.Kill();
		}
	}

	/** 
	 Handles process exit
	 
	 @param sender
	 @param e
	*/
	private void proc_Exited(Object sender, tangible.EventArgs e)
	{
		if (tempEncodedVideo == null)
		{
			return;
		}
		//Get process
		Process proc = (Process)sender;

		//Get the process exit code
		int iExitCode = proc.ExitCode;

		//Check if file exists
		boolean blFileExists = (new File(tempEncodedVideo.getEncodedVideoPath())).isFile();

		//Set success
		tempEncodedVideo.setSuccess(((new Integer(iExitCode)).equals(0) && blFileExists));

		//Construct new finished event args
		EncodeFinishedEventArgs efe = new EncodeFinishedEventArgs();

		//Set the encode video finished event's VideoInfo object
		efe.setEncodedVideoInfo(tempEncodedVideo);

		//Invoke/Fire real finished event
		//if (tempCaller != null)
		//    tempCaller.BeginInvoke(new EncodeFinishedEventHandler(OnEncodeFinished), tempCaller, efe);
		//else
		DoEncodeFinished(efe);

		//Reset progress error count
		iProgressErrorCount = 0;

		//Release resourced from process
		proc.Close();
	}

	/** 
	 Handles process progress
	 
	 @param sender
	 @param e
	*/
	private void proc_ErrorDataReceived(Object sender, DataReceivedEventArgs e)
	{
		if (e.Data != null)
		{
			tempEncodedVideo.setEncodingLog(tempEncodedVideo.getEncodingLog() + e.Data + System.lineSeparator());

			if (e.Data.startsWith("frame"))
			{
				//Reset progress error count
				iProgressErrorCount = 0;

				//Create a new progress event object
				EncodeProgressEventArgs epe = new EncodeProgressEventArgs();

				//Set raw data line
				epe.setRawOutputLine(e.Data);

				//Set total frames
				epe.setTotalFrames(tempVideoFile.getTotalFrames());

				//Split the raw string
				String[] parts = e.Data.split(new String[] {" ", "="}, StringSplitOptions.RemoveEmptyEntries);

				//Parse current frame
				long lCurrentFrame = 0L;
				tangible.OutObject<Long> tempOut_lCurrentFrame = new tangible.OutObject<Long>();
				tangible.TryParseHelper.tryParseLong(parts[1], tempOut_lCurrentFrame);
			lCurrentFrame = tempOut_lCurrentFrame.argValue;
				epe.setCurrentFrame(lCurrentFrame);

				//Parse FPS
				short sFPS = 0;
				tangible.OutObject<Short> tempOut_sFPS = new tangible.OutObject<Short>();
				tangible.TryParseHelper.tryParseShort(parts[3], tempOut_sFPS);
			sFPS = tempOut_sFPS.argValue;
				epe.setFPS(sFPS);

				//Calculate percentage
				double dCurrentFrame = (double)epe.getCurrentFrame();
				double dTotalFrames = (double)epe.getTotalFrames();
				short sPercentage = (short)Math.round((dCurrentFrame * 100 / dTotalFrames) * Math.pow(10, 0)) / Math.pow(10, 0);
				epe.setPercentage(sPercentage);

				////Invoke/Fire Real progress event
				//if (tempCaller != null)
				//    tempCaller.BeginInvoke(new EncodeProgressEventHandler(OnEncodeProgress), tempCaller, epe);
				//else
				DoEncodeProgress(epe);
				iProgressErrorCount = 0;
			}
			else
			{
				//Increment progress error
				iProgressErrorCount++;
			}
		}
		else
		{
			//Increment progress error
			iProgressErrorCount++;
		}

		//If ProgressErrorCount is more than limit, then kill the process
		if (iProgressErrorCount > PROGRESS_ERROR_LIMIT)
		{
			if (getKillOnTooManyErrors())
			{
				//Get the process
				Process proc = (Process)sender;

				//Murder
				try
				{
					proc.Kill();
				}
				catch (java.lang.Exception e)
				{
				}
			}
		}
	}

	public final boolean GetVideoThumbnail(VideoFile input, String saveThumbnailTo)
	{
		if (!input.getinfoGathered())
		{
			GetVideoInfo(input);
		}

		//divide the duration in 3 to get a preview image in the middle of the clip
		//instead of a black image from the beginning.
		int secs;
		secs = (int)Math.round((TimeSpan.FromTicks(input.getDuration().Ticks / 3).TotalSeconds) * Math.pow(10, 0)) / Math.pow(10, 0);
		if ((new Integer(secs)).equals(0))
		{
			secs = 1;
		}

		String Params = String.format("-i \"%1$s\" \"%2$s\" -vcodec mjpeg -ss %3$s -vframes 1 -an -f rawvideo", input.getPath(), saveThumbnailTo, secs);
		String output = RunProcess(Params);

		if ((new File(saveThumbnailTo)).isFile())
		{
			return true;
		}
		else
		{
			//try running again at frame 1 to get something
			Params = String.format("-i \"%1$s\" \"%2$s\" -vcodec mjpeg -ss %3$s -vframes 1 -an -f rawvideo", input.getPath(), saveThumbnailTo, 1);
			output = RunProcess(Params);

			if ((new File(saveThumbnailTo)).isFile())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}


	private String RunProcess(String Parameters)
	{
		return RunProcess(Parameters, 10000);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: private string RunProcess(string Parameters, int timeout = 10000)
	private String RunProcess(String Parameters, int timeout)
	{
		//create a process info
		ProcessStartInfo oInfo = new ProcessStartInfo(this.getFFmpegPath(), Parameters);
		oInfo.UseShellExecute = false;
		oInfo.CreateNoWindow = true;
		oInfo.RedirectStandardOutput = true;
		oInfo.RedirectStandardError = true;

		//Create the output
		String output = null;

		//try the process
		try
		{
			//run the process
			//Construct the process
			Process proc = new Process();

			//Set start info
			proc.StartInfo = oInfo;

			//Hook up events
			//proc.EnableRaisingEvents = true;
			//proc.ErrorDataReceived += new DataReceivedEventHandler(proc_ErrorDataReceived);
			//proc.Exited += new EventHandler(proc_Exited);

			//Start the process
			proc.Start();
			//now put it in a string
			//This needs to be before WaitForExit() to prevent deadlock, for details: http://msdn.microsoft.com/en-us/library/system.diagnostics.process.standardoutput%28v=VS.80%29.aspx
			output = proc.StandardError.ReadToEnd();

			//Wait for exit
			proc.WaitForExit(timeout);

			//Release resources
			proc.Close();
		}
		catch (RuntimeException e)
		{
			output = "";
		}

		return output;
	}

	public final void GetVideoInfo(VideoFile input)
	{
		String Params = String.format("-i \"%1$s\"", input.getPath());
		String output = RunProcess(Params);

		input.setRawInfo(output);
		if (!System.TStringHelper.IsNullOrEmpty(input.getRawInfo()))
		{
			input.setRawAudioFormat(ExtractRawAudioFormat(input.getRawInfo()));

			input.setRawVideoFormat(ExtractRawVideoFormat(input.getRawInfo()));

			input.setDuration(ExtractDuration(input.getRawInfo()));
			input.setBitRate(ExtractBitrate(input.getRawInfo()));


			String audioFormat;
			FFMpegAudioCodec audioCodec;
			tangible.OutObject<String> tempOut_audioFormat = new tangible.OutObject<String>();
			tangible.OutObject<Rasad.Core.FFMpeg.FFMpegAudioCodec> tempOut_audioCodec = new tangible.OutObject<Rasad.Core.FFMpeg.FFMpegAudioCodec>();
			ExtractAudioFormatAndCodec(input.getRawAudioFormat(), tempOut_audioFormat, tempOut_audioCodec);
		audioCodec = tempOut_audioCodec.argValue;
		audioFormat = tempOut_audioFormat.argValue;
			input.setAudioFormat(audioFormat);
			input.setAudioCodec(audioCodec);

			input.setVideoFormat(ExtractVideoFormat(input.getRawVideoFormat()));
			input.setWidth(ExtractVideoWidth(input.getRawVideoFormat()));
			input.setHeight(ExtractVideoHeight(input.getRawVideoFormat()));
			input.setFrameRate(ExtractFrameRate(input.getRawVideoFormat()));
			input.setTotalFrames(ExtractTotalFrames(input.getDuration(), input.getFrameRate()));
			input.setAudioBitRate(ExtractAudioBitRate(input.getRawAudioFormat()));
			input.setVideoBitRate(ExtractVideoBitRate(input.getRawVideoFormat()));
			input.setinfoGathered(true);
		}
		else
		{
			input.setinfoGathered(false);
		}
	}

	private String FFmpegPath;
	public final String getFFmpegPath()
	{
		return FFmpegPath;
	}
	public final void setFFmpegPath(String value)
	{
		FFmpegPath = value;
	}
	private String Params;
	private String getParams()
	{
		return Params;
	}
	private void setParams(String value)
	{
		Params = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Extraction methods
	private static Regex DurationFinder = new Regex("[D|d]uration:.((\\d|:|\\.)*)", RegexOptions.Compiled);
	private static Regex BitrateFinder = new Regex("[B|b]itrate:.((\\d|:)*)", RegexOptions.Compiled);
	private static Regex AudioFinder = new Regex("[A|a]udio:.*", RegexOptions.Compiled);
	private static Regex VideoFinder = new Regex("[V|v]ideo:.*", RegexOptions.Compiled);
	private static Regex VideoWidthFinder = new Regex("(\\d{2,4})x(\\d{2,4})", RegexOptions.Compiled);
	private static Regex VideoHeightFinder = new Regex("(\\d{2,4})x(\\d{2,4})", RegexOptions.Compiled);
	private Process _Process;

	private TimeSpan ExtractDuration(String rawInfo)
	{
		TimeSpan t = new TimeSpan(0);
		try
		{
			Match m = DurationFinder.Match(rawInfo);

			if (m.Success)
			{
				String duration = m.Groups[1].Value;
				int[] timepieces = duration.split("[:.]", -1).Select(k -> (int)k).ToArray();
				if (timepieces.length == 4)
				{
					t = new TimeSpan(0, timepieces[0], timepieces[1], timepieces[2], timepieces[3]);
				}
				else if (timepieces.length == 3)
				{
					t = new TimeSpan(0, timepieces[0], timepieces[1], timepieces[2], 0);
				}
			}
		}
		catch (java.lang.Exception e)
		{
		}
		return t;
	}
	private double ExtractBitrate(String rawInfo)
	{
		double kb = 0.0;
		try
		{
			Match m = BitrateFinder.Match(rawInfo);
			if (m.Success)
			{
				tangible.OutObject<Double> tempOut_kb = new tangible.OutObject<Double>();
				tangible.TryParseHelper.tryParseDouble(m.Groups[1].Value, tempOut_kb);
			kb = tempOut_kb.argValue;
			}
		}
		catch (java.lang.Exception e)
		{
		}
		return kb;
	}
	private String ExtractRawAudioFormat(String rawInfo)
	{
		String a = "";
		try
		{
			Match m = AudioFinder.Match(rawInfo);
			if (m.Success)
			{
				a = m.Value;
			}
		}
		catch (java.lang.Exception e)
		{
		}
		return a.replace("Audio: ", "");
	}
	private void ExtractAudioFormatAndCodec(String rawAudioFormat, tangible.OutObject<String> audioFormat, tangible.OutObject<FFMpegAudioCodec> audioCodec)
	{
		String[] parts = rawAudioFormat.split(new String[] {", "}, StringSplitOptions.None);
		audioFormat.argValue = parts[0].replace("Audio: ", "");

		if (tangible.StringHelper.isNullOrWhiteSpace(audioFormat.argValue))
		{
			audioCodec.argValue = FFMpegAudioCodec.None;
		}
		else
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var audioFormatParts = audioFormat.argValue.split(new char[] {' '}, StringSplitOptions.RemoveEmptyEntries);
			String audioCodecStr;
			if (audioFormatParts.Length > 0)
			{
				audioCodecStr = audioFormatParts[0];
			}
			else
			{
				audioCodecStr = "";
			}
			audioCodecStr = audioCodecStr.toLowerCase();
			if (audioCodecStr.equals("pcm_alaw"))
			{
				audioCodec.argValue = FFMpegAudioCodec.PCM_ALAW;
			}
			else if (audioCodecStr.equals("pcm_ulaw"))
			{
				audioCodec.argValue = FFMpegAudioCodec.PCM_ULAW;
			}
			else if (audioCodecStr.equals("aac"))
			{
				audioCodec.argValue = FFMpegAudioCodec.AAC;
			}
			else
			{
				audioCodec.argValue = FFMpegAudioCodec.Other;
			}
		}
	}
	private String ExtractRawVideoFormat(String rawInfo)
	{
		String v = "";
		try
		{
			Match m = VideoFinder.Match(rawInfo);
			if (m.Success)
			{
				v = m.Value;
			}
		}
		catch (java.lang.Exception e)
		{
		}
		return v.replace("Video: ", "");
	}
	private String ExtractVideoFormat(String rawVideoFormat)
	{
		String[] parts = rawVideoFormat.split(new String[] {", "}, StringSplitOptions.None);
		return parts[0].replace("Video: ", "");
	}
	private int ExtractVideoWidth(String rawInfo)
	{
		int width = 0;
		try
		{
			Match m = VideoWidthFinder.Match(rawInfo);
			if (m.Success)
			{
				tangible.OutObject<Integer> tempOut_width = new tangible.OutObject<Integer>();
				tangible.TryParseHelper.tryParseInt(m.Groups[1].Value, tempOut_width);
			width = tempOut_width.argValue;
			}
		}
		catch (java.lang.Exception e)
		{
		}
		return width;
	}
	private int ExtractVideoHeight(String rawInfo)
	{
		int height = 0;

		Match m = VideoHeightFinder.Match(rawInfo);
		if (m.Success)
		{
			tangible.OutObject<Integer> tempOut_height = new tangible.OutObject<Integer>();
			tangible.TryParseHelper.tryParseInt(m.Groups[2].Value, tempOut_height);
		height = tempOut_height.argValue;
		}
		return height;
	}
	private double ExtractFrameRate(String rawVideoFormat)
	{
		String[] parts = rawVideoFormat.split(new String[] {", "}, StringSplitOptions.None);

		double dFPS = 0;

		for (String p : parts)
		{
			if (p.toLowerCase().contains("fps"))
			{
				//dFPS = ParseDouble(p.ToLower().Replace("fps", "").Trim());
				tangible.OutObject<Double> tempOut_dFPS = new tangible.OutObject<Double>();
				tangible.TryParseHelper.tryParseDouble(p.toLowerCase().replace("fps", "").trim(), System.Globalization.NumberStyles.Any, System.Globalization.CultureInfo.InvariantCulture, tempOut_dFPS);
			dFPS = tempOut_dFPS.argValue;
				break;
			}
			else if (p.toLowerCase().contains("tbr"))
			{
				tangible.OutObject<Double> tempOut_dFPS2 = new tangible.OutObject<Double>();
				tangible.TryParseHelper.tryParseDouble(p.toLowerCase().replace("tbr", "").trim(), System.Globalization.NumberStyles.Any, System.Globalization.CultureInfo.InvariantCulture, tempOut_dFPS2);
			dFPS = tempOut_dFPS2.argValue;
				break;
			}
		}

		//Audio: mp3, 44100 Hz, 2 channels, s16, 140 kb/s

		return dFPS;
	}

	//private double ParseDoubleNazari(string data)
	//{
	//    double result=0;
	//    string[] parts = data.Split(",./".ToCharArray());
	//    if (parts.Length==2)
	//    {
	//        result = Convert.ToDouble(parts[0])/;

	//    }

	//}
	private double ExtractAudioBitRate(String rawAudioFormat)
	{
		String[] parts = rawAudioFormat.split(new String[] {", "}, StringSplitOptions.None);

		double dABR = 0;

		for (String p : parts)
		{
			if (p.toLowerCase().contains("kb/s"))
			{
				tangible.OutObject<Double> tempOut_dABR = new tangible.OutObject<Double>();
				tangible.TryParseHelper.tryParseDouble(p.toLowerCase().replace("kb/s", "").replace(".", ",").trim(), tempOut_dABR);
			dABR = tempOut_dABR.argValue;

				break;
			}
		}

		return dABR;
	}
	private double ExtractVideoBitRate(String rawVideoFormat)
	{
		String[] parts = rawVideoFormat.split(new String[] {", "}, StringSplitOptions.None);

		double dVBR = 0;

		for (String p : parts)
		{
			if (p.toLowerCase().contains("kb/s"))
			{
				tangible.OutObject<Double> tempOut_dVBR = new tangible.OutObject<Double>();
				tangible.TryParseHelper.tryParseDouble(p.toLowerCase().replace("kb/s", "").replace(".", ",").trim(), tempOut_dVBR);
			dVBR = tempOut_dVBR.argValue;

				break;
			}
		}

		return dVBR;
	}
	private long ExtractTotalFrames(TimeSpan duration, double frameRate)
	{
		return (long)Math.round((duration.TotalSeconds * frameRate) * Math.pow(10, 0)) / Math.pow(10, 0);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}