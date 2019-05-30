package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Core.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;

// ZipNameTransform.cs
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





/** 
 ZipNameTransform transforms names as per the Zip file naming convention.
 
 The use of absolute names is supported although its use is not valid 
 according to Zip naming conventions, and should not be used if maximum compatability is desired.
*/
public class ZipNameTransform implements INameTransform
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialize a new instance of <see cref="ZipNameTransform"></see>
	*/
	public ZipNameTransform()
	{
	}

	/** 
	 Initialize a new instance of <see cref="ZipNameTransform"></see>
	 
	 @param trimPrefix The string to trim from the front of paths if found.
	*/
	public ZipNameTransform(String trimPrefix)
	{
		setTrimPrefix(trimPrefix);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
	 Static constructor.
	*/
	static
	{
		char[] invalidPathChars;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NET_1_0 || NET_1_1 || NETCF_1_0
		invalidPathChars = Path.InvalidPathChars;
//#else
		invalidPathChars = Path.GetInvalidPathChars();
//#endif
		int howMany = invalidPathChars.length + 2;

		InvalidEntryCharsRelaxed = new char[howMany];
		System.arraycopy(invalidPathChars, 0, InvalidEntryCharsRelaxed, 0, invalidPathChars.length);
		InvalidEntryCharsRelaxed[howMany - 1] = '*';
		InvalidEntryCharsRelaxed[howMany - 2] = '?';

		howMany = invalidPathChars.length + 4;
		InvalidEntryChars = new char[howMany];
		System.arraycopy(invalidPathChars, 0, InvalidEntryChars, 0, invalidPathChars.length);
		InvalidEntryChars[howMany - 1] = ':';
		InvalidEntryChars[howMany - 2] = '\\';
		InvalidEntryChars[howMany - 3] = '*';
		InvalidEntryChars[howMany - 4] = '?';
	}

	/** 
	 Transform a windows directory name according to the Zip file naming conventions.
	 
	 @param name The directory name to transform.
	 @return The transformed name.
	*/
	public final String TransformDirectory(String name)
	{
		name = TransformFile(name);
		if (name.length() > 0)
		{
			if (!name.endsWith("/"))
			{
				name += "/";
			}
		}
		else
		{
			throw new ZipException("Cannot have an empty directory name");
		}
		return name;
	}

	/** 
	 Transform a windows file name according to the Zip file naming conventions.
	 
	 @param name The file name to transform.
	 @return The transformed name.
	*/
	public final String TransformFile(String name)
	{
		if (name != null)
		{
			String lowerName = name.toLowerCase();
			if ((trimPrefix_ != null) && (lowerName.indexOf(trimPrefix_) == 0))
			{
				name = name.substring(trimPrefix_.length());
			}

			name = name.replace("\\", "/");
			name = WindowsPathUtils.DropPathRoot(name);

			// Drop any leading slashes.
			while ((name.length() > 0) && (name.charAt(0) == '/'))
			{
				name = tangible.StringHelper.remove(name, 0, 1);
			}

			// Drop any trailing slashes.
			while ((name.length() > 0) && (name.charAt(name.length() - 1) == '/'))
			{
				name = tangible.StringHelper.remove(name, name.length() - 1, 1);
			}

			// Convert consecutive // characters to /
			int index = name.indexOf("//");
			while (index >= 0)
			{
				name = tangible.StringHelper.remove(name, index, 1);
				index = name.indexOf("//");
			}

			name = MakeValidName(name, '_');
		}
		else
		{
			name = "";
		}
		return name;
	}

	/** 
	 Get/set the path prefix to be trimmed from paths if present.
	 
	 The prefix is trimmed before any conversion from
	 a windows path is done.
	*/
	public final String getTrimPrefix()
	{
		return trimPrefix_;
	}
	public final void setTrimPrefix(String value)
	{
		trimPrefix_ = value;
		if (trimPrefix_ != null)
		{
			trimPrefix_ = trimPrefix_.toLowerCase();
		}
	}

	/** 
	 Force a name to be valid by replacing invalid characters with a fixed value
	 
	 @param name The name to force valid
	 @param replacement The replacement character to use.
	 @return Returns a valid name
	*/
	private static String MakeValidName(String name, char replacement)
	{
		int index = tangible.StringHelper.indexOfAny(name, InvalidEntryChars);
		if (index >= 0)
		{
			StringBuilder builder = new StringBuilder(name);

			while (index >= 0)
			{
				builder.setCharAt(index, replacement);

				if (index >= name.length())
				{
					index = -1;
				}
				else
				{
					index = tangible.StringHelper.indexOfAny(name, InvalidEntryChars, index + 1);
				}
			}
			name = builder.toString();
		}

		if (name.length() > 0xffff)
		{
			throw new PathTooLongException();
		}

		return name;
	}

	/** 
	 Test a name to see if it is a valid name for a zip entry.
	 
	 @param name The name to test.
	 @param relaxed If true checking is relaxed about windows file names and absolute paths.
	 @return Returns true if the name is a valid zip name; false otherwise.
	 Zip path names are actually in Unix format, and should only contain relative paths.
	 This means that any path stored should not contain a drive or
	 device letter, or a leading slash.  All slashes should forward slashes '/'.
	 An empty name is valid for a file where the input comes from standard input.
	 A null name is not considered valid.
	 
	*/
	public static boolean IsValidName(String name, boolean relaxed)
	{
		boolean result = (name != null);

		if (result)
		{
			if (relaxed)
			{
				result = tangible.StringHelper.indexOfAny(name, InvalidEntryCharsRelaxed) < 0;
			}
			else
			{
				result = (tangible.StringHelper.indexOfAny(name, InvalidEntryChars) < 0) && (name.indexOf('/') != 0);
			}
		}

		return result;
	}

	/** 
	 Test a name to see if it is a valid name for a zip entry.
	 
	 @param name The name to test.
	 @return Returns true if the name is a valid zip name; false otherwise.
	 Zip path names are actually in unix format,
	 and should only contain relative paths if a path is present.
	 This means that the path stored should not contain a drive or
	 device letter, or a leading slash.  All slashes should forward slashes '/'.
	 An empty name is valid where the input comes from standard input.
	 A null name is not considered valid.
	 
	*/
	public static boolean IsValidName(String name)
	{
		boolean result = (name != null) && (tangible.StringHelper.indexOfAny(name, InvalidEntryChars) < 0) && (name.indexOf('/') != 0);
		return result;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private String trimPrefix_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Class Fields
	private static char[] InvalidEntryChars;
	private static char[] InvalidEntryCharsRelaxed;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}