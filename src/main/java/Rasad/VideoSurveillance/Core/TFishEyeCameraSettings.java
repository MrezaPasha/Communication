package Rasad.VideoSurveillance.Core;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.Services.ICameraInformation;
import main.java.Rasad.Core.TViewModelBase;

import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(TFishEyeVirtualProfileSize))][KnownType(typeof(TFishEyeExtraParameter))][KnownType(typeof(FishEyeCameraLocation))][DataContract(IsReference = true)][Serializable] public class TFishEyeCameraSettings : TViewModelBase
public class TFishEyeCameraSettings extends TViewModelBase implements Serializable
{
	public TFishEyeCameraSettings(ICameraInformation camera)
	{
		setCamera(camera);

		setVirtualProfiles(new List<Rasad.VideoSurveillance.Core.TFishEyeVirtualProfileSize>());
		setExtraParameters(new ObservableCollection<TFishEyeExtraParameter>());
		getExtraParameters().Add(new TFishEyeExtraParameter("D1", "-1"));
		getExtraParameters().Add(new TFishEyeExtraParameter("D2", "-1"));
		getExtraParameters().Add(new TFishEyeExtraParameter("D3", "-1"));
		getExtraParameters().Add(new TFishEyeExtraParameter("D4", "-1"));
	}

	private ICameraInformation _Camera;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [IgnoreDataMember] public ICameraInformation Camera
	public final ICameraInformation getCamera()
	{
		return _Camera;
	}
	public final void setCamera(ICameraInformation value)
	{
		_Camera = value;
		OnPropertyChanged(() -> getFishEyeProfiles());
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public FishEyeCameraLocation Location
	public final FishEyeCameraLocation getLocation()
	{
		return GetValue<FishEyeCameraLocation>();
	}
	public final void setLocation(FishEyeCameraLocation value)
	{
		SetValue(value);
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public ObservableCollection<TFishEyeVirtualProfileSize> VirtualProfiles {get;private set;}
	private ObservableCollection<TFishEyeVirtualProfileSize> VirtualProfiles;
	public final ObservableCollection<TFishEyeVirtualProfileSize> getVirtualProfiles()
	{
		return VirtualProfiles;
	}
	private void setVirtualProfiles(ObservableCollection<TFishEyeVirtualProfileSize> value)
	{
		VirtualProfiles = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public TFishEyeVirtualProfileSize ProfileSmall
	public final TFishEyeVirtualProfileSize getProfileSmall()
	{
		return GetValue<TFishEyeVirtualProfileSize>();
	}
	public final void setProfileSmall(TFishEyeVirtualProfileSize value)
	{
		SetValue(value);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public TFishEyeVirtualProfileSize ProfileMedium
	public final TFishEyeVirtualProfileSize getProfileMedium()
	{
		return GetValue<TFishEyeVirtualProfileSize>();
	}
	public final void setProfileMedium(TFishEyeVirtualProfileSize value)
	{
		SetValue(value);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public TFishEyeVirtualProfileSize ProfileLarge
	public final TFishEyeVirtualProfileSize getProfileLarge()
	{
		return GetValue<TFishEyeVirtualProfileSize>();
	}
	public final void setProfileLarge(TFishEyeVirtualProfileSize value)
	{
		SetValue(value);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public ObservableCollection<TFishEyeExtraParameter> ExtraParameters {get;private set;}
	private ObservableCollection<TFishEyeExtraParameter> ExtraParameters;
	public final ObservableCollection<TFishEyeExtraParameter> getExtraParameters()
	{
		return ExtraParameters;
	}
	private void setExtraParameters(ObservableCollection<TFishEyeExtraParameter> value)
	{
		ExtraParameters = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [IgnoreDataMember] public IEnumerable<ICameraProfile> FishEyeProfiles
	public final java.lang.Iterable<ICameraProfile> getFishEyeProfiles()
	{
		if (getCamera() == null)
		{
			return new ArrayList<ICameraProfile>();
		}
		else
		{
			return getCamera().getProfiles();
		}
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String FishEyeProfileToken
	public final String getFishEyeProfileToken()
	{
		return GetValue<String>();
	}
	public final void setFishEyeProfileToken(String value)
	{
		SetValue(value);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String FishEyeSmallProfileToken
	public final String getFishEyeSmallProfileToken()
	{
		return GetValue<String>();
	}
	public final void setFishEyeSmallProfileToken(String value)
	{
		SetValue(value);
	}

	public final void GenerateDefaultSizes(Size biggestResolution)
	{
		int parts = 5;
		int widthStep = biggestResolution.Width / parts;
		this.getVirtualProfiles().Clear();
		tangible.Action1Param<Integer> addAct = (int obj) ->
		{
				if (((w < biggestResolution.Width && Math.abs(biggestResolution.Width - w) > 320) || w == biggestResolution.Width) && !this.getVirtualProfiles().Any(s -> s.Resolution.Width == w))
				{
					TFishEyeVirtualProfileSize tempVar = new TFishEyeVirtualProfileSize();
					tempVar.setResolution(new Size(w, w * 3 / 4));
					this.getVirtualProfiles().Add(tempVar);
				}
		};
		addAct.invoke(biggestResolution.Width);
		addAct.invoke(320);
		addAct.invoke(640);
		addAct.invoke(800);
		addAct.invoke(1280);
		addAct.invoke(1920);
		this.getVirtualProfiles().Sort(s -> s.Resolution.Width);
		this.setProfileSmall(this.getVirtualProfiles().get(0));
		this.setProfileMedium(this.getVirtualProfiles().get(this.getVirtualProfiles().Count / 2));
		this.setProfileLarge(this.getVirtualProfiles().get(this.getVirtualProfiles().Count - 1));
	}

	public final String GetSettingsXml()
	{
		return this.Serialize();
	}

	public static TFishEyeCameraSettings LoadFromXml(String settingsXml)
	{
		return TFishEyeCameraSettings.Deserialize(settingsXml);
	}

	public static TFishEyeCameraSettings Deserialize(String data)
	{
		return TSerializationHelper.<TFishEyeCameraSettings>DataContractDeserialize(data);
	}

	public final String Serialize()
	{
		return TSerializationHelper.DataContractSerialize(this);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [OnDeserialized] private void SetValuesOnDeserialized(StreamingContext context)
	private void SetValuesOnDeserialized(StreamingContext context)
	{
		if (getVirtualProfiles() == null)
		{
			setVirtualProfiles(new ObservableCollection<TFishEyeVirtualProfileSize>());
		}
	}
}