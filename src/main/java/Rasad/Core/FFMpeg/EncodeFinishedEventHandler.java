package Rasad.Core.FFMpeg;

import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

@FunctionalInterface
public interface EncodeFinishedEventHandler
{
	void invoke(Object sender, EncodeFinishedEventArgs e);
}