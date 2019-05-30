package Rasad.Core.Security;

import Rasad.Core.*;

public final class TSecureConnectionInitializer
{
	static
	{
		// Allow https connection
		ServicePointManager.SecurityProtocol = SecurityProtocolType.Ssl3;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		ServicePointManager.ServerCertificateValidationCallback += ValidateRemoteCertificate;
		//System.Net.ServicePointManager.ServerCertificateValidationCallback +=
		//    (send, certificate, chain, sslPolicyErrors) =>
		//    {
		//        return true;
		//    };
	}

	private static boolean ValidateRemoteCertificate(Object sender, X509Certificate cert, X509Chain chain, SslPolicyErrors error)
	{
		// If the certificate is a valid, signed certificate, return true.
		if (error == System.Net.Security.SslPolicyErrors.None)
		{
			return true;
		}

		try
		{
			TLogManager.Warn(String.format("X509Certificate [%1$s] Policy Error: '%2$s'", cert.Subject, error.toString()));
		}
		catch (RuntimeException exp)
		{
			TLogManager.Error(exp);
		}

		//return false;
		return true; // -> always return true
	}

	public static void Initialize()
	{
	}
}