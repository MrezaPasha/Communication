package Rasad.Core.Globalization;

import Rasad.Core.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [TypeConverter(typeof(TTimeSpanConverter))][Serializable, StructLayout(LayoutKind.Sequential), ComVisible(true)] public struct TTimeSpan : IComparable, IComparable<TTimeSpan>, IEquatable<TTimeSpan>, ICloneable, IXmlSerializable
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [TypeConverter(typeof(TTimeSpanConverter))][Serializable, StructLayout(LayoutKind.Sequential), ComVisible(true)] public struct TTimeSpan : IComparable, IComparable<TTimeSpan>, IEquatable<TTimeSpan>, ICloneable, IXmlSerializable
public final class TTimeSpan implements java.lang.Comparable, java.lang.Comparable<TTimeSpan>, IEquatable<TTimeSpan>, Cloneable, IXmlSerializable, Serializable
{
	public static final long TicksPerMillisecond = 10000L;
	private static final double MillisecondsPerTick = 0.0001;
	public static final long TicksPerSecond = 10000000L;
	private static final double SecondsPerTick = 1E-07;
	public static final long TicksPerMinute = 0x23c34600L;
	private static final double MinutesPerTick = 1.6666666666666667E-09;
	public static final long TicksPerHour = 0x861c46800L;
	private static final double HoursPerTick = 2.7777777777777777E-11;
	public static final long TicksPerDay = 0xc92a69c000L;
	private static final double DaysPerTick = 1.1574074074074074E-12;
	private static final int MillisPerSecond = 1000;
	private static final int MillisPerMinute = 0xea60;
	private static final int MillisPerHour = 0x36ee80;
	private static final int MillisPerDay = 0x5265c00;
	private static final long MaxSeconds = 0xd6bf94d5e5L;
	private static final long MinSeconds = -922337203685L;
	private static final long MaxMilliSeconds = 0x346dc5d638865L;
	private static final long MinMilliSeconds = -922337203685477L;
	public static TTimeSpan Zero = new TTimeSpan();
	public static TTimeSpan MaxValue = new TTimeSpan();
	public static TTimeSpan MinValue = new TTimeSpan();
	public long _ticks;

	public TTimeSpan()
	{
	}

	public TTimeSpan(long ticks)
	{
		_ticks = ticks;
	}

	public TTimeSpan(int hours, int minutes, int seconds)
	{
		_ticks = TimeToTicks(hours, minutes, seconds);
	}

	public TTimeSpan(int days, int hours, int minutes, int seconds)
	{
		this(days, hours, minutes, seconds, 0);
	}

	public TTimeSpan(int days, int hours, int minutes, int seconds, int milliseconds)
	{
		long num = ((((((days * 0xe10L) * 0x18L) + (hours * 0xe10L)) + (minutes * 60L)) + seconds) * 1000L) + milliseconds;
		if ((num > 0x346dc5d638865L) || (num < -922337203685477L))
		{
			throw new IndexOutOfBoundsException(null, "Overflow_TimeSpanTooLong");
		}
		_ticks = num * 10000L;
	}

	public long getTicks()
	{
		return _ticks;
	}

	public int getDays()
	{
		return (int)(_ticks / 0xc92a69c000L);
	}

	public int getHours()
	{
		return (int)((_ticks / 0x861c46800L) % 0x18L);
	}

	public int getMilliseconds()
	{
		return (int)((_ticks / 10000L) % 1000L);
	}

	public int getMinutes()
	{
		return (int)((_ticks / 0x23c34600L) % 60L);
	}

	public int getSeconds()
	{
		return (int)((_ticks / 10000000L) % 60L);
	}

	public double getTotalDays()
	{
		return (_ticks * 1.1574074074074074E-12);
	}

	public double getTotalHours()
	{
		return (_ticks * 2.7777777777777777E-11);
	}

	public double getTotalMilliseconds()
	{
		double num = _ticks * 0.0001;
		if (num > 922337203685477)
		{
			return 922337203685477;
		}
		if (num < -922337203685477)
		{
			return -922337203685477;
		}
		return num;
	}

	public double getTotalMinutes()
	{
		return (_ticks * 1.6666666666666667E-09);
	}

	public double getTotalSeconds()
	{
		return (_ticks * 1E-07);
	}

