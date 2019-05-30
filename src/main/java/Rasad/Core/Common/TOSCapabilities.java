package main.java.Rasad.Core.Common;


public final class TOSCapabilities
{
	private static Object lockObj = new Object();

	private static Boolean _IsWebSocketsSupported = null;
	public static boolean getIsWebSocketsSupported()
	{
		if (_IsWebSocketsSupported == null)
		{
			synchronized (lockObj)
			{
				if (_IsWebSocketsSupported == null)
				{
					try
					{
						new ClientWebSocket();
						_IsWebSocketsSupported = true;
					}
					catch (RuntimeException exp)
					{
						_IsWebSocketsSupported = false;
						//TODO MRCOMMENT : logger
						//TLogManager.Error("Web socket is not supported on this system", exp);
					}
				}
			}
		}
		return _IsWebSocketsSupported.booleanValue();
	}
}