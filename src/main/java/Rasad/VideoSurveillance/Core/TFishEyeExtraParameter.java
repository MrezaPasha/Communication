package Rasad.VideoSurveillance.Core;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Serializable][DataContract(IsReference = true)] public class TFishEyeExtraParameter : TViewModelBase
public class TFishEyeExtraParameter extends TViewModelBase implements Serializable
{
	public TFishEyeExtraParameter()
	{
		this("", "");
	}
	public TFishEyeExtraParameter(String title, String value)
	{
		this.setTitle(title);
		this.setValue(value);
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Title {get;set;}
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

	@Override
	public Object getvalue() {
		return null;
	}
}