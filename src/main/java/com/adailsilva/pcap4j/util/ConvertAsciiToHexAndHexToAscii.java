package com.adailsilva.pcap4j.util;

import java.math.BigInteger;

public class ConvertAsciiToHexAndHexToAscii {

	static final String original = "{\"time\":\"2020-08-08T18:33:35Z\",\"rxpk\":[{\"tmst\":3183774876,\"time\":\"2020-08-08T18:33:35.465725Z\",\"chan\":3,\"rfch\":0,\"freq\":917.400000,\"stat\":1,\"modu\":\"LORA\",\"datr\":\"SF8BW125\",\"codr\":\"4/5\",\"lsnr\":10.8,\"rssi\":-31,\"size\":23,\"data\":\"ACgMAtB+1bNwdfXpPA05TDRtXczlyzk=\"}]}";

	public static void main(String... args) {
		System.out.println("Original: " + original);
		String hex = toHexString(original);
		System.out.println("ASCII to Hex: " + hex);

		String ascii = fromHexString(hex);
		System.out.println("Hex to ASCII: " + ascii);
	}

	public static String toHexString(String input) {
		return String.format("%x", new BigInteger(1, input.getBytes()));
	}

	public static String fromHexString(String hex) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < hex.length(); i += 2) {
			str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
		}
		return str.toString();
	}

}
