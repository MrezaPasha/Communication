package Rasad.Core.FFMpeg;

import Rasad.Core.*;

/** 
 
 Ready made encoding commands for FFmpeg
 Use when calling EncodeVideo commands as string encodingCommand
 Add remove as you like
 


*/

public class QuickAudioEncodingCommands
{
	//mp3
	public static String MP3128Kbps = "-y -ab 128k -ar 44100";
	public static String MP396Kbps = "-y -ab 96k -ar 44100";
	public static String MP364Kbps = "-y -ab 64k -ar 44100";
	public static String MP332Kbps = "-y -ab 32k -ar 44100";
}