package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import java.util.*;
import java.time.*;

public class TPluginSetting
{
	private UUID RowKey;
	public final UUID getRowKey()
	{
		return RowKey;
	}
	public final void setRowKey(UUID value)
	{
		RowKey = value;
	}
	private UUID PluginKey;
	public final UUID getPluginKey()
	{
		return PluginKey;
	}
	public final void setPluginKey(UUID value)
	{
		PluginKey = value;
	}
	private String SettingValue;
	public final String getSettingValue()
	{
		return SettingValue;
	}
	public final void setSettingValue(String value)
	{
		SettingValue = value;
	}
	private UUID PluginTabKey;
	public final UUID getPluginTabKey()
	{
		return PluginTabKey;
	}
	public final void setPluginTabKey(UUID value)
	{
		PluginTabKey = value;
	}

}