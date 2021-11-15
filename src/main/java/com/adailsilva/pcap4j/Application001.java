package com.adailsilva.pcap4j;

import java.io.IOException;

import com.sun.jna.Platform;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapStat;
import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

public class Application001 {

	static PcapNetworkInterface getNetworkDevice() {
		PcapNetworkInterface device = null;
		try {
			device = new NifSelector().selectNetworkInterface();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return device;
	}

	public static void main(String[] args) throws PcapNativeException, NotOpenException {

		PcapNetworkInterface device = getNetworkDevice();
		System.out.println("Você escolheu: " + device);
		
		if (device == null) {
			System.out.println("Nenhum dispositivo escolhido.");
			System.exit(1);
		}

		// Abra o dispositivo e pegue um handle:
		int snapshotLength = 65536; // em bytes.
		int readTimeout = 50; // em milissegundos.
		PcapHandle handle = device.openLive(snapshotLength, PromiscuousMode.PROMISCUOUS, readTimeout);
		PcapDumper dumper = handle.dumpOpen("out.pcap");
		
		// Defina um filtro para ouvir apenas pacotes tcp na porta 80 (HTTP)
		//String filter = "tcp port 80";
		String filter = "udp port 1700";
		handle.setFilter(filter, BpfCompileMode.OPTIMIZE);

		// Crie um ouvinte que defina o que fazer com os pacotes recebidos:
		PacketListener listener = new PacketListener() {
			@Override
			public void gotPacket(Packet packet) {
				// Imprimir informações do pacote na tela:
				//System.out.println(handle.getTimestamp());
				System.out.println(packet);
				
				// Despejar pacotes para arquivo:
				try {
					dumper.dump(packet, handle.getTimestamp());
					
				} catch (NotOpenException e) {
					e.printStackTrace();
				}
			}
		};

		// Diga ao identificador para fazer um loop usando o listener que criamos:
		try {
			int maxPackets = -1; // "-1" Infinito.
			handle.loop(maxPackets, listener);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Imprimir estatísticas de handle:
		PcapStat stats = handle.getStats();
		System.out.println("Pacotes recebidos: " + stats.getNumPacketsReceived());
		System.out.println("Pacotes descartados: " + stats.getNumPacketsDropped());
		System.out.println("Pacotes descartados pela interface: " + stats.getNumPacketsDroppedByIf());
		
		// Suportado apenas por WinPcap:
		if (Platform.isWindows()) {
			System.out.println("Pacotes capturados: " + stats.getNumPacketsCaptured());
		}

		// Limpeza quando concluída:
		dumper.close();
		handle.close();
	}
}
