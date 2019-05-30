package Rasad.Core.Compression.Zip;

import Rasad.Core.*;
import Rasad.Core.Compression.*;

public class EntryPatchData
{
	public final long getSizePatchOffset()
	{
		return sizePatchOffset_;
	}
	public final void setSizePatchOffset(long value)
	{
		sizePatchOffset_ = value;
	}

	public final long getCrcPatchOffset()
	{
		return crcPatchOffset_;
	}
	public final void setCrcPatchOffset(long value)
	{
		crcPatchOffset_ = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Instance Fields
	private long sizePatchOffset_;
	private long crcPatchOffset_;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}