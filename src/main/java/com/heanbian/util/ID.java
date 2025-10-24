package com.heanbian.util;

public final class ID {
	
	private ID() {}

	public static String uuid() {
		return UUIDv7.generate().toString().replaceAll("-", "");
	}

	public static String nanoId() {
		return NanoID.fastId();
	}

	public static String nanoId(int size) {
		return NanoID.fastId(size);
	}

}