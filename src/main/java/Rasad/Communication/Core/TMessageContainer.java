package main.java.Rasad.Communication.Core;

import Rasad.Core.*;
import Rasad.Core.Services.*;
import com.google.zxing.common.BitArray;
import main.java.Rasad._core.BitConvertor;
import org.apache.commons.lang3.ArrayUtils;

import javax.sound.sampled.AudioFormat;
import java.util.*;
import java.io.*;

import static main.java.Rasad.Communication.Core.TCommunicationConfig.CustomSerializeMessagesHandler;

public class TMessageContainer<TMSG, TIdentity extends TMSG>
{
	private static final int MaxContentLength = 1 * 1024 * 1024; // 1 MB
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private const byte MagicByte1 = 0x3a;
	private static final byte MagicByte1 = 0x3a;
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private const byte Magicbyte2 = 0xF8;
	private static final byte Magicbyte2 = (byte)0xF8;
	public TMessageContainer()
	{
		setIsValid(true);
	}
	//Converts the bytes into an object of type Data
	public TMessageContainer(TMSG[] message)
	{
		_RecievedBytes.add((new TMessageWrapper<TMSG, TIdentity>(message)).ToByteArray());
	}

	//Converts the Data structure into an array of bytes
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] ToByte()
	public final byte[] ToByte()
	{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] messageBytes = Data.ToArray();
		List<Byte> temp = new ArrayList<Byte>();
		getData().forEach(temp::add);
		Byte[] messageBytes = (Byte[]) temp.toArray();




		//TODO MREDIT : check run time
		//byte[] messageBytes = getData().ToArray();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] LengthPrefixBytes = BitConverter.GetBytes((Int32)messageBytes.Length);
		byte[] LengthPrefixBytes = BitConvertor.getBytes((int)messageBytes.length);
		BitConvertor.getBytes((int)messageBytes.length);
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] magicBytes = new byte[] { MagicByte1, Magicbyte2 };
		byte[] magicBytes = new byte[] {MagicByte1, Magicbyte2};
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] prefixBytes = magicBytes.Concat(LengthPrefixBytes).ToArray();
		//TODO MRREPLACE:
		//byte[] prefixBytes = magicBytes.Concat(LengthPrefixBytes).ToArray();
		byte[] prefixBytes = BitConvertor.concat(magicBytes,LengthPrefixBytes);

		//TODO MRREPLACE:
		//return prefixBytes.Concat(messageBytes).ToArray();
		return BitConvertor.concat(prefixBytes,ArrayUtils.toPrimitive(messageBytes));
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: List<byte[]> _RecievedBytes = new List<byte[]>();
	private ArrayList<byte[]> _RecievedBytes = new ArrayList<byte[]>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public IEnumerable<byte> getData()

	public final java.lang.Iterable<Byte> getData()
	{
		//TODO MRREPLACE
		//return _RecievedBytes.SelectMany(t -> t);
		return (Iterable<Byte>) _RecievedBytes.iterator();
	}
	private int ContentLength;
	public final int getContentLength()
	{
		return ContentLength;
	}
	private void setContentLength(int value)
	{
		ContentLength = value;
	}
	private boolean IsValid;
	public final boolean getIsValid()
	{
		return IsValid;
	}
	private void setIsValid(boolean value)
	{
		IsValid = value;
	}

