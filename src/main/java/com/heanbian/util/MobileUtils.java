package com.heanbian.util;

import java.util.regex.Pattern;

public final class MobileUtils {

	private static final String PHONE_NUMBER_REGEX = "^(\\+86)?1[3-9]\\d{9}$";
	private static final Pattern PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

	public static boolean isValid(String mobile) {
		if (mobile == null) {
			return false;
		}
		return PATTERN.matcher(mobile).matches();
	}

}