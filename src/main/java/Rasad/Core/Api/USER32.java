package Rasad.Core.Api;

import Rasad.Core.*;

public class USER32
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Class Variables

	public static final int SM_CXSCREEN = 0;
	public static final int SM_CYSCREEN = 1;

	public static final int CURSOR_SHOWING = 0x00000001;

	/** 
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct ICONINFO
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct ICONINFO
	public final static class ICONINFO
	{
		public boolean fIcon; // Specifies whether this structure defines an icon or a cursor. A value of TRUE specifies
		public int xHotspot; // Specifies the x-coordinate of a cursor's hot spot. If this structure defines an icon, the hot
		public int yHotspot; // Specifies the y-coordinate of the cursor's hot spot. If this structure defines an icon, the hot
		public IntPtr hbmMask = System.IntPtr.Zero; // (HBITMAP) Specifies the icon bitmask bitmap. If this structure defines a black and white icon,
		public IntPtr hbmColor = System.IntPtr.Zero; // (HBITMAP) Handle to the icon color bitmap. This member can be optional if this

		public ICONINFO clone()
		{
			ICONINFO varCopy = new ICONINFO();

			varCopy.fIcon = this.fIcon;
			varCopy.xHotspot = this.xHotspot;
			varCopy.yHotspot = this.yHotspot;
			varCopy.hbmMask = this.hbmMask;
			varCopy.hbmColor = this.hbmColor;

			return varCopy;
		}
	}

	/** 
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct POINT
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct POINT
	public final static class POINT
	{
		public int x;
		public int y;

		public POINT clone()
		{
			POINT varCopy = new POINT();

			varCopy.x = this.x;
			varCopy.y = this.y;

			return varCopy;
		}
	}


	/** 
	 This structure shall be used to keep the size of the screen.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct SIZE
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct SIZE
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

	/** 
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct CURSORINFO
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct CURSORINFO
	public final static class CURSORINFO
	{
		public int cbSize; // Specifies the size, in bytes, of the structure.
		public int flags; // Specifies the cursor state. This parameter can be one of the following values:
		public IntPtr hCursor = System.IntPtr.Zero; // Handle to the cursor.
		public POINT ptScreenPos = new POINT(); // A POINT structure that receives the screen coordinates of the cursor.

		public CURSORINFO clone()
		{
			CURSORINFO varCopy = new CURSORINFO();

			varCopy.cbSize = this.cbSize;
			varCopy.flags = this.flags;
			varCopy.hCursor = this.hCursor;
			varCopy.ptScreenPos = this.ptScreenPos.clone();

			return varCopy;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Functions

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CloseWindow

	/** 
	 
	 
	 @param hWnd
	 @return 
	*/
	public static native boolean CloseWindow(IntPtr hWnd);
	static
	{
		System.loadLibrary("user32.dll");
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region DrawIcon

	/** 
	 
	 
	 @param hDC
	 @param X
	 @param Y
	 @param hIcon
	 @return 
	*/
	public static native boolean DrawIcon(IntPtr hDC, int X, int Y, IntPtr hIcon);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetDesktopWindow
	/** 
	 The GetDesktopWindow function returns a handle to the desktop window. 
	 The desktop window covers the entire screen. 
	 The desktop window is the area on top of which other windows are painted.
	 
	 @return 
	*/
	public static native IntPtr GetDesktopWindow();

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetDC

	/** 
	 This function retrieves a handle to a display device context (DC) for the client area of the specified window. 
	 The display device context can be used in subsequent graphics display interface (GDI) functions to draw in the client area of the window.
	 
	 @param hWnd
	 @return 
	*/
	public static native IntPtr GetDC(IntPtr ptr);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetSystemMetrics

	/** 
 Flags used with the Windows API (User32.dll):GetSystemMetrics(SystemMetric smIndex)
  
 This Enum and declaration signature was written by Gabriel T. Sharp
 ai_productions@verizon.net or osirisgothra@hotmail.com
 Obtained on pinvoke.net, please contribute your code to support the wiki!
	*/
	public enum SystemMetric 
	{
		/** 
		  Width of the screen of the primary display monitor, in pixels. This is the same values obtained by calling GetDeviceCaps as follows: GetDeviceCaps( hdcPrimaryMonitor, HORZRES).
		*/
		SM_CXSCREEN(0),
		/** 
		 Height of the screen of the primary display monitor, in pixels. This is the same values obtained by calling GetDeviceCaps as follows: GetDeviceCaps( hdcPrimaryMonitor, VERTRES).
		*/
		SM_CYSCREEN(1),
		/** 
		 Height of the arrow bitmap on a vertical scroll bar, in pixels.
		*/
		SM_CYVSCROLL(20),
		/** 
		 Width of a vertical scroll bar, in pixels.
		*/
		SM_CXVSCROLL(2),
		/** 
		 Height of a caption area, in pixels.
		*/
		SM_CYCAPTION(4),
		/** 
		 Width of a window border, in pixels. This is equivalent to the SM_CXEDGE value for windows with the 3-D look.
		*/
		SM_CXBORDER(5),
		/** 
		 Height of a window border, in pixels. This is equivalent to the SM_CYEDGE value for windows with the 3-D look.
		*/
		SM_CYBORDER(6),
		/** 
		 Thickness of the frame around the perimeter of a window that has a caption but is not sizable, in pixels. SM_CXFIXEDFRAME is the height of the horizontal border and SM_CYFIXEDFRAME is the width of the vertical border.
		*/
		SM_CXDLGFRAME(7),
		/** 
		 Thickness of the frame around the perimeter of a window that has a caption but is not sizable, in pixels. SM_CXFIXEDFRAME is the height of the horizontal border and SM_CYFIXEDFRAME is the width of the vertical border.
		*/
		SM_CYDLGFRAME(8),
		/** 
		 Height of the thumb box in a vertical scroll bar, in pixels
		*/
		SM_CYVTHUMB(9),
		/** 
		 Width of the thumb box in a horizontal scroll bar, in pixels.
		*/
		SM_CXHTHUMB(10),
		/** 
		 Default width of an icon, in pixels. The LoadIcon function can load only icons with the dimensions specified by SM_CXICON and SM_CYICON
		*/
		SM_CXICON(11),
		/** 
		 Default height of an icon, in pixels. The LoadIcon function can load only icons with the dimensions SM_CXICON and SM_CYICON.
		*/
		SM_CYICON(12),
		/** 
		 Width of a cursor, in pixels. The system cannot create cursors of other sizes.
		*/
		SM_CXCURSOR(13),
		/** 
		 Height of a cursor, in pixels. The system cannot create cursors of other sizes.
		*/
		SM_CYCURSOR(14),
		/** 
		 Height of a single-line menu bar, in pixels.
		*/
		SM_CYMENU(15),
		/** 
		 Width of the client area for a full-screen window on the primary display monitor, in pixels. To get the coordinates of the portion of the screen not obscured by the system taskbar or by application desktop toolbars, call the SystemParametersInfo function with the SPI_GETWORKAREA value.
		*/
		SM_CXFULLSCREEN(16),
		/** 
		 Height of the client area for a full-screen window on the primary display monitor, in pixels. To get the coordinates of the portion of the screen not obscured by the system taskbar or by application desktop toolbars, call the SystemParametersInfo function with the SPI_GETWORKAREA value.
		*/
		SM_CYFULLSCREEN(17),
		/** 
		 For double byte character set versions of the system, this is the height of the Kanji window at the bottom of the screen, in pixels
		*/
		SM_CYKANJIWINDOW(18),
		/** 
		 Nonzero if a mouse with a wheel is installed; zero otherwise
		*/
		SM_MOUSEWHEELPRESENT(75),
		/** 
		 Height of a horizontal scroll bar, in pixels.
		*/
		SM_CYHSCROLL(3),
		/** 
		 Width of the arrow bitmap on a horizontal scroll bar, in pixels.
		*/
		SM_CXHSCROLL(21),
		/** 
		 Nonzero if the debug version of User.exe is installed; zero otherwise.
		*/
		SM_DEBUG(22),
		/** 
		 Nonzero if the left and right mouse buttons are reversed; zero otherwise.
		*/
		SM_SWAPBUTTON(23),
		/** 
		 Reserved for future use
		*/
		SM_RESERVED1(24),
		/** 
		 Reserved for future use
		*/
		SM_RESERVED2(25),
		/** 
		 Reserved for future use
		*/
		SM_RESERVED3(26),
		/** 
		 Reserved for future use
		*/
		SM_RESERVED4(27),
		/** 
		 Minimum width of a window, in pixels.
		*/
		SM_CXMIN(28),
		/** 
		 Minimum height of a window, in pixels.
		*/
		SM_CYMIN(29),
		/** 
		 Width of a button in a window's caption or title bar, in pixels.
		*/
		SM_CXSIZE(30),
		/** 
		 Height of a button in a window's caption or title bar, in pixels.
		*/
		SM_CYSIZE(31),
		/** 
		 Thickness of the sizing border around the perimeter of a window that can be resized, in pixels. SM_CXSIZEFRAME is the width of the horizontal border, and SM_CYSIZEFRAME is the height of the vertical border.
		*/
		SM_CXFRAME(32),
		/** 
		 Thickness of the sizing border around the perimeter of a window that can be resized, in pixels. SM_CXSIZEFRAME is the width of the horizontal border, and SM_CYSIZEFRAME is the height of the vertical border.
		*/
		SM_CYFRAME(33),
		/** 
		 Minimum tracking width of a window, in pixels. The user cannot drag the window frame to a size smaller than these dimensions. A window can override this value by processing the WM_GETMINMAXINFO message.
		*/
		SM_CXMINTRACK(34),
		/** 
		 Minimum tracking height of a window, in pixels. The user cannot drag the window frame to a size smaller than these dimensions. A window can override this value by processing the WM_GETMINMAXINFO message
		*/
		SM_CYMINTRACK(35),
		/** 
		 Width of the rectangle around the location of a first click in a double-click sequence, in pixels. The second click must occur within the rectangle defined by SM_CXDOUBLECLK and SM_CYDOUBLECLK for the system to consider the two clicks a double-click
		*/
		SM_CXDOUBLECLK(36),
		/** 
		 Height of the rectangle around the location of a first click in a double-click sequence, in pixels. The second click must occur within the rectangle defined by SM_CXDOUBLECLK and SM_CYDOUBLECLK for the system to consider the two clicks a double-click. (The two clicks must also occur within a specified time.)
		*/
		SM_CYDOUBLECLK(37),
		/** 
		 Width of a grid cell for items in large icon view, in pixels. Each item fits into a rectangle of size SM_CXICONSPACING by SM_CYICONSPACING when arranged. This value is always greater than or equal to SM_CXICON
		*/
		SM_CXICONSPACING(38),
		/** 
		 Height of a grid cell for items in large icon view, in pixels. Each item fits into a rectangle of size SM_CXICONSPACING by SM_CYICONSPACING when arranged. This value is always greater than or equal to SM_CYICON.
		*/
		SM_CYICONSPACING(39),
		/** 
		 Nonzero if drop-down menus are right-aligned with the corresponding menu-bar item; zero if the menus are left-aligned.
		*/
		SM_MENUDROPALIGNMENT(40),
		/** 
		 Nonzero if the Microsoft Windows for Pen computing extensions are installed; zero otherwise.
		*/
		SM_PENWINDOWS(41),
		/** 
		 Nonzero if User32.dll supports DBCS; zero otherwise. (WinMe/95/98): Unicode
		*/
		SM_DBCSENABLED(42),
		/** 
		 Number of buttons on mouse, or zero if no mouse is installed.
		*/
		SM_CMOUSEBUTTONS(43),
		/** 
		 Identical Values Changed After Windows NT 4.0  
		*/
		SM_CXFIXEDFRAME(7),
		/** 
		 Identical Values Changed After Windows NT 4.0
		*/
		SM_CYFIXEDFRAME(8),
		/** 
		 Identical Values Changed After Windows NT 4.0
		*/
		SM_CXSIZEFRAME(32),
		/** 
		 Identical Values Changed After Windows NT 4.0
		*/
		SM_CYSIZEFRAME(33),
		/** 
		 Nonzero if security is present; zero otherwise.
		*/
		SM_SECURE(44),
		/** 
		 Width of a 3-D border, in pixels. This is the 3-D counterpart of SM_CXBORDER
		*/
		SM_CXEDGE(45),
		/** 
		 Height of a 3-D border, in pixels. This is the 3-D counterpart of SM_CYBORDER
		*/
		SM_CYEDGE(46),
		/** 
		 Width of a grid cell for a minimized window, in pixels. Each minimized window fits into a rectangle this size when arranged. This value is always greater than or equal to SM_CXMINIMIZED.
		*/
		SM_CXMINSPACING(47),
		/** 
		 Height of a grid cell for a minimized window, in pixels. Each minimized window fits into a rectangle this size when arranged. This value is always greater than or equal to SM_CYMINIMIZED.
		*/
		SM_CYMINSPACING(48),
		/** 
		 Recommended width of a small icon, in pixels. Small icons typically appear in window captions and in small icon view
		*/
		SM_CXSMICON(49),
		/** 
		 Recommended height of a small icon, in pixels. Small icons typically appear in window captions and in small icon view.
		*/
		SM_CYSMICON(50),
		/** 
		 Height of a small caption, in pixels
		*/
		SM_CYSMCAPTION(51),
		/** 
		 Width of small caption buttons, in pixels.
		*/
		SM_CXSMSIZE(52),
		/** 
		 Height of small caption buttons, in pixels.
		*/
		SM_CYSMSIZE(53),
		/** 
		 Width of menu bar buttons, such as the child window close button used in the multiple document interface, in pixels.
		*/
		SM_CXMENUSIZE(54),
		/** 
		 Height of menu bar buttons, such as the child window close button used in the multiple document interface, in pixels.
		*/
		SM_CYMENUSIZE(55),
		/** 
		 Flags specifying how the system arranged minimized windows
		*/
		SM_ARRANGE(56),
		/** 
		 Width of a minimized window, in pixels.
		*/
		SM_CXMINIMIZED(57),
		/** 
		 Height of a minimized window, in pixels.
		*/
		SM_CYMINIMIZED(58),
		/** 
		 Default maximum width of a window that has a caption and sizing borders, in pixels. This metric refers to the entire desktop. The user cannot drag the window frame to a size larger than these dimensions. A window can override this value by processing the WM_GETMINMAXINFO message.
		*/
		SM_CXMAXTRACK(59),
		/** 
		 Default maximum height of a window that has a caption and sizing borders, in pixels. This metric refers to the entire desktop. The user cannot drag the window frame to a size larger than these dimensions. A window can override this value by processing the WM_GETMINMAXINFO message.
		*/
		SM_CYMAXTRACK(60),
		/** 
		 Default width, in pixels, of a maximized top-level window on the primary display monitor.
		*/
		SM_CXMAXIMIZED(61),
		/** 
		 Default height, in pixels, of a maximized top-level window on the primary display monitor.
		*/
		SM_CYMAXIMIZED(62),
		/** 
		 Least significant bit is set if a network is present; otherwise, it is cleared. The other bits are reserved for future use
		*/
		SM_NETWORK(63),
		/** 
		 Value that specifies how the system was started: 0-normal, 1-failsafe, 2-failsafe /w net
		*/
		SM_CLEANBOOT(67),
		/** 
		 Width of a rectangle centered on a drag point to allow for limited movement of the mouse pointer before a drag operation begins, in pixels.
		*/
		SM_CXDRAG(68),
		/** 
		 Height of a rectangle centered on a drag point to allow for limited movement of the mouse pointer before a drag operation begins. This value is in pixels. It allows the user to click and release the mouse button easily without unintentionally starting a drag operation.
		*/
		SM_CYDRAG(69),
		/** 
		 Nonzero if the user requires an application to present information visually in situations where it would otherwise present the information only in audible form; zero otherwise.
		*/
		SM_SHOWSOUNDS(70),
		/** 
		 Width of the default menu check-mark bitmap, in pixels.
		*/
		SM_CXMENUCHECK(71),
		/** 
		 Height of the default menu check-mark bitmap, in pixels.
		*/
		SM_CYMENUCHECK(72),
		/** 
		 Nonzero if the computer has a low-end (slow) processor; zero otherwise
		*/
		SM_SLOWMACHINE(73),
		/** 
		 Nonzero if the system is enabled for Hebrew and Arabic languages, zero if not.
		*/
		SM_MIDEASTENABLED(74),
		/** 
		 Nonzero if a mouse is installed; zero otherwise. This value is rarely zero, because of support for virtual mice and because some systems detect the presence of the port instead of the presence of a mouse.
		*/
		SM_MOUSEPRESENT(19),
		/** 
		 Windows 2000 (v5.0+) Coordinate of the top of the virtual screen
		*/
		SM_XVIRTUALSCREEN(76),
		/** 
		 Windows 2000 (v5.0+) Coordinate of the left of the virtual screen
		*/
		SM_YVIRTUALSCREEN(77),
		/** 
		 Windows 2000 (v5.0+) Width of the virtual screen
		*/
		SM_CXVIRTUALSCREEN(78),
		/** 
		 Windows 2000 (v5.0+) Height of the virtual screen
		*/
		SM_CYVIRTUALSCREEN(79),
		/** 
		 Number of display monitors on the desktop
		*/
		SM_CMONITORS(80),
		/** 
		 Windows XP (v5.1+) Nonzero if all the display monitors have the same color format, zero otherwise. Note that two displays can have the same bit depth, but different color formats. For example, the red, green, and blue pixels can be encoded with different numbers of bits, or those bits can be located in different places in a pixel's color value.
		*/
		SM_SAMEDISPLAYFORMAT(81),
		/** 
		 Windows XP (v5.1+) Nonzero if Input Method Manager/Input Method Editor features are enabled; zero otherwise
		*/
		SM_IMMENABLED(82),
		/** 
		 Windows XP (v5.1+) Width of the left and right edges of the focus rectangle drawn by DrawFocusRect. This value is in pixels.
		*/
		SM_CXFOCUSBORDER(83),
		/** 
		 Windows XP (v5.1+) Height of the top and bottom edges of the focus rectangle drawn by DrawFocusRect. This value is in pixels.
		*/
		SM_CYFOCUSBORDER(84),
		/** 
		 Nonzero if the current operating system is the Windows XP Tablet PC edition, zero if not.
		*/
		SM_TABLETPC(86),
		/** 
		 Nonzero if the current operating system is the Windows XP, Media Center Edition, zero if not.
		*/
		SM_MEDIACENTER(87),
		/** 
		 Metrics Other
		*/
		SM_CMETRICS_OTHER(76),
		/** 
		 Metrics Windows 2000
		*/
		SM_CMETRICS_2000(83),
		/** 
		 Metrics Windows NT
		*/
		SM_CMETRICS_NT(88),
		/** 
		 Windows XP (v5.1+) This system metric is used in a Terminal Services environment. If the calling process is associated with a Terminal Services client session, the return value is nonzero. If the calling process is associated with the Terminal Server console session, the return value is zero. The console session is not necessarily the physical console - see WTSGetActiveConsoleSessionId for more information.
		*/
		SM_REMOTESESSION(0x1000),
		/** 
		 Windows XP (v5.1+) Nonzero if the current session is shutting down; zero otherwise
		*/
		SM_SHUTTINGDOWN(0x2000),
		/** 
		 Windows XP (v5.1+) This system metric is used in a Terminal Services environment. Its value is nonzero if the current session is remotely controlled; zero otherwise
		*/
		SM_REMOTECONTROL(0x2001);

		public static final int SIZE = java.lang.Integer.SIZE;

		private int intValue;
		private static java.util.HashMap<Integer, SystemMetric> mappings;
		private static java.util.HashMap<Integer, SystemMetric> getMappings()
		{
			if (mappings == null)
			{
				synchronized (SystemMetric.class)
				{
					if (mappings == null)
					{
						mappings = new java.util.HashMap<Integer, SystemMetric>();
					}
				}
			}
			return mappings;
		}

		private SystemMetric(int value)
		{
			intValue = value;
			getMappings().put(value, this);
		}

		public int getValue()
		{
			return intValue;
		}

		public static SystemMetric forValue(int value)
		{
			return getMappings().get(value);
		}
	}
	/** 
	 Retrieves various system metrics of display elements and system configuration settings.
	 
	 @param abc
	 @return 
	*/
	public static native int GetSystemMetrics(int abc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetWindowDC
	/** 
	 The GetWindowDC function retrieves the device context (DC) for the entire window, 
	 including title bar, menus, and scroll bars. A window device context permits painting anywhere in a window, 
	 because the origin of the device context is the upper-left corner of the window instead of the client area. 
	 GetWindowDC assigns default attributes to the window device context each time it retrieves the device context. 
	 Previous attributes are lost.
	 
	 @param ptr
	 @return 
	*/
	public static native IntPtr GetWindowDC(int ptr);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ReleaseDC

	/** 
	 
	 
	 @param hWnd
	 @param hDc
	 @return 
	*/
	public static native IntPtr ReleaseDC(IntPtr hWnd, IntPtr hDc);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetCursorInfo

	/** 
	 
	 
	 @param pci
	 @return 
	*/
	public static native boolean GetCursorInfo(tangible.OutObject<CURSORINFO> pci);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region CopyIcon

	/** 
	 
	 
	 @param hIcon
	 @return 
	*/
	public static native IntPtr CopyIcon(IntPtr hIcon);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetIconInfo

	/** 
	 
	 
	 @param hIcon
	 @param piconinfo
	 @return 
	*/
	public static native boolean GetIconInfo(IntPtr hIcon, tangible.OutObject<ICONINFO> piconinfo);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region GetCursorPos

	/** 
	 Retrieves the cursor position in screen coordinates.
	 
	 @param lpPoint
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("user32.dll")][return: MarshalAs(UnmanagedType.Bool)] public static extern bool GetCursorPos(out POINT lpPoint);
	public static native boolean GetCursorPos(tangible.OutObject<POINT> lpPoint);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public static native IntPtr GetForegroundWindow();

	public static native int GetWindowThreadProcessId(IntPtr handle, tangible.OutObject<Integer> processId);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

}