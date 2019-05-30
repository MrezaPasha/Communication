package Rasad.VideoSurveillance.Core;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Serializable][DataContract(IsReference = true)][KnownType(typeof(Size))] public class TFishEyeVirtualProfileSize : TViewModelBase
public class TFishEyeVirtualProfileSize extends TViewModelBase implements Serializable
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public Size Resolution
	public final Size getResolution()
	{
		return GetValue<Size>();
	}
	public final void setResolution(Size value)
	{
		SetValue(value);
	}

	@Override
	public String toString()
	{
		return getResolution().Width.toString();
	}
}