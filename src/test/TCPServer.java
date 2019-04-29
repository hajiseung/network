package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩(Binding)
			// : Socket에 SocketAddress(IPAddress + Port)를 바인딩 한다.
//			InetAddress inetAddress = InetAddress.getLocalHost();
//			String localhost = inetAddress.getHostAddress();
//			serverSocket.bind(new InetSocketAddress(localhost, 5000));
			serverSocket.bind(new InetSocketAddress("0.0.0.0", 6000));

			// 3. Accept
			// : 클라이언트의 연결요청을 기다린다.
			Socket socket = serverSocket.accept(); // blocking(잠시 멈춘다.)

			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remotePort = inetRemoteSocketAddress.getPort();

			System.out.println("[Server] Connected by client[" + remoteHostAddress + ":" + remotePort + "]");

			try {
				// 4. IOStream 받아오기
				InputStream is = socket.getInputStream(); // 서버에서의 정보를 읽어옴
				OutputStream os = socket.getOutputStream(); // 정보를 서버에 적어서 보내줌

				while (true) {
					// 5. Data 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); // blocking
					if (readByteCount == -1) {
						// 클라이언트 정상 종료 한 경우
						// closed()메소드 호출을 통해서
						System.out.println("[Server] Closed by Client");
						break;
					}

					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("[Server] received : " + data);

					// 6. 데이터 쓰기
					os.write(data.getBytes("UTF-8"));
				}
			} catch (SocketException e) {
//				e.printStackTrace();
				System.out.println("[Server] Sudden closed");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null && socket.isClosed() == false) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

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

}
