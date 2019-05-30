package Rasad.Core.DateTimeUtilities;

import Rasad.Core.*;
import java.time.*;

public final class MidnightNotifier
{
	private static Timer timer;
	private static LocalDateTime oldDateTime = LocalDateTime.MIN;

	static
	{
		oldDateTime = LocalDateTime.now();
		timer = new Timer(GetSleepTime());
		timer.Elapsed += (s, e) ->
		{
				try
				{
					OnDayChanged();
				}
				finally
				{
					oldDateTime = LocalDateTime.now();
					timer.Interval = GetSleepTime();
				}
		};
		timer.Start();

//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		SystemEvents.TimeChanged += OnSystemTimeChanged;
	}

	private static double GetSleepTime()
	{
		LocalDateTime midnightTonight = LocalDateTime.Today.plusDays(1);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var differenceInMilliseconds = (midnightTonight - LocalDateTime.now()).TotalMilliseconds;
		return differenceInMilliseconds;
	}

	private static void OnDayChanged()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var handler = DayChanged;
		if (handler != null)
		{
			try
			{
				handler(null, null);
			}
			catch (RuntimeException exp)
			{
				TLogManager.Error("Error in DayChanged event in MidnightNotifier", exp);
			}
		}
	}

	private static void OnSystemTimeChanged(Object sender, tangible.EventArgs e)
	{
		timer.Interval = GetSleepTime();
		LocalDateTime newDateTime = LocalDateTime.now();
		boolean dayChanged = newDateTime.getYear() != oldDateTime.getYear() || newDateTime.getMonthValue() != oldDateTime.getMonthValue() || newDateTime.getDayOfMonth() != oldDateTime.getDayOfMonth();
		oldDateTime = newDateTime;
		if (dayChanged)
		{
			OnDayChanged();
		}
	}

	public static tangible.Event<tangible.EventHandler<tangible.EventArgs>> DayChanged = new tangible.Event<tangible.EventHandler<tangible.EventArgs>>();
}