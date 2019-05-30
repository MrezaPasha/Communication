package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

// WindowsPathUtils.cs
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
 WindowsPathUtils provides simple utilities for handling windows paths.
*/
public abstract class WindowsPathUtils
{
	/** 
	 Initializes a new instance of the <see cref="WindowsPathUtils"/> class.
	*/
	public WindowsPathUtils()
	{
	}

	/** 
	 Remove any path root present in the path
	 
	 @param path A <see cref="string"/> containing path information.
	 @return The path with the root removed if it was present; path otherwise.
	 Unlike the <see cref="System.IO.Path"/> class the path isnt otherwise checked for validity.
	*/
	public static String DropPathRoot(String path)
	{
		String result = path;

		if ((path != null) && (path.length() > 0))
		{
			if ((path.charAt(0) == '\\') || (path.charAt(0) == '/'))
			{
				// UNC name ?
				if ((path.length() > 1) && ((path.charAt(1) == '\\') || (path.charAt(1) == '/')))
				{
					int index = 2;
					int elements = 2;

					// Scan for two separate elements \\machine\share\restofpath
					while ((index <= path.length()) && (((path.charAt(index) != '\\') && (path.charAt(index) != '/')) || (--elements > 0)))
					{
						index++;
					}

					index++;

					if (index < path.length())
					{
						result = path.substring(index);
					}
					else
					{
						result = "";
					}
				}
			}
			else if ((path.length() > 1) && (path.charAt(1) == ':'))
			{
				int dropCount = 2;
				if ((path.length() > 2) && ((path.charAt(2) == '\\') || (path.charAt(2) == '/')))
				{
					dropCount = 3;
				}
				result = tangible.StringHelper.remove(result, 0, dropCount);
			}
		}
		return result;
	}
}