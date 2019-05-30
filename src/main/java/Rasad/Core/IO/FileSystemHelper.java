package Rasad.Core.IO;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class FileSystemHelper
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor

	private FileSystemHelper()
	{
	}
	static
	{
		setInstance(new FileSystemHelper());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	private static FileSystemHelper Instance;
	public static FileSystemHelper getInstance()
	{
		return Instance;
	}
	private static void setInstance(FileSystemHelper value)
	{
		Instance = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Method

	public final DriveInfo[] GetLogicalDrives()
	{

		return DriveInfo.GetDrives();
	}
	public final String[] GetDirectories(String path)
	{
		return (new File(path)).list(File::isDirectory);
	}
	public final String[] GetFiles(String path)
	{
		return (new File(path)).list(File::isFile);
	}
	public final ArrayList<String> GetFilesAndFolders(String path)
	{
		ArrayList<String> childfolders = GetDirectories(path).ToList();
		childfolders.addAll(Arrays.asList(GetFiles(path)));
		return childfolders;

	}
	public final String GetDirectoryName(String path)
	{
		return (new File(path)).getName();
	}
	public final String GetFileName(String path)
	{
		return (new File(path)).getName();
	}
	public final String GetFileSize(String path)
	{
		long size = (new File(path)).length();
		return GetFileSize(size);
	}
	public final boolean IsDirectory(String path)
	{
		return (new File(path)).isDirectory();
	}
	public final boolean IsFile(String path)
	{
		return (new File(path)).isFile();
	}
	private String GetFileSize(long size)
	{
		if (size >= 1024)
		{
//C# TO JAVA CONVERTER TODO TASK: The '0:### ### ###' format specifier is not converted to Java:
			return String.format("{0:### ### ###} KB", size / 1024);
		}
		return String.format("%1$s Bytes", size);
	}
	public static boolean IsValidDirectoryPath(String path)
	{
		char[] invalidChars = System.IO.Path.GetInvalidPathChars().Union(new char[]{'?', '*'}).ToArray();
		return !System.TStringHelper.IsNullOrEmpty(path) && tangible.StringHelper.indexOfAny(path, invalidChars) == -1;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}