	public TTimeSpan Add(TTimeSpan ts)
	{
		long ticks = _ticks + ts._ticks;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		if (((_ticks >> 0x3f) == (ts._ticks >> 0x3f)) && ((_ticks >> 0x3f) != (ticks >> 0x3f)))
		{
			throw new OverflowException("Overflow_TimeSpanTooLong");
		}
		return new TTimeSpan(ticks);
	}

	public static int Compare(TTimeSpan t1, TTimeSpan t2)
	{
		if (t1._ticks > t2._ticks)
		{
			return 1;
		}
		if (t1._ticks < t2._ticks)
		{
			return -1;
		}
		return 0;
	}

	public int CompareTo(Object value)
	{
		if (value == null)
		{
			return 1;
		}
		if (!(value instanceof TTimeSpan))
		{
			throw new IllegalArgumentException("Arg_MustBeTimeSpan");
		}
		long num = ((TTimeSpan) value)._ticks;
		if (_ticks > num)
		{
			return 1;
		}
		if (_ticks < num)
		{
			return -1;
		}
		return 0;
	}

	public int CompareTo(TTimeSpan value)
	{
		long num = value._ticks;
		if (_ticks > num)
		{
			return 1;
		}
		if (_ticks < num)
		{
			return -1;
		}
		return 0;
	}

	public static TTimeSpan FromDays(double value)
	{
		return Interval(value, 86400000);
	}

	public TTimeSpan Duration()
	{
		if (_ticks == MinValue._ticks)
		{
			throw new OverflowException("Overflow_Duration");
		}
		return new TTimeSpan((_ticks >= 0L) ? _ticks : -_ticks);
	}

	@Override
	public boolean equals(Object value)
	{
		return ((value instanceof TTimeSpan) && (_ticks == ((TTimeSpan) value)._ticks));
	}

	public boolean equals(TTimeSpan obj)
	{
		return (_ticks == obj._ticks);
	}

	public static boolean equals(TTimeSpan t1, TTimeSpan t2)
	{
		return (t1._ticks == t2._ticks);
	}

	@Override
	public int hashCode()
	{
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		return (((int) _ticks) ^ ((int)(_ticks >> 32)));
	}

	public static TTimeSpan FromHours(double value)
	{
		return Interval(value, 0x36ee80);
	}

	private static TTimeSpan Interval(double value, int scale)
	{
		if (Double.isNaN(value))
		{
			throw new IllegalArgumentException("Arg_CannotBeNaN");
		}
		double num = value * scale;
		double num2 = num + ((value >= 0.0) ? 0.5 : -0.5);
		if ((num2 > 922337203685477) || (num2 < -922337203685477))
		{
			throw new OverflowException("Overflow_TimeSpanTooLong");
		}
		return new TTimeSpan(((long) num2) * 10000L);
	}

	public static TTimeSpan FromMilliseconds(double value)
	{
		return Interval(value, 1);
	}

	public static TTimeSpan FromMinutes(double value)
	{
		return Interval(value, 0xea60);
	}

	public TTimeSpan Negate()
	{
		if (_ticks == MinValue._ticks)
		{
			throw new OverflowException("Overflow_NegateTwosCompNum");
		}
		return new TTimeSpan(-_ticks);
	}

	public static TTimeSpan Parse(String s)
	{
		StringParser parser2 = new StringParser();
		return new TTimeSpan(parser2.Parse(s));
	}

	public static boolean TryParse(String s, tangible.OutObject<TTimeSpan> result)
	{
		long num;
		StringParser parser2 = new StringParser();
		tangible.OutObject<Long> tempOut_num = new tangible.OutObject<Long>();
		if (parser2.TryParse(s, tempOut_num))
		{
		num = tempOut_num.argValue;
			result.argValue = new TTimeSpan(num);
			return true;
		}
	else
	{
		num = tempOut_num.argValue;
	}
		result.argValue = Zero;
		return false;
	}

	public static TTimeSpan FromSeconds(double value)
	{
		return Interval(value, 1000);
	}

	public TTimeSpan Subtract(TTimeSpan ts)
	{
		long ticks = _ticks - ts._ticks;
//C# TO JAVA CONVERTER WARNING: The right shift operator was not replaced by Java's logical right shift operator since the left operand was not confirmed to be of an unsigned type, but you should review whether the logical right shift operator (>>>) is more appropriate:
		if (((_ticks >> 0x3f) != (ts._ticks >> 0x3f)) && ((_ticks >> 0x3f) != (ticks >> 0x3f)))
		{
			throw new OverflowException("Overflow_TimeSpanTooLong");
		}
		return new TTimeSpan(ticks);
	}

