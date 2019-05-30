package Rasad.Core.Net.IPAddressRangeGenerator;

import Rasad.Core.*;
import Rasad.Core.Net.*;
import java.util.*;
import java.io.*;

///#if IPADDRESSRANGE_NETFX45
///#endif


// NOTE: Why implement IReadOnlyDictionary<TKey,TVal> interface? 
// =============================================================
// Problem
// ----------
// An IPAddressRange after v.1.4 object cann't serialize to/deserialize from JSON text by using JSON.NET.
//
// Details
// ----------
// JSON.NET detect IEnumerable<IPAddress> interface prior to ISerializable. 
// At a result, JSON.NET try to serialize IPAddressRange as array, such as "["192.168.0.1", "192.168.0.2"]".
// This is unexpected behavior. (We expect "{"Begin":"192.168.0.1", "End:"192.168.0.2"}" style JSON text that is same with DataContractJsonSerializer.)
// In addition, JSON serialization with JSON.NET crash due to IPAddress cann't serialize by JSON.NET.
//
// Work around
// -----------
// To avoid this JSON.NET behavior, IPAddressRange should implement more high priority interface than IEnumerable<T> in JSON.NET.
// Such interfaces include the following.
// - IDictionary
// - IDictionary<TKey,TVal>
// - IReadOnlyDictionary<TKey,TVal>
// But, when IPAddressRange implement IDictionay or IDictionary<TKey,TVal>, serialization by DataContractJsonSerializer was broken.
// (Implementation of DataContractJsonSerializer is special for IDictionay and IDictionary<TKey,TVal>)
// 
// So there is no way without implement IReadOnlyDictionary<TKey,TVal>.
//
// Trade off
// -------------
// IReadOnlyDictionary<TKey,TVal> interface doesn't exist in .NET Framework v.4.0 or before.
// In order to give priority to supporting serialization by JSON.NET, I had to truncate the support for .NET Framework 4.0.
// (.NET Standard 1.4 support IReadOnlyDictionary<TKey,TVal>, therefore there is no problem on .NET Core appliction.)
// 
// Binary level compatiblity
// -------------------------
// There is no problem even if IPAddressRange.dll is replaced with the latest version.
// 
// Source code level compatiblity
// -------------------------
// You cann't apply LINQ extension methods directory to IPAddressRange object.
// Because IPAddressRange implement two types of IEnumerable<T> (IEnumerable<IPaddress> and IEnumerable<KeyValuePair<K,V>>).
// It cause ambiguous syntax error.
// To avoid this error, you should use "AsEnumerable()" method before IEnumerable<IPAddressRange> access.

