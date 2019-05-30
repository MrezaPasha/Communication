package main.java.Rasad.socket;

public interface SocketListener {	
	abstract public void processSocketEvent(byte [] data, SocketConnection connection);
}
