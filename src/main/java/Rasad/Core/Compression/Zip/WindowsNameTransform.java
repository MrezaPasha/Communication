package Rasad.Core.Compression.Zip;

import Rasad.Core.Compression.Core.*;
import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.nio.file.*;

// WindowsNameTransform.cs
//
// Copyright 2007 John Reilly
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
 WindowsNameTransform transforms <see cref="ZipFile"/> names to windows compatible ones.
*/
public class WindowsNameTransform implements INameTransform
{
	/** 
	 Initialises a new instance of <see cref="WindowsNameTransform"/>
	 
	 @param baseDirectory
	*/
	public WindowsNameTransform(String baseDirectory)
	{
		if (baseDirectory == null)
		{
			throw new NullPointerException("baseDirectory", "Directory name is invalid");
		}

		setBaseDirectory(baseDirectory);
	}

	/** 
	 Initialise a default instance of <see cref="WindowsNameTransform"/>
	*/
	public WindowsNameTransform()
	{
		// Do nothing.
	}

	/** 
	 Gets or sets a value containing the target directory to prefix values with.
	*/
	public final String getBaseDirectory()
	{
		return _baseDirectory;
	}
	public final void setBaseDirectory(String value)
	{
		if (value == null)
		{
			throw new NullPointerException("value");
		}

		_baseDirectory = (new File(value)).getAbsolutePath();
	}

	/** 
	 Gets or sets a value indicating wether paths on incoming values should be removed.
	*/
	public final boolean getTrimIncomingPaths()
	{
		return _trimIncomingPaths;
	}
	public final void setTrimIncomingPaths(boolean value)
	{
		_trimIncomingPaths = value;
	}

	/** 
	 Transform a Zip directory name to a windows directory name.
	 
	 @param name The directory name to transform.
	 @return The transformed name.
	*/
	public final String TransformDirectory(String name)
	{
		name = TransformFile(name);
		if (name.length() > 0)
		{
			while (name.endsWith("\\"))
			{
				name = tangible.StringHelper.remove(name, name.length() - 1, 1);
			}
		}
		else
		{
			throw new ZipException("Cannot have an empty directory name");
		}
		return name;
	}

	/** 
	 Transform a Zip format file name to a windows style one.
	 
	 @param name The file name to transform.
	 @return The transformed name.
	*/
	public final String TransformFile(String name)
	{
		if (name != null)
		{
			name = MakeValidName(name, _replacementChar);

			if (_trimIncomingPaths)
			{
				name = (new File(name)).getName();
			}

			// This may exceed windows length restrictions.
			// Combine will throw a PathTooLongException in that case.
			if (_baseDirectory != null)
			{
				name = Paths.get(_baseDirectory).resolve(name).toString();
			}
		}
		else
		{
			name = "";
		}
		return name;
	}

	/** 
	 Test a name to see if it is a valid name for a windows filename as extracted from a Zip archive.
	 
	 @param name The name to test.
	 @return Returns true if the name is a valid zip name; false otherwise.
	 The filename isnt a true windows path in some fundamental ways like no absolute paths, no rooted paths etc.
	*/
	public static boolean IsValidName(String name)
	{
		boolean result = (name != null) && (name.length() <= MaxPath) && (name.compareTo(MakeValidName(name, '_')) == 0);

		return result;
	}

	/** 
	 Initialise static class information.
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
		int howMany = invalidPathChars.length + 3;

		InvalidEntryChars = new char[howMany];
		System.arraycopy(invalidPathChars, 0, InvalidEntryChars, 0, invalidPathChars.length);
		InvalidEntryChars[howMany - 1] = '*';
		InvalidEntryChars[howMany - 2] = '?';
		InvalidEntryChars[howMany - 3] = ':';
	}

	/** 
	 Force a name to be valid by replacing invalid characters with a fixed value
	 
	 @param name The name to make valid
	 @param replacement The replacement character to use for any invalid characters.
	 @return Returns a valid name
	*/
	public static String MakeValidName(String name, char replacement)
	{
		if (name == null)
		{
			throw new NullPointerException("name");
		}

		name = WindowsPathUtils.DropPathRoot(name.replace("/", "\\"));

		// Drop any leading slashes.
		while ((name.length() > 0) && (name.charAt(0) == '\\'))
		{
			name = tangible.StringHelper.remove(name, 0, 1);
		}

		// Drop any trailing slashes.
		while ((name.length() > 0) && (name.charAt(name.length() - 1) == '\\'))
		{
			name = tangible.StringHelper.remove(name, name.length() - 1, 1);
		}

		// Convert consecutive \\ characters to \
		int index = name.indexOf("\\\\");
		while (index >= 0)
		{
			name = tangible.StringHelper.remove(name, index, 1);
			index = name.indexOf("\\\\");
		}

		// Convert any invalid characters using the replacement one.
		index = tangible.StringHelper.indexOfAny(name, InvalidEntryChars);
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

		// Check for names greater than MaxPath characters.
		// TODO: Were is CLR version of MaxPath defined?  Can't find it in Environment.
		if (name.length() > MaxPath)
		{
			throw new PathTooLongException();
		}

		return name;
	}

	/** 
	 Gets or set the character to replace invalid characters during transformations.
	*/
	public final char getReplacement()
	{
		return _replacementChar;
	}
	public final void setReplacement(char value)
	{
		for (int i = 0; i < InvalidEntryChars.length; ++i)
		{
			if (InvalidEntryChars[i] == value)
			{
				throw new IllegalArgumentException("invalid path character");
			}
		}

		if ((value == '\\') || (value == '/'))
		{
			throw new IllegalArgumentException("invalid replacement character");
		}

		_replacementChar = value;
	}

	/** 
	  The maximum windows path name permitted.
	 
	 This may not valid for all windows systems - CE?, etc but I cant find the equivalent in the CLR.
	*/
	private static final int MaxPath = 260;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private String _baseDirectory;
	private boolean _trimIncomingPaths;
	private char _replacementChar = '_';
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Class Fields
	private static char[] InvalidEntryChars;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}