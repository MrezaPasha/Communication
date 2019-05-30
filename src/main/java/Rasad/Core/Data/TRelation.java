package Rasad.Core.Data;

import Rasad.Core.Services.*;
import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class TRelation
{
	private String CONSTRAINT_NAME;
	public final String getCONSTRAINT_NAME()
	{
		return CONSTRAINT_NAME;
	}
	public final void setCONSTRAINT_NAME(String value)
	{
		CONSTRAINT_NAME = value;
	}
	private String FKTableSchema;
	public final String getFKTableSchema()
	{
		return FKTableSchema;
	}
	public final void setFKTableSchema(String value)
	{
		FKTableSchema = value;
	}
	private String FKTableName;
	public final String getFKTableName()
	{
		return FKTableName;
	}
	public final void setFKTableName(String value)
	{
		FKTableName = value;
	}
	private String FKColumnName;
	public final String getFKColumnName()
	{
		return FKColumnName;
	}
	public final void setFKColumnName(String value)
	{
		FKColumnName = value;
	}


	private String PKTableSchema;
	public final String getPKTableSchema()
	{
		return PKTableSchema;
	}
	public final void setPKTableSchema(String value)
	{
		PKTableSchema = value;
	}
	private String PKTableName;
	public final String getPKTableName()
	{
		return PKTableName;
	}
	public final void setPKTableName(String value)
	{
		PKTableName = value;
	}
	private String PKColumnName;
	public final String getPKColumnName()
	{
		return PKColumnName;
	}
	public final void setPKColumnName(String value)
	{
		PKColumnName = value;
	}

	@Override
	public String toString()
	{
		return String.format("%1$s %2$s.%3$s.%4$s = %5$s.%6$s.%7$s", getCONSTRAINT_NAME(), getPKTableSchema(), getPKTableName(), getPKColumnName(), getFKTableSchema(), getFKTableName(), getFKColumnName());
	}
}