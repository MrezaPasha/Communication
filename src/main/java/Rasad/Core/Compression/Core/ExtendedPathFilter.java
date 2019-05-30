package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

/** 
 ExtendedPathFilter filters based on name, file size, and the last write time of the file.
 
 Provides an example of how to customise filtering.
*/
public class ExtendedPathFilter extends Rasad.Core.Compression.Core.PathFilter
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors
	/** 
	 Initialise a new instance of ExtendedPathFilter.
	 
	 @param filter The filter to apply.
	 @param minSize The minimum file size to include.
	 @param maxSize The maximum file size to include.
	*/
	public ExtendedPathFilter(String filter, long minSize, long maxSize)
	{
		super(filter);
		setMinSize(minSize);
		setMaxSize(maxSize);
	}

	/** 
	 Initialise a new instance of ExtendedPathFilter.
	 
	 @param filter The filter to apply.
	 @param minDate The minimum <see cref="DateTime"/> to include.
	 @param maxDate The maximum <see cref="DateTime"/> to include.
	*/
	public ExtendedPathFilter(String filter, LocalDateTime minDate, LocalDateTime maxDate)
	{
		super(filter);
		setMinDate(minDate);
		setMaxDate(maxDate);
	}

	/** 
	 Initialise a new instance of ExtendedPathFilter.
	 
	 @param filter The filter to apply.
	 @param minSize The minimum file size to include.
	 @param maxSize The maximum file size to include.
	 @param minDate The minimum <see cref="DateTime"/> to include.
	 @param maxDate The maximum <see cref="DateTime"/> to include.
	*/
	public ExtendedPathFilter(String filter, long minSize, long maxSize, LocalDateTime minDate, LocalDateTime maxDate)
	{
		super(filter);
		setMinSize(minSize);
		setMaxSize(maxSize);
		setMinDate(minDate);
		setMaxDate(maxDate);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region IScanFilter Members
	/** 
	 Test a filename to see if it matches the filter.
	 
	 @param name The filename to test.
	 @return True if the filter matches, false otherwise.
	 @exception System.IO.FileNotFoundException The <see paramref="fileName"/> doesnt exist
	*/
	@Override
	public boolean IsMatch(String name)
	{
		boolean result = super.IsMatch(name);

		if (result)
		{
			File fileInfo = new File(name);
			result = (getMinSize() <= fileInfo.length()) && (getMaxSize() >= fileInfo.length()) && (getMinDate().compareTo(fileInfo.LastWriteTime) <= 0) && (getMaxDate().compareTo(fileInfo.LastWriteTime) >= 0);
		}
		return result;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	/** 
	 Get/set the minimum size/length for a file that will match this filter.
	 
	 The default value is zero.
	 @exception ArgumentOutOfRangeException value is less than zero; greater than <see cref="MaxSize"/>
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
	 Get/set the maximum size/length for a file that will match this filter.
	 
	 The default value is <see cref="System.Int64.MaxValue"/>
	 @exception ArgumentOutOfRangeException value is less than zero or less than <see cref="MinSize"/>
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

	/** 
	 Get/set the minimum <see cref="DateTime"/> value that will match for this filter.
	 
	 Files with a LastWrite time less than this value are excluded by the filter.
	*/
	public final LocalDateTime getMinDate()
	{
		return minDate_;
	}

	public final void setMinDate(LocalDateTime value)
	{
		if (value.compareTo(maxDate_) > 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("value");
//#else
			throw new IndexOutOfBoundsException("value", "Exceeds MaxDate");
//#endif
		}

		minDate_ = value;
	}

	/** 
	 Get/set the maximum <see cref="DateTime"/> value that will match for this filter.
	 
	 Files with a LastWrite time greater than this value are excluded by the filter.
	*/
	public final LocalDateTime getMaxDate()
	{
		return maxDate_;
	}

	public final void setMaxDate(LocalDateTime value)
	{
		if (minDate_.compareTo(value) > 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if NETCF_1_0
			throw new IndexOutOfBoundsException("value");
//#else
			throw new IndexOutOfBoundsException("value", "Exceeds MinDate");
//#endif
		}

		maxDate_ = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private long minSize_;
	private long maxSize_ = Long.MAX_VALUE;
	private LocalDateTime minDate_ = LocalDateTime.MIN;
	private LocalDateTime maxDate_ = LocalDateTime.MAX;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}