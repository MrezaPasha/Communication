package main.java.Rasad.Core.Services.PluginService;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [AttributeUsage(AttributeTargets.Assembly, Inherited = false, AllowMultiple = true)] public class TPluginWindowsServiceDefination : Attribute
public class TPluginWindowsServiceDefination
{
	public TPluginWindowsServiceDefination(String windowsServiceKey, String title, String windowsServiceName, String imageUrl)
	{
		this.setWindowsServiceKey(UUID.fromString(windowsServiceKey));
		this.setTitle(title);
		this.setWindowsServiceName(windowsServiceName);
		this.setImageUrl(imageUrl);
	}

	private UUID WindowsServiceKey;
	public final UUID getWindowsServiceKey()
	{
		return WindowsServiceKey;
	}
	public final void setWindowsServiceKey(UUID value)
	{
		WindowsServiceKey = value;
	}
	private String Title;
	public final String getTitle()
	{
		return Title;
	}
	public final void setTitle(String value)
	{
		Title = value;
	}
	private String WindowsServiceName;
	public final String getWindowsServiceName()
	{
		return WindowsServiceName;
	}
	public final void setWindowsServiceName(String value)
	{
		WindowsServiceName = value;
	}
	private String FileName;
	public final String getFileName()
	{
		return FileName;
	}
	public final void setFileName(String value)
	{
		FileName = value;
	}
	private String ImageUrl;
	public final String getImageUrl()
	{
		return ImageUrl;
	}
	public final void setImageUrl(String value)
	{
		ImageUrl = value;
	}
	@Override
	public String toString()
	{
		return getTitle() + " " + getWindowsServiceName();
	}
}