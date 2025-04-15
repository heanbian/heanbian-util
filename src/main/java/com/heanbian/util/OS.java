package com.heanbian.util;

public final class OS {

	public static boolean isMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}

	public static boolean isLinux() {
		var osName = System.getProperty("os.name").toLowerCase();
		return osName.contains("nix") || osName.contains("nux") || osName.contains("aix");
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}

}
