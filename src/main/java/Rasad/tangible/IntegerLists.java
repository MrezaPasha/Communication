package tangible;

//----------------------------------------------------------------------------------------
//	Copyright © 2007 - 2018 Tangible Software Solutions, Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class is used to convert between Integer Lists and int arrays.
//----------------------------------------------------------------------------------------
public final class IntegerLists
{
	public static int[] toArray(java.util.List<Integer> list)
	{
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i).intValue();
		}
		return array;
	}

	public static void addPrimitiveArrayToList(int[] array, java.util.List<Integer> list)
	{
		for (int i = 0; i < array.length; i++)
		{
			list.add(array[i]);
		}
	}

	public static void addPrimitiveArrayToList(int index, int[] array, java.util.List<Integer> list)
	{
		for (int i = 0; i < array.length; i++)
		{
			list.add(index + i, array[i]);
		}
	}
}