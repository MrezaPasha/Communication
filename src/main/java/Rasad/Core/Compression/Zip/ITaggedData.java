package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

//
// ZipExtraData.cs
//
// Copyright 2004-2007 John Reilly
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



// TODO: Sort out wether tagged data is useful and what a good implementation might look like.
// Its just a sketch of an idea at the moment.

/** 
 ExtraData tagged value interface.
*/
public interface ITaggedData
{
	/** 
	 Get the ID for this tagged data value.
	*/
	short getTagID();

	/** 
	 Set the contents of this instance from the data passed.
	 
	 @param data The data to extract contents from.
	 @param offset The offset to begin extracting data from.
	 @param count The number of bytes to extract.
	*/
	void SetData(byte[] data, int offset, int count);

	/** 
	 Get the data representing this instance.
	 
	 @return Returns the data for this instance.
	*/
	byte[] GetData();
}