package main.java.Rasad.Communication.Core;


import org.json.JSONObject;
import xyz.baddeveloper.lwsl.client.SocketClient;
import xyz.baddeveloper.lwsl.packet.Packet;
import xyz.baddeveloper.lwsl.server.SocketServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;



//using Rasad.Core.Services.NotificationService;
//using Rasad.Core.Services.NotificationService.Public;
//using Rasad.Core.Services.NotificationService.Structs;


//[System.Diagnostics.DebuggerStepThrough]
public class TSocketListener<TMSG, TIdentity extends TMSG> implements Closeable
{
    static
    {
        String MESSAGEPAYLOAD = "message";
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
        //NetworkChange.NetworkAvailabilityChanged += NetworkChange_NetworkAvailabilityChanged;
        setNetworkAvailabile(true);
    }

    //TODO MRCOMMENT : no need foe networkChange method
    /*private static void NetworkChange_NetworkAvailabilityChanged(Object sender, NetworkAvailabilityEventArgs e)
    {
        setNetworkAvailabile(e.IsAvailable);
    }*/
    private static boolean NetworkAvailabile;
    public static boolean getNetworkAvailabile()
    {
        return NetworkAvailabile;
    }
    private static void setNetworkAvailabile(boolean value)
    {
        NetworkAvailabile = value;
    }

    //C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
    ///#region Fields
    private static final int BufferLength = 10 * 1024;
    //TODO MRREPLACE
    private SocketClient _ClientSocket = new SocketClient("localhost", 7879);
    private tangible.Action1Param<RuntimeException> _ErrorCallback;
    private tangible.Action1Param<TMessageContainer<TMSG, TIdentity>> _MessageRecievedCallback;

    //C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: private byte[] _Buffer = new byte[BufferLength];
    private byte[] _Buffer = new byte[BufferLength];
    private TMessageContainer<TMSG, TIdentity> _Message;
    private Timer _Timer;
    private Timer _TimerPing;
    private boolean _IsDisposed;
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
    ///#endregion

    //C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
    ///#region Properties
    private long RecievedMessageCount;
    public final long getRecievedMessageCount()
    {
        return RecievedMessageCount;
    }
    private void setRecievedMessageCount(long value)
    {
        RecievedMessageCount = value;
    }
    private long RecievedDataSize;
    public final long getRecievedDataSize()
    {
        return RecievedDataSize;
    }
    private void setRecievedDataSize(long value)
    {
        RecievedDataSize = value;
    }
    private long SendMessageCount;
    public final long getSendMessageCount()
    {
        return SendMessageCount;
    }
    private void setSendMessageCount(long value)
    {
        SendMessageCount = value;
    }
    private long SendDataSize;
    public final long getSendDataSize()
    {
        return SendDataSize;
    }
    private void setSendDataSize(long value)
    {
        SendDataSize = value;
    }

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
    ///#endregion

    public void onConnected(Socket socket)
    {
        System.out.println("is Connected");
        boolean result = _ClientSocket.isConnected();
    }

    public final void close() throws IOException
    {
        System.out.println("is Disconnected");
        boolean result = _ClientSocket.isConnected();
    }

    public void onDisconnected(Socket socket)
    {
        try
        {
            System.out.println("is Disconnected");
            close();
        }
        catch (Exception e)
        {
            //TODO Logger
            System.out.println("error");
        }

    }

    /*public void onPacketReceive(SocketClient socket , Packet packet)
    {
        System.out.println(String.format("Packet received! (%s)", packet.getObject().toString()));
    }*/

    public final boolean StartReciving(SocketClient socket, tangible.Action1Param<TMessageContainer<TMSG, TIdentity>> messageRecievedCallback, tangible.Action1Param<RuntimeException> errorCallback) throws Exception
    {
        try
        {
            _ClientSocket = socket;
            //_ClientSocket.SendTimeout = 20000;
            _ClientSocket.setTimeout(2000);
            //TODO MRREMOVE : there is no property to set
            //_ClientSocket.ReceiveTimeout = 20000;
            // set callbackSunction
            _ClientSocket.addConnectEvent(this::onConnected);
            _ClientSocket.addDisconnectEvent(this::onDisconnected);
            _ClientSocket.addPacketReceivedEvent(this::OnDataReceived);
            _ClientSocket.connect();
            return true;
        }
        catch (Exception e)
        {
            //TODO MRREMOVE : logger
            return false;

        }





        //_MessageRecievedCallback = (TMessageContainer<TMSG, TIdentity> obj) -> messageRecievedCallback.invoke(obj);
        //_ErrorCallback = (RuntimeException obj) -> errorCallback.invoke(obj);
        //_Timer = new Timer(1000);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
        //_Timer.Elapsed += TimerTick;
        //_Timer.AutoReset = false;
        //_Timer.Start();

        //_TimerPing = new Timer(TCommunicationConfig<TMSG, TIdentity>.CommunicationPingIntervalMilliseconds);
        //_TimerPing.Elapsed += TimerPingTick;
        //_TimerPing.AutoReset = false;
        //_TimerPing.Start();

        //MRCOMMENT : replace with java socket

        /*UpdateIsConnected();
        if (WaitForData())
        {
            return true;
        }
        else
        {
            return false;
        }*/
    }

