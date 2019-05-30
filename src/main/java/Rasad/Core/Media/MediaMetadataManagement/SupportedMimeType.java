package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import java.util.*;

 </example>
*/
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [AttributeUsage(AttributeTargets.Class, AllowMultiple=true)] public sealed class SupportedMimeType : Attribute
public final class SupportedMimeType extends Attribute
{
	/** 
		Contains the registered <see cref="SupportedMimeType" />
		objects.
	*/
	private static ArrayList<SupportedMimeType> mimetypes = new ArrayList<SupportedMimeType> ();

	/** 
		Contains the mime-type.
	*/
	private String mimetype;

	/** 
		Contains the extension.
	*/
	private String extension;

	/** 
		Constructs and initializes the <see
		cref="SupportedMimeType" /> class by initializing the
		<see cref="FileTypes" /> class.
	*/
	static
	{
		FileTypes.Init();
	}

	/** 
		Constructs and initializes a new instance of the <see
		cref="SupportedMimeType" /> attribute for a specified
		mime-type.
	 
	 @param mimetype
		A <see cref="string" /> object containing a standard
		mime-type.
	 
	 
		<p>Standard practice is to use <see
		cref="SupportedMimeType(string)" /> to register standard
		mime-types, like "audio/mp3" and "video/mpeg" and to use
		<see cref="SupportedMimeType(string,string)" /> strictly
		to register extensions, using "taglib/ext" for the mime
		type. Eg. <c>SupportedMimeType("taglib/mp3",
		"mp3")</c>.</p>
	 
	*/
	public SupportedMimeType(String mimetype)
	{
		this.mimetype = mimetype;
		mimetypes.add(this);
	}

	/** 
		Constructs and initializes a new instance of the <see
		cref="SupportedMimeType" /> attribute for a specified
		mime-type and extension.
	 
	 @param mimetype
		A <see cref="string" /> object containing a standard
		mime-type.
	 
	 @param extension
		A <see cref="string" /> object containing a file
		extension.
	 
	 
		<p>Standard practice is to use <see
		cref="SupportedMimeType(string)" /> to register standard
		mime-types, like "audio/mp3" and "video/mpeg" and to use
		<see cref="SupportedMimeType(string,string)" /> strictly
		to register extensions, using "taglib/ext" for the mime
		type. Eg. <c>SupportedMimeType("taglib/mp3",
		"mp3")</c>.</p>
	 
	*/
	public SupportedMimeType(String mimetype, String extension)
	{
		this(mimetype);
		this.extension = extension;
	}

	/** 
		Gets the mime-type registered by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the mime-type
		registered by the current instance.
	 </value>
	 
		<p>The value is in the format "generic/specific". For
		example, "video/mp4".</p>
	 
	*/
	public String getMimeType()
	{
		return mimetype;
	}

	/** 
		Gets the extension registered by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the extension
		registered by the current instance, or <see
		langword="null" /> if not specified.
	 </value>
	 
		<p>The value is the file extension minus the preceding
		".". For example, "m4v".</p>
	 
	*/
	public String getExtension()
	{
		return extension;
	}

	/** 
		Gets all the mime-types that have been registered with
		<see cref="SupportedMimeType" />.
	 
	 <value>
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object containing all the
		mime-types that have been registered with <see
		cref="SupportedMimeType" />.
	 </value>
	 
		<p>These values are used by <see
		cref="Rasad.Core.Media.MediaMetadataManagement.File.Create(string,string,ReadStyle)" /> to
		match file types.</p>
	 
	*/
	public static java.lang.Iterable<String> getAllMimeTypes()
	{
		for (SupportedMimeType type : mimetypes)
		{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
			yield return type.getMimeType();
		}
	}

	/** 
		Gets all the extensions that have been registered with
		<see cref="SupportedMimeType" />.
	 
	 <value>
		A <see cref="T:System.Collections.Generic.IEnumerable`1" /> object containing all the
		extensions that have been registered with <see
		cref="SupportedMimeType" />.
	 </value>
	 
		<p>These values are currently not used in file type
		recognition.</p>
	 
	*/
	public static java.lang.Iterable<String> getAllExtensions()
	{
		for (SupportedMimeType type : mimetypes)
		{
			if (type.getExtension() != null)
			{
//C# TO JAVA CONVERTER TODO TASK: Java does not have an equivalent to the C# 'yield' keyword:
				yield return type.getExtension();
			}
		}
	}
}