package main.java.Rasad.Core;

public final class TImagingHelper
{
	public static boolean IsDisposed(System.Drawing.Bitmap bitmap)
	{
		try
		{
			System.Drawing.Imaging.PixelFormat px = bitmap.PixelFormat;
			int w = bitmap.Width;
			return false;
		}
		catch (java.lang.Exception e)
		{
			return true;
		}
	}
}