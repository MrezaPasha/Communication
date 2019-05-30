package main.java.Rasad.Core.Services;

import Rasad.Core.*;

import java.util.*;

public interface IEventOwner
{
	UUID getKey();
	String getTitle();
	//TODO MRCOMMENT : must uncomment
	//FrameworkElement CreatePresenter(IEventItem eventItem);

	String GetImageUrl(IEventItem eventItem);

	java.lang.Iterable<TEventType> getEventTypes();

}