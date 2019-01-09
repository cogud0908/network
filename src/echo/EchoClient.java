package echo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	private static final String SERVER_IP = "192.168.152.1";
	private static final int SERVER_PORT = 6000;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Socket socket = null;

		try {
			socket = new Socket();

			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("connected");

			try {
				// 3. IOStream 받아오기
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);

				// 4. 쓰기

				while (true) {
					// 5. 읽기
					System.out.print(">>");
					String line = sc.nextLine();
								
					if (line.equals("exit")) {
						// 정상종료 : remote socket close()
						// 메소드를 통해서 정상적으로 소켓을 닫은 경우
						log("closed by server");
						return;
					}	
					pw.println(line);
					
					String data = br.readLine();
					if(data == null)
					{
						log("closed by server");
						break;
					}
					System.out.println("<<" + data);
				}

			} catch (Exception e) {

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void log(String log) {
		System.out.println("[client] " + log);
	}
}
