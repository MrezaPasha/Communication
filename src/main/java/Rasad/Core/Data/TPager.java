package Rasad.Core.Data;

import Rasad.Core.*;
import java.io.*;

public class TPager extends TViewModelBase
{
	private Timer _EventTimer;
	public TPager()
	{
		getCommands().AddCommand("FirstPage", FirstPage, () -> getCurrentPageNumber() > 1);
		getCommands().AddCommand("PreviousPage", PreviousPage, () -> getCurrentPageNumber() > 1);
		getCommands().AddCommand("NextPage", NextPage, () -> getCurrentPageNumber() < getPageCount());
		getCommands().AddCommand("LastPage", LastPage, () -> getCurrentPageNumber() < getPageCount());
		_EventTimer = new Timer(500);
		_EventTimer.AutoReset = false;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		_EventTimer.Elapsed += _EventTimer_Elapsed;
	}

	private void _EventTimer_Elapsed(Object sender, ElapsedEventArgs e)
	{
		_EventTimer.Stop();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var del = CurrentPageChanged;
		if (del != null)
		{
			(() ->
			{
					del(this, tangible.EventArgs.Empty);

			}).RunInUiWait();

		}
	}

	public final void Update(int rowCount, int pageItemCount)
	{
		if (pageItemCount <= 0)
		{
			throw new IllegalArgumentException("pageItemCount");
		}

		setItemCount(rowCount);
		setPageItemCount(pageItemCount);

		int remain = rowCount % pageItemCount;
		int pageCount = rowCount / pageItemCount;
		if (remain > 0)
		{
			pageCount++;
		}
		if (pageCount == 0)
		{
			pageCount = 1;
		}

		if (getCurrentPageNumber() > pageCount)
		{
			setCurrentPageNumber(getPageCount());
		}
		this.setPageCount(pageCount);

		getCommands().Update();
	}

	public final void LastPage()
	{
		setCurrentPageNumber(getPageCount());
	}

	public final void FirstPage()
	{
		setCurrentPageNumber(1);
	}

	public final void NextPage()
	{
		if (getCurrentPageNumber() < getPageCount())
		{
			setCurrentPageNumber(getCurrentPageNumber() + 1);
		}
	}
	public final void PreviousPage()
	{
		if (getCurrentPageNumber() > 1)
		{
			setCurrentPageNumber(getCurrentPageNumber() - 1);
		}
	}

	public final boolean GoToItem(int itemIndex)
	{
		if (itemIndex < 0)
		{
			return false;
		}
		if (itemIndex >= getItemCount())
		{
			return false;
		}
		GoToPage((itemIndex / getPageItemCount()) + 1);
		return true;
	}

	public final void GoToPage(int pageNumber)
	{
		if (pageNumber <= 0)
		{
			pageNumber = 1;
		}
		if (pageNumber > getPageCount())
		{
			pageNumber = getPageCount();
		}
		if (getCurrentPageNumber() != pageNumber)
		{
			setCurrentPageNumber(pageNumber);
		}
	}
	@Override
	public boolean close() throws IOException
	{
		if (super.close())
		{
			Timer timer = _EventTimer;
			if (timer != null)
			{
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
				timer.Elapsed -= _EventTimer_Elapsed;
				timer = null;
			}
			return true;
		}
		return false;
	}
	public final int getItemCount()
	{
		return this.<Integer>GetValue("ItemCount");
	}
	private void setItemCount(int value)
	{
		SetValue(value, "ItemCount");
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DefaultValue(1)] public int CurrentPageNumber
	public final int getCurrentPageNumber()
	{
		return this.<Integer>GetValue("CurrentPageNumber");
	}
	private void setCurrentPageNumber(int value)
	{
		if (SetValue(value, "CurrentPageNumber"))
		{
			_EventTimer.Stop();
			_EventTimer.Start();
		}
	}

	public final int getPageItemCount()
	{
		return this.<Integer>GetValue("PageItemCount");
	}
	private void setPageItemCount(int value)
	{
		SetValue(value, "PageItemCount");
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DefaultValue(0)] public int PageCount
	public final int getPageCount()
	{
		return this.<Integer>GetValue("PageCount");
	}
	private void setPageCount(int value)
	{
		SetValue(value, "PageCount");
	}

	public tangible.Event<tangible.EventHandler<tangible.EventArgs>> CurrentPageChanged = new tangible.Event<tangible.EventHandler<tangible.EventArgs>>();



}