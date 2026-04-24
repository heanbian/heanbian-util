package com.heanbian.util;

public final class DeUtils {

    private DeUtils() {
    }

    public static String de(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        String text = value.trim();

        if (EmailUtils.isValid(text)) {
            return maskEmail(text);
        }
        if (MobileUtils.isValid(text)) {
            return maskMobile(text);
        }
        if (IdnoUtils.validated(text)) {
            return maskIdno(text);
        }

        return text;
    }

    public static String maskEmail(String email) {
        if (!EmailUtils.isValid(email)) {
            return email;
        }

        int atIndex = email.indexOf('@');
        String local = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        if (local.length() == 1) {
            return "*" + domain;
        }
        if (local.length() == 2) {
            return local.substring(0, 1) + "*" + domain;
        }

        int starCount = Math.max(1, Math.min(5, local.length() - 2));
        return local.substring(0, 1)
                + "*".repeat(starCount)
                + local.substring(local.length() - 1)
                + domain;
    }

    public static String maskMobile(String mobile) {
        if (mobile == null) {
            return null;
        }

        String text = mobile.trim();
        if (!MobileUtils.isValid(text)) {
            return text;
        }

        String prefix = "";
        String digits = text;

        if (digits.startsWith("+86")) {
            prefix = "+86";
            digits = digits.substring(3);
        } else if (digits.startsWith("86") && digits.length() == 13) {
            prefix = "86";
            digits = digits.substring(2);
        }

        return prefix + left(digits, 3) + "****" + right(digits, 4);
    }

    public static String maskIdno(String idno) {
        if (!IdnoUtils.validated(idno)) {
            return idno;
        }
        return left(idno, 6) + "********" + right(idno, 4);
    }

    public static String left(String str, int len) {
        if (str == null || len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    public static String right(String str, int len) {
        if (str == null || len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }
}
