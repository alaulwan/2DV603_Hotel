package model.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Connection {
	private Socket socket = new Socket();
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	
	
	
	private int BUFSIZE = 1024;
	private final int MYPORT = 0;
	private String RemoteIP;
	// private String RemoteIP = "94.46.42.40";
	private int RemotePort = 4444;
	
	private SocketAddress localBindPoint;
	private SocketAddress remoteBindPoint;
	
	public Connection(String RemoteIP) {
		this.RemoteIP = RemoteIP;
		/* Create local endpoint */
		localBindPoint = new InetSocketAddress(MYPORT);

		/* Create remote endpoint */
		remoteBindPoint = new InetSocketAddress(this.RemoteIP, RemotePort);
		
		try {
			socket.bind(localBindPoint);
			socket.connect(remoteBindPoint);
			
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
			
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String textToSend) {
		try {
			outputStream.write(textToSend.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void send(Object request) {
		try {
			objectOutputStream.writeObject(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Object receiveObject() {
		Object object=null;
		try {
			object = objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	public String receive() {
		String receivedMessage = "";
		byte[] buf = new byte[BUFSIZE];
		int numberOfBytes=-1;
		do {
			try {
				numberOfBytes = inputStream.read(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (numberOfBytes>0)
				receivedMessage += new String(buf, 0, numberOfBytes);

		} while (numberOfBytes>-1);
		
		return receivedMessage;
	}
	
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
