package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientSendThread extends Thread {

	private Socket socket;

	public ChatClientSendThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		try {

			// 3. IOStream 받아오기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			while (true) {
				// 5. 읽기
				String data = br.readLine();
				if (data.equals("quit")) {
					ChatClient.log("closed by server");
					return;
				}
				System.out.println(data);
			}
		} catch (Exception e) {

		}
	}
}
