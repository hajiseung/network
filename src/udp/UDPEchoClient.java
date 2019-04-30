package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient {
	private static final String SERVER_IP = "192.168.1.3";
	private static final int SERVER_PORT = 7000;

	public static void main(String[] args) {
		DatagramSocket socket = null;
		Scanner scn = null;
		try {
			// 1. Scanner 생성(표준 입력)
			scn = new Scanner(System.in);

			// 2.소켓 생성
			socket = new DatagramSocket();

			// 3.서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("Server Connected");

			while (true) {
				// 4. 키보드 입력 받기
				System.out.print(">>");
				String line = scn.nextLine();
				if ("quit".equals(line)) {
					break;
				}
				byte[] sendData = line.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(SERVER_IP, SERVER_PORT));
				socket.send(sendPacket);

				// 5.데이터 읽기
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE],
						UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket);
				String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				// 8.콘솔 출력
				System.out.println("<<" + message);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (scn != null) {
				scn.close();
			}
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
	}

	public static void log(String log) {
		System.out.println("[Client] " + log);
	}

}
