package main.java.Rasad.Core.Services.NotificationService.Services;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract][ProtoContract] public class TResponseLicenseInstalled : TMessageBase
public class TResponseLicenseInstalled extends TMessageBase
{
	private TResponseLicenseInstalled()
	{

	}
	public TResponseLicenseInstalled(UUID licenseRowKey)
	{
		setLicenseRowKey(licenseRowKey);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public Guid LicenseRowKey {get;private set;}
	private UUID LicenseRowKey;
	public final UUID getLicenseRowKey()
	{
		return LicenseRowKey;
	}
	private void setLicenseRowKey(UUID value)
	{
		LicenseRowKey = value;
	}

}