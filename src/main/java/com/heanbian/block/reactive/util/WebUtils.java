package com.heanbian.block.reactive.util;

public final class WebUtils {

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
