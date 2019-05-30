package Rasad.auxilary;

//----------------------------------------------------------------------------------------
//	Copyright © 2007 - 2018 Tangible Software Solutions, Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class is used to convert between Byte Lists and byte arrays.
//----------------------------------------------------------------------------------------
public final class ByteLists
{
	public static byte[] toArray(java.util.List<Byte> list)
	{
		byte[] array = new byte[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i).byteValue();
		}
		return array;
	}

	public static void addPrimitiveArrayToList(byte[] array, java.util.List<Byte> list)
	{
		for (int i = 0; i < array.length; i++)
		{
			list.add(array[i]);
		}
	}

	public static void addPrimitiveArrayToList(int index, byte[] array, java.util.List<Byte> list)
	{
		for (int i = 0; i < array.length; i++)
		{
			list.add(index + i, array[i]);
		}
	}
}