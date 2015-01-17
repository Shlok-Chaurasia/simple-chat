package server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread{
	
	private Server server;
	private Socket socket;
	
	public ServerThread(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		start();
		// TODO Auto-generated constructor stub
	}
	public void run()
	{
		try{
			
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			while(true)
			{
				String data = dataInputStream.readUTF();
				System.out.println("Sending Message: " + data);
				server.sendToAll(data);
			}
			
		}
		catch(EOFException ie)
		{
			
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
		finally{
			server.removeConnection(socket);
		}
	}
	
}
