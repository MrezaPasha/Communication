package Rasad.Core.Diagnostics.LogSystem;

import Rasad.Core.*;
import Rasad.Core.Diagnostics.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;

public class HDDLogger extends LocalLoggerBase
{
	public HDDLogger()
	{
		super();
		UpdateDayVarsAndFolder();
		StartTimer();

		timerLogFileCleaner = new System.Timers.Timer();
		timerLogFileCleaner.Enabled = false;
		timerLogFileCleaner.AutoReset = false;
		timerLogFileCleaner.Interval = TimeSpan.FromHours(2).TotalMilliseconds;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		timerLogFileCleaner.Elapsed += timerLogFileCleaner_Elapsed;
		timerLogFileCleaner.Start();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields
	private PersianCalendar _PersianCalendar = new PersianCalendar();
	private TTimer _Timer;
	private File _LogFolder = null;
	private volatile int old_logFolderYear = 0, old_logFolderMonth = 0, old_logFolderDay = 0;
	private volatile String old_Prefix = null;
	private volatile int logFolderYear, logFolderMonth, logFolderDay;

	private LocalDateTime logStartDateTime = null;
	private int logStartYear, logStartMonth, logStartDay;
	//private  object compressLock = new object();
	private Object logLockObj = new Object();
	private String logFileNameSuffix = null;
	private String currentDate;

	private System.Timers.Timer timerLogFileCleaner;


	private String getLogFolderBase()
	{
		String AssemblyFolder = (new File(Assembly.GetExecutingAssembly().Location)).getParent();
		return Paths.get(AssemblyFolder).resolve("Log").toString();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private
	@Override
	protected void LogAnyway(RasadLogLevel logLevel, String msg, String data)
	{
		LogToHDD(logLevel, msg, data);
	}

	private void UpdateDayVarsAndFolder()
	{
		LocalDateTime now = LocalDateTime.now();

		if (logStartDateTime == null)
		{
			logStartDateTime = now;
			logStartYear = _PersianCalendar.GetYear(now);
			logStartMonth = _PersianCalendar.GetMonth(now);
			logStartDay = _PersianCalendar.GetDayOfMonth(now);
		}


		logFolderYear = _PersianCalendar.GetYear(now);
		logFolderMonth = _PersianCalendar.GetMonth(now);
		logFolderDay = _PersianCalendar.GetDayOfMonth(now);
		if (old_logFolderYear != logFolderYear || old_logFolderMonth != logFolderMonth || old_logFolderDay != logFolderDay || !getPrefix().equals(old_Prefix) || _LogFolder == null)
		{
			old_logFolderYear = logFolderYear;
			old_logFolderMonth = logFolderMonth;
			old_logFolderDay = logFolderDay;
			old_Prefix = getPrefix();

			currentDate = String.format("%04d/%02d/%02d", logFolderYear, logFolderMonth, logFolderDay);

			_LogFolder = new File(Paths.get(getLogFolderBase()).resolve(currentDate.replace("/", "_")).toString());
			logFileNameSuffix = String.format("%04d_%02d_%02d_%02d_%02d_%02d_%03d-[%8$s]-%9$s", logStartYear, logStartMonth, logStartDay, logStartDateTime.getValue().getHour(), logStartDateTime.getValue().getMinute(), logStartDateTime.getValue().getSecond(), logStartDateTime.getValue().Millisecond, getPrefix().ToStringSafe(""), Process.GetCurrentProcess().Id);
			DeleteOlderLogsAsync();
		}
	}

	private void StartTimer()
	{
		_Timer = new TTimer(GetDiffToEndOfCurrentDay());
		_Timer.setElapsed(() -> OnDayChanging());
		_Timer.Start();
	}

	private void OnDayChanging()
	{
		CleanupArchiveLogFiles();
		TimeSpan diffToEndOfCurrentDay = GetDiffToEndOfCurrentDay();
		if (diffToEndOfCurrentDay.TotalMilliseconds > 0)
		{
			_Timer.setInterval(diffToEndOfCurrentDay.TotalMilliseconds);
		}
		else
		{
			_Timer.setInterval(10); // to retry again
		}
	}
	private TimeSpan GetDiffToEndOfCurrentDay()
	{
		return LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), 23, 59, 59) - LocalDateTime.now();
	}
	/** 
	 Archives previous month log files.
	*/
	public final void CleanupArchiveLogFiles()
	{
		//Task.Factory.StartNew(new Action(() =>
		//{
		//    lock (compressLock)
		//    {
		//        try
		//        {
		//            var LogTextFiles = _LogFolder.GetFiles("*.txt")
		//                .OrderByDescending(f => f.LastWriteTime)
		//                .Where(f => f.LastWriteTime.Month != DateTime.Now.Month || (f.LastWriteTime.Month == DateTime.Now.Month && f.LastWriteTime.Year != DateTime.Now.Year));
		//            if (!LogTextFiles.Any()) return;

		//            DateTime prevMonth = LogTextFiles.First().LastWriteTime;

		//            DirectoryInfo dirComp = new DirectoryInfo(string.Format("{0}\\{1}_{2:00}", _LogFolder, prevMonth.Year, prevMonth.Month));
		//            if (!dirComp.Exists) dirComp.Create();
		//            try
		//            {
		//                foreach (var file in LogTextFiles)
		//                {
		//                    File.Move(file.FullName, Path.Combine(dirComp.FullName, file.Name));
		//                }
		//                TCompressor.CompressFolder(dirComp.FullName, dirComp.FullName + ".Zip", "rasad");
		//            }
		//            finally
		//            {
		//                dirComp.Delete();
		//            }
		//        }
		//        catch (Exception ex)
		//        {
		//            System.Console.WriteLine("Error in CleanupArchiveLogFiles : " + ex.Message);
		//        }
		//    }
		//}));
	}

