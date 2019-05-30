package main.java.Rasad.Core;

import java.util.*;

public class CommandLineArgs extends HashMap<String, ArrayList<String>>
{
	public final int GetParamAsInt(String paramName)
	{
		ArrayList<String> val = null;
		tangible.OutObject<ArrayList<String>> tempOut_val = new tangible.OutObject<ArrayList<String>>();
		if (!this.TryGetValue(paramName, tempOut_val) || val == null || val.isEmpty())
		{
		val = tempOut_val.argValue;
			throw new RuntimeException(String.format("parameter %1$s is not specified", paramName));
		}
	else
	{
		val = tempOut_val.argValue;
	}
		if (val.size() > 1)
		{
			throw new RuntimeException(String.format("parameter %1$s is specified more than one time", paramName));
		}
		try
		{
			return Integer.parseInt(val.get(0));
		}
		catch (java.lang.Exception e)
		{
			throw new RuntimeException(String.format("parameter %1$s is not valid", paramName));
		}
	}

	public final String GetParamAsString(String paramName)
	{
		ArrayList<String> val = null;
		tangible.OutObject<ArrayList<String>> tempOut_val = new tangible.OutObject<ArrayList<String>>();
		if (!this.TryGetValue(paramName, tempOut_val) || val == null || val.isEmpty())
		{
		val = tempOut_val.argValue;
			throw new RuntimeException(String.format("parameter %1$s is not specified", paramName));
		}
	else
	{
		val = tempOut_val.argValue;
	}
		if (val.size() > 1)
		{
			throw new RuntimeException(String.format("parameter %1$s is specified more than one time", paramName));
		}
		return val.get(0);
	}

	public final String[] Format()
	{
		ArrayList<String> args = new ArrayList<String>();
		for (unknown arg : this)
		{
			if (arg.Value == null || arg.Value.Count == 0)
			{
				args.add(arg.Key);
			}
			else
			{
				for (String val : arg.Value)
				{
					args.add(String.format("/%1$s:%2$s", arg.Key, Uri.EscapeDataString(val)));
				}
			}
		}
		return args.toArray(new String[0]);
	}


	public static CommandLineArgs Parse(String[] args)
	{
		CommandLineArgs commandLineArgs = new CommandLineArgs();
		if (args.length == 0)
		{
			return commandLineArgs;
		}

		String pattern = "^/(?<argname>[A-Za-z0-9_\\-.%]+):(?<argvalue>.+)$";
		for (String x : args)
		{
			Match match = Regex.Match(x, pattern);

			if (!match.Success)
			{
				throw new RuntimeException("failed to parse command line");
			}
			String argname = match.Groups["argname"].Value.toLowerCase();
			ArrayList<String> values = null;
			tangible.OutObject<ArrayList<String>> tempOut_values = new tangible.OutObject<ArrayList<String>>();
			if (!commandLineArgs.TryGetValue(argname, tempOut_values))
			{
			values = tempOut_values.argValue;
				values = new ArrayList<String>();
				commandLineArgs.put(argname, values);
			}
		else
		{
			values = tempOut_values.argValue;
		}
			//var s = match.Groups["argvalue"].Value;
			values.add(Uri.UnescapeDataString(match.Groups["argvalue"].Value));
		}
		return commandLineArgs;
	}
}