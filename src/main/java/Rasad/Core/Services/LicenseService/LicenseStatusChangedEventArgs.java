package main.java.Rasad.Core.Services.LicenseService;

import Rasad.Lic.Core.*;
import Rasad.Core.*;
import Rasad.Core.Services.*;

public class LicenseStatusChangedEventArgs extends tangible.EventArgs
{
	public LicenseStatusChangedEventArgs(TLicenseContent newLicense)
	{
		this.setNewLicense(newLicense);
	}

	private TLicenseContent NewLicense;
	public final TLicenseContent getNewLicense()
	{
		return NewLicense;
	}
	private void setNewLicense(TLicenseContent value)
	{
		NewLicense = value;
	}
}