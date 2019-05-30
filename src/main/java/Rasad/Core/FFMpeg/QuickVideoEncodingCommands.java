package Rasad.Core.FFMpeg;

import Rasad.Core.*;

/** 
 
 Ready made encoding commands for FFmpeg
 Use when calling EncodeVideo commands as string encodingCommand
 Add remove as you like
 


*/

public class QuickVideoEncodingCommands
{
	//-b
	private static String LQVideoBitrate = "256k";
	private static String MQVideoBitrate = "512k";
	private static String HQVideoBitrate = "756k";
	private static String VHQVideoBitrate = "1024k";
	//-ab 
	private static String LQAudioBitrate = "32k";
	private static String MQAudioBitrate = "64k";
	private static String HQAudioBitrate = "96k";
	private static String VHQAudioBitrate = "128k";
	//-ar
	private static String LQAudioSamplingFrequency = "22050";
	private static String MQAudioSamplingFrequency = "44100";
	private static String HQAudioSamplingFrequency = "44100";
	//-s
	private static String SQCIF = "sqcif"; //128x96
	private static String QCIF = "qcif"; //176x144
	private static String QVGA = "qvga"; //320x240
	private static String CIF = "cif"; //352x288
	private static String VGA = "vga"; //640x480
	private static String SVGA = "svga"; //800x600


	// todo
	//insert logo
	//
	//string LogoPath ="/Path/to/transparent/png";
	//string PositionX ="0";
	//string PositionY ="0";
	//string.Format("-vhook \"vhook/imlib2.dll -x {0} -y {1}  -i {2}\"", PositionX,PositionY,LogoPath);



	//flv
	public static String FLVLowQualityQCIF = String.format("-y -b %1$s -ab %2$s -ar %3$s -s %4$s -f flv", LQVideoBitrate, LQAudioBitrate, LQAudioSamplingFrequency, QVGA);
	public static String FLVMediumQualityCIF = String.format("-y -b %1$s -ab %2$s -ar %3$s -s %4$s -f flv", MQVideoBitrate, MQAudioBitrate, MQAudioSamplingFrequency, CIF);
	public static String FLVHighQualityVGA = String.format("-y -b %1$s -ab %2$s -ar %3$s -s %4$s -f flv", HQVideoBitrate, HQAudioBitrate, HQAudioSamplingFrequency, VGA);
	public static String FLVVeryHighQualitySVGA = String.format("-y -b %1$s -ab %2$s -ar %3$s -s %4$s -f flv", VHQVideoBitrate, VHQAudioBitrate, HQAudioSamplingFrequency, SVGA);

	public static String FLVLowQualityKeepOriginalSize = String.format("-y -b %1$s -ab %2$s -ar %3$s -f flv", LQVideoBitrate, LQAudioBitrate, LQAudioSamplingFrequency, QVGA);
	public static String FLVMediumQualityKeepOriginalSize = String.format("-y -b %1$s -ab %2$s -ar %3$s -f flv", MQVideoBitrate, MQAudioBitrate, MQAudioSamplingFrequency, CIF);
	public static String FLVHighQualityKeepOriginalSize = String.format("-y -b %1$s -ab %2$s -ar %3$s -f flv", HQVideoBitrate, HQAudioBitrate, HQAudioSamplingFrequency, VGA);
	public static String FLVVeryHighQualityKeepOriginalSize = String.format("-y -b %1$s -ab %2$s -ar %3$s -f flv", VHQVideoBitrate, VHQAudioBitrate, HQAudioSamplingFrequency, SVGA);

