package Rasad.Core.Globalization;

import Rasad.Core.Globalization.Exceptions.*;
import Rasad.Core.*;
import java.util.*;
import java.io.*;
import java.time.*;

/** 
	 PersianDate class to work with dates in Jalali calendar transparently.
	 <example>
		 An example on how to convert current System.DateTime to PersianDate.
		 <code>
 		class MyClass 
	  {
 		   public static void Main() 
		 {
 			  Console.WriteLine("Current Persian Date Is : " + PersianDate.Now.ToString());
 		   }
 		}