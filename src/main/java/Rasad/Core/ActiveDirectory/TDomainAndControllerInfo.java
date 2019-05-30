package main.java.Rasad.Core.ActiveDirectory;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class TDomainAndControllerInfo
{
	public TDomainAndControllerInfo()
	{
		setDomainName("");
		setDomainControllerName("");
		setDomainControllerIPAddress("");
	}

	private String DomainName;
	public final String getDomainName()
	{
		return DomainName;
	}
	public final void setDomainName(String value)
	{
		DomainName = value;
	}
	/** 
	 Controller may be empty if unable to get it
	*/
	private String DomainControllerName;
	public final String getDomainControllerName()
	{
		return DomainControllerName;
	}
	public final void setDomainControllerName(String value)
	{
		DomainControllerName = value;
	}
	private String DomainControllerIPAddress;
	public final String getDomainControllerIPAddress()
	{
		return DomainControllerIPAddress;
	}
	public final void setDomainControllerIPAddress(String value)
	{
		DomainControllerIPAddress = value;
	}
}