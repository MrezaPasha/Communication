package Rasad.Core.Security;

import Rasad.Core.*;
import java.util.*;

/*
 * Public and Private key something like this:
 *
    static String publicKey =
@"-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDlOJu6TyygqxfWT7eLtGDwajtN
FOb9I5XRb6khyfD1Yt3YiCgQWMNW649887VGJiGr/L5i2osbl8C9+WJTeucF+S76
xFxdU6jE0NQ+Z+zEdhUTooNRaY5nZiu5PgDB0ED/ZKBUSLKL7eibMxZtMlUDHjm4
gwQco1KRMDSmXSMkDwIDAQAB
-----END PUBLIC KEY-----";
    static String privateKey =
@" -----BEGIN RSA PRIVATE KEY-----
MIICXQIBAAKBgQDlOJu6TyygqxfWT7eLtGDwajtNFOb9I5XRb6khyfD1Yt3YiCgQ
WMNW649887VGJiGr/L5i2osbl8C9+WJTeucF+S76xFxdU6jE0NQ+Z+zEdhUTooNR
aY5nZiu5PgDB0ED/ZKBUSLKL7eibMxZtMlUDHjm4gwQco1KRMDSmXSMkDwIDAQAB
AoGAfY9LpnuWK5Bs50UVep5c93SJdUi82u7yMx4iHFMc/Z2hfenfYEzu+57fI4fv
xTQ //5DbzRR/XKb8ulNv6+CHyPF31xk7YOBfkGI8qjLoq06V+FyBfDSwL8KbLyeH
m7KUZnLNQbk8yGLzB3iYKkRHlmUanQGaNMIJziWOkN+N9dECQQD0ONYRNZeuM8zd
8XJTSdcIX4a3gy3GGCJxOzv16XHxD03GW6UNLmfPwenKu+cdrQeaqEixrCejXdAF
z/7+BSMpAkEA8EaSOeP5Xr3ZrbiKzi6TGMwHMvC7HdJxaBJbVRfApFrE0/mPwmP5
rN7QwjrMY+0+AbXcm8mRQyQ1+IGEembsdwJBAN6az8Rv7QnD/YBvi52POIlRSSIM
V7SwWvSK4WSMnGb1ZBbhgdg57DXaspcwHsFV7hByQ5BvMtIduHcT14ECfcECQATe
aTgjFnqE/lQ22Rk0eGaYO80cc643BXVGafNfd9fcvwBMnk0iGX0XRsOozVt5Azil
psLBYuApa66NcVHJpCECQQDTjI2AQhFc1yRnCU/YgDnSpJVm1nASoRUnU8Jfm3Oz
uku7JUXcVpt08DFSceCEX9unCuMcT72rAQlLpdZir876
-----END RSA PRIVATE KEY-----";
 * 
 */
public final class TRSAHelper
{
	public static String Encryption(String strText, String publicKey)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var testData = strText.getBytes(java.nio.charset.StandardCharsets.UTF_8);

		try (RSACryptoServiceProvider rsa = PemKeyUtils.GetRSAProviderFromPemString(publicKey))
		{
			try
			{
				// client encrypting data with public key issued by server                    
				//rsa.FromXmlString(publicKey.ToString());

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
				var encryptedData = rsa.Encrypt(testData, true);

				String base64Encrypted = Convert.ToBase64String(encryptedData);

				return base64Encrypted;
			}
			finally
			{
				rsa.PersistKeyInCsp = false;
			}
		}
	}

	public static String Decryption(String strText, String privateKey)
	{
		//var privateKey = "<RSAKeyValue><Modulus>p/RhkL/JQWivd+QyM++1LRA9WlGkQXnJaO1w/+QB+80A712oHaLnWvDHV7zZuuTYXGGcmg4lnZ/2ZyQpJHUiz4r/D+jpjaFEjAHPpFSj9N7wNz+jpGkK0D3Jy7uO/V3kp/9+11v9ENPIaaHQQ/ShXmfGdESQpN+Py6qr1FknCyE=</Modulus><Exponent>AQAB</Exponent><P>3NIdQ397K8jpROwCLT5iI+tWgf5ZEFBLif44Oyr15zMTzEdlcMHGzraopWTjuWlVqZ4VtiHlN9BGdygVnyxLww==</P><Q>wrYwtCdhbmXtKyjSCU7ibTZQPOUhD6WpDBsn1gpMBtnvctTsS+YVnyQkntY0hxAVYXltwg4NlP7gb4Vfi0czSw==</Q><DP>nqNsVpN8/2Wk/9i1b5+Djd6CFX2OqoOk4Qv7hbZFVsWBEZ6xJ6Jumw7qdXfXido8qlBhtKkBLWm5Vd08O0/tVQ==</DP><DQ>eE4zx4wxghHOOYWOSRwJgSs25nk4NT/JNGau9WwFT5JmhZATtE2kfGDLk6yMZgtE+qYZb/ZY4/+bUqyYvSiwAw==</DQ><InverseQ>dEU63RGTD+YYGtp5eC+t0tGAc/uXPNaN+nN5SZ3JkVWP0Q60JcFTcjW+J5Bthj0gYQ1b63biflr/vDuc+C7ldg==</InverseQ><D>dHQlJhlksT6l06pdCNffNpS5BaPwERohheiE3li461+0k0PMKmhmpA6pGXOvQEAmqL9htwuyFz3vuoo/ILE7Za4rFMR06owwCEo4Ts9eCfaZEGbFV0x+8uKtl6lxOF6PFgPzDBVzHbbC7xWQNdJflQUho4MCVNZdzqC7AkI+ahU=</D></RSAKeyValue>";

		try (RSACryptoServiceProvider rsa = PemKeyUtils.GetRSAProviderFromPemString(privateKey))
		{
			try
			{
				String base64Encrypted = strText;

				// server decrypting data with private key                    
				//rsa.FromXmlString(privateKey);

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
				var resultBytes = Convert.FromBase64String(base64Encrypted);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
				var decryptedBytes = rsa.Decrypt(resultBytes, true);
				String decryptedData = Encoding.UTF8.GetString(decryptedBytes);
				return decryptedData.toString();
			}
			finally
			{
				rsa.PersistKeyInCsp = false;
			}
		}
	}

	public static String LargeCustomEncryption(String strText, String publicKey)
	{
		// generate symmetric key
		// hash
		String encKey = UUID.NewGuid().toString();
		String encKeyEncrypted = Encryption(encKey, publicKey);
		StringBuilder retVal = new StringBuilder();
		retVal.append(encKeyEncrypted.length().toString("000"));
		retVal.append(encKeyEncrypted);
		String actualDataEncrypted = StringCipher.Encrypt(strText, encKey);
		retVal.append(actualDataEncrypted);
		return retVal.toString();
	}

	public static String LargeCustomDecryption(String strText, String privateKey)
	{
		int encKeyEncryptedLen = Integer.parseInt(strText.substring(0, 3));
		String encKeyEncrypted = strText.substring(3, 3 + encKeyEncryptedLen);
		String encKey = Decryption(encKeyEncrypted, privateKey);
		String actualDataEncrypted = strText.substring(3 + encKeyEncrypted.length());
		String actualData = StringCipher.Decrypt(actualDataEncrypted, encKey);
		return actualData;
	}
}