package main.java.Rasad.Core.ActiveDirectory.ADPicker;

import Rasad.Core.*;
import main.java.Rasad.Core.ActiveDirectory.ADPicker.DirectoryLocations;
import main.java.Rasad.Core.ActiveDirectory.ADPicker.DirectoryObject;
import main.java.Rasad.Core.ActiveDirectory.ADPicker.DirectoryObjectTypes;

import java.io.*;
import javax.swing.*;

public class ObjectPickerEx extends CommonDialog
{
	private DirectoryObject[] _selectedObjects;
	private String[] attributeNames;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint length;
	private int length;

	private DirectoryObjectTypes AllowedTypes = DirectoryObjectTypes.values()[0];
	public final DirectoryObjectTypes getAllowedTypes()
	{
		return AllowedTypes;
	}
	public final void setAllowedTypes(DirectoryObjectTypes value)
	{
		AllowedTypes = value;
	}

	private DirectoryLocations AllowedLocations = DirectoryLocations.values()[0];
	public final DirectoryLocations getAllowedLocations()
	{
		return AllowedLocations;
	}
	public final void setAllowedLocations(DirectoryLocations value)
	{
		AllowedLocations = value;
	}

	private DirectoryObjectTypes DefaultTypes = DirectoryObjectTypes.values()[0];
	public final DirectoryObjectTypes getDefaultTypes()
	{
		return DefaultTypes;
	}
	public final void setDefaultTypes(DirectoryObjectTypes value)
	{
		DefaultTypes = value;
	}

	private DirectoryLocations DefaultLocations = DirectoryLocations.values()[0];
	public final DirectoryLocations getDefaultLocations()
	{
		return DefaultLocations;
	}
	public final void setDefaultLocations(DirectoryLocations value)
	{
		DefaultLocations = value;
	}

	private BuiltInPrincipals AllowedBuiltInPricipals = BuiltInPrincipals.values()[0];
	public final BuiltInPrincipals getAllowedBuiltInPricipals()
	{
		return AllowedBuiltInPricipals;
	}
	public final void setAllowedBuiltInPricipals(BuiltInPrincipals value)
	{
		AllowedBuiltInPricipals = value;
	}

	private String TargetComputer;
	public final String getTargetComputer()
	{
		return TargetComputer;
	}
	public final void setTargetComputer(String value)
	{
		TargetComputer = value;
	}

	private boolean MultiSelect;
	public final boolean getMultiSelect()
	{
		return MultiSelect;
	}
	public final void setMultiSelect(boolean value)
	{
		MultiSelect = value;
	}

	private ADsPathType PathType = ADsPathType.values()[0];
	public final ADsPathType getPathType()
	{
		return PathType;
	}
	public final void setPathType(ADsPathType value)
	{
		PathType = value;
	}

	public final DirectoryObject[] getSelectedObjects()
	{
		if (this._selectedObjects == null)
		{
			return new DirectoryObject[0];
		}
		return (DirectoryObject[])this._selectedObjects.clone();
	}

	public ObjectPickerEx()
	{
		this.Reset();
		this.attributeNames = new String[] {"objectSid"};
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: this.length = (uint)this.attributeNames.Length;
		this.length = (int)this.attributeNames.length;
	}

	@Override
	public void Reset()
	{
		this.setAllowedTypes(DirectoryObjectTypes.All);
		this.setAllowedLocations(DirectoryLocations.All);
		this.setDefaultTypes(DirectoryObjectTypes.All);
		this.setDefaultLocations(DirectoryLocations.None);
		this.setPathType(ADsPathType.Ldap);
		this.setMultiSelect(false);
		this.setTargetComputer((String)null);
		this._selectedObjects = (DirectoryObject[])null;
	}

	@Override
	protected boolean RunDialog(IntPtr hwndOwner)
	{
		IntPtr zero = IntPtr.Zero;
		ObjectPickerEx.NativeMethods.IDsObjectPicker dsObjectPicker = this.Initialize();
		if (dsObjectPicker == null)
		{
			this._selectedObjects = (DirectoryObject[])null;
			return false;
		}
		tangible.RefObject<IntPtr> tempRef_zero = new tangible.RefObject<IntPtr>(zero);
		int num1 = dsObjectPicker.InvokeDialog(hwndOwner, tempRef_zero);
	zero = tempRef_zero.argValue;
		if (System.IntPtr.OpInequality(zero, IntPtr.Zero))
		{
			this._selectedObjects = this.ProcessSelections(zero);
		}
		int num2 = 0;
		return num1 == num2;
	}

