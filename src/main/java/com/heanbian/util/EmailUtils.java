package com.heanbian.util;

import java.net.IDN;
import java.util.Locale;
import java.util.regex.Pattern;

public final class EmailUtils {

    private static final Pattern LOCAL = Pattern.compile(
            "^[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*$"
    );

    private static final Pattern DOMAIN_LABEL = Pattern.compile(
            "^[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?$"
    );

    private EmailUtils() {
    }

    public static boolean isValid(String email) {
        String value = normalize(email);
        if (value == null || value.length() > 254) {
            return false;
        }

        int firstAt = value.indexOf('@');
        int lastAt = value.lastIndexOf('@');
        if (firstAt <= 0 || firstAt != lastAt || firstAt == value.length() - 1) {
            return false;
        }

        String localPart = value.substring(0, firstAt);
        String domainPart = value.substring(firstAt + 1);

        if (localPart.length() > 64 || !LOCAL.matcher(localPart).matches()) {
            return false;
        }

        String asciiDomain = toAsciiDomain(domainPart);
        return asciiDomain != null && isValidDomain(asciiDomain);
    }

    private static boolean isValidDomain(String domain) {
        if (domain.length() > 253) {
            return false;
        }

        String[] labels = domain.split("\\.");
        if (labels.length < 2) {
            return false;
        }

        for (String label : labels) {
            if (!DOMAIN_LABEL.matcher(label).matches()) {
                return false;
            }
        }

        String tld = labels[labels.length - 1];
        return tld.length() >= 2 && !isNumeric(tld);
    }

    private static String toAsciiDomain(String domain) {
        try {
            String ascii = IDN.toASCII(domain, IDN.USE_STD3_ASCII_RULES);
            if (ascii.isEmpty()) {
                return null;
            }
            return ascii.toLowerCase(Locale.ROOT);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static boolean isNumeric(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return !value.isEmpty();
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
