package chat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientReceiveThread extends Thread {

	Scanner sc = new Scanner(System.in);
	private Socket socket;

	public ChatClientReceiveThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		PrintWriter pw = null;
		try {

			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			String name;
			
			do
			{
				System.out.print("닉네임을 입력하세요 : ");
				name = sc.nextLine();
				
				if(!name.isEmpty())
				{
					System.out.println("대화명을 한글자이상 입력하세요");
				}

			}while(!name.isEmpty());	
	
			name = "join:" + sc.nextLine();
			pw.println(name);
			
			while (true) {
				// 4. 쓰기
				String data = sc.nextLine();
				
				if(data.equals("quit"))
				{
					pw.println(data);
					pw.flush();
					break;
				}
				data = "message:"+data;
				
				pw.println(data);
				pw.flush();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
