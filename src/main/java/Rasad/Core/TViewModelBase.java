package main.java.Rasad.Core;

import main.java.Rasad.Core.IMvvmItem;

import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerStepThrough][Serializable][DataContract(IsReference = true)] public abstract class TViewModelBase : TNotifyPropertyChangeObject, IMvvmItem, IDataErrorInfo
public abstract class TViewModelBase extends TNotifyPropertyChangeObject implements IMvvmItem, IDataErrorInfo, Serializable //, ITypedList
{
	protected TViewModelBase()
	{
		setState(Rasad.Core.ViewModelState.Added);
		_ruleMap = new HashMap<String, TRuleBinder>();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields

	private static boolean _DesignMode = true;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlIgnore][IgnoreDataMember] private TCommandMap _Commands;
	private TCommandMap _Commands;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlIgnore][IgnoreDataMember] private Dictionary<string, TRuleBinder> _ruleMap;
	private HashMap<String, TRuleBinder> _ruleMap;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties
	public static boolean getDesignMode()
	{
		return _DesignMode;
	}
	public static void setDesignMode(boolean value)
	{
		_DesignMode = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)] public TCommandMap Commands
	public final TCommandMap getCommands()
	{
		if (_Commands == null)
		{
			_Commands = new TCommandMap();
		}
		return _Commands;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)] public virtual bool IsLoading
	public boolean getIsLoading()
	{
		return this.<Boolean>GetValue();
	}
	public void setIsLoading(boolean value)
	{
		if (SetValue(value))
		{
			getCommands().Update();
		}
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)] public bool IsDisposed {get;private set;}
	private boolean IsDisposed;
	public final boolean getIsDisposed()
	{
		return IsDisposed;
	}
	private void setIsDisposed(boolean value)
	{
		IsDisposed = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)] public ViewModelState State {get;set;}
	private ViewModelState State = ViewModelState.values()[0];
	public final ViewModelState getState()
	{
		return State;
	}
	public void setState(ViewModelState value)
	{
		State = value;
	}


//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)] public bool HasErrors
	public final boolean getHasErrors()
	{
		ArrayList<Object> values = _ruleMap.values().ToList();
		values.forEach(b -> b.Update());

		return values.Any(b -> b.HasError);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)] public string Error
	public final String getError()
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		var errors = from b in _ruleMap.values() where b.HasError select b.Error;

		return tangible.StringHelper.join("\n", errors);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Browsable(false)] public string this[string columnName]
	public final String get(String columnName)
	{
		if (_ruleMap.containsKey(columnName))
		{
			_ruleMap.get(columnName).Update();
			return _ruleMap.get(columnName).getError();
		}
		return null;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [OnDeserialized] private void OnDeserialized(System.Runtime.Serialization.StreamingContext c)
	private void OnDeserialized(System.Runtime.Serialization.StreamingContext c)
	{
		if (_ruleMap == null)
		{
			_ruleMap = new HashMap<String, TRuleBinder>();
		}
		OnDeserialized();
	}

	protected void OnDeserialized()
	{

	}

	/** 
	 Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
	 
	 <filterpriority>2</filterpriority>
	*/
	public boolean close() throws IOException
	{
		if (getIsDisposed())
		{
			return false;
		}
		setIsDisposed(true);
		return true;
	}
	public final void close() throws IOException
	{
		this.close();
	}

	public static <T> T Clone(T sender)
	{
		return TSerializationHelper.Clone(sender);
	}

	public static <T> T Deserialize(String serializedObject)
	{
		if (tangible.StringHelper.isNullOrEmpty(serializedObject))
		{
			return null;
		}
		return TSerializationHelper.<T>DataContractDeserialize(serializedObject);
	}

	public static String Serialize(Object value)
	{
		if (value == null)
		{
			return null;
		}
		return TSerializationHelper.DataContractSerialize(value);
	}

	public final <T> void AddRule(Expression<tangible.Func0Param<T>> expression, tangible.Func0Param<Boolean> ruleDelegate, String errorMessage)
	{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
		var name = expression.GetPropertyName();

		_ruleMap.put(name, new TRuleBinder(ruleDelegate, errorMessage));
	}


	public final <T> boolean SetValue(T value, String propertyName)
	{
		return SetValue(value, propertyName, true);
	}

	public final <T> boolean SetValue(T value)
	{
		return SetValue(value, null, true);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: public bool SetValue<T>(T value, [CallerMemberName] string propertyName = null, bool updateCommands = true)
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	public final <T> boolean SetValue(T value, String propertyName, boolean updateCommands)
	{
		if (System.TStringHelper.IsNullOrEmpty(propertyName))
		{
			throw new NullPointerException("propertyName");
		}

		boolean result = false;
		(() ->
		{
				result = super.SetValue(value, propertyName);
				if (result)
				{
					if (_ruleMap != null && _ruleMap.containsKey(propertyName))
					{
						_ruleMap.get(propertyName).setIsDirty(true);
					}

					if (getState() == ViewModelState.UnChanged)
					{
						setState(ViewModelState.Modified);
					}
					if (updateCommands)
					{
						getCommands().Update();
					}
				}
		}).RunInUiWait();
		return result;
	}

	/** 
	 
	 
	 <typeparam name="T"></typeparam>
	 @param value
	 @param propertySelector
	 @return 
	*/
//C# TO JAVA CONVERTER WARNING: There is no Java equivalent to C#'s shadowing via the 'new' keyword:
//ORIGINAL LINE: public new bool SetValue<T>(T value, Expression<Func<T>> propertySelector)
	public final <T> boolean SetValue(T value, Expression<tangible.Func0Param<T>> propertySelector)
	{
		return SetValue(value, propertySelector.GetPropertyName());
	}

	public final void ValidateAll()
	{
		for (Map.Entry<String, TRuleBinder> item : _ruleMap.entrySet())
		{
			Validate(item.getKey());
		}
	}

	public final <T> void Validate(Expression<tangible.Func0Param<T>> propertySelector)
	{
		Validate(propertySelector.GetPropertyName());
	}


	public final void Validate()
	{
		Validate(null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: public void Validate([CallerMemberName] string propertyName = null)
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	public final void Validate(String propertyName)
	{
		if (_ruleMap.containsKey(propertyName))
		{
			_ruleMap.get(propertyName).setIsDirty(true);
			OnPropertyChanged(propertyName);
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Internal Classes

	private static class TRuleBinder
	{
		private tangible.Func0Param<Boolean> ruleDelegate;
		private String message;
		public TRuleBinder(tangible.Func0Param<Boolean> ruleDelegate, String message)
		{
			this.ruleDelegate = () -> ruleDelegate.invoke();
			this.message = message;

			setIsDirty(false);
		}
		private String Error;
		public final String getError()
		{
			return Error;
		}
		public final void setError(String value)
		{
			Error = value;
		}
		private boolean HasError;
		public final boolean getHasError()
		{
			return HasError;
		}
		public final void setHasError(boolean value)
		{
			HasError = value;
		}
		private boolean IsDirty;
		public final boolean getIsDirty()
		{
			return IsDirty;
		}
		public final void setIsDirty(boolean value)
		{
			IsDirty = value;
		}
		public final void Update()
		{
			if (!getIsDirty())
			{
				return;
			}
			setIsDirty(false);
			setError(null);
			setHasError(false);
			try
			{
				if (!ruleDelegate.invoke())
				{
					setError(message);
					setHasError(true);
				}
			}
			catch (RuntimeException e)
			{
				setError(e.getMessage());
				setHasError(true);
			}
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}