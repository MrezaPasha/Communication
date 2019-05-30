package Rasad.Core.Diagnostics.LogSystem;

import Rasad.Core.*;
import Rasad.Core.Diagnostics.*;
import java.time.*;
import java.math.*;

public class CustomSystemEntity implements IConvertible
{
	public CustomSystemEntity(int entityIdentifier, String entityString)
	{
		this.setEntityIdentitifer(entityIdentifier);
		this.setEntityString(entityString);
	}

	private int EntityIdentitifer;
	public final int getEntityIdentitifer()
	{
		return EntityIdentitifer;
	}
	private void setEntityIdentitifer(int value)
	{
		EntityIdentitifer = value;
	}
	private String EntityString;
	public final String getEntityString()
	{
		return EntityString;
	}
	private void setEntityString(String value)
	{
		EntityString = value;
	}

	@Override
	public String toString()
	{
		return getEntityString();
	}

	public final TypeCode GetTypeCode()
	{
		throw new UnsupportedOperationException();
	}

	public final boolean ToBoolean(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte ToByte(IFormatProvider provider)
	public final byte ToByte(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	public final char ToChar(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	public final LocalDateTime ToDateTime(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	public final BigDecimal ToDecimal(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	public final double ToDouble(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	public final short ToInt16(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	public final int ToInt32(IFormatProvider provider)
	{
		return getEntityIdentitifer();
	}

	public final long ToInt64(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	public final byte ToSByte(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	public final float ToSingle(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

	public final String toString(IFormatProvider provider)
	{
		return this.toString();
	}

	public final Object ToType(java.lang.Class conversionType, IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ushort ToUInt16(IFormatProvider provider)
	public final short ToUInt16(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint ToUInt32(IFormatProvider provider)
	public final int ToUInt32(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public ulong ToUInt64(IFormatProvider provider)
	public final long ToUInt64(IFormatProvider provider)
	{
		throw new UnsupportedOperationException();
	}
}