	public static TTimeSpan FromTicks(long value)
	{
		return new TTimeSpan(value);
	}

	public static long TimeToTicks(int hour, int minute, int second)
	{
		long num = ((hour * 0xe10L) + (minute * 60L)) + second;
		if ((num > 0xd6bf94d5e5L) || (num < -922337203685L))
		{
			throw new IndexOutOfBoundsException(null, "Overflow_TimeSpanTooLong");
		}
		return (num * 10000000L);
	}

	private String IntToString(int n, int digits)
	{
		return (new Integer(n)).toString(TStringHelper.DuplicateString("0", digits));
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		int num = (int)(_ticks / 864000000000L);
		long num2 = _ticks % 864000000000L;
		if (_ticks < 0L)
		{
			builder.append("-");
			num = -num;
			num2 = -num2;
		}
		if (num != 0)
		{
			builder.append(num);
			builder.append(".");
		}
		builder.append(IntToString((int)((num2 / 36000000000L) % 24L), 2));
		builder.append(":");
		builder.append(IntToString((int)((num2 / 600000000L) % 60L), 2));
		builder.append(":");
		builder.append(IntToString((int)((num2 / 10000000L) % 60L), 2));
		int n = (int)(num2 % 10000000L);
		if (n != 0)
		{
			builder.append(".");
			builder.append(IntToString(n, 7));
		}
		return builder.toString();
	}

	public static TTimeSpan OpUnaryNegation(TTimeSpan t)
	{
		if (t._ticks == MinValue._ticks)
		{
			throw new OverflowException("Overflow_NegateTwosCompNum");
		}
		return new TTimeSpan(-t._ticks);
	}

	public static TTimeSpan OpSubtraction(TTimeSpan t1, TTimeSpan t2)
	{
		return t1.Subtract(t2.clone());
	}

	public static TTimeSpan OpSubtraction(String t1, TTimeSpan t2)
	{
		return Rasad.Core.Globalization.TTimeSpan.OpSubtraction(Parse(t1), t2.clone());
	}

	public static TTimeSpan OpSubtraction(TTimeSpan t1, String t2)
	{
		return Rasad.Core.Globalization.TTimeSpan.OpSubtraction(t1.clone(), Parse(t2));
	}

	public static TTimeSpan OpSubtraction(TTimeSpan t1, int t2)
	{
		return Rasad.Core.Globalization.TTimeSpan.OpSubtraction(t1.clone(), FromMinutes(t2));
	}

	public static TTimeSpan OpSubtraction(int t1, TTimeSpan t2)
	{
		return Rasad.Core.Globalization.TTimeSpan.OpSubtraction(FromMinutes(t1), t2.clone());
	}

	public static TTimeSpan OpUnaryPlus(TTimeSpan t)
	{
		return t;
	}

	public static TTimeSpan OpIncrement(TTimeSpan t)
	{
		return t = Rasad.Core.Globalization.TTimeSpan.OpAddition(t.clone(), new TTimeSpan(0, 1, 0));
	}

	public static TTimeSpan OpDecrement(TTimeSpan t)
	{
		return t = Rasad.Core.Globalization.TTimeSpan.OpSubtraction(t.clone(), new TTimeSpan(0, 1, 0));
	}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator TTimeSpan(int d)
	{
		return FromMinutes(d);
	}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator TTimeSpan(String s)
	{
		return Parse(s);
	}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator TTimeSpan(TimeSpan s)
	{
		return new TTimeSpan(s.Ticks);
	}

//C# TO JAVA CONVERTER TODO TASK: The following operator overload is not converted by C# to Java Converter:
	public static implicit operator TimeSpan(TTimeSpan s)
	{
		return new TimeSpan(s.getTicks());
	}

	public static TTimeSpan OpAddition(TTimeSpan t1, TTimeSpan t2)
	{
		return t1.Add(t2.clone());
	}

	public static TTimeSpan OpAddition(String t1, TTimeSpan t2)
	{
		return Rasad.Core.Globalization.TTimeSpan.OpAddition(Parse(t1), t2.clone());
	}