	private ObjectPickerEx.NativeMethods.IDsObjectPicker Initialize()
	{
		ObjectPickerEx.NativeMethods.IDsObjectPicker dsObjectPicker = (ObjectPickerEx.NativeMethods.IDsObjectPicker)new ObjectPickerEx.NativeMethods.DSObjectPicker();
		ObjectPickerEx.NativeMethods.DSOP_SCOPE_INIT_INFO[] dsopScopeInitInfoArray = new ObjectPickerEx.NativeMethods.DSOP_SCOPE_INIT_INFO[2];
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint defaultFilter = this.GetDefaultFilter();
		int defaultFilter = this.GetDefaultFilter();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint upLevelFilter = this.GetUpLevelFilter();
		int upLevelFilter = this.GetUpLevelFilter();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint downLevelFilter = this.GetDownLevelFilter();
		int downLevelFilter = this.GetDownLevelFilter();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint startingScope = this.GetStartingScope();
		int startingScope = this.GetStartingScope();
		if (startingScope > 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: dsopScopeInitInfoArray[0].cbSize = (uint)Marshal.SizeOf(typeof(ObjectPickerEx.NativeMethods.DSOP_SCOPE_INIT_INFO));
			dsopScopeInitInfoArray[0].cbSize = (int)System.Runtime.InteropServices.Marshal.SizeOf(ObjectPickerEx.NativeMethods.DSOP_SCOPE_INIT_INFO.class);
			dsopScopeInitInfoArray[0].flType = startingScope;
			dsopScopeInitInfoArray[0].flScope = 1 | defaultFilter;
			dsopScopeInitInfoArray[0].FilterFlags.Uplevel.flBothModes = upLevelFilter;
			dsopScopeInitInfoArray[0].FilterFlags.flDownlevel = downLevelFilter;
			dsopScopeInitInfoArray[0].pwzADsPath = (String)null;
			dsopScopeInitInfoArray[0].pwzDcName = (String)null;
			dsopScopeInitInfoArray[0].hr = 0;
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint otherScope = this.GetOtherScope();
		int otherScope = this.GetOtherScope();
		if (otherScope > 0)
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: dsopScopeInitInfoArray[1].cbSize = (uint)Marshal.SizeOf(typeof(ObjectPickerEx.NativeMethods.DSOP_SCOPE_INIT_INFO));
			dsopScopeInitInfoArray[1].cbSize = (int)System.Runtime.InteropServices.Marshal.SizeOf(ObjectPickerEx.NativeMethods.DSOP_SCOPE_INIT_INFO.class);
			dsopScopeInitInfoArray[1].flType = otherScope;
			dsopScopeInitInfoArray[1].flScope = defaultFilter;
			dsopScopeInitInfoArray[1].FilterFlags.Uplevel.flBothModes = upLevelFilter;
			dsopScopeInitInfoArray[1].FilterFlags.flDownlevel = downLevelFilter;
			dsopScopeInitInfoArray[1].pwzADsPath = (String)null;
			dsopScopeInitInfoArray[1].pwzDcName = (String)null;
			dsopScopeInitInfoArray[1].hr = 0;
		}
		IntPtr num1 = Marshal.AllocHGlobal(System.Runtime.InteropServices.Marshal.SizeOf(ObjectPickerEx.NativeMethods.DSOP_SCOPE_INIT_INFO.class) * dsopScopeInitInfoArray.length);
		for (int index = 0; index < dsopScopeInitInfoArray.length; ++index)
		{
			Marshal.StructureToPtr((Object)dsopScopeInitInfoArray[index], (IntPtr)((long)num1 + (long)(index * System.Runtime.InteropServices.Marshal.SizeOf(ObjectPickerEx.NativeMethods.DSOP_SCOPE_INIT_INFO.class))), false);
		}
		ObjectPickerEx.NativeMethods.DSOP_INIT_INFO pInitInfo = new ObjectPickerEx.NativeMethods.DSOP_INIT_INFO();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: pInitInfo.cbSize = (uint)Marshal.SizeOf((object)pInitInfo);
		pInitInfo.cbSize = (int)System.Runtime.InteropServices.Marshal.SizeOf((Object)pInitInfo);
		pInitInfo.pwzTargetComputer = this.getTargetComputer();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: pInitInfo.cDsScopeInfos = (uint)dsopScopeInitInfoArray.Length;
		pInitInfo.cDsScopeInfos = (int)dsopScopeInitInfoArray.length;
		pInitInfo.aDsScopeInfos = num1;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint num2 = 0;
		int num2 = 0;
		if (this.getMultiSelect())
		{
			num2 |= 1;
		}
		pInitInfo.flOptions = num2;
		pInitInfo.cAttributesToFetch = this.length;
		if (this.length > 0)
		{
			MarshalStrings marshalStrings = new MarshalStrings(this.attributeNames);
			pInitInfo.apwzAttributeNames = marshalStrings.getArrayPtr();
		}
		else
		{
			pInitInfo.cAttributesToFetch = 0;
			pInitInfo.apwzAttributeNames = IntPtr.Zero;
		}
		tangible.RefObject<Rasad.Core.ActiveDirectory.ADPicker.ObjectPickerEx.NativeMethods.DSOP_INIT_INFO> tempRef_pInitInfo = new tangible.RefObject<Rasad.Core.ActiveDirectory.ADPicker.ObjectPickerEx.NativeMethods.DSOP_INIT_INFO>(pInitInfo);
		if (dsObjectPicker.Initialize(tempRef_pInitInfo) != 0)
		{
		pInitInfo = tempRef_pInitInfo.argValue;
			return (ObjectPickerEx.NativeMethods.IDsObjectPicker)null;
		}
	else
	{
		pInitInfo = tempRef_pInitInfo.argValue;
	}
		return dsObjectPicker;
	}

