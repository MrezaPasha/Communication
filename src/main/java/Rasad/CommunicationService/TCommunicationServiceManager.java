package Rasad.CommunicationService;

import Rasad.Communication.Core.*;
import Rasad.Communication.Core.Server.*;
import Rasad.Core.*;
import Rasad.Core.Services.Implementation.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.Core.Services.NotificationService.Public.*;
import Rasad.DataAccess.*;
import Rasad.LogCore.*;
import Rasad.VS.Communication.Core.*;

public class TCommunicationServiceManager extends TWindowsServiceBase
{
	static
	{
		TRVSRuntimeRepository.InsideService = true;
		ModifyRequiredServices(false, true);

		setInstance(new TCommunicationServiceManager());
	}
	private static void ModifyRequiredServices(boolean stop, boolean start)
	{
		if (stop)
		{
			TConnectionStringService.Stop();
		}
		if (start)
		{
			TConnectionStringService.Start(true);

			RasadSmartLog.InitializeAndAttach(true);
			TLogManager.ServerID = TServerInformation.ServerID;
		}
	}
	private static TCommunicationServiceManager Instance;
	public static TCommunicationServiceManager getInstance()
	{
		return Instance;
	}
	private static void setInstance(TCommunicationServiceManager value)
	{
		Instance = value;
	}
	private static TServerTerminalSafe<TMessageBase, TNotifyRemotePartyIdentity> ServerTerminal;
	public static TServerTerminalSafe<TMessageBase, TNotifyRemotePartyIdentity> getServerTerminal()
	{
		return ServerTerminal;
	}
	private static void setServerTerminal(TServerTerminalSafe<TMessageBase, TNotifyRemotePartyIdentity> value)
	{
		ServerTerminal = value;
	}

	@Override
	public Object getSystemIdentity()
	{
		return SystemEntity.CommunicationService;
	}

	@Override
	protected void StartServiceInternal()
	{
		TVSNotificationInitializer.Initialize();
		setServerTerminal(new TServerTerminalSafe<TMessageBase, TNotifyRemotePartyIdentity>());
		//ServerTerminal.Start(RVSPortConstants.CommunicationPortNumber);
		getServerTerminal().Start();
	}

	@Override
	protected void StopServiceInternal()
	{
		getServerTerminal().Stop();
		ModifyRequiredServices(true, false);
	}
}