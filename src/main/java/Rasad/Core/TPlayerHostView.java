package main.java.Rasad.Core;

import Rasad.Core.Media.*;
import Rasad.Core.Services.*;
import Rasad.Core.Services.CameraService.*;
import Rasad.VideoSurveillance.Core.Interfaces.*;
import java.util.*;
import java.io.*;

public class TPlayerHostView extends TPlayerHost
{
	private TPlayerHostView(UUID key, CameraStreamRenderMode streamRenderMode)
	{
		super(key, streamRenderMode);

	}

	public TPlayerHostView(UUID key, CameraStreamRenderMode streamRenderMode, tangible.Func0Param<ICameraInformation> camera, tangible.Func0Param<Double> width)
	{
		super(key, streamRenderMode, camera, width);

	}
	public TPlayerHostView(UUID key, CameraStreamRenderMode streamRenderMode, tangible.Func0Param<ICameraInformation> camera, ICameraProfile forcedProfile)
	{
		super(key, streamRenderMode, camera, forcedProfile);

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	public final boolean getIsViewLoaded()
	{
		return GetValue<Boolean>();
	}
	public final void setIsViewLoaded(boolean value)
	{
		if (SetValue(value))
		{
			CheckWorking();
			OnPropertyChanged(() -> getIsDownloading());
		}
	}

	@Override
	public boolean getIsDownloading()
	{
		return super.getIsDownloading() && getIsViewLoaded();
	}

	@Override
	protected boolean getAllowWorkInternal()
	{
		if (!getIsViewLoaded())
		{
			return false;
		}
		if (getForcedProfile() != null)
		{
			return true;
		}
		if (getGetWidth() == null || GetWidth() <= 0)
		{
			return false;
		}
		return true;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods



	@Override
	public void OnPlayStateChanged()
	{
		super.OnPlayStateChanged();
		tangible.Action0Param notifyToUi = () ->
		{
				OnPropertyChanged(() -> getIsDownloading());
				OnPropertyChanged(() -> getIsPlaying());
				OnPropertyChanged(() -> getIsStopped());
				OnPropertyChanged(() -> getPlayerState().getValue());
				OnPropertyChanged(() -> getCurrentFrame());
				OnPropertyChanged(() -> getActiveProfile());
		};
		notifyToUi.RunInUiNoWait();
	}

	@Override
	public String toString()
	{
		return super.toString() + " , IsViewLoaded : " + getIsViewLoaded() + " , Width : " + GetWidth().toString();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}