	public static TTimeSpan OpAddition(TTimeSpan t1, String t2)
	{
		return Rasad.Core.Globalization.TTimeSpan.OpAddition(t1.clone(), Parse(t2));
	}

	public static TTimeSpan OpAddition(TTimeSpan t1, int t2)
	{
		return Rasad.Core.Globalization.TTimeSpan.OpAddition(t1.clone(), FromMinutes(t2));
	}

	public static TTimeSpan OpAddition(int t1, TTimeSpan t2)
	{
		return Rasad.Core.Globalization.TTimeSpan.OpAddition(FromMinutes(t1), t2.clone());
	}

	public static TTimeSpan OpMultiply(TTimeSpan t1, int t2)
	{
		return new TTimeSpan(t1.getTicks() * t2);
	}

	public static TTimeSpan OpMultiply(int t1, TTimeSpan t2)
	{
		return new TTimeSpan(t2.getTicks() * t1);
	}


	public static boolean OpEquality(TTimeSpan t1, TTimeSpan t2)
	{
		return (t1._ticks == t2._ticks);
	}

	public static boolean OpEquality(TTimeSpan t1, String t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpEquality(t1.clone(), Parse(t2)));
	}

	public static boolean OpEquality(String t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpEquality(Parse(t1), t2.clone()));
	}

	public static boolean OpEquality(int t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpEquality(FromMinutes(t1), t2.clone()));
	}

	public static boolean OpEquality(TTimeSpan t1, int t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpEquality(t1.clone(), FromMinutes(t2)));
	}

	public static boolean OpInequality(TTimeSpan t1, TTimeSpan t2)
	{
		return (t1._ticks != t2._ticks);
	}

	public static boolean OpInequality(TTimeSpan t1, String t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpInequality(t1.clone(), Parse(t2)));
	}

	public static boolean OpInequality(String t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpInequality(Parse(t1), t2.clone()));
	}

	public static boolean OpInequality(int t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpInequality(FromMinutes(t1), t2.clone()));
	}

	public static boolean OpInequality(TTimeSpan t1, int t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpInequality(t1.clone(), FromMinutes(t2)));
	}

	public static boolean OpLessThan(TTimeSpan t1, TTimeSpan t2)
	{
		return (t1._ticks < t2._ticks);
	}

	public static boolean OpLessThan(TTimeSpan t1, String t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpLessThan(t1.clone(), Parse(t2)));
	}

	public static boolean OpLessThan(String t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpLessThan(Parse(t1), t2.clone()));
	}

	public static boolean OpLessThan(int t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpLessThan(FromMinutes(t1), t2.clone()));
	}

	public static boolean OpLessThan(TTimeSpan t1, int t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpLessThan(t1.clone(), FromMinutes(t2)));
	}

	public static boolean OpLessThanOrEqual(TTimeSpan t1, TTimeSpan t2)
	{
		return (t1._ticks <= t2._ticks);
	}

	public static boolean OpLessThanOrEqual(TTimeSpan t1, String t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpLessThanOrEqual(t1.clone(), Parse(t2)));
	}

	public static boolean OpLessThanOrEqual(String t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpLessThanOrEqual(Parse(t1), t2.clone()));
	}

	public static boolean OpLessThanOrEqual(int t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpLessThanOrEqual(FromMinutes(t1), t2.clone()));
	}

	public static boolean OpLessThanOrEqual(TTimeSpan t1, int t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpLessThanOrEqual(t1.clone(), FromMinutes(t2)));
	}

	public static boolean OpGreaterThan(TTimeSpan t1, TTimeSpan t2)
	{
		return (t1._ticks > t2._ticks);
	}

	public static boolean OpGreaterThan(TTimeSpan t1, String t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpGreaterThan(t1.clone(), Parse(t2)));
	}

	public static boolean OpGreaterThan(String t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpGreaterThan(Parse(t1), t2.clone()));
	}

	public static boolean OpGreaterThan(int t1, TTimeSpan t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpGreaterThan(FromMinutes(t1), t2.clone()));
	}

	public static boolean OpGreaterThan(TTimeSpan t1, int t2)
	{
		return (Rasad.Core.Globalization.TTimeSpan.OpGreaterThan(t1.clone(), FromMinutes(t2)));
	}

	public static boolean OpGreaterThanOrEqual(TTimeSpan t1, TTimeSpan t2)
	{
		return (t1._ticks >= t2._ticks);
	}

	public static boolean OpGreaterThanOrEqual(TTimeSpan t1, String t2)
	{
		return (t1._ticks >= Parse(t2)._ticks);
	}

	public static boolean OpGreaterThanOrEqual(String t1, TTimeSpan t2)
	{
		return (Parse(t1)._ticks >= t2._ticks);
	}

	public static boolean OpGreaterThanOrEqual(int t1, TTimeSpan t2)
	{
		return (FromMinutes(t1)._ticks >= t2._ticks);
	}

	public static boolean OpGreaterThanOrEqual(TTimeSpan t1, int t2)
	{
		return (t1._ticks >= FromMinutes(t2)._ticks);
	}

	static
	{
		Zero = new TTimeSpan(0L);
		MaxValue = new TTimeSpan(0x7fffffffffffffffL);
		MinValue = new TTimeSpan(-9223372036854775808L);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] private struct StringParser
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] private struct StringParser
	private final static class StringParser
	{
		private String str;
		private char ch;
		private int pos;
		private int len;
		private ParseError error = ParseError.values()[0];

		public void NextChar()
		{
			if (pos < len)
			{
				pos++;
			}
			ch = (pos < len) ? str.charAt(pos) : '\0';
		}

		public char NextNonDigit()
		{
			for (int i = pos; i < len; i++)
			{
				char ch = str.charAt(i);
				if ((ch < '0') || (ch > '9'))
				{
					return ch;
				}
			}
			return '\0';
		}

		public long Parse(String s)
		{
			long num;
			tangible.OutObject<Long> tempOut_num = new tangible.OutObject<Long>();
			if (TryParse(s, tempOut_num))
			{
			num = tempOut_num.argValue;
				return num;
			}
		else
		{
			num = tempOut_num.argValue;
		}
			switch (error)
			{
				case Format:
					throw new NumberFormatException("Input string was not in a correct format.");

				case Overflow:
					throw new OverflowException("Overflow_TimeSpanTooLong");

				case OverflowHoursMinutesSeconds:
					throw new OverflowException("The TimeSpan could not be parsed because at least one of the hours, minutes, or seconds components is outside its valid range.");

				case ArgumentNull:
					throw new NullPointerException("s");
			}
			return 0L;
		}

		public boolean TryParse(String s, tangible.OutObject<Long> value)
		{
			long num;
			value.argValue = 0L;
			if (s == null)
			{
				error = ParseError.ArgumentNull;
				return false;
			}
			str = s;
			len = s.length();
			pos = -1;
			NextChar();
			SkipBlanks();
			boolean flag = false;
			if (ch == '-')
			{
				flag = true;
				NextChar();
			}
			if (NextNonDigit() == ':')
			{
				tangible.OutObject<Long> tempOut_num = new tangible.OutObject<Long>();
				if (!ParseTime(tempOut_num))
				{
				num = tempOut_num.argValue;
					return false;
				}
			else
			{
				num = tempOut_num.argValue;
			}
			}
			else
			{
				int num2;
				tangible.OutObject<Integer> tempOut_num2 = new tangible.OutObject<Integer>();
				if (!ParseInt(0xa2e3ff, tempOut_num2))
				{
				num2 = tempOut_num2.argValue;
					return false;
				}
			else
			{
				num2 = tempOut_num2.argValue;
			}
				num = num2 * 0xc92a69c000L;
				if (ch == '.')
				{
					long num3;
					NextChar();
					tangible.OutObject<Long> tempOut_num3 = new tangible.OutObject<Long>();
					if (!ParseTime(tempOut_num3))
					{
					num3 = tempOut_num3.argValue;
						return false;
					}
				else
				{
					num3 = tempOut_num3.argValue;
				}
					num += num3;
				}
			}
			if (flag)
			{
				num = -num;
				if (num > 0L)
				{
					error = ParseError.Overflow;
					return false;
				}
			}
			else if (num < 0L)
			{
				error = ParseError.Overflow;
				return false;
			}
			SkipBlanks();
			if (pos < len)
			{
				error = ParseError.Format;
				return false;
			}
			value.argValue = num;
			return true;
		}

		public boolean ParseInt(int max, tangible.OutObject<Integer> i)
		{
			i.argValue = 0;
			int pos = this.pos;
			while ((ch >= '0') && (ch <= '9'))
			{
				if ((i.argValue & 0xf0000000L) != 0L)
				{
					error = ParseError.Overflow;
					return false;
				}
				i.argValue = ((i.argValue * 10) + ch) - 0x30;
				if (i.argValue < 0)
				{
					error = ParseError.Overflow;
					return false;
				}
				NextChar();
			}
			if (pos == this.pos)
			{
				error = ParseError.Format;
				return false;
			}
			if (i.argValue > max)
			{
				error = ParseError.Overflow;
				return false;
			}
			return true;
		}

		public boolean ParseTime(tangible.OutObject<Long> time)
		{
			int num;
			time.argValue = 0L;
			tangible.OutObject<Integer> tempOut_num = new tangible.OutObject<Integer>();
			if (!ParseInt(0x17, tempOut_num))
			{
			num = tempOut_num.argValue;
				if (error == ParseError.Overflow)
				{
					error = ParseError.OverflowHoursMinutesSeconds;
				}
				return false;
			}
		else
		{
			num = tempOut_num.argValue;
		}
			time.argValue = num * 0x861c46800L;
			if (ch != ':')
			{
				error = ParseError.Format;
				return false;
			}
			NextChar();
			tangible.OutObject<Integer> tempOut_num2 = new tangible.OutObject<Integer>();
			if (!ParseInt(0x3b, tempOut_num2))
			{
			num = tempOut_num2.argValue;
				if (error == ParseError.Overflow)
				{
					error = ParseError.OverflowHoursMinutesSeconds;
				}
				return false;
			}
		else
		{
			num = tempOut_num2.argValue;
		}
			time.argValue += num * 0x23c34600L;
			if (ch == ':')
			{
				NextChar();
				if (ch != '.')
				{
					tangible.OutObject<Integer> tempOut_num3 = new tangible.OutObject<Integer>();
					if (!ParseInt(0x3b, tempOut_num3))
					{
					num = tempOut_num3.argValue;
						if (error == ParseError.Overflow)
						{
							error = ParseError.OverflowHoursMinutesSeconds;
						}
						return false;
					}
				else
				{
					num = tempOut_num3.argValue;
				}
					time.argValue += num * 10000000L;
				}
				if (ch == '.')
				{
					NextChar();
					int num2 = 10000000;
					while (((num2 > 1) && (ch >= '0')) && (ch <= '9'))
					{
						num2 /= 10;
						time.argValue += (ch - '0') * num2;
						NextChar();
					}
				}
			}
			return true;
		}

		public void SkipBlanks()
		{
			while ((ch == ' ') || (ch == '\t'))
			{
				NextChar();
			}
		}

		private enum ParseError
		{
			ArgumentNull(4),
			Format(1),
			Overflow(2),
			OverflowHoursMinutesSeconds(3);

			public static final int SIZE = java.lang.Integer.SIZE;

			private int intValue;
			private static java.util.HashMap<Integer, ParseError> mappings;
			private static java.util.HashMap<Integer, ParseError> getMappings()
			{
				if (mappings == null)
				{
					synchronized (ParseError.class)
					{
						if (mappings == null)
						{
							mappings = new java.util.HashMap<Integer, ParseError>();
						}
					}
				}
				return mappings;
			}

			private ParseError(int value)
			{
				intValue = value;
				getMappings().put(value, this);
			}

			public int getValue()
			{
				return intValue;
			}

			public static ParseError forValue(int value)
			{
				return getMappings().get(value);
			}
		}

		public StringParser clone()
		{
			StringParser varCopy = new StringParser();

			varCopy.str = this.str;
			varCopy.ch = this.ch;
			varCopy.pos = this.pos;
			varCopy.len = this.len;
			varCopy.error = this.error;

			return varCopy;
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region ICloneable Members

	public Object Clone()
	{
		return clone();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IXmlSerializable Members

	public XmlSchema GetSchema()
	{
		return null;
	}

	public void ReadXml(XmlReader reader)
	{
		String InnerXml = reader.ReadInnerXml();
		_ticks = Parse(InnerXml).getTicks();
	}

	public void WriteXml(XmlWriter writer)
	{
		writer.WriteValue(toString());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public TTimeSpan clone()
	{
		TTimeSpan varCopy = new TTimeSpan();

		varCopy._ticks = this._ticks;

		return varCopy;
	}
}