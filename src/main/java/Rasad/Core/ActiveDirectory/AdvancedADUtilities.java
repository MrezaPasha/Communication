package Rasad.Core.ActiveDirectory;

import Rasad.Core.*;
import java.util.*;

public final class AdvancedADUtilities
{
	/** 
	 This must be used after a user identity has been validated. Otherwise use AuthenticateUserAdvanced.
	 
	 @param domain
	 @param username
	 @param password
	 @return 
	*/
	public static boolean AuthenticateUserSimple(String domain, String username, String password)
	{
		class AnonymousType
		{
			public String Domain;
			public String Username;

			public AnonymousType(String _Domain, String _Username)
			{
				Domain = _Domain;
				Username = _Username;
			}
		}
		//TLogManager.Info("Authenticate User Simple", AnonymousType(domain, username));
		try
		{
			//TODO RCOMMENT : logger
			//TLogManager.Info("User identities match - now authentciate");
			try (PrincipalContext pc = new PrincipalContext(ContextType.Domain, domain))
			{
				boolean authenticated = pc.ValidateCredentials(username, password);
				//TODO RCOMMENT : logger

				//TLogManager.Info("User simple authenticated: " + String.valueOf(authenticated));
				return authenticated;
			}
		}
		catch (RuntimeException exp)
		{
			//TODO RCOMMENT : logger

			//TLogManager.Error("User LDAP simple authentication error", exp);
			throw exp;
		}
	}
	public static boolean AuthenticateUserAdvanced(String domain, String username, String password, UUID userGuid, String userSID)
	{
		class AnonymousType
		{
			public String Domain;
			public String Username;

			public AnonymousType(String _Domain, String _Username)
			{
				Domain = _Domain;
				Username = _Username;
			}
		}
		//TODO RCOMMENT : logger

		//TLogManager.Info("Authenticate User", AnonymousType(domain, username));
		try
		{
			// 1. split domain and username part
			// 2. find user on user list
			// 3. find user using domain
			// 4. check guid and sid of found user
			// 5. if match, now authenticate
			try (PrincipalContext domainContext = new PrincipalContext(ContextType.Domain, domain))
			{
				try (var foundUser = UserPrincipal.FindByIdentity(domainContext, IdentityType.SamAccountName, username))
				{
					if (foundUser != null)
					{
						//TODO RCOMMENT : logger

						//TLogManager.Info("User found - checking identities");
						if (userGuid.equals(foundUser.Guid) && userSID.equals(foundUser.Sid.Value))
						{
							//TODO RCOMMENT : logger

							//TLogManager.Info("User identities match - now authentciate");
							try (PrincipalContext pc = new PrincipalContext(ContextType.Domain, domain))
							{
								boolean authenticated = pc.ValidateCredentials(username, password);
								//TODO RCOMMENT : logger

								//TLogManager.Info("User authenticated: " + String.valueOf(authenticated));
								return authenticated;
							}
						}
						else
						{
							//TODO RCOMMENT : logger

							//TLogManager.Warn("User identities not match.");
						}
					}
					else
					{
						//TODO RCOMMENT : logger
						//TLogManager.Info("User not found on domain");
					}
				}
			}
			return false;
		}
		catch (RuntimeException exp)
		{
			//TODO RCOMMENT : logger

			//TLogManager.Error("User LDAP authentication error", exp);
			throw exp;
		}
	}

	public static ADUserIdentity FindUserIdentity(String domain, String username)
	{
		class AnonymousType
		{
			public String Domain;
			public String Username;

			public AnonymousType(String _Domain, String _Username)
			{
				Domain = _Domain;
				Username = _Username;
			}
		}
		//TODO RCOMMENT : logger

		//TLogManager.Info("FindUserIdentity", AnonymousType(domain, username));
		ADUserIdentity retVal = null;
		try
		{
			try (PrincipalContext domainContext = new PrincipalContext(ContextType.Domain, domain))
			{
				try (var foundUser = UserPrincipal.FindByIdentity(domainContext, IdentityType.SamAccountName, username))
				{
					if (foundUser != null)
					{
						//TODO RCOMMENT : logger

						//TLogManager.Info(String.format("User found for %1$s\\%2$s", domain, username));
						if (foundUser.Guid != null && foundUser.Sid != null)
						{
							//TODO RCOMMENT : logger

							//TLogManager.Info("Found user identities valuid and available");
							retVal = new ADUserIdentity();
							retVal.setName(foundUser.Name);
							retVal.System.Guid = foundUser.Guid.Value;
							retVal.setSID(foundUser.Sid.Value);
						}
						else
						{
							//TODO RCOMMENT : logger

							//TLogManager.Warn("Found user identities is not available");
						}
					}
					else
					{
						//TODO RCOMMENT : logger

						//TLogManager.Warn("User not found");
					}
				}
			}
			return retVal;
		}
		catch (RuntimeException exp)
		{
			//TODO RCOMMENT : logger

			//TLogManager.Error("FindUserIdentity error", exp);
			throw exp;
		}
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("advapi32.dll", EntryPoint = "LookupAccountSidW", CharSet = CharSet.Unicode, CallingConvention = CallingConvention.StdCall, SetLastError = true)][return: MarshalAs(UnmanagedType.Bool)] private static extern bool LookupAccountSid(string systemName, byte[] psid, StringBuilder accountName, ref uint nameLength, StringBuilder domainName, ref uint domainLength, out SIDType accountType);
	private static native boolean LookupAccountSid(String systemName, byte[] psid, StringBuilder accountName, tangible.RefObject<Integer> nameLength, StringBuilder domainName, tangible.RefObject<Integer> domainLength, tangible.OutObject<SIDType> accountType);
	static
	{
		System.loadLibrary("advapi32.dll");
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void LookupSID(byte[] sid, out string domainName, out SIDType accountType, out String accountName)
	public static void LookupSID(byte[] sid, tangible.OutObject<String> domainName, tangible.OutObject<SIDType> accountType, tangible.OutObject<String> accountName)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint nameLength = 512;
		int nameLength = 512;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint domainLength = 512;
		int domainLength = 512;
		StringBuilder accountNameParam = new StringBuilder((int)nameLength);
		StringBuilder domainName1 = new StringBuilder((int)domainLength);
		tangible.RefObject<Integer> tempRef_nameLength = new tangible.RefObject<Integer>(nameLength);
		tangible.RefObject<Integer> tempRef_domainLength = new tangible.RefObject<Integer>(domainLength);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: if (!LookupAccountSid((string)null, sid, accountNameParam, ref nameLength, domainName1, ref domainLength, out accountType))
		if (!LookupAccountSid((String)null, sid, accountNameParam, tempRef_nameLength, domainName1, tempRef_domainLength, accountType))
		{
		domainLength = tempRef_domainLength.argValue;
		nameLength = tempRef_nameLength.argValue;
			throw new Win32Exception(Marshal.GetLastWin32Error());
		}
	else
	{
		domainLength = tempRef_domainLength.argValue;
		nameLength = tempRef_nameLength.argValue;
	}
		domainName.argValue = domainName1.toString();
		accountName.argValue = accountNameParam.toString();
	}
}