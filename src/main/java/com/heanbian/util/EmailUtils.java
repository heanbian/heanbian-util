package com.heanbian.util;

import java.util.regex.Pattern;

public final class EmailUtils {

	private static final Pattern LOCAL_PART_PATTERN = Pattern
			.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*$");

	private static final Pattern DOMAIN_PATTERN = Pattern
			.compile("^(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$");

	public static boolean isValid(String email) {
		if (email == null || email.isEmpty()) {
			return false;
		}

		int atIndex = email.lastIndexOf('@');
		if (atIndex <= 0 || atIndex == email.length() - 1)
			return false;
		if (email.length() > 254) {
			return false;
		}

		String localPart = email.substring(0, atIndex);
		String domain = email.substring(atIndex + 1);

		if (localPart.length() > 64 || !LOCAL_PART_PATTERN.matcher(localPart).matches()) {
			return false;
		}

		return isValidDomain(domain);
	}

	private static boolean isValidDomain(String domain) {
		if (domain.startsWith("xn--")) {
			try {
				domain = java.net.IDN.toUnicode(domain);
			} catch (Exception e) {
				return false;
			}
		}

		if (!DOMAIN_PATTERN.matcher(domain).matches()) {
			return false;
		}

		String tld = domain.substring(domain.lastIndexOf('.') + 1);
		return tld.length() >= 2;// 顶级域名至少2个字母
	}

}
