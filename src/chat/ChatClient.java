package chat;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private static final String SERVER_IP = "192.168.0.68";
	private static final int SERVER_PORT = 6000;

	public static void main(String[] args) {

		Socket socket = null;
		Scanner sc = new Scanner(System.in);

		try {

			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("connected");

			Thread thread1 = new ChatClientReceiveThread(socket);
			Thread thread = new ChatClientSendThread(socket);
			thread1.start();
			thread.start();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void log(String log) {
		System.out.println("[client] " + log);
	}
}