    //C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] private void TimerTick(object sender, ElapsedEventArgs e)

    // TODO : MRCOMMENT : no need timer tick because socket library handle callback
    /*private void TimerTick(Object sender, ElapsedEventArgs e)
    {
        Timer tim = _Timer;
        try
        {
            UpdateIsConnected();

        }
        catch (java.lang.Exception e)
        {
        }
        finally
        {
            if (!_IsDisposed && tim != null)
            {
                tim.Start();
            }
        }
    }*/

    //C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [System.Diagnostics.DebuggerStepThrough] private void TimerPingTick(object sender, ElapsedEventArgs e)
    // TODO : MRCOMMENT : no need timer tick because socket library handle callback
    /*private void TimerPingTick(Object sender, ElapsedEventArgs e)
    {
        Timer tim = _TimerPing;
        try
        {
            // send ping if connected
            if (getIsConnected())
            {
//C# TO JAVA CONVERTER WARNING: Java does not allow direct instantiation of arrays of generic type parameters:
//ORIGINAL LINE: Send(new TMSG[] { TCommunicationConfig<TMSG, TIdentity>.GetNewPingMessage() });
                Send((TMSG[])new Object[] {TCommunicationConfig<TMSG, TIdentity>.GetNewPingMessage()});
            }
        }
        catch (java.lang.Exception e)
        {
        }
        finally
        {
            if (!_IsDisposed && tim != null)
            {
                tim.Start();
            }
        }
    }*/
    //TODO MRCOMMENT : t handle by connect and disconnect callback

    /*private void UpdateIsConnected()
    {
        boolean result = _ClientSocket.Connected;
        // ** Notes:
        //   Sometimes on servers, when number of cameras is high (or for any other reason), and main service is connected to local communication server
        //   (only when in local - when main service is connected to another computer, no disconnect is occurred.),
        //   part1 check is timed out, and because no data is available for main service in that time to read,
        //   the final result is false and server disconnects main service from communication.
        //   This checks is no longer required. This check is put when we had no ping messages,
        //   and in some cases cannot detect disconnect on sockets until some data is transmitted.
        //   Because we have ping messages, always we can detect disconnect and there is no more need for
        //   these buggy checks.
        //if (result)
        //{
        //    bool part1 = _ClientSocket.Poll(10000, SelectMode.SelectRead);
        //    bool part2 = (_ClientSocket.Available == 0);
        //    result = !(part1 && part2);
        //}
        this.setIsConnected(result && getNetworkAvailabile());
    }
*/
    //TODO MRCOMMENT : it handle by socket library by isConnected() method
    /*private boolean _IsConnected;
    public final boolean getIsConnected()
    {
        return _IsConnected;
    }*/
    //TODO MRCOMMENT : it handle by socket library by isConnected() method
   /* public final void setIsConnected(boolean value)
    {
        if (_IsConnected != value)
        {
            _IsConnected = value;
            if (!_IsConnected)
            {
                _ErrorCallback.invoke(new RuntimeException("Disconnected"));
            }
        }
    }*/

    //TODO MRCOMMENT : it handle by socket library
    /*private boolean WaitForData()
    {
        if (!getIsConnected())
        {
            return false;
        }

        try
        {
            //Start listening to the data asynchronously
            //TLogManager.Info("BeginReceive");
            _ClientSocket.BeginReceive(_Buffer, 0, _Buffer.length, SocketFlags.None, (System.IAsyncResult ar) -> OnDataReceived(ar), null);

            return true;

        }
        catch (RuntimeException exc)
        {
            _ErrorCallback.invoke(new RuntimeException("Error in WaitForData", exc));
        }
        return false;
    }*/

    private int dataReceiveSizeZeroCount = 0;

