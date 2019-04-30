package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;

public class RequestHandler extends Thread {
	private static final String DOCUMENT_ROOT = "./webapp";
	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// get IOStream

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":"
					+ inetSocketAddress.getPort());

			// get IOStream
			OutputStream outputStream = socket.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

			String request = null;

			while (true) {
				String line = br.readLine();
				// 브라우저가 연결을 끊었을때
				if (line == null) {
					System.out.println("연결 종료");
					break;
				}

				// Requset Header만 읽음
				if ("".equals(line)) {
					break;
				}

				// Header의 첫번째 라인만 처리
				if (request == null) {
					request = line;
				}

			}
//			consoleLog("received:" + request);
			String[] tokens = request.split(" ");
			if ("GET".equals(tokens[0])) {
				consoleLog("request:" + tokens[1]);
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			} else {
				// POST, PUT, DELETE, HEAD, CONNECT와 같은 Method는 무시
				// 응답 예시
				/*
				 * HTTP/1.1 404 File not Found\r\n Content-Type:text/html; charset="utf-8"\r\n
				 * \r\n HTML 에러 문서(./webapp/error/400.html)을 읽어서보낸다
				 */

				response400Error(outputStream, tokens[2]);
				consoleLog("Bad Request:" + tokens[1]);
			}
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
//			outputStream.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
//			outputStream.write("Content-Type:text/html; charset=utf-8\r\n".getBytes("UTF-8"));
//			outputStream.write("\r\n".getBytes());
//			outputStream.write("<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes("UTF-8"));

		} catch (Exception ex) {
			consoleLog("error:" + ex);
		} finally {
			// clean-up
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException ex) {
				consoleLog("error:" + ex);
			}
		}
	}

	public void responseStaticResource(OutputStream os, String url, String protocol) throws IOException {
		if ("/".equals(url)) {
			url = "/index.html";
		}
		// 아래와 동일한 코드
//		그러나 Exception으로 로직을 짜는건 아주 안좋은 것이다 e라는 객체가 생기기때문 (메모리 낭비)//if 문으로 충분히 할 수 있는건 if 로 하자
//		try {
//			FileInputStream fs = new FileInputStream(DOCUMENT_ROOT + url);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}

		File file = new File(DOCUMENT_ROOT + url);
		if (file.exists() == false) {
			// 응답 예시
			/*
			 * HTTP/1.1 404 File not Found\r\n Content-Type:text/html; charset="utf-8"\r\n
			 * \r\n HTML 에러 문서(./webapp/error/404.html)을 읽어서보낸다
			 */
			response404Error(os, protocol);
			return;
		}
		// nio(new IO)
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		// 응답
		os.write((protocol + "200 OK\r\n").getBytes("UTF-8"));
//		os.write("Content-Type:text/html; charset=utf-8\r\n".getBytes("UTF-8"));
		// css가 먹힌다 contentType때문에
		os.write(("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes("UTF-8"));
		os.write("\r\n".getBytes());
		os.write(body);
	}

	public void response400Error(OutputStream os, String protocol) throws IOException {
		responseError(os, protocol, "400");
	}

	public void response404Error(OutputStream os, String protocol) throws IOException {
		responseError(os, protocol, "404");
	}

	public void responseError(OutputStream os, String protocol, String errorNum) throws IOException {
		String url = "/error/" + errorNum + ".html";
		String status = null;
		File file = new File(DOCUMENT_ROOT + url);
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		if ("404".equals(errorNum)) {
			status = " 404 File Not Found";
		} else if ("400".equals(errorNum)) {
			status = " 400 Bad Request";
		}

		// 응답
		os.write((protocol + status + "\r\n").getBytes("UTF-8"));
		os.write(("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes("UTF-8"));
		os.write("\r\n".getBytes());
		os.write(body);
	}

	public void consoleLog(String message) {
		System.out.println("[RequestHandler#" + getId() + "] " + message);
	}
}
