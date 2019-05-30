package main.java.Rasad.Core.Services;

import Rasad.Core.*;
import main.java.Rasad.Core.Services.EventCategory;

import java.util.*;
import java.time.*;

public interface IEventItem
{
	EventCategory getCategory();
	void setCategory(EventCategory value);
	int getEventID();
	void setEventID(int value);
	UUID getEventOwnerKey();
	void setEventOwnerKey(UUID value);
	int getEventTypeID();
	void setEventTypeID(int value);

	String getEventTypeTitle();
	void setEventTypeTitle(String value);
	String getEventCause();
	void setEventCause(String value);

	LocalDateTime getEventDateTime();
	void setEventDateTime(LocalDateTime value);
	String getXmlContent();
	void setXmlContent(String value);
	String getIpAddress();
	void setIpAddress(String value);
	String getMacAddress();
	void setMacAddress(String value);
	String getDescription();
	void setDescription(String value);

	//TODO MRCOMMENT : must uncomment

/*
	FrameworkElement getPresenter();
*/
}