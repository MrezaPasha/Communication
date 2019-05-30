package Rasad.Core.Dynamic;

import Rasad.Core.*;
import java.util.*;

public class TDynamicNotifyPropertyChanged<T> extends DynamicObject implements INotifyPropertyChanged
{
	private static final HashMap<String, HashMap<String, PropertyInfo>> properties = new HashMap<String, HashMap<String, PropertyInfo>>();

	private T instance;
	private String typeName;

	public TDynamicNotifyPropertyChanged(T instance)
	{
		this.instance = instance;
		java.lang.Class type = T.class;
		typeName = type.FullName;
		if (!properties.containsKey(typeName))
		{
			SetProperties(type, typeName);
		}
	}

	public tangible.Event<PropertyChangedEventHandler> PropertyChanged = new tangible.Event<PropertyChangedEventHandler>();

	private static void SetProperties(java.lang.Class type, String typeName)
	{
		PropertyInfo[] props = type.GetProperties(BindingFlags.Public.getValue() | BindingFlags.Instance.getValue());
		HashMap<String, PropertyInfo> dict = props.ToDictionary(prop -> prop.Name);
		properties.put(typeName, dict);
	}

	@Override
	public boolean TryGetMember(GetMemberBinder binder, tangible.OutObject<Object> result)
	{
		if (properties.get(typeName).containsKey(binder.Name))
		{
			result.argValue = properties.get(typeName).get(binder.Name).GetValue(instance, null);
			return true;
		}
		result.argValue = null;
		return false;
	}

	@Override
	public boolean TrySetMember(SetMemberBinder binder, Object value)
	{
		if (properties.get(typeName).containsKey(binder.Name))
		{
			properties.get(typeName).get(binder.Name).SetValue(instance, value, null);
			if (PropertyChanged != null)
			{
				for (PropertyChangedEventHandler listener : PropertyChanged.listeners())
				{
					listener.invoke(this, new PropertyChangedEventArgs(binder.Name));
				}
			}
			return true;
		}
		return false;
	}
}