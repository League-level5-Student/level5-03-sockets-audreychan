package _02_Chat_Application;

import java.awt.Color;
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
					
					if(ChatApp.isLight) {
						if(i%3 == 0) connectedLabel.setText("Waiting for connection.");
						else if(i%3 == 1) connectedLabel.setText("Waiting for connection..");
						else if(i%3 == 2) connectedLabel.setText("Waiting for connection...");
					}
					else {
						if(i%3 == 0) connectedLabel.setText("<html><pre><font color =\"white\">Waiting for connection.</pre></html>");
						else if(i%3 == 1) connectedLabel.setText("<html><pre><font color =\"white\">Waiting for connection..</pre></html>");
						else if(i%3 == 2) connectedLabel.setText("<html><pre><font color =\"white\">Waiting for connection...</pre></html>");
					}
				}
			});
			thread1.start();
			connection = server.accept();
			thread1.stop();
			
			if(ChatApp.isLight)
				connectedLabel.setText("Connected!");
			else
				connectedLabel.setText("<html><pre><font color =\"white\">Connected!</pre></html>");
			
			out = new DataOutputStream(connection.getOutputStream());
			in = new DataInputStream(connection.getInputStream());
			
			out.flush();
			
			while(connection.isConnected()) {
				try {
					recievedMessage = in.readUTF();
					ChatApp.addMessage(recievedMessage);
					if(ChatApp.isLight)
						messages[messages.length-1].setBackground(new Color(205, 250, 212));
					else
						messages[messages.length-1].setBackground(new Color(2, 97, 14));
				}catch(EOFException e) {
					if(ChatApp.isLight)
						connectedLabel.setText("Connection Lost.");
					else
						connectedLabel.setText("<html><pre><font color =\"white\">Connection lost.</pre></html>");
					break;
				}
			}
		}
		catch(Exception e) {
			if(ChatApp.isLight)
				connectedLabel.setText("Connection Lost.");
			else
				connectedLabel.setText("<html><pre><font color =\"white\">Connection lost.</pre></html>");
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
