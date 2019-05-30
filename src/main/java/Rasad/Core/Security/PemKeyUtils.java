package Rasad.Core.Security;

import Rasad.Core.*;
import java.io.*;
import java.nio.file.*;

public class PemKeyUtils
{
	private static final String pemprivheader = "-----BEGIN RSA PRIVATE KEY-----";
	private static final String pemprivfooter = "-----END RSA PRIVATE KEY-----";
	private static final String pempubheader = "-----BEGIN PUBLIC KEY-----";
	private static final String pempubfooter = "-----END PUBLIC KEY-----";
	private static final String pemp8header = "-----BEGIN PRIVATE KEY-----";
	private static final String pemp8footer = "-----END PRIVATE KEY-----";
	private static final String pemp8encheader = "-----BEGIN ENCRYPTED PRIVATE KEY-----";
	private static final String pemp8encfooter = "-----END ENCRYPTED PRIVATE KEY-----";

	private static boolean verbose = false;

	public static RSACryptoServiceProvider GetRSAProviderFromPemFile(String pemfile)
	{
		return GetRSAProviderFromPemString(Files.readString(pemfile));
	}

	public static RSACryptoServiceProvider GetRSAProviderFromPemString(String pemFileText)
	{
		boolean isPrivateKeyFile = true;
		String pemstr = pemFileText.trim();
		if (pemstr.startsWith(pempubheader) && pemstr.endsWith(pempubfooter))
		{
			isPrivateKeyFile = false;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] pemkey;
		byte[] pemkey;
		if (isPrivateKeyFile)
		{
			pemkey = DecodeOpenSSLPrivateKey(pemstr);
		}
		else
		{
			pemkey = DecodeOpenSSLPublicKey(pemstr);
		}

		if (pemkey == null)
		{
			return null;
		}

		if (isPrivateKeyFile)
		{
			return DecodeRSAPrivateKey(pemkey);
		}
		else
		{
			return DecodeX509PublicKey(pemkey);
		}

	}


	//--------   Get the binary RSA PUBLIC key   --------
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static byte[] DecodeOpenSSLPublicKey(String instr)
	private static byte[] DecodeOpenSSLPublicKey(String instr)
	{
		final String pempubheader = "-----BEGIN PUBLIC KEY-----";
		final String pempubfooter = "-----END PUBLIC KEY-----";
		String pemstr = instr.trim();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] binkey;
		byte[] binkey;
		if (!pemstr.startsWith(pempubheader) || !pemstr.endsWith(pempubfooter))
		{
			return null;
		}
		StringBuilder sb = new StringBuilder(pemstr);
		sb.Replace(pempubheader, ""); //remove headers/footers, if present
		sb.Replace(pempubfooter, "");

		String pubstr = sb.toString().trim(); //get string after removing leading/trailing whitespace