	private DirectoryObject[] ProcessSelections(IntPtr dataObj)
	{
		if (System.IntPtr.OpEquality(dataObj, IntPtr.Zero))
		{
			throw new NullPointerException("dataObj");
		}
		DirectoryObject[] directoryObjectArray = (DirectoryObject[])null;
		ObjectPickerEx.NativeMethods.STGMEDIUM b = new ObjectPickerEx.NativeMethods.STGMEDIUM();
		b.tymed = 1;
		b.hGlobal = IntPtr.Zero;
		b.pUnkForRelease = IntPtr.Zero;
		ObjectPickerEx.NativeMethods.FORMATETC temp1 = new NativeMethods.FORMATETC();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: temp1.cfFormat = (uint)DataFormats.GetFormat("CFSTR_DSOP_DS_SELECTION_LIST").Id;
		temp1.cfFormat = (int)DataFormats.GetFormat("CFSTR_DSOP_DS_SELECTION_LIST").Id;
		temp1.ptd = IntPtr.Zero;
		temp1.dwAspect = 1;
		temp1.lindex = -1;
		temp1.tymed = 1;
		tangible.RefObject<Rasad.Core.ActiveDirectory.ADPicker.ObjectPickerEx.NativeMethods.FORMATETC> tempRef_temp1 = new tangible.RefObject<Rasad.Core.ActiveDirectory.ADPicker.ObjectPickerEx.NativeMethods.FORMATETC>(temp1);
		tangible.RefObject<Rasad.Core.ActiveDirectory.ADPicker.ObjectPickerEx.NativeMethods.STGMEDIUM> tempRef_b = new tangible.RefObject<Rasad.Core.ActiveDirectory.ADPicker.ObjectPickerEx.NativeMethods.STGMEDIUM>(b);
		if (((ObjectPickerEx.NativeMethods.IDataObject)Marshal.GetTypedObjectForIUnknown(dataObj, ObjectPickerEx.NativeMethods.IDataObject.class)).GetData(tempRef_temp1, tempRef_b) == 0)
		{
		b = tempRef_b.argValue;
		temp1 = tempRef_temp1.argValue;
			IntPtr ptr1 = ObjectPickerEx.NativeMethods.GlobalLock(b.hGlobal);
			try
			{
				ObjectPickerEx.NativeMethods.DS_SELECTION_LIST structure = (ObjectPickerEx.NativeMethods.DS_SELECTION_LIST)Marshal.PtrToStructure(ptr1, ObjectPickerEx.NativeMethods.DS_SELECTION_LIST.class);
				int cItems = (int)structure.cItems;
				int fetchedAttributes = (int)structure.cFetchedAttributes;
				directoryObjectArray = new DirectoryObject[cItems];
				if (cItems > 0)
				{
					ObjectPickerEx.NativeMethods.DS_SELECTION[] dsSelectionArray = new ObjectPickerEx.NativeMethods.DS_SELECTION[cItems];
					ObjectPickerEx.NativeMethods.DS_SELECTION_ATTRIBUTES[] selectionAttributesArray = new ObjectPickerEx.NativeMethods.DS_SELECTION_ATTRIBUTES[cItems];
					IntPtr ptr2 = (IntPtr)((long)ptr1 + (long)System.Runtime.InteropServices.Marshal.SizeOf((Object)structure));
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] numArray = (byte[])null;
					byte[] numArray = (byte[])null;
					for (int index = 0; index < cItems; ++index)
					{
						dsSelectionArray[index] = new ObjectPickerEx.NativeMethods.DS_SELECTION();
						dsSelectionArray[index] = (ObjectPickerEx.NativeMethods.DS_SELECTION)Marshal.PtrToStructure(ptr2, ObjectPickerEx.NativeMethods.DS_SELECTION.class);
						if (this.length > 0 && fetchedAttributes > 0)
						{
							selectionAttributesArray[index] = new ObjectPickerEx.NativeMethods.DS_SELECTION_ATTRIBUTES();
							selectionAttributesArray[index].attributeValues = Marshal.GetObjectsForNativeVariants(dsSelectionArray[index].pvarFetchedAttributes, fetchedAttributes);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: numArray = selectionAttributesArray[index].attributeValues[0] instanceof byte[] ? (byte[])selectionAttributesArray[index].attributeValues[0] : null;
							numArray = selectionAttributesArray[index].attributeValues[0] instanceof byte[] ? (byte[])selectionAttributesArray[index].attributeValues[0] : null;
						}
						String pwzClass = dsSelectionArray[index].pwzClass;
						String pwzName = dsSelectionArray[index].pwzName;
						String pwzUpn = dsSelectionArray[index].pwzUPN;
						String pwzAdsPath = dsSelectionArray[index].pwzADsPath;
						directoryObjectArray[index] = new DirectoryObject(pwzClass, pwzName, pwzUpn, pwzAdsPath);
						directoryObjectArray[index].setBinarySid(numArray);
						ptr2 = (IntPtr)((long)ptr2 + (long)System.Runtime.InteropServices.Marshal.SizeOf((Object)dsSelectionArray[index]));
					}
				}
			}
			finally
			{
				ObjectPickerEx.NativeMethods.GlobalUnlock(b.hGlobal);
			}
		}
	else
	{
		b = tempRef_b.argValue;
		temp1 = tempRef_temp1.argValue;
	}
		return directoryObjectArray;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint GetDefaultFilter()
	private int GetDefaultFilter()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint num = 0;
		int num = 0;
		if ((this.getDefaultTypes().getValue() & DirectoryObjectTypes.Users.getValue()) == DirectoryObjectTypes.Users.getValue() || (this.getDefaultTypes().getValue() & DirectoryObjectTypes.WellKnownPrincipals.getValue()) == DirectoryObjectTypes.WellKnownPrincipals.getValue())
		{
			num |= 64;
		}
		if ((this.getDefaultTypes().getValue() & DirectoryObjectTypes.Groups.getValue()) == DirectoryObjectTypes.Groups.getValue() || (this.getDefaultTypes().getValue() & DirectoryObjectTypes.BuiltInGroups.getValue()) == DirectoryObjectTypes.BuiltInGroups.getValue())
		{
			num |= 128;
		}
		if ((this.getDefaultTypes().getValue() & DirectoryObjectTypes.Computers.getValue()) == DirectoryObjectTypes.Computers.getValue())
		{
			num |= 256;
		}
		if ((this.getDefaultTypes().getValue() & DirectoryObjectTypes.Contacts.getValue()) == DirectoryObjectTypes.Contacts.getValue())
		{
			num |= 512;
		}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (uint)((ADsPathType)num | this.PathType);
		return (int)(((ADsPathType.forValue(num)).getValue()).getValue() | this.getPathType().getValue());
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint GetUpLevelFilter()
	private int GetUpLevelFilter()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint num = 0;
		int num = 0;
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.Users.getValue()) == DirectoryObjectTypes.Users.getValue())
		{
			num |= 2;
		}
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.Groups.getValue()) == DirectoryObjectTypes.Groups.getValue())
		{
			num |= 1008;
		}
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.Computers.getValue()) == DirectoryObjectTypes.Computers.getValue())
		{
			num |= 2048;
		}
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.Contacts.getValue()) == DirectoryObjectTypes.Contacts.getValue())
		{
			num |= 1024;
		}
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.BuiltInGroups.getValue()) == DirectoryObjectTypes.BuiltInGroups.getValue())
		{
			num |= 4;
		}
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.WellKnownPrincipals.getValue()) == DirectoryObjectTypes.WellKnownPrincipals.getValue())
		{
			num |= 8;
		}
		return num;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint GetDownLevelFilter()
	private int GetDownLevelFilter()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint num = 0;
		int num = 0;
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.Users.getValue()) == DirectoryObjectTypes.Users.getValue())
		{
			num |= 2147483649;
		}
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.Groups.getValue()) == DirectoryObjectTypes.Groups.getValue())
		{
			num |= 2147483654;
		}
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.Computers.getValue()) == DirectoryObjectTypes.Computers.getValue())
		{
			num |= 2147483656;
		}
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.BuiltInGroups.getValue()) == DirectoryObjectTypes.None.getValue())
		{
			num |= 2147516416;
		}
		if ((this.getAllowedTypes().getValue() & DirectoryObjectTypes.WellKnownPrincipals.getValue()) == DirectoryObjectTypes.WellKnownPrincipals.getValue())
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: num = (uint)((BuiltInPrincipals)num | this.AllowedBuiltInPricipals);
			num = (int)((BuiltInPrincipals.forValue(num)).getValue().getValue() | this.getAllowedBuiltInPricipals().getValue());
		}
		return num;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint GetStartingScope()
	private int GetStartingScope()
	{
		return ObjectPickerEx.GetScope(this.getDefaultLocations());
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private uint GetOtherScope()
	private int GetOtherScope()
	{
		return ObjectPickerEx.GetScope(this.getAllowedLocations().getValue() & ~this.getDefaultLocations().getValue());
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static uint GetScope(DirectoryLocations locations)
	public static int GetScope(DirectoryLocations locations)
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: uint num = 0;
		int num = 0;
		if ((locations.getValue() & DirectoryLocations.LocalComputer.getValue()) == DirectoryLocations.LocalComputer.getValue())
		{
			num |= 1;
		}
		if ((locations.getValue() & DirectoryLocations.JoinedDomain.getValue()) == DirectoryLocations.JoinedDomain.getValue())
		{
			num |= 6;
		}
		if ((locations.getValue() & DirectoryLocations.EnterpriseDomain.getValue()) == DirectoryLocations.EnterpriseDomain.getValue())
		{
			num |= 8;
		}
		if ((locations.getValue() & DirectoryLocations.GlobalCatalog.getValue()) == DirectoryLocations.GlobalCatalog.getValue())
		{
			num |= 16;
		}
		if ((locations.getValue() & DirectoryLocations.ExternalDomain.getValue()) == DirectoryLocations.ExternalDomain.getValue())
		{
			num |= 96;
		}
		if ((locations.getValue() & DirectoryLocations.Workgroup.getValue()) == DirectoryLocations.Workgroup.getValue())
		{
			num |= 128;
		}
		if ((locations.getValue() & DirectoryLocations.UserEntered.getValue()) == DirectoryLocations.UserEntered.getValue())
		{
			num |= 768;
		}
		return num;
	}

	public static DirectoryObject[] ShowUserObjectPicker(IWin32Window owner)
	{
		try (ObjectPickerEx objectPickerEx = new ObjectPickerEx())
		{
			objectPickerEx.setAllowedTypes(DirectoryObjectTypes.Users);
			objectPickerEx.setAllowedLocations(Rasad.Core.ActiveDirectory.ADPicker.DirectoryLocations.forValue(DirectoryLocations.LocalComputer.getValue() | DirectoryLocations.JoinedDomain.getValue() | DirectoryLocations.EnterpriseDomain.getValue() | DirectoryLocations.GlobalCatalog.getValue() | DirectoryLocations.ExternalDomain.getValue() | DirectoryLocations.UserEntered.getValue()));
			objectPickerEx.setDefaultTypes(DirectoryObjectTypes.Users);
			objectPickerEx.setDefaultLocations(DirectoryLocations.JoinedDomain);
			objectPickerEx.setTargetComputer((String)null);
			objectPickerEx.setAllowedBuiltInPricipals(BuiltInPrincipals.Users);
			objectPickerEx.setMultiSelect(true);
			objectPickerEx.setPathType(ADsPathType.NT4);
			try
			{
				if (objectPickerEx.ShowDialog(owner) == JOptionPane.OK_OPTION && objectPickerEx.getSelectedObjects() != null)
				{
					if (objectPickerEx.getSelectedObjects().length != 0)
					{
//C# TO JAVA CONVERTER TODO TASK: There is no 'goto' in Java:
						goto label_6;
					}
				}
				return (DirectoryObject[])null;
			}
			catch (COMException ex)
			{
				throw ex;
			}
		label_6:
			return objectPickerEx.getSelectedObjects();
		}
	}

	public static class NativeMethods
	{
		public static native IntPtr GlobalLock(IntPtr hMem);
		static
		{
			System.loadLibrary("kernel32.dll");
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DllImport("kernel32.dll")][return: MarshalAs(UnmanagedType.Bool)] public static extern bool GlobalUnlock(IntPtr hMem);
		public static native boolean GlobalUnlock(IntPtr hMem);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Guid("17D6CCD8-3B7B-11D2-B9E0-00C04FD8DBF7")][ComImport] public class DSObjectPicker
		public static class DSObjectPicker
		{
			// #### MY (commented)  -> a class with the comimport attribute cannot have a user-defined constructor
			//[MethodImpl(MethodImplOptions.InternalCall, MethodCodeType = MethodCodeType.Runtime)]
			//public extern DSObjectPicker();
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Guid("0C87E64E-3B7A-11D2-B9E0-00C04FD8DBF7")][InterfaceType(ComInterfaceType.InterfaceIsIUnknown)][ComImport] public interface IDsObjectPicker
		public interface IDsObjectPicker
		{
			int Initialize(tangible.RefObject<ObjectPickerEx.NativeMethods.DSOP_INIT_INFO> pInitInfo);

			int InvokeDialog(IntPtr HWND, tangible.RefObject<IntPtr> lpDataObject);
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Guid("0000010e-0000-0000-C000-000000000046")][InterfaceType(ComInterfaceType.InterfaceIsIUnknown)][ComImport] public interface IDataObject
		public interface IDataObject
		{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.PreserveSig)] int GetData(ref ObjectPickerEx.NativeMethods.FORMATETC pFormatEtc, ref ObjectPickerEx.NativeMethods.STGMEDIUM b);
			int GetData(tangible.RefObject<ObjectPickerEx.NativeMethods.FORMATETC> pFormatEtc, tangible.RefObject<ObjectPickerEx.NativeMethods.STGMEDIUM> b);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.PreserveSig)] void GetDataHere(ref ObjectPickerEx.NativeMethods.FORMATETC pFormatEtc, ref ObjectPickerEx.NativeMethods.STGMEDIUM b);
			void GetDataHere(tangible.RefObject<ObjectPickerEx.NativeMethods.FORMATETC> pFormatEtc, tangible.RefObject<ObjectPickerEx.NativeMethods.STGMEDIUM> b);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.PreserveSig)] int QueryGetData(IntPtr a);
			int QueryGetData(IntPtr a);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.PreserveSig)] int GetCanonicalFormatEtc(IntPtr a, IntPtr b);
			int GetCanonicalFormatEtc(IntPtr a, IntPtr b);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.PreserveSig)] int SetData(IntPtr a, IntPtr b, int c);
			int SetData(IntPtr a, IntPtr b, int c);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.PreserveSig)] int EnumFormatEtc(uint a, IntPtr b);
			int EnumFormatEtc(int a, IntPtr b);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.PreserveSig)] int DAdvise(IntPtr a, uint b, IntPtr c, ref uint d);
			int DAdvise(IntPtr a, int b, IntPtr c, tangible.RefObject<Integer> d);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.PreserveSig)] int DUnadvise(uint a);
			int DUnadvise(int a);

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.PreserveSig)] int EnumDAdvise(IntPtr a);
			int EnumDAdvise(IntPtr a);
		}

