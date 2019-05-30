package main.java.Rasad.Core;

import main.java.Rasad._core.TimeSpan;

public class SimulationOptions
{
	public SimulationOptions(boolean simulateRecordingInRecorderHost, boolean simulateMotionDetection)
	{
		// recording
		SimulateRecordingInRecorderHost = simulateRecordingInRecorderHost;
		// motion detection
		SimulateMotionDetection = simulateMotionDetection;


		RecordSimulationFile = "SimulationRecord.MP4";
		RecordSimulationFile_Duration = new TimeSpan(0, 0, 4, 59, 860);
		RecordSimulationFile_Width = 1920;
		RecordSimulationFile_Height = 1080;
		RecordSimulationFile_TotalFrames = tangible.FloatingPointToInteger.ToInt32(RecordSimulationFile_Duration.TotalSeconds() * RecordSimulationFile_FrameRate);
		RecordSimulationFile_FrameRate = 20.85;
	}

	public boolean SimulateRecordingInRecorderHost;

	public String RecordSimulationFile;
	public TimeSpan RecordSimulationFile_Duration = new TimeSpan();
	public int RecordSimulationFile_Width;
	public int RecordSimulationFile_Height;
	public int RecordSimulationFile_TotalFrames;
	public double RecordSimulationFile_FrameRate;

	public boolean SimulateMotionDetection;
}