		try
		{
			binkey = Convert.FromBase64String(pubstr);
		}
		catch (NumberFormatException e)
		{ //if can't b64 decode, data is not valid
			return null;
		}
		return binkey;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static RSACryptoServiceProvider DecodeX509PublicKey(byte[] x509Key)
	private static RSACryptoServiceProvider DecodeX509PublicKey(byte[] x509Key)
	{
		// encoded OID sequence for  PKCS #1 rsaEncryption szOID_RSA_RSA = "1.2.840.113549.1.1.1"
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] seqOid = { 0x30, 0x0D, 0x06, 0x09, 0x2A, 0x86, 0x48, 0x86, 0xF7, 0x0D, 0x01, 0x01, 0x01, 0x05, 0x00 };
		byte[] seqOid = {0x30, 0x0D, 0x06, 0x09, 0x2A, (byte)0x86, 0x48, (byte)0x86, (byte)0xF7, 0x0D, 0x01, 0x01, 0x01, 0x05, 0x00};
		// ---------  Set up stream to read the asn.1 encoded SubjectPublicKeyInfo blob  ------
		try (MemoryStream mem = new MemoryStream(x509Key))
		{
			try (BinaryReader binr = new BinaryReader(mem))
			{
				try
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var twobytes = binr.ReadUInt16();
					short twobytes = binr.ReadUInt16();
					switch (twobytes)
					{
						case 0x8130:
							binr.ReadByte(); //advance 1 byte
							break;
						case 0x8230:
							binr.ReadInt16(); //advance 2 bytes
							break;
						default:
							return null;
					}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
					var seq = binr.ReadBytes(15);
					if (!CompareBytearrays(seq, seqOid)) //make sure Sequence for OID is correct
					{
						return null;
					}

					twobytes = binr.ReadUInt16();
					if (twobytes == 0x8103) //data read as little endian order (actual data order for Bit String is 03 81)
					{
						binr.ReadByte(); //advance 1 byte
					}
					else if (twobytes == 0x8203)
					{
						binr.ReadInt16(); //advance 2 bytes
					}
					else
					{
						return null;
					}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var bt = binr.ReadByte();
					byte bt = binr.ReadByte();
					if (bt != 0x00) //expect null byte next
					{
						return null;
					}

					twobytes = binr.ReadUInt16();
					if (twobytes == 0x8130) //data read as little endian order (actual data order for Sequence is 30 81)
					{
						binr.ReadByte(); //advance 1 byte
					}
					else if (twobytes == 0x8230)
					{
						binr.ReadInt16(); //advance 2 bytes
					}
					else
					{
						return null;
					}

					twobytes = binr.ReadUInt16();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte lowbyte = 0x00;
					byte lowbyte = 0x00;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte highbyte = 0x00;
					byte highbyte = 0x00;

					if (twobytes == 0x8102) //data read as little endian order (actual data order for Integer is 02 81)
					{
						lowbyte = binr.ReadByte(); // read next bytes which is bytes in modulus
					}
					else if (twobytes == 0x8202)
					{
						highbyte = binr.ReadByte(); //advance 2 bytes
						lowbyte = binr.ReadByte();
					}
					else
					{
						return null;
					}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] modint = { lowbyte, highbyte, 0x00, 0x00 };
					byte[] modint = {lowbyte, highbyte, 0x00, 0x00}; //reverse byte order since asn.1 key uses big endian order
					int modsize = BitConverter.ToInt32(modint, 0);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte firstbyte = binr.ReadByte();
					byte firstbyte = binr.ReadByte();
					binr.BaseStream.Seek(-1, SeekOrigin.Current);

					if (firstbyte == 0x00)
					{ //if first byte (highest order) of modulus is zero, don't include it
						binr.ReadByte(); //skip this null byte
						modsize -= 1; //reduce modulus buffer size by 1
					}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] modulus = binr.ReadBytes(modsize);
					byte[] modulus = binr.ReadBytes(modsize); //read the modulus bytes

					if (binr.ReadByte() != 0x02) //expect an Integer for the exponent data
					{
						return null;
					}
					int expbytes = binr.ReadByte(); // should only need one byte for actual exponent data (for all useful values)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] exponent = binr.ReadBytes(expbytes);
					byte[] exponent = binr.ReadBytes(expbytes);

					// We don't really need to print anything but if we insist to...
					//showBytes("\nExponent", exponent);
					//showBytes("\nModulus", modulus);

