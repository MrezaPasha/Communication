package Rasad.Core.Services.CameraService;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;

public class TCameraEditorTab
{

	public TCameraEditorTab(java.util.UUID pluginKey, java.util.UUID tabKey, String tabTitle, java.lang.Class editorType)
	{
		this(pluginKey, tabKey, tabTitle, editorType, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TCameraEditorTab(Guid pluginKey, Guid tabKey, string tabTitle, Type editorType, Func<ICameraInformation, bool> canShowCallback = null)
	public TCameraEditorTab(UUID pluginKey, UUID tabKey, String tabTitle, java.lang.Class editorType, tangible.Func1Param<ICameraInformation, Boolean> canShowCallback)
	{
		this._CanShowCallback = (ICameraInformation arg) -> canShowCallback.invoke(arg);
		this.setPluginKey(pluginKey);
		this.setTabKey(tabKey);
		this.setTabTitle(tabTitle);
		this.setEditorType(editorType);
	}

	private tangible.Func1Param<ICameraInformation, Boolean> _CanShowCallback;
	private UUID PluginKey;
	public final UUID getPluginKey()
	{
		return PluginKey;
	}
	public final void setPluginKey(UUID value)
	{
		PluginKey = value;
	}
	private UUID TabKey;
	public final UUID getTabKey()
	{
		return TabKey;
	}
	public final void setTabKey(UUID value)
	{
		TabKey = value;
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

	public final boolean CanShowTab(ICameraInformation camera)
	{
		if (_CanShowCallback == null)
		{
			return true;
		}
		return _CanShowCallback.invoke(camera);
	}
	@Override
	public String toString()
	{
		return getTabTitle();
	}
}