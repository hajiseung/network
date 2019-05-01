package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "192.168.1.3";
	private static final int SERVER_PORT = 7000;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scn = null;
		try {
			// 1. Scanner 생성(표준 입력)
			scn = new Scanner(System.in);

			// 2.소켓 생성
			socket = new Socket();

			// 3.서버 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			log("Server Connected");

			// 4.IOStream 생성(받아오기)
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			while (true) {
				// 5. 키보드 입력 받기
				System.out.print(">>");
				String line = scn.nextLine();
				if ("quit".contentEquals(line)) {
					break;
				}
				// 6.데이터 쓰기
				pw.println(line);

				// 7.데이터 읽기
				String data = br.readLine();
				if (data == null) {
					log("Closed by Server");
					break;
				}

				// 8.콘솔 출력
				System.out.println("<<" + data);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (scn != null) {
					scn.close();
				}
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void log(String log) {
		System.out.println("[Client] " + log);
	}

}
