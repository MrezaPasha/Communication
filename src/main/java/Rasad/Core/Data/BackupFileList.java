package Rasad.Core.Data;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

public class BackupFileList
{
	public BackupFileList(DataTable dt)
	{
		setFiles(new ArrayList<File>());
		for (DataRow item : dt.Rows)
		{
			getFiles().add(new File(item));
		}
	}
	private ArrayList<File> Files;
	public final ArrayList<File> getFiles()
	{
		return Files;
	}
	private void setFiles(ArrayList<File> value)
	{
		Files = value;
	}
	public static class File
	{
		public FileInfo(DataRow row)
		{
			setLogicalName((String)row["LogicalName"]);
			setPhysicalName((String)row["PhysicalName"]);
			setType((String)row["Type"]);

			setSize((long)row["Size"]);
			setMaxSize((long)row["MaxSize"]);
			setFileID((long)row["FileID"]);
			setBackupSizeInBytes((long)row["BackupSizeInBytes"]);
		}
		/** 
		 nvarchar(128) => Logical name of the file.
		*/
		private String LogicalName;
		public final String getLogicalName()
		{
			return LogicalName;
		}
		public final void setLogicalName(String value)
		{
			LogicalName = value;
		}
		/** 
		 nvarchar(260) => Physical or operating-system name of the file.(path of file)
		*/
		private String PhysicalName;
		public final String getPhysicalName()
		{
			return PhysicalName;
		}
		public final void setPhysicalName(String value)
		{
			PhysicalName = value;
		}
		/** 
		 char(1) => The type of file, one of:
		 L = Microsoft SQL Server log file 
		 D = SQL Server data file 
		 F = Full Text Catalog
		 S = FileStream, FileTable, or In-Memory OLTP container
		*/
		private String Type;
		public final String getType()
		{
			return Type;
		}
		public final void setType(String value)
		{
			Type = value;
		}
		/** 
		 nvarchar(128) => Name of the filegroup that contains the file.
		*/
		private String FileGroupName;
		public final String getFileGroupName()
		{
			return FileGroupName;
		}
		public final void setFileGroupName(String value)
		{
			FileGroupName = value;
		}
		/** 
		 numeric(20,0) => Current size in bytes.
		*/
		private long Size;
		public final long getSize()
		{
			return Size;
		}
		public final void setSize(long value)
		{
			Size = value;
		}
		/** 
		 numeric(20,0) => Maximum allowed size in bytes.
		*/
		private long MaxSize;
		public final long getMaxSize()
		{
			return MaxSize;
		}
		public final void setMaxSize(long value)
		{
			MaxSize = value;
		}
		/** 
		 File identifier, unique within the database.
		*/
		private long FileID;
		public final long getFileID()
		{
			return FileID;
		}
		public final void setFileID(long value)
		{
			FileID = value;
		}

		private long BackupSizeInBytes;
		public final long getBackupSizeInBytes()
		{
			return BackupSizeInBytes;
		}
		public final void setBackupSizeInBytes(long value)
		{
			BackupSizeInBytes = value;
		}

		//CreateLSN         //numeric(25,0)         //Log sequence number at which the file was created.
		//DropLSN           //numeric(25,0) NULL    //The log sequence number at which the file was dropped. If the file has not been dropped, this value is NULL.
		//UniqueID          //uniqueidentifier      //Globally unique identifier of the file.
		//ReadOnlyLSN       //numeric(25,0) NULL    //Log sequence number at which the filegroup containing the file changed from read-write to read-only (the most recent change).
		//ReadWriteLSN      //numeric(25,0) NULL    //Log sequence number at which the filegroup containing the file changed from read-only to read-write (the most recent change).
		//BackupSizeInBytes //bigint    //Size of the backup for this file in bytes.
		//SourceBlockSize   //int       //Block size of the physical device containing the file in bytes (not the backup device).
		//FileGroupID       //int       //ID of the filegroup.
		//LogGroupGUID      //uniqueidentifier NULL //NULL. 
		//DifferentialBaseLSN   //numeric(25,0) NULL    //For differential backups, changes with log sequence numbers greater than or equal to DifferentialBaseLSN are included in the differential. 
		//For other backup types, the value is NULL. 
		//DifferentialBaseGUID//uniqueidentifier//For differential backups, the unique identifier of the differential base. 
		//For other backup types, the value is NULL.
		//IsReadOnly//bit//1 = The file is read-only.
		//IsPresent//bit//1 = The file is present in the backup.
		//TDEThumbprint//varbinary(32)//Shows the thumbprint of the Database Encryption Key. The encryptor thumbprint is a SHA-1 hash of the certificate with which the key is encrypted. For information about database encryption, see Transparent Data Encryption (TDE). 
		//SnapshotURL//nvarchar(360)//The URL for the Azure snapshot of the database file contained in the FILE_SNAPSHOT backup. Returns NULL if no FILE_SNAPSHOT backup.

		@Override
		public String toString()
		{
			return String.format("%1$s , %2$s , %3$s , Size : %4$s", getLogicalName(), getPhysicalName(), this.getType(), System.TStringHelper.ToFileSize(getSize()));
		}
	}
}