package main.java.Rasad.Core.Common;


public class TListItemHolder<T>
{
	public TListItemHolder(T value, String title)
	{
		this.setValue(value);
		this.setTitle(title);
	}

	private T Value;
	public final T getValue()
	{
		return Value;
	}
	private void setValue(T value)
	{
		Value = value;
	}
	private String Title;
	public final String getTitle()
	{
		return Title;
	}
	private void setTitle(String value)
	{
		Title = value;
	}
}