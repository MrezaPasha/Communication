package Rasad.VideoSurveillance.Core.Helpers;

import Rasad.Core.*;
import Rasad.VideoSurveillance.Core.*;
import java.util.*;

public final class TRVSVersionHelper
{
	private static class TRVSVersion
	{
		public TRVSVersion(String versionString)
		{
			this.setVersionString(versionString);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var parts = versionString.split(new char[] {'.'}, StringSplitOptions.None);
			if (parts.Length != 3)
			{
				throw new RuntimeException("Invalid version " + versionString);
			}
			this.setMajorVersion((int)parts[0]);
			this.setMinorVersion((int)parts[1]);
			this.setReleaseNumber((int)parts[2]);
		}

		private int MajorVersion;
		public final int getMajorVersion()
		{
			return MajorVersion;
		}
		private void setMajorVersion(int value)
		{
			MajorVersion = value;
		}
		private int MinorVersion;
		public final int getMinorVersion()
		{
			return MinorVersion;
		}
		private void setMinorVersion(int value)
		{
			MinorVersion = value;
		}
		private int ReleaseNumber;
		public final int getReleaseNumber()
		{
			return ReleaseNumber;
		}
		private void setReleaseNumber(int value)
		{
			ReleaseNumber = value;
		}

		private String VersionString;
		public final String getVersionString()
		{
			return VersionString;
		}
		private void setVersionString(String value)
		{
			VersionString = value;
		}

		public final double getEquivalentWholeNumber()
		{
			return (double)getMajorVersion() * 1000000000.0 + (double)getMinorVersion() * 1000000.0 + (double)getReleaseNumber() * 1000.0;
		}
	}

	public static String GetLatestVersion(ArrayList<String> installedVersions)
	{
		ArrayList<TRVSVersion> versions = new ArrayList<TRVSVersion>();
		for (String item : installedVersions)
		{
			try
			{
				versions.add(new TRVSVersion(item));
			}
			catch (RuntimeException exp)
			{
				TLogManager.Error(exp);
			}
		}
		if (!versions.Any())
		{
			return "";
		}
		else
		{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return versions.OrderByDescending(s -> s.EquivalentWholeNumber).First().VersionString;
		}
	}

	public static boolean IsVersionValid(ArrayList<String> installedVersions)
	{
		TRVSVersion sourceVersion = new TRVSVersion(RVSConstants.SystemVersion);
		ArrayList<TRVSVersion> versions = new ArrayList<TRVSVersion>();
		for (String item : installedVersions)
		{
			try
			{
				versions.add(new TRVSVersion(item));
			}
			catch (RuntimeException exp)
			{
				TLogManager.Error(exp);
			}
		}
		if (!versions.Any())
		{
			return false;
		}
		if (versions.Any(v -> v.EquivalentWholeNumber > sourceVersion.getEquivalentWholeNumber()))
		{
			return false;
		}
		return true;
	}
}