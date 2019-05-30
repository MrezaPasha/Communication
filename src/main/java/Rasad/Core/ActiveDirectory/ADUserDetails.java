package main.java.Rasad.Core.ActiveDirectory;


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract] public class ADUserDetails
public class ADUserDetails
{
	public ADUserDetails()
	{
		this("", "", "", "", "", "", "", "", "", "");
	}

	public ADUserDetails(String company, String title, String department, String telephoneNumber, String homePhone, String pager, String mobile, String fax, String ipPhone, String postalCode)
	{
		this.setCompany(company);
		this.setTitle(title);
		this.setDepartment(department);
		this.setTelephoneNumber(telephoneNumber);
		this.setHomePhone(homePhone);
		this.setPager(pager);
		this.setMobile(mobile);
		this.setFax(fax);
		this.setIPPhone(ipPhone);
		this.setPostalCode(postalCode);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Company {get;set;}
	private String Company;
	public final String getCompany()
	{
		return Company;
	}
	public final void setCompany(String value)
	{
		Company = value;
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
//ORIGINAL LINE: [DataMember] public String TelephoneNumber {get;set;}
	private String TelephoneNumber;
	public final String getTelephoneNumber()
	{
		return TelephoneNumber;
	}
	public final void setTelephoneNumber(String value)
	{
		TelephoneNumber = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String HomePhone {get;set;}
	private String HomePhone;
	public final String getHomePhone()
	{
		return HomePhone;
	}
	public final void setHomePhone(String value)
	{
		HomePhone = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Pager {get;set;}
	private String Pager;
	public final String getPager()
	{
		return Pager;
	}
	public final void setPager(String value)
	{
		Pager = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Mobile {get;set;}
	private String Mobile;
	public final String getMobile()
	{
		return Mobile;
	}
	public final void setMobile(String value)
	{
		Mobile = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String Fax {get;set;}
	private String Fax;
	public final String getFax()
	{
		return Fax;
	}
	public final void setFax(String value)
	{
		Fax = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String IPPhone {get;set;}
	private String IPPhone;
	public final String getIPPhone()
	{
		return IPPhone;
	}
	public final void setIPPhone(String value)
	{
		IPPhone = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public String PostalCode {get;set;}
	private String PostalCode;
	public final String getPostalCode()
	{
		return PostalCode;
	}
	public final void setPostalCode(String value)
	{
		PostalCode = value;
	}
}