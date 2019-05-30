package Rasad.Core.Data;

import Rasad.Core.Services.*;
import Rasad.Core.*;
import java.util.*;
import java.io.*;

public class TSqlHelper implements Closeable
{
	public TSqlHelper()
	{
		IConnectionStringService CSS = TServiceContext.Instance.<IConnectionStringService>GetService();
		setConnection(new SqlConnection(CSS.getProviderConnectionString()));
		getConnection().Open();

	}
	private SqlConnection Connection;
	public final SqlConnection getConnection()
	{
		return Connection;
	}
	private void setConnection(SqlConnection value)
	{
		Connection = value;
	}
	private SqlTransaction Transaction;
	public final SqlTransaction getTransaction()
	{
		return Transaction;
	}
	private void setTransaction(SqlTransaction value)
	{
		Transaction = value;
	}


	public final boolean DropTable(String tableSchema, String tableName)
	{
		EnsureTransaction();
		for (TRelation relation : GetRelations(tableSchema, tableName))
		{
			DropConstraint(relation.getFKTableSchema(), relation.getFKTableName(), relation.getCONSTRAINT_NAME());
		}
		String cmd = String.format("Drop Table [%1$s].[%2$s]", tableSchema, tableName);
		try (SqlCommand CMD = new SqlCommand(cmd, getConnection(), getTransaction()))
		{
			return CMD.ExecuteNonQuery() > 0;
		}
	}
	public final boolean CreateTable(TTableStructure table)
	{
		EnsureTransaction();
		if (ExistTable(table.getTableSchema(), table.getTableName()))
		{
			DropTable(table.getTableSchema(), table.getTableName());
		}
		try (SqlCommand CMD = new SqlCommand(table.getCreateTableScript(), getConnection(), getTransaction()))
		{
			return CMD.ExecuteNonQuery() > 0;
		}
	}
	public final boolean AddPrimaryKeyToTable(String tableSchema, String tableName, String... primaryKeys)
	{
		EnsureTransaction();
		String pks = "[" + tangible.StringHelper.join("], [", primaryKeys) + "]";
		String CmdTxt = String.format("Alter Table [%1$s].[%2$s] ADD Primary Key (%3$s)", tableSchema, tableName, pks);
		try (SqlCommand CMD = new SqlCommand(CmdTxt.toString(), getConnection(), getTransaction()))
		{
			return CMD.ExecuteNonQuery() > 0;
		}
	}
	public final boolean DropConstraint(String tableSchema, String tableName, String constraintName)
	{
		EnsureTransaction();
		String CmdTxt = String.format("ALTER TABLE [%1$s].[%2$s] DROP CONSTRAINT [%3$s]", tableSchema, tableName, constraintName);
		try (SqlCommand CMD = new SqlCommand(CmdTxt.toString(), getConnection(), getTransaction()))
		{
			return CMD.ExecuteNonQuery() > 0;
		}
	}

	public final boolean AddForeignKeyConstraint(String pkTableSchema, String pkTableName, String[] pKeys, String fkTableSchema, String fkTableName, String[] fKeys, boolean cascadeDelete)
	{
		return AddForeignKeyConstraint(pkTableSchema, pkTableName, pKeys, fkTableSchema, fkTableName, fKeys, cascadeDelete, false);
	}

