package com.heanbian.util;

import java.util.Locale;

public final class OS {

    private static final String OS_NAME =
            System.getProperty("os.name", "").toLowerCase(Locale.ROOT);

    private OS() {
    }

    public static boolean isMac() {
        return OS_NAME.contains("mac");
    }

    public static boolean isLinux() {
        return OS_NAME.contains("nix")
                || OS_NAME.contains("nux")
                || OS_NAME.contains("aix");
    }

    public static boolean isWindows() {
        return OS_NAME.contains("win");
    }
}
