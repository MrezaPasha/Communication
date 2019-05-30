package main.java.Rasad.Core.Services;

import Rasad.Core.*;

public interface ISettingsService
{

	String GetValue(String key);
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: string GetValue(string key, string defaultValue = null);
	String GetValue(String key, String defaultValue);

	String GetValueForCurrentUser(String key);
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: string GetValueForCurrentUser(string key, string defaultValue = null);
	String GetValueForCurrentUser(String key, String defaultValue);
	void SetValue(String key, String value);
	void SetValueForCurrentUser(String key, String value);
	void SetValueForCurrentUserAsync(String key, String value);
}