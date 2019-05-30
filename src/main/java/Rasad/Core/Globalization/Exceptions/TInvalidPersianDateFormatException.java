package Rasad.Core.Globalization.Exceptions;

import Rasad.Core.*;
import Rasad.Core.Globalization.*;

public class TInvalidPersianDateFormatException extends RuntimeException
{
	public TInvalidPersianDateFormatException(String message)
	{
		super(message);
	}

	public TInvalidPersianDateFormatException()
	{
		super();
	}
}