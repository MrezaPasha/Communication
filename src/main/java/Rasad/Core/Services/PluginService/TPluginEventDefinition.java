package main.java.Rasad.Core.Services.PluginService;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [AttributeUsage(AttributeTargets.Assembly, Inherited = false, AllowMultiple = true)] public class TPluginEventDefinition : Attribute
public class TPluginEventDefinition
{
	private TPluginEventDefinition(String key, String title, String description, String imageUrl)
	{
		this.setKey(UUID.fromString(key));
		this.setTitle(title);
		this.setDescription(description);
		this.setImageUrl(imageUrl);
	}

	public TPluginEventDefinition(String key, String title, String description, String imageUrl, String iTriggerTypeName, String iTriggerEditorTypeName)
	{
		this(key, title, description, imageUrl);
		this.setITriggerTypeName(iTriggerTypeName);
		this.setITriggerEditorTypeName(iTriggerEditorTypeName);
	}
	public TPluginEventDefinition(String key, String title, String description, String imageUrl, java.lang.Class iTriggerType, java.lang.Class iTriggerEditorType)
	{
		this(key, title, description, imageUrl);
		this.setITriggerType(iTriggerType);
		this.setITriggerTypeName(GetTypeName(iTriggerType));

		this.setITriggerEditorType(iTriggerEditorType);
		this.setITriggerEditorTypeName(GetTypeName(iTriggerEditorType));
	}

	private String GetTypeName(java.lang.Class type)
	{
		return type.getName() + ", " + type.getTypeName();
	}
	private UUID Key;
	public final UUID getKey()
	{
		return Key;
	}
	private void setKey(UUID value)
	{
		Key = value;
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
	private String Description;
	public final String getDescription()
	{
		return Description;
	}
	public final void setDescription(String value)
	{
		Description = value;
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

	private String ITriggerTypeName;
	public final String getITriggerTypeName()
	{
		return ITriggerTypeName;
	}
	public final void setITriggerTypeName(String value)
	{
		ITriggerTypeName = value;
	}
	private String ITriggerEditorTypeName;
	public final String getITriggerEditorTypeName()
	{
		return ITriggerEditorTypeName;
	}
	public final void setITriggerEditorTypeName(String value)
	{
		ITriggerEditorTypeName = value;
	}
	private java.lang.Class ITriggerType;
	public final java.lang.Class getITriggerType()
	{
		return ITriggerType;
	}
	public final void setITriggerType(java.lang.Class value)
	{
		ITriggerType = value;
	}
	private java.lang.Class ITriggerEditorType;
	public final java.lang.Class getITriggerEditorType()
	{
		return ITriggerEditorType;
	}
	public final void setITriggerEditorType(java.lang.Class value)
	{
		ITriggerEditorType = value;
	}

	@Override
	public String toString()
	{
		return getTitle();
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
}