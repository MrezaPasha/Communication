package main.java.Rasad.Core.Services.NotificationService.Structs;

import Rasad.Core.*;
import Rasad.Core.Services.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TRequestConnectionPingStruct
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Explicit)] public struct TRequestConnectionPingStruct
public final class TRequestConnectionPingStruct
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [FieldOffset(0)] private byte dummy;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [FieldOffset(0)] private byte dummy;
	private byte dummy;

	//  ** Always include a parameter-less consutructor for this class, even if be private
	//public TRequestConnectionPingStruct()
	//{
	//    this.dummy = 0;
	//}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte getDummy()
	public byte getDummy()
	{
		return dummy;
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public void setDummy(byte value)
	public void setDummy(byte value)
	{
		dummy = value;
	}

	@Override
	public String toString()
	{
		return "Ping Request";
	}

	public TRequestConnectionPingStruct clone()
	{
		TRequestConnectionPingStruct varCopy = new TRequestConnectionPingStruct();

		varCopy.dummy = this.dummy;

		return varCopy;
	}
}