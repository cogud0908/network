package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			
			System.out.println(hostname + " : " + hostAddress);

			// 
			byte[] address = inetAddress.getAddress();
			for (byte b : address) {
				System.out.print(b & 0x000000ff);
				System.out.print(".");
			}
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
	}
}
