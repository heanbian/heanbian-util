package com.heanbian.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MobileUtils {

	private static final String PHONE_NUMBER_PATTERN = "^(\\+86)?1[3-9]\\d{9}$";
	private static final Pattern PATTERN = Pattern.compile(PHONE_NUMBER_PATTERN);

	public static boolean isValid(String mobile) {
		if (mobile == null) {
			return false;
		}
		Matcher matcher = PATTERN.matcher(mobile);
		return matcher.matches();
	}

}