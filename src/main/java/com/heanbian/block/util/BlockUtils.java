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
