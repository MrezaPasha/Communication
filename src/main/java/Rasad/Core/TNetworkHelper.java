package Rasad.Core;

import java.util.*;

public final class TNetworkHelper
{
	static
	{
		setCurrentIpAddress(GetIPv4Address());
		setCurrentMacAddress(GetCurrentComputerMacAddresses().First().toString());
	}

	/** 
		 Error codes GetIpNetTable returns that we recognise
	*/
	private static final int ERROR_INSUFFICIENT_BUFFER = 122;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Get Mac From IP Method 2
	public static String GetMacByIp2(String ip)
	{
		ArrayList<MacIpPair> macIpPairs = GetAllMacAddressesAndIppairs();
		int index = tangible.ListHelper.findIndex(macIpPairs, x = ip.equals(> x.IpAddress));
		if (index >= 0)
		{
			return macIpPairs.get(index).MacAddress.toUpperCase();
		}
		else
		{
			return null;
		}
	}

	public static ArrayList<MacIpPair> GetAllMacAddressesAndIppairs()
	{
		ArrayList<MacIpPair> mip = new ArrayList<MacIpPair>();
		System.Diagnostics.Process pProcess = new System.Diagnostics.Process();
		pProcess.StartInfo.FileName = "arp";
		pProcess.StartInfo.Arguments = "-a ";
		pProcess.StartInfo.UseShellExecute = false;
		pProcess.StartInfo.RedirectStandardOutput = true;
		pProcess.StartInfo.CreateNoWindow = true;
		pProcess.Start();
		String cmdOutput = pProcess.StandardOutput.ReadToEnd();
		String pattern = "(?<ip>([0-9]{1,3}\\.?){4})\\s*(?<mac>([a-f0-9]{2}-?){6})";

		for (Match m : Regex.Matches(cmdOutput, pattern, RegexOptions.IgnoreCase))
		{
			MacIpPair tempVar = new MacIpPair();
			tempVar.MacAddress = m.Groups["mac"].Value;
			tempVar.IpAddress = m.Groups["ip"].Value;
			mip.add(tempVar.clone());
		}

		return mip;
	}
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct MacIpPair
	public final static class MacIpPair
	{
		public String MacAddress;
		public String IpAddress;

