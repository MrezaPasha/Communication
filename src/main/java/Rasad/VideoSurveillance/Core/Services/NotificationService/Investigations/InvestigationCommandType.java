package Rasad.VideoSurveillance.Core.Services.NotificationService.Investigations;

import ProtoBuf.*;
import Rasad.Core.Services.NotificationService.*;
import Rasad.VideoSurveillance.Core.*;

public enum InvestigationCommandType
{
	None(0),
	PrepareNativeFormat(1),
	PrepareCustomFormat(2),

	CancelPrepareNativeFormat(3),
	CancelPrepareCustomFormat(4),

	DeleteFileNativeFormat(5),
	DeleteFileCustomFormat(6),

	DeleteInvestigation(7),

	NewInvestigationGenerated(8); // to notify of new investigation for processing

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, InvestigationCommandType> mappings;
	private static java.util.HashMap<Integer, InvestigationCommandType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (InvestigationCommandType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, InvestigationCommandType>();
				}
			}
		}
		return mappings;
	}

	private InvestigationCommandType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static InvestigationCommandType forValue(int value)
	{
		return getMappings().get(value);
	}
}