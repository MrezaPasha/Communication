package Rasad.VideoSurveillance.Core;

public enum MapContentType
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("شهر")] City = 0,
	City(0),

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Description("ساختمان (عمومی)")] GeneralBuilding = 0,
	GeneralBuilding(0);

	//[Description("خانه ویلایی")]
	//House = 1,

	//[Description("آپارتمان")]
	//Apartment = 2,

	public static final int SIZE = java.lang.Integer.SIZE;

	private int intValue;
	private static java.util.HashMap<Integer, MapContentType> mappings;
	private static java.util.HashMap<Integer, MapContentType> getMappings()
	{
		if (mappings == null)
		{
			synchronized (MapContentType.class)
			{
				if (mappings == null)
				{
					mappings = new java.util.HashMap<Integer, MapContentType>();
				}
			}
		}
		return mappings;
	}

	private MapContentType(int value)
	{
		intValue = value;
		getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static MapContentType forValue(int value)
	{
		return getMappings().get(value);
	}
}