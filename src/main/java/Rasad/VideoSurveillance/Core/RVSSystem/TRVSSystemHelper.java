package Rasad.VideoSurveillance.Core.RVSSystem;

import Rasad.VideoSurveillance.Core.Helpers.*;
import Rasad.VideoSurveillance.Core.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public final class TRVSSystemHelper
{
	public static UUID EnsureEntityKeyInRegistry(String keyname, UUID defaultValue)
	{
		// always use 32-bit registry
		try
		{
			try (RegistryKey appNameKey = TRVSRegistry.GetRVSMachineLevelKey())
			{
				try (RegistryKey entitiesKey = appNameKey.CreateSubKey("Entities"))
				{
					String guidContentInReg;
					Object result = entitiesKey.GetValue(keyname, null);
					if (result == null)
					{
						guidContentInReg = null;
					}
					else
					{
						guidContentInReg = String.valueOf(result);
					}
					boolean storeNewKey = false;

					UUID retVal;
					if (tangible.StringHelper.isNullOrWhiteSpace(guidContentInReg))
					{
						storeNewKey = true;
						TLogManager.Info("EntityKey not available in registry");
						guidContentInReg = defaultValue.toString();
						retVal = defaultValue;
					}
					else
					{
						tangible.OutObject<UUID> tempOut_retVal = new tangible.OutObject<UUID>();
						if (!UUID.TryParse(guidContentInReg, tempOut_retVal))
						{
						retVal = tempOut_retVal.argValue;
							storeNewKey = true;
							guidContentInReg = defaultValue.toString();
						}
					else
					{
						retVal = tempOut_retVal.argValue;
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
						TLogManager.Info("Store new entity key in registry", AnonymousType(guidContentInReg));
						entitiesKey.SetValue(keyname, guidContentInReg, RegistryValueKind.String);
					}
					TLogManager.Info("Using entity key " + retVal.toString());
					return retVal;
				}
			}
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error("Error EnsureEntityKeyInRegistry", exp);
			throw exp;
		}
	}

	private static String _MapsStorageFolder = null;
	public static String getMapsStorageFolder()
	{
		if (tangible.StringHelper.isNullOrEmpty(_MapsStorageFolder))
		{
			_MapsStorageFolder = Paths.get((new File(System.Reflection.Assembly.GetExecutingAssembly().Location)).getParent()).resolve("Maps").toString();
		}
		return _MapsStorageFolder;
	}
}