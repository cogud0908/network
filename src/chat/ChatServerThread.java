package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import echo.EchoServer;

public class ChatServerThread extends Thread {

	private String name;
	private Socket socket;
	private List<PrintWriter> list;
	
	public ChatServerThread(Socket socket, List<PrintWriter> list) {
		this.socket = socket;
		this.list = list;
	}

	public void run() {
		
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

		// 클라이언트 정보
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		// 포트번호
		int remotePort = inetRemoteSocketAddress.getPort();

		System.out.println(remoteHostAddress + " : " + remotePort);
		ChatServer.log("connected by client[" + remoteHostAddress + ":" + remotePort + "]");

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			while (true) {

				String data = br.readLine();

				if (data == null) {
					break;
				}

				String [] tokens = data.split(":");
				
				if(tokens[0].equals("join"))
				{	
					doJoin(tokens[1],pw);
				} else if (tokens[0].equals("message"))
				{
					doMessage(tokens[1]);
				} else if (tokens[0].equals("quit"))
				{
					doQuit(pw);
				} else
				{
					ChatServer.log("error : 알 수 없는 요청("+tokens[0]+")");
				}
				
				ChatServer.log("received : " + data);
			}
		} catch (SocketException e) {
			ChatServer.log("abnormal closed by client");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doQuit(PrintWriter pw) {
				
		String data = name + "님이 퇴장하셨습니다.";
		broadcast(data);
		
		removePrintWriter(pw);
	}

	private void removePrintWriter(PrintWriter pw) {
		
		pw.println("quit");
		pw.flush();
		list.remove(pw);
	}

	private void doMessage(String data) {
		
		broadcast(name +" : "+data);
	}

	private void doJoin(String name, PrintWriter pw) {
		this.name = name;
		
		String data = name + "님이 참여하셨습니다.";
		broadcast(data);
		
		// pw 저장
		addList(pw);
		
		// ack
		pw.println(data);
		pw.flush();
	}

	private void addList(PrintWriter pw) {
		synchronized (list) {
			list.add(pw);
		}
	}
	
	private void broadcast(String data)
	{
		synchronized (list) {

			for (PrintWriter pw : list) {
				PrintWriter printWriter = pw;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}
	
}