		public MacIpPair clone()
		{
			MacIpPair varCopy = new MacIpPair();

			varCopy.MacAddress = this.MacAddress;
			varCopy.IpAddress = this.IpAddress;

			return varCopy;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	/** 
		 Gets the MAC address (<see cref="PhysicalAddress" />) associated with the specified IP.
	 
	 @param ipAddress The remote IP address.
	 17.
	 @return The remote machine's MAC address.
	 18.
	*/
	public static PhysicalAddress GetMacAddress(IPAddress ipAddress)
	{
		final int MacAddressLength = 6;
		int length = MacAddressLength;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var macBytes = new byte[MacAddressLength];
		byte[] macBytes = new byte[MacAddressLength];
		tangible.RefObject<Integer> tempRef_length = new tangible.RefObject<Integer>(length);
		SendArp(BitConverter.ToInt32(ipAddress.GetAddressBytes(), 0), 0, macBytes, tempRef_length);
	length = tempRef_length.argValue;
		return new PhysicalAddress(macBytes);
	}

	public static PhysicalAddress GetMacFromIP(IPAddress IP)
	{
		if (IP.AddressFamily != AddressFamily.InterNetwork)
		{
			throw new IllegalArgumentException("supports just IPv4 addresses");
		}

		int addrInt = IpToInt(IP);
		int srcAddrInt = IpToInt(IP);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var mac = new byte[6];
		byte[] mac = new byte[6]; // 48 bit

		int length = mac.length;
		tangible.RefObject<Integer> tempRef_length = new tangible.RefObject<Integer>(length);
		int reply = SendArp(addrInt, srcAddrInt, mac, tempRef_length);
	length = tempRef_length.argValue;

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var emptyMac = new byte[12];
		byte[] emptyMac = new byte[12];

		if (reply != 0)
		{
			//MessageBox.Show("No MAC Address found for IP Address: " + IP.ToString());
			return new PhysicalAddress(emptyMac);
		}
		return new PhysicalAddress(mac);
	}

	private static int IpToInt(IPAddress IP)
	{
		return BitConverter.ToInt32(IP.GetAddressBytes(), 0);
	}

	public static native int SendArp(int destIpAddress, int srcIpAddress, byte[] macAddress, tangible.RefObject<Integer> macAddressLength);
	static
	{
		System.loadLibrary("Iphlpapi.dll");
	}

	/** 
		 GetIpNetTable external method
	 
	 @param pIpNetTable
	 @param pdwSize
	 @param bOrder
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("IpHlpApi.dll")][return: MarshalAs(UnmanagedType.U4)] private static extern int GetIpNetTable(IntPtr pIpNetTable, [MarshalAs(UnmanagedType.U4)] ref int pdwSize, bool bOrder);
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("IpHlpApi.dll")][return: MarshalAs(UnmanagedType.U4)] private static extern int GetIpNetTable(IntPtr pIpNetTable, [MarshalAs(UnmanagedType.U4)] ref int pdwSize, bool bOrder);
	private static native int GetIpNetTable(IntPtr pIpNetTable, tangible.RefObject<Integer> pdwSize, boolean bOrder);
	static
	{
		System.loadLibrary("IpHlpApi.dll");
	}

	/** 
		 Get the IP and MAC addresses of all known devices on the LAN
	 
	 
		 1) This table is not updated often - it can take some human-scale time
		 to notice that a device has dropped off the network, or a new device
		 has connected.
		 2) This discards non-local devices if they are found - these are multicast
		 and can be discarded by IP address range.
	 
	 @return 
	*/
	public static HashMap<IPAddress, PhysicalAddress> GetAllDevicesOnLAN()
	{
		HashMap<IPAddress, PhysicalAddress> all = new HashMap<IPAddress, PhysicalAddress>();
		// Add this PC to the list...
		all.put(GetIPv4IPAddress(), GetMacAddress());
		int spaceForNetTable = 0;
		// Get the space needed
		// We do that by requesting the table, but not giving any space at all.
		// The return value will tell us how much we actually need.
		tangible.RefObject<Integer> tempRef_spaceForNetTable = new tangible.RefObject<Integer>(spaceForNetTable);
		GetIpNetTable(IntPtr.Zero, tempRef_spaceForNetTable, false);
	spaceForNetTable = tempRef_spaceForNetTable.argValue;
		// Allocate the space
		// We use a try-finally block to ensure release.
		IntPtr rawTable = IntPtr.Zero;
		try
		{
			rawTable = Marshal.AllocCoTaskMem(spaceForNetTable);
			// Get the actual data
			tangible.RefObject<Integer> tempRef_spaceForNetTable2 = new tangible.RefObject<Integer>(spaceForNetTable);
			int errorCode = GetIpNetTable(rawTable, tempRef_spaceForNetTable2, false);
		spaceForNetTable = tempRef_spaceForNetTable2.argValue;
			if (errorCode != 0)
			{
				// Failed for some reason - can do no more here.
				throw new RuntimeException(String.format("Unable to retrieve network table. Error code %1$s", errorCode));
			}
			// Get the rows count
			int rowsCount = Marshal.ReadInt32(rawTable);
			IntPtr currentBuffer = new IntPtr(rawTable.ToInt64() + System.Runtime.InteropServices.Marshal.SizeOf(Integer.class));
			// Convert the raw table to individual entries
			MIB_IPNETROW[] rows = new MIB_IPNETROW[rowsCount];
			for (int index = 0; index < rowsCount; index++)
			{
				rows[index] = (MIB_IPNETROW)Marshal.PtrToStructure(new IntPtr(currentBuffer.ToInt64() + (index * System.Runtime.InteropServices.Marshal.SizeOf(MIB_IPNETROW.class))), MIB_IPNETROW.class);
			}
			// Define the dummy entries list (we can discard these)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var virtualMAC = new PhysicalAddress(new byte[] { 0, 0, 0, 0, 0, 0 });
			PhysicalAddress virtualMAC = new PhysicalAddress(new byte[] {0, 0, 0, 0, 0, 0});
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var broadcastMAC = new PhysicalAddress(new byte[] { 255, 255, 255, 255, 255, 255 });
			PhysicalAddress broadcastMAC = new PhysicalAddress(new byte[] {(byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255});
			for (MIB_IPNETROW row : rows)
			{
				IPAddress ip = new IPAddress(BitConverter.GetBytes(row.dwAddr));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] rawMAC = { row.mac0, row.mac1, row.mac2, row.mac3, row.mac4, row.mac5 };
				byte[] rawMAC = {row.mac0, row.mac1, row.mac2, row.mac3, row.mac4, row.mac5};
				PhysicalAddress pa = new PhysicalAddress(rawMAC);
				if (!pa.equals(virtualMAC) && !pa.equals(broadcastMAC) && !IsMulticast(ip))
				{
					//Console.WriteLine("IP: {0}\t\tMAC: {1}", ip.ToString(), pa.ToString());
					if (!all.containsKey(ip))
					{
						all.put(ip, pa);
					}
				}
			}
		}
		finally
		{
			// Release the memory.
			Marshal.FreeCoTaskMem(rawTable);
		}
		return all;
	}



	/**  
	 This utility function displays all the IP (v4, not v6) addresses of the local computer. 
	*/
	public static java.lang.Iterable<IPAddress> GetIPAddresses(AddressFamily addressFamily)
	{

		// Get a list of all network interfaces (usually one per network card, dialup, and VPN connection) 
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		var networkInterfaces = NetworkInterface.GetAllNetworkInterfaces().Where(t -> t.OperationalStatus == OperationalStatus.Up && t.NetworkInterfaceType != NetworkInterfaceType.Loopback);
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		return networkInterfaces.SelectMany(t -> t.GetIPProperties().UnicastAddresses).Where(t -> t.Address.AddressFamily == addressFamily && !IPAddress.IsLoopback(t.Address)).Select(t -> t.Address);
	}



	/** 
		 Gets the IP address of the current PC
	 
	 @return 
	*/
	public static IPAddress GetIPv4IPAddress()
	{
		return GetIPAddresses(AddressFamily.InterNetwork).FirstOrDefault();
	}
	public static java.lang.Iterable<PhysicalAddress> GetCurrentComputerMacAddresses()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		return NetworkInterface.GetAllNetworkInterfaces().Where(t -> t.OperationalStatus == OperationalStatus.Up).Select(t -> t.GetPhysicalAddress());
	}


