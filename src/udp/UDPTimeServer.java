package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UDPTimeServer {
	private static final int PORT = 7000;
	public static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;
		try {
			datagramSocket = new DatagramSocket(PORT);
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				datagramSocket.receive(receivePacket);
				
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();

				String message = new String(data, 0, length, "UTF-8");
				System.out.println("[Server] Received:" + message);

				if ("".equals(message)) {
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss a");
					String Time = simpleDateFormat.format(calendar.getTime());
					System.out.println("[Server] Send:" + Time);
					byte[] timeData = Time.getBytes("UTF-8");

					DatagramPacket sendPacket = new DatagramPacket(timeData, timeData.length,
							receivePacket.getAddress(), receivePacket.getPort());
					datagramSocket.send(sendPacket);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (datagramSocket != null && datagramSocket.isClosed() == false)
				datagramSocket.close();
		}

	}

}
