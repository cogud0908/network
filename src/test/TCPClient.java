package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {

	private static final String SERVER_IP = "192.168.152.1";
	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {

		// 1. 소켓 생성
		Socket socket = null;

		try {
			socket = new Socket();

			// 2. 서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[client] connected");

			try {
				// 3. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				// 4. 쓰기
				String data = "Hello World";
				os.write(data.getBytes("UTF-8"));

				// 5. 읽기
				byte[] buffer = new byte[255];
				int readByteCount = is.read(buffer); // Blocking

				if (readByteCount == -1) {
					// 정상종료 : remote socket close()
					// 메소드를 통해서 정상적으로 소켓을 닫은 경우
					System.out.println("[client] closed by server");
					return;
				}

				data = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.println("[client] received : " + data);
			} catch (Exception e) {

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
