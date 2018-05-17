package model.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Connection {
	private Socket socket = new Socket();
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	private final int MYPORT = 0;
	private String RemoteIP;
	private int RemotePort = 4444;
	
	private SocketAddress localBindPoint;
	private SocketAddress remoteBindPoint;
	
	public Connection(String RemoteIP) {
		this.RemoteIP = RemoteIP;
		/* Create local endpoint */
		localBindPoint = new InetSocketAddress(MYPORT);

		/* Create remote endpoint */
		remoteBindPoint = new InetSocketAddress(this.RemoteIP, RemotePort);
		
		// Try to establish the connection
		try {
			socket.bind(localBindPoint);
			socket.connect(remoteBindPoint);
			
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method to send an object to the server
	public void send(Object request) {
		try {
			objectOutputStream.writeObject(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Method to receive an object from the server
	public Object receiveObject() {
		Object object=null;
		try {
			object = objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	// Close the connection
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
