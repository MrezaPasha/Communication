package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;
import java.io.*;
import java.time.*;

/** 
 A factory that creates <see cref="ITaggedData">tagged data</see> instances.
*/
public interface ITaggedDataFactory
{
	/** 
	 Get data for a specific tag value.
	 
	 @param tag The tag ID to find.
	 @param data The data to search.
	 @param offset The offset to begin extracting data from.
	 @param count The number of bytes to extract.
	 @return The located <see cref="ITaggedData">value found</see>, or null if not found.
	*/
	ITaggedData Create(short tag, byte[] data, int offset, int count);
}