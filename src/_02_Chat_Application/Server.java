package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Server {
	private int port;

	private ServerSocket server;
	private Socket connection;
	
	String connected;
	String recievedMessage;

	DataOutputStream out;
	DataInputStream in; 
	
	public Server(int port) {
		this.port = port;
		
		connected = "Unconnected.";
		recievedMessage = "";
	}
	
	public void start() {
		try {
			server = new ServerSocket();
			connected = "Waiting for connection...";
			connection = server.accept();
			connected = "Connected!";
			
			out = new DataOutputStream(connection.getOutputStream());
			in = new DataInputStream(connection.getInputStream());
			
			out.flush();
			
			while(connection.isConnected()) {
				try {
					recievedMessage = in.readUTF();
				}catch(EOFException e) {
					connected = "Connection lost.";
					System.exit(0);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send(String s) {
		try {
			if (out != null) {
				out.writeUTF(s);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
