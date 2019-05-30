package Rasad.VideoSurveillance.Core.Helpers;

import Rasad.VideoSurveillance.Core.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;

public class TComponentActivityMonitor
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor
	public TComponentActivityMonitor(UUID componentGuid, ActivityMonitorItem monitorItem)
	{
		this.setComponentGuid(componentGuid);
		this.setMonitorItem(monitorItem);

		this.activityLogFile = Path.Combine((new File(Assembly.GetExecutingAssembly().Location)).getParent(), "runtime", (new Integer(monitorItem.getValue())).toString());
		EnsureLogDirExistsSafe();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Variables
	private String activityLogFile;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	private UUID ComponentGuid;
	public final UUID getComponentGuid()
	{
		return ComponentGuid;
	}
	private void setComponentGuid(UUID value)
	{
		ComponentGuid = value;
	}
	private ActivityMonitorItem MonitorItem = ActivityMonitorItem.values()[0];
	public final ActivityMonitorItem getMonitorItem()
	{
		return MonitorItem;
	}
	private void setMonitorItem(ActivityMonitorItem value)
	{
		MonitorItem = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private void EnsureLogDirExistsSafe()
	{
		try
		{
			String path = (new File(activityLogFile)).getParent();
			if (!(new File(path)).isDirectory())
			{
				(new File(path)).mkdirs();
			}
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error in EnsureLogDirExists for " + getMonitorItem().toString(), exp);
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods
	public final void LogActivity()
	{
		try
		{
			switch (getMonitorItem())
			{
				case Alive:
					Files.writeString(activityLogFile, LocalDateTime.now().toString("yyyy-MM-ddTHH:mm:ss.fff", System.Globalization.CultureInfo.InvariantCulture));
					break;
				default:
					throw new RuntimeException("Invalid monitor item");
			}
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("LogActivity error for " + getMonitorItem().toString(), exp);
			EnsureLogDirExistsSafe();
		}
	}

	public final Object GetLastActivity()
	{

		String fileData = null;
		if ((new File(activityLogFile)).isFile())
		{
			fileData = Files.readString(activityLogFile);
		}

		switch (getMonitorItem())
		{
			case Alive:
				if (tangible.StringHelper.isNullOrWhiteSpace(fileData))
				{
					return LocalDateTime.now();
				}
				else
				{
					return LocalDateTime.ParseExact(fileData, "yyyy-MM-ddTHH:mm:ss.fff", System.Globalization.CultureInfo.InvariantCulture);
				}
			default:
				throw new RuntimeException("Invalid monitor item");
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}