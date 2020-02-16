package com.heanbian.block.util;

public final class BlockUtils {

	public static String remove(String rawString) {
		StringBuffer buf = new StringBuffer();
		char[] ch = rawString.toCharArray();
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

}
