package main.java.Rasad.Core;

public final class TStructSerializationHelper
{
	// source: http://codereview.stackexchange.com/questions/97120/fastest-de-serialise-struct-in-net

	private static final java.lang.reflect.Constructor IntPtrCtor = IntPtr.class.GetConstructor(new java.lang.Class[] {void * .class});
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: static readonly MethodInfo MarshalCopy = typeof(Marshal).GetMethod("Copy", new[] { typeof(IntPtr), typeof(byte[]), typeof(int), typeof(int) });
	private static final java.lang.reflect.Method MarshalCopy = Marshal.class.GetMethod("Copy", new java.lang.Class[] {IntPtr.class, byte[].class, Integer.class, Integer.class});
//C# TO JAVA CONVERTER TODO TASK: The C# 'struct' constraint has no equivalent in Java:
//ORIGINAL LINE: private static class DelegateHolder<T> where T : struct
	private static class DelegateHolder<T>
	{
		// ReSharper disable MemberHidesStaticFromOuterClass
		// ReSharper disable StaticMemberInGenericType
		public static final java.lang.Class TypeOfT = T.class;
		public static final int SizeInBytes = System.Runtime.InteropServices.Marshal.SizeOf(TypeOfT);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static readonly Func<T, byte[]> Serialize = CreateSerializationDelegate();
		public static final tangible.Func1Param<T, byte[]> Serialize = CreateSerializationDelegate();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static readonly Func<byte[], T> Deserialize = CreateDeserializationDelegate();
		public static final tangible.Func1Param<byte[], T> Deserialize = CreateDeserializationDelegate();


		//public static byte[] Serialize(T value)
		//{
		//    IntPtr p = new IntPtr(&value);
		//    byte[] result = new byte[sizeof(T)];
		//    Marshal.Copy(p, result, 0, result.Length);
		//    return result;
		//}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static Func<T, byte[]> CreateSerializationDelegate()
		private static tangible.Func1Param<T, byte[]> CreateSerializationDelegate()
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var dm = new DynamicMethod("Serialize" + TypeOfT.Name, typeof(byte[]), new[] { TypeOfT }, Assembly.GetExecutingAssembly().ManifestModule);
			DynamicMethod dm = new DynamicMethod("Serialize" + TypeOfT.getSimpleName(), byte[].class, new java.lang.Class[] {TypeOfT}, Assembly.GetExecutingAssembly().ManifestModule);
			dm.DefineParameter(1, ParameterAttributes.None, "value");

			System.Reflection.Emit.ILGenerator generator = dm.GetILGenerator();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: generator.DeclareLocal(typeof(byte[]));
			generator.DeclareLocal(byte[].class);

			//IntPtr p = new IntPtr(&value);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: generator.Emit(OpCodes.Ldarga_S, (byte)0);
			generator.Emit(OpCodes.Ldarga_S, (byte)0);
			generator.Emit(OpCodes.Conv_U);
			generator.Emit(OpCodes.Newobj, IntPtrCtor);

			//byte[] result = new byte[sizeof(T)]; 
			OpCode ldcStructSize = SizeInBytes < Byte.MAX_VALUE ? OpCodes.Ldc_I4_S : OpCodes.Ldc_I4;
			generator.Emit(ldcStructSize, SizeInBytes);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: generator.Emit(OpCodes.Newarr, typeof(byte));
			generator.Emit(OpCodes.Newarr, Byte.class);

			//Marshal.Copy(p, result, 0, result.Length);
			generator.Emit(OpCodes.Stloc_0);
			generator.Emit(OpCodes.Ldloc_0);
			generator.Emit(OpCodes.Ldc_I4_0);
			generator.Emit(OpCodes.Ldloc_0);
			generator.Emit(OpCodes.Ldlen);
			generator.Emit(OpCodes.Conv_I4);
			generator.EmitCall(OpCodes.Call, MarshalCopy, null);

			//return result
			generator.Emit(OpCodes.Ldloc_0);
			generator.Emit(OpCodes.Ret);

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (Func<T, byte[]>)dm.CreateDelegate(typeof(Func<T, byte[]>));
			return (tangible.Func1Param<T, byte[]>)dm.CreateDelegate(tangible.Func1Param<T, byte[]>.class);
		}

		//public static T Deserialize(byte[] data)
		//{
		//    fixed (byte* pData = &data[0])
		//    {
		//        return *(T*)pData;
		//    }
		//}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private static Func<byte[], T> CreateDeserializationDelegate()
		private static tangible.Func1Param<byte[], T> CreateDeserializationDelegate()
		{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: var dm = new DynamicMethod("Deserialize" + TypeOfT.Name, TypeOfT, new[] { typeof(byte[]) }, Assembly.GetExecutingAssembly().ManifestModule);
			DynamicMethod dm = new DynamicMethod("Deserialize" + TypeOfT.getSimpleName(), TypeOfT, new java.lang.Class[] {byte[].class}, Assembly.GetExecutingAssembly().ManifestModule);
			dm.DefineParameter(1, ParameterAttributes.None, "data");
			System.Reflection.Emit.ILGenerator generator = dm.GetILGenerator();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: generator.DeclareLocal(typeof(byte).MakePointerType(), pinned: true);
			generator.DeclareLocal(Byte.class.MakePointerType(), pinned: true);
			generator.DeclareLocal(TypeOfT);

			//fixed (byte* pData = &data[0])
			generator.Emit(OpCodes.Ldarg_0);
			generator.Emit(OpCodes.Ldc_I4_0);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: generator.Emit(OpCodes.Ldelema, typeof(byte));
			generator.Emit(OpCodes.Ldelema, Byte.class);
			generator.Emit(OpCodes.Stloc_0);

			// return *(T*)pData;
			generator.Emit(OpCodes.Ldloc_0);
			generator.Emit(OpCodes.Conv_I);
			generator.Emit(OpCodes.Ldobj, TypeOfT);
			generator.Emit(OpCodes.Ret);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: return (Func<byte[], T>)dm.CreateDelegate(typeof(Func<byte[], T>));
			return (tangible.Func1Param<byte[], T>)dm.CreateDelegate(tangible.Func1Param<byte[], T>.class);
		}
	}


	/** 
	 Do not check array bounds, possible buffer overflow
	*/
//C# TO JAVA CONVERTER TODO TASK: The C# 'struct' constraint has no equivalent in Java:
//ORIGINAL LINE: public static T Deserialize<T>(byte[] data) where T : struct
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
	public static <T> T Deserialize(byte[] data)
	{
		return DelegateHolder<T>.Deserialize(data);
	}

	/** 
	 Check array bounds
	*/
//C# TO JAVA CONVERTER TODO TASK: The C# 'struct' constraint has no equivalent in Java:
//ORIGINAL LINE: public static T DeserializeSafe<T>(byte[] data) where T : struct
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
	public static <T> T DeserializeSafe(byte[] data)
	{
		if (DelegateHolder<T>.SizeInBytes != data.length)
		{
			throw new IllegalArgumentException(String.format("Struct size is %1$s bytes but array is %2$s bytes length", DelegateHolder<T>.SizeInBytes, data.length));
		}
		return DelegateHolder<T>.Deserialize(data);
	}

	/** 
	 Marshal struct in byte array without any type information
	*/
//C# TO JAVA CONVERTER TODO TASK: The C# 'struct' constraint has no equivalent in Java:
//ORIGINAL LINE: public static byte[] Serialize<T>(this T value) where T : struct
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
	public static <T> byte[] Serialize(T value)
	{
		return DelegateHolder<T>.Serialize(value);
	}
}