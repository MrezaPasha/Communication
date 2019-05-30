package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

public final class TRenderHelper
{
	private static volatile int currentRenderDevices = 0;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public nested classes used only inside rendering classes
	public static class SceneOffscreen implements Closeable
	{
		private static boolean adapterInformationLogged = false;

		private static Direct3DEx _d3d;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Variables
		private int width;
		private int height;

		private Object m_csLock = new Object();
		//private Surface m_RenderTarget = null;
		private Device m_Device = null;

		private D3DImage d3dImage = null;
		private boolean firstRedner = true;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Properties
		public final Device getDirect3DDevice()
		{
			return m_Device;
		}

		public final int getWidth()
		{
			return this.width;
		}
		public final int getHeight()
		{
			return this.height;
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Ctor
		public SceneOffscreen(int w, int h)
		{
			this(null, w, h);
		}

		public SceneOffscreen(D3DImage d3dImage, int w, int h)
		{
			if (_d3d == null)
			{
				_d3d = new Direct3DEx();
			}

			this.width = w;
			this.height = h;
			this.d3dImage = d3dImage;
			firstRedner = true;

			if (m_Device == null)
			{
				//using (

				//)
				//{
				if (!adapterInformationLogged)
				{
					adapterInformationLogged = true;
					try
					{
						TLogManager.Info("Direct3D Adapter count: " + _d3d.Adapters.Count.toString());
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
						for (var item : _d3d.Adapters)
						{
							TLogManager.Info("Adapter " + item.Adapter.toString(), new {DeviceName = _d3d.Adapters[0].Details.DeviceName, Description = _d3d.Adapters[0].Details.Description});
						}
					}
					catch (RuntimeException exp)
					{
						TLogManager.Error("Error writing adapter information log", exp);
					}
				}

				DisplayMode _mode = _d3d.GetAdapterDisplayMode(0);
				PresentParameters _parameters = new PresentParameters();
				_parameters.BackBufferFormat = _mode.Format;
				_parameters.BackBufferCount = 1;
				_parameters.BackBufferWidth = width; // m_Control.Width;
				_parameters.BackBufferHeight = height; // m_Control.Height;
				_parameters.Multisample = MultisampleType.None;
				_parameters.SwapEffect = SwapEffect.Discard;
				_parameters.PresentationInterval = PresentInterval.Default;
				_parameters.Windowed = true;
				_parameters.PresentFlags = PresentFlags.Video | PresentFlags.LockableBackBuffer;


				m_Device = new DeviceEx(_d3d, 0, DeviceType.Hardware, IntPtr.Zero, CreateFlags.Multithreaded | CreateFlags.HardwareVertexProcessing, _parameters);

				TLogManager.Info("GPU: New Device");
				//m_Device = new DeviceEx(_d3d, 0, DeviceType.Hardware, IntPtr.Zero, CreateFlags.Multithreaded  | CreateFlags.EnablePresentStatistics | CreateFlags.MixedVertexProcessing, _parameters);

				//}
			}
		}

		protected void finalize() throws Throwable
		{
			Dispose();
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region Scene Handling

		public final void OnSurfaceReady(tangible.RefObject<Surface> _surface)
		{
			if (d3dImage != null)
			{
				Surface mainSurface = _surface.argValue;

				(() ->
				{
						try
						{

							synchronized (m_csLock)
							{
								if (!d3dImage.IsFrontBufferAvailable)
								{
									d3dImage.Unlock();
								}
								try
								{
									if (d3dImage.TryLock(new System.Windows.Duration(TimeSpan.FromSeconds(1))))
									{
										if (firstRedner)
										{
											firstRedner = false;
											d3dImage.SetBackBuffer(D3DResourceType.IDirect3DSurface9, mainSurface.ComPointer);
										}
										d3dImage.AddDirtyRect(new System.Windows.Int32Rect(0, 0, width, height));

									}
								}
								finally
								{
									d3dImage.Unlock();
								}

							}
						}
						catch (java.lang.Exception e)
						{
						}
				}).RunInUiWait();

			}
		}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#region IDisposable Members
		public final void close() throws IOException
		{
			synchronized (m_csLock)
			{
				if (m_Device != null)
				{
					m_Device.Dispose();
					TLogManager.Info("GPU: Dispose device");
					m_Device = null;
				}
			}
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion
	}

	public static class RenderVariables
	{
		public int surfacePitchY = 0, surfacePitchUV = 0, videoHeightDiv2 = 0, v1 = 0, v2 = 0;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Variables
	private static boolean gpuRenderModeAvailable = true;

	private static ArrayList<RenderDeviceCachedItem> _cachedRenderDevices = new ArrayList<RenderDeviceCachedItem>();
	private static Object lockObjCachedRenderDevices = new Object();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public static boolean CheckGPUAvailable()
	{
		// check if gpu render mode is available
		try
		{
			Rasad.Core.TRenderHelper.SceneOffscreen tempScene = new Rasad.Core.TRenderHelper.SceneOffscreen(100, 100);
			try
			{
				DeviceEx device = (DeviceEx)tempScene.getDirect3DDevice();
				try (Surface tempSurface = Surface.CreateRenderTargetEx((DeviceEx)device, 100, 100, Format.X8R8G8B8, MultisampleType.None, 0, true, Usage.None))
				{
					boolean _i420Format = false;
					try (tangible.OutObject<Boolean> tempOut__i420Format = new tangible.OutObject<Boolean>();
					Surface yuvSurface = TRenderHelper.CreateYUVSurface(device, 100, 100, tempOut__i420Format);
					_i420Format = tempOut__i420Format.argValue)
					{
					}
				}
			}
			finally
			{
				tempScene.close();
			}
			gpuRenderModeAvailable = true;
		}
		catch (RuntimeException exp)
		{
			TLogManager.Warn("GPU not available on this machine", exp);
			gpuRenderModeAvailable = false;
		}
		return gpuRenderModeAvailable;
	}
	static
	{
		setDefaultRenderModeManyView(CameraStreamRenderMode.GPU);
		setDefaultRenderModeFewView(CameraStreamRenderMode.GPU);
	}
	/** 
	 Live
	*/
	private static CameraStreamRenderMode DefaultRenderModeManyView = CameraStreamRenderMode.values()[0];
	public static CameraStreamRenderMode getDefaultRenderModeManyView()
	{
		return DefaultRenderModeManyView;
	}
	public static void setDefaultRenderModeManyView(CameraStreamRenderMode value)
	{
		DefaultRenderModeManyView = value;
	}
	/** 
	 Playback
	*/
	private static CameraStreamRenderMode DefaultRenderModeFewView = CameraStreamRenderMode.values()[0];
	public static CameraStreamRenderMode getDefaultRenderModeFewView()
	{
		return DefaultRenderModeFewView;
	}
	public static void setDefaultRenderModeFewView(CameraStreamRenderMode value)
	{
		DefaultRenderModeFewView = value;
	}

	public static CameraStreamRenderMode getActiveRenderModeManyView()
	{
			//return CameraStreamRenderMode.GPU;
			//return CameraStreamRenderMode.CPU;

		if (gpuRenderModeAvailable)
		{
			return getDefaultRenderModeManyView();
		}
		else
		{
			return CameraStreamRenderMode.CPU;
		}
	}

	public static CameraStreamRenderMode getActiveRenderModeFewView()
	{
		if (gpuRenderModeAvailable)
		{
			return getDefaultRenderModeFewView();
		}
		else
		{
			return CameraStreamRenderMode.CPU;
		}
	}

	public static GPUDisplayMethod getGPUDisplayMethod()
	{
		return GPUDisplayMethod.Method1UseSurfaces;
	}

	public static boolean getGPURenderModeAvailable()
	{
		return gpuRenderModeAvailable;
	}

	private static int MakeFourCC(int ch0, int ch1, int ch2, int ch3)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return ((int)(byte)(ch0) | ((int)(byte)(ch1) << 8) | ((int)(byte)(ch2) << 16) | ((int)(byte)(ch3) << 24));
		return ((int)(byte)(ch0) | ((int)(byte)(ch1) << 8) | ((int)(byte)(ch2) << 16) | ((int)(byte)(ch3) << 24));
	}

	public static Surface CreateYUVSurface(DeviceEx device, int videoWidth, int videoHeight, tangible.OutObject<Boolean> i420Format)
	{
		Surface surfaceYuv = null;
		i420Format.argValue = false;

		// I420 not supported, use YV12
		surfaceYuv = Surface.CreateOffscreenPlainEx(device, videoWidth, videoHeight, (Format)MakeFourCC('Y', 'V', '1', '2'), Pool.Default, Usage.None);
		return surfaceYuv;
	}

	public static Rasad.Core.TRenderHelper.RenderVariables CalculateRenderVariables(Surface surfaceYuv, int actualVideoWidth, int actualVideoHeight)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var dt = surfaceYuv.LockRectangle(LockFlags.Discard);
		try
		{
			Rasad.Core.TRenderHelper.RenderVariables retVal = new Rasad.Core.TRenderHelper.RenderVariables();
			retVal.surfacePitchY = dt.Pitch;
			retVal.surfacePitchUV = retVal.surfacePitchY / 2;
			retVal.v1 = actualVideoHeight * retVal.surfacePitchY;
			retVal.v2 = actualVideoHeight / 2 * retVal.surfacePitchUV;
			retVal.videoHeightDiv2 = actualVideoHeight / 2;
			return retVal;
		}
		finally
		{
			surfaceYuv.UnlockRectangle();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Manager Render Devices
	private static boolean FreeupUnusedRenderDevices(int maxCount)
	{
		synchronized (lockObjCachedRenderDevices)
		{
			// only freeup one item
			if (_cachedRenderDevices.size() < maxCount)
			{
				return false;
			}
			RenderDeviceCachedItem itemToFree = null;
			for (RenderDeviceCachedItem item : _cachedRenderDevices)
			{
				if (item.getUsageCount() == 0)
				{
					itemToFree = item;
					break;
				}
			}
			if (itemToFree != null)
			{
				itemToFree.getDevice().Dispose();
				itemToFree.getScene().close();
				_cachedRenderDevices.remove(itemToFree);
				return true;
			}
			else
			{
				return false;
			}
		}
	}

	public static void FreeUpUnusedRenderDevices()
	{
		while (FreeupUnusedRenderDevices(0))
		{
			// nothing to do
		}
	}

	public static RenderDeviceCachedItem UseCachedRenderDevice(int width, int height)
	{
		if (getGPUDisplayMethod() == VideoSurveillance.Core.GPUDisplayMethod.Method2UseDevices)
		{
			RenderDeviceCachedItem retVal = null;
			synchronized (lockObjCachedRenderDevices)
			{
				retVal = _cachedRenderDevices.FirstOrDefault(s -> s.Scene.Width == width && s.Scene.Height == height && s.UsageCount == 0);
				if (retVal == null)
				{
					//FreeupUnusedRenderDevices(maxRenderDeviceCount);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
						///#region Generate New
					Rasad.Core.TRenderHelper.SceneOffscreen scene = new Rasad.Core.TRenderHelper.SceneOffscreen(width, height);
					DeviceEx device = (DeviceEx)scene.getDirect3DDevice();
					RenderDeviceCachedItem newItem = new RenderDeviceCachedItem(scene, device);
					_cachedRenderDevices.add(newItem);
					retVal = newItem;
					TLogManager.Info("Render Device Count = " + String.valueOf(_cachedRenderDevices.size()));
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
						///#endregion
				}
				retVal.setUsageCount(retVal.getUsageCount() + 1);
			}
			return retVal;

			// =============================================

			//TLogManager.Debug("Generate Render Device");

			//RenderDeviceCachedItem retVal = null;
			///#region Generate New
			//Rasad.Core.TRenderHelper.SceneOffscreen scene = new Rasad.Core.TRenderHelper.SceneOffscreen(width, height);
			//DeviceEx device = (DeviceEx)scene.Direct3DDevice;
			//RenderDeviceCachedItem newItem = new RenderDeviceCachedItem(scene, device);
			////lock (lockObjCachedRenderDevices)
			////{
			////    _cachedRenderDevices.Add(newItem);
			////}
			//retVal = newItem;
			//System.Threading.Interlocked.Increment(ref currentRenderDevices);
			///#endregion
			//TLogManager.Debug("Generate Render Device Done - count = " + currentRenderDevices.ToString());
			//return retVal;

			// =============================================
		}
		else if (getGPUDisplayMethod() == VideoSurveillance.Core.GPUDisplayMethod.Method1UseSurfaces)
		{
			RenderDeviceCachedItem retVal = null;
			synchronized (lockObjCachedRenderDevices)
			{
				int diffTolerance = 200;
				int maxRenderDeviceCount = 7;
				for (RenderDeviceCachedItem item : _cachedRenderDevices)
				{
					int diff1 = Math.abs(item.getScene().getWidth() - width);
					int diff2 = Math.abs(item.getScene().getHeight() - height);
					if (diff1 <= diffTolerance && diff2 < diffTolerance)
					{
						retVal = item;
						break;
					}
				}
				if (retVal == null)
				{
					FreeupUnusedRenderDevices(maxRenderDeviceCount);
					if (_cachedRenderDevices.size() == maxRenderDeviceCount)
					{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
							///#region Find Nearest
						// no free item - use nearest available device
						RenderDeviceCachedItem nearestItem = _cachedRenderDevices.get(0);
						int nearestDiff = Math.abs(nearestItem.getScene().getWidth() - width) + Math.abs(nearestItem.getScene().getHeight() - height);
						for (RenderDeviceCachedItem item : _cachedRenderDevices)
						{
							int diff = Math.abs(item.getScene().getWidth() - width) + Math.abs(item.getScene().getHeight() - height);
							if (diff < nearestDiff)
							{
								nearestItem = item;
								nearestDiff = diff;
							}
						}
						retVal = nearestItem;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
							///#endregion
					}
					else
					{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
							///#region Generate New
						Rasad.Core.TRenderHelper.SceneOffscreen scene = new Rasad.Core.TRenderHelper.SceneOffscreen(width, height);
						DeviceEx device = (DeviceEx)scene.getDirect3DDevice();
						RenderDeviceCachedItem newItem = new RenderDeviceCachedItem(scene, device);
						_cachedRenderDevices.add(newItem);
						retVal = newItem;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
							///#endregion
					}
				}
				retVal.setUsageCount(retVal.getUsageCount() + 1);
			}
			return retVal;
		}
		else
		{
			TLogManager.Error("Invalid gpu display mode: " + getGPUDisplayMethod().toString());
			return null;
		}
	}

	public static void ReleaseCachedRenderDevice(RenderDeviceCachedItem item)
	{
		synchronized (lockObjCachedRenderDevices)
		{
			item.setUsageCount(item.getUsageCount() - 1);
		}

		// =============================================

		//item.Device.Dispose();
		//item.Scene.Dispose();
		//System.Threading.Interlocked.Decrement(ref currentRenderDevices);
		//TLogManager.Debug("Release Render Device - Count = " + currentRenderDevices.ToString());

		// =============================================

		//lock (lockObjCachedRenderDevices)
		//{
		//    item.UsageCount--;
		//}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}