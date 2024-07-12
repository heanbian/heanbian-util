package com.heanbian.block.util;

public final class HeanbianUtils {
	
	private HeanbianUtils() {}

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

	public static String left(final String str, final int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return "";
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}
	
	public static String removeStart(final String str, final String remove) {
        if (isBlank(str) || isBlank(remove)) {
            return str;
        }
        if (str.startsWith(remove)) {
            return str.substring(remove.length());
        }
        return str;
    }
}
