package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import Rasad.VideoSurveillance.Core.PluginRequiredService;

import java.util.*;

public class TPluginRequestServiceParams
{
	private UUID PluginKey;
	public final UUID getPluginKey()
	{
		return PluginKey;
	}
	public final void setPluginKey(UUID value)
	{
		PluginKey = value;
	}
	private Rasad.VideoSurveillance.Core.PluginRequiredService RequiredService = PluginRequiredService.values()[0];
	public final PluginRequiredService getRequiredService()
	{
		return RequiredService;
	}
	public final void setRequiredService(PluginRequiredService value)
	{
		RequiredService = value;
	}
}