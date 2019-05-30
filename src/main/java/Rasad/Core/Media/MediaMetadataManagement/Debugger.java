package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;
import java.time.*;

//
// Debugger.cs:
//
// Author:
//   Brian Nickel (brian.nickel@gmail.com)
//
// Copyright (C) 2006-2007 Brian Nickel
// 
// This library is free software; you can redistribute it and/or modify
// it  under the terms of the GNU Lesser General Public License version
// 2.1 as published by the Free Software Foundation.
//
// This library is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
// USA
//



public final class Debugger
{
	@FunctionalInterface
	public interface DebugMessageSentHandler
	{
		void invoke(String message);
	}

	public static tangible.Event<DebugMessageSentHandler> DebugMessageSent = new tangible.Event<DebugMessageSentHandler>();

	public static void Debug(String message)
	{
		if (DebugMessageSent != null)
		{
			for (DebugMessageSentHandler listener : DebugMessageSent.listeners())
			{
				listener.invoke(message);
			}
		}
	}

	public static void DumpHex(ByteVector data)
	{
		DumpHex(data.getData());
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void DumpHex(byte [] data)
	public static void DumpHex(byte[] data)
	{
			int cols = 16;
			int rows = data.length / cols + (data.length % cols != 0 ? 1 : 0);

		for (int row = 0; row < rows; row++)
		{
			for (int col = 0; col < cols; col++)
			{
				if (row == rows - 1 && data.length % cols != 0 && col >= data.length % cols)
				{
					System.out.print("   ");
				}
				else
				{
					System.out.printf(" %02x", data [row * cols + col]);
				}
			}

			System.out.print(" | ");

			for (int col = 0; col < cols; col++)
			{
				if (row == rows - 1 && data.length % cols != 0 && col >= data.length % cols)
				{
					System.out.print(" ");
				}
				else
				{
					WriteByte2(data [row * cols + col]);
				}
			}

			System.out.println();
		}
		System.out.println();
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static void WriteByte2(byte data)
	private static void WriteByte2(byte data)
	{
		for (char c : allowed)
		{
			if (c == data)
			{
				System.out.print(c);
				return;
			}
		}

		System.out.print(".");
	}

	private static String allowed = "0123456789abcdefghijklmnopqr" +
		"stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`~!@#$%^&*()_+-={}" +
		"[];:'\",.<>?/\\|";


	private static HashMap <Object, HashMap<Object, DebugTimeData>> debug_times = new HashMap<Object, HashMap<Object, DebugTimeData>> ();

	public static void AddDebugTime(Object o1, Object o2, LocalDateTime start)
	{
		DebugTimeData data = new DebugTimeData(LocalDateTime.now() - start, 1);
		if (debug_times.containsKey(o1) && debug_times.get(o1).containsKey(o2))
		{
			data.time += debug_times.get(o1).get(o2).time;
			data.occurances += debug_times.get(o1).get(o2).occurances;
		}

		if (!debug_times.containsKey(o1))
		{
			debug_times.put(o1, new HashMap<Object, DebugTimeData> ());
		}

		if (!debug_times.get(o1).containsKey(o2))
		{
			debug_times.get(o1).put(o2, data.clone());
		}
		else
		{
			debug_times.get(o1).put(o2, data.clone());
		}
	}

	public static void DumpDebugTime(Object o1)
	{
		System.out.println(o1.toString());
		if (!debug_times.containsKey(o1))
		{
			return;
		}

		for (Map.Entry <Object, DebugTimeData> pair : debug_times.get(o1).entrySet())
		{
			System.out.printf("  %1$s" + "\r\n", pair.getKey().toString());
			System.out.printf("    Objects: %1$s" + "\r\n", pair.getValue().time);
			System.out.printf("    Total:   %1$s" + "\r\n", pair.getValue().occurances);
			System.out.printf("    Average: %1$s" + "\r\n", new TimeSpan(pair.getValue().time.Ticks / pair.getValue().occurances));
			System.out.println("");
		}

		debug_times.remove(o1);
	}

//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: private struct DebugTimeData
	private final static class DebugTimeData
	{
		public TimeSpan time = new TimeSpan();
		public long occurances;

		public DebugTimeData()
		{
		}

		public DebugTimeData(TimeSpan time, int occurances)
		{
			this.time = time;
			this.occurances = occurances;
		}

		public DebugTimeData clone()
		{
			DebugTimeData varCopy = new DebugTimeData();

			varCopy.time = this.time;
			varCopy.occurances = this.occurances;

			return varCopy;
		}
	}
}