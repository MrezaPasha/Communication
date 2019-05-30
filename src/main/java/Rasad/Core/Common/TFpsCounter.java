package main.java.Rasad.Core.Common;


public class TFpsCounter
{
	private int _Counter = 1;
	private int _CurrentTick = 0;

	public TFpsCounter()
	{
		setFramePerSecond(null);
	}

	public final void Reset()
	{
		setFramePerSecond(null);
		_CurrentTick = Environment.TickCount;
	}

	public final void Signal()
	{
		if (Rasad.Core.Common.TCommonUtilities.ElapsedTickCount(_CurrentTick) > 1000)
		{
			int elapsed = Rasad.Core.Common.TCommonUtilities.ElapsedTickCount(_CurrentTick);
			_CurrentTick = Environment.TickCount;
			setFramePerSecond((float)((double)_Counter / ((double)elapsed / (double)1000.0)));
			_Counter = 1;
		}
		else
		{
			_Counter++;
		}
	}
	private Float FramePerSecond = null;
	public final Float getFramePerSecond()
	{
		return FramePerSecond;
	}
	private void setFramePerSecond(Float value)
	{
		FramePerSecond = value;
	}
	@Override
	public String toString()
	{
//C# TO JAVA CONVERTER TODO TASK: Arithmetic operations involving nullable type instances are not converted to null-value logic:
		return "FramePersocond : " + getFramePerSecond();
	}
}