	//private  string GetSenderText(this object sender)
	//{
	//    if (sender == null) return "?";
	//    if (sender is Type)
	//    {
	//        return ((Type)sender).Assembly.GetViewName();
	//    }
	//    else if (sender is string)
	//    {
	//        return sender.ToString();
	//    }
	//    else
	//    {
	//        return sender.GetType().Assembly.GetViewName();
	//    }
	//}
	//private  string GetViewName(this Assembly asm)
	//{
	//    return asm.GetName().Name.Replace("Rasad.", "").Replace(".", " ").Replace(".exe", "");
	//}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] private void LogToHDD(RasadLogLevel logLevel, string message, String data)
	private void LogToHDD(RasadLogLevel logLevel, String message, String data)
	{
///#if(DEBUG)
//            int index = message.IndexOf('\n');
//            if (index >= 0)
//            {
//                Console.WriteLine("* " + message.Substring(0, index));
//            }
//            else
//            {
//                Console.WriteLine("* " + message);
//            }
///#endif

		File logFolder;
		String CurrentDateStr;
		synchronized (logLockObj)
		{
			UpdateDayVarsAndFolder();
			logFolder = _LogFolder;
			CurrentDateStr = currentDate;
		}


		{
		//using (new TInterProcessMutexLock("{EAD039E1-322B-488F-BD42-69D104B65B1E}"))
			try
			{
				logFolder.Refresh();
				if (!logFolder.exists())
				{
					logFolder.Create();
				}

				LocalDateTime now = LocalDateTime.now();
				message = String.format("%1$s %02d:%02d:%02d.%03d\t%6$s\t%7$s :\t%8$s\t%9$s", CurrentDateStr, now.getHour(), now.getMinute(), now.getSecond(), now.Millisecond, logLevel.toString(), getPrefix(), message, data);
				//System.Diagnostics.Debug.WriteLine(message);  --> makes mplayer.exe output dirty!! (in RVS Player)

				String filePath = String.format("%1$s\\%2$s.txt", logFolder.getPath(), logFileNameSuffix);
				try (OutputStreamWriter writer = new OutputStreamWriter(filePath, java.nio.charset.StandardCharsets.UTF_16LE))
				{
					writer.write(message + System.lineSeparator());
				}
			}
			catch (RuntimeException ex)
			{
				System.Diagnostics.Debug.WriteLine("خطا در زمان ثبت رویداد در فایل !!!");
			}
		}
	}

