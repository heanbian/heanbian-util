package com.heanbian.block.reactive.util;

public final class FileUtils {

	public static int base64ToInt(String base64) {
		String str = base64.indexOf(",") > 0 ? base64.split(",")[1] : base64;
		int index = str.indexOf("=");
		if (str.indexOf("=") > 0) {
			str = str.substring(0, index);
		}
		int len = str.length();
		return len - (len / 8) * 2;
	}
}
