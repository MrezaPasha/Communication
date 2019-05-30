package Rasad.Core.Media.MediaMetadataManagement;

import Rasad.Core.*;
import Rasad.Core.Media.*;

/** 
	This class implements <see cref="IPicture" /> and provides
	mechanisms for loading pictures from files.
*/
public class Picture implements IPicture
{
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Fields

	/** 
		Contains the mime-type.
	*/
	private String mime_type;

	/** 
		Contains the content type.
	*/
	private PictureType type = PictureType.values()[0];

	/** 
		Contains the description.
	*/
	private String description;

	/** 
		Contains the picture data.
	*/
	private ByteVector data;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Constructors

	/** 
		Constructs and initializes a new instance of <see
		cref="Picture" /> with no data or values.
	*/
	public Picture()
	{
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Picture" /> by reading in the contents of a
		specified file.
	 
	 @param path
		A <see cref="string"/> object containing the path of the
		file to read.
	 
	 @exception ArgumentNullException
		<paramref name="path" /> is <see langword="null" />.
	 
	*/
	public Picture(String path)
	{
		if (path == null)
		{
			throw new NullPointerException("path");
		}

		setData(ByteVector.FromPath(path));
		FillInMimeFromData();
		setDescription(path);
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Picture" /> by reading in the contents of a
		specified file abstraction.
	 
	 @param abstraction
		A <see cref="File.IFileAbstraction"/> object containing
		abstraction of the file to read.
	 
	 @exception ArgumentNullException
		<paramref name="abstraction" /> is <see langword="null"
		/>.
	 
	*/
	public Picture(File.IFileAbstraction abstraction)
	{
		if (abstraction == null)
		{
			throw new NullPointerException("abstraction");
		}

		setData(ByteVector.FromFile(abstraction));
		FillInMimeFromData();
		setDescription(abstraction.getName());
	}

	/** 
		Constructs and initializes a new instance of <see
		cref="Picture" /> by using the contents of a <see
		cref="ByteVector" /> object.
	 
	 @param data
		A <see cref="ByteVector"/> object containing picture data
		to use.
	 
	 @exception ArgumentNullException
		<paramref name="data" /> is <see langword="null" />.
	 
	*/
	public Picture(ByteVector data)
	{
		if (data == null)
		{
			throw new NullPointerException("data");
		}

		setData(new ByteVector(data));
		FillInMimeFromData();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Static Methods

	/** 
		Creates a new <see cref="Picture" />, populating it with
		the contents of a file.
	 
	 @param filename
		A <see cref="string" /> object containing the path to a
		file to read the picture from.
	 
	 @return 
		A new <see cref="Picture" /> object containing the
		contents of the file and with a mime-type guessed from
		the file's contents.
	 
	*/
	@Deprecated
	public static Picture CreateFromPath(String filename)
	{
		return new Picture(filename);
	}

	/** 
		Creates a new <see cref="Picture" />, populating it with
		the contents of a file.
	 
	 @param abstraction
		A <see cref="File.IFileAbstraction" /> object containing
		the file abstraction to read the picture from.
	 
	 @return 
		A new <see cref="Picture" /> object containing the
		contents of the file and with a mime-type guessed from
		the file's contents.
	 
	*/
	@Deprecated
	public static Picture CreateFromFile(File.IFileAbstraction abstraction)
	{
		return new Picture(abstraction);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Public Properties

	/** 
		Gets and sets the mime-type of the picture data
		stored in the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the mime-type
		of the picture data stored in the current instance.
	 </value>
	*/
	public final String getMimeType()
	{
		return mime_type;
	}
	public final void setMimeType(String value)
	{
		mime_type = value;
	}

	/** 
		Gets and sets the type of content visible in the picture
		stored in the current instance.
	 
	 <value>
		A <see cref="PictureType" /> containing the type of
		content visible in the picture stored in the current
		instance.
	 </value>
	*/
	public final PictureType getType()
	{
		return type;
	}
	public final void setType(PictureType value)
	{
		type = value;
	}

	/** 
		Gets and sets a description of the picture stored in the
		current instance.
	 
	 <value>
		A <see cref="string" /> object containing a description
		of the picture stored in the current instance.
	 </value>
	*/
	public final String getDescription()
	{
		return description;
	}
	public final void setDescription(String value)
	{
		description = value;
	}

	/** 
		Gets and sets the picture data stored in the current
		instance.
	 
	 <value>
		A <see cref="ByteVector" /> object containing the picture
		data stored in the current instance.
	 </value>
	*/
	public final ByteVector getData()
	{
		return data;
	}
	public final void setData(ByteVector value)
	{
		data = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Private Methods

	/** 
		Fills in the mime type of the current instance by reading
		the first few bytes of the file. If the format cannot be
		identified, it assumed to be a JPEG file.
	*/
	private void FillInMimeFromData()
	{
		String mimetype = "image/jpeg";
		String ext = "jpg";

		if (getData().size() >= 4 && (getData().get(1) == 'P' && getData().get(2) == 'N' && getData().get(3) == 'G'))
		{
			mimetype = "image/png";
			ext = "png";
		}
		else if (getData().size() >= 3 && (getData().get(0) == 'G' && getData().get(1) == 'I' && getData().get(2) == 'F'))
		{
			mimetype = "image/gif";
			ext = "gif";
		}
		else if (getData().size() >= 2 && (getData().get(0) == 'B' && getData().get(1) == 'M'))
		{
			mimetype = "image/bmp";
			ext = "bmp";
		}

		setMimeType(mimetype);
		setType(PictureType.FrontCover);
		setDescription("cover." + ext);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}