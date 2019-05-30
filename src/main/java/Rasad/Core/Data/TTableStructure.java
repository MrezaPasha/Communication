package Rasad.Core.Data;

import Rasad.Core.Services.*;
import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class TTableStructure
{
	public TTableStructure(String tableSchema, String tableName)
	{
		setFields(new ArrayList<TTableField>());
		this.setTableSchema(tableSchema);
		this.setTableName(tableName);
	}

	private String TableSchema;
	public final String getTableSchema()
	{
		return TableSchema;
	}
	public final void setTableSchema(String value)
	{
		TableSchema = value;
	}
	private String TableName;
	public final String getTableName()
	{
		return TableName;
	}
	public final void setTableName(String value)
	{
		TableName = value;
	}
	private ArrayList<TTableField> Fields;
	public final ArrayList<TTableField> getFields()
	{
		return Fields;
	}
	private void setFields(ArrayList<TTableField> value)
	{
		Fields = value;
	}

	public final String getCreateTableScript()
	{
		StringBuilder CmdTxt = new StringBuilder();
		CmdTxt.append(String.format("Create Table [%1$s].[%2$s](\r\n\t", getTableSchema(), getTableName()));
		CmdTxt.append(tangible.StringHelper.join(",\r\n\t", this.getFields().Select(t -> t.GetCreateExpression())));
		CmdTxt.append("\r\n)");
		return CmdTxt.toString();
	}

	@Override
	public String toString()
	{
		return String.format("[%1$s].[%2$s]", getTableSchema(), getTableName());
	}

}