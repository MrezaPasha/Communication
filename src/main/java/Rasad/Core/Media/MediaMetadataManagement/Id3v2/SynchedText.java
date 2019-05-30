package Rasad.Core.Media.MediaMetadataManagement.Id3v2;

import Rasad.Core.*;
import Rasad.Core.Media.*;
import Rasad.Core.Media.MediaMetadataManagement.*;
import java.util.*;

/** 
	This structure contains a single entry in a <see
	cref="SynchronisedLyricsFrame" /> object.
*/
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct SynchedText
public final class SynchedText
{
	/** 
		Contains the time offset.
	*/
	private long time;

	/** 
		Contains the text.
	*/
	private String text;

	/** 
		Constructs and initializes a new instance of <see
		cref="SynchedText" /> with a specified time and text.
	 
	 @param time
		A <see cref="long" /> value representing an amount of
		time in a format define in the class using it. The
		specific format is specified in <see
		cref="SynchronisedLyricsFrame.Format" />.
	 
	 @param text
		A <see cref="string" /> object containing the text
		for the point in time.
	 
	*/
	public SynchedText()
	{
	}

	public SynchedText(long time, String text)
	{
		this.time = time;
		this.text = text;
	}

	/** 
		Gets and sets the time offset of the current instance.
	 
	 <value>
		A <see cref="long" /> value representing an amount of
		time in a format define in the class using it. The
		specific format is specified in <see
		cref="SynchronisedLyricsFrame.Format" />.
	 </value>
	*/
	public long getTime()
	{
		return time;
	}
	public void setTime(long value)
	{
		time = value;
	}

	/** 
		Gets and sets the text for the point in time represented
		by the current instance.
	 
	 <value>
		A <see cref="string" /> object containing the text
		for the point in time.
	 </value>
	*/
	public String getText()
	{
		return text;
	}
	public void setText(String value)
	{
		text = value;
	}

	public SynchedText clone()
	{
		SynchedText varCopy = new SynchedText();

		varCopy.time = this.time;
		varCopy.text = this.text;

		return varCopy;
	}
}