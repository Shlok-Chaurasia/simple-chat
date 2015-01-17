package client;

import java.awt.Panel;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends Panel implements Runnable {

	private TextField tf = new TextField();
	private TextArea ta = new TextArea();

	private Socket socket;

	private DataOutputStream dout;
	private DataInputStream din;

	public Client(String host, int port) {
		setLayout(new BorderLayout());
		add("North", tf);
		add("Center", ta);
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processMessage(e.getActionCommand());
			}
		});
		try {
			// Initiate the connection
			socket = new Socket(host, port);
			// We got a connection! Tell the world
			System.out.println("connected to " + socket);
			// Let's grab the streams and create DataInput/Output streams
			// from them
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			// Start a background thread for receiving messages
			new Thread(this).start();
		} catch (IOException ie) {
			System.out.println(ie);
		}
	}

	// Gets called when the user types something
	private void processMessage(String message) {
		try {
			// Send it to the server
			dout.writeUTF(message);
			// Clear out text input field
			tf.setText("");
		} catch (IOException ie) {
			System.out.println(ie);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			// Receive messages one-by-one, forever
			while (true) {
				// Get the next message
				String message = din.readUTF();
				// Print it to our text window
				ta.append(message + "\n");
			}
		} catch (IOException ie) {
			System.out.println(ie);
		}
	}

}
