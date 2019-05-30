package Rasad.Core.Data;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

public class TExclusiveAccessToDatabase implements Closeable
{
	public TExclusiveAccessToDatabase(String connectionString)
	{
		SqlConnectionStringBuilder SB = new SqlConnectionStringBuilder(connectionString);
		this.setDatabaseName(SB.InitialCatalog);
		_IsConnectionOwner = true;
		setConnection(new SqlConnection(connectionString));
		getConnection().Open();
		LockDatabase();
	}
	public TExclusiveAccessToDatabase(SqlConnection connection, String databaseName)
	{
		_IsConnectionOwner = false;
		this.setConnection(connection);
		this.setDatabaseName(databaseName);
		if (this.getConnection().State != ConnectionState.Open)
		{
			this.getConnection().Open();
		}
		LockDatabase();
	}
	private boolean _IsConnectionOwner = false;
	private SqlConnection Connection;
	public final SqlConnection getConnection()
	{
		return Connection;
	}
	private void setConnection(SqlConnection value)
	{
		Connection = value;
	}

	private void LockDatabase()
	{
		try (SqlCommand CMD = new SqlCommand("ALTER DATABASE [" + getDatabaseName() + "] SET Single_User WITH Rollback Immediate", getConnection()))
		{
			CMD.ExecuteNonQuery();
		}
	}
	private void UnlockDatabase()
	{
		try (SqlCommand CMD = new SqlCommand("ALTER DATABASE [" + getDatabaseName() + "] SET Multi_User", getConnection()))
		{
			CMD.ExecuteNonQuery();
		}
	}
	private String DatabaseName;
	public final String getDatabaseName()
	{
		return DatabaseName;
	}
	private void setDatabaseName(String value)
	{
		DatabaseName = value;
	}

	public final void close() throws IOException
	{
		UnlockDatabase();
		if (getConnection() != null)
		{
			if (_IsConnectionOwner)
			{
				this.getConnection().Close();
				this.getConnection().Dispose();
			}
			this.setConnection(null);
		}
	}
}