package _02_Chat_Application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp implements ActionListener, KeyListener {

	JFrame frame;
	JPanel panel;
	JLabel connectedLabel;
	JLabel spacer;
	JButton clear;
	static JLabel[] messages = new JLabel[20];
	JTextField messageBox;
	JButton send;

	String name;
	static boolean isLight;
	
	Server server;
	Client client;
	
	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp() {
		frame = new JFrame();
		panel = new JPanel();
		connectedLabel = new JLabel();
		spacer = new JLabel();
		clear = new JButton("Clear Messages");
		messageBox = new JTextField();
		send = new JButton("Send");
		
		int light = JOptionPane.showConfirmDialog(null, "Light mode?");
		if(light == 0) isLight = true;
		else isLight = false;
		
		if(!isLight) panel.setBackground(new Color(31, 31, 31));
		frame.add(panel);
		panel.add(connectedLabel);
		panel.add(spacer);
		panel.add(clear);
		
		for(int i = 0; i < messages.length; i++) {
			messages[i] = new JLabel();
			messages[i].setPreferredSize(new Dimension(400, 20));
			panel.add(messages[i]);
		}
		
		panel.add(messageBox);
		panel.add(send);
		
		spacer.setPreferredSize(new Dimension(400, 2));
		clear.addActionListener(this);
		send.addActionListener(this);
		messageBox.addKeyListener(this);
		messageBox.setPreferredSize(new Dimension(300, 20));
		messageBox.setText(" ");
		messageBox.requestFocus();
		
		name = JOptionPane.showInputDialog("Enter a username: ");
		
		frame.setPreferredSize(new Dimension(460, 900));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*=================================================*/
		
		server = new Server(80);
		if(isLight) connectedLabel.setText("Unconnected.");
		else connectedLabel.setText("<html><pre><font color =\"white\"> Unconnected. </pre></html>");
		server.start(connectedLabel, messages);
		
	}
	
	public static void addMessage(String message) {
		for(int i = 0; i < messages.length-1; i++) {
			messages[i].setText(messages[i+1].getText());
			messages[i].setOpaque(true);
			messages[i].setBackground(messages[i+1].getBackground());
			messages[i].setPreferredSize(messages[i+1].getPreferredSize());
		}
		
		String formatted = formatMessage(message, messages[messages.length-1]);
		
		if(isLight) 
			messages[messages.length-1].setText("<html><pre><font face = \"roboto\">    " + formatted + "</pre></html>");
		else
			messages[messages.length-1].setText("<html><pre><font color =\"white\">    " + formatted + "</pre></html>");
		messages[messages.length-1].setOpaque(true);
	}
	
	void sendMessage() {
		addMessage(name + ": " + messageBox.getText());
		if(isLight)
			messages[messages.length-1].setBackground(new Color(186, 216, 250));
		else
			messages[messages.length-1].setBackground(new Color(0, 53, 133));
		
		server.send(name + ": " + messageBox.getText());
		messageBox.setText(" ");
	}
	
	public static String formatMessage(String message, JLabel label) {
		String fin = "";
		int lineCount = 1;
		int size = 40;
		
		while(message.length() > size) {
			fin += message.substring(0, size) + "\n   ";
			message = message.substring(size+1);
			lineCount++;
		}
		fin += message;
		label.setPreferredSize(new Dimension(400, lineCount*20));
		
		return fin;
	}
	
	void clearMessages() {
		for(int i = 0; i < messages.length; i++) {
			messages[i].setText("");
			messages[i].setBackground(null);
			messages[i].setPreferredSize(new Dimension(400, 20));
		}
		messageBox.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton b = (JButton)e.getSource();
		if(b.equals(send)) sendMessage();
		else if(b.equals(clear)) clearMessages();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 10) sendMessage();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	// 11111111111111111111111111111111111 1111112222222222222222222222222222222222222 222222
	//<font face=\"veranda\">
}