					// ------- create RSACryptoServiceProvider instance and initialize with public key -----
					RSACryptoServiceProvider rsa = new RSACryptoServiceProvider();
					RSAParameters rsaKeyInfo = new RSAParameters();
					rsaKeyInfo.Modulus = modulus;
					rsaKeyInfo.Exponent = exponent;
					rsa.ImportParameters(rsaKeyInfo);
					return rsa;
				}
				catch (RuntimeException e)
				{
					return null;
				}
			}
		}
	}

	//------- Parses binary ans.1 RSA private key; returns RSACryptoServiceProvider  ---
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static RSACryptoServiceProvider DecodeRSAPrivateKey(byte[] privkey)
	private static RSACryptoServiceProvider DecodeRSAPrivateKey(byte[] privkey)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] MODULUS, E, D, P, Q, DP, DQ, IQ;
		byte[] MODULUS, E, D, P, Q, DP, DQ, IQ;

		// ---------  Set up stream to decode the asn.1 encoded RSA private key  ------
		ByteArrayInputStream mem = new ByteArrayInputStream(privkey);
		BinaryReader binr = new BinaryReader(mem); //wrap Memory Stream with BinaryReader for easy reading
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte bt = 0;
		byte bt = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: ushort twobytes = 0;
		short twobytes = 0;
		int elems = 0;
		try
		{
			twobytes = binr.ReadUInt16();
			if (twobytes == 0x8130) //data read as little endian order (actual data order for Sequence is 30 81)
			{
				binr.ReadByte(); //advance 1 byte
			}
			else if (twobytes == 0x8230)
			{
				binr.ReadInt16(); //advance 2 bytes
			}
			else
			{
				return null;
			}

			twobytes = binr.ReadUInt16();
			if (twobytes != 0x0102) //version number
			{
				return null;
			}
			bt = binr.ReadByte();
			if (bt != 0x00)
			{
				return null;
			}


			//------  all private key components are Integer sequences ----
			elems = GetIntegerSize(binr);
			MODULUS = binr.ReadBytes(elems);

			elems = GetIntegerSize(binr);
			E = binr.ReadBytes(elems);

			elems = GetIntegerSize(binr);
			D = binr.ReadBytes(elems);

			elems = GetIntegerSize(binr);
			P = binr.ReadBytes(elems);

			elems = GetIntegerSize(binr);
			Q = binr.ReadBytes(elems);

			elems = GetIntegerSize(binr);
			DP = binr.ReadBytes(elems);

			elems = GetIntegerSize(binr);
			DQ = binr.ReadBytes(elems);

			elems = GetIntegerSize(binr);
			IQ = binr.ReadBytes(elems);

			System.out.println("showing components ..");
			if (verbose)
			{
				showBytes("\nModulus", MODULUS);
				showBytes("\nExponent", E);
				showBytes("\nD", D);
				showBytes("\nP", P);
				showBytes("\nQ", Q);
				showBytes("\nDP", DP);
				showBytes("\nDQ", DQ);
				showBytes("\nIQ", IQ);
			}

			// ------- create RSACryptoServiceProvider instance and initialize with public key -----
			RSACryptoServiceProvider RSA = new RSACryptoServiceProvider();
			RSAParameters RSAparams = new RSAParameters();
			RSAparams.Modulus = MODULUS;
			RSAparams.Exponent = E;
			RSAparams.D = D;
			RSAparams.P = P;
			RSAparams.Q = Q;
			RSAparams.DP = DP;
			RSAparams.DQ = DQ;
			RSAparams.InverseQ = IQ;
			RSA.ImportParameters(RSAparams);
			return RSA;
		}
		catch (RuntimeException e)
		{
			return null;
		}
		finally
		{
			binr.Close();
		}
	}

	private static int GetIntegerSize(BinaryReader binr)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte bt = 0;
		byte bt = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte lowbyte = 0x00;
		byte lowbyte = 0x00;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte highbyte = 0x00;
		byte highbyte = 0x00;
		int count = 0;
		bt = binr.ReadByte();
		if (bt != 0x02) //expect integer
		{
			return 0;
		}
		bt = binr.ReadByte();

		if (bt == 0x81)
		{
			count = binr.ReadByte(); // data size in next byte
		}
		else
		{
			if (bt == 0x82)
			{
				highbyte = binr.ReadByte(); // data size in next 2 bytes
				lowbyte = binr.ReadByte();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] modint = { lowbyte, highbyte, 0x00, 0x00 };
				byte[] modint = {lowbyte, highbyte, 0x00, 0x00};
				count = BitConverter.ToInt32(modint, 0);
			}
			else
			{
				count = bt; // we already have the data size
			}
		}



		while (binr.ReadByte() == 0x00)
		{ //remove high order zeros in data
			count -= 1;
		}
		binr.BaseStream.Seek(-1, SeekOrigin.Current); //last ReadByte wasn't a removed zero, so back up a byte
		return count;
	}

	//-----  Get the binary RSA PRIVATE key, decrypting if necessary ----
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static byte[] DecodeOpenSSLPrivateKey(String instr)
	private static byte[] DecodeOpenSSLPrivateKey(String instr)
	{
		final String pemprivheader = "-----BEGIN RSA PRIVATE KEY-----";
		final String pemprivfooter = "-----END RSA PRIVATE KEY-----";
		String pemstr = instr.trim();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] binkey;
		byte[] binkey;
		if (!pemstr.startsWith(pemprivheader) || !pemstr.endsWith(pemprivfooter))
		{
			return null;
		}

		StringBuilder sb = new StringBuilder(pemstr);
		sb.Replace(pemprivheader, ""); //remove headers/footers, if present
		sb.Replace(pemprivfooter, "");

		String pvkstr = sb.toString().trim(); //get string after removing leading/trailing whitespace

		try
		{ // if there are no PEM encryption info lines, this is an UNencrypted PEM private key
			binkey = Convert.FromBase64String(pvkstr);
			return binkey;
		}
		catch (NumberFormatException e)
		{ //if can't b64 decode, it must be an encrypted private key
			//Console.WriteLine("Not an unencrypted OpenSSL PEM private key");  
		}

		StringReader str = new StringReader(pvkstr);

		//-------- read PEM encryption info. lines and extract salt -----
		if (!str.ReadLine().startsWith("Proc-Type: 4,ENCRYPTED"))
		{
			return null;
		}
		String saltline = str.ReadLine();
		if (!saltline.startsWith("DEK-Info: DES-EDE3-CBC,"))
		{
			return null;
		}
		String saltstr = saltline.substring(saltline.indexOf(",") + 1).trim();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] salt = new byte[saltstr.Length / 2];
		byte[] salt = new byte[saltstr.length() / 2];
		for (int i = 0; i < salt.length; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: salt[i] = Convert.ToByte(saltstr.Substring(i * 2, 2), 16);
			salt[i] = Byte.parseByte(saltstr.substring(i * 2, i * 2 + 2), 16);
		}
		if (!(str.ReadLine().equals("")))
		{
			return null;
		}

		//------ remaining b64 data is encrypted RSA key ----
		String encryptedstr = str.ReadToEnd();

		try
		{ //should have b64 encrypted RSA key now
			binkey = Convert.FromBase64String(encryptedstr);
		}
		catch (NumberFormatException e2)
		{ // bad b64 data.
			return null;
		}

		//------ Get the 3DES 24 byte key using PDK used by OpenSSL ----

		SecureString despswd = GetSecPswd("Enter password to derive 3DES key==>");
		//Console.Write("\nEnter password to derive 3DES key: ");
		//String pswd = Console.ReadLine();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] deskey = GetOpenSSL3deskey(salt, despswd, 1, 2);
		byte[] deskey = GetOpenSSL3deskey(salt, despswd, 1, 2); // count=1 (for OpenSSL implementation); 2 iterations to get at least 24 bytes
		if (deskey == null)
		{
			return null;
		}
		//showBytes("3DES key", deskey) ;

		//------ Decrypt the encrypted 3des-encrypted RSA private key ------
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] rsakey = DecryptKey(binkey, deskey, salt);
		byte[] rsakey = DecryptKey(binkey, deskey, salt); //OpenSSL uses salt value in PEM header also as 3DES IV
		if (rsakey != null)
		{
			return rsakey; //we have a decrypted RSA private key
		}
		else
		{
			System.out.println("Failed to decrypt RSA private key; probably wrong password.");
			return null;
		}
	}


	// ----- Decrypt the 3DES encrypted RSA private key ----------

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static byte[] DecryptKey(byte[] cipherData, byte[] desKey, byte[] IV)
	private static byte[] DecryptKey(byte[] cipherData, byte[] desKey, byte[] IV)
	{
		ByteArrayOutputStream memst = new ByteArrayOutputStream();
		TripleDES alg = TripleDES.Create();
		alg.Key = desKey;
		alg.IV = IV;
		try
		{
			CryptoStream cs = new CryptoStream(memst, alg.CreateDecryptor(), CryptoStreamMode.Write);
			cs.Write(cipherData, 0, cipherData.length);
			cs.Close();
		}
		catch (RuntimeException exc)
		{
			System.out.println(exc.getMessage());
			return null;
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] decryptedData = memst.ToArray();
		byte[] decryptedData = memst.ToArray();
		return decryptedData;
	}

	//-----   OpenSSL PBKD uses only one hash cycle (count); miter is number of iterations required to build sufficient bytes ---
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static byte[] GetOpenSSL3deskey(byte[] salt, SecureString secpswd, int count, int miter)
	private static byte[] GetOpenSSL3deskey(byte[] salt, SecureString secpswd, int count, int miter)
	{
		IntPtr unmanagedPswd = IntPtr.Zero;
		int HASHLENGTH = 16; //MD5 bytes
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] keymaterial = new byte[HASHLENGTH * miter];
		byte[] keymaterial = new byte[HASHLENGTH * miter]; //to store contatenated Mi hashed results


