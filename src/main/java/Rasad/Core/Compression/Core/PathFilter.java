package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

// PathFilter.cs
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
 PathFilter filters directories and files using a form of <see cref="System.Text.RegularExpressions.Regex">regular expressions</see>
 by full path name.
 See <see cref="NameFilter">NameFilter</see> for more detail on filtering.
*/
public class PathFilter implements IScanFilter
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a new instance of <see cref="PathFilter"></see>.
	 
	 @param filter The <see cref="NameFilter">filter</see> expression to apply.
	*/
	public PathFilter(String filter)
	{
		nameFilter_ = new NameFilter(filter);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IScanFilter Members
	/** 
	 Test a name to see if it matches the filter.
	 
	 @param name The name to test.
	 @return True if the name matches, false otherwise.
	 <see cref="Path.GetFullPath(string)"/> is used to get the full path before matching.
	*/
	public boolean IsMatch(String name)
	{
		boolean result = false;

		if (name != null)
		{
			String cooked = (name.length() > 0) ? (new File(name)).getAbsolutePath() : "";
			result = nameFilter_.IsMatch(cooked);
		}
		return result;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private NameFilter nameFilter_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}