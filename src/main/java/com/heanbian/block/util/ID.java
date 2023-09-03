package com.heanbian.block.util;

import java.util.UUID;

public final class ID {

	public static String id() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
