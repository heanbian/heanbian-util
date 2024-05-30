package com.heanbian.block.util;

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

	public static Sex getSex(String idno) {
		if (!validated(idno)) {
			return Sex.保密;
		}

		char cnm = idno.charAt(16);
		if (!Character.isDigit(cnm)) {
			return Sex.保密;
		}

		int num = cnm - '0';
		return num % 2 == 0 ? Sex.女 : Sex.男;
	}

	public static int getAge(String idno) {
		var d = getBirthday(idno);
		if (d == null) {
			return -1;
		}

		var n = LocalDate.now();
		var p = Period.between(d, n);
		return p.getYears();
	}

	public static LocalDate getBirthday(String idno) {
		if (!validated(idno)) {
			return null;
		}

		var birthday = idno.substring(6, 14);
		LocalDate d;
		try {
			d = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyyMMdd"));
		} catch (Exception e) {
			d = null;
		}
		return d;
	}

	public static boolean validated(String idno) {
		if (HeanbianUtils.isBlank(idno) || idno.length() != 18) {
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
		if (d == null) {
			return null;
		}
		return getZodiac(d.getYear());
	}

	public static String getZodiac(int year) {
		int z = year % 12;
		return switch (z) {
		case 4 -> "鼠";
		case 5 -> "牛";
		case 6 -> "虎";
		case 7 -> "兔";
		case 8 -> "龙";
		case 9 -> "蛇";
		case 10 -> "马";
		case 11 -> "羊";
		case 0 -> "猴";
		case 1 -> "鸡";
		case 2 -> "狗";
		case 3 -> "猪";
		default -> null;
		};
	}

}
