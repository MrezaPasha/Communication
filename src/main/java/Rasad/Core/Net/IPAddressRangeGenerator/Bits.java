package Rasad.Core.Net.IPAddressRangeGenerator;

import Rasad.Core.*;
import Rasad.Core.Net.*;

public final class Bits
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] Not(byte[] bytes)
	public static byte[] Not(byte[] bytes)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return bytes.Select(b => (byte)~b).ToArray();
		return bytes.Select(b -> (byte)~b).ToArray();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] And(byte[] A, byte[] B)
	public static byte[] And(byte[] A, byte[] B)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return A.Zip(B, (a, b) => (byte)(a & b)).ToArray();
		return A.Zip(B, (a, b) -> (byte)(a & b)).ToArray();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] Or(byte[] A, byte[] B)
	public static byte[] Or(byte[] A, byte[] B)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return A.Zip(B, (a, b) => (byte)(a | b)).ToArray();
		return A.Zip(B, (a, b) -> (byte)(a | b)).ToArray();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static bool GE(byte[] A, byte[] B)
	public static boolean GE(byte[] A, byte[] B)
	{
		return A.Zip(B, (a, b) -> a == b ? 0 : a < b ? 1 : -1).SkipWhile(c -> c == 0).FirstOrDefault() >= 0;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static bool LE(byte[] A, byte[] B)
	public static boolean LE(byte[] A, byte[] B)
	{
		return A.Zip(B, (a, b) -> a == b ? 0 : a < b ? 1 : -1).SkipWhile(c -> c == 0).FirstOrDefault() <= 0;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] GetBitMask(int sizeOfBuff, int bitLen)
	public static byte[] GetBitMask(int sizeOfBuff, int bitLen)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var maskBytes = new byte[sizeOfBuff];
		byte[] maskBytes = new byte[sizeOfBuff];
		int bytesLen = bitLen / 8;
		int bitsLen = bitLen % 8;
		for (int i = 0; i < bytesLen; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: maskBytes[i] = 0xff;
			maskBytes[i] = (byte)0xff;
		}
		if (bitsLen > 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: maskBytes[bytesLen] = (byte)~Enumerable.Range(1, 8 - bitsLen).Select(n => 1 << n - 1).Aggregate((a, b) => a | b);
			maskBytes[bytesLen] = (byte)~Enumerable.Range(1, 8 - bitsLen).Select(n -> 1 << n - 1).Aggregate((a, b) -> a | b);
		}
		return maskBytes;
	}

	/** 
	 Counts the number of leading 1's in a bitmask.
	 Returns null if value is invalid as a bitmask.
	 
	 @param bytes
	 @return 
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static Nullable<int> GetBitMaskLength(byte[] bytes)
	public static Integer GetBitMaskLength(byte[] bytes)
	{
		if (bytes == null)
		{
			throw new NullPointerException("bytes");
		}

		int bitLength = 0;
		int idx = 0;

		// find beginning 0xFF
		for (; idx < bytes.length && bytes[idx] == 0xff; idx++)
		{
			;
		}
		bitLength = 8 * idx;

		if (idx < bytes.length)
		{
			switch (bytes[idx])
			{
				case 0xFE:
					bitLength += 7;
					break;
				case 0xFC:
					bitLength += 6;
					break;
				case 0xF8:
					bitLength += 5;
					break;
				case 0xF0:
					bitLength += 4;
					break;
				case 0xE0:
					bitLength += 3;
					break;
				case 0xC0:
					bitLength += 2;
					break;
				case 0x80:
					bitLength += 1;
					break;
				case 0x00:
					break;
				default: // invalid bitmask
					return null;
			}
			// remainder must be 0x00
			if (bytes.Skip(idx + 1).Any(x -> x != 0x00))
			{
				return null;
			}
		}
		return bitLength;
	}


//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] Increment(byte[] bytes)
	public static byte[] Increment(byte[] bytes)
	{
		if (bytes == null)
		{
			throw new NullPointerException("bytes");
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var incrementIndex = Array.FindLastIndex(bytes, x => x < byte.MaxValue);
		int incrementIndex = Array.FindLastIndex(bytes, x -> x < Byte.MAX_VALUE);
		if (incrementIndex < 0)
		{
			throw new OverflowException();
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return bytes.Take(incrementIndex).Concat(new byte[] { (byte)(bytes[incrementIndex] + 1) }).Concat(new byte[bytes.Length - incrementIndex - 1]).ToArray();
		return bytes.Take(incrementIndex).Concat(new byte[] {(byte)(bytes[incrementIndex] + 1)}).Concat(new byte[bytes.length - incrementIndex - 1]).ToArray();
	}

}