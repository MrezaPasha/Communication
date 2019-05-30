package main.java.Rasad.Core.Api;

import Rasad.Core.*;

public class GDI32
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Common Structure

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Point
	/** 
	 The POINT structure defines the x- and y-coordinates of a point.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct POINT
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct POINT
	public final static class POINT
	{
		public int X;
		public int Y;

		public POINT()
		{
		}

		public POINT(int x, int y)
		{
			this.X = x;
			this.Y = y;
		}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
		public static implicit operator Point(POINT p)
		{
			return new System.Drawing.Point(p.X, p.Y);
		}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
		public static implicit operator POINT(System.Drawing.Point p)
		{
			return new POINT(p.X, p.Y);
		}

		public POINT clone()
		{
			POINT varCopy = new POINT();

			varCopy.X = this.X;
			varCopy.Y = this.Y;

			return varCopy;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Rect
	/** 
	 The RECT structure defines the x-y-width-height of a RECT.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct RECT
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct RECT
	public final static class RECT
	{
		public int X;
		public int Y;
		public int width;
		public int height;

		public RECT()
		{
		}

		public RECT(System.Drawing.Rectangle rect)
		{
			this.X = rect.X;
			this.Y = rect.Y;
			this.width = rect.Width;
			this.height = rect.Height;
		}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
		public static implicit operator Rectangle(RECT rect)
		{
			return new System.Drawing.Rectangle(rect.X, rect.Y, rect.width, rect.height);
		}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
		public static implicit operator RECT(System.Drawing.Rectangle rect)
		{
			return new RECT(rect);
		}

		public RECT clone()
		{
			RECT varCopy = new RECT();

			varCopy.X = this.X;
			varCopy.Y = this.Y;
			varCopy.width = this.width;
			varCopy.height = this.height;

			return varCopy;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region SIZE

	/** 
	 This structure shall be used to keep the size of the screen.
	*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct SIZE
	public final static class SIZE
	{
		public int cx;
		public int cy;

		public SIZE clone()
		{
			SIZE varCopy = new SIZE();

			varCopy.cx = this.cx;
			varCopy.cy = this.cy;

			return varCopy;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Functions

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region AbortDoc
	/** 
	  Stops the current print job and erases everything drawn since the last call to the StartDoc function.
	 
	 @param hdc
	 @return 
	*/
	public static native int AbortDoc(IntPtr hdc);
	static
	{
		System.loadLibrary("gdi32.dll");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region AbortPath
	/** 
	 Closes and discards any paths in the specified device context.
	 
	 @param hdc
	 @return 
	*/
	public static native boolean AbortPath(IntPtr hdc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region AddFontMemResourceEx
	/** 
	 Adds the font resource from a memory image to the system.
	 
	 @param pbFont
	 @param cbFont
	 @param pdv
	 @param pcFonts
	 @return 
	*/
	public static native IntPtr AddFontMemResourceEx(byte[] pbFont, int cbFont, IntPtr pdv, tangible.OutObject<Integer> pcFonts);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region AddFontResource
	/** 
	 Adds the font resource from the specified file to the system font table.
	 
	 @param lpszFilename
	 @return 
	*/
	public static native int AddFontResource(String lpszFilename);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region AlphaBlend

	/** 
	 BLENDFUNCTION
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct BLENDFUNCTION
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct BLENDFUNCTION
	public final static class BLENDFUNCTION
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte BlendOp;
		private byte BlendOp;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte BlendFlags;
		private byte BlendFlags;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte SourceConstantAlpha;
		private byte SourceConstantAlpha;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte AlphaFormat;
		private byte AlphaFormat;

		public BLENDFUNCTION()
		{
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public BLENDFUNCTION(byte op, byte flags, byte alpha, byte format)
		public BLENDFUNCTION(byte op, byte flags, byte alpha, byte format)
		{
			BlendOp = op;
			BlendFlags = flags;
			SourceConstantAlpha = alpha;
			AlphaFormat = format;
		}

		public BLENDFUNCTION clone()
		{
			BLENDFUNCTION varCopy = new BLENDFUNCTION();

			varCopy.BlendOp = this.BlendOp;
			varCopy.BlendFlags = this.BlendFlags;
			varCopy.SourceConstantAlpha = this.SourceConstantAlpha;
			varCopy.AlphaFormat = this.AlphaFormat;

			return varCopy;
		}
	}

	//
	// currently defined blend operation
	//
	public static final int AC_SRC_OVER = 0x00;

	//
	// currently defined alpha format
	//
	public static final int AC_SRC_ALPHA = 0x01;

	/** 
	 This function displays bitmaps that have transparent or semitransparent pixels on Microsoft Mobile Os'es
	 
	 @param hdcDest
	 @param nXOriginDest
	 @param nYOriginDest
	 @param nWidthDest
	 @param nHeightDest
	 @param hdcSrc
	 @param nXOriginSrc
	 @param nYOriginSrc
	 @param nWidthSrc
	 @param nHeightSrc
	 @param blendFunction
	 @return 
	*/
	public static native boolean AlphaBlend(IntPtr hdcDest, int nXOriginDest, int nYOriginDest, int nWidthDest, int nHeightDest, IntPtr hdcSrc, int nXOriginSrc, int nYOriginSrc, int nWidthSrc, int nHeightSrc, BLENDFUNCTION blendFunction);
	static
	{
		System.loadLibrary("gdi32.dll");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region BitBlt
	/** 
		Performs a bit-block transfer of the color data corresponding to a
		rectangle of pixels from the specified source device context into
		a destination device context.
	 
	 @param hdc Handle to the destination device context.
	 @param nXDest The leftmost x-coordinate of the destination rectangle (in pixels).
	 @param nYDest The topmost y-coordinate of the destination rectangle (in pixels).
	 @param nWidth The width of the source and destination rectangles (in pixels).
	 @param nHeight The height of the source and the destination rectangles (in pixels).
	 @param hdcSrc Handle to the source device context.
	 @param nXSrc The leftmost x-coordinate of the source rectangle (in pixels).
	 @param nYSrc The topmost y-coordinate of the source rectangle (in pixels).
	 @param dwRop A raster-operation code.
	 @return 
		<c>true</c> if the operation succeeded, <c>false</c> otherwise.
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("gdi32.dll")][return: MarshalAs(UnmanagedType.Bool)] public static extern bool BitBlt(IntPtr hdc, int nXDest, int nYDest, int nWidth, int nHeight, IntPtr hdcSrc, int nXSrc, int nYSrc, TernaryRasterOperations dwRop);
	public static native boolean BitBlt(IntPtr hdc, int nXDest, int nYDest, int nWidth, int nHeight, IntPtr hdcSrc, int nXSrc, int nYSrc, TernaryRasterOperations dwRop);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CancelDC

	/** 
	 Cancels any pending operation on the specified device context.
	 
	 @param hdc
	 @return 
	*/
	public static native boolean CancelDC(IntPtr hdc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreateDC

	public static native IntPtr CreateDC(IntPtr lpszDriver, String lpszDevice, IntPtr lpszOutput, IntPtr lpInitData);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreateIC
	/** 
	 The CreateIC function creates an information context for the specified device. 
	 The information context provides a fast way to get information about the device without creating a device context (DC). 
	 However, GDI drawing functions cannot accept a handle to an information context.
	 
	 @param lpszDriver in Pointer to a null-terminated character string that specifies the name of the device driver (for example, Epson). 
	 @param lpszDevice in Pointer to a null-terminated character string that specifies the name of the specific output device being used, as shown by the Print Manager (for example, Epson FX-80). It is not the printer model name. The lpszDevice parameter must be used. 
	 @param lpszOutput This parameter is ignored and should be set to NULL. It is provided only for compatibility with 16-bit Windows. 
	 @param lpdvmInit in Pointer to a DEVMODE structure containing device-specific initialization data for the device driver. The DocumentProperties function retrieves this structure filled in for a specified device. The lpdvmInit parameter must be NULL if the device driver is to use the default initialization (if any) specified by the user. 
	 @return If the function succeeds, the return value is the handle to an information context. If the function fails, the return value is NULL.
	*/
	public static native IntPtr CreateIC(String lpszDriver, String lpszDevice, String lpszOutput, IntPtr lpdvmInit);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreateCompatibleDC

	public static native IntPtr CreateCompatibleDC(IntPtr hdc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreateCompatibleBitmap

	public static native IntPtr CreateCompatibleBitmap(IntPtr hdc, int nWidth, int nHeight);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreateDiscardableBitmap

	/** 
	 The CreateDiscardableBitmap API
	 
	 @param hdc
	 @param nWidth
	 @param nHeight
	 @return 
	*/
	public static native IntPtr CreateDiscardableBitmap(IntPtr hdc, int nWidth, int nHeight);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CombineRgn

	public enum CombineRgnStyles 
	{
		RGN_AND(1),
		RGN_OR(2),
		RGN_XOR(3),
		RGN_DIFF(4),
		RGN_COPY(5),
		RGN_MIN(1),
		RGN_MAX(5);

		public static final int SIZE = java.lang.Integer.SIZE;

		private int intValue;
		private static java.util.HashMap<Integer, CombineRgnStyles> mappings;
		private static java.util.HashMap<Integer, CombineRgnStyles> getMappings()
		{
			if (mappings == null)
			{
				synchronized (CombineRgnStyles.class)
				{
					if (mappings == null)
					{
						mappings = new java.util.HashMap<Integer, CombineRgnStyles>();
					}
				}
			}
			return mappings;
		}

		private CombineRgnStyles(int value)
		{
			intValue = value;
			getMappings().put(value, this);
		}

		public int getValue()
		{
			return intValue;
		}

		public static CombineRgnStyles forValue(int value)
		{
			return getMappings().get(value);
		}
	}

	/**CombineRgn Return Values:
	*/
	public static final int ERROR = 0;
	public static final int NULLREGION = 1;
	public static final int SIMPLEREGION = 2;
	public static final int COMPLEXREGION = 3;

	/** 
	 The CombineRgn API
	 
	 @param hrgnDest
	 @param hrgnSrc1
	 @param hrgnSrc2
	 @param fnCombineMode
	 @return 
	*/
	public static native int CombineRgn(IntPtr hrgnDest, IntPtr hrgnSrc1, IntPtr hrgnSrc2, CombineRgnStyles fnCombineMode);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreatePolygonRgn

	/** 
	 The CreatePolygonRgn API
	 Values for fnPolyFillMode can be 1=Alternate or 2=Winding
	 
	 @param lppt
	 @param cPoints
	 @param fnPolyFillMode
	 @return 
	*/
	public static native IntPtr CreatePolygonRgn(POINT[] lppt, int cPoints, int fnPolyFillMode);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreatePolyPolygonRgn

	/** 
	 The CreatePolyPolygonRgn API
	 
	 @param lppt
	 @param lpPolyCounts
	 @param nCount
	 @param fnPolyFillMode
	 @return 
	*/
	public static native IntPtr CreatePolyPolygonRgn(POINT[] lppt, int[] lpPolyCounts, int nCount, int fnPolyFillMode);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreateEllipticRgn
	/** 
	 The CreateEllipticRgn API
	 
	 @param nLeftRect
	 @param nTopRect
	 @param nRightRect
	 @param nBottomRect
	 @return 
	*/
	public static native IntPtr CreateEllipticRgn(int nLeftRect, int nTopRect, int nRightRect, int nBottomRect);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CreateRectRgnIndirect

	/** 
	 The CreateRectRgnIndirect API
	 
	 @param lprc
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("gdi32.dll")] public static extern IntPtr CreateRectRgnIndirect([In] ref RECT lprc);
	public static native IntPtr CreateRectRgnIndirect(tangible.RefObject<RECT> lprc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region DeleteDC
	/** 
	 
	 
	 @param hDc
	 @return 
	*/
	public static native IntPtr DeleteDC(IntPtr hDc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region DeleteObject
	/** 
	 
	 
	 @param hDc
	 @return 
	*/
	public static native IntPtr DeleteObject(IntPtr hDc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ellipse
	/** 
	 The Ellipse API
	 
	 @param hdc
	 @param nLeftRect
	 @param nTopRect
	 @param nRightRect
	 @param nBottomRect
	 @return 
	*/
	public static native boolean Ellipse(IntPtr hdc, int nLeftRect, int nTopRect, int nRightRect, int nBottomRect);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region FillRgn
	/** 
	 The FillRgn API
	 
	 @param hdc
	 @param hrgn
	 @param hbr
	 @return 
	*/
	public static native boolean FillRgn(IntPtr hdc, IntPtr hrgn, IntPtr hbr);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Rectangle

	/** 
	 The Rectangle API
	 
	 @param hdc
	 @param nLeftRect
	 @param nTopRect
	 @param nRightRect
	 @param nBottomRect
	 @return 
	*/
	public static native boolean Rectangle(IntPtr hdc, int nLeftRect, int nTopRect, int nRightRect, int nBottomRect);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region FrameRgn

	/** 
	 The FrameRgn API
	 
	 @param hdc
	 @param hrgn
	 @param hbr
	 @param nWidth
	 @param nHeight
	 @return 
	*/
	public static native boolean FrameRgn(IntPtr hdc, IntPtr hrgn, IntPtr hbr, int nWidth, int nHeight);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region FillPath
	/** 
	 The FillPath API
	 
	 @param hdc
	 @return 
	*/
	public static native boolean FillPath(IntPtr hdc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetBkColor

	public static native int GetBkColor(IntPtr hdc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetBkMode

	public static native int GetBkMode(IntPtr hdc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region StretchBlt

	public enum StretchMode
	{
		STRETCH_ANDSCANS(1),
		STRETCH_ORSCANS(2),
		STRETCH_DELETESCANS(3),
		STRETCH_HALFTONE(4);

		public static final int SIZE = java.lang.Integer.SIZE;

		private int intValue;
		private static java.util.HashMap<Integer, StretchMode> mappings;
		private static java.util.HashMap<Integer, StretchMode> getMappings()
		{
			if (mappings == null)
			{
				synchronized (StretchMode.class)
				{
					if (mappings == null)
					{
						mappings = new java.util.HashMap<Integer, StretchMode>();
					}
				}
			}
			return mappings;
		}

		private StretchMode(int value)
		{
			intValue = value;
			getMappings().put(value, this);
		}

		public int getValue()
		{
			return intValue;
		}

		public static StretchMode forValue(int value)
		{
			return getMappings().get(value);
		}
	}

	/** 
	 only if WinVer >= 5.0.0 (see wingdi.h)
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum TernaryRasterOperations : uint
	public enum TernaryRasterOperations 
	{
		SRCCOPY(0x00CC0020), // dest = source
		SRCPAINT(0x00EE0086), // dest = source OR dest
		SRCAND(0x008800C6), // dest = source AND dest
		SRCINVERT(0x00660046), // dest = source XOR dest
		SRCERASE(0x00440328), // dest = source AND (NOT dest )
		NOTSRCCOPY(0x00330008), // dest = (NOT source)
		NOTSRCERASE(0x001100A6), // dest = (NOT src) AND (NOT dest)
		MERGECOPY(0x00C000CA), // dest = (source AND pattern)
		MERGEPAINT(0x00BB0226), // dest = (NOT source) OR dest
		PATCOPY(0x00F00021), // dest = pattern
		PATPAINT(0x00FB0A09), // dest = DPSnoo
		PATINVERT(0x005A0049), // dest = pattern XOR dest
		DSTINVERT(0x00550009), // dest = (NOT dest)
		BLACKNESS(0x00000042), // dest = BLACK
		WHITENESS(0x00FF0062); // dest = WHITE

		public static final int SIZE = java.lang.Integer.SIZE;

		private int intValue;
		private static java.util.HashMap<Integer, TernaryRasterOperations> mappings;
		private static java.util.HashMap<Integer, TernaryRasterOperations> getMappings()
		{
			if (mappings == null)
			{
				synchronized (TernaryRasterOperations.class)
				{
					if (mappings == null)
					{
						mappings = new java.util.HashMap<Integer, TernaryRasterOperations>();
					}
				}
			}
			return mappings;
		}

		private TernaryRasterOperations(int value)
		{
			intValue = value;
			getMappings().put(value, this);
		}

		public int getValue()
		{
			return intValue;
		}

		public static TernaryRasterOperations forValue(int value)
		{
			return getMappings().get(value);
		}
	}

	/** 
	 Copies bits from one device context onto another.
	 
	 @param hdcDest
	 @param nXOriginDest
	 @param nYOriginDest
	 @param nWidthDest
	 @param nHeightDest
	 @param hdcSrc
	 @param nXOriginSrc
	 @param nYOriginSrc
	 @param nWidthSrc
	 @param nHeightSrc
	 @param dwRop
	 @return 
	*/
	public static native boolean StretchBlt(IntPtr hdcDest, int nXOriginDest, int nYOriginDest, int nWidthDest, int nHeightDest, IntPtr hdcSrc, int nXOriginSrc, int nYOriginSrc, int nWidthSrc, int nHeightSrc, TernaryRasterOperations dwRop);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetPixel

	/** 
	 The GetPixel API
	 
	 @param hdc
	 @param nXPos
	 @param nYPos
	 @return 
	*/
	public static native int GetPixel(IntPtr hdc, int nXPos, int nYPos);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetBitmapBits

	/** 
	 The GetBitmapBits API
	 
	 @param hbmp
	 @param cbBuffer
	 @param lpvBits
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("gdi32.dll")] public static extern int GetBitmapBits(IntPtr hbmp, int cbBuffer,[Out] byte[] lpvBits);
	public static native int GetBitmapBits(IntPtr hbmp, int cbBuffer, byte[] lpvBits);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region SetStretchBltMode
	/** 
	 The SetStretchBltMode function sets the bitmap stretching mode in the specified device context.
	 
	 @param hdc
	 @param iStretchMode
	 @return 
	*/
	public static native int SetStretchBltMode(IntPtr hdc, StretchMode iStretchMode);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region SelectObject

	public static native IntPtr SelectObject(IntPtr hdc, IntPtr bmp);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}