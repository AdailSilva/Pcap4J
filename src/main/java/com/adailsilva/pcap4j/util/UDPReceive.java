package com.adailsilva.pcap4j.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPReceive {
	private static DatagramSocket dsocket;

	public static void main(String args[]) {
		try {
			int port = 1700;

			dsocket = new DatagramSocket(port);

			// Create a buffer to read datagrams into. If a
			// packet is larger than this buffer, the
			// excess will simply be discarded!
			byte[] buffer = new byte[2048];

			// Create a packet to receive data into the buffer
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			// Now loop forever, waiting to receive packets and printing them.
			while (true) {
				// Wait to receive a datagram
				dsocket.receive(packet);

				// Convert the contents to a string, and display them
				String msg = new String(buffer, 0, packet.getLength());
				System.out.println(packet.getAddress().getHostName() + ": " + msg);

				// Reset the length of the packet before reusing it.
				packet.setLength(buffer.length);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
