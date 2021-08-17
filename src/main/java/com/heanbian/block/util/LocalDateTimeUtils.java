package com.heanbian.block.util;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

public final class LocalDateTimeUtils {

	public static String now(String pattern) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	public static LocalDateTime parse(String date, String pattern) {
		return LocalDateTime.parse(pattern, DateTimeFormatter.ofPattern(pattern));
	}

	public static List<LocalDate> betweenDays(final LocalDate start, final LocalDate end) {
		long len = ChronoUnit.DAYS.between(start, end);
		return Stream.iterate(start, d -> d.plusDays(1)).limit(len + 1).collect(toList());
	}

	public static List<LocalDateTime> betweenDays(final LocalDateTime start, final LocalDateTime end) {
		long len = ChronoUnit.DAYS.between(start, end);
		return Stream.iterate(start, d -> d.plusDays(1)).limit(len + 1).collect(toList());
	}

	private LocalDateTimeUtils() {
	}
}
