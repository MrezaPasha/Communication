package main.java.Rasad.Communication.Core;


import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.RegistryException;
import com.github.sarxos.winreg.WindowsRegistry;
import main.java.Rasad.Core.Services.NotificationService.Public.SystemEntity;
import main.java.Rasad.Core.Services.NotificationService.Public.TNotifyRemotePartyIdentity;
import main.java.Rasad.Core.Services.NotificationService.Public.TRequestServerRuntimeInfomation;
import main.java.Rasad.Core.Services.NotificationService.Public.TResponseServerRuntimeInfomation;

import java.util.*;
import static main.java.Rasad.Core.Services.NotificationService.Public.SystemEntity.*;

public final class TNotificationServiceHelper
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static String[] GetServerIPAddress(byte serverID)
	public static String[] GetServerIPAddress(byte serverID)
	{
		try
		{
			TRequestServerRuntimeInfomation req = new TRequestServerRuntimeInfomation(serverID);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var res = new TNotificationService().NotifyAndWaitForResponse(req,2);
			if (res == null)
			{
				return null;
			}
			else
			{
				TResponseServerRuntimeInfomation resp = res instanceof TResponseServerRuntimeInfomation ? (TResponseServerRuntimeInfomation) res : null;
				return resp.getIPAddressList();
			}
		}
		catch (RuntimeException exp)
		{
			//TODO MRCOMMENT : logger
			//TLogManager.Error("Error getting server ip address list", exp);
			return null;
		}
	}

	private static UUID EnsureEntityKeyInRegistry(String keyname, UUID defaultValue) throws RegistryException {
		// always use 32-bit registry
		UUID retVal = null;

		try
		{
			//try (RegistryKey appNameKey = Rasad.VideoSurveillance.Core.Helpers.TRVSRegistry.GetRVSMachineLevelKey())
			WindowsRegistry registryKey = WindowsRegistry.getInstance();
			try
			{
				try
				{
					registryKey.createKey(HKey.HKCC,"Entities");
					String guidContentInReg;
					//Object result = entitiesKey.GetValue(keyname, null);
					Object result = registryKey.readStringSubKeys(HKey.HKCC,"Entities");
					if (result == null)
					{
						guidContentInReg = null;
					}
					else
					{
						guidContentInReg = String.valueOf(result);
					}
					boolean storeNewKey = false;


					if (tangible.StringHelper.isNullOrWhiteSpace(guidContentInReg))
					{
						storeNewKey = true;
						//TODO MRCOMMENT : logger
						//TLogManager.Info("EntityKey not available in registry");
						guidContentInReg = defaultValue.toString();
						retVal = defaultValue;
					}
					else
					{
						UUID tempOut_retVal= UUID.fromString(guidContentInReg);
						//if (!UUID.TryParse(guidContentInReg, tempOut_retVal))
						if (tempOut_retVal != null)
						{
						retVal = tempOut_retVal;
							storeNewKey = true;
							guidContentInReg = defaultValue.toString();
						}
					else
					{
						retVal = tempOut_retVal;
					}
					}
					if (storeNewKey)
					{
						class AnonymousType
						{
							public String Key;

							public AnonymousType(String _Key)
							{
								Key = _Key;
							}
						}
						//TODO MRCOMMENT : logger
						//TLogManager.Info("Store new entity key in registry", AnonymousType(guidContentInReg));
						//TODO MRREPLACE : must edited
						//entitiesKey.SetValue(keyname, guidContentInReg, RegistryValueKind.String);
						String test = "pasha";
						registryKey.writeStringValue(HKey.HKCC,keyname,guidContentInReg,test);
					}
					//TODO MRCOMMENT : logger
					//TLogManager.Info("Using entity key " + retVal.toString());
					return retVal;
				}
				catch (Exception e)
				{

				}
			}
			catch (Exception e)
			{

			}
		}
		catch (RuntimeException exp)
		{
			//TODO MRCOMMENT : logger
			//TLogManager.Error("Error EnsureEntityKeyInRegistry", exp);
			throw exp;
		}
		return retVal;
	}

	public static TNotifyRemotePartyIdentity PrepareEntityIdentity(SystemEntity systemEntity, boolean requestUnsentMessages) throws RegistryException {
		UUID entityKey;
		CommunicationEntityLifetime lifetime;
		switch (systemEntity)
		{
            case CommunicationService:
			case MainService:
			case RecordingService:
			case PlaybackService:
			case PresetTourSevice:
			case MotionDetectionService:
			case StreamingService:
			case CameraMonitoringService:
			case EventTriggerService:
			case LicenseService:
			case Plugin:
			case IOService:
			{
					entityKey = EnsureEntityKeyInRegistry(systemEntity.toString(), UUID.randomUUID());
					lifetime = CommunicationEntityLifetime.Permanent;
			}
				break;
			case LogService:
			case Client:
			case Debugger:
			case Bootstrap:
			case RecorderHost:
			case Other:
			case StreamerHost:
			case MotionDetectorHost:
			default:
			{
					entityKey = UUID.randomUUID();
					lifetime = CommunicationEntityLifetime.Temporary;
			}
				break;
		}

		//TODO MRREPLACE : must change
		//TNotifyRemotePartyIdentity notifyRemotePartyIdentity = new TNotifyRemotePartyIdentity(systemEntity,entityKey,lifetime,requestUnsentMessages,serverID)
		return new TNotifyRemotePartyIdentity();
		////
	}
}