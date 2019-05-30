package Rasad.Core.Media;

import Rasad.Core.Api.*;
import Rasad.Core.*;

public final class TScreenCapture
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Class Functions

	private static int ScreenWidth;
	private static int getScreenWidth()
	{
		return ScreenWidth;
	}
	private static void setScreenWidth(int value)
	{
		ScreenWidth = value;
	}
	private static int ScreenHeight;
	private static int getScreenHeight()
	{
		return ScreenHeight;
	}
	private static void setScreenHeight(int value)
	{
		ScreenHeight = value;
	}

	static
	{
		//We pass SM_CXSCREEN constant to GetSystemMetrics to get the X coordinates of screen.
		setScreenWidth(USER32.GetSystemMetrics(USER32.SM_CXSCREEN));

		//We pass SM_CYSCREEN constant to GetSystemMetrics to get the Y coordinates of screen.
		setScreenHeight(USER32.GetSystemMetrics(USER32.SM_CYSCREEN));

	}


	public static Bitmap GetDesktopImage(InterpolationMode interpolationMode, SmoothingMode smoothingMode)
	{
		return GetDesktopImage(interpolationMode, smoothingMode, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static Bitmap GetDesktopImage(InterpolationMode interpolationMode)
	{
		return GetDesktopImage(interpolationMode, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static Bitmap GetDesktopImage()
	{
		return GetDesktopImage(InterpolationMode.NearestNeighbor, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static Bitmap GetDesktopImage(InterpolationMode interpolationMode = InterpolationMode.NearestNeighbor, SmoothingMode smoothingMode = SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat pixelFormat = System.Drawing.Imaging.PixelFormat.Format16bppRgb565)
	public static Bitmap GetDesktopImage(InterpolationMode interpolationMode, SmoothingMode smoothingMode, System.Drawing.Imaging.PixelFormat pixelFormat)
	{
		return GetDesktopImage(200, 125, interpolationMode, smoothingMode, pixelFormat);
	}

	public static Bitmap GetDesktopImage(int width, int height, InterpolationMode interpolationMode, SmoothingMode smoothingMode)
	{
		return GetDesktopImage(width, height, interpolationMode, smoothingMode, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static Bitmap GetDesktopImage(int width, int height, InterpolationMode interpolationMode)
	{
		return GetDesktopImage(width, height, interpolationMode, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static Bitmap GetDesktopImage(int width, int height)
	{
		return GetDesktopImage(width, height, InterpolationMode.NearestNeighbor, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static Bitmap GetDesktopImage(int width, int height, InterpolationMode interpolationMode = InterpolationMode.NearestNeighbor, SmoothingMode smoothingMode = SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat pixelFormat = System.Drawing.Imaging.PixelFormat.Format16bppRgb565)
	public static Bitmap GetDesktopImage(int width, int height, InterpolationMode interpolationMode, SmoothingMode smoothingMode, System.Drawing.Imaging.PixelFormat pixelFormat)
	{
		return GetDesktopImage(new Size(width, height), interpolationMode, smoothingMode, pixelFormat);
	}
	//private static Bitmap GetDesktopImage_(Size destSize)
	//{
	//    int cursorX = 0;
	//    int cursorY = 0;

	//    Icon cursor = CaptureCursorIcon(ref cursorX, ref cursorY);

	//    //In size variable we shall keep the size of the screen.
	//    USER32.SIZE size;

	//    //Variable to keep the handle to bitmap.
	//    IntPtr hBitmap;

	//    //Here we get the handle to the desktop device context.
	//    IntPtr hDC = USER32.GetDC(USER32.GetDesktopWindow());

	//    //Here we make a compatible device context in memory for screen device context.
	//    IntPtr hMemDC = GDI32.CreateCompatibleDC(hDC);

	//    //We pass SM_CXSCREEN constant to GetSystemMetrics to get the X coordinates of screen.
	//    size.cx = USER32.GetSystemMetrics(USER32.SM_CXSCREEN);

	//    //We pass SM_CYSCREEN constant to GetSystemMetrics to get the Y coordinates of screen.
	//    size.cy = USER32.GetSystemMetrics(USER32.SM_CYSCREEN);

	//    //We create a compatible bitmap of screen size using screen device context.
	//    hBitmap = GDI32.CreateCompatibleBitmap(hDC, destSize.Width, destSize.Height);

	//    //As hBitmap is IntPtr we can not check it against null. For this purspose IntPtr.Zero is used.
	//    if (hBitmap != IntPtr.Zero)
	//    {
	//        //Here we select the compatible bitmap in memeory device context and keeps the refrence to Old bitmap.
	//        IntPtr hOld = (IntPtr)GDI32.SelectObject(hMemDC, hBitmap);
	//        Graphics g = Graphics.FromHdc(hDC);

	//        g.DrawIcon(cursor, new Rectangle(cursorX, cursorY, cursor.Width, cursor.Height));
	//        //USER32.DrawIcon(hOld, cursorX, cursorY, cursor.Handle);

	//        //We copy the Bitmap to the memory device context.
	//        GDI32.SetStretchBltMode(hMemDC, GDI32.StretchMode.STRETCH_HALFTONE);
	//        //GDI32.BitBlt(hMemDC, 0, 0,size.cx,size.cy, hDC, 0, 0, GDI32.SRCCOPY);
	//        GDI32.StretchBlt(hMemDC, 0, 0, destSize.Width, destSize.Height, hDC, 0, 0, size.cx, size.cy, GDI32.TernaryRasterOperations.SRCCOPY);
	//        //We select the old bitmap back to the memory device context.
	//        GDI32.SelectObject(hMemDC, hOld);
	//        //We delete the memory device context.
	//        GDI32.DeleteDC(hMemDC);
	//        //We release the screen device context.
	//        USER32.ReleaseDC(USER32.GetDesktopWindow(), hDC);
	//        //Image is created by Image bitmap handle and stored in local variable.
	//        Bitmap bmp = System.Drawing.Image.FromHbitmap(hBitmap);


	//        //Release the memory for compatible bitmap.
	//        GDI32.Remove(hBitmap);
	//        //This statement runs the garbage collector manually.
	//        GC.Collect();
	//        //Return the bitmap 
	//        return bmp;
	//    }
	//    //If hBitmap is null retunrn null.
	//    return null;
	//}



	public static Bitmap GetDesktopImage(Size destSize, InterpolationMode interpolationMode, SmoothingMode smoothingMode)
	{
		return GetDesktopImage(destSize, interpolationMode, smoothingMode, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static Bitmap GetDesktopImage(Size destSize, InterpolationMode interpolationMode)
	{
		return GetDesktopImage(destSize, interpolationMode, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static Bitmap GetDesktopImage(Size destSize)
	{
		return GetDesktopImage(destSize, InterpolationMode.NearestNeighbor, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static Bitmap GetDesktopImage(Size destSize, InterpolationMode interpolationMode = InterpolationMode.NearestNeighbor, SmoothingMode smoothingMode = SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat pixelFormat = System.Drawing.Imaging.PixelFormat.Format16bppRgb565)
	public static Bitmap GetDesktopImage(Size destSize, InterpolationMode interpolationMode, SmoothingMode smoothingMode, System.Drawing.Imaging.PixelFormat pixelFormat)
	{
		int cursorX = 0;
		int cursorY = 0;

		tangible.RefObject<Integer> tempRef_cursorX = new tangible.RefObject<Integer>(cursorX);
		tangible.RefObject<Integer> tempRef_cursorY = new tangible.RefObject<Integer>(cursorY);
		Icon cursor = CaptureCursorIcon(tempRef_cursorX, tempRef_cursorY);
	cursorY = tempRef_cursorY.argValue;
	cursorX = tempRef_cursorX.argValue;

		//In size variable we shall keep the size of the screen.
		USER32.SIZE size = new USER32.SIZE();

		//Variable to keep the handle to bitmap.
		IntPtr hBitmap = System.IntPtr.Zero;

		//Here we get the handle to the desktop device context.
		IntPtr hDC = USER32.GetDC(USER32.GetDesktopWindow());

		//Here we make a compatible device context in memory for screen device context.
		IntPtr hMemDC = GDI32.CreateCompatibleDC(hDC);

		//We pass SM_CXSCREEN constant to GetSystemMetrics to get the X coordinates of screen.
		size.cx = USER32.GetSystemMetrics(USER32.SM_CXSCREEN);

		//We pass SM_CYSCREEN constant to GetSystemMetrics to get the Y coordinates of screen.
		size.cy = USER32.GetSystemMetrics(USER32.SM_CYSCREEN);

		//We create a compatible bitmap of screen size using screen device context.
		hBitmap = GDI32.CreateCompatibleBitmap(hDC, size.cx, size.cy);

		//As hBitmap is IntPtr we can not check it against null. For this purspose IntPtr.Zero is used.
		if (System.IntPtr.OpInequality(hBitmap, IntPtr.Zero))
		{
			//Here we select the compatible bitmap in memeory device context and keeps the refrence to Old bitmap.
			IntPtr hOld = (IntPtr)GDI32.SelectObject(hMemDC, hBitmap);

			//USER32.DrawIcon(hOld, cursorX, cursorY, cursor.Handle);

			//We copy the Bitmap to the memory device context.
			GDI32.SetStretchBltMode(hMemDC, GDI32.StretchMode.STRETCH_HALFTONE);
			GDI32.BitBlt(hMemDC, 0, 0, size.cx, size.cy, hDC, 0, 0, GDI32.TernaryRasterOperations.SRCCOPY);

			USER32.DrawIcon(hMemDC, cursorX, cursorY, cursor.Handle);

			// GDI32.StretchBlt(hMemDC, 0, 0, destSize.Width, destSize.Height, hDC, 0, 0, size.cx, size.cy, GDI32.TernaryRasterOperations.SRCCOPY);

			//We select the old bitmap back to the memory device context.
			GDI32.SelectObject(hMemDC, hOld);
			//We delete the memory device context.
			GDI32.DeleteDC(hMemDC);
			//We release the screen device context.
			USER32.ReleaseDC(USER32.GetDesktopWindow(), hDC);
			//Image is created by Image bitmap handle and stored in local variable.
			Bitmap bmp = System.Drawing.Image.FromHbitmap(hBitmap);

			//Release the memory for compatible bitmap.
			GDI32.DeleteObject(hBitmap);
			//This statement runs the garbage collector manually.
			System.gc();

			// Create the destination Bitmap, and set its resolution
			Bitmap final_Renamed = new Bitmap(destSize.Width, destSize.Height, pixelFormat);
			//final.SetResolution(72,72);

			// Create a Graphics object from the destination Bitmap, and set the quality
			Graphics gfinal = Graphics.FromImage(final_Renamed);
			gfinal.SmoothingMode = smoothingMode;
			gfinal.InterpolationMode = interpolationMode;

			// Do the resize
			gfinal.DrawImage(bmp, 0, 0, destSize.Width, destSize.Height);

			//Return the bitmap 
			return final_Renamed;
		}
		//If hBitmap is null retunrn null.
		return null;
	}


	private static Bitmap CaptureCursorBitmap(tangible.RefObject<Integer> x, tangible.RefObject<Integer> y)
	{
		Bitmap bmp;
		IntPtr hicon = System.IntPtr.Zero;
		USER32.CURSORINFO ci = new USER32.CURSORINFO();
		USER32.ICONINFO icInfo = new USER32.ICONINFO();
		ci.cbSize = System.Runtime.InteropServices.Marshal.SizeOf(ci.clone());
		tangible.OutObject<Rasad.Core.Api.USER32.CURSORINFO> tempOut_ci = new tangible.OutObject<Rasad.Core.Api.USER32.CURSORINFO>();
		if (USER32.GetCursorInfo(tempOut_ci))
		{
		ci = tempOut_ci.argValue;
			if (ci.flags == USER32.CURSOR_SHOWING)
			{
				hicon = USER32.CopyIcon(ci.hCursor);
				tangible.OutObject<Rasad.Core.Api.USER32.ICONINFO> tempOut_icInfo = new tangible.OutObject<Rasad.Core.Api.USER32.ICONINFO>();
				if (USER32.GetIconInfo(hicon, tempOut_icInfo))
				{
				icInfo = tempOut_icInfo.argValue;
					x.argValue = ci.ptScreenPos.x - ((int)icInfo.xHotspot);
					y.argValue = ci.ptScreenPos.y - ((int)icInfo.yHotspot);

					Icon ic = Icon.FromHandle(hicon);
					bmp = ic.ToBitmap();
					return bmp;
				}
			else
			{
				icInfo = tempOut_icInfo.argValue;
			}
			}
		}
	else
	{
		ci = tempOut_ci.argValue;
	}

		return null;
	}


	private static Icon CaptureCursorIcon(tangible.RefObject<Integer> x, tangible.RefObject<Integer> y)
	{
		IntPtr hicon = System.IntPtr.Zero;
		USER32.CURSORINFO ci = new USER32.CURSORINFO();
		USER32.ICONINFO icInfo = new USER32.ICONINFO();
		ci.cbSize = System.Runtime.InteropServices.Marshal.SizeOf(ci.clone());
		tangible.OutObject<Rasad.Core.Api.USER32.CURSORINFO> tempOut_ci = new tangible.OutObject<Rasad.Core.Api.USER32.CURSORINFO>();
		if (USER32.GetCursorInfo(tempOut_ci))
		{
		ci = tempOut_ci.argValue;
			if (ci.flags == USER32.CURSOR_SHOWING)
			{
				hicon = USER32.CopyIcon(ci.hCursor);
				tangible.OutObject<Rasad.Core.Api.USER32.ICONINFO> tempOut_icInfo = new tangible.OutObject<Rasad.Core.Api.USER32.ICONINFO>();
				if (USER32.GetIconInfo(hicon, tempOut_icInfo))
				{
				icInfo = tempOut_icInfo.argValue;
					x.argValue = ci.ptScreenPos.x - ((int)icInfo.xHotspot);
					y.argValue = ci.ptScreenPos.y - ((int)icInfo.yHotspot);

					Icon ic = Icon.FromHandle(hicon);

					return ic;
				}
			else
			{
				icInfo = tempOut_icInfo.argValue;
			}
			}
		}
	else
	{
		ci = tempOut_ci.argValue;
	}

		return null;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] ToByteArray(this Image img)
	public static byte[] ToByteArray(Image img)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] byteArray = new byte[0];
		byte[] byteArray = new byte[0];
		try (MemoryStream stream = new MemoryStream())
		{
			img.Save(stream, System.Drawing.Imaging.ImageFormat.Png);
			stream.Close();

			byteArray = stream.ToArray();
		}
		return byteArray;
	}

	public static byte[] GetDesktopImageAsByteArray(InterpolationMode interpolationMode, SmoothingMode smoothingMode)
	{
		return GetDesktopImageAsByteArray(interpolationMode, smoothingMode, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static byte[] GetDesktopImageAsByteArray(InterpolationMode interpolationMode)
	{
		return GetDesktopImageAsByteArray(interpolationMode, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static byte[] GetDesktopImageAsByteArray()
	{
		return GetDesktopImageAsByteArray(InterpolationMode.NearestNeighbor, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static byte[] GetDesktopImageAsByteArray(InterpolationMode interpolationMode = InterpolationMode.NearestNeighbor, SmoothingMode smoothingMode = SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat pixelFormat = System.Drawing.Imaging.PixelFormat.Format16bppRgb565)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
	public static byte[] GetDesktopImageAsByteArray(InterpolationMode interpolationMode, SmoothingMode smoothingMode, System.Drawing.Imaging.PixelFormat pixelFormat)
	{
		return GetDesktopImage(interpolationMode, smoothingMode, pixelFormat).ToByteArray();
	}

	public static byte[] GetDesktopImageAsByteArray(int width, int height, InterpolationMode interpolationMode, SmoothingMode smoothingMode)
	{
		return GetDesktopImageAsByteArray(width, height, interpolationMode, smoothingMode, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static byte[] GetDesktopImageAsByteArray(int width, int height, InterpolationMode interpolationMode)
	{
		return GetDesktopImageAsByteArray(width, height, interpolationMode, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static byte[] GetDesktopImageAsByteArray(int width, int height)
	{
		return GetDesktopImageAsByteArray(width, height, InterpolationMode.NearestNeighbor, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static byte[] GetDesktopImageAsByteArray(int width, int height, InterpolationMode interpolationMode = InterpolationMode.NearestNeighbor, SmoothingMode smoothingMode = SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat pixelFormat = System.Drawing.Imaging.PixelFormat.Format16bppRgb565)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
	public static byte[] GetDesktopImageAsByteArray(int width, int height, InterpolationMode interpolationMode, SmoothingMode smoothingMode, System.Drawing.Imaging.PixelFormat pixelFormat)
	{
		return GetDesktopImageAsByteArray(new Size(width, height), interpolationMode, smoothingMode, pixelFormat);
	}

	public static byte[] GetDesktopImageAsByteArray(Size destSize, InterpolationMode interpolationMode, SmoothingMode smoothingMode)
	{
		return GetDesktopImageAsByteArray(destSize, interpolationMode, smoothingMode, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static byte[] GetDesktopImageAsByteArray(Size destSize, InterpolationMode interpolationMode)
	{
		return GetDesktopImageAsByteArray(destSize, interpolationMode, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

	public static byte[] GetDesktopImageAsByteArray(Size destSize)
	{
		return GetDesktopImageAsByteArray(destSize, InterpolationMode.NearestNeighbor, SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat.Format16bppRgb565);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static byte[] GetDesktopImageAsByteArray(Size destSize, InterpolationMode interpolationMode = InterpolationMode.NearestNeighbor, SmoothingMode smoothingMode = SmoothingMode.HighSpeed, System.Drawing.Imaging.PixelFormat pixelFormat = System.Drawing.Imaging.PixelFormat.Format16bppRgb565)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
	public static byte[] GetDesktopImageAsByteArray(Size destSize, InterpolationMode interpolationMode, SmoothingMode smoothingMode, System.Drawing.Imaging.PixelFormat pixelFormat)
	{
		return GetDesktopImage(destSize).ToByteArray();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static BitmapImage GetBitmapImage(byte[] screenData)
	public static BitmapImage GetBitmapImage(byte[] screenData)
	{
		BitmapImage BI = new BitmapImage();
		BI.BeginInit();
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
		BI.StreamSource = new MemoryStream(screenData);
		BI.EndInit();
		return BI;
	}
}