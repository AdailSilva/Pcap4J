package com.adailsilva.pcap4j;

import java.io.EOFException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

public class Application002 {

	public static void main(String[] args) throws UnknownHostException, PcapNativeException {

		// Encontrar interface de rede:
		// Encontrar uma interface de rede na qual deseja capturar pacotes.
		InetAddress addr = InetAddress.getByName("192.168.0.8");
		PcapNetworkInterface nif = Pcaps.getDevByAddress(addr);

		// DEBUG:
		System.out.println("ADDR: " + addr);
		System.out.println("NIF: " + nif);

		// Alça Pcap aberta:
		// Em seguida, abrir um identificador pcap na interface de rede.
		// Um identificador pcap fornece APIs para capturar pacotes, enviar pacotes e
		// assim por diante.
		int snapLen = 65536;
		PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
		int timeout = 10;
		PcapHandle handle = nif.openLive(snapLen, mode, timeout);

		// DEBUG:
		System.out.println("HANDLE: " + handle);
		
		
		
		try {
			
			Packet packetPURO = handle.getNextPacket();
			
			System.out.println("packetPURO" + packetPURO);
			
		} catch (Exception e) {
			System.out.println("Exceção GERAL.");
		}

		// Captura de Pacotes:
		// Pegar um pacote usando o identificador pcap.
		try {
			Packet packet = handle.getNextPacketEx();

			// DEBUG:
			System.out.println("PACOTE: " + packet);
			
			
			// Obter informações sobre o pacote:
			// O pacote capturado consiste em cabeçalhos e cargas úteis de alguns
			// protocolos, como Ethernet, IPv4 e TCP.
			// A API de pacotes do Pcap4J permite que você obtenha informações dos
			// cabeçalhos de protocolo.
			IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
			Inet4Address srcAddr = ipV4Packet.getHeader().getSrcAddr();

			// DEBUG:
			System.out.println("Source ADDR [srcAddr]: " + srcAddr);

		} catch (EOFException e) {
			System.out.println("Exceção: EOFException.");
			e.printStackTrace();
		} catch (PcapNativeException e) {
			System.out.println("Exceção: PcapNativeException.");
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println("Exceção: TimeoutException.");
			e.printStackTrace();
		} catch (NotOpenException e) {
			System.out.println("Exceção: NotOpenException.");
			e.printStackTrace();
		}
		handle.close();

	}
}
