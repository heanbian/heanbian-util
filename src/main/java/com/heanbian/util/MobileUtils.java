package com.heanbian.util;

import java.util.regex.Pattern;

public final class MobileUtils {

    private static final Pattern PATTERN = Pattern.compile("^(?:\\+?86)?1[3-9]\\d{9}$");

    private MobileUtils() {
    }

    public static boolean isValid(String mobile) {
        if (mobile == null) {
            return false;
        }
        String value = mobile.trim();
        return !value.isEmpty() && PATTERN.matcher(value).matches();
    }
}
