package Rasad.Core.Globalization;

import Rasad.Core.*;

/** 
	 Type Converter for PersianDate type which handles convertion of various types to PersianDate, mostly in design
	 mode.
*/
public class TDateTimeTypeConverter extends TypeConverter
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Override

	@Override
	public boolean CanConvertFrom(ITypeDescriptorContext context, java.lang.Class sourceType)
	{
		if (sourceType == String.class)
		{
			return true;
		}

		return super.CanConvertFrom(context, sourceType);
	}

	@Override
	public Object ConvertFrom(ITypeDescriptorContext context, CultureInfo culture, Object value)
	{
		if (value != null && value instanceof String)
		{
			return new TDateTime(value.toString());
		}

		return super.ConvertFrom(context, culture, value);
	}

	@Override
	public Object ConvertTo(ITypeDescriptorContext context, CultureInfo culture, Object value, java.lang.Class destinationType)
	{
		if (value != null && value instanceof TDateTime)
		{
			if (destinationType == InstanceDescriptor.class && value != null)
			{
				TDateTime pd = (TDateTime) value;
				java.lang.reflect.Constructor ctor = TDateTime.class.GetConstructor(new java.lang.Class[] {Integer.class, Integer.class, Integer.class});
				Object[] args = {pd.getYear(), pd.getMonth(), pd.getDay()};

				if (ctor != null)
				{
					return new InstanceDescriptor(ctor, args);
				}
			}
			else if (destinationType == String.class)
			{
				TDateTime pd = (TDateTime) value;
				return pd.toString();
			}
			else if (destinationType == TDateTime.class)
			{
				TDateTime pd = (TDateTime) value;
				return pd;
			}
		}

		return super.ConvertTo(context, culture, value, destinationType);
	}

	@Override
	public boolean CanConvertTo(ITypeDescriptorContext context, java.lang.Class destinationType)
	{
		if (destinationType == String.class || destinationType == TDateTime.class || destinationType == InstanceDescriptor.class)
		{
			return true;
		}

		return super.CanConvertTo(context, destinationType);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}