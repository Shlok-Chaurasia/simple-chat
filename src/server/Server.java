package server;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;


public class Server {
	
	private ServerSocket serverSocket;
	private Hashtable outputStreams = new Hashtable();
	public Server(int port) throws IOException{
		// TODO Auto-generated constructor stub
		serverSocket = new ServerSocket(port);
		System.out.println("Connection from:" + serverSocket);
		while(true)
		{
			Socket socket = serverSocket.accept();
			System.out.println("Connection from:" + socket);
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			outputStreams.put(socket, dataOutputStream);
			new ServerThread(this, socket);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = Integer.parseInt(args[0]);
		try {
			new Server(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Enumeration getOutputStreams() {
		return outputStreams.elements();
		}
	
	public void sendToAll(String data) {
		// TODO Auto-generated method stub
		synchronized(outputStreams)
		{
			for (Enumeration e = getOutputStreams(); e.hasMoreElements(); ) {
				DataOutputStream dout = (DataOutputStream)e.nextElement();
				try{
					dout.writeUTF(data);
				}
				catch(IOException ie)
				{
					 System.out.println( ie ); 
				}
			}
		}
		
	}

	public void removeConnection(Socket socket) {
		// TODO Auto-generated method stub
		synchronized(outputStreams)
		{
			outputStreams.remove(socket);
			try{
				socket.close();
			}
			catch(IOException ie)
			{
				System.out.println( "Error closing "+ socket );
		         ie.printStackTrace();	
			}
		}
	}

}
