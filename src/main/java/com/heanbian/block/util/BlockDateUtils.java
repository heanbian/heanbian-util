package com.heanbian.block.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SplittableRandom;
import java.util.stream.Stream;

/**
 * 
 * @author heanbian
 *
 */
public final class BlockDateUtils {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";
	public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss";

	public static final String DEFAULT_FORMAT_DENSE = "yyyyMMddHHmmss";
	public static final String DEFAULT_FORMAT_DATE_DENSE = "yyyyMMdd";
	public static final String DEFAULT_FORMAT_TIME_DENSE = "HHmmss";

	public static String now(final String pattern) {
		Objects.requireNonNull(pattern, "pattern must not be null");
		return ZonedDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	public static String getDateTime() {
		return now(DEFAULT_FORMAT);
	}

	public static String getDate() {
		return now(DEFAULT_FORMAT_DATE);
	}

	public static String getDate(final int day) {
		return addDay(day).format(DateTimeFormatter.ofPattern(DEFAULT_FORMAT_DATE));
	}

	public static String getTime() {
		return now(DEFAULT_FORMAT_TIME);
	}

	public static long getDateTimeLong() {
		return Long.parseLong(now(DEFAULT_FORMAT_DENSE));
	}

	public static long getDateLong() {
		return Long.parseLong(now(DEFAULT_FORMAT_DATE_DENSE));
	}

	public static long getDateLong(final int day) {
		return Long.parseLong(addDay(day).format(DateTimeFormatter.ofPattern(DEFAULT_FORMAT_DATE_DENSE)));
	}

	public static long getRandomDateLong(final int bound) {
		SplittableRandom random = new SplittableRandom();
		int c = random.nextInt(bound);
		return getDateLong(-c);
	}

	public static String getRandomDate(final int bound) {
		SplittableRandom random = new SplittableRandom();
		int c = random.nextInt(bound);
		return getDate(-c);
	}

	public static long getTimeLong() {
		return Long.parseLong(now(DEFAULT_FORMAT_TIME_DENSE));
	}

	public static ZonedDateTime addDay(final long day) {
		return ZonedDateTime.now(ZoneId.systemDefault()).plusDays(day);
	}

	public static List<String> between(final String start, final String end) {
		List<String> dates = new ArrayList<>();
		LocalDate startDate = LocalDate.parse(start);
		LocalDate endDate = LocalDate.parse(end);

		long distance = ChronoUnit.DAYS.between(startDate, endDate);
		if (distance < 1) {
			return dates;
		}
		Stream.iterate(startDate, d -> {
			return d.plusDays(1);
		}).limit(distance + 1).forEach(f -> {
			dates.add(f.toString());
		});
		return dates;
	}
}