//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct FORMATETC
		public final static class FORMATETC
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint cfFormat;
			public int cfFormat;
			public IntPtr ptd = System.IntPtr.Zero;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint dwAspect;
			public int dwAspect;
			public int lindex;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint tymed;
			public int tymed;

			public FORMATETC clone()
			{
				FORMATETC varCopy = new FORMATETC();

				varCopy.cfFormat = this.cfFormat;
				varCopy.ptd = this.ptd;
				varCopy.dwAspect = this.dwAspect;
				varCopy.lindex = this.lindex;
				varCopy.tymed = this.tymed;

				return varCopy;
			}
		}

//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: public struct STGMEDIUM
		public final static class STGMEDIUM
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint tymed;
			public int tymed;
			public IntPtr hGlobal = System.IntPtr.Zero;
			public IntPtr pUnkForRelease = System.IntPtr.Zero;

			public STGMEDIUM clone()
			{
				STGMEDIUM varCopy = new STGMEDIUM();

				varCopy.tymed = this.tymed;
				varCopy.hGlobal = this.hGlobal;
				varCopy.pUnkForRelease = this.pUnkForRelease;

				return varCopy;
			}
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)] public struct DSOP_INIT_INFO
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)] public struct DSOP_INIT_INFO
		public final static class DSOP_INIT_INFO
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint cbSize;
			public int cbSize;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.LPWStr)] public string pwzTargetComputer;
			public String pwzTargetComputer;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint cDsScopeInfos;
			public int cDsScopeInfos;
			public IntPtr aDsScopeInfos = System.IntPtr.Zero;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint flOptions;
			public int flOptions;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint cAttributesToFetch;
			public int cAttributesToFetch;
			public IntPtr apwzAttributeNames = System.IntPtr.Zero;

			public DSOP_INIT_INFO clone()
			{
				DSOP_INIT_INFO varCopy = new DSOP_INIT_INFO();

				varCopy.cbSize = this.cbSize;
				varCopy.pwzTargetComputer = this.pwzTargetComputer;
				varCopy.cDsScopeInfos = this.cDsScopeInfos;
				varCopy.aDsScopeInfos = this.aDsScopeInfos;
				varCopy.flOptions = this.flOptions;
				varCopy.cAttributesToFetch = this.cAttributesToFetch;
				varCopy.apwzAttributeNames = this.apwzAttributeNames;

				return varCopy;
			}
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [Serializable][StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)] public struct DSOP_SCOPE_INIT_INFO
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [Serializable][StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)] public struct DSOP_SCOPE_INIT_INFO
		public final static class DSOP_SCOPE_INIT_INFO implements Serializable
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint cbSize;
			public int cbSize;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint flType;
			public int flType;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint flScope;
			public int flScope;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.Struct)] public ObjectPickerEx.NativeMethods.DSOP_FILTER_FLAGS FilterFlags;
			public ObjectPickerEx.NativeMethods.DSOP_FILTER_FLAGS FilterFlags = new ObjectPickerEx.NativeMethods.DSOP_FILTER_FLAGS();
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.LPWStr)] public string pwzDcName;
			public String pwzDcName;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.LPWStr)] public string pwzADsPath;
			public String pwzADsPath;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint hr;
			public int hr;

			public DSOP_SCOPE_INIT_INFO clone()
			{
				DSOP_SCOPE_INIT_INFO varCopy = new DSOP_SCOPE_INIT_INFO();

				varCopy.cbSize = this.cbSize;
				varCopy.flType = this.flType;
				varCopy.flScope = this.flScope;
				varCopy.FilterFlags = this.FilterFlags.clone();
				varCopy.pwzDcName = this.pwzDcName;
				varCopy.pwzADsPath = this.pwzADsPath;
				varCopy.hr = this.hr;

				return varCopy;
			}
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)] public struct DSOP_UPLEVEL_FILTER_FLAGS
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)] public struct DSOP_UPLEVEL_FILTER_FLAGS
		public final static class DSOP_UPLEVEL_FILTER_FLAGS
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint flBothModes;
			public int flBothModes;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint flMixedModeOnly;
			public int flMixedModeOnly;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint flNativeModeOnly;
			public int flNativeModeOnly;

			public DSOP_UPLEVEL_FILTER_FLAGS clone()
			{
				DSOP_UPLEVEL_FILTER_FLAGS varCopy = new DSOP_UPLEVEL_FILTER_FLAGS();

				varCopy.flBothModes = this.flBothModes;
				varCopy.flMixedModeOnly = this.flMixedModeOnly;
				varCopy.flNativeModeOnly = this.flNativeModeOnly;

				return varCopy;
			}
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)] public struct DSOP_FILTER_FLAGS
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Auto)] public struct DSOP_FILTER_FLAGS
		public final static class DSOP_FILTER_FLAGS
		{
			public ObjectPickerEx.NativeMethods.DSOP_UPLEVEL_FILTER_FLAGS Uplevel = new ObjectPickerEx.NativeMethods.DSOP_UPLEVEL_FILTER_FLAGS();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint flDownlevel;
			public int flDownlevel;

			public DSOP_FILTER_FLAGS clone()
			{
				DSOP_FILTER_FLAGS varCopy = new DSOP_FILTER_FLAGS();

				varCopy.Uplevel = this.Uplevel.clone();
				varCopy.flDownlevel = this.flDownlevel;

				return varCopy;
			}
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)] public struct DS_SELECTION
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)] public struct DS_SELECTION
		public final static class DS_SELECTION
		{
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.LPWStr)] public string pwzName;
			public String pwzName;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.LPWStr)] public string pwzADsPath;
			public String pwzADsPath;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.LPWStr)] public string pwzClass;
			public String pwzClass;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MarshalAs(UnmanagedType.LPWStr)] public string pwzUPN;
			public String pwzUPN;
			public IntPtr pvarFetchedAttributes = System.IntPtr.Zero;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint flScopeType;
			public int flScopeType;

			public DS_SELECTION clone()
			{
				DS_SELECTION varCopy = new DS_SELECTION();

				varCopy.pwzName = this.pwzName;
				varCopy.pwzADsPath = this.pwzADsPath;
				varCopy.pwzClass = this.pwzClass;
				varCopy.pwzUPN = this.pwzUPN;
				varCopy.pvarFetchedAttributes = this.pvarFetchedAttributes;
				varCopy.flScopeType = this.flScopeType;

				return varCopy;
			}
		}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)] public struct DS_SELECTION_LIST
