package main.java.Rasad.Core.Services.NotificationService.Services;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoContract][DataContract] public class TLicenseContentMessage : TMessageBase
public class TLicenseContentMessage extends TMessageBase
{
	private TLicenseContentMessage()
	{
	}
	//TODO MRCOMMENT : must uncomment
	/*public TLicenseContentMessage(TLicenseContent license)
	{
		super();
		this.setLicense(license);
	}*/

	//TODO MRComment : must uncomment
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public TLicenseContent License {get;private set;}
	/*private TLicenseContent License;
	public final TLicenseContent getLicense()
	{
		return License;
	}
	private void setLicense(TLicenseContent value)
	{
		License = value;
	}

	@Override
	public String toString()
	{
		String ret;
		if (getLicense() == null)
		{
			ret = "{No License}";
		}
		else
		{
			ret = "License - Count: " + getLicense().Count.toString() + ", IsTrial: " + getLicense().IsTrial.toString() +
				", ExpirationDate: " + getLicense().ExpireDate.ToStringSafe();
		}
		return ret;
	}*/
}