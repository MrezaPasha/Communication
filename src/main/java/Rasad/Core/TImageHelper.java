package main.java.Rasad.Core;

public final class TImageHelper
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] ImageToByteArray(this Image imageIn)
	public static byte[] ImageToByteArray(Image imageIn)
	{
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
		MemoryStream ms = new MemoryStream();
		imageIn.Save(ms, ImageFormat.Jpeg);
		return ms.ToArray();
	}

	public static Image ResizeImage(Image imgToResize, Size size)
	{
		int sourceWidth = imgToResize.Width;
		int sourceHeight = imgToResize.Height;

		float nPercent = 0;
		float nPercentW = 0;
		float nPercentH = 0;

		nPercentW = (size.Width / (float) sourceWidth);
		nPercentH = (size.Height / (float) sourceHeight);

		if (nPercentH < nPercentW)
		{
			nPercent = nPercentH;
		}
		else
		{
			nPercent = nPercentW;
		}

		int destWidth = (int)(sourceWidth * nPercent);
		int destHeight = (int)(sourceHeight * nPercent);

		Bitmap b = new Bitmap(destWidth, destHeight);
		Graphics g = Graphics.FromImage(b);
		g.InterpolationMode = InterpolationMode.HighQualityBicubic;

		g.DrawImage(imgToResize, 0, 0, destWidth, destHeight);
		g.Dispose();

		return b;
	}
}