package Rasad.Core.Api;

import Rasad.Core.*;

public final class Msvcrt
{
//C# TO JAVA CONVERTER TODO TASK: C# 'unsafe' code is not converted by C# to Java Converter:
//	public static unsafe void CopyUnmanagedMemory(byte* srcPtr, int srcOffset, byte* dstPtr, int dstOffset, int count)
//		{
//			srcPtr += srcOffset;
//			dstPtr += dstOffset;
//
//			memcpy(dstPtr, srcPtr, count);
//		}


	public static void SetUnmanagedMemory(IntPtr dst, int filler, int count)
	{
		memset(dst, filler, count);
	}


//C# TO JAVA CONVERTER TODO TASK: C# 'unsafe' code is not converted by C# to Java Converter:
//	private static extern unsafe byte* memcpy(byte* dst, byte* src, int count);


	private static native void memset(IntPtr dst, int filler, int count);
	static
	{
		System.loadLibrary("msvcrt.dll");
	}
}