package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {

		// InetSocketAddress <= socketAddress (ip Address + port)

		Scanner sc = new Scanner(System.in);
		String line;

		while (true) {
			System.out.print(">> ");

			line = sc.nextLine();
			if (line.equals("exit"))
			{
				System.out.println("종료");
				break;
			}
			try {
				
				InetAddress[] inetAddress = InetAddress.getAllByName(line);
								
				for(int i = 0; i < inetAddress.length; i++)
				{
					System.out.print(line + " : ");
					System.out.println(inetAddress[i].getHostAddress());
				}

			} catch (UnknownHostException e) {
				System.out.println("도메인을 정확히 입력해 주십시오");
			}
		}
	}
}
