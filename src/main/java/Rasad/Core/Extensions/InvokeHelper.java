package Rasad.Core.Extensions;

import Rasad.Core.*;
import java.util.*;

//internal class Helpers
//{
//    static private System.Collections.Generic.SortedDictionary<object, InvokeHelper> m_List = new SortedDictionary<object, InvokeHelper>();
//    public static void AddHelper(object Instance, InvokeHelper helper)
//    {
//        m_List.Add(Instance, helper);
//    }
//    public static InvokeHelper find(object Instance)
//    {
//        if (m_List.ContainsKey(Instance))
//            return m_List[Instance];
//        else
//            return null;
//    }

//}
public class InvokeHelper
{
	private static final HashMap<String, java.lang.Class> Types = new HashMap<String, java.lang.Class>();
	private java.lang.Class m_Type;
	protected Object m_Instance;

	public InvokeHelper(Object Instance)
	{
		m_Instance = Instance;
		m_Type = Instance.getClass();
		//            Helpers.AddHelper(Instance, this);
	}

	public InvokeHelper(Object Instance, java.lang.Class type)
	{
		m_Instance = Instance;
		m_Type = type;
	}

	public final Object getInstance()
	{
		return m_Instance;
	}

	public final boolean HasField(String FieldName)
	{
		for (java.lang.reflect.Field field : m_Type.getFields(BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue()))
		{
			if (field.Name.equals(FieldName))
			{
				return true;
			}
		}
		return false;
	}

	public final Object GetProperty(String FieldName)
	{
		return m_Type.InvokeMember(FieldName, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue() | BindingFlags.GetProperty.getValue(), null, m_Instance, null);
	}

	public final Object SetProperty(String FieldName, Object Value)
	{
		return m_Type.InvokeMember(FieldName, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue() | BindingFlags.SetProperty.getValue(), null, m_Instance, new Object[] {Value});
	}

	public final Object GetField(String FieldName, Object[] args)
	{
		return m_Type.InvokeMember(FieldName, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue() | BindingFlags.GetField.getValue(), null, m_Instance, args);
	}

	public final Object SetField(String FieldName, Object[] args)
	{
		return m_Type.InvokeMember(FieldName, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue() | BindingFlags.SetField.getValue(), null, m_Instance, args);
	}

	public final Object SetField(String FieldName, Object Value)
	{
		return SetField(FieldName, new Object[] {Value});
	}

	public final Object StaticGetProperty(String PropertyName)
	{
		return m_Type.InvokeMember(PropertyName, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Static.getValue() | BindingFlags.GetProperty.getValue(), null, null, null);
	}

	//public object CreateInstance(object[] args)
	//{
	//    return null;
	//}


	public final Object GetField(String FieldName)
	{
		return GetField(FieldName, null);
	}

	public final Object InvokeByRef(String MethodName, tangible.RefObject<Object[]> args, ParameterModifier modifiers)
	{
		ParameterModifier[] mod = {modifiers};

		return m_Type.InvokeMember(MethodName, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue() | BindingFlags.InvokeMethod.getValue(), null, m_Instance, args.argValue, mod, null, null);
	}

	public final Object InvokeByOut(String MethodName, Object[] args, ParameterModifier modifiers)
	{
		ParameterModifier[] mod = {modifiers};

		return m_Type.InvokeMember(MethodName, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue() | BindingFlags.InvokeMethod.getValue(), null, m_Instance, args, mod, null, null);
	}

	public final Object Invoke(String MethodName, Object[] args)
	{
		return m_Type.InvokeMember(MethodName, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue() | BindingFlags.InvokeMethod.getValue(), null, m_Instance, args);
	}

	public final Object InvokeStatic(String MethodName, Object[] args)
	{
		return m_Type.InvokeMember(MethodName, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Static.getValue() | BindingFlags.InvokeMethod.getValue(), null, null, args);
	}

	public final Object InvokeStatic(String MethodName, Object arg1, Object arg2)
	{
		return InvokeStatic(MethodName, new Object[] {arg1, arg2});
	}

	public final Object InvokeStatic(String MethodName, Object arg1)
	{
		return InvokeStatic(MethodName, new Object[] {arg1});
	}

	public final Object InvokeStatic(String MethodName)
	{
		return InvokeStatic(MethodName, null);
	}


	public final Object Invoke(String MethodName)
	{
		return Invoke(MethodName, null);
	}

	public final Object Invoke(String MethodName, Object arg1)
	{
		return Invoke(MethodName, new Object[] {arg1});
	}

	public final Object Invoke(String MethodName, int arg1)
	{
		return Invoke(MethodName, new Object[] {arg1});
	}

	public final Object Invoke(String MethodName, Object obj1, Object obj2)
	{
		return Invoke(MethodName, new Object[] {obj1, obj2});
	}

	public final Object Invoke(String MethodName, Object obj1, Object obj2, Object obj3)
	{
		return Invoke(MethodName, new Object[] {obj1, obj2, obj3});
	}

	public static java.lang.Class FindType(String TypeName)
	{
		if (Types.containsKey(TypeName))
		{
			return Types.get(TypeName);
		}
		java.lang.Class ret = null;
		for (Assembly assembly : AppDomain.CurrentDomain.GetAssemblies())
		{
			for (Module module : assembly.GetModules())
			{
				for (java.lang.Class type : module.GetTypes())
				{
					if (type.FullName.equals(TypeName))
					{
						ret = type;
						Types.put(TypeName, ret);
						return ret;
					}
				}
			}
		}
		Types.put(TypeName, ret);

		return ret;
	}
}