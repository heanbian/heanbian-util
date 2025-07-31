package com.heanbian.util;

import java.util.zip.CRC32;

public final class HashUtils {

	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static String generateShortHash(String input) {
		CRC32 crc32 = new CRC32();
		crc32.update(input.getBytes());
		long checksum = crc32.getValue();
		return toBase62(checksum);
	}

	private static String toBase62(long value) {
		StringBuilder sb = new StringBuilder();
		do {
			int index = (int) (value % 62);
			sb.append(BASE62.charAt(index));
			value /= 62;
		} while (value > 0);
		return sb.reverse().toString();
	}

}
