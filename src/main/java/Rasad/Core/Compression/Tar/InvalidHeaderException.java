package Rasad.Core.Compression.Tar;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

// InvalidHeaderException.cs
//
// Copyright (C) 2001 Mike Krueger
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


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0 && !NETCF_2_0
//#endif



/** 
 This exception is used to indicate that there is a problem
 with a TAR archive header.
*/
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0 && !NETCF_2_0

//#endif
public class InvalidHeaderException extends TarException
{

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !NETCF_1_0 && !NETCF_2_0
	/** 
	 Deserialization constructor 
	 
	 @param information <see cref="SerializationInfo"/> for this constructor
	 @param context <see cref="StreamingContext"/> for this constructor
	*/
	protected InvalidHeaderException(SerializationInfo information, StreamingContext context)

	{
		super(information, context);
	}
//#endif

	/** 
	 Initialise a new instance of the InvalidHeaderException class.
	*/
	public InvalidHeaderException()
	{
	}

	/** 
	 Initialises a new instance of the InvalidHeaderException class with a specified message.
	 
	 @param message Message describing the exception cause.
	*/
	public InvalidHeaderException(String message)
	{
		super(message);
	}

	/** 
	 Initialise a new instance of InvalidHeaderException
	 
	 @param message Message describing the problem.
	 @param exception The exception that is the cause of the current exception.
	*/
	public InvalidHeaderException(String message, RuntimeException exception)
	{
		super(message, exception);
	}
}
/* The original Java file had this header:
** Authored by Timothy Gerard Endres
** <mailto:time@gjt.org>  <http: //www.trustice.com>
** 
** This work has been placed into the public domain.
** You may use this work in any way and for any purpose you wish.
**
** THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
** NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
** OF THIS SOFTWARE, ASSUMES _NO_ RESPONSIBILITY FOR ANY
** CONSEQUENCE RESULTING FROM THE USE, MODIFICATION, OR
** REDISTRIBUTION OF THIS SOFTWARE. 
** 
*/

