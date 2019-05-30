package main.java.Rasad.Core;

import Rasad.Core.Services.*;
import java.io.*;
import java.nio.file.*;

public final class TWindowsServiceHelper
{
	public static Task<Boolean> InstallServiceAsync(String fileName)
	{
		return Task.Run(() ->
		{
				if ((new File(fileName)).isFile())
				{
					try
					{
						//Note : Exe Should compile in x86 mode to work !!!
						ManagedInstallerClass.InstallHelper(new String[] {"/i", fileName});
						return true;
					}
					catch (RuntimeException exc)
					{
						TLogManager.Error(exc);
						return false;
					}
				}
				return false;
		});
	}
	public static Task<Boolean> UninstallServiceAsync(String fileName)
	{
		return Task.Run(() ->
		{
				if ((new File(fileName)).isFile())
				{
					try
					{
						//Note : Exe Should compile in x86 mode to work !!!
						ManagedInstallerClass.InstallHelper(new String[] {"/u", fileName});
					}
					catch (RuntimeException exc)
					{
						TLogManager.Error("Error UninstallServiceAsync", exc);
					}
					// don't return immediately, first check if process exists
					// if exists, kill it, and run uninstall again
					boolean needsUninstallAgain = false;
					try
					{
						var exisitingProcesses = Process.GetProcessesByName(System.IO.Path.GetFileNameWithoutExtension((new File(fileName)).getName()));
						if (exisitingProcesses != null && exisitingProcesses.Length > 0)
						{
							TLogManager.Warn("Found service process still exists after uninstall (" + (new File(fileName)).getName() + "), trying to kill it and then uninstall again");
							needsUninstallAgain = true;
							exisitingProcesses.ForEach(p -> p.Kill());
						}
					}
					catch (RuntimeException exp)
					{
						TLogManager.Error("Error checking service process after uninstall for " + (new File(fileName)).getName(), exp);
					}
					if (needsUninstallAgain)
					{
						try
						{
							//Note : Exe Should compile in x86 mode to work !!!
							ManagedInstallerClass.InstallHelper(new String[] {"/u", fileName});
						}
						catch (RuntimeException exc)
						{
							TLogManager.Error("Error UninstallServiceAsync uninstalling again", exc);
						}
					}
					return true;
				}
				else
				{
					return false;
				}
		});
	}

	public static System.ServiceProcess.ServiceController FindService(String serviceName)
	{
		return System.ServiceProcess.ServiceController.GetServices().FirstOrDefault(t -> t.ServiceName.EqualsIgnoreCase(serviceName));
	}
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent in Java to the 'async' keyword:
//ORIGINAL LINE: public static async Task StartService(string serviceName)
	public static Task StartService(String serviceName)
	{
		System.ServiceProcess.ServiceController sc = FindService(serviceName);
		if (sc != null && sc.Status != System.ServiceProcess.ServiceControllerStatus.Running)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to 'await' in Java:
			await Task.Run(() ->
			{
					sc.Start();
					sc.WaitForStatus(System.ServiceProcess.ServiceControllerStatus.Running);
			});
		}
	}



	public static Task ConfigureService(String serviceName, int FailurTime1, int FailurTime2)
	{
		return ConfigureService(serviceName, FailurTime1, FailurTime2, 60000);
	}

	public static Task ConfigureService(String serviceName, int FailurTime1)
	{
		return ConfigureService(serviceName, FailurTime1, 60000, 60000);
	}

	public static Task ConfigureService(String serviceName)
	{
		return ConfigureService(serviceName, 60000, 60000, 60000);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static Task ConfigureService(string serviceName, int FailurTime1 = 60000, int FailurTime2 = 60000, int FailurTime3 = 60000)
	public static Task ConfigureService(String serviceName, int FailurTime1, int FailurTime2, int FailurTime3)
	{
		return Task.Run(() ->
		{
				try
				{
					String filePath = Paths.get(Environment.SystemDirectory).resolve("sc.exe").toString();
					String command = String.format("failure \"%1$s\" reset= 86400 actions= restart/%2$s/restart/%3$s/restart/%4$s", serviceName, FailurTime1, FailurTime2, FailurTime3);
					ProcessStartInfo PSI = new ProcessStartInfo(filePath, command);
					PSI.CreateNoWindow = true;
					PSI.WindowStyle = ProcessWindowStyle.Hidden;
					Process.Start(PSI);
				}
				catch (RuntimeException exc)
				{
					TLogManager.Error(exc);
				}
		});

	}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent in Java to the 'async' keyword:
//ORIGINAL LINE: public static async Task EnsureInstallAndStart(string serviceName, string filePath)
	public static Task EnsureInstallAndStart(String serviceName, String filePath)
	{
		System.ServiceProcess.ServiceController finded = TWindowsServiceHelper.FindService(serviceName);
		if (finded == null)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to 'await' in Java:
			boolean result = await InstallServiceAsync(filePath);
			if (result)
			{
				TLogManager.Info(String.Concat("TWindowsServiceHelper", "Install Service ", serviceName, "..."));
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to 'await' in Java:
				await ConfigureService(serviceName);
				TLogManager.Info(String.Concat("TWindowsServiceHelper", "Starting Service ", serviceName, "..."));
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to 'await' in Java:
				await StartService(serviceName);
				TLogManager.Info(String.Concat("TWindowsServiceHelper", serviceName, " Started."));
			}
		}
		else if (finded.Status != System.ServiceProcess.ServiceControllerStatus.Running)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to 'await' in Java:
			await Task.Run(() ->
			{
					if (finded.Status != System.ServiceProcess.ServiceControllerStatus.StartPending)
					{
						TLogManager.Info("TWindowsServiceHelper", "Starting Service ", serviceName, "...");
						finded.Start();
					}
					finded.WaitForStatus(System.ServiceProcess.ServiceControllerStatus.Running);
					TLogManager.Info("TWindowsServiceHelper", serviceName, " Started.");
			});
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent in Java to the 'async' keyword:
//ORIGINAL LINE: public static async Task EnsureUnInstallService(string serviceName, string filePath)
	public static Task EnsureUnInstallService(String serviceName, String filePath)
	{
		System.ServiceProcess.ServiceController finded = TWindowsServiceHelper.FindService(serviceName);
		if (finded != null)
		{
			TLogManager.Info("TWindowsServiceHelper", "Uninstall Service ", serviceName, "...");
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to 'await' in Java:
			await UninstallServiceAsync(filePath);
		}
	}
}