package Rasad.CommunicationService;

public class TCommunicationService1 extends ServiceBase
{
	public TCommunicationService1()
	{
		InitializeComponent();
	}

	@Override
	protected void OnStart(String[] args)
	{
		TCommunicationServiceManager.getInstance().StartService();
	}

	@Override
	protected void OnStop()
	{
		TCommunicationServiceManager.getInstance().StopService();
	}




	/**  
	 Required designer variable.
	*/
	private System.ComponentModel.IContainer components = null;

	/** 
	 Clean up any resources being used.
	 
	 @param disposing true if managed resources should be disposed; otherwise, false.
	*/
	@Override
	protected void Dispose(boolean disposing)
	{
		if (disposing && (components != null))
		{
			components.Dispose();
		}
		super.Dispose(disposing);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Component Designer generated code

	/**  
	 Required method for Designer support - do not modify 
	 the contents of this method with the code editor.
	*/
	private void InitializeComponent()
	{
		// 
		// Service1
		// 
		this.ServiceName = "Rasad Communication Service";

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}