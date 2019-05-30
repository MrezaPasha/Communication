package Rasad.VideoSurveillance.Core;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract] public class TCameraEventParameter
public class TCameraEventParameter
{
	public TCameraEventParameter()
	{
		this(null, null);
	}
	public TCameraEventParameter(String name, String value)
	{
		this.setName(name);
		this.setValue(value);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Name {get;set;}
	private String Name;
	public final String getName()
	{
		return Name;
	}
	public final void setName(String value)
	{
		Name = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Value {get;set;}
	private String Value;
	public final String getValue()
	{
		return Value;
	}
	public final void setValue(String value)
	{
		Value = value;
	}
}