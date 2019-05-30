package main.java.Rasad.Core.Services.PluginService;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;

public class TSystemSettingsTab
{
	public TSystemSettingsTab(UUID key, String tabTitle, java.lang.Class editorType, String imageUrl, String description)
	{
		this.setKey(key);
		this.setTabTitle(tabTitle);
		this.setEditorType(editorType);
		this.setImageUrl(imageUrl);
		this.setDescription(description);
	}
	private UUID Key;
	public final UUID getKey()
	{
		return Key;
	}
	public final void setKey(UUID value)
	{
		Key = value;
	}
	private String TabTitle;
	public final String getTabTitle()
	{
		return TabTitle;
	}
	public final void setTabTitle(String value)
	{
		TabTitle = value;
	}
	private java.lang.Class EditorType;
	public final java.lang.Class getEditorType()
	{
		return EditorType;
	}
	public final void setEditorType(java.lang.Class value)
	{
		EditorType = value;
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
	private String Description;
	public final String getDescription()
	{
		return Description;
	}
	public final void setDescription(String value)
	{
		Description = value;
	}


}