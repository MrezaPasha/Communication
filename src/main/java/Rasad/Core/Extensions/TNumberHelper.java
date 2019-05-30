package Rasad.Core.Extensions;

import Rasad.Core.*;

public final class TNumberHelper
{
	private static final CultureInfo arabic = new CultureInfo("fa-IR");
	private static final CultureInfo latin = new CultureInfo("en-US");

	public static String ToArabic(String input)
	{
		String[] arabicDigits = arabic.NumberFormat.NativeDigits;
		for (int i = 0; i < arabicDigits.length; i++)
		{
			input = input.replace(String.valueOf(i), arabicDigits[i]);
		}
		return input;
	}
	public static String ToLatin(String input)
	{
		String[] latinDigits = latin.NumberFormat.NativeDigits;
		String[] arabicDigits = arabic.NumberFormat.NativeDigits;
		for (int i = 0; i < latinDigits.length; i++)
		{
			input = input.replace(arabicDigits[i], latinDigits[i]);
		}
		return input;
	}
	private static String[] cvtText = new String[1000];

	static
	{
		BuildMapping();
	}

	private static void BuildMapping()
	{
		cvtText[1] = "يک";
		cvtText[2] = "دو";
		cvtText[3] = "سه";
		cvtText[4] = "چهار";
		cvtText[5] = "پنج";
		cvtText[6] = "شش";
		cvtText[7] = "هفت";
		cvtText[8] = "هشت";
		cvtText[9] = "نه";
		cvtText[10] = "ده";
		cvtText[11] = "یازده";
		cvtText[12] = "دوازده";
		cvtText[13] = "سیزده";
		cvtText[14] = "چهارده";
		cvtText[15] = "پانزده";
		cvtText[16] = "شانزده";
		cvtText[17] = "هفده";
		cvtText[18] = "هجده";
		cvtText[19] = "نوزده";
		cvtText[20] = "بيست";
		cvtText[21] = "سی";
		cvtText[22] = "چهل";
		cvtText[23] = "پنجاه";
		cvtText[24] = "شصت";
		cvtText[25] = "هفتاد";
		cvtText[26] = "هشتاد";
		cvtText[27] = "نود";
		cvtText[28] = "صد";
		cvtText[29] = "هزار";
		cvtText[30] = "میلیون";
		cvtText[31] = "میلیارد";
		cvtText[100] = "صد";
		cvtText[200] = "دویست";
		cvtText[300] = "سیصد";
		cvtText[400] = "چهارصد";
		cvtText[500] = "پانصد";
		cvtText[600] = "ششصد";
		cvtText[700] = "هفتصد";
		cvtText[800] = "هشتصد";
		cvtText[900] = "نهصد";
	}

	private static String cvt100(long Number, boolean sequential, long baseNumber)
	{
		int x = (int)Number;

		int t;
		String result = "";

		if (x > 999)
		{
			throw new IndexOutOfBoundsException("Number is larger than 999");
		}

		if (x > 99)
		{
			t = x / 100;
			switch (t)
			{
				case 1:
					result = cvtText[100];
					break;
				case 2:
					result = cvtText[200];
					break;
				case 3:
					result = cvtText[300];
					break;
				case 4:
					result = cvtText[400];
					break;
				case 5:
					result = cvtText[500];
					break;
				case 6:
					result = cvtText[600];
					break;
				case 7:
					result = cvtText[700];
					break;
				case 8:
					result = cvtText[800];
					break;
				case 9:
					result = cvtText[900];
					break;
			}

			x = x - (t * 100);

			if (x <= 0)
			{
				if (sequential)
				{
					result += "م";
				}
				return result;
			}
			else
			{
				result += String.format(" %1$s ", " و ");
				;
			}
		}

		if (x == 30)
		{
			result = cvtText[21];
			if (sequential)
			{
				return result + " ام";
			}
			else
			{
				return result;
			}
		}
		else if (x > 20)
		{
			t = x / 10;
			result = result + cvtText[t + 18];
			x = x - (t * 10);

			if (x <= 0)
			{
				if (sequential)
				{
					result += "م";
				}
				return result;
			}
			else
			{
				result += String.format(" %1$s ", " و ");
				;
			}
		}

		if (x > 0)
		{
			if (sequential)
			{
				if (x == 1 && baseNumber == 1)
				{
					result += "اول";
				}
				else if (x == 3)
				{
					result += "سوم";
				}
				else
				{
					result += cvtText[x] + "م";
				}
			}
			else
			{
				result += cvtText[x];
			}
		}

		return result;
	}

	/** Converts a long number to its written form in Persian
	 @param x
	 @return 
	 @exception ArgumentOutOfRangeException
	*/

