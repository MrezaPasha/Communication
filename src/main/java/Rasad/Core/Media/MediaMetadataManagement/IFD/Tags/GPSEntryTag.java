package Rasad.Core.Media.MediaMetadataManagement.IFD.Tags;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import Rasad.Core.Media.MediaMetadataManagement.IFD.*;

//
// GPSEntryTag.cs:
//
// Author:
//   Ruben Vermeersch (ruben@savanne.be)
//   Mike Gemuende (mike@gemuende.de)
//
// Copyright (C) 2009-2010 Ruben Vermeersch
// Copyright (C) 2009 Mike Gemuende
//
// This library is free software; you can redistribute it and/or modify
// it  under the terms of the GNU Lesser General Public License version
// 2.1 as published by the Free Software Foundation.
//
// This library is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
// USA
//


/** 
	Entry tags occuring in the GPS IFD
	The complete overview can be obtained at:
	http: //www.awaresystems.be/imaging/tiff.html
*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public enum GPSEntryTag : ushort
public enum GPSEntryTag 
{

	/** 
		 Indicates the version of GPSInfoIFD. (Hex: 0x0000)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsversionid.html
	*/
	GPSVersionID((short)0),

	/** 
		 Indicates whether the latitude is north or south latitude. (Hex: 0x0001)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpslatituderef.html
	*/
	GPSLatitudeRef((short)1),

	/** 
		 Indicates the latitude. (Hex: 0x0002)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpslatitude.html
	*/
	GPSLatitude((short)2),

	/** 
		 Indicates whether the longitude is east or west longitude. (Hex: 0x0003)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpslongituderef.html
	*/
	GPSLongitudeRef((short)3),

	/** 
		 Indicates the longitude. (Hex: 0x0004)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpslongitude.html
	*/
	GPSLongitude((short)4),

	/** 
		 Indicates the altitude used as the reference altitude. (Hex: 0x0005)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsaltituderef.html
	*/
	GPSAltitudeRef((short)5),

	/** 
		 Indicates the altitude based on the reference in GPSAltitudeRef. (Hex: 0x0006)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsaltitude.html
	*/
	GPSAltitude((short)6),

	/** 
		 Indicates the time as UTC (Coordinated Universal Time). (Hex: 0x0007)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpstimestamp.html
	*/
	GPSTimeStamp((short)7),

	/** 
		 Indicates the GPS satellites used for measurements. (Hex: 0x0008)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpssatellites.html
	*/
	GPSSatellites((short)8),

	/** 
		 Indicates the status of the GPS receiver when the image is recorded. (Hex: 0x0009)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsstatus.html
	*/
	GPSStatus((short)9),

	/** 
		 Indicates the GPS measurement mode. (Hex: 0x000A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsmeasuremode.html
	*/
	GPSMeasureMode((short)10),

	/** 
		 Indicates the GPS DOP (data degree of precision). (Hex: 0x000B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdop.html
	*/
	GPSDOP((short)11),

	/** 
		 Indicates the unit used to express the GPS receiver speed of movement. (Hex: 0x000C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsspeedref.html
	*/
	GPSSpeedRef((short)12),

	/** 
		 Indicates the speed of GPS receiver movement. (Hex: 0x000D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsspeed.html
	*/
	GPSSpeed((short)13),

	/** 
		 Indicates the reference for giving the direction of GPS receiver movement. (Hex: 0x000E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpstrackref.html
	*/
	GPSTrackRef((short)14),

	/** 
		 Indicates the direction of GPS receiver movement. (Hex: 0x000F)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpstrack.html
	*/
	GPSTrack((short)15),

	/** 
		 Indicates the reference for giving the direction of the image when it is captured. (Hex: 0x0010)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsimgdirectionref.html
	*/
	GPSImgDirectionRef((short)16),

	/** 
		 Indicates the direction of the image when it was captured. (Hex: 0x0011)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsimgdirection.html
	*/
	GPSImgDirection((short)17),

	/** 
		 Indicates the geodetic survey data used by the GPS receiver. (Hex: 0x0012)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsmapdatum.html
	*/
	GPSMapDatum((short)18),

	/** 
		 Indicates whether the latitude of the destination point is north or south latitude. (Hex: 0x0013)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdestlatituderef.html
	*/
	GPSDestLatitudeRef((short)19),

	/** 
		 Indicates the latitude of the destination point. (Hex: 0x0014)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdestlatitude.html
	*/
	GPSDestLatitude((short)20),

	/** 
		 Indicates whether the longitude of the destination point is east or west longitude. (Hex: 0x0015)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdestlongituderef.html
	*/
	GPSDestLongitudeRef((short)21),

	/** 
		 Indicates the longitude of the destination point. (Hex: 0x0016)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdestlongitude.html
	*/
	GPSDestLongitude((short)22),

	/** 
		 Indicates the reference used for giving the bearing to the destination point. (Hex: 0x0017)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdestbearingref.html
	*/
	GPSDestBearingRef((short)23),

	/** 
		 Indicates the bearing to the destination point. (Hex: 0x0018)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdestbearing.html
	*/
	GPSDestBearing((short)24),

	/** 
		 Indicates the unit used to express the distance to the destination point. (Hex: 0x0019)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdestdistanceref.html
	*/
	GPSDestDistanceRef((short)25),

	/** 
		 Indicates the distance to the destination point. (Hex: 0x001A)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdestdistance.html
	*/
	GPSDestDistance((short)26),

	/** 
		 A character string recording the name of the method used for location finding. (Hex: 0x001B)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsprocessingmethod.html
	*/
	GPSProcessingMethod((short)27),

	/** 
		 A character string recording the name of the GPS area. (Hex: 0x001C)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsareainformation.html
	*/
	GPSAreaInformation((short)28),

	/** 
		 A character string recording date and time information relative to UTC (Coordinated Universal Time). (Hex: 0x001D)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdatestamp.html
	*/
	GPSDateStamp((short)29),

	/** 
		 Indicates whether differential correction is applied to the GPS receiver. (Hex: 0x001E)
		 http://www.awaresystems.be/imaging/tiff/tifftags/privateifd/gps/gpsdifferential.html
	*/
	GPSDifferential((short)30);

	public static final int SIZE = java.lang.Short.SIZE;

	private short shortValue;
	private static java.util.HashMap<Short, GPSEntryTag> mappings;
	private static java.util.HashMap<Short, GPSEntryTag> getMappings()
	{
		if (mappings == null)
		{
			synchronized (GPSEntryTag.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Short, GPSEntryTag>();
				}
			}
		}
		return mappings;
	}

	private GPSEntryTag(short value)
	{
		shortValue = value;
		getMappings().put(value, this);
	}

	public short getValue()
	{
		return shortValue;
	}

	public static GPSEntryTag forValue(short value)
	{
		return getMappings().get(value);
	}
}