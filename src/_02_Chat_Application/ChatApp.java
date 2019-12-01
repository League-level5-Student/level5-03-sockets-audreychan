package _02_Chat_Application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp implements ActionListener {

	JFrame frame;
	JPanel panel;
	JLabel connected;
	JLabel messages;
	JLabel serverOrClient;
	JTextField messageBox;
	JButton send;
	
	String messageText;
	boolean unsentMessage;
	
	Server server;
	Client client;
	
	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp() {
		frame = new JFrame();
		panel = new JPanel();
		connected = new JLabel();
		messages = new JLabel();
		serverOrClient = new JLabel();
		messageBox = new JTextField();
		send = new JButton("Send");
		
		frame.add(panel);
		panel.add(connected);
		panel.add(serverOrClient);
		panel.add(messages);
		panel.add(messageBox);
		panel.add(send);
		
		send.addActionListener(this);
		
		messageText = "";
		unsentMessage = false;
		
		frame.setPreferredSize(new Dimension(500, 1000));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int host = JOptionPane.showConfirmDialog(null, "Are you hosting this session?", "Buttons!", JOptionPane.YES_NO_OPTION);
		if(host == JOptionPane.YES_OPTION){
			while(true) {
				connected.setText(server.connected);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
