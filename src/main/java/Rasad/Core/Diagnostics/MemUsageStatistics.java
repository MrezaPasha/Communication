package Rasad.Core.Diagnostics;

import Rasad.Core.*;

public class MemUsageStatistics
{
	public double la;
	public double lb;
	public long min;
	public long max;

	public MemUsageStatistics(double a, double b, long min, long max)
	{
		this.la = a;
		this.lb = b;
		this.min = min;
		this.max = max;
	}
	public MemUsageStatistics(Tuple<Double, Double> ab, long min, long max)
	{
		this(ab.Item1, ab.Item2, min, max);
	}

	public static MemUsageStatistics Collect(tangible.Action0Param action, int iterations)
	{
		return Collect(_ ->
		{
				action.invoke();
				System.runFinalization();
				return GC.GetTotalMemory(true);
		}, iterations);
	}

	private static class Clojure
	{
		private tangible.Action0Param action;
		public Clojure(tangible.Action0Param action)
		{
			this.action = () -> action.invoke();
		}
		public static Clojure Create(tangible.Action0Param action)
		{
			return new Clojure(action);
		}
		public final long MemUsage(int i)
		{
			action.invoke();
			System.runFinalization();
			return GC.GetTotalMemory(true);
		}
	}
	public static boolean Validate(tangible.Action0Param action, int iterations)
	{
		return Validate(Clojure.Create(action).MemUsage, iterations);
	}

	public static boolean Validate(tangible.Func1Param<Integer, Long> action, int iterations)
	{
		Rasad.Core.Diagnostics.MemUsageStatistics stat = Collect(action, iterations);
		long delta = stat.max - stat.min;
		if (delta > 10 * iterations || Math.abs(stat.la) > 0.5)
		{
			return false;
		}
		double lmin;
		double lmax;
		if (stat.la < 0)
		{
			lmin = stat.la * iterations + stat.lb;
			lmax = stat.lb;
		}
		else
		{
			lmin = stat.lb;
			lmax = stat.la * iterations + stat.lb;
		}
		boolean res = lmin >= (stat.min - 0.25 * delta) && lmax <= (stat.max + 0.25 * delta);
		if (!res)
		{
			return false;
		}
		return true;
	}

	public static MemUsageStatistics Collect(tangible.Func1Param<Integer, Long> func, int iterations)
	{
		if (iterations < 1)
		{
			return null;
		}
		SimpleLinearRegression slr = new SimpleLinearRegression();

//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var memUsg = func.invoke(0);
		slr.Next(memUsg, 0);

		var min = memUsg;
		var max = memUsg;

		for (int i = 1; i < iterations; ++i)
		{
			memUsg = func.invoke(i);

			slr.Next(memUsg, i);
			if (memUsg > max)
			{
				max = memUsg;
			}
			else if (memUsg < min)
			{
				min = memUsg;
			}
		}

		return new MemUsageStatistics(slr.GetParameters(), min, max);
	}

	@Override
	public String toString()
	{
		return String.format("velocity = %0.6fx %3$s%1.0f", la, lb, lb < 0 ? "" : "+");
	}
}