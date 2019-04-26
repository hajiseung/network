package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KeyBoardTest {

	public static void main(String[] args) {
		// 기반 스트림 생성(표준입력, 키보드, System.in)

		// 보조2
		BufferedReader br = null;
		try {
			// 보조스트림1
			// byte|byte|byte -> char로 변환
			InputStreamReader isr = new InputStreamReader(System.in, "utf-8");

			// 보조스트림2
			// char1|char2|char3|\n => String(char1char2char3)로 변환
			br = new BufferedReader(isr);

			// read
			String line = null;
			while ((line = br.readLine()) != null) {
				if ("exit".equals(line)) {

					break;
				}
				System.out.println(">>" + line);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
