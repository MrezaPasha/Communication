package Rasad.CommunicationService;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [RunInstaller(true)] public partial class ProjectInstaller: System.Configuration.Install.Installer
public class ProjectInstaller extends System.Configuration.Install.Installer
{
	public ProjectInstaller()
	{
		InitializeComponent();
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
		this.serviceProcessInstaller1 = new System.ServiceProcess.ServiceProcessInstaller();
		this.serviceInstaller1 = new System.ServiceProcess.ServiceInstaller();
		// 
		// serviceProcessInstaller1
		// 
		this.serviceProcessInstaller1.Account = System.ServiceProcess.ServiceAccount.LocalSystem;
		this.serviceProcessInstaller1.Password = null;
		this.serviceProcessInstaller1.Username = null;
		// 
		// serviceInstaller1
		// 
		this.serviceInstaller1.Description = "Prepare a way to connect clients together.";
		this.serviceInstaller1.ServiceName = "Rasad Communication Service";
		this.serviceInstaller1.StartType = System.ServiceProcess.ServiceStartMode.Automatic;
		// 
		// ProjectInstaller
		// 
		this.Installers.AddRange(new System.Configuration.Install.Installer[] {this.serviceProcessInstaller1, this.serviceInstaller1});

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private System.ServiceProcess.ServiceProcessInstaller serviceProcessInstaller1;
	private System.ServiceProcess.ServiceInstaller serviceInstaller1;
}