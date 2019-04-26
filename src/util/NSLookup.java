package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class NSLookup {

	public static void main(String[] args) {
//		Scanner scn = new Scanner(System.in);
//		String hostname = "";
//		while (true) {
//			System.out.print(">");
//			hostname = scn.nextLine();
//			if (hostname.equals("exit")) {
//				break;
//			} else {
//				InetAddress[] IpArry = InetAddress.getAllByName(hostname);
//				for (InetAddress tmp : IpArry) {
//					System.out.println(hostname + " : " + tmp.getHostAddress());
//				}
//			}
//		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {
				System.out.print(">");
				String hostname = br.readLine();
				if (hostname.equals("exit")) {
					break;
				} else {
					InetAddress[] data = InetAddress.getAllByName(hostname);
					for (InetAddress tmp : data) {
						System.out.println(hostname + " : " + tmp.getHostAddress());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