//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private List<byte> headerBytes = new List<byte>();
	private ArrayList<Byte> headerBytes = new ArrayList<Byte>();
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public bool Recieve(List<byte> byteData)
	public final boolean Recieve(ArrayList<Byte> byteData)
	{
		try
		{
			if (getContentLength() == 0)
			{
				int headerRemained = 6 - headerBytes.size();
				if (headerRemained > 0)
				{
					int canRead = Math.min(headerRemained, byteData.size());
					//TODO MRREPLACE
					headerBytes.addAll(Arrays.asList(byteData.get(canRead)));
					byteData.subList(0, canRead).clear();
				}
				if (headerBytes.size() == 6)
				{
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] lengthBytes = new byte[4];
					byte[] lengthBytes = new byte[4];
					System.arraycopy(tangible.ByteLists.toArray(headerBytes), 2, lengthBytes, 0, lengthBytes.length);
					setContentLength(BitConvertor.toInt32(lengthBytes, 0));
					setIsValid(headerBytes.get(0).equals(MagicByte1) && headerBytes.get(1).equals(Magicbyte2) && getContentLength() > 0 && getContentLength() < MaxContentLength);
				}
			}
			if (!getIsValid())
			{
				return false;
			}

			if (getContentLength() > 0)
			{
				//TODO MECOMMENT : must uncomment
				//_RecievedBytes.add(t -> t.Len)

				//int Remained = getContentLength() - _RecievedBytes.Sum(t -> t.Length);
				int Remained = getContentLength() - BitConvertor.sum(_RecievedBytes);
				int canRead = Math.min(Remained, byteData.size());
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: _RecievedBytes.Add(byteData.Take(canRead).ToArray());
				//TODO MRREPLACE
				//_RecievedBytes.add((Byte)(byteData.get(canRead).ToArray()));
				//BitConvertor.concat(_RecievedBytes,byteData.get(canRead).)
				//TODO MRCHANGE
				byte[] temp = null;
				temp[0] = byteData.get(canRead);
				_RecievedBytes.add(temp);
				byteData.subList(0, canRead).clear();

				//setIsComplete(_RecievedBytes.Sum(t -> t.Length) == getContentLength());
				setIsComplete(BitConvertor.sum(_RecievedBytes) == getContentLength());
				return getIsComplete();
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			return false;
		}

	}

	private boolean IsComplete;
	public final boolean getIsComplete()
	{
		return IsComplete;
	}
	private void setIsComplete(boolean value)
	{
		IsComplete = value;
	}

	private TMSG[] deserializedMessage = null;

	public final TMSG[] GetMessage()
	{
		return GetMessage(false);
	}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public TMSG[] GetMessage(bool dontDeserialize = false)
	public final TMSG[] GetMessage(boolean dontDeserialize)
	{
		// deserialize one time only
		if (deserializedMessage != null)
		{
			return deserializedMessage;
		}
		TMessageWrapper messageWrapper = new TMessageWrapper();

		deserializedMessage = (TMSG[]) messageWrapper.Load(convertIteratorByteToByteArray(getData()), dontDeserialize);
		return deserializedMessage;
	}

	public byte[] convertIteratorByteToByteArray(java.lang.Iterable<Byte> iterBytes)
	{
		byte[] result = null;
		List<Byte> bytes= new ArrayList<>();
		for (Byte b:iterBytes) {
			bytes.add(b);
		}
		return ArrayUtils.toPrimitive((Byte[]) bytes.toArray());
	}

	//[KnownType(typeof(byte[]))]
	//[DataContract]
	//[ProtoContract]
	private static class TMessageWrapper<TMSG, TIdentity extends TMSG>
	{
		private TMessageWrapper()
		{
		} // for protobuf
		public TMessageWrapper(TMSG[] dataItems)
		{
			//TODO MREDITED
			TCommunicationConfig<TMSG, TIdentity> communicationConfig = new TCommunicationConfig<>();
			D =  communicationConfig.CustomSerializeMessagesHandler.invoke(dataItems);

			//D = TCommunicationConfig<TMSG, TIdentity>.CustomSerializeMessagesHandler(dataItems);
		}

		//[DataMember]
		//[ProtoMember(1)]
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] D;
		private byte[] D;

		//[DataMember]
		//[ProtoMember(2)]
		//private string DT;


		public  TMSG[] Load(byte[] data)
		{
			return Load(data, false);
		}

//C# TO JAVA CONVERTER NOTE: Java does not support optional parameters. Overloaded method(s) are created above:
//ORIGINAL LINE: public static TMSG[] Load(byte[] data, bool dontDeserialize = false)
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
		public TMSG[] Load(byte[] data, boolean dontDeserialize)
		{
			//TODO MREDITED
			//return TCommunicationConfig<TMSG, TIdentity>.CustomDeserializeMessages(data, dontDeserialize);
			 TCommunicationConfig communicationConfig = new TCommunicationConfig();
			return (TMSG[]) communicationConfig.CustomDeserializeMessages(data, dontDeserialize);

		}
		//private string GetXml()
		//{
		//    return TSerializationHelper.DataContractSerialize(this);
		//}
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: public byte[] ToByteArray()
		public final byte[] ToByteArray()
		{
			//return Encoding.Unicode.GetBytes(GetXml());
			//return TSerializationHelper.ProtoBufSerialize<TMessageWrapper>(this);
			return D;
		}
		@Override
		public String toString()
		{
			//return string.Format("Type : {0} , Data : {1}", DT, D);
			return String.format("Data : %1$s", D);
		}
	}
}