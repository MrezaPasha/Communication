package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

/** 
 NameAndSizeFilter filters based on name and file size.
 
 A sample showing how filters might be extended.
*/
@Deprecated
public class NameAndSizeFilter extends PathFilter
{

	/** 
	 Initialise a new instance of NameAndSizeFilter.
	 
	 @param filter The filter to apply.
	 @param minSize The minimum file size to include.
	 @param maxSize The maximum file size to include.
	*/
	public NameAndSizeFilter(String filter, long minSize, long maxSize)
	{
		super(filter);
		setMinSize(minSize);
		setMaxSize(maxSize);
	}

	/** 
	 Test a filename to see if it matches the filter.
	 
	 @param name The filename to test.
	 @return True if the filter matches, false otherwise.
	*/
	@Override
	public boolean IsMatch(String name)
	{
		boolean result = super.IsMatch(name);

		if (result)
		{
			File fileInfo = new File(name);
			long length = fileInfo.length();
			result = (getMinSize() <= length) && (getMaxSize() >= length);
		}
		return result;
	}

	/** 
	 Get/set the minimum size for a file that will match this filter.
	*/
	public final long getMinSize()
	{
		return minSize_;
	}
	public final void setMinSize(long value)
	{
		if ((value < 0) || (maxSize_ < value))
		{
			throw new IndexOutOfBoundsException("value");
		}

		minSize_ = value;
	}

	/** 
	 Get/set the maximum size for a file that will match this filter.
	*/
	public final long getMaxSize()
	{
		return maxSize_;
	}
	public final void setMaxSize(long value)
	{
		if ((value < 0) || (minSize_ > value))
		{
			throw new IndexOutOfBoundsException("value");
		}

		maxSize_ = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private long minSize_;
	private long maxSize_ = Long.MAX_VALUE;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}