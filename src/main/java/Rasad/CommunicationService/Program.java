package Rasad.CommunicationService;

public final class Program
{
	/** 
	 The main entry point for the application.
	*/
	static void main()
	{
		ServiceBase[] ServicesToRun;
		ServicesToRun = new ServiceBase[] {new TCommunicationService1()};
		ServiceBase.Run(ServicesToRun);
	}
}