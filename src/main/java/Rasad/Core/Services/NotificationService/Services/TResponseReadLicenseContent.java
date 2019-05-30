package main.java.Rasad.Core.Services.NotificationService.Services;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoContract][DataContract] public class TResponseReadLicenseContent : TMessageBase
public class TResponseReadLicenseContent extends TMessageBase
{
	private TResponseReadLicenseContent()
	{
	}
	public TResponseReadLicenseContent(TLicenseContent license)
	{
		super();
		this.setLicense(license);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public TLicenseContent License {get;private set;}
	private TLicenseContent License;
	public final TLicenseContent getLicense()
	{
		return License;
	}
	private void setLicense(TLicenseContent value)
	{
		License = value;
	}
}