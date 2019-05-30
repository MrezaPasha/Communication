package main.java.Rasad.Core.ActiveDirectory.ADPicker;

import Rasad.Core.*;

public class DirectoryObject
{
	private String ClassName;
	public final String getClassName()
	{
		return ClassName;
	}
	public final void setClassName(String value)
	{
		ClassName = value;
	}

	private String Name;
	public final String getName()
	{
		return Name;
	}
	public final void setName(String value)
	{
		Name = value;
	}

	private String UPN;
	public final String getUPN()
	{
		return UPN;
	}
	public final void setUPN(String value)
	{
		UPN = value;
	}

	private String Path;
	public final String getPath()
	{
		return Path;
	}
	public final void setPath(String value)
	{
		Path = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] BinarySid;
	private byte[] BinarySid;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getBinarySid()
	public final byte[] getBinarySid()
	{
		return BinarySid;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setBinarySid(byte[] value)
	public final void setBinarySid(byte[] value)
	{
		BinarySid = value;
	}

	public final String getSid()
	{
		if (this.getBinarySid() != null)
		{
			//TODO MRCOMMENT : must uncomment
			//return (new SecurityIdentifier(this.getBinarySid(), 0)).Value;
			return null;
		}
		return (String)null;
	}

	public DirectoryObject(String className, String name, String upn, String path)
	{
		this.setClassName(className);
		this.setName(name);
		this.setUPN(upn);
		this.setPath(path);
	}
}