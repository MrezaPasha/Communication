package main.java.Rasad.Communication.Core;

import Rasad.auxilary.EventArgs;

public class GetCommunicationServiceSeverAddressEventArgs extends EventArgs
{
	public GetCommunicationServiceSeverAddressEventArgs()
	{
		super();
		setCommunicationServiceSeverAddress("");
	}

	private String CommunicationServiceSeverAddress;
	public final String getCommunicationServiceSeverAddress()
	{
		return CommunicationServiceSeverAddress;
	}
	public final void setCommunicationServiceSeverAddress(String value)
	{
		CommunicationServiceSeverAddress = value;
	}
}