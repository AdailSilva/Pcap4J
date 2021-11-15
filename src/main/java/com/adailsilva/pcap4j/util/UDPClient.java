package com.adailsilva.pcap4j.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

	public static void main(String[] args) throws Exception {
		
		int porta = 1700;
		String host = "127.0.0.1";
		
		DatagramSocket datagramSocket = new DatagramSocket();
		InetAddress inetAddress = InetAddress.getByName(host);
		
		// Preparando envio:
		String nome = "AdailSilva";
		byte[] bufferParaEnvio = String.valueOf(nome).getBytes();
				
		// Envio:
		DatagramPacket datagramPacketParaEnvio = new DatagramPacket(bufferParaEnvio, bufferParaEnvio.length, inetAddress, porta);
		datagramSocket.send(datagramPacketParaEnvio);
		
		// Preparando Recepção:
		byte[] bufferParaRecepcao = new byte[65536];
		
		// Recepção:
		DatagramPacket datagramPacketParaRecepcao = new DatagramPacket(bufferParaRecepcao, bufferParaRecepcao.length);
		datagramSocket.receive(datagramPacketParaRecepcao);		
		String stringRecebida = new String(datagramPacketParaRecepcao.getData(), 0, datagramPacketParaRecepcao.getLength());
		System.out.println("String Recebida: " + stringRecebida);
		
		datagramSocket.close();
		
	}
}
