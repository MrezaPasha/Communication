package Rasad.Core.Globalization;

import Rasad.Core.*;
import java.time.*;

public class TShamsiDateTimeConverter implements IValueConverter
{
	public final Object Convert(Object value, java.lang.Class targetType, Object parameter, CultureInfo culture)
	{
		return ConvertTo(value, targetType);
	}

	public final Object ConvertBack(Object value, java.lang.Class targetType, Object parameter, CultureInfo culture)
	{
		return ConvertTo(value, targetType);
	}


	public static Object ConvertTo(Object value, java.lang.Class targetType)
	{
		return ConvertTo(value, targetType, false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static object ConvertTo(object value, Type targetType, bool includeLitteral = false)
	public static Object ConvertTo(Object value, java.lang.Class targetType, boolean includeLitteral)
	{
		if (System.TTypeHelper.IsNumeric(targetType) || targetType == Object.class)
		{
			if (value == null)
			{
				return System.TTypeHelper.GetDefaultValue(targetType);
			}
			if (value.getClass().IsNumeric() || value.getClass().IsString())
			{
				return System.TObjectHelper.To(value, targetType);
			}
			if (value instanceof LocalDateTime)
			{
				return ShDate.DateToInt((LocalDateTime) value).To(targetType);
			}
			throw new IllegalStateException(String.format("Can not Convert to %1$s", targetType.getSimpleName()));
		}
		if (System.TTypeHelper.IsString(targetType))
		{
			if (value == null)
			{
				return null;
			}
			if (value.getClass().IsNumeric() || value.getClass().IsString())
			{
				return System.TObjectHelper.To(value, targetType);
			}
			if (value instanceof LocalDateTime)
			{
				return ShDate.DateToStr((LocalDateTime) value, (includeLitteral ? "/" : ""));
			}
			throw new IllegalStateException(String.format("Can not Convert to %1$s", targetType.getSimpleName()));
		}
		if (System.TTypeHelper.IsDateTime(targetType) || targetType == LocalDateTime.class)
		{
			if (value == null)
			{
				return null;
			}
			if (value.getClass().IsNumeric() || value.getClass().IsString())
			{
				if (ShDate.IsDateValid(value.toString()))
				{
					return ShDate.ToDateTime(value.toString());
				}
				return null;
			}
			if (value instanceof LocalDateTime)
			{
				return value;
			}
			throw new IllegalStateException(String.format("Can not Convert to %1$s", targetType.getSimpleName()));
		}
		throw new IllegalStateException(String.format("Can not Convert to %1$s", targetType.getSimpleName()));
	}
}