    //C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//ORIGINAL LINE: [MethodImpl(MethodImplOptions.Synchronized)] private void OnDataReceived(IAsyncResult ar)
    //private void OnDataReceived(IAsyncResult ar)
    private void OnDataReceived(SocketClient socket , Packet packet)
    {
        boolean errorOccured = false;
        try
        {
            //Socket socket = _ClientSocket;
            if (socket == null || !socket.isConnected() )
            {
                return;
            }

            /*SocketError socketError;
            tangible.OutObject<System.Net.Sockets.SocketError> tempOut_socketError = new tangible.OutObject<System.Net.Sockets.SocketError>();*/
            int AllRecievedBytes = packet.getObject().getString("message").getBytes().length;
            //socketError = tempOut_socketError.argValue;
            //TLogManager.Info("EndReceive");

            /*if (socketError != SocketError.Success)
            {
                throw new RuntimeException("Disconnected (" + socketError.toString() + ")");
            }*/
            if (AllRecievedBytes == 0)
            {
                dataReceiveSizeZeroCount++;
                if (dataReceiveSizeZeroCount >= 3)
                {
                    throw new RuntimeException("Disconnected (No bytes received)");
                }
            }
            else
            {
                dataReceiveSizeZeroCount = 0;
            }
            setRecievedDataSize(getRecievedDataSize() + AllRecievedBytes);

            if (AllRecievedBytes > 0)
            {
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: List<byte> AllBytes = new List<byte>(_Buffer.Take(AllRecievedBytes));
                //TODO : MRREPLACE : check is working on runtime
                ArrayList<Byte> AllBytes = new ArrayList<Byte>(packet.getObject().getInt("message"));

                //TODO : MRREPLACE : check is working on runtime
                //while (AllBytes.Any())
                while (!AllBytes.isEmpty())
                {
                    if (_Message == null)
                    {
                        _Message = new TMessageContainer<TMSG, TIdentity>();
                    }
                    if (_Message.Recieve(AllBytes))
                    {
                        setRecievedMessageCount(getRecievedMessageCount() + 1);
                        //TLogManager.Log("Recieve ", AllRecievedBytes.ToString(), " Completed");
                        try
                        {
                            _MessageRecievedCallback.invoke(_Message);
                        }
                        catch (RuntimeException exc)
                        {
                            //TODO MRCOMMENT : logger
                            //TLogManager.Error("TSocketListener callback Error ! ", exc);
                        }
                        finally
                        {
                            _Message = null;
                        }
                    }
                    else
                    {
                        //TODO : MRREPLACE : check is working on runtime
                        //if (AllBytes.Any())
                        if (AllBytes.isEmpty())
                        {
                            AllBytes.clear();
                            _Message = null;
                            //TODO MRCOMMENT : logger

                            //TLogManager.Error("TSocketListener Invalid Received Bytes", "Recieve Count: " + AllRecievedBytes);
                        }
                        if (_Message != null && !_Message.getIsValid())
                        {
                            //TODO MRCOMMENT : logger
                            //TLogManager.Error("TSocketListener Invalid Message, ContentLength=" + String.valueOf(_Message.getContentLength()));
                            _Message = null;
                        }
                    }
                }
            }
        }
        catch (RuntimeException ex)
        {
            _ErrorCallback.invoke(new RuntimeException("Error inside OnDataReceived", ex));
            errorOccured = true;
        }
        //TODO MRCOMMENT : no need for this lines
        /*finally
        {
            if (!errorOccured && !_IsDisposed && socket.isConnected())
            {
                if (!WaitForData())
                {
                    _ErrorCallback.invoke(new RuntimeException("Disconnected 2"));
                }
            }
        }*/
    }

    //protected virtual void OnSend(IAsyncResult ar)
    //{
    //    try
    //    {
    //        _ClientSocket.EndSend(ar);
    //    }
    //    catch (Exception ex)
    //    {
    //        _ErrorCallback(ex);
    //    }
    //}

    public final boolean Send(TMSG[] message)
    {
        return Send(new TMessageContainer<TMSG, TIdentity>(message));
    }

    public final boolean Send(TMessageContainer<TMSG, TIdentity> message)
    {
        if (!_ClientSocket.isConnected() || _ClientSocket == null)
        {
            return false;
        }
//C# TO JAVA CONVERTER WARNING: Unsigned integer types have no direct equivalent in Java:
//ORIGINAL LINE: byte[] byteData = message.ToByte();
        byte[] byteData = message.ToByte();
        try
        {
            setSendDataSize(getSendDataSize() + byteData.length);
            setSendMessageCount(getSendMessageCount() + 1);
            //Send it to the server
            //TODO MRREPLACE : check is working on runtime
            //return _ClientSocket.Send(byteData, SocketFlags.None) == byteData.length;
            _ClientSocket.sendPacket(new Packet(new JSONObject().put("message", byteData)));
            return true;
        }
        catch (RuntimeException exc)

        {
            //TODO MRCOMMENT : logger
            //TLogManager.Error("Sending Error : " + String.valueOf(byteData.length), exc);
            _ErrorCallback.invoke(new RuntimeException("Error in TSocketListener Send", exc));
            return false;
        }

    }

    //TODO MRCOMMENT : no need to close function
    /*public final void close() throws IOException
    {
        if (_IsDisposed)
        {
            return;
        }
        _IsDisposed = true;
        if (_TimerPing != null)
        {
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
            _TimerPing.Elapsed -= TimerPingTick;
            _TimerPing.Stop();
            _TimerPing.Dispose();
        }
        if (_Timer != null)
        {
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
            _Timer.Elapsed -= TimerTick;
            _Timer.Stop();
            _Timer.Dispose();
        }
        if (_ClientSocket != null)
        {
            try
            {
                if (_ClientSocket.Connected)
                {
                    _ClientSocket.Disconnect(false);
                    _ClientSocket.Close();
                }
            }
            catch (java.lang.Exception e)
            {
            }

            _ClientSocket.Dispose();
        }
    }*/
}