package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.util.*;

// NameFilter.cs
//
// Copyright 2005 John Reilly
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// Linking this library statically or dynamically with other modules is
// making a combined work based on this library.  Thus, the terms and
// conditions of the GNU General Public License cover the whole
// combination.
// 
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent
// modules, and to copy and distribute the resulting executable under
// terms of your choice, provided that you also meet, for each linked
// independent module, the terms and conditions of the license of that
// module.  An independent module is a module which is not derived from
// or based on this library.  If you modify this library, you may extend
// this exception to your version of the library, but you are not
// obligated to do so.  If you do not wish to do so, delete this
// exception statement from your version.

// HISTORY
//	2010-03-03	Z-1654	Fixed bug where escape characters were excluded in SplitQuoted()



/** 
 NameFilter is a string matching class which allows for both positive and negative
 matching.
 A filter is a sequence of independant <see cref="Regex">regular expressions</see> separated by semi-colons ';'.
 To include a semi-colon it may be quoted as in \;. Each expression can be prefixed by a plus '+' sign or
 a minus '-' sign to denote the expression is intended to include or exclude names.
 If neither a plus or minus sign is found include is the default.
 A given name is tested for inclusion before checking exclusions.  Only names matching an include spec 
 and not matching an exclude spec are deemed to match the filter.
 An empty filter matches any name.
 
 <example>The following expression includes all name ending in '.dat' with the exception of 'dummy.dat'
 "+\.dat$;-^dummy\.dat$"
 </example>
*/
public class NameFilter implements IScanFilter
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Construct an instance based on the filter expression passed
	 
	 @param filter The filter expression.
	*/
	public NameFilter(String filter)
	{
		filter_ = filter;
		inclusions_ = new ArrayList();
		exclusions_ = new ArrayList();
		Compile();
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Test a string to see if it is a valid regular expression.
	 
	 @param expression The expression to test.
	 @return True if expression is a valid <see cref="System.Text.RegularExpressions.Regex"/> false otherwise.
	*/
	public static boolean IsValidExpression(String expression)
	{
		boolean result = true;
		try
		{
			Regex exp = new Regex(expression, RegexOptions.IgnoreCase.getValue() | RegexOptions.Singleline.getValue());
		}
		catch (IllegalArgumentException e)
		{
			result = false;
		}
		return result;
	}

	/** 
	 Test an expression to see if it is valid as a filter.
	 
	 @param toTest The filter expression to test.
	 @return True if the expression is valid, false otherwise.
	*/
	public static boolean IsValidFilterExpression(String toTest)
	{
		boolean result = true;

		try
		{
			if (toTest != null)
			{
				String[] items = SplitQuoted(toTest);
				for (int i = 0; i < items.length; ++i)
				{
					if ((items[i] != null) && (items[i].length() > 0))
					{
						String toCompile;

						if (items[i].charAt(0) == '+')
						{
							toCompile = items[i].substring(1, items[i].length());
						}
						else if (items[i].charAt(0) == '-')
						{
							toCompile = items[i].substring(1, items[i].length());
						}
						else
						{
							toCompile = items[i];
						}

						Regex testRegex = new Regex(toCompile, RegexOptions.IgnoreCase.getValue() | RegexOptions.Singleline.getValue());
					}
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			result = false;
		}

		return result;
	}

	/** 
	 Split a string into its component pieces
	 
	 @param original The original string
	 @return Returns an array of <see cref="T:System.String"/> values containing the individual filter elements.
	*/
	public static String[] SplitQuoted(String original)
	{
		char escape = '\\';
		char[] separators = {';'};

		ArrayList result = new ArrayList();

		if ((original != null) && (original.length() > 0))
		{
			int endIndex = -1;
			StringBuilder b = new StringBuilder();

			while (endIndex < original.length())
			{
				endIndex += 1;
				if (endIndex >= original.length())
				{
					result.add(b.toString());
				}
				else if (original.charAt(endIndex) == escape)
				{
					endIndex += 1;
					if (endIndex >= original.length())
					{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
						throw new IllegalArgumentException("Missing terminating escape character");
//#else
						throw new IllegalArgumentException("Missing terminating escape character", "original");
//#endif
					}
					// include escape if this is not an escaped separator
					if (Array.indexOf(separators, original.charAt(endIndex)) < 0)
					{
						b.append(escape);
					}

					b.append(original.charAt(endIndex));
				}
				else
				{
					if (Array.indexOf(separators, original.charAt(endIndex)) >= 0)
					{
						result.add(b.toString());
						b.setLength(0);
					}
					else
					{
						b.append(original.charAt(endIndex));
					}
				}
			}
		}

		return (String[])result.toArray(new String[0]);
	}

	/** 
	 Convert this filter to its string equivalent.
	 
	 @return The string equivalent for this filter.
	*/
	@Override
	public String toString()
	{
		return filter_;
	}

	/** 
	 Test a value to see if it is included by the filter.
	 
	 @param name The value to test.
	 @return True if the value is included, false otherwise.
	*/
	public final boolean IsIncluded(String name)
	{
		boolean result = false;
		if (inclusions_.isEmpty())
		{
			result = true;
		}
		else
		{
			for (Regex r : inclusions_)
			{
				if (r.IsMatch(name))
				{
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/** 
	 Test a value to see if it is excluded by the filter.
	 
	 @param name The value to test.
	 @return True if the value is excluded, false otherwise.
	*/
	public final boolean IsExcluded(String name)
	{
		boolean result = false;
		for (Regex r : exclusions_)
		{
			if (r.IsMatch(name))
			{
				result = true;
				break;
			}
		}
		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IScanFilter Members
	/** 
	 Test a value to see if it matches the filter.
	 
	 @param name The value to test.
	 @return True if the value matches, false otherwise.
	*/
	public final boolean IsMatch(String name)
	{
		return (IsIncluded(name) && !IsExcluded(name));
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Compile this filter.
	*/
	private void Compile()
	{
		// TODO: Check to see if combining RE's makes it faster/smaller.
		// simple scheme would be to have one RE for inclusion and one for exclusion.
		if (filter_ == null)
		{
			return;
		}

		String[] items = SplitQuoted(filter_);
		for (int i = 0; i < items.length; ++i)
		{
			if ((items[i] != null) && (items[i].length() > 0))
			{
				boolean include = (items[i].charAt(0) != '-');
				String toCompile;

				if (items[i].charAt(0) == '+')
				{
					toCompile = items[i].substring(1, items[i].length());
				}
				else if (items[i].charAt(0) == '-')
				{
					toCompile = items[i].substring(1, items[i].length());
				}
				else
				{
					toCompile = items[i];
				}

				// NOTE: Regular expressions can fail to compile here for a number of reasons that cause an exception
				// these are left unhandled here as the caller is responsible for ensuring all is valid.
				// several functions IsValidFilterExpression and IsValidExpression are provided for such checking
				if (include)
				{
					inclusions_.add(new Regex(toCompile, RegexOptions.IgnoreCase.getValue() | RegexOptions.Compiled.getValue() | RegexOptions.Singleline.getValue()));
				}
				else
				{
					exclusions_.add(new Regex(toCompile, RegexOptions.IgnoreCase.getValue() | RegexOptions.Compiled.getValue() | RegexOptions.Singleline.getValue()));
				}
			}
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private String filter_;
	private ArrayList inclusions_;
	private ArrayList exclusions_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}