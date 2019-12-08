package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Server {
	private int port;

	private ServerSocket server;
	Socket connection;
	
	String recievedMessage;

	DataOutputStream out;
	DataInputStream in; 
	
	public Server(int port) {
		this.port = port;
		
		recievedMessage = "";
	}
	
	public void start(JLabel connectedLabel, JLabel[] messages) {
		try {
			server = new ServerSocket(port);
			//connectedLabel.setText("Waiting for connection...");
			Thread thread1 = new Thread(()-> {
				for(int i = 0; true; i++) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(i%3 == 0) connectedLabel.setText("Waiting for connection.");
					else if(i%3 == 1) connectedLabel.setText("Waiting for connection..");
					else if(i%3 == 2) connectedLabel.setText("Waiting for connection...");
				}
			});
			thread1.start();
			connection = server.accept();
			thread1.stop();
			connectedLabel.setText("Connected!");
			
			out = new DataOutputStream(connection.getOutputStream());
			in = new DataInputStream(connection.getInputStream());
			
			out.flush();
			
			while(connection.isConnected()) {
				try {
					recievedMessage = in.readUTF();
					for(int i = 0; i < messages.length-1; i++) {
						messages[i].setText(messages[i+1].getText());
					}
					messages[messages.length-1].setText(recievedMessage);
				}catch(EOFException e) {
					connectedLabel.setText("Connection lost.");
					break;
				}
			}
		}
		catch(Exception e) {
			connectedLabel.setText("Connection lost.");
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
