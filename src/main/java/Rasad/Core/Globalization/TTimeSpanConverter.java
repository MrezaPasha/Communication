package Rasad.Core.Globalization;

import Rasad.Core.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [HostProtection(SecurityAction.LinkDemand, SharedState = true)] public class TTimeSpanConverter : TypeConverter
public class TTimeSpanConverter extends TypeConverter
{
	@Override
	public boolean CanConvertFrom(ITypeDescriptorContext context, java.lang.Class sourceType)
	{
		return ((sourceType == String.class) || super.CanConvertFrom(context, sourceType));
	}

	@Override
	public boolean CanConvertTo(ITypeDescriptorContext context, java.lang.Class destinationType)
	{
		return ((destinationType == InstanceDescriptor.class) || super.CanConvertTo(context, destinationType));
	}

	@Override
	public Object ConvertFrom(ITypeDescriptorContext context, CultureInfo culture, Object value)
	{
		if (value instanceof String)
		{
			String s = ((String) value).trim();
			try
			{
				return TTimeSpan.Parse(s);
			}
			catch (NumberFormatException exception)
			{
				throw new NumberFormatException("ConvertInvalidPrimitive", exception);
			}
		}
		return super.ConvertFrom(context, culture, value);
	}

	@Override
	public Object ConvertTo(ITypeDescriptorContext context, CultureInfo culture, Object value, java.lang.Class destinationType)
	{
		if (destinationType == null)
		{
			throw new NullPointerException("destinationType");
		}
		if ((destinationType == InstanceDescriptor.class) && (value instanceof TTimeSpan))
		{
			java.lang.reflect.Method method = TTimeSpan.class.GetMethod("Parse", new java.lang.Class[] {String.class});
			if (method != null)
			{
				return new InstanceDescriptor(method, new Object[] {value.toString()});
			}
		}
		return super.ConvertTo(context, culture, value, destinationType);
	}
}