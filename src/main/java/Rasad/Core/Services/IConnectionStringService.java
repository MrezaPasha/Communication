package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import Rasad.Core.Services.SqlServerEdition;

public interface IConnectionStringService
{
	String getProviderConnectionString();
	String getEFConnectionString();
	String getServerEdition();
	String getServerTransport();
	String getAuthScheme();
	String getServerIpAddress();
	String getServerTcpPort();
	String getServerMachinName();
	String getClientIpAddress();
	String getClientMachinName();

	String getRVSServerAddress();

	boolean getIsDatabaseServerOnCurrentComputer();

	SqlServerEdition getSqlServerEdition();

	boolean getIsSqlExpress();

	void ClearCache(String... tableNames);
	String GenerateEFConnectionString(String providerConnectionString);
}