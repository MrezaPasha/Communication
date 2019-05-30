package main.java.Rasad.Core;

public final class TMemoryManagement
{

	public static void CleanUp()
	{
		CleanUp(false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static void CleanUp(bool waitForCleanup = false)
	public static void CleanUp(boolean waitForCleanup)
	{
		//try
		//{
		//    if (waitForCleanup)
		//    {
		//        GC.Collect();
		//        GC.WaitForPendingFinalizers();
		//    }
		//    GC.Collect();
		//    //GC.WaitForPendingFinalizers();
		//    //GC.Collect();
		//    Process loProcess = Process.GetCurrentProcess();
		//    loProcess.MaxWorkingSet = (IntPtr)(loProcess.MaxWorkingSet);
		//    loProcess.MinWorkingSet = (IntPtr)(loProcess.MinWorkingSet);
		//}
		//catch (Exception e)
		//{
		//    Debug.WriteLine("TMemoryManagement.CleanUp()" + e);
		//}
	}
}