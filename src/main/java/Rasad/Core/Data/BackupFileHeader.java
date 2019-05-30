package Rasad.Core.Data;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

public class BackupFileHeader
{
	public BackupFileHeader(DataRow row)
	{
		setBackupName((String)row["BackupName"]);
		setBackupDescription((String)row["BackupDescription"]);
		setDatabaseCreationDate((LocalDateTime)row["DatabaseCreationDate"]);
		setDatabaseVersion((int)row["DatabaseVersion"]);
		setBackupSize((long)row["BackupSize"]);
		setBackupStartDate((LocalDateTime)row["BackupStartDate"]);
		setBackupFinishDate((LocalDateTime)row["BackupFinishDate"]);

		setUserName((String)row["UserName"]);
		setServerName((String)row["ServerName"]);
		setDatabaseName((String)row["DatabaseName"]);
		setMachineName((String)row["MachineName"]);

	}
	private String BackupName;
	public final String getBackupName()
	{
		return BackupName;
	}
	public final void setBackupName(String value)
	{
		BackupName = value;
	}
	private String BackupDescription;
	public final String getBackupDescription()
	{
		return BackupDescription;
	}
	public final void setBackupDescription(String value)
	{
		BackupDescription = value;
	}
	private LocalDateTime DatabaseCreationDate = LocalDateTime.MIN;
	public final LocalDateTime getDatabaseCreationDate()
	{
		return DatabaseCreationDate;
	}
	public final void setDatabaseCreationDate(LocalDateTime value)
	{
		DatabaseCreationDate = value;
	}
	private int DatabaseVersion;
	public final int getDatabaseVersion()
	{
		return DatabaseVersion;
	}
	public final void setDatabaseVersion(int value)
	{
		DatabaseVersion = value;
	}
	private long BackupSize;
	public final long getBackupSize()
	{
		return BackupSize;
	}
	public final void setBackupSize(long value)
	{
		BackupSize = value;
	}
	private LocalDateTime BackupStartDate = LocalDateTime.MIN;
	public final LocalDateTime getBackupStartDate()
	{
		return BackupStartDate;
	}
	public final void setBackupStartDate(LocalDateTime value)
	{
		BackupStartDate = value;
	}
	private LocalDateTime BackupFinishDate = LocalDateTime.MIN;
	public final LocalDateTime getBackupFinishDate()
	{
		return BackupFinishDate;
	}
	public final void setBackupFinishDate(LocalDateTime value)
	{
		BackupFinishDate = value;
	}

	private String UserName;
	public final String getUserName()
	{
		return UserName;
	}
	public final void setUserName(String value)
	{
		UserName = value;
	}
	private String ServerName;
	public final String getServerName()
	{
		return ServerName;
	}
	public final void setServerName(String value)
	{
		ServerName = value;
	}
	private String DatabaseName;
	public final String getDatabaseName()
	{
		return DatabaseName;
	}
	public final void setDatabaseName(String value)
	{
		DatabaseName = value;
	}
	private String MachineName;
	public final String getMachineName()
	{
		return MachineName;
	}
	public final void setMachineName(String value)
	{
		MachineName = value;
	}

	//BackupName NULL 
	//BackupDescription NULL 
	//BackupType 1 
	//ExpirationDate NULL 
	//Compressed 0 
	//Position 1 
	//DeviceType 2 
	//UserName TESTServer1\DBA 
	//ServerName TESTServer1 
	//DatabaseName AdventureWorks 
	//DatabaseVersion 611 
	//DatabaseCreationDate 10/22/08 13:48 
	//BackupSize 177324544 
	//FirstLSN 414000000754800000 
	//LastLSN 414000000758300000 
	//CheckpointLSN 414000000754800000 
	//DatabaseBackupLSN 0 
	//BackupStartDate 3/19/09 12:02 
	//BackupFinishDate 3/19/09 12:02 
	//SortOrder 0 
	//CodePage 0 
	//UnicodeLocaleId 1033 
	//UnicodeComparisonStyle 196608 
	//CompatibilityLevel 90 
	//SoftwareVendorId 4608 
	//SoftwareVersionMajor 9 
	//SoftwareVersionMinor 0 
	//SoftwareVersionBuild 3077 
	//MachineName TESTServer1 
	//Flags 512 
	//BindingID 459DDE25-B461-4CFD-B72E-0D4388F50331 
	//RecoveryForkID E1BF182D-E21A-485A-9E2F-09E9C7DEC9D4 
	//Collation Latin1_General_CS_AS 
	//FamilyGUID E1BF182D-E21A-485A-9E2F-09E9C7DEC9D4 
	//HasBulkLoggedData 0 
	//IsSnapshot 0 
	//IsReadOnly 0 
	//IsSingleUser 0 
	//HasBackupChecksums 0 
	//IsDamaged 0 
	//BeginsLogChain 0 
	//HasIncompleteMetaData 0 
	//IsForceOffline 0 
	//IsCopyOnly 0 
	//FirstRecoveryForkID E1BF182D-E21A-485A-9E2F-09E9C7DEC9D4 
	//ForkPointLSN NULL 
	//RecoveryModel FULL 
	//DifferentialBaseLSN NULL 
	//DifferentialBaseGUID NULL 
	//BackupTypeDescription Database 
	//BackupSetGUID 0C6D57F2-2EDB-4DEB-9C10-53C68578B046 

}