//C# TO JAVA CONVERTER WARNING: Java does not allow user-defined value types. The behavior of this class will differ from the original:
//ORIGINAL LINE: [StructLayout(LayoutKind.Sequential, CharSet = CharSet.Unicode)] public struct DS_SELECTION_LIST
		public final static class DS_SELECTION_LIST
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint cItems;
			public int cItems;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public uint cFetchedAttributes;
			public int cFetchedAttributes;

			public DS_SELECTION_LIST clone()
			{
				DS_SELECTION_LIST varCopy = new DS_SELECTION_LIST();

				varCopy.cItems = this.cItems;
				varCopy.cFetchedAttributes = this.cFetchedAttributes;

				return varCopy;
			}
		}

		public static class DS_SELECTION_ATTRIBUTES
		{
			public Object[] attributeValues;
		}

		public static class DSOP_SCOPE_TYPE_FLAGS
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_TARGET_COMPUTER = 1;
			public static final int DSOP_SCOPE_TYPE_TARGET_COMPUTER = 1;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_UPLEVEL_JOINED_DOMAIN = 2;
			public static final int DSOP_SCOPE_TYPE_UPLEVEL_JOINED_DOMAIN = 2;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_DOWNLEVEL_JOINED_DOMAIN = 4;
			public static final int DSOP_SCOPE_TYPE_DOWNLEVEL_JOINED_DOMAIN = 4;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_ENTERPRISE_DOMAIN = 8;
			public static final int DSOP_SCOPE_TYPE_ENTERPRISE_DOMAIN = 8;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_GLOBAL_CATALOG = 16;
			public static final int DSOP_SCOPE_TYPE_GLOBAL_CATALOG = 16;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_EXTERNAL_UPLEVEL_DOMAIN = 32;
			public static final int DSOP_SCOPE_TYPE_EXTERNAL_UPLEVEL_DOMAIN = 32;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_EXTERNAL_DOWNLEVEL_DOMAIN = 64;
			public static final int DSOP_SCOPE_TYPE_EXTERNAL_DOWNLEVEL_DOMAIN = 64;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_WORKGROUP = 128;
			public static final int DSOP_SCOPE_TYPE_WORKGROUP = 128;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_USER_ENTERED_UPLEVEL_SCOPE = 256;
			public static final int DSOP_SCOPE_TYPE_USER_ENTERED_UPLEVEL_SCOPE = 256;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_TYPE_USER_ENTERED_DOWNLEVEL_SCOPE = 512;
			public static final int DSOP_SCOPE_TYPE_USER_ENTERED_DOWNLEVEL_SCOPE = 512;
		}

		public static class DSOP_INIT_INFO_FLAGS
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FLAG_MULTISELECT = 1;
			public static final int DSOP_FLAG_MULTISELECT = 1;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FLAG_SKIP_TARGET_COMPUTER_DC_CHECK = 2;
			public static final int DSOP_FLAG_SKIP_TARGET_COMPUTER_DC_CHECK = 2;
		}

		public static class DSOP_SCOPE_INIT_INFO_FLAGS
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_STARTING_SCOPE = 1;
			public static final int DSOP_SCOPE_FLAG_STARTING_SCOPE = 1;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_WANT_PROVIDER_WINNT = 2;
			public static final int DSOP_SCOPE_FLAG_WANT_PROVIDER_WINNT = 2;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_WANT_PROVIDER_LDAP = 4;
			public static final int DSOP_SCOPE_FLAG_WANT_PROVIDER_LDAP = 4;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_WANT_PROVIDER_GC = 8;
			public static final int DSOP_SCOPE_FLAG_WANT_PROVIDER_GC = 8;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_WANT_SID_PATH = 16;
			public static final int DSOP_SCOPE_FLAG_WANT_SID_PATH = 16;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_WANT_DOWNLEVEL_BUILTIN_PATH = 32;
			public static final int DSOP_SCOPE_FLAG_WANT_DOWNLEVEL_BUILTIN_PATH = 32;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_DEFAULT_FILTER_USERS = 64;
			public static final int DSOP_SCOPE_FLAG_DEFAULT_FILTER_USERS = 64;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_DEFAULT_FILTER_GROUPS = 128;
			public static final int DSOP_SCOPE_FLAG_DEFAULT_FILTER_GROUPS = 128;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_DEFAULT_FILTER_COMPUTERS = 256;
			public static final int DSOP_SCOPE_FLAG_DEFAULT_FILTER_COMPUTERS = 256;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_SCOPE_FLAG_DEFAULT_FILTER_CONTACTS = 512;
			public static final int DSOP_SCOPE_FLAG_DEFAULT_FILTER_CONTACTS = 512;
		}

		public static class DSOP_FILTER_FLAGS_FLAGS
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_INCLUDE_ADVANCED_VIEW = 1;
			public static final int DSOP_FILTER_INCLUDE_ADVANCED_VIEW = 1;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_USERS = 2;
			public static final int DSOP_FILTER_USERS = 2;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_BUILTIN_GROUPS = 4;
			public static final int DSOP_FILTER_BUILTIN_GROUPS = 4;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_WELL_KNOWN_PRINCIPALS = 8;
			public static final int DSOP_FILTER_WELL_KNOWN_PRINCIPALS = 8;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_UNIVERSAL_GROUPS_DL = 16;
			public static final int DSOP_FILTER_UNIVERSAL_GROUPS_DL = 16;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_UNIVERSAL_GROUPS_SE = 32;
			public static final int DSOP_FILTER_UNIVERSAL_GROUPS_SE = 32;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_GLOBAL_GROUPS_DL = 64;
			public static final int DSOP_FILTER_GLOBAL_GROUPS_DL = 64;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_GLOBAL_GROUPS_SE = 128;
			public static final int DSOP_FILTER_GLOBAL_GROUPS_SE = 128;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_DOMAIN_LOCAL_GROUPS_DL = 256;
			public static final int DSOP_FILTER_DOMAIN_LOCAL_GROUPS_DL = 256;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_DOMAIN_LOCAL_GROUPS_SE = 512;
			public static final int DSOP_FILTER_DOMAIN_LOCAL_GROUPS_SE = 512;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_CONTACTS = 1024;
			public static final int DSOP_FILTER_CONTACTS = 1024;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_FILTER_COMPUTERS = 2048;
			public static final int DSOP_FILTER_COMPUTERS = 2048;
		}

		public static class DSOP_DOWNLEVEL_FLAGS
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_USERS = 2147483649;
			public static final int DSOP_DOWNLEVEL_FILTER_USERS = (int)2147483649;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_LOCAL_GROUPS = 2147483650;
			public static final int DSOP_DOWNLEVEL_FILTER_LOCAL_GROUPS = (int)2147483650;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_GLOBAL_GROUPS = 2147483652;
			public static final int DSOP_DOWNLEVEL_FILTER_GLOBAL_GROUPS = (int)2147483652;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_COMPUTERS = 2147483656;
			public static final int DSOP_DOWNLEVEL_FILTER_COMPUTERS = (int)2147483656;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_WORLD = 2147483664;
			public static final int DSOP_DOWNLEVEL_FILTER_WORLD = (int)2147483664;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_AUTHENTICATED_USER = 2147483680;
			public static final int DSOP_DOWNLEVEL_FILTER_AUTHENTICATED_USER = (int)2147483680;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_ANONYMOUS = 2147483712;
			public static final int DSOP_DOWNLEVEL_FILTER_ANONYMOUS = (int)2147483712;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_BATCH = 2147483776;
			public static final int DSOP_DOWNLEVEL_FILTER_BATCH = (int)2147483776;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_CREATOR_OWNER = 2147483904;
			public static final int DSOP_DOWNLEVEL_FILTER_CREATOR_OWNER = (int)2147483904;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_CREATOR_GROUP = 2147484160;
			public static final int DSOP_DOWNLEVEL_FILTER_CREATOR_GROUP = (int)2147484160;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_DIALUP = 2147484672;
			public static final int DSOP_DOWNLEVEL_FILTER_DIALUP = (int)2147484672;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_INTERACTIVE = 2147485696;
			public static final int DSOP_DOWNLEVEL_FILTER_INTERACTIVE = (int)2147485696;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_NETWORK = 2147487744;
			public static final int DSOP_DOWNLEVEL_FILTER_NETWORK = (int)2147487744;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_SERVICE = 2147491840;
			public static final int DSOP_DOWNLEVEL_FILTER_SERVICE = (int)2147491840;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_SYSTEM = 2147500032;
			public static final int DSOP_DOWNLEVEL_FILTER_SYSTEM = (int)2147500032;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_EXCLUDE_BUILTIN_GROUPS = 2147516416;
			public static final int DSOP_DOWNLEVEL_FILTER_EXCLUDE_BUILTIN_GROUPS = (int)2147516416;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_TERMINAL_SERVER = 2147549184;
			public static final int DSOP_DOWNLEVEL_FILTER_TERMINAL_SERVER = (int)2147549184;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_ALL_WELLKNOWN_SIDS = 2147614720;
			public static final int DSOP_DOWNLEVEL_FILTER_ALL_WELLKNOWN_SIDS = (int)2147614720;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_LOCAL_SERVICE = 2147745792;
			public static final int DSOP_DOWNLEVEL_FILTER_LOCAL_SERVICE = (int)2147745792;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_NETWORK_SERVICE = 2148007936;
			public static final int DSOP_DOWNLEVEL_FILTER_NETWORK_SERVICE = (int)2148007936;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public const uint DSOP_DOWNLEVEL_FILTER_REMOTE_LOGON = 2148532224;
			public static final int DSOP_DOWNLEVEL_FILTER_REMOTE_LOGON = (int)2148532224;
		}

		public static class HRESULT
		{
			public static final int S_OK = 0;
			public static final int S_FALSE = 1;
			public static final int E_NOTIMPL = -2147467263;
		}

		public static class CLIPBOARD_FORMAT
		{
			public static final String CFSTR_DSOP_DS_SELECTION_LIST = "CFSTR_DSOP_DS_SELECTION_LIST";
		}

		public enum TYMED
		{
			TYMED_NULL(0),
			TYMED_HGLOBAL(1),
			TYMED_FILE(2),
			TYMED_ISTREAM(4),
			TYMED_ISTORAGE(8),
			TYMED_GDI(16), // 0x00000010
			TYMED_MFPICT(32), // 0x00000020
			TYMED_ENHMF(64); // 0x00000040

			public static final int SIZE = java.lang.Integer.SIZE;

			private int intValue;
			private static java.util.HashMap<Integer, TYMED> mappings;
			private static java.util.HashMap<Integer, TYMED> getMappings()
			{
				if (mappings == null)
				{
					synchronized (TYMED.class)
					{
						if (mappings == null)
						{
							mappings = new java.util.HashMap<Integer, TYMED>();
						}
					}
				}
				return mappings;
			}

			private TYMED(int value)
			{
				intValue = value;
				getMappings().put(value, this);
			}

			public int getValue()
			{
				return intValue;
			}

			public static TYMED forValue(int value)
			{
				return getMappings().get(value);
			}
		}

		public enum DVASPECT
		{
			DVASPECT_CONTENT(1),
			DVASPECT_THUMBNAIL(2),
			DVASPECT_ICON(4),
			DVASPECT_DOCPRINT(8);

			public static final int SIZE = java.lang.Integer.SIZE;

			private int intValue;
			private static java.util.HashMap<Integer, DVASPECT> mappings;
			private static java.util.HashMap<Integer, DVASPECT> getMappings()
			{
				if (mappings == null)
				{
					synchronized (DVASPECT.class)
					{
						if (mappings == null)
						{
							mappings = new java.util.HashMap<Integer, DVASPECT>();
						}
					}
				}
				return mappings;
			}

			private DVASPECT(int value)
			{
				intValue = value;
				getMappings().put(value, this);
			}

			public int getValue()
			{
				return intValue;
			}

			public static DVASPECT forValue(int value)
			{
				return getMappings().get(value);
			}
		}
	}
}