	public final boolean AddForeignKeyConstraint(String pkTableSchema, String pkTableName, String[] pKeys, String fkTableSchema, String fkTableName, String[] fKeys)
	{
		return AddForeignKeyConstraint(pkTableSchema, pkTableName, pKeys, fkTableSchema, fkTableName, fKeys, false, false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public bool AddForeignKeyConstraint(string pkTableSchema, string pkTableName, string[] pKeys, string fkTableSchema, string fkTableName, string[] fKeys, bool cascadeDelete = false, bool cascadeUpdate = false)
	public final boolean AddForeignKeyConstraint(String pkTableSchema, String pkTableName, String[] pKeys, String fkTableSchema, String fkTableName, String[] fKeys, boolean cascadeDelete, boolean cascadeUpdate)
	{
		EnsureTransaction();
		String FKeyColumns = "[" + tangible.StringHelper.join("],[", fKeys) + "]";
		String PKeyColumns = "[" + tangible.StringHelper.join("],[", pKeys) + "]";
		String CmdTxt = String.format("ALTER TABLE [%1$s].[%2$s] ADD Foreign Key (%3$s) REFERENCES [%4$s].[%5$s](%6$s)", fkTableSchema, fkTableName, FKeyColumns, pkTableSchema, pkTableName, PKeyColumns);
		if (cascadeDelete)
		{
			CmdTxt += " ON DELETE CASCADE";
		}
		if (cascadeUpdate)
		{
			CmdTxt += " ON UPDATE CASCADE";
		}
		try (SqlCommand CMD = new SqlCommand(CmdTxt, getConnection(), getTransaction()))
		{
			return CMD.ExecuteNonQuery() > 0;
		}
	}


	public final boolean AddForeignKeyConstraint(TTableStructure pkTable, String[] pKeys, TTableStructure fkTable, String[] fKeys, boolean cascadeDelete)
	{
		return AddForeignKeyConstraint(pkTable, pKeys, fkTable, fKeys, cascadeDelete, false);
	}

	public final boolean AddForeignKeyConstraint(TTableStructure pkTable, String[] pKeys, TTableStructure fkTable, String[] fKeys)
	{
		return AddForeignKeyConstraint(pkTable, pKeys, fkTable, fKeys, false, false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public bool AddForeignKeyConstraint(TTableStructure pkTable, string[] pKeys, TTableStructure fkTable, string[] fKeys, bool cascadeDelete = false, bool cascadeUpdate = false)
	public final boolean AddForeignKeyConstraint(TTableStructure pkTable, String[] pKeys, TTableStructure fkTable, String[] fKeys, boolean cascadeDelete, boolean cascadeUpdate)
	{
		return AddForeignKeyConstraint(pkTable.getTableSchema(), pkTable.getTableName(), pKeys, fkTable.getTableSchema(), fkTable.getTableName(), fKeys, cascadeDelete, cascadeUpdate);
	}


	public final boolean ExistTable(String tableSchema, String tableName)
	{
		String cmd = String.format("IF exists(" + "\r\n" +
"	                        Select 1 from INFORMATION_SCHEMA.TABLES" + "\r\n" +
"	                        Where TABLE_TYPE = 'Base Table'" + "\r\n" +
"	                        And TABLE_SCHEMA = '%1$s'" + "\r\n" +
"                            And TABLE_NAME = '%2$s'" + "\r\n" +
"                        ) Select 1 Else Select 0", tableSchema, tableName);
		try (SqlCommand CMD = new SqlCommand(cmd, getConnection(), getTransaction()))
		{
			return (boolean)CMD.ExecuteScalar();
		}
	}
	public final ArrayList<TRelation> GetRelations(String tableSchema, String tableName)
	{
		ArrayList<TRelation> Result = new ArrayList<TRelation>();
		String cmd = String.format("SELECT" + "\r\n" +
"    c.CONSTRAINT_NAME," + "\r\n" +
"	cu.TABLE_SCHEMA as FKTableSchema," + "\r\n" +
"    cu.TABLE_NAME AS FKTableName," + "\r\n" +
"	cu.COLUMN_NAME AS FKColumnName," + "\r\n" +
"	ku.TABLE_SCHEMA as PKTableSchema," + "\r\n" +
"    ku.TABLE_NAME AS PKTableName," + "\r\n" +
"	ku.COLUMN_NAME AS PKColumnName" + "\r\n" +
"FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS c" + "\r\n" +
"INNER JOIN INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE cu" + "\r\n" +
"ON cu.CONSTRAINT_NAME = c.CONSTRAINT_NAME" + "\r\n" +
"INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE ku" + "\r\n" +
"ON ku.CONSTRAINT_NAME = c.UNIQUE_CONSTRAINT_NAME" + "\r\n" +
"where ku.TABLE_SCHEMA='%1$s' and ku.TABLE_NAME='%2$s'", tableSchema, tableName);
		try (SqlCommand CMD = new SqlCommand(cmd, getConnection(), getTransaction()))
		{

			try (SqlDataReader DR = CMD.ExecuteReader())
			{
				while (DR.Read())
				{
					TRelation Rel = new TRelation();
					Rel.setCONSTRAINT_NAME(DR["CONSTRAINT_NAME"].ToStringSafe());

					Rel.setPKTableSchema(DR["PKTableSchema"].ToStringSafe());
					Rel.setPKTableName(DR["PKTableName"].ToStringSafe());
					Rel.setPKColumnName(DR["PKColumnName"].ToStringSafe());

					Rel.setFKTableSchema(DR["FKTableSchema"].ToStringSafe());
					Rel.setFKTableName(DR["FKTableName"].ToStringSafe());
					Rel.setFKColumnName(DR["FKColumnName"].ToStringSafe());

					Result.add(Rel);
				}

			}
		}
		return Result;
	}



	private void EnsureTransaction()
	{
		if (getTransaction() == null)
		{
			setTransaction(getConnection().BeginTransaction());
		}
	}

	public final void CommitTransaction()
	{
		if (getTransaction() != null)
		{
			getTransaction().Commit();
			getTransaction().Dispose();
			setTransaction(null);
		}
	}

	public final void RollbackTransaction()
	{
		if (getTransaction() != null)
		{
			getTransaction().Rollback();
			getTransaction().Dispose();
			setTransaction(null);
		}
	}

	public final void close() throws IOException
	{
		RollbackTransaction();
		if (getConnection() != null)
		{
			getConnection().Close();
			getConnection().Dispose();
		}
	}
}