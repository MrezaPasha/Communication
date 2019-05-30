package main.java.Rasad.Core;

import Rasad.Core.*;
import Rasad.Core.ViewModelState;
import main.java.Rasad.Core.IMvvmItem;
import main.java.Rasad.Core.TViewModelBase;

import java.util.*;
import java.io.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DebuggerStepThrough] public class TViewModelBaseCollection<T> : ObservableCollection<T>, IMvvmItem where T : class, IMvvmItem
public class TViewModelBaseCollection<T extends IMvvmItem> extends ObservableCollection<T> implements IMvvmItem
{


	public TViewModelBaseCollection()
	{
		setState(Rasad.Core.ViewModelState.Added);
		_deletedItems = new ObservableCollection<T>();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlIgnore] private TCommandMap _Commands;
	private TCommandMap _Commands;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [XmlIgnore] private ObservableCollection<T> _deletedItems;
	private ObservableCollection<T> _deletedItems;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] private Dictionary<string, object> _values = new Dictionary<string, object>();
	private HashMap<String, Object> _values = new HashMap<String, Object>();
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Properties


	public static boolean getDesignMode()
	{
		return TViewModelBase.getDesignMode();
	}

	public final TCommandMap getCommands()
	{
		if (_Commands == null)
		{
			_Commands = new TCommandMap();
		}
		return _Commands;
	}

	public ObservableCollection<T> getDeletedItems()
	{
		return _deletedItems;
	}

	public final boolean getHasChanges()
	{
		return this.Any(t -> t.State != ViewModelState.UnChanged) || getDeletedItems().Any();
	}

	private ViewModelState State = ViewModelState.values()[0];
	public final ViewModelState getState()
	{
		return State;
	}
	public final void setState(ViewModelState value)
	{
		State = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Methods


	public void close() throws IOException
	{
		if (_deletedItems != null)
		{
			_deletedItems.Clear();
			_deletedItems = null;
		}
	}




	public final Object GetValue()
	{
		return GetValue(null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: public object GetValue([CallerMemberName] string propertyName = null)
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	public final Object GetValue(String propertyName)
	{
		synchronized (this)
		{
			Object result;
			if (!(_values.containsKey(propertyName) ? (result = _values.get(propertyName)) == result : false))
			{
				result = null;
			}
			return result;
		}
	}


	public final <K> K GetValue()
	{
		return GetValue(null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: public K GetValue<K>([CallerMemberName] string propertyName = null)
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	public final <K> K GetValue(String propertyName)
	{
		Object Result;
		if (!(_values.containsKey(propertyName) ? (Result = _values.get(propertyName)) == Result : false))
		{
			Result = null;
		}
		return (K)Result;
	}


	public final <PropType> boolean SetValue(PropType value, String propertyName)
	{
		return SetValue(value, propertyName, true);
	}

	public final <PropType> boolean SetValue(PropType value)
	{
		return SetValue(value, null, true);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: public bool SetValue<PropType>(PropType value, [CallerMemberName] string propertyName = null, bool updateCommands = true)
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	public final <PropType> boolean SetValue(PropType value, String propertyName, boolean updateCommands)
	{
		synchronized (this)
		{
			Object oldValue = this.<PropType>GetValue(propertyName);

			if (Equals(oldValue, value))
			{
				return false;
			}
			_values.put(propertyName, value);
			if (getState() == ViewModelState.UnChanged)
			{
				setState(ViewModelState.Modified);
			}
			if (updateCommands)
			{
				getCommands().Update();
			}
			OnPropertyChanged(propertyName);
			return true;
		}
	}


	//private T _SelectedItem;
	//public T SelectedItem
	//{
	//    get
	//    {
	//        return _SelectedItem;
	//    }
	//    set
	//    {
	//        if (!_SelectedItem.Equals(value))
	//        {
	//            _SelectedItem = value;
	//            OnPropertyChanged("SelectedItem");
	//            OnPropertyChanged("ExistCurrentItem");
	//        }
	//    }
	//}


	public final void OnPropertyChanged()
	{
		OnPropertyChanged(null);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: public void OnPropertyChanged([CallerMemberName] string propertyName = null)
//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
	public final void OnPropertyChanged(String propertyName)
	{
		super.OnPropertyChanged(new PropertyChangedEventArgs(propertyName));
	}
	public final <T> void OnPropertyChanged(Expression<tangible.Func0Param<T>> propertySelector)
	{
		OnPropertyChanged(propertySelector.GetPropertyName());
	}
	//public bool ExistCurrentItem
	//{
	//    get
	//    {
	//        return SelectedItem != null;
	//    }
	//}

	public final boolean DeleteItem(T item)
	{
		if (item.getState() != ViewModelState.Added)
		{
			item.setState(ViewModelState.Deleted);
			getDeletedItems().Add(item);
		}
		return this.Remove(item);
	}

	public final void AddItem(T item)
	{
		item.setState(ViewModelState.Added);
		this.Add(item);
	}

	public final void AcceptChanges()
	{
		for (T item : this)
		{
			item.setState(ViewModelState.UnChanged);
		}
		getDeletedItems().Clear();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Events
//C# TO JAVA CONVERTER TODO TASK: This event cannot be converted to Java:
//	public new event PropertyChangedEventHandler PropertyChanged
//		{
//			add
//			{
//				super.PropertyChanged += value;
//			}
//			remove
//			{
//				super.PropertyChanged -= value;
//			}
//		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}