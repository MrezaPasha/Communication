package main.java.Rasad.Core;

import Rasad.Core.*;
import java.util.*;
import java.io.*;

public interface IMvvmItem extends INotifyPropertyChanged, Closeable
{
	Rasad.Core.ViewModelState getState();
	void setState(Rasad.Core.ViewModelState value);
}