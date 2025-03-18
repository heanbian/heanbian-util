package com.heanbian.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class IdnoUtils {

	private static final List<String> PROVINCES = List.of("11", "12", "13", "14", "15", "21", "22", "23", "31", "32",
			"33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
			"63", "64", "65", "71", "81", "82", "91");

	private static final char[] COEF = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
	private static final int[] CODES = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	private static final String[] ZODIACS = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

	public static String getSex(String idno) {
		if (!validated(idno)) {
			return "保密";
		}
		return (idno.charAt(16) - '0') % 2 == 0 ? "女" : "男";
	}

	public static int getAge(String idno) {
		var d = getBirthday(idno);
		return d != null ? Period.between(d, LocalDate.now()).getYears() : -1;
	}

	public static LocalDate getBirthday(String idno) {
		return validated(idno) ? LocalDate.parse(idno.substring(6, 14), FORMAT) : null;
	}

	public static boolean validated(String idno) {
		if (idno == null || idno.length() != 18) {
			return false;
		}

		String code = idno.substring(0, 2);

		if (!PROVINCES.contains(code)) {
			return false;
		}

		String idno17 = idno.substring(0, 17);

		char[] ca = idno17.toCharArray();
		for (char s : ca) {
			if (!Character.isDigit(s)) {
				return false;
			}
		}

		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum += Character.getNumericValue(ca[i]) * CODES[i];
		}

		int r = sum % 11;
		char endcoef = COEF[r];
		char endidno = idno.charAt(17);

		if (endidno != endcoef) {
			return false;
		}

		return true;
	}

	public static String getZodiac(String idno) {
		var d = getBirthday(idno);
		return d != null ? getZodiac(d.getYear()) : null;
	}

	public static String getZodiac(int year) {
		return ZODIACS[year % 12];
	}

}
