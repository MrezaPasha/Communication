package main.java.Rasad.Core.Services.BackupRestore;

import ProtoBuf.*;
import Rasad.Core.*;
import Rasad.Core.Services.*;
import main.java.Rasad.Core.TViewModelBase;

import java.io.*;
import java.time.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [KnownType(typeof(DateTime))][DataContract][ProtoContract][ProtoInclude(1000, typeof(TResponseBackupFileList))] public class TBackupItem : TViewModelBase
public class TBackupItem extends TViewModelBase
{
	private TBackupItem()
	{
	} // for protobuf
	public TBackupItem(File file, LocalDateTime backupDateTime, String creatorFullName, String description)
	{
		this.setCreateDate(backupDateTime);
		this.setFileSize(file.length());
		this.setFilePath(file.getPath());
		this.setCreatorFullName(creatorFullName);
		this.setDescription(description);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(1)] public string Description
	public final String getDescription()
	{
		return GetValue<String>("Description");
	}
	public final void setDescription(String value)
	{
		SetValue(value, "Description");
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(2)] public DateTime CreateDate
	public final LocalDateTime getCreateDate()
	{
		return GetValue<LocalDateTime>("CreateDate");
	}
	public final void setCreateDate(LocalDateTime value)
	{
		SetValue(value, "CreateDate");
	}

	public final String getCreateDateRelative()
	{
		return getCreateDate().ToRelativeDate();
	}

	public final String getCreateDateText()
	{
		return getCreateDate().ToShamsiString() + " - " + getCreateDateRelative();
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(3)] public string CreatorFullName
	public final String getCreatorFullName()
	{
		return GetValue<String>("CreatorFullName");
	}
	public final void setCreatorFullName(String value)
	{
		SetValue(value, "CreatorFullName");
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(4)] public Int64 FileSize
	public final long getFileSize()
	{
		return GetValue<Long>("FileSize");
	}
	public final void setFileSize(long value)
	{
		SetValue(value, "FileSize");
	}

	public final String getFileSizeText()
	{
		return getFileSize().ToFileSize();
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [ProtoMember(5)] public string FilePath
	public final String getFilePath()
	{
		return GetValue<String>("FilePath");
	}
	public final void setFilePath(String value)
	{
		SetValue(value, "FilePath");
	}


}