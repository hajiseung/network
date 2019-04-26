package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Localhost {

	public static void main(String[] args) {
		try {

			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostName = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			System.out.println("HostName = " + hostName + "\nHostAddress = " + hostAddress);

			InetAddress[] inetaddresses = InetAddress.getAllByName(hostName);
//			System.out.println(Arrays.toString(Inetaddresses));
			for (InetAddress tmp : inetaddresses) {
				System.out.println(tmp.getHostAddress());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
