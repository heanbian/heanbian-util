package com.heanbian.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class AddrUtils {

	public static boolean isValid(String ip) {
		return isValidIPv4(ip) || isValidIPv6(ip);
	}

	public static boolean isValidIPv4(String ip) {
		try {
			InetAddress addr = InetAddress.getByName(ip);
			return addr instanceof Inet4Address;
		} catch (UnknownHostException e) {
			return false;
		}
	}

	public static boolean isValidIPv6(String ip) {
		try {
			InetAddress addr = InetAddress.getByName(ip);
			return addr instanceof Inet6Address;
		} catch (UnknownHostException e) {
			return false;
		}
	}

	public static boolean isRange(String segment) {
		if (segment.contains("/")) {
			String[] parts = segment.trim().split("/", -1);
			if (parts.length != 2) {
				return false;
			}

			String ip = parts[0].trim();
			int mask = Integer.parseInt(parts[1].trim());
			if (isValidIPv4(ip)) {
				return mask >= 0 && mask <= 32;
			}
			if (isValidIPv6(ip)) {
				return mask >= 0 && mask <= 128;
			}
		}
		return false;
	}

}