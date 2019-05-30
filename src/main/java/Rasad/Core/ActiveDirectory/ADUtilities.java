package main.java.Rasad.Core.ActiveDirectory;

import com.sun.security.auth.UserPrincipal;
import main.java.Rasad.Core.ActiveDirectory.ADUser;

import java.util.*;
import java.io.*;
import java.nio.file.*;


public final class ADUtilities
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods
	private static void AddSystemLog(String msg, EventLogEntryType logType)
	{
		//TODO MRCOMMENT : logger
		//TLogManager.Info("ADService (" + logType.toString() + "): " + msg);
		//try
		//{
		//    using (EventLog eventLog = new EventLog("Application"))
		//    {
		//        eventLog.Source = "ADService";
		//        eventLog.WriteEntry(msg, logType);
		//    }
		//}
		//catch
		//{ }
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if(_FOR_TEST_)
	private static ADUser[] GenerateTestUsers()
	{
		ArrayList<ADUser> retVal = new ArrayList<ADUser>();

		String[] guidList = new String[] {"9D5434AD-C3E5-4315-94B4-A6E7BB31C916", "61EF1791-D4C2-4B32-B406-684AD81441EC", "2BE41D45-DF2B-4B38-A4E9-82888B7908FB", "5F995FF3-5078-4493-B72C-F13665EF963C", "28E3A35B-EB8F-4670-96C7-9F251C823A08", "D680AF9D-01F4-400F-A98A-34E90F011A1C", "BD55D6FB-E1F4-4A16-8B5B-8066B81F91AE", "247446CA-8CF5-4B07-8148-3B340B511677", "1D0CF5C1-46A6-4C8D-A448-74D7E3CE7D2D", "A6093610-9147-4C93-975A-55A547130F54"};
		for (int i = 1; i <= 10; i++)
		{
			ADUser tempVar = new ADUser();
			tempVar.setDepartment("دپارتمان");
			tempVar.setDescription("");
			tempVar.setDisplayName("کاربر دامین " + String.valueOf(i));
			tempVar.setEmail("domainuser" + String.valueOf(i) + "@rasad.com");
			tempVar.setEmployeeId("");
			tempVar.setEnabled(true);
			tempVar.setEnabledHasValue(true);
			tempVar.setFirstName("کاربر");
			tempVar.setLastName("دامین " + String.valueOf(i));
			tempVar.setGUID(guidList[i - 1]);
			tempVar.setMiddleName("");
			tempVar.setName("کاربر دامین " + String.valueOf(i));
			tempVar.setTitle("کاربر توسعه");
			tempVar.setUsername("domainuser" + String.valueOf(i));
			tempVar.setUserPrincipalName("domainuser" + String.valueOf(i));
			tempVar.setVoiceTelephoneNumber("");
			retVal.add(tempVar);
		}
		return retVal.toArray(new ADUser[0]);
	}

	private static ADUserDetails GenerateTestUserDetails(String username)
	{
		ADUserDetails tempVar = new ADUserDetails();
		tempVar.setCompany("رصد");
		tempVar.setDepartment("دپارتمان");
		tempVar.setFax("000");
		tempVar.setHomePhone("000");
		tempVar.setIPPhone("");
		tempVar.setMobile("");
		tempVar.setPager("");
		tempVar.setPostalCode("");
		tempVar.setTelephoneNumber("");
		tempVar.setTitle("");
		return tempVar;
	}
//#endif

	private static ADUser[] GetUserListFilterInternal(String domainName, String domainController, String username, String password, String searchString, boolean usernameFilter, boolean exactUsernameFilter)
	{
		ArrayList<ADUser> retVal = new ArrayList<ADUser>();
		try
		{
			System.out.println("GetUserListFilter Request: " + domainName + ", " + username + ", " + searchString);

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if(_FOR_TEST_)
			if (exactUsernameFilter)
			{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
				retVal.addAll(Arrays.asList(GenerateTestUsers().Where(s = searchString.equals(> s.Username)).ToArray()));
			}
			else
			{
				searchString = (searchString == null ? "" : searchString.toLowerCase());
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
				retVal.addAll(Arrays.asList(GenerateTestUsers().Where(s -> s.DisplayName.toLowerCase().contains(searchString) || s.Username.Contains(searchString) || s.FirstName.Contains(searchString) || s.LastName.Contains(searchString)).ToArray()));
			}
//#else
			if (tangible.StringHelper.isNullOrWhiteSpace(searchString))
			{
				return new ADUser[] { };
			}

			PrincipalContext pc;
			if (!tangible.StringHelper.isNullOrWhiteSpace(domainController) || !tangible.StringHelper.isNullOrWhiteSpace(username))
			{
				String domainContact = tangible.StringHelper.isNullOrWhiteSpace(domainController) ? domainName : domainController;
				pc = new PrincipalContext(ContextType.Domain, domainContact, username, password);
			}
			else
			{
				pc = new PrincipalContext(ContextType.Domain, domainName);
			}
			//using (PrincipalContext pc = new PrincipalContext(ContextType.Domain, domainContact, username, password))
			try
			{
				try (UserPrincipa up = new UserPrincipal(pc))
				{
					if (usernameFilter)
					{
						if (exactUsernameFilter)
						{
							up.SamAccountName = searchString;
						}
						else
						{
							up.SamAccountName = "*" + searchString + "*";
						}
					}
					else
					{
						up.Name = "*" + searchString + "*";
					}
					try (PrincipalSearcher search = new PrincipalSearcher())
					{
						search.QueryFilter = up;
						PrincipalSearchResult<Principal> results = search.FindAll();
						AddSystemLog(results.Count().toString(), EventLogEntryType.Error);
						for (UserPrincipal result : results)
						{
							if (result.DisplayName != null && result.Guid != null)
							{
								DirectoryEntry de = (DirectoryEntry)result.GetUnderlyingObject();
								String title = "";
								String department = "";
								if (de != null)
								{
									title = (String)de.Properties[ADProperties.TITLE].Value;
									department = (String)de.Properties[ADProperties.DEPARTMENT].Value;
								}
								retVal.add(new ADUser(result.Guid.Value, result.SamAccountName, result.GivenName, result.Surname, result.DisplayName, result.EmailAddress, result.Description, result.EmployeeId, result.Name, result.MiddleName, result.UserPrincipalName, result.VoiceTelephoneNumber, result.Enabled, title, department, result.Sid.Value));
							}
						}
					}
				}
			}
			finally
			{
				if (pc != null)
				{
					pc.Dispose();
				}
			}
//#endif
		}
		catch (RuntimeException exp)
		{
			AddSystemLog("Error in GetUserList: " + exp.getMessage(), EventLogEntryType.Error);
			throw exp;
		}
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		retVal = retVal.OrderBy(s -> s.Username).ToList();

		return retVal.toArray(new ADUser[0]);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Methods
	public static boolean Authenticate(String domainName, String domainController, String username, String password)
	{
		try
		{
			System.out.println("Authenticate Request: " + domainName + ", " + username);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if(_FOR_TEST_)
			if (GenerateTestUsers().Any(s = username.toLowerCase().equals(> s.Username.toLowerCase())) && password.equals("123"))
			{
				return true;
			}
			else
			{
				return false;
			}
//#else
			String domainContact = tangible.StringHelper.isNullOrWhiteSpace(domainController) ? domainName : domainController;
			try (PrincipalContext pc = new PrincipalContext(ContextType.Domain, domainContact))
			{
				boolean authenticated = pc.ValidateCredentials(username, password);
				System.out.println("         => " + (authenticated ? "Successful" : "Failed"));
				return authenticated;
			}
//#endif
		}
		catch (RuntimeException exp)
		{
			AddSystemLog("Error in Authenticate: " + exp.getMessage(), EventLogEntryType.Error);
			throw exp;
			return false;
		}
	}

	public static ADUser[] GetUserList(String domainName, String domainController, String username, String password)
	{
		ArrayList<ADUser> retVal = new ArrayList<ADUser>();
		try
		{
			System.out.println("GetUserList Request: " + domainName + ", " + username);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if(_FOR_TEST_)
			retVal.addAll(Arrays.asList(GenerateTestUsers().ToArray()));
//#else
			String domainContact = tangible.StringHelper.isNullOrWhiteSpace(domainController) ? domainName : domainController;
			try (PrincipalContext pc = new PrincipalContext(ContextType.Domain, domainContact, username, password))
			{
				try (UserPrincipal up = new UserPrincipal(pc))
				{
					try (PrincipalSearcher search = new PrincipalSearcher(up))
					{
						search.QueryFilter = up;
						for (UserPrincipal result : search.FindAll())
						{
							if (result.DisplayName != null && result.Guid != null)
							{
								DirectoryEntry de = (DirectoryEntry)result.GetUnderlyingObject();
								//de.RefreshCache(new String[] { ADProperties.TITLE });
								String title = "";
								String department = "";
								if (de != null)
								{
									title = (String)de.Properties[ADProperties.TITLE].Value;
									department = (String)de.Properties[ADProperties.DEPARTMENT].Value;
								}
								retVal.add(new ADUser(result.Guid.Value, result.SamAccountName, result.GivenName, result.Surname, result.DisplayName, result.EmailAddress, result.Description, result.EmployeeId, result.Name, result.MiddleName, result.UserPrincipalName, result.VoiceTelephoneNumber, result.Enabled, title, department, result.Sid.Value));
							}
						}
					}
				}
			}
//#endif
		}
		catch (RuntimeException exp)
		{
			AddSystemLog("Error in GetUserList: " + exp.getMessage(), EventLogEntryType.Error);
			throw exp;
		}
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		retVal = retVal.OrderBy(s -> s.Username).ToList();

		return retVal.toArray(new ADUser[0]);
	}

	public static ADUser[] GetUserListFilterComprehensive(String domainName, String domainController, String username, String password, String searchString)
	{
		Rasad.Core.ActiveDirectory.ADUser[] results1 = GetUserListFilter(domainName, domainController, username, password, searchString);
		Rasad.Core.ActiveDirectory.ADUser[] results2 = GetUserListUsernameFilter(domainName, domainController, username, password, searchString);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		return results1.Union(results2).GroupBy(s -> s.Username.toLowerCase()).Select(s -> s.First()).OrderBy(s -> s.Username).ToArray();
	}

	public static ADUser[] GetUserListFilter(String domainName, String domainController, String username, String password, String searchString)
	{
		return GetUserListFilterInternal(domainName, domainController, username, password, searchString, false, false);
	}

	public static ADUser[] GetUserListUsernameFilter(String domainName, String domainController, String username, String password, String requestedIdentifier)
	{
		return GetUserListFilterInternal(domainName, domainController, username, password, requestedIdentifier, true, false);
	}

	public static ADUser GetUser(String domainName, String domainController, String username, String password, String requestedIdentifier)
	{
		System.out.println("GetUser Request: " + domainName + ", " + username + ", Requested ID: " + requestedIdentifier);
		//return GetUserList(domainName, username, password).FirstOrDefault(s => s.GUID.ToLower() == requestedIdentifier.ToLower());
		// it is better to use guid, but now on;y use username
		return GetUserListFilterInternal(domainName, domainController, username, password, requestedIdentifier, true, true).FirstOrDefault(s = requestedIdentifier.toLowerCase().equals(> s.Username.toLowerCase()));
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] GetUserThumbnail(String domainName, String domainController, String username, String password, String requestedIdentifier)
	public static byte[] GetUserThumbnail(String domainName, String domainController, String username, String password, String requestedIdentifier)
	{
		try
		{
			System.out.println("GetUserThumbnail Request: " + domainName + ", " + username + ", " + requestedIdentifier);
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if(_FOR_TEST_)
			if (GenerateTestUsers().Any(s = requestedIdentifier.equals(> s.Username)))
			{
				return File.ReadAllBytes(Paths.get((new File(Assembly.GetExecutingAssembly().Location)).getParent()).resolve("UserNoImage.jpg").toString());
			}
//#else
			if (tangible.StringHelper.isNullOrWhiteSpace(requestedIdentifier))
			{
				return null;
			}

			PrincipalContext pc;
			if (!tangible.StringHelper.isNullOrWhiteSpace(domainController) || !tangible.StringHelper.isNullOrWhiteSpace(username))
			{
				String domainContact = tangible.StringHelper.isNullOrWhiteSpace(domainController) ? domainName : domainController;
				pc = new PrincipalContext(ContextType.Domain, domainContact, username, password);
			}
			else
			{
				pc = new PrincipalContext(ContextType.Domain, domainName);
			}

			//using (PrincipalContext pc = new PrincipalContext(ContextType.Domain, domainContact, username, password))
			try
			{
				try (UserPrincipal up = new UserPrincipal(pc))
				{
					up.SamAccountName = requestedIdentifier;
					try (PrincipalSearcher search = new PrincipalSearcher())
					{
						search.QueryFilter = up;
						PrincipalSearchResult<Principal> results = search.FindAll();
						AddSystemLog(results.Count().toString(), EventLogEntryType.Error);
						for (UserPrincipal result : results)
						{
							if (result.DisplayName != null && result.Guid != null)
							{
								if (result.SamAccountName.toLowerCase().equals(requestedIdentifier.toLowerCase()))
								{
									Object tempVar = result.GetUnderlyingObject();
									DirectoryEntry de = (tempVar instanceof DirectoryEntry ? (DirectoryEntry)tempVar : null);
									if (de != null)
									{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] data = de.Properties["thumbnailPhoto"].Value instanceof byte[] ? (byte[])de.Properties["thumbnailPhoto"].Value : null;
										byte[] data = de.Properties["thumbnailPhoto"].Value instanceof byte[] ? (byte[])de.Properties["thumbnailPhoto"].Value : null;
										if (data != null)
										{
											return data;
										}
										else
										{
											AddSystemLog("DirectoryEntry not available for " + requestedIdentifier, EventLogEntryType.Warning);
										}
									}
								}
							}
						}
					}
				}
			}
			finally
			{
				if (pc != null)
				{
					pc.Dispose();
				}
			}
//#endif
		}
		catch (RuntimeException exp)
		{
			AddSystemLog("Error in GetUserThumbnail: " + exp.getMessage(), EventLogEntryType.Error);
			throw exp;
		}
		return null;
	}

	public static ADUserDetails GetUserDetails(String domainName, String domainController, String username, String password, String requestedIdentifier)
	{
		try
		{
			System.out.println("GetUserDetails Request: " + domainName + ", " + username + ", " + requestedIdentifier);
			if (tangible.StringHelper.isNullOrWhiteSpace(requestedIdentifier))
			{
				return null;
			}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if(_FOR_TEST_)
			if (GenerateTestUsers().Any(s = username.equals(> s.Username)))
			{
				return GenerateTestUserDetails(username);
			}
//#else
			String domainContact = tangible.StringHelper.isNullOrWhiteSpace(domainController) ? domainName : domainController;
			try (PrincipalContext pc = new PrincipalContext(ContextType.Domain, domainContact, username, password))
			{
				try (UserPrincipal up = new UserPrincipal(pc))
				{
					up.SamAccountName = requestedIdentifier;
					try (PrincipalSearcher search = new PrincipalSearcher())
					{
						search.QueryFilter = up;
						PrincipalSearchResult<Principal> results = search.FindAll();
						AddSystemLog(results.Count().toString(), EventLogEntryType.Error);
						for (UserPrincipal result : results)
						{
							if (result.DisplayName != null && result.Guid != null)
							{
								if (result.SamAccountName.toLowerCase().equals(requestedIdentifier.toLowerCase()))
								{
									Object tempVar = result.GetUnderlyingObject();
									DirectoryEntry de = (tempVar instanceof DirectoryEntry ? (DirectoryEntry)tempVar : null);
									if (de != null)
									{
										String company = (String)de.Properties["company"].Value;
										String title = (String)de.Properties[ADProperties.TITLE].Value;
										String department = (String)de.Properties["department"].Value;
										String telephoneNumber = (String)de.Properties["telephoneNumber"].Value;
										String homePhone = (String)de.Properties[ADProperties.HOMEPHONE].Value;
										String pager = (String)de.Properties[ADProperties.PAGER].Value;
										String mobile = (String)de.Properties[ADProperties.MOBILE].Value;
										String fax = (String)de.Properties[ADProperties.FAX].Value;
										String ipphone = (String)de.Properties["ipphone"].Value;
										String postalCode = (String)de.Properties[ADProperties.POSTALCODE].Value;

										ADUserDetails ret = new ADUserDetails(company, title, department, telephoneNumber, homePhone, pager, mobile, fax, ipphone, postalCode);
										return ret;
									}
								}
							}
						}
					}
				}
			}
//#endif
		}
		catch (RuntimeException exp)
		{
			AddSystemLog("Error in GetUserThumbnail: " + exp.getMessage(), EventLogEntryType.Error);
			throw exp;
		}
		return null;
	}

	public static String GetCurrentUserDomain()
	{
		try (var domain = Domain.GetCurrentDomain())
		{
			if (domain != null)
			{
				return domain.Name;
			}
			else
			{
				return null;
			}
		}
	}

	private static String NormalizeDomainName(String domainName)
	{
		// only get main domain name
		int subDomainCount = domainName.Count(s -> s == '.');
		String actualDomainName;
		if (subDomainCount > 0)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var parts = domainName.split(new char[] {'.'}, StringSplitOptions.RemoveEmptyEntries);
			if (parts.Length >= 2)
			{
				actualDomainName = parts[parts.Length - 2];
			}
			else
			{
				actualDomainName = domainName;
			}
		}
		else
		{
			actualDomainName = domainName;
		}
		return actualDomainName;
	}

	public static String GetCurrentMachineDomain()
	{
		return GetCurrentMachineDomain(true);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static String GetCurrentMachineDomain(bool normalizeDomainName = true)
	public static String GetCurrentMachineDomain(boolean normalizeDomainName)
	{
		TLogManager.Info("GetCurrentMachineDomain");
		try (var domain = Domain.GetComputerDomain())
		{
			if (domain != null)
			{
				TLogManager.Info("Domain Name: " + domain.Name);
				if (normalizeDomainName)
				{
					String ret = NormalizeDomainName(domain.Name);
					TLogManager.Info("Top level domain: " + ret);
					return ret;
				}
				else
				{
					return domain.Name;
				}
			}
			else
			{
				TLogManager.Info("No domain detected");
				return null;
			}
		}
	}

	public static TDomainAndControllerInfo GetCurrentMachineDomainAndController()
	{
		return GetCurrentMachineDomainAndController(true);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static TDomainAndControllerInfo GetCurrentMachineDomainAndController(bool normalizeDomainName = true)
	public static TDomainAndControllerInfo GetCurrentMachineDomainAndController(boolean normalizeDomainName)
	{
		TDomainAndControllerInfo retVal = new TDomainAndControllerInfo();
		try (var domain = Domain.GetComputerDomain())
		{
			if (domain != null)
			{
				if (normalizeDomainName)
				{
					retVal.setDomainName(NormalizeDomainName(domain.Name));
				}
				else
				{
					retVal.setDomainName(domain.Name);
				}
				try
				{
					if (domain.DomainControllers.Count > 0)
					{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
						var c = domain.DomainControllers[0];
						retVal.setDomainControllerName(c.Name);
						retVal.setDomainControllerIPAddress(c.IPAddress);
					}
				}
				catch (RuntimeException exp)
				{
					TLogManager.Error("Error ignored in getting domain controller for " + retVal.getDomainName(), exp);
				}
			}
		}
		return retVal;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}