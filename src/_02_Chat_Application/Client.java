package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	private String ip;
	private int port;

	Socket connection;
	
	String connected;
	String recievedMessage;

	DataOutputStream out;
	DataInputStream in;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		
		connected = "";
		recievedMessage = "";
	}
	
	public void start(){
		try {
			
			connected = "Trying to connect...";
			connection = new Socket(ip, port);
			connected = "Connected!";

			out = new DataOutputStream(connection.getOutputStream());
			in = new DataInputStream(connection.getInputStream());

			out.flush();
			
			while (connection.isConnected()) {
				try {
					recievedMessage = in.readUTF();
				} catch (Exception e) {
					connected = "Connection lost.";
					System.exit(0);
				}
			}
		} catch (Exception e) {
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