	//3gp
	public static String THREEGPLowQualitySQCIF = String.format("-y -acodec aac -ac 1 -b %1$s -ab %2$s -ar %3$s -s %4$s -f 3gp", LQVideoBitrate, LQAudioBitrate, LQAudioSamplingFrequency, SQCIF);
	public static String THREEGPMediumQualityQCIF = String.format("-y -acodec aac -b %1$s -ab %2$s -ar %3$s -s %4$s -f 3gp", MQVideoBitrate, MQAudioBitrate, MQAudioSamplingFrequency, QCIF);
	public static String THREEGPHighQualityCIF = String.format("-y -acodec aac -b %1$s -ab %2$s -ar %3$s -s %4$s -f 3gp", VHQVideoBitrate, VHQAudioBitrate, HQAudioSamplingFrequency, CIF);
	//mp4
	public static String MP4LowQualityKeepOriginalSize = String.format("-y -b %1$s -ab %2$s -ar %3$s -f mp4", LQVideoBitrate, LQAudioBitrate, LQAudioSamplingFrequency, QVGA);
	public static String MP4MediumQualityKeepOriginalSize = String.format("-y -b %1$s -ab %2$s -ar %3$s -f mp4", MQVideoBitrate, MQAudioBitrate, MQAudioSamplingFrequency, CIF);
	public static String MP4HighQualityKeepOriginalSize = String.format("-y -b %1$s -ab %2$s -ar %3$s -f mp4", HQVideoBitrate, HQAudioBitrate, HQAudioSamplingFrequency, VGA);

	public static String MP4LowQualityQVGA = String.format("-y -b %1$s -ab %2$s -ar %3$s -s %4$s -f mp4", LQVideoBitrate, LQAudioBitrate, LQAudioSamplingFrequency, QVGA);
	public static String MP4MediumQualityCIF = String.format("-y -b %1$s -ab %2$s -ar %3$s -s %4$s -f mp4", MQVideoBitrate, MQAudioBitrate, MQAudioSamplingFrequency, CIF);
	public static String MP4HighQualityVGA = String.format("-y -b %1$s -ab %2$s -ar %3$s -s %4$s -f mp4", HQVideoBitrate, HQAudioBitrate, HQAudioSamplingFrequency, VGA);

	//WMV
	public static String WMVLowQualityQVGA = String.format("-y -vcodec wmv2  -acodec wmav2 -b %1$s -ab %2$s -ar %3$s -s %4$s", LQVideoBitrate, LQAudioBitrate, LQAudioSamplingFrequency, QVGA);
	public static String WMVMediumQualityCIF = String.format("-y -vcodec wmv2  -acodec wmav2 -b %1$s -ab %2$s -ar %3$s -s %4$s", MQVideoBitrate, MQAudioBitrate, MQAudioSamplingFrequency, CIF);
	public static String WMVHighQualityVGA = String.format("-y -vcodec wmv2  -acodec wmav2 -b %1$s -ab %2$s -ar %3$s -s %4$s", HQVideoBitrate, HQAudioBitrate, HQAudioSamplingFrequency, VGA);
	public static String WMVVeryHighQualitySVGA = String.format("-y -vcodec wmv2  -acodec wmav2 -b %1$s -ab %2$s -ar %3$s -s %4$s", VHQVideoBitrate, VHQAudioBitrate, HQAudioSamplingFrequency, SVGA);

	public static String WMVLowQualityKeepOriginalSize = String.format("-y -vcodec wmv2  -acodec wmav2 -b %1$s -ab %2$s -ar %3$s", LQVideoBitrate, LQAudioBitrate, LQAudioSamplingFrequency, QVGA);
	public static String WMVMediumQualityKeepOriginalSize = String.format("-y -vcodec wmv2  -acodec wmav2 -b %1$s -ab %2$s -ar %3$s", MQVideoBitrate, MQAudioBitrate, MQAudioSamplingFrequency, CIF);
	public static String WMVHighQualityKeepOriginalSize = String.format("-y -vcodec wmv2  -acodec wmav2 -b %1$s -ab %2$s -ar %3$s", HQVideoBitrate, HQAudioBitrate, HQAudioSamplingFrequency, VGA);
	public static String WMVVeryHighQualityKeepOriginalSize = String.format("-y -vcodec wmv2  -acodec wmav2 -b %1$s -ab %2$s -ar %3$s", VHQVideoBitrate, VHQAudioBitrate, HQAudioSamplingFrequency, SVGA);

	public static String AutoDetect(long videoBitrate)
	{
		return String.format("-y -b %1$s", String.valueOf(videoBitrate));
	}
}