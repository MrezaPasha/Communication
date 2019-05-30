package Rasad.Core.Data;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

@FunctionalInterface
public interface ActionCompletedEventHandler
{
	void invoke(TSqlBackup sender, RuntimeException exc);
}