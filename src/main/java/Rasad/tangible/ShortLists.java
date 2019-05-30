package tangible;

//----------------------------------------------------------------------------------------
//	Copyright © 2007 - 2018 Tangible Software Solutions, Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class is used to convert between Short Lists and short arrays.
//----------------------------------------------------------------------------------------
public final class ShortLists
{
	public static short[] toArray(java.util.List<Short> list)
	{
		short[] array = new short[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i).shortValue();
		}
		return array;
	}

	public static void addPrimitiveArrayToList(short[] array, java.util.List<Short> list)
	{
		for (int i = 0; i < array.length; i++)
		{
			list.add(array[i]);
		}
	}

	public static void addPrimitiveArrayToList(int index, short[] array, java.util.List<Short> list)
	{
		for (int i = 0; i < array.length; i++)
		{
			list.add(index + i, array[i]);
		}
	}
}