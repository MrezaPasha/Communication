package main.java.Rasad.Core.Services.NotificationService.Services;


import main.java.Rasad.Core.Services.NotificationService.TMessageBase;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoContract][DataContract] public class TRequestOpenLicenseContent : TMessageBase
public class TRequestOpenLicenseContent extends TMessageBase
{
	private TRequestOpenLicenseContent()
	{

	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public TRequestOpenLicenseContent(byte[] content)
	public TRequestOpenLicenseContent(byte[] content)
	{
		super();
		this.setContent(content);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember][ProtoMember(1)] public byte[] Content {get;private set;}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] Content;
	private byte[] Content;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] getContent()
	public final byte[] getContent()
	{
		return Content;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private void setContent(byte[] value)
	private void setContent(byte[] value)
	{
		Content = value;
	}
}