	private LocalDateTime GetLogDirDateTime(String dirname)
	{
		try
		{
			String dirNamePart = Path.GetFileNameWithoutExtension(dirname);
			String[] parts = dirNamePart.split(new char[] {'_'}, StringSplitOptions.None);
			if (parts.length != 3)
			{
				return null;
			}
			else
			{
				int year = Integer.parseInt(parts[0]);
				int month = Integer.parseInt(parts[1]);
				int day = Integer.parseInt(parts[2]);
				return _PersianCalendar.ToDateTime(year, month, day);
			}
		}
		catch (java.lang.Exception e)
		{
			return null;
		}
	}

	private void DeleteOldLogs()
	{
		try
		{
			LocalDateTime now = LocalDateTime.now();
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			String[] logDirectories = Directory.GetDirectories(getLogFolderBase(), "*", SearchOption.TopDirectoryOnly).OrderBy(s -> s).ToArray();
			DriveInfo logDriveInfo = new DriveInfo((new File(getLogFolderBase())).Root.getPath());
			long minDiskFreeSpaceLogFiles = TLogManager.getMinDiskFreeSpaceLogFilesMB() * 1024 * 1024;
			boolean checkForDriveSizeDelete = logDriveInfo.AvailableFreeSpace <= minDiskFreeSpaceLogFiles;
			for (String dirname : logDirectories)
			{
				LocalDateTime logDirDateTime = null;
				try
				{
					logDirDateTime = GetLogDirDateTime(dirname);
				}
				catch (RuntimeException exp)
				{
					//OnErrorOccurred("Log filename format is invalid and cannot be parsed to check if delete (in deleting old log files). Filename = '" + dirname + "': " + exp.Message);
					continue;
				}
				if (logDirDateTime == null)
				{
					continue; // skip this folder
				}

				boolean delete;
				delete = (logDirDateTime.compareTo(now) > 0) || ((int)((now - logDirDateTime.getValue()).TotalDays) > TLogManager.getMaxDaysKeepLogFiles());
				if (!delete)
				{
					if (checkForDriveSizeDelete)
					{
						// logDriveInfo.AvailableFreeSpace is always up to date
						if (logDriveInfo.AvailableFreeSpace < minDiskFreeSpaceLogFiles)
						{
							delete = true;
						}
					}
				}
				if (delete)
				{
					try
					{
						TLogManager.Info("Delete log folder '" + dirname + "'.");
						Directory.Delete(dirname, true);
					}
					catch (java.lang.Exception e)
					{
					}
				}
				Thread.sleep(5);
			}
		}
		catch (RuntimeException exp)
		{
			//OnErrorOccurred(new Exception("Error deleting old log file: " + exp.Message, exp));
		}
	}

	private void DeleteOlderLogsAsync()
	{
		// delete logs older than 1 month
//C# TO JAVA CONVERTER TODO TASK: Local functions are not converted by C# to Java Converter:
//		new System.Threading.Thread()
//			{
//			void run()
//			{
//					DeleteOldLogs();
//			}
//			}{ IsBackground = true, Priority = ThreadPriority.Lowest }.Start();
	}

	private void timerLogFileCleaner_Elapsed(Object sender, System.Timers.ElapsedEventArgs e)
	{
		try
		{
			DeleteOldLogs();
		}
		finally
		{
			timerLogFileCleaner.Start();
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}