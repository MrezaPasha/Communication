package Rasad.Core.Extensions;

import Rasad.Core.*;

public final class Validate
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Method
	public static boolean IsFirstNumber(Object source)
	{
		if (Rasad.Core.Extensions.Validate.IsNull(source) || Rasad.Core.Extensions.Validate.IsDBNull(source) || source.toString().length() == 0)
		{
			return false;
		}
		//Regex validator = new Regex(@"^([A-Z][a-z]+)(\s[A-Z][a-z]+)*$");
		Regex validator = new Regex("(\\d+)([A-Z][a-z]+)", RegexOptions.IgnoreCase.getValue() | RegexOptions.CultureInvariant.getValue() | RegexOptions.IgnorePatternWhitespace.getValue() | RegexOptions.Compiled.getValue());
		return validator.IsMatch(source.toString());
	}
	// Function to test for Positive Integers.
	public static boolean IsNaturalNumber(Object source)
	{
		if (Rasad.Core.Extensions.Validate.IsNull(source) || Rasad.Core.Extensions.Validate.IsDBNull(source) || source.toString().length() == 0)
		{
			return false;
		}
		Regex objNotNaturalPattern = new Regex("[^0-9]");
		Regex objNaturalPattern = new Regex("0*[1-9][0-9]*");
		return !objNotNaturalPattern.IsMatch(source.toString()) && objNaturalPattern.IsMatch(source.toString());
	}
	// Function to test for Positive Integers with zero inclusive
	public static boolean IsWholeNumber(Object source)
	{
		if (Rasad.Core.Extensions.Validate.IsNull(source) || Rasad.Core.Extensions.Validate.IsDBNull(source) || source.toString().length() == 0)
		{
			return false;
		}
		Regex objNotWholePattern = new Regex("[^0-9]");
		return !objNotWholePattern.IsMatch(source.toString());
	}
	// Function to Test for Integers both Positive & Negative
	public static boolean IsInteger(Object source)
	{
		if (Rasad.Core.Extensions.Validate.IsNull(source) || Rasad.Core.Extensions.Validate.IsDBNull(source) || source.toString().length() == 0)
		{
			return false;
		}
		Regex objNotIntPattern = new Regex("[^0-9-]");
		Regex objIntPattern = new Regex("^-[0-9]+$|^[0-9]+$");
		return !objNotIntPattern.IsMatch(source.toString()) && objIntPattern.IsMatch(source.toString());
	}
	// Function to Test for Positive Number both Integer & Real
	public static boolean IsPositiveNumber(Object source)
	{
		if (Rasad.Core.Extensions.Validate.IsNull(source) || Rasad.Core.Extensions.Validate.IsDBNull(source) || source.toString().length() == 0)
		{
			return false;
		}
		Regex objNotPositivePattern = new Regex("[^0-9.]");
		Regex objPositivePattern = new Regex("^[.][0-9]+$|[0-9]*[.]*[0-9]+$");
		Regex objTwoDotPattern = new Regex("[0-9]*[.][0-9]*[.][0-9]*");
		return !objNotPositivePattern.IsMatch(source.toString()) && objPositivePattern.IsMatch(source.toString()) && !objTwoDotPattern.IsMatch(source.toString());
	}
	// Function to test whether the string is valid number or not
	public static boolean IsNumber(Object source)
	{
		if (Rasad.Core.Extensions.Validate.IsNull(source) || Rasad.Core.Extensions.Validate.IsDBNull(source) || source.toString().length() == 0)
		{
			return false;
		}
		Regex objNotNumberPattern = new Regex("[^0-9.-]");
		Regex objTwoDotPattern = new Regex("[0-9]*[.][0-9]*[.][0-9]*");
		Regex objTwoMinusPattern = new Regex("[0-9]*[-][0-9]*[-][0-9]*");
		String strValidRealPattern = "^([-]|[.]|[-.]|[0-9])[0-9]*[.]*[0-9]+$";
		String strValidIntegerPattern = "^([-]|[0-9])[0-9]*$";
		Regex objNumberPattern = new Regex("(" + strValidRealPattern + ")|(" + strValidIntegerPattern + ")");
		return !objNotNumberPattern.IsMatch(source.toString()) && !objTwoDotPattern.IsMatch(source.toString()) && !objTwoMinusPattern.IsMatch(source.toString()) && objNumberPattern.IsMatch(source.toString());
	}
	// Function To test for Alphabets.
	public static boolean IsAlpha(Object source)
	{
		if (Rasad.Core.Extensions.Validate.IsNull(source) || Rasad.Core.Extensions.Validate.IsDBNull(source) || source.toString().length() == 0)
		{
			return false;
		}
		Regex objAlphaPattern = new Regex("[^a-zA-Z]");
		return !objAlphaPattern.IsMatch(source.toString());
	}
	// Function to Check for AlphaNumeric.
	public static boolean IsAlphaNumeric(Object source)
	{
		if (Rasad.Core.Extensions.Validate.IsNull(source) || Rasad.Core.Extensions.Validate.IsDBNull(source) || source.toString().length() == 0)
		{
			return false;
		}
		Regex objAlphaNumericPattern = new Regex("[^a-zA-Z0-9]");
		return !objAlphaNumericPattern.IsMatch(source.toString());
	}
	public static boolean IsDBNull(Object source)
	{
		return Convert.IsDBNull(source);
	}
	public static boolean IsNull(Object source)
	{
		return source == null;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}