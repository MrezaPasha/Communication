package main.java.Rasad.Core.Services.PluginService;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad._core.ParsCalendar;

import java.util.*;
import java.time.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [AttributeUsage(AttributeTargets.Assembly, Inherited = false, AllowMultiple = false)] public class TPluginDefination : Attribute
public class TPluginDefination
{
	public TPluginDefination(String pluginKey, String title, String description, String folderName, String version, String baseRVSVersion, String shamsiReleaseDate, String imageUrl, java.lang.Class initializerType)
	{
		this.setPluginKey(UUID.fromString(pluginKey));
		this.setTitle(title);
		this.setDescription(description);
		this.setFolderName(folderName);
		this.setVersion(version);
		this.setBaseRVSVersion(baseRVSVersion);
		//this.setReleaseDateTime(ShDate.ToDateTime(shamsiReleaseDate));
		this.setReleaseDateTime(LocalDateTime.parse(ParsCalendar.getInstance().gregorianToJalaliDate(shamsiReleaseDate)));
		//
		this.setImageUrl(imageUrl);

		if (initializerType != null)
		{
			this.setInitializerType(initializerType);
			this.setInitializerTypeName(initializerType.getName());
		}
	}
	public TPluginDefination(String pluginKey, String title, String description, String folderName, String version, String baseRVSVersion, String shamsiReleaseDate, String imageUrl, String initializerType, String filePath)
	{
		this(pluginKey, title, description, folderName, version, baseRVSVersion, shamsiReleaseDate, imageUrl, null);
		this.setInitializerTypeName(initializerType);
		this.setFilePath(filePath);
	}

	private UUID PluginKey;
	public final UUID getPluginKey()
	{
		return PluginKey;
	}
	private void setPluginKey(UUID value)
	{
		PluginKey = value;
	}
	private String Title;
	public final String getTitle()
	{
		return Title;
	}
	private void setTitle(String value)
	{
		Title = value;
	}
	private String FolderName;
	public final String getFolderName()
	{
		return FolderName;
	}
	private void setFolderName(String value)
	{
		FolderName = value;
	}
	private String ImageUrl;
	public final String getImageUrl()
	{
		return ImageUrl;
	}
	private void setImageUrl(String value)
	{
		ImageUrl = value;
	}

	private String Version;
	public final String getVersion()
	{
		return Version;
	}
	private void setVersion(String value)
	{
		Version = value;
	}
	private String BaseRVSVersion;
	public final String getBaseRVSVersion()
	{
		return BaseRVSVersion;
	}
	private void setBaseRVSVersion(String value)
	{
		BaseRVSVersion = value;
	}
	private String Description;
	public final String getDescription()
	{
		return Description;
	}
	private void setDescription(String value)
	{
		Description = value;
	}
	private LocalDateTime ReleaseDateTime = LocalDateTime.MIN;
	public final LocalDateTime getReleaseDateTime()
	{
		return ReleaseDateTime;
	}
	private void setReleaseDateTime(LocalDateTime value)
	{
		ReleaseDateTime = value;
	}
	private java.lang.Class InitializerType;
	public final java.lang.Class getInitializerType()
	{
		return InitializerType;
	}
	public final void setInitializerType(java.lang.Class value)
	{
		InitializerType = value;
	}
	private String InitializerTypeName;
	public final String getInitializerTypeName()
	{
		return InitializerTypeName;
	}
	public final void setInitializerTypeName(String value)
	{
		InitializerTypeName = value;
	}
	private String FilePath;
	public final String getFilePath()
	{
		return FilePath;
	}
	public final void setFilePath(String value)
	{
		FilePath = value;
	}

	@Override
	public String toString()
	{
		return getTitle() + " => " + getDescription();
	}
}