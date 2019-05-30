package Rasad.Core.Data;

import Rasad.Core.Services.*;
import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class TTableField
{
	public TTableField()
	{

	}

	public TTableField(boolean isPrimaryKey, String fieldName, String fieldType, boolean allowNull, boolean isIdentity)
	{
		this(isPrimaryKey, fieldName, fieldType, allowNull, isIdentity, null);
	}

	public TTableField(boolean isPrimaryKey, String fieldName, String fieldType, boolean allowNull)
	{
		this(isPrimaryKey, fieldName, fieldType, allowNull, false, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TTableField(bool isPrimaryKey, string fieldName, string fieldType, bool allowNull, bool isIdentity = false, string defaultValue = null)
	public TTableField(boolean isPrimaryKey, String fieldName, String fieldType, boolean allowNull, boolean isIdentity, String defaultValue)
	{
		this();
		this.setFieldName(fieldName);
		this.setFieldType(fieldType);
		this.setAllowNull(allowNull);
		this.setIsPrimaryKey(isPrimaryKey);
		this.setIsIDENTITY(isIdentity);
		this.setDefaultValue(defaultValue);
	}
	public TTableField(String fieldName, String formula, boolean isPersisted)
	{
		this();
		this.setFieldName(fieldName);
		this.setFormula(formula);
		this.setIsPersisted(isPersisted);
	}
	private String FieldName;
	public final String getFieldName()
	{
		return FieldName;
	}
	public final void setFieldName(String value)
	{
		FieldName = value;
	}
	private String FieldType;
	public final String getFieldType()
	{
		return FieldType;
	}
	public final void setFieldType(String value)
	{
		FieldType = value;
	}
	private boolean AllowNull;
	public final boolean getAllowNull()
	{
		return AllowNull;
	}
	public final void setAllowNull(boolean value)
	{
		AllowNull = value;
	}

	public final boolean getIsComputed()
	{
		return !getFormula().IsNullOrEmpty();
	}
	private boolean IsIDENTITY;
	public final boolean getIsIDENTITY()
	{
		return IsIDENTITY;
	}
	public final void setIsIDENTITY(boolean value)
	{
		IsIDENTITY = value;
	}
	private boolean IsPersisted;
	public final boolean getIsPersisted()
	{
		return IsPersisted;
	}
	public final void setIsPersisted(boolean value)
	{
		IsPersisted = value;
	}
	private String Formula;
	public final String getFormula()
	{
		return Formula;
	}
	public final void setFormula(String value)
	{
		Formula = value;
	}

	private boolean IsPrimaryKey;
	public final boolean getIsPrimaryKey()
	{
		return IsPrimaryKey;
	}
	public final void setIsPrimaryKey(boolean value)
	{
		IsPrimaryKey = value;
	}
	private String DefaultValue;
	public final String getDefaultValue()
	{
		return DefaultValue;
	}
	public final void setDefaultValue(String value)
	{
		DefaultValue = value;
	}


	public final String GetCreateExpression()
	{
		if (getIsComputed())
		{
			if (getIsPersisted())
			{
				return String.format("[%1$s]\tAS\t%2$s PERSISTED", getFieldName(), getFormula());
			}
			else
			{
				return String.format("[{0}]\tAS\t{1}");
			}
		}
		else
		{
			String Result = String.format("[%1$s]\t%2$s", getFieldName(), getFieldType());
			if (getIsIDENTITY())
			{
				Result += " IDENTITY(1,1)";
			}
			if (getAllowNull())
			{
				Result += " NULL";
			}
			else
			{
				Result += " NOT NULL";
			}

			if (!getDefaultValue().IsNullOrEmpty())
			{
				Result += " DEFAULT (" + getDefaultValue() + ")";
			}
			if (getIsPrimaryKey())
			{
				Result += " PRIMARY KEY";
			}
			return Result;
		}
	}

	@Override
	public String toString()
	{
		return String.format("%1$s %2$s %3$s", getFieldName(), getFieldType(), (getIsPrimaryKey() ? "Key" : ""));
	}
}