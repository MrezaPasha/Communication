package Rasad.Core.Compression.Core;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

// StreamUtils.cs
//
// Copyright 2005 John Reilly
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// Linking this library statically or dynamically with other modules is
// making a combined work based on this library.  Thus, the terms and
// conditions of the GNU General Public License cover the whole
// combination.
// 
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent
// modules, and to copy and distribute the resulting executable under
// terms of your choice, provided that you also meet, for each linked
// independent module, the terms and conditions of the license of that
// module.  An independent module is a module which is not derived from
// or based on this library.  If you modify this library, you may extend
// this exception to your version of the library, but you are not
// obligated to do so.  If you do not wish to do so, delete this
// exception statement from your version.



/** 
 Provides simple <see cref="Stream"/>" utilities.
*/
public final class StreamUtils
{
	/** 
	 Read from a <see cref="Stream"/> ensuring all the required data is read.
	 
	 @param stream The stream to read.
	 @param buffer The buffer to fill.
	 {@link ReadFully(Stream,byte[],int,int)}
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void ReadFully(Stream stream, byte[] buffer)
	public static void ReadFully(Stream stream, byte[] buffer)
	{
		ReadFully(stream, buffer, 0, buffer.length);
	}

	/** 
	 Read from a <see cref="Stream"/>" ensuring all the required data is read.
	 
	 @param stream The stream to read data from.
	 @param buffer The buffer to store data in.
	 @param offset The offset at which to begin storing data.
	 @param count The number of bytes of data to store.
	 @exception ArgumentNullException Required parameter is null
	 @exception ArgumentOutOfRangeException <paramref name="offset"/> and or <paramref name="count"/> are invalid.
	 @exception EndOfStreamException End of stream is encountered before all the data has been read.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void ReadFully(Stream stream, byte[] buffer, int offset, int count)
	public static void ReadFully(InputStream stream, byte[] buffer, int offset, int count)
	{
		if (stream == null)
		{
			throw new NullPointerException("stream");
		}

		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		// Offset can equal length when buffer and count are 0.
		if ((offset < 0) || (offset > buffer.length))
		{
			throw new IndexOutOfBoundsException("offset");
		}

		if ((count < 0) || (offset + count > buffer.length))
		{
			throw new IndexOutOfBoundsException("count");
		}

		while (count > 0)
		{
			int readCount = stream.read(buffer, offset, count);
			if (readCount <= 0)
			{
				throw new EndOfStreamException();
			}
			offset += readCount;
			count -= readCount;
		}
	}

	/** 
	 Copy the contents of one <see cref="Stream"/> to another.
	 
	 @param source The stream to source data from.
	 @param destination The stream to write data to.
	 @param buffer The buffer to use during copying.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void Copy(Stream source, Stream destination, byte[] buffer)
	public static void Copy(InputStream source, OutputStream destination, byte[] buffer)
	{
		if (source == null)
		{
			throw new NullPointerException("source");
		}

		if (destination == null)
		{
			throw new NullPointerException("destination");
		}

		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		// Ensure a reasonable size of buffer is used without being prohibitive.
		if (buffer.length < 128)
		{
			throw new IllegalArgumentException("Buffer is too small", "buffer");
		}

		boolean copying = true;

		while (copying)
		{
			int bytesRead = source.read(buffer, 0, buffer.length);
			if (bytesRead > 0)
			{
				destination.write(buffer, 0, bytesRead);
			}
			else
			{
				destination.flush();
				copying = false;
			}
		}
	}

	/** 
	 Copy the contents of one <see cref="Stream"/> to another.
	 
	 @param source The stream to source data from.
	 @param destination The stream to write data to.
	 @param buffer The buffer to use during copying.
	 @param progressHandler The <see cref="ProgressHandler">progress handler delegate</see> to use.
	 @param updateInterval The minimum <see cref="TimeSpan"/> between progress updates.
	 @param sender The source for this event.
	 @param name The name to use with the event.
	 This form is specialised for use within #Zip to support events during archive operations.
	*/
//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.Stream is input or output:
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void Copy(Stream source, Stream destination, byte[] buffer, ProgressHandler progressHandler, TimeSpan updateInterval, object sender, string name)
	public static void Copy(Stream source, Stream destination, byte[] buffer, ProgressHandler progressHandler, TimeSpan updateInterval, Object sender, String name)
	{
		Copy(source, destination, buffer, progressHandler, updateInterval, sender, name, -1);
	}

	/** 
	 Copy the contents of one <see cref="Stream"/> to another.
	 
	 @param source The stream to source data from.
	 @param destination The stream to write data to.
	 @param buffer The buffer to use during copying.
	 @param progressHandler The <see cref="ProgressHandler">progress handler delegate</see> to use.
	 @param updateInterval The minimum <see cref="TimeSpan"/> between progress updates.
	 @param sender The source for this event.
	 @param name The name to use with the event.
	 @param fixedTarget A predetermined fixed target value to use with progress updates.
	 If the value is negative the target is calculated by looking at the stream.
	 This form is specialised for use within #Zip to support events during archive operations.
	*/
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static void Copy(Stream source, Stream destination, byte[] buffer, ProgressHandler progressHandler, TimeSpan updateInterval, object sender, string name, long fixedTarget)
	public static void Copy(InputStream source, OutputStream destination, byte[] buffer, ProgressHandler progressHandler, TimeSpan updateInterval, Object sender, String name, long fixedTarget)
	{
		if (source == null)
		{
			throw new NullPointerException("source");
		}

		if (destination == null)
		{
			throw new NullPointerException("destination");
		}

		if (buffer == null)
		{
			throw new NullPointerException("buffer");
		}

		// Ensure a reasonable size of buffer is used without being prohibitive.
		if (buffer.length < 128)
		{
			throw new IllegalArgumentException("Buffer is too small", "buffer");
		}

		if (progressHandler == null)
		{
			throw new NullPointerException("progressHandler");
		}

		boolean copying = true;

		LocalDateTime marker = LocalDateTime.now();
		long processed = 0;
		long target = 0;

		if (fixedTarget >= 0)
		{
			target = fixedTarget;
		}
		else if (source.CanSeek)
		{
			target = source.Length - source.Position;
		}

		// Always fire 0% progress..
		ProgressEventArgs args = new ProgressEventArgs(name, processed, target);
		progressHandler.invoke(sender, args);

		boolean progressFired = true;

		while (copying)
		{
			int bytesRead = source.read(buffer, 0, buffer.length);
			if (bytesRead > 0)
			{
				processed += bytesRead;
				progressFired = false;
				destination.write(buffer, 0, bytesRead);
			}
			else
			{
				destination.flush();
				copying = false;
			}

			if (LocalDateTime.now() - marker > updateInterval)
			{
				progressFired = true;
				marker = LocalDateTime.now();
				args = new ProgressEventArgs(name, processed, target);
				progressHandler.invoke(sender, args);

				copying = args.getContinueRunning();
			}
		}

		if (!progressFired)
		{
			args = new ProgressEventArgs(name, processed, target);
			progressHandler.invoke(sender, args);
		}
	}

	/** 
	 Initialise an instance of <see cref="StreamUtils"></see>
	*/
	private StreamUtils()
	{
		// Do nothing.
	}
}