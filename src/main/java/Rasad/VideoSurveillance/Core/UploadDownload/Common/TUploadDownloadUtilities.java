package Rasad.VideoSurveillance.Core.UploadDownload.Common;

import Rasad.VideoSurveillance.Core.*;
import java.io.*;

public final class TUploadDownloadUtilities
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] GetBytes(int value)
	public static byte[] GetBytes(int value)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] retVal = new byte[4];
		byte[] retVal = new byte[4];
		for (int i = 0; i < 4; i++)
		{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: retVal[i] = Convert.ToByte(((value >> i * 8) & 0xff));
			retVal[i] = (byte)((value >> i * 8) & 0xff);
		}
		return retVal;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] ReadNetworkStream(NetworkStream stream, int maxSize, bool first4BytesIsSize)
	public static byte[] ReadNetworkStream(NetworkStream stream, int maxSize, boolean first4BytesIsSize)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] retVal = null;
		byte[] retVal = null;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] temp = new byte[5 * 1024];
		byte[] temp = new byte[5 * 1024];

		int expectedSize = 0;
		if (first4BytesIsSize)
		{
			int bytesRead = stream.Read(temp, 0, 4);
			if (bytesRead == 4)
			{
				expectedSize = temp[0] | (temp[1] << 8) | (temp[2] << 16) | (temp[3] << 24);
			}
		}

		try (MemoryStream ms = new MemoryStream())
		{
			int totalBytesRead = 0;
			while (true)
			{
				int remainingRequestedReadSize = Math.min(temp.length, expectedSize - totalBytesRead);
				int bytesRead = stream.Read(temp, 0, remainingRequestedReadSize);
				if (bytesRead == 0)
				{
					break;
				}
				ms.Write(temp, 0, bytesRead);
				totalBytesRead += bytesRead;
				if (expectedSize > 0)
				{
					if (totalBytesRead >= expectedSize)
					{
						break;
					}
				}
				if (maxSize > 0 && ms.Length >= maxSize)
				{
					break;
				}
			}
			retVal = ms.ToArray();
		}
		return retVal;
	}

	public static boolean ReadNetworkStreamAndSave(NetworkStream stream, long maxSize, boolean first4BytesIsSize, OutputStream saveToStream)
	{
		// returns true if completed

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] temp = new byte[5 * 1024];
		byte[] temp = new byte[5 * 1024];

		int expectedSize = 0;
		if (first4BytesIsSize)
		{
			int bytesRead = stream.Read(temp, 0, 4);
			if (bytesRead == 4)
			{
				expectedSize = temp[0] | (temp[1] << 8) | (temp[2] << 16) | (temp[3] << 24);
			}
		}

		boolean fullDataReceived = false;
		int totalBytesRead = 0;
		while (true)
		{
			int remainingRequestedReadSize = Math.min(temp.length, expectedSize - totalBytesRead);
			int bytesRead = stream.Read(temp, 0, remainingRequestedReadSize);
			if (bytesRead == 0)
			{
				break;
			}
			saveToStream.write(temp, 0, bytesRead);
			totalBytesRead += bytesRead;
			if (expectedSize > 0)
			{
				if (totalBytesRead >= expectedSize)
				{
					fullDataReceived = true;
					break;
				}
			}
			if (maxSize > 0 && totalBytesRead >= maxSize)
			{
				break;
			}
		}
		return fullDataReceived;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void WriteNetworkStream(NetworkStream stream, byte[] data, bool includeSizeInFirst4Bytes)
	public static void WriteNetworkStream(NetworkStream stream, byte[] data, boolean includeSizeInFirst4Bytes)
	{
		if (includeSizeInFirst4Bytes)
		{
			int size = data.length;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] sizeBytes = GetBytes(size);
			byte[] sizeBytes = GetBytes(size);
			stream.Write(sizeBytes, 0, sizeBytes.length);
		}
		stream.Write(data, 0, data.length);
		stream.Flush();
	}


	public static void WriteNetworkStreamFromSource(NetworkStream stream, Stream source, boolean includeSizeInFirst4Bytes)
	{
		WriteNetworkStreamFromSource(stream, source, includeSizeInFirst4Bytes, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static void WriteNetworkStreamFromSource(NetworkStream stream, Stream source, bool includeSizeInFirst4Bytes, Action<int> progressPercent = null)
	public static void WriteNetworkStreamFromSource(NetworkStream stream, InputStream source, boolean includeSizeInFirst4Bytes, tangible.Action1Param<Integer> progressPercent)
	{
		int totalSize = (int)source.Length;
		if (includeSizeInFirst4Bytes)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] sizeBytes = GetBytes(totalSize);
			byte[] sizeBytes = GetBytes(totalSize);
			stream.Write(sizeBytes, 0, sizeBytes.length);
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] temp = new byte[5 * 1024];
		byte[] temp = new byte[5 * 1024];
		int bytesWritten = 0;
		while (true)
		{
			int bytesRead = source.read(temp, 0, temp.length);
			if (bytesRead == 0)
			{
				break;
			}
			stream.Write(temp, 0, bytesRead);
			bytesWritten += bytesRead;
			if (progressPercent != null)
			{
				try
				{
					int percent = FloatingPointToInteger.ToInt32((double)bytesWritten / (double)totalSize * 100.0);
					if (percent > 100)
					{
						percent = 100;
					}
					progressPercent.invoke(percent);
				}
				catch (RuntimeException exp)
				{
					TLogManager.Error("Error progress for WriteNetworkStreamFromSource", exp);
				}
			}
		}
	}
}