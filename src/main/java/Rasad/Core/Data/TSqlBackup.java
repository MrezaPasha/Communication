package Rasad.Core.Data;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

public class TSqlBackup
{
	public TSqlBackup(String connectionString)
	{
		SqlConnectionStringBuilder builder = new SqlConnectionStringBuilder(connectionString);
		this.setDatabase(builder.InitialCatalog);
		builder.InitialCatalog = "master";
		// Store the new connection string
		this.setMasterConnectionString(builder.ConnectionString);
		this.setConnectionString(connectionString);
	}
	private String MasterConnectionString;
	public final String getMasterConnectionString()
	{
		return MasterConnectionString;
	}
	private void setMasterConnectionString(String value)
	{
		MasterConnectionString = value;
	}
	private String ConnectionString;
	public final String getConnectionString()
	{
		return ConnectionString;
	}
	private void setConnectionString(String value)
	{
		ConnectionString = value;
	}
	private String Database;
	public final String getDatabase()
	{
		return Database;
	}
	public final void setDatabase(String value)
	{
		Database = value;
	}
	/** 
	 Enables using SQL Copy Only backup. This will only function if the SQL Server is running 2008 or higher.
	*/
	private boolean CopyOnly;
	public final boolean getCopyOnly()
	{
		return CopyOnly;
	}
	public final void setCopyOnly(boolean value)
	{
		CopyOnly = value;
	}

	/** 
	 Enables using SQL Compression on the backup. This will only function if the SQL Server is running 2008 or higher and non-Express editions.
	*/
	private boolean Compression;
	public final boolean getCompression()
	{
		return Compression;
	}
	public final void setCompression(boolean value)
	{
		Compression = value;
	}

	/** 
	 
	 
	 @param sourceDatabaseName
	 @param backupName Specifies the name of the backup set. Names can have a maximum of 128 characters. If NAME is not specified, it is blank.
	 @param backupDescription Specifies the free-form text that describes the backup set. The string can have a maximum of 255 characters
	 @param backupFilePath
	*/
	public final void StartBackupDatabase(String backupName, String backupDescription, String backupFilePath)
	{
		Task.Factory.StartNew(() ->
		{
				if (System.TStringHelper.IsNullOrEmpty(backupDescription))
				{
					backupDescription = " ";
				}
				_Exception = null;
				if (System.TStringHelper.IsNullOrEmpty(Thread.currentThread().Name))
				{
					Thread.currentThread().Name = "SQL Backup";
				}
				try (SqlConnection con = new SqlConnection(this.getMasterConnectionString()))
				{
					//The FireInfoMessageEventOnUserErrors property and InfoMessage event ensure that the C# code captures these messages during execution rather than only at the end.
					con.FireInfoMessageEventOnUserErrors = true;
					con.InfoMessage += OnInfoMessage;
					con.Open();
					//The stats = 1 clause tells SQL Server to emit severity 0 messages at the specified percentage interval (in this case 1%).
					try (SqlCommand cmd = new SqlCommand("BACKUP DATABASE @database TO DISK = @filename with FORMAT,description = @description, name = @name, stats = 1", con))
					{

						cmd.Parameters.Clear();
						cmd.Parameters.AddWithValue("@database", getDatabase());
						cmd.Parameters.AddWithValue("@filename", backupFilePath);
						cmd.Parameters.AddWithValue("@description", backupDescription);
						cmd.Parameters.AddWithValue("@name", backupName);

						cmd.CommandTimeout = (int)TimeSpan.FromMinutes(10).TotalSeconds;
						cmd.ExecuteNonQuery();
					}

					con.Close();
					con.InfoMessage -= OnInfoMessage;
					con.FireInfoMessageEventOnUserErrors = false;
				}
		}).ContinueWith(task ->
		{
				if (task.Exception != null)
				{
					OnCompleted(task.Exception);
				}
				else if (!System.TStringHelper.IsNullOrEmpty(_Exception))
				{
					OnCompleted(new RuntimeException(_Exception));
				}
				else
				{
					OnCompleted(null);
				}

			});
	}



