package main.java.Rasad.tangible;

@FunctionalInterface
public interface EventHandler<T extends tangible.EventArgs>
{
	void invoke(Object sender, T e);
}