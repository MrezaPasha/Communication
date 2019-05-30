package Rasad.VideoSurveillance.Core.Helpers;

import Rasad.Core.Globalization.*;
import Rasad.VideoSurveillance.Core.*;
import java.time.*;

public final class TRecordFilePropertyHelper
{
	private static Object ReadOrThrow(Object value, String itemName)
	{
		if (value == null)
		{
			throw new RuntimeException("Field not available : " + itemName);
		}
		return value;
	}

	public static void SetRecordFileProperties(String filename, TRecordFileProperties props)
	{
		try (ShellObject file = ShellObject.FromParsingName(filename))
		{
			try (ShellPropertyWriter propertyWriter = file.Properties.GetPropertyWriter())
			{
				// HasRasadFlag
				propertyWriter.WriteProperty(file.Properties.System.ApplicationName, "RASAD RVS");

				// comment
				tangible.Func1Param<LocalDateTime, String> timeToStr = (LocalDateTime arg) ->
				{
						return String.format("%02d:%02d:%02d", d.Hour, d.Minute, d.Second);
				};
				String comment = String.format("%1$s - %2$s - %3$s", props.getCameraName(), ShDate.DateToStr(props.getStartDateTime()) + " " + timeToStr.invoke(props.getStartDateTime()), ShDate.DateToStr(props.getEndDateTime()) + " " + timeToStr.invoke(props.getEndDateTime()));
				propertyWriter.WriteProperty(file.Properties.System.Comment, comment);

				// RecordFileID
				propertyWriter.WriteProperty(file.Properties.System.Project, String.valueOf(props.getRecordFileID()));

				// CameraName
				propertyWriter.WriteProperty(file.Properties.System.Status, props.getCameraName());

				// CameraID
				propertyWriter.WriteProperty(file.Properties.System.AcquisitionID, props.getCameraID());

				// MACAddress
				propertyWriter.WriteProperty(file.Properties.System.IdentityProperty, props.getMACAddress());

				// IPAddress
				propertyWriter.WriteProperty(file.Properties.System.InternalName, props.getIPAddress());

				// StartDateTime
				propertyWriter.WriteProperty(file.Properties.System.StartDate, props.getStartDateTime());

				// EdnDateTime
				propertyWriter.WriteProperty(file.Properties.System.EndDate, props.getEndDateTime());

				// Duration
				propertyWriter.WriteProperty(file.Properties.System.PriorityText, String.valueOf(props.getDuration().TotalMilliseconds));

				// FrameRate
				propertyWriter.WriteProperty(file.Properties.System.ProviderItemID, String.valueOf(props.getFrameRate()));

				// TotalFrames
				propertyWriter.WriteProperty(file.Properties.System.SourceItem, String.valueOf(props.getTotalFrames()));

				propertyWriter.Close();
			}
		}
	}

	public static TRecordFileProperties GetRecordFileProperties(String filename)
	{
		TRecordFileProperties props = new TRecordFileProperties();
		try (ShellObject file = ShellObject.FromParsingName(filename))
		{
			try (ShellPropertyWriter propertyWriter = file.Properties.GetPropertyWriter())
			{
				// HasRasadFlag
				props.setHasRasadFlag(file.Properties.System.ApplicationName.Value.equals("RASAD RVS"));
				// don't use comment! file.Properties.System.Comment

				// RecordFileID
				props.setRecordFileID((Long)ReadOrThrow(file.Properties.System.Project.Value, "RecordFileID"));

				// CameraName
				props.setCameraName(file.Properties.System.Status.Value);

				// CameraID
				props.setCameraID((Short)ReadOrThrow(file.Properties.System.AcquisitionID.Value, "CameraID"));

				// MACAddress
				props.setMACAddress(file.Properties.System.IdentityProperty.Value);

				// IPAddress
				props.setIPAddress(file.Properties.System.InternalName.Value);

				// StartDateTime
				props.setStartDateTime(file.Properties.System.StartDate.Value.Value);

				// EdnDateTime
				props.setEndDateTime(file.Properties.System.EndDate.Value.Value);

				// Duration
				props.setDuration(TimeSpan.FromMilliseconds(Double.parseDouble((String)ReadOrThrow(file.Properties.System.PriorityText.Value, "Duration"))));

				// FrameRate
				props.setFrameRate(Double.parseDouble((String)ReadOrThrow(file.Properties.System.ProviderItemID.Value, "FrameRate")));

				// TotalFrames
				props.setTotalFrames(Long.parseLong((String)ReadOrThrow(file.Properties.System.SourceItem.Value, "TotalFrames")));
			}
		}
		return props;
	}
}