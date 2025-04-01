package com.heanbian.util;

import java.util.regex.Pattern;

public final class AddrUtils {

	// IPv4 地址的正则表达式
	private static final String IPV4_REGEX = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

	// IPv6 地址的正则表达式
	private static final String IPV6_REGEX = "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$|"
			+ "^((([0-9A-Fa-f]{1,4}:){1,6}:)|(([0-9A-Fa-f]{1,4}:){1,5}:[0-9A-Fa-f]{1,4})|(([0-9A-Fa-f]{1,4}:){1,4}(:[0-9A-Fa-f]{1,4}){1,2})|(([0-9A-Fa-f]{1,4}:){1,3}(:[0-9A-Fa-f]{1,4}){1,3})|(([0-9A-Fa-f]{1,4}:){1,2}(:[0-9A-Fa-f]{1,4}){1,4})|(([0-9A-Fa-f]{1,4}:)(:[0-9A-Fa-f]{1,4}){1,5})|(:((:[0-9A-Fa-f]{1,4}){1,6})))(::)?([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?$|"
			+ "^::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])$|"
			+ "^([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])$";

	private static final Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

	private static final Pattern IPV6_PATTERN = Pattern.compile(IPV6_REGEX, Pattern.CASE_INSENSITIVE);

	public static boolean isValid(String input) {
		return isValidIPv4(input) || isValidIPv6(input);
	}

	private static boolean isValidIPv4(String input) {
		return IPV4_PATTERN.matcher(input).matches();
	}

	private static boolean isValidIPv6(String input) {
		return IPV6_PATTERN.matcher(input).matches();
	}

}