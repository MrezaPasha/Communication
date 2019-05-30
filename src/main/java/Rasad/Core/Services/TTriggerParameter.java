package main.java.Rasad.Core.Services;

import Rasad.Core.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract] public class TTriggerParameter
public class TTriggerParameter
{

	public TTriggerParameter(int parameterIndex, String title)
	{
		this(parameterIndex, title, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TTriggerParameter(int parameterIndex, string title, object value = null)
	public TTriggerParameter(int parameterIndex, String title, Object value)
	{
		this.setParameterIndex(parameterIndex);
		this.setTitle(title);
		this.setValue(value);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public int ParameterIndex {get;set;}
	private int ParameterIndex;
	public final int getParameterIndex()
	{
		return ParameterIndex;
	}
	public final void setParameterIndex(int value)
	{
		ParameterIndex = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public string Title {get;set;}
	private String Title;
	public final String getTitle()
	{
		return Title;
	}
	public final void setTitle(String value)
	{
		Title = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public object Value {get;set;}
	private Object Value;
	public final Object getValue()
	{
		return Value;
	}
	public final void setValue(Object value)
	{
		Value = value;
	}

	@Override
	public String toString()
	{
		return String.format("%1$s - %2$s => %3$s", getParameterIndex(), getTitle(), getValue());
	}
}