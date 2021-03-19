package com.heanbian.block.util;

public final class BlockUtils {

	private BlockUtils() {
	}

	public static String remove(String str) {
		StringBuffer buf = new StringBuffer();
		char[] ch = str.toCharArray();
		for (int i = 0, len = ch.length; i < len; i++) {
			if (Character.isSpaceChar(ch[i])) {
				continue;
			}
			buf.append(ch[i]);
		}
		return buf.toString();
	}

}
