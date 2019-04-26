package io;

import java.io.File;
import java.util.Scanner;

public class PhoneList02 {

	public static void main(String[] args) {
//		File file = ;
		Scanner scn = null;
		try {
			scn = new Scanner(new File("phone.txt"));
			while (scn.hasNextLine()) {
				String name = scn.next();
				String num1 = scn.next();
				String num2 = scn.next();
				String num3 = scn.next();

				System.out.println(name + ":" + num1 + "-" + num2 + "-" + num3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scn != null) {
				scn.close();
			}
		}
	}

}
