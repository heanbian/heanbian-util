package com.heanbian.util;

public final class DeUtils {

	private static final String PHONE_REGEX = "(86){0,1}1\\d{10}";

	public static String de(String value) {
		if (value == null || value.length() == 0) {
			return "";
		}

		// 邮箱
		if (value.contains("@")) {
			return value.replaceAll("(\\w+)\\w{5}@(\\w+)", "$1*****@$2");
		}

		// 手机号
		if (value.matches(PHONE_REGEX)) {
			return value.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		}

		// 身份证
		if (value.length() == 18) {
			return left(value, 6).concat("********").concat(right(value, 4));
		}
		return value;
	}

	public static String left(final String str, final int len) {
		if (str == null) {
			return "";
		}
		if (len < 0) {
			return "";
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	public static String right(final String str, final int len) {
		if (str == null) {
			return "";
		}
		if (len < 0) {
			return "";
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

}