	public final void StartRestoreDatabase(String backupFilePath)
	{
		StartRestoreDatabase(backupFilePath, null);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public void StartRestoreDatabase(string backupFilePath, string toDatabaseName = null)
	public final void StartRestoreDatabase(String backupFilePath, String toDatabaseName)
	{
		Task.Factory.StartNew(() ->
		{
				if (System.TStringHelper.IsNullOrEmpty(Thread.currentThread().Name))
				{
					Thread.currentThread().Name = "SQL Restore";
				}
				if (System.TStringHelper.IsNullOrEmpty(toDatabaseName))
				{
					toDatabaseName = this.getDatabase();
				}
				try (SqlConnection con = new SqlConnection(this.getMasterConnectionString()))
				{
					try (TExclusiveAccessToDatabase Locker = new TExclusiveAccessToDatabase(con, this.getDatabase()))
					{
						//The FireInfoMessageEventOnUserErrors property and InfoMessage event ensure that the C# code captures these messages during execution rather than only at the end.
						con.FireInfoMessageEventOnUserErrors = true;
						con.InfoMessage += OnInfoMessage;
						if (con.State != ConnectionState.Open)
						{
							con.Open();
						}
						//The stats = 1 clause tells SQL Server to emit severity 0 messages at the specified percentage interval (in this case 1%).
						try (SqlCommand cmd = new SqlCommand("RESTORE DATABASE @toDatabaseName FROM DISK = @backupFilePath WITH REPLACE, RECOVERY, stats = 1", con))
						{
							cmd.Parameters.Clear();
							cmd.Parameters.AddWithValue("@toDatabaseName", toDatabaseName);
							cmd.Parameters.AddWithValue("@backupFilePath", backupFilePath);

							cmd.CommandTimeout = (int)TimeSpan.FromMinutes(10).TotalSeconds;
							cmd.ExecuteNonQuery();
						}

						con.InfoMessage -= OnInfoMessage;
						con.FireInfoMessageEventOnUserErrors = false;
					}
				}
		}).ContinueWith(task ->
		{
				if (task.Exception != null)
				{
					OnCompleted(task.Exception);
				}
				else if (!System.TStringHelper.IsNullOrEmpty(_Exception))
				{
					OnCompleted(new RuntimeException(_Exception));
				}
				else
				{
					OnCompleted(null);
				}
			});
	}
	public final void StartRestoreToNewDatabase(String backupFilePath, String newDatabaseName, String new_MDF_FilePath, String new_Log_FilePath)
	{
		Task.Factory.StartNew(() ->
		{
				if (System.TStringHelper.IsNullOrEmpty(Thread.currentThread().Name))
				{
					Thread.currentThread().Name = "SQL Restore";
				}

				try (SqlConnection con = new SqlConnection(this.getMasterConnectionString()))
				{
					try (TExclusiveAccessToDatabase Locker = new TExclusiveAccessToDatabase(con, this.getDatabase()))
					{
						Rasad.Core.Data.BackupFileList backupFileList = ReadFileList(backupFilePath);
						var MdfFileInfo = backupFileList.getFiles().FirstOrDefault(t -> t.Type.equals("D"));
						var LdfFileInfo = backupFileList.getFiles().FirstOrDefault(t -> t.Type.equals("L"));

						//The FireInfoMessageEventOnUserErrors property and InfoMessage event ensure that the C# code captures these messages during execution rather than only at the end.
						con.FireInfoMessageEventOnUserErrors = true;
						con.InfoMessage += OnInfoMessage;
						if (con.State != ConnectionState.Open)
						{
							con.Open();
						}
						//The stats = 1 clause tells SQL Server to emit severity 0 messages at the specified percentage interval (in this case 1%).
						try (SqlCommand cmd = new SqlCommand("RESTORE DATABASE @newDatabaseName FROM DISK = @backupFilePath WITH REPLACE, RECOVERY, stats = 1,MOVE @previousMdfName TO @mdfFilePath,MOVE @previousLdfName TO @ldfFilePath", con))
						{
							cmd.Parameters.Clear();
							cmd.Parameters.AddWithValue("@newDatabaseName", newDatabaseName);
							cmd.Parameters.AddWithValue("@backupFilePath", backupFilePath);

							cmd.Parameters.AddWithValue("@previousMdfName", MdfFileInfo.LogicalName);
							cmd.Parameters.AddWithValue("@previousLdfName", LdfFileInfo.LogicalName);

							cmd.Parameters.AddWithValue("@mdfFilePath", new_MDF_FilePath);
							cmd.Parameters.AddWithValue("@ldfFilePath", new_Log_FilePath);

							cmd.CommandTimeout = (int)TimeSpan.FromMinutes(10).TotalSeconds;
							cmd.ExecuteNonQuery();
						}

						con.InfoMessage -= OnInfoMessage;
						con.FireInfoMessageEventOnUserErrors = false;
					}
				}
		}).ContinueWith(task ->
		{
				if (task.Exception != null)
				{
					OnCompleted(task.Exception);
				}
				else if (!System.TStringHelper.IsNullOrEmpty(_Exception))
				{
					OnCompleted(new RuntimeException(_Exception));
				}
				else
				{
					OnCompleted(null);
				}
			});
	}

	protected void OnCompleted(RuntimeException exception)
	{
		if (ActionCompleted != null)
		{
			for (ActionCompletedEventHandler listener : ActionCompleted.listeners())
			{
				listener.invoke(this, exception);
			}
		}
	}


	private void OnInfoMessage(Object sender, SqlInfoMessageEventArgs e)
	{
		for (SqlError info : e.Errors)
		{
			if (info.Class > 10)
			{
				_Exception += "\r\n" + info.Message;
				_Exception = _Exception.trim();
				// TODO: treat this as a genuine error
			}
			else
			{
				String numValue = info.Message.split(java.util.regex.Pattern.quote(" "), -1).First();
				int progressPercent;
				tangible.OutObject<Integer> tempOut_progressPercent = new tangible.OutObject<Integer>();
				if (tangible.TryParseHelper.tryParseInt(numValue, tempOut_progressPercent))
				{
				progressPercent = tempOut_progressPercent.argValue;
					OnProgressChanged(progressPercent);
				}
			else
			{
				progressPercent = tempOut_progressPercent.argValue;
			}
				// TODO: treat this as a progress message
			}
		}
	}
	private String _Exception;
	protected void OnProgressChanged(int percent)
	{
		if (ProgressChanged != null)
		{
			for (System.ComponentModel.ProgressChangedEventHandler listener : ProgressChanged.listeners())
			{
				listener.invoke(this, new System.ComponentModel.ProgressChangedEventArgs(percent, null));
			}
		}
	}
	public tangible.Event<ActionCompletedEventHandler> ActionCompleted = new tangible.Event<ActionCompletedEventHandler>();

	public tangible.Event<System.ComponentModel.ProgressChangedEventHandler> ProgressChanged = new tangible.Event<System.ComponentModel.ProgressChangedEventHandler>();
	protected final String QuoteIdentifier(String name)
	{
		return "[" + name.replace("]", "]]") + "]";
	}

	protected final String QuoteString(String text)
	{
		return "'" + text.replace("'", "''") + "'";
	}

	public final BackupFileHeader ReadHeader(String backupFilePath)
	{
		if (System.TStringHelper.IsNullOrEmpty(backupFilePath))
		{
			throw new NullPointerException("backupFilePath");
		}
		if (!(new File(backupFilePath)).isFile())
		{
			throw new FileNotFoundException("backup file not found !", backupFilePath);
		}

		DataTable Result = new DataTable();
		try (SqlConnection con = new SqlConnection(this.getConnectionString()))
		{
			con.Open();
			String commandText = String.format("RESTORE HEADERONLY FROM DISK = '%1$s'", backupFilePath);

			try (SqlCommand cmd = new SqlCommand(commandText, con))
			{
				try (SqlDataAdapter AD = new SqlDataAdapter(cmd))
				{
					AD.Fill(Result);
				}
			}
			con.Close();
		}
		if (Result.Rows.Any())
		{
			DataRow DR = Result.Rows[0];
			return new BackupFileHeader(DR);
		}
		else
		{
			return null;
		}
	}

	public final BackupFileList ReadFileList(String backupFileName)
	{
		DataTable Result = new DataTable();
		try (SqlConnection con = new SqlConnection(this.getConnectionString()))
		{
			con.Open();
			String commandText = String.format("RESTORE FILELISTONLY FROM DISK = '%1$s'", backupFileName);

			try (SqlCommand cmd = new SqlCommand(commandText, con))
			{
				try (SqlDataAdapter AD = new SqlDataAdapter(cmd))
				{
					AD.Fill(Result);
				}
			}
			con.Close();
		}
		if (Result.Rows.Any())
		{
			return new BackupFileList(Result);
		}
		else
		{
			return null;
		}
	}

	public final boolean VerifyBackupFile(String backupFileName)
	{
		DataTable Result = new DataTable();
		try (SqlConnection con = new SqlConnection(this.getConnectionString()))
		{
			con.Open();
			String commandText = String.format("RESTORE VERIFYONLY FROM DISK = '%1$s'", backupFileName);
			try
			{
				try (SqlCommand cmd = new SqlCommand(commandText, con))
				{
					cmd.ExecuteNonQuery();
				}
				return true;
			}
			catch (java.lang.Exception e)
			{
				return false;
			}
			finally
			{
				con.Close();
			}
		}
	}



}