	public static String GetIPv4Address()
	{
		IPAddress result = GetIPv4IPAddress();
		if (result != null)
		{
			return result.toString();
		}
		return null;
	}

	/** 
		 Gets the MAC address of the current PC.
	 
	 @return 
	*/
	private static PhysicalAddress GetMacAddress()
	{
		// Only consider Ethernet network interfaces
		NetworkInterface Result = NetworkInterface.GetAllNetworkInterfaces().FirstOrDefault(t -> t.NetworkInterfaceType == NetworkInterfaceType.Ethernet && t.OperationalStatus == OperationalStatus.Up);
		if (Result != null)
		{
			return Result.GetPhysicalAddress();
		}
		return null;
	}

	/** 
		 Returns true if the specified IP address is a multicast address
	 
	 @param ip
	 @return 
	*/
	private static boolean IsMulticast(IPAddress ip)
	{
		boolean result = true;
		if (!ip.IsIPv6Multicast)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte highIP = ip.GetAddressBytes()[0];
			byte highIP = ip.GetAddressBytes()[0];
			if (highIP < 224 || highIP > 239)
			{
				result = false;
			}
		}
		return result;
	}


	public static java.lang.Iterable<IPAddress> PingToAllAsync()
	{
		return PingToAllAsync("192.168.1.");
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static IEnumerable<IPAddress> PingToAllAsync(string baseAddress = "192.168.1.")
	public static java.lang.Iterable<IPAddress> PingToAllAsync(String baseAddress)
	{
		String data = "Ping test check";
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] byteBuffer = Encoding.ASCII.GetBytes(data);
		byte[] byteBuffer = data.getBytes(java.nio.charset.StandardCharsets.US_ASCII);

		ArrayList<PingReply> Pings = new ArrayList<PingReply>();
		for (int i = 0; i <= 255; i++)
		{
			Ping p = new Ping();
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
			p.PingCompleted += p_PingCompleted;
			PingOptions pingOptions = new PingOptions(64, true);

			p.SendAsync(IPAddress.Parse(baseAddress + i), 4000, byteBuffer, pingOptions, Pings);
		}
		while (Pings.size() < 256)
		{
			Thread.sleep(500);
		}

		return Pings.Select(t -> t.Address);
	}

