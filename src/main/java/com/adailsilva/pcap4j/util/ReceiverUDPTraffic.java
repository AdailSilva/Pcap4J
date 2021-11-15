package com.adailsilva.pcap4j.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ReceiverUDPTraffic {

	private DatagramSocket serverSocket;

	public static void main(String[] args) {
		// int port = args.length == 0 ? 1700 : Integer.parseInt(args[0]);
		int port = 1700;
		new ReceiverUDPTraffic().run(port);
	}

	public void run(int port) {

		String charset = "UTF-8";

		try {
			serverSocket = new DatagramSocket(port);
			byte[] receiveData = new byte[1024];
			// String sendString = "AdailSilva";
			// byte[] sendData = sendString.getBytes("UTF-8");

			System.out.printf("Ouvindo tráfego UDP: %s:%d%n", InetAddress.getLocalHost().getHostAddress(), port);
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			while (true) {
				serverSocket.receive(receivePacket);

				String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
				byte[] response = sentence.getBytes(charset);
				String str = new String(response, StandardCharsets.UTF_8);

				String filtro = byteToHexString(str.getBytes(StandardCharsets.UTF_8));

				int start = filtro.indexOf("7B2274696D65223A");
				// int end = filtro.lastIndexOf("");

				try {

					// String responseFiltrada = filtro.substring(start, end + 1);
					String responseFiltrada = filtro.substring(start);

					if (responseFiltrada.contains("227278706B22")) {

						// System.out.println("JSON do tipo: \"rxpk\".");

					} else if (responseFiltrada.contains("227374617422")) {

						// System.out.println("JSON do tipo: \"stat\".");
					}

					// System.out.println("[ IP de Origem: " +
					// receivePacket.getAddress().getHostName()
					// + " | Payload em HEXADECIMAL: " + responseFiltrada + " ]");

					String asciiString = fromHexAscii(responseFiltrada);

					// System.out.println("IP de Origem: " +
					// receivePacket.getAddress().getHostName()
					// + " | Payload em ASCII: " + asciiString);

					System.out.println("IP: " + receivePacket.getAddress().getHostName() + " " + asciiString);

					System.out.println("");

				} catch (StringIndexOutOfBoundsException e) {
					// System.out.println("Informação não necessária, pacote descartado | "
					// + "Índice inicial: " + start + " | Índice final: " + end + ".");

					// System.out.println("Informação não necessária, pacote descartado | " +
					// "Índice inicial: " + start
					// + " | Índice final: " + "RETIRADO" + ".");
					System.out.println("");
				}

				// Agora envia o pacote de confirmação de volta ao remetente.
				// DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
				// receivePacket.getAddress(),receivePacket.getPort());
				// serverSocket.send(sendPacket);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		// deve fechar serverSocket no bloco finally.
	}

	public static String byteToHexString(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte aByte : bytes) {
			result.append(String.format("%02x", aByte));
		}
		// Upper case:
		return result.toString().toUpperCase();
	}

	public static String fromHexAscii(String hex) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < hex.length(); i += 2) {
			str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
		}
		return str.toString();
	}
}