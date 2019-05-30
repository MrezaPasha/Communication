package Rasad.Core.Media;

import NAudio.Dsp.*;
import NAudio.Wave.*;
import Rasad.Core.*;
import java.io.*;

public class FftEventArgs extends tangible.EventArgs
{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerStepThrough] public FftEventArgs(Complex[] result)
	public FftEventArgs(Complex[] result)
	{
		this.setResult(result);
	}
	private Complex[] Result;
	public final Complex[] getResult()
	{
		return Result;
	}
	private void setResult(Complex[] value)
	{
		Result = value;
	}
}