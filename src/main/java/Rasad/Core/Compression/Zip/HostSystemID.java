package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.nio.file.*;
import java.time.*;

// ZipEntry.cs
//
// Copyright (C) 2001 Mike Krueger
// Copyright (C) 2004 John Reilly
//
// This file was translated from java, it was part of the GNU Classpath
// Copyright (C) 2001 Free Software Foundation, Inc.
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
//	2009-12-22	Z-1649	Added AES support
//	2010-02-02	DavidP	Changed NTFS Extra Data min length to 4
//	2012-06-03	Z-1744	Use only the low order byte of "Version Needed to Extract"
//	2012-07-18	Z-1676	Translate to forward slashes and remove drive from name in constructor




/** 
 Defines known values for the <see cref="HostSystemID"/> property.
*/
public enum HostSystemID
{
	/** 
	 Host system = MSDOS
	*/
	Msdos(0),
	/** 
	 Host system = Amiga
	*/
	Amiga(1),
	/** 
	 Host system = Open VMS
	*/
	OpenVms(2),
	/** 
	 Host system = Unix
	*/
	Unix(3),
	/** 
	 Host system = VMCms
	*/
	VMCms(4),
	/** 
	 Host system = Atari ST
	*/
	AtariST(5),
	/** 
	 Host system = OS2
	*/
	OS2(6),
	/** 
	 Host system = Macintosh
	*/
	Macintosh(7),
	/** 
	 Host system = ZSystem
	*/
	ZSystem(8),
	/** 
	 Host system = Cpm
	*/
	Cpm(9),
	/** 
	 Host system = Windows NT
	*/
	WindowsNT(10),
	/** 
	 Host system = MVS
	*/
	MVS(11),
	/** 
	 Host system = VSE
	*/
	Vse(12),
	/** 
	 Host system = Acorn RISC
	*/
	AcornRisc(13),
	/** 
	 Host system = VFAT
	*/
	Vfat(14),
	/** 
	 Host system = Alternate MVS
	*/
	AlternateMvs(15),
	/** 
	 Host system = BEOS
	*/
	BeOS(16),
	/** 
	 Host system = Tandem
	*/
	Tandem(17),
	/** 
	 Host system = OS400
	*/
	OS400(18),
	/** 
	 Host system = OSX
	*/
	OSX(19),
	/** 
	 Host system = WinZIP AES
	*/
	WinZipAES(99);

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, HostSystemID> mappings;
	private static java.util.HashMap<Integer, HostSystemID> getMappings()
	{
		if (mappings == null)
		{
			synchronized (HostSystemID.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, HostSystemID>();
				}
			}
		}
		return mappings;
	}

	private HostSystemID(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static HostSystemID forValue(int value)
	{
		return getMappings().get(value);
	}
}