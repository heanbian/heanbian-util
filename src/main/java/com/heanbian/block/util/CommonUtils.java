package com.heanbian.block.util;

public final class CommonUtils {

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

	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}

	public static boolean isBlank(final CharSequence cs) {
		final int strLen = length(cs);
		if (strLen == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static int length(final CharSequence cs) {
		return cs == null ? 0 : cs.length();
	}

	private CommonUtils() {
	}
}
