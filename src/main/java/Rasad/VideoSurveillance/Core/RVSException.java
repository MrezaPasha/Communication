package Rasad.VideoSurveillance.Core;

import java.io.*;

public class RVSException extends RuntimeException implements Serializable
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Variables
	//private String message = String.Empty;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Ctor
	public RVSException(SerializationInfo info, StreamingContext context)
	{
		super(info, context);
		this.setErrorCode(RVSErrorCode.forValue((short)info.GetUInt32("ErrorCode")));
	}

	public RVSException(RVSErrorCode errorCode)
	{
		super("RVS Exception: " + errorCode.toString());
		this.setErrorCode(errorCode);
	}

	public RVSException(RVSErrorCode errorCode, RuntimeException innerException)
	{
		super("RVS Exception: " + errorCode.toString(), innerException);
		this.setErrorCode(errorCode);
	}

	public RVSException(RVSErrorCode errorCode, String message)
	{
		super("RVS Exception: " + errorCode.toString() + ", " + message);
		this.setErrorCode(errorCode);
	}

	public RVSException(RVSErrorCode errorCode, String message, RuntimeException innerException)
	{
		super("RVS Exception: " + errorCode.toString() + ", " + message, innerException);
		this.setErrorCode(errorCode);
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties
	private RVSErrorCode ErrorCode = RVSErrorCode.values()[0];
	public final RVSErrorCode getErrorCode()
	{
		return ErrorCode;
	}
	private void setErrorCode(RVSErrorCode value)
	{
		ErrorCode = value;
	}

	//public override string Message
	//{
	//    get
	//    {
	//        return this.ToString();
	//    }
	//}

	public final String getHumanString()
	{
		if (this.getErrorCode() == RVSErrorCode.Unknown)
		{
			return this.getMessage();
		}
		else
		{
			try
			{
				String descriptionAttributeValue = this.getErrorCode().GetEnumDescription();
				if (!tangible.StringHelper.isNullOrWhiteSpace(descriptionAttributeValue))
				{
					return descriptionAttributeValue + " " + this.getMessage();
				}
				else
				{
					return this.getMessage();
				}
			}
			catch (java.lang.Exception e)
			{
				return this.getMessage();
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods
	@Override
	public void GetObjectData(SerializationInfo info, StreamingContext context)
	{
		super.GetObjectData(info, context);
		info.AddValue("ErrorCode", this.getErrorCode().getValue());
	}

	@Override
	public String toString()
	{
		String retVal = "RVS Exception: " + this.getErrorCode().toString();
		if (!tangible.StringHelper.isNullOrEmpty(this.getMessage()))
		{
			retVal += ", " + this.getMessage();
		}
		return retVal;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}