	public static String ToWord(long x)
	{
		return ToWord(x, false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static string ToWord(this Int64 x, bool sequential = false)
	public static String ToWord(long x, boolean sequential)
	{
		long BaseNumber = x;
		//Build array for number to words mapping
		//BuildMapping();

		long t;
		String result = "";

		if (x > 999999999999)
		{
			throw new IndexOutOfBoundsException("Number is too large to process");
		}

		if (x > 999999999)
		{
			t = x / 1000000000;
			result += cvt100(t, false, 0) + " " + cvtText[31];
			x = x - (t * 1000000000);

			if (x <= 0)
			{
				if (sequential)
				{
					result += "م";
				}
				return result;
			}
			else
			{
				result += String.format(" %1$s ", " و ");
			}
		}

		if (x > 999999)
		{
			t = x / 1000000;
			result += cvt100(t, false, 0) + " " + cvtText[30];
			x = x - (t * 1000000);

			if (x <= 0)
			{
				if (sequential)
				{
					result += "م";
				}
				return result;
			}
			else
			{
				result += String.format(" %1$s ", " و ");
			}
		}

		if (x > 999)
		{
			t = x / 1000;
			result += cvt100(t, false, 0) + " " + cvtText[29];
			x = x - (t * 1000);

			if (x <= 0)
			{
				if (sequential)
				{
					result += "م";
				}
				return result;
			}
			else
			{
				result += String.format(" %1$s ", " و ");
				;
			}
		}

		if (x > 0)
		{
			result += cvt100(x, sequential, BaseNumber);
		}
		else if (x == 0)
		{
			if (sequential)
			{
				result += "صفرم";
			}
			else
			{
				result += "صفر";
			}
		}

		return result;
	}


//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string ToHex(this System.Byte thisNumber)
	public static String ToHex(byte thisNumber)
	{
		return String.format("%02x", thisNumber).toUpperCase();
	}
	public static String ToHex(short thisNumber)
	{
		return String.format("%02x", thisNumber).toUpperCase();
	}
	public static String ToHex(int thisNumber)
	{
		return String.format("%02x", thisNumber).toUpperCase();
	}
	public static String ToHex(long thisNumber)
	{
		return String.format("%02x", thisNumber).toUpperCase();
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string ToHex(this System.UInt16 thisNumber)
	public static String ToHex(short thisNumber)
	{
		return String.format("%02x", thisNumber).toUpperCase();
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string ToHex(this System.UInt32 thisNumber)
	public static String ToHex(int thisNumber)
	{
		return String.format("%02x", thisNumber).toUpperCase();
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static string ToHex(this System.UInt64 thisNumber)
	public static String ToHex(long thisNumber)
	{
		return String.format("%02x", thisNumber).toUpperCase();
	}

	/** 
	 تبدیل عدد به حروف فارسی
	 
	 @param thisNumber
	 @return 
	*/
	public static String ToWord(short thisNumber)
	{
		return Rasad.Core.Extensions.TNumberHelper.ToWord((long)thisNumber);
	}
	/** 
	 تبدیل عدد به حروف فارسی ترتیبی
	 
	 @param thisNumber
	 @param sequential ترتیبی
	 @return 
	*/
	public static String ToWord(short thisNumber, boolean sequential)
	{
		return Rasad.Core.Extensions.TNumberHelper.ToWord((long)thisNumber, sequential);
	}
	/** 
	 تبدیل عدد به حروف فارسی
	 
	 @param thisNumber
	 @return 
	*/
	public static String ToWord(int thisNumber)
	{
		return Rasad.Core.Extensions.TNumberHelper.ToWord((long)thisNumber);
	}
	/** 
	 تبدیل عدد به حروف فارسی ترتیبی
	 
	 @param thisNumber
	 @param sequential ترتیبی
	 @return 
	*/
	public static String ToWord(int thisNumber, boolean sequential)
	{
		return Rasad.Core.Extensions.TNumberHelper.ToWord((long)thisNumber, sequential);
	}

	public static String Join(java.lang.Iterable<Integer> thisArray, String seaprator)
	{
		return System.Linq.TDynamicEnumerable.Select(thisArray, t -> t.toString()).Concat(seaprator);
	}

	public static Integer ToNullableInt32(String source)
	{
		if (System.TStringHelper.IsNullOrEmpty(source))
		{
			return null;
		}
		try
		{
			return Integer.parseInt(source);
		}
		catch (java.lang.Exception e)
		{
			return null;
		}
	}


	public static int ToInt32(String source)
	{
		return ToInt32(source, 0);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static int ToInt32(string source, int defaultValue = 0)
	public static int ToInt32(String source, int defaultValue)
	{
		try
		{
			return Integer.parseInt(source);
		}
		catch (java.lang.Exception e)
		{
			return defaultValue;
		}
	}

	public static Float ToNullableSingle(String source)
	{
		if (System.TStringHelper.IsNullOrEmpty(source))
		{
			return null;
		}
		try
		{
			return Float.parseFloat(source);
		}
		catch (java.lang.Exception e)
		{
			return null;
		}
	}
}