package com.heanbian.block.util;

import java.util.SplittableRandom;

public final class BlockUtils {

	public static int randomInt(final int bound) {
		return new SplittableRandom().nextInt(bound);
	}

	public static long randomLong(final int bound) {
		return new SplittableRandom().nextLong(bound);
	}

	public static String remove(String str) {
		if (str == null || str.isBlank()) {
			return str;
		}
		int sz = str.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}
		return new String(chs, 0, count);
	}

}