///#if IPADDRESSRANGE_NETFX45
public class IPAddressRange implements ISerializable, java.lang.Iterable<String>, IReadOnlyDictionary<String, String>, Serializable
///#else
//    public class IPAddressRange : IEnumerable<IPAddress>, IReadOnlyDictionary<string, string>
///#endif
{
	// Pattern 1. CIDR range: "192.168.0.0/24", "fe80::/10"
	private static Regex m1_regex = new Regex("^(?<adr>[\\da-f\\.:]+)/(?<maskLen>\\d+)$", RegexOptions.IgnoreCase.getValue() | RegexOptions.Compiled.getValue());

	// Pattern 2. Uni address: "127.0.0.1", ":;1"
	private static Regex m2_regex = new Regex("^(?<adr>[\\da-f\\.:]+)$", RegexOptions.IgnoreCase.getValue() | RegexOptions.Compiled.getValue());

	// Pattern 3. Begin end range: "169.258.0.0-169.258.0.255"
	private static Regex m3_regex = new Regex("^(?<begin>[\\da-f\\.:]+)[\\-–](?<end>[\\da-f\\.:]+)$", RegexOptions.IgnoreCase.getValue() | RegexOptions.Compiled.getValue());

	// Pattern 4. Bit mask range: "192.168.0.0/255.255.255.0"
	private static Regex m4_regex = new Regex("^(?<adr>[\\da-f\\.:]+)/(?<bitmask>[\\da-f\\.:]+)$", RegexOptions.IgnoreCase.getValue() | RegexOptions.Compiled.getValue());


	private IPAddress Begin;
	public final IPAddress getBegin()
	{
		return Begin;
	}
	public final void setBegin(IPAddress value)
	{
		Begin = value;
	}

	private IPAddress End;
	public final IPAddress getEnd()
	{
		return End;
	}
	public final void setEnd(IPAddress value)
	{
		End = value;
	}

	/** 
	 Creates an empty range object, equivalent to "0.0.0.0/0".
	*/
	public IPAddressRange()
	{
		this(new IPAddress(0L));
	}

	/** 
	 Creates a new range with the same start/end address (range of one)
	 
	 @param singleAddress
	*/
	public IPAddressRange(IPAddress singleAddress)
	{
		if (singleAddress == null)
		{
			throw new NullPointerException("singleAddress");
		}

		setEnd(singleAddress);
		setBegin(getEnd());
	}

	/** 
	 Create a new range from a begin and end address.
	 Throws an exception if Begin comes after End, or the
	 addresses are not in the same family.
	*/
	public IPAddressRange(IPAddress begin, IPAddress end)
	{
		if (begin == null)
		{
			throw new NullPointerException("begin");
		}

		if (end == null)
		{
			throw new NullPointerException("end");
		}

		setBegin(begin);
		setEnd(end);

		if (getBegin().AddressFamily != getEnd().AddressFamily)
		{
			throw new IllegalArgumentException("Elements must be of the same address family", "end");
		}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var beginBytes = getBegin().GetAddressBytes();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var endBytes = getEnd().GetAddressBytes();
		if (!Bits.LE(endBytes, beginBytes))
		{
			throw new IllegalArgumentException("Begin must be smaller than the End", "begin");
		}
	}

	/** 
	 Creates a range from a base address and mask bits.
	 This can also be used with <see cref="SubnetMaskLength"/> to create a
	 range based on a subnet mask.
	 
	 @param baseAddress
	 @param maskLength
	*/
	public IPAddressRange(IPAddress baseAddress, int maskLength)
	{
		if (baseAddress == null)
		{
			throw new NullPointerException("baseAddress");
		}

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var baseAdrBytes = baseAddress.GetAddressBytes();
		if (baseAdrBytes.Length * 8 < maskLength)
		{
			throw new NumberFormatException();
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var maskBytes = Bits.GetBitMask(baseAdrBytes.Length, maskLength);
		byte[] maskBytes = Bits.GetBitMask(baseAdrBytes.Length, maskLength);
		baseAdrBytes = Bits.And(baseAdrBytes, maskBytes);

		setBegin(new IPAddress(baseAdrBytes));
		setEnd(new IPAddress(Bits.Or(baseAdrBytes, Bits.Not(maskBytes))));
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EditorBrowsable(EditorBrowsableState.Never), Obsolete("Use IPAddressRange.Parse static method instead.")] public IPAddressRange(string ipRangeString)
	@Deprecated
	public IPAddressRange(String ipRangeString)
	{
		Rasad.Core.Net.IPAddressRangeGenerator.IPAddressRange parsed = Parse(ipRangeString);
		setBegin(parsed.getBegin());
		setEnd(parsed.getEnd());
	}

///#if IPADDRESSRANGE_NETFX45
	protected IPAddressRange(SerializationInfo info, StreamingContext context)
	{
		ArrayList<String> names = new ArrayList<String>();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		for (var item : info)
		{
			names.add(item.Name);
		}

		tangible.Func1Param<String, IPAddress> deserialize = (String name) -> names.contains(name) ? IPAddress.Parse(info.GetValue(name, Object.class).toString()) : new IPAddress(0L);

		this.setBegin(deserialize.invoke("Begin"));
		this.setEnd(deserialize.invoke("End"));
	}

	public void GetObjectData(SerializationInfo info, StreamingContext context)
	{
		if (info == null)
		{
			throw new NullPointerException("info");
		}

		info.AddValue("Begin", this.getBegin() != null ? this.getBegin().toString() : "");
		info.AddValue("End", this.getEnd() != null ? this.getEnd().toString() : "");
	}
///#endif

	public final boolean Contains(IPAddress ipaddress)
	{
		if (ipaddress == null)
		{
			throw new NullPointerException("ipaddress");
		}

		if (ipaddress.AddressFamily != this.getBegin().AddressFamily)
		{
			return false;
		}
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var adrBytes = ipaddress.GetAddressBytes();
		return Bits.GE(this.getBegin().GetAddressBytes(), adrBytes) && Bits.LE(this.getEnd().GetAddressBytes(), adrBytes);
	}

	public final boolean Contains(IPAddressRange range)
	{
		if (range == null)
		{
			throw new NullPointerException("range");
		}

		if (this.getBegin().AddressFamily != range.getBegin().AddressFamily)
		{
			return false;
		}

		return Bits.GE(this.getBegin().GetAddressBytes(), range.getBegin().GetAddressBytes()) && Bits.LE(this.getEnd().GetAddressBytes(), range.getEnd().GetAddressBytes());

		throw new UnsupportedOperationException();
	}

	public static IPAddressRange Parse(String ipRangeString)
	{
		if (ipRangeString == null)
		{
			throw new NullPointerException("ipRangeString");
		}

		// remove all spaces.
		ipRangeString = ipRangeString.replace(" ", "");

		// Pattern 1. CIDR range: "192.168.0.0/24", "fe80::/10"
		System.Text.RegularExpressions.Match m1 = m1_regex.Match(ipRangeString);
		if (m1.Success)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var baseAdrBytes = IPAddress.Parse(m1.Groups["adr"].Value).GetAddressBytes();
			int maskLen = Integer.parseInt(m1.Groups["maskLen"].Value);
			if (baseAdrBytes.Length * 8 < maskLen)
			{
				throw new NumberFormatException();
			}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var maskBytes = Bits.GetBitMask(baseAdrBytes.Length, maskLen);
			byte[] maskBytes = Bits.GetBitMask(baseAdrBytes.Length, maskLen);
			baseAdrBytes = Bits.And(baseAdrBytes, maskBytes);
			return new IPAddressRange(new IPAddress(baseAdrBytes), new IPAddress(Bits.Or(baseAdrBytes, Bits.Not(maskBytes))));
		}

		// Pattern 2. Uni address: "127.0.0.1", ":;1"
		System.Text.RegularExpressions.Match m2 = m2_regex.Match(ipRangeString);
		if (m2.Success)
		{
			return new IPAddressRange(IPAddress.Parse(ipRangeString));
		}

		// Pattern 3. Begin end range: "169.258.0.0-169.258.0.255"
		System.Text.RegularExpressions.Match m3 = m3_regex.Match(ipRangeString);
		if (m3.Success)
		{
			return new IPAddressRange(IPAddress.Parse(m3.Groups["begin"].Value), IPAddress.Parse(m3.Groups["end"].Value));
		}

		// Pattern 4. Bit mask range: "192.168.0.0/255.255.255.0"
		System.Text.RegularExpressions.Match m4 = m4_regex.Match(ipRangeString);
		if (m4.Success)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var baseAdrBytes = IPAddress.Parse(m4.Groups["adr"].Value).GetAddressBytes();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
			var maskBytes = IPAddress.Parse(m4.Groups["bitmask"].Value).GetAddressBytes();
			baseAdrBytes = Bits.And(baseAdrBytes, maskBytes);
			return new IPAddressRange(new IPAddress(baseAdrBytes), new IPAddress(Bits.Or(baseAdrBytes, Bits.Not(maskBytes))));
		}

		throw new NumberFormatException("Unknown IP range string.");
	}

	public static boolean TryParse(String ipRangeString, tangible.OutObject<IPAddressRange> ipRange)
	{
		try
		{
			ipRange.argValue = IPAddressRange.Parse(ipRangeString);
			return true;
		}
		catch (RuntimeException e)
		{
			ipRange.argValue = null;
			return false;
		}
	}

	/** 
	 Takes a subnetmask (eg, "255.255.254.0") and returns the CIDR bit length of that
	 address. Throws an exception if the passed address is not valid as a subnet mask.
	 
	 @param subnetMask The subnet mask to use
	 @return 
	*/
	public static int SubnetMaskLength(IPAddress subnetMask)
	{
		if (subnetMask == null)
		{
			throw new NullPointerException("subnetMask");
		}

		Integer length = Bits.GetBitMaskLength(subnetMask.GetAddressBytes());
		if (length == null)
		{
			throw new IllegalArgumentException("Not a valid subnet mask", "subnetMask");
		}
		return length.intValue();
	}

	public final Iterator<String> iterator()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var first = getBegin().GetAddressBytes();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var last = getEnd().GetAddressBytes();
		for (var ip = first; Bits.GE(ip, last); ip = Bits.Increment(ip))
		{
			//yield return new IPAddress(ip);
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
			yield return String.format("%1$s.%2$s.%3$s.%4$s", ip[0], ip[1], ip[2], ip[3]);
		}
	}

	public final Iterator GetEnumerator()
	{
		if (this instanceof IEnumerable)
			return IEnumerable_GetEnumerator();
		else if (this instanceof IEnumerable)
			return IEnumerable_GetEnumerator();
		else
			throw new UnsupportedOperationException("No interface found.");
	}

	private Iterator IEnumerable_GetEnumerator()
	{
		return GetEnumerator();
	}

	/** 
	 Returns the range in the format "begin-end", or 
	 as a single address if End is the same as Begin.
	 
	 @return 
	*/
	@Override
	public String toString()
	{
		return Equals(getBegin(), getEnd()) ? getBegin().toString() : String.format("%1$s-%2$s", getBegin(), getEnd());
	}

	public final int GetPrefixLength()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] byteBegin = Begin.GetAddressBytes();
		byte[] byteBegin = getBegin().GetAddressBytes();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] byteEnd = End.GetAddressBytes();
		byte[] byteEnd = getEnd().GetAddressBytes();

		// Handle single IP
		if (getBegin().equals(getEnd()))
		{
			return byteBegin.length * 8;
		}

		int length = byteBegin.length * 8;

		for (int i = 0; i < length; i++)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] mask = Bits.GetBitMask(byteBegin.Length, i);
			byte[] mask = Bits.GetBitMask(byteBegin.length, i);
			if ((new IPAddress(Bits.And(byteBegin, mask))).equals(getBegin()))
			{
				if ((new IPAddress(Bits.Or(byteBegin, Bits.Not(mask)))).equals(getEnd()))
				{
					return i;
				}
			}
		}
		throw new NumberFormatException(String.format("%1$s is not a CIDR Subnet", toString()));
	}

	/** 
	 Returns a Cidr String if this matches exactly a Cidr subnet
	*/
	public final String ToCidrString()
	{
		return String.format("%1$s/%2$s", getBegin(), GetPrefixLength());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region JSON.NET Support by implement IReadOnlyDictionary<string, string>

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [EditorBrowsable(EditorBrowsableState.Never)] public IPAddressRange(IEnumerable<KeyValuePair<string, string>> items)
	public IPAddressRange(java.lang.Iterable<Map.Entry<String, String>> items)
	{
		String value1;
		String value2;
		tangible.OutObject<String> tempOut_value1 = new tangible.OutObject<String>();
		if (TryGetValue(items, "Begin", tempOut_value1))
		{
		value1 = tempOut_value1.argValue;
			this.setBegin(IPAddress.Parse(value1));
		}
		else
		{
		value1 = tempOut_value1.argValue;
			throw new KeyNotFoundException();
		}
		tangible.OutObject<String> tempOut_value2 = new tangible.OutObject<String>();
		if (TryGetValue(items, "End", tempOut_value2))
		{
		value2 = tempOut_value2.argValue;
			this.setEnd(IPAddress.Parse(value2));
		}
		else
		{
		value2 = tempOut_value2.argValue;
			throw new KeyNotFoundException();
		}

		//this.Begin = IPAddress.Parse(TryGetValue(items, "Begin", out var value1) ? value1 : throw new KeyNotFoundException());
		//this.End = IPAddress.Parse(TryGetValue(items, "End", out var value2) ? value2 : throw new KeyNotFoundException());
	}

	/** 
	 Returns the input typed as IEnumerable&lt;IPAddress&gt;
	*/
	public final java.lang.Iterable<IPAddress> AsEnumerable()
	{
		String tempVar = this;
		return (tempVar instanceof java.lang.Iterable<IPAddress> ? (java.lang.Iterable<IPAddress>)tempVar : null);
	}

	private java.lang.Iterable<Map.Entry<String, String>> GetDictionaryItems()
	{
		return new Map.Entry<String,String>[]
		{
			new Map.Entry<String,String>("Begin", getBegin().toString()),
			new Map.Entry<String,String>("End", getEnd().toString())
		};
	}

	private boolean TryGetValue(String key, tangible.OutObject<String> value)
	{
		return TryGetValue(GetDictionaryItems(), key, value);
	}

	private boolean TryGetValue(java.lang.Iterable<Map.Entry<String, String>> items, String key, tangible.OutObject<String> value)
	{
		items = (items != null) ? items : GetDictionaryItems();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var foundItem = System.EnumerableExtensions.FirstOrDefault(items, item = key.equals(> item.Key));
		value.argValue = foundItem.Value;
		return foundItem.Key != null;
	}

	public final java.lang.Iterable<String> getKeys()
	{
		return System.Linq.TDynamicEnumerable.Select(GetDictionaryItems(), item -> item.Key);
	}

	public final java.lang.Iterable<String> getValues()
	{
		return System.Linq.TDynamicEnumerable.Select(GetDictionaryItems(), item -> item.Value);
	}

	public final int getCount()
	{
		return GetDictionaryItems().size();
	}

	public final String get(String key)
	{
		String value;
		tangible.OutObject<String> tempOut_value = new tangible.OutObject<String>();
		if (TryGetValue(key, tempOut_value))
		{
		value = tempOut_value.argValue;
			return value;
		}
		else
		{
		value = tempOut_value.argValue;
			throw new KeyNotFoundException();
		}
	}

	public final boolean ContainsKey(String key)
	{
		return GetDictionaryItems().Any(item = key.equals(> item.Key));
	}

	public final boolean TryGetValue(String key, tangible.OutObject<String> value)
	{
		return TryGetValue(key, value);
	}

	private Iterator<Map.Entry<String, String>> IEnumerable_GetEnumerator()
	{
		return GetDictionaryItems().iterator();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}