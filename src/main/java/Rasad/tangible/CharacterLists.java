package tangible;

//----------------------------------------------------------------------------------------
//	Copyright © 2007 - 2018 Tangible Software Solutions, Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class is used to convert between Character Lists and char arrays.
//----------------------------------------------------------------------------------------
public final class CharacterLists
{
	public static char[] toArray(java.util.List<Character> list)
	{
		char[] array = new char[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			array[i] = list.get(i).charValue();
		}
		return array;
	}

	public static void addPrimitiveArrayToList(char[] array, java.util.List<Character> list)
	{
		for (int i = 0; i < array.length; i++)
		{
			list.add(array[i]);
		}
	}

	public static void addPrimitiveArrayToList(int index, char[] array, java.util.List<Character> list)
	{
		for (int i = 0; i < array.length; i++)
		{
			list.add(index + i, array[i]);
		}
	}
}