package main.java.Rasad.Core.Services.NotificationService.Services;


import java.util.*;
import java.nio.file.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [AttributeUsage(AttributeTargets.Field, Inherited = false, AllowMultiple = false)] public sealed class ServiceInformationAttribute : Attribute
public final class ServiceInformationAttribute
{


	public ServiceInformationAttribute(String serviceKey, String fileName, String serviceName, String folderPath)
	{
		this(serviceKey, fileName, serviceName, folderPath, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public ServiceInformationAttribute(string serviceKey, string fileName, string serviceName, string folderPath, string imageUrl = null)
	public ServiceInformationAttribute(String serviceKey, String fileName, String serviceName, String folderPath, String imageUrl)
	{
		this.setServiceKey(UUID.fromString(serviceKey));
		this.setFileName(fileName);
		this.setServiceName(serviceName);
		this.setFolderPath(folderPath);
		this.setImageUrl(imageUrl);
	}
	public ServiceInformationAttribute(String serviceKey, String fileName, String serviceName)
	{
		this(serviceKey, fileName, serviceName, null, null);

	}
	private String FolderPath;
	public String getFolderPath()
	{
		return FolderPath;
	}
	public void setFolderPath(String value)
	{
		FolderPath = value;
	}

	private UUID ServiceKey;
	public UUID getServiceKey()
	{
		return ServiceKey;
	}
	public void setServiceKey(UUID value)
	{
		ServiceKey = value;
	}
	private String FileName;
	public String getFileName()
	{
		return FileName;
	}
	public void setFileName(String value)
	{
		FileName = value;
	}
	private String ServiceName;
	public String getServiceName()
	{
		return ServiceName;
	}
	public void setServiceName(String value)
	{
		ServiceName = value;
	}

	private String ImageUrl;
	public String getImageUrl()
	{
		return ImageUrl;
	}
	public void setImageUrl(String value)
	{
		ImageUrl = value;
	}

	public String getFullPath()
	{
		if (getFolderPath().isEmpty())
		{
			//TODO MRCOMMENT : must uncomment
			//return Paths.get(AppDomain.CurrentDomain.BaseDirectory).resolve(getFileName()).toString();
			return "comment";
		}
		else
		{
			return Paths.get(getFolderPath()).resolve(getFileName()).toString();
		}
	}

	private UUID PluginKey = null;
	public UUID getPluginKey()
	{
		return PluginKey;
	}
	public void setPluginKey(UUID value)
	{
		PluginKey = value;
	}

	public boolean equals(String serviceName)
	{
		if (this.getServiceKey().toString() == serviceName)
		{
			return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		return getServiceName() + " (" + getFullPath() + ")";
	}
}