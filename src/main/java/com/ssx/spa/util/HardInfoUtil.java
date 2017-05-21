package com.ssx.spa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HardInfoUtil {
    public static String getMacAddress() {
        String mac = "";
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("cat /sys/class/net/eth0/address").getInputStream()));
            while (true) {
                String line = bReader.readLine();
                if (line == null) {
                    break;
                }
                mac = line.trim();
            }
        } catch (IOException e) {
        }
        return mac;
    }
}
