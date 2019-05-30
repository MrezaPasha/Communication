package Rasad.Core;

import Newtonsoft.Json.*;
import java.io.*;

public class TSerializationHelper
{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static Byte[] SerializeStruct<T>(T s)
	public static <T> byte[] SerializeStruct(T s) //where T : struct
	{
		if (!T.class.IsValueType)
		{
			throw new IllegalStateException("invalid value type");
		}
		int objsize = System.Runtime.InteropServices.Marshal.SizeOf(T.class);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: Byte[] ret = new Byte[objsize];
		byte[] ret = new byte[objsize];
		IntPtr buff = Marshal.AllocHGlobal(objsize);
		Marshal.StructureToPtr(s, buff, true);
		Marshal.Copy(buff, ret, 0, objsize);
		Marshal.FreeHGlobal(buff);
		return ret;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static T DeserializeStruct<T>(Byte[] data)
	public static <T> T DeserializeStruct(byte[] data) //where T : struct
	{
		if (!T.class.IsValueType)
		{
			throw new IllegalStateException("invalid value type");
		}
		int objsize = System.Runtime.InteropServices.Marshal.SizeOf(T.class);
		IntPtr buff = Marshal.AllocHGlobal(objsize);
		Marshal.Copy(data, 0, buff, objsize);
		T retStruct = (T)Marshal.PtrToStructure(buff, T.class);
		Marshal.FreeHGlobal(buff);
		return retStruct;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] ProtoBufSerialize<T>(T obj)
	public static <T> byte[] ProtoBufSerialize(T obj)
	{
		try (MemoryStream memoryStream = new MemoryStream())
		{
			//var serializer = new DataContractSerializer(obj.GetType(), knowTypes);
			ProtoBuf.Serializer.<T>Serialize(memoryStream, obj);
			return memoryStream.ToArray();
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static T ProtoBufDeserialize<T>(byte[] data)
	public static <T> T ProtoBufDeserialize(byte[] data)
	{
		try (MemoryStream memoryStream = new MemoryStream(data))
		{
			return ProtoBuf.Serializer.<T>Deserialize(memoryStream);
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] DataContractSerializeBinary(object obj, params Type[] knowTypes)
	public static byte[] DataContractSerializeBinary(Object obj, java.lang.Class... knowTypes)
	{
		try (MemoryStream memoryStream = new MemoryStream())
		{
			DataContractSerializer serializer = new DataContractSerializer(obj.getClass(), knowTypes);
			serializer.WriteObject(memoryStream, obj);
			return memoryStream.ToArray();
		}
	}

	public static String DataContractSerialize(Object obj, java.lang.Class... knowTypes)
	{
		return Encoding.UTF8.GetString(DataContractSerializeBinary(obj, knowTypes));
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static object DataContractDeserializeBinary(byte[] objData, Type toType)
	public static Object DataContractDeserializeBinary(byte[] objData, java.lang.Class toType)
	{
		try (MemoryStream memoryStream = new MemoryStream(objData))
		{
			XmlDictionaryReaderQuotas tempVar = new XmlDictionaryReaderQuotas();
			tempVar.MaxArrayLength = Integer.MAX_VALUE;
			tempVar.MaxStringContentLength = Integer.MAX_VALUE;
			XmlDictionaryReader reader = XmlDictionaryReader.CreateTextReader(memoryStream, Encoding.UTF8, tempVar, null);
			DataContractSerializer serializer = new DataContractSerializer(toType);
			return serializer.ReadObject(reader);
		}
	}

	public static Object DataContractDeserialize(String xml, java.lang.Class toType)
	{
		return DataContractDeserializeBinary(xml.getBytes(java.nio.charset.StandardCharsets.UTF_8), toType);
	}

	public static <T> T DataContractDeserialize(String rawXml, java.lang.Class... knownTypes)
	{
		try (XmlReader reader = XmlReader.Create(new StringReader(rawXml)))
		{
		//using (XmlDictionaryReader reader = XmlDictionaryReader.CreateTextReader(Encoding.UTF8.GetBytes(rawXml), new XmlDictionaryReaderQuotas() { MaxStringContentLength = int.MaxValue, MaxArrayLength = int.MaxValue, MaxNameTableCharCount = int.MaxValue }))

			DataContractSerializer Deserializer = new DataContractSerializer(T.class, knownTypes, Integer.MAX_VALUE, false, false, null, new MyCustomerResolver(T.class.getPackage()));
			return (T)Deserializer.ReadObject(reader);

		}
	}
	public static class MyCustomerResolver extends DataContractResolver
	{
		public MyCustomerResolver(Assembly sourceAssembly)
		{
			setSourceAssembly(sourceAssembly);
		}
		private Assembly SourceAssembly;
		public final Assembly getSourceAssembly()
		{
			return SourceAssembly;
		}
		private void setSourceAssembly(Assembly value)
		{
			SourceAssembly = value;
		}
		@Override
		public boolean TryResolveType(java.lang.Class dataContractType, java.lang.Class declaredType, DataContractResolver knownTypeResolver, tangible.OutObject<XmlDictionaryString> typeName, tangible.OutObject<XmlDictionaryString> typeNamespace)
		{
			//if (dataContractType == typeof(Customer))
			//{
			//    XmlDictionary dictionary = new XmlDictionary();
			//    typeName = dictionary.Add("SomeCustomer");
			//    typeNamespace = dictionary.Add("http://tempuri.com");
			//    return true;
			//}
			//else
			//{
			return knownTypeResolver.TryResolveType(dataContractType, declaredType, null, typeName, typeNamespace);
			//}
		}



		@Override
		public java.lang.Class ResolveName(String typeName, String typeNamespace, java.lang.Class declaredType, DataContractResolver knownTypeResolver)
		{
			java.lang.Class Result = knownTypeResolver.ResolveName(typeName, typeNamespace, null, knownTypeResolver);
			if (Result == null)
			{
				Result = getSourceAssembly().GetExportedTypes().FirstOrDefault(t = typeName.equals(> t.Name));
			}
			return Result;
		}
	}

	public static Object XmlDeserialize(String xml, java.lang.Class toType)
	{
		try (MemoryStream memoryStream = new MemoryStream(xml.getBytes(java.nio.charset.StandardCharsets.UTF_8)))
		{
			InputStreamReader str = new InputStreamReader(memoryStream);
			XmlSerializer xSerializer = new XmlSerializer(toType);
			return xSerializer.Deserialize(str);
		}
	}

	public static <T> T Clone(T sender)
	{
		return Rasad.Core.TSerializationHelper.<T>DataContractDeserialize(DataContractSerialize(sender));
	}

	public static String AutoSerialize(Object obj)
	{
		return (new TSerializationContainer(obj)).GetXml();
	}
	public static Object AutoDeserialize(String xml)
	{
		return TSerializationContainer.Deserialize(xml);
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataContract] private class TSerializationContainer
	private static class TSerializationContainer
	{
		public TSerializationContainer(Object serializingObject)
		{
			setFullTypeName(serializingObject.getClass().getName() + "," + serializingObject.getClass().Assembly.GetName().Name);
			setContentXml(DataContractSerialize(serializingObject));
		}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public string ContentXml {get;set;}
		private String ContentXml;
		public final String getContentXml()
		{
			return ContentXml;
		}
		public final void setContentXml(String value)
		{
			ContentXml = value;
		}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [DataMember] public string FullTypeName {get;set;}
		private String FullTypeName;
		public final String getFullTypeName()
		{
			return FullTypeName;
		}
		public final void setFullTypeName(String value)
		{
			FullTypeName = value;
		}

		public final String GetXml()
		{
			return DataContractSerialize(this);
		}

		public static Object Deserialize(String xml)
		{
			TSerializationContainer Result = Rasad.Core.TSerializationHelper.<TSerializationContainer>DataContractDeserialize(xml);
			return Result.GetObject();
		}

		private Object GetObject()
		{
			java.lang.Class t = TTypeHelper.GetTypeSafe(getFullTypeName());
			if (t == null)
			{
				return null;
			}
			return DataContractDeserialize(getContentXml(), t);
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] BinarySerialize(object obj)
	public static byte[] BinarySerialize(Object obj)
	{
		try (MemoryStream ms = new MemoryStream())
		{
			BinaryFormatter bf = new BinaryFormatter();
			bf.Serialize(ms, obj);
			return ms.ToArray();
		}
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static object BinaryDeserialize(byte[] objData)
	public static Object BinaryDeserialize(byte[] objData)
	{
		try (MemoryStream ms = new MemoryStream(objData))
		{
			BinaryFormatter bf = new BinaryFormatter();
			return bf.Deserialize(ms);
		}
	}

	public static String JsonSerializeString(Object obj)
	{
		return JsonConvert.SerializeObject(obj);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] JsonSerializeUnicode(object obj)
	public static byte[] JsonSerializeUnicode(Object obj)
	{
		return JsonSerializeString(obj).getBytes(java.nio.charset.StandardCharsets.UTF_8);
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static byte[] JsonSerialize(object obj)
	public static byte[] JsonSerialize(Object obj)
	{
		return JsonSerializeString(obj).getBytes(java.nio.charset.StandardCharsets.US_ASCII);
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static T JsonDeserializeUnicode<T>(byte[] jsonData)
	public static <T> T JsonDeserializeUnicode(byte[] jsonData)
	{
		return Rasad.Core.TSerializationHelper.<T>JsonDeserializeString(Encoding.UTF8.GetString(jsonData));
	}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public static T JsonDeserialize<T>(byte[] jsonData)
	public static <T> T JsonDeserialize(byte[] jsonData)
	{
		return Rasad.Core.TSerializationHelper.<T>JsonDeserializeString(Encoding.ASCII.GetString(jsonData));
	}

	public static <T> T JsonDeserializeString(String jsonStr)
	{
		return JsonConvert.<T>DeserializeObject(jsonStr);
	}

}