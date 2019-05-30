package Rasad.Core.KeyboardTools;

import Rasad.Core.*;
import java.io.*;

@FunctionalInterface
public interface RawKeyEventHandler
{
	void invoke(Object sender, RawKeyEventArgs args);
}