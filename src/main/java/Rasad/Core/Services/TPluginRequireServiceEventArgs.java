package main.java.Rasad.Core.Services;

import Rasad.Core.Services.*;
import Rasad.Core.*;
import Rasad.VideoSurveillance.Core.PluginRequiredService;

import java.util.*;

public class TPluginRequireServiceEventArgs extends tangible.EventArgs
{
	public TPluginRequireServiceEventArgs(Rasad.VideoSurveillance.Core.PluginRequiredService requiredService, TPluginBase plugin, UUID pluginKey)
	{
		super();
		this.setRequiredService(requiredService);
		this.setPlugin(plugin);
		this.setPluginKey(pluginKey);
		this.setProvidedService(null);
	}

	private Rasad.VideoSurveillance.Core.PluginRequiredService RequiredService = Rasad.VideoSurveillance.Core.PluginRequiredService.values()[0];
	public final PluginRequiredService getRequiredService()
	{
		return RequiredService;
	}
	private void setRequiredService(PluginRequiredService value)
	{
		RequiredService = value;
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
	private TPluginBase Plugin;
	public final TPluginBase getPlugin()
	{
		return Plugin;
	}
	private void setPlugin(TPluginBase value)
	{
		Plugin = value;
	}
	private Object ProvidedService;
	public final Object getProvidedService()
	{
		return ProvidedService;
	}
	public final void setProvidedService(Object value)
	{
		ProvidedService = value;
	}
}