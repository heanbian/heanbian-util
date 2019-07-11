package com.heanbian.block.core.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class HDateUtils {

	public static String now(final String pattern) {
		Objects.requireNonNull(pattern, "pattern must not be null");
		return ZonedDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

}
