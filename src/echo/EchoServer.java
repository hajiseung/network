package echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private static final int PORT = 7000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩(Binding)
			serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT));
			log("server start...[port:" + PORT + "]");
			while (true) {
				// 3. Accept
				// : 클라이언트의 연결요청을 기다린다.
				Socket socket = serverSocket.accept(); // blocking(잠시 멈춘다.)
				Thread thread = new EchoServerReceiveThread(socket);
				thread.start();
			}

			/*
			 * InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress)
			 * socket.getRemoteSocketAddress(); String remoteHostAddress =
			 * inetRemoteSocketAddress.getAddress().getHostAddress(); int remotePort =
			 * inetRemoteSocketAddress.getPort();
			 * 
			 * log("Connected by client[" + remoteHostAddress + ":" + remotePort + "]");
			 * 
			 * // 데이터 통신을 위한 처리 try { // 4. IOStream 생성(받아오기)
			 * 
			 * BufferedReader br = new BufferedReader(new
			 * InputStreamReader(socket.getInputStream(), "UTF-8"));
			 * 
			 * PrintWriter pw = new PrintWriter(new
			 * OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			 * 
			 * while (true) { // 5. 데이터 읽기 String data = br.readLine(); if (data == null) {
			 * log("Closed by client"); break; } log("Received:" + data); // 6. 데이터 쓰기
			 * pw.println(data); }
			 * 
			 * } catch (SocketException e) { // e.printStackTrace();
			 * System.out.println("[Server] Sudden closed"); } catch (IOException e) {
			 * e.printStackTrace(); } finally { try { if (socket != null &&
			 * socket.isClosed() == false) { socket.close(); } } catch (IOException e) {
			 * e.printStackTrace(); } }
			 */

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void log(String log) {
		System.out.println("[Server#" + Thread.currentThread().getId() + "] " + log);
	}
}
