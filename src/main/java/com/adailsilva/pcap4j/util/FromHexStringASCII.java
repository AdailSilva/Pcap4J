package com.adailsilva.pcap4j.util;

public class FromHexStringASCII {

	public static String fromHexString(String hex) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < hex.length(); i += 2) {
			str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
		}
		return str.toString();
	}

	public static void main(String[] args) {
		
		String hexadecimal = "7b 22 74 69 6d 65 22 3a 22 32 30 32 30 2d 30 38 2d 31 30 54 30 32 3a 33 30 3a 32 30 5a 22 2c 22 72 78 70 6b 22 3a 5b 7b 22 74 6d 73 74 22 3a 32 32 32 32 37 37 32 32 39 32 2c 22 74 69 6d 65 22 3a 22 32 30 32 30 2d 30 38 2d 31 30 54 30 32 3a 33 30 3a 31 39 2e 31 30 30 34 31 37 5a 22 2c 22 63 68 61 6e 22 3a 33 2c 22 72 66 63 68 22 3a 30 2c 22 66 72 65 71 22 3a 39 31 37 2e 34 30 30 30 30 30 2c 22 73 74 61 74 22 3a 31 2c 22 6d 6f 64 75 22 3a 22 4c 4f 52 41 22 2c 22 64 61 74 72 22 3a 22 53 46 38 42 57 31 32 35 22 2c 22 63 6f 64 72 22 3a 22 34 2f 35 22 2c 22 6c 73 6e 72 22 3a 31 30 2e 38 2c 22 72 73 73 69 22 3a 2d 34 35 2c 22 73 69 7a 65 22 3a 32 33 2c 22 64 61 74 61 22 3a 22 41 43 67 4d 41 74 42 2b 31 62 4e 77 64 66 58 70 50 41 30 35 54 44 51 59 67 69 74 61 63 67 63 3d 22 7d 5d 7d";
		
		String hexadecimalSemEspaco = hexadecimal.replace(" ", "");
		
		String jsonResult = fromHexString(hexadecimalSemEspaco);
		
		System.out.println(jsonResult);

	}
}
