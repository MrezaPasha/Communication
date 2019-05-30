package Rasad.Core.Diagnostics;

import Rasad.Core.*;

/** 
 Linear regression model estmated by ordinary least squares
*/
public class SimpleLinearRegression
{
	private double step = 1.0;
	private double Sx = 0;
	private double Sy = 0;
	private double Sxy = 0;
	private double Sxx = 0;
	private int n = 0;

	/** 
	 add new point
	 
	 @param y output of function over input x
	 @param x input over which y was evaluated
	*/
	public final void Next(double y, double x)
	{
		Sx += x;
		Sy += y;
		Sxy += x * y;
		Sxx += x * x;
		++n;
	}

	/** 
	 add new point
	 
	 @param y output of function over input n*step, where n - count of added points
	*/
	public final void Next(double y)
	{
		Next(y, step * n);
	}

	/** 
	 return pair (a,b) parameters of linear function l(x) = a*x + b
	 
	 @return 
	*/
	public final Tuple<Double, Double> GetParameters()
	{
		if (n < 2)
		{
			return Tuple.Create(0.0, 0.0);
		}
		double dn = n;
		double a = (Sxy * dn - Sx * Sy) / (Sxx * dn - Sx * Sx);
		double b = (Sy - a * Sx) / dn;
		return Tuple.Create(a, b);
	}
}