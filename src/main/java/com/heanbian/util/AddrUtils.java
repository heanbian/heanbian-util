package com.heanbian.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class AddrUtils {

    private AddrUtils() {
    }

    public static boolean isValid(String ip) {
        String value = normalize(ip);
        return isValidIPv4(value) || isValidIPv6(value);
    }

    public static boolean isValidIPv4(String ip) {
        String value = normalize(ip);
        if (value == null || value.indexOf('.') < 0) {
            return false;
        }

        String[] parts = value.split("\\.", -1);
        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            if (part.isEmpty() || part.length() > 3) {
                return false;
            }
            for (int i = 0; i < part.length(); i++) {
                if (!Character.isDigit(part.charAt(i))) {
                    return false;
                }
            }
            if (part.length() > 1 && part.charAt(0) == '0') {
                return false;
            }

            int number = Integer.parseInt(part);
            if (number < 0 || number > 255) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidIPv6(String ip) {
        String value = normalize(ip);
        if (value == null || value.indexOf(':') < 0) {
            return false;
        }

        try {
            InetAddress address = InetAddress.getByName(value);
            return address instanceof Inet6Address;
        } catch (UnknownHostException ex) {
            return false;
        }
    }

    public static boolean isRange(String segment) {
        return isValidCidr(segment);
    }

    public static boolean isValidCidr(String segment) {
        String value = normalize(segment);
        if (value == null) {
            return false;
        }

        int slash = value.indexOf('/');
        if (slash <= 0 || slash != value.lastIndexOf('/')) {
            return false;
        }

        String ip = value.substring(0, slash).trim();
        String maskText = value.substring(slash + 1).trim();
        if (maskText.isEmpty()) {
            return false;
        }

        final int mask;
        try {
            mask = Integer.parseInt(maskText);
        } catch (NumberFormatException ex) {
            return false;
        }

        if (isValidIPv4(ip)) {
            return mask >= 0 && mask <= 32;
        }
        if (isValidIPv6(ip)) {
            return mask >= 0 && mask <= 128;
        }

        return false;
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
