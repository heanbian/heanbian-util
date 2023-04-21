package com.heanbian.block.util;

public final class DesensitizationUtils {

	private static final String PHONE_REGEX = "(86){0,1}1\\d{10}";

	public static String de(String value) {
		if (HeanbianUtils.isBlank(value)) {
			return "";
		}

		// 邮箱
		if (value.contains("@")) {
			return value.replaceAll("(\\w+)\\w{6}@(\\w+)", "$1******@$2");
		}

		// 手机号
		if (value.matches(PHONE_REGEX)) {
			return value.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		}

		return value;
	}
}
