package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPTimeClient {
	private static final String SERVER_IP = "192.168.1.3";
	private static final int SERVER_PORT = 7000;

	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;
		Scanner scn = null;
		try {
			scn = new Scanner(System.in);
			datagramSocket = new DatagramSocket();
			datagramSocket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			while (true) {
				System.out.print(">>");
				String message = scn.nextLine();
				byte[] data = message.getBytes("UTF-8");
				if ("quit".equals(message)) {
					break;
				}

				DatagramPacket sendPacket = new DatagramPacket(data, data.length,
						new InetSocketAddress(SERVER_IP, SERVER_PORT));
				datagramSocket.send(sendPacket);

				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPTimeServer.BUFFER_SIZE],
						UDPTimeServer.BUFFER_SIZE);
				datagramSocket.receive(receivePacket);
				String output = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				System.out.println("<<" + output);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (datagramSocket != null && datagramSocket.isClosed() == false)
				datagramSocket.close();
			if (scn != null) {
				scn.close();
			}
		}
	}

}
