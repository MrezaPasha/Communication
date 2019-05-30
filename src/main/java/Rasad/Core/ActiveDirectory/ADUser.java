package main.java.Rasad.Core.ActiveDirectory;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract] public class ADUser
public class ADUser
{
	public ADUser()
	{
		this(UUID.randomUUID(), "", "", "", "", "", "", "", "", "", "", "", false, "", "", "");
	}

	public ADUser(UUID guid, String username, String firstName, String lastName, String displayName, String email, String description, String employeeId, String name, String middleName, String userPrincipalName, String voiceTelephoneNumber, Boolean enabled, String title, String department, String sid)
	{
		this.setGUID(guid);
		this.setUsername(username);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDisplayName(displayName);
		this.setEmail(email);
		this.setDescription(description);
		this.setEmployeeId(employeeId);
		this.setName(name);
		this.setMiddleName(middleName);
		this.setUserPrincipalName(userPrincipalName);
		this.setVoiceTelephoneNumber(voiceTelephoneNumber);
		this.setEnabledHasValue(enabled != null);
		this.setEnabled((enabled != null ? enabled.booleanValue() : true));
		this.setTitle(title);
		this.setDepartment(department);
		this.setSID(sid);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public Guid GUID {get;set;}
	private UUID GUID;
	public final UUID getGUID()
	{
		return GUID;
	}
	public final void setGUID(UUID value)
	{
		GUID = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Username {get;set;}
	private String Username;
	public final String getUsername()
	{
		return Username;
	}
	public final void setUsername(String value)
	{
		Username = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String FirstName {get;set;}
	private String FirstName;
	public final String getFirstName()
	{
		return FirstName;
	}
	public final void setFirstName(String value)
	{
		FirstName = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String LastName {get;set;}
	private String LastName;
	public final String getLastName()
	{
		return LastName;
	}
	public final void setLastName(String value)
	{
		LastName = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String DisplayName {get;set;}
	private String DisplayName;
	public final String getDisplayName()
	{
		return DisplayName;
	}
	public final void setDisplayName(String value)
	{
		DisplayName = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Email {get;set;}
	private String Email;
	public final String getEmail()
	{
		return Email;
	}
	public final void setEmail(String value)
	{
		Email = value;
	}

	// more extra fields
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Description {get;set;}
	private String Description;
	public final String getDescription()
	{
		return Description;
	}
	public final void setDescription(String value)
	{
		Description = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String EmployeeId {get;set;}
	private String EmployeeId;
	public final String getEmployeeId()
	{
		return EmployeeId;
	}
	public final void setEmployeeId(String value)
	{
		EmployeeId = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Name {get;set;}
	private String Name;
	public final String getName()
	{
		return Name;
	}
	public final void setName(String value)
	{
		Name = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String MiddleName {get;set;}
	private String MiddleName;
	public final String getMiddleName()
	{
		return MiddleName;
	}
	public final void setMiddleName(String value)
	{
		MiddleName = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String UserPrincipalName {get;set;}
	private String UserPrincipalName;
	public final String getUserPrincipalName()
	{
		return UserPrincipalName;
	}
	public final void setUserPrincipalName(String value)
	{
		UserPrincipalName = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String VoiceTelephoneNumber {get;set;}
	private String VoiceTelephoneNumber;
	public final String getVoiceTelephoneNumber()
	{
		return VoiceTelephoneNumber;
	}
	public final void setVoiceTelephoneNumber(String value)
	{
		VoiceTelephoneNumber = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public bool EnabledHasValue {get;set;}
	private boolean EnabledHasValue;
	public final boolean getEnabledHasValue()
	{
		return EnabledHasValue;
	}
	public final void setEnabledHasValue(boolean value)
	{
		EnabledHasValue = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public bool Enabled {get;set;}
	private boolean Enabled;
	public final boolean getEnabled()
	{
		return Enabled;
	}
	public final void setEnabled(boolean value)
	{
		Enabled = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Title {get;set;}
	private String Title;
	public final String getTitle()
	{
		return Title;
	}
	public final void setTitle(String value)
	{
		Title = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Department {get;set;}
	private String Department;
	public final String getDepartment()
	{
		return Department;
	}
	public final void setDepartment(String value)
	{
		Department = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String SID {get;set;}
	private String SID;
	public final String getSID()
	{
		return SID;
	}
	public final void setSID(String value)
	{
		SID = value;
	}

	@Override
	public String toString()
	{
		return getUsername() + " [" + getFirstName() + ", " + getLastName() + "]: " + getDisplayName() + " - GUID=" + getGUID() +
			" - Email=" + getEmail() + " - Description=" + getDescription();
	}
}