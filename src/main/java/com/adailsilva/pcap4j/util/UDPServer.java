package com.adailsilva.pcap4j.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {

	public static void main(String[] args) throws Exception {
		
		int porta = 1700;
		String host = "127.0.0.1";
		
		// Conexão:
		DatagramSocket datagramSocket = new DatagramSocket(porta);
		
		// Recepção:
		byte[] bufferParaRecepcao = new byte[65536];
		DatagramPacket datagramPacketParaRecepcao = new DatagramPacket(bufferParaRecepcao, bufferParaRecepcao.length);
		datagramSocket.receive(datagramPacketParaRecepcao);
		
		String stringRecebida = new String(datagramPacketParaRecepcao.getData(), 0, datagramPacketParaRecepcao.getLength());
		
		// Envia de volta o que recebe:
		byte[] bufferParaEnvio = String.valueOf(stringRecebida).getBytes();		
		InetAddress inetAddress = InetAddress.getByName(host);
		DatagramPacket datagramPacketParaEnvio = new DatagramPacket(bufferParaEnvio, bufferParaEnvio.length, inetAddress, datagramPacketParaRecepcao.getPort());
		datagramSocket.send(datagramPacketParaEnvio);
		
		datagramSocket.close();
	}

}
