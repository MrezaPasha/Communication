package main.java.Rasad.Core;

import java.util.*;

public class ExposedObject extends DynamicObject
{
	private HashMap<String, HashMap<Integer, ArrayList<java.lang.reflect.Method>>> m_genInstanceMethods;
	private HashMap<String, HashMap<Integer, ArrayList<java.lang.reflect.Method>>> m_instanceMethods;
	private Object m_object;
	private java.lang.Class m_type;

	private ExposedObject(Object obj)
	{
		m_object = obj;
		m_type = obj.getClass();

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		m_instanceMethods = m_type.getMethods(BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue()).Where(m -> !m.IsGenericMethod).GroupBy(m -> m.Name).ToDictionary(p -> p.Key, p -> p.GroupBy(r -> r.GetParameters().Length).ToDictionary(r -> r.Key, r -> r.ToList()));

//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
		m_genInstanceMethods = m_type.getMethods(BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue()).Where(m -> m.IsGenericMethod).GroupBy(m -> m.Name).ToDictionary(p -> p.Key, p -> p.GroupBy(r -> r.GetParameters().Length).ToDictionary(r -> r.Key, r -> r.ToList()));
	}

	public final Object getObject()
	{
		return m_object;
	}

	public static <T> dynamic New()
	{
		return New(T.class);
	}

	public static dynamic New(java.lang.Class type)
	{
		return From(type.newInstance());
	}

	public static dynamic From(Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		return new ExposedObject(obj);
	}

	public static <T> T Cast(ExposedObject t)
	{
		return (T) t.m_object;
	}

	@Override
	public boolean TryInvokeMember(InvokeMemberBinder binder, Object[] args, tangible.OutObject<Object> result)
	{
		// Get type args of the call
		java.lang.Class[] typeArgs = ExposedObjectHelper.GetTypeArgs(binder);
		if (typeArgs != null && typeArgs.length == 0)
		{
			typeArgs = null;
		}

		//
		// Try to call a non-generic instance method
		//
		if (typeArgs == null && m_instanceMethods.containsKey(binder.Name) && m_instanceMethods.get(binder.Name).containsKey(args.length) && ExposedObjectHelper.InvokeBestMethod(args, m_object, m_instanceMethods.get(binder.Name).get(args.length), result))
		{
			return true;
		}

		//
		// Try to call a generic instance method
		//
		if (m_instanceMethods.containsKey(binder.Name) && m_instanceMethods.get(binder.Name).containsKey(args.length))
		{
			ArrayList<java.lang.reflect.Method> methods = new ArrayList<java.lang.reflect.Method>();

			for (java.lang.reflect.Method method : m_genInstanceMethods.get(binder.Name).get(args.length))
			{
				if (method.GetGenericArguments().length == typeArgs.length)
				{
					methods.add(method.MakeGenericMethod(typeArgs));
				}
			}

			if (ExposedObjectHelper.InvokeBestMethod(args, m_object, methods, result))
			{
				return true;
			}
		}

		result.argValue = null;
		return false;
	}

	@Override
	public boolean TrySetMember(SetMemberBinder binder, Object value)
	{
		PropertyInfo propertyInfo = m_type.GetProperty(binder.Name, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue());

		if (propertyInfo != null)
		{
			propertyInfo.SetValue(m_object, value, null);
			return true;
		}

		java.lang.reflect.Field fieldInfo = m_type.getField(binder.Name, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue());

		if (fieldInfo != null)
		{
			fieldInfo.set(m_object, value);
			return true;
		}

		return false;
	}

	private boolean TryGetMember(java.lang.Class type, String member, tangible.OutObject<Object> result)
	{
		PropertyInfo propertyInfo = type.GetProperty(member, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue());

		if (propertyInfo != null)
		{
			result.argValue = propertyInfo.GetValue(m_object, null);
			return true;
		}

		java.lang.reflect.Field fieldInfo = type.getField(member, BindingFlags.NonPublic.getValue() | BindingFlags.Public.getValue() | BindingFlags.Instance.getValue());

		if (fieldInfo != null)
		{
			result.argValue = fieldInfo.GetValue(m_object);
			return true;
		}

		if (type.getSuperclass() != null)
		{
			return TryGetMember(type.getSuperclass(), member, result);
		}
		result.argValue = null;
		return false;
	}

	@Override
	public boolean TryGetMember(GetMemberBinder binder, tangible.OutObject<Object> result)
	{
		return TryGetMember(m_object.getClass(), binder.Name, result);
	}

	@Override
	public boolean TryConvert(ConvertBinder binder, tangible.OutObject<Object> result)
	{
		result.argValue = m_object;
		return true;
	}

	protected static class ExposedObjectHelper
	{
		private static final java.lang.Class SCsharpInvokePropertyType = RuntimeBinderException.class.getPackage().GetType("Microsoft.CSharp.RuntimeBinder.ICSharpInvokeOrInvokeMemberBinder");

		public static boolean InvokeBestMethod(Object[] args, Object target, ArrayList<java.lang.reflect.Method> instanceMethods, tangible.OutObject<Object> result)
		{
			if (instanceMethods.size() == 1)
			{
				// Just one matching instance method - call it
				if (TryInvoke(instanceMethods.get(0), target, args, result))
				{
					return true;
				}
			}
			else if (instanceMethods.size() > 1)
			{
				// Find a method with best matching parameters
				java.lang.reflect.Method best = null;
				java.lang.Class[] bestParams = null;
				java.lang.Class[] actualParams = args.Select(p -> p == null ? Object.class : p.getClass()).ToArray();

				tangible.Func2Param<java.lang.Class[], Type[], Boolean> isAssignableFrom = (a, b) ->
				{
						for (int i = 0; i < a.Length; i++)
						{
							if (!b[i].isAssignableFrom(a[i]))
							{
								return false;
							}
						}
						return true;
				};


//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
				for (java.lang.reflect.Method method : instanceMethods.Where(m -> m.GetParameters().Length == args.length))
				{
					java.lang.Class[] mParams = method.GetParameters().Select(x -> x.ParameterType).ToArray();
					if (isAssignableFrom.invoke(mParams, actualParams))
					{
						if (best == null || isAssignableFrom.invoke(bestParams, mParams))
						{
							best = method;
							bestParams = mParams;
						}
					}
				}

				if (best != null && TryInvoke(best, target, args, result))
				{
					return true;
				}
			}

			result.argValue = null;
			return false;
		}

		public static boolean TryInvoke(java.lang.reflect.Method methodInfo, Object target, Object[] args, tangible.OutObject<Object> result)
		{
			try
			{
				result.argValue = methodInfo.Invoke(target, args);
				return true;
			}
			catch (TargetInvocationException e)
			{
			}
			catch (TargetParameterCountException e2)
			{
			}

			result.argValue = null;
			return false;
		}

		public static java.lang.Class[] GetTypeArgs(InvokeMemberBinder binder)
		{
			if (SCsharpInvokePropertyType.isInstance(binder))
			{
				PropertyInfo typeArgsProperty = SCsharpInvokePropertyType.GetProperty("TypeArguments");
				return ((java.lang.Iterable<java.lang.Class>) typeArgsProperty.GetValue(binder, null)).ToArray();
			}
			return null;
		}
	}
}