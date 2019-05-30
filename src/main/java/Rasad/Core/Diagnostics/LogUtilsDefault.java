package Rasad.Core.Diagnostics;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class LogUtilsDefault extends LogUtilsBase
{

	//--------------------------------------------------
	// public section
	//--------------------------------------------------

	public LogUtilsDefault()
	{
		_activityId = UUID.NewGuid();
	}

	public LogUtilsDefault(UUID activityId)
	{
		this._activityId = activityId;
	}

	//--------------------------------------------------
	// protected section
	//--------------------------------------------------

	protected UUID _activityId;
}