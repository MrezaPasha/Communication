package main.java.Rasad.Core;

import java.util.*;
import java.io.*;

public interface Closeable<T> extends Closeable
{
	T getvalue();
}