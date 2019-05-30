package Rasad.Core.Api;

import Rasad.Core.*;

public final class WinAPIOther
{
	public static final int S_OK = 0;

	public static native int WerAddExcludedApplication(String pwzExeName, boolean bAllUsers);
	static
	{
		System.loadLibrary("wer.dll");
	}
	public static native int WerRemoveExcludedApplication(String pwzExeName, boolean bAllUsers);
}