	private static void p_PingCompleted(Object sender, PingCompletedEventArgs e)
	{
		ArrayList<PingReply> Pings = (ArrayList<PingReply>)e.UserState;
		Pings.add(e.Reply);
		(sender instanceof Ping ? (Ping)sender : null).Dispose();
	}


	public static boolean Ping(String hostNameOrAddress)
	{
		return Ping(hostNameOrAddress, 200);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static bool Ping(String hostNameOrAddress, int timeout = 200)
	public static boolean Ping(String hostNameOrAddress, int timeout)
	{
		Ping x = new Ping();
		boolean pingOK = false;
		boolean addressValid = false;
		try
		{
			PingReply pingReply = x.Send(hostNameOrAddress, 100);
			pingOK = pingReply.Status == IPStatus.Success;
			addressValid = true;
		}
		catch (java.lang.Exception e)
		{
			addressValid = false;
		}
		return pingOK && addressValid;
	}

	/** 
		 MIB_IPNETROW structure returned by GetIpNetTable
		 DO NOT MODIFY THIS STRUCTURE.
	*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct MIB_IPNETROW
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential)] public struct MIB_IPNETROW
	public final static class MIB_IPNETROW
	{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U4)] public int dwIndex;
		public int dwIndex;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U4)] public int dwPhysAddrLen;
		public int dwPhysAddrLen;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac0;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac0;
		public byte mac0;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac1;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac1;
		public byte mac1;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac2;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac2;
		public byte mac2;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac3;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac3;
		public byte mac3;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac4;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac4;
		public byte mac4;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac5;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac5;
		public byte mac5;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac6;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac6;
		public byte mac6;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac7;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U1)] public byte mac7;
		public byte mac7;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U4)] public int dwAddr;
		public int dwAddr;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.U4)] public int dwType;
		public int dwType;

		public MIB_IPNETROW clone()
		{
			MIB_IPNETROW varCopy = new MIB_IPNETROW();

			varCopy.dwIndex = this.dwIndex;
			varCopy.dwPhysAddrLen = this.dwPhysAddrLen;
			varCopy.mac0 = this.mac0;
			varCopy.mac1 = this.mac1;
			varCopy.mac2 = this.mac2;
			varCopy.mac3 = this.mac3;
			varCopy.mac4 = this.mac4;
			varCopy.mac5 = this.mac5;
			varCopy.mac6 = this.mac6;
			varCopy.mac7 = this.mac7;
			varCopy.dwAddr = this.dwAddr;
			varCopy.dwType = this.dwType;

			return varCopy;
		}
	}


	private static String CurrentIpAddress;
	public static String getCurrentIpAddress()
	{
		return CurrentIpAddress;
	}
	private static void setCurrentIpAddress(String value)
	{
		CurrentIpAddress = value;
	}

	private static String CurrentMacAddress;
	public static String getCurrentMacAddress()
	{
		return CurrentMacAddress;
	}
	public static void setCurrentMacAddress(String value)
	{
		CurrentMacAddress = value;
	}

	public static IPAddress GetIPAddressPart(String ipAddressAndPort)
	{
		IPAddress retVal = null;
		try
		{
			// try ip address
			retVal = IPAddress.Parse(ipAddressAndPort);
		}
		catch (java.lang.Exception e)
		{
			try
			{
				// try ipendpoint
				IPEndPoint ipEndPoint = TNetworkHelper.ParseIPEndPoint(ipAddressAndPort);
				retVal = ipEndPoint.Address;
			}
			catch (java.lang.Exception e2)
			{
			}
		}
		return retVal;
	}

	public static IPEndPoint ParseIPEndPoint(String endPoint)
	{
		String[] ep = endPoint.split("[:]", -1);
		if (ep.length != 2)
		{
			throw new NumberFormatException("Invalid endpoint format");
		}
		IPAddress ip;
		tangible.OutObject<System.Net.IPAddress> tempOut_ip = new tangible.OutObject<System.Net.IPAddress>();
		if (!IPAddress.TryParse(ep[0], tempOut_ip))
		{
		ip = tempOut_ip.argValue;
			throw new NumberFormatException("Invalid ip-adress");
		}
	else
	{
		ip = tempOut_ip.argValue;
	}
		int port;
		tangible.OutObject<Integer> tempOut_port = new tangible.OutObject<Integer>();
		if (!tangible.TryParseHelper.tryParseInt(ep[1], NumberStyles.None, NumberFormatInfo.InvariantInfo, tempOut_port))
		{
		port = tempOut_port.argValue;
			throw new NumberFormatException("Invalid port");
		}
	else
	{
		port = tempOut_port.argValue;
	}
		return new IPEndPoint(ip, port);
	}

	public static void ConnectWithTimeout(TcpClient client, IPEndPoint endPoint, int timeoutMilliseconds)
	{
		ConnectWithTimeout(client, endPoint.Address, endPoint.Port, timeoutMilliseconds);
	}
	public static void ConnectWithTimeout(TcpClient client, IPAddress ipAddress, int port, int timeoutMilliseconds)
	{
		System.IAsyncResult result = client.BeginConnect(ipAddress, port, null, null);
		boolean success = result.AsyncWaitHandle.WaitOne(TimeSpan.FromMilliseconds(timeoutMilliseconds));
		if (!success)
		{
			throw new RuntimeException("Failed to connect to " + ipAddress.toString() + ":" + String.valueOf(port));
		}
		// we have connected
		client.EndConnect(result);
	}

	public static String IncrementIPAddress(String startAddress, int count)
	{
		if (count == 0)
		{
			return startAddress;
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] beginIP = startAddress.Split(new char[] { '.' }).Select(s => Convert.ToByte(s)).ToArray();
		byte[] beginIP = startAddress.split("[.]", -1).Select(s -> (byte)s).ToArray();
		for (int i0 = beginIP[0]; i0 <= 255; i0++)
		{
			for (int i1 = beginIP[1]; i1 <= 255; i1++)
			{
				for (int i2 = beginIP[2]; i2 <= 255; i2++)
				{
					for (int i3 = beginIP[3]; i3 <= 255; i3++)
					{
						if (count == 0)
						{
							return String.format("%1$s.%2$s.%3$s.%4$s", i0, i1, i2, i3);
						}
						count--;
					}
				}
			}
		}
		throw new RuntimeException("Unable to increment " + startAddress + " by " + String.valueOf(count) + " units.");
	}
}