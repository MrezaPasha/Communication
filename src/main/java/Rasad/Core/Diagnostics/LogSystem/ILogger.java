package Rasad.Core.Diagnostics.LogSystem;

import Rasad.Core.*;
import Rasad.Core.Diagnostics.*;

public interface ILogger
{
	// Identifiers
	int getSystemEntity();
	void setSystemEntity(int value);
	String getSystemEntityString();
	void setSystemEntityString(String value);
	Byte getServerID();
	void setServerID(Byte value);
	Short getCameraID();
	void setCameraID(Short value);
	RasadLogLevel getLogLevel();
	void setLogLevel(RasadLogLevel value);

	// Debug
	void Debug(String msg);
	void Debug(String msg, String data);

	// Information
	void Info(String msg);
	void Info(String msg, String data);

	// Warning
	void Warn(String msg);
	void Warn(String msg, String data);

	// Error
	void Error(String msg);
	//void Error(Exception exp);
	//void Error(String msg, Exception exp);

	// Fatal
	void Fatal(String msg);
	//void Fatal(Exception exp);
	//void Fatal(String msg, Exception exp);
}