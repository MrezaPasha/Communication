package Rasad.Core.Compression;

import Rasad.Core.Compression.Core.*;
import Rasad.Core.Compression.Zip.*;
import Rasad.Core.*;
import java.io.*;
import java.time.*;

public final class TCompressor
{
	/** 
	 Compresses the files in the nominated folder, and creates a zip file on disk named as outPathname. 
	 
	 @param targetZipFile
	 @param password
	 @param sourcefolderName
	*/
	public static void CompressFolder(String sourcefolderName, String targetZipFile, String password)
	{
		try (FileStream fsOut = File.Create(targetZipFile))
		{
			try (ZipOutputStream zipStream = new ZipOutputStream(fsOut))
			{

				zipStream.SetLevel(9); //0-9, 9 being the highest level of compression

				zipStream.Password = password; // optional. Null is the same as not setting. Required if using AES.

				// This setting will strip the leading part of the folder path in the entries, to
				// make the entries relative to the starting folder.
				// To include the full path for each entry up to the drive root, assign folderOffset = 0.
				int folderOffset = sourcefolderName.length() + (sourcefolderName.endsWith("\\") ? 0 : 1);

				CompressFolderInternal(sourcefolderName, zipStream, folderOffset);

				zipStream.IsStreamOwner = true; // Makes the Close also Close the underlying stream
				zipStream.Close();
			}
		}
	}

	/** 
	 Recurses down the folder structure
	 
	 @param path
	 @param zipStream
	 @param folderOffset
	*/
	private static void CompressFolderInternal(String path, ZipOutputStream zipStream, int folderOffset)
	{

		String[] files = (new File(path)).list(File::isFile);

		for (String filename : files)
		{

			File fi = new File(filename);

			String entryName = filename.substring(folderOffset); // Makes the name in zip based on the folder
			entryName = ZipEntry.CleanName(entryName); // Removes drive from name and fixes slash direction
			ZipEntry newEntry = new ZipEntry(entryName);
			newEntry.setDateTime(fi.LastWriteTime); // Note the zip format stores 2 second granularity

			// Specifying the AESKeySize triggers AES encryption. Allowable values are 0 (off), 128 or 256.
			// A password on the ZipOutputStream is required if using AES.
			//   newEntry.AESKeySize = 256;

			// To permit the zip to be unpacked by built-in extractor in WinXP and Server2003, WinZip 8, Java, and other older code,
			// you need to do one of the following: Specify UseZip64.Off, or set the Size.
			// If the file may be bigger than 4GB, or you do not need WinXP built-in compatibility, you do not need either,
			// but the zip will be in Zip64 format which not all utilities can understand.
			//   zipStream.UseZip64 = UseZip64.Off;
			newEntry.setSize(fi.length());

			zipStream.PutNextEntry(newEntry);

			// Zip the file in buffered chunks
			// the "using" will close the stream even if an exception occurs
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] buffer = new byte[4096];
			byte[] buffer = new byte[4096];
			try (FileStream streamReader = File.OpenRead(filename))
			{
				StreamUtils.Copy(streamReader, zipStream, buffer);
			}
			zipStream.CloseEntry();
		}
		String[] folders = (new File(path)).list(File::isDirectory);
		for (String folder : folders)
		{
			CompressFolderInternal(folder, zipStream, folderOffset);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
	public static MemoryStream CompressToMemoryStream(MemoryStream sourceMemoryStream, String zipEntryName)
	{

//C# TO JAVA CONVERTER TODO TASK: C# to Java Converter cannot determine whether this System.IO.MemoryStream is input or output:
		MemoryStream outputMemStream = new MemoryStream();
		ZipOutputStream zipStream = new ZipOutputStream(outputMemStream);

		zipStream.SetLevel(3); //0-9, 9 being the highest level of compression

		ZipEntry newEntry = new ZipEntry(zipEntryName);
		newEntry.setDateTime(LocalDateTime.now());

		zipStream.PutNextEntry(newEntry);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: StreamUtils.Copy(sourceMemoryStream, zipStream, new byte[4096]);
		StreamUtils.Copy(sourceMemoryStream, zipStream, new byte[4096]);
		zipStream.CloseEntry();

		zipStream.IsStreamOwner = false; // False stops the Close also Closing the underlying stream.
		zipStream.Close(); // Must finish the ZipOutputStream before using outputMemStream.

		outputMemStream.Position = 0;
		return outputMemStream;

		// Alternative outputs:
		// ToArray is the cleaner and easiest to use correctly with the penalty of duplicating allocated memory.
		// GetBuffer returns a raw buffer raw and so you need to account for the true length yourself.
		//byte[] byteArrayOut = outputMemStream.ToArray();
		//long len = outputMemStream.Length;
	}

	//private void DownloadZipToBrowser(List<string> zipFileList)
	//{

	//    Response.ContentType = "application/zip";
	//    // If the browser is receiving a mangled zipfile, IIS Compression may cause this problem. Some members have found that
	//    //    Response.ContentType = "application/octet-stream"     has solved this. May be specific to Internet Explorer.

	//    Response.AppendHeader("content-disposition", "attachment; filename=\"Download.zip\"");
	//    Response.CacheControl = "Private";
	//    Response.Cache.SetExpires(DateTime.Now.AddMinutes(3)); // or put a timestamp in the filename in the content-disposition

	//    byte[] buffer = new byte[4096];

	//    ZipOutputStream zipOutputStream = new ZipOutputStream(Response.OutputStream);
	//    zipOutputStream.SetLevel(3); //0-9, 9 being the highest level of compression

	//    foreach (string fileName in zipFileList)
	//    {

	//        Stream fs = File.OpenRead(fileName);    // or any suitable inputstream

	//        ZipEntry entry = new ZipEntry(ZipEntry.CleanName(fileName));
	//        entry.Size = fs.Length;
	//        // Setting the Size provides WinXP built-in extractor compatibility,
	//        //  but if not available, you can set zipOutputStream.UseZip64 = UseZip64.Off instead.

	//        zipOutputStream.PutNextEntry(entry);

	//        int count = fs.Read(buffer, 0, buffer.Length);
	//        while (count > 0)
	//        {
	//            zipOutputStream.Write(buffer, 0, count);
	//            count = fs.Read(buffer, 0, buffer.Length);
	//            if (!Response.IsClientConnected)
	//            {
	//                break;
	//            }
	//            Response.Flush();
	//        }
	//        fs.Close();
	//    }
	//    zipOutputStream.Close();

	//    Response.Flush();
	//    Response.End();
	//}
}