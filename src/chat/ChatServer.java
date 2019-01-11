package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	private static final int PORT = 6000;
		
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		List<PrintWriter> list = new ArrayList<PrintWriter>();
		
		try {
			serverSocket = new ServerSocket();
			
			serverSocket.setReuseAddress(true);
			
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress,PORT));
			log("binding " + hostAddress + ":" + PORT);
			
			while(true)
			{
				Socket socket = serverSocket.accept();
				Thread thread = new ChatServerThread(socket,list);
				thread.start();
			}
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void log(String log) {
		System.out.println("[server#" + Thread.currentThread().getId() + "] " + log);
	}
}
