package com.adailsilva.pcap4j.util;

import java.nio.charset.StandardCharsets;

public class ByteToHex {

    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02x", aByte));
            // upper case
            // result.append(String.format("%02X", aByte));
        }
        return result.toString();
    }

    public static void main(String[] args) {

        String input = "AdailSilva";
        System.out.println(hex(input.getBytes(StandardCharsets.UTF_8)));

    }
}
