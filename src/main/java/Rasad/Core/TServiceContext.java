package main.java.Rasad.Core;

import java.util.*;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] public class TServiceContext : IServiceProvider, IServiceContainer
public class TServiceContext implements IServiceProvider, IServiceContainer
{
	static
	{
		setInstance(new TServiceContext());
	}

	public TServiceContext()
	{
		_Services = new TServiceInformationCollection(this);
	}

	private static TServiceContext Instance;
	public static TServiceContext getInstance()
	{
		return Instance;
	}
	private static void setInstance(TServiceContext value)
	{
		Instance = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Fields

	private TServiceInformationCollection _Services;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public final void AddService(java.lang.Class serviceType, ServiceCreatorCallback callback, boolean promote)
	{
		synchronized (_Services)
		{
			_Services.add(serviceType, callback, null);
		}
	}

	public final void AddService(java.lang.Class serviceType, ServiceCreatorCallback callback)
	{
		synchronized (_Services)
		{
			_Services.add(serviceType, callback, null);
		}
	}

	public final void AddService(java.lang.Class serviceType, Object serviceInstance, boolean promote)
	{
		synchronized (_Services)
		{
			_Services.add(serviceType, null, serviceInstance);
		}
	}

	public final void AddService(java.lang.Class serviceType, Object serviceInstance)
	{
		synchronized (_Services)
		{
			_Services.add(serviceType, null, serviceInstance);
		}
	}

	public final void RemoveService(java.lang.Class serviceType, boolean promote)
	{
		synchronized (_Services)
		{
			_Services.remove(serviceType);
		}
	}

	public final void RemoveService(java.lang.Class serviceType)
	{
		synchronized (_Services)
		{
			_Services.remove(serviceType);
		}
	}

	public final Object GetService(java.lang.Class serviceType)
	{
		synchronized (_Services)
		{
			TServiceInformation SI = _Services.FirstOrDefault(t -> serviceType.isAssignableFrom(t.ServiceType));
			if (SI == null)
			{
				return null;
			}
			return SI.getServiceInstance();
		}
	}

	public final <serviceType> void AddService(Object serviceInstance)
	{
		synchronized (_Services)
		{
			if (!_Services.Any(t -> t.ServiceType == serviceType.class))
			{
				AddService(serviceType.class, serviceInstance);
			}
		}
	}

	private static class TServiceInformation
	{
		private TServiceContext _Owner;
		private Object _ServiceInstance;


		public TServiceInformation(TServiceContext owner, java.lang.Class serviceType, ServiceCreatorCallback creatorCallback)
		{
			this(owner, serviceType, creatorCallback, null);
		}

		public TServiceInformation(TServiceContext owner, java.lang.Class serviceType)
		{
			this(owner, serviceType, null, null);
		}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TServiceInformation(TServiceContext owner, Type serviceType, ServiceCreatorCallback creatorCallback = null, object serviceInstance = null)
		public TServiceInformation(TServiceContext owner, java.lang.Class serviceType, ServiceCreatorCallback creatorCallback, Object serviceInstance)
		{
			_Owner = owner;
			setServiceType(serviceType);
			setCreatorCallback((System.ComponentModel.Design.IServiceContainer container, java.lang.Class serviceType) -> creatorCallback.invoke(container, serviceType));
			_ServiceInstance = serviceInstance;
		}

		private java.lang.Class ServiceType;
		public final java.lang.Class getServiceType()
		{
			return ServiceType;
		}
		public final void setServiceType(java.lang.Class value)
		{
			ServiceType = value;
		}

		public final Object getServiceInstance()
		{
			if (_ServiceInstance == null && getCreatorCallback() != null)
			{
				_ServiceInstance = getCreatorCallback().Invoke(_Owner, getServiceType());
			}
			return _ServiceInstance;
		}

		private ServiceCreatorCallback CreatorCallback;
		public final ServiceCreatorCallback getCreatorCallback()
		{
			return CreatorCallback;
		}
		public final void setCreatorCallback(ServiceCreatorCallback value)
		{
			CreatorCallback = (System.ComponentModel.Design.IServiceContainer container, java.lang.Class serviceType) -> value.invoke(container, serviceType);
		}

		@Override
		public String toString()
		{
			if (getServiceInstance() != null)
			{
				return getServiceInstance().getClass().getSimpleName();
			}
			else if (getServiceType() != null)
			{
				return getServiceType().getSimpleName();
			}
			else
			{
				return super.toString();
			}
		}
	}

	private static class TServiceInformationCollection extends ArrayList<TServiceInformation>
	{
		public TServiceInformationCollection(TServiceContext owner)
		{
			setOwner(owner);
		}

		private TServiceContext Owner;
		public final TServiceContext getOwner()
		{
			return Owner;
		}
		private void setOwner(TServiceContext value)
		{
			Owner = value;
		}


		public final void Add(java.lang.Class serviceType, ServiceCreatorCallback creatorCallback)
		{
			Add(serviceType, creatorCallback, null);
		}

		public final void Add(java.lang.Class serviceType)
		{
			Add(serviceType, null, null);
		}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public void Add(Type serviceType, ServiceCreatorCallback creatorCallback = null, object serviceInstance = null)
		public final void Add(java.lang.Class serviceType, ServiceCreatorCallback creatorCallback, Object serviceInstance)
		{
			this.add(new TServiceInformation(getOwner(), serviceType, creatorCallback, serviceInstance));
		}

		public final int Remove(java.lang.Class serviceType)
		{
			return RemoveAll(t -> t.ServiceType == serviceType);
		}
	}
}