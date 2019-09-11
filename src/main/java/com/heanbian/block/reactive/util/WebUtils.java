package com.heanbian.block.reactive.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public final class WebUtils {

	/**
	 * 除去字符串中的空白字符
	 * 
	 * @param raw 原字符串
	 * @return String
	 */
	public static String remove(String raw) {
		StringBuffer buf = new StringBuffer();
		char[] ch = raw.toCharArray();
		for (int i = 0, len = ch.length; i < len; i++) {
			if (Character.isSpaceChar(ch[i])) {
				continue;
			}
			buf.append(ch[i]);
		}
		return buf.toString();
	}

	public static String deepToString(Object[] a) {
		if (a == null) {
			return "";
		}
		int max = a.length - 1;
		if (max == -1) {
			return "";
		}
		StringBuilder b = new StringBuilder();
		for (int i = 0;; i++) {
			b.append(String.valueOf(a[i]));
			if (i == max) {
				return b.toString();
			}
			b.append(",");
		}
	}

	public static String getRealIP() {
		String ip = "";
		Enumeration<NetworkInterface> enumeration;
		try {
			enumeration = NetworkInterface.getNetworkInterfaces();
			loop: while (enumeration.hasMoreElements()) {
				NetworkInterface networkInterface = enumeration.nextElement();
				Enumeration<InetAddress> inet = networkInterface.getInetAddresses();
				while (inet.hasMoreElements()) {
					InetAddress address = inet.nextElement();
					if (address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
						ip = address.getHostAddress();
						break loop;
					}
				}
			}
		} catch (SocketException e) {
			ip = "127.0.0.1";
		}
		return ip;
	}

}
