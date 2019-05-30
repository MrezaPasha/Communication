package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.IEventItem;

import java.util.*;

public interface IEventsService
{
	void AddEvent(IEventItem eventItem);
	void DeleteEvent(int eventID);
	void DeleteEvent(IEventItem eventItem);
	//TODO MRCOMMENT : convert observable connection to list
	List<IEventItem> getEvents();

	void RegisterEventOwner(IEventOwner eventOwner);
	void UnRegisterEventOwner(UUID eventOwnerKey);

	java.lang.Iterable<IEventOwner> getEventOwners();
	IEventOwner get(UUID eventOwnerKey);
}