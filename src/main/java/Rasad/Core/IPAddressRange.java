package Rasad.Core;

public class IPAddressRange
{
	private AddressFamily addressFamily = AddressFamily.values()[0];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: readonly byte[] lowerBytes;
	private byte[] lowerBytes;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: readonly byte[] upperBytes;
	private byte[] upperBytes;

	public IPAddressRange(IPAddress lower, IPAddress upper)
	{
		// Assert that lower.AddressFamily == upper.AddressFamily

		this.addressFamily = lower.AddressFamily;
		this.lowerBytes = lower.GetAddressBytes();
		this.upperBytes = upper.GetAddressBytes();
	}

	public final boolean IsInRange(IPAddress address)
	{
		if (address.AddressFamily != addressFamily)
		{
			return false;
		}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] addressBytes = address.GetAddressBytes();
		byte[] addressBytes = address.GetAddressBytes();

		boolean lowerBoundary = true, upperBoundary = true;

		for (int i = 0; i < this.lowerBytes.length && (lowerBoundary || upperBoundary); i++)
		{
			if ((lowerBoundary && addressBytes[i] < lowerBytes[i]) || (upperBoundary && addressBytes[i] > upperBytes[i]))
			{
				return false;
			}

			lowerBoundary &= (addressBytes[i] == lowerBytes[i]);
			upperBoundary &= (addressBytes[i] == upperBytes[i]);
		}

		return true;
	}
}