//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] psbytes = new byte[secpswd.Length];
		byte[] psbytes = new byte[secpswd.Length];
		unmanagedPswd = Marshal.SecureStringToGlobalAllocAnsi(secpswd);
		Marshal.Copy(unmanagedPswd, psbytes, 0, psbytes.length);
		Marshal.ZeroFreeGlobalAllocAnsi(unmanagedPswd);

		//UTF8Encoding utf8 = new UTF8Encoding();
		//byte[] psbytes = utf8.GetBytes(pswd);

		// --- contatenate salt and pswd bytes into fixed data array ---
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] data00 = new byte[psbytes.Length + salt.Length];
		byte[] data00 = new byte[psbytes.length + salt.length];
		System.arraycopy(psbytes, 0, data00, 0, psbytes.length); //copy the pswd bytes
		System.arraycopy(salt, 0, data00, psbytes.length, salt.length); //concatenate the salt bytes

		// ---- do multi-hashing and contatenate results  D1, D2 ...  into keymaterial bytes ----
		MD5 md5 = new MD5CryptoServiceProvider();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] result = null;
		byte[] result = null;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] hashtarget = new byte[HASHLENGTH + data00.Length];
		byte[] hashtarget = new byte[HASHLENGTH + data00.length]; //fixed length initial hashtarget

		for (int j = 0; j < miter; j++)
		{
			// ----  Now hash consecutively for count times ------
			if (j == 0)
			{
				result = data00; //initialize
			}
			else
			{
				System.arraycopy(result, 0, hashtarget, 0, result.length);
				System.arraycopy(data00, 0, hashtarget, result.length, data00.length);
				result = hashtarget;
				//Console.WriteLine("Updated new initial hash target:") ;
				//showBytes(result) ;
			}

			for (int i = 0; i < count; i++)
			{
				result = md5.ComputeHash(result);
			}
			System.arraycopy(result, 0, keymaterial, j * HASHLENGTH, result.length); //contatenate to keymaterial
		}
		//showBytes("Final key material", keymaterial);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] deskey = new byte[24];
		byte[] deskey = new byte[24];
		System.arraycopy(keymaterial, 0, deskey, 0, deskey.length);

		Array.Clear(psbytes, 0, psbytes.length);
		Array.Clear(data00, 0, data00.length);
		Array.Clear(result, 0, result.length);
		Array.Clear(hashtarget, 0, hashtarget.length);
		Array.Clear(keymaterial, 0, keymaterial.length);

		return deskey;
	}

	private static SecureString GetSecPswd(String prompt)
	{
		SecureString password = new SecureString();

		Console.ForegroundColor = ConsoleColor.Gray;
		System.out.print(prompt);
		Console.ForegroundColor = ConsoleColor.Magenta;

		while (true)
		{
			ConsoleKeyInfo cki = Console.ReadKey(true);
			if (cki.Key == ConsoleKey.Enter)
			{
				Console.ForegroundColor = ConsoleColor.Gray;
				System.out.println();
				return password;
			}
			else if (cki.Key == ConsoleKey.Backspace)
			{
				// remove the last asterisk from the screen...
				if (password.Length > 0)
				{
					Console.SetCursorPosition(Console.CursorLeft - 1, Console.CursorTop);
					System.out.print(" ");
					Console.SetCursorPosition(Console.CursorLeft - 1, Console.CursorTop);
					password.RemoveAt(password.Length - 1);
				}
			}
			else if (cki.Key == ConsoleKey.Escape)
			{
				Console.ForegroundColor = ConsoleColor.Gray;
				System.out.println();
				return password;
			}
			else if (Character.isLetterOrDigit(cki.KeyChar) || Character.IsSymbol(cki.KeyChar))
			{
				if (password.Length < 20)
				{
					password.AppendChar(cki.KeyChar);
					System.out.print("*");
				}
				else
				{
					Console.Beep();
				}
			}
			else
			{
				Console.Beep();
			}
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static bool CompareBytearrays(byte[] a, byte[] b)
	private static boolean CompareBytearrays(byte[] a, byte[] b)
	{
		if (a.length != b.length)
		{
			return false;
		}
		int i = 0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: foreach (byte c in a)
		for (byte c : a)
		{
			if (c != b[i])
			{
				return false;
			}
			i++;
		}
		return true;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static void showBytes(String info, byte[] data)
	private static void showBytes(String info, byte[] data)
	{
		System.out.printf("%1$s  [%2$s bytes]" + "\r\n", info, data.length);
		for (int i = 1; i <= data.length; i++)
		{
			System.out.printf("%02X  ", data[i - 1]);
			if (i % 16 == 0)
			{
				System.out.println();
			}
		}
		System.out.println("\n\n");
	}

}