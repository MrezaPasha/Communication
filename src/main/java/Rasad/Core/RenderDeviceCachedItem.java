package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

public class RenderDeviceCachedItem
{
	public RenderDeviceCachedItem(Rasad.Core.TRenderHelper.SceneOffscreen scene, DeviceEx device)
	{
		this.setScene(scene);
		this.setDevice(device);
		setUsageCount(0);
	}

	private Rasad.Core.TRenderHelper.SceneOffscreen Scene;
	public final Rasad.Core.TRenderHelper.SceneOffscreen getScene()
	{
		return Scene;
	}
	private void setScene(Rasad.Core.TRenderHelper.SceneOffscreen value)
	{
		Scene = value;
	}
	private DeviceEx Device;
	public final DeviceEx getDevice()
	{
		return Device;
	}
	private void setDevice(DeviceEx value)
	{
		Device = value;
	}

	private int UsageCount;
	public final int getUsageCount()
	{
		return UsageCount;
	}
	public final void setUsageCount(int value)
	{
		UsageCount = value;
	}

	public final int getWidth()
	{
		return getScene().getWidth();
	}
	public final int getHeight()
	{
		return getScene().getHeight();
	}
}